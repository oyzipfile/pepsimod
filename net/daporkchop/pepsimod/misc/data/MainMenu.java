// 
// Decompiled by Procyon v0.5.36
// 

package net.daporkchop.pepsimod.misc.data;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;
import java.util.Objects;
import net.daporkchop.pepsimod.util.render.Texture;

public class MainMenu implements AutoCloseable
{
    protected String[] splashes;
    public Texture banner;
    
    public void setup(final String[] splashes, final Texture banner) {
        if (this.banner != null) {
            this.close();
        }
        this.splashes = Objects.requireNonNull(splashes, "splashes");
        this.banner = Objects.requireNonNull(banner, "banner");
    }
    
    public String getRandomSplash() {
        final Random r = ThreadLocalRandom.current();
        final String[] colors = { "§1", "§9", "§a", "§b", "§c", "§f" };
        return colors[r.nextInt(colors.length)] + this.splashes[r.nextInt(this.splashes.length)];
    }
    
    @Override
    public void close() {
        this.banner.close();
        this.splashes = null;
    }
}
