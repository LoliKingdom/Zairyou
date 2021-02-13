package zone.rong.zairyou.api.material.type;

import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import it.unimi.dsi.fastutil.objects.ObjectLists;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.ModContainer;
import zone.rong.zairyou.Zairyou;
import zone.rong.zairyou.api.block.IZairyouBlockBuilder;
import zone.rong.zairyou.api.block.StorageBlock;
import zone.rong.zairyou.api.material.Material;
import zone.rong.zairyou.api.ore.OreBlock;
import zone.rong.zairyou.api.ore.OreGrade;
import zone.rong.zairyou.api.util.RecipeUtil;
import zone.rong.zairyou.api.util.Util;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.function.LongUnaryOperator;

// TODO: Extra argument for Block creation
public class BlockMaterialType<B extends IZairyouBlockBuilder<?>> implements IMaterialType {

    public static final Map<String, BlockMaterialType<?>> REGISTRY = new Object2ObjectOpenHashMap<>();

    // BLOCK(Zairyou.ID, 0, "ore"),
    // FRAME(Zairyou.ID, 0, "frame", "frameGt"),
    // ORE(Zairyou.ID, 0, "ore"),
    // STONE(Zairyou.ID, 0, "stone~&");

    public static final BlockMaterialType<StorageBlock.Builder> STORAGE = new BlockMaterialType<>(Zairyou.ID, "block", StorageBlock.Builder::new);
    public static final BlockMaterialType<OreBlock.Builder> ORE = new BlockMaterialType<>(Zairyou.ID, "ore", m -> new OreBlock.Builder(m, OreGrade.NORMAL));
    public static final BlockMaterialType<OreBlock.Builder> ORE_POOR = new BlockMaterialType<>(Zairyou.ID, "orePoor", m -> new OreBlock.Builder(m, OreGrade.POOR));
    public static final BlockMaterialType<OreBlock.Builder> ORE_RICH = new BlockMaterialType<>(Zairyou.ID, "oreRich", m -> new OreBlock.Builder(m, OreGrade.RICH));
    // public static final BlockMaterialType<StorageBlock.Builder> FRAME = new BlockMaterialType<>(Zairyou.ID, "frame", StorageBlock.Builder::new);

    static {
        STORAGE.recipe((r, t, m) -> {
            final ItemStack blockStack = new ItemStack(m.getBlock(t));
            final ItemStack ingotStack = m.getItem(ItemMaterialType.INGOT, false);
            r.register(RecipeUtil.addShapeless(String.format("%s_ingots_to_block", m.getName()), blockStack,
                    ingotStack, ingotStack, ingotStack, ingotStack, ingotStack, ingotStack, ingotStack, ingotStack, ingotStack));
        });
    }

    private final String domain, id, modName;
    private final List<String> prefixes = new ObjectArrayList<>(1); // ToDo
    private final Function<Material, B> blockCreation;

    private int modelLayers;
    private long materialAmount;
    private List<RecipeRegisterCallback<BlockMaterialType<B>>> recipes;

    public BlockMaterialType(String domain, String id, Function<Material, B> blockCreation) {
        this(domain, id, id, blockCreation);
    }

    public BlockMaterialType(String domain, String id, String oreName, Function<Material, B> blockCreation) {
        this.domain = domain;
        this.id = id;
        this.prefixes.add(oreName);
        this.blockCreation = blockCreation;
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

    public B createBlock(Material material) {
        return blockCreation.apply(material);
    }

    public List<RecipeRegisterCallback<BlockMaterialType<B>>> getRecipes() {
        return this.recipes == null ? ObjectLists.emptyList() : this.recipes;
    }

    public BlockMaterialType<B> modelLayers(int modelLayers) {
        this.modelLayers = modelLayers;
        return this;
    }

    public BlockMaterialType<B> materialAmount(LongUnaryOperator amount) {
        this.materialAmount = amount.applyAsLong(Util.M);
        return this;
    }

    public BlockMaterialType<B> prefix(String prefix) {
        this.prefixes.add(prefix);
        return this;
    }

    public BlockMaterialType<B> prefixAndItself(String prefix) {
        this.prefixes.add(prefix);
        this.prefixes.add(prefix.concat("*"));
        return this;
    }

    public BlockMaterialType<B> recipe(RecipeRegisterCallback<BlockMaterialType<B>> callback) {
        if (this.recipes == null) {
            this.recipes = new ObjectArrayList<>(1);
        }
        this.recipes.add(callback);
        return this;
    }

}
