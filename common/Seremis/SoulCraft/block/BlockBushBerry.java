package Seremis.SoulCraft.block;

import java.util.List;
import java.util.Random;

import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;
import Seremis.SoulCraft.core.lib.RenderIds;
import Seremis.SoulCraft.item.ModItems;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockBushBerry extends SCBlock {

    public BlockBushBerry(int ID, Material material) {
        super(ID, material);
        setUnlocalizedName("berryBush");
        setNumbersofMetadata(7);
        setTickRandomly(true);
    }
    
    @Override
    public void updateTick(World world, int x, int y, int z, Random random) {
        int metadata = world.getBlockMetadata(x, y, z);
        if(metadata == 7 || metadata == 15) {
            return;
        }
        if(random.nextInt(20) == 0 && canGrow(world, x, y, z)) {
            grow(world, x, y, z);
        }
        System.out.println("tick");
    }
    
    public boolean canGrow(World world, int x, int y, int z) {
        if(world.getBlockLightValue(x, y, z) >= 9)
            return true;
        return false;
    }
    
    public void grow(World world, int x, int y, int z) {
        int metadata = world.getBlockMetadata(x, y, z);
        world.setBlock(x, y, z, blockID, metadata, 2);
    }
    
    @Override
    public AxisAlignedBB getCollisionBoundingBoxFromPool(World world, int x, int y, int z) {
        return null;
    }
    
    @Override
    public int getRenderType() {
        return RenderIds.BushRenderID;
    }
    
    @Override
    public boolean isOpaqueCube() {
        return false;
    }
    
    @SideOnly(Side.CLIENT)
    public void getSubBlocks(int blockID, CreativeTabs tab, List subItems)  {
        for (int ix = 0; ix < this.getNumbersOfMetadata(); ix++) {
            subItems.add(new ItemStack(this, 1, ix));
        }
    }
    
    @Override
    public int damageDropped (int metadata) {
        return metadata;
    }
    
    @Override
    public int idDropped(int par1, Random random, int par2) {
        return ModItems.berry.itemID;
    }
}
