package zone.rong.zairyou.api.material.type;

import com.google.common.base.CaseFormat;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import zone.rong.zairyou.Zairyou;

import java.util.Locale;

public enum MaterialType {

    INGOT(Zairyou.ID, new ModelResourceLocation(Zairyou.ID + ":ingot", "inventory"), false),
    DUST(Zairyou.ID, new ModelResourceLocation(Zairyou.ID + ":dust", "inventory"), false),

    COIL("thermalfoundation", new ModelResourceLocation(Zairyou.ID + ":coil", "inventory"), true),
    FERTILIZER("thermalfoundation", new ModelResourceLocation(Zairyou.ID + ":fertilizer", "inventory"), false),
    SERVO("thermalfoundation", new ModelResourceLocation(Zairyou.ID + ":servo", "inventory"), false);

    private final String modId;
    private final ModelResourceLocation textureLocation;
    private final boolean hasTextureLayers;

    MaterialType(String modId, ModelResourceLocation textureLocation, boolean hasTextureLayers) {
        this.modId = modId;
        this.textureLocation = textureLocation;
        this.hasTextureLayers = hasTextureLayers;
    }

    public String getModID() {
        return modId;
    }

    public String getTranslationKey() {
        return String.join(".", modId, toString(), "name");
    }

    public ModelResourceLocation getTextureLocation() {
        return textureLocation;
    }

    public boolean hasOverlayTexture() {
        return hasTextureLayers;
    }

    @Override
    public String toString() {
        return this.name().toLowerCase(Locale.ROOT);
    }

    public String toCamelString() {
        return CaseFormat.UPPER_UNDERSCORE.to(CaseFormat.LOWER_CAMEL, this.name());
    }

}
