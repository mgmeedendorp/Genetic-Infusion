package seremis.geninfusion.util

import java.util.Random

import net.minecraft.entity.item.EntityItem
import net.minecraft.inventory.IInventory
import net.minecraft.item.{Item, ItemStack}
import net.minecraft.nbt.NBTTagCompound
import net.minecraft.world.World
import seremis.geninfusion.GeneticInfusion
import seremis.geninfusion.api.util.Coordinate3D

object UtilBlock {

    def getBlockCoordsAtSide(x: Int, y: Int, z: Int, side: Int): Coordinate3D = {
        val coord = new Coordinate3D(x, y, z)
        val shiftMap = Array(Array(0, -1, 0), Array(0, 1, 0), Array(0, 0, -1), Array(0, 0, 1), Array(-1, 0, 0), Array(1, 0, 0))

        val shiftx = shiftMap(side)(0)
        val shifty = shiftMap(side)(1)
        val shiftz = shiftMap(side)(2)

        coord.move(shiftx, shifty, shiftz)
    }

    def dropItemInWorld(x: Int, y: Int, z: Int, world: World, stack: ItemStack): EntityItem = {
        val rand = new Random()

        val rx = rand.nextFloat() * 0.8F + 0.5F
        val ry = rand.nextFloat() * 0.8F + 0.5F
        val rz = rand.nextFloat() * 0.8F + 0.5F

        val entityItem = new EntityItem(world, x + rx, y + ry, z + rz, new ItemStack(stack.getItem, stack.stackSize, stack.getMetadata))

        if (stack.hasTagCompound) {
            entityItem.getEntityItem.setTagCompound(stack.getTagCompound.copy().asInstanceOf[NBTTagCompound])
        }

        val factor = 0.005F
        entityItem.motionX = rand.nextGaussian() * factor
        entityItem.motionY = rand.nextGaussian() * factor + 0.2F
        entityItem.motionZ = rand.nextGaussian() * factor
        world.spawnEntityInWorld(entityItem)

        stack.stackSize = 0
        entityItem
    }

    def dropItemInWorld(x: Int, y: Int, z: Int, world: World, item: Item, amount: Int): EntityItem = {
        dropItemInWorld(x, y, z, world, new ItemStack(item, amount))
    }

    def dropItemInWorld(x: Int, y: Int, z: Int, world: World, item: Item, amount: Int, metadata: Int): EntityItem = {
        dropItemInWorld(x, y, z, world, new ItemStack(item, amount, metadata))
    }

    def dropItemsFromTile(world: World, x: Int, y: Int, z: Int) {
        val tileEntity = world.getTileEntity(x, y, z)
        if (!tileEntity.isInstanceOf[IInventory] || GeneticInfusion.commonProxy.isRenderWorld(tileEntity.getWorld)) return

        val inventory = tileEntity.asInstanceOf[IInventory]

        for (i <- 0 until inventory.getSizeInventory) {
            dropItemsFromTile(world, x, y, z, i)
        }
    }

    def dropItemsFromTile(world: World, x: Int, y: Int, z: Int, slot: Int) {
        val tileEntity = world.getTileEntity(x, y, z)

        if (!tileEntity.isInstanceOf[IInventory] || GeneticInfusion.commonProxy.isRenderWorld(tileEntity.getWorld)) return

        val inventory = tileEntity.asInstanceOf[IInventory]
        val stack = inventory.getStackInSlot(slot)

        val rand = new Random()

        if (stack != null && stack.stackSize > 0) {
            dropItemInWorld(x, y, z, world, stack)
        }
    }
}