package seremis.geninfusion.soul;

import net.minecraft.entity.monster.EntityZombie;
import seremis.geninfusion.api.soul.lib.Genes;
import static seremis.geninfusion.api.soul.lib.TraitMethods.*;
import seremis.geninfusion.api.soul.lib.Traits;
import seremis.geninfusion.soul.gene.*;
import seremis.geninfusion.soul.gene.newAI.GeneUseNewAI;
import seremis.geninfusion.soul.gene.newAI.canSwim.GeneAICanSwim;
import seremis.geninfusion.soul.gene.oldAI.GeneCeaseAIMovement;
import seremis.geninfusion.soul.gene.oldAI.GeneUseOldAI;
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
        geneRegistry.registerGene(Genes.GENE_VERTICAL_FACE_SPEED, new GeneVerticalFaceSpeed());
        geneRegistry.registerGene(Genes.GENE_IS_CREATURE, new GeneIsCreature());
        geneRegistry.registerGene(Genes.GENE_AI_CAN_SWIM, new GeneAICanSwim());
        geneRegistry.registerGene(Genes.GENE_CEASE_AI_MOVEMENT, new GeneCeaseAIMovement());

        geneRegistry.registerMasterGene(Genes.GENE_USE_NEW_AI, new GeneUseNewAI());
        geneRegistry.registerMasterGene(Genes.GENE_USE_OLD_AI, new GeneUseOldAI());

        traitRegistry.registerTrait(Traits.TRAIT_FIRE, new TraitFire(), METHOD_ON_UPDATE, METHOD_ATTACK_ENTITY_AS_MOB);
        traitRegistry.registerTrait(Traits.TRAIT_MOVEMENT, new TraitMovement(), METHOD_ON_UPDATE);
        traitRegistry.registerTrait(Traits.TRAIT_ATTACKED, new TraitAttacked(), METHOD_ON_UPDATE, METHOD_ATTACK_ENTITY_FROM, METHOD_DAMAGE_ENTITY, METHOD_ON_DEATH);
        traitRegistry.registerTrait(Traits.TRAIT_ITEM_PICKUP, new TraitItemPickup(), METHOD_ON_UPDATE);
        traitRegistry.registerTrait(Traits.TRAIT_ITEM_DROPS, new TraitItemDrops(), METHOD_ON_DEATH);
        traitRegistry.registerTrait(Traits.TRAIT_FLUIDS, new TraitFluids(), METHOD_ON_UPDATE);
        traitRegistry.registerTrait(Traits.TRAIT_SOUNDS, new TraitSounds(), METHOD_ON_UPDATE, METHOD_PLAY_SOUND_AT_ENTITY, METHOD_FIRST_TICK);
        traitRegistry.registerTrait(Traits.TRAIT_ATTRIBUTES, new TraitInitialValues(), METHOD_FIRST_TICK);
        traitRegistry.registerTrait(Traits.TRAIT_ATTACK, new TraitAttack(), METHOD_ATTACK_ENTITY_AS_MOB);
        traitRegistry.registerTrait(Traits.TRAIT_AI, new TraitAI(), METHOD_FIRST_TICK, METHOD_ON_UPDATE, METHOD_FIND_PLAYER_TO_ATTACK);

        standardSoulRegistry.register(new StandardSoulZombie(), EntityZombie.class);
    }
}
