package Seremis.SoulCraft.block;

import java.util.Random;

import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeDirection;
import Seremis.SoulCraft.mod_SoulCraft;
import Seremis.SoulCraft.core.lib.GuiIds;
import Seremis.SoulCraft.core.lib.RenderIds;
import Seremis.SoulCraft.core.proxy.CommonProxy;
import Seremis.SoulCraft.item.ItemTransporterModules;
import Seremis.SoulCraft.item.ModItems;
import Seremis.SoulCraft.tileentity.TileTransporter;
import Seremis.SoulCraft.util.UtilBlock;

public class BlockTransporter extends SCBlock {

    public BlockTransporter(int ID, Material material) {
        super(ID, material);
        setUnlocalizedName("transporter");
        setBlockBounds(0.0f, 0.0f, 0.0f, 1.0f, 0.8f, 1.0f);
        setNeedsIcon(false);
    }

    @Override
    public void randomDisplayTick(World world, int x, int y, int z, Random random) {
        TileTransporter tile = (TileTransporter) world.getBlockTileEntity(x, y, z);
        ForgeDirection direction = tile.direction;
        if(!tile.hasEngine()) {
            return;
        }
        double particleX = x + 0.5 + random.nextFloat() / 10;
        double yy = y + 0.7 + random.nextFloat() / 10;
        double zz = z + 0.5 + random.nextFloat() / 10;

        float offsetX = 0.0F;
        float offsetZ = 0.0F;
        float offsetX2 = 0.0F;
        float offsetZ2 = 0.0F;
        double velocityX = 0.0D;
        double velocityZ = 0.0D;
        switch(direction.ordinal()) {
            case 2:
                offsetX = -0.30F;
                offsetZ = 0.40F;
                offsetX2 = 0.25F;
                offsetZ2 = 0.30F;
                velocityZ = 0.05D * random.nextDouble();
                break;
            case 3:
                offsetX = 0.23F;
                offsetZ = -0.40F;
                offsetX2 = -0.30F;
                offsetZ2 = -0.40F;
                velocityZ = -0.05D * random.nextDouble();
                break;
            case 4:
                offsetX = 0.30F;
                offsetZ = 0.30F;
                offsetX2 = 0.30F;
                offsetZ2 = -0.40F;
                velocityX = 0.05D * random.nextDouble();
                break;
            case 5:
                offsetX = -0.45F;
                offsetZ = -0.30F;
                offsetX2 = -0.45F;
                offsetZ2 = 0.25F;
                velocityX = -0.05D * random.nextDouble();
                break;
        }
        world.spawnParticle("smoke", particleX + offsetX2, yy, zz + offsetZ2, velocityX, 0.0D, velocityZ);
        world.spawnParticle("smoke", particleX + offsetX, yy, zz + offsetZ, velocityX, 0.0D, velocityZ);
        world.spawnParticle("flame", particleX + offsetX2, yy, zz + offsetZ2, velocityX, 0.0D, velocityZ);
        world.spawnParticle("flame", particleX + offsetX, yy, zz + offsetZ, velocityX, 0.0D, velocityZ);
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
        return RenderIds.TransporterRenderID;
    }

    @Override
    public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int par6, float par7, float par8, float par9) {
        if(player.isSneaking()) {
            return false;
        }

        TileTransporter tile = (TileTransporter) (world.getBlockTileEntity(x, y, z));
        ItemStack playerItem = player.getCurrentEquippedItem();
        if(tile != null && playerItem != null && playerItem.itemID == ModItems.transporterModules.itemID && playerItem.getItemDamage() == ItemTransporterModules.engine().getItemDamage()) {
            if(CommonProxy.proxy.isRenderWorld(world)) {
                return false;
            }
            if(tile.hasEngine()) {
                player.addChatMessage("This transporter already has engines");
                return true;
            }
            tile.setHasEngine(true);
            playerItem.stackSize--;
        }
        if(tile != null && playerItem != null && playerItem.itemID == ModItems.transporterModules.itemID && playerItem.getItemDamage() == ItemTransporterModules.storage().getItemDamage()) {
            if(CommonProxy.proxy.isRenderWorld(world)) {
                return false;
            }
            if(tile.hasInventory()) {
                player.addChatMessage("This transporter already has a storage module!");
                return true;
            }
            tile.setHasInventory(true);
            playerItem.stackSize--;
        }
        if(tile != null && playerItem == null && tile.hasInventory()) {
            player.openGui(mod_SoulCraft.instance, GuiIds.GUI_TRANSPORTER_ID, world, x, y, z);
        }
        return true;
    }

    @Override
    public void onEntityCollidedWithBlock(World world, int x, int y, int z, Entity entity) {
        if(CommonProxy.proxy.isRenderWorld(world)) {
            return;
        }
        TileTransporter tile = (TileTransporter) (world.getBlockTileEntity(x, y, z));
        if(tile != null && tile.hasInventory() && entity instanceof EntityItem) {
            for(int a = 0; a < 9; a++) {
                if(((TileTransporter) tile).setInventorySlot(a, ((EntityItem) entity).getEntityItem())) {
                    CommonProxy.proxy.removeEntity(entity);
                    break;
                }
            }
        }
    }

    @Override
    public void breakBlock(World world, int x, int y, int z, int par5, int par6) {
        if(CommonProxy.proxy.isRenderWorld(world)) {
            return;
        }
        UtilBlock.dropItemsFromTile(world, x, y, z);

        TileTransporter tile = (TileTransporter) world.getBlockTileEntity(x, y, z);
        if(tile.hasEngine()) {
            EntityItem item = new EntityItem(world, x, y, z, new ItemStack(ModItems.transporterModules, 1, 1));
            world.spawnEntityInWorld(item);
        }
        if(tile.hasInventory()) {
            EntityItem item = new EntityItem(world, x, y, z, new ItemStack(ModItems.transporterModules, 1, 0));
            world.spawnEntityInWorld(item);
        }
        super.breakBlock(world, x, y, z, par5, par6);
    }

    @Override
    public TileEntity createTileEntity(World world, int metadata) {
        return new TileTransporter();
    }
}
