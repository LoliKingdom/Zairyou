package zone.rong.zairyou.api.item;

import net.minecraft.client.resources.I18n;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import zone.rong.zairyou.api.material.Material;
import zone.rong.zairyou.api.material.type.ItemMaterialType;

import javax.annotation.Nullable;

public class MaterialItem extends Item {

    private final Material material;
    private final ItemMaterialType itemMaterialType;

    private final String[] oreNames;

    public MaterialItem(Material material, ItemMaterialType itemMaterialType) {
        this.material = material;
        this.itemMaterialType = itemMaterialType;
        this.oreNames = material.getOreNames(itemMaterialType);
        this.setCreativeTab(CreativeTabs.MATERIALS);
    }

    public Material getMaterial() {
        return material;
    }

    public ItemMaterialType getMaterialType() {
        return itemMaterialType;
    }

    public String[] getOreNames() {
        return oreNames;
    }

    @Override
    public String getItemStackDisplayName(ItemStack stack) {
        // return I18n.translateToLocal(material.getTranslationKey()) + " " + I18n.translateToLocal(materialType.getTranslationKey());
        return I18n.format(itemMaterialType.getTranslationKey(), I18n.format(material.getTranslationKey()));
    }

    @Nullable
    @Override
    public String getCreatorModId(ItemStack stack) {
        return itemMaterialType.getModID();
    }
}
