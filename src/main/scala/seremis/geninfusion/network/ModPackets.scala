package seremis.geninfusion.network

import seremis.geninfusion.lib.DefaultProps
import seremis.geninfusion.network.packet.{PacketAddDataWatcherHelperMapping, PacketEntityData, PacketTileData}

object ModPackets {

    val wrapper = NetworkRegistry.INSTANCE.newSimpleChannel(DefaultProps.ID)

    def init() {
        wrapper.registerMessage(classOf[PacketEntityData.Handler], classOf[PacketEntityData], 0, Side.CLIENT)
        wrapper.registerMessage(classOf[PacketEntityData.Handler], classOf[PacketEntityData], 1, Side.SERVER)
        wrapper.registerMessage(classOf[PacketTileData.Handler], classOf[PacketTileData], 2, Side.CLIENT)
        wrapper.registerMessage(classOf[PacketTileData.Handler], classOf[PacketTileData], 3, Side.SERVER)
        wrapper.registerMessage(classOf[PacketAddDataWatcherHelperMapping.Handler], classOf[PacketAddDataWatcherHelperMapping], 4, Side.CLIENT)
    }
}
