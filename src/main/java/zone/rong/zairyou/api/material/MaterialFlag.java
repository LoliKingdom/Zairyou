package zone.rong.zairyou.api.material;

public enum MaterialFlag {

    METAL,
    GENERATE_DUST_VARIANTS,
    GENERATE_DEFAULT_METAL_TYPES,
    NEEDS_BLAST_FURNACE;

    public final long bit;

    MaterialFlag() {
        this.bit = 1L << ordinal();
    }

}
