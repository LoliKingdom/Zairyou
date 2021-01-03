package zone.rong.zairyou.api.util;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraftforge.oredict.ShapedOreRecipe;
import zone.rong.zairyou.Zairyou;

public class RecipeUtil {

    private static final FurnaceRecipes FURNACE_INSTANCE = FurnaceRecipes.instance();
    private static final short MAX_META = Short.MAX_VALUE; // 32767

    private RecipeUtil() {}

    public static ShapedOreRecipe addShaped(String name, boolean mirrored, ItemStack result, Object... inputs) {
        ShapedOreRecipe recipe = new ShapedOreRecipe(null, result, inputs);
        recipe.setRegistryName(Zairyou.ID, name);
        recipe.setMirrored(mirrored);
        return recipe;
    }

    public static ShapedOreRecipe addShaped(String name, boolean mirrored, Item result, Object... inputs) {
        return addShaped(name, mirrored, new ItemStack(result), inputs);
    }

    public static ShapedOreRecipe addShaped(String name, boolean mirrored, Block result, Object... inputs) {
        return addShaped(name, mirrored, new ItemStack(result), inputs);
    }

    public static void addSmelting(ItemStack input, ItemStack output, float xp) {
        if (input.getMetadata() == 0) {
            input.setItemDamage(MAX_META);
        }
        FURNACE_INSTANCE.addSmeltingRecipe(input, output, xp);
    }

    public static void addSmelting(Item input, ItemStack output, float xp) {
        FURNACE_INSTANCE.addSmelting(input, output, xp);
    }

    public static void addSmelting(Block input, ItemStack output, float xp) {
        FURNACE_INSTANCE.addSmeltingRecipeForBlock(input, output, xp);
    }

}
