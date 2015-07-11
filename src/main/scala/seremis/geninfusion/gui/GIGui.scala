package seremis.geninfusion.gui

import net.minecraft.client.gui.FontRenderer
import net.minecraft.client.gui.inventory.GuiContainer
import net.minecraft.inventory.Container

import scala.collection.JavaConversions._

class GIGui(guiContainer: Container) extends GuiContainer(guiContainer) {

    override def initGui() {
        super.initGui()
    }

    override def updateScreen() {
        super.updateScreen()
    }

    def getLeft: Int = guiLeft

    def getTop: Int = guiTop

    def getXSize: Int = xSize

    def getYSize: Int = ySize

    def getFontRenderer: FontRenderer = fontRendererObj

    def drawHoveringString(list: List[String], x: Int, y: Int) {
        drawHoveringText(list, x, y, fontRendererObj)
    }

    override def drawGuiContainerForegroundLayer(x: Int, y: Int) {}

    override def drawGuiContainerBackgroundLayer(f: Float, i: Int, j: Int) {}
}