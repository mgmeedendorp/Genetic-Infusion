package seremis.soulcraft.block;

import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import seremis.soulcraft.core.lib.Blocks;

public class BlockOreIsolatziumItem extends ItemBlock {

    private static String[] subNames = {Blocks.ORE_ISOLATZIUM_ITEM_META_0_UNLOCALIZED_NAME, Blocks.ORE_ISOLATZIUM_ITEM_META_1_UNLOCALIZED_NAME, Blocks.ORE_ISOLATZIUM_ITEM_META_2_UNLOCALIZED_NAME, Blocks.ORE_ISOLATZIUM_ITEM_META_3_UNLOCALIZED_NAME};

    public BlockOreIsolatziumItem(int ID) {
        super(ID);
        setHasSubtypes(true);
        setUnlocalizedName(Blocks.ORE_ISOLATZIUM_ITEM_UNLOCALIZED_NAME);
    }

    @Override
    public int getMetadata(int damageValue) {
        return damageValue;
    }

    @Override
    public String getUnlocalizedName(ItemStack itemstack) {
        return getUnlocalizedName() + "." + subNames[itemstack.getItemDamage()];
    }

}
