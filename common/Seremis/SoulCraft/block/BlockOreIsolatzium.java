package Seremis.SoulCraft.block;

import java.util.List;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import Seremis.SoulCraft.items.ModItems;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockOreIsolatzium extends SCBlock {
	
	public BlockOreIsolatzium(int ID, Material material) {
		super(ID, material);
		setHardness(1.0F);
		setStepSound(Block.soundStoneFootstep);
		setUnlocalizedName("oreIsolatzium");
		setNumbersofMetadata(4);
	}
	
	@SideOnly(Side.CLIENT)
    public void getSubBlocks(int blockID, CreativeTabs tab, List subItems)  {
    	for (int ix = 0; ix < 4; ix++) {
    		subItems.add(new ItemStack(this, 1, ix));
 		}
    }
	
//	@Override
//	public int getRenderColor(int metadata) {
//		//TODO add rendercolors
//		return 0;
//	}
	
	@Override
	public int damageDropped (int metadata) {
		return metadata;
	}
	
	@Override
	public int idDropped(int par1, Random random, int par2) {
		return ModItems.ShardIsolatzium.itemID;
	}
}
