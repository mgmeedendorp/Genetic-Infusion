package Seremis.SoulCraft.item;

import net.minecraft.item.ItemStack;

public class ItemAlloyIsolatzium extends SCItem {

    private String[] subNames = { "AlloyIsolatziumRed", "AlloyIsolatziumGreen", "AlloyIsolatziumBlue", "AlloyIsolatziumBlack" };

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
}
