package zone.rong.zairyou.api.fluid;

import net.minecraft.util.ResourceLocation;
import zone.rong.zairyou.Zairyou;

public enum FluidType {

    LIQUID(new ResourceLocation(Zairyou.ID, "blocks/fluids/still"), new ResourceLocation(Zairyou.ID, "blocks/fluids/still")),
    MOLTEN(new ResourceLocation(Zairyou.ID, "blocks/fluids/molten_still"), new ResourceLocation(Zairyou.ID, "blocks/fluids/molten_still")),
    GASEOUS(LIQUID.stillTexture, LIQUID.flowingTexture),
    PLASMA(MOLTEN.stillTexture, MOLTEN.flowingTexture);

    public static final FluidType[] VALUES;

    static {
        VALUES = values();
    }

    private final ResourceLocation stillTexture, flowingTexture;

    FluidType(ResourceLocation stillTexture, ResourceLocation flowingTexture) {
        this.stillTexture = stillTexture;
        this.flowingTexture = flowingTexture;
    }

    public ResourceLocation getStillTexture() {
        return stillTexture;
    }

    public ResourceLocation getFlowingTexture() {
        return flowingTexture;
    }

}
