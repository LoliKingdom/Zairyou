package zone.rong.zairyou.objects;

import net.minecraft.init.Items;
import net.minecraft.init.PotionTypes;
import net.minecraft.item.EnumRarity;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.PotionType;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import zone.rong.zairyou.api.fluid.PotionFluid;
import zone.rong.zairyou.api.material.Material;
import zone.rong.zairyou.api.material.TextureSet;
import zone.rong.zairyou.api.material.type.ItemMaterialType;

import static zone.rong.zairyou.api.material.element.Element.*;
import static zone.rong.zairyou.api.material.MaterialBuilder.of;
import static zone.rong.zairyou.api.material.MaterialFlag.*;
import static zone.rong.zairyou.api.fluid.FluidType.*;
import static zone.rong.zairyou.api.material.type.ItemMaterialType.*;

public class Materials {

    /** Vanilla Materials **/

    public static final Material AIR = of("air", 0xC9DAE2).fluid(LIQUID, fluid -> fluid.temperature(79).customName("liquid_air")).fluid(GASEOUS, fluid -> fluid.customTranslation().temperature(290)).build();
    public static final Material CHARCOAL = of("charcoal", 0x4C4335).flag(GENERATE_DUST_VARIANTS).build();
    public static final Material COAL = of("coal", 0x464646).ore().flag(GENERATE_DUST_VARIANTS).tex(DUST).fluid(LIQUID, fluid -> fluid.still("blocks/fluids/coal_still").flow("blocks/fluids/coal_flow").noTint().customTranslation().density(900).viscosity(2000)).build();
    public static final Material COPPER = of("copper", 0xE77C56).ore().flag(GENERATE_DEFAULT_METAL_TYPES).noTints(DUST, INGOT).tex(DUST, INGOT).fluid(MOLTEN, fluid -> fluid.temperature(1385))/*.tools(1, 144, 5.0F, 1.5F, -3.2F, 8, tools -> tools.axe().hoe().pickaxe().shovel().sword())*/.build();
    public static final Material DIAMOND = of("diamond", 0xA1FBE8).ore().flag(GENERATE_DEFAULT_METAL_TYPES).noTints(DUST, INGOT).tex(DUST, INGOT).build();
    public static final Material EMERALD = of("emerald", 0x17DD62).ore().flag(GENERATE_DEFAULT_METAL_TYPES).noTints(DUST, INGOT).tex(DUST, INGOT).build();
    public static final Material FLINT = of("flint", 0x7F7F7F).build();
    public static final Material IRON = of("iron", 0xAAAAAA).ore().flag(GENERATE_DEFAULT_METAL_TYPES).items(BUZZSAW_BLADE, SAW_BLADE).fluid(MOLTEN, fluid -> fluid.temperature(1803)).build();
    public static final Material GOLD = of("gold", 0xFFFF0B).ore().flag(GENERATE_DEFAULT_METAL_TYPES).items(COIL).fluid(MOLTEN, fluid -> fluid.temperature(1337)).build();
    public static final Material REDSTONE = of("redstone", 0xC80000).ore().items(CRYSTAL).fluid(MOLTEN, fluid -> fluid.noTint().customTranslation().still("blocks/fluids/redstone_still").flow("blocks/fluids/redstone_flow").luminosity(7).density(1200).viscosity(1500).rarity(EnumRarity.UNCOMMON)).build();
    public static final Material GLOWSTONE = of("glowstone", 0xFFBC5E).items(CRYSTAL).fluid(MOLTEN, fluid -> fluid.noTint().customTranslation().still("blocks/fluids/glowstone_still").flow("blocks/fluids/glowstone_flow").luminosity(15).density(-500).viscosity(100).gasLike().rarity(EnumRarity.UNCOMMON)).build();
    public static final Material OBSIDIAN = of("obsidian", 0x211B2E).formula(b -> b.elements(f -> f.put(Mg, 1).put(Fe, 1).put(Si, 2).put(O, 8))).items(DUST, ROD).build();
    public static final Material WOOD = of("wood", 0x866526).flag(GENERATE_DUST_VARIANTS).build();

    public static final Material COKE = of("coke", 0x4C4335).flag(GENERATE_DUST_VARIANTS).items(ItemMaterialType.COAL).build();
    public static final Material ELECTRUM = of("electrum", 0xFFFF64).flag(GENERATE_DEFAULT_METAL_TYPES).items(COIL).fluid(MOLTEN, fluid -> fluid.temperature(1337)).build();
    public static final Material SILVER = of("silver", 0x79A7E0).ore().flag(GENERATE_DEFAULT_METAL_TYPES).items(COIL).fluid(MOLTEN, fluid -> fluid.temperature(1235)).build();

    public static final Material AEROTHEUM = of("aerotheum", 0xD5D181).flag(GENERATE_DUST_VARIANTS).fluid(MOLTEN, fluid -> fluid.still("blocks/fluids/aerotheum_still").flow("blocks/fluids/aerotheum_flow").noTint().density(-800).viscosity(100).gasLike().rarity(EnumRarity.RARE)).build();
    public static final Material CRYOTHEUM = of("cryotheum", 0x49EFFF).flag(GENERATE_DUST_VARIANTS).fluid(MOLTEN, fluid -> fluid.still("blocks/fluids/cryotheum_still").flow("blocks/fluids/cryotheum_flow").noTint().density(4000).viscosity(4000).temperature(50).rarity(EnumRarity.RARE)).build();
    public static final Material PETROTHEUM = of("petrotheum", 0x6E5A5D).flag(GENERATE_DUST_VARIANTS).fluid(MOLTEN, fluid -> fluid.still("blocks/fluids/petrotheum_still").flow("blocks/fluids/petrotheum_flow").noTint().density(4000).viscosity(1500).temperature(350).rarity(EnumRarity.RARE)).build();
    public static final Material PYROTHEUM = of("pyrotheum", 0xE56000).flag(GENERATE_DUST_VARIANTS).fluid(MOLTEN, fluid -> fluid.still("blocks/fluids/pyrotheum_still").flow("blocks/fluids/pyrotheum_flow").noTint().luminosity(15).density(2000).viscosity(1200).temperature(4000).rarity(EnumRarity.RARE)).build();
    public static final Material BLIZZ = of("blizz", 0x211B2E).flag(GENERATE_DUST_VARIANTS).items(ROD).build();
    public static final Material BLITZ = of("blitz", 0x211B2E).flag(GENERATE_DUST_VARIANTS).items(ROD).build();
    public static final Material BASALZ = of("basalz", 0x211B2E).flag(GENERATE_DUST_VARIANTS).items(ROD).build();
    public static final Material CINNABAR = of("cinnabar", 0xBF4538).formula(b -> b.element(Hg).element(S)).items(CRYSTAL).build();

    public static final Material NITER = of("niter", 0xFFC8C8).flag(GENERATE_DUST_VARIANTS).formula(b -> b.element(K).element(N).element(O, 3)).build();
    public static final Material SULFUR = of("sulfur", 0xC8C800).flag(GENERATE_DUST_VARIANTS).formula(S).build();

    public static final Material TAR = of("tar", 0x2E2E2E).build();
    // public static final Material ROSIN = of("rosin", 0xE68821).types(GLOB).build();

    public static final Material ENDER_EYE = of("ender_eye", 0x8EC969).flag(GENERATE_DUST_VARIANTS).build();
    public static final Material ENDER_PEARL = of("ender_pearl", 0x2CCDB1).flag(GENERATE_DUST_VARIANTS).items(CRYSTAL).fluid(MOLTEN, fluid -> fluid.customName("ender").still("blocks/fluids/ender_still").flow("blocks/fluids/ender_flow").noTint().luminosity(3).density(4000).viscosity(2500).rarity(EnumRarity.UNCOMMON)).build();

    public static final Material PRIMAL_MANA = of("mana", 0x065E8E).flag(GENERATE_DEFAULT_METAL_TYPES).fluid(MOLTEN, fluid -> fluid.still("blocks/fluids/mana_still").flow("blocks/fluids/mana_flow").noTint().luminosity(15).density(600).viscosity(6000).rarity(EnumRarity.EPIC)).build();

    /* Pure Fluid Materials */
    public static final Material BIOCRUDE = of("biocrude", 0x346217).fluid(LIQUID, fluid -> fluid.still("blocks/fluids/biocrude_still").flow("blocks/fluids/biocrude_flow").noTint().customTranslation().density(1500).viscosity(2500)).build();
    public static final Material CREOSOTE = of("creosote", 0x804000).fluid(LIQUID, fluid -> fluid.still("blocks/fluids/creosote_still").flow("blocks/fluids/creosote_flow").noTint().customTranslation().density(1100).viscosity(2000)).build();
    public static final Material CRUDE_OIL = of("crude_oil", 0x666666).fluid(LIQUID, fluid -> fluid.still("blocks/fluids/crude_oil_still").flow("blocks/fluids/crude_oil_flow").noTint().customTranslation().density(900).viscosity(2000)).build();
    public static final Material EXPERIENCE = of("experience", 0x28CE0A).fluid(LIQUID, fluid -> fluid.still("blocks/fluids/experience_still").flow("blocks/fluids/experience_flow").noTint().luminosity(12).density(-200).viscosity(200).rarity(EnumRarity.UNCOMMON)).build();
    public static final Material MUSHROOM_STEW = of("mushroom_stew", 0xB79474).fluid(LIQUID, fluid -> fluid.still("blocks/fluids/mushroom_stew_still").flow("blocks/fluids/mushroom_stew_flow").noTint().customTranslation().density(2000).viscosity(2000)).build();
    public static final Material REFINED_BIOFUEL = of("refined_biofuel", 0x7FA814).fluid(LIQUID, fluid -> fluid.still("blocks/fluids/refined_biofuel_still").flow("blocks/fluids/refined_biofuel_flow").noTint().customTranslation().density(750).viscosity(800)).build();
    public static final Material REFINED_OIL = of("refined_oil", 0x666666).fluid(LIQUID, fluid -> fluid.still("blocks/fluids/refined_oil_still").flow("blocks/fluids/refined_oil_flow").noTint().customTranslation().density(800).viscosity(1400)).build();
    public static final Material REFINED_FUEL = of("refined_fuel", 0xFFFF00).fluid(LIQUID, fluid -> fluid.still("blocks/fluids/refined_fuel_still").flow("blocks/fluids/refined_fuel_flow").noTint().customTranslation().density(750).viscosity(800)).build();
    public static final Material RESIN = of("resin", 0xA66E00).fluid(LIQUID, fluid -> fluid.still("blocks/fluids/resin_still").flow("blocks/fluids/resin_flow").noTint().customTranslation().density(900).viscosity(3000)).build();
    public static final Material SAP = of("sap", 0x946426).fluid(LIQUID, fluid -> fluid.still("blocks/fluids/sap_still").flow("blocks/fluids/sap_flow").noTint().customTranslation().density(1050).viscosity(1500)).build();
    public static final Material SEED_OIL = of("seed_oil", 0xC4FF00).fluid(LIQUID, fluid -> fluid.still("blocks/fluids/seed_oil_still").flow("blocks/fluids/seed_oil_flow").noTint().customTranslation().density(950).viscosity(1300)).build();
    // Fixme UNUSED: public static final Material SYRUP = of("syrup", 0x946426).fluid(LIQUID, fluid -> fluid.still("blocks/fluids/sap_still").flow("blocks/fluids/sap_flow").noTint().density(1400).viscosity(2500)).build();
    public static final Material STEAM = of("steam", 0xFFFFFF).fluid(GASEOUS, fluid -> fluid.still("blocks/fluids/steam_still").flow("blocks/fluids/steam_flow").noTint().customTranslation().viscosity(200).temperature(750)).build();
    public static final Material TREE_OIL = of("tree_oil", 0x8F7638).fluid(LIQUID, fluid -> fluid.still("blocks/fluids/tree_oil_still").flow("blocks/fluids/tree_oil_flow").noTint().customTranslation().density(900).viscosity(1200)).build();

    /* Marker/Pseudo Materials - TODO: Match colours with electric tier defaults(?) */
    public static final Material BASIC = of("basic").items(FERTILIZER, SLAG, BAIT).noTints(FERTILIZER, SLAG).tex(FERTILIZER, "items/fertilizer/basic", 0).tex(SLAG, "items/slag/basic", 0).build();
    public static final Material RICH = of("rich").items(FERTILIZER, SLAG, BAIT).noTints(FERTILIZER, SLAG).tex(FERTILIZER, "items/fertilizer/rich", 0).tex(SLAG, "items/slag/rich", 0).build();
    public static final Material FLUX = of("flux").items(FERTILIZER, BAIT).noTint(FERTILIZER).tex(FERTILIZER, "items/fertilizer/flux", 0).build();

    public static void init() {
        Potions.init();
        Materials.CHARCOAL.setItem(ItemMaterialType.COAL, Items.COAL, 1);
        Materials.COAL.setItem(ItemMaterialType.COAL, Items.COAL, 0);
        Materials.IRON.setItem(ItemMaterialType.INGOT, Items.IRON_INGOT);
        Materials.IRON.setItem(ItemMaterialType.NUGGET, Items.IRON_NUGGET);
        Materials.GOLD.setItem(ItemMaterialType.INGOT, Items.GOLD_INGOT);
        Materials.GOLD.setItem(ItemMaterialType.NUGGET, Items.GOLD_NUGGET);
        Materials.REDSTONE.setItem(ItemMaterialType.DUST, Items.REDSTONE);
        Materials.ENDER_EYE.setItem(ItemMaterialType.GEM, Items.ENDER_EYE);
        Materials.ENDER_PEARL.setItem(ItemMaterialType.GEM, Items.ENDER_PEARL);
    }

    public static class Potions {

        /** Potions **/
        public static final Material NORMAL = of("potion", 0xF800F8).provideFluid(LIQUID, m -> new PotionFluid("potion", "potion.effect.").setLuminosity(3).setDensity(500).setViscosity(1500).setRarity(EnumRarity.UNCOMMON)).build();
        public static final Material SPLASH = of("potion_splash", 0xF800F8).provideFluid(LIQUID, m -> new PotionFluid("potion_splash", "splash_potion.effect.").setLuminosity(3).setDensity(500).setViscosity(1500).setRarity(EnumRarity.UNCOMMON)).build();
        public static final Material LINGERING = of("potion_lingering", 0xF800F8).provideFluid(LIQUID, m -> new PotionFluid("potion_lingering", "lingering_potion.effect.").setLuminosity(3).setDensity(500).setViscosity(1500).setRarity(EnumRarity.UNCOMMON)).build();

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

}
