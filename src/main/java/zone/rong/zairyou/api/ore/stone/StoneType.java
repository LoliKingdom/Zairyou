package zone.rong.zairyou.api.ore;

import net.minecraft.util.IStringSerializable;
import net.minecraft.util.ResourceLocation;
import zone.rong.zairyou.Zairyou;
import zone.rong.zairyou.api.material.Material;

import java.util.Locale;

public enum StoneType implements IStringSerializable {

    ANDESITE("tfc", new ResourceLocation(Zairyou.ID, "blocks/ore/andesite"), "", "stone", "andesite");

    private final String modId;
    private final Material backingMaterial;
    private final ResourceLocation baseTexture, topTexture;
    private final String[] prefixes;

    StoneType(String modId, ResourceLocation baseTexture, String... prefixes) {
        this.modId = modId;
        this.backingMaterial = Material.of(toString());
        this.baseTexture = baseTexture;
        this.topTexture = baseTexture;
        this.prefixes = prefixes;
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
