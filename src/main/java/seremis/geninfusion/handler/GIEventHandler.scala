package seremis.geninfusion.handler

import cpw.mods.fml.common.eventhandler.SubscribeEvent
import net.minecraft.entity.EntityLiving
import net.minecraft.tileentity.TileEntity
import net.minecraftforge.event.entity.EntityJoinWorldEvent
import net.minecraftforge.event.entity.living.LivingDeathEvent
import net.minecraftforge.event.world.WorldEvent
import seremis.geninfusion.api.soul.{IEntitySoulCustom, ISoulReceptor, SoulHelper}
import seremis.geninfusion.api.util.Coordinate3D
import seremis.geninfusion.lib.DefaultProps
import seremis.geninfusion.soul.entity.{EntitySoulCustom, EntitySoulCustomCreature}
import seremis.geninfusion.world.GIWorldSavedData

import scala.collection.mutable.ListBuffer

class GIEventHandler {

    @SubscribeEvent
    def joinWorld(event: EntityJoinWorldEvent) {
        if (event.entity.isInstanceOf[EntitySoulCustom]) {
            println(event.entity + " server? " + !event.world.isRemote)
        } else if (event.entity.isInstanceOf[EntitySoulCustomCreature]) {
            println(event.entity + " server? " + !event.world.isRemote)
        }
    }

    @SubscribeEvent
    def loadWorld(event: WorldEvent.Load) {
        event.world.perWorldStorage.setData(DefaultProps.ID, new GIWorldSavedData())
    }

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

                if(living.isInstanceOf[IEntitySoulCustom])
                    living.asInstanceOf[IEntitySoulCustom].setSoulPreserved(true)
            })
        }
    }
}