package zone.rong.zairyou.api.material.type;

import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import it.unimi.dsi.fastutil.objects.ObjectLists;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.ModContainer;
import zone.rong.zairyou.Zairyou;
import zone.rong.zairyou.api.block.IBlockGetter;
import zone.rong.zairyou.api.block.StorageBlock;
import zone.rong.zairyou.api.ore.block.OreBlock;
import zone.rong.zairyou.api.ore.block.SurfaceOreRockBlock;
import zone.rong.zairyou.api.util.RecipeUtil;
import zone.rong.zairyou.api.util.Util;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Map;
import java.util.function.LongUnaryOperator;
import java.util.stream.Collectors;

public class BlockMaterialType<G extends IBlockGetter> implements IMaterialType {

    public static final Map<String, BlockMaterialType<?>> REGISTRY = new Object2ObjectOpenHashMap<>();

    static boolean instantiatedBlocks = false;

    // BLOCK(Zairyou.ID, 0, "ore"),
    // FRAME(Zairyou.ID, 0, "frame", "frameGt"),
    // ORE(Zairyou.ID, 0, "ore"),
    // STONE(Zairyou.ID, 0, "stone~&");

    // public static final BlockMaterialType FRAME = new BlockMaterialType(Zairyou.ID, "frame", "frame", FrameBlock.class);
    public static final BlockMaterialType<StorageBlock.Getter> STORAGE = new BlockMaterialType<>(Zairyou.ID, "block", "block", StorageBlock.class, new StorageBlock.Getter()).materialAmount(l -> l * 9);
    public static final BlockMaterialType<OreBlock.Getter> ORE = new BlockMaterialType<>(Zairyou.ID, "ore", OreBlock.class, new OreBlock.Getter()).modelLayers(2).materialAmount(l -> -1);
    public static final BlockMaterialType<OreBlock.Getter> ORE_POOR = new BlockMaterialType<>(Zairyou.ID, "poor_ore", OreBlock.class, new OreBlock.Getter()).modelLayers(2).materialAmount(l -> -1);
    public static final BlockMaterialType<OreBlock.Getter> ORE_RICH = new BlockMaterialType<>(Zairyou.ID, "rich_ore", OreBlock.class, new OreBlock.Getter()).modelLayers(2).materialAmount(l -> -1);
    public static final BlockMaterialType<SurfaceOreRockBlock.Getter> ORE_SURFACE_ROCK = new BlockMaterialType<>(Zairyou.ID, "surface_ore_rock", SurfaceOreRockBlock.class, new SurfaceOreRockBlock.Getter()).modelLayers(2).materialAmount(l -> -1);

    static {
        STORAGE.recipe((r, t, m) -> {
            final ItemStack blockStack = m.getStack(STORAGE, 1);
            final ItemStack ingotStack = m.getItem(ItemMaterialType.INGOT, false);
            r.register(RecipeUtil.addShapeless(String.format("%s_ingots_to_block", m.getName()), blockStack,
                    ingotStack, ingotStack, ingotStack, ingotStack, ingotStack, ingotStack, ingotStack, ingotStack, ingotStack));
        });
    }

    public static void instantiateBlocks() {
        instantiatedBlocks = true;
        REGISTRY.values().stream().map(BlockMaterialType::getBlockClass).collect(Collectors.toSet()).forEach(c -> {
            try {
                c.getMethod("create").invoke(null);
            } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
                e.printStackTrace();
            }
        });
    }

    private final String domain, id, modName;
    private final List<String> prefixes;
    private final Class<?> blockClass;
    private final G getter;

    private int modelLayers;
    private long materialAmount;
    private List<RecipeRegisterCallback<BlockMaterialType<G>>> recipes;

    public BlockMaterialType(String domain, String id, Class<?> blockClass, G getter) {
        this(domain, id, "", blockClass, getter);
    }

    public BlockMaterialType(String domain, String id, String oreName, Class<?> blockClass, G getter) {
        this.domain = domain;
        this.id = id;
        if (!oreName.isEmpty()) {
            this.prefixes = new ObjectArrayList<>(1);
            this.prefixes.add(oreName);
        } else {
            this.prefixes = new ObjectArrayList<>(0);
        }
        this.blockClass = blockClass;
        this.getter = getter;
        ModContainer mod = Loader.instance().getIndexedModList().get(domain);
        this.modName = mod == null ? Zairyou.NAME : mod.getName();
        this.modelLayers = 1;
        this.materialAmount = Util.M;
        REGISTRY.put(id, this);
    }

    @Override
    public String getDomain() {
        return domain;
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public String getModName() {
        return modName;
    }

    public Class<?> getBlockClass() {
        return blockClass;
    }

    public G getGetter() {
        return getter;
    }

    @Override
    public int getModelLayers() {
        return modelLayers;
    }

    @Override
    public long getMaterialAmount() {
        return materialAmount;
    }

    @Override
    public List<String> getPrefixes() {
        return prefixes;
    }

    public List<RecipeRegisterCallback<BlockMaterialType<G>>> getRecipes() {
        return this.recipes == null ? ObjectLists.emptyList() : this.recipes;
    }

    public BlockMaterialType<G> modelLayers(int modelLayers) {
        this.modelLayers = modelLayers;
        return this;
    }

    public BlockMaterialType<G> materialAmount(LongUnaryOperator amount) {
        this.materialAmount = amount.applyAsLong(Util.M);
        return this;
    }

    public BlockMaterialType<G> prefix(String prefix) {
        this.prefixes.add(prefix);
        return this;
    }

    public BlockMaterialType<G> prefixAndItself(String prefix) {
        this.prefixes.add(prefix);
        this.prefixes.add(prefix.concat("*"));
        return this;
    }

    public BlockMaterialType<G> recipe(RecipeRegisterCallback<BlockMaterialType<G>> callback) {
        if (this.recipes == null) {
            this.recipes = new ObjectArrayList<>(1);
        }
        this.recipes.add(callback);
        return this;
    }

}
