package SoulCraft.items;

import net.minecraft.item.Item;
import SoulCraft.mod_SoulCraft;
import SoulCraft.core.DefaultProps;

public class SCItem extends Item {

	public SCItem(int ID, int texture) {
		super(ID);
		setMaxStackSize(64);
		setCreativeTab(mod_SoulCraft.CreativeTab);
		setIconIndex(texture);
		setTextureFile(DefaultProps.ITEMS_TEXTURE_FILE);
	}

}
