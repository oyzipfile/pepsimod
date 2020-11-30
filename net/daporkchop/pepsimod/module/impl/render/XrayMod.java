// 
// Decompiled by Procyon v0.5.36
// 

package net.daporkchop.pepsimod.module.impl.render;

import net.daporkchop.pepsimod.module.ModuleCategory;
import net.daporkchop.pepsimod.optimization.BlockID;
import net.minecraft.util.ResourceLocation;
import net.daporkchop.pepsimod.command.api.Command;
import it.unimi.dsi.fastutil.ints.IntIterator;
import java.util.Iterator;
import net.daporkchop.pepsimod.util.config.impl.XrayTranslator;
import net.minecraft.block.Block;
import net.daporkchop.pepsimod.module.api.ModuleOption;
import net.daporkchop.pepsimod.module.api.Module;

public class XrayMod extends Module
{
    public static XrayMod INSTANCE;
    
    public XrayMod() {
        super("Xray");
        XrayMod.INSTANCE = this;
    }
    
    @Override
    public void onEnable() {
        try {
            XrayMod.mc.renderGlobal.loadRenderers();
        }
        catch (NullPointerException ex) {}
    }
    
    @Override
    public void onDisable() {
        try {
            XrayMod.mc.renderGlobal.loadRenderers();
        }
        catch (NullPointerException ex) {}
    }
    
    @Override
    public void tick() {
    }
    
    @Override
    public void init() {
        XrayMod.INSTANCE = this;
    }
    
    public ModuleOption[] getDefaultOptions() {
        return new ModuleOption[] { new ModuleOption((T)0, "add", new String[0], value -> true, () -> 0, "add", false), new ModuleOption((T)0, "remove", new String[0], value -> true, () -> 0, "remove", false) };
    }
    
    @Override
    public String getSuggestion(final String cmd, final String[] args) {
        if (args.length == 2 && args[1].equals("add")) {
            return cmd + " " + ((Block)Block.REGISTRY.getObjectById(7)).getRegistryName().toString();
        }
        if (args.length == 3 && args[1].equals("add")) {
            if (args[2].isEmpty()) {
                return cmd + ((Block)Block.REGISTRY.getObjectById(7)).getRegistryName().toString();
            }
            final String arg = args[2];
            for (final Block b : Block.REGISTRY) {
                final String s = b.getRegistryName().toString();
                if (s.startsWith(arg)) {
                    return args[0] + " " + args[1] + " " + s;
                }
            }
            return "";
        }
        else {
            if (args.length == 2 && args[1].equals("remove")) {
                return cmd + " " + ((Block)Block.REGISTRY.getObjectById(XrayTranslator.INSTANCE.target_blocks.iterator().nextInt())).getRegistryName();
            }
            if (args.length != 3 || !args[1].equals("remove")) {
                return super.getSuggestion(cmd, args);
            }
            if (args[2].isEmpty()) {
                return cmd + ((Block)Block.REGISTRY.getObjectById(XrayTranslator.INSTANCE.target_blocks.iterator().nextInt())).getRegistryName();
            }
            final String arg = args[2];
            for (final Integer i : XrayTranslator.INSTANCE.target_blocks) {
                final String s = ((Block)Block.REGISTRY.getObjectById((int)i)).getRegistryName().toString();
                if (s.startsWith(arg)) {
                    return args[0] + " " + args[1] + " " + s;
                }
            }
            return "";
        }
    }
    
    @Override
    public void execute(final String cmd, final String[] args) {
        if (args.length == 3 && !args[2].isEmpty() && cmd.startsWith(".xray add ")) {
            final String s = args[2].toLowerCase();
            try {
                final int id = Integer.parseInt(s);
                final Block block = (Block)Block.REGISTRY.getObjectById(id);
                if (block == null) {
                    Command.clientMessage("Not a valid block ID: §o" + args[2]);
                }
                else {
                    XrayTranslator.INSTANCE.target_blocks.add(id);
                    Command.clientMessage("Added §o" + block.getRegistryName().toString() + '§' + "r to the Xray list");
                    if (this.state.enabled) {
                        XrayMod.mc.renderGlobal.loadRenderers();
                    }
                }
            }
            catch (NumberFormatException e) {
                if (s.contains(":") && !s.endsWith(":") && !s.startsWith(":")) {
                    final String[] split = s.split(":");
                    final Block block2 = (Block)Block.REGISTRY.getObject((Object)new ResourceLocation(split[0], split[1]));
                    if (block2 == null) {
                        Command.clientMessage("Invalid id: §o" + s);
                    }
                    else {
                        XrayTranslator.INSTANCE.target_blocks.add(((BlockID)block2).getBlockId());
                        Command.clientMessage("Added §o" + block2.getRegistryName().toString() + '§' + "r to the Xray list");
                        if (this.state.enabled) {
                            XrayMod.mc.renderGlobal.loadRenderers();
                        }
                    }
                }
                else {
                    Command.clientMessage("Invalid id: §o" + s);
                }
            }
            return;
        }
        if (args.length == 3 && !args[2].isEmpty() && cmd.startsWith(".xray remove ")) {
            final String s = args[2].toLowerCase();
            try {
                final int id = Integer.parseInt(s);
                if (XrayTranslator.INSTANCE.target_blocks.contains(id)) {
                    XrayTranslator.INSTANCE.target_blocks.remove((Object)id);
                    Command.clientMessage("Removed §o" + id + '§' + "r from the Xray list");
                    if (this.state.enabled) {
                        XrayMod.mc.renderGlobal.loadRenderers();
                    }
                }
                else {
                    Command.clientMessage("Block ID §o" + args[2] + '§' + "r is not on the Xray list!");
                }
            }
            catch (NumberFormatException e) {
                if (s.contains(":") && !s.endsWith(":") && !s.startsWith(":")) {
                    final String[] split = s.split(":");
                    final Block block2 = (Block)Block.REGISTRY.getObject((Object)new ResourceLocation(split[0], split[1]));
                    if (block2 == null) {
                        Command.clientMessage("Invalid id: §o" + s);
                    }
                    else {
                        final int id2 = ((BlockID)block2).getBlockId();
                        if (XrayTranslator.INSTANCE.target_blocks.contains(id2)) {
                            XrayTranslator.INSTANCE.target_blocks.remove(id2);
                            Command.clientMessage("Removed §o" + s + '§' + "r from the Xray list");
                            if (this.state.enabled) {
                                XrayMod.mc.renderGlobal.loadRenderers();
                            }
                        }
                        else {
                            Command.clientMessage("Block ID §o" + s + '§' + "r is not on the Xray list!");
                        }
                    }
                }
                else {
                    Command.clientMessage("Invalid id: §o" + s);
                }
            }
            return;
        }
        super.execute(cmd, args);
    }
    
    @Override
    public ModuleCategory getCategory() {
        return ModuleCategory.RENDER;
    }
}
