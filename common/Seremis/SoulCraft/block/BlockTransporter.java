package Seremis.SoulCraft.block;

import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import Seremis.SoulCraft.core.lib.RenderIds;
import Seremis.SoulCraft.core.proxy.CommonProxy;
import Seremis.SoulCraft.tileentity.TileTransporter;
import Seremis.SoulCraft.util.UtilBlock;

public class BlockTransporter extends SCBlock {

    public BlockTransporter(int ID, Material material) {
        super(ID, material);
        setUnlocalizedName("transporter");
        setBlockBounds(0.0f, 0.0f, 0.0f, 1.0f, 0.8f, 1.0f);
    }    
    
    @Override
    public void registerIcons(IconRegister iconRegister) {}
    
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
        if(CommonProxy.proxy.isRenderWorld(world)){return false;}
        TileTransporter tile = (TileTransporter)(world.getBlockTileEntity(x, y, z));
        if(tile != null && player.getItemInUse().itemID == 0) {
            
        }
        return false;
    }
    
    @Override
    public void onEntityCollidedWithBlock(World world, int x, int y, int z, Entity entity) {
        if(CommonProxy.proxy.isRenderWorld(world)){return;}
        TileTransporter tile = (TileTransporter)(world.getBlockTileEntity(x, y, z));
        if(tile != null && tile.hasInventory() && entity instanceof EntityItem){
            for(int a=0; a<9; a++)
            {
                if(((TileTransporter)tile).setInventorySlot(a, ((EntityItem) entity).getEntityItem())) {
                    CommonProxy.proxy.removeEntity(entity);
                    break;
                }
            }
        }
    }
    
    
    @Override
    public void breakBlock(World world, int x, int y, int z, int par5, int par6) {
        UtilBlock.dropItemsFromTile(world, x, y, z);
        super.breakBlock(world, x, y, z, par5, par6);
    }
    
    @Override
    public TileEntity createTileEntity(World world, int metadata) {
        return new TileTransporter(true, false);
    }
}
