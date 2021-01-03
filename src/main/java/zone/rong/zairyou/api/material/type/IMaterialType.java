package zone.rong.zairyou.api.material.type;

import com.google.common.base.CaseFormat;
import zone.rong.zairyou.api.util.ITranslatable;

public interface IMaterialType extends ITranslatable {

    String name();

    String getModID();

    int getModelLayers();

    String[] getPrefixes();

    default String getTranslationKey() {
        return String.join(".", getModID(), "material_type", toString(), "name");
    }

    default String[] toCamelStrings() {
        String[] prefixes = getPrefixes();
        if (prefixes.length == 0) {
            return new String[] {CaseFormat.LOWER_UNDERSCORE.to(CaseFormat.LOWER_CAMEL, name())};
        }
        String[] alternates = new String[prefixes.length];
        for (int i = 0; i < alternates.length; i++) {
            alternates[i] = CaseFormat.LOWER_UNDERSCORE.to(CaseFormat.LOWER_CAMEL, prefixes[i]);
        }
        return alternates;
    }

}
