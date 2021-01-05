package zone.rong.zairyou.api.fluid;

import net.minecraft.client.resources.I18n;
import net.minecraft.init.PotionTypes;
import net.minecraft.potion.PotionType;
import net.minecraft.potion.PotionUtils;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import zone.rong.zairyou.Zairyou;

public class PotionFluid extends Fluid {

    private final String prefix;

    public PotionFluid(String fluidName, String prefix) {
        super(fluidName, new ResourceLocation(Zairyou.ID, "blocks/fluids/potion_still"), new ResourceLocation(Zairyou.ID, "blocks/fluids/potion_flow"));
        this.prefix = prefix;
        FluidRegistry.registerFluid(this);
    }

    @Override
    public int getColor() {
        return 0xFFF800F8;
    }

    @Override
    public int getColor(FluidStack stack) {
        return PotionUtils.getPotionColor(getPotionType(stack));
    }

    public PotionType getPotionType(FluidStack stack) {
        return PotionUtils.getPotionTypeFromNBT(stack.tag);
    }

    @Override
    public String getLocalizedName(FluidStack stack) {
        PotionType type = PotionUtils.getPotionTypeFromNBT(stack.tag);
        if (type == PotionTypes.EMPTY || type == PotionTypes.WATER) {
            return super.getLocalizedName(stack);
        }
        return I18n.format(type.getNamePrefixed(prefix));
    }

}
