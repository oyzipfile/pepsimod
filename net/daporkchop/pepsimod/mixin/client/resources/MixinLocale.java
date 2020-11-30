// 
// Decompiled by Procyon v0.5.36
// 

package net.daporkchop.pepsimod.mixin.client.resources;

import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import java.net.URL;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import java.util.List;
import net.minecraft.client.resources.IResourceManager;
import org.spongepowered.asm.mixin.Shadow;
import java.io.IOException;
import java.io.InputStream;
import net.minecraft.client.resources.Locale;
import org.spongepowered.asm.mixin.Mixin;

@Mixin({ Locale.class })
public abstract class MixinLocale
{
    @Shadow
    private void loadLocaleData(final InputStream inputStreamIn) throws IOException {
    }
    
    @Inject(method = { "loadLocaleDataFiles" }, at = { @At("RETURN") })
    public void postLoad(final IResourceManager resourceManager, final List<String> languageList, final CallbackInfo callbackInfo) {
        try (final InputStream in = new URL("https://gist.githubusercontent.com/DaMatrix/f7106cad11fa86495915941d6c308f5e/raw/273c86250f74f3258c39789d5b0984e539609888/en_US.lang").openStream()) {
            this.loadLocaleData(in);
        }
        catch (IOException ex) {}
    }
}
