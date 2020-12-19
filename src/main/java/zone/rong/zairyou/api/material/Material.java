package zone.rong.zairyou.api.material;

import com.google.common.base.CaseFormat;
import it.unimi.dsi.fastutil.objects.Object2ObjectMap;
import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.util.EnumHelper;
import zone.rong.zairyou.Zairyou;
import zone.rong.zairyou.api.item.MaterialItem;
import zone.rong.zairyou.api.item.tool.ExtendedToolMaterial;
import zone.rong.zairyou.api.item.tool.MaterialTools;
import zone.rong.zairyou.api.material.type.MaterialType;

import java.util.EnumMap;
import java.util.EnumSet;
import java.util.Map;
import java.util.Set;
import java.util.function.UnaryOperator;

import static zone.rong.zairyou.api.material.type.MaterialType.*;

/**
 * Material. That's it.
 */
public class Material {

    public static final Object2ObjectMap<String, Material> REGISTRY = new Object2ObjectOpenHashMap<>();

    public static final Material NONE = new Material("none", 0).enableAllTools(0, 0, 0F, 0F, 0F, 0);
    public static final Material COPPER = new Material("copper", 0xFF8000)
            .allowTypes(DUST, INGOT)
            .enableTools(1, 144, 5.0F, 1.5F, -3.2F, 8, tools -> tools.axe().hoe().pickaxe().shovel().sword());
    public static final Material ELECTRUM = new Material("electrum", 0xFFFF64)
            .allowTypes(DUST, INGOT, COIL);
    public static final Material GOLD = new Material("gold", 0xFFFF00)
            .allowTypes(DUST, COIL);
    public static final Material REDSTONE = new Material("redstone", 0xC80000)
            .allowType(SERVO)
            .disableTint(SERVO)
            .texture(SERVO, new ModelResourceLocation(Zairyou.ID + ":custom/redstone_servo", "inventory"));
    public static final Material SILVER = new Material("silver", 0xDCDCFF)
            .allowTypes(DUST, INGOT, COIL);

    /* Marker/Pseudo Materials - TODO: Match colours with electric tier defaults */
    public static final Material BASIC = new Material("basic", 0x0)
            .allowType(FERTILIZER)
            .disableTint(FERTILIZER)
            .texture(FERTILIZER, new ModelResourceLocation(Zairyou.ID + ":custom/basic_fertilizer", "inventory"));
    public static final Material RICH = new Material("rich", 0x0)
            .allowType(FERTILIZER)
            .disableTint(FERTILIZER)
            .texture(FERTILIZER, new ModelResourceLocation(Zairyou.ID + ":custom/rich_fertilizer", "inventory"));
    public static final Material FLUX = new Material("flux", 0x0)
            .allowType(FERTILIZER)
            .disableTint(FERTILIZER)
            .texture(FERTILIZER, new ModelResourceLocation(Zairyou.ID + ":custom/flux_fertilizer", "inventory"));

    private final String name, translationKey;
    private final int colour;
    private final EnumMap<MaterialType, MaterialItem> typeItems = new EnumMap<>(MaterialType.class);
    // private final TextureSet textureSet;

    private boolean hasTools;

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

    public String getOreName(MaterialType type) {
        return type.toCamelString().concat(CaseFormat.LOWER_UNDERSCORE.to(CaseFormat.UPPER_CAMEL, this.name));
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
        return typeItems.keySet();
    }

    public Map<MaterialType, MaterialItem> getItems() {
        return typeItems;
    }

    public ExtendedToolMaterial getToolMaterial() {
        return toolMaterial;
    }

    public MaterialTools getTools() {
        return tools;
    }

    public MaterialItem getItem(MaterialType type) {
        return typeItems.get(type);
    }

    public ItemStack getStack(MaterialType type, int count) {
        return new ItemStack(typeItems.get(type), count);
    }

    public ItemStack getStack(MaterialType type, int count, String tagName, NBTTagCompound tag) {
        ItemStack stack = new ItemStack(typeItems.get(type), count);
        stack.getTagCompound().setTag(tagName, tag);
        return stack;
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
        this.typeItems.put(type, null);
        return this;
    }

    public Material allowTypes(MaterialType... types) {
        for (MaterialType type : types) {
            this.typeItems.put(type, null);
        }
        return this;
    }

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

    public MaterialItem setItem(MaterialType type, MaterialItem item) {
        this.typeItems.put(type, item);
        return item;
    }

}
