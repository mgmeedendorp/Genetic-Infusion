package seremis.geninfusion.core

import cpw.mods.fml.relauncher.{Side, SideOnly}
import net.minecraft.creativetab.CreativeTabs
import net.minecraft.entity.monster.{EntitySkeleton, EntityCreeper, EntityZombie}
import net.minecraft.item.{ItemStack, Item}
import seremis.geninfusion.api.soul.SoulHelper
import seremis.geninfusion.block.ModBlocks

class GICreativeTab(name: String) extends CreativeTabs(name) {

    @SideOnly(Side.CLIENT)
    override def getTabIconItem: Item = Item.getItemFromBlock(ModBlocks.crystal)

    @SideOnly(Side.CLIENT)
    override def displayAllReleventItems(list: java.util.List[_]) {
        super.displayAllReleventItems(list)

        val entities = Array(new EntityZombie(null), new EntitySkeleton(null), new EntityCreeper(null))

        for(entity <- entities) {
            val stack = new ItemStack(ModBlocks.crystal)
            val soul = SoulHelper.standardSoulRegistry.getSoulForEntity(entity)

//            soul.get.writeToNBT(stack.getTagCompound)
//            list.asInstanceOf[java.util.List[ItemStack]].add(stack)
        }
    }
}