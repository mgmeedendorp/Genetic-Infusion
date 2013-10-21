package Seremis.SoulCraft.block;

import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import Seremis.SoulCraft.core.lib.Blocks;

public class BlockBushItem extends ItemBlock {

    private static String[] subNames = {Blocks.BUSH_ITEM_META_0_UNLOCALIZED_NAME, Blocks.BUSH_ITEM_META_1_UNLOCALIZED_NAME};

    public BlockBushItem(int ID) {
        super(ID);
        setHasSubtypes(true);
        setUnlocalizedName(Blocks.BUSH_ITEM_UNLOCALIZED_NAME);
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
