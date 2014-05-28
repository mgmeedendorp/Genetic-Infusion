package seremis.soulcraft.core;

import seremis.soulcraft.item.ModItems;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;

public class SCCreativeTab extends CreativeTabs {
    public SCCreativeTab(String name) {
        super(name);
    }
    
	@Override
	public Item getTabIconItem() {
		return ModItems.titaniumIngot;
	}

}
