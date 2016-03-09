package seremis.geninfusion.handler

import net.minecraft.entity.EntityLiving
import net.minecraft.init.Blocks
import net.minecraft.item.ItemStack
import net.minecraft.tileentity.TileEntity
import net.minecraft.world.World
import net.minecraftforge.event.entity.living.LivingDeathEvent
import net.minecraftforge.event.world.BlockEvent.PlaceEvent
import seremis.geninfusion.api.soul.{ISoulReceptor, SoulHelper}
import seremis.geninfusion.api.util.vector.Coordinate3D
import seremis.geninfusion.entity.EntityClayGolem

import scala.collection.mutable.ListBuffer

class GIEventHandler {

    @SubscribeEvent
    def entityDeath(event: LivingDeathEvent) {
        if(!event.entityLiving.worldObj.isRemote && event.entityLiving.isInstanceOf[EntityLiving] && SoulHelper.geneRegistry.getSoulFor(event.entityLiving.asInstanceOf[EntityLiving]).nonEmpty) {
            val living = event.entityLiving.asInstanceOf[EntityLiving]
            val world = event.entityLiving.worldObj
            val coords = new Coordinate3D(living)

            val radius = 5

            val receptors: ListBuffer[ISoulReceptor] = ListBuffer()

            val minX = coords.x.toInt - radius
            val maxX = coords.x.toInt + radius
            val minY = coords.y.toInt - radius
            val maxY = coords.y.toInt + radius
            val minZ = coords.z.toInt - radius
            val maxZ = coords.z.toInt + radius

            for(x <- minX until maxX; y <- minY until maxY; z <- minZ until maxZ) {
                val tile = world.getTileEntity(x, y, z)

                if(tile.isInstanceOf[ISoulReceptor]) {
                    receptors += tile.asInstanceOf[ISoulReceptor]
                }
            }

            var distance = Double.PositiveInfinity
            var closest: Option[ISoulReceptor] = None

            for(receptor <- receptors if !receptor.hasSoul) {
                val tile = receptor.asInstanceOf[TileEntity]
                val dist = living.getDistanceSq(tile.xCoord, tile.yCoord, tile.zCoord)

                if(dist < distance) {
                    distance = dist
                    closest = Some(receptor)
                }
            }

            closest.foreach(closest => {
                val closestTile = closest.asInstanceOf[TileEntity]

                closest.setSoul(SoulHelper.geneRegistry.getSoulFor(living))

                world.markBlockForUpdate(closestTile.xCoord, closestTile.yCoord, closestTile.zCoord)
                closestTile.markDirty()
            })
        }
    }

    @SubscribeEvent
    def placeBlockEvent(event: PlaceEvent) {
        if(!event.player.worldObj.isRemote && event.placedBlock == Blocks.pumpkin || event.placedBlock == Blocks.lit_pumpkin) {
            val world = event.player.worldObj

            val x = event.blockSnapshot.x
            val y = event.blockSnapshot.y
            val z = event.blockSnapshot.z

            val clay1 = isBlockHardenedClay(world, x, y - 1, z)
            val clay2 = isBlockHardenedClay(world, x - 1, y - 1, z)
            val clay25 = isBlockHardenedClay(world, x, y - 1, z - 1)
            val clay3 = isBlockHardenedClay(world, x + 1, y - 1, z)
            val clay35 = isBlockHardenedClay(world, x, y -1, z + 1)
            val clay4 = isBlockHardenedClay(world, x, y -2, z)

            if(clay1 && ((clay2 && clay3) ^ (clay25 && clay35)) && clay4) {
                val entity = new EntityClayGolem(world)
                entity.setPosition(x + 0.5F, y - 2, z + 0.5F)
                entity.setClayAtCreation(0, getStackAtPosition(world, x, y - 2, z))
                entity.setClayAtCreation(1, getStackAtPosition(world, x, y - 1, z))

                world.setBlock(x, y, z, Blocks.air)
                world.setBlock(x, y - 1, z, Blocks.air)
                world.setBlock(x, y - 2, z, Blocks.air)
                if(clay2 && clay3) {
                    entity.setClayAtCreation(2, getStackAtPosition(world, x + 1, y - 1, z))
                    entity.setClayAtCreation(3, getStackAtPosition(world, x - 1, y - 1, z))
                    world.setBlock(x - 1, y - 1, z, Blocks.air)
                    world.setBlock(x + 1, y - 1, z, Blocks.air)
                } else if(clay25 && clay35) {
                    entity.setClayAtCreation(2, getStackAtPosition(world, x, y - 1, z + 1))
                    entity.setClayAtCreation(3, getStackAtPosition(world, x, y - 1, z - 1))
                    world.setBlock(x, y - 1, z + 1, Blocks.air)
                    world.setBlock(x, y - 1, z - 1, Blocks.air)
                    entity.rotationYaw = 90
                }

                world.spawnEntityInWorld(entity)
            }
        }
    }

    def isBlockHardenedClay(world: World, x: Int, y: Int, z: Int): Boolean = {
        world.getBlock(x, y, z) == Blocks.hardened_clay || world.getBlock(x, y, z) == Blocks.stained_hardened_clay
    }

    def getStackAtPosition(world: World, x: Int, y: Int, z: Int): ItemStack = {
        new ItemStack(world.getBlock(x, y, z), 1, world.getBlockMetadata(x, y, z))
    }
}