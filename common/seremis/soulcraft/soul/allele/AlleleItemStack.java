package seremis.soulcraft.soul.allele;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import seremis.soulcraft.soul.Allele;

public class AlleleItemStack extends Allele {

    public ItemStack stack;
    
    public AlleleItemStack(boolean isDominant, ItemStack stack) {
        super(isDominant, EnumAlleleType.ITEMSTACK);
    }
    
    public AlleleItemStack(NBTTagCompound compound) {
        super(compound);
        readFromNBT(compound);
    }
    
    public void readFromNBT(NBTTagCompound compound) {
        super.readFromNBT(compound);
        stack = ItemStack.loadItemStackFromNBT(compound);
    }

    public void writeToNBT(NBTTagCompound compound) {
        super.writeToNBT(compound);
        stack.writeToNBT(compound);
    }
}
