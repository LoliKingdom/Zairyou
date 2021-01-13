package zone.rong.zairyou.modsupport.api.tfc.item;

import net.dries007.tfc.api.types.Metal;
import zone.rong.zairyou.api.material.AbstractMaterialBuilderAppender;

public class TFCMaterialViewer extends AbstractMaterialBuilderAppender.Viewer<TFCMaterialBuilder> {

    public TFCMaterialViewer(TFCMaterialBuilder appender) {
        super(appender);
    }

    public Metal.Tier getTier() {
        return appender.tier;
    }

}
