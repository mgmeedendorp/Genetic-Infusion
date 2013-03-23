package SoulCraft.block;

import net.minecraft.block.material.Material;
import SoulCraft.core.lib.RenderIds;

public class BlockMonsterEgg extends SCBlock {
	
	public BlockMonsterEgg(int ID, Material material) {
		super(ID, material);
		setUnlocalizedName("MonsterEggs");
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
		return RenderIds.MonsterEggRenderID;
	}
}
