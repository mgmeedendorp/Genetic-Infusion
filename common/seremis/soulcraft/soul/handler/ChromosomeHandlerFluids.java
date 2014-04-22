package seremis.soulcraft.soul.handler;

import seremis.soulcraft.soul.EnumChromosome;
import seremis.soulcraft.soul.SoulHandler;
import seremis.soulcraft.soul.allele.AlleleBoolean;
import seremis.soulcraft.soul.entity.IEntitySoulCustom;
import seremis.soulcraft.soul.event.EntityEventHandler;
import net.minecraft.entity.EntityLiving;
import net.minecraft.util.DamageSource;

public class ChromosomeHandlerFluids extends EntityEventHandler {

    @Override
    public void onInit(IEntitySoulCustom entity) {}

    @Override
    public void onUpdate(IEntitySoulCustom entity) {
        boolean drownsInWater = ((AlleleBoolean)SoulHandler.getSoulFrom((EntityLiving) entity).getChromosomes()[EnumChromosome.DROWNS_IN_WATER.ordinal()].getActive()).value;
        
        if(drownsInWater) {
            int air = entity.getAir();
            if (entity.isEntityAlive() && entity.isInWater()) {
                --air;
                
                if(air == -20) {
                    entity.setAir(0);
                    entity.attackEntityFrom(DamageSource.drown, 2.0F);
                }
            }
        }
        
        boolean drownsInAir = ((AlleleBoolean)SoulHandler.getSoulFrom((EntityLiving) entity).getChromosomes()[EnumChromosome.DROWNS_IN_AIR.ordinal()].getActive()).value;
        
        if(drownsInAir) {
            int air = entity.getAir();
            if (entity.isEntityAlive() && !entity.isInWater() && !entity.handleLavaMovement()) {
                --air;
                
                if(air == -20) {
                    entity.setAir(0);
                    entity.attackEntityFrom(DamageSource.drown, 2.0F);
                }
            }
        }
    }
}
