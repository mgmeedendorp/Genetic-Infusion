package seremis.geninfusion.block;

import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.item.ItemBlock;
import net.minecraftforge.oredict.OreDictionary;
import seremis.geninfusion.lib.Blocks;
import seremis.geninfusion.tileentity.TileCrystal;
import seremis.geninfusion.world.GIWorldGenerator;

public class ModBlocks {

    public static Block oreTitanium;
    public static BlockCrystal crystal;
    public static BlockConnectedGlass connectedGlass;

    public static GIWorldGenerator worldGen;

    public static void init() {

        oreTitanium = new GIBlock(Material.rock).setHardness(20F).setTextureName(Blocks.ORE_TITANIUM_UNLOCALIZED_NAME());
        crystal = new BlockCrystal(Material.coral);
        connectedGlass = new BlockConnectedGlass(Material.glass);

        registerBlock(oreTitanium, Blocks.ORE_TITANIUM_UNLOCALIZED_NAME());
        registerBlock(crystal, Blocks.CRYSTAL_UNLOCALIZED_NAME());
        registerBlock(connectedGlass, Blocks.CONNECTED_GLASS_UNLOCALIZED_NAME());

        worldGen = new GIWorldGenerator();
        GameRegistry.registerWorldGenerator(worldGen, 0);
        oreDictionary();
        tileEntity();
    }

    public static void oreDictionary() {
        OreDictionary.registerOre("oreTitanium", oreTitanium);
    }

    public static void tileEntity() {
        GameRegistry.registerTileEntity(TileCrystal.class, Blocks.CRYSTAL_UNLOCALIZED_NAME());
    }

    public static void registerBlock(Block block, String name) {
        GameRegistry.registerBlock(block, name);
    }

    public static void registerBlock(Block block, Class<? extends ItemBlock> itemBlock, String name) {
        GameRegistry.registerBlock(block, itemBlock, name);
    }
}
