// 
// Decompiled by Procyon v0.5.36
// 

package net.daporkchop.pepsimod.module.impl.combat;

import net.daporkchop.pepsimod.module.ModuleCategory;
import net.daporkchop.pepsimod.module.api.ModuleOption;
import net.daporkchop.pepsimod.the.wurst.pkg.name.RotationUtils;
import net.minecraft.item.ItemBow;
import net.daporkchop.pepsimod.the.wurst.pkg.name.RenderUtils;
import net.minecraft.util.math.AxisAlignedBB;
import java.awt.Color;
import org.lwjgl.opengl.GL11;
import net.daporkchop.pepsimod.util.ReflectionStuff;
import net.minecraft.entity.Entity;
import net.daporkchop.pepsimod.util.PepsiUtils;
import net.minecraft.client.gui.GuiIngame;
import net.minecraft.entity.EntityLivingBase;
import net.daporkchop.pepsimod.module.api.Module;

public class BowAimBotMod extends Module
{
    public static BowAimBotMod INSTANCE;
    public EntityLivingBase target;
    public float velocity;
    
    public BowAimBotMod() {
        super("BowAimBot");
        BowAimBotMod.INSTANCE = this;
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
    public void onRenderGUI(final float partialTicks, final int width, final int height, final GuiIngame gui) {
        if (this.velocity != -1.0f) {
            if (this.velocity > 0.0f) {
                gui.drawCenteredString(BowAimBotMod.mc.fontRenderer, "Ready!", width / 2, height / 2 - 20, 16777215);
            }
            else {
                gui.drawCenteredString(BowAimBotMod.mc.fontRenderer, "Charging...", width / 2, height / 2 - 20, 16740352);
            }
        }
    }
    
    @Override
    public void onRender(final float partialTicks) {
        if (this.target != null) {
            final double[] pos = PepsiUtils.interpolate((Entity)this.target);
            final double x = pos[0] - ReflectionStuff.getRenderPosX();
            final double y = pos[1] - ReflectionStuff.getRenderPosY();
            final double z = pos[2] - ReflectionStuff.getRenderPosZ();
            GL11.glPushMatrix();
            GL11.glTranslated(x, y, z);
            GL11.glRotatef(-this.target.rotationYaw, 0.0f, 1.0f, 0.0f);
            PepsiUtils.glColor(Color.RED);
            RenderUtils.drawOutlinedBox(new AxisAlignedBB(this.target.width / 2.0, 0.0, -(this.target.width / 2.0), -this.target.width / 2.0, this.target.height + 0.1, this.target.width / 2.0));
            GL11.glPopMatrix();
        }
        this.target = null;
        if (BowAimBotMod.mc.player.inventory.getCurrentItem() != null && BowAimBotMod.mc.player.inventory.getCurrentItem().getItem() instanceof ItemBow && BowAimBotMod.mc.gameSettings.keyBindUseItem.isKeyDown()) {
            this.target = PepsiUtils.getClosestEntityWithoutReachFactor();
            this.aimAtTarget();
            return;
        }
        this.velocity = -1.0f;
    }
    
    private void aimAtTarget() {
        if (this.target == null) {
            return;
        }
        this.velocity = (72000 - BowAimBotMod.mc.player.getItemInUseCount()) / 20.0f;
        this.velocity = (this.velocity * this.velocity + this.velocity * 2.0f) / 3.0f;
        if (this.velocity > 1.0f) {
            this.velocity = 1.0f;
        }
        if (this.velocity < 0.1) {
            if (this.target instanceof EntityLivingBase) {
                RotationUtils.faceEntityClient((Entity)this.target);
                RotationUtils.faceEntityPacket((Entity)this.target);
            }
            return;
        }
        if (this.velocity > 1.0f) {
            this.velocity = 1.0f;
        }
        final double posX = this.target.posX - BowAimBotMod.mc.player.posX;
        final double posY = this.target.posY + this.target.getEyeHeight() - 0.15 - BowAimBotMod.mc.player.posY - BowAimBotMod.mc.player.getEyeHeight();
        final double posZ = this.target.posZ - BowAimBotMod.mc.player.posZ;
        final float yaw = (float)(Math.atan2(posZ, posX) * 180.0 / 3.141592653589793) - 90.0f;
        final double y2 = Math.sqrt(posX * posX + posZ * posZ);
        final float g = 0.006f;
        final float tmp = (float)(this.velocity * this.velocity * this.velocity * this.velocity - g * (g * (y2 * y2) + 2.0 * posY * (this.velocity * this.velocity)));
        final float pitch = (float)(-Math.toDegrees(Math.atan((this.velocity * this.velocity - Math.sqrt(tmp)) / (g * y2))));
        BowAimBotMod.mc.player.rotationYaw = yaw;
        BowAimBotMod.mc.player.rotationPitch = pitch;
    }
    
    @Override
    public void init() {
        BowAimBotMod.INSTANCE = this;
    }
    
    public ModuleOption[] getDefaultOptions() {
        return new ModuleOption[0];
    }
    
    @Override
    public ModuleCategory getCategory() {
        return ModuleCategory.COMBAT;
    }
}
