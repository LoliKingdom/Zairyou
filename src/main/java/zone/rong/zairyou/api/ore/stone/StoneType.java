package zone.rong.zairyou.api.ore.stone;

import net.minecraft.util.IStringSerializable;
import net.minecraft.util.ResourceLocation;
import zone.rong.zairyou.Zairyou;
import zone.rong.zairyou.api.material.Material;
import zone.rong.zairyou.api.util.ITranslatable;

import java.util.Locale;

import static zone.rong.zairyou.api.material.Material.*;

public enum StoneType implements IStringSerializable, ITranslatable {

    ANDESITE("tfc", new ResourceLocation(Zairyou.ID, "blocks/stone_type/andesite"), "", "stone", "andesite"),
    BASALT("tfc", new ResourceLocation(Zairyou.ID, "blocks/stone_type/basalt"), "", "stone", "basalt"),
    CHALK("tfc", new ResourceLocation(Zairyou.ID, "blocks/stone_type/chalk"), "", "stone", "chalk"),
    CHERT("tfc", new ResourceLocation(Zairyou.ID, "blocks/stone_type/chalk"), "", "stone", "chalk"),
    CLAYSTONE("tfc", new ResourceLocation(Zairyou.ID, "blocks/stone_type/claystone"), "", "stone", "claystone"),
    CONGLOMERATE("tfc", new ResourceLocation(Zairyou.ID, "blocks/stone_type/conglomerate"), "", "stone", "conglomerate"),
    DACITE("tfc", new ResourceLocation(Zairyou.ID, "blocks/stone_type/dacite"), "", "stone", "dacite"),
    DIORITE("tfc", new ResourceLocation(Zairyou.ID, "blocks/stone_type/diorite"), "", "stone", "diorite"),
    DOLOMITE("tfc", new ResourceLocation(Zairyou.ID, "blocks/stone_type/dolomite"), "", "stone", "dolomite"),
    GABBRO("tfc", new ResourceLocation(Zairyou.ID, "blocks/stone_type/gabbro"), "", "stone", "gabbro"),
    GNEISS("tfc", new ResourceLocation(Zairyou.ID, "blocks/stone_type/gneiss"), "", "stone", "gneiss"),
    GRANITE("tfc", new ResourceLocation(Zairyou.ID, "blocks/stone_type/granite"), "", "stone", "granite"),
    LIMESTONE("tfc", new ResourceLocation(Zairyou.ID, "blocks/stone_type/limestone"), "", "stone", "limestone"),
    MARBLE("tfc", new ResourceLocation(Zairyou.ID, "blocks/stone_type/marble"), "", "stone", "marble"),
    PHYLLITE("tfc", new ResourceLocation(Zairyou.ID, "blocks/stone_type/phyllite"), "", "stone", "phyllite"),
    QUARTZITE("tfc", new ResourceLocation(Zairyou.ID, "blocks/stone_type/quartzite"), "", "stone", "quartzite"),
    RHYOLITE("tfc", new ResourceLocation(Zairyou.ID, "blocks/stone_type/rhyolite"), "", "stone", "rhyolite"),
    ROCKSALT("tfc", new ResourceLocation(Zairyou.ID, "blocks/stone_type/rocksalt"), "", "stone", "rocksalt"),
    SCHIST("tfc", new ResourceLocation(Zairyou.ID, "blocks/stone_type/schist"), "", "stone", "schist"),
    SHALE("tfc", new ResourceLocation(Zairyou.ID, "blocks/stone_type/shale"), "", "stone", "shale"),
    SLATE("tfc", new ResourceLocation(Zairyou.ID, "blocks/stone_type/slate"), "", "stone", "slate");

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
        this.backingMaterial = of(toString());
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
