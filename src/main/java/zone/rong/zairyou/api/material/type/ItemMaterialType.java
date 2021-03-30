package zone.rong.zairyou.api.material.type;

import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import it.unimi.dsi.fastutil.objects.ObjectLists;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.ModContainer;
import net.minecraftforge.registries.IForgeRegistry;
import zone.rong.zairyou.Zairyou;
import zone.rong.zairyou.api.item.MaterialItem;
import zone.rong.zairyou.api.material.Material;
import zone.rong.zairyou.api.util.RecipeUtil;
import zone.rong.zairyou.api.util.Util;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.function.LongUnaryOperator;
import java.util.function.Supplier;

public final class ItemMaterialType implements IMaterialType {

    public static final Map<String, ItemMaterialType> REGISTRY = new Object2ObjectOpenHashMap<>();

    public static final ItemMaterialType COAL = new ItemMaterialType("minecraft", "coal").prefix("gem");
    public static final ItemMaterialType DUST = new ItemMaterialType("minecraft", "dust");
    public static final ItemMaterialType GEM = new ItemMaterialType("minecraft", "gem");
    public static final ItemMaterialType INGOT = new ItemMaterialType("minecraft", "ingot");
    public static final ItemMaterialType NUGGET = new ItemMaterialType("minecraft", "nugget").materialAmount(u -> u / 9);
    public static final ItemMaterialType ROD = new ItemMaterialType("minecraft", "rod").materialAmount(u -> u / 2);

    public static final ItemMaterialType DENSE_PLATE = new ItemMaterialType("gregtech", "dense_plate", "plateDense").materialAmount(u -> u * 9);
    public static final ItemMaterialType DOUBLE_PLATE = new ItemMaterialType("gregtech", "double_plate", "plateDouble").materialAmount(u -> u * 2);
    public static final ItemMaterialType CRUSHED = new ItemMaterialType("gregtech", "crushed").materialAmount(u -> -1);
    public static final ItemMaterialType CENTRIFUGED_CRUSHED = new ItemMaterialType("gregtech", "centrifuged_crushed", "crushedCentrifuged").materialAmount(u -> -1);
    public static final ItemMaterialType DOUBLE_INGOT = new ItemMaterialType("gregtech", "double_ingot", "ingotDouble").materialAmount(u -> u * 2);
    public static final ItemMaterialType FOIL = new ItemMaterialType("gregtech", "foil").materialAmount(u -> u / 4);
    public static final ItemMaterialType GEAR = new ItemMaterialType("gregtech", "gear").materialAmount(u -> u * 4);
    public static final ItemMaterialType HOT_INGOT = new ItemMaterialType("gregtech", "hot_ingot", "ingotHot").materialAmount(u -> u);
    public static final ItemMaterialType LENS = new ItemMaterialType("gregtech", "lens").materialAmount(u -> (u * 3) / 4);
    public static final ItemMaterialType PLATE = new ItemMaterialType("gregtech", "plate").materialAmount(u -> u);
    public static final ItemMaterialType PURIFIED_CRUSHED = new ItemMaterialType("gregtech", "purified_crushed", "crushedPurified").materialAmount(u -> -1);
    public static final ItemMaterialType RING = new ItemMaterialType("gregtech", "ring").materialAmount(u -> u * 9);
    public static final ItemMaterialType SMALL_DUST = new ItemMaterialType("gregtech", "small_dust", "dustSmall").materialAmount(u -> u / 4);
    public static final ItemMaterialType TINY_DUST = new ItemMaterialType("gregtech", "tiny_dust", "dustTiny").materialAmount(u -> u / 9);

    public static final ItemMaterialType AXE_HEAD = new ItemMaterialType("gregtech", "axe_head", "toolHeadAxe*").materialAmount(u -> u * 3);
    public static final ItemMaterialType BUZZSAW_BLADE = new ItemMaterialType("gregtech", "buzzsaw_blade", "toolHeadBuzzsaw*").materialAmount(u -> u * 4);
    public static final ItemMaterialType CHAINSAW_TIP = new ItemMaterialType("gregtech", "chainsaw_tip", "toolHeadChainsaw*").materialAmount(u -> u * 2);
    public static final ItemMaterialType DRILL_BIT = new ItemMaterialType("gregtech", "drill_head", "toolHeadDrill*").materialAmount(u -> u * 4);
    public static final ItemMaterialType FILE_HEAD = new ItemMaterialType("gregtech", "file_head", "toolHeadFile*").materialAmount(u -> u * 2);
    public static final ItemMaterialType HAMMER_HEAD = new ItemMaterialType("gregtech", "hammer_head", "toolHeadHammer*").materialAmount(u -> u * 6);
    public static final ItemMaterialType HOE_BLADE = new ItemMaterialType("gregtech", "hoe_head", "toolHeadHoe*").materialAmount(u -> u * 2);
    public static final ItemMaterialType PICKAXE_HEAD = new ItemMaterialType("gregtech", "pickaxe_head", "toolHeadPickaxe*").materialAmount(u -> u * 3);
    public static final ItemMaterialType SAW_BLADE = new ItemMaterialType("gregtech", "saw_blade", "toolHeadSaw*").materialAmount(u -> u * 2);
    public static final ItemMaterialType SICKLE_BLADE = new ItemMaterialType("gregtech", "sickle_blade", "toolHeadSickle*").materialAmount(u -> u * 3);
    public static final ItemMaterialType SCREWDRIVER_BIT = new ItemMaterialType("gregtech", "screwdriver_bit", "toolHeadScrewdriver*").materialAmount(u -> u);
    public static final ItemMaterialType SHOVEL_HEAD = new ItemMaterialType("gregtech", "shovel_head", "toolHeadShovel*").materialAmount(u -> u);
    public static final ItemMaterialType SWORD_BLADE = new ItemMaterialType("gregtech", "sword_blade", "toolHeadSword*").materialAmount(u -> u * 2);
    public static final ItemMaterialType WRENCH_TIP = new ItemMaterialType("gregtech", "wrench_tip", "toolHeadWrench*").materialAmount(u -> u * 4);

    public static final ItemMaterialType GRINDING_BALL = new ItemMaterialType("enderio", "ball").materialAmount(u -> (u * 5) / 24);

    public static final ItemMaterialType ORE = new ItemMaterialType("tfc", "ore").materialAmount(u -> -1);
    public static final ItemMaterialType ORE_SMALL = new ItemMaterialType("tfc", "oreSmall").materialAmount(u -> -1);
    public static final ItemMaterialType ORE_RICH = new ItemMaterialType("tfc", "oreRich").materialAmount(u -> -1);
    public static final ItemMaterialType ORE_POOR = new ItemMaterialType("tfc", "orePoor").materialAmount(u -> -1);

    /**
     *     BAIT("thermalfoundation", 1, -1, "bait"),
     *     COIL("thermalfoundation", 2, M * 2, "coil"),
     *     CRYSTAL("thermalfoundation", 2, M, "crystal"),
     *     FERTILIZER("thermalfoundation", 1, -1, "fertilizer"),
     *     // GLOB("thermalfoundation", 1, "glob"), BasicItem
     *     // SERVO("thermalfoundation", 1, "servo"), BasicItem
     *     SLAG("thermalfoundation", 1, -1, "slag~&", "crystalSlag~&", "itemSlag~&"),
     */

    public static final ItemMaterialType CHIPPED_GEM = new ItemMaterialType("tfc", "chipped_gem", "gemChipped*").prefix("chipped").materialAmount(u -> u / 4);
    public static final ItemMaterialType FLAWED_GEM = new ItemMaterialType("tfc", "flawed_gem", "gemFlawed*").prefix("flawed").materialAmount(u -> u / 2);
    public static final ItemMaterialType FLAWLESS_GEM = new ItemMaterialType("tfc", "flawless_gem", "gemFlawless*").prefix("flawless").materialAmount(u -> u * 2);
    public static final ItemMaterialType EXQUISITE_GEM = new ItemMaterialType("tfc", "exquisite_gem", "gemExquisite*").prefix("exquisite").materialAmount(u -> u * 4);

    public static final ItemMaterialType COIL = new ItemMaterialType("thermalfoundation", "coil").modelLayers(2).materialAmount(u -> u * 2);
    public static final ItemMaterialType CRYSTAL = new ItemMaterialType("thermalfoundation", "crystal").materialAmount(u -> u);

    static {

        DUST.recipe((r, t, m) -> {
            final ItemStack dustStack = m.getItem(t, false);
            final ItemStack tinyDustStack = m.getItem(ItemMaterialType.TINY_DUST, false);
            r.register(RecipeUtil.addShaped(String.format("%s_small_dusts_to_dust", m.getName()), false,
                    dustStack, "xx", "xx", 'x', m.getItem(ItemMaterialType.SMALL_DUST, false)));
            r.register(RecipeUtil.addShapeless(String.format("%s_tiny_dusts_to_dust", m.getName()),
                    dustStack,
                    tinyDustStack, tinyDustStack, tinyDustStack, tinyDustStack, tinyDustStack, tinyDustStack, tinyDustStack, tinyDustStack, tinyDustStack));
        });

        INGOT.recipe((r, t, m) -> {
            final ItemStack ingotStack = m.getItem(t, false);
            final ItemStack nuggetStack = m.getItem(NUGGET, false);
            r.register(RecipeUtil.addShapeless(String.format("%s_nugget_to_ingot", m.getName()),
                    ingotStack,
                    nuggetStack, nuggetStack, nuggetStack, nuggetStack, nuggetStack, nuggetStack, nuggetStack, nuggetStack, nuggetStack
            ));
            if (m.hasType(DUST)) {
                RecipeUtil.addSmelting(m.getItem(DUST, false), ingotStack, 0.5F);
            }
        });

        NUGGET.recipe((r, t, m) -> {
            final ItemStack nuggetStack = m.getStack(t, 9);
            final ItemStack ingotStack = m.getItem(INGOT, false);
            r.register(RecipeUtil.addShapeless(String.format("%s_ingot_to_nuggets", m.getName()), nuggetStack, ingotStack));
        });

        TINY_DUST.recipe((r, t, m) -> {
            final ItemStack dustStack = m.getItem(t, false);
            r.register(RecipeUtil.addShapeless(String.format("%s_dust_to_tiny_dusts", m.getName()), m.getStack(ItemMaterialType.TINY_DUST, 9), dustStack));
        });

        GRINDING_BALL.recipe((r, t, m) -> r.register(RecipeUtil.addShaped(String.format("%s_grinding_ball", m.getName()), false, m.getStack(t, 24),
                " x ", "xxx", " x ", 'x', m.getItem(INGOT, false))));

        COIL.recipe((r, t, m) -> r.register(RecipeUtil.addShaped(String.format("%s_coil", m.getName()), true, m.getItem(t, false),
                "x  ", " r ", "  x", 'x', m.getItem(INGOT, false), 'r', Items.REDSTONE)));

    }

    private final String domain, id, modName;
    private final List<String> prefixes = new ObjectArrayList<>(1); // ToDo

    private int modelLayers;
    private long materialAmount;
    private Function<Material, ? extends MaterialItem> itemSupplier;
    private List<RecipeRegisterCallback<ItemMaterialType>> recipes;

    public ItemMaterialType(String domain, String id) {
        this(domain, id, id);
    }

    public ItemMaterialType(String domain, String id, String oreName) {
        this.domain = domain;
        this.id = id;
        this.prefixes.add(oreName);
        ModContainer mod = Loader.instance().getIndexedModList().get(domain);
        this.modName = mod == null ? Zairyou.NAME : mod.getName();
        this.modelLayers = 1;
        this.materialAmount = Util.M;
        this.itemSupplier = m -> new MaterialItem(m, this);
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

    public Function<Material, ? extends MaterialItem> getItemSupplier() {
        return itemSupplier;
    }

    public List<RecipeRegisterCallback<ItemMaterialType>> getRecipes() {
        return this.recipes == null ? ObjectLists.emptyList() : this.recipes;
    }

    public ItemMaterialType modelLayers(int modelLayers) {
        this.modelLayers = modelLayers;
        return this;
    }

    public ItemMaterialType materialAmount(LongUnaryOperator amount) {
        this.materialAmount = amount.applyAsLong(Util.M);
        return this;
    }

    public ItemMaterialType prefix(String prefix) {
        this.prefixes.add(prefix);
        return this;
    }

    public ItemMaterialType prefixAndItself(String prefix) {
        this.prefixes.add(prefix);
        this.prefixes.add(prefix.concat("*"));
        return this;
    }

    public ItemMaterialType setItemSupplier(Function<Material, ? extends MaterialItem> itemSupplier) {
        this.itemSupplier = itemSupplier;
        return this;
    }

    public ItemMaterialType recipe(RecipeRegisterCallback<ItemMaterialType> callback) {
        if (this.recipes == null) {
            this.recipes = new ObjectArrayList<>(1);
        }
        this.recipes.add(callback);
        return this;
    }

}
