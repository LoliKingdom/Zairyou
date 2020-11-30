package zone.rong.zairyou.api.item;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import zone.rong.zairyou.api.material.Material;
import zone.rong.zairyou.api.material.type.MaterialType;

public class MaterialItem extends Item {

    private final Material material;
    private final MaterialType materialType;

    public MaterialItem(Material material, MaterialType materialType) {
        this.material = material;
        this.materialType = materialType;
        this.setCreativeTab(CreativeTabs.MATERIALS);
    }

    public Material getMaterial() {
        return material;
    }

    public MaterialType getMaterialType() {
        return materialType;
    }

}
