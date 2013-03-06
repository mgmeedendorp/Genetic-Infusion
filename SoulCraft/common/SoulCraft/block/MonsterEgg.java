package SoulCraft.block;

import net.minecraft.block.material.Material;
import net.minecraft.entity.EntityLiving;
import SoulCraft.core.DefaultProps;

public class MonsterEgg extends SCBlock {
	
	public MonsterEgg(int ID, int texture, Material material) {
		super(ID, texture, material);
		setBlockName("MonsterEggs");
		setLightValue(0.5F);
		setHardness(3.0F);
		setResistance(15.0F);
		setBlockBounds(0.0625F, 0.0F, 0.0625F, 0.9375F, 1.0F, 0.9375F);
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
