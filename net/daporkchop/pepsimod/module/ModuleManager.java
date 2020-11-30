// 
// Decompiled by Procyon v0.5.36
// 

package net.daporkchop.pepsimod.module;

import net.minecraft.network.Packet;
import java.util.concurrent.ThreadLocalRandom;
import net.daporkchop.pepsimod.util.config.impl.GeneralTranslator;
import net.daporkchop.pepsimod.module.api.ModuleSortType;
import java.util.Iterator;
import net.daporkchop.pepsimod.module.api.Module;
import java.util.ArrayList;

public class ModuleManager
{
    public static ArrayList<Module> AVALIBLE_MODULES;
    public static ArrayList<Module> ENABLED_MODULES;
    
    public static Module registerModule(final Module toRegister) {
        if (toRegister.shouldRegister()) {
            ModuleManager.AVALIBLE_MODULES.add(toRegister);
            if (toRegister.state.enabled) {
                enableModule(toRegister);
            }
            else {
                disableModule(toRegister);
            }
        }
        return toRegister;
    }
    
    public static void registerModules(final Module... toRegister) {
        for (final Module module : toRegister) {
            registerModule(module);
        }
    }
    
    public static void unRegister(final Module module) {
        if (ModuleManager.AVALIBLE_MODULES.contains(module)) {
            ModuleManager.AVALIBLE_MODULES.remove(module);
            ModuleManager.ENABLED_MODULES.remove(module);
        }
    }
    
    public static Module enableModule(final Module toEnable) {
        if (!ModuleManager.ENABLED_MODULES.contains(toEnable)) {
            if (!ModuleManager.AVALIBLE_MODULES.contains(toEnable)) {
                throw new IllegalStateException("Attempted to enable an unregistered Module!");
            }
            ModuleManager.ENABLED_MODULES.add(toEnable);
            toEnable.setEnabled(true);
        }
        return toEnable;
    }
    
    public static Module disableModule(final Module toDisable) {
        if (toDisable.state.enabled && ModuleManager.ENABLED_MODULES.contains(toDisable)) {
            if (!ModuleManager.AVALIBLE_MODULES.contains(toDisable)) {
                throw new IllegalStateException("Attempted to disable an unregistered Module!");
            }
            ModuleManager.ENABLED_MODULES.remove(toDisable);
            toDisable.setEnabled(false);
        }
        return toDisable;
    }
    
    public static Module toggleModule(final Module toToggle) {
        if (toToggle.state.enabled) {
            disableModule(toToggle);
        }
        else {
            enableModule(toToggle);
        }
        return toToggle;
    }
    
    public static Module getModuleByName(final String name) {
        for (final Module module : ModuleManager.AVALIBLE_MODULES) {
            if (module.name.equals(name)) {
                return module;
            }
        }
        return null;
    }
    
    public static void sortModules(final ModuleSortType type) {
        GeneralTranslator.INSTANCE.sortType = type;
        switch (type) {
            case ALPHABETICAL: {
                final ArrayList<Module> tempArrayList = (ArrayList<Module>)ModuleManager.ENABLED_MODULES.clone();
                final ArrayList<Module> newArrayList = new ArrayList<Module>();
            Label_0067:
                for (final Module module : tempArrayList) {
                    for (int i = 0; i < newArrayList.size(); ++i) {
                        if (module.name.compareTo(newArrayList.get(i).name) < 0) {
                            newArrayList.add(i, module);
                            continue Label_0067;
                        }
                    }
                    newArrayList.add(module);
                }
                ModuleManager.ENABLED_MODULES = newArrayList;
            }
            case SIZE: {
                final ArrayList<Module> tempArrayList2 = (ArrayList<Module>)ModuleManager.ENABLED_MODULES.clone();
                final ArrayList<Module> newArrayList2 = new ArrayList<Module>();
            Label_0184:
                for (final Module module2 : tempArrayList2) {
                    if (module2.text == null) {
                        return;
                    }
                    for (int j = 0; j < newArrayList2.size(); ++j) {
                        final Module existingModule = newArrayList2.get(j);
                        if (module2.text.width() > existingModule.text.width()) {
                            newArrayList2.add(j, module2);
                            continue Label_0184;
                        }
                        if (module2.text.width() == existingModule.text.width() && module2.name.compareTo(existingModule.name) < 0) {
                            newArrayList2.add(j, module2);
                            continue Label_0184;
                        }
                    }
                    newArrayList2.add(module2);
                }
                ModuleManager.ENABLED_MODULES = newArrayList2;
                break;
            }
            case RANDOM: {
                final ArrayList<Module> tempArrayList3 = (ArrayList<Module>)ModuleManager.ENABLED_MODULES.clone();
                final ArrayList<Module> newArrayList3 = new ArrayList<Module>();
                for (final Module module3 : tempArrayList3) {
                    newArrayList3.add(ThreadLocalRandom.current().nextInt(newArrayList3.size()), module3);
                }
                ModuleManager.ENABLED_MODULES = newArrayList3;
                break;
            }
        }
    }
    
    public static boolean preRecievePacket(final Packet<?> packetIn) {
        boolean cancel = false;
        for (final Module module : ModuleManager.ENABLED_MODULES) {
            if (module.preRecievePacket(packetIn)) {
                cancel = true;
            }
        }
        return cancel;
    }
    
    public static void postRecievePacket(final Packet<?> packetIn) {
        for (final Module module : ModuleManager.ENABLED_MODULES) {
            module.postRecievePacket(packetIn);
        }
    }
    
    public static boolean preSendPacket(final Packet<?> packetIn) {
        boolean cancel = false;
        for (final Module module : ModuleManager.ENABLED_MODULES) {
            if (module.preSendPacket(packetIn)) {
                cancel = true;
            }
        }
        return cancel;
    }
    
    public static void postSendPacket(final Packet<?> packetIn) {
        for (final Module module : ModuleManager.ENABLED_MODULES) {
            module.postSendPacket(packetIn);
        }
    }
    
    static {
        ModuleManager.AVALIBLE_MODULES = new ArrayList<Module>();
        ModuleManager.ENABLED_MODULES = new ArrayList<Module>();
    }
}
