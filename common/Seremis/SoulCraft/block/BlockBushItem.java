package Seremis.SoulCraft.block;

import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;

public class BlockBushItem extends ItemBlock {

    private static String[] subNames = {"blockBush1", "blockBush2", "blockBush3", "blockBush4", "blockBush5"};

    public BlockBushItem(int ID) {
        super(ID);
        setHasSubtypes(true);
        setUnlocalizedName("bushBerryItem");
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
