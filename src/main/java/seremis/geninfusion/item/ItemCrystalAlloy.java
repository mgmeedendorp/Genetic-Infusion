package seremis.geninfusion.item;

import net.minecraft.item.ItemStack;
import seremis.geninfusion.lib.Items;

public class ItemCrystalAlloy extends GIItem {

    private String[] subNames = {Items.CRYSTAL_ALLOY_META_0_UNLOCALIZED_NAME, Items.CRYSTAL_ALLOY_META_1_UNLOCALIZED_NAME, Items.CRYSTAL_ALLOY_META_2_UNLOCALIZED_NAME, Items.CRYSTAL_ALLOY_META_3_UNLOCALIZED_NAME};

    public ItemCrystalAlloy() {
        super();
        setHasSubtypes(true);
        setMaxDamage(0);
        setUnlocalizedName(Items.CRYSTAL_ALLOY_UNLOCALIZED_NAME);
        setNumbersofMetadata(4);
    }

    @Override
    public String getUnlocalizedName(ItemStack itemstack) {
        return getUnlocalizedName() + "." + subNames[itemstack.getItemDamage()];
    }
}
