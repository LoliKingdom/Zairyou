package zone.rong.zairyou.api.material;

import com.google.common.base.CaseFormat;
import it.unimi.dsi.fastutil.objects.Object2ObjectMap;
import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import it.unimi.dsi.fastutil.objects.ObjectOpenHashSet;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.util.EnumHelper;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import zone.rong.zairyou.Zairyou;
import zone.rong.zairyou.api.fluid.ExtendedFluid;
import zone.rong.zairyou.api.fluid.FluidType;
import zone.rong.zairyou.api.item.tool.ExtendedToolMaterial;
import zone.rong.zairyou.api.item.tool.MaterialTools;
import zone.rong.zairyou.api.material.type.BlockMaterialType;
import zone.rong.zairyou.api.material.type.IMaterialType;
import zone.rong.zairyou.api.material.type.ItemMaterialType;
import zone.rong.zairyou.api.ore.OreBlock;
import zone.rong.zairyou.api.ore.OreGrade;

import java.util.*;
import java.util.function.Consumer;
import java.util.function.UnaryOperator;

/**
 * Material. That's it.
 */
public class Material {

    public static final Object2ObjectMap<String, Material> REGISTRY = new Object2ObjectOpenHashMap<>();

    public static Material of(String name) {
        return of(name, 0x0);
    }

    public static Material of(String name, int colour) {
        if (REGISTRY.containsKey(name)) {
            throw new IllegalStateException(name + " has been registered already!");
        }
        return new Material(name, colour);
    }

    public static final Material NONE = of("none").tools(0, 0, 0F, 0F, 0F, 0);
    public static final Material BASIC = of("basic");

    private final String name, translationKey;
    private final int colour;
    private final Map<IMaterialType, ResourceLocation[]> textures;

    private EnumMap<BlockMaterialType, Block> typeBlocks;
    private EnumMap<ItemMaterialType, ItemStack> typeItems;
    private EnumMap<FluidType, Fluid> typeFluids;
    // private final TextureSet textureSet;

    private boolean hasTools = false;

    private Set<IMaterialType> disabledTint;
    private ExtendedToolMaterial toolMaterial;
    private MaterialTools tools;

    protected Material(String name, int colour) {
        this.name = name;
        this.translationKey = String.join(".", Zairyou.ID, "material", name, "name");
        this.colour = colour;
        this.textures = new Object2ObjectOpenHashMap<>();
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
            if (ore.endsWith("~") || (ore.endsWith("~&") && this == Material.BASIC)) {
                oreNames.add(stripped);
                continue;
            }
            if (ore.endsWith("*")) {
                oreNames.add(stripped);
            }
            oreNames.add(stripped.concat(toCamelString()));
        }
        return oreNames.toArray(new String[0]);
    }

    public String getTranslationKey() {
        return translationKey;
    }

    public int getColour() {
        return colour;
    }

    public boolean hasTint(IMaterialType type) {
        return this.disabledTint == null || !this.disabledTint.contains(type);
    }

    public boolean hasType(BlockMaterialType type) {
        return this.typeBlocks != null && this.typeBlocks.containsKey(type);
    }

    public boolean hasType(ItemMaterialType type) {
        return this.typeItems != null && this.typeItems.containsKey(type);
    }

    public Set<BlockMaterialType> getAllowedBlockTypes() {
        return this.typeBlocks == null ? Collections.emptySet() : this.typeBlocks.keySet();
    }

    public Set<ItemMaterialType> getAllowedItemTypes() {
        return this.typeItems == null ? Collections.emptySet() : this.typeItems.keySet();
    }

    public Map<BlockMaterialType, Block> getBlocks() {
        return this.typeBlocks == null ? Collections.emptyMap() : this.typeBlocks;
    }

    public Map<ItemMaterialType, ItemStack> getItems() {
        return this.typeItems == null ? Collections.emptyMap() : this.typeItems;
    }

    public Map<FluidType, Fluid> getFluids() {
        return this.typeFluids == null ? Collections.emptyMap() : this.typeFluids;
    }

    public ExtendedToolMaterial getToolMaterial() {
        return toolMaterial;
    }

    public MaterialTools getTools() {
        return tools;
    }

    public Block getBlock(BlockMaterialType type) {
        return this.typeBlocks == null ? null : this.typeBlocks.get(type);
    }

    public ItemStack getItem(ItemMaterialType type, boolean copy) {
        if (this.typeItems == null) {
            return ItemStack.EMPTY;
        }
        ItemStack stack = this.typeItems.get(type);
        if (stack == null) {
            return ItemStack.EMPTY;
        }
        return copy ? stack.copy() : stack;
    }

    public Fluid getFluid(FluidType type) {
        return this.typeFluids == null ? null : this.typeFluids.get(type);
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

    public ResourceLocation getTexture(IMaterialType type, int layer) {
        return this.textures.get(type)[layer];
    }

    public ResourceLocation[] getTextures(IMaterialType type) {
        return this.textures.get(type);
    }

    public boolean hasTools() {
        return hasTools;
    }

    @Deprecated
    public TextureSet getTextureSet() {
        return TextureSet.NONE;
    }

    public Material tools(Item.ToolMaterial toolMaterial, int attackSpeed) {
        this.hasTools = true;
        this.toolMaterial = new ExtendedToolMaterial(toolMaterial, attackSpeed);
        this.tools = new MaterialTools(this.toolMaterial);
        return this;
    }

    public Material tools(Item.ToolMaterial toolMaterial, int attackSpeed, UnaryOperator<MaterialTools> applicableTools) {
        this.hasTools = true;
        this.toolMaterial = new ExtendedToolMaterial(toolMaterial, attackSpeed);
        this.tools = applicableTools.apply(new MaterialTools(this.toolMaterial));
        return this;
    }

    public Material tools(int harvestLevel, int maxUses, float efficiency, float attackDamage, float attackSpeed, int enchantability) {
        this.hasTools = true;
        this.toolMaterial = new ExtendedToolMaterial(EnumHelper.addToolMaterial(name, harvestLevel, maxUses, efficiency, attackDamage, enchantability), attackSpeed);
        this.tools = new MaterialTools(this.toolMaterial).axe().hoe().pickaxe().shovel().sword();
        return this;
    }

    public Material tools(int harvestLevel, int maxUses, float efficiency, float attackDamage, float attackSpeed, int enchantability, UnaryOperator<MaterialTools> applicableTools) {
        this.hasTools = true;
        this.toolMaterial = new ExtendedToolMaterial(EnumHelper.addToolMaterial(name, harvestLevel, maxUses, efficiency, attackDamage, enchantability), attackSpeed);
        this.tools = applicableTools.apply(new MaterialTools(this.toolMaterial));
        return this;
    }

    // TODO: placeholder
    public Material ore() {
        if (this.typeBlocks == null) {
            this.typeBlocks = new EnumMap<>(BlockMaterialType.class);
        }
        this.typeBlocks.put(BlockMaterialType.ORE, new OreBlock(this, OreGrade.NORMAL));
        return this;
    }

    @Deprecated
    public Material type(BlockMaterialType type) {
        /*
        if (type == BlockMaterialType.ORE) {
            throw new UnsupportedOperationException("Please register " + type.toString() + " with the valid method.");
        }
         */
        if (this.typeBlocks == null) {
            this.typeBlocks = new EnumMap<>(BlockMaterialType.class);
        }
        this.typeBlocks.put(type, null);
        ResourceLocation[] locations = new ResourceLocation[type.getModelLayers()];
        this.textures.put(type, locations);
        for (int i = 0; i < type.getModelLayers(); i++) {
            locations[i] = new ResourceLocation(Zairyou.ID, String.join("/", "blocks", type.toString(), Integer.toString(i)));
        }
        return this;
    }

    @Deprecated
    public Material types(BlockMaterialType... types) {
        if (this.typeBlocks == null) {
            this.typeBlocks = new EnumMap<>(BlockMaterialType.class);
        }
        for (BlockMaterialType type : types) {
            type(type);
        }
        return this;
    }

    public Material type(ItemMaterialType type) {
        if (this.typeItems == null) {
            this.typeItems = new EnumMap<>(ItemMaterialType.class);
        }
        this.typeItems.put(type, null);
        ResourceLocation[] locations = new ResourceLocation[type.getModelLayers()];
        this.textures.put(type, locations);
        for (int i = 0; i < type.getModelLayers(); i++) {
            locations[i] = new ResourceLocation(Zairyou.ID, String.join("/", "items", type.toString(), Integer.toString(i)));
        }
        return this;
    }

    public Material types(ItemMaterialType... types) {
        for (ItemMaterialType type : types) {
            type(type);
        }
        return this;
    }

    public Material types(ItemMaterialType[] types, ItemMaterialType... moreTypes) {
        for (ItemMaterialType type : types) {
            type(type);
        }
        for (ItemMaterialType type : moreTypes) {
            type(type);
        }
        return this;
    }

    public Material fluid(FluidType type, Fluid fluid) {
        if (this.typeFluids == null) {
            this.typeFluids = new EnumMap<>(FluidType.class);
        }
        FluidRegistry.addBucketForFluid(fluid);
        this.typeFluids.put(type, fluid);
        return this;
    }

    public Material fluid(FluidType fluidType, UnaryOperator<ExtendedFluid.Builder> builderOperator) {
        return fluid(fluidType, builderOperator.apply(new ExtendedFluid.Builder(this, fluidType)).build());
    }

    public Material fluid(FluidType fluidType, UnaryOperator<ExtendedFluid.Builder> builderOperator, Consumer<Block> blockConsumer) {
        Fluid fluid = builderOperator.apply(new ExtendedFluid.Builder(this, fluidType)).build();
        blockConsumer.accept(fluid.getBlock());
        return fluid(fluidType, fluid);
    }

    public Material texture(IMaterialType type, ResourceLocation location, int layer) {
        this.textures.get(type)[layer] = location;
        return this;
    }

    public Material texture(IMaterialType type, String domain, String id, int layer) {
        return texture(type, new ResourceLocation(domain, id), layer);
    }

    public Material texture(IMaterialType type, String id, int layer) {
        return texture(type, new ResourceLocation(Zairyou.ID, id), layer);
    }

    public Material noTint(ItemMaterialType type) {
        if (this.disabledTint == null) {
            this.disabledTint = new ObjectOpenHashSet<>();
        }
        this.disabledTint.add(type);
        return this;
    }

    public Material noTints(ItemMaterialType... types) {
        if (this.disabledTint == null) {
            this.disabledTint = new ObjectOpenHashSet<>();
        }
        Collections.addAll(this.disabledTint, types);
        return this;
    }

    public Block setBlock(BlockMaterialType type, Block block) {
        this.typeBlocks.put(type, block);
        return block;
    }

    public ItemStack setItem(ItemMaterialType type, ItemStack stack) {
        this.typeItems.put(type, stack);
        return stack;
    }

    public Item setItem(ItemMaterialType type, Item item, int meta) {
        this.typeItems.put(type, new ItemStack(item, 1, meta));
        return item;
    }

    public Item setItem(ItemMaterialType type, Item item) {
        this.typeItems.put(type, new ItemStack(item));
        return item;
    }

    public String toCamelString() {
        return CaseFormat.UPPER_UNDERSCORE.to(CaseFormat.UPPER_CAMEL, this.name);
    }

}
