package zone.rong.zairyou.api.module;

import net.minecraftforge.fml.common.Loader;
import zone.rong.zairyou.modsupport.api.mysticalagriculture.MysticalAgricultureModule;
import zone.rong.zairyou.modsupport.api.tfc.TFCModule;

public class ModuleInitialization {

    public static void onConstruction() {
        if (Loader.isModLoaded("tfc")) {
            IZairyouModule.register(TFCModule.class, new TFCModule());
        }
        IZairyouModule.register(MysticalAgricultureModule.class, new MysticalAgricultureModule());
    }

}
