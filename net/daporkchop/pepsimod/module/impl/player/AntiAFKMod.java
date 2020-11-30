// 
// Decompiled by Procyon v0.5.36
// 

package net.daporkchop.pepsimod.module.impl.player;

import net.daporkchop.pepsimod.module.ModuleCategory;
import net.daporkchop.pepsimod.module.api.OptionCompletions;
import net.daporkchop.pepsimod.module.api.option.OptionExtended;
import net.daporkchop.pepsimod.module.api.option.ExtensionSlider;
import net.daporkchop.pepsimod.module.api.option.ExtensionType;
import net.daporkchop.pepsimod.module.api.ModuleOption;
import net.minecraft.client.settings.KeyBinding;
import org.lwjgl.opengl.Display;
import java.util.List;
import net.minecraft.util.EnumHand;
import net.daporkchop.pepsimod.optimization.OverrideCounter;
import net.daporkchop.pepsimod.the.wurst.pkg.name.RotationUtils;
import java.util.concurrent.ThreadLocalRandom;
import net.minecraft.util.Tuple;
import java.util.ArrayList;
import net.daporkchop.pepsimod.util.config.impl.AntiAFKTranslator;
import net.daporkchop.pepsimod.module.api.Module;

public class AntiAFKMod extends Module
{
    public static AntiAFKMod INSTANCE;
    protected long lastRun;
    protected Runnable cleaner;
    
    public AntiAFKMod() {
        super("AntiAFK");
        this.lastRun = System.currentTimeMillis();
        this.cleaner = null;
        AntiAFKMod.INSTANCE = this;
    }
    
    @Override
    public void onEnable() {
        this.lastRun = System.currentTimeMillis();
    }
    
    @Override
    public void onDisable() {
        this.clean();
    }
    
    @Override
    public void tick() {
        if (AntiAFKTranslator.INSTANCE.requireInactive && AntiAFKMod.mc.inGameHasFocus) {
            this.lastRun = System.currentTimeMillis();
        }
        else if (this.isAnyKeyPressed()) {
            this.clean();
            this.lastRun = System.currentTimeMillis();
        }
        else if (this.lastRun + AntiAFKTranslator.INSTANCE.delay <= System.currentTimeMillis()) {
            this.clean();
            this.lastRun = System.currentTimeMillis();
            final List<Tuple<Runnable, Runnable>> functions = new ArrayList<Tuple<Runnable, Runnable>>();
            if (AntiAFKTranslator.INSTANCE.spin) {
                functions.add((Tuple<Runnable, Runnable>)new Tuple(() -> RotationUtils.faceVectorClient(AntiAFKMod.mc.player.getPositionVector().add(ThreadLocalRandom.current().nextDouble(-5.0, 5.0), ThreadLocalRandom.current().nextDouble(-5.0, 5.0), ThreadLocalRandom.current().nextDouble(-5.0, 5.0))), () -> {}));
            }
            if (AntiAFKTranslator.INSTANCE.sneak) {
                functions.add((Tuple<Runnable, Runnable>)new Tuple(() -> ((OverrideCounter)AntiAFKMod.mc.gameSettings.keyBindSneak).incrementOverride(), () -> ((OverrideCounter)AntiAFKMod.mc.gameSettings.keyBindSneak).decrementOverride()));
            }
            if (AntiAFKTranslator.INSTANCE.swingArm) {
                functions.add((Tuple<Runnable, Runnable>)new Tuple(() -> AntiAFKMod.mc.player.swingArm(EnumHand.MAIN_HAND), () -> {}));
            }
            if (AntiAFKTranslator.INSTANCE.strafe) {
                final int flag = ThreadLocalRandom.current().nextInt(2);
                functions.add((Tuple<Runnable, Runnable>)new Tuple(() -> {
                    switch (flag) {
                        case 0: {
                            ((OverrideCounter)AntiAFKMod.mc.gameSettings.keyBindLeft).incrementOverride();
                            break;
                        }
                        case 1: {
                            ((OverrideCounter)AntiAFKMod.mc.gameSettings.keyBindRight).incrementOverride();
                            break;
                        }
                    }
                    return;
                }, () -> {
                    switch (flag) {
                        case 0: {
                            ((OverrideCounter)AntiAFKMod.mc.gameSettings.keyBindLeft).decrementOverride();
                            break;
                        }
                        case 1: {
                            ((OverrideCounter)AntiAFKMod.mc.gameSettings.keyBindRight).decrementOverride();
                            break;
                        }
                    }
                    return;
                }));
            }
            else if (AntiAFKTranslator.INSTANCE.move) {
                final int flag = ThreadLocalRandom.current().nextInt(4);
                final int flag2;
                functions.add((Tuple<Runnable, Runnable>)new Tuple(() -> {
                    switch (flag2) {
                        case 0: {
                            ((OverrideCounter)AntiAFKMod.mc.gameSettings.keyBindForward).incrementOverride();
                            break;
                        }
                        case 1: {
                            ((OverrideCounter)AntiAFKMod.mc.gameSettings.keyBindBack).incrementOverride();
                            break;
                        }
                        case 2: {
                            ((OverrideCounter)AntiAFKMod.mc.gameSettings.keyBindLeft).incrementOverride();
                            break;
                        }
                        case 3: {
                            ((OverrideCounter)AntiAFKMod.mc.gameSettings.keyBindRight).incrementOverride();
                            break;
                        }
                    }
                    return;
                }, () -> {
                    switch (flag2) {
                        case 0: {
                            ((OverrideCounter)AntiAFKMod.mc.gameSettings.keyBindForward).decrementOverride();
                            break;
                        }
                        case 1: {
                            ((OverrideCounter)AntiAFKMod.mc.gameSettings.keyBindBack).decrementOverride();
                            break;
                        }
                        case 2: {
                            ((OverrideCounter)AntiAFKMod.mc.gameSettings.keyBindLeft).decrementOverride();
                            break;
                        }
                        case 3: {
                            ((OverrideCounter)AntiAFKMod.mc.gameSettings.keyBindRight).decrementOverride();
                            break;
                        }
                    }
                    return;
                }));
            }
            if (!functions.isEmpty()) {
                final Tuple<Runnable, Runnable> tuple = functions.get(ThreadLocalRandom.current().nextInt(functions.size()));
                ((Runnable)tuple.getFirst()).run();
                this.cleaner = (Runnable)tuple.getSecond();
            }
        }
    }
    
    protected void clean() {
        if (this.cleaner != null) {
            this.cleaner.run();
            this.cleaner = null;
        }
    }
    
    protected boolean isAnyKeyPressed() {
        if (!Display.isActive()) {
            return false;
        }
        for (final KeyBinding keyBind : AntiAFKMod.mc.gameSettings.keyBindings) {
            if (keyBind.isKeyDown()) {
                return true;
            }
        }
        return false;
    }
    
    @Override
    public void init() {
        AntiAFKMod.INSTANCE = this;
    }
    
    public ModuleOption[] getDefaultOptions() {
        return new ModuleOption[] { new ModuleOption((T)AntiAFKTranslator.INSTANCE.delay, "delay", new String[] { "1000", "2000", "3000", "4000", "5000" }, val -> {
                AntiAFKTranslator.INSTANCE.delay = val;
                return Boolean.valueOf(true);
            }, () -> AntiAFKTranslator.INSTANCE.delay, "Delay", (OptionExtended)new ExtensionSlider(ExtensionType.VALUE_INT, 0, 10000, 500)), new ModuleOption((T)AntiAFKTranslator.INSTANCE.spin, "spin", OptionCompletions.BOOLEAN, val -> {
                AntiAFKTranslator.INSTANCE.spin = val;
                this.clean();
                return Boolean.valueOf(true);
            }, () -> AntiAFKTranslator.INSTANCE.spin, "Spin"), new ModuleOption((T)AntiAFKTranslator.INSTANCE.sneak, "sneak", OptionCompletions.BOOLEAN, val -> {
                AntiAFKTranslator.INSTANCE.sneak = val;
                this.clean();
                return Boolean.valueOf(true);
            }, () -> AntiAFKTranslator.INSTANCE.sneak, "Sneak"), new ModuleOption((T)AntiAFKTranslator.INSTANCE.swingArm, "swingArm", OptionCompletions.BOOLEAN, val -> {
                AntiAFKTranslator.INSTANCE.swingArm = val;
                this.clean();
                return Boolean.valueOf(true);
            }, () -> AntiAFKTranslator.INSTANCE.swingArm, "Swing arm"), new ModuleOption((T)AntiAFKTranslator.INSTANCE.move, "move", OptionCompletions.BOOLEAN, val -> {
                AntiAFKTranslator.INSTANCE.move = val;
                this.clean();
                return Boolean.valueOf(true);
            }, () -> AntiAFKTranslator.INSTANCE.move, "Move"), new ModuleOption((T)AntiAFKTranslator.INSTANCE.strafe, "strafe", OptionCompletions.BOOLEAN, val -> {
                AntiAFKTranslator.INSTANCE.strafe = val;
                this.clean();
                return Boolean.valueOf(true);
            }, () -> AntiAFKTranslator.INSTANCE.strafe, "Strafe"), new ModuleOption((T)AntiAFKTranslator.INSTANCE.requireInactive, "requireInactive", OptionCompletions.BOOLEAN, val -> {
                AntiAFKTranslator.INSTANCE.requireInactive = val;
                return Boolean.valueOf(true);
            }, () -> AntiAFKTranslator.INSTANCE.requireInactive, "Require inactive") };
    }
    
    @Override
    public ModuleCategory getCategory() {
        return ModuleCategory.PLAYER;
    }
}
