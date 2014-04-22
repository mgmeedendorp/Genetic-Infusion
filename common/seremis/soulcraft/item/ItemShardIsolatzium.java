package seremis.soulcraft.item;

import java.util.List;

import seremis.soulcraft.core.lib.Items;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemShardIsolatzium extends SCItem {

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
