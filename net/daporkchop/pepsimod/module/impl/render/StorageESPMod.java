// 
// Decompiled by Procyon v0.5.36
// 

package net.daporkchop.pepsimod.module.impl.render;

import net.daporkchop.pepsimod.module.ModuleCategory;
import java.util.function.Consumer;
import net.daporkchop.pepsimod.util.config.impl.TracersTranslator;
import net.daporkchop.pepsimod.util.render.WorldRenderer;
import net.daporkchop.pepsimod.module.api.OptionCompletions;
import net.daporkchop.pepsimod.module.api.ModuleOption;
import java.util.Iterator;
import net.minecraft.tileentity.TileEntityHopper;
import net.minecraft.tileentity.TileEntityFurnace;
import net.minecraft.tileentity.TileEntityEnderChest;
import net.minecraft.block.BlockChest;
import net.daporkchop.pepsimod.util.ReflectionStuff;
import net.daporkchop.pepsimod.util.PepsiUtils;
import net.minecraft.tileentity.TileEntityChest;
import net.daporkchop.pepsimod.util.config.impl.ESPTranslator;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.IBlockAccess;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.util.math.AxisAlignedBB;
import java.util.ArrayList;
import net.daporkchop.pepsimod.util.RenderColor;
import net.daporkchop.pepsimod.module.api.Module;

public class StorageESPMod extends Module
{
    public static final RenderColor chestColor;
    public static final RenderColor trappedColor;
    public static final RenderColor enderColor;
    public static final RenderColor hopperColor;
    public static final RenderColor furnaceColor;
    public static StorageESPMod INSTANCE;
    public final ArrayList<AxisAlignedBB> basic;
    public final ArrayList<AxisAlignedBB> trapped;
    public final ArrayList<AxisAlignedBB> ender;
    public final ArrayList<AxisAlignedBB> hopper;
    public final ArrayList<AxisAlignedBB> furnace;
    
    public static AxisAlignedBB getBoundingBox(final World world, final BlockPos pos) {
        return world.getBlockState(pos).getBoundingBox((IBlockAccess)world, pos);
    }
    
    public StorageESPMod() {
        super("StorageESP");
        this.basic = new ArrayList<AxisAlignedBB>();
        this.trapped = new ArrayList<AxisAlignedBB>();
        this.ender = new ArrayList<AxisAlignedBB>();
        this.hopper = new ArrayList<AxisAlignedBB>();
        this.furnace = new ArrayList<AxisAlignedBB>();
        StorageESPMod.INSTANCE = this;
    }
    
    @Override
    public void onEnable() {
    }
    
    @Override
    public void onDisable() {
    }
    
    @Override
    public void tick() {
        this.basic.clear();
        this.trapped.clear();
        this.ender.clear();
        this.hopper.clear();
        this.furnace.clear();
        for (final TileEntity te : StorageESPMod.mc.world.loadedTileEntityList) {
            if ((ESPTranslator.INSTANCE.basic || ESPTranslator.INSTANCE.trapped) && te instanceof TileEntityChest) {
                final TileEntityChest chestTe = (TileEntityChest)te;
                if (chestTe.adjacentChestXPos != null) {
                    continue;
                }
                if (chestTe.adjacentChestZPos != null) {
                    continue;
                }
                final AxisAlignedBB bb = PepsiUtils.offsetBB(PepsiUtils.cloneBB(getBoundingBox((World)StorageESPMod.mc.world, te.getPos())), te.getPos());
                if (chestTe.adjacentChestXNeg != null) {
                    ReflectionStuff.setMinX(bb, bb.minX - 1.0);
                }
                else if (chestTe.adjacentChestZNeg != null) {
                    ReflectionStuff.setMinZ(bb, bb.minZ - 1.0);
                }
                if (chestTe.getChestType() == BlockChest.Type.TRAP) {
                    if (!ESPTranslator.INSTANCE.trapped) {
                        continue;
                    }
                    this.trapped.add(bb);
                }
                else {
                    if (!ESPTranslator.INSTANCE.basic) {
                        continue;
                    }
                    this.basic.add(bb);
                }
            }
            else if (ESPTranslator.INSTANCE.ender && te instanceof TileEntityEnderChest) {
                this.ender.add(PepsiUtils.offsetBB(PepsiUtils.cloneBB(getBoundingBox((World)StorageESPMod.mc.world, te.getPos())), te.getPos()));
            }
            else if (ESPTranslator.INSTANCE.furnace && te instanceof TileEntityFurnace) {
                this.furnace.add(PepsiUtils.offsetBB(PepsiUtils.cloneBB(getBoundingBox((World)StorageESPMod.mc.world, te.getPos())), te.getPos()));
            }
            else {
                if (!ESPTranslator.INSTANCE.hopper || !(te instanceof TileEntityHopper)) {
                    continue;
                }
                this.hopper.add(PepsiUtils.offsetBB(PepsiUtils.cloneBB(getBoundingBox((World)StorageESPMod.mc.world, te.getPos())), te.getPos()));
            }
        }
    }
    
    @Override
    public void init() {
        StorageESPMod.INSTANCE = this;
    }
    
    public ModuleOption[] getDefaultOptions() {
        return new ModuleOption[] { new ModuleOption((T)ESPTranslator.INSTANCE.basic, "normal", OptionCompletions.BOOLEAN, value -> {
                ESPTranslator.INSTANCE.basic = value;
                return Boolean.valueOf(true);
            }, () -> ESPTranslator.INSTANCE.basic, "Normal"), new ModuleOption((T)ESPTranslator.INSTANCE.trapped, "trapped", OptionCompletions.BOOLEAN, value -> {
                ESPTranslator.INSTANCE.trapped = value;
                return Boolean.valueOf(true);
            }, () -> ESPTranslator.INSTANCE.trapped, "Trapped"), new ModuleOption((T)ESPTranslator.INSTANCE.ender, "ender", OptionCompletions.BOOLEAN, value -> {
                ESPTranslator.INSTANCE.ender = value;
                return Boolean.valueOf(true);
            }, () -> ESPTranslator.INSTANCE.ender, "Ender"), new ModuleOption((T)ESPTranslator.INSTANCE.hopper, "hopper", OptionCompletions.BOOLEAN, value -> {
                ESPTranslator.INSTANCE.hopper = value;
                return Boolean.valueOf(true);
            }, () -> ESPTranslator.INSTANCE.hopper, "Hopper"), new ModuleOption((T)ESPTranslator.INSTANCE.furnace, "furnace", OptionCompletions.BOOLEAN, value -> {
                ESPTranslator.INSTANCE.furnace = value;
                return Boolean.valueOf(true);
            }, () -> ESPTranslator.INSTANCE.furnace, "Furnace") };
    }
    
    @Override
    public void renderWorld(final WorldRenderer renderer) {
        renderer.width(TracersTranslator.INSTANCE.width);
        if (ESPTranslator.INSTANCE.basic) {
            renderer.color(StorageESPMod.chestColor);
            this.basic.forEach(renderer::outline);
        }
        if (ESPTranslator.INSTANCE.trapped) {
            renderer.color(StorageESPMod.trappedColor);
            this.trapped.forEach(renderer::outline);
        }
        if (ESPTranslator.INSTANCE.ender) {
            renderer.color(StorageESPMod.enderColor);
            this.ender.forEach(renderer::outline);
        }
        if (ESPTranslator.INSTANCE.hopper) {
            renderer.color(StorageESPMod.hopperColor);
            this.hopper.forEach(renderer::outline);
        }
        if (ESPTranslator.INSTANCE.furnace) {
            renderer.color(StorageESPMod.furnaceColor);
            this.furnace.forEach(renderer::outline);
        }
    }
    
    @Override
    public ModuleCategory getCategory() {
        return ModuleCategory.RENDER;
    }
    
    static {
        chestColor = new RenderColor(196, 139, 53, 128);
        trappedColor = new RenderColor(81, 57, 22, 128);
        enderColor = new RenderColor(25, 35, 40, 128);
        hopperColor = new RenderColor(45, 45, 45, 128);
        furnaceColor = new RenderColor(151, 151, 151, 128);
    }
}
