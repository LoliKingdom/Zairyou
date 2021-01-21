package zone.rong.zairyou.modsupport.api.tfc;

import zone.rong.zairyou.api.material.Material;

public class TFCModule {

    public static void init() {
        Material.registerAppender(TFCMaterial.class, TFCMaterial::new);
    }

}
