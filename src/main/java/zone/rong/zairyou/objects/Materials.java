package zone.rong.zairyou.objects;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import net.minecraft.init.PotionTypes;
import net.minecraft.item.EnumRarity;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.PotionType;
import net.minecraft.potion.PotionUtils;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.registries.IForgeRegistry;
import zone.rong.zairyou.api.fluid.DeprecatedPotionFluid;
import zone.rong.zairyou.api.fluid.FluidType;
import zone.rong.zairyou.api.fluid.PotionFluid;
import zone.rong.zairyou.api.material.Material;
import zone.rong.zairyou.api.material.type.ItemMaterialType;

import java.util.EnumMap;
import java.util.Map;

import static zone.rong.zairyou.api.material.Material.of;
import static zone.rong.zairyou.api.fluid.FluidType.*;
import static zone.rong.zairyou.api.material.type.ItemMaterialType.*;

public class Materials {

    private static final ItemMaterialType[] METAL_TYPES = { DUST, SMALL_DUST, TINY_DUST, INGOT, NUGGET };
    private static final ItemMaterialType[] DUSTS = { DUST, SMALL_DUST, TINY_DUST };

    /*

		fluidPotion = new FluidPotion("potion", "thermalfoundation", "potion.effect.").setLuminosity(3).setDensity(500).setViscosity(1500).setRarity(EnumRarity.UNCOMMON);
		fluidPotionSplash = new FluidPotion("potion_splash", "thermalfoundation", "splash_potion.effect.").setLuminosity(3).setDensity(500).setViscosity(1500).setRarity(EnumRarity.UNCOMMON);
		fluidPotionLingering = new FluidPotion("potion_lingering", "thermalfoundation", "lingering_potion.effect.").setLuminosity(3).setDensity(500).setViscosity(1500).setRarity(EnumRarity.UNCOMMON);

		fluidGlowstone = new FluidCore("glowstone", "thermalfoundation").setLuminosity(15).setDensity(-500).setViscosity(100).setGaseous(true).setRarity(EnumRarity.UNCOMMON);
		fluidEnder = new FluidCore("ender", "thermalfoundation").setLuminosity(3).setDensity(4000).setViscosity(2500).setRarity(EnumRarity.UNCOMMON);
		fluidPyrotheum = new FluidCore("pyrotheum", "thermalfoundation").setLuminosity(15).setDensity(2000).setViscosity(1200).setTemperature(4000).setRarity(EnumRarity.RARE);
		fluidCryotheum = new FluidCore("cryotheum", "thermalfoundation").setDensity(4000).setViscosity(4000).setTemperature(50).setRarity(EnumRarity.RARE);
		fluidAerotheum = new FluidCore("aerotheum", "thermalfoundation").setDensity(-800).setViscosity(100).setGaseous(true).setRarity(EnumRarity.RARE);
		fluidPetrotheum = new FluidCore("petrotheum", "thermalfoundation").setDensity(4000).setViscosity(1500).setTemperature(350).setRarity(EnumRarity.RARE);
		fluidMana = new FluidCore("mana", "thermalfoundation").setLuminosity(15).setDensity(600).setViscosity(6000).setTemperature(350).setRarity(EnumRarity.EPIC);
     */

    public static final Material AIR = of("air", 0x58A7A5).fluid(LIQUID, fluid -> fluid.temperature(79).customName("liquid_air")).fluid(GASEOUS, fluid -> fluid.customTranslation().temperature(290));
    public static final Material CHARCOAL = of("charcoal", 0x4C4335).types(DUSTS);
    public static final Material COAL = of("coal", 0x464646).ore().types(DUSTS).fluid(LIQUID, fluid -> fluid.still("blocks/fluids/coal_still").flow("blocks/fluids/coal_flow").noTint().customTranslation().density(900).viscosity(2000));
    public static final Material COKE = of("coke", 0x4C4335).types(DUSTS, ItemMaterialType.COAL);
    public static final Material COPPER = of("copper", 0xFF7400).ore().types(METAL_TYPES).fluid(MOLTEN, fluid -> fluid.temperature(1385)).tools(1, 144, 5.0F, 1.5F, -3.2F, 8, tools -> tools.axe().hoe().pickaxe().shovel().sword());
    public static final Material ELECTRUM = of("electrum", 0xFFFF64).types(METAL_TYPES, COIL).fluid(MOLTEN, fluid -> fluid.temperature(1337));
    public static final Material IRON = of("iron", 0xAAAAAA).ore().types(DUSTS, BUZZSAW_BLADE, SAW_BLADE).fluid(MOLTEN, fluid -> fluid.temperature(1803));
    public static final Material GOLD = of("gold", 0xFFFF00).ore().types(DUSTS, COIL).fluid(MOLTEN, fluid -> fluid.temperature(1337));
    public static final Material REDSTONE = of("redstone", 0xC80000).ore().types(SERVO, CRYSTAL).noTint(SERVO).texture(SERVO, "items/servo/redstone", 0).fluid(MOLTEN, fluid -> fluid.noTint().customTranslation().still("blocks/fluids/redstone_still").flow("blocks/fluids/redstone_flow").luminosity(7).density(1200).viscosity(1500).rarity(EnumRarity.UNCOMMON));
    public static final Material GLOWSTONE = of("glowstone", 0xD0B809).type(CRYSTAL).fluid(MOLTEN, fluid -> fluid.noTint().customTranslation().still("blocks/fluids/glowstone_still").flow("blocks/fluids/glowstone_flow").luminosity(15).density(-500).viscosity(100).gasLike().rarity(EnumRarity.UNCOMMON));
    public static final Material SILVER = of("silver", 0xC0C0C0).ore().types(METAL_TYPES, COIL).fluid(MOLTEN, fluid -> fluid.temperature(1235));
    public static final Material OBSIDIAN = of("obsidian", 0x211B2E).types(DUSTS, ROD);
    public static final Material WOOD = of("wood", 0xE68821).types(DUSTS);

    public static final Material AEROTHEUM = of("aerotheum", 0xD5D181).types(DUSTS).fluid(MOLTEN, fluid -> fluid.still("blocks/fluids/aerotheum_still").flow("blocks/fluids/aerotheum_flow").noTint().density(-800).viscosity(100).gasLike().rarity(EnumRarity.RARE));
    public static final Material CRYOTHEUM = of("cryotheum", 0x49EFFF).types(DUSTS).fluid(MOLTEN, fluid -> fluid.still("blocks/fluids/cryotheum_still").flow("blocks/fluids/cryotheum_flow").noTint().density(4000).viscosity(4000).temperature(50).rarity(EnumRarity.RARE));
    public static final Material PETROTHEUM = of("petrotheum", 0x6E5A5D).types(DUSTS).fluid(MOLTEN, fluid -> fluid.still("blocks/fluids/petrotheum_still").flow("blocks/fluids/petrotheum_flow").noTint().density(4000).viscosity(1500).temperature(350).rarity(EnumRarity.RARE));
    public static final Material PYROTHEUM = of("pyrotheum", 0xE56000).types(DUSTS).fluid(MOLTEN, fluid -> fluid.still("blocks/fluids/pyrotheum_still").flow("blocks/fluids/pyrotheum_flow").noTint().luminosity(15).density(2000).viscosity(1200).temperature(4000).rarity(EnumRarity.RARE));
    public static final Material BLIZZ = of("blizz", 0x211B2E).types(DUSTS, ROD);
    public static final Material BLITZ = of("blitz", 0x211B2E).types(DUSTS, ROD);
    public static final Material BASALZ = of("basalz", 0x211B2E).types(DUSTS, ROD);
    public static final Material CINNABAR = of("cinnabar", 0xBF4538).type(CRYSTAL);

    public static final Material NITER = of("niter", 0xFFC8C8).types(DUSTS);
    public static final Material SULFUR = of("sulfur", 0xC8C800).types(DUSTS);

    public static final Material TAR = of("tar", 0x2E2E2E);
    // public static final Material ROSIN = of("rosin", 0xE68821).types(GLOB);

    public static final Material ENDER_EYE = of("ender_eye", 0xFFC8C8).types(DUSTS);
    public static final Material ENDER_PEARL = of("ender_pearl", 0xFFC8C8).types(DUSTS, CRYSTAL).fluid(MOLTEN, fluid -> fluid.customName("ender").still("blocks/fluids/ender_still").flow("blocks/fluids/ender_flow").noTint().luminosity(3).density(4000).viscosity(2500).rarity(EnumRarity.UNCOMMON));

    public static final Material PRIMAL_MANA = of("mana", 0x065E8E).types(DUSTS, INGOT).fluid(MOLTEN, fluid -> fluid.still("blocks/fluids/mana_still").flow("blocks/fluids/mana_flow").noTint().luminosity(15).density(600).viscosity(6000).rarity(EnumRarity.EPIC));

    /* Pure Fluid Materials */
    public static final Material BIOCRUDE = of("biocrude", 0x346217).fluid(LIQUID, fluid -> fluid.still("blocks/fluids/biocrude_still").flow("blocks/fluids/biocrude_flow").noTint().customTranslation().density(1500).viscosity(2500));
    public static final Material CREOSOTE = of("creosote", 0x804000).fluid(LIQUID, fluid -> fluid.still("blocks/fluids/creosote_still").flow("blocks/fluids/creosote_flow").noTint().customTranslation().density(1100).viscosity(2000));
    public static final Material CRUDE_OIL = of("crude_oil", 0x666666).fluid(LIQUID, fluid -> fluid.still("blocks/fluids/crude_oil_still").flow("blocks/fluids/crude_oil_flow").noTint().customTranslation().density(900).viscosity(2000));
    public static final Material EXPERIENCE = of("experience", 0x28CE0A).fluid(LIQUID, fluid -> fluid.still("blocks/fluids/experience_still").flow("blocks/fluids/experience_flow").noTint().luminosity(12).density(-200).viscosity(200).rarity(EnumRarity.UNCOMMON));
    public static final Material MUSHROOM_STEW = of("mushroom_stew", 0xB79474).fluid(LIQUID, fluid -> fluid.still("blocks/fluids/mushroom_stew_still").flow("blocks/fluids/mushroom_stew_flow").noTint().customTranslation().density(2000).viscosity(2000));
    public static final Material REFINED_BIOFUEL = of("refined_biofuel", 0x7FA814).fluid(LIQUID, fluid -> fluid.still("blocks/fluids/refined_biofuel_still").flow("blocks/fluids/refined_biofuel_flow").noTint().customTranslation().density(750).viscosity(800));
    public static final Material REFINED_OIL = of("refined_oil", 0x666666).fluid(LIQUID, fluid -> fluid.still("blocks/fluids/refined_oil_still").flow("blocks/fluids/refined_oil_flow").noTint().customTranslation().density(800).viscosity(1400));
    public static final Material REFINED_FUEL = of("refined_fuel", 0xFFFF00).fluid(LIQUID, fluid -> fluid.still("blocks/fluids/refined_fuel_still").flow("blocks/fluids/refined_fuel_flow").noTint().customTranslation().density(750).viscosity(800));
    public static final Material RESIN = of("resin", 0xA66E00).fluid(LIQUID, fluid -> fluid.still("blocks/fluids/resin_still").flow("blocks/fluids/resin_flow").noTint().customTranslation().density(900).viscosity(3000));
    public static final Material SAP = of("sap", 0x946426).fluid(LIQUID, fluid -> fluid.still("blocks/fluids/sap_still").flow("blocks/fluids/sap_flow").noTint().customTranslation().density(1050).viscosity(1500));
    public static final Material SEED_OIL = of("seed_oil", 0xC4FF00).fluid(LIQUID, fluid -> fluid.still("blocks/fluids/seed_oil_still").flow("blocks/fluids/seed_oil_flow").noTint().customTranslation().density(950).viscosity(1300));
    // Fixme UNUSED: public static final Material SYRUP = of("syrup", 0x946426).fluid(LIQUID, fluid -> fluid.still("blocks/fluids/sap_still").flow("blocks/fluids/sap_flow").noTint().density(1400).viscosity(2500));
    public static final Material STEAM = of("steam", 0xFFFFFF).fluid(GASEOUS, fluid -> fluid.still("blocks/fluids/steam_still").flow("blocks/fluids/steam_flow").noTint().customTranslation().viscosity(200).temperature(750));
    public static final Material TREE_OIL = of("tree_oil", 0x8F7638).fluid(LIQUID, fluid -> fluid.still("blocks/fluids/tree_oil_still").flow("blocks/fluids/tree_oil_flow").noTint().customTranslation().density(900).viscosity(1200));

    /* Marker/Pseudo Materials - TODO: Match colours with electric tier defaults(?) */
    public static final Material RICH = of("rich").types(FERTILIZER, SLAG, BAIT).noTints(FERTILIZER, SLAG).texture(FERTILIZER, "items/fertilizer/rich", 0).texture(SLAG, "items/slag/rich", 0);
    public static final Material FLUX = of("flux").types(FERTILIZER, BAIT).noTint(FERTILIZER).texture(FERTILIZER, "items/fertilizer/flux", 0);

    public static void init() {
        Material.BASIC.types(FERTILIZER, SLAG, BAIT).noTints(FERTILIZER, SLAG).texture(FERTILIZER, "items/fertilizer/basic", 0).texture(SLAG, "items/slag/basic", 0);
        Potions.init();
        // POTION.getFluid(LIQUID).setBlock(new PotionFluidBlock((PotionFluid) POTION.getFluid(LIQUID), LIQUID));
        // SPLASH_POTION.getFluid(LIQUID).setBlock(new PotionFluidBlock((PotionFluid) SPLASH_POTION.getFluid(LIQUID), LIQUID));
        // LINGERING_POTION.getFluid(LIQUID).setBlock(new PotionFluidBlock((PotionFluid) LINGERING_POTION.getFluid(LIQUID), LIQUID));
    }

    public static class Potions {

        /** Potions **/
        public static final Material NORMAL = of("potion", 0xF800F8).fluid(LIQUID, new PotionFluid("potion", "potion.effect.").setLuminosity(3).setDensity(500).setViscosity(1500).setRarity(EnumRarity.UNCOMMON), false);
        public static final Material SPLASH = of("potion_splash", 0xF800F8).fluid(LIQUID, new PotionFluid("potion_splash", "splash_potion.effect.").setLuminosity(3).setDensity(500).setViscosity(1500).setRarity(EnumRarity.UNCOMMON), false);
        public static final Material LINGERING = of("potion_lingering", 0xF800F8).fluid(LIQUID, new PotionFluid("potion_lingering", "lingering_potion.effect.").setLuminosity(3).setDensity(500).setViscosity(1500).setRarity(EnumRarity.UNCOMMON), false);

        public static void init() { }

        public static FluidStack getNormalFluid(PotionType type, int amount) {
            if (type == null || type == PotionTypes.EMPTY) {
                return null;
            }
            if (type == PotionTypes.WATER) {
                return new FluidStack(FluidRegistry.WATER, amount);
            }
            FluidStack stack = NORMAL.getStack(LIQUID, amount);
            return topUp(stack, type);
        }

        public static FluidStack getSplashFluid(PotionType type, int amount) {
            if (type == null || type == PotionTypes.EMPTY) {
                return null;
            }
            if (type == PotionTypes.WATER) {
                return new FluidStack(FluidRegistry.WATER, amount);
            }
            FluidStack stack = SPLASH.getStack(LIQUID, amount);
            return topUp(stack, type);
        }

        public static FluidStack getLingeringFluid(PotionType type, int amount) {
            if (type == null || type == PotionTypes.EMPTY) {
                return null;
            }
            if (type == PotionTypes.WATER) {
                return new FluidStack(FluidRegistry.WATER, amount);
            }
            FluidStack stack = LINGERING.getStack(LIQUID, amount);
            return topUp(stack, type);
        }

        @SuppressWarnings("ConstantConditions")
        private static FluidStack topUp(FluidStack stack, PotionType type) {
            ResourceLocation resourcelocation = PotionType.REGISTRY.getNameForObject(type);
            /* NOTE: This can actually happen. */
            if (resourcelocation == null) {
                return null;
            }
            if (stack.tag == null) {
                stack.tag = new NBTTagCompound();
            }
            stack.tag.setString("Potion", resourcelocation.toString());
            return stack;
        }

        public static boolean isPotion(FluidStack stack) {
            return stack.getFluid().getName().equals(NORMAL.getName());
        }

        public static boolean isSplashPotion(FluidStack stack) {
            return stack.getFluid().getName().equals(SPLASH.getName());
        }

        public static boolean isLingeringPotion(FluidStack stack) {
            return stack.getFluid().getName().equals(LINGERING.getName());
        }

    }

    @Deprecated
    public static class DeprecatedPotions {

        private static final Map<PotionFormat, BiMap<PotionType, Material>> potionMaterials = new EnumMap<>(PotionFormat.class);

        static {
            potionMaterials.put(PotionFormat.NORMAL, HashBiMap.create());
            potionMaterials.put(PotionFormat.SPLASH, HashBiMap.create());
            potionMaterials.put(PotionFormat.LINGERING, HashBiMap.create());
        }

        public static void init(IForgeRegistry<PotionType> registry) {
            registry.getValuesCollection().forEach(p -> {
                if (p == PotionTypes.EMPTY) {
                    return;
                }
                for (PotionFormat format : PotionFormat.VALUES) {
                    Material m = Material.of(format.name + "_" + PotionType.REGISTRY.getNameForObject(p).getResourcePath(), PotionUtils.getPotionColor(p));
                    potionMaterials.get(format).put(p, m);
                    m.fluid(LIQUID, new DeprecatedPotionFluid(m, format, p), false);
                    System.out.println("Material: " + m.getName() + " | PotionType: " + PotionType.REGISTRY.getNameForObject(p));
                }
            });
        }

        public static Material getMaterial(PotionFormat format, PotionType type) {
            return potionMaterials.get(format).get(type);
        }

        public static PotionType getPotionType(PotionFormat format, Material material) {
            return potionMaterials.get(format).inverse().get(material);
        }

        public static boolean isPotion(FluidStack stack) {
            return stack.getFluid().getUnlocalizedName().startsWith(PotionFormat.NORMAL.prefix);
        }

        public static boolean isSplashPotion(FluidStack stack) {
            return stack.getFluid().getUnlocalizedName().startsWith(PotionFormat.SPLASH.prefix);
        }

        public static boolean isLingeringPotion(FluidStack stack) {
            return stack.getFluid().getUnlocalizedName().startsWith(PotionFormat.LINGERING.prefix);
        }

        /**
         * Because of ThermalExpansion's stupid formatting. We have to pull this shit.
         */
        public enum PotionFormat {

            NORMAL("potion"),
            SPLASH("splash_potion"),
            LINGERING("lingering_potion");

            public static final PotionFormat[] VALUES;

            static {
                VALUES = values();
            }

            public final String name;
            public final String prefix;

            PotionFormat(String name) {
                this.name = name;
                this.prefix = name.concat(".effect.");
            }

        }

    }

}
