package zone.rong.zairyou.api.fluid;

import net.minecraft.block.Block;
import net.minecraft.client.resources.I18n;
import net.minecraft.item.EnumRarity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import zone.rong.zairyou.Zairyou;
import zone.rong.zairyou.api.client.RenderUtils;
import zone.rong.zairyou.api.material.Material;

import java.util.function.Function;
import java.util.function.IntSupplier;
import java.util.function.Supplier;

public class ExtendedFluid extends Fluid {

    private final Material backingMaterial;
    private final FluidType fluidType;
    private final Supplier<String> translation;

    public ExtendedFluid(Builder builder) {
        super(builder.name, builder.still == null ? builder.fluidType.getStillTexture() : builder.still, builder.flow == null ? builder.fluidType.getFlowingTexture() : builder.flow, builder.overlay);
        this.backingMaterial = builder.material;
        this.fluidType = builder.fluidType;
        if (!builder.noTint) {
            setColor(RenderUtils.convertRGB2ARGB(fluidType.getBaseAlpha(), builder.colour));
        }
        if (builder.customTranslation) {
            final String tlKey = String.join(".", Zairyou.ID, "fluid", builder.name + "_" + fluidType.toString(), "name");
            this.translation = () -> I18n.format(tlKey);
        } else {
            this.translation = () -> I18n.format(fluidType.getTranslationKey(), I18n.format(backingMaterial.getTranslationKey()));
        }
    }

    @Override
    public String getLocalizedName(FluidStack stack) {
        return translation.get();
    }

    public static class Builder {

        private String name;

        private final Material material;
        private final FluidType fluidType;

        private int colour;
        private ResourceLocation still, flow, overlay;
        private boolean noTint, gasLike, customTranslation = false;
        private IntSupplier luminosity, density, viscosity, temperature;
        private Function<Fluid, Block> fluidBlock;
        private EnumRarity rarity;

        public Builder(Material material, FluidType fluidType) {
            this.name = material.getName();
            this.material = material;
            this.fluidType = fluidType;
            this.colour = material.getColour();
        }

        public Builder customColour(int colour) {
            this.colour = colour;
            return this;
        }

        public Builder customName(String name) {
            this.name = name;
            return this;
        }

        public Builder customTranslation() {
            this.customTranslation = true;
            return this;
        }

        public Builder still(ResourceLocation still) {
            this.still = still;
            return this;
        }

        public Builder still(String still) {
            this.still = new ResourceLocation(Zairyou.ID, still);
            return this;
        }

        public Builder flow(ResourceLocation flow) {
            this.flow = flow;
            return this;
        }

        public Builder flow(String flow) {
            this.flow = new ResourceLocation(Zairyou.ID, flow);
            return this;
        }

        public Builder overlay(ResourceLocation overlay) {
            this.overlay = overlay;
            return this;
        }

        public Builder noTint() {
            this.noTint = true;
            return this;
        }

        public Builder luminosity(int luminosity) {
            this.luminosity = () -> luminosity;
            return this;
        }

        public Builder density(int density) {
            this.density = () -> density;
            return this;
        }

        public Builder viscosity(int viscosity) {
            this.viscosity = () -> viscosity;
            return this;
        }

        public Builder temperature(int temperature) {
            this.temperature = () -> temperature;
            return this;
        }

        public Builder rarity(EnumRarity rarity) {
            this.rarity = rarity;
            return this;
        }

        public Builder gasLike() {
            this.gasLike = true;
            return this;
        }

        public Builder customBlock(Function<Fluid, Block> block) {
            this.fluidBlock = block;
            return this;
        }

        public ExtendedFluid build() {
            ExtendedFluid fluid = new ExtendedFluid(this);
            FluidRegistry.registerFluid(fluid);
            fluid.setBlock(this.fluidBlock == null ? new DefaultFluidBlock(fluid, this.fluidType) : this.fluidBlock.apply(fluid))
                    .setLuminosity(this.luminosity == null ? this.fluidType.getBaseLuminosity() : this.luminosity.getAsInt())
                    .setDensity(this.density == null ? this.fluidType.getBaseDensity() : this.density.getAsInt())
                    .setViscosity(this.viscosity == null ? this.fluidType.getBaseViscosity() : this.viscosity.getAsInt())
                    .setTemperature(this.temperature == null ? this.fluidType.getBaseTemperature() : this.temperature.getAsInt())
                    .setRarity(this.rarity == null ? EnumRarity.COMMON : this.rarity)
                    .setGaseous(this.fluidType == FluidType.GASEOUS || this.gasLike);
            return fluid;
        }

    }

}
