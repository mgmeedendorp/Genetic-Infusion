package Seremis.SoulCraft.inventory;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ICrafting;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import Seremis.SoulCraft.core.proxy.CommonProxy;
import Seremis.SoulCraft.item.ItemTransporterModules;
import Seremis.SoulCraft.slot.SCSlot;
import Seremis.SoulCraft.slot.UpgradeSlot;
import Seremis.SoulCraft.tileentity.TileTransporter;
import Seremis.SoulCraft.util.UtilBlock;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ContainerTransporter extends SCContainer {

    private TileTransporter tile;
    private World world;
    private float speed = 0;

    public ContainerTransporter(EntityPlayer player, IInventory tile) {
        super(tile);
        this.tile = (TileTransporter) tile;
        this.speed = this.tile.getSpeed();
        this.world = player.worldObj;
        this.addSlotToContainer(new SCSlot(tile, 0, 62, 17));
        this.addSlotToContainer(new SCSlot(tile, 1, 80, 17));
        this.addSlotToContainer(new SCSlot(tile, 2, 98, 17));
        this.addSlotToContainer(new SCSlot(tile, 3, 62, 35));
        this.addSlotToContainer(new SCSlot(tile, 4, 80, 35));
        this.addSlotToContainer(new SCSlot(tile, 5, 98, 35));
        this.addSlotToContainer(new SCSlot(tile, 6, 62, 53));
        this.addSlotToContainer(new SCSlot(tile, 7, 80, 53));
        this.addSlotToContainer(new SCSlot(tile, 8, 98, 53));

        // Upgrade Slots
        this.addSlotToContainer(new UpgradeSlot(tile, 9, 154, 17));
        this.addSlotToContainer(new UpgradeSlot(tile, 10, 154, 35));
        this.addSlotToContainer(new UpgradeSlot(tile, 11, 154, 53));
        this.addPlayerInventory(player);
    }

    @Override
    public void addCraftingToCrafters(ICrafting iCrafting) {
        super.addCraftingToCrafters(iCrafting);
        // speed *10 for more precision, is reverted when received.
        iCrafting.sendProgressBarUpdate(this, 0, (int) this.tile.getSpeed() * 10);
    }

    @Override
    public void detectAndSendChanges() {
        super.detectAndSendChanges();

        for(int i = 0; i < this.crafters.size(); ++i) {
            ICrafting icrafting = (ICrafting) this.crafters.get(i);

            if(this.speed != this.tile.getSpeed()) {
                icrafting.sendProgressBarUpdate(this, 0, (int) this.tile.getSpeed() * 10);
            }
        }
        this.speed = this.tile.getSpeed();
    }

    @SideOnly(Side.CLIENT)
    public void updateProgressBar(int id, int value) {
        if(id == 0) {
            // Revert the *10 at sending the value for more precise values
            this.tile.setSpeed(value / 10);
        }

    }

    @Override
    public ItemStack transferStackInSlot(EntityPlayer par1EntityPlayer, int par2) {
        ItemStack itemstack = null;
        Slot slot = (Slot) this.inventorySlots.get(par2);

        if(slot != null && slot.getHasStack()) {
            ItemStack itemstack1 = slot.getStack();
            itemstack = itemstack1.copy();

            if(par2 < 9) {
                if(!this.mergeItemStack(itemstack1, 9, this.inventorySlots.size(), true)) {
                    return null;
                }
            } else if(!this.mergeItemStack(itemstack1, 0, 9, false)) {
                return null;
            }

            if(itemstack1.stackSize == 0) {
                slot.putStack((ItemStack) null);
            } else {
                slot.onSlotChanged();
            }
        }

        return itemstack;
    }

    @Override
    public void onContainerClosed(EntityPlayer player) {
        super.onContainerClosed(player);

        if(CommonProxy.proxy.isServerWorld(world) && !tile.hasInventory()) {
            if(tile.hasEngine()) {
                for(int i = 0; i < 12; i++) {
                    if(tile.getStackInSlot(i) != null && tile.getStackInSlot(i).itemID != ItemTransporterModules.engine().itemID && tile.getStackInSlot(i).getItemDamage() != ItemTransporterModules.engine().getItemDamage()) {
                        UtilBlock.dropItemsFromTile(world, tile.xCoord, tile.yCoord, tile.zCoord, i);
                    }
                }
            } else {
                UtilBlock.dropItemsFromTile(world, tile.xCoord, tile.yCoord, tile.zCoord);
            }
            tile.emptyInventory();
            tile.onInventoryChanged();
        }
    }
}
