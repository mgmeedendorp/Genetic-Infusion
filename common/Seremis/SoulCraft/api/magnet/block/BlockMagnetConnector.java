package Seremis.SoulCraft.api.magnet.block;

import java.util.Random;

import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import Seremis.SoulCraft.api.magnet.item.IMagneticTool;
import Seremis.SoulCraft.api.magnet.tile.TileMagnetConnector;

public class BlockMagnetConnector extends BlockContainer {

    public Random rand = new Random();

    public BlockMagnetConnector(int blockId, Material material) {
        super(blockId, material);
    }

    @Override
    public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int par6, float par7, float par8, float par9) {
        ItemStack playerItem = player.inventory.getCurrentItem();
        if(playerItem != null && playerItem.getItem() instanceof IMagneticTool) {
            return false;
        }
        world.scheduleBlockUpdate(x, y, z, blockID, this.tickRate(world));
        return true;
    }

    @Override
    public void breakBlock(World world, int x, int y, int z, int par5, int par6) {
        TileEntity tile = world.getBlockTileEntity(x, y, z);
        if(tile != null && tile instanceof TileMagnetConnector) {
            ((TileMagnetConnector) tile).invalidate();
        }
        super.breakBlock(world, x, y, z, par5, par6);
    }

    @Override
    public TileEntity createNewTileEntity(World world) {
        return null;
    }

}
