package seremis.geninfusion.helper;

import seremis.geninfusion.lib.DefaultProps;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ConnectedTextureHelper {

    private Block block;

    private String[] textures = new String[47];

    private IIcon[] icons = new IIcon[47];

    private int[][][] coordMatrix = { { {0, 0, -1}, {0, 0, 1}, {-1, 0, 0}, {1, 0, 0}, {-1, 0, -1}, {1, 0, -1}, {-1, 0, 1}, {1, 0, 1}}, { {0, 0, -1}, {0, 0, 1}, {-1, 0, 0}, {1, 0, 0}, {-1, 0, -1}, {1, 0, -1}, {-1, 0, 1}, {1, 0, 1}}, { {0, 1, 0}, {0, -1, 0}, {1, 0, 0}, {-1, 0, 0}, {1, 1, 0}, {-1, 1, 0}, {1, -1, 0}, {-1, -1, 0}}, { {0, 1, 0}, {0, -1, 0}, {-1, 0, 0}, {1, 0, 0}, {-1, 1, 0}, {1, 1, 0}, {-1, -1, 0}, {1, -1, 0}}, { {0, 1, 0}, {0, -1, 0}, {0, 0, -1}, {0, 0, 1}, {0, 1, -1}, {0, 1, 1}, {0, -1, -1}, {0, -1, 1}}, { {0, 1, 0}, {0, -1, 0}, {0, 0, 1}, {0, 0, -1}, {0, 1, 1}, {0, 1, -1}, {0, -1, 1}, {0, -1, -1}}};

    public ConnectedTextureHelper(Block block, String[] textureName) {
        this.block = block;
        textures = textureName;
    }

    public ConnectedTextureHelper(Block block, String textureName) {
        this.block = block;
        setLogicTextureNames(textureName);
    }

    private void setLogicTextureNames(String textureName) {
        for(int i = 0; i < textures.length; i++) {
            textures[i] = textureName + i;
        }

    }

    public void registerBlockIcons(IIconRegister iconRegister) {
        for(int i = 0; i < textures.length; i++) {
            icons[i] = iconRegister.registerIcon(DefaultProps.ID + ":connected/" + textures[i]);
        }
    }

    @SideOnly(Side.CLIENT)
    public IIcon getIcon(IBlockAccess blockaccess, int x, int y, int z, int side) {
        Block[] adjacentIds = new Block[8];

        int mapping = 0;

        int[][] adjacentCoords = new int[8][3];

        boolean up = false, down = false, left = false, right = false, upLeft = false, upRight = false, downLeft = false, downRight = false;

        for(int i = 0; i < 8; i++) {
            int[] array = coordMatrix[side][i];
            adjacentIds[i] = blockaccess.getBlock(x + array[0], y + array[1], z + array[2]);
            if(adjacentIds[i] == block) {
                adjacentCoords[i] = new int[] {x + array[0], y + array[1], z + array[2]};
                switch(i) {
                    case 0:
                        up = true;
                        break;
                    case 1:
                        down = true;
                        break;
                    case 2:
                        left = true;
                        break;
                    case 3:
                        right = true;
                        break;
                    case 4:
                        upLeft = true;
                        break;
                    case 5:
                        upRight = true;
                        break;
                    case 6:
                        downLeft = true;
                        break;
                    case 7:
                        downRight = true;
                        break;
                }
            }
        }

        if(up) {
            mapping = 36;
            if(right) {
                mapping = 16;
                if(left) {
                    mapping = 18;
                    if(down) {
                        mapping = 46;
                        if(upRight) {
                            mapping = 8;
                            if(upLeft) {
                                mapping = 11;
                                if(downRight) {
                                    mapping = 33;
                                    if(downLeft) {
                                        mapping = 26;
                                    }
                                } else if(downLeft) {
                                    mapping = 32;
                                }
                            } else if(downRight) {
                                mapping = 23;
                                if(downLeft) {
                                    mapping = 45;
                                }
                            } else if(downLeft) {
                                mapping = 34;
                            }
                        } else if(downRight) {
                            mapping = 9;
                            if(upLeft) {
                                mapping = 35;
                                if(downLeft) {
                                    mapping = 44;
                                }
                            } else if(downLeft) {
                                mapping = 22;
                            }
                        } else if(upLeft) {
                            mapping = 20;
                            if(downLeft) {
                                mapping = 10;
                            }
                        } else if(downLeft) {
                            mapping = 21;
                        }
                    } else if(upLeft) {
                        mapping = 42;
                        if(upRight) {
                            mapping = 38;
                        }
                    } else if(upRight) {
                        mapping = 40;
                    } else if(downRight) {
                        mapping = 18;
                        if(downLeft) {
                            mapping = 18;
                        }
                    }
                } else if(down) {
                    mapping = 6;
                    if(downRight) {
                        mapping = 28;
                        if(upRight) {
                            mapping = 25;
                        }
                    } else if(upRight) {
                        mapping = 30;
                    }
                } else if(upRight) {
                    mapping = 37;
                }
            } else if(left) {
                mapping = 17;
                if(down) {
                    mapping = 19;
                    if(upLeft) {
                        mapping = 41;
                        if(downLeft) {
                            mapping = 27;
                        }
                    } else if(downLeft) {
                        mapping = 43;
                    }
                } else if(upLeft) {
                    mapping = 39;
                }
            } else if(down) {
                mapping = 24;
            }
        } else if(down) {
            mapping = 12;
            if(right) {
                mapping = 4;
                if(left) {
                    mapping = 7;
                    if(downRight) {
                        mapping = 31;
                        if(downLeft) {
                            mapping = 14;
                        }
                    } else if(downLeft) {
                        mapping = 29;
                    }
                } else if(downRight) {
                    mapping = 13;
                }
            } else if(left) {
                mapping = 5;
                if(downLeft) {
                    mapping = 15;
                }

            }
        } else if(left) {
            mapping = 3;
            if(right) {
                mapping = 2;
            }
        } else if(right) {
            mapping = 1;
        }

        return icons[mapping];
    }

}
