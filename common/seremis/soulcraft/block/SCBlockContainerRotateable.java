package seremis.soulcraft.block;

import net.minecraft.block.material.Material;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Icon;
import net.minecraft.util.MathHelper;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeDirection;
import seremis.soulcraft.core.proxy.CommonProxy;
import seremis.soulcraft.tileentity.SCTile;
import seremis.soulcraft.tileentity.SCTileMagnetConnector;
import seremis.soulcraft.tileentity.SCTileMagnetConsumer;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class SCBlockContainerRotateable extends SCBlockContainer {

    private boolean useTile;

    public SCBlockContainerRotateable(int ID, Material material, boolean useTile, String[] sidedTextureNames) {
        super(ID, material);
        this.useTile = useTile;
        setNeedsSidedTexture(true, sidedTextureNames);
    }

    @Override
    public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase entity, ItemStack itemStack) {
        int direction = 0;
        int facing = MathHelper.floor_double(entity.rotationYaw * 4.0F / 360.0F + 0.5D) & 3;
        // Strange...
        switch(facing) {
            case 3:
                direction = ForgeDirection.WEST.ordinal();
                break;
            case 2:
                direction = ForgeDirection.EAST.ordinal();
                break;
            case 1:
                direction = ForgeDirection.SOUTH.ordinal();
                break;
            case 0:
                direction = ForgeDirection.NORTH.ordinal();
                break;
        }
        if(!useTile) {
            world.setBlockMetadataWithNotify(x, y, z, direction - 2, 3);
        }
        if(useTile && CommonProxy.proxy.isServerWorld(world)) {
            TileEntity tile = world.getBlockTileEntity(x, y, z);
            if(tile != null && tile instanceof SCTile) {
                ((SCTile) tile).setDirection(direction);
            } else if(tile != null && tile instanceof SCTileMagnetConnector) {
                ((SCTileMagnetConnector) tile).setDirection(direction);
            } else if(tile != null && tile instanceof SCTileMagnetConsumer) {
                ((SCTileMagnetConsumer) tile).setDirection(direction);
            }
        }
    }

    @Override
    @SideOnly(Side.CLIENT)
    public Icon getIcon(int side, int metadata) {
        return getSidedIcons()[side];
    }

    @Override
    @SideOnly(Side.CLIENT)
    public Icon getBlockTexture(IBlockAccess blockAccess, int x, int y, int z, int side) {
        if(useTile) {
            TileEntity tile = blockAccess.getBlockTileEntity(x, y, z);
            if(tile != null && tile instanceof SCTile) {
                int direction = ((SCTile) tile).getDirection();
                return getSidedIcons()[getTextureIndex(direction, side)];
            } else if(tile != null && tile instanceof SCTileMagnetConnector) {
                int direction = ((SCTileMagnetConnector) tile).getDirection();
                return getSidedIcons()[getTextureIndex(direction, side)];
            } else if(tile != null && tile instanceof SCTileMagnetConsumer) {
                int direction = ((SCTileMagnetConsumer) tile).getDirection();
                return getSidedIcons()[getTextureIndex(direction, side)];
            }
        } else {
            int direction = blockAccess.getBlockMetadata(x, y, z) + 2;
            return getSidedIcons()[getTextureIndex(direction, side)];
        }
        return null;
    }

    private int getTextureIndex(int direction, int side) {
        int index = side < 2 ? side : direction + side;
        while(index > 5) {
            index = index - 4;
        }
        return index;
    }
}
