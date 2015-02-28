package seremis.geninfusion.soul.allele;

import net.minecraft.client.Minecraft;
import net.minecraft.nbt.NBTTagCompound;
import seremis.geninfusion.api.soul.EnumAlleleType;
import seremis.geninfusion.soul.Allele;

public class AlleleClassArray extends Allele {

    public Class[] value;

    public AlleleClassArray(boolean isDominant, Class[] value) {
        super(isDominant, EnumAlleleType.CLASS_ARRAY);
        this.value = value;
    }

    public AlleleClassArray(Object... args) {
        super(args);
        value = new Class[args.length - 1];
        for(int i = 1; i < args.length; i++) {
            value[i - 1] = (Class) args[i];
        }
    }

    public AlleleClassArray(NBTTagCompound compound) {
        super(compound);
        readFromNBT(compound);
    }

    @Override
    public void writeToNBT(NBTTagCompound compound) {
        super.writeToNBT(compound);
        compound.setInteger("length", value.length);
        for(int i = 0; i < value.length; i++) {
            compound.setString("value" + i, value[i].getName());
        }
    }

    @Override
    public void readFromNBT(NBTTagCompound compound) {
        super.readFromNBT(compound);
        value = new Class[compound.getInteger("length")];
        try {
            for(int i = 0; i < value.length; i++) {
                value[i] = Class.forName(compound.getString("value" + i));
            }
        } catch(ClassNotFoundException e) {
            System.out.println("For some reason a class cannot be found on the " + (Minecraft.getMinecraft().theWorld.isRemote ? "client." : "server."));
            e.printStackTrace();
        }
    }
}
