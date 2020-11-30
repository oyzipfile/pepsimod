// 
// Decompiled by Procyon v0.5.36
// 

package net.daporkchop.pepsimod.command.impl;

import net.minecraft.client.gui.GuiScreen;
import net.minecraft.inventory.IInventory;
import net.minecraft.client.gui.inventory.GuiChest;
import net.minecraft.item.ItemBlock;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.util.NonNullList;
import net.minecraft.item.ItemStack;
import net.minecraft.inventory.InventoryBasic;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.block.Block;
import net.daporkchop.pepsimod.command.api.Command;

public class PeekCommand extends Command
{
    public static Block[] SHULKERS;
    
    public static boolean isShulkerBox(final Block block) {
        for (final Block b : PeekCommand.SHULKERS) {
            if (b == block) {
                return true;
            }
        }
        return false;
    }
    
    public static InventoryBasic getFromItemNBT(final NBTTagCompound tag) {
        final NonNullList<ItemStack> items = (NonNullList<ItemStack>)NonNullList.withSize(27, (Object)ItemStack.EMPTY);
        String customName = "Shulker Box";
        if (tag.hasKey("Items", 9)) {
            ItemStackHelper.loadAllItems(tag, (NonNullList)items);
        }
        if (tag.hasKey("CustomName", 8)) {
            customName = tag.getString("CustomName");
        }
        final InventoryBasic inventoryBasic = new InventoryBasic(customName, true, items.size());
        for (int i = 0; i < items.size(); ++i) {
            inventoryBasic.setInventorySlotContents(i, (ItemStack)items.get(i));
        }
        return inventoryBasic;
    }
    
    public PeekCommand() {
        super("peek");
        PeekCommand.SHULKERS = new Block[] { Blocks.BLACK_SHULKER_BOX, Blocks.BLUE_SHULKER_BOX, Blocks.BROWN_SHULKER_BOX, Blocks.CYAN_SHULKER_BOX, Blocks.GRAY_SHULKER_BOX, Blocks.GREEN_SHULKER_BOX, Blocks.LIGHT_BLUE_SHULKER_BOX, Blocks.LIME_SHULKER_BOX, Blocks.MAGENTA_SHULKER_BOX, Blocks.ORANGE_SHULKER_BOX, Blocks.PINK_SHULKER_BOX, Blocks.PURPLE_SHULKER_BOX, Blocks.RED_SHULKER_BOX, Blocks.SILVER_SHULKER_BOX, Blocks.WHITE_SHULKER_BOX, Blocks.YELLOW_SHULKER_BOX };
    }
    
    @Override
    public void execute(final String cmd, final String[] args) {
        ItemStack stack = null;
        if (!PeekCommand.mc.player.getHeldItemOffhand().isEmpty()) {
            stack = PeekCommand.mc.player.getHeldItemOffhand();
        }
        if (!PeekCommand.mc.player.getHeldItemMainhand().isEmpty()) {
            stack = PeekCommand.mc.player.getHeldItemMainhand();
        }
        if (stack != null && !stack.isEmpty() && stack.getItem() instanceof ItemBlock) {
            final Block block = ((ItemBlock)stack.getItem()).getBlock();
            if (isShulkerBox(block)) {
                if (stack.hasTagCompound()) {
                    final ItemStack wtf_java = stack;
                    PeekCommand.mc.displayGuiScreen((GuiScreen)new GuiChest((IInventory)PeekCommand.mc.player.inventory, (IInventory)getFromItemNBT(wtf_java.getTagCompound().getCompoundTag("BlockEntityTag"))));
                }
                else {
                    PeekCommand.mc.displayGuiScreen((GuiScreen)new GuiChest((IInventory)new InventoryBasic("Shulker Box", true, 27), (IInventory)PeekCommand.mc.player.inventory));
                }
                return;
            }
        }
        Command.clientMessage("Not holding a shulker box!");
    }
    
    @Override
    public String getSuggestion(final String cmd, final String[] args) {
        return ".peek";
    }
}
