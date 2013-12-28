package seremis.soulcraft.item;

import net.minecraft.item.ItemStack;

public class ItemBerry extends SCItemFood {

    private String[] subNames = {"berry0", "berry1", "berry2", "berry3"};

    public ItemBerry(int ID) {
        super(ID, 4);
        setHasSubtypes(true);
        setNumbersofMetadata(2);
        setMaxDamage(0);
        setUnlocalizedName("berry");
        setAlwaysEdible();
    }

    @Override
    public String getUnlocalizedName(ItemStack itemstack) {
        return getUnlocalizedName() + "." + subNames[itemstack.getItemDamage()];
    }
}
