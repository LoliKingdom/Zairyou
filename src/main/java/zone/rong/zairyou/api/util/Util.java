package zone.rong.zairyou.api.util;

import java.util.EnumMap;
import java.util.Map;
import java.util.function.Function;
import java.util.function.UnaryOperator;
import java.util.stream.Collectors;

public class Util {

    /**
     * <p/>
     * This is worth exactly one normal Item.
     * This constant can be divided by many commonly used Numbers such as
     * 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 12, 14, 15, 16, 18, 20, 21, 24, ... 64 or 81
     * without loosing precision and is for that reason used as Unit of Amount.
     * But it is also small enough to be multiplied with larger Numbers.
     * <p/>
     * This is used to determine the amount of Material contained inside a prefixed Ore.
     * For example Nugget = M / 9 as it contains out of 1/9 of an Ingot.
     */
    public static final long M = 3628800;

    /**
     * Fluid per Material Unit
     */
    public static final int L = 144;

    private Util() {}

    public static <K extends Enum<K>, V> EnumMap<K, V> keepEnumMap(Class<K> clazz, Map<K, V> originalMap, UnaryOperator<K> keyMapper, UnaryOperator<V> valueMapper) {
        if (originalMap instanceof EnumMap) {
            return (EnumMap<K, V>) originalMap;
        }
        return new EnumMap<>(originalMap);
    }

    public static <K extends Enum<K>, V, U> EnumMap<K, U> convertValues(Class<K> clazz, Map<K, V> originalMap, UnaryOperator<K> keyMapper, Function<Map.Entry<K, V>, U> valueMapper) {
        return originalMap.entrySet()
                .stream()
                .collect(Collectors.toMap(e -> keyMapper.apply(e.getKey()), valueMapper, (l, r) -> { throw new IllegalArgumentException(); }, () -> new EnumMap<>(clazz)));
    }

}
