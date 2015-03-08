package seremis.geninfusion.soul.allele;

import net.minecraft.nbt.NBTTagCompound;
import seremis.geninfusion.api.soul.EnumAlleleType;
import seremis.geninfusion.soul.Allele;

public class AlleleString extends Allele {

    public String value;

    public AlleleString(boolean isDominant, String value) {
        super(isDominant, EnumAlleleType.STRING);
        this.value = value;
    }

    public AlleleString(Object... args) {
        super(args);
        value = (String) args[1];
        type = EnumAlleleType.STRING;
    }

    public AlleleString(NBTTagCompound compound) {
        super(compound);
        readFromNBT(compound);
    }

    @Override
    public void writeToNBT(NBTTagCompound compound) {
        super.writeToNBT(compound);
        if(value != null)
            compound.setString("value", value);
    }

    @Override
    public void readFromNBT(NBTTagCompound compound) {
        super.readFromNBT(compound);
        value = null;
        if(compound.hasKey("value"))
            value = compound.getString("value");
    }
}
