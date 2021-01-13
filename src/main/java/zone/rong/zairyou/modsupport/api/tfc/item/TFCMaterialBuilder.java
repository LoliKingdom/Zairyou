package zone.rong.zairyou.modsupport.api.tfc.item;

import net.dries007.tfc.api.types.Metal;
import zone.rong.zairyou.api.material.AbstractMaterialBuilderAppender;
import zone.rong.zairyou.api.material.MaterialBuilder;
import zone.rong.zairyou.api.material.MaterialFlag;

public class TFCMaterialBuilder extends AbstractMaterialBuilderAppender<TFCMaterialBuilder, TFCMaterialViewer> {

    Metal.Tier tier;

    public TFCMaterialBuilder(MaterialBuilder existingBuilder) {
        super(existingBuilder);
    }

    @Override
    public Class<TFCMaterialViewer> getViewerClass() {
        return TFCMaterialViewer.class;
    }

    @Override
    public TFCMaterialViewer building() {
        if (this.tier == null && (existingBuilder.getFlag() & MaterialFlag.NEEDS_BLAST_FURNACE.bit) > 0) {
            this.tier = Metal.Tier.TIER_IV;
        }
        return new TFCMaterialViewer(this);
    }

    public TFCMaterialBuilder tier(Metal.Tier tier) {
        this.tier = tier;
        return this;
    }

}
