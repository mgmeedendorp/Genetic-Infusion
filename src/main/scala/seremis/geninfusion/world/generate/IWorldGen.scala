package seremis.geninfusion.world.generate

import net.minecraft.world.World

import scala.util.Random

trait IWorldGen {
    def generate(world: World, random: Random, x: Int, y: Int, z: Int): Boolean
}
