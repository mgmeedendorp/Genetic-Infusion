package Seremis.SoulCraft.item;

import java.util.List;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemAlloyIsolatzium extends SCItem {
    
    private String[] subNames = {"AlloyIsolatziumRed", "AlloyIsolatziumGreen", "AlloyIsolatziumBlue", "AlloyIsolatziumBlack"};

    public ItemAlloyIsolatzium(int ID) {
        super(ID);
        setHasSubtypes(true);
        setMaxDamage(0);
        setUnlocalizedName("isolatziumAlloy");
        setNumbersofMetadata(4);
    }
    
    @Override
    public String getUnlocalizedName(ItemStack itemstack) {
        return getUnlocalizedName() + "." + subNames[itemstack.getItemDamage()];
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void getSubItems(int par1, CreativeTabs creativetab, List list) {
        for(int var4 = 0; var4 < getNumbersofMetadata(); ++var4) {
            list.add(new ItemStack(par1, 1, var4));
        }
    }
}
