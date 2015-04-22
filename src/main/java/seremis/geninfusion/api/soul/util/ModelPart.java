package seremis.geninfusion.api.soul.util;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.model.*;
import net.minecraft.entity.EntityLiving;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.Vec3;
import net.minecraftforge.common.util.Constants;
import seremis.geninfusion.api.soul.SoulHelper;
import seremis.geninfusion.helper.GIReflectionHelper;
import seremis.geninfusion.util.INBTTagable;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ModelPart extends ModelRenderer implements INBTTagable {

    public float initialRotationPointX, initialRotationPointY, initialRotationPointZ;
    public float initialRotateAngleX, initialRotateAngleY, initialRotateAngleZ;

    public ModelPart(String boxName) {
        super(SoulHelper.entityModel(), boxName);
    }

    public ModelPart() {
        super(SoulHelper.entityModel());
    }

    public ModelPart(int textureOffsetX, int textureOffsetY) {
        super(SoulHelper.entityModel(), textureOffsetX, textureOffsetY);
    }

    public ModelPart(NBTTagCompound compound) {
        this(compound.getString("boxName"));
        readFromNBT(compound);
    }

    private boolean firstTick = true;

    @Override
    @SideOnly(Side.CLIENT)
    public void render(float scale) {
        if(firstTick) {
            initialRotationPointX = rotationPointX;
            initialRotationPointY = rotationPointY;
            initialRotationPointZ = rotationPointZ;
            initialRotateAngleX = rotateAngleX;
            initialRotateAngleY = rotateAngleY;
            initialRotateAngleZ = rotateAngleZ;
            firstTick = false;
        }
        super.render(scale);
    }


    public Vec3 getInitialRotationPoints() {
        return Vec3.createVectorHelper(initialRotationPointX, initialRotationPointY, initialRotationPointZ);
    }

    public Vec3 getInitialRotateAngles() {
        return Vec3.createVectorHelper(initialRotateAngleX, initialRotateAngleY, initialRotateAngleZ);
    }

    public int getTextureOffsetX() {
        return (Integer) GIReflectionHelper.getField(this, "textureOffsetX");
    }

    public int getTextureOffsetY() {
        return (Integer) GIReflectionHelper.getField(this, "textureOffsetY");
    }

    public List<ModelBox> getBoxList() {
        return cubeList;
    }

    public TexturedQuad[] getBoxQuads(ModelBox box) {
        return (TexturedQuad[]) GIReflectionHelper.getField(box, "quadList");
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
        if(boxName != null && !boxName.equals(""))
            compound.setString("boxName", boxName);
        compound.setFloat("textureWidth", textureWidth);
        compound.setFloat("textureHeight", textureHeight);
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
                ModelPart modelPart = ModelPart.modelRendererToModelPart((ModelRenderer) childModel);
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
                NBTTagCompound boxCompound = new NBTTagCompound();

                if(box.field_78247_g != null && !box.field_78247_g.equals(""))
                    compound.setString("boxName", box.field_78247_g);
                boxCompound.setFloat("originX", box.posX1);
                boxCompound.setFloat("originY", box.posY1);
                boxCompound.setFloat("originZ", box.posZ1);
                boxCompound.setInteger("sizeX", (int) (box.posX2 - box.posX1));
                boxCompound.setInteger("sizeY", (int) (box.posY2 - box.posY1));
                boxCompound.setInteger("sizeZ", (int) (box.posZ2 - box.posZ1));
                TexturedQuad[] quads = (TexturedQuad[]) GIReflectionHelper.getField(box, "quadList");

                NBTTagList quadList = new NBTTagList();

                for(TexturedQuad quad : quads) {
                    NBTTagList vertexList = new NBTTagList();

                    PositionTextureVertex[] vertices = quad.vertexPositions;

                    for(PositionTextureVertex vertex : vertices) {
                        NBTTagCompound vertexCompound = new NBTTagCompound();

                        vertexCompound.setDouble("vectorX", vertex.vector3D.xCoord);
                        vertexCompound.setDouble("vectorY", vertex.vector3D.yCoord);
                        vertexCompound.setDouble("vectorZ", vertex.vector3D.zCoord);
                        vertexCompound.setFloat("texturePositionX", vertex.texturePositionX);
                        vertexCompound.setFloat("texturePositionY", vertex.texturePositionY);

                        vertexList.appendTag(vertexCompound);
                    }
                    NBTTagCompound vertexListCompound = new NBTTagCompound();
                    vertexListCompound.setTag("vertexList", vertexList);

                    quadList.appendTag(vertexListCompound);
                }
                boxCompound.setTag("quadList", quadList);


                NBTTagList vertexList = new NBTTagList();
                PositionTextureVertex[] vertices = (PositionTextureVertex[]) GIReflectionHelper.getField(box, "vertexPositions");

                for(PositionTextureVertex vertex : vertices) {
                    NBTTagCompound vertexCompound = new NBTTagCompound();

                    vertexCompound.setDouble("vectorX", vertex.vector3D.xCoord);
                    vertexCompound.setDouble("vectorY", vertex.vector3D.yCoord);
                    vertexCompound.setDouble("vectorZ", vertex.vector3D.zCoord);
                    vertexCompound.setFloat("texturePositionX", vertex.texturePositionX);
                    vertexCompound.setFloat("texturePositionY", vertex.texturePositionY);

                    vertexList.appendTag(vertexCompound);
                }

                boxCompound.setTag("vertexList", vertexList);

                boxList.appendTag(boxCompound);
            }
        }
        compound.setTag("cubeList", boxList);
        return compound;
    }

    @Override
    public NBTTagCompound readFromNBT(NBTTagCompound compound) {
        if(compound.hasKey("boxName"))
            GIReflectionHelper.setField(this, "boxName", compound.getString("boxName"));

        textureWidth = compound.getFloat("textureWidth");
        textureHeight = compound.getFloat("textureHeight");
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

            NBTTagList quadList = (NBTTagList) boxCompound.getTag("quadList");

            TexturedQuad[] quads = new TexturedQuad[quadList.tagCount()];

            for(int j = 0; j < quadList.tagCount(); j++) {
                NBTTagCompound vertexListCompound = quadList.getCompoundTagAt(j);
                NBTTagList vertexList = vertexListCompound.getTagList("vertexList", Constants.NBT.TAG_COMPOUND);

                PositionTextureVertex[] vertices = new PositionTextureVertex[vertexList.tagCount()];

                for(int k = 0; k < vertexList.tagCount(); k++) {
                    NBTTagCompound vertexCompound = vertexList.getCompoundTagAt(k);

                    Vec3 vector = Vec3.createVectorHelper(vertexCompound.getDouble("vectorX"), vertexCompound.getDouble("vectorY"), vertexCompound.getDouble("vectorZ"));
                    vertices[k] = new PositionTextureVertex(vector, vertexCompound.getFloat("texturePositionX"), vertexCompound.getFloat("texturePositionY"));
                }

                quads[j] = new TexturedQuad(vertices);
            }

            GIReflectionHelper.setField(box, "quadList", quads);


            NBTTagList vertexList = boxCompound.getTagList("vertexList", Constants.NBT.TAG_COMPOUND);

            PositionTextureVertex[] vertices = new PositionTextureVertex[vertexList.tagCount()];

            for(int k = 0; k < vertexList.tagCount(); k++) {
                NBTTagCompound vertexCompound = vertexList.getCompoundTagAt(k);

                Vec3 vector = Vec3.createVectorHelper(vertexCompound.getDouble("vectorX"), vertexCompound.getDouble("vectorY"), vertexCompound.getDouble("vectorZ"));
                vertices[k] = new PositionTextureVertex(vector, vertexCompound.getFloat("texturePositionX"), vertexCompound.getFloat("texturePositionY"));
            }

            GIReflectionHelper.setField(box, "vertexPositions", vertices);

            cubeList.add(box);
        }
        return compound;
    }

    private Random rand = new Random();

    public ModelPart mutate() {
        switch(rand.nextInt(2)) {
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
        }
        return this;
    }

    public static ModelPart[] getModelPartsFromModel(ModelBase model, EntityLiving entity) {
        ArrayList<ModelPart> parts = new ArrayList<ModelPart>();

        //With this skeletons animate without zombie arms without having bows in their hands
        if(!(model instanceof ModelSkeleton)) {
            model.setRotationAngles(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, entity);
        }

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

    public static ModelPart fromNBT(NBTTagCompound compound) {
        ModelPart part = new ModelPart();
        part.readFromNBT(compound);
        return part;
    }

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof ModelPart) {
            ModelPart part = (ModelPart) obj;
            NBTTagCompound compound1 = new NBTTagCompound();
            NBTTagCompound compound2 = new NBTTagCompound();
            this.writeToNBT(compound1);
            part.writeToNBT(compound2);
            return compound1.equals(compound2);
        }
        return false;
    }
}