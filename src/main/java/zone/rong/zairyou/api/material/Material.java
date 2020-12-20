package zone.rong.zairyou.api.material;

import com.google.common.base.CaseFormat;
import it.unimi.dsi.fastutil.objects.Object2ObjectMap;
import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.util.EnumHelper;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import zone.rong.zairyou.Zairyou;
import zone.rong.zairyou.api.fluid.DefaultFluidBlock;
import zone.rong.zairyou.api.fluid.FluidType;
import zone.rong.zairyou.api.item.MaterialItem;
import zone.rong.zairyou.api.item.tool.ExtendedToolMaterial;
import zone.rong.zairyou.api.item.tool.MaterialTools;
import zone.rong.zairyou.api.material.type.MaterialType;
import zone.rong.zairyou.api.util.RenderUtils;

import javax.annotation.Nullable;
import java.util.*;
import java.util.function.UnaryOperator;

import static zone.rong.zairyou.api.fluid.FluidType.*;
import static zone.rong.zairyou.api.material.type.MaterialType.*;

/**
 * Material. That's it.
 */
public class Material {

    public static final Object2ObjectMap<String, Material> REGISTRY = new Object2ObjectOpenHashMap<>();

    public static final Material NONE = new Material("none", 0).enableAllTools(0, 0, 0F, 0F, 0F, 0);

    public static final Material COPPER = new Material("copper", 0xFF7400)
            .allowTypes(DUST, INGOT)
            .fluid(MOLTEN, "copper", fluid -> fluid.setLuminosity(8).setDensity(3000).setViscosity(6000).setTemperature(1385))
            .enableTools(1, 144, 5.0F, 1.5F, -3.2F, 8, tools -> tools.axe().hoe().pickaxe().shovel().sword());

    public static final Material ELECTRUM = new Material("electrum", 0xFFFF64)
            .allowTypes(DUST, INGOT, COIL)
            .fluid(MOLTEN, "electrum", fluid -> fluid.setLuminosity(8).setDensity(3000).setViscosity(6000).setTemperature(1337));

    public static final Material GOLD = new Material("gold", 0xFFFF00)
            .allowTypes(DUST, COIL)
            .fluid(MOLTEN, "gold", fluid -> fluid.setLuminosity(8).setDensity(3000).setViscosity(6000).setTemperature(1337));

    public static final Material REDSTONE = new Material("redstone", 0xC80000)
            .allowType(SERVO)
            .disableTint(SERVO)
            .texture(SERVO, new ModelResourceLocation(Zairyou.ID + ":custom/redstone_servo", "inventory"));

    public static final Material SILVER = new Material("silver", 0xCCE0FF)
            .allowTypes(DUST, INGOT, COIL)
            .fluid(MOLTEN, "silver", fluid -> fluid.setLuminosity(8).setDensity(3000).setViscosity(6000).setTemperature(1235));

    /* Marker/Pseudo Materials - TODO: Match colours with electric tier defaults */
    public static final Material BASIC = new Material("basic", 0x0)
            .allowTypes(FERTILIZER, SLAG)
            .disableTints(FERTILIZER, SLAG)
            .texture(FERTILIZER, new ModelResourceLocation(Zairyou.ID + ":custom/basic_fertilizer", "inventory"))
            .texture(SLAG, new ModelResourceLocation(Zairyou.ID + ":custom/basic_slag", "inventory"));

    public static final Material RICH = new Material("rich", 0x0)
            .allowTypes(FERTILIZER, SLAG)
            .disableTints(FERTILIZER, SLAG)
            .texture(FERTILIZER, new ModelResourceLocation(Zairyou.ID + ":custom/rich_fertilizer", "inventory"))
            .texture(SLAG, new ModelResourceLocation(Zairyou.ID + ":custom/rich_slag", "inventory"));

    public static final Material FLUX = new Material("flux", 0x0)
            .allowType(FERTILIZER)
            .disableTint(FERTILIZER)
            .texture(FERTILIZER, new ModelResourceLocation(Zairyou.ID + ":custom/flux_fertilizer", "inventory"));

    private final String name, translationKey;
    private final int colour;

    private EnumMap<MaterialType, MaterialItem> typeItems;
    private EnumMap<FluidType, Fluid> typeFluids;
    // private final TextureSet textureSet;

    private boolean hasTools = false;

    private EnumSet<MaterialType> disabledTint;
    private EnumMap<MaterialType, ModelResourceLocation> customTextures;
    private ExtendedToolMaterial toolMaterial;
    private MaterialTools tools;

    public Material(String name, int colour) {
        this.name = name;
        this.translationKey = String.join(".", Zairyou.ID, name, "name");
        this.colour = colour;
        if (REGISTRY.containsKey(name)) {
            throw new IllegalStateException(name + " has been registered already!");
        }
        REGISTRY.put(name, this);
    }

    public String getName() {
        return name;
    }

    public String[] getOreNames(MaterialType type) {
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

    public boolean hasTint(MaterialType type) {
        return this.disabledTint == null || !this.disabledTint.contains(type);
    }

    public Set<MaterialType> getAllowedTypes() {
        return this.typeItems == null ? Collections.emptySet() : this.typeItems.keySet();
    }

    public Map<MaterialType, MaterialItem> getItems() {
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
    public MaterialItem getItem(MaterialType type) {
        return this.typeItems == null ? null : this.typeItems.getOrDefault(type, null);
    }

    @Nullable
    public Fluid getFluid(FluidType type) {
        return this.typeFluids == null ? null : this.typeFluids.getOrDefault(type, null);
    }

    public ItemStack getStack(MaterialType type, int count) {
        Item item = getItem(type);
        if (item == null) {
            return ItemStack.EMPTY;
        }
        return new ItemStack(item, count);
    }

    public ItemStack getStack(MaterialType type, int count, String tagName, NBTTagCompound tag) {
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

    public ModelResourceLocation getTexture(MaterialType type) {
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

    public Material enableAllTools(Item.ToolMaterial toolMaterial, int attackSpeed) {
        this.hasTools = true;
        this.toolMaterial = new ExtendedToolMaterial(toolMaterial, attackSpeed);
        this.tools = new MaterialTools(this.toolMaterial);
        return this;
    }

    public Material enableTools(Item.ToolMaterial toolMaterial, int attackSpeed, UnaryOperator<MaterialTools> applicableTools) {
        this.hasTools = true;
        this.toolMaterial = new ExtendedToolMaterial(toolMaterial, attackSpeed);
        this.tools = applicableTools.apply(new MaterialTools(this.toolMaterial));
        return this;
    }

    public Material enableAllTools(int harvestLevel, int maxUses, float efficiency, float attackDamage, float attackSpeed, int enchantability) {
        this.hasTools = true;
        this.toolMaterial = new ExtendedToolMaterial(EnumHelper.addToolMaterial(name, harvestLevel, maxUses, efficiency, attackDamage, enchantability), attackSpeed);
        this.tools = new MaterialTools(this.toolMaterial).axe().hoe().pickaxe().shovel().sword();
        return this;
    }

    public Material enableTools(int harvestLevel, int maxUses, float efficiency, float attackDamage, float attackSpeed, int enchantability, UnaryOperator<MaterialTools> applicableTools) {
        this.hasTools = true;
        this.toolMaterial = new ExtendedToolMaterial(EnumHelper.addToolMaterial(name, harvestLevel, maxUses, efficiency, attackDamage, enchantability), attackSpeed);
        this.tools = applicableTools.apply(new MaterialTools(this.toolMaterial));
        return this;
    }

    public Material allowType(MaterialType type) {
        if (this.typeItems == null) {
            this.typeItems = new EnumMap<>(MaterialType.class);
        }
        this.typeItems.put(type, null);
        return this;
    }

    public Material allowTypes(MaterialType... types) {
        if (this.typeItems == null) {
            this.typeItems = new EnumMap<>(MaterialType.class);
        }
        for (MaterialType type : types) {
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

    public Material fluid(FluidType type, String fluidName, int colour, UnaryOperator<Fluid> fluidCallback, UnaryOperator<DefaultFluidBlock> blockCallback) {
        Fluid fluid = fluidCallback.apply(new Fluid(fluidName, type.getStillTexture(), type.getFlowingTexture(), RenderUtils.convertRGB2ARGB(colour)));
        FluidRegistry.registerFluid(fluid);
        fluid.setBlock(blockCallback.apply(new DefaultFluidBlock(fluid, type)));
        return fluid(type, fluid);
    }

    public Material fluid(FluidType type, String fluidName, int colour, UnaryOperator<Fluid> fluidCallback) {
        return fluid(type, fluidName, colour, fluidCallback, block -> block);
    }

    public Material fluid(FluidType type, String fluidName, UnaryOperator<Fluid> fluidCallback, UnaryOperator<DefaultFluidBlock> blockCallback) {
        return fluid(type, fluidName, this.colour, fluidCallback, blockCallback);
    }

    public Material fluid(FluidType type, String fluidName, UnaryOperator<Fluid> fluidCallback) {
        return fluid(type, fluidName, this.colour, fluidCallback, block -> block);
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

    public Material texture(MaterialType type, ModelResourceLocation location) {
        if (this.customTextures == null) {
            this.customTextures = new EnumMap<>(MaterialType.class);
        }
        this.customTextures.put(type, location);
        return this;
    }

    public Material disableTint(MaterialType type) {
        if (this.disabledTint == null) {
            this.disabledTint = EnumSet.noneOf(MaterialType.class);
        }
        this.disabledTint.add(type);
        return this;
    }

    public Material disableTints(MaterialType... types) {
        if (this.disabledTint == null) {
            this.disabledTint = EnumSet.noneOf(MaterialType.class);
        }
        Collections.addAll(this.disabledTint, types);
        return this;
    }

    public MaterialItem setItem(MaterialType type, MaterialItem item) {
        this.typeItems.put(type, item);
        return item;
    }

    public String toCamelString() {
        return CaseFormat.UPPER_UNDERSCORE.to(CaseFormat.UPPER_CAMEL, this.name);
    }

}
