package seremis.geninfusion.soul.allele;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import seremis.geninfusion.api.soul.EnumAlleleType;
import seremis.geninfusion.soul.Allele;
import seremis.geninfusion.util.inventory.Inventory;

/*
 * A multiple itemstack allele
 */
public class AlleleInventory extends Allele {

    public Inventory inventory;

    public AlleleInventory(boolean isDominant, ItemStack[] stacks) {
        super(isDominant, EnumAlleleType.INVENTORY);
        inventory = new Inventory(stacks.length, null, 64, null);
        inventory.setItemStacks(stacks);
    }

    public AlleleInventory(Object... args) {
        super(args);
        inventory = new Inventory(args.length - 1, null, 64, null);
        for(int i = 1; i < args.length; i++) {
            inventory.setInventorySlotContents(i - 1, (ItemStack) args[i]);
        }
        type = EnumAlleleType.INVENTORY;
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
