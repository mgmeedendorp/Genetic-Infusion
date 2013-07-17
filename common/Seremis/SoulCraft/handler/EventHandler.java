package Seremis.SoulCraft.handler;

import net.minecraftforge.event.ForgeSubscribe;
import net.minecraftforge.event.entity.player.BonemealEvent;
import Seremis.SoulCraft.block.BlockBush;
import Seremis.SoulCraft.block.ModBlocks;

public class EventHandler {

    @ForgeSubscribe
    public void bonemealUsed(BonemealEvent event) {
        if(event.ID == ModBlocks.bushBerry.blockID) {
            ((BlockBush) ModBlocks.bushBerry).grow(event.world, event.X, event.Y, event.Z);
        }
    }

}
