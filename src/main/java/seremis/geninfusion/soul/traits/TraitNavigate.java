package seremis.geninfusion.soul.traits;

import net.minecraft.entity.Entity;
import net.minecraft.init.Blocks;
import net.minecraft.util.MathHelper;
import seremis.geninfusion.api.soul.IEntitySoulCustom;
import seremis.geninfusion.api.soul.SoulHelper;
import seremis.geninfusion.api.soul.lib.Genes;

public class TraitNavigate extends Trait {

    @Override
    public void updateWanderPath(IEntitySoulCustom entity) {
        double posX = entity.getDouble("posX");
        double posY = entity.getDouble("posY");
        double posZ = entity.getDouble("posZ");

        entity.getWorld().theProfiler.startSection("stroll");
        boolean flag = false;
        int i = -1;
        int j = -1;
        int k = -1;
        float f = -99999.0F;

        for(int l = 0; l < 10; ++l) {
            int i1 = MathHelper.floor_double(posX + (double) entity.getRandom().nextInt(13) - 6.0D);
            int j1 = MathHelper.floor_double(posY + (double) entity.getRandom().nextInt(7) - 3.0D);
            int k1 = MathHelper.floor_double(posZ + (double) entity.getRandom().nextInt(13) - 6.0D);
            float f1 = entity.getBlockPathWeight(i1, j1, k1);

            if(f1 > f) {
                f = f1;
                i = i1;
                j = j1;
                k = k1;
                flag = true;
            }
        }

        if(flag) {
            entity.setObject("pathToEntity", entity.getWorld().getEntityPathToXYZ((Entity) entity, i, j, k, 10.0F, true, false, false, true));
        }

        entity.getWorld().theProfiler.endSection();
    }

    @Override
    public float getBlockPathWeight(IEntitySoulCustom entity, int x, int y, int z) {
        boolean burnsInDaylight = SoulHelper.geneRegistry().getValueFromAllele(entity, Genes.GENE_BURNS_IN_DAYLIGHT);
        boolean eatsGrass = SoulHelper.geneRegistry().getValueFromAllele(entity, Genes.GENE_AI_EAT_GRASS);

        if(burnsInDaylight) {
            return 0.5F - entity.getWorld().getLightBrightness(x, y, z);
        }
        if(eatsGrass) {
            return entity.getWorld().getBlock(x, y - 1, z) == Blocks.grass ? 10.0F : entity.getWorld().getLightBrightness(x, y, z) - 0.5F;
        }
        //TODO silverfish in stone block
        return 0.0F;
    }

    @Override
    public Entity findPlayerToAttack(IEntitySoulCustom entity) {
        return entity.getWorld().getClosestPlayerToEntity((Entity) entity, 50);
    }
}
