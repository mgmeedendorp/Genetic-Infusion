package seremis.geninfusion.network

import cpw.mods.fml.common.network.NetworkRegistry
import cpw.mods.fml.relauncher.Side
import seremis.geninfusion.lib.DefaultProps
import seremis.geninfusion.network.packet.{PacketEntityData, PacketTileData}

object ModPackets {

    val wrapper = NetworkRegistry.INSTANCE.newSimpleChannel(DefaultProps.ID)

    def init() {
        wrapper.registerMessage(classOf[PacketEntityData.Handler], classOf[PacketEntityData], 0, Side.CLIENT)
        wrapper.registerMessage(classOf[PacketEntityData.Handler], classOf[PacketEntityData], 1, Side.SERVER)
        wrapper.registerMessage(classOf[PacketTileData.Handler], classOf[PacketTileData], 2, Side.CLIENT)
        wrapper.registerMessage(classOf[PacketTileData.Handler], classOf[PacketTileData], 3, Side.SERVER)
    }
}
