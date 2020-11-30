// 
// Decompiled by Procyon v0.5.36
// 

package net.daporkchop.pepsimod.module.impl.misc;

import net.daporkchop.pepsimod.module.ModuleCategory;
import net.daporkchop.pepsimod.module.api.ModuleLaunchState;
import net.minecraft.network.play.client.CPacketPlayer;
import net.minecraft.network.Packet;
import net.daporkchop.pepsimod.module.api.option.OptionExtended;
import net.daporkchop.pepsimod.module.api.option.ExtensionSlider;
import net.daporkchop.pepsimod.module.api.option.ExtensionType;
import net.daporkchop.pepsimod.command.api.Command;
import net.daporkchop.pepsimod.module.api.ModuleOption;
import net.daporkchop.pepsimod.util.config.impl.FreecamTranslator;
import net.minecraft.client.entity.EntityPlayerSP;
import org.lwjgl.opengl.Display;
import net.daporkchop.pepsimod.util.EntityFakePlayer;
import net.daporkchop.pepsimod.module.api.Module;

public class FreecamMod extends Module
{
    public static FreecamMod INSTANCE;
    public EntityFakePlayer fakePlayer;
    
    public static void doMove(final float speed) {
        FreecamMod.mc.player.motionX = 0.0;
        FreecamMod.mc.player.motionY = 0.0;
        FreecamMod.mc.player.motionZ = 0.0;
        if (Display.isActive()) {
            if (FreecamMod.mc.gameSettings.keyBindJump.isKeyDown()) {
                final EntityPlayerSP player = FreecamMod.mc.player;
                player.motionY += speed;
            }
            if (FreecamMod.mc.gameSettings.keyBindSneak.isKeyDown()) {
                final EntityPlayerSP player2 = FreecamMod.mc.player;
                player2.motionY -= speed;
            }
            float forward = 0.0f;
            if (FreecamMod.mc.gameSettings.keyBindForward.isKeyDown()) {
                forward += speed;
            }
            if (FreecamMod.mc.gameSettings.keyBindBack.isKeyDown()) {
                forward -= speed;
            }
            float strafe = 0.0f;
            if (FreecamMod.mc.gameSettings.keyBindLeft.isKeyDown()) {
                strafe += speed;
            }
            if (FreecamMod.mc.gameSettings.keyBindRight.isKeyDown()) {
                strafe -= speed;
            }
            final float yaw = FreecamMod.mc.player.rotationYaw;
            FreecamMod.mc.player.motionX = forward * Math.cos(Math.toRadians(yaw + 90.0f)) + strafe * Math.sin(Math.toRadians(yaw + 90.0f));
            FreecamMod.mc.player.motionZ = forward * Math.sin(Math.toRadians(yaw + 90.0f)) - strafe * Math.cos(Math.toRadians(yaw + 90.0f));
        }
    }
    
    public FreecamMod() {
        super("Freecam");
        FreecamMod.INSTANCE = this;
    }
    
    @Override
    public void onEnable() {
        FreecamMod.INSTANCE = this;
        if (FreecamMod.pepsimod.hasInitializedModules) {
            this.fakePlayer = new EntityFakePlayer();
        }
    }
    
    @Override
    public void onDisable() {
        FreecamMod.INSTANCE = this;
        if (FreecamMod.pepsimod.hasInitializedModules) {
            this.fakePlayer.resetPlayerPosition();
            this.fakePlayer.despawn();
        }
    }
    
    @Override
    public void tick() {
        doMove(FreecamTranslator.INSTANCE.speed);
    }
    
    @Override
    public void init() {
        FreecamMod.INSTANCE = this;
    }
    
    public ModuleOption[] getDefaultOptions() {
        return new ModuleOption[] { new ModuleOption((T)1.0f, "speed", new String[] { "1.0", "0.0" }, value -> {
                if (value <= 0.0f) {
                    Command.clientMessage("Speed cannot be negative or 0!");
                    return Boolean.valueOf(false);
                }
                else {
                    FreecamTranslator.INSTANCE.speed = value;
                    return Boolean.valueOf(true);
                }
            }, () -> FreecamTranslator.INSTANCE.speed, "Speed", (OptionExtended)new ExtensionSlider(ExtensionType.VALUE_FLOAT, 0.0f, 1.0f, 0.1f)) };
    }
    
    @Override
    public boolean preSendPacket(final Packet<?> packetIn) {
        return packetIn instanceof CPacketPlayer;
    }
    
    @Override
    public ModuleLaunchState getLaunchState() {
        return ModuleLaunchState.DISABLED;
    }
    
    @Override
    public ModuleCategory getCategory() {
        return ModuleCategory.MISC;
    }
}
