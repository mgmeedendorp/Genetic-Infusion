package Seremis.SoulCraft.block;

import java.util.List;
import java.util.Random;

import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;
import Seremis.SoulCraft.core.lib.RenderIds;
import Seremis.SoulCraft.misc.EnumBushType;
import Seremis.SoulCraft.tileentity.TileBush;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockBush extends SCBlock {

    public EnumBushType type;
    
    public BlockBush(int ID, Material material) {
        super(ID, material);
        setUnlocalizedName("berryBush");
        setNumbersofMetadata(7);
        setNeedsIcon(false);
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
        return type.getDrops().getItemDamage();
    }

    @Override
    public int idDropped(int par1, Random random, int par2) {
        return type.getDrops().itemID;
    }

    @Override
    public TileEntity createTileEntity(World world, int metadata) {
        this.type = EnumBushType.getTypeFromMetadata(metadata);
        return new TileBush(type);
    }

    public void grow(World world, int x, int y, int z) {
        TileBush tile = (TileBush) world.getBlockTileEntity(x, y, z);
        tile.updateStage();
    }
}
