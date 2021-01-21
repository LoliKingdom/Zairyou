package zone.rong.zairyou.modsupport.api.tfc;

import net.dries007.tfc.api.types.Metal;
import zone.rong.zairyou.api.material.IMaterialAppender;
import zone.rong.zairyou.api.material.Material;

import static zone.rong.zairyou.api.material.MaterialFlag.*;

public class TFCMaterial implements IMaterialAppender<TFCMaterial> {

    private final Material material;

    private Metal.Tier metalTier;

    public TFCMaterial(Material material) {
        this.material = material;
    }

    public TFCMaterial metal(Metal.Tier metalTier) {
        this.metalTier = metalTier;
        return this;
    }

    @Override
    public void onBuilding(Material material) {
        if (this.material.hasFlag(GENERATE_DEFAULT_METAL_TYPES)) {
            this.metal(this.material.hasFlag(NEEDS_BLAST_FURNACE) ? Metal.Tier.TIER_IV : Metal.Tier.TIER_III);
        }
    }

    @Override
    public Material cast() {
        return material;
    }

}
