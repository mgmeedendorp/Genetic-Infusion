package Seremis.SoulCraft.block;

import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Icon;
import net.minecraft.world.World;
import Seremis.SoulCraft.mod_SoulCraft;
import Seremis.SoulCraft.core.lib.DefaultProps;

public class SCBlock extends BlockContainer {
    
    private Icon[] iconBuffer;
    private int metadata = 0;
    
	public SCBlock(int ID, Material material) {
		super(ID, material);
		setUnlocalizedName("");
		setCreativeTab(mod_SoulCraft.CreativeTab);
	}
	
	@Override
    public void registerIcons(IconRegister iconRegister) {
        if(this.metadata == 0) {
             blockIcon = iconRegister.registerIcon(DefaultProps.ID+":"+this.getUnlocalizedName().substring(5));
        } else {
            iconBuffer = new Icon[metadata+1];
            for(int x = 1; x<metadata+1; x++) {
                iconBuffer[x] = iconRegister.registerIcon(DefaultProps.ID+":" + this.getUnlocalizedName().substring(5)+x);
            }
        }
    }

    @Override
    public Icon getBlockTextureFromSideAndMetadata(int side, int metadata)
    {
        if(this.metadata > 0)
        {
            blockIcon = iconBuffer[metadata+1];
        }
        return this.blockIcon;
    }
    
    public void setNumbersofMetadata(int metadata) {
        this.metadata = metadata;
    }


	@Override
	public TileEntity createNewTileEntity(World var1) {
		return null;
	}
}
