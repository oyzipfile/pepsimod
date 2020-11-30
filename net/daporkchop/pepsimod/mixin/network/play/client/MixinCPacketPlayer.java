// 
// Decompiled by Procyon v0.5.36
// 

package net.daporkchop.pepsimod.mixin.network.play.client;

import org.spongepowered.asm.mixin.Overwrite;
import java.io.IOException;
import net.daporkchop.pepsimod.module.impl.misc.AntiHungerMod;
import net.daporkchop.pepsimod.module.impl.misc.NoFallMod;
import net.minecraft.network.PacketBuffer;
import org.spongepowered.asm.mixin.Shadow;
import net.minecraft.network.play.client.CPacketPlayer;
import org.spongepowered.asm.mixin.Mixin;

@Mixin({ CPacketPlayer.class })
public abstract class MixinCPacketPlayer
{
    @Shadow
    protected boolean onGround;
    
    @Overwrite
    public void writePacketData(final PacketBuffer buf) throws IOException {
        if (NoFallMod.NO_FALL && AntiHungerMod.ANTI_HUNGER) {
            buf.writeByte((int)(this.onGround ? 0 : 1));
        }
        else if (NoFallMod.NO_FALL) {
            buf.writeByte(1);
        }
        else if (AntiHungerMod.ANTI_HUNGER) {
            buf.writeByte(0);
        }
        else {
            buf.writeByte((int)(this.onGround ? 1 : 0));
        }
    }
}
