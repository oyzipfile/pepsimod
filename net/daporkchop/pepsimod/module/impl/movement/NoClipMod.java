// 
// Decompiled by Procyon v0.5.36
// 

package net.daporkchop.pepsimod.module.impl.movement;

import net.daporkchop.pepsimod.module.ModuleCategory;
import net.daporkchop.pepsimod.module.api.ModuleOption;
import net.minecraft.client.entity.EntityPlayerSP;
import net.daporkchop.pepsimod.util.ReflectionStuff;
import net.daporkchop.pepsimod.module.api.Module;

public class NoClipMod extends Module
{
    public static NoClipMod INSTANCE;
    
    public NoClipMod() {
        super("NoClip");
        NoClipMod.INSTANCE = this;
    }
    
    @Override
    public void onEnable() {
    }
    
    @Override
    public void onDisable() {
        if (NoClipMod.pepsimod.isInitialized) {
            NoClipMod.mc.player.noClip = false;
        }
    }
    
    @Override
    public void tick() {
        NoClipMod.mc.player.noClip = true;
        NoClipMod.mc.player.fallDistance = 0.0f;
        NoClipMod.mc.player.onGround = false;
        NoClipMod.mc.player.capabilities.isFlying = false;
        NoClipMod.mc.player.motionX = 0.0;
        NoClipMod.mc.player.motionY = 0.0;
        NoClipMod.mc.player.motionZ = 0.0;
        final float speed = 0.2f;
        NoClipMod.mc.player.jumpMovementFactor = speed;
        if (ReflectionStuff.getPressed(NoClipMod.mc.gameSettings.keyBindJump)) {
            final EntityPlayerSP player = NoClipMod.mc.player;
            player.motionY += speed;
        }
        if (ReflectionStuff.getPressed(NoClipMod.mc.gameSettings.keyBindSneak)) {
            final EntityPlayerSP player2 = NoClipMod.mc.player;
            player2.motionY -= speed;
        }
    }
    
    @Override
    public void init() {
        NoClipMod.INSTANCE = this;
    }
    
    public ModuleOption[] getDefaultOptions() {
        return new ModuleOption[0];
    }
    
    @Override
    public ModuleCategory getCategory() {
        return ModuleCategory.MOVEMENT;
    }
}
