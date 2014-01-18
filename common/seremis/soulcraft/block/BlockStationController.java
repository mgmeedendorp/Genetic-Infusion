package seremis.soulcraft.block;

import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import seremis.soulcraft.SoulCraft;
import seremis.soulcraft.core.lib.Blocks;
import seremis.soulcraft.core.lib.GuiIds;
import seremis.soulcraft.core.proxy.CommonProxy;
import seremis.soulcraft.tileentity.TileStationController;
import seremis.soulcraft.util.UtilBlock;

public class BlockStationController extends SCBlockContainerRotateable {

    private static String[] textureNames = {"side", "side", "side", "side", "front", "side"};

    public BlockStationController(int ID, Material material) {
        super(ID, material, true, textureNames);
        setUnlocalizedName(Blocks.STATION_CONTROLLER_UNLOCALIZED_NAME);
    }

    @Override
    public void onNeighborBlockChange(World world, int x, int y, int z, int id) {
        if(CommonProxy.proxy.isServerWorld(world)) {
            if(isNeighbourBlockPowered(world, x, y, z)) {
                TileStationController tile = (TileStationController) world.getBlockTileEntity(x, y, z);
                tile.onRedstoneSignal();
            }
        }
    }
    
    public boolean isNeighbourBlockPowered(World world, int x, int y, int z) {
        if(world.getBlockPowerInput(x, y, z) > 0) {
            return true;
        }
        if(world.getBlockPowerInput(x-1, y, z) > 0) {
            return true;
        }
        if(world.getBlockPowerInput(x+1, y, z) > 0) {
            return true;
        }
        if(world.getBlockPowerInput(x, y, z-1) > 0) {
            return true;
        }
        if(world.getBlockPowerInput(x, y, z+1) > 0) {
            return true;
        }
        if(world.getBlockPowerInput(x-1, y, z-1) > 0) {
            return true;
        }
        if(world.getBlockPowerInput(x+1, y, z-1) > 0) {
            return true;
        }
        if(world.getBlockPowerInput(x+1, y, z-1) > 0) {
            return true;
        }
        if(world.getBlockPowerInput(x+1, y, z+1) > 0) {
            return true;
        }
        return false;
    }

    @Override
    public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ) {
        if(player.isSneaking()) {
            return false;
        }
        if(CommonProxy.proxy.isServerWorld(world)) {
            TileStationController tile = (TileStationController) world.getBlockTileEntity(x, y, z);
            if(multiblockCheck(world, x, y, z)) {
                tile.initiateMultiblock();
                if(tile.isMultiblock) {
                    player.openGui(SoulCraft.instance, GuiIds.GUI_STATION_TRANSPORTER_SCREEN_ID, world, x, y, z);
                }
            }
        }
        return true;
    }

    private boolean multiblockCheck(World world, int x, int y, int z) {
        if(CommonProxy.proxy.isServerWorld(world)) {
            TileStationController tile = (TileStationController) world.getBlockTileEntity(x, y, z);
            if(tile != null) {
                if(tile.isValid()) {
                    return true;
                }
            }
        }
        return false;
    }
    
    @Override
    public void breakBlock(World world, int x, int y, int z, int blockId, int metadata) {
        super.breakBlock(world, x, y, z, blockId, metadata);
        UtilBlock.dropItemsFromTile(world, x, y, z);
    }

    @Override
    public TileEntity createNewTileEntity(World world) {
        return new TileStationController();
    }
}
