package Seremis.SoulCraft.block;

import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import Seremis.SoulCraft.core.proxy.CommonProxy;
import Seremis.SoulCraft.tileentity.TileStationController;

public class BlockStationController extends SCBlockRotateable {
    
    private static String[] textureNames = {"side", "side", "front", "side", "side", "side"};
    
    public BlockStationController(int ID, Material material) {
        super(ID, material, true, 0, textureNames);
        setUnlocalizedName("stationController");
    }    
    
    @Override
    public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ) {
        if(player.isSneaking()){
            return false;
        }
        if(CommonProxy.proxy.isServerWorld(world)) {
            TileStationController tile = (TileStationController) world.getBlockTileEntity(x, y, z);
            if(multiblockCheck(world, x, y, z)) {
                tile.initiateMultiblock();
            }
        }
        return true;
    }
    
    private boolean multiblockCheck(World world, int x, int y, int z) {
        if(CommonProxy.proxy.isServerWorld(world)) {
            TileStationController tile = (TileStationController) world.getBlockTileEntity(x, y, z);
            if(tile != null) {
                if(tile.getIsValid()) {
                    return true;
                }
            }
        }
        return false;
    }
    
    @Override
    public TileEntity createNewTileEntity(World world) {
        return new TileStationController();
    }
}
