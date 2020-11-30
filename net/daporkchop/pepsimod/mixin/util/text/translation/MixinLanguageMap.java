// 
// Decompiled by Procyon v0.5.36
// 

package net.daporkchop.pepsimod.mixin.util.text.translation;

import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;
import net.daporkchop.pepsimod.util.PepsiConstants;
import java.util.Map;
import net.minecraft.util.text.translation.LanguageMap;
import org.spongepowered.asm.mixin.Mixin;

@Mixin({ LanguageMap.class })
public abstract class MixinLanguageMap
{
    @Redirect(method = { "Lnet/minecraft/util/text/translation/LanguageMap;replaceWith(Ljava/util/Map;)V" }, at = @At(value = "INVOKE", target = "Ljava/util/Map;putAll(Ljava/util/Map;)V"))
    private static void postReplaceWith(final Map<String, String> languageList, final Map<String, String> newMap) {
        languageList.putAll(newMap);
        if (PepsiConstants.pepsimod != null) {
            languageList.putAll(PepsiConstants.pepsimod.data.localeKeys);
        }
    }
}
