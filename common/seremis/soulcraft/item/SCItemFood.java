package seremis.soulcraft.item;

import java.util.List;

import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Icon;
import seremis.soulcraft.SoulCraft;
import seremis.soulcraft.core.lib.DefaultProps;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class SCItemFood extends ItemFood {

    private Icon iconBuffer[];
    private int metadata = 0;

    public SCItemFood(int ID, int healAmount, float saturationModifier, boolean isWolfsFavoriteMeat) {
        super(ID, healAmount, saturationModifier, isWolfsFavoriteMeat);
        setMaxStackSize(64);
        setCreativeTab(SoulCraft.CreativeTab);
    }

    public SCItemFood(int ID, int healAmount, boolean isWolfsFavoriteMeat) {
        this(ID, healAmount, 0.6F, isWolfsFavoriteMeat);
    }

    public SCItemFood(int ID, int healAmount) {
        this(ID, healAmount, false);
    }

    @Override
    public SCItemFood setUnlocalizedName(String par1Str) {
        return (SCItemFood) super.setUnlocalizedName(par1Str);
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
