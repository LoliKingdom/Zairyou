package zone.rong.zairyou.api.item;

import com.google.common.collect.Lists;
import it.unimi.dsi.fastutil.objects.Object2ObjectMap;
import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
import net.minecraft.client.renderer.block.model.ModelBakery;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.ModelBakeEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.ModContainer;
import zone.rong.zairyou.Zairyou;
import zone.rong.zairyou.api.client.Bakery;
import zone.rong.zairyou.api.client.IModelOverride;
import zone.rong.zairyou.api.client.RenderUtils;

import javax.annotation.Nullable;
import java.util.Set;

public class BasicItem extends Item implements IModelOverride {

    public static final Object2ObjectMap<ResourceLocation, BasicItem> REGISTRY = new Object2ObjectOpenHashMap<>();

    public static BasicItem of(String modId, String name, ResourceLocation... textures) {
        return new BasicItem(modId, name, textures);
    }

    public static BasicItem of(String name, ResourceLocation... textures) {
        return of(Zairyou.ID, name, textures);
    }

    public static BasicItem of(String modId, String name, String... textures) {
        final ResourceLocation[] locations = new ResourceLocation[textures.length];
        for (int i = 0; i < textures.length; i++) {
            locations[i] = new ResourceLocation(Zairyou.ID, "items/basic/".concat(textures[i]));
        }
        return of(modId, name, locations);
    }

    private final String modName;
    private final ResourceLocation[] textures;

    private BasicItem(String modId, String name, ResourceLocation... textures) {
        ModContainer mod = Loader.instance().getIndexedModList().get(modId);
        this.modName = mod == null ? Zairyou.NAME : mod.getName();
        this.textures = textures;
        this.setRegistryName(Zairyou.ID, name);
        this.setUnlocalizedName(String.join(".", modId, name));
        REGISTRY.put(this.getRegistryName(), this);
    }

    @Nullable
    @Override
    public String getCreatorModId(ItemStack stack) {
        return modName;
    }

    @Override
    public void addTextures(Set<ResourceLocation> textures) {
        textures.addAll(Lists.newArrayList(this.textures));
    }

    @Override
    public void onModelRegister() {
        final ModelResourceLocation location = RenderUtils.getSimpleModelLocation(this);
        ModelBakery.registerItemVariants(this, location);
        ModelLoader.setCustomMeshDefinition(this, stack -> location);
    }

    @Override
    public void onModelBake(ModelBakeEvent event) {
        event.getModelRegistry().putObject(RenderUtils.getSimpleModelLocation(this),
                Bakery.INSTANCE.getItemDepartment()
                        .template(Bakery.ModelType.NORMAL_ITEM)
                        .prepareTextures("layer", this.textures)
                        .bake().take());
    }
}
