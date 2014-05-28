package seremis.soulcraft.soul.allele;

import seremis.soulcraft.soul.Allele;
import seremis.soulcraft.util.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

/*
 * Just a multiple itemstack allele
 */
public class AlleleInventory extends Allele {

    public Inventory inventory;
    
    public AlleleInventory(boolean isDominant, ItemStack[] stacks) {
        super(isDominant, EnumAlleleType.INVENTORY);
        inventory = new Inventory(stacks.length, null, 64, null);
        inventory.setItemStacks(stacks);
    }
    
    public AlleleInventory(NBTTagCompound compound) {
        super(compound);
        readFromNBT(compound);
    }
    
    @Override
	public void writeToNBT(NBTTagCompound compound) {
        super.writeToNBT(compound);
        inventory.writeToNBT(compound);
    }
    
    @Override
	public void readFromNBT(NBTTagCompound compound) {
        super.readFromNBT(compound);
        inventory = new Inventory(compound, null);
    }
}
