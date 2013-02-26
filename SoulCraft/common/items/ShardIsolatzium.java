package voidrunner101.SoulCraft.common.items;

import java.util.List;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ShardIsolatzium extends SCItem {

	private String[] subNames = {"ShardIsolatziumRed", "ShardIsolatziumGreen", "ShardIsolatziumBlue", "ShardIsolatziumBlack"};
	
	public ShardIsolatzium(int ID, int texture) {
		super(ID, texture);
		setHasSubtypes(true);
		setMaxDamage(0);
		setItemName("isolatziumShard");
	}
	
	@SideOnly(Side.CLIENT)
	public int getIconFromDamage(int i){
		return i + 1;
	}

	@Override
	public String getItemNameIS(ItemStack itemstack) {
		return getItemName() + "." + subNames[itemstack.getItemDamage()];
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void getSubItems(int par1, CreativeTabs creativetab, List list) {
		for(int var4 = 0; var4 < 4; ++var4) {
			list.add(new ItemStack(par1, 1, var4));
		}
	}
}
