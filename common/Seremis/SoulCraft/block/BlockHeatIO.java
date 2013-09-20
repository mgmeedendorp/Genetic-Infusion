package Seremis.SoulCraft.block;

import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import Seremis.SoulCraft.core.lib.Blocks;
import Seremis.SoulCraft.tileentity.TileHeatIO;

public class BlockHeatIO extends SCBlock {

    public BlockHeatIO(int ID, Material material) {
        super(ID, material);
        setUnlocalizedName(Blocks.HEAT_IO_UNLOCALIZED_NAME);
        setNeedsIcon(false);
    }

    @Override
    public TileEntity createNewTileEntity(World world) {
        return new TileHeatIO();
    }
}