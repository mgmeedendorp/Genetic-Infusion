package com.seremis.geninfusion.register

sealed trait RegisterPhase

object RegisterPhase {

    case object PreInit extends RegisterPhase

    case object Init extends RegisterPhase

    case object PostInit extends RegisterPhase

}