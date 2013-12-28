package seremis.soulcraft.block;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import seremis.soulcraft.core.lib.Blocks;
import seremis.soulcraft.item.ModItems;

public class BlockOreIsolatzium extends SCBlock {

    public BlockOreIsolatzium(int ID, Material material) {
        super(ID, material);
        setHardness(1.0F);
        setStepSound(Block.soundStoneFootstep);
        setUnlocalizedName(Blocks.ORE_ISOLATZIUM_UNLOCALIZED_NAME);
        setNumbersofMetadata(4);
    }

    @Override
    public int damageDropped(int metadata) {
        return metadata;
    }

    @Override
    public int idDropped(int par1, Random random, int par2) {
        return ModItems.crystalShard.itemID;
    }
}
