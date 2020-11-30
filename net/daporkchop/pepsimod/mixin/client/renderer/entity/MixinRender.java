// 
// Decompiled by Procyon v0.5.36
// 

package net.daporkchop.pepsimod.mixin.client.renderer.entity;

import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;
import net.daporkchop.pepsimod.util.RenderColor;
import net.daporkchop.pepsimod.misc.data.Group;
import net.daporkchop.pepsimod.util.config.impl.ESPTranslator;
import net.daporkchop.pepsimod.module.impl.render.ESPMod;
import net.daporkchop.pepsimod.util.PepsiConstants;
import org.spongepowered.asm.mixin.Overwrite;
import net.minecraft.client.renderer.EntityRenderer;
import net.daporkchop.pepsimod.util.PepsiUtils;
import net.daporkchop.pepsimod.util.config.impl.NameTagsTranslator;
import net.daporkchop.pepsimod.module.impl.render.NameTagsMod;
import net.daporkchop.pepsimod.module.impl.render.HealthTagsMod;
import net.daporkchop.pepsimod.util.config.impl.FriendsTranslator;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.client.gui.FontRenderer;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Shadow;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.entity.Render;
import org.spongepowered.asm.mixin.Mixin;
import net.minecraft.entity.Entity;

@Mixin({ Render.class })
public abstract class MixinRender<T extends Entity>
{
    @Shadow
    @Final
    protected RenderManager renderManager;
    
    @Shadow
    public FontRenderer getFontRendererFromRenderManager() {
        return null;
    }
    
    @Overwrite
    protected void renderLivingLabel(final T entityIn, String str, final double x, final double y, final double z, final int maxDistance) {
        final double d0 = entityIn.getDistanceSq(this.renderManager.renderViewEntity);
        if (d0 <= maxDistance * maxDistance) {
            final boolean flag = entityIn.isSneaking();
            final float f = this.renderManager.playerViewY;
            final float f2 = this.renderManager.playerViewX;
            final boolean flag2 = this.renderManager.options.thirdPersonView == 2;
            final float f3 = entityIn.height + 0.5f - (flag ? 0.25f : 0.0f);
            final int i = "deadmau5".equals(str) ? -10 : 0;
            if (entityIn instanceof EntityLivingBase) {
                if (entityIn instanceof EntityPlayer && FriendsTranslator.INSTANCE.isFriend(entityIn)) {
                    str = "§b" + str;
                }
                if (HealthTagsMod.INSTANCE.state.enabled) {
                    str += " ";
                    final int health = (int)((EntityLivingBase)entityIn).getHealth();
                    if (health <= 5) {
                        str += "§4";
                    }
                    else if (health <= 10) {
                        str += "§6";
                    }
                    else if (health <= 15) {
                        str += "§e";
                    }
                    else {
                        str += "§a";
                    }
                    str += health;
                }
            }
            if (NameTagsMod.INSTANCE.state.enabled) {
                PepsiUtils.drawNameplateNoScale(this.getFontRendererFromRenderManager(), str, (float)x, (float)y, (float)z, i, f, f2, flag2, f3, NameTagsTranslator.INSTANCE.scale);
            }
            else {
                EntityRenderer.drawNameplate(this.getFontRendererFromRenderManager(), str, (float)x, (float)y + f3, (float)z, i, f, f2, flag2, flag);
            }
        }
    }
    
    @ModifyConstant(method = { "Lnet/minecraft/client/renderer/entity/Render;getTeamColor(Lnet/minecraft/entity/Entity;)I" }, constant = { @Constant(intValue = 16777215) })
    public int changeDefaultTeamColor(final int old, final Entity entity) {
        if (entity instanceof EntityPlayer) {
            final Group group = PepsiConstants.pepsimod.data.getGroup((EntityPlayer)entity);
            if (group != null && group.color != 0) {
                return group.color;
            }
        }
        if (ESPMod.INSTANCE.state.enabled && !ESPTranslator.INSTANCE.box) {
            final RenderColor color = ESPMod.INSTANCE.chooseColor(entity);
            if (color != null) {
                return color.getIntColor();
            }
        }
        return old;
    }
}
