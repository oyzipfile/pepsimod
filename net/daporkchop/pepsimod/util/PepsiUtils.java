// 
// Decompiled by Procyon v0.5.36
// 

package net.daporkchop.pepsimod.util;

import java.io.InputStream;
import java.io.IOException;
import javax.imageio.ImageIO;
import net.minecraftforge.fml.client.FMLClientHandler;
import net.daporkchop.pepsimod.util.config.impl.GeneralTranslator;
import net.minecraft.client.gui.GuiDisconnected;
import java.util.concurrent.ThreadLocalRandom;
import java.util.TimerTask;
import net.daporkchop.pepsimod.Pepsimod;
import java.util.Iterator;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemArmor;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.EnumFacing;
import net.minecraft.item.Item;
import net.minecraft.item.ItemFishingRod;
import net.minecraft.item.ItemLingeringPotion;
import net.minecraft.item.ItemSplashPotion;
import net.minecraft.item.ItemEnderPearl;
import net.minecraft.item.ItemEgg;
import net.minecraft.item.ItemSnowball;
import net.minecraft.item.ItemBow;
import net.minecraft.item.ItemStack;
import org.lwjgl.opengl.GL11;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.client.renderer.Vector3d;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.AxisAlignedBB;
import net.daporkchop.pepsimod.optimization.BlockID;
import net.minecraft.block.Block;
import net.minecraft.util.math.Vec3d;
import net.daporkchop.pepsimod.util.config.impl.TargettingTranslator;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.Entity;
import net.daporkchop.pepsimod.util.colors.rainbow.ColorChangeType;
import net.daporkchop.pepsimod.util.colors.GradientText;
import net.minecraft.client.Minecraft;
import org.apache.commons.lang3.ArrayUtils;
import net.daporkchop.pepsimod.util.colors.FixedColorElement;
import net.daporkchop.pepsimod.util.colors.ColorizedText;
import net.minecraft.client.gui.GuiButton;
import net.daporkchop.pepsimod.util.misc.IWurstRenderListener;
import java.util.ArrayList;
import net.daporkchop.pepsimod.util.colors.rainbow.RainbowText;
import java.awt.Color;
import net.daporkchop.pepsimod.util.colors.rainbow.RainbowCycle;
import java.awt.image.BufferedImage;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.client.multiplayer.ServerData;
import java.util.Timer;

public class PepsiUtils extends PepsiConstants
{
    public static final char COLOR_ESCAPE = '§';
    public static final String[] colorCodes;
    public static final Timer timer;
    public static final ServerData TOOBEETOOTEE_DATA;
    public static final KeyBinding[] controls;
    public static final BufferedImage PEPSI_LOGO;
    public static String buttonPrefix;
    public static RainbowCycle rainbowCycle;
    public static Color RAINBOW_COLOR;
    public static RainbowText PEPSI_NAME;
    public static ArrayList<IWurstRenderListener> wurstRenderListeners;
    public static ArrayList<IWurstRenderListener> toRemoveWurstRenderListeners;
    public static GuiButton reconnectButton;
    public static GuiButton autoReconnectButton;
    public static int autoReconnectWaitTime;
    public static String lastIp;
    public static int lastPort;
    
    public static ColorizedText getGradientFromStringThroughColor(final String text, final Color color1, final Color color2, final Color through) {
        final int charCount = text.length();
        final String[] letters = text.split("");
        final int colorCountPart1 = Math.floorDiv(charCount, 2);
        final int colorCountPart2 = ceilDiv(charCount, 2);
        final Color[] colorsPart1 = new Color[colorCountPart1];
        final Color[] colorsPart2 = new Color[colorCountPart2];
        int rDiffStep = (color1.getRed() - through.getRed()) / colorCountPart1;
        int gDiffStep = (color1.getGreen() - through.getGreen()) / colorCountPart1;
        int bDiffStep = (color1.getBlue() - through.getBlue()) / colorCountPart1;
        for (int i = 0; i < colorCountPart1; ++i) {
            colorsPart1[i] = new Color(ensureRange(color1.getRed() + i * rDiffStep * -1, 0, 255), ensureRange(color1.getGreen() + i * gDiffStep * -1, 0, 255), ensureRange(color1.getBlue() + i * bDiffStep * -1, 0, 255));
        }
        rDiffStep = (through.getRed() - color2.getRed()) / colorCountPart2;
        gDiffStep = (through.getGreen() - color2.getGreen()) / colorCountPart2;
        bDiffStep = (through.getBlue() - color2.getBlue()) / colorCountPart2;
        for (int i = 0; i < colorCountPart2; ++i) {
            colorsPart2[i] = new Color(ensureRange(through.getRed() + i * rDiffStep * -1, 0, 255), ensureRange(through.getGreen() + i * gDiffStep * -1, 0, 255), ensureRange(through.getBlue() + i * bDiffStep * -1, 0, 255));
        }
        final FixedColorElement[] elements = new FixedColorElement[charCount];
        final Color[] merged = (Color[])ArrayUtils.addAll((Object[])colorsPart1, (Object[])colorsPart2);
        for (int j = 0; j < charCount; ++j) {
            elements[j] = new FixedColorElement(merged[j].getRGB(), letters[j]);
        }
        return new GradientText(elements, Minecraft.getMinecraft().fontRenderer.getStringWidth(text));
    }
    
    public static int ceilDiv(final int x, final int y) {
        return Math.floorDiv(x, y) + ((x % y != 0) ? 1 : 0);
    }
    
    public static int ensureRange(final int value, final int min, final int max) {
        final int toReturn = Math.min(Math.max(value, min), max);
        return toReturn;
    }
    
    public static RainbowCycle rainbowCycle(final int count, final RainbowCycle toRunOn) {
        for (int i = 0; i < count; ++i) {
            if (toRunOn.red == ColorChangeType.INCREASE) {
                toRunOn.r += 4;
                if (toRunOn.r > 255) {
                    toRunOn.red = ColorChangeType.DECRASE;
                    toRunOn.green = ColorChangeType.INCREASE;
                }
            }
            else if (toRunOn.red == ColorChangeType.DECRASE) {
                toRunOn.r -= 4;
                if (toRunOn.r == 0) {
                    toRunOn.red = ColorChangeType.NONE;
                }
            }
            if (toRunOn.green == ColorChangeType.INCREASE) {
                toRunOn.g += 4;
                if (toRunOn.g > 255) {
                    toRunOn.green = ColorChangeType.DECRASE;
                    toRunOn.blue = ColorChangeType.INCREASE;
                }
            }
            else if (toRunOn.green == ColorChangeType.DECRASE) {
                toRunOn.g -= 4;
                if (toRunOn.g == 0) {
                    toRunOn.green = ColorChangeType.NONE;
                }
            }
            if (toRunOn.blue == ColorChangeType.INCREASE) {
                toRunOn.b += 4;
                if (toRunOn.b > 255) {
                    toRunOn.blue = ColorChangeType.DECRASE;
                    toRunOn.red = ColorChangeType.INCREASE;
                }
            }
            else if (toRunOn.blue == ColorChangeType.DECRASE) {
                toRunOn.b -= 4;
                if (toRunOn.b == 0) {
                    toRunOn.blue = ColorChangeType.NONE;
                }
            }
        }
        return toRunOn;
    }
    
    public static RainbowCycle rainbowCycleBackwards(final int count, final RainbowCycle toRunOn) {
        for (int i = 0; i < count; ++i) {
            if (toRunOn.red == ColorChangeType.INCREASE) {
                toRunOn.r -= 8;
                if (toRunOn.r == 0) {
                    toRunOn.red = ColorChangeType.NONE;
                }
            }
            else if (toRunOn.red == ColorChangeType.DECRASE) {
                toRunOn.r += 8;
                if (toRunOn.r > 255) {
                    toRunOn.red = ColorChangeType.INCREASE;
                    toRunOn.green = ColorChangeType.DECRASE;
                }
            }
            if (toRunOn.green == ColorChangeType.INCREASE) {
                toRunOn.g -= 8;
                if (toRunOn.g == 0) {
                    toRunOn.green = ColorChangeType.NONE;
                }
            }
            else if (toRunOn.green == ColorChangeType.DECRASE) {
                toRunOn.g += 8;
                if (toRunOn.g > 255) {
                    toRunOn.green = ColorChangeType.INCREASE;
                    toRunOn.blue = ColorChangeType.DECRASE;
                }
            }
            if (toRunOn.blue == ColorChangeType.INCREASE) {
                toRunOn.b -= 8;
                if (toRunOn.b == 0) {
                    toRunOn.blue = ColorChangeType.NONE;
                }
            }
            else if (toRunOn.blue == ColorChangeType.DECRASE) {
                toRunOn.b += 8;
                if (toRunOn.b > 255) {
                    toRunOn.blue = ColorChangeType.INCREASE;
                    toRunOn.red = ColorChangeType.DECRASE;
                }
            }
        }
        return toRunOn;
    }
    
    public static boolean canEntityBeSeen(final Entity entityIn, final EntityPlayer player, final TargettingTranslator.TargetBone bone) {
        return entityIn.world.rayTraceBlocks(new Vec3d(player.posX, player.posY + player.getEyeHeight(), player.posZ), new Vec3d(entityIn.posX, getTargetHeight(entityIn, bone), entityIn.posZ), false, true, false) == null;
    }
    
    public static double getTargetHeight(final Entity entity, final TargettingTranslator.TargetBone bone) {
        double targetHeight = entity.posY;
        if (bone == TargettingTranslator.TargetBone.HEAD) {
            targetHeight = entity.getEyeHeight();
        }
        else if (bone == TargettingTranslator.TargetBone.MIDDLE) {
            targetHeight = entity.getEyeHeight() / 2.0f;
        }
        return targetHeight;
    }
    
    public static Vec3d adjustVectorForBone(final Vec3d vec3d, final Entity entity, final TargettingTranslator.TargetBone bone) {
        ReflectionStuff.setY_vec3d(vec3d, getTargetHeight(entity, bone));
        return vec3d;
    }
    
    public static void setBlockIdFields() {
        Block.REGISTRY.forEach(block -> block.internal_setBlockId(Block.REGISTRY.getIDForObject((Object)block)));
    }
    
    public static AxisAlignedBB cloneBB(final AxisAlignedBB bb) {
        return new AxisAlignedBB(bb.minX, bb.minY, bb.minZ, bb.maxX, bb.maxY, bb.maxZ);
    }
    
    public static AxisAlignedBB offsetBB(final AxisAlignedBB bb, final BlockPos pos) {
        ReflectionStuff.setMinX(bb, ReflectionStuff.getMinX(bb) + pos.getX());
        ReflectionStuff.setMinY(bb, ReflectionStuff.getMinY(bb) + pos.getY());
        ReflectionStuff.setMinZ(bb, ReflectionStuff.getMinZ(bb) + pos.getZ());
        ReflectionStuff.setMaxX(bb, ReflectionStuff.getMaxX(bb) + pos.getX());
        ReflectionStuff.setMaxY(bb, ReflectionStuff.getMaxY(bb) + pos.getY());
        ReflectionStuff.setMaxZ(bb, ReflectionStuff.getMaxZ(bb) + pos.getZ());
        return bb;
    }
    
    public static AxisAlignedBB unionBB(final AxisAlignedBB bb1, final AxisAlignedBB bb2) {
        ReflectionStuff.setMinX(bb1, Math.min(ReflectionStuff.getMinX(bb1), ReflectionStuff.getMinX(bb2)));
        ReflectionStuff.setMinY(bb1, Math.min(ReflectionStuff.getMinY(bb1), ReflectionStuff.getMinY(bb2)));
        ReflectionStuff.setMinZ(bb1, Math.min(ReflectionStuff.getMinZ(bb1), ReflectionStuff.getMinZ(bb2)));
        ReflectionStuff.setMaxX(bb1, Math.min(ReflectionStuff.getMaxX(bb1), ReflectionStuff.getMaxX(bb2)));
        ReflectionStuff.setMaxY(bb1, Math.min(ReflectionStuff.getMaxY(bb1), ReflectionStuff.getMaxY(bb2)));
        ReflectionStuff.setMaxZ(bb1, Math.min(ReflectionStuff.getMaxZ(bb1), ReflectionStuff.getMaxZ(bb2)));
        return bb1;
    }
    
    public static Vector3d sub(final Vector3d in, final Vector3d with) {
        in.x -= with.x;
        in.y -= with.y;
        in.z -= with.z;
        return in;
    }
    
    public static Vec3d getInterpolatedAmount(final Entity entity, final double x, final double y, final double z) {
        return new Vec3d((entity.posX - entity.lastTickPosX) * x, (entity.posY - entity.lastTickPosY) * y, (entity.posZ - entity.lastTickPosZ) * z);
    }
    
    public static Vec3d getInterpolatedAmount(final Entity entity, final double ticks) {
        return getInterpolatedAmount(entity, ticks, ticks, ticks);
    }
    
    public static void copyPlayerModel(final EntityPlayer from, final EntityPlayer to) {
        to.getDataManager().set((DataParameter)ReflectionStuff.getPLAYER_MODEL_FLAG(), from.getDataManager().get((DataParameter)ReflectionStuff.getPLAYER_MODEL_FLAG()));
    }
    
    public static void glColor(final RenderColor color) {
        GL11.glColor4b(color.r, color.g, color.b, color.a);
    }
    
    public static void glColor(final Color color) {
        RenderColor.glColor(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha());
    }
    
    public static boolean isThrowable(final ItemStack stack) {
        final Item item = stack.getItem();
        return item instanceof ItemBow || item instanceof ItemSnowball || item instanceof ItemEgg || item instanceof ItemEnderPearl || item instanceof ItemSplashPotion || item instanceof ItemLingeringPotion || item instanceof ItemFishingRod;
    }
    
    public static float round(final float input, final float step) {
        return Math.round(input / step) * step;
    }
    
    public static float ensureRange(final float value, final float min, final float max) {
        final float toReturn = Math.min(Math.max(value, min), max);
        return toReturn;
    }
    
    public static String roundFloatForSlider(final float f) {
        return String.format("%.2f", f);
    }
    
    public static String roundCoords(final double d) {
        return String.format("%.2f", d);
    }
    
    public static String getFacing() {
        final Entity entity = PepsiUtils.mc.getRenderViewEntity();
        final EnumFacing enumfacing = entity.getHorizontalFacing();
        String s = "Invalid";
        switch (enumfacing) {
            case NORTH: {
                s = "-Z";
                break;
            }
            case SOUTH: {
                s = "+Z";
                break;
            }
            case WEST: {
                s = "-X";
                break;
            }
            case EAST: {
                s = "+X";
                break;
            }
        }
        return s;
    }
    
    public static void renderItem(final int x, final int y, final float partialTicks, final EntityPlayer player, final ItemStack stack) {
        if (!stack.isEmpty()) {
            GlStateManager.pushMatrix();
            RenderHelper.enableGUIStandardItemLighting();
            GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
            try {
                GlStateManager.translate(0.0f, 0.0f, 32.0f);
                PepsiUtils.mc.getRenderItem().zLevel = 200.0f;
                PepsiUtils.mc.getRenderItem().renderItemAndEffectIntoGUI(stack, x, y);
                PepsiUtils.mc.getRenderItem().renderItemOverlayIntoGUI(PepsiUtils.mc.fontRenderer, stack, x, y, "");
                PepsiUtils.mc.getRenderItem().zLevel = 0.0f;
            }
            catch (Exception e) {
                e.printStackTrace();
            }
            RenderHelper.disableStandardItemLighting();
            GlStateManager.popMatrix();
        }
    }
    
    public static ItemStack getWearingArmor(final int armorType) {
        return PepsiUtils.mc.player.inventoryContainer.getSlot(5 + armorType).getStack();
    }
    
    public static void drawNameplateNoScale(final FontRenderer fontRendererIn, final String str, final float x, final float y, final float z, final int verticalShift, final float viewerYaw, final float viewerPitch, final boolean isThirdPersonFrontal, final float offset, float size) {
        GlStateManager.pushMatrix();
        final double dist = new Vec3d((double)x, (double)(y + offset), (double)z).length();
        GlStateManager.translate(x, y + offset, z);
        GlStateManager.glNormal3f(0.0f, 1.0f, 0.0f);
        GlStateManager.rotate(-viewerYaw, 0.0f, 1.0f, 0.0f);
        GlStateManager.rotate((isThirdPersonFrontal ? -1 : 1) * viewerPitch, 1.0f, 0.0f, 0.0f);
        size *= (float)(dist * 0.3);
        GlStateManager.scale(-0.025f * size, -0.025f * size, 0.025f * size);
        GlStateManager.disableLighting();
        GlStateManager.depthMask(false);
        GlStateManager.disableDepth();
        GlStateManager.enableBlend();
        GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
        final int i = fontRendererIn.getStringWidth(str) / 2;
        GlStateManager.disableTexture2D();
        final Tessellator tessellator = Tessellator.getInstance();
        final BufferBuilder bufferbuilder = tessellator.getBuffer();
        bufferbuilder.begin(7, DefaultVertexFormats.POSITION_COLOR);
        bufferbuilder.pos((double)(-i - 1), (double)(-8 + verticalShift), 0.0).color(0.0f, 0.0f, 0.0f, 0.25f).endVertex();
        bufferbuilder.pos((double)(-i - 1), (double)(1 + verticalShift), 0.0).color(0.0f, 0.0f, 0.0f, 0.25f).endVertex();
        bufferbuilder.pos((double)(i + 1), (double)(1 + verticalShift), 0.0).color(0.0f, 0.0f, 0.0f, 0.25f).endVertex();
        bufferbuilder.pos((double)(i + 1), (double)(-8 + verticalShift), 0.0).color(0.0f, 0.0f, 0.0f, 0.25f).endVertex();
        tessellator.draw();
        GlStateManager.enableTexture2D();
        int color = 553648127;
        color = -1;
        GlStateManager.enableDepth();
        GlStateManager.depthMask(true);
        fontRendererIn.drawString(str, -fontRendererIn.getStringWidth(str) / 2, verticalShift - 7, color);
        GlStateManager.enableLighting();
        GlStateManager.disableBlend();
        GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
        GlStateManager.popMatrix();
    }
    
    public static void renderFloatingText(final String text, final float x, final float y, final float z, final int color, final boolean renderBlackBackground, final float scale) {
        drawNameplateNoScale(PepsiUtils.mc.fontRenderer, text, (float)(x - ReflectionStuff.getRenderPosX(PepsiUtils.mc.getRenderManager())), (float)(y - ReflectionStuff.getRenderPosY(PepsiUtils.mc.getRenderManager())), (float)(z - ReflectionStuff.getRenderPosZ(PepsiUtils.mc.getRenderManager())), 0, PepsiUtils.mc.getRenderManager().playerViewY, PepsiUtils.mc.getRenderManager().playerViewX, false, 0.0f, scale);
    }
    
    public static void renderFloatingItemIcon(final float x, final float y, final float z, final Item item, final float partialTickTime) {
        final RenderManager renderManager = PepsiUtils.mc.getRenderManager();
        final float playerX = (float)(PepsiUtils.mc.player.lastTickPosX + (PepsiUtils.mc.player.posX - PepsiUtils.mc.player.lastTickPosX) * partialTickTime);
        final float playerY = (float)(PepsiUtils.mc.player.lastTickPosY + (PepsiUtils.mc.player.posY - PepsiUtils.mc.player.lastTickPosY) * partialTickTime);
        final float playerZ = (float)(PepsiUtils.mc.player.lastTickPosZ + (PepsiUtils.mc.player.posZ - PepsiUtils.mc.player.lastTickPosZ) * partialTickTime);
        final float dx = x - playerX;
        final float dy = y - playerY;
        final float dz = z - playerZ;
        final float scale = 0.025f;
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 0.75f);
        GL11.glPushMatrix();
        GL11.glTranslatef(dx, dy, dz);
        GL11.glRotatef(-renderManager.playerViewY, 0.0f, 1.0f, 0.0f);
        GL11.glRotatef(renderManager.playerViewX, 1.0f, 0.0f, 0.0f);
        GL11.glScalef(-scale, -scale, scale);
        GL11.glDisable(2896);
        GL11.glDepthMask(false);
        GL11.glDisable(2929);
        GL11.glEnable(3042);
        GL11.glBlendFunc(770, 771);
        renderItemTexture(-8, -8, item, 16, 16);
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        GL11.glDepthMask(true);
        GL11.glEnable(2929);
        GL11.glPopMatrix();
    }
    
    public static void renderItemTexture(final int x, final int y, final Item item, final int width, final int height) {
        final IBakedModel iBakedModel = PepsiUtils.mc.getRenderItem().getItemModelMesher().getItemModel(new ItemStack(item));
        final TextureAtlasSprite textureAtlasSprite = PepsiUtils.mc.getTextureMapBlocks().getAtlasSprite(iBakedModel.getParticleTexture().getIconName());
        PepsiUtils.mc.getTextureManager().bindTexture(TextureMap.LOCATION_BLOCKS_TEXTURE);
        renderTexture(x, y, textureAtlasSprite, width, height, 0.0);
    }
    
    private static void renderTexture(final int x, final int y, final TextureAtlasSprite textureAtlasSprite, final int width, final int height, final double zLevel) {
        final Tessellator tessellator = Tessellator.getInstance();
        final BufferBuilder worldrenderer = tessellator.getBuffer();
        worldrenderer.begin(7, DefaultVertexFormats.POSITION_COLOR);
        worldrenderer.pos((double)x, (double)(y + height), zLevel).tex((double)textureAtlasSprite.getMaxU(), (double)textureAtlasSprite.getMaxV()).endVertex();
        worldrenderer.pos((double)(x + width), (double)(y + height), zLevel).tex((double)textureAtlasSprite.getMinU(), (double)textureAtlasSprite.getMaxV()).endVertex();
        worldrenderer.pos((double)(x + width), (double)y, zLevel).tex((double)textureAtlasSprite.getMinU(), (double)textureAtlasSprite.getMinV()).endVertex();
        worldrenderer.pos((double)x, (double)y, zLevel).tex((double)textureAtlasSprite.getMaxU(), (double)textureAtlasSprite.getMinV()).endVertex();
        tessellator.draw();
    }
    
    public static int getBestTool(final Block block) {
        float best = -1.0f;
        int index = -1;
        for (int i = 0; i < 9; ++i) {
            final ItemStack itemStack = PepsiUtils.mc.player.inventory.getStackInSlot(i);
            if (itemStack != null) {
                final float str = itemStack.getItem().getDestroySpeed(itemStack, block.getDefaultState());
                if (str > best) {
                    best = str;
                    index = i;
                }
            }
        }
        return index;
    }
    
    public static double getDimensionCoord(final double coord) {
        return (PepsiUtils.mc.player.dimension == 0) ? (coord / 8.0) : (coord * 8.0);
    }
    
    public static int getArmorType(final ItemArmor armor) {
        return armor.armorType.ordinal() - 2;
    }
    
    public static double[] interpolate(final Entity entity) {
        final double partialTicks = ReflectionStuff.getTimer().renderPartialTicks;
        final double[] pos = { entity.lastTickPosX + (entity.posX - entity.lastTickPosX) * partialTicks, entity.lastTickPosY + (entity.posY - entity.lastTickPosY) * partialTicks, entity.lastTickPosZ + (entity.posZ - entity.lastTickPosZ) * partialTicks };
        return pos;
    }
    
    public static boolean isAttackable(final EntityLivingBase entity) {
        return entity != null && entity != PepsiUtils.mc.player && entity.isEntityAlive();
    }
    
    public static EntityLivingBase getClosestEntityWithoutReachFactor() {
        EntityLivingBase closestEntity = null;
        double distance = 9999.0;
        for (final Object object : PepsiUtils.mc.world.loadedEntityList) {
            if (object instanceof EntityLivingBase) {
                final EntityLivingBase entity = (EntityLivingBase)object;
                if (!isAttackable(entity)) {
                    continue;
                }
                final double newDistance = PepsiUtils.mc.player.getDistanceSq((Entity)entity);
                if (closestEntity != null) {
                    if (distance <= newDistance) {
                        continue;
                    }
                    closestEntity = entity;
                    distance = newDistance;
                }
                else {
                    closestEntity = entity;
                    distance = newDistance;
                }
            }
        }
        return closestEntity;
    }
    
    public static boolean isControlsPressed() {
        for (final KeyBinding keyBinding : PepsiUtils.controls) {
            if (ReflectionStuff.getPressed(keyBinding)) {
                return true;
            }
        }
        return false;
    }
    
    public static void drawRect(final float paramXStart, final float paramYStart, final float paramXEnd, final float paramYEnd, final int paramColor) {
        final float alpha = (paramColor >> 24 & 0xFF) / 255.0f;
        final float red = (paramColor >> 16 & 0xFF) / 255.0f;
        final float green = (paramColor >> 8 & 0xFF) / 255.0f;
        final float blue = (paramColor & 0xFF) / 255.0f;
        GL11.glPushMatrix();
        GL11.glEnable(3042);
        GL11.glDisable(3553);
        GL11.glBlendFunc(770, 771);
        GL11.glEnable(2848);
        GL11.glColor4f(red, green, blue, alpha);
        GL11.glBegin(7);
        GL11.glVertex2d((double)paramXEnd, (double)paramYStart);
        GL11.glVertex2d((double)paramXStart, (double)paramYStart);
        GL11.glVertex2d((double)paramXStart, (double)paramYEnd);
        GL11.glVertex2d((double)paramXEnd, (double)paramYEnd);
        GL11.glEnd();
        GL11.glEnable(3553);
        GL11.glDisable(3042);
        GL11.glDisable(2848);
        GL11.glPopMatrix();
    }
    
    public static Vec3d getPlayerPos(final float partialTicks) {
        return getEntityPos(partialTicks, (Entity)PepsiUtils.mc.player);
    }
    
    public static Vec3d getEntityPos(final float partialTicks, final Entity entity) {
        if (partialTicks == 1.0f) {
            return new Vec3d(entity.posX, entity.posY, entity.posZ);
        }
        final double x = entity.prevPosX + (entity.posX - entity.prevPosX) * partialTicks;
        final double y = entity.prevPosY + (entity.posY - entity.prevPosY) * partialTicks;
        final double z = entity.prevPosZ + (entity.posZ - entity.prevPosZ) * partialTicks;
        return new Vec3d(x, y, z);
    }
    
    static {
        colorCodes = new String[] { "c", "9", "f", "1", "4" };
        timer = new Timer();
        TOOBEETOOTEE_DATA = new ServerData("toobeetootee", "2b2t.org", false);
        controls = new KeyBinding[] { PepsiUtils.mc.gameSettings.keyBindForward, PepsiUtils.mc.gameSettings.keyBindBack, PepsiUtils.mc.gameSettings.keyBindRight, PepsiUtils.mc.gameSettings.keyBindLeft, PepsiUtils.mc.gameSettings.keyBindJump, PepsiUtils.mc.gameSettings.keyBindSneak };
        PepsiUtils.buttonPrefix = "§c";
        PepsiUtils.rainbowCycle = new RainbowCycle();
        PepsiUtils.RAINBOW_COLOR = new Color(0, 0, 0);
        PepsiUtils.PEPSI_NAME = new RainbowText(Pepsimod.NAME_VERSION);
        PepsiUtils.wurstRenderListeners = new ArrayList<IWurstRenderListener>();
        PepsiUtils.toRemoveWurstRenderListeners = new ArrayList<IWurstRenderListener>();
        PepsiUtils.autoReconnectWaitTime = 5;
        PepsiUtils.TOOBEETOOTEE_DATA.setResourceMode(ServerData.ServerResourceMode.PROMPT);
        PepsiUtils.timer.schedule(new TimerTask() {
            @Override
            public void run() {
                PepsiUtils.buttonPrefix = '§' + PepsiUtils.colorCodes[ThreadLocalRandom.current().nextInt(PepsiUtils.colorCodes.length)];
            }
        }, 1000L, 1000L);
        PepsiUtils.timer.schedule(new TimerTask() {
            @Override
            public void run() {
                if (PepsiConstants.mc.currentScreen != null && PepsiConstants.mc.currentScreen instanceof GuiDisconnected && PepsiUtils.autoReconnectButton != null && GeneralTranslator.INSTANCE.autoReconnect) {
                    PepsiUtils.autoReconnectButton.displayString = "AutoReconnect (§a" + --PepsiUtils.autoReconnectWaitTime + "§r)";
                    if (PepsiUtils.autoReconnectWaitTime <= 0) {
                        final ServerData data = new ServerData("", PepsiUtils.lastIp + ':' + PepsiUtils.lastPort, false);
                        data.setResourceMode(ServerData.ServerResourceMode.PROMPT);
                        PepsiConstants.mc.addScheduledTask(() -> FMLClientHandler.instance().connectToServer(PepsiConstants.mc.currentScreen, data));
                        PepsiUtils.autoReconnectWaitTime = 5;
                    }
                }
            }
        }, 1000L, 1000L);
        PepsiUtils.timer.schedule(new TimerTask() {
            @Override
            public void run() {
                if (PepsiUtils.rainbowCycle.red == ColorChangeType.INCREASE) {
                    final RainbowCycle rainbowCycle = PepsiUtils.rainbowCycle;
                    rainbowCycle.r += 4;
                    if (PepsiUtils.rainbowCycle.r > 255) {
                        PepsiUtils.rainbowCycle.red = ColorChangeType.DECRASE;
                        PepsiUtils.rainbowCycle.green = ColorChangeType.INCREASE;
                    }
                }
                else if (PepsiUtils.rainbowCycle.red == ColorChangeType.DECRASE) {
                    final RainbowCycle rainbowCycle2 = PepsiUtils.rainbowCycle;
                    rainbowCycle2.r -= 4;
                    if (PepsiUtils.rainbowCycle.r == 0) {
                        PepsiUtils.rainbowCycle.red = ColorChangeType.NONE;
                    }
                }
                if (PepsiUtils.rainbowCycle.green == ColorChangeType.INCREASE) {
                    final RainbowCycle rainbowCycle3 = PepsiUtils.rainbowCycle;
                    rainbowCycle3.g += 4;
                    if (PepsiUtils.rainbowCycle.g > 255) {
                        PepsiUtils.rainbowCycle.green = ColorChangeType.DECRASE;
                        PepsiUtils.rainbowCycle.blue = ColorChangeType.INCREASE;
                    }
                }
                else if (PepsiUtils.rainbowCycle.green == ColorChangeType.DECRASE) {
                    final RainbowCycle rainbowCycle4 = PepsiUtils.rainbowCycle;
                    rainbowCycle4.g -= 4;
                    if (PepsiUtils.rainbowCycle.g == 0) {
                        PepsiUtils.rainbowCycle.green = ColorChangeType.NONE;
                    }
                }
                if (PepsiUtils.rainbowCycle.blue == ColorChangeType.INCREASE) {
                    final RainbowCycle rainbowCycle5 = PepsiUtils.rainbowCycle;
                    rainbowCycle5.b += 4;
                    if (PepsiUtils.rainbowCycle.b > 255) {
                        PepsiUtils.rainbowCycle.blue = ColorChangeType.DECRASE;
                        PepsiUtils.rainbowCycle.red = ColorChangeType.INCREASE;
                    }
                }
                else if (PepsiUtils.rainbowCycle.blue == ColorChangeType.DECRASE) {
                    final RainbowCycle rainbowCycle6 = PepsiUtils.rainbowCycle;
                    rainbowCycle6.b -= 4;
                    if (PepsiUtils.rainbowCycle.b == 0) {
                        PepsiUtils.rainbowCycle.blue = ColorChangeType.NONE;
                    }
                }
                PepsiUtils.RAINBOW_COLOR = new Color(PepsiUtils.ensureRange(PepsiUtils.rainbowCycle.r, 0, 255), PepsiUtils.ensureRange(PepsiUtils.rainbowCycle.g, 0, 255), PepsiUtils.ensureRange(PepsiUtils.rainbowCycle.b, 0, 255));
            }
        }, 0L, 50L);
        BufferedImage pepsiLogo = null;
        try (final InputStream in = PepsiUtils.class.getResourceAsStream("/pepsilogo.png")) {
            pepsiLogo = ImageIO.read(in);
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
        finally {
            PEPSI_LOGO = pepsiLogo;
        }
    }
}
