// 
// Decompiled by Procyon v0.5.36
// 

package net.daporkchop.pepsimod.util.colors;

import java.awt.Color;

public class ColorUtils
{
    public static final int BUTTON_OFF_OFF;
    public static final int BUTTON_ON_OFF;
    public static final int BUTTON_OFF_ON;
    public static final int BUTTON_ON_ON;
    public static final int WINDOW_ON;
    public static final int WINDOW_OFF;
    public static final int BACKGROUND;
    public static final int TYPE_BUTTON = 0;
    public static final int TYPE_WINDOW = 1;
    public static final int TYPE_SLIDER = 2;
    public static final int TYPE_BG = 3;
    
    public static int getColorForGuiEntry(final int type, final boolean hovered, final boolean state) {
        switch (type) {
            case 0: {
                if (hovered) {
                    return state ? ColorUtils.BUTTON_ON_ON : ColorUtils.BUTTON_ON_OFF;
                }
                return state ? ColorUtils.BUTTON_OFF_ON : ColorUtils.BUTTON_OFF_OFF;
            }
            case 1: {
                return hovered ? ColorUtils.WINDOW_ON : ColorUtils.WINDOW_OFF;
            }
            case 2: {
                return hovered ? ColorUtils.BUTTON_ON_ON : ColorUtils.BUTTON_OFF_ON;
            }
            default: {
                throw new IllegalStateException("Invalid type: " + type);
            }
        }
    }
    
    static {
        BUTTON_OFF_OFF = new Color(255, 32, 32).getRGB();
        BUTTON_ON_OFF = new Color(244, 66, 66).getRGB();
        BUTTON_OFF_ON = new Color(43, 120, 255).getRGB();
        BUTTON_ON_ON = new Color(88, 143, 239).getRGB();
        WINDOW_ON = new Color(255, 255, 255).getRGB();
        WINDOW_OFF = new Color(183, 183, 183).getRGB();
        BACKGROUND = new Color(128, 128, 128).getRGB();
    }
}
