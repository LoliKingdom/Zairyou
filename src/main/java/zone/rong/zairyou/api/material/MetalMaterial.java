package zone.rong.zairyou.api.material;

import net.dries007.tfc.api.types.Metal;
import net.minecraft.item.Item;
import net.minecraftforge.common.util.EnumHelper;
import zone.rong.zairyou.api.item.tool.ExtendedToolMaterial;
import zone.rong.zairyou.api.material.element.Element;

public class MetalMaterial extends Material<MetalMaterial> {

    public static MetalMaterial ofMetal(String name, int colour, Metal.Tier metalTier, float specificHeat, int meltTemp) {
        return ofMetal(name, colour, null, NONE_TOOL_EXTENDED, metalTier, specificHeat, meltTemp);
    }

    public static MetalMaterial ofMetal(String name, Metal.Tier metalTier, float specificHeat, int meltTemp) {
        return ofMetal(name, 0x0, null, NONE_TOOL_EXTENDED, metalTier, specificHeat, meltTemp);
    }

    public static MetalMaterial ofMetal(String name, int colour, Element element, Metal.Tier metalTier, float specificHeat, int meltTemp) {
        return ofMetal(name, colour, element, NONE_TOOL_EXTENDED, metalTier, specificHeat, meltTemp);
    }

    public static MetalMaterial ofMetal(String name, int colour, Element element, int harvestLevel, int durability, float efficiency, float attackDamage, float attackSpeed, int enchantability, Metal.Tier metalTier, float specificHeat, int meltTemp) {
        return ofMetal(name, colour, element, EnumHelper.addToolMaterial(name, harvestLevel, durability, efficiency, attackDamage, enchantability), attackSpeed, metalTier, specificHeat, meltTemp);
    }

    public static MetalMaterial ofMetal(String name, int colour, Element element, Item.ToolMaterial toolMaterial, float attackSpeed, Metal.Tier metalTier, float specificHeat, int meltTemp) {
        return ofMetal(name, colour, element, new ExtendedToolMaterial(toolMaterial, attackSpeed), metalTier, specificHeat, meltTemp);
    }

    public static MetalMaterial ofMetal(String name, int colour, Element element, ExtendedToolMaterial extendedToolMaterial, Metal.Tier metalTier, float specificHeat, int meltTemp) {
        return new MetalMaterial(name, colour, element, extendedToolMaterial, metalTier, specificHeat, meltTemp);
    }

    private final Metal.Tier metalTier;
    private final float specificHeat;
    private final int meltTemp;

    protected MetalMaterial(String name, int colour, Element element, ExtendedToolMaterial toolMaterial, Metal.Tier metalTier, float specificHeat, int meltTemp) {
        super(name, colour, element, toolMaterial);
        this.metalTier = metalTier;
        this.specificHeat = specificHeat;
        this.meltTemp = meltTemp;
    }

    public Metal.Tier getMetalTier() {
        return metalTier;
    }

    public float getSpecificHeat() {
        return specificHeat;
    }

    public int getMeltingTemperature() {
        return meltTemp;
    }

}
