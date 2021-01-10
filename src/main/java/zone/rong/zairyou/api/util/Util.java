package zone.rong.zairyou.api.util;

import java.util.EnumMap;
import java.util.Map;
import java.util.function.Function;
import java.util.function.UnaryOperator;
import java.util.stream.Collectors;

public class Util {

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
