package zone.rong.zairyou.api.material;

import zone.rong.zairyou.api.material.type.BlockMaterialType;
import zone.rong.zairyou.api.material.type.ItemMaterialType;

import static zone.rong.zairyou.api.material.type.BlockMaterialType.*;
import static zone.rong.zairyou.api.material.type.ItemMaterialType.*;

public enum MaterialFlag {

    METAL,
    ORE,
    GENERATE_DEFAULT_METAL_TYPES,
    GENERATE_DUST_VARIANTS,
    NEEDS_BLAST_FURNACE;

    public static final MaterialFlag[] VALUES;

    static {
        VALUES = values();
    }

    public final long bit;

    MaterialFlag() {
        this.bit = 1L << ordinal();
    }

    public void checkFlag(Material material) {
        switch (this) {
            case ORE:
                if ((material.flags & this.bit) > 0) {
                    material.block(BlockMaterialType.ORE);
                    material.block(BlockMaterialType.ORE_POOR);
                    material.block(BlockMaterialType.ORE_RICH);
                    material.items(ItemMaterialType.ORE, ItemMaterialType.ORE_POOR, ItemMaterialType.ORE_RICH);
                    material.flags |= GENERATE_DUST_VARIANTS.bit;
                }
                break;
            case GENERATE_DEFAULT_METAL_TYPES:
                if ((material.flags & this.bit) > 0) {
                    material.block(STORAGE);
                    material.items(INGOT, NUGGET);
                    material.flags |= GENERATE_DUST_VARIANTS.bit;
                }
                break;
            case GENERATE_DUST_VARIANTS:
                if ((material.flags & this.bit) > 0) {
                    material.items(DUST, SMALL_DUST, TINY_DUST);
                }
                break;
        }
    }

}
