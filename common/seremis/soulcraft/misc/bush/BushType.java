package seremis.soulcraft.misc.bush;

import java.util.HashMap;

import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import cpw.mods.fml.client.FMLClientHandler;

public class BushType {

    private int maxStage;
    // First stage with berries
    private int berryStage;

    private HashMap<ItemStack, Float> chanceDrops = new HashMap<ItemStack, Float>();

    public String[] textureMap;

    public BushType(int maxStage, int berryStage) {
        this.maxStage = maxStage;
        this.berryStage = berryStage;
        textureMap = new String[maxStage];
    }

    protected void addTextureToStage(int stage, String textureLoc) {
        if(stage > maxStage) {
           // SCLogger.log(Level.SEVERE, "Mod tried to register a texture to BushType '" + this.getClass().getName() + "' with a higher stage then the specified stage.");
            return;
        }
        textureMap[stage - 1] = textureLoc;
    }

    public int getMaxStage() {
        return maxStage;
    }

    public int getBerryStage() {
        return berryStage;
    }

    public void applyTexture(int stage) {
        if(!(stage > maxStage)) {
            FMLClientHandler.instance().getClient().renderEngine.bindTexture(new ResourceLocation(textureMap[stage - 1]));
        }
    }

    public void addFortuneDrop(ItemStack drop, float chance) {
        chanceDrops.put(drop, chance);
    }

    public HashMap<ItemStack, Float> getDrops() {
        return chanceDrops;
    }
}
