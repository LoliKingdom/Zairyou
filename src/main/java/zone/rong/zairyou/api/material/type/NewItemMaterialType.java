package zone.rong.zairyou.api.material.type;

import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.item.crafting.IRecipe;
import net.minecraftforge.oredict.ShapedOreRecipe;
import net.minecraftforge.oredict.ShapelessOreRecipe;
import zone.rong.zairyou.api.material.Material;
import zone.rong.zairyou.api.util.Util;

import java.util.List;
import java.util.function.Function;
import java.util.function.LongUnaryOperator;
import java.util.stream.Collectors;

public final class NewItemMaterialType {

    final String domain, id;

    final int modelLayers;
    final long materialAmount;

    final List<String> prefixes;
    final List<Function<Material, ? extends IRecipe>> recipes;

    NewItemMaterialType(String domain, String id, int modelLayers, long materialAmount, List<String> prefixes, List<Function<Material, ? extends IRecipe>> recipes) {
        this.domain = domain;
        this.id = id;
        this.modelLayers = modelLayers;
        this.materialAmount = materialAmount;
        this.prefixes = prefixes;
        this.recipes = recipes;
    }

    public String getDomain() {
        return domain;
    }

    public String getId() {
        return id;
    }

    public int getModelLayers() {
        return modelLayers;
    }

    public long getMaterialAmount() {
        return materialAmount;
    }

    public List<String> getPrefixes() {
        return prefixes;
    }

    public List<IRecipe> getRecipes(Material material) {
        return recipes.stream().map(f -> f.apply(material)).collect(Collectors.toList());
    }

    public static class Builder {

        final String domain, id;

        int modelLayers = 1;
        long materialAmount = Util.M;

        List<String> prefixes = new ObjectArrayList<>(0);
        List<Function<Material, ? extends IRecipe>> recipes = new ObjectArrayList<>(0);

        public Builder(String domain, String id) {
            this.domain = domain;
            this.id = id;
        }

        public Builder modelLayers(int modelLayers) {
            this.modelLayers = modelLayers;
            return this;
        }

        public Builder materialAmount(LongUnaryOperator amount) {
            this.materialAmount = amount.applyAsLong(Util.M);
            return this;
        }

        public Builder shapelessRecipe(Function<Material, ShapelessOreRecipe> function) {
            this.recipes.add(function);
            return this;
        }

        public Builder shapedRecipe(Function<Material, ShapedOreRecipe> function) {
            this.recipes.add(function);
            return this;
        }

        public Builder recipe(Function<Material, IRecipe> function) {
            this.recipes.add(function);
            return this;
        }

        public NewItemMaterialType build() {
            return new NewItemMaterialType(this.domain, this.id, this.modelLayers, this.materialAmount, this.prefixes, this.recipes);
        }

    }

}
