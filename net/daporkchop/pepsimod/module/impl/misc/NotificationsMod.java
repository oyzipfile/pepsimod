// 
// Decompiled by Procyon v0.5.36
// 

package net.daporkchop.pepsimod.module.impl.misc;

import net.minecraft.network.play.server.SPacketSpawnPlayer;
import net.minecraft.network.play.server.SPacketChat;
import net.minecraft.network.Packet;
import net.daporkchop.pepsimod.module.ModuleCategory;
import net.daporkchop.pepsimod.module.api.OptionCompletions;
import net.daporkchop.pepsimod.module.api.ModuleOption;
import java.awt.image.BufferedImage;
import java.awt.AWTException;
import java.io.IOException;
import java.awt.Image;
import java.io.InputStream;
import javax.imageio.ImageIO;
import java.io.ByteArrayInputStream;
import java.util.Base64;
import net.daporkchop.pepsimod.util.config.impl.NotificationsTranslator;
import net.daporkchop.pepsimod.module.ModuleManager;
import org.lwjgl.opengl.Display;
import java.awt.PopupMenu;
import java.awt.SystemTray;
import java.awt.TrayIcon;
import net.daporkchop.pepsimod.module.api.Module;

public class NotificationsMod extends Module
{
    public static NotificationsMod INSTANCE;
    public TrayIcon trayIcon;
    public SystemTray tray;
    public PopupMenu menu;
    public boolean inQueue;
    
    public static void sendNotification(final String message, final TrayIcon.MessageType type) {
        if (!Display.isActive() && ModuleManager.ENABLED_MODULES.contains(NotificationsMod.INSTANCE)) {
            NotificationsMod.INSTANCE.trayIcon.displayMessage("pepsimod", message, type);
        }
    }
    
    public NotificationsMod() {
        super("Notifications");
        this.inQueue = false;
        NotificationsMod.INSTANCE = this;
    }
    
    @Override
    public void onEnable() {
    }
    
    @Override
    public void onDisable() {
    }
    
    @Override
    public void tick() {
        if (NotificationsTranslator.INSTANCE.death && NotificationsMod.mc.player.getHealth() <= 0.0f) {
            sendNotification("You died!", TrayIcon.MessageType.WARNING);
        }
    }
    
    @Override
    public void init() {
        try {
            NotificationsMod.INSTANCE = this;
            this.tray = SystemTray.getSystemTray();
            final BufferedImage read = ImageIO.read(new ByteArrayInputStream(Base64.getDecoder().decode("iVBORw0KGgoAAAANSUhEUgAAABAAAAAQCAMAAAAoLQ9TAAAABGdBTUEAALGPC/xhBQAAACBjSFJNAAB6JgAAgIQAAPoAAACA6AAAdTAAAOpgAAA6mAAAF3CculE8AAACDVBMVEUAAAD////++fneAAD1j5z0+fz0+Pru9PgAWJH//////Pz82+D5uMH3pK/3oa34r7n7ztT+8vT////////84uX2nKjwWW3vSF/0g5L70Nb////////819zzd4f3q7X////////96Orze4v////8/f7////3p7LY5e7k7vP96ezyaXujwte60eH6zNLuQ1uTt9D5u8N9qcf5u8SAq8j6ztTvRVyYu9L96+7ybX8rc6PA1eP////4rLZwocHx9vn/7e7WuMZGha/H2uf////Z6fFuocFLiLG60eH////////l7vObvtRRjLM9f6t/q8jO3+r////////9/v7g6/K70uGjwtegwNawy93T4uz2+fsAWJHtNE3sJ0LsJkHsLkjsKkTrGjfrHDjrGjbsJD/rIj3rHTnrGzftOlL71drtL0nrGzjzfY3/+vv7/f7uPVX709j////9/v7sJUD2nqr//f76/P1yosLtNU7rHzvze4v+8vTf6vE2eqftNU/rIDzzcoP96uz3+vt9qscKXZTsKUT1g5L+7O7n7/WCrckQYpcMX5XtITzyTmP6r7j68/bs9PjL3emQts8/gawHXJQAVY/uP1bqS2Lbh5q+ucuWutJonb8+gKsaaJwEWpIAVpAAV5AFWpJqia0vd6USZZoAWJEAV5EBV5EUZJkAVo8IXJQjbqASY5gQYpgbaZwAAAAprTAwAAAAW3RSTlMAAAAAAAAAAAACKHrA4ufPlUIICWDO+f3jiRsHdu/7qRxW7PyRC8HoT13y/qqZ/ua4/Lf8lf3jWPD+pBm65UhO5/qHBWrp+Z4XBlPD9frbexUBH2mt0te9gjUElCjBbQAAAAFiS0dEAIgFHUgAAAAJcEhZcwAACxMAAAsTAQCanBgAAAEDSURBVBjTY2BgYOTk4ubh5eMXEBRiZAACRmERUbHomNg4cQlJKSZGBkZpGdn4hEQgSEqWk1dgZGBWVEpJTAWBtPQMZRVVBgW1zCwwPzUxOydXXYNBUysNwk/Lyy8o1NZh0NWDCCQWFZcUlJbpMxiUg01IrKisKiiorjFkMKoFCSTW1TcUFDQ2NRszmJgCtSS0tLYVFLR3dHaZMZhbpKV19/T29U+YOGnyFEsrBmubqdOmz5g5a/acufPmL7C1Y2Cxd1i4aPHcefPmL1m6zNFJiIHR2cV1+Yp585fOn7fSzd2DFeg5Ty9vn1Wr16z19fMPYAP5lzEwKDgkNCw8ItKZnSMKAAihVlWjuDlCAAAAJXRFWHRkYXRlOmNyZWF0ZQAyMDE3LTA5LTA5VDE3OjE3OjAyKzAyOjAw4iAIuQAAACV0RVh0ZGF0ZTptb2RpZnkAMjAxNy0wOS0wOVQxNzoxNzowMiswMjowMJN9sAUAAAAASUVORK5CYII=")));
            final String tooltip = "pepsimod";
            final PopupMenu popupMenu = new PopupMenu();
            this.menu = popupMenu;
            this.trayIcon = new TrayIcon(read, tooltip, popupMenu);
            this.tray.add(this.trayIcon);
        }
        catch (IOException | AWTException ex2) {
            final Exception ex;
            final Exception e = ex;
            e.printStackTrace();
            ModuleManager.unRegister(this);
        }
    }
    
    public ModuleOption[] getDefaultOptions() {
        return new ModuleOption[] { new ModuleOption((T)NotificationsTranslator.INSTANCE.chat, "chat", OptionCompletions.BOOLEAN, value -> {
                NotificationsTranslator.INSTANCE.chat = value;
                return Boolean.valueOf(true);
            }, () -> NotificationsTranslator.INSTANCE.chat, "Chat"), new ModuleOption((T)NotificationsTranslator.INSTANCE.queue, "queue", OptionCompletions.BOOLEAN, value -> {
                NotificationsTranslator.INSTANCE.queue = value;
                return Boolean.valueOf(true);
            }, () -> NotificationsTranslator.INSTANCE.queue, "Queue"), new ModuleOption((T)NotificationsTranslator.INSTANCE.death, "death", OptionCompletions.BOOLEAN, value -> {
                NotificationsTranslator.INSTANCE.death = value;
                return Boolean.valueOf(true);
            }, () -> NotificationsTranslator.INSTANCE.death, "Death"), new ModuleOption((T)NotificationsTranslator.INSTANCE.player, "visual_range", OptionCompletions.BOOLEAN, value -> {
                NotificationsTranslator.INSTANCE.player = value;
                return Boolean.valueOf(true);
            }, () -> NotificationsTranslator.INSTANCE.player, "Visual Range") };
    }
    
    @Override
    public ModuleCategory getCategory() {
        return ModuleCategory.MISC;
    }
    
    @Override
    public boolean shouldRegister() {
        return SystemTray.isSupported();
    }
    
    @Override
    public void postRecievePacket(final Packet<?> packet) {
        if (packet instanceof SPacketChat) {
            final SPacketChat pck = (SPacketChat)packet;
            if (!pck.isSystem()) {
                final String message = pck.getChatComponent().getUnformattedText().toLowerCase();
                if (NotificationsTranslator.INSTANCE.queue && message.startsWith("position in queue")) {
                    this.inQueue = true;
                }
                else if (this.inQueue && message.startsWith("connecting to")) {
                    sendNotification("Finished going through the queue!", TrayIcon.MessageType.INFO);
                    this.inQueue = false;
                }
                else if (NotificationsTranslator.INSTANCE.chat && message.contains(NotificationsMod.mc.getSession().getUsername().toLowerCase())) {
                    sendNotification("Your name was mentioned in chat!", TrayIcon.MessageType.INFO);
                }
            }
        }
        else if (NotificationsTranslator.INSTANCE.player && packet instanceof SPacketSpawnPlayer) {
            sendNotification(NotificationsMod.mc.getConnection().getPlayerInfo(((SPacketSpawnPlayer)packet).getUniqueId()).getGameProfile().getName() + " entered visual range!", TrayIcon.MessageType.INFO);
        }
    }
}
