// 
// Decompiled by Procyon v0.5.36
// 

package net.daporkchop.pepsimod.mixin.client.entity;

import net.daporkchop.pepsimod.util.render.Texture;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import net.daporkchop.pepsimod.misc.data.Group;
import net.daporkchop.pepsimod.util.PepsiConstants;
import net.minecraft.util.ResourceLocation;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.Shadow;
import net.minecraft.client.network.NetworkPlayerInfo;
import com.mojang.authlib.GameProfile;
import net.minecraft.world.World;
import net.minecraft.client.entity.AbstractClientPlayer;
import org.spongepowered.asm.mixin.Mixin;
import net.minecraft.entity.player.EntityPlayer;

@Mixin({ AbstractClientPlayer.class })
public abstract class MixinAbstractClientPlayer extends EntityPlayer
{
    public MixinAbstractClientPlayer() {
        super((World)null, (GameProfile)null);
    }
    
    @Shadow
    protected abstract NetworkPlayerInfo getPlayerInfo();
    
    @Inject(method = { "getLocationCape" }, at = { @At("HEAD") }, cancellable = true)
    public void preGetLocationCape(final CallbackInfoReturnable<ResourceLocation> callbackInfo) {
        if (this.getPlayerInfo() != null) {
            final Group group = PepsiConstants.pepsimod.data.getGroup(this.getPlayerInfo());
            if (group != null) {
                group.doWithCapeIfPresent(tex -> callbackInfo.setReturnValue(tex.texture));
            }
        }
    }
}
