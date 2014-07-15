package seremis.soulcraft.soul;

import seremis.soulcraft.api.soul.GeneRegistry;
import seremis.soulcraft.api.soul.TraitRegistry;
import seremis.soulcraft.api.soul.lib.Genes;
import seremis.soulcraft.api.soul.lib.Traits;
import seremis.soulcraft.soul.gene.*;
import seremis.soulcraft.soul.traits.*;

public class ModSouls {

    public static void init() {                
        GeneRegistry.registerGene(Genes.GENE_MAX_HEALTH, new GeneMaxHealth());
        GeneRegistry.registerGene(Genes.GENE_INVULNERABLE, new GeneInvulnerable());
        GeneRegistry.registerGene(Genes.GENE_ATTACK_DAMAGE, new GeneAttackDamage());
        GeneRegistry.registerGene(Genes.GENE_MOVEMENT_SPEED, new GeneMovementSpeed());
        GeneRegistry.registerGene(Genes.GENE_FOLLOW_RANGE, new GeneFollowRange());
        GeneRegistry.registerGene(Genes.GENE_BURNS_IN_DAYLIGHT, new GeneBurnsInDaylight());
        GeneRegistry.registerGene(Genes.GENE_DROWNS_IN_WATER, new GeneDrownsInWater());
        GeneRegistry.registerGene(Genes.GENE_DROWNS_IN_AIR, new GeneDrownsInAir());
        GeneRegistry.registerGene(Genes.GENE_IMMUNE_TO_FIRE, new GeneImmuneToFire());
        GeneRegistry.registerGene(Genes.GENE_MAX_HURT_RESISTANT_TIME, new GeneMaxHurtResistantTime());
        GeneRegistry.registerGene(Genes.GENE_PICKS_UP_ITEMS, new GenePicksUpItems());
        GeneRegistry.registerGene(Genes.GENE_ITEM_DROPS, new GeneItemDrops());
        GeneRegistry.registerGene(Genes.GENE_RARE_ITEM_DROPS, new GeneRareItemDrops());
        GeneRegistry.registerGene(Genes.GENE_RARE_ITEM_DROP_CHANCES, new GeneRareItemDropChances());
        GeneRegistry.registerGene(Genes.GENE_EQUIPMENT_DROP_CHANCES, new GeneEquipmentDropChances());
        GeneRegistry.registerGene(Genes.GENE_LIVING_SOUND, new GeneLivingSound());
        GeneRegistry.registerGene(Genes.GENE_HURT_SOUND, new GeneHurtSound());
        GeneRegistry.registerGene(Genes.GENE_DEATH_SOUND, new GeneDeathSound());
        GeneRegistry.registerGene(Genes.GENE_WALK_SOUND, new GeneWalkSound());
        GeneRegistry.registerGene(Genes.GENE_SPLASH_SOUND, new GeneSplashSound());
        GeneRegistry.registerGene(Genes.GENE_SWIM_SOUND, new GeneSwimSound());
        GeneRegistry.registerGene(Genes.GENE_SOUND_VOLUME, new GeneSoundVolume());
        GeneRegistry.registerGene(Genes.GENE_CREATURE_ATTRIBUTE, new GeneCreatureAttribute());
        GeneRegistry.registerGene(Genes.GENE_TELEPORT_TIME_IN_PORTAL, new GeneTeleportTimeInPortal());
        GeneRegistry.registerGene(Genes.GENE_PORTAL_COOLDOWN, new GenePortalCooldown());
        GeneRegistry.registerGene(Genes.GENE_KNOCKBACK_RESISTANCE, new GeneKnockbackResistance());
        GeneRegistry.registerGene(Genes.GENE_SHOULD_DESPAWN, new GeneShouldDespawn());
        
        TraitRegistry.registerTrait(Traits.TRAIT_FIRE, new TraitFire());
        TraitRegistry.registerTrait(Traits.TRAIT_MOVEMENT, new TraitMovement());
        TraitRegistry.registerTrait(Traits.TRAIT_ATTACKED, new TraitAttacked());
        TraitRegistry.registerTrait(Traits.TRAIT_ITEM_PICKUP, new TraitItemPickup());
        TraitRegistry.registerTrait(Traits.TRAIT_ITEM_DROPS, new TraitItemDrops());
        TraitRegistry.registerTrait(Traits.TRAIT_FLUIDS, new TraitFluids());
        TraitRegistry.registerTrait(Traits.TRAIT_SOUNDS, new TraitSounds());
        TraitRegistry.registerTrait(Traits.TRAIT_ATTRIBUTES, new TraitAttributes());
    }
}
