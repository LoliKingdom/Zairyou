package zone.rong.zairyou.api.material;

public enum MaterialFlag {

    GENERATE_DUST_VARIANTS,
    GENERATE_DEFAULT_METAL_TYPES;

    final long bit;

    MaterialFlag() {
        this.bit = 1L << ordinal();
    }

}
