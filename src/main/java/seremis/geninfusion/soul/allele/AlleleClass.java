package seremis.geninfusion.soul.allele;

import net.minecraft.nbt.NBTTagCompound;
import seremis.geninfusion.api.soul.EnumAlleleType;
import seremis.geninfusion.soul.Allele;

public class AlleleClass extends Allele {

    public Class value;

    public AlleleClass(boolean isDominant, Class value) {
        super(isDominant, EnumAlleleType.CLASS);
        this.value = value;
    }

    public AlleleClass(Object... args) {
        super(args);
        value = (Class) args[1];
    }

    public AlleleClass(NBTTagCompound compound) {
        super(compound);
        readFromNBT(compound);
    }

    @Override
    public void writeToNBT(NBTTagCompound compound) {
        super.writeToNBT(compound);
        compound.setString("value", String.valueOf(value));
    }

    @Override
    public void readFromNBT(NBTTagCompound compound) {
        super.readFromNBT(compound);
        try {
            value = Class.forName(compound.getString("value"));
        } catch(ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

}
