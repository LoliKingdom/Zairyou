package zone.rong.zairyou.api.material;

import it.unimi.dsi.fastutil.objects.Object2ObjectMap;
import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraftforge.common.util.EnumHelper;
import zone.rong.zairyou.api.item.MaterialItem;
import zone.rong.zairyou.api.item.tool.ExtendedToolMaterial;
import zone.rong.zairyou.api.item.tool.MaterialTools;
import zone.rong.zairyou.api.material.type.MaterialType;

import java.util.EnumMap;
import java.util.Set;
import java.util.function.UnaryOperator;

import static zone.rong.zairyou.api.material.type.MaterialType.*;

/**
 * Material. That's it.
 */
public class Material {

    public static final Object2ObjectMap<String, Material> REGISTRY = new Object2ObjectOpenHashMap<>();

    public static final Material COPPER = new Material("copper", 0xFF8000)
            .allowTypes(DUST, INGOT)
            .enableTools(1, 144, 5.0F, 1.5F, -3.2F, 8, tools -> tools.axe().hoe().pickaxe().shovel().sword());

    private final String name;
    private final int colour;
    private final EnumMap<MaterialType, MaterialItem> typeItems = new EnumMap<>(MaterialType.class);
    // private final TextureSet textureSet;

    private boolean hasCustomTexture;
    private boolean hasTools;

    private EnumMap<MaterialType, ModelResourceLocation> customTextures;
    private ExtendedToolMaterial toolMaterial;
    private MaterialTools tools;

    public Material(String name, int colour) {
        this.name = name;
        this.colour = colour;
        REGISTRY.put(name, this);
    }

    public String getName() {
        return name;
    }

    public int getColour() {
        return colour;
    }

    public Set<MaterialType> getAllowedTypes() {
        return typeItems.keySet();
    }

    public EnumMap<MaterialType, MaterialItem> getItems() {
        return typeItems;
    }

    public MaterialItem getItem(MaterialType type) {
        return typeItems.get(type);
    }

    public ModelResourceLocation getTexture(MaterialType type) {
        if (this.hasCustomTexture) {
            return customTextures.get(type);
        }
        return type.getTextureLocation();
    }

    public boolean hasCustomTexture() {
        return hasCustomTexture;
    }

    public boolean hasTools() {
        return hasTools;
    }

    @Deprecated
    public TextureSet getTextureSet() {
        return TextureSet.NONE;
    }

    public Material enableTools(Item.ToolMaterial toolMaterial, int attackSpeed, UnaryOperator<MaterialTools> applicableTools) {
        this.hasTools = true;
        this.toolMaterial = new ExtendedToolMaterial(toolMaterial, attackSpeed);
        this.tools = applicableTools.apply(new MaterialTools(this.toolMaterial));
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
        this.hasCustomTexture = true;
        if (this.customTextures == null) {
            this.customTextures = new EnumMap<>(MaterialType.class);
        }
        this.customTextures.put(type, location);
        return this;
    }

    public MaterialItem setItem(MaterialType type, MaterialItem item) {
        this.typeItems.replace(type, item);
        return item;
    }

}
