package seremis.geninfusion.gui.util

import seremis.geninfusion.gui.GIGui

class GuiRectangle(var x: Int, var y: Int, var w: Int, var h: Int, var u: Int, var v: Int) {

    def inRect(gui: GIGui, mouseX: Int, mouseY: Int): Boolean = isInBetween(x, x + w, mouseX - gui.getLeft) && isInBetween(y, y + h, mouseY - gui.getTop)

    def isInBetween(x: Int, y: Int, i: Int): Boolean = i >= Math.min(x, y) && i <= Math.max(x, y)


    def setX(x: Int) {
        this.x = x
    }

    def setY(y: Int) {
        this.y = y
    }

    def setWidth(w: Int) {
        this.w = w
    }

    def setHeight(h: Int) {
        this.h = h
    }

    def getX: Int = x

    def getY: Int = y

    def getWidth: Int = w

    def getHeight: Int = h

    def draw(gui: GIGui) {
        gui.drawTexturedModalRect(x, y, u, v, w, h)
    }

    def drawString(gui: GIGui, mouseX: Int, mouseY: Int, str: String) {
        if (inRect(gui, mouseX, mouseY)) {
            gui.drawHoveringString(List(str.split("\n"):_*), mouseX - gui.getLeft, mouseY - gui.getTop)
        }
    }

    override def toString: String = "GuiRectangle[x: " + x + ", y: " + y + ", width: " + w + ", height : " + h + "]"
}