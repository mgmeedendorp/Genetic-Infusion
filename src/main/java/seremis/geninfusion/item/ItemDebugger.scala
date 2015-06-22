package seremis.geninfusion.item

import net.minecraft.entity.EntityLivingBase
import net.minecraft.entity.effect.EntityLightningBolt
import net.minecraft.entity.monster.{EntityCreeper, EntitySkeleton, EntityZombie}
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.item.ItemStack
import net.minecraft.world.World
import seremis.geninfusion.GeneticInfusion
import seremis.geninfusion.api.soul.SoulHelper
import seremis.geninfusion.lib.Items

class ItemDebugger extends GIItem() {

    setHasSubtypes(true)
    setMaxDurability(0)
    setNumbersofMetadata(4)
    setUnlocalizedName(Items.DEBUGGER_UNLOCALIZED_NAME)

    private val subNames: Array[String] = Array(Items.DEBUGGER_META_0_UNLOCALIZED_NAME, Items.DEBUGGER_META_1_UNLOCALIZED_NAME, Items.DEBUGGER_META_2_UNLOCALIZED_NAME, Items.DEBUGGER_META_3_UNLOCALIZED_NAME)

    override def getUnlocalizedName(itemstack: ItemStack): String = {
        getUnlocalizedName() + "." + subNames(itemstack.getMetadata)
    }

    override def onItemUse(stack: ItemStack, player: EntityPlayer, world: World, x: Int, y: Int, z: Int, side: Int, hitX: Float, hitY: Float, hitZ: Float): Boolean = {
        if (GeneticInfusion.commonProxy.isServerWorld(world)) {
            if (stack.getMetadata == 0) {

            }
            if (stack.getMetadata == 1) {
                try {
                    val entity = SoulHelper.instanceHelper.getSoulEntityInstance(world, SoulHelper.standardSoulRegistry.getSoulForEntity(new EntityZombie(world)), x + 0.5F, y + 1, z + 0.5F).asInstanceOf[EntityLivingBase]
                    world.spawnEntityInWorld(entity)
                } catch {
                    case e: Exception => e.printStackTrace()
                }
            }
            if (stack.getMetadata == 2) {
                val entity = SoulHelper.instanceHelper.getSoulEntityInstance(world, SoulHelper.produceOffspring(SoulHelper.standardSoulRegistry.getSoulForEntity(new EntitySkeleton(world)), SoulHelper.standardSoulRegistry.getSoulForEntity(new EntityZombie(world))).get, x + 0.5F, y + 1, z + 0.5F).asInstanceOf[EntityLivingBase]
                world.spawnEntityInWorld(entity)
            }
            if (stack.getMetadata == 3) {
                val soul1 = SoulHelper.produceOffspring(SoulHelper.standardSoulRegistry.getSoulForEntity(new EntityZombie(world)), SoulHelper.geneRegistry.getSoulFor(new EntitySkeleton(world))).get
                val soul2 = SoulHelper.produceOffspring(soul1, SoulHelper.standardSoulRegistry.getSoulForEntity(new EntityCreeper(world))).get
                val soul3 = SoulHelper.produceOffspring(soul2, SoulHelper.standardSoulRegistry.getSoulForEntity(new EntityCreeper(world))).get
                val entity = SoulHelper.instanceHelper.getSoulEntityInstance(world, soul3, x + 0.5F, y + 1,  z + 0.5F).asInstanceOf[EntityLivingBase]

                world.spawnEntityInWorld(entity)
            }
        }
        if (stack.getMetadata == 3) {
            world.spawnEntityInWorld(new EntityLightningBolt(world, x, y, z))
        }
        true
    }
}