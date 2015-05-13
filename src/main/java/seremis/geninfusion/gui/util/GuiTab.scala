package seremis.geninfusion.gui.util

import seremis.geninfusion.gui.GIGui

abstract class GuiTab(id: Int, x: Int, y: Int, w: Int, h: Int, u: Int, v: Int, name: String) extends GuiRectangle(x, y, w, h, u, v) {

    var visible: Boolean = true

    def getId: Int = id

    override def draw(gui: GIGui) {
        if (visible) {
            gui.drawTexturedModalRect(gui.getLeft + x, gui.getTop + y, u, v, w, h)
        }
    }

    override def drawString(gui: GIGui, mouseX: Int, mouseY: Int, str: String) {
        if (visible && inRect(gui, mouseX, mouseY)) {
            gui.drawHoveringString(List(str.split("\n"):_*), mouseX - gui.getLeft, mouseY - gui.getTop)
        }
    }

    def getName: String = name

    def drawBackground(gui: GIGui, x: Int, y: Int)

    def drawForeground(gui: GIGui, x: Int, y: Int)

    def mouseClick(gui: GIGui, x: Int, y: Int, button: Int) {}

    def mouseMoveClick(gui: GIGui, x: Int, y: Int, button: Int, timeSinceClicked: Long) {}

    def mouseReleased(gui: GIGui, x: Int, y: Int, button: Int) {}

    def initGui(gui: GIGui) {}

    def keyTyped(gui: GIGui, keyTyped: Char, keyCode: Int) {}

    override def toString: String = "GuiTab[id: " + id + " x: " + x + ", y: " + y + ", width: " + w + ", height: " + h + " u:" + u + " v: " + v + ", name: '" + name + "']"
}