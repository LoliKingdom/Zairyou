package zone.rong.zairyou.api.material;

import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
import net.minecraft.util.ResourceLocation;
import zone.rong.zairyou.Zairyou;
import zone.rong.zairyou.api.material.type.BlockMaterialType;
import zone.rong.zairyou.api.material.type.IMaterialType;
import zone.rong.zairyou.api.material.type.ItemMaterialType;

import java.util.Locale;
import java.util.Map;
import java.util.function.BiFunction;

public enum TextureSet {

    DULL, // e.g = Vanilla Iron Ingot
    METALLIC, // e.g = Vanilla Copper Ingot (1.17)
    SHINY, // e.g = Vanilla Gold Ingot
    ROUGH, // e.g = Vanilla Amethyst Shard (1.17 - ?)
    // MAGNETIC, // e.g = Magnetic (+ -) overlay -> change to material property
    DIAMOND, // e.g = Vanilla Diamond
    RUBY, // e.g = Vanilla Ruby Gem (unused)
    LAPIS, // e.g = Vanilla Lapis
    EMERALD, // e.g = Vanilla Emerald
    QUARTZ, // e.g = Vanilla Quartz
    FINE, // e.g = Vanilla Diamond
    FLINT, // e.g = Vanilla Flint
    COAL; // e.g = Vanilla Coal

    static Map<IMaterialType, ResourceLocation[]> cachedLocations = new Object2ObjectOpenHashMap<>();

    static final BiFunction<BlockMaterialType, TextureSet, String> blockLocation = (b, t) -> String.join("/", "blocks", b.toString(), t.toString());
    static final BiFunction<ItemMaterialType, TextureSet, String> itemLocation = (i, t) -> String.join("/", "items", i.toString(), t.toString());

    public ResourceLocation[] getTextureLocations(BlockMaterialType blockMaterialType) {
        ResourceLocation[] locations = cachedLocations.get(blockMaterialType);
        if (locations == null) {
            String pre = blockLocation.apply(blockMaterialType, this);
            locations = new ResourceLocation[blockMaterialType.getModelLayers()];
            for (int i = 0; i < blockMaterialType.getModelLayers(); i++) {
                locations[i] = new ResourceLocation(Zairyou.ID, pre + "_" + i);
            }
        }
        return locations;
    }

    public ResourceLocation[] getTextureLocations(ItemMaterialType itemMaterialType) {
        ResourceLocation[] locations = cachedLocations.get(itemMaterialType);
        if (locations == null) {
            String pre = itemLocation.apply(itemMaterialType, this);
            locations = new ResourceLocation[itemMaterialType.getModelLayers()];
            for (int i = 0; i < itemMaterialType.getModelLayers(); i++) {
                locations[i] = new ResourceLocation(Zairyou.ID, pre + "_" + i);
            }
        }
        return locations;
    }

    @Override
    public String toString() {
        return this.name().toLowerCase(Locale.ROOT);
    }

}
