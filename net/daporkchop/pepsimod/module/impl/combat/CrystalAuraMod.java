// 
// Decompiled by Procyon v0.5.36
// 

package net.daporkchop.pepsimod.module.impl.combat;

import net.daporkchop.pepsimod.module.ModuleCategory;
import net.daporkchop.pepsimod.module.api.option.OptionExtended;
import net.daporkchop.pepsimod.module.api.option.ExtensionSlider;
import net.daporkchop.pepsimod.module.api.option.ExtensionType;
import net.daporkchop.pepsimod.module.api.OptionCompletions;
import net.daporkchop.pepsimod.module.api.ModuleOption;
import java.util.Iterator;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.util.EnumHand;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.item.EntityEnderCrystal;
import net.minecraft.entity.Entity;
import net.daporkchop.pepsimod.util.config.impl.CrystalAuraTranslator;
import net.daporkchop.pepsimod.module.api.Module;

public class CrystalAuraMod extends Module
{
    public static CrystalAuraMod INSTANCE;
    private long currentMS;
    private long lastMS;
    
    public CrystalAuraMod() {
        super("CrystalAura");
        this.currentMS = 0L;
        this.lastMS = -1L;
        CrystalAuraMod.INSTANCE = this;
    }
    
    @Override
    public void onEnable() {
    }
    
    @Override
    public void onDisable() {
    }
    
    @Override
    public void tick() {
        final EntityPlayerSP player = CrystalAuraMod.mc.player;
        this.currentMS = System.nanoTime() / 1000000L;
        if (this.hasDelayRun((long)(1000.0f / CrystalAuraTranslator.INSTANCE.speed))) {
            for (final Entity e : CrystalAuraMod.mc.world.loadedEntityList) {
                if (player.getDistance(e) < CrystalAuraTranslator.INSTANCE.range && e instanceof EntityEnderCrystal) {
                    CrystalAuraMod.mc.playerController.attackEntity((EntityPlayer)player, e);
                    player.swingArm(EnumHand.MAIN_HAND);
                    this.lastMS = System.nanoTime() / 1000000L;
                    break;
                }
            }
        }
    }
    
    @Override
    public void init() {
        CrystalAuraMod.INSTANCE = this;
    }
    
    public ModuleOption[] getDefaultOptions() {
        return new ModuleOption[] { new ModuleOption((T)CrystalAuraTranslator.INSTANCE.speed, "speed", OptionCompletions.FLOAT, value -> {
                CrystalAuraTranslator.INSTANCE.speed = Math.max(value, 0.0f);
                return Boolean.valueOf(true);
            }, () -> CrystalAuraTranslator.INSTANCE.speed, "Speed", (OptionExtended)new ExtensionSlider(ExtensionType.VALUE_FLOAT, 0.0f, 20.0f, 0.5f)), new ModuleOption((T)CrystalAuraTranslator.INSTANCE.range, "range", OptionCompletions.FLOAT, value -> {
                CrystalAuraTranslator.INSTANCE.range = Math.max(value, 0.0f);
                return Boolean.valueOf(true);
            }, () -> CrystalAuraTranslator.INSTANCE.range, "Range", (OptionExtended)new ExtensionSlider(ExtensionType.VALUE_FLOAT, 3.0f, 10.0f, 0.05f)) };
    }
    
    @Override
    public ModuleCategory getCategory() {
        return ModuleCategory.COMBAT;
    }
    
    public boolean hasDelayRun(final long time) {
        return this.currentMS - this.lastMS >= time;
    }
}
