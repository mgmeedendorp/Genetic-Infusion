package seremis.geninfusion.soul.allele;

import seremis.geninfusion.soul.Allele;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public class AlleleItemStack extends Allele {

    public ItemStack stack;
    
    public AlleleItemStack(boolean isDominant, ItemStack stack) {
        super(isDominant, EnumAlleleType.ITEMSTACK);
    }
    
    public AlleleItemStack(NBTTagCompound compound) {
        super(compound);
        readFromNBT(compound);
    }
    
    @Override
	public void readFromNBT(NBTTagCompound compound) {
        super.readFromNBT(compound);
        stack = ItemStack.loadItemStackFromNBT(compound);
    }

    @Override
	public void writeToNBT(NBTTagCompound compound) {
        super.writeToNBT(compound);
        stack.writeToNBT(compound);
    }
}
