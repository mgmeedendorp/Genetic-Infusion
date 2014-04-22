package seremis.soulcraft.block;

import java.util.Random;

import seremis.soulcraft.core.lib.Blocks;
import seremis.soulcraft.item.ModItems;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.item.Item;

public class BlockOreIsolatzium extends SCBlock {

    public BlockOreIsolatzium(Material material) {
        super(material);
        setHardness(1.0F);
        setStepSound(Block.soundTypeStone);
        setBlockName(Blocks.ORE_ISOLATZIUM_UNLOCALIZED_NAME);
        setNumbersofMetadata(4);
    }

    @Override
    public int damageDropped(int metadata) {
        return metadata;
    }

    @Override
    public Item getItemDropped(int par1, Random random, int par2) {
        return ModItems.crystalShard;
    }
}
