package zone.rong.zairyou.api.ore.stone;

import net.minecraft.util.IStringSerializable;
import net.minecraft.util.ResourceLocation;
import zone.rong.zairyou.Zairyou;
import zone.rong.zairyou.api.material.Material;
import zone.rong.zairyou.api.material.MaterialBuilder;
import zone.rong.zairyou.api.util.ITranslatable;

import java.util.Locale;

public enum StoneType implements IStringSerializable, ITranslatable {

    ANDESITE("tfc", new ResourceLocation(Zairyou.ID, "blocks/stone_type/andesite"), "", "stone", "andesite"),
    BASALT("tfc", new ResourceLocation(Zairyou.ID, "blocks/stone_type/basalt"), "", "stone", "basalt"),
    CHALK("tfc", new ResourceLocation(Zairyou.ID, "blocks/stone_type/chalk"), "", "stone", "chalk");

    public static final StoneType[] VALUES;

    static {
        VALUES = values();
    }

    private final String modId;
    private final Material backingMaterial;
    private final ResourceLocation baseTexture, topTexture;
    private final String[] prefixes;

    StoneType(String modId, ResourceLocation baseTexture, String... prefixes) {
        this.modId = modId;
        this.backingMaterial = MaterialBuilder.of(toString()).build();
        this.baseTexture = baseTexture;
        this.topTexture = baseTexture;
        this.prefixes = prefixes;
    }

    public String getModId() {
        return modId;
    }

    public Material getMaterial() {
        return backingMaterial;
    }

    public ResourceLocation getBaseTexture() {
        return baseTexture;
    }

    @Deprecated
    public ResourceLocation getTopTexture() {
        return topTexture;
    }

    @Override
    public String getTranslationKey() {
        return String.join(".", modId, "stone_type", toString(), "name");
    }

    @Override
    public String getName() {
        return toString();
    }

    @Override
    public String toString() {
        return this.name().toLowerCase(Locale.ROOT);
    }

}
