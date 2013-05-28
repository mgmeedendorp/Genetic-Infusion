package Seremis.SoulCraft.items;

import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.item.Item;
import net.minecraft.util.Icon;
import Seremis.SoulCraft.mod_SoulCraft;
import Seremis.SoulCraft.core.lib.DefaultProps;

public class SCItem extends Item {

    private Icon iconBuffer[];
    private int metadata = 0;
    
	public SCItem(int ID) {
		super(ID);
		setMaxStackSize(64);
		setCreativeTab(mod_SoulCraft.CreativeTab);
	}

	@Override
	public SCItem setUnlocalizedName(String par1Str) {
	    return (SCItem) super.setUnlocalizedName(par1Str);
	}
	
	@Override
    public void updateIcons(IconRegister iconRegister) {
        if(this.metadata == 0) {
             iconIndex = iconRegister.registerIcon(DefaultProps.ID+":"+this.getUnlocalizedName().substring(5));
        } else {
            iconBuffer = new Icon[metadata+1];
            for(int x = 1; x<metadata+1; x++) {
                iconBuffer[x] = iconRegister.registerIcon(DefaultProps.ID+":"+this.getUnlocalizedName().substring(5)+x);
            }
        }
    }

    @Override
    public Icon getIconFromDamage(int metadata)
    {
        if(this.metadata != 0)
        {
            iconIndex = iconBuffer[metadata+1];
        }
        return this.iconIndex;
    }
    
    public int getNumbersofMetadata() {
        return this.metadata;
    }
    
    public void setNumbersofMetadata(int metadata) {
        this.metadata = metadata;
    }

}
