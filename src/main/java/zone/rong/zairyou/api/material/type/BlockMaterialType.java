package zone.rong.zairyou.api.material.type;

import com.google.common.base.CaseFormat;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import zone.rong.zairyou.Zairyou;

import java.util.Locale;

public enum BlockMaterialType {

    ORE(Zairyou.ID, "ore", new ModelResourceLocation(Zairyou.ID + ":ore", "inventory"), true);

    private final String modId;
    private final ModelResourceLocation textureLocation;
    private final boolean hasTextureLayers;
    private final String[] prefixes;

    BlockMaterialType(String modId, String prefix, ModelResourceLocation textureLocation, boolean hasTextureLayers) {
        this.modId = modId;
        this.textureLocation = textureLocation;
        this.hasTextureLayers = hasTextureLayers;
        this.prefixes = new String[] { prefix };
    }

    BlockMaterialType(String modId, String prefix, ModelResourceLocation textureLocation, boolean hasTextureLayers, String... alternatePrefixes) {
        this.modId = modId;
        this.textureLocation = textureLocation;
        this.hasTextureLayers = hasTextureLayers;
        this.prefixes = new String[alternatePrefixes.length + 1];
        this.prefixes[0] = prefix;
        System.arraycopy(alternatePrefixes, 0, this.prefixes, 1, alternatePrefixes.length);
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

    public String[] getPrefixes() {
        return prefixes;
    }

    @Override
    public String toString() {
        return this.name().toLowerCase(Locale.ROOT);
    }

    @Deprecated
    public String[] toCamelStrings() {
        if (prefixes.length == 0) {
            return new String[] {CaseFormat.LOWER_UNDERSCORE.to(CaseFormat.LOWER_CAMEL, this.name())};
        }
        String[] alternates = new String[prefixes.length];
        for (int i = 0; i < alternates.length; i++) {
            alternates[i] = CaseFormat.LOWER_UNDERSCORE.to(CaseFormat.LOWER_CAMEL, prefixes[i]);
        }
        return alternates;
    }

}
