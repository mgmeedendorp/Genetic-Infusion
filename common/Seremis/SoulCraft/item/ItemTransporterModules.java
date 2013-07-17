package Seremis.SoulCraft.item;

import java.util.List;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemTransporterModules extends SCItem {

    private String[] subNames = { "transporterStorage", "transporterEngine" };

    public ItemTransporterModules(int ID) {
        super(ID);
        setHasSubtypes(true);
        setMaxDamage(0);
        setUnlocalizedName("transporterModules");
        setNumbersofMetadata(2);
    }

    @Override
    public String getUnlocalizedName(ItemStack itemstack) {
        return getUnlocalizedName() + "." + subNames[itemstack.getItemDamage()];
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void getSubItems(int par1, CreativeTabs creativetab, List list) {
        for(int var4 = 0; var4 < this.getNumbersofMetadata(); ++var4) {
            list.add(new ItemStack(par1, 1, var4));
        }
    }

    public static ItemStack engine() {
        return new ItemStack(ModItems.transporterModules, 1, 1);
    }

    public static ItemStack storage() {
        return new ItemStack(ModItems.transporterModules, 1, 0);
    }
}
