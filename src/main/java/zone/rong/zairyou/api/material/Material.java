package zone.rong.zairyou.api.material;

import com.google.common.base.CaseFormat;
import it.unimi.dsi.fastutil.objects.*;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
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
import zone.rong.zairyou.api.material.element.Element;
import zone.rong.zairyou.api.material.element.FormulaBuilder;
import zone.rong.zairyou.api.material.type.BlockMaterialType;
import zone.rong.zairyou.api.material.type.IMaterialType;
import zone.rong.zairyou.api.material.type.ItemMaterialType;

import javax.annotation.Nullable;
import java.util.*;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.UnaryOperator;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Material. That's it.
 */
public class Material implements Comparable<Material> {

    public static final Item.ToolMaterial NONE_TOOL = EnumHelper.addToolMaterial("none", -1, 1, 1, 1, 1);
    public static final ExtendedToolMaterial NONE_TOOL_EXTENDED = new ExtendedToolMaterial(NONE_TOOL, -1);

    private static final Object2ObjectMap<String, Material> REGISTRY = new Object2ObjectRBTreeMap<>();
    private static final Object2ObjectMap<Class<?>, Function<Material, ?>> APPENDER_REGISTRY = new Object2ObjectOpenHashMap<>();

    public static Material get(String name) {
        return REGISTRY.getOrDefault(name, NONE);
    }

    public static ObjectCollection<Material> all() {
        return REGISTRY.values();
    }

    public static <M extends Material> Stream<M> all(Class<M> clazz) {
        return REGISTRY.values().stream().filter(clazz::isInstance).map(clazz::cast);
    }

    public static void all(BiConsumer<String, Material> consumer) {
        REGISTRY.forEach(consumer);
    }

    public static void all(Consumer<Material> consumer) {
        all().forEach(consumer);
    }

    public static Material of(String name, int colour) {
        return of(name, colour, null, NONE_TOOL_EXTENDED);
    }

    public static Material of(String name) {
        return of(name, 0x0, null, NONE_TOOL_EXTENDED);
    }

    public static Material of(String name, int colour, Element element) {
        return of(name, colour, element, NONE_TOOL_EXTENDED);
    }

    public static Material of(String name, int colour, Element element, int harvestLevel, int durability, float efficiency, float attackDamage, float attackSpeed, int enchantability) {
        return of(name, colour, element, EnumHelper.addToolMaterial(name, harvestLevel, durability, efficiency, attackDamage, enchantability), attackSpeed);
    }

    public static Material of(String name, int colour, Element element, Item.ToolMaterial toolMaterial, float attackSpeed) {
        return of(name, colour, element, new ExtendedToolMaterial(toolMaterial, attackSpeed));
    }

    public static Material of(String name, int colour, Element element, ExtendedToolMaterial extendedToolMaterial) {
        return new Material(name, colour, element, extendedToolMaterial);
    }

    public static final Material NONE = of("none");

    private static boolean frozen = false;

    protected final String name, translationKey;
    protected final Element baseElement;
    protected final int colour;
    protected final ExtendedToolMaterial toolMaterial;

    protected long flags = 0L;
    protected String chemicalFormula = "";

    protected List<UnaryOperator<Material>> delegatingCode;

    protected Set<BlockMaterialType<?>> blocks;
    protected Map<BlockMaterialType, ResourceLocation[]> blockTextures;

    protected Map<ItemMaterialType, ItemStack> items;
    protected Map<ItemMaterialType, ResourceLocation[]> itemTextures;

    protected EnumMap<FluidType, Fluid> fluids;

    protected Set<IMaterialType> disabledTint;

    protected Material(String name, int colour, Element element, ExtendedToolMaterial toolMaterial) {
        this.name = name;
        this.translationKey = String.join(".", Zairyou.ID, "material", name, "name");
        this.colour = colour;
        this.baseElement = element;
        if (this.baseElement != null) {
            this.formula(this.baseElement);
        }
        this.toolMaterial = toolMaterial;
        REGISTRY.put(name, this);
    }

    public String getName() {
        return name;
    }

    public String[] getOreNames(ItemMaterialType type) {
        List<String> typeOreNames = type.getPrefixes();
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
        return oreNames.toArray(new String[typeOreNames.size()]);
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

    public ExtendedToolMaterial getToolMaterial() {
        return toolMaterial;
    }

    public boolean hasTint(IMaterialType type) {
        return this.disabledTint == null || !this.disabledTint.contains(type);
    }

    public boolean hasType(BlockMaterialType<?> type) {
        return this.blocks.contains(type);
    }

    public boolean hasType(ItemMaterialType type) {
        return this.items != null && this.items.containsKey(type);
    }

    public boolean hasFlag(MaterialFlag flag) {
        return (this.flags & flag.bit) > 0;
    }

    public Map<BlockMaterialType<?>, Collection<Block>> getBlocks() {
        if (this.blocks == null) {
            return Collections.emptyMap();
        }
        Map<BlockMaterialType<?>, Collection<Block>> map = new Object2ObjectOpenHashMap<>();
        this.blocks.forEach(b -> map.put(b, b.getGetter().getBlocks()));
        return map;
    }

    public Map<BlockMaterialType<?>, Collection<IBlockState>> getBlockStates() {
        if (this.blocks == null) {
            return Collections.emptyMap();
        }
        Map<BlockMaterialType<?>, Collection<IBlockState>> map = new Object2ObjectOpenHashMap<>();
        this.blocks.forEach(b -> map.put(b, b.getGetter().getBlockStates()));
        return map;
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

    public IBlockState getBlock(BlockMaterialType<?> type, Object... arguments) {
        if (arguments.length == 0) {
            return type.getGetter().getBlockState(this);
        }
        return type.getGetter().getBlockState(arguments);
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

    public Item getItem(ItemMaterialType type) {
        return getItem(type, false).getItem();
    }

    public Fluid getFluid(FluidType type) {
        return this.fluids == null ? null : this.fluids.get(type);
    }

    public ItemStack getStack(BlockMaterialType type, int count) {
        return getStack(this, type, count);
    }

    public <T> ItemStack getStack(T genericType, BlockMaterialType type, int count) {
        IBlockState state = getBlock(type, genericType);
        if (state == null) {
            return ItemStack.EMPTY;
        }
        return new ItemStack(state.getBlock(), count, state.getBlock().getMetaFromState(state));
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

    public ResourceLocation[] getTextures(BlockMaterialType<?> type) {
        return this.blockTextures.get(type);
    }

    public void setBlock(BlockMaterialType<?> type, IBlockState state, Object... arguments) {
        if (!this.hasType(type)) {
            throw new IllegalStateException(this.getName() + " does not have " + type.getId() + "!");
        }
        if (arguments.length == 0) {
            type.getGetter().supplyBlock(state, this);
        } else {
            type.getGetter().supplyBlock(state, arguments);
        }
        this.blocks.add(type); // In case
    }

    public void setItem(ItemMaterialType type, ItemStack stack) {
        if (!this.hasType(type)) {
            throw new IllegalStateException(this.getName() + " does not have " + type.getId() + "!");
        }
        this.items.put(type, stack);
    }

    public void setItem(ItemMaterialType type, Item item, int meta) {
        if (!this.hasType(type)) {
            throw new IllegalStateException(this.getName() + " does not have " + type.getId() + "!");
        }
        this.items.put(type, new ItemStack(item, 1, meta));
    }

    public void setItem(ItemMaterialType type, Item item) {
        if (!this.hasType(type)) {
            throw new IllegalStateException(this.getName() + " does not have " + type.getId() + "!");
        }
        this.items.put(type, new ItemStack(item));
    }

    public String toCamelString() {
        return CaseFormat.UPPER_UNDERSCORE.to(CaseFormat.UPPER_CAMEL, this.name);
    }

    public <M extends Material> M formula(String formula) {
        this.chemicalFormula = formula;
        return self();
    }

    public <M extends Material> M formula(UnaryOperator<FormulaBuilder> builder) {
        return formula(builder.apply(FormulaBuilder.of()).build());
    }

    public <M extends Material> M formula(Element element, int atoms) {
        return formula(FormulaBuilder.of().element(element, atoms).build());
    }

    public <M extends Material> M formula(Element element) {
        return formula(FormulaBuilder.of().element(element).build());
    }

    public Material block(BlockMaterialType<?> blockMaterialType) {
        if (this.blocks == null) {
            this.blocks = new ObjectOpenHashSet<>();
            this.blockTextures = new Object2ObjectOpenHashMap<>();
        }
        this.blocks.add(blockMaterialType);
        this.blockTextures.put(blockMaterialType, TextureSet.DULL.getTextureLocations(blockMaterialType));
        return self();
    }

    public <M extends Material> M items(ItemMaterialType... itemMaterialTypes) {
        if (itemMaterialTypes.length == 0) {
            throw new IllegalArgumentException("No ItemMaterialTypes specified for " + this.name + " material!");
        }
        if (this.items == null) {
            this.items = new Object2ObjectOpenHashMap<>();
            this.itemTextures = new Object2ObjectOpenHashMap<>();
        }
        for (ItemMaterialType itemMaterialType : itemMaterialTypes) {
            this.items.put(itemMaterialType, ItemStack.EMPTY);
            this.itemTextures.put(itemMaterialType, TextureSet.DULL.getTextureLocations(itemMaterialType));
        }
        return self();
    }

    public <M extends Material> M provideFluid(FluidType type, Fluid fluid) {
        if (this.fluids == null) {
            this.fluids = new EnumMap<>(FluidType.class);
        }
        /*
        if (bucket) {
            FluidRegistry.addBucketForFluid(fluid);
        }
         */
        this.fluids.put(type, fluid);
        return self();
    }

    public <M extends Material> M fluid(FluidType fluidType, UnaryOperator<ExtendedFluid.Builder> builderOperator) {
        Fluid fluid = builderOperator.apply(new ExtendedFluid.Builder(this, fluidType)).build();
        return provideFluid(fluidType, fluid);
    }

    public <M extends Material> M tex(IMaterialType type, ResourceLocation location, int layer) {
        if (!frozen) {
            createDelegate(m -> m.tex(type, location, layer));
            return self();
        }
        if (type instanceof ItemMaterialType) {
            if (this.itemTextures == null || !this.itemTextures.containsKey(type)) {
                throw new IllegalStateException("ItemMaterialType not found for " + this.name + ", cannot change the texture of its item.");
            }
            ResourceLocation[] locations = this.itemTextures.get(type);
            if (locations.length <= layer) {
                throw new IllegalStateException(type.getId() + " does not have layer " + layer);
            }
            locations[layer] = location;
        } else {
            if (this.blockTextures == null || !this.blockTextures.containsKey(type)) {
                throw new IllegalStateException("ItemMaterialType not found for " + this.name + ", cannot change the texture of its item.");
            }
            ResourceLocation[] locations = this.blockTextures.get(type);
            if (locations.length <= layer) {
                throw new IllegalStateException(type.getId() + " does not have layer " + layer);
            }
        }
        return self();
    }

    public <M extends Material> M tex(IMaterialType type, String location, int layer) {
        int colonIndex = location.indexOf(':');
        if (colonIndex == -1) {
            return tex(type, new ResourceLocation(Zairyou.ID, location), layer);
        }
        return tex(type, new ResourceLocation(location), layer);
    }

    // TODO fix
    public <M extends Material> M tex(IMaterialType... types) {
        if (!frozen) {
            createDelegate(m -> m.tex(types));
            return self();
        }
        for (IMaterialType type : types) {
            if (type instanceof ItemMaterialType) {
                if (!this.itemTextures.containsKey(type)) {
                    throw new IllegalStateException("ItemMaterialType not found for " + this.name + ", cannot change the texture of its item.");
                }
                ResourceLocation[] locations = this.itemTextures.get(type);
                for (int i = 0; i < locations.length; i++) {
                    locations[i] = new ResourceLocation(Zairyou.ID, String.join("/", "items", type.getId(), "custom", this.name + "_" + i));
                }
            }
            else {
                if (!this.blockTextures.containsKey(type)) {
                    throw new IllegalStateException("BlockMaterialType not found for " + this.name + ", cannot change the texture of its item.");
                }
                ResourceLocation[] locations = this.blockTextures.get(type);
                for (int i = 0; i < locations.length; i++) {
                    locations[i] = new ResourceLocation(Zairyou.ID, String.join("/", "blocks", type.getId(), "custom", this.name + "_" + i));
                }
            }
        }
        return self();
    }

    public <M extends Material> M noTint(IMaterialType type) {
        if (!frozen) {
            createDelegate(m -> m.noTint(type));
            return self();
        }
        if (this.items == null || !this.items.containsKey(type)) {
            throw new IllegalStateException("ItemMaterialType not found, cannot decide on whether to tint the item or not.");
        }
        if (this.disabledTint == null) {
            this.disabledTint = new ObjectOpenHashSet<>();
        }
        this.disabledTint.add(type);
        return self();
    }

    public <M extends Material> M noTints(IMaterialType... types) {
        if (!frozen) {
            createDelegate(m -> m.noTints(types));
            return self();
        }
        if (this.items == null) {
            throw new IllegalStateException("ItemMaterialType not found, cannot decide on whether to tint the item or not.");
        }
        if (this.disabledTint == null) {
            this.disabledTint = new ObjectOpenHashSet<>();
        }
        for (IMaterialType type : types) {
            if (type instanceof ItemMaterialType && !this.items.containsKey(type)) {
                throw new IllegalStateException(type.getId() + "for " + this.name + " not found, cannot decide on whether to tint the item or not.");
            }
            if (type instanceof BlockMaterialType && !this.blocks.contains(type)) {
                throw new IllegalStateException(type.getId() + "for " + this.name + " not found, cannot decide on whether to tint the block or not.");
            }
            this.disabledTint.add(type);
        }
        return self();
    }

    public <M extends Material> M flag(MaterialFlag... flags) {
        if (flags.length == 0) {
            throw new IllegalArgumentException("Flags not specified for " + this.name + " material!");
        }
        for (MaterialFlag flag : flags) {
            this.flags |= flag.bit;
        }
        return self();
    }

    public void prepare() {
        frozen = true;
        checkFlags();
        if (this.delegatingCode != null) {
            this.delegatingCode.forEach(s -> s.apply(this));
        }
    }

    private void checkFlags() {
        for (MaterialFlag materialFlag : MaterialFlag.VALUES) {
            materialFlag.checkFlag(this);
        }
    }

    private void createDelegate(UnaryOperator<Material> delegatingCode) {
        if (this.delegatingCode == null) {
            this.delegatingCode = new ObjectArrayList<>();
        }
        this.delegatingCode.add(delegatingCode);
    }
    
    protected <M extends Material> M self() {
        return (M) this;
    }

    @Override
    public int compareTo(Material m) {
        return this.getName().compareTo(m.getName());
    }
}
