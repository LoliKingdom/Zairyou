package zone.rong.zairyou.api.ore;

import zone.rong.zairyou.api.material.type.BlockMaterialType;
import zone.rong.zairyou.api.material.type.ItemMaterialType;
import zone.rong.zairyou.api.util.ITranslatable;

import java.util.Locale;

public enum OreGrade implements ITranslatable {

    NORMAL("", BlockMaterialType.ORE, ItemMaterialType.ORE),
    SMALL("Small", BlockMaterialType.ORE_SURFACE_ROCK, ItemMaterialType.ORE_SMALL),
    POOR("Poor", BlockMaterialType.ORE_POOR, ItemMaterialType.ORE_POOR),
    RICH("Rich", BlockMaterialType.ORE_RICH, ItemMaterialType.ORE_RICH);

    public static final OreGrade[] VALUES;

    static {
        VALUES = values();
    }

    private final String append;
    private final BlockMaterialType blockRepresentation;
    private final ItemMaterialType itemRepresentation;

    OreGrade(String append, BlockMaterialType blockRepresentation, ItemMaterialType itemRepresentation) {
        this.append = append;
        this.blockRepresentation = blockRepresentation;
        this.itemRepresentation = itemRepresentation;
    }

    public String getAppend() {
        return append;
    }

    public BlockMaterialType getBlockRepresentation() {
        return blockRepresentation;
    }

    public ItemMaterialType getItemRepresentation() {
        return itemRepresentation;
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
