package seremis.geninfusion.soul.allele;

import net.minecraft.nbt.NBTTagCompound;
import seremis.geninfusion.api.soul.EnumAlleleType;
import seremis.geninfusion.api.soul.util.ModelPart;
import seremis.geninfusion.soul.Allele;

public class AlleleModelPartArray extends Allele {

    public ModelPart[] value;

    public AlleleModelPartArray(boolean isDominant, ModelPart[] value) {
        super(isDominant, EnumAlleleType.MODEL_PART_ARRAY);
        this.value = value;
    }

    public AlleleModelPartArray(Object... args) {
        super(args);
        value = (ModelPart[]) args[1];
        type = EnumAlleleType.MODEL_PART_ARRAY;
    }

    public AlleleModelPartArray(NBTTagCompound compound) {
        super(compound);
        readFromNBT(compound);
    }

    @Override
    public void readFromNBT(NBTTagCompound compound) {
        super.readFromNBT(compound);
        value = new ModelPart[compound.getInteger("length")];
        for(int i = 0; i < value.length; i++) {
            value[i] = new ModelPart(compound.getCompoundTag("value" + i));
        }
    }

    @Override
    public void writeToNBT(NBTTagCompound compound) {
        super.writeToNBT(compound);
        compound.setInteger("length", value.length);
        for(int i = 0; i < value.length; i++) {
            NBTTagCompound valueCompound = new NBTTagCompound();
            value[i].writeToNBT(valueCompound);
            compound.setTag("value" + i, valueCompound);
        }
    }
}
