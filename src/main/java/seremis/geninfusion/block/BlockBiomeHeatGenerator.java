package seremis.geninfusion.block;

import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import seremis.geninfusion.lib.Blocks;
import seremis.geninfusion.tileentity.TileBiomeHeatGenerator;

import java.util.Random;

public class BlockBiomeHeatGenerator extends GIBlockContainer {

    public BlockBiomeHeatGenerator(Material material) {
        super(material);
        setBlockName(Blocks.BIOME_HEAT_GENERATOR_UNLOCALIZED_NAME);
        setTickRandomly(true);
    }

    @Override
    public TileEntity createNewTileEntity(World worldl, int metadata) {
        return new TileBiomeHeatGenerator();
    }

    @Override
    public void updateTick(World world, int x, int y, int z, Random random) {
        TileEntity tile = world.getTileEntity(x, y, z);
        if(tile != null && tile instanceof TileBiomeHeatGenerator) {
            ((TileBiomeHeatGenerator) tile).biomeTicks++;
        }
    }
}
