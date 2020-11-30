// 
// Decompiled by Procyon v0.5.36
// 

package net.daporkchop.pepsimod.command.impl;

import java.util.Iterator;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.client.gui.inventory.GuiInventory;
import net.minecraft.client.entity.EntityOtherPlayerMP;
import net.minecraft.entity.Entity;
import net.daporkchop.pepsimod.command.api.Command;

public class InvSeeCommand extends Command
{
    public InvSeeCommand() {
        super("invsee");
    }
    
    @Override
    public void execute(final String cmd, final String[] args) {
        if (args.length < 2) {
            Command.clientMessage("Usage: .invsee <player name>");
            return;
        }
        for (final Entity entity : InvSeeCommand.mc.world.getLoadedEntityList()) {
            if (entity instanceof EntityOtherPlayerMP) {
                final EntityOtherPlayerMP player = (EntityOtherPlayerMP)entity;
                if (player.getName().equals(args[1])) {
                    Command.clientMessage("Showing inventory of " + player.getName());
                    InvSeeCommand.mc.displayGuiScreen((GuiScreen)new GuiInventory((EntityPlayer)player));
                    return;
                }
                continue;
            }
        }
        Command.clientMessage("Such player in range!");
    }
    
    @Override
    public String getSuggestion(final String cmd, final String[] args) {
        switch (args.length) {
            case 1: {
                final String aName = this.getPlayerName("");
                if (aName == null) {
                    break;
                }
                return ".invsee " + aName;
            }
            case 2: {
                final String bName = this.getPlayerName(args[1]);
                if (bName == null) {
                    break;
                }
                return ".invsee " + bName;
            }
        }
        return ".invsee";
    }
    
    public String getPlayerName(final String in) {
        for (final Entity e : InvSeeCommand.mc.world.loadedEntityList) {
            if (e instanceof EntityOtherPlayerMP) {
                final EntityOtherPlayerMP player = (EntityOtherPlayerMP)e;
                if (player.getName().startsWith(in)) {
                    return player.getName();
                }
                continue;
            }
        }
        return null;
    }
}
