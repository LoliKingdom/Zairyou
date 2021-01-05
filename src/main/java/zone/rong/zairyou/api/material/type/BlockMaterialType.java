package zone.rong.zairyou.api.material.type;

import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.ModContainer;
import zone.rong.zairyou.Zairyou;

import java.util.Locale;

public enum BlockMaterialType implements IMaterialType {

    ORE(Zairyou.ID, 0, "ore");

    private final String modId, modName;
    private final int modelLayers;
    private final String[] prefixes;

    BlockMaterialType(String modId, int modelLayers, String... alternatePrefixes) {
        this.modId = modId;
        ModContainer mod = Loader.instance().getIndexedModList().get(modId);
        this.modName = mod == null ? Zairyou.NAME : mod.getName();
        this.modelLayers = modelLayers;
        this.prefixes = alternatePrefixes;
    }

    @Override
    public String getModID() {
        return modId;
    }

    @Override
    public String getModName() {
        return modName;
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
