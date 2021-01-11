package zone.rong.zairyou.api.material.type;

import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.ModContainer;
import zone.rong.zairyou.Zairyou;

import java.util.Locale;

import static zone.rong.zairyou.api.util.Util.M;

public enum ItemMaterialType implements IMaterialType {

    COAL("minecraft", 1, M, "gem", "coal~"),
    DUST("minecraft", 1, M, "dust"),
    GEM("minecraft", 1, M, "gem"),
    INGOT("minecraft", 1, M, "ingot"),
    NUGGET("minecraft", 1, M / 9, "nugget"),
    ROD("minecraft", 1, M / 2, "rod", "stick"),

    DOUBLE_INGOT("gregtech", 1, M * 2, "ingotDouble"),
    GEAR("gregtech", 1, M * 4, "gear"),
    HOT_INGOT("gregtech", 1, M, "ingotHot"),
    LENS("gregtech", 2, (M * 3) / 4, "lens"),
    RING("gregtech", 1, M / 4, "ring"),

    AXE_HEAD("gregtech", 2, M * 3, "toolHeadAxe*"),
    BUZZSAW_BLADE("gregtech", 2, M * 4, "toolHeadBuzzSaw*"),
    CHAINSAW_TIP("gregtech", 2, M * 2, "toolHeadChainsaw*"),
    DRILL_BIT("gregtech", 2, M * 4, "toolHeadDrill*"),
    FILE_HEAD("gregtech", 2, M * 2, "toolHeadFile*"),
    HAMMER_HEAD("gregtech", 2, M * 6, "toolHeadHammer*"),
    HOE_BLADE("gregtech", 2, M * 2, "toolHeadHoe*"),
    PICKAXE_HEAD("gregtech", 2, M * 3, "toolHeadPickaxe*"),
    SAW_BLADE("gregtech", 2, M * 2, "toolHeadSaw*"),
    SICKLE_BLADE("gregtech", 2, M * 3, "toolHeadSickle*"),
    SCREWDRIVER_BIT("gregtech", 2, M, "toolHeadScrewdriver*"),
    SHOVEL_HEAD("gregtech", 2, M, "toolHeadShovel*"),
    SWORD_BLADE("gregtech", 2, M * 2, "toolHeadSword*"),
    WRENCH_TIP("gregtech", 2, M * 4, "toolHeadWrench*"),

    CHIPPED_GEM("tfc", 1, M / 4, "chipped", "gemChipped*"),
    FLAWED_GEM("tfc", 1, M / 2, "chipped", "gemChipped*"),
    FLAWLESS_GEM("tfc", 1, M * 2, "chipped", "gemChipped*"),
    EXQUISITE_GEM("tfc", 1, M * 4, "exquisite", "gemExquisite*"),

    CRUSHED(Zairyou.ID, 1, -1, "crushed"),
    CENTRIFUGED_CRUSHED(Zairyou.ID, 1, -1, "crushedCentrifuged"),
    PURIFIED_CRUSHED(Zairyou.ID, 1, -1, "crushedPurified"),

    SMALL_DUST(Zairyou.ID, 1, M / 4, "dustSmall"),
    TINY_DUST(Zairyou.ID, 1, M / 9, "dustTiny"),

    // DENSE_INGOT(Zairyou.ID, 1, M * 9, "ingotDense"),

    PLATE(Zairyou.ID, 1, M, "plate"),
    DOUBLE_PLATE(Zairyou.ID, 1, M * 2, "plateDouble"),
    DENSE_PLATE(Zairyou.ID, 1, M * 9, "plateDense"),

    FOIL(Zairyou.ID, 1, M / 4, "foil"),

    BAIT("thermalfoundation", 1, -1, "bait"),
    COIL("thermalfoundation", 2, M * 2, "coil"),
    CRYSTAL("thermalfoundation", 2, M, "crystal"),
    FERTILIZER("thermalfoundation", 1, -1, "fertilizer"),
    // GLOB("thermalfoundation", 1, "glob"), BasicItem
    // SERVO("thermalfoundation", 1, "servo"), BasicItem
    SLAG("thermalfoundation", 1, -1, "slag~&", "crystalSlag~&", "itemSlag~&");

    // public static final ItemMaterialType[] VALUES = values();

    private final String modId, modName;
    private final int modelLayers;
    private final long materialAmount;
    private final String[] prefixes;

    ItemMaterialType(String modId, int modelLayers, long materialAmount, String... alternatePrefixes) {
        this.modId = modId;
        ModContainer mod = Loader.instance().getIndexedModList().get(modId);
        this.modName = mod == null ? Zairyou.NAME : mod.getName();
        this.modelLayers = modelLayers;
        this.materialAmount = materialAmount;
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
