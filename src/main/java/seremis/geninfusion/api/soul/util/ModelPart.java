package seremis.geninfusion.api.soul.util;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelBox;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import seremis.geninfusion.helper.GIReflectionHelper;
import seremis.geninfusion.util.INBTTagable;

import java.util.Random;

public class ModelPart extends ModelRenderer implements INBTTagable {

    public ModelPart(ModelBase modelBase, String boxName) {
        super(modelBase, boxName);
    }

    public ModelPart(ModelBase modelBase) {
        super(modelBase);
    }

    public ModelPart(ModelBase modelBase, int textureOffsetX, int textureOffsetY) {
        super(modelBase, textureOffsetX, textureOffsetY);
    }

    public ModelPart(NBTTagCompound compound) {
        this(null, compound.getString("boxName"));
        readFromNBT(compound);
    }

    @Override
    public void writeToNBT(NBTTagCompound compound) {
        compound.setString("boxName", boxName);
        compound.setFloat("textureWidth", textureWidth);
        compound.setFloat("textureHeight", textureHeight);
        compound.setInteger("textureOffsetX", (Integer) GIReflectionHelper.getField(this, "textureOffsetX"));
        compound.setInteger("textureOffsetY", (Integer) GIReflectionHelper.getField(this, "textureOffsetY"));
        compound.setFloat("rotationPointX", rotationPointX);
        compound.setFloat("rotationPointY", rotationPointY);
        compound.setFloat("rotationPointZ", rotationPointZ);
        compound.setFloat("rotateAngleX", rotateAngleX);
        compound.setFloat("rotateAngleY", rotateAngleY);
        compound.setFloat("rotateAngleZ", rotateAngleZ);
        compound.setBoolean("mirror", mirror);
        compound.setBoolean("showModel", showModel);
        compound.setBoolean("isHidden", isHidden);
        compound.setFloat("offsetX", offsetX);
        compound.setFloat("offsetY", offsetY);
        compound.setFloat("offsetZ", offsetZ);

        NBTTagList childList = new NBTTagList();

        for(int i = 0; i < childModels.size(); i++) {
            ModelPart modelPart = (ModelPart)childModels.get(i);
            NBTTagCompound compound1 = new NBTTagCompound();

            modelPart.writeToNBT(compound1);

            childList.appendTag(compound1);
        }
        compound.setTag("childModels", childList);

        NBTTagList boxList = new NBTTagList();

        for(Object cube : cubeList) {
            ModelBox box = (ModelBox) cube;
            NBTTagCompound compound1 = new NBTTagCompound();

            compound.setString("boxName", box.field_78247_g);
            compound1.setFloat("originX", box.posX1);
            compound1.setFloat("originY", box.posY1);
            compound1.setFloat("originZ", box.posZ1);
            compound1.setInteger("sizeX", (int) (box.posX2 - box.posX1));
            compound1.setInteger("sizeY", (int) (box.posY2 - box.posY1));
            compound1.setInteger("sizeZ", (int) (box.posZ2 - box.posZ1));

            boxList.appendTag(compound1);
        }
        compound.setTag("cubeList", boxList);
    }

    @Override
    public void readFromNBT(NBTTagCompound compound) {
        textureWidth = compound.getFloat("textureWidth");
        textureHeight = compound.getFloat("textureHeight");
        setTextureOffset(compound.getInteger("textureOffsetX"), compound.getInteger("textureOffsetY"));
        rotationPointX = compound.getFloat("rotationPointX");
        rotationPointY = compound.getFloat("rotationPointY");
        rotationPointZ = compound.getFloat("rotationPointZ");
        rotateAngleX = compound.getFloat("rotateAngleX");
        rotateAngleY = compound.getFloat("rotateAngleY");
        rotateAngleZ = compound.getFloat("rotateAngleZ");
        mirror = compound.getBoolean("mirror");
        showModel = compound.getBoolean("showModel");
        isHidden = compound.getBoolean("isHidden");
        offsetX = compound.getFloat("offsetX");
        offsetY = compound.getFloat("offsetY");
        offsetZ = compound.getFloat("offsetZ");

        NBTTagList childList = (NBTTagList) compound.getTag("childModels");
        for(int i = 0; i < childList.tagCount(); i++) {
            ModelPart childPart = new ModelPart(childList.getCompoundTagAt(i));
            addChild(childPart);
        }

        NBTTagList boxList = (NBTTagList) compound.getTag("cubeList");
        for(int i = 0; i < boxList.tagCount(); i++) {
            ModelBox box = new ModelBox(this, (Integer) GIReflectionHelper.getField(this, "textureOffsetX"), (Integer) GIReflectionHelper.getField(this, "textureOffsetY"), compound.getFloat("originX"), compound.getFloat("originY"), compound.getFloat("originZ"), compound.getInteger("sizeX"), compound.getInteger("sizeY"), compound.getInteger("sizeZ"), 0.0F);
            cubeList.add(box);
        }
    }

    private Random rand = new Random();

    public ModelPart mutate() {
        switch(rand.nextInt(3)) {
            case 0:
                rotationPointX = (float) (rotationPointX*((rand.nextFloat()*2*0.1)+0.9));
                rotationPointY = (float) (rotationPointY*((rand.nextFloat()*2*0.1)+0.9));
                rotationPointZ = (float) (rotationPointZ*((rand.nextFloat()*2*0.1)+0.9));
                break;
            case 1:
                rotateAngleX = (float) (rotateAngleX*((rand.nextFloat()*2*0.1)+0.9));
                rotateAngleY = (float) (rotateAngleY*((rand.nextFloat()*2*0.1)+0.9));
                rotateAngleZ = (float) (rotateAngleZ*((rand.nextFloat()*2*0.1)+0.9));
                break;
            case 2:
                cubeList.remove(rand.nextInt(cubeList.size()));
                break;
        }

        return this;
    }
}
