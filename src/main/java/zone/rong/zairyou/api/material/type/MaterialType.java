package zone.rong.zairyou.api.material.type;

import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import zone.rong.zairyou.Zairyou;

import java.util.Locale;

public enum MaterialType {

    INGOT(Zairyou.ID, new ModelResourceLocation(Zairyou.ID + ":ingot", "inventory")),
    DUST(Zairyou.ID, new ModelResourceLocation(Zairyou.ID + ":dust", "inventory")),

    FERTILIZER("thermalfoundation", new ModelResourceLocation(Zairyou.ID + ":fertilizer", "inventory")),
    SERVO("thermalfoundation", new ModelResourceLocation(Zairyou.ID + ":servo", "inventory"));

    private final String modId;
    private final ModelResourceLocation textureLocation;

    MaterialType(String modId, ModelResourceLocation textureLocation) {
        this.modId = modId;
        this.textureLocation = textureLocation;
    }

    public String getModID() {
        return Zairyou.ID;
    }

    public ModelResourceLocation getTextureLocation() {
        return textureLocation;
    }

    @Override
    public String toString() {
        return this.name().toLowerCase(Locale.ROOT);
    }

}
