package seremis.geninfusion.api.soul.util;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelBox;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import seremis.geninfusion.api.soul.SoulHelper;
import seremis.geninfusion.helper.GIReflectionHelper;
import seremis.geninfusion.util.INBTTagable;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Random;

public class ModelPart extends ModelRenderer implements INBTTagable {

    public ModelPart(String boxName) {
        super(SoulHelper.entityModel, boxName);
    }

    public ModelPart() {
        super(SoulHelper.entityModel);
    }

    public ModelPart(int textureOffsetX, int textureOffsetY) {
        super(SoulHelper.entityModel, textureOffsetX, textureOffsetY);
    }

    public ModelPart(NBTTagCompound compound) {
        this(compound.getString("boxName"));
        readFromNBT(compound);
    }

    @Override
    public void writeToNBT(NBTTagCompound compound) {
        if(boxName != null && !boxName.equals(""))
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

        if(childModels != null) {
            for(Object childModel : childModels) {
                ModelPart modelPart = (ModelPart) childModel;
                NBTTagCompound compound1 = new NBTTagCompound();

                modelPart.writeToNBT(compound1);

                childList.appendTag(compound1);
            }
        }
        compound.setTag("childModels", childList);

        NBTTagList boxList = new NBTTagList();

        if(cubeList != null) {
            for(Object cube : cubeList) {
                ModelBox box = (ModelBox) cube;
                NBTTagCompound compound1 = new NBTTagCompound();

                if(box.field_78247_g != null && !box.field_78247_g.equals(""))
                    compound.setString("boxName", box.field_78247_g);
                compound1.setFloat("originX", box.posX1);
                compound1.setFloat("originY", box.posY1);
                compound1.setFloat("originZ", box.posZ1);
                compound1.setInteger("sizeX", (int) (box.posX2 - box.posX1));
                compound1.setInteger("sizeY", (int) (box.posY2 - box.posY1));
                compound1.setInteger("sizeZ", (int) (box.posZ2 - box.posZ1));

                boxList.appendTag(compound1);
            }
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
            NBTTagCompound boxCompound = boxList.getCompoundTagAt(i);
            ModelBox box = new ModelBox(this, (Integer) GIReflectionHelper.getField(this, "textureOffsetX"), (Integer) GIReflectionHelper.getField(this, "textureOffsetY"), boxCompound.getFloat("originX"), boxCompound.getFloat("originY"), boxCompound.getFloat("originZ"), boxCompound.getInteger("sizeX"), boxCompound.getInteger("sizeY"), boxCompound.getInteger("sizeZ"), 0.0F);
            cubeList.add(box);
        }
    }

    private Random rand = new Random();

    public ModelPart mutate() {
        switch(rand.nextInt(3)) {
            case 0:
                rotationPointX = (float) (rotationPointX * ((rand.nextFloat() * 2 * 0.1) + 0.9));
                rotationPointY = (float) (rotationPointY * ((rand.nextFloat() * 2 * 0.1) + 0.9));
                rotationPointZ = (float) (rotationPointZ * ((rand.nextFloat() * 2 * 0.1) + 0.9));
                break;
            case 1:
                rotateAngleX = (float) (rotateAngleX * ((rand.nextFloat() * 2 * 0.1) + 0.9));
                rotateAngleY = (float) (rotateAngleY * ((rand.nextFloat() * 2 * 0.1) + 0.9));
                rotateAngleZ = (float) (rotateAngleZ * ((rand.nextFloat() * 2 * 0.1) + 0.9));
                break;
            case 2:
                cubeList.remove(rand.nextInt(cubeList.size()));
                break;
        }

        return this;
    }

    public static ModelPart[] getModelPartsFromModel(ModelBase model) {
        ArrayList<ModelPart> parts = new ArrayList<ModelPart>();

        Field[] fields = GIReflectionHelper.getFields(model);

        for(Field field : fields) {
            if(field.getType().equals(ModelRenderer.class) && !field.getName().equals("bipedCloak") && !field.getName().equals("bipedEars")) {
                ModelRenderer renderer = (ModelRenderer) GIReflectionHelper.getField(model, field.getName());
                parts.add(modelRendererToModelPart(renderer));
            }
        }

        return parts.toArray(new ModelPart[parts.size()]);
    }

    public static ModelPart modelRendererToModelPart(ModelRenderer model) {
        ModelPart modelPart = new ModelPart(model.boxName);
        modelPart.childModels = model.childModels;
        modelPart.cubeList = model.cubeList;
        modelPart.isHidden = model.isHidden;
        modelPart.showModel = model.showModel;
        modelPart.mirror = model.mirror;
        modelPart.rotationPointX = model.rotationPointX;
        modelPart.rotationPointY = model.rotationPointY;
        modelPart.rotationPointZ = model.rotationPointZ;
        modelPart.rotateAngleX = model.rotateAngleX;
        modelPart.rotateAngleY = model.rotateAngleY;
        modelPart.rotateAngleZ = model.rotateAngleZ;
        modelPart.textureWidth = model.textureWidth;
        modelPart.textureHeight = model.textureHeight;
        modelPart.offsetX = model.offsetX;
        modelPart.offsetY = model.offsetY;
        modelPart.offsetZ = model.offsetZ;
        modelPart.setTextureOffset((Integer) GIReflectionHelper.getField(model, "textureOffsetX"), (Integer) GIReflectionHelper.getField(model, "textureOffsetY"));
        return modelPart;
    }
}