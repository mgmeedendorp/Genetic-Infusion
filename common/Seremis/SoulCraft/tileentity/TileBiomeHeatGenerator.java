package Seremis.SoulCraft.tileentity;

import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.chunk.Chunk;

public class TileBiomeHeatGenerator extends SCTileMagnetConnector {
    
    public int biomeTicks = 0;
    public byte[] currentChunkBiomes = new byte[256];    
    private BiomeGenBase currentBiome;
    
    @Override
    public void updateEntity() {
        if(biomeTicks >= 1) {
            biomeTicks = 0;
            
        }
        warm((int) (getCurrentBiome().temperature*2));
    }
    
    public BiomeGenBase getCurrentBiome() {
        if(currentBiome == null) {
            Chunk currentChunk = worldObj.getChunkFromBlockCoords(xCoord, zCoord);
            currentChunkBiomes = currentChunk.getBiomeArray();
            
            int modX = xCoord % 16;
            int modZ = zCoord % 16;
            int arrayPosition = modX + (modZ *16);
            int currentBiomeID = currentChunkBiomes[arrayPosition];
            
            currentBiome = BiomeGenBase.biomeList[currentBiomeID];
        }
        return currentBiome;
    }
    
    public void changeBiome() {
        
    }
    
    //IMagnetConnector//
    
    @Override
    public double getRange() {
        return 5;
    }

    @Override
    public int getHeatTransmissionSpeed() {
        return 5;
    }

    @Override
    public int getHeatLossPerTick() {
        return 0;
    }

    @Override
    public int getMaxHeat() {
        return 1000;
    }
}
