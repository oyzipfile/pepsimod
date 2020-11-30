// 
// Decompiled by Procyon v0.5.36
// 

package net.daporkchop.pepsimod.module.impl.render;

import net.daporkchop.pepsimod.module.ModuleCategory;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.item.ItemStack;
import net.minecraft.client.entity.EntityPlayerSP;
import net.daporkchop.pepsimod.the.wurst.pkg.name.RenderUtils;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.Vec3d;
import net.minecraft.item.ItemFishingRod;
import net.minecraft.item.ItemPotion;
import org.lwjgl.opengl.GL11;
import net.daporkchop.pepsimod.util.ReflectionStuff;
import net.minecraft.item.ItemBow;
import net.daporkchop.pepsimod.util.PepsiUtils;
import net.daporkchop.pepsimod.module.api.ModuleOption;
import net.daporkchop.pepsimod.util.RenderColor;
import net.daporkchop.pepsimod.module.api.Module;

public class TrajectoriesMod extends Module
{
    public static final RenderColor lineColor;
    public static TrajectoriesMod INSTANCE;
    
    public TrajectoriesMod() {
        super("Trajectories");
        TrajectoriesMod.INSTANCE = this;
    }
    
    @Override
    public void onEnable() {
    }
    
    @Override
    public void onDisable() {
    }
    
    @Override
    public void tick() {
    }
    
    @Override
    public void init() {
    }
    
    public ModuleOption[] getDefaultOptions() {
        return new ModuleOption[0];
    }
    
    @Override
    public void onRender(final float partialTicks) {
        final EntityPlayerSP player = TrajectoriesMod.mc.player;
        final ItemStack stack = player.inventory.getCurrentItem();
        if (stack == null) {
            return;
        }
        if (!PepsiUtils.isThrowable(stack)) {
            return;
        }
        final boolean usingBow = stack.getItem() instanceof ItemBow;
        double arrowPosX = player.lastTickPosX + (player.posX - player.lastTickPosX) * ReflectionStuff.getTimer().renderPartialTicks - Math.cos((float)Math.toRadians(player.rotationYaw)) * 0.1599999964237213;
        double arrowPosY = player.lastTickPosY + (player.posY - player.lastTickPosY) * ReflectionStuff.getTimer().renderPartialTicks + player.getEyeHeight() - 0.1;
        double arrowPosZ = player.lastTickPosZ + (player.posZ - player.lastTickPosZ) * ReflectionStuff.getTimer().renderPartialTicks - Math.sin((float)Math.toRadians(player.rotationYaw)) * 0.1599999964237213;
        final float arrowMotionFactor = usingBow ? 1.0f : 0.4f;
        final float yaw = (float)Math.toRadians(player.rotationYaw);
        final float pitch = (float)Math.toRadians(player.rotationPitch);
        float arrowMotionX = (float)(-Math.sin(yaw) * Math.cos(pitch) * arrowMotionFactor);
        float arrowMotionY = (float)(-Math.sin(pitch) * arrowMotionFactor);
        float arrowMotionZ = (float)(Math.cos(yaw) * Math.cos(pitch) * arrowMotionFactor);
        final double arrowMotion = Math.sqrt(arrowMotionX * arrowMotionX + arrowMotionY * arrowMotionY + arrowMotionZ * arrowMotionZ);
        arrowMotionX /= (float)arrowMotion;
        arrowMotionY /= (float)arrowMotion;
        arrowMotionZ /= (float)arrowMotion;
        if (usingBow) {
            float bowPower = (72000 - player.getItemInUseCount()) / 20.0f;
            bowPower = (bowPower * bowPower + bowPower * 2.0f) / 3.0f;
            if (bowPower > 1.0f || bowPower <= 0.1f) {
                bowPower = 1.0f;
            }
            bowPower *= 3.0f;
            arrowMotionX *= bowPower;
            arrowMotionY *= bowPower;
            arrowMotionZ *= bowPower;
        }
        else {
            arrowMotionX *= 1.5;
            arrowMotionY *= 1.5;
            arrowMotionZ *= 1.5;
        }
        GL11.glPushMatrix();
        GL11.glDisable(3553);
        GL11.glEnable(3042);
        GL11.glBlendFunc(770, 771);
        GL11.glDisable(2929);
        GL11.glDepthMask(false);
        GL11.glEnable(2848);
        GL11.glLineWidth(2.0f);
        final RenderManager renderManager = TrajectoriesMod.mc.getRenderManager();
        boolean hitEntity = false;
        final double gravity = usingBow ? 0.05 : ((stack.getItem() instanceof ItemPotion) ? 0.4 : ((stack.getItem() instanceof ItemFishingRod) ? 0.15 : 0.03));
        final Vec3d playerVector = new Vec3d(player.posX, player.posY + player.getEyeHeight(), player.posZ);
        PepsiUtils.glColor(TrajectoriesMod.lineColor);
        GL11.glBegin(3);
        for (int i = 0; i < 1000; ++i) {
            GL11.glVertex3d(arrowPosX - ReflectionStuff.getRenderPosX(renderManager), arrowPosY - ReflectionStuff.getRenderPosY(renderManager), arrowPosZ - ReflectionStuff.getRenderPosZ(renderManager));
            arrowPosX += arrowMotionX * 0.1;
            arrowPosY += arrowMotionY * 0.1;
            arrowPosZ += arrowMotionZ * 0.1;
            arrowMotionX *= (float)0.999;
            arrowMotionY *= (float)0.999;
            arrowMotionZ *= (float)0.999;
            arrowMotionY -= (float)(gravity * 0.1);
            final RayTraceResult result = TrajectoriesMod.mc.world.rayTraceBlocks(playerVector, new Vec3d(arrowPosX, arrowPosY, arrowPosZ));
            if (result != null) {
                break;
            }
            if (!TrajectoriesMod.mc.world.checkNoEntityCollision(new AxisAlignedBB(arrowPosX - 0.25, arrowPosY - 0.25, arrowPosZ - 0.25, arrowPosX + 0.25, arrowPosY + 0.25, arrowPosZ + 0.25), (Entity)TrajectoriesMod.mc.player)) {
                hitEntity = true;
                break;
            }
        }
        GL11.glEnd();
        final double renderX = arrowPosX - ReflectionStuff.getRenderPosX(renderManager);
        final double renderY = arrowPosY - ReflectionStuff.getRenderPosY(renderManager);
        final double renderZ = arrowPosZ - ReflectionStuff.getRenderPosZ(renderManager);
        GL11.glPushMatrix();
        GL11.glTranslated(renderX - 0.5, renderY - 0.5, renderZ - 0.5);
        if (hitEntity) {
            GL11.glColor4f(1.0f, 0.0f, 0.0f, 0.25f);
        }
        else {
            GL11.glColor4f(0.0f, 1.0f, 0.0f, 0.25f);
        }
        RenderUtils.drawSolidBox();
        if (hitEntity) {
            GL11.glColor4f(1.0f, 0.0f, 0.0f, 0.75f);
        }
        else {
            GL11.glColor4f(0.0f, 1.0f, 0.0f, 0.75f);
        }
        RenderUtils.drawOutlinedBox();
        GL11.glPopMatrix();
        GL11.glDisable(3042);
        GL11.glEnable(3553);
        GL11.glEnable(2929);
        GL11.glDepthMask(true);
        GL11.glDisable(2848);
        GL11.glPopMatrix();
    }
    
    @Override
    public ModuleCategory getCategory() {
        return ModuleCategory.RENDER;
    }
    
    static {
        lineColor = new RenderColor(51, 196, 191, 128);
    }
}
