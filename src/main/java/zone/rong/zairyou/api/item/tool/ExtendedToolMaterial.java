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

    public int getMaxUses() {
        return this.base.getMaxUses();
    }

    public int getDurability() {
        return this.getMaxUses();
    }

    public float getEfficiency() {
        return this.base.getEfficiency();
    }

    public float getAttackDamage() {
        return this.base.getAttackDamage();
    }

    public int getHarvestLevel() {
        return this.base.getHarvestLevel();
    }

    public int getEnchantability() {
        return this.base.getEnchantability();
    }

    public float getAttackSpeed() {
        return attackSpeed;
    }

}
