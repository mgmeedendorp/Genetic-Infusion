package com.seremis.geninfusion.handler

import com.seremis.geninfusion.api.GIApiInterface
import com.seremis.geninfusion.api.genetics.ISoul
import com.seremis.geninfusion.api.lib.Genes
import com.seremis.geninfusion.api.util.VariableName
import com.seremis.geninfusion.soulentity.SoulEntityLiving
import com.seremis.geninfusion.soulentity.logic.FieldLogic
import net.minecraft.entity.monster.EntityZombie
import net.minecraft.nbt.NBTTagCompound
import net.minecraft.util.EnumHand
import net.minecraftforge.event.entity.player.PlayerInteractEvent.RightClickBlock
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent

class GIEventHandler {


    @SubscribeEvent
    def playerInteract(event: RightClickBlock) {
        if(!event.getWorld.isRemote && event.getEntityPlayer.getHeldItem(EnumHand.MAIN_HAND) == null) {

            //val method = FunctionLib.FuncEntityEntityInit

            //println(method)

           // val m = GIApiInterface.entityMethodRegistry.getMethodsForName(method)

           // println(m)

            val entity = new SoulEntityLiving(event.getWorld, GIApiInterface.geneDefaultsRegistry.getSoulForClass(classOf[EntityZombie]))

            entity.setPosition(event.getHitVec.xCoord, event.getHitVec.yCoord + 1, event.getHitVec.zCoord)

            println(entity)

            entity.getEntityId


            val geneName = Genes.GeneTest

            println(geneName)


            val compound = new NBTTagCompound

            entity.setVar(VariableName("abcdef", classOf[Int]), 100)
            entity.makePersistent(VariableName("abcdef", classOf[Int]))

            entity.asInstanceOf[SoulEntityLiving].fieldLogic.writeToNBT(compound)

            println(compound)

            val logicOut = new FieldLogic(entity)

            logicOut.readFromNBT(compound)

            println(logicOut.getVar(VariableName("abcdef", classOf[Int])))

            println(GIApiInterface.geneDefaultsRegistry.getDefaultValueForClass(classOf[EntityZombie], geneName))

            val soul = GIApiInterface.geneDefaultsRegistry.getSoulForClass(classOf[EntityZombie])
            println(soul)

            val nbt = new NBTTagCompound

            GIApiInterface.dataTypeRegistry.writeValueToNBT(nbt, "soul", classOf[ISoul], soul)

            println(nbt)

            val out2 = GIApiInterface.dataTypeRegistry.readValueFromNBT(nbt, "soul", classOf[ISoul])

            println(out2)

            println(soul == out2)
        }
    }
}
