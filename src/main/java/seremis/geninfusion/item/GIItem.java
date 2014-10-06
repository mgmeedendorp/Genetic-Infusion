package seremis.geninfusion.item;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import seremis.geninfusion.GeneticInfusion;
import seremis.geninfusion.lib.DefaultProps;

import java.util.List;

public class GIItem extends Item {

    private IIcon iconBuffer[];
    private int metadata = 0;

    public GIItem() {
        super();
        setMaxStackSize(64);
        setCreativeTab(GeneticInfusion.CreativeTab);
    }

    @Override
    public GIItem setUnlocalizedName(String par1Str) {
        return (GIItem) super.setUnlocalizedName(par1Str);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerIcons(IIconRegister iconRegister) {
        if(this.metadata == 0) {
            itemIcon = iconRegister.registerIcon(DefaultProps.ID + ":" + this.getUnlocalizedName().substring(5));
        } else {
            iconBuffer = new IIcon[metadata];
            for(int x = 0; x < iconBuffer.length; x++) {
                iconBuffer[x] = iconRegister.registerIcon(DefaultProps.ID + ":" + this.getUnlocalizedName().substring(5) + (x + 1));
            }
        }
    }

    @Override
    @SideOnly(Side.CLIENT)
    public IIcon getIconFromDamage(int metadata) {
        if(this.metadata != 0) {
            itemIcon = iconBuffer[metadata];
        }
        return this.itemIcon;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void getSubItems(Item item, CreativeTabs creativetab, List list) {
        if(metadata > 0) {
            for(int i = 0; i < getNumbersofMetadata(); ++i) {
                list.add(new ItemStack(item, 1, i));
            }
        } else {
            list.add(new ItemStack(item, 1, 0));
        }
    }

    public int getNumbersofMetadata() {
        return this.metadata;
    }

    public void setNumbersofMetadata(int metadata) {
        this.metadata = metadata;
    }

}
