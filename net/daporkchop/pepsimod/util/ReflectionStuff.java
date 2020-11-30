// 
// Decompiled by Procyon v0.5.36
// 

package net.daporkchop.pepsimod.util;

import net.minecraft.network.datasync.DataParameter;
import net.minecraft.util.Timer;
import net.minecraft.client.renderer.RenderItem;
import net.minecraft.client.gui.GuiScreen;
import java.util.Map;
import java.util.Set;
import com.google.common.collect.ImmutableSet;
import net.minecraft.util.math.BlockPos;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.text.translation.LanguageMap;
import net.minecraft.network.play.client.CPacketVehicleMove;
import net.minecraft.client.resources.DefaultResourcePack;
import net.minecraft.client.gui.GuiDisconnected;
import net.minecraft.client.multiplayer.PlayerControllerMP;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.network.play.client.CPacketPlayer;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.client.Minecraft;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraftforge.fml.common.FMLLog;
import java.lang.reflect.Method;
import java.lang.reflect.Field;

public class ReflectionStuff extends PepsiConstants
{
    public static Field renderPosX;
    public static Field renderPosY;
    public static Field renderPosZ;
    public static Field sleeping;
    public static Field PLAYER_MODEL_FLAG;
    public static Field minX;
    public static Field minY;
    public static Field minZ;
    public static Field maxX;
    public static Field maxY;
    public static Field maxZ;
    public static Field y_vec3d;
    public static Field timer;
    public static Field boundingBox;
    public static Field debugFps;
    public static Field itemRenderer;
    public static Field pressed;
    public static Field ridingEntity;
    public static Field horseJumpPower;
    public static Field cPacketPlayer_x;
    public static Field cPacketPlayer_y;
    public static Field cPacketPlayer_z;
    public static Field landMovementFactor;
    public static Field inWater;
    public static Field rightClickDelayTimer;
    public static Field curBlockDamageMP;
    public static Field blockHitDelay;
    public static Field cPacketPlayer_onGround;
    public static Field parentScreen;
    public static Field DEFAULT_RESOURCE_DOMAINS;
    public static Field cPacketVehicleMove_y;
    public static Field currentPlayerItem;
    public static Field languageMap_instance;
    public static Field languageMap_languageList;
    public static Method updateFallState;
    public static Method rightClickMouse;
    private static Field modifiersField;
    
    public static Field getField(final Class c, final String... names) {
        for (final String s : names) {
            try {
                final Field f = c.getDeclaredField(s);
                f.setAccessible(true);
                ReflectionStuff.modifiersField.setInt(f, f.getModifiers() & 0xFFFFFFEF);
                return f;
            }
            catch (NoSuchFieldException e) {
                FMLLog.log.info("unable to find field: " + s);
            }
            catch (IllegalAccessException e2) {
                FMLLog.log.info("unable to make field changeable!");
            }
        }
        throw new IllegalStateException("Field with names: " + names + " not found!");
    }
    
    public static Method getMethod(final Class c, final String[] names, final Class<?>... args) {
        final int length = names.length;
        int i = 0;
        while (i < length) {
            final String s = names[i];
            try {
                final Method m = c.getDeclaredMethod(s, (Class[])args);
                m.setAccessible(true);
                return m;
            }
            catch (NoSuchMethodException e) {
                FMLLog.log.info("unable to find method: " + s);
                ++i;
                continue;
            }
            break;
        }
        throw new IllegalStateException("Method with names: " + names + " not found!");
    }
    
    public static void init() {
        try {
            ReflectionStuff.renderPosX = getField(RenderManager.class, "renderPosX", "field_78725_b", "o");
            ReflectionStuff.renderPosY = getField(RenderManager.class, "renderPosY", "field_78726_c", "p");
            ReflectionStuff.renderPosZ = getField(RenderManager.class, "renderPosZ", "field_78723_d", "q");
            ReflectionStuff.sleeping = getField(EntityPlayer.class, "sleeping", "field_71083_bS", "bK");
            ReflectionStuff.PLAYER_MODEL_FLAG = getField(EntityPlayer.class, "PLAYER_MODEL_FLAG", "field_184827_bp", "br");
            ReflectionStuff.minX = getField(AxisAlignedBB.class, "minX", "field_72340_a", "a");
            ReflectionStuff.minY = getField(AxisAlignedBB.class, "minY", "field_72338_b", "b");
            ReflectionStuff.minZ = getField(AxisAlignedBB.class, "minZ", "field_72339_c", "c");
            ReflectionStuff.maxX = getField(AxisAlignedBB.class, "maxX", "field_72336_d", "d");
            ReflectionStuff.maxY = getField(AxisAlignedBB.class, "maxY", "field_72337_e", "e");
            ReflectionStuff.maxZ = getField(AxisAlignedBB.class, "maxZ", "field_72334_f", "f");
            ReflectionStuff.y_vec3d = getField(Vec3d.class, "y", "field_72448_b", "c");
            ReflectionStuff.timer = getField(Minecraft.class, "timer", "field_71428_T", "Y");
            ReflectionStuff.boundingBox = getField(Entity.class, "boundingBox", "field_70121_D", "av");
            ReflectionStuff.debugFps = getField(Minecraft.class, "debugFPS", "field_71470_ab", "ar");
            ReflectionStuff.itemRenderer = getField(ItemRenderer.class, "itemRenderer", "field_178112_h", "k");
            ReflectionStuff.pressed = getField(KeyBinding.class, "pressed", "field_74513_e", "i");
            ReflectionStuff.ridingEntity = getField(Entity.class, "ridingEntity", "field_184239_as", "au");
            ReflectionStuff.horseJumpPower = getField(EntityPlayerSP.class, "horseJumpPower", "field_110321_bQ", "cq");
            ReflectionStuff.cPacketPlayer_x = getField(CPacketPlayer.class, "x", "field_149479_a", "a");
            ReflectionStuff.cPacketPlayer_y = getField(CPacketPlayer.class, "y", "field_149477_b", "b");
            ReflectionStuff.cPacketPlayer_z = getField(CPacketPlayer.class, "z", "field_149478_c", "c");
            ReflectionStuff.inWater = getField(Entity.class, "inWater", "field_70171_ac", "U");
            ReflectionStuff.landMovementFactor = getField(EntityLivingBase.class, "landMovementFactor", "field_70746_aG", "bC");
            ReflectionStuff.rightClickDelayTimer = getField(Minecraft.class, "rightClickDelayTimer", "field_71467_ac", "as");
            ReflectionStuff.blockHitDelay = getField(PlayerControllerMP.class, "blockHitDelay", "field_78781_i", "g");
            ReflectionStuff.curBlockDamageMP = getField(PlayerControllerMP.class, "curBlockDamageMP", "field_78770_f", "e");
            ReflectionStuff.cPacketPlayer_onGround = getField(CPacketPlayer.class, "onGround", "field_149474_g", "f");
            ReflectionStuff.parentScreen = getField(GuiDisconnected.class, "parentScreen", "field_146307_h", "h");
            ReflectionStuff.DEFAULT_RESOURCE_DOMAINS = getField(DefaultResourcePack.class, "DEFAULT_RESOURCE_DOMAINS", "field_110608_a", "a");
            ReflectionStuff.cPacketVehicleMove_y = getField(CPacketVehicleMove.class, "y", "field_187008_b", "b");
            ReflectionStuff.currentPlayerItem = getField(PlayerControllerMP.class, "currentPlayerItem", "field_78777_l", "j");
            ReflectionStuff.languageMap_instance = getField(LanguageMap.class, "instance", "field_74817_a", "c");
            ReflectionStuff.languageMap_languageList = getField(LanguageMap.class, "languageList", "field_74816_c", "d");
            ReflectionStuff.updateFallState = getMethod(Entity.class, new String[] { "updateFallState", "func_184231_a", "a" }, Double.TYPE, Boolean.TYPE, IBlockState.class, BlockPos.class);
            ReflectionStuff.rightClickMouse = getMethod(Minecraft.class, new String[] { "rightClickMouse", "func_147121_ag", "aB" }, (Class<?>[])new Class[0]);
            setDEFAULT_RESOURCE_DOMAINS((Set<String>)ImmutableSet.builder().addAll((Iterable)DefaultResourcePack.DEFAULT_RESOURCE_DOMAINS).add((Object)"wdl").build());
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public static Map<String, String> getLanguageMapMap() {
        try {
            return (Map<String, String>)ReflectionStuff.languageMap_languageList.get(ReflectionStuff.languageMap_instance.get(null));
        }
        catch (Exception e) {
            throw new IllegalStateException(e);
        }
    }
    
    public static LanguageMap getLanguageMap() {
        try {
            return (LanguageMap)ReflectionStuff.languageMap_instance.get(null);
        }
        catch (Exception e) {
            throw new IllegalStateException(e);
        }
    }
    
    public static void setCurrentPlayerItem(final int i) {
        try {
            ReflectionStuff.currentPlayerItem.setInt(ReflectionStuff.mc.playerController, i);
        }
        catch (Exception e) {
            throw new IllegalStateException(e);
        }
    }
    
    public static double getCPacketVehicleMove_y(final CPacketVehicleMove n) {
        try {
            return ReflectionStuff.cPacketVehicleMove_y.getDouble(n);
        }
        catch (Exception e) {
            throw new IllegalStateException(e);
        }
    }
    
    public static void setcPacketVehicleMove_y(final CPacketVehicleMove n, final double y) {
        try {
            ReflectionStuff.cPacketVehicleMove_y.setDouble(n, y);
        }
        catch (Exception e) {
            throw new IllegalStateException(e);
        }
    }
    
    public static void setDEFAULT_RESOURCE_DOMAINS(final Set<String> n) {
        try {
            ReflectionStuff.DEFAULT_RESOURCE_DOMAINS.set(null, n);
        }
        catch (Exception e) {
            throw new IllegalStateException(e);
        }
    }
    
    public static GuiScreen getParentScreen(final GuiDisconnected disconnected) {
        try {
            return (GuiScreen)ReflectionStuff.parentScreen.get(disconnected);
        }
        catch (Exception e) {
            throw new IllegalStateException(e);
        }
    }
    
    public static void rightClickMouse() {
        try {
            ReflectionStuff.rightClickMouse.invoke(ReflectionStuff.mc, new Object[0]);
        }
        catch (Exception e) {
            e.printStackTrace();
            throw new IllegalStateException(e);
        }
    }
    
    public static void setcPacketPlayer_onGround(final CPacketPlayer packet, final boolean onGround) {
        try {
            ReflectionStuff.cPacketPlayer_onGround.setBoolean(packet, onGround);
        }
        catch (Exception e) {
            e.printStackTrace();
            throw new IllegalStateException(e);
        }
    }
    
    public static boolean getPressed(final KeyBinding binding) {
        try {
            return ReflectionStuff.pressed.getBoolean(binding);
        }
        catch (Exception e) {
            e.printStackTrace();
            throw new IllegalStateException(e);
        }
    }
    
    public static float getCurBlockDamageMP() {
        try {
            return ReflectionStuff.curBlockDamageMP.getFloat(ReflectionStuff.mc.playerController);
        }
        catch (Exception e) {
            e.printStackTrace();
            throw new IllegalStateException(e);
        }
    }
    
    public static void setCurBlockDamageMP(final float val) {
        try {
            ReflectionStuff.curBlockDamageMP.setFloat(ReflectionStuff.mc.playerController, val);
        }
        catch (Exception e) {
            e.printStackTrace();
            throw new IllegalStateException(e);
        }
    }
    
    public static int getBlockHitDelay() {
        try {
            return ReflectionStuff.blockHitDelay.getInt(ReflectionStuff.mc.playerController);
        }
        catch (Exception e) {
            e.printStackTrace();
            throw new IllegalStateException(e);
        }
    }
    
    public static void setBlockHitDelay(final int val) {
        try {
            ReflectionStuff.blockHitDelay.setInt(ReflectionStuff.mc.playerController, val);
        }
        catch (Exception e) {
            e.printStackTrace();
            throw new IllegalStateException(e);
        }
    }
    
    public static void setRightClickDelayTimer(final int val) {
        try {
            ReflectionStuff.rightClickDelayTimer.setInt(ReflectionStuff.mc, val);
        }
        catch (Exception e) {
            e.printStackTrace();
            throw new IllegalStateException(e);
        }
    }
    
    public static void setInWater(final Entity entity, final boolean y) {
        try {
            ReflectionStuff.inWater.setBoolean(entity, y);
        }
        catch (Exception e) {
            e.printStackTrace();
            throw new IllegalStateException(e);
        }
    }
    
    public static void setLandMovementFactor(final EntityLivingBase entity, final float y) {
        try {
            ReflectionStuff.landMovementFactor.setFloat(entity, y);
        }
        catch (Exception e) {
            e.printStackTrace();
            throw new IllegalStateException(e);
        }
    }
    
    public static void setCPacketPlayer_x(final CPacketPlayer packet, final double x) {
        try {
            ReflectionStuff.cPacketPlayer_x.setDouble(packet, x);
        }
        catch (Exception e) {
            e.printStackTrace();
            throw new IllegalStateException(e);
        }
    }
    
    public static void setCPacketPlayer_y(final CPacketPlayer packet, final double y) {
        try {
            ReflectionStuff.cPacketPlayer_y.setDouble(packet, y);
        }
        catch (Exception e) {
            e.printStackTrace();
            throw new IllegalStateException(e);
        }
    }
    
    public static void setCPacketPlayer_z(final CPacketPlayer packet, final double z) {
        try {
            ReflectionStuff.cPacketPlayer_z.setDouble(packet, z);
        }
        catch (Exception e) {
            e.printStackTrace();
            throw new IllegalStateException(e);
        }
    }
    
    public static void setHorseJumpPower(final float value) {
        try {
            ReflectionStuff.horseJumpPower.setFloat(ReflectionStuff.mc.player, value);
        }
        catch (Exception e) {
            e.printStackTrace();
            throw new IllegalStateException(e);
        }
    }
    
    public static void updateEntityFallState(final Entity e, final double d, final boolean b, final IBlockState state, final BlockPos pos) {
        try {
            ReflectionStuff.updateFallState.invoke(e, d, b, state, pos);
        }
        catch (Exception exception) {
            exception.printStackTrace();
            throw new IllegalStateException(exception);
        }
    }
    
    public static Entity getRidingEntity(final Entity toGetFrom) {
        try {
            return (Entity)ReflectionStuff.ridingEntity.get(toGetFrom);
        }
        catch (Exception e) {
            e.printStackTrace();
            throw new IllegalStateException(e);
        }
    }
    
    public static void setPressed(final KeyBinding keyBinding, final boolean state) {
        try {
            ReflectionStuff.pressed.setBoolean(keyBinding, state);
        }
        catch (Exception e) {
            e.printStackTrace();
            throw new IllegalStateException(e);
        }
    }
    
    public static RenderItem getItemRenderer() {
        try {
            return (RenderItem)ReflectionStuff.itemRenderer.get(ReflectionStuff.mc.getItemRenderer());
        }
        catch (Exception e) {
            e.printStackTrace();
            throw new IllegalStateException(e);
        }
    }
    
    public static int getDebugFps() {
        try {
            return ReflectionStuff.debugFps.getInt(null);
        }
        catch (Exception e) {
            e.printStackTrace();
            throw new IllegalStateException(e);
        }
    }
    
    public static AxisAlignedBB getBoundingBox(final Entity entity) {
        try {
            return (AxisAlignedBB)ReflectionStuff.boundingBox.get(entity);
        }
        catch (Exception e) {
            e.printStackTrace();
            throw new IllegalStateException(e);
        }
    }
    
    public static Timer getTimer() {
        try {
            return (Timer)ReflectionStuff.timer.get(ReflectionStuff.mc);
        }
        catch (Exception e) {
            e.printStackTrace();
            throw new IllegalStateException(e);
        }
    }
    
    public static void setY_vec3d(final Vec3d vec, final double val) {
        try {
            ReflectionStuff.y_vec3d.setDouble(vec, val);
        }
        catch (Exception e) {
            e.printStackTrace();
            throw new IllegalStateException(e);
        }
    }
    
    public static double getMinX(final AxisAlignedBB bb) {
        try {
            return ReflectionStuff.minX.getDouble(bb);
        }
        catch (Exception e) {
            e.printStackTrace();
            throw new IllegalStateException(e);
        }
    }
    
    public static double getMinY(final AxisAlignedBB bb) {
        try {
            return ReflectionStuff.minY.getDouble(bb);
        }
        catch (Exception e) {
            e.printStackTrace();
            throw new IllegalStateException(e);
        }
    }
    
    public static double getMinZ(final AxisAlignedBB bb) {
        try {
            return ReflectionStuff.minZ.getDouble(bb);
        }
        catch (Exception e) {
            e.printStackTrace();
            throw new IllegalStateException(e);
        }
    }
    
    public static double getMaxX(final AxisAlignedBB bb) {
        try {
            return ReflectionStuff.maxX.getDouble(bb);
        }
        catch (Exception e) {
            e.printStackTrace();
            throw new IllegalStateException(e);
        }
    }
    
    public static double getMaxY(final AxisAlignedBB bb) {
        try {
            return ReflectionStuff.maxY.getDouble(bb);
        }
        catch (Exception e) {
            e.printStackTrace();
            throw new IllegalStateException(e);
        }
    }
    
    public static double getMaxZ(final AxisAlignedBB bb) {
        try {
            return ReflectionStuff.maxZ.getDouble(bb);
        }
        catch (Exception e) {
            e.printStackTrace();
            throw new IllegalStateException(e);
        }
    }
    
    public static void setMinX(final AxisAlignedBB bb, final double val) {
        try {
            ReflectionStuff.minX.setDouble(bb, val);
        }
        catch (Exception e) {
            e.printStackTrace();
            throw new IllegalStateException(e);
        }
    }
    
    public static void setMinY(final AxisAlignedBB bb, final double val) {
        try {
            ReflectionStuff.minY.setDouble(bb, val);
        }
        catch (Exception e) {
            e.printStackTrace();
            throw new IllegalStateException(e);
        }
    }
    
    public static void setMinZ(final AxisAlignedBB bb, final double val) {
        try {
            ReflectionStuff.minZ.setDouble(bb, val);
        }
        catch (Exception e) {
            e.printStackTrace();
            throw new IllegalStateException(e);
        }
    }
    
    public static void setMaxX(final AxisAlignedBB bb, final double val) {
        try {
            ReflectionStuff.maxX.setDouble(bb, val);
        }
        catch (Exception e) {
            e.printStackTrace();
            throw new IllegalStateException(e);
        }
    }
    
    public static void setMaxY(final AxisAlignedBB bb, final double val) {
        try {
            ReflectionStuff.maxY.setDouble(bb, val);
        }
        catch (Exception e) {
            e.printStackTrace();
            throw new IllegalStateException(e);
        }
    }
    
    public static void setMaxZ(final AxisAlignedBB bb, final double val) {
        try {
            ReflectionStuff.maxZ.setDouble(bb, val);
        }
        catch (Exception e) {
            e.printStackTrace();
            throw new IllegalStateException(e);
        }
    }
    
    public static DataParameter<Byte> getPLAYER_MODEL_FLAG() {
        try {
            return (DataParameter<Byte>)ReflectionStuff.PLAYER_MODEL_FLAG.get(null);
        }
        catch (Exception e) {
            e.printStackTrace();
            throw new IllegalStateException(e);
        }
    }
    
    public static double getRenderPosX(final RenderManager mgr) {
        try {
            return ReflectionStuff.renderPosX.getDouble(mgr);
        }
        catch (Exception e) {
            e.printStackTrace();
            throw new IllegalStateException(e);
        }
    }
    
    public static double getRenderPosY(final RenderManager mgr) {
        try {
            return ReflectionStuff.renderPosY.getDouble(mgr);
        }
        catch (Exception e) {
            e.printStackTrace();
            throw new IllegalStateException(e);
        }
    }
    
    public static double getRenderPosZ(final RenderManager mgr) {
        try {
            return ReflectionStuff.renderPosZ.getDouble(mgr);
        }
        catch (Exception e) {
            e.printStackTrace();
            throw new IllegalStateException(e);
        }
    }
    
    public static double getRenderPosX() {
        try {
            return ReflectionStuff.renderPosX.getDouble(ReflectionStuff.mc.getRenderManager());
        }
        catch (Exception e) {
            e.printStackTrace();
            throw new IllegalStateException(e);
        }
    }
    
    public static double getRenderPosY() {
        try {
            return ReflectionStuff.renderPosY.getDouble(ReflectionStuff.mc.getRenderManager());
        }
        catch (Exception e) {
            e.printStackTrace();
            throw new IllegalStateException(e);
        }
    }
    
    public static double getRenderPosZ() {
        try {
            return ReflectionStuff.renderPosZ.getDouble(ReflectionStuff.mc.getRenderManager());
        }
        catch (Exception e) {
            e.printStackTrace();
            throw new IllegalStateException(e);
        }
    }
    
    public static boolean getSleeping(final EntityPlayer mgr) {
        try {
            return ReflectionStuff.sleeping.getBoolean(mgr);
        }
        catch (Exception e) {
            e.printStackTrace();
            throw new IllegalStateException(e);
        }
    }
    
    static {
        try {
            (ReflectionStuff.modifiersField = Field.class.getDeclaredField("modifiers")).setAccessible(true);
        }
        catch (Exception ex) {}
    }
}
