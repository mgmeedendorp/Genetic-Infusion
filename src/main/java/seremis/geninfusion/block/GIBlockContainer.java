package seremis.geninfusion.block;

import java.util.List;

import seremis.geninfusion.GeneticInfusion;
import seremis.geninfusion.lib.DefaultProps;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class GIBlockContainer extends BlockContainer {

    private IIcon[] iconBuffer;
    private int metadata = 0;
    private boolean needsIcon = true;
    private IIcon[] sidedIconBuffer;
    private boolean needsSidedTexture = false;
    private String[] sidedTextureNames = {"bottom", "top", "back", "front", "left", "right"};;

    public GIBlockContainer(Material material) {
        super(material);
        setBlockName("");
        setCreativeTab(GeneticInfusion.CreativeTab);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerBlockIcons(IIconRegister iconRegister) {
        if(!needsIcon) {
            return;
        }
        if(this.needsSidedTexture) {
            sidedIconBuffer = new IIcon[sidedTextureNames.length];
            for(int i = 0; i < sidedTextureNames.length; i++) {
                sidedIconBuffer[i] = iconRegister.registerIcon(DefaultProps.ID + ":" + getUnlocalizedName().substring(5) + "_" + sidedTextureNames[i]);
            }
        }
        if(this.metadata == 0 && !needsSidedTexture) {
            blockIcon = iconRegister.registerIcon(DefaultProps.ID + ":" + getUnlocalizedName().substring(5));
        } else {
            iconBuffer = new IIcon[metadata];
            for(int x = 0; x < iconBuffer.length; x++) {
                iconBuffer[x] = iconRegister.registerIcon(DefaultProps.ID + ":" + getUnlocalizedName().substring(5) + (x + 1));
            }
        }
    }

    @Override
    @SideOnly(Side.CLIENT)
    public IIcon getIcon(int side, int metadata) {
        if(this.metadata > 0 && needsIcon) {
            blockIcon = iconBuffer[metadata];
        }
        if(needsSidedTexture) {
            return getSidedIcons()[side];
        }
        return this.blockIcon;
    }

    @SideOnly(Side.CLIENT)
    public IIcon[] getSidedIcons() {
        return sidedIconBuffer;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void getSubBlocks(Item item, CreativeTabs tab, List subItems) {
        if(metadata > 0) {
            for(int ix = 0; ix < getNumbersOfMetadata(); ix++) {
                subItems.add(new ItemStack(this, 1, ix));
            }
        } else {
            subItems.add(new ItemStack(this, 1, 0));
        }
    }

    public int getNumbersOfMetadata() {
        return metadata;
    }

    public void setNumbersofMetadata(int metadata) {
        this.metadata = metadata;
    }

    public void setNeedsIcon(boolean needsIcon) {
        this.needsIcon = needsIcon;
    }

    public void setNeedsSidedTexture(boolean needsSidedTexture, String[] textureNames) {
        this.needsSidedTexture = needsSidedTexture;
        this.sidedTextureNames = textureNames;
    }

    @Override
    public TileEntity createNewTileEntity(World world, int metadata) {
        return null;
    }
}
