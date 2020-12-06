package zone.rong.zairyou.api.item.tool;

import net.minecraft.item.ItemAxe;
import net.minecraft.item.ItemStack;
import zone.rong.zairyou.api.material.Material;

public class MaterialAxeItem extends ItemAxe implements IMaterialTool {

    public MaterialAxeItem() {
        super(Material.NONE.getToolMaterial().getBase()); // Dummy ToolMaterial
    }

    @Override
    public int getMaxDamage(ItemStack stack) {
        return getMaterial(stack).getToolMaterial().getBase().getMaxUses();
    }

    @Override
    public void setDamage(ItemStack stack, int damage) {
        setDurability(stack, damage);
    }

}
