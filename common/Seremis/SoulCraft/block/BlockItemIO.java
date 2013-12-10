package Seremis.SoulCraft.block;

import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import Seremis.SoulCraft.core.lib.Blocks;
import Seremis.SoulCraft.tileentity.TileItemIO;

public class BlockItemIO extends SCBlockContainer {

    public BlockItemIO(int ID, Material material) {
        super(ID, material);
        setUnlocalizedName(Blocks.ITEM_IO_UNLOCALIZED_NAME);
    }

    @Override
    public TileEntity createNewTileEntity(World world) {
        return new TileItemIO();
    }
}
