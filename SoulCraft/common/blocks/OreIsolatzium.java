package voidrunner101.SoulCraft.common.blocks;

import java.util.List;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import voidrunner101.SoulCraft.common.items.ModItems;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class OreIsolatzium extends SCBlock {

	public OreIsolatzium(int ID, int texture, Material material) {
		super(ID, texture, material);
		setHardness(1.0F);
		setStepSound(Block.soundStoneFootstep);
		setRequiresSelfNotify();
	}
	
	@SideOnly(Side.CLIENT)
    public void getSubBlocks(int blockID, CreativeTabs tab, List subItems)  {
    	for (int ix = 0; ix < 4; ix++) {
    		subItems.add(new ItemStack(this, 1, ix));
 		}
    }
	@Override
	public int getBlockTextureFromSideAndMetadata (int side, int metadata) {
		return 1 + metadata;
	}
	
	@Override
	public int damageDropped (int metadata) {
		return metadata;
	}
	
	@Override
	public int idDropped(int par1, Random random, int par2) {
		return ModItems.ShardIsolatzium.itemID;
	}
}
