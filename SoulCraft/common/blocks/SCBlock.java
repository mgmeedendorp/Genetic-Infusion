package voidrunner101.SoulCraft.common.blocks;

import java.util.Random;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import voidrunner101.SoulCraft.common.mod_SoulCraft;
import voidrunner101.SoulCraft.common.core.DefaultProps;

public class SCBlock extends BlockContainer {

	public SCBlock(int ID, int texture, Material material) {
		super(ID, texture, material);
		setBlockName("");
		setCreativeTab(mod_SoulCraft.CreativeTab);
	}
	
	public int quantityDropped(Random par1Random) {
		return 1;
	}
	 
	public int idDropped(int par1, Random random, int par2) {
		return this.blockID;
	}
	
	@SideOnly(Side.CLIENT)
    public String getTextureFile () {
            return DefaultProps.BLOCKS_TEXTURE_FILE;
    }

	@Override
	public TileEntity createNewTileEntity(World var1) {
		return null;
	}

}
