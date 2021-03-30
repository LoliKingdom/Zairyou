package zone.rong.zairyou.api.ore.stone;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import net.dries007.tfc.TerraFirmaCraft;
import net.dries007.tfc.api.registries.TFCRegistries;
import net.dries007.tfc.api.types.Rock;
import net.dries007.tfc.types.DefaultRocks;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.ResourceLocation;
import zone.rong.zairyou.Zairyou;
import zone.rong.zairyou.api.material.Material;
import zone.rong.zairyou.api.util.ITranslatable;

import java.util.Locale;

import static zone.rong.zairyou.api.material.Material.*;

public enum StoneType implements IStringSerializable, ITranslatable {

    ANDESITE("tfc", DefaultRocks.ANDESITE, "", "stone", "andesite"),
    BASALT("tfc", DefaultRocks.BASALT, "", "stone", "basalt"),
    CHALK("tfc", DefaultRocks.CHALK, "", "stone", "chalk"),
    CHERT("tfc", DefaultRocks.CHERT, "", "stone", "chert"),
    CLAYSTONE("tfc", DefaultRocks.CLAYSTONE, "", "stone", "claystone"),
    CONGLOMERATE("tfc", DefaultRocks.CONGLOMERATE, "", "stone", "conglomerate"),
    DACITE("tfc", DefaultRocks.DACITE, "", "stone", "dacite"),
    DIORITE("tfc", DefaultRocks.DIORITE, "", "stone", "diorite"),
    DOLOMITE("tfc", DefaultRocks.DOLOMITE, "", "stone", "dolomite"),
    GABBRO("tfc", DefaultRocks.GABBRO, "", "stone", "gabbro"),
    GNEISS("tfc", DefaultRocks.GNEISS, "", "stone", "gneiss"),
    GRANITE("tfc", DefaultRocks.GRANITE, "", "stone", "granite"),
    LIMESTONE("tfc", DefaultRocks.LIMESTONE, "", "stone", "limestone"),
    MARBLE("tfc", DefaultRocks.MARBLE, "", "stone", "marble"),
    PHYLLITE("tfc", DefaultRocks.PHYLLITE, "", "stone", "phyllite"),
    QUARTZITE("tfc", DefaultRocks.QUARTZITE, "", "stone", "quartzite"),
    RHYOLITE("tfc", DefaultRocks.RHYOLITE, "", "stone", "rhyolite"),
    ROCKSALT("tfc", DefaultRocks.ROCKSALT, "", "stone", "rocksalt"),
    SCHIST("tfc", DefaultRocks.SCHIST, "", "stone", "schist"),
    SHALE("tfc", DefaultRocks.SHALE, "", "stone", "shale"),
    SLATE("tfc", DefaultRocks.SLATE, "", "stone", "slate");

    public static final StoneType[] TFC_ROCKS;
    public static final StoneType[] VALUES;

    private static final BiMap<StoneType, Rock> LOOKUP;

    static {
        TFC_ROCKS = new StoneType[20];
        VALUES = values();
        System.arraycopy(VALUES, 0, TFC_ROCKS, 0, 20);
        LOOKUP = HashBiMap.create(TFC_ROCKS.length);
    }

    public static Rock getRockFromType(StoneType stoneType) {
        Rock rock = LOOKUP.get(stoneType);
        if (rock == null) {
            rock = stoneType.getRock();
            LOOKUP.put(stoneType, rock);
        }
        return rock;
    }

    public static StoneType getTypeFromRock(Rock rock) {
        StoneType type = LOOKUP.inverse().get(rock);
        if (type == null) {
            for (StoneType stoneType : VALUES) {
                LOOKUP.put(stoneType, stoneType.getRock());
                if (stoneType.getRock() == rock) {
                    type = stoneType;
                }
            }
        }
        return type;
    }

    private final String modId;
    private final Material backingMaterial;
    private final ResourceLocation rockLocation;
    private final String[] prefixes;

    private Rock rock = null;

    StoneType(String modId, ResourceLocation rockLocation, String... prefixes) {
        this.modId = modId;
        this.backingMaterial = of(toString());
        this.rockLocation = rockLocation;
        this.prefixes = prefixes;
    }

    public ResourceLocation getTypeLocation(Rock.Type type) {
        return new ResourceLocation(TerraFirmaCraft.MOD_ID, String.join("/", "blocks", "stonetypes", type.name(), name()));
    }

    public String getModId() {
        return modId;
    }

    public Material getMaterial() {
        return backingMaterial;
    }

    public ResourceLocation getRockLocation() {
        return rockLocation;
    }

    public Rock getRock() {
        if (rock == null) {
            rock = TFCRegistries.ROCKS.getValue(rockLocation);
        }
        return rock;
    }

    @Override
    public String getTranslationKey() {
        return String.join(".", modId, "stone_type", toString(), "name");
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
