package seremis.geninfusion.item;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import seremis.geninfusion.lib.Items;

import java.util.List;

public class ItemShardIsolatzium extends GIItem {

    private String[] subNames = {Items.CRYSTAL_SHARD_META_0_UNLOCALIZED_NAME, Items.CRYSTAL_SHARD_META_1_UNLOCALIZED_NAME, Items.CRYSTAL_SHARD_META_2_UNLOCALIZED_NAME, Items.CRYSTAL_SHARD_META_3_UNLOCALIZED_NAME};

    public ItemShardIsolatzium() {
        super();
        setHasSubtypes(true);
        setMaxDamage(0);
        setUnlocalizedName(Items.CRYSTAL_SHARD_UNLOCALIZED_NAME);
        setNumbersofMetadata(4);
    }

    @Override
    public String getUnlocalizedName(ItemStack itemstack) {
        return getUnlocalizedName() + "." + subNames[itemstack.getItemDamage()];
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void getSubItems(Item item, CreativeTabs creativetab, List list) {
        for(int var4 = 0; var4 < getNumbersofMetadata(); ++var4) {
            list.add(new ItemStack(item, 1, var4));
        }
    }
}
