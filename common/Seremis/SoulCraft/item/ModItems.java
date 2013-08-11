package Seremis.SoulCraft.item;

import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;
import Seremis.SoulCraft.core.lib.DefaultProps;
import cpw.mods.fml.common.registry.LanguageRegistry;

public class ModItems {

    public static SCItem ingotTitanium;
    public static SCItem shardIsolatzium;
    public static SCItem plateTitanium;
    public static SCItem alloyIsolatzium;
    public static ItemTransporterModules transporterModules;
    public static SCItem berry;
    public static ItemThermometer thermometer;

    public static void init() {
        ingotTitanium = new SCItem(DefaultProps.TitaniumIngotID).setUnlocalizedName("ingotTitanium");
        shardIsolatzium = new ItemShardIsolatzium(DefaultProps.ShardIsolatziumID);
        plateTitanium = new SCItem(DefaultProps.TitaniumPlateID).setUnlocalizedName("plateTitanium");
        alloyIsolatzium = new ItemAlloyIsolatzium(DefaultProps.IsolatziumAlloyID);
        transporterModules = new ItemTransporterModules(DefaultProps.TransporterModulesID);
        berry = new ItemBerry(DefaultProps.BerryID);
        thermometer = new ItemThermometer(DefaultProps.ThermometerID);

        LanguageRegistry.addName(ingotTitanium, "Titanium Ingot");
        LanguageRegistry.addName(new ItemStack(shardIsolatzium, 1, 0), "Red Isolatzium shard");
        LanguageRegistry.addName(new ItemStack(shardIsolatzium, 1, 1), "Green Isolatzium shard");
        LanguageRegistry.addName(new ItemStack(shardIsolatzium, 1, 2), "Blue Isolatzium shard");
        LanguageRegistry.addName(new ItemStack(shardIsolatzium, 1, 3), "Black Isolatzium shard");
        LanguageRegistry.addName(plateTitanium, "Titanium Plate");
        LanguageRegistry.addName(new ItemStack(alloyIsolatzium, 1, 0), "Red Isolatzium Alloy");
        LanguageRegistry.addName(new ItemStack(alloyIsolatzium, 1, 1), "Green Isolatzium Alloy");
        LanguageRegistry.addName(new ItemStack(alloyIsolatzium, 1, 2), "Blue Isolatzium Alloy");
        LanguageRegistry.addName(new ItemStack(alloyIsolatzium, 1, 3), "Black Isolatzium Alloy");
        LanguageRegistry.addName(new ItemStack(transporterModules, 1, 0), "Storage Module");
        LanguageRegistry.addName(new ItemStack(transporterModules, 1, 1), "Engine Module");
        LanguageRegistry.addName(berry, "Berry");
        LanguageRegistry.addName(thermometer, "Thermometer");

        oreDictionary();
    }

    public static void oreDictionary() {
        OreDictionary.registerOre("ingotTitanium", ingotTitanium);
        OreDictionary.registerOre("shardIsolatziumRed", new ItemStack(shardIsolatzium, 1, 0));
        OreDictionary.registerOre("shardIsolatziumGreen", new ItemStack(shardIsolatzium, 1, 1));
        OreDictionary.registerOre("shardIsolatziumBlue", new ItemStack(shardIsolatzium, 1, 2));
        OreDictionary.registerOre("shardIsolatziumBlack", new ItemStack(shardIsolatzium, 1, 3));
        OreDictionary.registerOre("plateTitanium", plateTitanium);
        OreDictionary.registerOre("alloyIsolatziumRed", new ItemStack(alloyIsolatzium, 1, 0));
        OreDictionary.registerOre("alloyIsolatziumGreen", new ItemStack(alloyIsolatzium, 1, 1));
        OreDictionary.registerOre("alloyIsolatziumBlue", new ItemStack(alloyIsolatzium, 1, 2));
        OreDictionary.registerOre("alloyIsolatziumBlack", new ItemStack(alloyIsolatzium, 1, 3));
        OreDictionary.registerOre("berry", berry);
    }
}
