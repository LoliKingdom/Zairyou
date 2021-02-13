package zone.rong.zairyou.api.material;

import com.google.common.base.Optional;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import net.minecraft.block.properties.PropertyHelper;

import java.util.List;

public class MaterialProperty extends PropertyHelper<Material> {

    public static boolean frozen = false;

    private final List<Material> allowedTypes;
    private ImmutableList<Material> allowedTypesLocked;

    public MaterialProperty(Material... allowedTypes) {
        super("material", Material.class);
        this.allowedTypes = Lists.newArrayList(allowedTypes);
    }

    public boolean appendMaterial(Material material) {
        if (frozen || allowedTypes.size() == 16) {
            return false;
        }
        this.allowedTypes.add(material);
        return true;
    }

    @Override
    public ImmutableList<Material> getAllowedValues() {
        if (this.allowedTypesLocked == null) {
            this.allowedTypesLocked = ImmutableList.copyOf(allowedTypes);
        }
        return this.allowedTypesLocked;
    }

    @Override
    @SuppressWarnings("all")
    public Optional<Material> parseValue(String value) {
        Material material = Material.get(value);
        return material == Material.NONE ? Optional.absent() : Optional.of(material);
    }

    @Override
    public String getName(Material value) {
        return value.getName();
    }

}
