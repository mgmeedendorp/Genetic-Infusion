package seremis.soulcraft.block;

import java.util.Random;

import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import seremis.soulcraft.core.lib.Blocks;
import seremis.soulcraft.tileentity.TileBiomeHeatGenerator;

public class BlockBiomeHeatGenerator extends SCBlockContainer {

    public BlockBiomeHeatGenerator(int ID, Material material) {
        super(ID, material);
        setUnlocalizedName(Blocks.BIOME_HEAT_GENERATOR_UNLOCALIZED_NAME);
        setTickRandomly(true);
    }
    
    @Override
    public TileEntity createNewTileEntity(World world) {
        return new TileBiomeHeatGenerator();
    }
    
    @Override
    public void updateTick(World world, int x, int y, int z, Random random) {
        TileEntity tile = world.getBlockTileEntity(x, y, z);
        if(tile != null && tile instanceof TileBiomeHeatGenerator) {
            ((TileBiomeHeatGenerator)tile).biomeTicks++;
        }
    }
}
