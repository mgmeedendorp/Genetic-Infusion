package Seremis.SoulCraft.block;

import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;

public class BlockBushBerryItem extends ItemBlock {

    private static String[] subNames = { "blockBushBerryStage1", "blockBushBerryStage2", "blockBushBerryStage3", "blockBushBerryStage4", "blockBushBerryStage5", "blockBushBerryStage6", "blockBushBerryStage7" };

    public BlockBushBerryItem(int ID) {
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
