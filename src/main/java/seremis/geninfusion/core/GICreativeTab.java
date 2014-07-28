package seremis.geninfusion.core;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import seremis.geninfusion.item.ModItems;

public class GICreativeTab extends CreativeTabs {
    public GICreativeTab(String name) {
        super(name);
    }
    
	@Override
	public Item getTabIconItem() {
		return ModItems.titaniumIngot;
	}

}
