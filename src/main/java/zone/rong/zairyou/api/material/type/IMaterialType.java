package zone.rong.zairyou.api.material.type;

import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import zone.rong.zairyou.api.util.ITranslatable;

public interface IMaterialType extends ITranslatable {

    String getModID();

    ModelResourceLocation getTextureLocation();

    boolean hasOverlayTexture();

    String[] getPrefixes();

}
