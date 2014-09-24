package seremis.geninfusion.soul.entity

import java.io.IOException
import java.lang.reflect.Field
import java.util
import java.util.Random
import java.util.HashMap

import cpw.mods.fml.common.registry.IEntityAdditionalSpawnData
import cpw.mods.fml.relauncher.ReflectionHelper
import io.netty.buffer.ByteBuf
import net.minecraft.entity._
import net.minecraft.entity.ai.EntityAITasks
import net.minecraft.entity.ai.attributes.BaseAttributeMap
import net.minecraft.item.ItemStack
import net.minecraft.nbt.{CompressedStreamTools, NBTSizeTracker, NBTTagCompound, NBTTagList}
import net.minecraft.potion.PotionEffect
import net.minecraft.util.{AxisAlignedBB, CombatTracker, DamageSource}
import net.minecraft.world.World
import net.minecraftforge.common.ForgeHooks
import net.minecraftforge.common.util.Constants
import seremis.geninfusion.api.soul.lib.Genes
import seremis.geninfusion.api.soul.{IEntitySoulCustom, ISoul, SoulHelper}
import seremis.geninfusion.entity.GIEntityLiving
import seremis.geninfusion.helper.DataHelper
import seremis.geninfusion.misc.Data
import seremis.geninfusion.soul.allele.{AlleleFloat, AlleleString}
import seremis.geninfusion.soul.{Soul, TraitHandler}

import scala.collection.{JavaConverters, JavaConversions}

class EntitySoulCustom(world: World) extends GIEntityLiving(world) with IEntitySoulCustom with IEntityAdditionalSpawnData {

  def this(world: World, soul: ISoul, x: Double, y: Double, z: Double) {
    this(world)
    setPosition(x, y, z)
    setSize(0.8F, 1.7F)
    this.soul = soul
  }

  var soul: ISoul = null

  override def getSoul: ISoul = soul

  override def writeSpawnData(data: ByteBuf) {
    val compound: NBTTagCompound = new NBTTagCompound
    writeToNBT(compound)
    var abyte: Array[Byte] = null
    try {
      abyte = CompressedStreamTools.compress(compound)
    }
    catch {
      case e: Exception => e.printStackTrace()
        return
    }
    data.writeShort(abyte.length.asInstanceOf[Short])
    data.writeBytes(abyte)
  }

  override def readSpawnData(data: ByteBuf) {
    val short1: Short = data.readShort
    val abyte: Array[Byte] = new Array[Byte](short1)
    data.readBytes(abyte)
    var compound: NBTTagCompound = null
    try {
      compound = CompressedStreamTools.func_152457_a(abyte, NBTSizeTracker.field_152451_a)
    }
    catch {
      case e: IOException =>
        e.printStackTrace()
        return
    }
    readFromNBT(compound)
  }

  override def getWorld: World = {
    worldObj
  }

  override def getBoundingBox: AxisAlignedBB = {
    boundingBox
  }

  override def getEntityId: Int = {
    super.getEntityId
  }

  override def getAttributeMap: BaseAttributeMap = {
    super.getAttributeMap
  }

  override def getCombatTracker: CombatTracker = {
    super.func_110142_aN
  }

  private var creatureAttribute: EnumCreatureAttribute = null

  override def getCreatureAttribute: EnumCreatureAttribute = {
    creatureAttribute
  }

  override def getActivePotionsMap: util.HashMap[Integer, PotionEffect] = {
    var `var`: util.HashMap[Integer, PotionEffect] = new util.HashMap[Integer, PotionEffect]
    try {
      val field: Field = ReflectionHelper.findField(classOf[Entity], "activePotionsMap", "field_70147_f")
      field.setAccessible(true)
      `var` = field.get(this).asInstanceOf[util.HashMap[Integer, PotionEffect]]
      field.setAccessible(false)
    }
    catch {
      case e: Exception =>
        e.printStackTrace()
    }
    `var`
  }

  override def getDataWatcher: DataWatcher = {
    super.getDataWatcher()
  }

  override def getTasks: EntityAITasks = {
    tasks
  }

  override def getTargetTasks: EntityAITasks = {
    targetTasks
  }

  override def getLeashedCompound: NBTTagCompound = {
    var `var`: NBTTagCompound = new NBTTagCompound
    try {
      val field: Field = ReflectionHelper.findField(classOf[Entity], "field_110170_bx", "field_110170_bx")
      field.setAccessible(true)
      `var` = field.get(this).asInstanceOf[NBTTagCompound]
      field.setAccessible(false)
    }
    catch {
      case e: Exception =>
        e.printStackTrace()
    }
    `var`
  }

  override def onDeathUpdate {
    super.onDeathUpdate()
  }

  override def setFlag(id: Int, value: Boolean) {
    super.setFlag(id, value)
  }

  override def getFlag(id: Int): Boolean = {
    super.getFlag(id)
  }

  override def getRandom: Random = {
    rand
  }

  override def isChild: Boolean = {
    false
    //TODO this
  }

  private var talkInterval: Int = 0

  override def getTalkInterval: Int = {
    talkInterval
  }

  override def getDeathSound: String = {
    SoulHelper.geneRegistry.getActiveFor(this.asInstanceOf[IEntitySoulCustom], Genes.GENE_DEATH_SOUND).asInstanceOf[AlleleString].value
  }

  override def getLivingSound: String = {
    SoulHelper.geneRegistry.getActiveFor(this.asInstanceOf[IEntitySoulCustom], Genes.GENE_LIVING_SOUND).asInstanceOf[AlleleString].value
  }

  override def getHurtSound: String = {
    SoulHelper.geneRegistry.getActiveFor(this.asInstanceOf[IEntitySoulCustom], Genes.GENE_HURT_SOUND).asInstanceOf[AlleleString].value
  }

  override def getSplashSound: String = {
    SoulHelper.geneRegistry.getActiveFor(this.asInstanceOf[IEntitySoulCustom], Genes.GENE_SPLASH_SOUND).asInstanceOf[AlleleString].value
  }

  override def getSwimSound: String = {
    SoulHelper.geneRegistry.getActiveFor(this.asInstanceOf[IEntitySoulCustom], Genes.GENE_SWIM_SOUND).asInstanceOf[AlleleString].value
  }

  override def getSoundVolume: Float = {
    SoulHelper.geneRegistry.getActiveFor(this.asInstanceOf[IEntitySoulCustom], Genes.GENE_SOUND_VOLUME).asInstanceOf[AlleleFloat].value
  }

  override def applyEntityAttributes {
    this.getAttributeMap.registerAttribute(SharedMonsterAttributes.maxHealth)
    this.getAttributeMap.registerAttribute(SharedMonsterAttributes.knockbackResistance)
    this.getAttributeMap.registerAttribute(SharedMonsterAttributes.movementSpeed)
    this.getAttributeMap.registerAttribute(SharedMonsterAttributes.attackDamage)
    this.getAttributeMap.registerAttribute(SharedMonsterAttributes.followRange)
    getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(20.0D)
  }

  private var firstUpdate: Boolean = true

  override def onUpdate {
    syncVariables
    if (ForgeHooks.onLivingUpdate(this)) return
    if (firstUpdate) {
      firstUpdate = false
      TraitHandler.firstTick(this)
    }
    TraitHandler.entityUpdate(this)
  }

  override def onDeath(source: DamageSource) {
    if (ForgeHooks.onLivingDeath(this, source)) return
    if (source.getEntity != null) {
      source.getEntity.onKillEntity(this)
    }
    TraitHandler.entityDeath(this, source)
  }

  override def onKillEntity(entity: EntityLivingBase) {
    TraitHandler.onKillEntity(this, entity)
  }

  override def attackEntityFrom(source: DamageSource, damage: Float): Boolean = {
    !ForgeHooks.onLivingAttack(this, source, damage) && TraitHandler.attackEntityFrom(this, source, damage)
  }

  override def attackEntity(entity: Entity, distance: Float) {
    TraitHandler.attackEntity(this, entity, distance);
  }

  override def damageEntity(source: DamageSource, damage: Float) {
    TraitHandler.damageEntity(this, source, damage)
  }

  override def onSpawnWithEgg(data: IEntityLivingData): IEntityLivingData = {
    TraitHandler.spawnEntityFromEgg(this, data)
  }

  override def playSound(name: String, volume: Float, pitch: Float) {
    TraitHandler.playSoundAtEntity(this, name, volume, pitch)
  }

  override def updateAITick {
    TraitHandler.updateAITick(this)
  }

  override def attackEntityAsMob(entity: Entity): Boolean = {
    TraitHandler.attackEntityAsMob(this, entity)
  }

  override def readFromNBT(compound: NBTTagCompound) {
    super.readFromNBT(compound)
    soul = new Soul(compound)
    if (compound.hasKey("traitVariables")) {
      val tagList: NBTTagList = compound.getTagList("traitVariables", Constants.NBT.TAG_COMPOUND)
      var compoundBoolean: NBTTagCompound = null
      var compoundByte: NBTTagCompound = null
      var compoundInteger: NBTTagCompound = null
      var compoundFloat: NBTTagCompound = null
      var compoundDouble: NBTTagCompound = null
      var compoundString: NBTTagCompound = null
      var compoundItemStack: NBTTagCompound = null
      for (i <- 0 until tagList.tagCount()) {
        if (tagList.getCompoundTagAt(i).getString("type") == "boolean") {
          compoundBoolean = tagList.getCompoundTagAt(i)
        }
        else if (tagList.getCompoundTagAt(i).getString("type") == "byte") {
          compoundByte = tagList.getCompoundTagAt(i)
        }
        else if (tagList.getCompoundTagAt(i).getString("type") == "integer") {
          compoundInteger = tagList.getCompoundTagAt(i)
        }
        else if (tagList.getCompoundTagAt(i).getString("type") == "float") {
          compoundFloat = tagList.getCompoundTagAt(i)
        }
        else if (tagList.getCompoundTagAt(i).getString("type") == "double") {
          compoundDouble = tagList.getCompoundTagAt(i)
        }
        else if (tagList.getCompoundTagAt(i).getString("type") == "string") {
          compoundString = tagList.getCompoundTagAt(i)
        }
        else if (tagList.getCompoundTagAt(i).getString("type") == "itemStack") {
          compoundItemStack = tagList.getCompoundTagAt(i)
        }
      }
      if (compoundBoolean != null) {
        val size: Int = compoundBoolean.getInteger("size")
        for (i <- 0 until size) {
          val name: String = compoundBoolean.getString("boolean" + i + "Name")
          val value: Boolean = compoundBoolean.getBoolean("boolean" + i + "Value")
          persistentBoolean.put(name, value)
        }
      }
      if (compoundByte != null) {
        val size: Int = compoundByte.getInteger("size")
        for (i <- 0 until size) {
          val name: String = compoundByte.getString("byte" + i + "Name")
          val value: Byte = compoundByte.getByte("byte" + i + "Value")
          persistentByte.put(name, value)
        }
      }
      if (compoundInteger != null) {
        val size: Int = compoundInteger.getInteger("size")
        for (i <- 0 until size) {
          val name: String = compoundInteger.getString("integer" + i + "Name")
          val value: Int = compoundInteger.getInteger("integer" + i + "Value")
          persistentInteger.put(name, value)
        }
      }
      if (compoundFloat != null) {
        val size: Int = compoundFloat.getInteger("size")
        for (i <- 0 until size) {
          val name: String = compoundFloat.getString("float" + i + "Name")
          val value: Float = compoundFloat.getFloat("float" + i + "Value")
          persistentFloat.put(name, value)
        }
      }
      if (compoundDouble != null) {
        val size: Int = compoundDouble.getInteger("size")
        for (i <- 0 until size) {
          val name: String = compoundDouble.getString("double" + i + "Name")
          val value: Double = compoundDouble.getDouble("double" + i + "Value")
          persistentDouble.put(name, value)
        }
      }
      if (compoundString != null) {
        val size: Int = compoundString.getInteger("size")
        for (i <- 0 until size) {
          val name: String = compoundString.getString("string" + i + "Name")
          val value: String = compoundString.getString("string" + i + "Value")
          persistentString.put(name, value)
        }
      }
      if (compoundItemStack != null) {
        val size: Int = compoundItemStack.getInteger("size")
        for (i <- 0 until size) {
          val name: String = compoundItemStack.getString("itemStack" + i + "Name")
          val stackCompound: NBTTagCompound = compoundItemStack.getCompoundTag("itemStack" + i + "Value")
          val stack: ItemStack = ItemStack.loadItemStackFromNBT(stackCompound)
          persistentItemStack.put(name, stack)
        }
      }
    }
  }

  override def writeToNBT(compound: NBTTagCompound) {
    super.writeToNBT(compound)
    soul.writeToNBT(compound)
    if (persistentBoolean != null) {
      val tagList = new NBTTagList()
      val stringList = new util.ArrayList[String]()
      if (!persistentBoolean.isEmpty) {
        stringList.addAll(persistentBoolean.keySet)
        val booleanCompound = new NBTTagCompound()
        for (i <- 0 until persistentBoolean.size) {
          booleanCompound.setString("boolean" + i + "Name", stringList.get(i))
          booleanCompound.setBoolean("boolean" + i + "Value", persistentBoolean.get(stringList.get(i)))
        }
        booleanCompound.setString("type", "boolean")
        booleanCompound.setInteger("size", persistentBoolean.size)
        tagList.appendTag(booleanCompound)
        stringList.clear()
      }
      if (!persistentByte.isEmpty) {
        stringList.addAll(persistentByte.keySet)
        val byteCompound = new NBTTagCompound()
        for (i <- 0 until persistentByte.size) {
          byteCompound.setString("byte" + i + "Name", stringList.get(i))
          byteCompound.setByte("byte" + i + "Value", persistentByte.get(stringList.get(i)))
        }
        byteCompound.setString("type", "byte")
        byteCompound.setInteger("size", persistentByte.size)
        tagList.appendTag(byteCompound)
        stringList.clear()
      }
      if (!persistentInteger.isEmpty) {
        stringList.addAll(persistentInteger.keySet)
        val integerCompound = new NBTTagCompound()
        for (i <- 0 until persistentInteger.size) {
          integerCompound.setString("integer" + i + "Name", stringList.get(i))
          integerCompound.setInteger("integer" + i + "Value", persistentInteger.get(stringList.get(i)))
        }
        integerCompound.setString("type", "integer")
        integerCompound.setInteger("size", persistentInteger.size)
        tagList.appendTag(integerCompound)
        stringList.clear()
      }
      if (!persistentFloat.isEmpty) {
        stringList.addAll(persistentFloat.keySet)
        val floatCompound = new NBTTagCompound()
        for (i <- 0 until persistentFloat.size) {
          floatCompound.setString("float" + i + "Name", stringList.get(i))
          floatCompound.setFloat("float" + i + "Value", persistentFloat.get(stringList.get(i)))
        }
        floatCompound.setString("type", "float")
        floatCompound.setInteger("size", persistentFloat.size)
        stringList.clear()
      }
      if (!persistentDouble.isEmpty) {
        stringList.addAll(persistentDouble.keySet)
        val doubleCompound = new NBTTagCompound()
        for (i <- 0 until persistentDouble.size) {
          doubleCompound.setString("double" + i + "Name", stringList.get(i))
          doubleCompound.setDouble("double" + i + "Value", persistentDouble.get(stringList.get(i)))
        }
        doubleCompound.setString("type", "double")
        doubleCompound.setInteger("size", persistentDouble.size)
        stringList.clear()
      }
      if (!persistentString.isEmpty) {
        stringList.addAll(persistentString.keySet)
        val stringCompound = new NBTTagCompound()
        for (i <- 0 until persistentString.size) {
          stringCompound.setString("string" + i + "Name", stringList.get(i))
          stringCompound.setString("string" + i + "Value", persistentString.get(stringList.get(i)))
        }
        stringCompound.setString("type", "string")
        stringCompound.setInteger("size", persistentString.size)
        stringList.clear()
      }
      if (!persistentItemStack.isEmpty) {
        stringList.addAll(persistentItemStack.keySet)
        val itemStackCompound = new NBTTagCompound()
        for (i <- 0 until persistentItemStack.size) {
          val stacks = new util.ArrayList[ItemStack](persistentItemStack.values)
          if (stacks.get(i) != null && stacks.get(i).getItem != null &&
            stacks.get(i).stackSize > 0) {
            itemStackCompound.setString("itemStack" + i + "Name", stringList.get(i))
            val compoundStack = new NBTTagCompound()
            itemStackCompound.setTag("itemStack" + i + "Value", stacks.get(i).writeToNBT(compoundStack))
          }
        }
        itemStackCompound.setString("type", "itemStack")
        itemStackCompound.setInteger("size", persistentString.size)
        stringList.clear()
      }
      compound.setTag("traitVariables", tagList)
    }
  }

  protected val persistentBoolean, variableBoolean: HashMap[String, Boolean] = new HashMap[String, Boolean]
  protected val persistentByte, variableByte: HashMap[String, Byte] = new HashMap[String, Byte]
  protected val persistentInteger, variableInteger: HashMap[String, Integer] = new HashMap[String, Integer]
  protected val persistentFloat, variableFloat: HashMap[String, Float] = new HashMap[String, Float]
  protected val persistentDouble, variableDouble: HashMap[String, Double] = new HashMap[String, Double]
  protected val persistentString, variableString: HashMap[String, String] = new HashMap[String, String]
  protected val persistentItemStack, variableItemStack: HashMap[String, ItemStack] = new HashMap[String, ItemStack]

  override def setPersistentVariable(name: String, variable: Boolean) {
    persistentBoolean.put(name, variable)
  }

  override def setPersistentVariable(name: String, variable: Byte) {
    persistentByte.put(name, variable)
  }

  override def setPersistentVariable(name: String, variable: Int) {
    persistentInteger.put(name, variable)
  }

  override def setPersistentVariable(name: String, variable: Float) {
    persistentFloat.put(name, variable)
  }

  override def setPersistentVariable(name: String, variable: Double) {
    persistentDouble.put(name, variable)
  }

  override def setPersistentVariable(name: String, variable: String) {
    persistentString.put(name, variable)
  }

  override def setPersistentVariable(name: String, variable: ItemStack) {
    persistentItemStack.remove(name)
    if (variable != null) persistentItemStack.put(name, variable)
  }

  override def getPersistentBoolean(name: String): Boolean = {
    if (!persistentBoolean.containsKey(name)) false else persistentBoolean.get(name)
  }

  override def getPersistentByte(name: String): Byte = {
    if (!persistentByte.containsKey(name)) 0 else persistentByte.get(name)
  }

  override def getPersistentInteger(name: String): Int = {
    if (!persistentInteger.containsKey(name)) 0 else persistentInteger.get(name)
  }

  override def getPersistentFloat(name: String): Float = {
    if (!persistentFloat.containsKey(name)) 0 else persistentFloat.get(name)
  }

  override def getPersistentDouble(name: String): Double = {
    if (!persistentDouble.containsKey(name)) 0 else persistentDouble.get(name)
  }

  override def getPersistentString(name: String): String = {
    if (!persistentString.containsKey(name)) null else persistentString.get(name)
  }

  override def getPersistentItemStack(name: String): ItemStack = {
    if (!persistentItemStack.containsKey(name)) null else persistentItemStack.get(name)
  }

  override def setVariable(name: String, variable: Boolean) {
    variableBoolean.put(name, variable)
  }

  override def setVariable(name: String, variable: Byte) {
    variableByte.put(name, variable)
  }

  override def setVariable(name: String, variable: Int) {
    variableInteger.put(name, variable)
  }

  override def setVariable(name: String, variable: Float) {
    variableFloat.put(name, variable)
  }

  override def setVariable(name: String, variable: Double) {
    variableDouble.put(name, variable)
  }

  override def setVariable(name: String, variable: String) {
    variableString.put(name, variable)
  }

  override def setVariable(name: String, variable: ItemStack) {
    variableItemStack.put(name, variable)
  }

  override def getBoolean(name: String): Boolean = {
    if (!variableBoolean.containsKey(name)) false else variableBoolean.get(name)
  }

  override def getByte(name: String): Byte = {
    if (!variableByte.containsKey(name)) 0 else variableByte.get(name)
  }

  override def getInteger(name: String): Int = {
    if (!variableInteger.containsKey(name)) 0 else variableInteger.get(name)
  }

  override def getFloat(name: String): Float = {
    if (!variableFloat.containsKey(name)) 0 else variableFloat.get(name)
  }

  override def getDouble(name: String): Double = {
    if (!variableDouble.containsKey(name)) 0 else variableDouble.get(name)
  }

  override def getString(name: String): String = {
    if (!variableString.containsKey(name)) null else variableString.get(name)
  }

  override def getItemStack(name: String): ItemStack = {
    if (!variableItemStack.containsKey(name)) null else variableItemStack.get(name)
  }

  override def forceVariableSync() {
    this.syncVariables
  }

  //The variables as they were the last tick
  protected var oldData: Data = DataHelper.writePrimitives(this)
  //The variables in the custom system
  protected var newData: Data = new Data()

  def syncVariables() {
    //The variables in the entity class
    val data = DataHelper.writePrimitives(this)

    newData.booleanDataMap.putAll(persistentBoolean)
    newData.booleanDataMap.putAll(variableBoolean)

    val clss = this.getClass

    for ((key, value) <- data) {
      if(oldData.booleanDataMap.containsKey(key) && oldData.booleanDataMap.get(key) != value) {
        oldData.booleanDataMap.put(key, value)
        clss.getField(key).setAccessible(true)
        clss.getField(key).setBoolean(this, value)
      }
    }


    oldData = data;
    //TODO save forge custom nbt data
  }
}
