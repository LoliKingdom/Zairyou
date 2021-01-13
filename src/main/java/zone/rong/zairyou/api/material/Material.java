package zone.rong.zairyou.api.material;

import com.google.common.base.CaseFormat;
import it.unimi.dsi.fastutil.objects.*;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;
import org.apache.commons.lang3.tuple.Pair;
import zone.rong.zairyou.Zairyou;
import zone.rong.zairyou.api.fluid.FluidType;
import zone.rong.zairyou.api.item.MaterialItem;
import zone.rong.zairyou.api.material.type.BlockMaterialType;
import zone.rong.zairyou.api.material.type.IMaterialType;
import zone.rong.zairyou.api.material.type.ItemMaterialType;
import zone.rong.zairyou.api.util.Util;

import javax.annotation.Nullable;
import java.util.*;
import java.util.function.BiConsumer;
import java.util.function.Function;

/**
 * Material. That's it.
 */
public class Material {

    public static final Object2ObjectMap<String, Material> REGISTRY = new Object2ObjectOpenHashMap<>();

    public static Material get(String name) {
        return REGISTRY.getOrDefault(name, NONE);
    }

    public static ObjectCollection<Material> all() {
        return REGISTRY.values();
    }

    public static void all(BiConsumer<String, Material> consumer) {
        REGISTRY.forEach(consumer);
    }

    public static final Material NONE = MaterialBuilder.of("none").build();

    private final String name, translationKey, chemicalFormula;
    private final int colour;
    private final long flags;

    private final EnumMap<BlockMaterialType, Block> blocks;
    private final EnumMap<BlockMaterialType, ResourceLocation[]> blockTextures;

    private final EnumMap<ItemMaterialType, ItemStack> items;
    private final EnumMap<ItemMaterialType, ResourceLocation[]> itemTextures;

    private final EnumMap<FluidType, Fluid> fluids;

    private final Set<IMaterialType> disabledTint;

    // private final ExtendedToolMaterial toolMaterial;
    // private final MaterialTools tools;

    private final Map<Class<?>, AbstractMaterialBuilderAppender.Viewer<?>> viewers;

    protected Material(String name,
                       int colour,
                       String chemicalFormula,
                       long flags,
                       Map<BlockMaterialType, Function<Material, Block>> blocks,
                       Map<BlockMaterialType, ResourceLocation[]> blockTextures,
                       Map<ItemMaterialType, ItemStack> items,
                       Map<ItemMaterialType, ResourceLocation[]> itemTextures,
                       Map<FluidType, Function<Material, Fluid>> fluids,
                       Set<IMaterialType> disabledTint,
                       Map<Class<?>, AbstractMaterialBuilderAppender.Viewer<?>> viewers) {
        this.name = name;
        this.translationKey = String.join(".", Zairyou.ID, "material", name, "name");
        this.colour = colour;
        this.flags = flags;
        this.chemicalFormula = chemicalFormula;
        this.blocks = blocks == null ? null : Util.keepEnumMapAndConvertValues(BlockMaterialType.class, blocks, k -> k, e -> e.getValue().apply(this));
        this.blockTextures = blockTextures == null ? null : Util.keepEnumMap(blockTextures, k -> k, e -> e);
        this.items = items == null ? null : Util.keepEnumMap(items, k -> k, e -> e);
        this.itemTextures = itemTextures == null ? null : Util.keepEnumMap(itemTextures, k -> k, e -> e);
        this.fluids = fluids == null ? null : Util.keepEnumMapAndConvertValues(FluidType.class, fluids, k -> k, e -> e.getValue().apply(this));
        this.disabledTint = disabledTint;
        this.viewers = viewers;
        REGISTRY.put(name, this);
    }

    public String getName() {
        return name;
    }

    public String[] getOreNames(ItemMaterialType type) {
        final String[] typeOreNames = type.getPrefixes();
        List<String> oreNames = new ObjectArrayList<>();
        for (final String ore : typeOreNames) {
            final String stripped = ore.replaceAll("\\p{Punct}", "");
            if (ore.endsWith("~") || (ore.endsWith("~&") && this.name.equals("basic"))) {
                oreNames.add(stripped);
                continue;
            }
            if (ore.endsWith("*")) {
                oreNames.add(stripped);
            }
            oreNames.add(stripped.concat(toCamelString()));
        }
        return oreNames.toArray(new String[typeOreNames.length]);
    }

    public String getTranslationKey() {
        return translationKey;
    }

    public int getColour() {
        return colour;
    }

    public String getChemicalFormula() {
        return chemicalFormula;
    }

    @SuppressWarnings("unchecked")
    public <T extends AbstractMaterialBuilderAppender.Viewer<?>> T view(Class<T> clazz) {
        return (T) this.viewers.get(clazz);
    }

    public boolean hasTint(IMaterialType type) {
        return this.disabledTint == null || !this.disabledTint.contains(type);
    }

    public boolean hasType(BlockMaterialType type) {
        return this.blocks != null && this.blocks.containsKey(type);
    }

    public boolean hasType(ItemMaterialType type) {
        return this.items != null && this.items.containsKey(type);
    }

    public boolean hasFlag(MaterialFlag flag) {
        return (this.flags & flag.bit) > 0;
    }

    public Map<BlockMaterialType, Block> getBlocks() {
        return this.blocks == null ? Collections.emptyMap() : this.blocks;
    }

    public Map<ItemMaterialType, ItemStack> getItems() {
        return this.items == null ? Collections.emptyMap() : this.items;
    }

    public Map<FluidType, Fluid> getFluids() {
        return this.fluids == null ? Collections.emptyMap() : this.fluids;
    }

    /*
    public ExtendedToolMaterial getToolMaterial() {
        return toolMaterial;
    }

    public MaterialTools getTools() {
        return tools;
    }
     */

    public Block getBlock(BlockMaterialType type) {
        return this.blocks == null ? null : this.blocks.get(type);
    }

    public ItemStack getItem(ItemMaterialType type, boolean copy) {
        if (this.items == null) {
            return ItemStack.EMPTY;
        }
        ItemStack stack = this.items.get(type);
        if (stack == null || stack.isEmpty()) {
            return ItemStack.EMPTY;
        }
        return copy ? stack.copy() : stack;
    }

    @Nullable
    public MaterialItem getItem(ItemMaterialType type) {
        Item item = getItem(type, false).getItem();
        if (item instanceof MaterialItem) {
            return (MaterialItem) item;
        }
        return null;
    }

    public Fluid getFluid(FluidType type) {
        return this.fluids == null ? null : this.fluids.get(type);
    }

    public ItemStack getStack(BlockMaterialType type, int count) {
        Block block = getBlock(type);
        if (block == null) {
            return ItemStack.EMPTY;
        }
        return new ItemStack(block, count);
    }

    public ItemStack getStack(ItemMaterialType type, int count) {
        ItemStack stack = getItem(type, true);
        stack.setCount(count);
        return stack;
    }

    public FluidStack getStack(FluidType type, int amount) {
        return new FluidStack(getFluid(type), amount);
    }

    public ResourceLocation getTexture(ItemMaterialType type, int layer) {
        return this.itemTextures.get(type)[layer];
    }

    public ResourceLocation getTexture(BlockMaterialType type, int layer) {
        return this.blockTextures.get(type)[layer];
    }

    public ResourceLocation[] getTextures(ItemMaterialType type) {
        return this.itemTextures.get(type);
    }

    public ResourceLocation[] getTextures(BlockMaterialType type) {
        return this.blockTextures.get(type);
    }

    public Block setBlock(BlockMaterialType type, Block block) {
        this.blocks.put(type, block);
        return block;
    }

    public void setItem(ItemMaterialType type, ItemStack stack) {
        this.items.put(type, stack);
    }

    public void setItem(ItemMaterialType type, Item item, int meta) {
        this.items.put(type, new ItemStack(item, 1, meta));
    }

    public void setItem(ItemMaterialType type, Item item) {
        this.items.put(type, new ItemStack(item));
    }

    public String toCamelString() {
        return CaseFormat.UPPER_UNDERSCORE.to(CaseFormat.UPPER_CAMEL, this.name);
    }

}
