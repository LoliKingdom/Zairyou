package zone.rong.zairyou.api.fluid;

import net.minecraft.init.SoundEvents;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import zone.rong.zairyou.Zairyou;

import java.util.Locale;

public enum FluidType {

    LIQUID(new ResourceLocation(Zairyou.ID, "blocks/fluids/still"), new ResourceLocation(Zairyou.ID, "blocks/fluids/flow"), 0xAA),
    MOLTEN(new ResourceLocation(Zairyou.ID, "blocks/fluids/molten_still"), new ResourceLocation(Zairyou.ID, "blocks/fluids/molten_flow"),
            SoundEvents.ITEM_BUCKET_FILL_LAVA, SoundEvents.ITEM_BUCKET_EMPTY_LAVA, 0xFF),
    GASEOUS(LIQUID.stillTexture, LIQUID.flowingTexture, 0x69),
    PLASMA(MOLTEN.stillTexture, MOLTEN.flowingTexture, 0xBE);

    public static final FluidType[] VALUES;

    static {
        VALUES = values();
    }

    private final ResourceLocation stillTexture, flowingTexture;
    private final SoundEvent fillSound, emptySound;
    private final int alpha;

    FluidType(ResourceLocation stillTexture, ResourceLocation flowingTexture, int alpha) {
        this.stillTexture = stillTexture;
        this.flowingTexture = flowingTexture;
        this.fillSound = SoundEvents.ITEM_BUCKET_FILL;
        this.emptySound = SoundEvents.ITEM_BUCKET_EMPTY;
        this.alpha = alpha;
    }

    FluidType(ResourceLocation stillTexture, ResourceLocation flowingTexture, SoundEvent fillSound, SoundEvent emptySound, int alpha) {
        this.stillTexture = stillTexture;
        this.flowingTexture = flowingTexture;
        this.fillSound = fillSound;
        this.emptySound = emptySound;
        this.alpha = alpha;
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

    public String getTranslationKey() {
        return String.join(".", Zairyou.ID, toString(), "name");
    }

    @Override
    public String toString() {
        return this.name().toLowerCase(Locale.ROOT);
    }

}
