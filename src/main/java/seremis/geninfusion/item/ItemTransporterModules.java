package seremis.geninfusion.item;

import seremis.geninfusion.lib.Items;
import net.minecraft.item.ItemStack;

public class ItemTransporterModules extends GIItem {

    private String[] subNames = {Items.TRANSPORTER_MODULES_META_0_UNLOCALIZED_NAME, Items.TRANSPORTER_MODULES_META_1_UNLOCALIZED_NAME, Items.TRANSPORTER_MODULES_META_2_UNLOCALIZED_NAME};

    public ItemTransporterModules() {
        super();
        setHasSubtypes(true);
        setMaxDamage(0);
        setUnlocalizedName(Items.TRANSPORTER_MODULES_UNLOCALIZED_NAME);
        setNumbersofMetadata(3);
    }

    @Override
    public String getUnlocalizedName(ItemStack itemstack) {
        return getUnlocalizedName() + "." + subNames[itemstack.getItemDamage()];
    }

    public static ItemStack engine() {
        return new ItemStack(ModItems.transporterModules, 1, 1);
    }

    public static ItemStack storage() {
        return new ItemStack(ModItems.transporterModules, 1, 0);
    }

    public boolean isTransporter(ItemStack stack) {
        return stack.isItemEqual(new ItemStack(this)) && stack.getItemDamage() == 2;
    }

    public boolean isUpgrade(ItemStack stack) {
        return stack.isItemEqual(new ItemStack(this)) && stack.getItemDamage() == 1 || stack.isItemEqual(new ItemStack(this)) && stack.getItemDamage() == 0;
    }
}
