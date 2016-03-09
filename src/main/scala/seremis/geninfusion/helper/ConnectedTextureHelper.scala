package seremis.geninfusion.helper

import net.minecraft.block.Block
import net.minecraft.world.IBlockAccess
import seremis.geninfusion.lib.DefaultProps

class ConnectedTextureHelper(block: Block, textures: Array[String]) {

    val icons: Array[IIcon] = new Array[IIcon](47)

    val coordMatrix: Array[Array[Array[Int]]] = Array(Array(Array(0, 0, -1), Array(0, 0, 1), Array(-1, 0, 0), Array(1, 0, 0), Array(-1, 0, -1), Array(1, 0, -1), Array(-1, 0, 1), Array(1, 0, 1)), Array(Array(0, 0, -1), Array(0, 0, 1), Array(-1, 0, 0), Array(1, 0, 0), Array(-1, 0, -1), Array(1, 0, -1), Array(-1, 0, 1), Array(1, 0, 1)), Array(Array(0, 1, 0), Array(0, -1, 0), Array(1, 0, 0), Array(-1, 0, 0), Array(1, 1, 0), Array(-1, 1, 0), Array(1, -1, 0), Array(-1, -1, 0)), Array(Array(0, 1, 0), Array(0, -1, 0), Array(-1, 0, 0), Array(1, 0, 0), Array(-1, 1, 0), Array(1, 1, 0), Array(-1, -1, 0), Array(1, -1, 0)), Array(Array(0, 1, 0), Array(0, -1, 0), Array(0, 0, -1), Array(0, 0, 1), Array(0, 1, -1), Array(0, 1, 1), Array(0, -1, -1), Array(0, -1, 1)), Array(Array(0, 1, 0), Array(0, -1, 0), Array(0, 0, 1), Array(0, 0, -1), Array(0, 1, 1), Array(0, 1, -1), Array(0, -1, 1), Array(0, -1, -1)))

    def this(block: Block, textureName: String) {
        this(block, Array.ofDim[String](47))
        setLogicTextureNames(textureName)
    }

    private def setLogicTextureNames(textureName: String) {
        for (i <- 0 until textures.length) {
            textures(i) = textureName + i
        }
    }

    @SideOnly(Side.CLIENT)
    def registerIcons(iconRegister: IIconRegister) {
        for (i <- 0 until textures.length) {
            icons(i) = iconRegister.registerIcon(DefaultProps.ID + ":connected/" + textures(i))
        }
    }

    @SideOnly(Side.CLIENT)
    def getIcon(blockAccess: IBlockAccess, x: Int, y: Int, z: Int, side: Int): IIcon = {
        val adjacentIds = Array.ofDim[Block](8)

        var mapping = 0

        val adjacentCoords = Array.ofDim[Int](8, 3)

        var up = false
        var down = false
        var left = false
        var right = false
        var upLeft = false
        var upRight = false
        var downLeft = false
        var downRight = false

        for (i <- 0 until 8) {
            val array = coordMatrix(side)(i)
            adjacentIds(i) = blockAccess.getBlock(x + array(0), y + array(1), z + array(2))

            if (adjacentIds(i) == block) {
                adjacentCoords(i) = Array(x + array(0), y + array(1), z + array(2))
                i match {
                    case 0 => up = true
                    case 1 => down = true
                    case 2 => left = true
                    case 3 => right = true
                    case 4 => upLeft = true
                    case 5 => upRight = true
                    case 6 => downLeft = true
                    case 7 => downRight = true
                }
            }
        }

        if (up) {
            mapping = 36
            if (right) {
                mapping = 16
                if (left) {
                    mapping = 18
                    if (down) {
                        mapping = 46
                        if (upRight) {
                            mapping = 8
                            if (upLeft) {
                                mapping = 11
                                if (downRight) {
                                    mapping = 33
                                    if (downLeft) {
                                        mapping = 26
                                    }
                                } else if (downLeft) {
                                    mapping = 32
                                }
                            } else if (downRight) {
                                mapping = 23
                                if (downLeft) {
                                    mapping = 45
                                }
                            } else if (downLeft) {
                                mapping = 34
                            }
                        } else if (downRight) {
                            mapping = 9
                            if (upLeft) {
                                mapping = 35
                                if (downLeft) {
                                    mapping = 44
                                }
                            } else if (downLeft) {
                                mapping = 22
                            }
                        } else if (upLeft) {
                            mapping = 20
                            if (downLeft) {
                                mapping = 10
                            }
                        } else if (downLeft) {
                            mapping = 21
                        }
                    } else if (upLeft) {
                        mapping = 42
                        if (upRight) {
                            mapping = 38
                        }
                    } else if (upRight) {
                        mapping = 40
                    } else if (downRight) {
                        mapping = 18
                        if (downLeft) {
                            mapping = 18
                        }
                    }
                } else if (down) {
                    mapping = 6
                    if (downRight) {
                        mapping = 28
                        if (upRight) {
                            mapping = 25
                        }
                    } else if (upRight) {
                        mapping = 30
                    }
                } else if (upRight) {
                    mapping = 37
                }
            } else if (left) {
                mapping = 17
                if (down) {
                    mapping = 19
                    if (upLeft) {
                        mapping = 41
                        if (downLeft) {
                            mapping = 27
                        }
                    } else if (downLeft) {
                        mapping = 43
                    }
                } else if (upLeft) {
                    mapping = 39
                }
            } else if (down) {
                mapping = 24
            }
        } else if (down) {
            mapping = 12
            if (right) {
                mapping = 4
                if (left) {
                    mapping = 7
                    if (downRight) {
                        mapping = 31
                        if (downLeft) {
                            mapping = 14
                        }
                    } else if (downLeft) {
                        mapping = 29
                    }
                } else if (downRight) {
                    mapping = 13
                }
            } else if (left) {
                mapping = 5
                if (downLeft) {
                    mapping = 15
                }
            }
        } else if (left) {
            mapping = 3
            if (right) {
                mapping = 2
            }
        } else if (right) {
            mapping = 1
        }

        icons(mapping)
    }
}