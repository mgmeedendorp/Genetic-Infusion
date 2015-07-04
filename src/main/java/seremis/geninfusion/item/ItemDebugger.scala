package seremis.geninfusion.item

import net.minecraft.entity.EntityLivingBase
import net.minecraft.entity.effect.EntityLightningBolt
import net.minecraft.entity.monster.{EntityCreeper, EntitySkeleton, EntityZombie}
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.item.ItemStack
import net.minecraft.world.World
import seremis.geninfusion.GeneticInfusion
import seremis.geninfusion.api.soul.SoulHelper
import seremis.geninfusion.entity.EntityClayGolem
import seremis.geninfusion.lib.Items

class ItemDebugger extends GIItem() {

    setHasSubtypes(true)
    setMaxDurability(0)
    setNumbersofMetadata(4)
    setUnlocalizedName(Items.DebuggerName)

    private val subNames: Array[String] = Array(Items.Debugger0Name, Items.Debugger1Name, Items.Debugger2Name, Items.Debugger3Name)

    override def getUnlocalizedName(itemstack: ItemStack): String = {
        getUnlocalizedName() + "." + subNames(itemstack.getMetadata)
    }

    override def onItemUse(stack: ItemStack, player: EntityPlayer, world: World, x: Int, y: Int, z: Int, side: Int, hitX: Float, hitY: Float, hitZ: Float): Boolean = {
        if (GeneticInfusion.commonProxy.isServerWorld(world)) {
            if (stack.getMetadata == 0) {
                val entity = new EntityClayGolem(world)
                entity.setPosition(x + 0.5F, y + 1, z + 0.5F)
                world.spawnEntityInWorld(entity)
            }
            if (stack.getMetadata == 1) {
                try {
                    val entity = SoulHelper.instanceHelper.getSoulEntityInstance(world, SoulHelper.standardSoulRegistry.getSoulForEntity(new EntityZombie(world)).get, x + 0.5F, y + 1, z + 0.5F).asInstanceOf[EntityLivingBase]
                    world.spawnEntityInWorld(entity)
                } catch {
                    case e: Exception => e.printStackTrace()
                }
            }
            if (stack.getMetadata == 2) {
                val entity = SoulHelper.instanceHelper.getSoulEntityInstance(world, SoulHelper.produceOffspring(SoulHelper.standardSoulRegistry.getSoulForEntity(new EntitySkeleton(world)).get, SoulHelper.standardSoulRegistry.getSoulForEntity(new EntityZombie(world)).get).get, x + 0.5F, y + 1, z + 0.5F).asInstanceOf[EntityLivingBase]
                world.spawnEntityInWorld(entity)
            }
            if (stack.getMetadata == 3) {
                val soul1 = SoulHelper.produceOffspring(SoulHelper.standardSoulRegistry.getSoulForEntity(new EntityZombie(world)).get, SoulHelper.geneRegistry.getSoulFor(new EntitySkeleton(world)).get).get
                val soul2 = SoulHelper.produceOffspring(soul1, SoulHelper.standardSoulRegistry.getSoulForEntity(new EntityCreeper(world)).get).get
                val soul3 = SoulHelper.produceOffspring(soul2, SoulHelper.standardSoulRegistry.getSoulForEntity(new EntityCreeper(world)).get).get
                val entity = SoulHelper.instanceHelper.getSoulEntityInstance(world, soul3, x + 0.5F, y + 1,  z + 0.5F).asInstanceOf[EntityLivingBase]

                world.spawnEntityInWorld(entity)
            }
        }
        if (stack.getMetadata == 3) {
            world.spawnEntityInWorld(new EntityLightningBolt(world, x, y, z))
            world.spawnEntityInWorld(new EntityLightningBolt(world, x, y, z))
            world.spawnEntityInWorld(new EntityLightningBolt(world, x, y, z))
            world.spawnEntityInWorld(new EntityLightningBolt(world, x, y, z))
            world.spawnEntityInWorld(new EntityLightningBolt(world, x, y, z))
        }
        true
    }
}