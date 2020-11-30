package zone.rong.zairyou.api.material;

import java.util.Locale;

@Deprecated
public enum TextureSet {

    NONE,
    DULL,
    METALLIC,
    SHINY,
    ROUGH,
    MAGNETIC,
    DIAMOND,
    RUBY,
    LAPIS,
    GEM_H,
    GEM_V,
    QUARTZ,
    FINE,
    FLINT,
    LIGNITE;

    @Override
    public String toString() {
        return this.name().toLowerCase(Locale.ROOT);
    }

}
