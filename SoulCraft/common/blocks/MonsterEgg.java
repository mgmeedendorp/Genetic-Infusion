package voidrunner101.SoulCraft.common.blocks;

import net.minecraft.block.material.Material;
import net.minecraft.entity.EntityLiving;
import voidrunner101.SoulCraft.mod_SoulCraft;
import voidrunner101.SoulCraft.common.core.DefaultProps;

public class MonsterEgg extends SCBlock {

	/**
	 * The entity inside the egg.
	 */
	private EntityLiving entity;
	
	public MonsterEgg(int ID, int texture, Material material) {
		super(ID, texture, material);
		setBlockName("MonsterEggs");
		setLightValue(0.375F);
		setHardness(3.0F);
		setResistance(15.0F);
		setCreativeTab(mod_SoulCraft.CreativeTab);
		setRequiresSelfNotify();
	}
	
	@Override
	public boolean isOpaqueCube() {
		return false;
	} 
	
	@Override
	public boolean renderAsNormalBlock() {
		return false;
	}
	
	@Override
	public int getRenderType() {
		return DefaultProps.MonsterEggRenderID;
	}
}
