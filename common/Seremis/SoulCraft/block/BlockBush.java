package Seremis.SoulCraft.block;

import java.util.List;
import java.util.Random;

import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;
import Seremis.SoulCraft.core.lib.RenderIds;
import Seremis.SoulCraft.item.ModItems;
import Seremis.SoulCraft.misc.EnumBushType;
import Seremis.SoulCraft.tileentity.TileBush;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockBush extends SCBlock {

    public BlockBush(int ID, Material material) {
        super(ID, material);
        setUnlocalizedName("berryBush");
        setNumbersofMetadata(7);
        setNeedsIcon(false);
        setTickRandomly(true);
    }
    
//    @Override
//    @SideOnly(Side.CLIENT)
//    public void registerIcons(IconRegister iconRegister) {}

    @Override
    public void updateTick(World world, int x, int y, int z, Random random) {
//        int metadata = world.getBlockMetadata(x, y, z);
//        if(metadata == 7 || metadata == 15) {
//            return;
//        }
//        if(random.nextInt(20) == 0 && canGrow(world, x, y, z)) {
//            grow(world, x, y, z);
//        }
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
    public boolean isOpaqueCube() {
        return false;
    }
    
    @Override
    public boolean renderAsNormalBlock() {
        return false;
    }
    
    @Override
    public int getRenderType() {
        return RenderIds.BushRenderID;
    }

    @SideOnly(Side.CLIENT)
    public void getSubBlocks(int blockID, CreativeTabs tab, List subItems) {
        for(int ix = 0; ix < this.getNumbersOfMetadata(); ix++) {
            subItems.add(new ItemStack(this, 1, ix));
        }
    }

    @Override
    public int damageDropped(int metadata) {
        return metadata;
    }

    @Override
    public int idDropped(int par1, Random random, int par2) {
        return ModItems.berry.itemID;
    }

    @Override
    public TileEntity createTileEntity(World world, int metadata) {
        switch(metadata) {
            case 0 :
                return new TileBush(EnumBushType.NORMAL);
        }
        return new TileBush(EnumBushType.NORMAL);
    }
}
