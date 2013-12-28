package seremis.soulcraft.item;

import java.util.List;

import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Icon;
import seremis.soulcraft.SoulCraft;
import seremis.soulcraft.core.lib.DefaultProps;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class SCItem extends Item {

    private Icon iconBuffer[];
    private int metadata = 0;

    public SCItem(int ID) {
        super(ID);
        setMaxStackSize(64);
        setCreativeTab(SoulCraft.CreativeTab);
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

    @Override
    @SideOnly(Side.CLIENT)
    public void getSubItems(int itemID, CreativeTabs creativetab, List list) {
        if(metadata > 0) {
            for(int i = 0; i < getNumbersofMetadata(); ++i) {
                list.add(new ItemStack(itemID, 1, i));
            }
        } else {
            list.add(new ItemStack(itemID, 1, 0));
        }
    }

    public int getNumbersofMetadata() {
        return this.metadata;
    }

    public void setNumbersofMetadata(int metadata) {
        this.metadata = metadata;
    }

}
