// 
// Decompiled by Procyon v0.5.36
// 

package net.daporkchop.pepsimod.module.impl.misc;

import net.minecraft.item.ItemStack;
import net.minecraft.client.gui.FontRenderer;
import net.daporkchop.pepsimod.util.colors.ColorizedText;
import net.minecraft.entity.player.EntityPlayer;
import net.daporkchop.pepsimod.util.ReflectionStuff;
import net.daporkchop.pepsimod.misc.TickRate;
import java.awt.Color;
import net.minecraft.client.gui.GuiChat;
import net.daporkchop.pepsimod.util.colors.rainbow.RainbowText;
import net.daporkchop.pepsimod.Pepsimod;
import net.minecraft.client.gui.Gui;
import net.daporkchop.pepsimod.util.PepsiUtils;
import net.minecraft.client.gui.GuiIngame;
import net.daporkchop.pepsimod.module.ModuleCategory;
import net.daporkchop.pepsimod.module.api.option.OptionExtended;
import net.daporkchop.pepsimod.module.api.option.ExtensionSlider;
import net.daporkchop.pepsimod.module.api.option.ExtensionType;
import net.daporkchop.pepsimod.module.api.OptionCompletions;
import net.daporkchop.pepsimod.util.config.impl.HUDTranslator;
import net.daporkchop.pepsimod.module.api.ModuleOption;
import java.util.Iterator;
import net.daporkchop.pepsimod.util.config.impl.GeneralTranslator;
import net.daporkchop.pepsimod.module.ModuleManager;
import net.daporkchop.pepsimod.module.api.Module;

public class HUDMod extends Module
{
    public static HUDMod INSTANCE;
    public String serverBrand;
    
    public HUDMod(final boolean isEnabled, final int key) {
        super(isEnabled, "HUD", key, true);
        this.serverBrand = "";
        HUDMod.INSTANCE = this;
    }
    
    @Override
    public void onEnable() {
    }
    
    @Override
    public void onDisable() {
    }
    
    @Override
    public boolean shouldTick() {
        return true;
    }
    
    @Override
    public void tick() {
        for (final Module module : ModuleManager.ENABLED_MODULES) {
            module.updateName();
        }
        ModuleManager.sortModules(GeneralTranslator.INSTANCE.sortType);
    }
    
    @Override
    public void init() {
        HUDMod.INSTANCE = this;
    }
    
    public ModuleOption[] getDefaultOptions() {
        final Iterator<Module> iterator;
        Module module;
        return new ModuleOption[] { new ModuleOption((T)HUDTranslator.INSTANCE.drawLogo, "draw_logo", OptionCompletions.BOOLEAN, value -> {
                HUDTranslator.INSTANCE.drawLogo = value;
                return Boolean.valueOf(true);
            }, () -> HUDTranslator.INSTANCE.drawLogo, "Watermark"), new ModuleOption((T)HUDTranslator.INSTANCE.arrayList, "arraylist", OptionCompletions.BOOLEAN, value -> {
                HUDTranslator.INSTANCE.arrayList = value;
                return Boolean.valueOf(true);
            }, () -> HUDTranslator.INSTANCE.arrayList, "ArrayList"), new ModuleOption((T)HUDTranslator.INSTANCE.TPS, "tps", OptionCompletions.BOOLEAN, value -> {
                HUDTranslator.INSTANCE.TPS = value;
                return Boolean.valueOf(true);
            }, () -> HUDTranslator.INSTANCE.TPS, "TPS"), new ModuleOption((T)HUDTranslator.INSTANCE.coords, "coords", OptionCompletions.BOOLEAN, value -> {
                HUDTranslator.INSTANCE.coords = value;
                return Boolean.valueOf(true);
            }, () -> HUDTranslator.INSTANCE.coords, "Coords"), new ModuleOption((T)HUDTranslator.INSTANCE.netherCoords, "nether_coords", OptionCompletions.BOOLEAN, value -> {
                HUDTranslator.INSTANCE.netherCoords = value;
                return Boolean.valueOf(true);
            }, () -> HUDTranslator.INSTANCE.netherCoords, "NetherCoords"), new ModuleOption((T)HUDTranslator.INSTANCE.arrayListTop, "arraylist_top", OptionCompletions.BOOLEAN, value -> {
                HUDTranslator.INSTANCE.arrayListTop = value;
                return Boolean.valueOf(true);
            }, () -> HUDTranslator.INSTANCE.arrayListTop, "ArrayListOnTop"), new ModuleOption((T)HUDTranslator.INSTANCE.serverBrand, "server_brand", OptionCompletions.BOOLEAN, value -> {
                HUDTranslator.INSTANCE.serverBrand = value;
                return Boolean.valueOf(true);
            }, () -> HUDTranslator.INSTANCE.serverBrand, "ServerBrand"), new ModuleOption((T)HUDTranslator.INSTANCE.rainbow, "rainbow", OptionCompletions.BOOLEAN, value -> {
                HUDTranslator.INSTANCE.rainbow = value;
                ModuleManager.AVALIBLE_MODULES.iterator();
                while (iterator.hasNext()) {
                    module = iterator.next();
                    module.updateName();
                }
                return Boolean.valueOf(true);
            }, () -> HUDTranslator.INSTANCE.rainbow, "Rainbow"), new ModuleOption((T)HUDTranslator.INSTANCE.direction, "direction", OptionCompletions.BOOLEAN, value -> {
                HUDTranslator.INSTANCE.direction = value;
                return Boolean.valueOf(true);
            }, () -> HUDTranslator.INSTANCE.direction, "Direction"), new ModuleOption((T)HUDTranslator.INSTANCE.armor, "armor", OptionCompletions.BOOLEAN, value -> {
                HUDTranslator.INSTANCE.armor = value;
                return Boolean.valueOf(true);
            }, () -> HUDTranslator.INSTANCE.armor, "Armor"), new ModuleOption((T)HUDTranslator.INSTANCE.effects, "effects", OptionCompletions.BOOLEAN, value -> {
                HUDTranslator.INSTANCE.effects = value;
                return Boolean.valueOf(true);
            }, () -> HUDTranslator.INSTANCE.effects, "Effects"), new ModuleOption((T)HUDTranslator.INSTANCE.fps, "fps", OptionCompletions.BOOLEAN, value -> {
                HUDTranslator.INSTANCE.fps = value;
                return Boolean.valueOf(true);
            }, () -> HUDTranslator.INSTANCE.fps, "FPS"), new ModuleOption((T)HUDTranslator.INSTANCE.ping, "ping", OptionCompletions.BOOLEAN, value -> {
                HUDTranslator.INSTANCE.ping = value;
                return Boolean.valueOf(true);
            }, () -> HUDTranslator.INSTANCE.ping, "Ping"), new ModuleOption((T)HUDTranslator.INSTANCE.r, "r", new String[] { "0", "128", "255" }, value -> {
                HUDTranslator.INSTANCE.r = value;
                return Boolean.valueOf(true);
            }, () -> HUDTranslator.INSTANCE.r, "Red", (OptionExtended)new ExtensionSlider(ExtensionType.VALUE_INT, 0, 255, 1)), new ModuleOption((T)HUDTranslator.INSTANCE.g, "g", new String[] { "0", "128", "255" }, value -> {
                HUDTranslator.INSTANCE.g = value;
                return Boolean.valueOf(true);
            }, () -> HUDTranslator.INSTANCE.g, "Green", (OptionExtended)new ExtensionSlider(ExtensionType.VALUE_INT, 0, 255, 1)), new ModuleOption((T)HUDTranslator.INSTANCE.b, "b", new String[] { "0", "128", "255" }, value -> {
                HUDTranslator.INSTANCE.b = value;
                return Boolean.valueOf(true);
            }, () -> HUDTranslator.INSTANCE.b, "Blue", (OptionExtended)new ExtensionSlider(ExtensionType.VALUE_INT, 0, 255, 1)) };
    }
    
    @Override
    public ModuleCategory getCategory() {
        return ModuleCategory.MISC;
    }
    
    @Override
    public void registerKeybind(final String name, final int key) {
    }
    
    @Override
    public void onRenderGUI(final float partialTicks, final int width, final int height, final GuiIngame gui) {
        if (HUDTranslator.INSTANCE.drawLogo) {
            if (HUDTranslator.INSTANCE.rainbow) {
                PepsiUtils.PEPSI_NAME.drawAtPos((Gui)gui, 2, 2, 0);
            }
            else {
                HUDTranslator.INSTANCE.bindColor();
                HUDMod.mc.fontRenderer.drawString(Pepsimod.NAME_VERSION, 2.0f, 2.0f, HUDTranslator.INSTANCE.getColor(), true);
            }
        }
        if (HUDTranslator.INSTANCE.arrayList) {
            if (HUDTranslator.INSTANCE.arrayListTop) {
                int i = 0;
                int j = 0;
                while (i < ModuleManager.ENABLED_MODULES.size()) {
                    final Module module = ModuleManager.ENABLED_MODULES.get(i);
                    if (!module.state.hidden) {
                        if (HUDTranslator.INSTANCE.rainbow) {
                            if (module.text instanceof RainbowText) {
                                ((RainbowText)module.text).drawAtPos((Gui)gui, width - 2 - module.text.width(), 2 + j * 10, ++j * 10);
                            }
                            else {
                                module.text.drawAtPos((Gui)gui, width - 2 - module.text.width(), 2 + ++j * 10);
                            }
                        }
                        else {
                            HUDTranslator.INSTANCE.bindColor();
                            HUDMod.mc.fontRenderer.drawString(module.text.getRawText(), width - 2 - module.text.width(), 2 + j * 10, HUDTranslator.INSTANCE.getColor());
                            ++j;
                        }
                    }
                    ++i;
                }
            }
            else {
                int k = (HUDMod.mc.currentScreen instanceof GuiChat) ? 14 : 0;
                for (int l = 0; l < ModuleManager.ENABLED_MODULES.size(); ++l) {
                    final Module module = ModuleManager.ENABLED_MODULES.get(l);
                    if (!module.state.hidden) {
                        if (HUDTranslator.INSTANCE.rainbow) {
                            if (module.text instanceof RainbowText) {
                                final RainbowText rainbowText = (RainbowText)module.text;
                                final int x = width - 2 - module.text.width();
                                k += 10;
                                rainbowText.drawAtPos((Gui)gui, x, height - k, k / 10 * 8);
                            }
                            else {
                                final ColorizedText text3 = module.text;
                                final int n = width - 2 - module.text.width();
                                k += 10;
                                text3.drawAtPos((Gui)gui, n, height - k);
                            }
                        }
                        else {
                            HUDTranslator.INSTANCE.bindColor();
                            final FontRenderer fontRenderer = HUDMod.mc.fontRenderer;
                            final String rawText = module.text.getRawText();
                            final int n2 = width - 2 - module.text.width();
                            k += 10;
                            fontRenderer.drawString(rawText, n2, height - k, HUDTranslator.INSTANCE.getColor());
                        }
                    }
                }
            }
        }
        int i = 0;
        if (HUDTranslator.INSTANCE.arrayListTop) {
            i = ((HUDMod.mc.currentScreen instanceof GuiChat) ? 14 : 0);
            if (HUDTranslator.INSTANCE.serverBrand) {
                final String text = "§7Server brand: §r" + HUDMod.INSTANCE.serverBrand;
                final FontRenderer fontRenderer2 = HUDMod.mc.fontRenderer;
                final String s = text;
                final int n3 = width - (HUDMod.mc.fontRenderer.getStringWidth("Server brand: " + HUDMod.INSTANCE.serverBrand) + 2);
                final int n4 = height - 2;
                i += 10;
                gui.drawString(fontRenderer2, s, n3, n4 - i, Color.white.getRGB());
            }
            if (HUDTranslator.INSTANCE.ping) {
                try {
                    final int ping = HUDMod.mc.getConnection().getPlayerInfo(HUDMod.mc.getConnection().getGameProfile().getId()).getResponseTime();
                    final String text2 = "§7Ping: §r" + ping;
                    final FontRenderer fontRenderer3 = HUDMod.mc.fontRenderer;
                    final String s2 = text2;
                    final int n5 = width - (HUDMod.mc.fontRenderer.getStringWidth("Ping: " + ping) + 2);
                    final int n6 = height - 2;
                    i += 10;
                    gui.drawString(fontRenderer3, s2, n5, n6 - i, Color.white.getRGB());
                }
                catch (NullPointerException ex) {}
            }
            if (HUDTranslator.INSTANCE.TPS) {
                final String text = "§7TPS: §r" + TickRate.TPS;
                final FontRenderer fontRenderer4 = HUDMod.mc.fontRenderer;
                final String s3 = text;
                final int n7 = width - (HUDMod.mc.fontRenderer.getStringWidth("TPS: " + TickRate.TPS) + 2);
                final int n8 = height - 2;
                i += 10;
                gui.drawString(fontRenderer4, s3, n7, n8 - i, Color.white.getRGB());
            }
            if (HUDTranslator.INSTANCE.fps) {
                final String text = "§7FPS: §r" + ReflectionStuff.getDebugFps();
                final FontRenderer fontRenderer5 = HUDMod.mc.fontRenderer;
                final String s4 = text;
                final int n9 = width - (HUDMod.mc.fontRenderer.getStringWidth("FPS: " + ReflectionStuff.getDebugFps()) + 2);
                final int n10 = height - 2;
                i += 10;
                gui.drawString(fontRenderer5, s4, n9, n10 - i, Color.white.getRGB());
            }
        }
        else {
            if (HUDTranslator.INSTANCE.serverBrand) {
                final String text = "§7Server brand: §r" + HUDMod.INSTANCE.serverBrand;
                gui.drawString(HUDMod.mc.fontRenderer, text, width - (HUDMod.mc.fontRenderer.getStringWidth("Server brand: " + HUDMod.INSTANCE.serverBrand) + 2), 2 + i++ * 10, Color.white.getRGB());
            }
            if (HUDTranslator.INSTANCE.ping) {
                try {
                    final int ping = HUDMod.mc.getConnection().getPlayerInfo(HUDMod.mc.getConnection().getGameProfile().getId()).getResponseTime();
                    final String text2 = "§7Ping: §r" + ping;
                    gui.drawString(HUDMod.mc.fontRenderer, text2, width - (HUDMod.mc.fontRenderer.getStringWidth("Ping: " + ping) + 2), 2 + i++ * 10, Color.white.getRGB());
                }
                catch (NullPointerException ex2) {}
            }
            if (HUDTranslator.INSTANCE.TPS) {
                final String text = "§7TPS: §r" + TickRate.TPS;
                gui.drawString(HUDMod.mc.fontRenderer, text, width - (HUDMod.mc.fontRenderer.getStringWidth("TPS: " + TickRate.TPS) + 2), 2 + i++ * 10, Color.white.getRGB());
            }
            if (HUDTranslator.INSTANCE.fps) {
                final String text = "§7FPS: §r" + ReflectionStuff.getDebugFps();
                gui.drawString(HUDMod.mc.fontRenderer, text, width - (HUDMod.mc.fontRenderer.getStringWidth("FPS: " + ReflectionStuff.getDebugFps()) + 2), 2 + i++ * 10, Color.white.getRGB());
            }
        }
        i = ((HUDMod.mc.currentScreen instanceof GuiChat) ? 14 : 0);
        if (HUDTranslator.INSTANCE.coords) {
            String toRender = "§7XYZ§f: §7" + PepsiUtils.roundCoords(HUDMod.mc.player.posX) + "" + '§' + "f, " + '§' + "7" + PepsiUtils.roundCoords(HUDMod.mc.player.posY) + "" + '§' + "f, " + '§' + "7" + PepsiUtils.roundCoords(HUDMod.mc.player.posZ);
            if (HUDTranslator.INSTANCE.netherCoords && HUDMod.mc.player.dimension != 1) {
                toRender = toRender + " §f(§7" + PepsiUtils.roundCoords(PepsiUtils.getDimensionCoord(HUDMod.mc.player.posX)) + "" + '§' + "f, " + '§' + "7" + PepsiUtils.roundCoords(HUDMod.mc.player.posY) + "" + '§' + "f, " + '§' + "7" + PepsiUtils.roundCoords(PepsiUtils.getDimensionCoord(HUDMod.mc.player.posZ)) + "" + '§' + "f)";
            }
            final FontRenderer fontRenderer6 = HUDMod.mc.fontRenderer;
            final String s5 = toRender;
            final float n11 = 2.0f;
            i += 10;
            fontRenderer6.drawString(s5, n11, (float)(height - i), Color.white.getRGB(), true);
        }
        if (HUDTranslator.INSTANCE.direction) {
            final FontRenderer fontRenderer7 = HUDMod.mc.fontRenderer;
            final String string = "§7[§f" + PepsiUtils.getFacing() + '§' + "7]";
            final float n12 = 2.0f;
            i += 10;
            fontRenderer7.drawString(string, n12, (float)(height - i), Color.white.getRGB(), true);
        }
        if (HUDTranslator.INSTANCE.armor) {
            i = 0;
            int xPos = width / 2;
            xPos -= 90;
            for (int m = 0; m < 4; ++m) {
                final ItemStack stack = PepsiUtils.getWearingArmor(m);
                PepsiUtils.renderItem(xPos + 20 * i++, height - 70, partialTicks, (EntityPlayer)HUDMod.mc.player, stack);
            }
        }
    }
}
