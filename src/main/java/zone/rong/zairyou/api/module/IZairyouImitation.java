package zone.rong.zairyou.api.module;

/**
 * A form of module that, instead of supporting a certain mod, copies a functionality of a mod for de-deduplication.
 *
 * Any classes extending this interface should NOT have any mod dependency.
 */
public interface IZairyouImitation extends IZairyouModule {

    default boolean canLoad() {
        return true; // Config(?)
    }

}
