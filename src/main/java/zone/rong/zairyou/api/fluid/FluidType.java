package zone.rong.zairyou.api.fluid;

import net.minecraft.init.SoundEvents;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import zone.rong.zairyou.Zairyou;

public enum FluidType {

    LIQUID(new ResourceLocation(Zairyou.ID, "blocks/fluids/still"), new ResourceLocation(Zairyou.ID, "blocks/fluids/still")),
    MOLTEN(new ResourceLocation(Zairyou.ID, "blocks/fluids/molten_still"), new ResourceLocation(Zairyou.ID, "blocks/fluids/molten_still"),
            SoundEvents.ITEM_BUCKET_FILL_LAVA, SoundEvents.ITEM_BUCKET_EMPTY_LAVA),
    GASEOUS(LIQUID.stillTexture, LIQUID.flowingTexture),
    PLASMA(MOLTEN.stillTexture, MOLTEN.flowingTexture);

    public static final FluidType[] VALUES;

    static {
        VALUES = values();
    }

    private final ResourceLocation stillTexture, flowingTexture;
    private final SoundEvent fillSound, emptySound;

    FluidType(ResourceLocation stillTexture, ResourceLocation flowingTexture) {
        this.stillTexture = stillTexture;
        this.flowingTexture = flowingTexture;
        this.fillSound = SoundEvents.ITEM_BUCKET_FILL;
        this.emptySound = SoundEvents.ITEM_BUCKET_EMPTY;
    }

    FluidType(ResourceLocation stillTexture, ResourceLocation flowingTexture, SoundEvent fillSound, SoundEvent emptySound) {
        this.stillTexture = stillTexture;
        this.flowingTexture = flowingTexture;
        this.fillSound = fillSound;
        this.emptySound = emptySound;
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

}
