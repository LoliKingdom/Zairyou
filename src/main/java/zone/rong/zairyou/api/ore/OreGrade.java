package zone.rong.zairyou.api.ore;

import net.minecraft.util.ResourceLocation;
import zone.rong.zairyou.Zairyou;
import zone.rong.zairyou.api.util.ITranslatable;

import java.util.Locale;

public enum OreGrade implements ITranslatable {

    NORMAL("", new ResourceLocation(Zairyou.ID, "blocks/grade/normal")),
    POOR("Poor", new ResourceLocation(Zairyou.ID, "blocks/grade/poor")),
    RICH("Rich", new ResourceLocation(Zairyou.ID, "blocks/grade/rich"));

    public static final OreGrade[] VALUES;

    static {
        VALUES = values();
    }

    private final String append;
    private final ResourceLocation textureLocation;

    OreGrade(String append, ResourceLocation textureLocation) {
        this.append = append;
        this.textureLocation = textureLocation;
    }

    public String getAppend() {
        return append;
    }

    public ResourceLocation getTextureLocation() {
        return textureLocation;
    }

    @Override
    public String getTranslationKey() {
        return "zairyou.ore_grade." + toString() + ".name";
    }

    @Override
    public String toString() {
        return this.name().toLowerCase(Locale.ROOT);
    }

}
