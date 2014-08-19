package seremis.geninfusion.soul;

import net.minecraft.entity.monster.EntityZombie;
import seremis.geninfusion.api.soul.lib.GeneGroups;
import seremis.geninfusion.api.soul.lib.Genes;
import seremis.geninfusion.api.soul.lib.Traits;
import seremis.geninfusion.soul.gene.*;
import seremis.geninfusion.soul.gene.newAI.canSwim.GeneAICanSwim;
import seremis.geninfusion.soul.gene.newAI.GeneUseNewAI;
import seremis.geninfusion.soul.geneGroup.ai.GeneGroupNewAI;
import seremis.geninfusion.soul.geneGroup.ai.GeneGroupNewAISwim;
import seremis.geninfusion.soul.standardSoul.StandardSoulZombie;
import seremis.geninfusion.soul.traits.*;

import static seremis.geninfusion.api.soul.SoulHelper.*;

public class ModSouls {

    public static void init() {
        geneRegistry.registerGene(Genes.GENE_MAX_HEALTH, new GeneMaxHealth());
        geneRegistry.registerGene(Genes.GENE_INVULNERABLE, new GeneInvulnerable());
        geneRegistry.registerGene(Genes.GENE_ATTACK_DAMAGE, new GeneAttackDamage());
        geneRegistry.registerGene(Genes.GENE_MOVEMENT_SPEED, new GeneMovementSpeed());
        geneRegistry.registerGene(Genes.GENE_FOLLOW_RANGE, new GeneFollowRange());
        geneRegistry.registerGene(Genes.GENE_BURNS_IN_DAYLIGHT, new GeneBurnsInDaylight());
        geneRegistry.registerGene(Genes.GENE_DROWNS_IN_WATER, new GeneDrownsInWater());
        geneRegistry.registerGene(Genes.GENE_DROWNS_IN_AIR, new GeneDrownsInAir());
        geneRegistry.registerGene(Genes.GENE_IMMUNE_TO_FIRE, new GeneImmuneToFire());
        geneRegistry.registerGene(Genes.GENE_MAX_HURT_RESISTANT_TIME, new GeneMaxHurtResistantTime());
        geneRegistry.registerGene(Genes.GENE_PICKS_UP_ITEMS, new GenePicksUpItems());
        geneRegistry.registerGene(Genes.GENE_ITEM_DROPS, new GeneItemDrops());
        geneRegistry.registerGene(Genes.GENE_RARE_ITEM_DROPS, new GeneRareItemDrops());
        geneRegistry.registerGene(Genes.GENE_RARE_ITEM_DROP_CHANCES, new GeneRareItemDropChances());
        geneRegistry.registerGene(Genes.GENE_EQUIPMENT_DROP_CHANCES, new GeneEquipmentDropChances());
        geneRegistry.registerGene(Genes.GENE_LIVING_SOUND, new GeneLivingSound());
        geneRegistry.registerGene(Genes.GENE_HURT_SOUND, new GeneHurtSound());
        geneRegistry.registerGene(Genes.GENE_DEATH_SOUND, new GeneDeathSound());
        geneRegistry.registerGene(Genes.GENE_WALK_SOUND, new GeneWalkSound());
        geneRegistry.registerGene(Genes.GENE_SPLASH_SOUND, new GeneSplashSound());
        geneRegistry.registerGene(Genes.GENE_SWIM_SOUND, new GeneSwimSound());
        geneRegistry.registerGene(Genes.GENE_SOUND_VOLUME, new GeneSoundVolume());
        geneRegistry.registerGene(Genes.GENE_CREATURE_ATTRIBUTE, new GeneCreatureAttribute());
        geneRegistry.registerGene(Genes.GENE_TELEPORT_TIME_IN_PORTAL, new GeneTeleportTimeInPortal());
        geneRegistry.registerGene(Genes.GENE_PORTAL_COOLDOWN, new GenePortalCooldown());
        geneRegistry.registerGene(Genes.GENE_KNOCKBACK_RESISTANCE, new GeneKnockbackResistance());
        geneRegistry.registerGene(Genes.GENE_SHOULD_DESPAWN, new GeneShouldDespawn());
        geneRegistry.registerGene(Genes.GENE_TALK_INTERVAL, new GeneTalkInterval());
        geneRegistry.registerGene(Genes.GENE_SET_ON_FIRE_FROM_ATTACK, new GeneSetOnFireFromAttack());
        geneRegistry.registerGene(Genes.GENE_EXPERIENCE_VALUE, new GeneExperienceValue());
        geneRegistry.registerGene(Genes.GENE_USE_NEW_AI, new GeneUseNewAI());
        geneRegistry.registerGene(Genes.GENE_USE_OLD_AI, new GeneUseOldAI());
        geneRegistry.registerGene(Genes.GENE_VERTICAL_FACE_SPEED, new GeneVerticalFaceSpeed());
        geneRegistry.registerGene(Genes.GENE_IS_CREATURE, new GeneIsCreature());
        geneRegistry.registerGene(Genes.GENE_AI_CAN_SWIM, new GeneAICanSwim());

        geneGroupRegistry.registerGeneGroup(GeneGroups.GENE_GROUP_NEW_AI, new GeneGroupNewAI());
        geneGroupRegistry.registerGeneGroup(GeneGroups.GENE_GROUP_NEW_AI_CAN_SWIM, new GeneGroupNewAISwim());
        
        traitRegistry.registerTrait(Traits.TRAIT_FIRE, new TraitFire());
        traitRegistry.registerTrait(Traits.TRAIT_MOVEMENT, new TraitMovement());
        traitRegistry.registerTrait(Traits.TRAIT_ATTACKED, new TraitAttacked());
        traitRegistry.registerTrait(Traits.TRAIT_ITEM_PICKUP, new TraitItemPickup());
        traitRegistry.registerTrait(Traits.TRAIT_ITEM_DROPS, new TraitItemDrops());
        traitRegistry.registerTrait(Traits.TRAIT_FLUIDS, new TraitFluids());
        traitRegistry.registerTrait(Traits.TRAIT_SOUNDS, new TraitSounds());
        traitRegistry.registerTrait(Traits.TRAIT_ATTRIBUTES, new TraitInitialValues());
        traitRegistry.registerTrait(Traits.TRAIT_ATTACK, new TraitAttack());
        traitRegistry.registerTrait(Traits.TRAIT_AI, new TraitAI());

        standardSoulRegistry.register(new StandardSoulZombie(), EntityZombie.class);
    }
}
