package zone.rong.zairyou.api.block.metablock;

import com.google.common.collect.Lists;
import it.unimi.dsi.fastutil.objects.Object2ObjectArrayMap;
import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.block.material.Material;
import zone.rong.zairyou.Zairyou;
import zone.rong.zairyou.api.property.FreezableProperty;

import java.lang.reflect.InvocationTargetException;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Supplier;

public class MutableMetaBlockBuilder<P extends FreezableProperty<C>, C extends Comparable<C>, B extends AbstractMetaBlock<P, C, ?>> {

    private final String registryName;
    private final Class<B> blockClass;
    private final List<C> entries;
    private final Map<Class<?>, Supplier<?>> ctorArguments;

    private BiConsumer<List<C>, B> blockModifiers;

    public MutableMetaBlockBuilder(String registryName, Class<B> blockClass, Material blockMaterial, Class<P> propertyClass) {
        this.registryName = registryName;
        this.blockClass = blockClass;
        this.entries = new ObjectArrayList<>();
        this.ctorArguments = new Object2ObjectArrayMap<>();
        this.ctorArguments.put(Material.class, () -> blockMaterial);
        this.ctorArguments.put(propertyClass, () -> {
            try {
                return propertyClass.newInstance();
            } catch (InstantiationException | IllegalAccessException e) {
                e.printStackTrace();
            }
            return null;
        });
    }

    @SafeVarargs
    public final MutableMetaBlockBuilder<P, C, B> entries(C... entries) {
        Collections.addAll(this.entries, entries);
        return this;
    }

    public final MutableMetaBlockBuilder<P, C, B> entries(Collection<C> entries) {
        this.entries.addAll(entries);
        return this;
    }

    public final MutableMetaBlockBuilder<P, C, B> modify(BiConsumer<List<C>, B> consumer) {
        if (blockModifiers == null) {
            blockModifiers = consumer;
        } else {
            blockModifiers = blockModifiers.andThen(consumer);
        }
        return this;
    }

    public final <T> MutableMetaBlockBuilder<P, C, B> argument(Class<T> clazz, T object) {
        this.ctorArguments.put(clazz, () -> object);
        return this;
    }

    public final <T> MutableMetaBlockBuilder<P, C, B> argument(Class<T> clazz, Supplier<T> object) {
        this.ctorArguments.put(clazz, object);
        return this;
    }

    public Map<C, B> build() {
        Collections.sort(entries);
        final List<List<C>> metaPartitions = Lists.partition(entries, 16);
        final Map<C, B> returnMap = new Object2ObjectOpenHashMap<>(entries.size());
        int count = 0;
        for (List<C> partition : metaPartitions) {
            try {
                B block = blockClass.getConstructor(this.ctorArguments.keySet().toArray(new Class[0])).newInstance(this.ctorArguments.values().stream().map(Supplier::get).toArray());
                partition.forEach(entry -> {
                    if (block.getAllowedTypes().appendEntry(entry)) {
                        returnMap.put(entry, block);
                    } else {
                        throw new RuntimeException("MutableMetaBlockBuilder did not succeed in appending meta entry!");
                    }
                });
                block.alignBlockStateContainer();
                if (blockModifiers != null) {
                    blockModifiers.accept(partition, block); // After alignment as some modifiers query getValidStates
                }
                String name = registryName + "_" + count++;
                block.setRegistryName(Zairyou.ID, name);
            } catch (NoSuchMethodException | InstantiationException | InvocationTargetException | IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        return returnMap;
    }

}
