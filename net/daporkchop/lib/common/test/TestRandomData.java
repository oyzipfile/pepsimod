// 
// Decompiled by Procyon v0.5.36
// 

package net.daporkchop.lib.common.test;

import java.util.concurrent.ThreadLocalRandom;

public class TestRandomData
{
    public static final byte[][] randomBytes;
    
    public static byte[] getRandomBytes(final int minLen, final int maxLen) {
        final ThreadLocalRandom r = ThreadLocalRandom.current();
        final byte[] b = new byte[r.nextInt(minLen, maxLen)];
        r.nextBytes(b);
        return b;
    }
    
    static {
        randomBytes = new byte[32][];
        final ThreadLocalRandom r = ThreadLocalRandom.current();
        for (int i = TestRandomData.randomBytes.length - 1; i >= 0; --i) {
            r.nextBytes(TestRandomData.randomBytes[i] = new byte[r.nextInt(1024, 8192)]);
        }
    }
}
