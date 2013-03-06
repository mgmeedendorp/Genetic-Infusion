package SoulCraft.items;

import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;

public class OreSoulCrystalItem extends ItemBlock {
	
	private static String[] subNames = {"OreSoulCrystalRed", "OreSoulCrystalGreen", "OreSoulCrystalBlue", "OreSoulCrystalBlack", "OreTitanium"};

	public OreSoulCrystalItem(int ID) {
		super(ID);
		setHasSubtypes(true);
		setItemName("OreSoulCrystalItem");
	}
	
	@Override
	public int getMetadata (int damageValue) {
		return damageValue;
	}
	
	@Override
	public String getItemNameIS(ItemStack itemstack) {
		return getItemName() + "." + subNames[itemstack.getItemDamage()];
	}

}
