package seremis.geninfusion.api.soul.util

import net.minecraft.crash.CrashReport
import net.minecraft.entity.DataWatcher
import util.control.Breaks._


object DataWatcherHelper {

    /**
     * Adds an object to a dataWatcher at the first empty Id.
     * @param dataWatcher The dataWatcher to add the object to.
     * @param obj The object to add to the dataWatcher.
     * @return The Id of the added object.
     */
    def addObjectAtUnusedId(dataWatcher: DataWatcher, obj: Object): Int = {
        for(i <- 0 until Byte.MaxValue) {
            breakable {
                try {
                    dataWatcher.addObject(i, obj)
                } catch {
                    case e: Exception => break
                }
                return i
            }
        }
        println(CrashReport.makeCrashReport(new ArrayIndexOutOfBoundsException, "The dataWatcher mapping is completely filled. Try combining multiple variables into one dataWatcher.").getCompleteReport)
        0
    }
}
