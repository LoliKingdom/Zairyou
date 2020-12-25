package zone.rong.zairyou.api.material;

import com.google.common.base.CaseFormat;
import it.unimi.dsi.fastutil.objects.Object2ObjectMap;
import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.resources.I18n;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.util.EnumHelper;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import zone.rong.zairyou.Zairyou;
import zone.rong.zairyou.api.fluid.DefaultFluidBlock;
import zone.rong.zairyou.api.fluid.FluidType;
import zone.rong.zairyou.api.item.tool.ExtendedToolMaterial;
import zone.rong.zairyou.api.item.tool.MaterialTools;
import zone.rong.zairyou.api.material.type.BlockMaterialType;
import zone.rong.zairyou.api.material.type.ItemMaterialType;
import zone.rong.zairyou.api.client.RenderUtils;

import javax.annotation.Nullable;
import java.util.*;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.IntSupplier;
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

    private EnumMap<BlockMaterialType, Block> typeBlocks;
    private EnumMap<ItemMaterialType, Item> typeItems;
    private EnumMap<FluidType, Fluid> typeFluids;
    // private final TextureSet textureSet;

    private boolean hasTools = false;

    private EnumSet<ItemMaterialType> disabledTint;
    private EnumMap<ItemMaterialType, ModelResourceLocation> customTextures;
    private ExtendedToolMaterial toolMaterial;
    private MaterialTools tools;

    protected Material(String name, int colour) {
        this.name = name;
        this.translationKey = String.join(".", Zairyou.ID, name, "name");
        this.colour = colour;
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

    public boolean hasTint(ItemMaterialType type) {
        return this.disabledTint == null || !this.disabledTint.contains(type);
    }

    public Set<ItemMaterialType> getAllowedTypes() {
        return this.typeItems == null ? Collections.emptySet() : this.typeItems.keySet();
    }

    public Map<ItemMaterialType, Item> getItems() {
        return this.typeItems == null ? Collections.emptyMap() : this.typeItems;
    }

    public ExtendedToolMaterial getToolMaterial() {
        return toolMaterial;
    }

    public MaterialTools getTools() {
        return tools;
    }

    public Map<FluidType, Fluid> getFluids() {
        return this.typeFluids == null ? Collections.emptyMap() : this.typeFluids;
    }

    @Nullable
    public Item getItem(ItemMaterialType type) {
        return this.typeItems == null ? null : this.typeItems.get(type);
    }

    @Nullable
    public Fluid getFluid(FluidType type) {
        return this.typeFluids == null ? null : this.typeFluids.get(type);
    }

    public ItemStack getStack(ItemMaterialType type, int count) {
        Item item = getItem(type);
        if (item == null) {
            return ItemStack.EMPTY;
        }
        return new ItemStack(item, count);
    }

    public ItemStack getStack(ItemMaterialType type, int count, String tagName, NBTTagCompound tag) {
        Item item = getItem(type);
        if (item == null) {
            return ItemStack.EMPTY;
        }
        ItemStack stack = new ItemStack(item, count);
        stack.getTagCompound().setTag(tagName, tag);
        return stack;
    }

    @Nullable
    public FluidStack getStack(FluidType type, int amount) {
        return new FluidStack(getFluid(type), amount);
    }

    public ModelResourceLocation getTexture(ItemMaterialType type) {
        if (this.customTextures == null) {
            return type.getTextureLocation();
        }
        ModelResourceLocation loc = this.customTextures.get(type);
        if (loc == null) {
            return type.getTextureLocation();
        }
        return loc;
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

    public Material type(BlockMaterialType type) {
        if (this.typeBlocks == null) {
            this.typeBlocks = new EnumMap<>(BlockMaterialType.class);
        }
        this.typeBlocks.put(type, null);
        return this;
    }

    public Material types(BlockMaterialType... types) {
        if (this.typeBlocks == null) {
            this.typeBlocks = new EnumMap<>(BlockMaterialType.class);
        }
        for (BlockMaterialType type : types) {
            this.typeBlocks.put(type, null);
        }
        return this;
    }

    public Material type(ItemMaterialType type) {
        if (this.typeItems == null) {
            this.typeItems = new EnumMap<>(ItemMaterialType.class);
        }
        this.typeItems.put(type, null);
        return this;
    }

    public Material types(ItemMaterialType... types) {
        if (this.typeItems == null) {
            this.typeItems = new EnumMap<>(ItemMaterialType.class);
        }
        for (ItemMaterialType type : types) {
            this.typeItems.put(type, null);
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

    public Material fluid(FluidType fluidType, UnaryOperator<FluidBuilder> builderOperator) {
        return fluid(fluidType, builderOperator.apply(new FluidBuilder(this.name, fluidType, this.colour)).build());
    }

    public Material fluid(FluidType fluidType, UnaryOperator<FluidBuilder> builderOperator, Consumer<Block> blockConsumer) {
        Fluid fluid = builderOperator.apply(new FluidBuilder(this.name, fluidType, this.colour)).build();
        blockConsumer.accept(fluid.getBlock());
        return fluid(fluidType, fluid);
    }

    /*
    public Material fluids(FluidType... types) {
        if (this.typeFluids == null) {
            this.typeFluids = new EnumMap<>(FluidType.class);
        }
        for (FluidType type : types) {
            this.typeFluids.put(type, null);
        }
        return this;
    }
     */

    public Material texture(ItemMaterialType type, ModelResourceLocation location) {
        if (this.customTextures == null) {
            this.customTextures = new EnumMap<>(ItemMaterialType.class);
        }
        this.customTextures.put(type, location);
        return this;
    }

    public Material texture(ItemMaterialType type, String domain, String id) {
        return texture(type, new ModelResourceLocation(domain + id, "inventory")); // TODO: differentiate Block and Item
    }

    public Material texture(ItemMaterialType type, String id) {
        return texture(type, new ModelResourceLocation(Zairyou.ID + ":" + id, "inventory")); // TODO: differentiate Block and Item
    }

    public Material noTint(ItemMaterialType type) {
        if (this.disabledTint == null) {
            this.disabledTint = EnumSet.noneOf(ItemMaterialType.class);
        }
        this.disabledTint.add(type);
        return this;
    }

    public Material noTints(ItemMaterialType... types) {
        if (this.disabledTint == null) {
            this.disabledTint = EnumSet.noneOf(ItemMaterialType.class);
        }
        Collections.addAll(this.disabledTint, types);
        return this;
    }

    public Item setItem(ItemMaterialType type, Item item) {
        this.typeItems.put(type, item);
        return item;
    }

    public String toCamelString() {
        return CaseFormat.UPPER_UNDERSCORE.to(CaseFormat.UPPER_CAMEL, this.name);
    }

    public class FluidBuilder {

        private String name;

        private final FluidType fluidType;
        private final int colour;

        private ResourceLocation still, flow, overlay;
        private boolean noTint = false;
        private IntSupplier luminosity, density, viscosity, temperature;
        private Function<Fluid, Block> fluidBlock;
        private EnumRarity rarity;

        FluidBuilder(String name, FluidType fluidType, int colour) {
            this.name = name;
            this.fluidType = fluidType;
            this.colour = colour;
        }

        public FluidBuilder customName(String name) {
            this.name = name;
            return this;
        }

        public FluidBuilder still(ResourceLocation still) {
            this.still = still;
            return this;
        }

        public FluidBuilder still(String still) {
            this.still = new ResourceLocation(Zairyou.ID, still);
            return this;
        }

        public FluidBuilder flow(ResourceLocation flow) {
            this.flow = flow;
            return this;
        }

        public FluidBuilder flow(String flow) {
            this.flow = new ResourceLocation(Zairyou.ID, flow);
            return this;
        }

        public FluidBuilder overlay(ResourceLocation overlay) {
            this.overlay = overlay;
            return this;
        }

        public FluidBuilder noTint() {
            this.noTint = true;
            return this;
        }

        public FluidBuilder luminosity(int luminosity) {
            this.luminosity = () -> luminosity;
            return this;
        }

        public FluidBuilder density(int density) {
            this.density = () -> density;
            return this;
        }

        public FluidBuilder viscosity(int viscosity) {
            this.viscosity = () -> viscosity;
            return this;
        }

        public FluidBuilder temperature(int temperature) {
            this.temperature = () -> temperature;
            return this;
        }

        public FluidBuilder rarity(EnumRarity rarity) {
            this.rarity = rarity;
            return this;
        }

        public FluidBuilder customBlock(Function<Fluid, Block> block) {
            this.fluidBlock = block;
            return this;
        }

        Fluid build() {
            Fluid fluid = new Fluid(this.name, this.still == null ? this.fluidType.getStillTexture() : this.still, this.flow == null ? this.fluidType.getFlowingTexture() : this.flow, this.overlay) {
                @Override
                public String getLocalizedName(FluidStack stack) {
                    return I18n.format(fluidType.getTranslationKey(), I18n.format(Material.this.translationKey));
                }
            };
            if (!this.noTint) {
                fluid.setColor(RenderUtils.convertRGB2ARGB(this.fluidType.getBaseAlpha(), this.colour));
            }
            FluidRegistry.registerFluid(fluid);
            fluid.setBlock(this.fluidBlock == null ? new DefaultFluidBlock(fluid, this.fluidType) : this.fluidBlock.apply(fluid))
                    .setLuminosity(this.luminosity == null ? this.fluidType.getBaseLuminosity() : this.luminosity.getAsInt())
                    .setDensity(this.density == null ? this.fluidType.getBaseDensity() : this.density.getAsInt())
                    .setViscosity(this.viscosity == null ? this.fluidType.getBaseViscosity() : this.viscosity.getAsInt())
                    .setTemperature(this.temperature == null ? this.fluidType.getBaseTemperature() : this.temperature.getAsInt())
                    .setRarity(this.rarity == null ? EnumRarity.COMMON : this.rarity)
                    .setGaseous(this.fluidType == FluidType.GASEOUS);
            return fluid;
        }

    }

}
