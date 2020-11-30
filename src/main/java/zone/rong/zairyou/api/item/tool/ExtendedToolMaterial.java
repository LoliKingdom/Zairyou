package zone.rong.zairyou.api.item.tool;

import net.minecraft.item.Item;

public class ExtendedToolMaterial {

    private final Item.ToolMaterial base;
    private final float attackSpeed;

    public ExtendedToolMaterial(Item.ToolMaterial base, float attackSpeed) {
        this.base = base;
        this.attackSpeed = attackSpeed;
    }

    public Item.ToolMaterial getBase() {
        return base;
    }

    public float getAttackSpeed() {
        return attackSpeed;
    }
}
