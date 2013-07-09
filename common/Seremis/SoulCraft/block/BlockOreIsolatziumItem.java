package Seremis.SoulCraft.block;

import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;

public class BlockOreIsolatziumItem extends ItemBlock {
	
	private static String[] subNames = {"OreSoulCrystalRed", "OreSoulCrystalGreen", "OreSoulCrystalBlue", "OreSoulCrystalBlack", "OreTitanium"};

	public BlockOreIsolatziumItem(int ID) {
		super(ID);
		setHasSubtypes(true);
		setUnlocalizedName("oreIsolatziumItem");
	}
	
	@Override
	public int getMetadata (int damageValue) {
		return damageValue;
	}
	
	@Override
	public String getUnlocalizedName(ItemStack itemstack) {
		return getUnlocalizedName() + "." + subNames[itemstack.getItemDamage()];
	}

}
