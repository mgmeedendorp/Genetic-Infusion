package Seremis.SoulCraft.block;

import net.minecraft.block.material.Material;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Icon;
import net.minecraft.util.MathHelper;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeDirection;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class SCBlockRotateable extends SCBlockContainer {

    public SCBlockRotateable(int ID, Material material, String[] sidedTextureNames) {
        super(ID, material);
        setNeedsSidedTexture(true, sidedTextureNames);
    }

    @Override
    public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase entity, ItemStack itemStack) {
        int direction = 0;
        int facing = MathHelper.floor_double(entity.rotationYaw * 4.0F / 360.0F + 0.5D) & 3;
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
        world.setBlockMetadataWithNotify(x, y, z, direction - 2, 3);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public Icon getIcon(int side, int metadata) {
        return getSidedIcons()[side];
    }

    @Override
    @SideOnly(Side.CLIENT)
    public Icon getBlockTexture(IBlockAccess blockAccess, int x, int y, int z, int side) {
        int direction = blockAccess.getBlockMetadata(x, y, z) + 2;
        return getSidedIcons()[getTextureIndex(direction, side)];
    }

    private int getTextureIndex(int direction, int side) {
        int index = side < 2 ? side : direction + side;
        while(index > 5) {
            index = index - 4;
        }
        return index;
    }
}
