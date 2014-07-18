package seremis.geninfusion.core;

import seremis.geninfusion.item.ModItems;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;

public class GICreativeTab extends CreativeTabs {
    public GICreativeTab(String name) {
        super(name);
    }
    
	@Override
	public Item getTabIconItem() {
		return ModItems.titaniumIngot;
	}

}
