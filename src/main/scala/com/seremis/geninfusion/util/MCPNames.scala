package com.seremis.geninfusion.util

import java.io.File

import com.google.common.base.{Charsets, Splitter}
import com.google.common.io.{Files, LineProcessor}
import com.seremis.geninfusion.GeneticInfusion
import net.minecraft.launchwrapper.Launch
import net.minecraftforge.common.ForgeVersion

import scala.collection.mutable.HashMap

object MCPNames {

    var fields: HashMap[String, String] = _
    var methods: HashMap[String, String] = _

    val mappingsDir = System.getProperty("user.home").replace("\\", "/") + "/.gradle/caches/minecraft/net/minecraftforge/forge/1.7.10-" + ForgeVersion.getVersion + "-1.7.10/unpacked/conf/"

    val DEV_ENV = Launch.blackboard.get("fml.deobfuscatedEnvironment").asInstanceOf[Boolean]

    def init() {
        fields = readMappings(new File(mappingsDir + "fields.csv"))
        methods = readMappings(new File(mappingsDir + "methods.csv"))
    }

    def field(srg: String): String = if(DEV_ENV) fields.getOrElse(srg, srg) else srg

    def method(srg: String): String = if(DEV_ENV) methods.getOrElse(srg, srg) else srg

    private def readMappings(file: File): HashMap[String, String] = {
        if(!DEV_ENV) return null
        if (!file.isFile) {
            throw new RuntimeException("Couldn't find MCP mappings")
        }
        GeneticInfusion.logger.info("Reading SRG->MCP mappings from " + file)
        Files.readLines(file, Charsets.UTF_8, new MCPFileParser())
    }

    private class MCPFileParser extends LineProcessor[HashMap[String, String]] {

        val map: HashMap[String, String] = HashMap()

        var foundFirst: Boolean = false

        val splitter = Splitter.on(',').trimResults()

        override def processLine(line: String): Boolean = {
            if (!foundFirst) {
                foundFirst = true
                return true
            }
            val split = splitter.split(line).iterator()
            val srg = split.next()
            val mcp = split.next()
            if (!map.contains(srg)) {
                map.put(srg, mcp)
            }
            true
        }

        override def getResult = map.clone()
    }
}
