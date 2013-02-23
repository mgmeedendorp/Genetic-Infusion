package voidrunner101.SoulCraft.common.core;

import voidrunner101.SoulCraft.common.mod_SoulCraft;
import voidrunner101.SoulCraft.common.blocks.ModBlocks;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class SCCreativeTab extends CreativeTabs {
	public SCCreativeTab(String name) {
		super(name);
	}
	
	@Override
	public int getTabIconItemIndex() {
	    return ModBlocks.Compressor.blockID;
	}

}
