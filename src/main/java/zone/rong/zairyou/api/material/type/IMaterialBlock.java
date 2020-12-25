package zone.rong.zairyou.api.material.type;

import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.ModelBakeEvent;

import java.util.Set;

public interface IMaterialBlock {

    void addTextures(Set<ResourceLocation> textures);

    void onModelRegister();

    void onModelBake(ModelBakeEvent event);

}
