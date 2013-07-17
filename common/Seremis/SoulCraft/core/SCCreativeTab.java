package Seremis.SoulCraft.core;

import net.minecraft.creativetab.CreativeTabs;
import Seremis.SoulCraft.block.ModBlocks;

public class SCCreativeTab extends CreativeTabs {
    public SCCreativeTab(String name) {
        super(name);
    }

    @Override
    public int getTabIconItemIndex() {
        return ModBlocks.compressor.blockID;
    }

}
