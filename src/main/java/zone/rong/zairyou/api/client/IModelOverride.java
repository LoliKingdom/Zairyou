package zone.rong.zairyou.api.client;

import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.ModelBakeEvent;

import java.util.Set;

public interface IModelOverride {

    void addTextures(Set<ResourceLocation> textures);

    void onModelRegister();

    void onModelBake(ModelBakeEvent event);

}
