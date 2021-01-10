package zone.rong.zairyou;

import net.minecraftforge.common.ForgeVersion;
import net.minecraftforge.fml.relauncher.IFMLLoadingPlugin;

import java.util.Map;

@IFMLLoadingPlugin.MCVersion(ForgeVersion.mcVersion)
@IFMLLoadingPlugin.SortingIndex(Integer.MIN_VALUE)
@IFMLLoadingPlugin.Name("Zairyou")
public class ZairyouCoreMod implements IFMLLoadingPlugin {

    // TODO
    public ZairyouCoreMod() {
        // MixinBootstrap.init();
        // Mixins.addConfiguration("mixins.zairyou.json");
    }

    @Override
    public String[] getASMTransformerClass() {
        return null;
    }

    @Override
    public String getModContainerClass() {
        return null;
    }

    @Override
    public String getSetupClass() {
        return null;
    }

    @Override
    public void injectData(Map<String, Object> data) { }

    @Override
    public String getAccessTransformerClass() {
        return null;
    }

}