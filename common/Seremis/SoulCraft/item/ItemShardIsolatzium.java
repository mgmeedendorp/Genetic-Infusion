package Seremis.SoulCraft.item;

import java.util.List;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import Seremis.SoulCraft.core.lib.Items;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemShardIsolatzium extends SCItem {

    private String[] subNames = {Items.CRYSTAL_SHARD_META_0_UNLOCALIZED_NAME, Items.CRYSTAL_SHARD_META_1_UNLOCALIZED_NAME, Items.CRYSTAL_SHARD_META_2_UNLOCALIZED_NAME, Items.CRYSTAL_SHARD_META_3_UNLOCALIZED_NAME};

    public ItemShardIsolatzium(int ID) {
        super(ID);
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
    public void getSubItems(int par1, CreativeTabs creativetab, List list) {
        for(int var4 = 0; var4 < getNumbersofMetadata(); ++var4) {
            list.add(new ItemStack(par1, 1, var4));
        }
    }
}
