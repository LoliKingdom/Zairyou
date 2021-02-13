package zone.rong.zairyou.api.material.type;

import com.google.common.base.CaseFormat;
import net.minecraft.item.crafting.IRecipe;
import net.minecraftforge.registries.IForgeRegistry;
import zone.rong.zairyou.api.material.Material;
import zone.rong.zairyou.api.util.ITranslatable;

import java.util.List;

public interface IMaterialType extends ITranslatable {

    String getId();

    String getDomain();

    String getModName();

    int getModelLayers();

    long getMaterialAmount();

    List<String> getPrefixes();

    @Override
    default String getTranslationKey() {
        return String.join(".", getDomain(), "material_type", getId(), "name");
    }

    default String[] toCamelStrings() {
        List<String> prefixes = getPrefixes();
        if (prefixes.size() == 0) {
            return new String[] {CaseFormat.LOWER_UNDERSCORE.to(CaseFormat.LOWER_CAMEL, getId())};
        }
        String[] alternates = new String[prefixes.size()];
        for (int i = 0; i < alternates.length; i++) {
            alternates[i] = CaseFormat.LOWER_UNDERSCORE.to(CaseFormat.LOWER_CAMEL, prefixes.get(i));
        }
        return alternates;
    }

    interface RecipeRegisterCallback<T extends IMaterialType> {

        void onRecipeRegister(IForgeRegistry<IRecipe> registry, T materialType, Material material);

    }

}
