package com.seremis.geninfusion.util

import java.awt.image.BufferedImage
import javax.imageio.ImageIO

import net.minecraft.util.ResourceLocation

object TextureHelper {

    /**
      * Get BufferedImage from disk, location specified by ResourceLocation.
      *
      * @param location The location the image is stored at.
      * @return The image found at the specified location.
      */
    def getBufferedImage(location: ResourceLocation): BufferedImage = {
        try {
            return ImageIO.read(getClass.getResourceAsStream("/assets/" + location.getResourceDomain + "/" + location.getResourcePath))
        } catch {
            case e: Exception => e.printStackTrace()
        }
        null
    }
}
