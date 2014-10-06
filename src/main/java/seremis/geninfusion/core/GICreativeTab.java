package seremis.geninfusion.core;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import seremis.geninfusion.item.ModItems;

public class GICreativeTab extends CreativeTabs {
    public GICreativeTab(String name) {
        super(name);
    }
    
	@Override
    @SideOnly(Side.CLIENT)
	public Item getTabIconItem() {
		return ModItems.titaniumIngot;
	}

}
