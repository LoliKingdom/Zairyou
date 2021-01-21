package zone.rong.zairyou.api.material;

import com.google.common.base.CaseFormat;
import it.unimi.dsi.fastutil.objects.*;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.util.EnumHelper;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;
import zone.rong.zairyou.Zairyou;
import zone.rong.zairyou.api.fluid.ExtendedFluid;
import zone.rong.zairyou.api.fluid.FluidType;
import zone.rong.zairyou.api.item.MaterialItem;
import zone.rong.zairyou.api.item.tool.ExtendedToolMaterial;
import zone.rong.zairyou.api.item.tool.MaterialTools;
import zone.rong.zairyou.api.material.element.Element;
import zone.rong.zairyou.api.material.element.FormulaBuilder;
import zone.rong.zairyou.api.material.type.BlockMaterialType;
import zone.rong.zairyou.api.material.type.IMaterialType;
import zone.rong.zairyou.api.material.type.ItemMaterialType;
import zone.rong.zairyou.api.ore.OreBlock;
import zone.rong.zairyou.api.ore.OreGrade;

import javax.annotation.Nullable;
import java.util.*;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.UnaryOperator;

import static zone.rong.zairyou.api.material.MaterialFlag.GENERATE_DEFAULT_METAL_TYPES;
import static zone.rong.zairyou.api.material.MaterialFlag.GENERATE_DUST_VARIANTS;
import static zone.rong.zairyou.api.material.type.ItemMaterialType.*;

/**
 * Material. That's it.
 */
public class Material {

    private static final Object2ObjectMap<String, Material> REGISTRY = new Object2ObjectOpenHashMap<>();
    private static final Object2ObjectMap<Class<?>, Function<Material, ?>> APPENDER_REGISTRY = new Object2ObjectOpenHashMap<>();

    public static <T extends IMaterialAppender<T>> void registerAppender(Class<T> clazz, Function<Material, T> construct) {
        APPENDER_REGISTRY.put(clazz, construct);
    }

    public static Material get(String name) {
        return REGISTRY.getOrDefault(name, NONE);
    }

    public static ObjectCollection<Material> all() {
        return REGISTRY.values();
    }

    public static void all(BiConsumer<String, Material> consumer) {
        REGISTRY.forEach(consumer);
    }

    public static void all(Consumer<Material> consumer) {
        all().forEach(consumer);
    }

    public static Material of(String name, int colour) {
        return new Material(name, colour, null);
    }

    public static Material of(String name, int colour, Element element) {
        return new Material(name, colour, element);
    }

    public static Material of(String name) {
        return new Material(name, 0x0, null);
    }

    public static final Material NONE = of("none");
    private static boolean frozen = false;

    protected final String name, translationKey;
    protected final Element baseElement;
    protected final int colour;

    protected long flags = 0L;
    protected String chemicalFormula = "";

    protected Object2ObjectMap<Class<?>, IMaterialAppender<?>> appenders;

    protected List<UnaryOperator<Material>> delegatingCode;

    protected EnumMap<BlockMaterialType, Block> blocks;
    protected EnumMap<BlockMaterialType, ResourceLocation[]> blockTextures;

    protected EnumMap<ItemMaterialType, ItemStack> items;
    protected EnumMap<ItemMaterialType, ResourceLocation[]> itemTextures;

    protected EnumMap<FluidType, Fluid> fluids;

    protected ExtendedToolMaterial toolMaterial;

    protected Set<IMaterialType> disabledTint;

    private Material(String name, int colour, Element element) {
        this.name = name;
        this.translationKey = String.join(".", Zairyou.ID, "material", name, "name");
        this.colour = colour;
        this.baseElement = element;
        if (this.baseElement != null) {
            this.formula(this.baseElement);
        }
        REGISTRY.put(name, this);
    }

    public <T extends IMaterialAppender<T>> T cast(Class<T> clazz) {
        if (this.appenders == null) {
            this.appenders = new Object2ObjectOpenHashMap<>();
        }
        T appender = (T) this.appenders.get(clazz);
        if (appender == null) {
            this.appenders.put(clazz, appender = (T) APPENDER_REGISTRY.get(clazz).apply(this));
        }
        return appender;
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

    public Material formula(String formula) {
        this.chemicalFormula = formula;
        return this;
    }

    public Material formula(UnaryOperator<FormulaBuilder> builder) {
        return formula(builder.apply(FormulaBuilder.of()).build());
    }

    public Material formula(Element element, int atoms) {
        return formula(FormulaBuilder.of().element(element, atoms).build());
    }

    public Material formula(Element element) {
        return formula(FormulaBuilder.of().element(element).build());
    }

    // TODO: placeholder
    public Material ore() {
        if (this.blocks == null) {
            this.blocks = new EnumMap<>(BlockMaterialType.class);
        }
        this.blocks.put(BlockMaterialType.ORE, new OreBlock(this, OreGrade.NORMAL));
        return this;
    }

    public Material items(ItemMaterialType... itemMaterialTypes) {
        if (itemMaterialTypes.length == 0) {
            throw new IllegalArgumentException("No ItemMaterialTypes specified for " + this.name + " material!");
        }
        if (this.items == null) {
            this.items = new EnumMap<>(ItemMaterialType.class);
            this.itemTextures = new EnumMap<>(ItemMaterialType.class);
        }
        for (ItemMaterialType itemMaterialType : itemMaterialTypes) {
            this.items.put(itemMaterialType, ItemStack.EMPTY);
            this.itemTextures.put(itemMaterialType, TextureSet.DULL.getTextureLocations(itemMaterialType));
        }
        return this;
    }

    public Material tools(Item.ToolMaterial toolMaterial, int attackSpeed) {
        this.toolMaterial = new ExtendedToolMaterial(toolMaterial, attackSpeed);
        // this.tools = new MaterialTools(this.toolMaterial);
        return this;
    }

    public Material tools(Item.ToolMaterial toolMaterial, int attackSpeed, UnaryOperator<MaterialTools> applicableTools) {
        this.toolMaterial = new ExtendedToolMaterial(toolMaterial, attackSpeed);
        // this.tools = applicableTools.apply(new MaterialTools(this.toolMaterial));
        return this;
    }

    public Material tools(int harvestLevel, int maxUses, float efficiency, float attackDamage, float attackSpeed, int enchantability) {
        this.toolMaterial = new ExtendedToolMaterial(EnumHelper.addToolMaterial(name, harvestLevel, maxUses, efficiency, attackDamage, enchantability), attackSpeed);
        // this.tools = new MaterialTools(this.toolMaterial).axe().hoe().pickaxe().shovel().sword();
        return this;
    }

    public Material tools(int harvestLevel, int maxUses, float efficiency, float attackDamage, float attackSpeed, int enchantability, UnaryOperator<MaterialTools> applicableTools) {
        this.toolMaterial = new ExtendedToolMaterial(EnumHelper.addToolMaterial(name, harvestLevel, maxUses, efficiency, attackDamage, enchantability), attackSpeed);
        // this.tools = applicableTools.apply(new MaterialTools(this.toolMaterial));
        return this;
    }

    public Material provideFluid(FluidType type, Fluid fluid) {
        if (this.fluids == null) {
            this.fluids = new EnumMap<>(FluidType.class);
        }
        /*
        if (bucket) {
            FluidRegistry.addBucketForFluid(fluid);
        }
         */
        this.fluids.put(type, fluid);
        return this;
    }

    public Material fluid(FluidType fluidType, UnaryOperator<ExtendedFluid.Builder> builderOperator) {
        Fluid fluid = builderOperator.apply(new ExtendedFluid.Builder(this, fluidType)).build();
        return provideFluid(fluidType, fluid);
    }

    public Material tex(ItemMaterialType itemMaterialType, ResourceLocation location, int layer) {
        if (!frozen) {
            createDelegate(m -> m.tex(itemMaterialType, location, layer));
            return this;
        }
        if (this.itemTextures == null || !this.itemTextures.containsKey(itemMaterialType)) {
            throw new IllegalStateException("ItemMaterialType not found for " + this.name + ", cannot change the texture of its item.");
        }
        ResourceLocation[] locations = this.itemTextures.get(itemMaterialType);
        if (locations.length <= layer) {
            throw new IllegalStateException(itemMaterialType + " does not have layer " + layer);
        }
        locations[layer] = location;
        return this;
    }

    public Material tex(ItemMaterialType itemMaterialType, String location, int layer) {
        int colonIndex = location.indexOf(':');
        if (colonIndex == -1) {
            return tex(itemMaterialType, new ResourceLocation(Zairyou.ID, location), layer);
        }
        return tex(itemMaterialType, new ResourceLocation(location), layer);
    }

    public Material tex(ItemMaterialType... itemMaterialTypes) {
        if (!frozen) {
            createDelegate(m -> m.tex(itemMaterialTypes));
            return this;
        }
        if (this.itemTextures == null) {
            throw new IllegalStateException("ItemMaterialType not found for " + this.name + ", cannot change the texture of its item.");
        }
        for (ItemMaterialType itemMaterialType : itemMaterialTypes) {
            if (!this.itemTextures.containsKey(itemMaterialType)) {
                throw new IllegalStateException("ItemMaterialType not found for " + this.name + ", cannot change the texture of its item.");
            }
            ResourceLocation[] locations = this.itemTextures.get(itemMaterialType);
            for (int i = 0; i < locations.length; i++) {
                locations[i] = new ResourceLocation(Zairyou.ID, String.join("/", "items", itemMaterialType.toString(), "custom", this.name + "_" + i));
            }
        }
        return this;
    }

    public Material noTint(ItemMaterialType type) {
        if (!frozen) {
            createDelegate(m -> m.noTint(type));
            return this;
        }
        if (this.items == null || !this.items.containsKey(type)) {
            throw new IllegalStateException("ItemMaterialType not found, cannot decide on whether to tint the item or not.");
        }
        if (this.disabledTint == null) {
            this.disabledTint = new ObjectOpenHashSet<>();
        }
        this.disabledTint.add(type);
        return this;
    }

    public Material noTints(ItemMaterialType... types) {
        if (!frozen) {
            createDelegate(m -> m.noTints(types));
            return this;
        }
        if (this.items == null) {
            throw new IllegalStateException("ItemMaterialType not found, cannot decide on whether to tint the item or not.");
        }
        if (this.disabledTint == null) {
            this.disabledTint = new ObjectOpenHashSet<>();
        }
        for (ItemMaterialType type : types) {
            if (!this.items.containsKey(type)) {
                throw new IllegalStateException("ItemMaterialType not found, cannot decide on whether to tint the item or not.");
            }
            this.disabledTint.add(type);
        }
        return this;
    }

    public Material flag(MaterialFlag... flags) {
        if (flags.length == 0) {
            throw new IllegalArgumentException("Flags not specified for " + this.name + " material!");
        }
        for (MaterialFlag flag : flags) {
            this.flags |= flag.bit;
        }
        return this;
    }

    public void prepare() {
        frozen = true;
        checkFlags();
        if (this.delegatingCode != null) {
            this.delegatingCode.forEach(s -> s.apply(this));
        }
    }

    private void checkFlags() {
        if ((this.flags & GENERATE_DEFAULT_METAL_TYPES.bit) > 0) {
            items(INGOT, NUGGET);
            this.flags |= GENERATE_DUST_VARIANTS.bit;
        }
        if ((this.flags & GENERATE_DUST_VARIANTS.bit) > 0) {
            items(DUST, SMALL_DUST, TINY_DUST);
        }
    }

    private void createDelegate(UnaryOperator<Material> delegatingCode) {
        if (this.delegatingCode == null) {
            this.delegatingCode = new ObjectArrayList<>();
        }
        this.delegatingCode.add(delegatingCode);
    }

}
