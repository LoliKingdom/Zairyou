package zone.rong.zairyou.api.material.type;

import com.google.common.base.CaseFormat;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import zone.rong.zairyou.Zairyou;

import java.util.Locale;

public enum MaterialType {

    INGOT(Zairyou.ID, "ingot", new ModelResourceLocation(Zairyou.ID + ":ingot", "inventory"), false),
    DUST(Zairyou.ID, "dust", new ModelResourceLocation(Zairyou.ID + ":dust", "inventory"), false),

    COIL("thermalfoundation", "coil", new ModelResourceLocation(Zairyou.ID + ":coil", "inventory"), true),
    FERTILIZER("thermalfoundation", "fertilizer", new ModelResourceLocation(Zairyou.ID + ":fertilizer", "inventory"), false),
    SERVO("thermalfoundation", "servo", new ModelResourceLocation(Zairyou.ID + ":servo", "inventory"), false),
    SLAG("thermalfoundation", "slag~&", new ModelResourceLocation(Zairyou.ID + ":slag", "inventory"), false, "crystalSlag~&", "itemSlag~&");

    private final String modId;
    private final ModelResourceLocation textureLocation;
    private final boolean hasTextureLayers;
    private final String[] prefixes;

    MaterialType(String modId, String prefix, ModelResourceLocation textureLocation, boolean hasTextureLayers) {
        this.modId = modId;
        this.textureLocation = textureLocation;
        this.hasTextureLayers = hasTextureLayers;
        this.prefixes = new String[] { prefix };
    }

    MaterialType(String modId, String prefix, ModelResourceLocation textureLocation, boolean hasTextureLayers, String... alternatePrefixes) {
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
