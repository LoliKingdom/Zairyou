package zone.rong.zairyou.api.material;

import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import it.unimi.dsi.fastutil.objects.ObjectOpenHashSet;
import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fml.common.Loader;
import zone.rong.zairyou.Zairyou;
import zone.rong.zairyou.api.event.MaterialRegisterEvent;
import zone.rong.zairyou.api.fluid.ExtendedFluid;
import zone.rong.zairyou.api.fluid.FluidType;
import zone.rong.zairyou.api.material.element.Element;
import zone.rong.zairyou.api.material.element.FormulaBuilder;
import zone.rong.zairyou.api.material.type.BlockMaterialType;
import zone.rong.zairyou.api.material.type.IMaterialType;
import zone.rong.zairyou.api.material.type.ItemMaterialType;
import zone.rong.zairyou.api.ore.OreBlock;
import zone.rong.zairyou.api.ore.OreGrade;

import java.util.Collections;
import java.util.EnumMap;
import java.util.List;
import java.util.Set;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.function.UnaryOperator;

import static zone.rong.zairyou.api.material.type.ItemMaterialType.*;
import static zone.rong.zairyou.api.material.MaterialFlag.*;

public class MaterialBuilder {

    public static MaterialBuilder of(String name, int colour) {
        return new MaterialBuilder(name, colour);
    }

    public static MaterialBuilder of(String name) {
        return new MaterialBuilder(name, 0x0);
    }

    public final String name;
    public final int colour;

    private String chemicalFormula = "";

    private EnumMap<BlockMaterialType, Function<Material, Block>> blocks;
    private EnumMap<BlockMaterialType, ResourceLocation[]> blockTextures;

    private EnumMap<ItemMaterialType, ItemStack> items;
    private EnumMap<ItemMaterialType, ResourceLocation[]> itemTextures;

    private EnumMap<FluidType, Function<Material, Fluid>> fluids;

    private Set<IMaterialType> disabledTint;

    private List<UnaryOperator<MaterialBuilder>> delegatingCode;

    private long flags = 0L;

    boolean building = false;

    MaterialBuilder(String name, int colour) {
        this.name = name;
        this.colour = colour;
    }

    public MaterialBuilder formula(String formula) {
        this.chemicalFormula = formula;
        return this;
    }

    public MaterialBuilder formula(UnaryOperator<FormulaBuilder> builder) {
        return formula(builder.apply(FormulaBuilder.of()).build());
    }

    public MaterialBuilder formula(Element element, int atoms) {
        return formula(FormulaBuilder.of().element(element, atoms).build());
    }

    public MaterialBuilder formula(Element element) {
        return formula(FormulaBuilder.of().element(element).build());
    }

    // TODO: placeholder
    public MaterialBuilder ore() {
        if (this.blocks == null) {
            this.blocks = new EnumMap<>(BlockMaterialType.class);
        }
        this.blocks.put(BlockMaterialType.ORE, m -> new OreBlock(m, OreGrade.NORMAL));
        return this;
    }

    public MaterialBuilder items(ItemMaterialType... itemMaterialTypes) {
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

    /*

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

     */

    public MaterialBuilder provideFluid(FluidType type, Function<Material, Fluid> fluid) {
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

    public MaterialBuilder fluid(FluidType fluidType, UnaryOperator<ExtendedFluid.Builder> builderOperator) {
        Function<Material, Fluid> fluid = m -> builderOperator.apply(new ExtendedFluid.Builder(m, fluidType)).build();
        return provideFluid(fluidType, fluid);
    }

    public MaterialBuilder tex(ItemMaterialType itemMaterialType, ResourceLocation location, int layer) {
        if (!building) {
            createDelegate(b -> b.tex(itemMaterialType, location, layer));
            return this;
        }
        if (this.itemTextures == null || !this.itemTextures.containsKey(itemMaterialType)) {
            throw new IllegalStateException("ItemMaterialType not found, cannot change the texture of its item.");
        }
        ResourceLocation[] locations = this.itemTextures.get(itemMaterialType);
        if (locations.length <= layer) {
            throw new IllegalStateException(itemMaterialType + " does not have layer " + layer);
        }
        locations[layer] = location;
        return this;
    }

    public MaterialBuilder tex(ItemMaterialType itemMaterialType, String location, int layer) {
        if (!building) {
            createDelegate(b -> b.tex(itemMaterialType, location, layer));
            return this;
        }
        int colonIndex = location.indexOf(':');
        if (colonIndex == -1) {
            return tex(itemMaterialType, new ResourceLocation(Zairyou.ID, location), layer);
        }
        return tex(itemMaterialType, new ResourceLocation(location), layer);
    }

    public MaterialBuilder tex(ItemMaterialType... itemMaterialTypes) {
        if (!building) {
            createDelegate(b -> b.tex(itemMaterialTypes));
            return this;
        }
        if (this.itemTextures == null) {
            throw new IllegalStateException("ItemMaterialType not found, cannot change the texture of its item.");
        }
        for (ItemMaterialType itemMaterialType : itemMaterialTypes) {
            if (!this.itemTextures.containsKey(itemMaterialType)) {
                throw new IllegalStateException("ItemMaterialType not found, cannot change the texture of its item.");
            }
            ResourceLocation[] locations = this.itemTextures.get(itemMaterialType);
            for (int i = 0; i < locations.length; i++) {
                locations[i] = new ResourceLocation(Zairyou.ID, String.join("/", "items", itemMaterialType.toString(), "custom", this.name + "_" + i));
            }
        }
        return this;
    }

    public MaterialBuilder noTint(ItemMaterialType type) {
        if (!building) {
            createDelegate(b -> b.noTint(type));
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

    public MaterialBuilder noTints(ItemMaterialType... types) {
        if (types.length == 0) {
            throw new IllegalArgumentException("noTints not specified for " + this.name + " material!");
        }
        if (this.disabledTint == null) {
            this.disabledTint = new ObjectOpenHashSet<>();
        }
        Collections.addAll(this.disabledTint, types);
        return this;
    }

    public MaterialBuilder flag(MaterialFlag... flags) {
        if (flags.length == 0) {
            throw new IllegalArgumentException("Flags not specified for " + this.name + " material!");
        }
        for (MaterialFlag flag : flags) {
            this.flags |= flag.bit;
        }
        return this;
    }

    public Material build() {
        building = true;
        MinecraftForge.EVENT_BUS.post(new MaterialRegisterEvent(this.name, this.colour, this, Loader.instance().activeModContainer()));
        checkFlags();
        if (this.delegatingCode != null) {
            this.delegatingCode.forEach(s -> s.apply(this));
        }
        return new Material(name, colour, chemicalFormula, flags, blocks, blockTextures, items, itemTextures, fluids, disabledTint);
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

    private void createDelegate(UnaryOperator<MaterialBuilder> delegatingCode) {
        if (this.delegatingCode == null) {
            this.delegatingCode = new ObjectArrayList<>();
        }
        this.delegatingCode.add(delegatingCode);
    }

}
