package Seremis.SoulCraft.block;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import Seremis.SoulCraft.core.lib.Blocks;
import Seremis.SoulCraft.core.lib.RenderIds;
import Seremis.SoulCraft.core.proxy.CommonProxy;
import Seremis.SoulCraft.misc.bush.BushManager;
import Seremis.SoulCraft.misc.bush.BushType;
import Seremis.SoulCraft.tileentity.TileBush;
import Seremis.SoulCraft.util.UtilBlock;

public class BlockBush extends SCBlock {

    public BlockBush(int ID, Material material) {
        super(ID, material);
        setUnlocalizedName(Blocks.BUSH_UNLOCALIZED_NAME);
        setNumbersofMetadata(2);
        setNeedsIcon(false);
        setBlockBounds(0.2F, 0.0F, 0.2F, 0.85F, 0.9F, 0.85F);
    }

    public int onBlockPlaced(World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ, int metadata) {
        world.markBlockForRenderUpdate(x, y + 1, z);
        return metadata;
    }

    @Override
    public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ) {
        if(CommonProxy.proxy.isServerWorld(world)) {
            if(player.getCurrentEquippedItem() == null) {
                if(((TileBush) world.getBlockTileEntity(x, y, z)).getStage() == ((TileBush) world.getBlockTileEntity(x, y, z)).getType().getMaxStage()) {
                    ArrayList<ItemStack> drops = getBlockDropped(world, x, y, z, world.getBlockMetadata(x, y, z), 0);
                    if(drops != null) {
                        for(ItemStack stack : drops) {
                            UtilBlock.dropItemInWorld(x, y, z, world, stack);
                            TileBush tile = (TileBush) world.getBlockTileEntity(x, y, z);
                            tile.setStage(tile.getType().getBerryStage() - 1);
                        }
                    }
                }
            }
        }
        return false;
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

    @Override
    public void onBlockDestroyedByPlayer(World world, int x, int y, int z, int metadata) {
        if(!world.isRemote) {
            ArrayList<ItemStack> items = getBlockDropped(world, x, y, z, metadata, 0);

            for(ItemStack item : items) {
                this.dropBlockAsItem_do(world, x, y, z, item);
            }
        }
        super.onBlockDestroyedByPlayer(world, x, y, z, metadata);
    }

    @Override
    public ArrayList<ItemStack> getBlockDropped(World world, int x, int y, int z, int metadata, int fortune) {
        if(CommonProxy.proxy.isRenderWorld(world))
            return null;

        TileBush tile = (TileBush) world.getBlockTileEntity(x, y, z);

        if(tile == null || tile.getStage() >= tile.getType().getBerryStage())
            return new ArrayList<ItemStack>();

        HashMap<ItemStack, Float> drops = tile.getType().getDrops();
        ItemStack[] stacks = new ItemStack[drops.size()];

        int index = 0;
        for(ItemStack stack : drops.keySet()) {
            stacks[index] = stack;
            index++;
        }

        ArrayList<ItemStack> dropList = new ArrayList<ItemStack>();
        int index2 = 0;
        for(float a : drops.values()) {
            if(a == 1) {
                dropList.add(stacks[index2]);
            } else if(a < 1) {
                Random rand = new Random();
                if(rand.nextFloat() < a) {
                    dropList.add(stacks[index2]);
                }
            }
            index2++;
        }
        return dropList;
    }

    @Override
    public TileEntity createTileEntity(World world, int metadata) {
        return new TileBush(getType(metadata));
    }

    public BushType getType(int metadata) {
        switch(metadata) {
            case 0:
                return BushManager.typeNormal;
            case 1:
                return BushManager.typeBlue;
            default:
                return BushManager.typeNormal;
        }
    }

    public void grow(World world, int x, int y, int z) {
        if(CommonProxy.proxy.isRenderWorld(world))
            return;
        TileBush tile = (TileBush) world.getBlockTileEntity(x, y, z);
        tile.updateStage();
    }
}
