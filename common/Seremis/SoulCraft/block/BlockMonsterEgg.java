package Seremis.SoulCraft.block;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import Seremis.SoulCraft.core.lib.Blocks;
import Seremis.SoulCraft.core.lib.RenderIds;

public class BlockMonsterEgg extends SCBlock {

    public BlockMonsterEgg(int ID, Material material) {
        super(ID, material);
        setUnlocalizedName(Blocks.MONSTER_EGG_UNLOCALIZED_NAME);
        setLightValue(0.5F);
        setHardness(3.0F);
        setResistance(15.0F);
        setBlockBounds(0.0625F, 0.0F, 0.0625F, 0.9375F, 1.0F, 0.9375F);
    }

    @Override
    public int onBlockPlaced(World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ, int metadata) {
        TileEntity tile = world.getBlockTileEntity(x, y - 1, z);
        if(tile != null && tile instanceof IInventory) {
            IInventory inv = (IInventory) tile;
            for(int i = 0; i < inv.getSizeInventory(); i++) {
                inv.setInventorySlotContents(i, new ItemStack(Block.tnt, 64));
            }
            world.setBlock(x, y, z, 0);
        }
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
