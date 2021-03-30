package zone.rong.zairyou.api.modsupport.tfc;

import com.google.common.collect.ImmutableMap;
import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
import net.dries007.tfc.api.types.Metal;
import net.dries007.tfc.objects.fluids.FluidsTFC;
import net.dries007.tfc.objects.fluids.properties.FluidWrapper;
import zone.rong.zairyou.api.material.MetalMaterial;

import java.lang.reflect.Field;
import java.util.Map;

public class TFCMetalsWorkaround {

    public static final Field allMetalFluidsField;
    public static final Map<Metal, FluidWrapper> allMetalFluids;

    static {
        Field field;
        ImmutableMap<Metal, FluidWrapper> map;
        try {
            field = FluidsTFC.class.getDeclaredField("allMetalFluids");
            field.setAccessible(true);
            map = (ImmutableMap<Metal, FluidWrapper>) field.get(null);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            field = null;
            map = ImmutableMap.of();
            e.printStackTrace();
        }
        allMetalFluidsField = field;
        allMetalFluids = new Object2ObjectOpenHashMap<>(map);
    }

    public static void pre$runAfterBlockRegister(MetalMaterial metalMaterial) {
        allMetalFluids.put(metalMaterial.getRepresentation(), metalMaterial.getMetalFluidWrapper());
    }

    public static void post$runAfterBlockRegister() {
        try {
            allMetalFluidsField.set(null, ImmutableMap.copyOf(allMetalFluids));
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

}
