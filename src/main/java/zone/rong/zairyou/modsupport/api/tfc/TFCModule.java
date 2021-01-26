package zone.rong.zairyou.modsupport.api.tfc;

import net.minecraftforge.fml.common.Loader;
import zone.rong.zairyou.api.IZairyouModule;
import zone.rong.zairyou.api.material.Material;

public class TFCModule implements IZairyouModule {

    @Override
    public boolean canLoad() {
        return Loader.isModLoaded("tfc");
    }

    @Override
    public void preInit() {
        Material.registerAppender(TFCMaterial.class, TFCMaterial::new);
    }

    @Override
    public void init() { }

    @Override
    public void postInit() { }

}
