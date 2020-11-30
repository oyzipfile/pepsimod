// 
// Decompiled by Procyon v0.5.36
// 

package net.daporkchop.pepsimod;

import java.util.Map;
import javax.annotation.Nullable;
import org.spongepowered.asm.mixin.MixinEnvironment;
import org.spongepowered.asm.mixin.Mixins;
import org.spongepowered.asm.launch.MixinBootstrap;
import net.minecraftforge.fml.common.FMLLog;
import net.minecraftforge.fml.relauncher.IFMLLoadingPlugin;

public class PepsimodMixinLoader implements IFMLLoadingPlugin
{
    public static boolean isObfuscatedEnvironment;
    
    public PepsimodMixinLoader() {
        FMLLog.log.info("\n\n\nPepsimod Mixin init\n\n");
        MixinBootstrap.init();
        Mixins.addConfiguration("mixins.pepsimod.json");
        MixinEnvironment.getDefaultEnvironment().setObfuscationContext("searge");
        FMLLog.log.info(MixinEnvironment.getDefaultEnvironment().getObfuscationContext());
    }
    
    public String[] getASMTransformerClass() {
        return new String[0];
    }
    
    public String getModContainerClass() {
        return null;
    }
    
    @Nullable
    public String getSetupClass() {
        return null;
    }
    
    public void injectData(final Map<String, Object> data) {
        PepsimodMixinLoader.isObfuscatedEnvironment = data.get("runtimeDeobfuscationEnabled");
    }
    
    public String getAccessTransformerClass() {
        return null;
    }
    
    static {
        PepsimodMixinLoader.isObfuscatedEnvironment = false;
    }
}
