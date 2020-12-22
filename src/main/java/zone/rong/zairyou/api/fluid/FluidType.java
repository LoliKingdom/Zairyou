package zone.rong.zairyou.api.fluid;

import net.minecraft.init.SoundEvents;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import zone.rong.zairyou.Zairyou;

import java.util.Locale;

public enum FluidType {

    LIQUID(new ResourceLocation(Zairyou.ID, "blocks/fluids/still"), new ResourceLocation(Zairyou.ID, "blocks/fluids/flow"), 0xAA, 0, 1000, 1000, 300),
    MOLTEN(new ResourceLocation(Zairyou.ID, "blocks/fluids/molten_still"), new ResourceLocation(Zairyou.ID, "blocks/fluids/molten_flow"),
            SoundEvents.ITEM_BUCKET_FILL_LAVA, SoundEvents.ITEM_BUCKET_EMPTY_LAVA, 0xFF, 15, 3000, 6000, 1300),
    GASEOUS(LIQUID.stillTexture, LIQUID.flowingTexture, 0x69, 0, 1000, 1000, 300),
    PLASMA(MOLTEN.stillTexture, MOLTEN.flowingTexture, 0xBE, 15, 1000, 1000, 6400);

    public static final FluidType[] VALUES;

    static {
        VALUES = values();
    }

    private final ResourceLocation stillTexture, flowingTexture;
    private final SoundEvent fillSound, emptySound;
    private final int alpha, luminosity, density, viscosity, temperature;

    FluidType(ResourceLocation stillTexture, ResourceLocation flowingTexture, int alpha, int luminosity, int density, int viscosity, int temperature) {
        this.stillTexture = stillTexture;
        this.flowingTexture = flowingTexture;
        this.fillSound = SoundEvents.ITEM_BUCKET_FILL;
        this.emptySound = SoundEvents.ITEM_BUCKET_EMPTY;
        this.alpha = alpha;
        this.luminosity = luminosity;
        this.density = density;
        this.viscosity = viscosity;
        this.temperature = temperature;
    }

    FluidType(ResourceLocation stillTexture, ResourceLocation flowingTexture, SoundEvent fillSound, SoundEvent emptySound, int alpha, int luminosity, int density, int viscosity, int temperature) {
        this.stillTexture = stillTexture;
        this.flowingTexture = flowingTexture;
        this.fillSound = fillSound;
        this.emptySound = emptySound;
        this.alpha = alpha;
        this.luminosity = luminosity;
        this.density = density;
        this.viscosity = viscosity;
        this.temperature = temperature;
    }

    public ResourceLocation getStillTexture() {
        return stillTexture;
    }

    public ResourceLocation getFlowingTexture() {
        return flowingTexture;
    }

    public SoundEvent getFillSound() {
        return fillSound;
    }

    public SoundEvent getEmptySound() {
        return emptySound;
    }

    public int getBaseAlpha() {
        return alpha;
    }

    public int getBaseLuminosity() {
        return luminosity;
    }

    public int getBaseDensity() {
        return density;
    }

    public int getBaseViscosity() {
        return viscosity;
    }

    public int getBaseTemperature() {
        return temperature;
    }

    public String getTranslationKey() {
        return String.join(".", Zairyou.ID, toString(), "name");
    }

    @Override
    public String toString() {
        return this.name().toLowerCase(Locale.ROOT);
    }

}
