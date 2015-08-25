package seremis.geninfusion.api.util.render.model

import java.util
import java.util.Random

import cpw.mods.fml.relauncher.{Side, SideOnly}
import net.minecraft.client.model._
import net.minecraft.client.renderer.GLAllocation
import net.minecraft.entity.{Entity, EntityLiving}
import net.minecraft.nbt.{NBTTagCompound, NBTTagList}
import net.minecraft.util.Vec3
import net.minecraftforge.common.util.Constants
import seremis.geninfusion.api.soul.SoulHelper
import seremis.geninfusion.api.soul.lib.VariableLib
import seremis.geninfusion.helper.GIReflectionHelper
import seremis.geninfusion.util.{GIModelBox, INBTTagable}

import scala.collection.JavaConversions._
import scala.collection.mutable.ListBuffer

object ModelPart {

    private def getModelPartsFromModel(model: ModelBase, entity: EntityLiving, callRotationAngles: Boolean): Array[ModelPart] = {
        val parts: ListBuffer[ModelPart] = ListBuffer()

        model.swingProgress = 0

        if (callRotationAngles && !model.isInstanceOf[ModelSkeleton]) {
            model.setRotationAngles(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, null.asInstanceOf[Entity])
        }

        val fields = GIReflectionHelper.getFields(model)

        for (field <- fields if (field.getType == classOf[ModelRenderer] || field.getType == classOf[ModelPart]) && field.getName != VariableLib.ModelBipedCloak && field.getName != VariableLib.ModelBipedEars && field.getName != VariableLib.ModelBipedHeadwear) {
           // parts.add(modelRendererToModelPart(GIReflectionHelper.getField(model, field.getName).asInstanceOf[ModelRenderer]))
        }
        parts.to[Array]
    }

    def rendererToPart(model: ModelRenderer, modelPartType: String): ModelPart = {
        val modelPart = new ModelPart(GIReflectionHelper.getField(model, VariableLib.ModelRendererBaseModel).asInstanceOf[ModelBase], model.boxName, modelPartType)
        modelPart.childModels = model.childModels
        modelPart.cubeList = model.cubeList
        modelPart.isHidden = model.isHidden
        modelPart.showModel = model.showModel
        modelPart.mirror = model.mirror
        modelPart.rotationPointX = model.rotationPointX
        modelPart.rotationPointY = model.rotationPointY
        modelPart.rotationPointZ = model.rotationPointZ
        modelPart.rotateAngleX = model.rotateAngleX
        modelPart.rotateAngleY = model.rotateAngleY
        modelPart.rotateAngleZ = model.rotateAngleZ
        modelPart.textureWidth = model.textureWidth
        modelPart.textureHeight = model.textureHeight
        modelPart.offsetX = model.offsetX
        modelPart.offsetY = model.offsetY
        modelPart.offsetZ = model.offsetZ
        modelPart.setTextureOffset(GIReflectionHelper.getField(model, VariableLib.ModelRendererTextureOffsetX).asInstanceOf[Int], GIReflectionHelper.getField(model, VariableLib.ModelRendererTextureOffsetY).asInstanceOf[Int])
        modelPart
    }

    def fromNBT(compound: NBTTagCompound): ModelPart = {
        val part = new ModelPart(null.asInstanceOf[String])
        part.readFromNBT(compound)
        part
    }
}

class ModelPart(model: ModelBase, boxName: String, var modelPartTypeName: String) extends ModelRenderer(model, boxName) with INBTTagable {

    var modelPartType = if(modelPartTypeName != null) SoulHelper.modelPartTypeRegistry.getModelPartType(modelPartTypeName).get else null

    var initialRotationPointX: Float = 0.0F
    var initialRotationPointY: Float = 0.0F
    var initialRotationPointZ: Float = 0.0F

    var initialRotateAngleX: Float = 0.0F
    var initialRotateAngleY: Float = 0.0F
    var initialRotateAngleZ: Float = 0.0F

    def this(modelPartTypeName: String) {
        this(SoulHelper.entityModel, "", modelPartTypeName)
    }

    def this(boxName: String, modelPartTypeName: String) {
        this(SoulHelper.entityModel, boxName, modelPartTypeName)
    }

    def this(model: ModelBase, modelPartTypeName: String) {
        this(model, "", modelPartTypeName)
    }

    def this(compound: NBTTagCompound) {
        this(null, compound.getString("boxName"), null)
        readFromNBT(compound)
    }

    private var firstTick: Boolean = true

    @SideOnly(Side.CLIENT)
    override def render(scale: Float) {
        if (firstTick) {
            initialRotationPointX = rotationPointX
            initialRotationPointY = rotationPointY
            initialRotationPointZ = rotationPointZ
            initialRotateAngleX = rotateAngleX
            initialRotateAngleY = rotateAngleY
            initialRotateAngleZ = rotateAngleZ
            firstTick = false
        }
        super.render(scale)
    }

    def getInitialRotationPoints: Vec3 = {
        Vec3.createVectorHelper(initialRotationPointX, initialRotationPointY, initialRotationPointZ)
    }

    def getInitialRotateAngles: Vec3 = {
        Vec3.createVectorHelper(initialRotateAngleX, initialRotateAngleY, initialRotateAngleZ)
    }

    def getTextureOffsetX: Int = {
        GIReflectionHelper.getField(this, VariableLib.ModelRendererTextureOffsetX).asInstanceOf[java.lang.Integer]
    }

    def getTextureOffsetY: Int = {
        GIReflectionHelper.getField(this, VariableLib.ModelRendererTextureOffsetY).asInstanceOf[java.lang.Integer]
    }

    def getBoxList: Array[ModelBox] = cubeList.asInstanceOf[util.ArrayList[ModelBox]].to[Array]

    def getBoxQuads(box: ModelBox): Array[TexturedQuad] = {
        GIReflectionHelper.getField(box, VariableLib.ModelBoxQuadList).asInstanceOf[Array[TexturedQuad]]
    }

    def addGIBox(x: Float, y: Float, z: Float, sizeX: Float, sizeY: Float, sizeZ: Float) {
        cubeList.asInstanceOf[util.List[ModelBox]].add(new GIModelBox(this, getTextureOffsetX, getTextureOffsetY, x, y, z, sizeX, sizeY, sizeZ, 0.0F))
    }

    override def writeToNBT(compound: NBTTagCompound): NBTTagCompound = {
        if (boxName != null && boxName != "") compound.setString("boxName", boxName)
        compound.setFloat("textureWidth", textureWidth)
        compound.setFloat("textureHeight", textureHeight)
        compound.setFloat("rotationPointX", rotationPointX)
        compound.setFloat("rotationPointY", rotationPointY)
        compound.setFloat("rotationPointZ", rotationPointZ)
        compound.setFloat("rotateAngleX", rotateAngleX)
        compound.setFloat("rotateAngleY", rotateAngleY)
        compound.setFloat("rotateAngleZ", rotateAngleZ)
        compound.setBoolean("mirror", mirror)
        compound.setBoolean("showModel", showModel)
        compound.setBoolean("isHidden", isHidden)
        compound.setFloat("offsetX", offsetX)
        compound.setFloat("offsetY", offsetY)
        compound.setFloat("offsetZ", offsetZ)
        val childList = new NBTTagList()
        if (childModels != null) {
            for (childModel <- childModels) {
                val modelPart = childModel.asInstanceOf[ModelPart]
                val compound1 = new NBTTagCompound()
                modelPart.writeToNBT(compound1)
                childList.appendTag(compound1)
            }
        }
        compound.setTag("childModels", childList)
        val boxList = new NBTTagList()
        if (cubeList != null) {
            for (cube <- cubeList) {
                val box = cube.asInstanceOf[ModelBox]
                val boxCompound = new NBTTagCompound()
                if (box.field_78247_g != null && box.field_78247_g != "") compound.setString("boxName", box.field_78247_g)
                boxCompound.setFloat("originX", box.posX1)
                boxCompound.setFloat("originY", box.posY1)
                boxCompound.setFloat("originZ", box.posZ1)
                boxCompound.setInteger("sizeX", (box.posX2 - box.posX1).toInt)
                boxCompound.setInteger("sizeY", (box.posY2 - box.posY1).toInt)
                boxCompound.setInteger("sizeZ", (box.posZ2 - box.posZ1).toInt)
                val quads = GIReflectionHelper.getField(box, VariableLib.ModelBoxQuadList).asInstanceOf[Array[TexturedQuad]]
                val quadList = new NBTTagList()
                for (quad <- quads) {
                    val vertexList = new NBTTagList()
                    val vertices = quad.vertexPositions
                    for (vertex <- vertices) {
                        val vertexCompound = new NBTTagCompound()
                        vertexCompound.setDouble("vectorX", vertex.vector3D.xCoord)
                        vertexCompound.setDouble("vectorY", vertex.vector3D.yCoord)
                        vertexCompound.setDouble("vectorZ", vertex.vector3D.zCoord)
                        vertexCompound.setFloat("texturePositionX", vertex.texturePositionX)
                        vertexCompound.setFloat("texturePositionY", vertex.texturePositionY)
                        vertexList.appendTag(vertexCompound)
                    }
                    val vertexListCompound = new NBTTagCompound()
                    vertexListCompound.setTag("vertexList", vertexList)
                    quadList.appendTag(vertexListCompound)
                }
                boxCompound.setTag("quadList", quadList)
                val vertexList = new NBTTagList()
                val vertices = GIReflectionHelper.getField(box, VariableLib.ModelBoxVertexPositions).asInstanceOf[Array[PositionTextureVertex]]
                for (vertex <- vertices) {
                    val vertexCompound = new NBTTagCompound()
                    vertexCompound.setDouble("vectorX", vertex.vector3D.xCoord)
                    vertexCompound.setDouble("vectorY", vertex.vector3D.yCoord)
                    vertexCompound.setDouble("vectorZ", vertex.vector3D.zCoord)
                    vertexCompound.setFloat("texturePositionX", vertex.texturePositionX)
                    vertexCompound.setFloat("texturePositionY", vertex.texturePositionY)
                    vertexList.appendTag(vertexCompound)
                }
                boxCompound.setTag("vertexList", vertexList)
                boxList.appendTag(boxCompound)
            }
        }
        compound.setTag("cubeList", boxList)

        compound.setString("modelPartType", modelPartTypeName)

        compound
    }

    override def readFromNBT(compound: NBTTagCompound): NBTTagCompound = {
        if (compound.hasKey("boxName")) GIReflectionHelper.setField(this, VariableLib.ModelRendererBoxName,
            compound.getString("boxName"))
        textureWidth = compound.getFloat("textureWidth")
        textureHeight = compound.getFloat("textureHeight")
        rotationPointX = compound.getFloat("rotationPointX")
        rotationPointY = compound.getFloat("rotationPointY")
        rotationPointZ = compound.getFloat("rotationPointZ")
        rotateAngleX = compound.getFloat("rotateAngleX")
        rotateAngleY = compound.getFloat("rotateAngleY")
        rotateAngleZ = compound.getFloat("rotateAngleZ")
        mirror = compound.getBoolean("mirror")
        showModel = compound.getBoolean("showModel")
        isHidden = compound.getBoolean("isHidden")
        offsetX = compound.getFloat("offsetX")
        offsetY = compound.getFloat("offsetY")
        offsetZ = compound.getFloat("offsetZ")

        val childList = compound.getTag("childModels").asInstanceOf[NBTTagList]

        for (i <- 0 until childList.tagCount()) {
            addChild(new ModelPart(childList.getCompoundTagAt(i)))
        }
        val boxList = compound.getTag("cubeList").asInstanceOf[NBTTagList]
        for (i <- 0 until boxList.tagCount()) {
            val boxCompound = boxList.getCompoundTagAt(i)

            val box = new ModelBox(this, GIReflectionHelper.getField(this, VariableLib.ModelRendererTextureOffsetX).asInstanceOf[java.lang.Integer], GIReflectionHelper.getField(this, VariableLib.ModelRendererTextureOffsetY).asInstanceOf[java.lang.Integer], boxCompound.getFloat("originX"), boxCompound.getFloat("originY"), boxCompound.getFloat("originZ"), boxCompound.getInteger("sizeX"), boxCompound.getInteger("sizeY"), boxCompound.getInteger("sizeZ"), 0.0F)

            val quadList = boxCompound.getTag("quadList").asInstanceOf[NBTTagList]
            val quads = Array.ofDim[TexturedQuad](quadList.tagCount())

            for (j <- 0 until quadList.tagCount()) {
                val vertexListCompound = quadList.getCompoundTagAt(j)

                val vertexList = vertexListCompound.getTagList("vertexList", Constants.NBT.TAG_COMPOUND)
                val vertices = Array.ofDim[PositionTextureVertex](vertexList.tagCount())

                for (k <- 0 until vertexList.tagCount()) {
                    val vertexCompound = vertexList.getCompoundTagAt(k)
                    val vector = Vec3.createVectorHelper(vertexCompound.getDouble("vectorX"), vertexCompound.getDouble("vectorY"), vertexCompound.getDouble("vectorZ"))

                    vertices(k) = new PositionTextureVertex(vector, vertexCompound.getFloat("texturePositionX"), vertexCompound.getFloat("texturePositionY"))
                }
                quads(j) = new TexturedQuad(vertices)
            }
            GIReflectionHelper.setField(box, VariableLib.ModelBoxQuadList, quads)

            val vertexList = boxCompound.getTagList("vertexList", Constants.NBT.TAG_COMPOUND)
            val vertices = Array.ofDim[PositionTextureVertex](vertexList.tagCount())

            for (k <- 0 until vertexList.tagCount()) {
                val vertexCompound = vertexList.getCompoundTagAt(k)
                val vector = Vec3.createVectorHelper(vertexCompound.getDouble("vectorX"), vertexCompound.getDouble("vectorY"), vertexCompound.getDouble("vectorZ"))

                vertices(k) = new PositionTextureVertex(vector, vertexCompound.getFloat("texturePositionX"), vertexCompound.getFloat("texturePositionY"))
            }
            GIReflectionHelper.setField(box, VariableLib.ModelBoxVertexPositions, vertices)
            cubeList.asInstanceOf[util.ArrayList[ModelBox]].add(box)
        }

        modelPartTypeName = compound.getString("modelPartType")
        modelPartType = SoulHelper.modelPartTypeRegistry.getModelPartType(modelPartTypeName).get

        compound
    }

    private val rand: Random = new Random()

    def mutate(): ModelPart = {
        rand.nextInt(2) match {
            case 0 =>
                rotationPointX = (rotationPointX * ((rand.nextFloat() * 2 * 0.1) + 0.9)).toFloat
                rotationPointY = (rotationPointY * ((rand.nextFloat() * 2 * 0.1) + 0.9)).toFloat
                rotationPointZ = (rotationPointZ * ((rand.nextFloat() * 2 * 0.1) + 0.9)).toFloat

            case 1 =>
                rotateAngleX = (rotateAngleX * ((rand.nextFloat() * 2 * 0.1) + 0.9)).toFloat
                rotateAngleY = (rotateAngleY * ((rand.nextFloat() * 2 * 0.1) + 0.9)).toFloat
                rotateAngleZ = (rotateAngleZ * ((rand.nextFloat() * 2 * 0.1) + 0.9)).toFloat
        }
        this
    }

    //noinspection ComparingUnrelatedTypes
    override def equals(obj: Any): Boolean = {
        if (obj.isInstanceOf[ModelPart]) {
            val part = obj.asInstanceOf[ModelPart]

            val compound1 = new NBTTagCompound()
            val compound2 = new NBTTagCompound()

            this.writeToNBT(compound1)
            part.writeToNBT(compound2)

            return compound1.equals(compound2)
        }
        false
    }

    def resetDisplayList() {
        if(GIReflectionHelper.getField(this, VariableLib.ModelRendererCompiled).asInstanceOf[Boolean]) {
            GLAllocation.deleteDisplayLists(GIReflectionHelper.getField(this, VariableLib.ModelRendererDisplayList).asInstanceOf[Int])
            GIReflectionHelper.setField(this, VariableLib.ModelRendererCompiled, false)
        }
    }
}