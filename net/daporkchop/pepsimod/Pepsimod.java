// 
// Decompiled by Procyon v0.5.36
// 

package net.daporkchop.pepsimod;

import java.io.OutputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import net.daporkchop.pepsimod.util.config.Config;
import java.io.IOException;
import org.apache.commons.io.IOUtils;
import java.io.FileInputStream;
import java.util.TimerTask;
import net.daporkchop.pepsimod.util.PepsiUtils;
import net.daporkchop.pepsimod.event.MiscEventHandler;
import net.daporkchop.pepsimod.event.GuiRenderHandler;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.daporkchop.pepsimod.command.CommandRegistry;
import net.daporkchop.pepsimod.command.impl.GoToCommand;
import net.daporkchop.pepsimod.command.impl.waypoint.WaypointShowCommand;
import net.daporkchop.pepsimod.command.impl.waypoint.WaypointRemoveCommand;
import net.daporkchop.pepsimod.command.impl.waypoint.WaypointListCommand;
import net.daporkchop.pepsimod.command.impl.waypoint.WaypointHideCommand;
import net.daporkchop.pepsimod.command.impl.waypoint.WaypointHardClearCommand;
import net.daporkchop.pepsimod.command.impl.waypoint.WaypointClearCommand;
import net.daporkchop.pepsimod.command.impl.waypoint.WaypointAddCommand;
import net.daporkchop.pepsimod.command.impl.PeekCommand;
import net.daporkchop.pepsimod.command.impl.InvSeeCommand;
import net.daporkchop.pepsimod.command.impl.ListCommand;
import net.daporkchop.pepsimod.command.impl.SaveCommand;
import net.daporkchop.pepsimod.command.impl.SortModulesCommand;
import net.daporkchop.pepsimod.command.impl.ToggleCommand;
import net.daporkchop.pepsimod.command.impl.SetRotCommand;
import net.daporkchop.pepsimod.command.impl.HelpCommand;
import net.daporkchop.pepsimod.command.api.Command;
import net.daporkchop.pepsimod.util.config.impl.HUDTranslator;
import net.daporkchop.pepsimod.module.ModuleManager;
import net.daporkchop.pepsimod.module.impl.player.AntiAFKMod;
import net.daporkchop.pepsimod.module.impl.combat.AutoArmorMod;
import net.daporkchop.pepsimod.module.impl.misc.AutoToolMod;
import net.daporkchop.pepsimod.module.impl.misc.AutoFishMod;
import net.daporkchop.pepsimod.module.impl.combat.BedBomberMod;
import net.daporkchop.pepsimod.module.impl.misc.NotificationsMod;
import net.daporkchop.pepsimod.module.impl.movement.BoatFlyMod;
import net.daporkchop.pepsimod.module.impl.movement.NoClipMod;
import net.daporkchop.pepsimod.module.impl.misc.WaypointsMod;
import net.daporkchop.pepsimod.module.impl.render.ESPMod;
import net.daporkchop.pepsimod.module.impl.render.UnfocusedCPUMod;
import net.daporkchop.pepsimod.module.impl.player.ScaffoldMod;
import net.daporkchop.pepsimod.module.impl.player.AutoMineMod;
import net.daporkchop.pepsimod.module.impl.movement.StepMod;
import net.daporkchop.pepsimod.module.impl.player.AutoEatMod;
import net.daporkchop.pepsimod.module.impl.player.SpeedmineMod;
import net.daporkchop.pepsimod.module.impl.player.FastPlaceMod;
import net.daporkchop.pepsimod.module.impl.movement.FlightMod;
import net.daporkchop.pepsimod.module.impl.movement.NoSlowdownMod;
import net.daporkchop.pepsimod.module.impl.player.SprintMod;
import net.daporkchop.pepsimod.module.impl.movement.JesusMod;
import net.daporkchop.pepsimod.module.impl.movement.AutoRespawnMod;
import net.daporkchop.pepsimod.module.impl.misc.AnnouncerMod;
import net.daporkchop.pepsimod.module.impl.render.AntiTotemAnimationMod;
import net.daporkchop.pepsimod.module.impl.combat.CrystalAuraMod;
import net.daporkchop.pepsimod.module.impl.combat.AutoTotemMod;
import net.daporkchop.pepsimod.module.impl.movement.HorseJumpPowerMod;
import net.daporkchop.pepsimod.module.impl.movement.InventoryMoveMod;
import net.daporkchop.pepsimod.module.impl.movement.SafewalkMod;
import net.daporkchop.pepsimod.module.impl.movement.EntitySpeedMod;
import net.daporkchop.pepsimod.module.impl.movement.AutoWalkMod;
import net.daporkchop.pepsimod.module.impl.movement.ElytraFlyMod;
import net.daporkchop.pepsimod.module.impl.render.ZoomMod;
import net.daporkchop.pepsimod.module.impl.misc.HUDMod;
import net.daporkchop.pepsimod.module.impl.misc.ClickGuiMod;
import net.daporkchop.pepsimod.module.impl.render.TracersMod;
import net.daporkchop.pepsimod.module.impl.render.TrajectoriesMod;
import net.daporkchop.pepsimod.module.impl.render.AntiInvisibleMod;
import net.daporkchop.pepsimod.module.impl.render.NoWeatherMod;
import net.daporkchop.pepsimod.module.impl.render.NoOverlayMod;
import net.daporkchop.pepsimod.module.impl.render.NoHurtCamMod;
import net.daporkchop.pepsimod.module.impl.render.NameTagsMod;
import net.daporkchop.pepsimod.module.impl.render.HealthTagsMod;
import net.daporkchop.pepsimod.module.impl.misc.FreecamMod;
import net.daporkchop.pepsimod.module.impl.render.StorageESPMod;
import net.daporkchop.pepsimod.module.impl.render.AntiBlindMod;
import net.daporkchop.pepsimod.module.impl.render.XrayMod;
import net.daporkchop.pepsimod.module.impl.misc.TimerMod;
import net.daporkchop.pepsimod.module.impl.movement.VelocityMod;
import net.daporkchop.pepsimod.module.impl.combat.AuraMod;
import net.daporkchop.pepsimod.module.impl.combat.CriticalsMod;
import net.daporkchop.pepsimod.module.impl.render.FullbrightMod;
import net.daporkchop.pepsimod.module.impl.misc.AntiHungerMod;
import net.daporkchop.pepsimod.module.impl.misc.NoFallMod;
import net.daporkchop.pepsimod.module.api.Module;
import net.daporkchop.pepsimod.gui.clickgui.WindowPlayer;
import net.daporkchop.pepsimod.gui.clickgui.WindowMovement;
import net.daporkchop.pepsimod.gui.clickgui.WindowMisc;
import net.daporkchop.pepsimod.gui.clickgui.WindowCombat;
import net.daporkchop.pepsimod.gui.clickgui.WindowRender;
import net.daporkchop.pepsimod.clickgui.Window;
import net.daporkchop.pepsimod.clickgui.ClickGUI;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.daporkchop.pepsimod.key.KeyRegistry;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import java.io.File;
import net.daporkchop.pepsimod.util.ReflectionStuff;
import net.minecraft.client.Minecraft;
import net.minecraftforge.fml.common.event.FMLConstructionEvent;
import net.minecraft.util.Session;
import net.daporkchop.pepsimod.misc.data.DataLoader;
import net.minecraftforge.fml.common.Mod;
import net.daporkchop.pepsimod.util.PepsiConstants;

@Mod(modid = "pepsimod", version = "v11.1", useMetadata = true)
public class Pepsimod extends PepsiConstants
{
    public static final String VERSION;
    public static final String CHAT_PREFIX = "§0§l[§c§lpepsi§9§lmod§0§l]§r";
    public static final String NAME_VERSION;
    public DataLoader data;
    public boolean isMcLeaksAccount;
    public Session originalSession;
    public boolean hasInitializedModules;
    public boolean isInitialized;
    
    public Pepsimod() {
        this.isMcLeaksAccount = false;
        this.originalSession = null;
        this.hasInitializedModules = false;
        this.isInitialized = false;
        PepsiConstants.pepsimod = this;
    }
    
    @Mod.EventHandler
    public void construction(final FMLConstructionEvent event) {
        Pepsimod.mc = Minecraft.getMinecraft();
        Pepsimod.pepsimod = this;
        ReflectionStuff.init();
        this.data = new DataLoader("https://raw.githubusercontent.com/Team-Pepsi/pepsimod/master/resources/resources.json", new File(Pepsimod.mc.gameDir, "pepsimod/resources/"));
    }
    
    @Mod.EventHandler
    public void preInit(final FMLPreInitializationEvent event) {
        MinecraftForge.EVENT_BUS.register((Object)new KeyRegistry());
    }
    
    @Mod.EventHandler
    public void init(final FMLInitializationEvent event) {
        this.data.load();
        new ClickGUI();
        ClickGUI.INSTANCE.setWindows(new WindowRender(), new WindowCombat(), new WindowMisc(), new WindowMovement(), new WindowPlayer());
        this.loadConfig();
        ModuleManager.registerModules(new NoFallMod(), new AntiHungerMod(), new FullbrightMod(), new CriticalsMod(), new AuraMod(), new VelocityMod(), new TimerMod(), new XrayMod(), new AntiBlindMod(), new StorageESPMod(), new FreecamMod(), new HealthTagsMod(), new NameTagsMod(), new NoHurtCamMod(), new NoOverlayMod(), new NoWeatherMod(), new AntiInvisibleMod(), new TrajectoriesMod(), new TracersMod(), new ClickGuiMod(false, 54), new HUDMod(true, -1), new ZoomMod(-1), new ElytraFlyMod(), new AutoWalkMod(), new EntitySpeedMod(), new SafewalkMod(), new InventoryMoveMod(), new HorseJumpPowerMod(), new AutoTotemMod(), new CrystalAuraMod(), new AntiTotemAnimationMod(), new AnnouncerMod(), new AutoRespawnMod(), new JesusMod(), new SprintMod(), new NoSlowdownMod(), new FlightMod(), new FastPlaceMod(), new SpeedmineMod(), new AutoEatMod(), new StepMod(), new AutoMineMod(), new ScaffoldMod(), new UnfocusedCPUMod(), new ESPMod(), new WaypointsMod(), new NoClipMod(), new BoatFlyMod(), new NotificationsMod(), new BedBomberMod(), new AutoFishMod(), new AutoToolMod(), new AutoArmorMod(), new AntiAFKMod());
        ClickGUI.INSTANCE.initWindows();
        HUDTranslator.INSTANCE.parseConfigLate();
        CommandRegistry.registerCommands(new HelpCommand(), new SetRotCommand(), new ToggleCommand(), new SortModulesCommand(), new SaveCommand(), new ListCommand(), new InvSeeCommand(), new PeekCommand(), new WaypointAddCommand(), new WaypointClearCommand(), new WaypointHardClearCommand(), new WaypointHideCommand(), new WaypointListCommand(), new WaypointRemoveCommand(), new WaypointShowCommand(), new GoToCommand());
    }
    
    @Mod.EventHandler
    public void postInit(final FMLPostInitializationEvent event) {
        MinecraftForge.EVENT_BUS.register((Object)new GuiRenderHandler());
        MinecraftForge.EVENT_BUS.register((Object)new MiscEventHandler());
        PepsiUtils.timer.schedule(new TimerTask() {
            @Override
            public void run() {
                PepsiConstants.pepsimod.saveConfig();
            }
        }, 360000L, 360000L);
    }
    
    public void loadConfig() {
        String launcherJson = "{}";
        final File file = new File(Pepsimod.mc.gameDir, "pepsimodConf.json");
        try (final InputStream in = new FileInputStream(file)) {
            launcherJson = IOUtils.toString(in, "UTF-8");
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        Config.loadConfig(launcherJson);
    }
    
    public void saveConfig() {
        final String config = Config.saveConfig();
        try {
            final File file = new File(Pepsimod.mc.gameDir, "pepsimodConf.json");
            if (!file.exists() && !file.createNewFile()) {
                throw new IllegalStateException(String.format("Unable to create file: %s", file.getAbsolutePath()));
            }
            try (final OutputStream out = new FileOutputStream(file)) {
                out.write(config.getBytes("UTF-8"));
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    static {
        String version = Pepsimod.class.getAnnotation(Mod.class).version();
        if (version.indexOf(45) == -1) {
            version += String.format("-%s", "1.12.2");
        }
        VERSION = version;
        NAME_VERSION = String.format("pepsimod %s", Pepsimod.VERSION);
    }
}
