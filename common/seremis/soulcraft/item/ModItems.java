package seremis.soulcraft.item;

import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;
import seremis.soulcraft.core.lib.Items;
import cpw.mods.fml.common.registry.LanguageRegistry;

public class ModItems {

    public static SCItem titaniumIngot;
    public static SCItem crystalShard;
    public static SCItem plateTitanium;
    public static SCItem crystalAlloy;
    public static ItemTransporterModules transporterModules;
    public static ItemBerry berry;
    public static ItemKernel kernel;
    public static ItemThermometer thermometer;

    public static void init() {
        titaniumIngot = new SCItem(Items.titaniumIngotID).setUnlocalizedName(Items.TITANIUM_INGOT_UNLOCALIZED_NAME);
        crystalShard = new ItemShardIsolatzium(Items.crystalShardID);
        plateTitanium = new SCItem(Items.titaniumPlateID).setUnlocalizedName(Items.TITANIUM_PLATE_UNLOCALIZED_NAME);
        crystalAlloy = new ItemCrystalAlloy(Items.crystalAlloyID);
        transporterModules = new ItemTransporterModules(Items.transporterModulesID);
        berry = new ItemBerry(Items.berryID);
        kernel = new ItemKernel(Items.kernelID);
        thermometer = new ItemThermometer(Items.thermometerID);

        LanguageRegistry.addName(titaniumIngot, Items.TITANIUM_INGOT_LOCALIZED_NAME);
        LanguageRegistry.addName(new ItemStack(crystalShard, 1, 0), Items.CRYSTAL_SHARD_META_0_LOCALIZED_NAME);
        LanguageRegistry.addName(new ItemStack(crystalShard, 1, 1), Items.CRYSTAL_SHARD_META_1_LOCALIZED_NAME);
        LanguageRegistry.addName(new ItemStack(crystalShard, 1, 2), Items.CRYSTAL_SHARD_META_2_LOCALIZED_NAME);
        LanguageRegistry.addName(new ItemStack(crystalShard, 1, 3), Items.CRYSTAL_SHARD_META_3_LOCALIZED_NAME);
        LanguageRegistry.addName(plateTitanium, Items.TITANIUM_PLATE_LOCALIZED_NAME);
        LanguageRegistry.addName(new ItemStack(crystalAlloy, 1, 0), Items.CRYSTAL_ALLOY_META_0_LOCALIZED_NAME);
        LanguageRegistry.addName(new ItemStack(crystalAlloy, 1, 1), Items.CRYSTAL_ALLOY_META_1_LOCALIZED_NAME);
        LanguageRegistry.addName(new ItemStack(crystalAlloy, 1, 2), Items.CRYSTAL_ALLOY_META_2_LOCALIZED_NAME);
        LanguageRegistry.addName(new ItemStack(crystalAlloy, 1, 3), Items.CRYSTAL_ALLOY_META_3_LOCALIZED_NAME);
        LanguageRegistry.addName(new ItemStack(transporterModules, 1, 0), Items.TRANSPORTER_MODULES_META_0_LOCALIZED_NAME);
        LanguageRegistry.addName(new ItemStack(transporterModules, 1, 1), Items.TRANSPORTER_MODULES_META_1_LOCALIZED_NAME);
        LanguageRegistry.addName(new ItemStack(transporterModules, 1, 2), Items.TRANSPORTER_MODULES_META_2_LOCALIZED_NAME);
        LanguageRegistry.addName(new ItemStack(berry, 1, 0), Items.BERRY_META_0_LOCALIZED_NAME);
        LanguageRegistry.addName(new ItemStack(berry, 1, 1), Items.BERRY_META_1_LOCALIZED_NAME);
        LanguageRegistry.addName(new ItemStack(kernel, 1, 0), Items.KERNEL_META_0_LOCALIZED_NAME);
        LanguageRegistry.addName(new ItemStack(kernel, 1, 1), Items.KERNEL_META_1_LOCALIZED_NAME);
        LanguageRegistry.addName(new ItemStack(thermometer, 1, 0), Items.THERMOMETER_META_0_LOCALIZED_NAME);
        LanguageRegistry.addName(new ItemStack(thermometer, 1, 1), Items.THERMOMETER_META_1_LOCALIZED_NAME);

        oreDictionary();
    }

    public static void oreDictionary() {
        OreDictionary.registerOre("ingotTitanium", titaniumIngot);
        OreDictionary.registerOre("shardIsolatziumRed", new ItemStack(crystalShard, 1, 0));
        OreDictionary.registerOre("shardIsolatziumGreen", new ItemStack(crystalShard, 1, 1));
        OreDictionary.registerOre("shardIsolatziumBlue", new ItemStack(crystalShard, 1, 2));
        OreDictionary.registerOre("shardIsolatziumBlack", new ItemStack(crystalShard, 1, 3));
        OreDictionary.registerOre("plateTitanium", plateTitanium);
        OreDictionary.registerOre("alloyIsolatziumRed", new ItemStack(crystalAlloy, 1, 0));
        OreDictionary.registerOre("alloyIsolatziumGreen", new ItemStack(crystalAlloy, 1, 1));
        OreDictionary.registerOre("alloyIsolatziumBlue", new ItemStack(crystalAlloy, 1, 2));
        OreDictionary.registerOre("alloyIsolatziumBlack", new ItemStack(crystalAlloy, 1, 3));
        OreDictionary.registerOre("berryRed", new ItemStack(berry, 1, 0));
        OreDictionary.registerOre("berryBlue", new ItemStack(berry, 1, 1));
    }
}
