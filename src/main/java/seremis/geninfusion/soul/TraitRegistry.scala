package seremis.geninfusion.soul

import seremis.geninfusion.api.soul.{ITrait, ITraitRegistry}

class TraitRegistry extends ITraitRegistry {

    var traits: Map[String, ITrait] = Map()

    var names: Map[ITrait, String] = Map()

    var ids: Map[ITrait, Integer] = Map()

    override def registerTrait(name: String, trt: ITrait) {
        if (!traits.contains(name)) {
            traits += (name -> trt)
            names += (trt -> name)
            ids += (trt -> ids.size)
        }
    }

    override def unregisterTrait(trt: ITrait) {
        traits -= getName(trt)
        names -= trt
        ids -= trt
    }

    override def unregisterTrait(name: String) {
        unregisterTrait(getTrait(name))
    }

    override def getTrait(name: String): ITrait = {
        if (traits.contains(name)) traits.get(name).get else null
    }

    override def getName(trt: ITrait): String = {
        if (names.contains(trt)) names.get(trt).get else null
    }

    override def getId(name: String): Int = ids.get(getTrait(name)).get

    override def getId(trt: ITrait): Int = getId(getName(trt))

    override def getTraits: Array[ITrait] = {
        traits.values.toArray
    }
}