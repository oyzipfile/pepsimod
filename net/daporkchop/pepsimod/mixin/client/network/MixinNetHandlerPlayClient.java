// 
// Decompiled by Procyon v0.5.36
// 

package net.daporkchop.pepsimod.mixin.client.network;

import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import java.util.Iterator;
import net.daporkchop.pepsimod.module.impl.misc.AnnouncerMod;
import net.daporkchop.pepsimod.util.PepsiConstants;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import net.minecraft.network.play.server.SPacketPlayerListItem;
import com.google.common.collect.Maps;
import org.spongepowered.asm.mixin.Shadow;
import net.minecraft.client.network.NetworkPlayerInfo;
import java.util.UUID;
import java.util.Map;
import net.minecraft.client.network.NetHandlerPlayClient;
import org.spongepowered.asm.mixin.Mixin;

@Mixin({ NetHandlerPlayClient.class })
public abstract class MixinNetHandlerPlayClient
{
    @Shadow
    private final Map<UUID, NetworkPlayerInfo> playerInfoMap;
    
    public MixinNetHandlerPlayClient() {
        this.playerInfoMap = (Map<UUID, NetworkPlayerInfo>)Maps.newHashMap();
    }
    
    @Inject(method = { "handlePlayerListItem" }, at = { @At("HEAD") })
    public void preHandlePlayerListItem(final SPacketPlayerListItem listItem, final CallbackInfo callbackInfo) {
        try {
            if (listItem.getEntries().size() == 1) {
                if (listItem.getAction() == SPacketPlayerListItem.Action.ADD_PLAYER) {
                    for (final SPacketPlayerListItem.AddPlayerData data : listItem.getEntries()) {
                        if (!data.getProfile().getId().equals(PepsiConstants.mc.player.getGameProfile().getId())) {
                            AnnouncerMod.INSTANCE.onPlayerJoin(data.getProfile().getName());
                        }
                    }
                }
                else if (listItem.getAction() == SPacketPlayerListItem.Action.REMOVE_PLAYER) {
                    for (final SPacketPlayerListItem.AddPlayerData data : listItem.getEntries()) {
                        if (!data.getProfile().getId().equals(PepsiConstants.mc.player.getGameProfile().getId())) {
                            AnnouncerMod.INSTANCE.onPlayerLeave(this.playerInfoMap.get(data.getProfile().getId()).getGameProfile().getName());
                        }
                    }
                }
            }
        }
        catch (NullPointerException ex) {}
    }
}
