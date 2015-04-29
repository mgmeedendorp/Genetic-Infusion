package seremis.geninfusion.block;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import seremis.geninfusion.GeneticInfusion;
import seremis.geninfusion.lib.DefaultProps;

import java.util.List;

public class GIBlock extends Block {

    private IIcon[] iconBuffer;
    private int metadata = 0;
    private boolean needsIcon = true;
    private IIcon[] sidedIconBuffer;
    private boolean needsSidedTexture = false;
    private String[] sidedTextureNames = {"bottom", "top", "back", "front", "left", "right"};

    public GIBlock(Material material) {
        super(material);
        this.setTextureName("");
        setCreativeTab(GeneticInfusion.creativeTab());
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerIcons(IIconRegister iconRegister) {
        if(!needsIcon) {
            return;
        }
        if(this.needsSidedTexture) {
            sidedIconBuffer = new IIcon[sidedTextureNames.length];
            for(int i = 0; i < sidedTextureNames.length; i++) {
                sidedIconBuffer[i] = iconRegister.registerIcon(DefaultProps.ID() + ":" + getUnlocalizedName().substring(5) + "_" + sidedTextureNames[i]);
            }
        }
        if(this.metadata == 0) {
            blockIcon = iconRegister.registerIcon(DefaultProps.ID() + ":" + getUnlocalizedName().substring(5));
        } else {
            iconBuffer = new IIcon[metadata];
            for(int x = 0; x < iconBuffer.length; x++) {
                iconBuffer[x] = iconRegister.registerIcon(DefaultProps.ID() + ":" + getUnlocalizedName().substring(5) + (x + 1));
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
}
