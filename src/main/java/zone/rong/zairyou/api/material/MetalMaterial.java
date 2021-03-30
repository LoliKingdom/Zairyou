package zone.rong.zairyou.api.material;

import net.dries007.tfc.api.types.Metal;
import net.dries007.tfc.objects.fluids.FluidsTFC;
import net.dries007.tfc.objects.fluids.properties.FluidWrapper;
import net.dries007.tfc.objects.fluids.properties.MetalProperty;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.util.EnumHelper;
import zone.rong.zairyou.Zairyou;
import zone.rong.zairyou.api.fluid.FluidType;
import zone.rong.zairyou.api.item.tool.ExtendedToolMaterial;
import zone.rong.zairyou.api.material.element.Element;

public class MetalMaterial extends Material {

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

    protected final Metal metal;
    protected final FluidWrapper metalFluidWrapper;

    protected MetalMaterial(String name, int colour, Element element, ExtendedToolMaterial toolMaterial, Metal.Tier metalTier, float specificHeat, int meltTemp) {
        super(name, colour, element, toolMaterial);
        fluid(FluidType.MOLTEN, fluid -> fluid.still("blocks/fluids/molten_still").flow("blocks/fluids/molten_flow").temperature(meltTemp + 273).luminosity(15).density(600).viscosity(6000).rarity(EnumRarity.EPIC));
        this.metal = new Metal(new ResourceLocation(Zairyou.ID, name), metalTier, true, specificHeat, meltTemp, colour, toolMaterial.getBase(), null);
        this.metalFluidWrapper = FluidsTFC.getWrapper(this.fluids.get(FluidType.MOLTEN)).with(MetalProperty.METAL, new MetalProperty(this.metal));
    }

    public Metal getRepresentation() {
        return metal;
    }

    public Metal.Tier getMetalTier() {
        return metal.getTier();
    }

    public float getSpecificHeat() {
        return metal.getSpecificHeat();
    }

    public int getMeltingTemperature() {
        return (int) metal.getMeltTemp();
    }

    public FluidWrapper getMetalFluidWrapper() {
        return metalFluidWrapper;
    }

}
