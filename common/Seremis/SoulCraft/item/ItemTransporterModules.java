package Seremis.SoulCraft.item;

import java.util.List;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import Seremis.SoulCraft.core.lib.Items;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemTransporterModules extends SCItem {

    private String[] subNames = {Items.TRANSPORTER_MODULES_META_0_UNLOCALIZED_NAME, Items.TRANSPORTER_MODULES_META_1_UNLOCALIZED_NAME, Items.TRANSPORTER_MODULES_META_2_UNLOCALIZED_NAME};

    public ItemTransporterModules(int ID) {
        super(ID);
        setHasSubtypes(true);
        setMaxDamage(0);
        setUnlocalizedName(Items.TRANSPORTER_MODULES_UNLOCALIZED_NAME);
        setNumbersofMetadata(3);
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

    public static ItemStack engine() {
        return new ItemStack(ModItems.transporterModules, 1, 1);
    }

    public static ItemStack storage() {
        return new ItemStack(ModItems.transporterModules, 1, 0);
    }

    public boolean isTransporter(ItemStack stack) {
        return stack.itemID == itemID && stack.getItemDamage() == 2;
    }

    public boolean isUpgrade(ItemStack stack) {
        return stack.itemID == itemID && stack.getItemDamage() == 1 || stack.itemID == itemID && stack.getItemDamage() == 0;
    }
}
