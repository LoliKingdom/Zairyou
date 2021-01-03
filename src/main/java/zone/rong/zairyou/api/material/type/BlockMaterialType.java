package zone.rong.zairyou.api.material.type;

import zone.rong.zairyou.Zairyou;

import java.util.Locale;

public enum BlockMaterialType implements IMaterialType {

    ORE(Zairyou.ID, 0, "ore");

    private final String modId;
    private final int modelLayers;
    private final String[] prefixes;

    BlockMaterialType(String modId, int modelLayers, String... alternatePrefixes) {
        this.modId = modId;
        this.modelLayers = modelLayers;
        this.prefixes = alternatePrefixes;
    }

    @Override
    public String getModID() {
        return modId;
    }

    @Override
    public int getModelLayers() {
        return modelLayers;
    }

    @Override
    public String[] getPrefixes() {
        return prefixes;
    }

    @Override
    public String toString() {
        return this.name().toLowerCase(Locale.ROOT);
    }

}
