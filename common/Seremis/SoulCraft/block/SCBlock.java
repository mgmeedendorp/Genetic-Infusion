package Seremis.SoulCraft.block;

import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
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
    
	public SCBlock(int ID, Material material) {
		super(ID, material);
		setUnlocalizedName("");
		setCreativeTab(mod_SoulCraft.CreativeTab);
	}
	
	@Override
	@SideOnly(Side.CLIENT)
    public void registerIcons(IconRegister iconRegister) {
        if(this.metadata == 0) {
             blockIcon = iconRegister.registerIcon(DefaultProps.ID+":"+this.getUnlocalizedName().substring(5));
        } else if(metadata < 0) {
            blockIcon = null;
        } else {
            iconBuffer = new Icon[metadata];
            for(int x = 0; x<iconBuffer.length; x++) {
                iconBuffer[x] = iconRegister.registerIcon(DefaultProps.ID+":" + this.getUnlocalizedName().substring(5)+(x+1));
            }
        }
    }

    @Override
    public Icon getIcon(int side, int metadata) {
        if(this.metadata > 0)
        {
            blockIcon = iconBuffer[metadata];
        }
        return this.blockIcon;
    }
    
    public int getNumbersOfMetadata() {
        return metadata;
    }
    
    public void setNumbersofMetadata(int metadata) {
        this.metadata = metadata;
    }


	@Override
	public TileEntity createNewTileEntity(World var1) {
		return null;
	}
}
