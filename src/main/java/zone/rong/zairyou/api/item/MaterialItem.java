package zone.rong.zairyou.api.item;

import net.minecraft.client.resources.I18n;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import zone.rong.zairyou.api.material.Material;
import zone.rong.zairyou.api.material.type.MaterialType;

import javax.annotation.Nullable;

public class MaterialItem extends Item {

    private final Material material;
    private final MaterialType materialType;

    private final String oreName;

    public MaterialItem(Material material, MaterialType materialType) {
        this.material = material;
        this.materialType = materialType;
        this.oreName = material.getOreName(materialType);
        this.setCreativeTab(CreativeTabs.MATERIALS);
    }

    public Material getMaterial() {
        return material;
    }

    public MaterialType getMaterialType() {
        return materialType;
    }

    public String getOreName() {
        return oreName;
    }

    @Override
    public String getItemStackDisplayName(ItemStack stack) {
        // return I18n.translateToLocal(material.getTranslationKey()) + " " + I18n.translateToLocal(materialType.getTranslationKey());
        return I18n.format(materialType.getTranslationKey(), I18n.format(material.getTranslationKey()));
    }

    @Nullable
    @Override
    public String getCreatorModId(ItemStack stack) {
        return materialType.getModID();
    }
}
