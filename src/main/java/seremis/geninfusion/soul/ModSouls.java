package seremis.geninfusion.soul;

import net.minecraft.entity.monster.EntityZombie;
import seremis.geninfusion.api.soul.SoulHelper;
import seremis.geninfusion.api.soul.lib.Genes;
import seremis.geninfusion.api.soul.lib.Traits;
import seremis.geninfusion.soul.gene.*;
import seremis.geninfusion.soul.standardSoul.StandardSoulZombie;
import seremis.geninfusion.soul.traits.*;

public class ModSouls {

    public static void init() {
        SoulHelper.geneRegistry.registerGene(Genes.GENE_MAX_HEALTH, new GeneMaxHealth());
        SoulHelper.geneRegistry.registerGene(Genes.GENE_INVULNERABLE, new GeneInvulnerable());
        SoulHelper.geneRegistry.registerGene(Genes.GENE_ATTACK_DAMAGE, new GeneAttackDamage());
        SoulHelper.geneRegistry.registerGene(Genes.GENE_MOVEMENT_SPEED, new GeneMovementSpeed());
        SoulHelper.geneRegistry.registerGene(Genes.GENE_FOLLOW_RANGE, new GeneFollowRange());
        SoulHelper.geneRegistry.registerGene(Genes.GENE_BURNS_IN_DAYLIGHT, new GeneBurnsInDaylight());
        SoulHelper.geneRegistry.registerGene(Genes.GENE_DROWNS_IN_WATER, new GeneDrownsInWater());
        SoulHelper.geneRegistry.registerGene(Genes.GENE_DROWNS_IN_AIR, new GeneDrownsInAir());
        SoulHelper.geneRegistry.registerGene(Genes.GENE_IMMUNE_TO_FIRE, new GeneImmuneToFire());
        SoulHelper.geneRegistry.registerGene(Genes.GENE_MAX_HURT_RESISTANT_TIME, new GeneMaxHurtResistantTime());
        SoulHelper.geneRegistry.registerGene(Genes.GENE_PICKS_UP_ITEMS, new GenePicksUpItems());
        SoulHelper.geneRegistry.registerGene(Genes.GENE_ITEM_DROPS, new GeneItemDrops());
        SoulHelper.geneRegistry.registerGene(Genes.GENE_RARE_ITEM_DROPS, new GeneRareItemDrops());
        SoulHelper.geneRegistry.registerGene(Genes.GENE_RARE_ITEM_DROP_CHANCES, new GeneRareItemDropChances());
        SoulHelper.geneRegistry.registerGene(Genes.GENE_EQUIPMENT_DROP_CHANCES, new GeneEquipmentDropChances());
        SoulHelper.geneRegistry.registerGene(Genes.GENE_LIVING_SOUND, new GeneLivingSound());
        SoulHelper.geneRegistry.registerGene(Genes.GENE_HURT_SOUND, new GeneHurtSound());
        SoulHelper.geneRegistry.registerGene(Genes.GENE_DEATH_SOUND, new GeneDeathSound());
        SoulHelper.geneRegistry.registerGene(Genes.GENE_WALK_SOUND, new GeneWalkSound());
        SoulHelper.geneRegistry.registerGene(Genes.GENE_SPLASH_SOUND, new GeneSplashSound());
        SoulHelper.geneRegistry.registerGene(Genes.GENE_SWIM_SOUND, new GeneSwimSound());
        SoulHelper.geneRegistry.registerGene(Genes.GENE_SOUND_VOLUME, new GeneSoundVolume());
        SoulHelper.geneRegistry.registerGene(Genes.GENE_CREATURE_ATTRIBUTE, new GeneCreatureAttribute());
        SoulHelper.geneRegistry.registerGene(Genes.GENE_TELEPORT_TIME_IN_PORTAL, new GeneTeleportTimeInPortal());
        SoulHelper.geneRegistry.registerGene(Genes.GENE_PORTAL_COOLDOWN, new GenePortalCooldown());
        SoulHelper.geneRegistry.registerGene(Genes.GENE_KNOCKBACK_RESISTANCE, new GeneKnockbackResistance());
        SoulHelper.geneRegistry.registerGene(Genes.GENE_SHOULD_DESPAWN, new GeneShouldDespawn());
        SoulHelper.geneRegistry.registerGene(Genes.GENE_TALK_INTERVAL, new GeneTalkInterval());
        SoulHelper.geneRegistry.registerGene(Genes.GENE_SET_ON_FIRE_FROM_ATTACK, new GeneSetOnFireFromAttack());
        
        SoulHelper.traitRegistry.registerTrait(Traits.TRAIT_FIRE, new TraitFire());
        SoulHelper.traitRegistry.registerTrait(Traits.TRAIT_MOVEMENT, new TraitMovement());
        SoulHelper.traitRegistry.registerTrait(Traits.TRAIT_ATTACKED, new TraitAttacked());
        SoulHelper.traitRegistry.registerTrait(Traits.TRAIT_ITEM_PICKUP, new TraitItemPickup());
        SoulHelper.traitRegistry.registerTrait(Traits.TRAIT_ITEM_DROPS, new TraitItemDrops());
        SoulHelper.traitRegistry.registerTrait(Traits.TRAIT_FLUIDS, new TraitFluids());
        SoulHelper.traitRegistry.registerTrait(Traits.TRAIT_SOUNDS, new TraitSounds());
        SoulHelper.traitRegistry.registerTrait(Traits.TRAIT_ATTRIBUTES, new TraitAttributes());

        SoulHelper.standardSoulRegistry.register(new StandardSoulZombie(), EntityZombie.class);
    }
}
