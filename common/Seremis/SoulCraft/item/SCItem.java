package Seremis.SoulCraft.item;

import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.item.Item;
import net.minecraft.util.Icon;
import Seremis.SoulCraft.mod_SoulCraft;
import Seremis.SoulCraft.core.lib.DefaultProps;

public class SCItem extends Item {

    private Icon iconBuffer[];
    private int metadata = 0;

    public SCItem(int ID) {
        super(ID);
        setMaxStackSize(64);
        setCreativeTab(mod_SoulCraft.CreativeTab);
    }

    @Override
    public SCItem setUnlocalizedName(String par1Str) {
        return (SCItem) super.setUnlocalizedName(par1Str);
    }

    @Override
    public void registerIcons(IconRegister iconRegister) {
        if(this.metadata == 0) {
            itemIcon = iconRegister.registerIcon(DefaultProps.ID + ":" + this.getUnlocalizedName().substring(5));
        } else {
            iconBuffer = new Icon[metadata];
            for(int x = 0; x < iconBuffer.length; x++) {
                iconBuffer[x] = iconRegister.registerIcon(DefaultProps.ID + ":" + this.getUnlocalizedName().substring(5) + (x + 1));
            }
        }
    }

    @Override
    public Icon getIconFromDamage(int metadata) {
        if(this.metadata != 0) {
            itemIcon = iconBuffer[metadata];
        }
        return this.itemIcon;
    }

    public int getNumbersofMetadata() {
        return this.metadata;
    }

    public void setNumbersofMetadata(int metadata) {
        this.metadata = metadata;
    }

}
