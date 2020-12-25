package zone.rong.zairyou.api.client;

import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
import net.minecraft.client.resources.IResourceManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.ICustomModelLoader;
import net.minecraftforge.client.model.IModel;
import net.minecraftforge.client.model.ModelLoaderRegistry;
import zone.rong.zairyou.Zairyou;

import java.util.Map;

public class ZairyouModelLoader implements ICustomModelLoader {

    private static final Map<String, IModel> internalCache = new Object2ObjectOpenHashMap<>();

    public static void register(String path, IModel model) {
        internalCache.put(path, model);
    }

    @Override
    public void onResourceManagerReload(IResourceManager resourceManager) { }

    @Override
    public boolean accepts(ResourceLocation modelLocation) {
        return modelLocation.getResourceDomain().equals(Zairyou.ID) && internalCache.containsKey(modelLocation.getResourcePath());
    }

    @Override
    public IModel loadModel(ResourceLocation modelLocation) {
        return internalCache.getOrDefault(modelLocation.getResourcePath(), ModelLoaderRegistry.getMissingModel());
    }

}
