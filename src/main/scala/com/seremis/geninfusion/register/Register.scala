package com.seremis.geninfusion.register

trait Register {
    var initPhase: RegisterPhase = _

    def register()
    def registerServer()
    def registerClient()
}