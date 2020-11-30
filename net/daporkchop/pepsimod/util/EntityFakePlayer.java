// 
// Decompiled by Procyon v0.5.36
// 

package net.daporkchop.pepsimod.util;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.Entity;
import net.minecraft.world.World;
import net.minecraft.client.entity.EntityOtherPlayerMP;

public class EntityFakePlayer extends EntityOtherPlayerMP
{
    public EntityFakePlayer() {
        super((World)PepsiConstants.mc.world, PepsiConstants.mc.player.getGameProfile());
        this.copyLocationAndAnglesFrom((Entity)PepsiConstants.mc.player);
        this.inventory.copyInventory(PepsiConstants.mc.player.inventory);
        PepsiUtils.copyPlayerModel((EntityPlayer)PepsiConstants.mc.player, (EntityPlayer)this);
        this.rotationYawHead = PepsiConstants.mc.player.rotationYawHead;
        this.renderYawOffset = PepsiConstants.mc.player.renderYawOffset;
        this.chasingPosX = this.posX;
        this.chasingPosY = this.posY;
        this.chasingPosZ = this.posZ;
        PepsiConstants.mc.world.addEntityToWorld(this.getEntityId(), (Entity)this);
    }
    
    public void resetPlayerPosition() {
        PepsiConstants.mc.player.setPositionAndRotation(this.posX, this.posY, this.posZ, this.rotationYaw, this.rotationPitch);
    }
    
    public void despawn() {
        PepsiConstants.mc.world.removeEntityFromWorld(this.getEntityId());
    }
}
