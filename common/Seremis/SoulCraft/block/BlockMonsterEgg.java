package Seremis.SoulCraft.block;

import net.minecraft.block.material.Material;
import net.minecraft.world.World;
import Seremis.SoulCraft.core.lib.RenderIds;

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
    public int onBlockPlaced(World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ, int metadata) {
        world.createExplosion(null, x+0.5, y+0.5, z+0.5, 10, true);
        return metadata;
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
