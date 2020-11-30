// 
// Decompiled by Procyon v0.5.36
// 

package net.daporkchop.pepsimod.module.impl.movement;

import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.Entity;
import net.daporkchop.pepsimod.util.event.MoveEvent;
import net.daporkchop.pepsimod.module.ModuleCategory;
import net.daporkchop.pepsimod.module.api.ModuleOption;
import net.minecraft.client.renderer.Vector3d;
import net.daporkchop.pepsimod.module.api.Module;

public class SafewalkMod extends Module
{
    public static SafewalkMod INSTANCE;
    private Vector3d vec;
    
    public SafewalkMod() {
        super("Safewalk");
        this.vec = new Vector3d();
        SafewalkMod.INSTANCE = this;
    }
    
    @Override
    public void onEnable() {
    }
    
    @Override
    public void onDisable() {
    }
    
    @Override
    public void tick() {
    }
    
    @Override
    public void init() {
        SafewalkMod.INSTANCE = this;
    }
    
    public ModuleOption[] getDefaultOptions() {
        return new ModuleOption[0];
    }
    
    @Override
    public ModuleCategory getCategory() {
        return ModuleCategory.MOVEMENT;
    }
    
    @Override
    public void onPlayerMove(final MoveEvent event) {
        double x = event.x;
        final double y = event.y;
        double z = event.z;
        if (SafewalkMod.mc.player.onGround) {
            final double increment = 0.05;
            while (x != 0.0 && this.isOffsetBBEmpty(x, -1.0, 0.0)) {
                if (x < increment && x >= -increment) {
                    x = 0.0;
                }
                else if (x > 0.0) {
                    x -= increment;
                }
                else {
                    x += increment;
                }
            }
            while (z != 0.0 && this.isOffsetBBEmpty(0.0, -1.0, z)) {
                if (z < increment && z >= -increment) {
                    z = 0.0;
                }
                else if (z > 0.0) {
                    z -= increment;
                }
                else {
                    z += increment;
                }
            }
            while (x != 0.0 && z != 0.0 && this.isOffsetBBEmpty(x, -1.0, z)) {
                if (x < increment && x >= -increment) {
                    x = 0.0;
                }
                else if (x > 0.0) {
                    x -= increment;
                }
                else {
                    x += increment;
                }
                if (z < increment && z >= -increment) {
                    z = 0.0;
                }
                else if (z > 0.0) {
                    z -= increment;
                }
                else {
                    z += increment;
                }
            }
        }
        event.x = x;
        event.y = y;
        event.z = z;
    }
    
    public boolean isOffsetBBEmpty(final double offsetX, final double offsetY, final double offsetZ) {
        final EntityPlayerSP playerSP = SafewalkMod.mc.player;
        this.vec.x = offsetX;
        this.vec.y = offsetY;
        this.vec.z = offsetZ;
        return SafewalkMod.mc.world.getCollisionBoxes((Entity)playerSP, playerSP.getEntityBoundingBox().offset(this.vec.x, this.vec.y, this.vec.z)).isEmpty();
    }
}
