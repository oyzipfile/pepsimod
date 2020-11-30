// 
// Decompiled by Procyon v0.5.36
// 

package net.daporkchop.pepsimod.module.impl.movement;

import net.daporkchop.pepsimod.command.api.Command;
import net.daporkchop.pepsimod.module.ModuleCategory;
import net.daporkchop.pepsimod.module.api.option.OptionExtended;
import net.daporkchop.pepsimod.module.api.option.ExtensionSlider;
import net.daporkchop.pepsimod.module.api.option.ExtensionType;
import net.daporkchop.pepsimod.module.api.OptionCompletions;
import net.daporkchop.pepsimod.module.api.ModuleOption;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemElytra;
import net.daporkchop.pepsimod.util.ReflectionStuff;
import net.minecraft.network.Packet;
import net.minecraft.entity.Entity;
import net.minecraft.network.play.client.CPacketEntityAction;
import net.minecraft.init.Items;
import net.daporkchop.pepsimod.util.PepsiUtils;
import net.daporkchop.pepsimod.module.api.Module;
import net.daporkchop.pepsimod.module.ModuleManager;
import net.daporkchop.pepsimod.util.config.impl.ElytraFlyTranslator;
import net.daporkchop.pepsimod.module.api.TimeModule;

public class ElytraFlyMod extends TimeModule
{
    public static final String[] modes;
    public static ElytraFlyMod INSTANCE;
    
    public ElytraFlyMod() {
        super("Elytra+");
        ElytraFlyMod.INSTANCE = this;
    }
    
    @Override
    public void onEnable() {
        if (ElytraFlyTranslator.INSTANCE.mode == ElytraFlyTranslator.ElytraFlyMode.PACKET && ElytraFlyMod.mc.world == null) {
            ModuleManager.disableModule(this);
        }
    }
    
    @Override
    public void onDisable() {
    }
    
    @Override
    public void tick() {
        this.updateMS();
        final ItemStack chestplate = PepsiUtils.getWearingArmor(1);
        if (chestplate == null || chestplate.getItem() != Items.ELYTRA) {
            return;
        }
        if (ElytraFlyMod.mc.player.isElytraFlying()) {
            if (ElytraFlyTranslator.INSTANCE.stopInWater && ElytraFlyMod.mc.player.isInWater()) {
                ElytraFlyMod.mc.getConnection().sendPacket((Packet)new CPacketEntityAction((Entity)ElytraFlyMod.mc.player, CPacketEntityAction.Action.START_FALL_FLYING));
                return;
            }
            if (ElytraFlyTranslator.INSTANCE.fly && ElytraFlyTranslator.INSTANCE.mode == ElytraFlyTranslator.ElytraFlyMode.NORMAL) {
                if (ReflectionStuff.getPressed(ElytraFlyMod.mc.gameSettings.keyBindJump)) {
                    final EntityPlayerSP player = ElytraFlyMod.mc.player;
                    player.motionY += 0.08 * ElytraFlyTranslator.INSTANCE.speed;
                }
                else if (ReflectionStuff.getPressed(ElytraFlyMod.mc.gameSettings.keyBindSneak)) {
                    final EntityPlayerSP player2 = ElytraFlyMod.mc.player;
                    player2.motionY -= 0.04 * ElytraFlyTranslator.INSTANCE.speed;
                }
                if (ReflectionStuff.getPressed(ElytraFlyMod.mc.gameSettings.keyBindForward)) {
                    final double yaw = Math.toRadians(ElytraFlyMod.mc.player.rotationYaw);
                    final EntityPlayerSP player3 = ElytraFlyMod.mc.player;
                    player3.motionX -= Math.sin(yaw) * ElytraFlyTranslator.INSTANCE.speed;
                    final EntityPlayerSP player4 = ElytraFlyMod.mc.player;
                    player4.motionZ += Math.cos(yaw) * ElytraFlyTranslator.INSTANCE.speed;
                }
                else if (ReflectionStuff.getPressed(ElytraFlyMod.mc.gameSettings.keyBindBack)) {
                    final double yaw = Math.toRadians(ElytraFlyMod.mc.player.rotationYaw);
                    final EntityPlayerSP player5 = ElytraFlyMod.mc.player;
                    player5.motionX += Math.sin(yaw) * ElytraFlyTranslator.INSTANCE.speed;
                    final EntityPlayerSP player6 = ElytraFlyMod.mc.player;
                    player6.motionZ -= Math.cos(yaw) * ElytraFlyTranslator.INSTANCE.speed;
                }
            }
        }
        else if (ElytraFlyTranslator.INSTANCE.easyStart && ElytraFlyTranslator.INSTANCE.mode != ElytraFlyTranslator.ElytraFlyMode.PACKET && ItemElytra.isUsable(chestplate) && ElytraFlyMod.mc.gameSettings.keyBindJump.isPressed()) {
            if (this.hasTimePassedM(1000L)) {
                this.updateLastMS();
                ElytraFlyMod.mc.player.setJumping(false);
                ElytraFlyMod.mc.player.setSprinting(true);
                ElytraFlyMod.mc.player.jump();
            }
            ElytraFlyMod.mc.getConnection().sendPacket((Packet)new CPacketEntityAction((Entity)ElytraFlyMod.mc.player, CPacketEntityAction.Action.START_FALL_FLYING));
        }
        if (ElytraFlyTranslator.INSTANCE.fly && ElytraFlyTranslator.INSTANCE.mode == ElytraFlyTranslator.ElytraFlyMode.PACKET) {
            final EntityPlayerSP player7 = ElytraFlyMod.mc.player;
            final EntityPlayerSP player8 = ElytraFlyMod.mc.player;
            final double n = 0.0;
            player8.motionZ = n;
            player7.motionX = n;
            if (ReflectionStuff.getPressed(ElytraFlyMod.mc.gameSettings.keyBindForward)) {
                final double yaw = Math.toRadians(ElytraFlyMod.mc.player.rotationYaw);
                final EntityPlayerSP player9 = ElytraFlyMod.mc.player;
                player9.motionX -= Math.sin(yaw) * ElytraFlyTranslator.INSTANCE.speed;
                final EntityPlayerSP player10 = ElytraFlyMod.mc.player;
                player10.motionZ += Math.cos(yaw) * ElytraFlyTranslator.INSTANCE.speed;
            }
            else if (ReflectionStuff.getPressed(ElytraFlyMod.mc.gameSettings.keyBindBack)) {
                final double yaw = Math.toRadians(ElytraFlyMod.mc.player.rotationYaw);
                final EntityPlayerSP player11 = ElytraFlyMod.mc.player;
                player11.motionX += Math.sin(yaw) * ElytraFlyTranslator.INSTANCE.speed;
                final EntityPlayerSP player12 = ElytraFlyMod.mc.player;
                player12.motionZ -= Math.cos(yaw) * ElytraFlyTranslator.INSTANCE.speed;
            }
            ElytraFlyMod.mc.player.motionY = 0.0;
            ElytraFlyMod.mc.getConnection().sendPacket((Packet)new CPacketEntityAction((Entity)ElytraFlyMod.mc.player, CPacketEntityAction.Action.START_FALL_FLYING));
            ElytraFlyMod.mc.getConnection().sendPacket((Packet)new CPacketEntityAction((Entity)ElytraFlyMod.mc.player, CPacketEntityAction.Action.START_FALL_FLYING));
        }
    }
    
    @Override
    public void init() {
    }
    
    public ModuleOption[] getDefaultOptions() {
        return new ModuleOption[] { new ModuleOption((T)ElytraFlyTranslator.INSTANCE.easyStart, "easyStart", OptionCompletions.BOOLEAN, value -> {
                ElytraFlyTranslator.INSTANCE.easyStart = value;
                return Boolean.valueOf(true);
            }, () -> ElytraFlyTranslator.INSTANCE.easyStart, "EasyStart"), new ModuleOption((T)ElytraFlyTranslator.INSTANCE.stopInWater, "stopInWater", OptionCompletions.BOOLEAN, value -> {
                ElytraFlyTranslator.INSTANCE.stopInWater = value;
                return Boolean.valueOf(true);
            }, () -> ElytraFlyTranslator.INSTANCE.stopInWater, "StopInWater"), new ModuleOption((T)ElytraFlyTranslator.INSTANCE.fly, "fly", OptionCompletions.BOOLEAN, value -> {
                ElytraFlyTranslator.INSTANCE.fly = value;
                return Boolean.valueOf(true);
            }, () -> ElytraFlyTranslator.INSTANCE.fly, "Fly"), new ModuleOption((T)ElytraFlyTranslator.INSTANCE.mode, "mode", ElytraFlyMod.modes, value -> {
                ElytraFlyTranslator.INSTANCE.mode = value;
                return Boolean.valueOf(true);
            }, () -> ElytraFlyTranslator.INSTANCE.mode, "Mode", false), new ModuleOption((T)ElytraFlyTranslator.INSTANCE.speed, "speed", OptionCompletions.FLOAT, value -> {
                ElytraFlyTranslator.INSTANCE.speed = Math.max(value, 0.0f);
                return Boolean.valueOf(true);
            }, () -> ElytraFlyTranslator.INSTANCE.speed, "Speed", (OptionExtended)new ExtensionSlider(ExtensionType.VALUE_FLOAT, 0.0f, 0.5f, 0.005f)) };
    }
    
    @Override
    public boolean hasModeInName() {
        return true;
    }
    
    @Override
    public String getModeForName() {
        return ElytraFlyTranslator.INSTANCE.mode.name();
    }
    
    @Override
    public ModuleCategory getCategory() {
        return ModuleCategory.MOVEMENT;
    }
    
    @Override
    public String getSuggestion(final String cmd, final String[] args) {
        if (args.length == 2 && args[1].equals("mode")) {
            return cmd + " " + ElytraFlyMod.modes[0];
        }
        if (args.length != 3 || !args[1].equals("mode")) {
            return super.getSuggestion(cmd, args);
        }
        if (args[2].isEmpty()) {
            return cmd + ElytraFlyMod.modes[0];
        }
        for (final String s : ElytraFlyMod.modes) {
            if (s.startsWith(args[2])) {
                return args[0] + " " + args[1] + " " + s;
            }
        }
        return "";
    }
    
    @Override
    public void execute(final String cmd, final String[] args) {
        if (args.length == 3 && !args[2].isEmpty() && cmd.startsWith(".elytra+ mode ")) {
            final String s = args[2].toUpperCase();
            try {
                final ElytraFlyTranslator.ElytraFlyMode mode = ElytraFlyTranslator.ElytraFlyMode.valueOf(s);
                if (mode == null) {
                    Command.clientMessage("Not a valid mode: " + args[2]);
                }
                else {
                    this.getOptionByName("mode").setValue(mode);
                    Command.clientMessage("Set §o" + args[1] + '§' + "r to " + '§' + "o" + s);
                }
            }
            catch (Exception e) {
                Command.clientMessage("Not a valid mode: " + args[2]);
            }
            return;
        }
        super.execute(cmd, args);
    }
    
    static {
        modes = new String[] { "normal", "packet" };
    }
}
