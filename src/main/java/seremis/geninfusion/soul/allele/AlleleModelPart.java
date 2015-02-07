package seremis.geninfusion.soul.allele;

import net.minecraft.nbt.NBTTagCompound;
import seremis.geninfusion.api.soul.EnumAlleleType;
import seremis.geninfusion.api.soul.util.ModelPart;
import seremis.geninfusion.soul.Allele;

public class AlleleModelPart extends Allele {

    private ModelPart value;

    public AlleleModelPart(boolean isDominant, ModelPart value) {
        super(isDominant, EnumAlleleType.MODEL_PART);
        this.value = value;
    }

    public AlleleModelPart(Object... args) {
        super(args);
        value = (ModelPart) args[1];
    }

    public AlleleModelPart(NBTTagCompound compound) {
        super(compound);
        readFromNBT(compound);
    }

    @Override
    public void readFromNBT(NBTTagCompound compound) {
        super.readFromNBT(compound);
        value = new ModelPart(compound);
    }

    @Override
    public void writeToNBT(NBTTagCompound compound) {
        super.writeToNBT(compound);
        value.writeToNBT(compound);
    }

}
