package Seremis.SoulCraft.block;

import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import Seremis.SoulCraft.mod_SoulCraft;
import Seremis.SoulCraft.api.magnet.block.BlockMagnetConnector;
import Seremis.SoulCraft.core.lib.RenderIds;
import Seremis.SoulCraft.core.proxy.CommonProxy;
import Seremis.SoulCraft.tileentity.TileCrystalStand;
import Seremis.SoulCraft.util.UtilBlock;

public class BlockCrystalStand extends BlockMagnetConnector {

    public BlockCrystalStand(int ID, Material material) {
        super(ID, material);
        setUnlocalizedName("crystalStand");
        setHardness(0.5F);
        setCreativeTab(mod_SoulCraft.CreativeTab);
        setBlockBounds(0.25F, 0.0F, 0.25F, 0.8125F, 0.75F, 0.8125F);
    }

    @Override
    public void registerIcons(IconRegister iconRegister) {}

    @Override
    public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int par6, float par7, float par8, float par9) {
        super.onBlockActivated(world, x, y, z, player, par6, par7, par8, par9);
        TileCrystalStand tile = (TileCrystalStand) (world.getBlockTileEntity(x, y, z));
        ItemStack currPlayerItem = player.getCurrentEquippedItem();
        ItemStack currStack = tile.getStackInSlot(0);

        if(currPlayerItem != null && tile != null && currStack == null && currPlayerItem.itemID == ModBlocks.crystal.blockID) {
            tile.setInventorySlotContents(0, new ItemStack(ModBlocks.crystal, 1));
            player.getCurrentEquippedItem().stackSize--;
            world.markBlockForRenderUpdate(x, y, z);
        }
        if(tile != null && currStack != null) {
            tile.setInventorySlotContents(0, null);
            if(CommonProxy.proxy.isServerWorld(world)) {
                if(currStack != null && currStack.stackSize > 0) {
                    float rx = rand.nextFloat() * 0.8F + 0.1F;
                    float ry = rand.nextFloat() * 0.8F + 0.1F;
                    float rz = rand.nextFloat() * 0.8F + 0.1F;

                    EntityItem entityItem = new EntityItem(world, x + rx, y + ry, z + rz, new ItemStack(currStack.itemID, currStack.stackSize, currStack.getItemDamage()));

                    if(currStack.hasTagCompound()) {
                        entityItem.getEntityItem().setTagCompound((NBTTagCompound) currStack.getTagCompound().copy());
                    }

                    float factor = 0.05F;
                    entityItem.motionX = rand.nextGaussian() * factor;
                    entityItem.motionY = rand.nextGaussian() * factor + 0.2F;
                    entityItem.motionZ = rand.nextGaussian() * factor;
                    world.spawnEntityInWorld(entityItem);
                    currStack.stackSize = 0;
                }
            }
        }
        world.markBlockForRenderUpdate(x, y, z);
        return true;
    }

    public boolean isOpaqueCube() {
        return false;
    }

    public boolean renderAsNormalBlock() {
        return false;
    }

    @Override
    public int getRenderType() {
        return RenderIds.CrystalStandRenderID;
    }

    @Override
    public void breakBlock(World world, int x, int y, int z, int par5, int par6) {
        UtilBlock.dropItemsFromTile(world, x, y, z);
        super.breakBlock(world, x, y, z, par5, par6);
    }

    @Override
    public TileEntity createTileEntity(World world, int metadata) {
        return new TileCrystalStand();
    }

}
