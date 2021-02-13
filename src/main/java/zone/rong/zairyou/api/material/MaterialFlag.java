package zone.rong.zairyou.api.material;

import static zone.rong.zairyou.api.material.type.BlockMaterialType.*;
import static zone.rong.zairyou.api.material.type.ItemMaterialType.*;

public enum MaterialFlag {

    METAL,
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
