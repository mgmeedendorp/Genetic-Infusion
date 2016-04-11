package com.seremis.geninfusion.api.util

import java.io.File

import com.google.common.base.{Charsets, Splitter}
import com.google.common.io.{Files, LineProcessor}
import com.seremis.geninfusion.GeneticInfusion
import net.minecraft.launchwrapper.Launch

import scala.collection.mutable.HashMap

object MCPNames {

   lazy val fields: HashMap[String, String] = readMappings(new File(mappingsDir + "fields.csv"))
   lazy val methods: HashMap[String, String] = readMappings(new File(mappingsDir + "methods.csv"))

    val mappingsDir = System.getProperty("user.home").replace("\\", "/") + "/.gradle/caches/minecraft/de/oceanlabs/mcp/mcp_snapshot/20160408/"

    val DEV_ENV = Launch.blackboard.get("fml.deobfuscatedEnvironment").asInstanceOf[Boolean]

    def field(srg: String): String = if(DEV_ENV) fields.getOrElse(srg, srg) else srg

    def method(srg: String): String = if(DEV_ENV) methods.getOrElse(srg, srg) else srg

    private def readMappings(file: File): HashMap[String, String] = {
        if(DEV_ENV) {
            if(!file.isFile) {
                throw new RuntimeException("Couldn't find MCP mappings in location: '" + mappingsDir + "'")
            }
            GeneticInfusion.logger.info("Reading SRG->MCP mappings from " + file)
            return Files.readLines(file, Charsets.UTF_8, new MCPFileParser())
        }
        null
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
