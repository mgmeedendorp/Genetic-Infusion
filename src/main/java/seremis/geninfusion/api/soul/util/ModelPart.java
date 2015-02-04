package seremis.geninfusion.api.soul.util;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.model.ModelBox;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import seremis.geninfusion.helper.GIReflectionHelper;
import seremis.geninfusion.util.INBTTagable;

import java.util.ArrayList;
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
        this(new ModelBiped(), compound.getString("boxName"));
        readFromNBT(compound);
    }

    @Override
    public void writeToNBT(NBTTagCompound compound) {
        if(boxName != null && !boxName.equals("")) compound.setString("boxName", boxName);
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
            for(int i = 0; i < childModels.size(); i++) {
                ModelPart modelPart = (ModelPart) childModels.get(i);
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

                if(box.field_78247_g != null && !box.field_78247_g.equals("")) compound.setString("boxName", box.field_78247_g);
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

    public static ModelPart[] getHeadFromModel(ModelBiped model) {
        ArrayList<ModelPart> parts = new ArrayList<ModelPart>();

        ModelRenderer head = model.bipedHead;
        ModelRenderer ears = model.bipedEars;
        ModelRenderer headWear = model.bipedHeadwear;

        ModelPart headPart = new ModelPart(model, head.boxName);
        headPart.childModels = head.childModels;
        headPart.cubeList = head.cubeList;
        headPart.isHidden = head.isHidden;
        headPart.showModel = head.showModel;
        headPart.mirror = head.mirror;
        headPart.rotationPointX = head.rotationPointX;
        headPart.rotationPointY = head.rotationPointY;
        headPart.rotationPointZ = head.rotationPointZ;
        headPart.rotateAngleX = head.rotateAngleX;
        headPart.rotateAngleY = head.rotateAngleY;
        headPart.rotateAngleZ = head.rotateAngleZ;
        headPart.textureWidth = head.textureWidth;
        headPart.textureHeight = head.textureHeight;
        headPart.offsetX = head.offsetX;
        headPart.offsetY = head.offsetY;
        headPart.offsetZ = head.offsetZ;
        headPart.setTextureOffset((Integer) GIReflectionHelper.getField(head, "textureOffsetX"), (Integer) GIReflectionHelper.getField(head, "textureOffsetY"));
        parts.add(headPart);

        ModelPart earsPart = new ModelPart(model, ears.boxName);
        earsPart.childModels = ears.childModels;
        earsPart.cubeList = ears.cubeList;
        earsPart.isHidden = ears.isHidden;
        earsPart.showModel = ears.showModel;
        earsPart.mirror = ears.mirror;
        earsPart.rotationPointX = ears.rotationPointX;
        earsPart.rotationPointY = ears.rotationPointY;
        earsPart.rotationPointZ = ears.rotationPointZ;
        earsPart.rotateAngleX = ears.rotateAngleX;
        earsPart.rotateAngleY = ears.rotateAngleY;
        earsPart.rotateAngleZ = ears.rotateAngleZ;
        earsPart.textureWidth = ears.textureWidth;
        earsPart.textureHeight = ears.textureHeight;
        earsPart.offsetX = ears.offsetX;
        earsPart.offsetY = ears.offsetY;
        earsPart.offsetZ = ears.offsetZ;
        earsPart.setTextureOffset((Integer) GIReflectionHelper.getField(ears, "textureOffsetX"), (Integer) GIReflectionHelper.getField(ears, "textureOffsetY"));
        parts.add(earsPart);
        
        ModelPart headWearPart = new ModelPart(model, headWear.boxName);
        headWearPart.childModels = headWear.childModels;
        headWearPart.cubeList = headWear.cubeList;
        headWearPart.isHidden = headWear.isHidden;
        headWearPart.showModel = headWear.showModel;
        headWearPart.mirror = headWear.mirror;
        headWearPart.rotationPointX = headWear.rotationPointX;
        headWearPart.rotationPointY = headWear.rotationPointY;
        headWearPart.rotationPointZ = headWear.rotationPointZ;
        headWearPart.rotateAngleX = headWear.rotateAngleX;
        headWearPart.rotateAngleY = headWear.rotateAngleY;
        headWearPart.rotateAngleZ = headWear.rotateAngleZ;
        headWearPart.textureWidth = headWear.textureWidth;
        headWearPart.textureHeight = headWear.textureHeight;
        headWearPart.offsetX = headWear.offsetX;
        headWearPart.offsetY = headWear.offsetY;
        headWearPart.offsetZ = headWear.offsetZ;
        headWearPart.setTextureOffset((Integer) GIReflectionHelper.getField(headWear, "textureOffsetX"), (Integer) GIReflectionHelper.getField(headWear, "textureOffsetY"));
        parts.add(headWearPart);

        return parts.toArray(new ModelPart[parts.size()]);
    }

    public static ModelPart[] getBodyFromModel(ModelBiped model) {
        ArrayList<ModelPart> parts = new ArrayList<ModelPart>();

        ModelRenderer body = model.bipedBody;
        ModelRenderer cloak = model.bipedCloak;

        ModelPart bodyPart = new ModelPart(model, body.boxName);
        bodyPart.childModels = body.childModels;
        bodyPart.cubeList = body.cubeList;
        bodyPart.isHidden = body.isHidden;
        bodyPart.showModel = body.showModel;
        bodyPart.mirror = body.mirror;
        bodyPart.rotationPointX = body.rotationPointX;
        bodyPart.rotationPointY = body.rotationPointY;
        bodyPart.rotationPointZ = body.rotationPointZ;
        bodyPart.rotateAngleX = body.rotateAngleX;
        bodyPart.rotateAngleY = body.rotateAngleY;
        bodyPart.rotateAngleZ = body.rotateAngleZ;
        bodyPart.textureWidth = body.textureWidth;
        bodyPart.textureHeight = body.textureHeight;
        bodyPart.offsetX = body.offsetX;
        bodyPart.offsetY = body.offsetY;
        bodyPart.offsetZ = body.offsetZ;
        bodyPart.setTextureOffset((Integer) GIReflectionHelper.getField(body, "textureOffsetX"), (Integer) GIReflectionHelper.getField(body, "textureOffsetY"));
        parts.add(bodyPart);

        ModelPart cloakPart = new ModelPart(model, cloak.boxName);
        cloakPart.childModels = cloak.childModels;
        cloakPart.cubeList = cloak.cubeList;
        cloakPart.isHidden = cloak.isHidden;
        cloakPart.showModel = cloak.showModel;
        cloakPart.mirror = cloak.mirror;
        cloakPart.rotationPointX = cloak.rotationPointX;
        cloakPart.rotationPointY = cloak.rotationPointY;
        cloakPart.rotationPointZ = cloak.rotationPointZ;
        cloakPart.rotateAngleX = cloak.rotateAngleX;
        cloakPart.rotateAngleY = cloak.rotateAngleY;
        cloakPart.rotateAngleZ = cloak.rotateAngleZ;
        cloakPart.textureWidth = cloak.textureWidth;
        cloakPart.textureHeight = cloak.textureHeight;
        cloakPart.offsetX = cloak.offsetX;
        cloakPart.offsetY = cloak.offsetY;
        cloakPart.offsetZ = cloak.offsetZ;
        cloakPart.setTextureOffset((Integer) GIReflectionHelper.getField(cloak, "textureOffsetX"), (Integer) GIReflectionHelper.getField(cloak, "textureOffsetY"));
        parts.add(cloakPart);

        return parts.toArray(new ModelPart[parts.size()]);
    }
}