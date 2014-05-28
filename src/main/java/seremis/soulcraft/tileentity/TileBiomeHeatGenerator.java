package seremis.soulcraft.tileentity;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import seremis.soulcraft.core.proxy.CommonProxy;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.chunk.Chunk;

public class TileBiomeHeatGenerator extends SCTileMagnetConnector {
    
    public int biomeTicks = 0;
    public byte[] currentChunkBiomes = new byte[256];    
    private BiomeGenBase currentBiome;
    private boolean biomeChanged = false;
    
    private int[][] biomeChanger;
    
    @Override
    public void updateEntity() {
        if(CommonProxy.proxy.isRenderWorld(worldObj)) {
            return;
        }
        getCurrentBiome();
        fillBiomeChanger();
        changeBiome();
        if(biomeTicks >= 1) {
            fillBiomeChanger();
            biomeTicks = 0;
            System.out.println("Biometick");
            changeBiome();
        }
        warm((int) (getCurrentBiome().temperature*2));
    }
    
    public BiomeGenBase getCurrentBiome() {
        if(currentBiome == null || biomeChanged) {
            Chunk currentChunk = worldObj.getChunkFromBlockCoords(xCoord, zCoord);
            currentChunkBiomes = currentChunk.getBiomeArray();
            
            int modX = (int) (Math.sqrt(xCoord*xCoord) % 16);
            int modZ = (int) (Math.sqrt(zCoord*zCoord) % 16);
            int arrayPosition = modX + (modZ *16);
            int currentBiomeID = currentChunkBiomes[arrayPosition];
            
            currentBiome = BiomeGenBase.getBiome(currentBiomeID);
        }
        return currentBiome;
    }
    
    public void changeBiome() {
        Random rand = new Random();
        Chunk currentChunk = worldObj.getChunkFromBlockCoords(xCoord, zCoord);
        
        int toChange = rand.nextInt(256);
        
        int[] currentBiomeInChunk = getCurrentBiomeInChunk();
        
        while(currentBiomeInChunk[toChange] == 0) {
            toChange = rand.nextInt(256);
        }
        
        if(biomeChanger[currentChunkBiomes[toChange]] != null && biomeChanger[currentChunkBiomes[toChange]].length != 0)
            currentChunkBiomes[toChange] = (byte) biomeChanger[currentChunkBiomes[toChange]][0];
        
        currentChunk.setBiomeArray(currentChunkBiomes);
        biomeChanged = true;
    }
    
    public int[] getCurrentBiomeInChunk() {
        int[] numbers = new int[256];
        
        for(int i = 0; i < currentChunkBiomes.length; i++) {
            if(BiomeGenBase.getBiome(currentChunkBiomes[i]).biomeID == currentBiome.biomeID) {
                numbers[i] = 1;
            } else {
                numbers[i] = 0;
            }
        }
        return numbers;
    }
    
    public void fillBiomeChanger() {
        if(biomeChanger == null || biomeChanger.length == 0) {
            biomeChanger = new int[256][];
            for(BiomeGenBase biome : BiomeGenBase.getBiomeGenArray()) {
                List<Integer> temp = new ArrayList<Integer>();
                if(biome != null && biome.biomeID != BiomeGenBase.hell.biomeID) {
                    for(BiomeGenBase biome1 : BiomeGenBase.getBiomeGenArray()) {
                        if(biome1 != null) {
                            if(biome1.temperature < biome.temperature) {
                                temp.add(biome1.biomeID);
                            }
                        }
                    }
                    int[] finalArray = new int[temp.size()];
                    int index = 0;
                    for(Integer nr : temp) {
                        finalArray[index] = nr;
                        index++;
                    }
                    biomeChanger[biome.biomeID] = finalArray;
                }
            }
        }
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
