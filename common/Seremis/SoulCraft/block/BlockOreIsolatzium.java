package Seremis.SoulCraft.block;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import Seremis.SoulCraft.item.ModItems;

public class BlockOreIsolatzium extends SCBlock {

    public BlockOreIsolatzium(int ID, Material material) {
        super(ID, material);
        setHardness(1.0F);
        setStepSound(Block.soundStoneFootstep);
        setUnlocalizedName("oreIsolatzium");
        setNumbersofMetadata(4);
    }

    @Override
    public int damageDropped(int metadata) {
        return metadata;
    }

    @Override
    public int idDropped(int par1, Random random, int par2) {
        return ModItems.shardIsolatzium.itemID;
    }
}
