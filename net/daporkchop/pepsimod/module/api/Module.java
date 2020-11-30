// 
// Decompiled by Procyon v0.5.36
// 

package net.daporkchop.pepsimod.module.api;

import net.daporkchop.pepsimod.util.event.MoveEvent;
import net.daporkchop.pepsimod.module.ModuleCategory;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.daporkchop.pepsimod.util.render.WorldRenderer;
import net.minecraft.client.gui.GuiIngame;
import net.daporkchop.pepsimod.module.ModuleManager;
import net.daporkchop.pepsimod.util.config.impl.HUDTranslator;
import net.minecraft.network.Packet;
import java.util.ArrayList;
import net.daporkchop.pepsimod.command.CommandRegistry;
import net.daporkchop.pepsimod.util.colors.rainbow.RainbowText;
import net.daporkchop.pepsimod.util.config.impl.GeneralTranslator;
import net.daporkchop.pepsimod.util.colors.ColorizedText;
import net.minecraft.client.settings.KeyBinding;
import net.daporkchop.pepsimod.util.misc.ITickListener;
import net.daporkchop.pepsimod.command.api.Command;

public abstract class Module extends Command implements ITickListener
{
    public KeyBinding keybind;
    public ColorizedText text;
    public ModuleOption[] options;
    public String nameFull;
    public String[] completionOptions;
    public GeneralTranslator.ModuleState state;
    
    public static boolean shouldBeEnabled(final boolean in, final ModuleLaunchState state) {
        return state == ModuleLaunchState.ENABLED || (state != ModuleLaunchState.DISABLED && in);
    }
    
    public Module(final String name) {
        this(false, name, -1, false);
    }
    
    public Module(final boolean def, final String name, final int keybind, final boolean hide) {
        super(name.toLowerCase());
        this.nameFull = name;
        this.options = this.getDefaultOptions();
        this.registerKeybind(name, keybind);
        this.state = GeneralTranslator.INSTANCE.getState(name, new GeneralTranslator.ModuleState(def, hide));
        if (this.state == null) {
            GeneralTranslator.INSTANCE.states.put(name, this.state = new GeneralTranslator.ModuleState(def, hide));
        }
    }
    
    public boolean toggle() {
        this.state.enabled = !this.state.enabled;
        if (this.state.enabled) {
            this.onEnable();
        }
        else {
            this.onDisable();
        }
        return this.state.enabled;
    }
    
    public boolean setEnabled(final boolean enabled) {
        this.state.enabled = enabled;
        if (this.state.enabled) {
            this.onEnable();
        }
        else {
            this.onDisable();
        }
        return this.state.enabled;
    }
    
    public final void doInit() {
        this.init();
        if (this.hasModeInName()) {
            this.updateName();
        }
        else {
            this.text = new RainbowText(this.nameFull);
        }
        CommandRegistry.registerCommand(this);
        final ArrayList<String> temp = new ArrayList<String>();
        for (final ModuleOption option : this.options) {
            temp.add(option.getName());
        }
        this.completionOptions = temp.toArray(new String[temp.size()]);
    }
    
    public ModuleOption getOptionByName(final String name) {
        for (final ModuleOption moduleOption : this.options) {
            if (moduleOption.getName().equals(name)) {
                return moduleOption;
            }
        }
        return null;
    }
    
    public boolean shouldTick() {
        return this.state.enabled;
    }
    
    public abstract void onEnable();
    
    public abstract void onDisable();
    
    public abstract void init();
    
    protected abstract ModuleOption[] getDefaultOptions();
    
    public boolean preRecievePacket(final Packet<?> packetIn) {
        return false;
    }
    
    public void postRecievePacket(final Packet<?> packetIn) {
    }
    
    public boolean preSendPacket(final Packet<?> packetIn) {
        return false;
    }
    
    public void postSendPacket(final Packet<?> packetIn) {
    }
    
    public boolean hasModeInName() {
        return false;
    }
    
    public String getModeForName() {
        return "";
    }
    
    public void updateName() {
        if (Module.pepsimod.isInitialized && this.hasModeInName()) {
            if (HUDTranslator.INSTANCE.rainbow) {
                this.text = new RainbowText(this.nameFull + '§' + "customa8a8a8 [" + this.getModeForName() + "]");
            }
            else {
                this.text = new RainbowText(this.nameFull + '§' + "7 [" + this.getModeForName() + "]");
            }
        }
    }
    
    @Override
    public String getSuggestion(final String cmd, final String[] args) {
        switch (args.length) {
            case 1: {
                return "." + this.name + " " + ((this.completionOptions.length == 0) ? "" : this.completionOptions[0]);
            }
            case 2: {
                if (args[1].isEmpty()) {
                    return "." + this.name + " " + ((this.completionOptions.length == 0) ? "" : this.completionOptions[0]);
                }
                final String[] completionOptions = this.completionOptions;
                final int length = completionOptions.length;
                int i = 0;
                while (i < length) {
                    final String mode = completionOptions[i];
                    if (mode.equals(args[1])) {
                        final ModuleOption option = this.getOptionByName(args[1]);
                        if (option == null) {
                            return "";
                        }
                        return args[0] + " " + args[1] + " " + option.getDefaultValue();
                    }
                    else {
                        if (mode.startsWith(args[1])) {
                            return "." + this.name + " " + mode;
                        }
                        ++i;
                    }
                }
                return "";
            }
            case 3: {
                if (args[2].isEmpty()) {
                    final ModuleOption option2 = this.getOptionByName(args[1].trim());
                    if (option2 == null) {
                        return "";
                    }
                    return args[0] + " " + args[1] + " " + option2.getDefaultValue();
                }
                else {
                    final ModuleOption option2 = this.getOptionByName(args[1]);
                    if (option2 == null) {
                        return "";
                    }
                    if (option2.getDefaultValue().toString().startsWith(args[2])) {
                        return args[0] + " " + args[1] + " " + option2.getDefaultValue();
                    }
                    for (final String s : option2.defaultCompletions()) {
                        if (s.startsWith(args[2])) {
                            return args[0] + " " + args[1] + " " + s;
                        }
                    }
                    return "";
                }
                break;
            }
            default: {
                return "." + this.name;
            }
        }
    }
    
    @Override
    public void execute(final String cmd, final String[] args) {
        switch (args.length) {
            case 1: {
                String commands = "";
                for (int i = 0; i < this.completionOptions.length; ++i) {
                    commands = commands + "§o" + this.completionOptions[i] + '§' + "r" + ((i + 1 == this.completionOptions.length) ? "" : ", ");
                }
                Command.clientMessage(commands);
                break;
            }
            case 2: {
                if (args[1].isEmpty()) {
                    String cmds = "";
                    for (int j = 0; j < this.completionOptions.length; ++j) {
                        cmds = cmds + "§o" + this.completionOptions[j] + '§' + "r" + ((j + 1 == this.completionOptions.length) ? "" : ", ");
                    }
                    Command.clientMessage(cmds);
                    break;
                }
                final ModuleOption option = this.getOptionByName(args[1]);
                if (option == null) {
                    Command.clientMessage("Unknown option: §o" + args[1]);
                    break;
                }
                Command.clientMessage(args[1] + ": " + option.getValue());
                break;
            }
            case 3: {
                if (args[2].isEmpty()) {
                    final ModuleOption opt = this.getOptionByName(args[1]);
                    if (opt == null) {
                        Command.clientMessage("Unknown option: §o" + args[1]);
                        break;
                    }
                    Command.clientMessage(args[1] + ": " + opt.getValue());
                    break;
                }
                else {
                    final ModuleOption opt = this.getOptionByName(args[1]);
                    if (opt == null) {
                        Command.clientMessage("Unknown option: §o" + args[1]);
                        break;
                    }
                    try {
                        final String canonicalName = opt.getValue().getClass().getCanonicalName();
                        switch (canonicalName) {
                            case "java.lang.String": {
                                opt.setValue(args[2]);
                                break;
                            }
                            case "java.lang.Boolean": {
                                opt.setValue(Boolean.parseBoolean(args[2]));
                                break;
                            }
                            case "java.lang.Byte": {
                                opt.setValue(Byte.parseByte(args[2]));
                                break;
                            }
                            case "java.lang.Double": {
                                opt.setValue(Double.parseDouble(args[2]));
                                break;
                            }
                            case "java.lang.Float": {
                                opt.setValue(Float.parseFloat(args[2]));
                                break;
                            }
                            case "java.lang.Integer": {
                                opt.setValue(Integer.parseInt(args[2]));
                                break;
                            }
                            case "java.lang.Short": {
                                opt.setValue(Short.parseShort(args[2]));
                                break;
                            }
                            default: {
                                Command.clientMessage("Unknown value type: §o" + opt.getValue().getClass().getCanonicalName() + '§' + "r. Please report to devs!");
                                return;
                            }
                        }
                        final String s = args[1];
                        switch (s) {
                            case "hidden": {
                                this.state.hidden = opt.getValue();
                                break;
                            }
                            case "enabled": {
                                if (opt.getValue()) {
                                    ModuleManager.enableModule(this);
                                    break;
                                }
                                ModuleManager.disableModule(this);
                                break;
                            }
                        }
                        Command.clientMessage("Set §o" + args[1] + '§' + "r to " + '§' + "o" + opt.getValue());
                    }
                    catch (NumberFormatException e) {
                        Command.clientMessage("Invalid number: §o" + args[2]);
                    }
                    break;
                }
                break;
            }
        }
    }
    
    public void onRender(final float partialTicks) {
    }
    
    public void onRenderGUI(final float partialTicks, final int width, final int height, final GuiIngame gui) {
    }
    
    public void renderOverlay(final WorldRenderer renderer) {
    }
    
    public void renderWorld(final WorldRenderer renderer) {
    }
    
    @Deprecated
    public ModuleLaunchState getLaunchState() {
        return ModuleLaunchState.AUTO;
    }
    
    public void registerKeybind(final String name, final int key) {
        ClientRegistry.registerKeyBinding(this.keybind = new KeyBinding(name, (key == -1) ? 0 : key, "key.categories.pepsimod"));
    }
    
    public abstract ModuleCategory getCategory();
    
    public void onPlayerMove(final MoveEvent e) {
    }
    
    public boolean shouldRegister() {
        return true;
    }
}
