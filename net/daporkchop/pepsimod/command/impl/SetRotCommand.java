// 
// Decompiled by Procyon v0.5.36
// 

package net.daporkchop.pepsimod.command.impl;

import net.daporkchop.pepsimod.command.api.Command;

public class SetRotCommand extends Command
{
    public SetRotCommand() {
        super("setrot");
    }
    
    @Override
    public void execute(final String cmd, final String[] args) {
        if (args.length < 3) {
            Command.clientMessage("Usage: .setrot <yaw> <pitch>");
            return;
        }
        try {
            final float yaw = Float.parseFloat(args[1]);
            final float pitch = Float.parseFloat(args[2]);
            SetRotCommand.mc.player.setPositionAndRotation(SetRotCommand.mc.player.posX, SetRotCommand.mc.player.posY, SetRotCommand.mc.player.posZ, yaw, pitch);
            Command.clientMessage("Set rotation to yaw: " + yaw + " pitch: " + pitch);
        }
        catch (NumberFormatException e) {
            Command.clientMessage("Invalid arguemnts!");
        }
    }
    
    @Override
    public String getSuggestion(final String cmd, final String[] args) {
        switch (args.length) {
            case 1: {
                return ".setrot 0 0";
            }
            case 2: {
                return ".setrot " + args[1] + ((args[1].length() == 0) ? "0 0" : " 0");
            }
            default: {
                return ".setrot";
            }
        }
    }
}
