package Seremis.SoulCraft.block;

import java.util.List;

import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Icon;
import net.minecraft.world.World;
import Seremis.SoulCraft.mod_SoulCraft;
import Seremis.SoulCraft.core.lib.DefaultProps;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class SCBlock extends BlockContainer {

    private Icon[] iconBuffer;
    private int metadata = 0;
    private boolean needsIcon = true;

    public SCBlock(int ID, Material material) {
        super(ID, material);
        setUnlocalizedName("");
        setCreativeTab(mod_SoulCraft.CreativeTab);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerIcons(IconRegister iconRegister) {
        if(!needsIcon) {
            return;
        }
        
        if(this.metadata == 0) {
            blockIcon = iconRegister.registerIcon(DefaultProps.ID + ":" + this.getUnlocalizedName().substring(5));
        } else {
            iconBuffer = new Icon[metadata];
            for(int x = 0; x < iconBuffer.length; x++) {
                iconBuffer[x] = iconRegister.registerIcon(DefaultProps.ID + ":" + this.getUnlocalizedName().substring(5) + (x + 1));
            }
        }
    }

    @Override
    @SideOnly(Side.CLIENT)
    public Icon getIcon(int side, int metadata) {
        if(this.metadata > 0 && needsIcon) {
            blockIcon = iconBuffer[metadata];
        }
        return this.blockIcon;
    }
    
    @Override
    @SideOnly(Side.CLIENT)
    public void getSubBlocks(int blockID, CreativeTabs tab, List subItems) {
        if(metadata > 0) {
            for(int ix = 0; ix < this.getNumbersOfMetadata(); ix++) {
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

    @Override
    public TileEntity createNewTileEntity(World world) {
        return null;
    }
}
