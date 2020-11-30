// 
// Decompiled by Procyon v0.5.36
// 

package net.daporkchop.pepsimod.the.wurst.pkg.name;

import net.daporkchop.pepsimod.util.config.impl.TargettingTranslator;
import java.util.Iterator;
import java.util.ArrayList;
import net.minecraft.entity.monster.EntityGolem;
import net.minecraft.entity.EntityFlying;
import net.minecraft.entity.monster.EntitySlime;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.passive.EntityWaterMob;
import net.minecraft.entity.passive.EntityAmbientCreature;
import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.EntityLiving;
import net.daporkchop.pepsimod.util.config.impl.FriendsTranslator;
import net.minecraft.entity.player.EntityPlayer;
import net.daporkchop.pepsimod.util.PepsiUtils;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.Entity;
import net.daporkchop.pepsimod.util.PepsiConstants;

public class EntityUtils extends PepsiConstants
{
    public static final TargetSettings DEFAULT_SETTINGS;
    public static final String[] colors;
    
    public static boolean isCorrectEntity(final Entity en, final TargetSettings settings) {
        if (en == null) {
            return false;
        }
        if (en instanceof EntityLivingBase && (((EntityLivingBase)en).isDead || ((EntityLivingBase)en).getHealth() <= 0.0f)) {
            return false;
        }
        if (EntityUtils.mc.player.getDistance(en) > settings.getRange()) {
            return false;
        }
        if (settings.getFOV() < 360.0f && RotationUtils.getAngleToClientRotation(PepsiUtils.adjustVectorForBone(en.getEntityBoundingBox().getCenter(), en, settings.getTargetBone())) > settings.getFOV() / 2.0f) {
            return false;
        }
        if (!settings.targetBehindWalls() && !PepsiUtils.canEntityBeSeen(en, (EntityPlayer)EntityUtils.mc.player, settings.getTargetBone())) {
            return false;
        }
        if (!settings.targetFriends() && FriendsTranslator.INSTANCE.isFriend(en)) {
            return false;
        }
        if (en instanceof EntityPlayer) {
            if (!settings.targetPlayers()) {
                if (!((EntityPlayer)en).isPlayerSleeping() && !en.isInvisible()) {
                    return false;
                }
            }
            else if (!settings.targetSleepingPlayers()) {
                if (((EntityPlayer)en).isPlayerSleeping()) {
                    return false;
                }
            }
            else if (!settings.targetInvisiblePlayers() && en.isInvisible()) {
                return false;
            }
            if (settings.targetTeams() && !checkName(en.getDisplayName().getFormattedText(), settings.getTeamColors())) {
                return false;
            }
            if (en == EntityUtils.mc.player) {
                return false;
            }
            if (en.getName().equals(EntityUtils.mc.player.getName())) {
                return false;
            }
        }
        else {
            if (!(en instanceof EntityLiving)) {
                return false;
            }
            if (en.isInvisible()) {
                if (!settings.targetInvisibleMobs()) {
                    return false;
                }
            }
            else if (en instanceof EntityAgeable || en instanceof EntityAmbientCreature || en instanceof EntityWaterMob) {
                if (!settings.targetAnimals()) {
                    return false;
                }
            }
            else if (en instanceof EntityMob || en instanceof EntitySlime || en instanceof EntityFlying) {
                if (!settings.targetMonsters()) {
                    return false;
                }
            }
            else {
                if (!(en instanceof EntityGolem)) {
                    return false;
                }
                if (!settings.targetGolems()) {
                    return false;
                }
            }
            if (settings.targetTeams() && en.hasCustomName() && !checkName(en.getCustomNameTag(), settings.getTeamColors())) {
                return false;
            }
        }
        return true;
    }
    
    private static boolean checkName(final String name, final boolean[] teamColors) {
        boolean hasKnownColor = false;
        for (int i = 0; i < 16; ++i) {
            if (name.contains('§' + EntityUtils.colors[i])) {
                hasKnownColor = true;
                if (teamColors[i]) {
                    return true;
                }
            }
        }
        return !hasKnownColor && teamColors[15];
    }
    
    public static ArrayList<Entity> getValidEntities(final TargetSettings settings) {
        final ArrayList<Entity> validEntities = new ArrayList<Entity>();
        for (final Entity entity : EntityUtils.mc.world.loadedEntityList) {
            if (isCorrectEntity(entity, settings)) {
                validEntities.add(entity);
            }
            if (validEntities.size() >= 64) {
                break;
            }
        }
        return validEntities;
    }
    
    public static Entity getClosestEntity(final TargetSettings settings) {
        Entity closestEntity = null;
        for (final Entity entity : EntityUtils.mc.world.loadedEntityList) {
            if (isCorrectEntity(entity, settings) && (closestEntity == null || EntityUtils.mc.player.getDistance(entity) < EntityUtils.mc.player.getDistance(closestEntity))) {
                closestEntity = entity;
            }
        }
        return closestEntity;
    }
    
    public static Entity getBestEntityToAttack(final TargetSettings settings) {
        Entity bestEntity = null;
        float bestAngle = Float.POSITIVE_INFINITY;
        for (final Entity entity : EntityUtils.mc.world.loadedEntityList) {
            if (!isCorrectEntity(entity, settings)) {
                continue;
            }
            final float angle = RotationUtils.getAngleToServerRotation(PepsiUtils.adjustVectorForBone(entity.getEntityBoundingBox().getCenter(), entity, settings.getTargetBone()));
            if (angle >= bestAngle) {
                continue;
            }
            bestEntity = entity;
            bestAngle = angle;
        }
        return bestEntity;
    }
    
    public static Entity getClosestEntityOtherThan(final Entity otherEntity, final TargetSettings settings) {
        Entity closestEnemy = null;
        for (final Entity entity : EntityUtils.mc.world.loadedEntityList) {
            if (isCorrectEntity(entity, settings) && entity != otherEntity && (closestEnemy == null || EntityUtils.mc.player.getDistance(entity) < EntityUtils.mc.player.getDistance(closestEnemy))) {
                closestEnemy = entity;
            }
        }
        return closestEnemy;
    }
    
    public static Entity getClosestEntityWithName(final String name, final TargetSettings settings) {
        Entity closestEntity = null;
        for (final Entity entity : EntityUtils.mc.world.loadedEntityList) {
            if (!isCorrectEntity(entity, settings)) {
                continue;
            }
            if (!entity.getName().equalsIgnoreCase(name)) {
                continue;
            }
            if (closestEntity != null && EntityUtils.mc.player.getDistanceSq(entity) >= EntityUtils.mc.player.getDistanceSq(closestEntity)) {
                continue;
            }
            closestEntity = entity;
        }
        return closestEntity;
    }
    
    static {
        DEFAULT_SETTINGS = new TargetSettings();
        colors = new String[] { "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "a", "b", "c", "d", "e", "f" };
    }
    
    public static class TargetSettings
    {
        public static final boolean[] team_colors;
        
        public boolean targetFriends() {
            return TargettingTranslator.INSTANCE.friends;
        }
        
        public boolean targetBehindWalls() {
            return TargettingTranslator.INSTANCE.through_walls;
        }
        
        public float getRange() {
            return TargettingTranslator.INSTANCE.reach;
        }
        
        public float getFOV() {
            return TargettingTranslator.INSTANCE.fov;
        }
        
        public boolean targetPlayers() {
            return TargettingTranslator.INSTANCE.players;
        }
        
        public boolean targetAnimals() {
            return TargettingTranslator.INSTANCE.animals;
        }
        
        public boolean targetMonsters() {
            return TargettingTranslator.INSTANCE.monsters;
        }
        
        public boolean targetGolems() {
            return TargettingTranslator.INSTANCE.golems;
        }
        
        public boolean targetSleepingPlayers() {
            return TargettingTranslator.INSTANCE.sleeping;
        }
        
        public boolean targetInvisiblePlayers() {
            return TargettingTranslator.INSTANCE.players && TargettingTranslator.INSTANCE.invisible;
        }
        
        public boolean targetInvisibleMobs() {
            return TargettingTranslator.INSTANCE.monsters && TargettingTranslator.INSTANCE.invisible;
        }
        
        public boolean targetTeams() {
            return TargettingTranslator.INSTANCE.teams;
        }
        
        public boolean[] getTeamColors() {
            return TargetSettings.team_colors;
        }
        
        public TargettingTranslator.TargetBone getTargetBone() {
            return TargettingTranslator.INSTANCE.targetBone;
        }
        
        static {
            team_colors = new boolean[] { true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true };
        }
    }
}
