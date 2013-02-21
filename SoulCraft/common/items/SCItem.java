package voidrunner101.SoulCraft.common.items;

import net.minecraft.item.Item;
import voidrunner101.SoulCraft.mod_SoulCraft;
import voidrunner101.SoulCraft.common.core.DefaultProps;

public class SCItem extends Item {

	public SCItem(int ID, int texture) {
		super(ID);
		setMaxStackSize(64);
		setCreativeTab(mod_SoulCraft.CreativeTab);
		setIconIndex(texture);
		setTextureFile(DefaultProps.ITEMS_TEXTURE_FILE);
	}

}
