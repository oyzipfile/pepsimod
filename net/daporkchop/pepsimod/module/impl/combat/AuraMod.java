// 
// Decompiled by Procyon v0.5.36
// 

package net.daporkchop.pepsimod.module.impl.combat;

import net.daporkchop.pepsimod.module.ModuleCategory;
import net.daporkchop.pepsimod.command.api.Command;
import net.daporkchop.pepsimod.module.api.option.OptionExtended;
import net.daporkchop.pepsimod.module.api.option.ExtensionSlider;
import net.daporkchop.pepsimod.module.api.option.ExtensionType;
import net.daporkchop.pepsimod.module.api.OptionCompletions;
import net.daporkchop.pepsimod.module.api.ModuleOption;
import net.minecraft.entity.Entity;
import net.minecraft.util.EnumHand;
import net.minecraft.entity.player.EntityPlayer;
import net.daporkchop.pepsimod.the.wurst.pkg.name.RotationUtils;
import net.daporkchop.pepsimod.the.wurst.pkg.name.EntityUtils;
import net.daporkchop.pepsimod.util.config.impl.TargettingTranslator;
import net.daporkchop.pepsimod.module.impl.player.AutoEatMod;
import net.daporkchop.pepsimod.module.api.Module;

public class AuraMod extends Module
{
    public static final String[] targetBoneStrings;
    public static AuraMod INSTANCE;
    public int lastTick;
    
    public AuraMod() {
        super("Aura");
        this.lastTick = 0;
        AuraMod.INSTANCE = this;
    }
    
    @Override
    public void onEnable() {
        if (AuraMod.mc.player == null) {}
    }
    
    @Override
    public void onDisable() {
        if (AuraMod.mc.player == null) {}
    }
    
    @Override
    public void tick() {
        if (AutoEatMod.INSTANCE.state.enabled && !AutoEatMod.INSTANCE.doneEating) {
            return;
        }
        if (TargettingTranslator.INSTANCE.use_cooldown) {
            if (AuraMod.mc.player.getCooledAttackStrength(0.0f) == 1.0f) {
                final Entity entity = EntityUtils.getBestEntityToAttack(EntityUtils.DEFAULT_SETTINGS);
                if (entity == null) {
                    return;
                }
                if (TargettingTranslator.INSTANCE.rotate) {
                    if (!RotationUtils.faceEntityPacket(entity)) {
                        return;
                    }
                    if (!TargettingTranslator.INSTANCE.silent) {
                        RotationUtils.faceEntityClient(entity);
                    }
                }
                AuraMod.mc.playerController.attackEntity((EntityPlayer)AuraMod.mc.player, entity);
                if (!TargettingTranslator.INSTANCE.silent) {
                    AuraMod.mc.player.swingArm(EnumHand.MAIN_HAND);
                }
            }
        }
        else {
            ++this.lastTick;
            if (this.lastTick >= TargettingTranslator.INSTANCE.delay) {
                this.lastTick = 0;
                final Entity entity = EntityUtils.getBestEntityToAttack(EntityUtils.DEFAULT_SETTINGS);
                if (entity == null) {
                    return;
                }
                if (TargettingTranslator.INSTANCE.rotate) {
                    if (!RotationUtils.faceEntityPacket(entity)) {
                        return;
                    }
                    if (!TargettingTranslator.INSTANCE.silent) {
                        RotationUtils.faceEntityClient(entity);
                    }
                }
                AuraMod.mc.playerController.attackEntity((EntityPlayer)AuraMod.mc.player, entity);
                if (!TargettingTranslator.INSTANCE.silent) {
                    AuraMod.mc.player.swingArm(EnumHand.MAIN_HAND);
                }
            }
        }
    }
    
    @Override
    public void init() {
    }
    
    public ModuleOption[] getDefaultOptions() {
        return new ModuleOption[] { new ModuleOption((T)TargettingTranslator.INSTANCE.players, "players", OptionCompletions.BOOLEAN, value -> {
                TargettingTranslator.INSTANCE.players = value;
                return Boolean.valueOf(true);
            }, () -> TargettingTranslator.INSTANCE.players, "Players"), new ModuleOption((T)TargettingTranslator.INSTANCE.animals, "animals", OptionCompletions.BOOLEAN, value -> {
                TargettingTranslator.INSTANCE.animals = value;
                return Boolean.valueOf(true);
            }, () -> TargettingTranslator.INSTANCE.animals, "Animals"), new ModuleOption((T)TargettingTranslator.INSTANCE.monsters, "monsters", OptionCompletions.BOOLEAN, value -> {
                TargettingTranslator.INSTANCE.monsters = value;
                return Boolean.valueOf(true);
            }, () -> TargettingTranslator.INSTANCE.monsters, "Monsters"), new ModuleOption((T)TargettingTranslator.INSTANCE.golems, "golems", OptionCompletions.BOOLEAN, value -> {
                TargettingTranslator.INSTANCE.golems = value;
                return Boolean.valueOf(true);
            }, () -> TargettingTranslator.INSTANCE.golems, "Golems"), new ModuleOption((T)TargettingTranslator.INSTANCE.sleeping, "sleeping", OptionCompletions.BOOLEAN, value -> {
                TargettingTranslator.INSTANCE.sleeping = value;
                return Boolean.valueOf(true);
            }, () -> TargettingTranslator.INSTANCE.sleeping, "Sleeping"), new ModuleOption((T)TargettingTranslator.INSTANCE.invisible, "invisible", OptionCompletions.BOOLEAN, value -> {
                TargettingTranslator.INSTANCE.invisible = value;
                return Boolean.valueOf(true);
            }, () -> TargettingTranslator.INSTANCE.invisible, "Invisible"), new ModuleOption((T)TargettingTranslator.INSTANCE.teams, "teams", OptionCompletions.BOOLEAN, value -> {
                TargettingTranslator.INSTANCE.teams = value;
                return Boolean.valueOf(true);
            }, () -> TargettingTranslator.INSTANCE.teams, "Teams"), new ModuleOption((T)TargettingTranslator.INSTANCE.friends, "friends", OptionCompletions.BOOLEAN, value -> {
                TargettingTranslator.INSTANCE.friends = value;
                return Boolean.valueOf(true);
            }, () -> TargettingTranslator.INSTANCE.friends, "Friends"), new ModuleOption((T)TargettingTranslator.INSTANCE.through_walls, "through_walls", OptionCompletions.BOOLEAN, value -> {
                TargettingTranslator.INSTANCE.through_walls = value;
                return Boolean.valueOf(true);
            }, () -> TargettingTranslator.INSTANCE.through_walls, "Through Walls"), new ModuleOption((T)TargettingTranslator.INSTANCE.fov, "fov", OptionCompletions.FLOAT, value -> {
                TargettingTranslator.INSTANCE.fov = value;
                return Boolean.valueOf(true);
            }, () -> TargettingTranslator.INSTANCE.fov, "FOV", (OptionExtended)new ExtensionSlider(ExtensionType.VALUE_FLOAT, 0.0f, 360.0f, 0.5f)), new ModuleOption((T)TargettingTranslator.INSTANCE.reach, "reach", OptionCompletions.FLOAT, value -> {
                TargettingTranslator.INSTANCE.reach = value;
                return Boolean.valueOf(true);
            }, () -> TargettingTranslator.INSTANCE.reach, "Reach", (OptionExtended)new ExtensionSlider(ExtensionType.VALUE_FLOAT, 0.0f, 10.0f, 0.1f)), new ModuleOption((T)TargettingTranslator.INSTANCE.delay, "delay", OptionCompletions.INTEGER, value -> {
                TargettingTranslator.INSTANCE.delay = value;
                return Boolean.valueOf(true);
            }, () -> TargettingTranslator.INSTANCE.delay, "Delay", (OptionExtended)new ExtensionSlider(ExtensionType.VALUE_INT, 0, 50, 1)), new ModuleOption((T)TargettingTranslator.INSTANCE.use_cooldown, "use_cooldown", OptionCompletions.BOOLEAN, value -> {
                TargettingTranslator.INSTANCE.use_cooldown = value;
                return Boolean.valueOf(true);
            }, () -> TargettingTranslator.INSTANCE.use_cooldown, "Use Cooldown"), new ModuleOption((T)TargettingTranslator.INSTANCE.silent, "silent", OptionCompletions.BOOLEAN, value -> {
                TargettingTranslator.INSTANCE.silent = value;
                return Boolean.valueOf(true);
            }, () -> TargettingTranslator.INSTANCE.silent, "Silent"), new ModuleOption((T)TargettingTranslator.INSTANCE.rotate, "rotate", OptionCompletions.BOOLEAN, value -> {
                TargettingTranslator.INSTANCE.rotate = value;
                return Boolean.valueOf(true);
            }, () -> TargettingTranslator.INSTANCE.rotate, "Rotate"), new ModuleOption((T)TargettingTranslator.INSTANCE.targetBone, "bone", AuraMod.targetBoneStrings, value -> {
                TargettingTranslator.INSTANCE.targetBone = value;
                return Boolean.valueOf(true);
            }, () -> TargettingTranslator.INSTANCE.targetBone, "Bone", false) };
    }
    
    @Override
    public boolean hasModeInName() {
        return true;
    }
    
    @Override
    public String getModeForName() {
        String mode = "";
        if (TargettingTranslator.INSTANCE.silent) {
            mode += "Silent:";
        }
        if (TargettingTranslator.INSTANCE.rotate) {
            mode += "Rotate";
        }
        else {
            mode += "NoRotate";
        }
        return mode;
    }
    
    @Override
    public String getSuggestion(final String cmd, final String[] args) {
        if (args.length == 2 && args[1].equals("bone")) {
            return cmd + " " + AuraMod.targetBoneStrings[0];
        }
        if (args.length != 3 || !args[1].equals("bone")) {
            return super.getSuggestion(cmd, args);
        }
        if (args[2].isEmpty()) {
            return cmd + AuraMod.targetBoneStrings[0];
        }
        for (final String s : AuraMod.targetBoneStrings) {
            if (s.startsWith(args[2])) {
                return args[0] + " " + args[1] + " " + s;
            }
        }
        return "";
    }
    
    @Override
    public void execute(final String cmd, final String[] args) {
        if (args.length == 3 && !args[2].isEmpty() && cmd.startsWith(".aura bone ")) {
            final String s = args[2].toUpperCase();
            try {
                final TargettingTranslator.TargetBone bone = TargettingTranslator.TargetBone.valueOf(s);
                if (bone == null) {
                    Command.clientMessage("Not a valid bone: " + args[2]);
                }
                else {
                    this.getOptionByName("bone").setValue(bone);
                    Command.clientMessage("Set §o" + args[1] + '§' + "r to " + '§' + "o" + s);
                }
            }
            catch (Exception e) {
                Command.clientMessage("Not a valid bone: " + args[2]);
            }
            return;
        }
        super.execute(cmd, args);
    }
    
    @Override
    public ModuleCategory getCategory() {
        return ModuleCategory.COMBAT;
    }
    
    static {
        targetBoneStrings = new String[] { "head", "feet", "middle" };
    }
}
