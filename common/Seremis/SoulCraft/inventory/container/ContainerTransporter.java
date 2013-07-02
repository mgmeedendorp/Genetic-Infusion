package Seremis.SoulCraft.inventory.container;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ICrafting;
import net.minecraft.inventory.IInventory;
import Seremis.SoulCraft.slot.SCSlot;
import Seremis.SoulCraft.slot.UpgradeSlot;
import Seremis.SoulCraft.tileentity.TileTransporter;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;


public class ContainerTransporter extends SCContainer {

    private TileTransporter tile;
    private float speed = 0;
    
    public ContainerTransporter(EntityPlayer player, IInventory tile) {
        super(tile);
        this.tile = (TileTransporter)tile;
        this.speed = this.tile.getSpeed();
        this.addPlayerInventory(player);
        this.addSlotToContainer(new SCSlot(tile, 0, 61, 16));
        this.addSlotToContainer(new SCSlot(tile, 1, 79, 16));
        this.addSlotToContainer(new SCSlot(tile, 2, 97, 16));
        this.addSlotToContainer(new SCSlot(tile, 3, 61, 34));
        this.addSlotToContainer(new SCSlot(tile, 4, 79, 34));
        this.addSlotToContainer(new SCSlot(tile, 5, 97, 34));
        this.addSlotToContainer(new SCSlot(tile, 6, 61, 52));
        this.addSlotToContainer(new SCSlot(tile, 7, 79, 52));
        this.addSlotToContainer(new SCSlot(tile, 8, 97, 52));
        
        //Upgrade Slots
        this.addSlotToContainer(new UpgradeSlot(tile, 9, 153, 16));
        this.addSlotToContainer(new UpgradeSlot(tile, 10, 153, 34));
        this.addSlotToContainer(new UpgradeSlot(tile, 11, 153, 52));
    }
    
    @Override
    public void addCraftingToCrafters(ICrafting iCrafting) {
        super.addCraftingToCrafters(iCrafting);
        //speed *10 for values with one decimal value, is reverted when received.
        iCrafting.sendProgressBarUpdate(this, 0, (int)this.tile.getSpeed()*10);
    }
    
    @Override
    public void detectAndSendChanges() {
        super.detectAndSendChanges();
        
        for (int i = 0; i < this.crafters.size(); ++i) {
            ICrafting icrafting = (ICrafting)this.crafters.get(i);
            
            if (this.speed != this.tile.getSpeed()) {
                icrafting.sendProgressBarUpdate(this, 0, (int)this.tile.getSpeed()*10);
            }             
        }
        this.speed = this.tile.getSpeed();
    }
    
    @SideOnly(Side.CLIENT)
    public void updateProgressBar(int id, int value)
    {
        if (id == 0)
        {
            //Revert the *10 at sending the value for more precise values
            this.tile.setSpeed(value/10);
        }

    }
}
