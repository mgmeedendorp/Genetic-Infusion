package seremis.soulcraft.block;

import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import seremis.soulcraft.core.lib.Blocks;
import seremis.soulcraft.tileentity.TileHeatIO;

public class BlockHeatIO extends SCBlockContainer {

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