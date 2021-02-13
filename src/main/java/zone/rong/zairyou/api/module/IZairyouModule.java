package zone.rong.zairyou.api.module;

import it.unimi.dsi.fastutil.objects.Object2ObjectMap;
import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;

public interface IZairyouModule {

    Object2ObjectMap<Class<?>, IZairyouModule> REGISTRY = new Object2ObjectOpenHashMap<>();

    static <T extends IZairyouModule> void register(Class<T> clazz, T object) {
        REGISTRY.put(clazz, object);
    }

    static <T extends IZairyouModule> T get(Class<T> clazz) {
        return (T) REGISTRY.get(clazz);
    }

    boolean canLoad();

    void preInit();

    void init();

    void postInit();

}
