package seremis.geninfusion.api.soul.util

import net.minecraft.crash.CrashReport
import net.minecraft.entity.DataWatcher
import net.minecraft.nbt.NBTTagCompound
import util.control.Breaks._


object DataWatcherHelper {

    var ids: Map[DataWatcher, Map[String, Int]] = Map()
    var names: Map[DataWatcher, Map[Int, String]] = Map()

    /**
     * Adds an object to a dataWatcher at the first empty Id.
     * @param dataWatcher The dataWatcher to add the object to.
     * @param obj The object to add to the dataWatcher.
     * @param name The name of the object.
     * @return The Id of the added object.
     */
    def addObjectAtUnusedId(dataWatcher: DataWatcher, obj: Object, name: String): Int = {
        for(i <- 0 until Byte.MaxValue) {
            breakable {
                try {
                    dataWatcher.addObject(i, obj)
                } catch {
                    case e: Exception => break
                }
                ids += (dataWatcher -> (name -> i))
                names += (dataWatcher -> (i -> name))
                return i
            }
        }
        println(CrashReport.makeCrashReport(new ArrayIndexOutOfBoundsException, "The dataWatcher mapping is completely filled. Try combining multiple variables into one dataWatcher.").getCompleteReport)
        -1
    }

    /**
     * Gets an Object watched by a dataWatcher when it is registered through addObjectAtUnusedId().
     * @param dataWatcher The dataWatcher watching the requested object.
     * @param name The name of the object.
     * @return The object from the specified dataWatcher added with the given name.
     */
    def getObjectFromMapping(dataWatcher: DataWatcher, name: String): Object = {
        dataWatcher.getAllWatched.get(ids.get(dataWatcher).get.get(name).get).asInstanceOf[Object]
    }

    def writeDataToNBT(compound: NBTTagCompound, dataWatcher: DataWatcher, name: String) = {
        //TODO
    }
}
