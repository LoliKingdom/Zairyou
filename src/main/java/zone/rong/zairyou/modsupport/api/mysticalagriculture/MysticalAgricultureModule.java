package zone.rong.zairyou.modsupport.api.mysticalagriculture;

import zone.rong.zairyou.api.module.IZairyouImitation;
import zone.rong.zairyou.api.material.type.ItemMaterialType;

public class MysticalAgricultureModule implements IZairyouImitation {

    public static final ItemMaterialType ESSENCE = new ItemMaterialType("mysticalagriculture", "essence").materialAmount(u -> -1);

    @Override
    public void preInit() {

    }

    @Override
    public void init() {

    }

    @Override
    public void postInit() {

    }

}
