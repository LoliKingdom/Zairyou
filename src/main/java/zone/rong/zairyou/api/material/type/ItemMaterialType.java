package zone.rong.zairyou.api.material.type;

import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.ModContainer;
import zone.rong.zairyou.Zairyou;

import java.util.Locale;

public enum ItemMaterialType implements IMaterialType {

    COAL(Zairyou.ID, 1, "gem", "coal~"),

    CRUSHED(Zairyou.ID, 1, "crushed"),
    CENTRIFUGED_CRUSHED(Zairyou.ID, 1, "crushedCentrifuged"),
    PURIFIED_CRUSHED(Zairyou.ID, 1, "crushedPurified"),

    DUST(Zairyou.ID, 1, "dust"),
    SMALL_DUST(Zairyou.ID, 1, "dustSmall"),
    TINY_DUST(Zairyou.ID, 1, "dustTiny"),

    INGOT(Zairyou.ID, 1, "ingot"),
    HOT_INGOT(Zairyou.ID, 1, "ingotHot"),
    DOUBLE_INGOT(Zairyou.ID, 1, "ingotDouble"),
    DENSE_INGOT(Zairyou.ID, 1, "ingotDense"),

    CHIPPED_GEM(Zairyou.ID, 1, "chipped", "gemChipped"),
    GEM(Zairyou.ID, 1, "gem"),
    EXQUISITE_GEM(Zairyou.ID, 1, "exquisite", "gemExquisite"),

    PLATE(Zairyou.ID, 1, "plate"),
    DOUBLE_PLATE(Zairyou.ID, 1, "plateDouble"),
    DENSE_PLATE(Zairyou.ID, 1, "plateDense"),

    NUGGET(Zairyou.ID, 1, "nugget"),

    ROD(Zairyou.ID, 1, "rod", "stick"),
    LONG_ROD(Zairyou.ID, 1, "rodLong", "stickLong"),

    FOIL(Zairyou.ID, 1, "foil"),

    SAW_BLADE(Zairyou.ID, 2, "toolHeadSaw"),

    BUZZSAW_BLADE("gregtech", 2, "toolHeadBuzzSaw"),

    BAIT("thermalfoundation", 1, "bait"),
    COIL("thermalfoundation", 2, "coil"),
    CRYSTAL("thermalfoundation", 2, "crystal"),
    FERTILIZER("thermalfoundation", 1, "fertilizer"),
    GLOB("thermalfoundation", 1, "glob"),
    SERVO("thermalfoundation", 1, "servo"),
    SLAG("thermalfoundation", 1, "slag~&", "crystalSlag~&", "itemSlag~&");

    // public static final ItemMaterialType[] VALUES = values();

    private final String modId, modName;
    private final int modelLayers;
    private final String[] prefixes;

    ItemMaterialType(String modId, int modelLayers, String... alternatePrefixes) {
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
