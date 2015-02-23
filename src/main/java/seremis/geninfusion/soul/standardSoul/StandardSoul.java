package seremis.geninfusion.soul.standardSoul;

import net.minecraft.client.model.ModelZombie;
import net.minecraft.entity.EnumCreatureAttribute;
import net.minecraft.entity.monster.EntityCreeper;
import net.minecraft.entity.passive.EntityChicken;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import seremis.geninfusion.api.soul.IChromosome;
import seremis.geninfusion.api.soul.IStandardSoul;
import seremis.geninfusion.api.soul.lib.Genes;
import seremis.geninfusion.api.soul.util.ModelPart;
import seremis.geninfusion.api.soul.util.animation.AnimationPart;
import seremis.geninfusion.soul.Chromosome;
import seremis.geninfusion.soul.allele.*;

/**
 * @author Seremis
 */
public class StandardSoul implements IStandardSoul {

    @Override
    public IChromosome getChromosomeFromGene(String gene) {
        if(gene.equals(Genes.GENE_ATTACK_DAMAGE)) {
            return new Chromosome(new AlleleFloat(false, 0F));
        } else if(gene.equals(Genes.GENE_BURNS_IN_DAYLIGHT)) {
            return new Chromosome(new AlleleBoolean(true, false));
        } else if(gene.equals(Genes.GENE_CEASE_AI_MOVEMENT)) {
            return new Chromosome(new AlleleBoolean(true, false));
        } else if(gene.equals(Genes.GENE_CHILDREN_BURN_IN_DAYLIGHT)) {
            return new Chromosome(new AlleleBoolean(true, false));
        } else if(gene.equals(Genes.GENE_CREATURE_ATTRIBUTE)) {
            return new Chromosome(new AlleleInteger(true, EnumCreatureAttribute.UNDEFINED.ordinal()));
        } else if(gene.equals(Genes.GENE_DEATH_SOUND)) {
            return new Chromosome(new AlleleString(false, "game.hostile.die"));
        } else if(gene.equals(Genes.GENE_DROWNS_IN_AIR)) {
            return new Chromosome(new AlleleBoolean(true, false));
        } else if(gene.equals(Genes.GENE_DROWNS_IN_WATER)) {
            return new Chromosome(new AlleleBoolean(false, true));
        } else if(gene.equals(Genes.GENE_EQUIPMENT_DROP_CHANCES)) {
            return new Chromosome(new AlleleFloatArray(true, new float[]{0.085F, 0.085F, 0.085F, 0.085F, 0.085F}));
        } else if(gene.equals(Genes.GENE_EXPERIENCE_VALUE)) {
            return new Chromosome(new AlleleInteger(true, 5));
        } else if(gene.equals(Genes.GENE_FOLLOW_RANGE)) {
            return new Chromosome(new AlleleFloat(true, 16));
        } else if(gene.equals(Genes.GENE_HURT_SOUND)) {
            return new Chromosome(new AlleleString(true, "mob.hostile.hurt"));
        } else if(gene.equals(Genes.GENE_IMMUNE_TO_FIRE)) {
            return new Chromosome(new AlleleBoolean(true, false));
        } else if(gene.equals(Genes.GENE_INVULNERABLE)) {
            return new Chromosome(new AlleleBoolean(true, false));
        } else if(gene.equals(Genes.GENE_IS_CREATURE)) {
            return new Chromosome(new AlleleBoolean(false, false));
        } else if(gene.equals(Genes.GENE_ITEM_DROPS)) {
            return new Chromosome(new AlleleInventory(true, new ItemStack[]{}));
        } else if(gene.equals(Genes.GENE_KNOCKBACK_RESISTANCE)) {
            return new Chromosome(new AlleleFloat(true, 0));
        } else if(gene.equals(Genes.GENE_LIVING_SOUND)) {
            return new Chromosome(new AlleleString(false, null));
        } else if(gene.equals(Genes.GENE_MAX_HEALTH)) {
            return new Chromosome(new AlleleFloat(true, 20));
        } else if(gene.equals(Genes.GENE_MAX_HURT_RESISTANT_TIME)) {
            return new Chromosome(new AlleleInteger(true, 20));
        } else if(gene.equals(Genes.GENE_MOVEMENT_SPEED)) {
            return new Chromosome(new AlleleDouble(true, 0.10000000149011612D));
        } else if(gene.equals(Genes.GENE_PICKS_UP_ITEMS)) {
            return new Chromosome(new AlleleBoolean(true, false));
        } else if(gene.equals(Genes.GENE_PORTAL_COOLDOWN)) {
            return new Chromosome(new AlleleInteger(true, 900));
        } else if(gene.equals(Genes.GENE_RARE_ITEM_DROP_CHANCES)) {
            return new Chromosome(new AlleleFloatArray(true, new float[]{0.33F, 0.33F, 0.33F}));
        } else if(gene.equals(Genes.GENE_RARE_ITEM_DROPS)) {
            return new Chromosome(new AlleleInventory(true, new ItemStack[]{}));
        } else if(gene.equals(Genes.GENE_SET_ON_FIRE_FROM_ATTACK)) {
            return new Chromosome(new AlleleBoolean(true, false));
        } else if(gene.equals(Genes.GENE_SHOULD_DESPAWN)) {
            return new Chromosome(new AlleleBoolean(true, true));
        } else if(gene.equals(Genes.GENE_SOUND_VOLUME)) {
            return new Chromosome(new AlleleFloat(true, 1F));
        } else if(gene.equals(Genes.GENE_SPLASH_SOUND)) {
            return new Chromosome(new AlleleString(true, "game.neutral.swim.splash"));
        } else if(gene.equals(Genes.GENE_SWIM_SOUND)) {
            return new Chromosome(new AlleleString(true, "game.neutral.swim"));
        } else if(gene.equals(Genes.GENE_TALK_INTERVAL)) {
            return new Chromosome(new AlleleInteger(true, 80));
        } else if(gene.equals(Genes.GENE_TELEPORT_TIME_IN_PORTAL)) {
            return new Chromosome(new AlleleInteger(true, 0));
        } else if(gene.equals(Genes.GENE_WALK_SOUND)) {
            return new Chromosome(new AlleleString(true, "mob.neutral.step"));
        } else if(gene.equals(Genes.GENE_USE_NEW_AI)) {
            return new Chromosome(new AlleleBoolean(true, false));
        } else if(gene.equals(Genes.GENE_USE_OLD_AI)) {
            return new Chromosome(new AlleleBoolean(true, true));
        } else if(gene.equals(Genes.GENE_VERTICAL_FACE_SPEED)) {
            return new Chromosome(new AlleleInteger(true, 40));
        } else if(gene.equals(Genes.GENE_IS_CREATURE)) {
            return new Chromosome(new AlleleBoolean(true, true));
        } else if(gene.equals(Genes.GENE_IS_TAMEABLE)) {
            return new Chromosome(new AlleleBoolean(true, false));
        } else if(gene.equals(Genes.GENE_AI_ARROW_ATTACK)) {
            return new Chromosome(new AlleleBoolean(true, false));
        } else if(gene.equals(Genes.GENE_AI_ARROW_ATTACK_MAX_RANGED_ATTACK_TIME)) {
            return new Chromosome(new AlleleInteger(false, 0));
        } else if(gene.equals(Genes.GENE_AI_ARROW_ATTACK_MIN_RANGED_ATTACK_TIME)) {
            return new Chromosome(new AlleleInteger(false, 0));
        } else if(gene.equals(Genes.GENE_AI_ARROW_ATTACK_MOVE_SPEED)) {
            return new Chromosome(new AlleleDouble(false, 1.0D));
        } else if(gene.equals(Genes.GENE_AI_ARROW_ATTACK_RANGED_ATTACK_TIME_MODIFIER)) {
            return new Chromosome(new AlleleFloat(false, 1.0F));
        } else if(gene.equals(Genes.GENE_AI_ATTACK_ON_COLLIDE)) {
            return new Chromosome(new AlleleBoolean(true, true));
        } else if(gene.equals(Genes.GENE_AI_ATTACK_ON_COLLIDE_LONG_MEMORY)) {
            return new Chromosome(new AlleleBoolean(false, false));
        } else if(gene.equals(Genes.GENE_AI_ATTACK_ON_COLLIDE_MOVE_SPEED)) {
            return new Chromosome(new AlleleDouble(false, 1.0D));
        } else if(gene.equals(Genes.GENE_AI_ATTACK_ON_COLLIDE_TARGET)) {
            return new Chromosome(new AlleleClass(false, EntityPlayer.class));
        } else if(gene.equals(Genes.GENE_AI_AVOID_ENTITY)) {
            return new Chromosome(new AlleleBoolean(true, false));
        } else if(gene.equals(Genes.GENE_AI_AVOID_ENTITY_FAR_SPEED)) {
            return new Chromosome(new AlleleDouble(false, 1.0D));
        } else if(gene.equals(Genes.GENE_AI_AVOID_ENTITY_NEAR_SPEED)) {
            return new Chromosome(new AlleleDouble(false, 1.2D));
        } else if(gene.equals(Genes.GENE_AI_AVOID_ENTITY_RANGE)) {
            return new Chromosome(new AlleleFloat(false, 6.0F));
        } else if(gene.equals(Genes.GENE_AI_AVOID_ENTITY_TARGET)) {
            return new Chromosome(new AlleleClass(false, EntityCreeper.class));
        } else if(gene.equals(Genes.GENE_AI_BEG)) {
            return new Chromosome(new AlleleBoolean(true, false));
        } else if(gene.equals(Genes.GENE_AI_BEG_RANGE)) {
            return new Chromosome(new AlleleFloat(false, 0.0F));
        } else if(gene.equals(Genes.GENE_AI_BREAK_DOOR)) {
            return new Chromosome(new AlleleBoolean(true, false));
        } else if(gene.equals(Genes.GENE_AI_CONTROLLED_BY_PLAYER)) {
            return new Chromosome(new AlleleBoolean(true, false));
        } else if(gene.equals(Genes.GENE_AI_CONTROLLED_BY_PLAYER_MAX_SPEED)) {
            return new Chromosome(new AlleleDouble(false, 1.0D));
        } else if(gene.equals(Genes.GENE_AI_CREEPER_SWELL)) {
            return new Chromosome(new AlleleBoolean(true, false));
        } else if(gene.equals(Genes.GENE_AI_DEFEND_VILLAGE)) {
            return new Chromosome(new AlleleBoolean(true, false));
        } else if(gene.equals(Genes.GENE_AI_EAT_GRASS)) {
            return new Chromosome(new AlleleBoolean(true, false));
        } else if(gene.equals(Genes.GENE_AI_FLEE_SUN)) {
            return new Chromosome(new AlleleBoolean(true, false));
        } else if(gene.equals(Genes.GENE_AI_FLEE_SUN_MOVE_SPEED)) {
            return new Chromosome(new AlleleDouble(false, 1.0D));
        } else if(gene.equals(Genes.GENE_AI_FOLLOW_GOLEM)) {
            return new Chromosome(new AlleleBoolean(true, false));
        } else if(gene.equals(Genes.GENE_AI_FOLLOW_OWNER)) {
            return new Chromosome(new AlleleBoolean(true, false));
        } else if(gene.equals(Genes.GENE_AI_FOLLOW_OWNER_MOVE_SPEED)) {
            return new Chromosome(new AlleleDouble(false, 1.0D));
        } else if(gene.equals(Genes.GENE_AI_FOLLOW_OWNER_MIN_DISTANCE)) {
            return new Chromosome(new AlleleFloat(false, 5.0F));
        } else if(gene.equals(Genes.GENE_AI_FOLLOW_OWNER_MAX_DISTANCE)) {
            return new Chromosome(new AlleleFloat(true, 10.0F));
        } else if(gene.equals(Genes.GENE_AI_FOLLOW_PARENT)) {
            return new Chromosome(new AlleleBoolean(true, false));
        } else if(gene.equals(Genes.GENE_AI_FOLLOW_PARENT_MOVE_SPEED)) {
            return new Chromosome(new AlleleDouble(false, 1.0D));
        } else if(gene.equals(Genes.GENE_AI_HURT_BY_TARGET)) {
            return new Chromosome(new AlleleBoolean(true, true));
        } else if(gene.equals(Genes.GENE_AI_HURT_BY_TARGET_CALL_HELP)) {
            return new Chromosome(new AlleleBoolean(true, false));
        } else if(gene.equals(Genes.GENE_AI_LEAP_AT_TARGET)) {
            return new Chromosome(new AlleleBoolean(true, false));
        } else if(gene.equals(Genes.GENE_AI_LEAP_AT_TARGET_MOTION_Y)) {
            return new Chromosome(new AlleleFloat(false, 0.4F));
        } else if(gene.equals(Genes.GENE_AI_LOOK_AT_TRADE_PLAYER)) {
            return new Chromosome(new AlleleBoolean(true, false));
        } else if(gene.equals(Genes.GENE_AI_LOOK_AT_VILLAGER)) {
            return new Chromosome(new AlleleBoolean(true, false));
        } else if(gene.equals(Genes.GENE_AI_LOOK_IDLE)) {
            return new Chromosome(new AlleleBoolean(true, true));
        } else if(gene.equals(Genes.GENE_AI_MATE)) {
            return new Chromosome(new AlleleBoolean(true, false));
        } else if(gene.equals(Genes.GENE_AI_MATE_MOVE_SPEED)) {
            return new Chromosome(new AlleleDouble(false, 1.0D));
        } else if(gene.equals(Genes.GENE_AI_MOVE_INDOORS)) {
            return new Chromosome(new AlleleBoolean(true, false));
        } else if(gene.equals(Genes.GENE_AI_MOVE_THROUGH_VILLAGE)) {
            return new Chromosome(new AlleleBoolean(true, false));
        } else if(gene.equals(Genes.GENE_AI_MOVE_THROUGH_VILLAGE_IS_NOCTURNAL)) {
            return new Chromosome(new AlleleBoolean(false, true));
        } else if(gene.equals(Genes.GENE_AI_MOVE_THROUGH_VILLAGE_MOVE_SPEED)) {
            return new Chromosome(new AlleleDouble(false, 1.0D));
        } else if(gene.equals(Genes.GENE_AI_MOVE_TOWARDS_RESTRICTION)) {
            return new Chromosome(new AlleleBoolean(true, false));
        } else if(gene.equals(Genes.GENE_AI_MOVE_TOWARDS_RESTRICTION_MOVE_SPEED)) {
            return new Chromosome(new AlleleDouble(false, 1.0D));
        } else if(gene.equals(Genes.GENE_AI_MOVE_TOWARDS_TARGET)) {
            return new Chromosome(new AlleleBoolean(true, false));
        } else if(gene.equals(Genes.GENE_AI_MOVE_TOWARDS_TARGET_MAX_DISTANCE)) {
            return new Chromosome(new AlleleFloat(true, 32.0F));
        } else if(gene.equals(Genes.GENE_AI_MOVE_TOWARDS_TARGET_MOVE_SPEED)) {
            return new Chromosome(new AlleleDouble(true, 0.9D));
        } else if(gene.equals(Genes.GENE_AI_NEAREST_ATTACKABLE_TARGET)) {
            return new Chromosome(new AlleleBoolean(true, true));
        } else if(gene.equals(Genes.GENE_AI_NEAREST_ATTACKABLE_TARGET_ENTITY_SELECTOR)) {
            return new Chromosome(new AlleleString(true, ""));
        } else if(gene.equals(Genes.GENE_AI_NEAREST_ATTACKABLE_TARGET_NEARBY_ONLY)) {
            return new Chromosome(new AlleleBoolean(true, false));
        } else if(gene.equals(Genes.GENE_AI_NEAREST_ATTACKABLE_TARGET_TARGET)) {
            return new Chromosome(new AlleleClass(false, EntityPlayer.class));
        } else if(gene.equals(Genes.GENE_AI_NEAREST_ATTACKABLE_TARGET_TARGET_CHANCE)) {
            return new Chromosome(new AlleleInteger(true, 0));
        } else if(gene.equals(Genes.GENE_AI_NEAREST_ATTACKABLE_TARGET_VISIBLE)) {
            return new Chromosome(new AlleleBoolean(true, false));
        } else if(gene.equals(Genes.GENE_AI_OCELOT_ATTACK)) {
            return new Chromosome(new AlleleBoolean(true, false));
        } else if(gene.equals(Genes.GENE_AI_OCELOT_SIT)) {
            return new Chromosome(new AlleleBoolean(true, false));
        } else if(gene.equals(Genes.GENE_AI_OCELOT_SIT_MOVE_SPEED)) {
            return new Chromosome(new AlleleDouble(false, 1.0D));
        } else if(gene.equals(Genes.GENE_AI_OPEN_DOOR)) {
            return new Chromosome(new AlleleBoolean(true, false));
        } else if(gene.equals(Genes.GENE_AI_OPEN_DOOR_CLOSE_DOOR)) {
            return new Chromosome(new AlleleBoolean(false, true));
        } else if(gene.equals(Genes.GENE_AI_OWNER_HURT_BY_TARGET)) {
            return new Chromosome(new AlleleBoolean(true, false));
        } else if(gene.equals(Genes.GENE_AI_OWNER_HURT_TARGET)) {
            return new Chromosome(new AlleleBoolean(true, false));
        } else if(gene.equals(Genes.GENE_AI_PANIC)) {
            return new Chromosome(new AlleleBoolean(true, false));
        } else if(gene.equals(Genes.GENE_AI_PANIC_MOVE_SPEED)) {
            return new Chromosome(new AlleleDouble(true, 1.25D));
        } else if(gene.equals(Genes.GENE_AI_PLAY)) {
            return new Chromosome(new AlleleBoolean(true, false));
        } else if(gene.equals(Genes.GENE_AI_PLAY_MOVE_SPEED)) {
            return new Chromosome(new AlleleDouble(false, 1.0D));
        } else if(gene.equals(Genes.GENE_AI_RESTRICT_OPEN_DOOR)) {
            return new Chromosome(new AlleleBoolean(true, false));
        } else if(gene.equals(Genes.GENE_AI_RESTRICT_SUN)) {
            return new Chromosome(new AlleleBoolean(true, false));
        } else if(gene.equals(Genes.GENE_AI_RUN_AROUND_LIKE_CRAZY)) {
            return new Chromosome(new AlleleBoolean(true, false));
        } else if(gene.equals(Genes.GENE_AI_RUN_AROUND_LIKE_CRAZY_MOVE_SPEED)) {
            return new Chromosome(new AlleleDouble(false, 1.0D));
        } else if(gene.equals(Genes.GENE_AI_SIT)) {
            return new Chromosome(new AlleleBoolean(true, false));
        } else if(gene.equals(Genes.GENE_AI_SWIMMING)) {
            return new Chromosome(new AlleleBoolean(true, true));
        } else if(gene.equals(Genes.GENE_AI_TARGET_NON_TAMED)) {
            return new Chromosome(new AlleleBoolean(true, false));
        } else if(gene.equals(Genes.GENE_AI_TARGET_NON_TAMED_TARGET)) {
            return new Chromosome(new AlleleClass(true, EntityChicken.class));
        } else if(gene.equals(Genes.GENE_AI_TARGET_NON_TAMED_TARGET_CHANCE)) {
            return new Chromosome(new AlleleInteger(true, 750));
        } else if(gene.equals(Genes.GENE_AI_TARGET_NON_TAMED_VISIBLE)) {
            return new Chromosome(new AlleleBoolean(true, false));
        } else if(gene.equals(Genes.GENE_AI_TEMPT)) {
            return new Chromosome(new AlleleBoolean(true, false));
        } else if(gene.equals(Genes.GENE_AI_TEMPT_ITEM)) {
            return new Chromosome(new AlleleItemStack(true, new ItemStack(Items.wheat)));
        } else if(gene.equals(Genes.GENE_AI_TEMPT_MOVE_SPEED)) {
            return new Chromosome(new AlleleDouble(true, 1.2D));
        } else if(gene.equals(Genes.GENE_AI_TEMPT_SCARED_BY_PLAYER)) {
            return new Chromosome(new AlleleBoolean(true, false));
        } else if(gene.equals(Genes.GENE_AI_TRADE_PLAYER)) {
            return new Chromosome(new AlleleBoolean(true, false));
        } else if(gene.equals(Genes.GENE_AI_VILLAGER_MATE)) {
            return new Chromosome(new AlleleBoolean(true, false));
        } else if(gene.equals(Genes.GENE_AI_WANDER)) {
            return new Chromosome(new AlleleBoolean(true, true));
        } else if(gene.equals(Genes.GENE_AI_WANDER_MOVE_SPEED)) {
            return new Chromosome(new AlleleDouble(true, 1.0D));
        } else if(gene.equals(Genes.GENE_AI_WATCH_CLOSEST)) {
            return new Chromosome(new AlleleBoolean(true, true));
        } else if(gene.equals(Genes.GENE_AI_WATCH_CLOSEST_CHANCE)) {
            return new Chromosome(new AlleleFloat(true, 0.02F));
        } else if(gene.equals(Genes.GENE_AI_WATCH_CLOSEST_RANGE)) {
            return new Chromosome(new AlleleFloat(true, 8.0F));
        } else if(gene.equals(Genes.GENE_AI_WATCH_CLOSEST_TARGET)) {
            return new Chromosome(new AlleleClass(true, EntityPlayer.class));
        } else if(gene.equals(Genes.GENE_AI_WATCH_CLOSEST_2)) {
            return new Chromosome(new AlleleBoolean(true, false));
        } else if(gene.equals(Genes.GENE_AI_WATCH_CLOSEST_2_CHANCE)) {
            return new Chromosome(new AlleleFloat(true, 1.0F));
        } else if(gene.equals(Genes.GENE_AI_WATCH_CLOSEST_2_RANGE)) {
            return new Chromosome(new AlleleFloat(true, 3.0F));
        } else if(gene.equals(Genes.GENE_AI_WATCH_CLOSEST_2_TARGET)) {
            return new Chromosome(new AlleleClass(true, EntityPlayer.class));
        } else if(gene.equals(Genes.GENE_AI_MODIFIER_ATTACK)) {
            return new Chromosome(new AlleleFloat(true, 7.0F));
        } else if(gene.equals(Genes.GENE_AI_MODIFIER_DO_USELESS_THINGS)) {
            return new Chromosome(new AlleleFloat(true, 1.0F));
        } else if(gene.equals(Genes.GENE_AI_MODIFIER_HELP_OWNER)) {
            return new Chromosome(new AlleleFloat(true, 7.0F));
        } else if(gene.equals(Genes.GENE_AI_MODIFIER_MATE)) {
            return new Chromosome(new AlleleFloat(true, 6.0F));
        } else if(gene.equals(Genes.GENE_AI_MODIFIER_RUN)) {
            return new Chromosome(new AlleleFloat(true, 7.0F));
        } else if(gene.equals(Genes.GENE_AI_MODIFIER_SURVIVE)) {
            return new Chromosome(new AlleleFloat(true, 8.0F));
        } else if(gene.equals(Genes.GENE_AI_MODIFIER_TRADE)) {
            return new Chromosome(new AlleleFloat(true, 5.0F));
        } else if(gene.equals(Genes.GENE_AI_MODIFIER_WANDER)) {
            return new Chromosome(new AlleleFloat(true, 2.0F));
        } else if(gene.equals(Genes.GENE_MODEL_HEAD)) {
            return new Chromosome(new AlleleModelPartArray(true, ModelPart.getHeadFromModel(new ModelZombie())));
        } else if(gene.equals(Genes.GENE_MODEL_BODY)) {
            return new Chromosome(new AlleleModelPartArray(true, ModelPart.getBodyFromModel(new ModelZombie())));
        } else if(gene.equals(Genes.GENE_MODEL_ARM_LEFT)) {
            return new Chromosome(new AlleleModelPartArray(true, new ModelPart[] {ModelPart.getArmsFromModel(new ModelZombie())[0]}));
        } else if(gene.equals(Genes.GENE_MODEL_ARM_RIGHT)) {
            return new Chromosome(new AlleleModelPartArray(true, new ModelPart[] {ModelPart.getArmsFromModel(new ModelZombie())[1]}));
        } else if(gene.equals(Genes.GENE_MODEL_LEG_LEFT)) {
            return new Chromosome(new AlleleModelPartArray(true, new ModelPart[] {ModelPart.getLegsFromModel(new ModelZombie())[0]}));
        } else if(gene.equals(Genes.GENE_MODEL_LEG_RIGHT)) {
            return new Chromosome(new AlleleModelPartArray(true, new ModelPart[] {ModelPart.getLegsFromModel(new ModelZombie())[1]}));
        } else if(gene.equals(Genes.GENE_MODEL_EARS)) {
            return new Chromosome(new AlleleModelPartArray(true, ModelPart.getEarsFromModel(new ModelZombie())));
        } else if(gene.equals(Genes.GENE_MODEL_CLOAK)) {
            return new Chromosome(new AlleleModelPartArray(true, ModelPart.getCloakFromModel(new ModelZombie())));
        } else if(gene.equals(Genes.GENE_ANIM_LINEAR_HEAD)) {
            return new Chromosome(new AlleleAnimationPartArray(true, AnimationPart.bipedHeadLinearAnimationParts()));
        } else if(gene.equals(Genes.GENE_ANIM_LINEAR_BODY)) {
            return new Chromosome(new AlleleAnimationPartArray(true, AnimationPart.bipedBodyLinearAnimationParts()));
        } else if(gene.equals(Genes.GENE_ANIM_WAVE_ARM_LEFT)) {
            return new Chromosome(new AlleleAnimationPartArray(true, AnimationPart.bipedArmLeftWaveAnimationParts()));
        } else if(gene.equals(Genes.GENE_ANIM_WAVE_ARM_RIGHT)) {
            return new Chromosome(new AlleleAnimationPartArray(true, AnimationPart.bipedArmRightWaveAnimationParts()));
        } else if(gene.equals(Genes.GENE_ANIM_WAVE_LEG_LEFT)) {
            return new Chromosome(new AlleleAnimationPartArray(true, AnimationPart.bipedLegLeftWaveAnimationParts()));
        } else if(gene.equals(Genes.GENE_ANIM_WAVE_LEG_RIGHT)) {
            return new Chromosome(new AlleleAnimationPartArray(true, AnimationPart.bipedLegRightWaveAnimationParts()));
        } else if(gene.equals(Genes.GENE_ANIM_LINEAR_CLOAK)) {
            return new Chromosome(new AlleleAnimationPartArray(true, new AnimationPart[] {}));
        } else if(gene.equals(Genes.GENE_ANIM_LINEAR_EARS)) {
            return new Chromosome(new AlleleAnimationPartArray(true, new AnimationPart[] {}));
        } else if(gene.equals(Genes.GENE_ANIM_LINEAR_ARM_LEFT)) {
            return new Chromosome(new AlleleAnimationPartArray(true, AnimationPart.bipedArmLeftLinearAnimationParts()));
        } else if(gene.equals(Genes.GENE_ANIM_LINEAR_ARM_RIGHT)) {
            return new Chromosome(new AlleleAnimationPartArray(true, AnimationPart.bipedArmRightLinearAnimationParts()));
        } else if(gene.equals(Genes.GENE_ANIM_LINEAR_LEG_LEFT)) {
            return new Chromosome(new AlleleAnimationPartArray(true, AnimationPart.bipedLegLeftLinearAnimationParts()));
        } else if(gene.equals(Genes.GENE_ANIM_LINEAR_LEG_RIGHT)) {
            return new Chromosome(new AlleleAnimationPartArray(true, AnimationPart.bipedLegRightLinearAnimationParts()));
        } else if(gene.equals(Genes.GENE_TEXTURE)) {
            return new Chromosome(new AlleleString(false, "textures/entity/zombie/zombie.png"));
        }
        return null;
    }
}
