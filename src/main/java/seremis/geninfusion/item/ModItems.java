package seremis.geninfusion.item;

import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.item.Item;
import net.minecraftforge.oredict.OreDictionary;
import seremis.geninfusion.lib.DefaultProps;
import seremis.geninfusion.lib.Items;

public class ModItems {

    public static GIItem titaniumIngot;
    public static ItemDebugger thermometer;

    public static void init() {
        titaniumIngot = new GIItem().setUnlocalizedName(Items.TITANIUM_INGOT_UNLOCALIZED_NAME());
        thermometer = new ItemDebugger();

        registerItem(titaniumIngot, Items.TITANIUM_INGOT_UNLOCALIZED_NAME());
        registerItem(thermometer, Items.THERMOMETER_UNLOCALIZED_NAME());

        oreDictionary();
    }

    public static void oreDictionary() {
        OreDictionary.registerOre("ingotTitanium", titaniumIngot);
    }

    public static void registerItem(Item item, String name) {
        GameRegistry.registerItem(item, DefaultProps.nameLower() + ".item." + name);
    }
}
