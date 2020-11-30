// 
// Decompiled by Procyon v0.5.36
// 

package net.daporkchop.pepsimod.event;

import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.daporkchop.pepsimod.module.impl.misc.NotificationsMod;
import java.awt.TrayIcon;
import net.daporkchop.pepsimod.module.impl.misc.HUDMod;
import net.minecraftforge.fml.common.network.FMLNetworkEvent;
import net.daporkchop.pepsimod.util.PepsiConstants;

public class MiscEventHandler extends PepsiConstants
{
    public static MiscEventHandler INSTANCE;
    
    @SubscribeEvent
    public void onDisconnect(final FMLNetworkEvent.ClientDisconnectionFromServerEvent event) {
        HUDMod.INSTANCE.serverBrand = "";
        System.out.println("[PEPSIMOD] Saving config...");
        MiscEventHandler.pepsimod.saveConfig();
        System.out.println("[PEPSIMOD] Saved.");
        NotificationsMod.sendNotification("Disconnected from server", TrayIcon.MessageType.WARNING);
        NotificationsMod.INSTANCE.inQueue = false;
    }
}
