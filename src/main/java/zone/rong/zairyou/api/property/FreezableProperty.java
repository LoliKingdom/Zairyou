package zone.rong.zairyou.api.property;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import net.minecraft.block.properties.PropertyHelper;

import java.util.ArrayList;
import java.util.List;

public abstract class FreezableProperty<T extends Comparable<T>> extends PropertyHelper<T> {

    private boolean frozen = false;

    private final List<T> allowedTypes;

    private ImmutableList<T> allowedTypesLocked;

    @SafeVarargs
    public FreezableProperty(String name, Class<T> clazz, T... startingEntries) {
        super(name, clazz);
        this.allowedTypes = Lists.newArrayList(startingEntries);
    }

    public FreezableProperty(String name, Class<T> clazz) {
        super(name, clazz);
        this.allowedTypes = new ArrayList<>();
    }

    public boolean appendEntry(T entry) {
        if (frozen || allowedTypes.size() == 16) {
            return false;
        }
        this.allowedTypes.add(entry);
        return true;
    }

    public List<T> getValues() {
        return allowedTypes;
    }

    @Override
    public ImmutableList<T> getAllowedValues() {
        if (this.allowedTypesLocked == null) {
            this.allowedTypesLocked = ImmutableList.copyOf(allowedTypes);
            frozen = true;
        }
        return this.allowedTypesLocked;
    }

}
