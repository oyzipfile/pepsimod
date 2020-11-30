// 
// Decompiled by Procyon v0.5.36
// 

package net.daporkchop.pepsimod.util.config;

import net.daporkchop.pepsimod.util.config.impl.XrayTranslator;
import net.daporkchop.pepsimod.util.config.impl.WaypointsTranslator;
import net.daporkchop.pepsimod.util.config.impl.VelocityTranslator;
import net.daporkchop.pepsimod.util.config.impl.TracersTranslator;
import net.daporkchop.pepsimod.util.config.impl.TimerTranslator;
import net.daporkchop.pepsimod.util.config.impl.TargettingTranslator;
import net.daporkchop.pepsimod.util.config.impl.StepTranslator;
import net.daporkchop.pepsimod.util.config.impl.SpeedmineTranslator;
import net.daporkchop.pepsimod.util.config.impl.NoWeatherTranslator;
import net.daporkchop.pepsimod.util.config.impl.NotificationsTranslator;
import net.daporkchop.pepsimod.util.config.impl.NameTagsTranslator;
import net.daporkchop.pepsimod.util.config.impl.HUDTranslator;
import net.daporkchop.pepsimod.util.config.impl.GeneralTranslator;
import net.daporkchop.pepsimod.util.config.impl.FriendsTranslator;
import net.daporkchop.pepsimod.util.config.impl.FreecamTranslator;
import net.daporkchop.pepsimod.util.config.impl.FlightTranslator;
import net.daporkchop.pepsimod.util.config.impl.ESPTranslator;
import net.daporkchop.pepsimod.util.config.impl.EntitySpeedTranslator;
import net.daporkchop.pepsimod.util.config.impl.ElytraFlyTranslator;
import net.daporkchop.pepsimod.util.config.impl.CrystalAuraTranslator;
import net.daporkchop.pepsimod.util.config.impl.CriticalsTranslator;
import net.daporkchop.pepsimod.util.config.impl.CpuLimitTranslator;
import net.daporkchop.pepsimod.util.config.impl.ClickGUITranslator;
import net.daporkchop.pepsimod.util.config.impl.BedBomberTranslator;
import net.daporkchop.pepsimod.util.config.impl.AutoEatTranslator;
import net.daporkchop.pepsimod.util.config.impl.AntiAFKTranslator;
import net.daporkchop.pepsimod.util.config.impl.AnnouncerTranslator;
import com.google.gson.Gson;
import java.util.Iterator;
import com.google.gson.JsonObject;
import com.google.gson.JsonElement;
import java.util.Map;
import net.minecraftforge.fml.common.FMLLog;
import com.google.gson.JsonParser;
import java.util.Hashtable;

public class Config
{
    private static Hashtable<String, IConfigTranslator> translators;
    
    public static void registerConfigTranslator(final IConfigTranslator element) {
        Config.translators.put(element.name(), element);
    }
    
    public static void loadConfig(final String configJson) {
        System.out.println("Loading config!");
        System.out.println(configJson);
        JsonObject object = null;
        try {
            object = new JsonParser().parse(configJson).getAsJsonObject();
        }
        catch (IllegalStateException e) {
            FMLLog.info("Using default config because the file is empty or unreadable", new Object[0]);
            return;
        }
        for (final Map.Entry<String, JsonElement> entry : object.entrySet()) {
            Config.translators.getOrDefault(entry.getKey(), NullConfigTranslator.INSTANCE).decode(entry.getKey(), entry.getValue().getAsJsonObject());
        }
    }
    
    public static String saveConfig() {
        final JsonObject object = new JsonObject();
        for (final IConfigTranslator translator : Config.translators.values()) {
            final JsonObject elementObj = new JsonObject();
            translator.encode(elementObj);
            object.add(translator.name(), (JsonElement)elementObj);
        }
        return new Gson().toJson((JsonElement)object);
    }
    
    static {
        Config.translators = new Hashtable<String, IConfigTranslator>();
        registerConfigTranslator(AnnouncerTranslator.INSTANCE);
        registerConfigTranslator(AntiAFKTranslator.INSTANCE);
        registerConfigTranslator(AutoEatTranslator.INSTANCE);
        registerConfigTranslator(BedBomberTranslator.INSTANCE);
        registerConfigTranslator(ClickGUITranslator.INSTANCE);
        registerConfigTranslator(CpuLimitTranslator.INSTANCE);
        registerConfigTranslator(CriticalsTranslator.INSTANCE);
        registerConfigTranslator(CrystalAuraTranslator.INSTANCE);
        registerConfigTranslator(ElytraFlyTranslator.INSTANCE);
        registerConfigTranslator(EntitySpeedTranslator.INSTANCE);
        registerConfigTranslator(ESPTranslator.INSTANCE);
        registerConfigTranslator(FlightTranslator.INSTANCE);
        registerConfigTranslator(FreecamTranslator.INSTANCE);
        registerConfigTranslator(FriendsTranslator.INSTANCE);
        registerConfigTranslator(GeneralTranslator.INSTANCE);
        registerConfigTranslator(HUDTranslator.INSTANCE);
        registerConfigTranslator(NameTagsTranslator.INSTANCE);
        registerConfigTranslator(NotificationsTranslator.INSTANCE);
        registerConfigTranslator(NoWeatherTranslator.INSTANCE);
        registerConfigTranslator(SpeedmineTranslator.INSTANCE);
        registerConfigTranslator(StepTranslator.INSTANCE);
        registerConfigTranslator(TargettingTranslator.INSTANCE);
        registerConfigTranslator(TimerTranslator.INSTANCE);
        registerConfigTranslator(TracersTranslator.INSTANCE);
        registerConfigTranslator(VelocityTranslator.INSTANCE);
        registerConfigTranslator(WaypointsTranslator.INSTANCE);
        registerConfigTranslator(XrayTranslator.INSTANCE);
    }
}
