package zone.rong.zairyou.api.ore;

import net.dries007.tfc.api.capability.metal.IMetalItem;
import net.dries007.tfc.api.types.Metal;
import net.minecraft.item.ItemStack;
import zone.rong.zairyou.api.material.MetalMaterial;
import zone.rong.zairyou.api.material.type.ItemMaterialType;

import javax.annotation.Nullable;

public class MetalOreItem extends OreItem implements IMetalItem {

    public MetalOreItem(MetalMaterial material, ItemMaterialType itemMaterialType) {
        super(material, itemMaterialType);
    }

    @Override
    public MetalMaterial getMaterial() {
        return (MetalMaterial) material;
    }

    @Nullable
    @Override
    public Metal getMetal(ItemStack itemStack) {
        return getMaterial().getRepresentation();
    }

    @Override
    public int getSmeltAmount(ItemStack itemStack) {
        if (itemMaterialType == ItemMaterialType.ORE_SMALL) {
            return 10;
        } else if (itemMaterialType == ItemMaterialType.ORE) {
            return 25;
        } else if (itemMaterialType == ItemMaterialType.ORE_POOR) {
            return 15;
        } else { // Rich
            return 35;
        }
    }

}
