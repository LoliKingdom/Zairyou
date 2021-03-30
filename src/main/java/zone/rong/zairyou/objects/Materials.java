package zone.rong.zairyou.objects;

import net.minecraft.init.Blocks;
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
import zone.rong.zairyou.api.material.MetalMaterial;
import zone.rong.zairyou.api.material.type.BlockMaterialType;
import zone.rong.zairyou.api.material.type.ItemMaterialType;

import static net.dries007.tfc.TerraFirmaCraft.MOD_ID;
import static net.dries007.tfc.api.types.Metal.Tier.*;
import static zone.rong.zairyou.api.material.MaterialFlag.ORE;
import static zone.rong.zairyou.api.material.MetalMaterial.ofMetal;
import static zone.rong.zairyou.api.material.element.Element.*;
import static zone.rong.zairyou.api.material.Material.of;
import static zone.rong.zairyou.api.material.MaterialFlag.*;
import static zone.rong.zairyou.api.fluid.FluidType.*;
import static zone.rong.zairyou.api.material.type.BlockMaterialType.*;
import static zone.rong.zairyou.api.material.type.ItemMaterialType.*;

@SuppressWarnings({ "unused", "unchecked" })
public class Materials {

    /*
    public static Material Aluminium = new Material(Ref.ID, "aluminium", 0x80c8f0, DULL, Al).asMetal(933, 1700, RING, FOIL, GEAR, FRAME, ORE).addTools(1.5F, 10.0F, 140, 2);
    public static Material Beryllium = new Material(Ref.ID, "beryllium", 0x64b464, METALLIC, Be).asMetal(1560, 0, ORE).addTools(2.0F, 14.0F, 64, 2);
    public static Material Bismuth = new Material(Ref.ID, "bismuth", 0x64a0a0, METALLIC, Bi).asMetal(544, 0, ORE, ORE_SMALL);
    public static Material Carbon = new Material(Ref.ID, "carbon", 0x141414, DULL, C).asSolid(); //TODO: Tools, Carbon Fluid? Removed Tools
    public static Material Chrome = new Material(Ref.ID, "chrome", 0xffe6e6, SHINY, Cr).asMetal(2180, 1700, SCREW, BOLT, RING, PLATE, ROTOR).addTools(2.5F, 11.0F, 256, 3);
    public static Material Cobalt = new Material(Ref.ID, "cobalt", 0x5050fa, METALLIC, Co).asMetal(1768, 0).addTools(3.0F, 8.0F, 512, 3);
    public static Material Gold = new Material(Ref.ID, "gold", 0xffff1e, SHINY, Au).asMetal(1337, 0, FOIL, ROD, WIRE_FINE, GEAR, ORE, ORE_SMALL).addTools(GOLD.getAttackDamage(), GOLD.getEfficiency(), GOLD.getMaxUses(), GOLD.getHarvestLevel());
    public static Material Iridium = new Material(Ref.ID, "iridium", 0xf0f0f5, DULL, Ir).asMetal(2719, 2719, FRAME, ORE, ORE_SMALL).addTools(5.0F, 8.0F, 2560, 4);
    public static Material Iron = new Material(Ref.ID, "iron", 0xc8c8c8, METALLIC, Fe).asMetal(1811, 0, RING, GEAR, FRAME, ORE, ORE_SMALL).asPlasma().addTools(IRON.getAttackDamage(), IRON.getEfficiency(), IRON.getMaxUses(), IRON.getHarvestLevel());
    public static Material Lanthanum = new Material(Ref.ID, "lanthanum", 0xffffff, METALLIC, La).asSolid(1193, 1193);
    public static Material Lead = new Material(Ref.ID, "lead", 0x8c648c, DULL, Pb).asMetal(600, 0, PLATE, PLATE_DENSE, FOIL, ROD, ORE, ORE_SMALL);
    public static Material Manganese = new Material(Ref.ID, "manganese", 0xfafafa, DULL, Mn).asMetal(1519, 0, ORE);
    public static Material Molybdenum = new Material(Ref.ID, "molybdenum", 0xb4b4dc, SHINY, Mo).asMetal(2896, 0, ORE).addTools(2.0F, 7.0F, 512, 2);
    public static Material Neodymium = new Material(Ref.ID, "neodymium", 0x646464, METALLIC, Nd).asMetal(1297, 1297, PLATE, ROD, ORE); //TODO: Bastnasite or Monazite for Ore Form
    public static Material Neutronium = new Material(Ref.ID, "neutronium", 0xfafafa, DULL, Nt).asMetal(10000, 10000, SCREW, BOLT, RING, GEAR, FRAME).addTools(9.0F, 24.0F, 655360, 6); //TODO Vibranium
    public static Material Nickel = new Material(Ref.ID, "nickel", 0xc8c8fa, METALLIC, Ni).asMetal(1728, 0, ORE, ORE_SMALL).asPlasma();
    public static Material Osmium = new Material(Ref.ID, "osmium", 0x3232ff, METALLIC, Os).asMetal(3306, 3306, SCREW, BOLT, RING, PLATE, FOIL, ROD, WIRE_FINE).addTools(4.0F, 16.0F, 1080, 4);
    public static Material Palladium = new Material(Ref.ID, "palladium", 0x808080, SHINY, Pd).asMetal(1828, 1828, ORE).addTools(3.0F, 10.0F, 420, 2);
    public static Material Platinum = new Material(Ref.ID, "platinum", 0xffffc8, SHINY, Pt).asMetal(2041, 0, PLATE, FOIL, ROD, WIRE_FINE, ORE, ORE_SMALL).addTools(4.5F, 18.0F, 48, 2);
    public static Material Plutonium = new Material(Ref.ID, "plutonium_244", 0xf03232, METALLIC, Pu).asMetal(912, 0).addTools(2.5F, 6.0F, 280, 3, of(Enchantments.FIRE_ASPECT, 2)); //TODO: Enchantment: Radioactivity
    public static Material Plutonium241 = new Material(Ref.ID, "plutonium_241", 0xfa4646, SHINY, Pu241).asMetal(912, 0).addTools(2.5F, 6.0F, 280, 3);
    public static Material Silver = new Material(Ref.ID, "silver", 0xdcdcff, SHINY, Ag).asMetal(1234, 0, ORE, ORE_SMALL);
    public static Material Thorium = new Material(Ref.ID, "thorium", 0x001e00, SHINY, Th).asMetal(2115, 0, ORE).addTools(1.5F, 6.0F, 512, 2);
    public static Material Titanium = new Material(Ref.ID, "titanium", 0xdca0f0, METALLIC, Ti).asMetal(1941, 1940, ROD).addTools(2.5F, 7.0F, 1600, 3);
    public static Material Tungsten = new Material(Ref.ID, "tungsten", 0x323232, METALLIC, W).asMetal(3695, 3000, FOIL).addTools(2.0F, 6.0F, 512, 3); //Tungstensteel would be the one with tools
    public static Material Uranium = new Material(Ref.ID, "uranium_238", 0x32f032, METALLIC, U).asMetal(1405, 0, ORE);
    public static Material Uranium235 = new Material(Ref.ID, "uranium_235", 0x46fa46, METALLIC, U235).asMetal(1405, 0).addTools(3.0F, 6.0F, 512, 3);
    public static Material Graphite = new Material(Ref.ID, "graphite", 0x808080, DULL).asDust(ORE, ORE_SMALL);
    public static Material Americium = new Material(Ref.ID, "americium", 0xc8c8c8, METALLIC, Am).asMetal(1149, 0); //TODO: When we're thinking about fusion
    public static Material Antimony = new Material(Ref.ID, "antimony", 0xdcdcf0, SHINY, Sb).asMetal(1449, 0);
    public static Material Argon = new Material(Ref.ID, "argon", 0xff00f0, NONE, Ar).asGas();
    public static Material Arsenic = new Material(Ref.ID, "arsenic", 0xffffff, DULL, As).asSolid();
    public static Material Barium = new Material(Ref.ID, "barium", 0xffffff, METALLIC, Ba).asDust(1000);
    public static Material Boron = new Material(Ref.ID, "boron", 0xfafafa, DULL, B).asDust(2349);
    public static Material Caesium = new Material(Ref.ID, "caesium", 0xffffff, METALLIC, Cs).asMetal(2349, 0);
    public static Material Calcium = new Material(Ref.ID, "calcium", 0xfff5f5, METALLIC, Ca).asDust(1115);
    public static Material Cadmium = new Material(Ref.ID, "cadmium", 0x32323c, SHINY, Cd).asDust(594);
    public static Material Cerium = new Material(Ref.ID, "cerium", 0xffffff, METALLIC, Ce).asSolid(1068, 1068);
    public static Material Chlorine = new Material(Ref.ID, "chlorine", 0xffffff, NONE, Cr).asGas();
    public static Material Copper = new Material(Ref.ID, "copper", 0xff6400, SHINY, Cu).asMetal(1357, 0, PLATE, ROD, FOIL, WIRE_FINE, GEAR, ORE, ORE_SMALL);
    public static Material Deuterium = new Material(Ref.ID, "deuterium", 0xffff00, NONE, D).asGas();
    public static Material Dysprosium = new Material(Ref.ID, "dysprosium", 0xffffff, METALLIC, D).asMetal(1680, 1680);
    public static Material Europium = new Material(Ref.ID, "europium", 0xffffff, METALLIC, Eu).asMetal(1099, 1099);
    public static Material Fluorine = new Material(Ref.ID, "fluorine", 0xffffff, NONE, F).asGas();
    public static Material Gallium = new Material(Ref.ID, "gallium", 0xdcdcff, SHINY, Ga).asMetal(302, 0);
    public static Material Hydrogen = new Material(Ref.ID, "hydrogen", 0x0000ff, NONE, H).asGas();
    public static Material Helium = new Material(Ref.ID, "helium", 0xffff00, NONE, He).asPlasma();
    public static Material Helium3 = new Material(Ref.ID, "helium_3", 0xffffff, NONE, He_3).asGas();
    public static Material Indium = new Material(Ref.ID, "indium", 0x400080, METALLIC, In).asSolid(429, 0);
    public static Material Lithium = new Material(Ref.ID, "lithium", 0xe1dcff, DULL, Li).asSolid(454, 0, ORE);
    public static Material Lutetium = new Material(Ref.ID, "lutetium", 0xffffff, DULL, Lu).asMetal(1925, 1925);
    public static Material Magnesium = new Material(Ref.ID, "magnesium", 0xffc8c8, METALLIC, Mg).asMetal(923, 0);
    public static Material Mercury = new Material(Ref.ID, "mercury", 0xffdcdc, SHINY, Hg).asFluid();
    public static Material Niobium = new Material(Ref.ID, "niobium", 0xbeb4c8, METALLIC, Nb).asMetal(2750, 2750);
    public static Material Nitrogen = new Material(Ref.ID, "nitrogen", 0x0096c8, NONE, N).asPlasma();
    public static Material Oxygen = new Material(Ref.ID, "oxygen", 0x0064c8, NONE, O).asPlasma();
    public static Material Phosphor = new Material(Ref.ID, "phosphor", 0xffff00, DULL, P).asDust(317);
    public static Material Potassium = new Material(Ref.ID, "potassium", 0xfafafa, METALLIC, K).asSolid(336, 0);
    public static Material Radon = new Material(Ref.ID, "radon", 0xff00ff, NONE, Rn).asGas();
    public static Material Silicon = new Material(Ref.ID, "silicon", 0x3c3c50, METALLIC, Si).asMetal(1687, 1687, PLATE, FOIL);
    public static Material Sodium = new Material(Ref.ID, "sodium", 0x000096, METALLIC, Na).asDust(370);
    public static Material Sulfur = new Material(Ref.ID, "sulfur", 0xc8c800, DULL, S).asDust(388, ORE, ORE_SMALL).asPlasma();
    public static Material Tantalum = new Material(Ref.ID, "tantalum", 0xffffff, METALLIC, Ta).asSolid(3290, 0);
    public static Material Tin = new Material(Ref.ID, "tin", 0xdcdcdc, DULL, Sn).asMetal(505, 505, PLATE, ROD, SCREW, BOLT, RING, GEAR, FOIL, WIRE_FINE, FRAME, ORE, ORE_SMALL);
    public static Material Tritium = new Material(Ref.ID, "tritium", 0xff0000, METALLIC, T).asGas();
    public static Material Vanadium = new Material(Ref.ID, "vanadium", 0x323232, METALLIC, V).asMetal(2183, 2183);
    public static Material Yttrium = new Material(Ref.ID, "yttrium", 0xdcfadc, METALLIC, Y).asMetal(1799, 1799);
    public static Material Zinc = new Material(Ref.ID, "zinc", 0xfaf0f0, METALLIC, Zn).asMetal(692, 0, PLATE, FOIL, ORE, ORE_SMALL);

    //TODO: We can be more lenient about what fluids we have in, its not as bad as solids above, and we can stop them from showing in JEI (I think...)

    public static Material WoodGas = new Material(Ref.ID, "wood_gas", 0xdecd87, NONE).asGas(24);
    public static Material Methane = new Material(Ref.ID, "methane", 0xffffff, NONE).asGas(104).mats(ImmutableMap.of(Carbon, 1, Hydrogen, 4));
    public static Material CarbonDioxide = new Material(Ref.ID, "carbon_dioxide", 0xa9d0f5, NONE).asGas().mats(ImmutableMap.of(Carbon, 1, Oxygen, 2));
    //public static Material NobleGases = new Material(Ref.ID, "noble_gases", 0xc9e3fc, NONE).asGas()/*.setTemp(79, 0).addComposition(of(CarbonDioxide, 21, Helium, 9, Methane, 3, Deuterium, 1));
    public static Material Air = new Material(Ref.ID, "air", 0xc9e3fc, NONE).asGas().mats(ImmutableMap.of(Nitrogen, 40, Oxygen, 11, Argon, 1/*, NobleGases, 1));
    public static Material NitrogenDioxide = new Material(Ref.ID, "nitrogen_dioxide", 0x64afff, NONE).asGas().mats(ImmutableMap.of(Nitrogen, 1, Oxygen, 2));
    public static Material NaturalGas = new Material(Ref.ID, "natural_gas", 0xffffff, NONE).asGas(15);
    public static Material SulfuricGas = new Material(Ref.ID, "sulfuric_gas", 0xffffff, NONE).asGas(20);
    public static Material RefineryGas = new Material(Ref.ID, "refinery_gas", 0xffffff, NONE).asGas(128);
    public static Material LPG = new Material(Ref.ID, "lpg", 0xffff00, NONE).asGas(256);
    public static Material Ethane = new Material(Ref.ID, "ethane", 0xc8c8ff, NONE).asGas(168).mats(ImmutableMap.of(Carbon, 2, Hydrogen, 6));
    public static Material Propane = new Material(Ref.ID, "propane", 0xfae250, NONE).asGas(232).mats(ImmutableMap.of(Carbon, 2, Hydrogen, 6));
    public static Material Butane = new Material(Ref.ID, "butane", 0xb6371e, NONE).asGas(296).mats(ImmutableMap.of(Carbon, 4, Hydrogen, 10));
    public static Material Butene = new Material(Ref.ID, "butene", 0xcf5005, NONE).asGas(256).mats(ImmutableMap.of(Carbon, 4, Hydrogen, 8));
    public static Material Butadiene = new Material(Ref.ID, "butadiene", 0xe86900, NONE).asGas(206).mats(ImmutableMap.of(Carbon, 4, Hydrogen, 6));
    public static Material VinylChloride = new Material(Ref.ID, "vinyl_chloride", 0xfff0f0, NONE).asGas().mats(ImmutableMap.of(Carbon, 2, Hydrogen, 3, Chlorine, 1));
    public static Material SulfurDioxide = new Material(Ref.ID, "sulfur_dioxide", 0xc8c819, NONE).asGas().mats(ImmutableMap.of(Sulfur, 1, Oxygen, 2));
    public static Material SulfurTrioxide = new Material(Ref.ID, "sulfur_trioxide", 0xa0a014, NONE).asGas()/*.setTemp(344, 1).mats(ImmutableMap.of(Sulfur, 1, Oxygen, 3));
    public static Material Dimethylamine = new Material(Ref.ID, "dimethylamine", 0x554469, NONE).asGas().mats(ImmutableMap.of(Carbon, 2, Hydrogen, 7, Nitrogen, 1));
    public static Material DinitrogenTetroxide = new Material(Ref.ID, "dinitrogen_tetroxide", 0x004184, NONE).asGas().mats(ImmutableMap.of(Nitrogen, 2, Oxygen, 4));
    public static Material NitricOxide = new Material(Ref.ID, "nitric_oxide", 0x7dc8f0, NONE).asGas().mats(ImmutableMap.of(Nitrogen, 1, Oxygen, 1));
    public static Material Ammonia = new Material(Ref.ID, "ammonia", 0x3f3480, NONE).asGas().mats(ImmutableMap.of(Nitrogen, 1, Hydrogen, 3));
    public static Material Chloromethane = new Material(Ref.ID, "chloromethane", 0xc82ca0, NONE).asGas().mats(ImmutableMap.of(Carbon, 1, Hydrogen, 3, Chlorine, 1));
    public static Material Tetrafluoroethylene = new Material(Ref.ID, "tetrafluoroethylene", 0x7d7d7d, NONE).asGas().mats(ImmutableMap.of(Carbon, 2, Fluorine, 4));
    public static Material CarbonMonoxide = new Material(Ref.ID, "carbon_monoxide", 0x0e4880, NONE).asGas(24).mats(ImmutableMap.of(Carbon, 1, Oxygen, 1));
    public static Material Ethylene = new Material(Ref.ID, "ethylene", 0xe1e1e1, NONE).asGas(128).mats(ImmutableMap.of(Carbon, 2, Hydrogen, 4));
    public static Material Propene = new Material(Ref.ID, "propene", 0xffdd55, NONE).asGas(192).mats(ImmutableMap.of(Carbon, 3, Hydrogen, 6));
    public static Material Ethenone = new Material(Ref.ID, "ethenone", 0x141446, NONE).asGas().mats(ImmutableMap.of(Carbon, 2, Hydrogen, 2, Oxygen, 1));
    public static Material HydricSulfide = new Material(Ref.ID, "hydric_sulfide", 0xffffff, NONE).asGas().mats(ImmutableMap.of(Hydrogen, 2, Sulfur, 1));

    public static Material Lava = new Material(Ref.ID, "lava", 0xff4000, NONE).asFluid();
    public static Material Water = new Material(Ref.ID, "water", 0x0000ff, NONE).asFluid().mats(ImmutableMap.of(Hydrogen, 2, Oxygen, 1));
    public static Material Steam = new Material(Ref.ID, "steam", 0xa0a0a0, NONE).asGas();
    public static Material UUAmplifier = new Material(Ref.ID, "uu_amplifier", 0x600080, NONE).asFluid();
    public static Material UUMatter = new Material(Ref.ID, "uu_matter", 0x8000c4, NONE).asFluid();
    public static Material Antimatter = new Material(Ref.ID, "anti_matter", 0x8000c4, NONE).asFluid();
    //public static Material CharcoalByproducts = new Material(Ref.ID, "charcoal_byproducts", 0x784421, NONE).asFluid(); //TODO I'll think about this and woods when I get started on pyrolysis
    public static Material Glue = new Material(Ref.ID, "glue", 0xc8c400, NONE).asFluid();
    public static Material Honey = new Material(Ref.ID, "honey", 0xd2c800, NONE).asFluid(); //TODO: Only when Forestry's present?
    public static Material Lubricant = new Material(Ref.ID, "lubricant", 0xffc400, NONE).asFluid();
    //public static Material WoodTar = new Material(Ref.ID, "wood_tar", 0x28170b, NONE).asFluid(); TODO: not sure if needed
    public static Material WoodVinegar = new Material(Ref.ID, "wood_vinegar", 0xd45500, NONE).asFluid();
    public static Material LiquidAir = new Material(Ref.ID, "liquid_air", 0xa9d0f5, NONE).asFluid()/*.setTemp(79, 0).mats(ImmutableMap.of(Nitrogen, 40, Oxygen, 11, Argon, 1/*, NobleGases, 1)); //TODO Rrename to liquid oxygen <- Nope, add fluid to Oxygen
    public static Material DistilledWater = new Material(Ref.ID, "distilled_water", 0x5C5CFF, NONE).asFluid().mats(ImmutableMap.of(Hydrogen, 2, Oxygen, 1));
    public static Material Glyceryl = new Material(Ref.ID, "glyceryl", 0x009696, NONE).asFluid().mats(ImmutableMap.of(Carbon, 3, Hydrogen, 5, Nitrogen, 3, Oxygen, 9));
    public static Material Titaniumtetrachloride = new Material(Ref.ID, "titaniumtetrachloride", 0xd40d5c, NONE).asFluid().mats(ImmutableMap.of(Titanium, 1, Chlorine, 4));
    public static Material SodiumPersulfate = new Material(Ref.ID, "sodium_persulfate", 0xffffff, NONE).asFluid().mats(ImmutableMap.of(Sodium, 2, Sulfur, 2, Oxygen, 8));
    public static Material DilutedHydrochloricAcid = new Material(Ref.ID, "diluted_hydrochloric_acid", 0x99a7a3, NONE).asFluid().mats(ImmutableMap.of(Hydrogen, 1, Chlorine, 1));
    public static Material NitrationMixture = new Material(Ref.ID, "nitration_mixture", 0xe6e2ab, NONE).asFluid();
    public static Material Dichlorobenzene = new Material(Ref.ID, "dichlorobenzene", 0x004455, NONE).asFluid().mats(ImmutableMap.of(Carbon, 6, Hydrogen, 4, Chlorine, 2));
    public static Material Styrene = new Material(Ref.ID, "styrene", 0xd2c8be, NONE).asFluid().mats(ImmutableMap.of(Carbon, 8, Hydrogen, 8));
    public static Material Isoprene = new Material(Ref.ID, "isoprene", 0x141414, NONE).asFluid().mats(ImmutableMap.of(Carbon, 8, Hydrogen, 8));
    public static Material Tetranitromethane = new Material(Ref.ID, "tetranitromethane", 0x0f2828, NONE).asFluid().mats(ImmutableMap.of(Carbon, 1, Nitrogen, 4, Oxygen, 8));
    public static Material Epichlorohydrin = new Material(Ref.ID, "epichlorohydrin", 0x501d05, NONE).asFluid().mats(ImmutableMap.of(Carbon, 3, Hydrogen, 5, Chlorine, 1, Oxygen, 1));
    public static Material NitricAcid = new Material(Ref.ID, "nitric_acid", 0xe6e2ab, NONE).asFluid().mats(ImmutableMap.of(Hydrogen, 1, Nitrogen, 1, Oxygen, 3));
    public static Material Dimethylhydrazine = new Material(Ref.ID, "dimethylhydrazine", 0x000055, NONE).asFluid().mats(ImmutableMap.of(Carbon, 2, Hydrogen, 8, Nitrogen, 2));
    public static Material Chloramine = new Material(Ref.ID, "chloramine", 0x3f9f80, NONE).asFluid().mats(ImmutableMap.of(Nitrogen, 1, Hydrogen, 2, Chlorine, 1));
    public static Material Dimethyldichlorosilane = new Material(Ref.ID, "dimethyldichlorosilane", 0x441650, NONE).asFluid().mats(ImmutableMap.of(Carbon, 2, Hydrogen, 6, Chlorine, 2, Silicon, 1));
    public static Material HydrofluoricAcid = new Material(Ref.ID, "hydrofluoric_acid", 0x0088aa, NONE).asFluid().mats(ImmutableMap.of(Hydrogen, 1, Fluorine, 1));
    public static Material Chloroform = new Material(Ref.ID, "chloroform", 0x892ca0, NONE).asFluid().mats(ImmutableMap.of(Carbon, 1, Hydrogen, 1, Chlorine, 3));
    public static Material BisphenolA = new Material(Ref.ID, "bisphenol_a", 0xd4b300, NONE).asFluid().mats(ImmutableMap.of(Carbon, 15, Hydrogen, 16, Oxygen, 2));
    public static Material AceticAcid = new Material(Ref.ID, "acetic_acid", 0xc8b4a0, NONE).asFluid().mats(ImmutableMap.of(Carbon, 2, Hydrogen, 4, Oxygen, 2));
    //public static Material CalciumAcetateSolution = new Material(Ref.ID, "calcium_acetate_solution", 0xdcc8b4, RUBY).asFluid().addComposition(of(Calcium, 1, Carbon, 2, Oxygen, 4, Hydrogen, 6);
    public static Material Acetone = new Material(Ref.ID, "acetone", 0xafafaf, NONE).asFluid().mats(ImmutableMap.of(Carbon, 3, Hydrogen, 6, Oxygen, 1));
    public static Material Methanol = new Material(Ref.ID, "methanol", 0xaa8800, NONE).asFluid(84).mats(ImmutableMap.of(Carbon, 1, Hydrogen, 4, Oxygen, 1));
    public static Material VinylAcetate = new Material(Ref.ID, "vinyl_acetate", 0xffb380, NONE).asFluid().mats(ImmutableMap.of(Carbon, 4, Hydrogen, 6, Oxygen, 2));
    public static Material PolyvinylAcetate = new Material(Ref.ID, "polyvinyl_acetate", 0xff9955, NONE).asFluid().mats(ImmutableMap.of(Carbon, 4, Hydrogen, 6, Oxygen, 2));
    public static Material MethylAcetate = new Material(Ref.ID, "methyl_acetate", 0xeec6af, NONE).asFluid().mats(ImmutableMap.of(Carbon, 3, Hydrogen, 6, Oxygen, 2));
    public static Material AllylChloride = new Material(Ref.ID, "allyl_chloride", 0x87deaa, NONE).asFluid().mats(ImmutableMap.of(Carbon, 3, Hydrogen, 5, Chlorine, 1));
    public static Material HydrochloricAcid = new Material(Ref.ID, "hydrochloric_acid", 0x6f8a91, NONE).asFluid().mats(ImmutableMap.of(Hydrogen, 1, Chlorine, 1));
    public static Material HypochlorousAcid = new Material(Ref.ID, "hypochlorous_acid", 0x6f8a91, NONE).asFluid().mats(ImmutableMap.of(Hydrogen, 1, Chlorine, 1, Oxygen, 1));
    public static Material Cumene = new Material(Ref.ID, "cumene", 0x552200, NONE).asFluid().mats(ImmutableMap.of(Carbon, 9, Hydrogen, 12));
    public static Material PhosphoricAcid = new Material(Ref.ID, "phosphoric_acid", 0xdcdc00, NONE).asFluid().mats(ImmutableMap.of(Hydrogen, 3, Phosphor, 1, Oxygen, 4));
    public static Material SulfuricAcid = new Material(Ref.ID, "sulfuric_acid", 0xff8000, NONE).asFluid().mats(ImmutableMap.of(Hydrogen, 2, Sulfur, 1, Oxygen, 4));
    public static Material DilutedSulfuricAcid = new Material(Ref.ID, "diluted_sulfuric_acid", 0xc07820, NONE).asFluid().mats(ImmutableMap.of(SulfuricAcid, 1));
    public static Material Benzene = new Material(Ref.ID, "benzene", 0x1a1a1a, NONE).asFluid(288).mats(ImmutableMap.of(Carbon, 6, Hydrogen, 6));
    public static Material Phenol = new Material(Ref.ID, "phenol", 0x784421, NONE).asFluid(288).mats(ImmutableMap.of(Carbon, 6, Hydrogen, 6, Oxygen, 1));
    public static Material Toluene = new Material(Ref.ID, "toluene", 0x501d05, NONE).asFluid(328).mats(ImmutableMap.of(Carbon, 7, Hydrogen, 8));
    public static Material SulfuricNaphtha = new Material(Ref.ID, "sulfuric_naphtha", 0xffff00, NONE).asFluid(32);
    public static Material Naphtha = new Material(Ref.ID, "naphtha", 0xffff00, NONE).asFluid(256);
    public static Material DrillingFluid = new Material(Ref.ID, "drilling_fluid", 0xffffff, NONE).asFluid(); //TODO: Perhaps for a bedrock drill?
    public static Material BlueVitriol = new Material(Ref.ID, "blue_vitriol_water_solution", 0xffffff, NONE).asFluid();
    public static Material IndiumConcentrate = new Material(Ref.ID, "indium_concentrate", 0xffffff, NONE).asFluid();
    public static Material NickelSulfate = new Material(Ref.ID, "nickel_sulfate", 0xffffff, NONE).asFluid();
    public static Material RocketFuel = new Material(Ref.ID, "rocket_fuel", 0xffffff, NONE).asFluid();
    public static Material LeadZincSolution = new Material(Ref.ID, "lead_zinc_solution", 0xffffff, NONE).asFluid();

    public static Material Diesel = new Material(Ref.ID, "diesel", 0xffff00, NONE).asFluid(128);
    public static Material NitroFuel = new Material(Ref.ID, "cetane_boosted_diesel", 0xc8ff00, NONE).asFluid(512);
    public static Material BioDiesel = new Material(Ref.ID, "bio_diesel", 0xff8000, NONE).asFluid(192);
    public static Material Biomass = new Material(Ref.ID, "biomass", 0x00ff00, NONE).asFluid(8);
    public static Material Ethanol = new Material(Ref.ID, "ethanol", 0xff8000, NONE).asFluid(148).mats(ImmutableMap.of(Carbon, 2, Hydrogen, 6, Oxygen, 1));
    public static Material Creosote = new Material(Ref.ID, "creosote", 0x804000, NONE).asFluid(8);
    public static Material FishOil = new Material(Ref.ID, "fish_oil", 0xffc400, NONE).asFluid(2);
    public static Material Oil = new Material(Ref.ID, "oil", 0x0a0a0a, NONE).asFluid(16);
    public static Material SeedOil = new Material(Ref.ID, "seed_oil", 0xc4ff00, NONE).asFluid(2);
    //public static Materials SeedOilHemp = new Materials(722, "Hemp Seed Oil", 196, 255, 0, lime, NONE).asSemi(2);
    //public static Materials SeedOilLin = new Materials(723, "Lin Seed Oil", 196, 255, 0, lime, NONE).asSemi(2);
    //public static Material OilExtraHeavy = new Material(Ref.ID, "extra_heavy_oil", 0x0a0a0a, NONE).asFluid(40);
    public static Material OilHeavy = new Material(Ref.ID, "heavy_oil", 0x0a0a0a, NONE).asFluid(32);
    public static Material OilMedium = new Material(Ref.ID, "raw_oil", 0x0a0a0a, NONE).asFluid(24);
    public static Material OilLight = new Material(Ref.ID, "light_oil", 0x0a0a0a, NONE).asFluid(16);
    public static Material SulfuricLightFuel = new Material(Ref.ID, "sulfuric_light_diesel", 0xffff00, NONE).asFluid(32);
    public static Material SulfuricHeavyFuel = new Material(Ref.ID, "sulfuric_heavy_diesel", 0xffff00, NONE).asFluid(32);
    public static Material LightDiesel = new Material(Ref.ID, "light_diesel", 0xffff00, NONE).asFluid(256);
    public static Material HeavyDiesel = new Material(Ref.ID, "heavy_diesel", 0xffff00, NONE).asFluid(192);
    public static Material Glycerol = new Material(Ref.ID, "glycerol", 0x87de87, NONE).asFluid(164).mats(ImmutableMap.of(Carbon, 3, Hydrogen, 8, Oxygen, 3));

    public static Material SodiumSulfide = new Material(Ref.ID, "sodium_sulfide", 0xffe680, NONE).asDust().mats(ImmutableMap.of(Sodium, 2, Sulfur, 1));
    public static Material IridiumSodiumOxide = new Material(Ref.ID, "iridium_sodium_oxide", 0xffffff, NONE).asDust();
    public static Material PlatinumGroupSludge = new Material(Ref.ID, "platinum_group_sludge", 0x001e00, NONE).asDust();
    public static Material Glowstone = new Material(Ref.ID, "glowstone", 0xffff00, SHINY).asDust();
    public static Material Graphene = new Material(Ref.ID, "graphene", 0x808080, DULL).asDust();
    public static Material Oilsands = new Material(Ref.ID, "oilsands", 0x0a0a0a, NONE).asDust(ORE);
    public static Material RareEarth = new Material(Ref.ID, "rare_earth", 0x808064, FINE).asDust();
    public static Material Endstone = new Material(Ref.ID, "endstone", 0xffffff, DULL).asDust();
    public static Material Netherrack = new Material(Ref.ID, "netherrack", 0xc80000, DULL).asDust();
    public static Material Almandine = new Material(Ref.ID, "almandine", 0xff0000, ROUGH).asDust(ORE, ORE_SMALL).mats(ImmutableMap.of(Aluminium, 2, Iron, 3, Silicon, 3, Oxygen, 12));
    public static Material Andradite = new Material(Ref.ID, "andradite", 0x967800, ROUGH).asDust(ORE, ORE_SMALL).mats(ImmutableMap.of(Calcium, 3, Iron, 2, Silicon, 3, Oxygen, 12));
    public static Material Ash = new Material(Ref.ID, "ash", 0x969696, DULL).asDust();
    public static Material BandedIron = new Material(Ref.ID, "banded_iron", 0x915a5a, DULL).asDust(ORE).mats(ImmutableMap.of(Iron, 2, Oxygen, 3));
    public static Material BrownLimonite = new Material(Ref.ID, "brown_limonite", 0xc86400, METALLIC).asDust(ORE).mats(ImmutableMap.of(Iron, 1, Hydrogen, 1, Oxygen, 2));
    public static Material Calcite = new Material(Ref.ID, "calcite", 0xfae6dc, DULL).asDust(ORE).mats(ImmutableMap.of(Calcium, 1, Carbon, 1, Oxygen, 3));
    public static Material Cassiterite = new Material(Ref.ID, "cassiterite", 0xdcdcdc, METALLIC).asDust(ORE, ORE_SMALL).mats(ImmutableMap.of(Tin, 1, Oxygen, 2));
    public static Material Chalcopyrite = new Material(Ref.ID, "chalcopyrite", 0xa07828, DULL).asDust(ORE, ORE_SMALL).mats(ImmutableMap.of(Copper, 1, Iron, 1, Sulfur, 2));
    public static Material Clay = new Material(Ref.ID, "clay", 0xc8c8dc, ROUGH).asDust().mats(ImmutableMap.of(Sodium, 2, Lithium, 1, Aluminium, 2, Silicon, 2, Water, 6));
    public static Material Cobaltite = new Material(Ref.ID, "cobaltite", 0x5050fa, METALLIC).asDust(ORE).mats(ImmutableMap.of(Cobalt, 1, Arsenic, 1, Sulfur, 1));
    public static Material Cooperite = new Material(Ref.ID, "cooperite", 0xffffc8, METALLIC).asDust(ORE, ORE_SMALL).mats(ImmutableMap.of(Platinum, 3, Nickel, 1, Sulfur, 1, Palladium, 1));
    public static Material DarkAsh = new Material(Ref.ID, "dark_ash", 0x323232, DULL).asDust();
    public static Material Galena = new Material(Ref.ID, "galena", 0x643c64, DULL).asDust(ORE, ORE_SMALL).mats(ImmutableMap.of(Lead, 3, Silver, 3, Sulfur, 2));
    public static Material Garnierite = new Material(Ref.ID, "garnierite", 0x32c846, METALLIC).asDust(ORE, ORE_SMALL).mats(ImmutableMap.of(Nickel, 1, Oxygen, 1));
    public static Material Grossular = new Material(Ref.ID, "grossular", 0xc86400, ROUGH).asDust(ORE, ORE_SMALL).mats(ImmutableMap.of(Calcium, 3, Aluminium, 2, Silicon, 3, Oxygen, 12));
    public static Material Ilmenite = new Material(Ref.ID, "ilmenite", 0x463732, METALLIC).asDust(ORE).mats(ImmutableMap.of(Iron, 1, Titanium, 1, Oxygen, 3));
    public static Material Rutile = new Material(Ref.ID, "rutile", 0xd40d5c, GEM_H).asDust(ORE, ORE_SMALL).mats(ImmutableMap.of(Titanium, 1, Oxygen, 2));
    public static Material MagnesiumChloride = new Material(Ref.ID, "magnesiumchloride", 0xd40d5c, DULL).asDust().mats(ImmutableMap.of(Magnesium, 1, Chlorine, 2));
    public static Material Magnesite = new Material(Ref.ID, "magnesite", 0xfafab4, METALLIC).asDust(ORE).mats(ImmutableMap.of(Magnesium, 1, Carbon, 1, Oxygen, 3));
    public static Material Magnetite = new Material(Ref.ID, "magnetite", 0x1e1e1e, METALLIC).asDust(ORE).mats(ImmutableMap.of(Iron, 3, Oxygen, 4));
    public static Material Molybdenite = new Material(Ref.ID, "molybdenite", 0x91919, METALLIC).asDust(ORE).mats(ImmutableMap.of(Molybdenum, 1, Sulfur, 2));
    public static Material Obsidian = new Material(Ref.ID, "obsidian", 0x503264, DULL).asDust().addHandleStat(222, -0.5F, ImmutableMap.of(Enchantments.UNBREAKING, 2)).mats(ImmutableMap.of(Magnesium, 1, Iron, 1, Silicon, 2, Oxygen, 8));
    public static Material Phosphate = new Material(Ref.ID, "phosphate", 0xffff00, DULL).asDust(ORE).mats(ImmutableMap.of(Phosphor, 1, Oxygen, 4));
    public static Material Polydimethylsiloxane = new Material(Ref.ID, "polydimethylsiloxane", 0xf5f5f5, NONE).asDust().mats(ImmutableMap.of(Carbon, 2, Hydrogen, 6, Oxygen, 1, Silicon, 1));
    //public static Material Powellite = new Material(Ref.ID, "powellite", 0xffff00, DULL).asDust(ORE).addComposition(of(Calcium, 1, Molybdenum, 1, Oxygen, 4));
    public static Material Pyrite = new Material(Ref.ID, "pyrite", 0x967828, ROUGH).asDust(ORE, ORE_SMALL).mats(ImmutableMap.of(Iron, 1, Sulfur, 2));
    public static Material Pyrolusite = new Material(Ref.ID, "pyrolusite", 0x9696aa, DULL).asDust(ORE, ORE_SMALL).mats(ImmutableMap.of(Manganese, 1, Oxygen, 2));
    public static Material Pyrope = new Material(Ref.ID, "pyrope", 0x783264, METALLIC).asDust(ORE, ORE_SMALL).mats(ImmutableMap.of(Aluminium, 2, Magnesium, 3, Silicon, 3, Oxygen, 12));
    public static Material RawRubber = new Material(Ref.ID, "raw_rubber", 0xccc789, DULL).asDust().mats(ImmutableMap.of(Carbon, 5, Hydrogen, 8));
    public static Material Saltpeter = new Material(Ref.ID, "saltpeter", 0xe6e6e6, FINE).asDust(ORE, ORE_SMALL).mats(ImmutableMap.of(Potassium, 1, Nitrogen, 1, Oxygen, 3));
    public static Material Scheelite = new Material(Ref.ID, "scheelite", 0xc88c14, DULL).asDust(2500, ORE).mats(ImmutableMap.of(Tungsten, 1, Calcium, 2, Oxygen, 4));
    public static Material SiliconDioxide = new Material(Ref.ID, "silicon_dioxide", 0xc8c8c8, QUARTZ).asDust().mats(ImmutableMap.of(Silicon, 1, Oxygen, 2));
    //public static Material Pyrochlore = new Material(Ref.ID, "pyrochlore", 0x2b1100, METALLIC).asDust(ORE).addComposition(of(Calcium, 2, Niobium, 2, Oxygen, 7));
    public static Material FerriteMixture = new Material(Ref.ID, "ferrite_mixture", 0xb4b4b4, METALLIC).asDust().mats(ImmutableMap.of(Nickel, 1, Zinc, 1, Iron, 4));
    public static Material Massicot = new Material(Ref.ID, "massicot", 0xffdd55, DULL).asDust().mats(ImmutableMap.of(Lead, 1, Oxygen, 1));
    public static Material ArsenicTrioxide = new Material(Ref.ID, "arsenic_trioxide", 0xffffff, SHINY).asDust().mats(ImmutableMap.of(Arsenic, 2, Oxygen, 3));
    public static Material CobaltOxide = new Material(Ref.ID, "cobalt_oxide", 0x668000, DULL).asDust().mats(ImmutableMap.of(Cobalt, 1, Oxygen, 1));
    public static Material Magnesia = new Material(Ref.ID, "magnesia", 0xffffff, DULL).asDust().mats(ImmutableMap.of(Magnesium, 1, Oxygen, 1));
    public static Material Quicklime = new Material(Ref.ID, "quicklime", 0xf0f0f0, DULL).asDust().mats(ImmutableMap.of(Calcium, 1, Oxygen, 1));
    public static Material Potash = new Material(Ref.ID, "potash", 0x784237, DULL).asDust().mats(ImmutableMap.of(Potassium, 2, Oxygen, 1));
    public static Material SodaAsh = new Material(Ref.ID, "soda_ash", 0xdcdcff, DULL).asDust().mats(ImmutableMap.of(Sodium, 2, Carbon, 1, Oxygen, 3));
    public static Material Brick = new Material(Ref.ID, "brick", 0x9b5643, ROUGH).asDust().mats(ImmutableMap.of(Aluminium, 4, Silicon, 3, Oxygen, 12));
    public static Material Fireclay = new Material(Ref.ID, "fireclay", 0xada09b, ROUGH).asDust().mats(ImmutableMap.of(Brick, 1));
    public static Material SodiumBisulfate = new Material(Ref.ID, "sodium_bisulfate", 0x004455, NONE).asDust().mats(ImmutableMap.of(Sodium, 1, Hydrogen, 1, Sulfur, 1, Oxygen, 4));
    public static Material RawStyreneButadieneRubber = new Material(Ref.ID, "raw_styrene_butadiene_rubber", 0x54403d, SHINY).asDust().mats(ImmutableMap.of(Styrene, 1, Butadiene, 3));
    public static Material PhosphorousPentoxide = new Material(Ref.ID, "phosphorous_pentoxide", 0xdcdc00, NONE).asDust().mats(ImmutableMap.of(Phosphor, 4, Oxygen, 10));
    public static Material SodiumHydroxide = new Material(Ref.ID, "sodium_hydroxide", 0x003380, DULL).asDust().mats(ImmutableMap.of(Sodium, 1, Oxygen, 1, Hydrogen, 1));
    public static Material Spessartine = new Material(Ref.ID, "spessartine", 0xff6464, DULL).asDust(ORE, ORE_SMALL).mats(ImmutableMap.of(Aluminium, 2, Manganese, 3, Silicon, 3, Oxygen, 12));
    public static Material Sphalerite = new Material(Ref.ID, "sphalerite", 0xffffff, DULL).asDust(ORE, ORE_SMALL).mats(ImmutableMap.of(Zinc, 1, Sulfur, 1));
    public static Material Stibnite = new Material(Ref.ID, "stibnite", 0x464646, METALLIC).asDust(ORE, ORE_SMALL).mats(ImmutableMap.of(Antimony, 2, Sulfur, 3));
    public static Material Tetrahedrite = new Material(Ref.ID, "tetrahedrite", 0xc82000, DULL).asDust(ORE).mats(ImmutableMap.of(Copper, 3, Antimony, 1, Sulfur, 3, Iron, 1));
    public static Material Tungstate = new Material(Ref.ID, "tungstate", 0x373223, DULL).asDust(ORE).mats(ImmutableMap.of(Tungsten, 1, Lithium, 2, Oxygen, 4));
    public static Material Uraninite = new Material(Ref.ID, "uraninite", 0x232323, METALLIC).asDust(ORE).mats(ImmutableMap.of(Uranium, 1, Oxygen, 2));
    public static Material Uvarovite = new Material(Ref.ID, "uvarovite", 0xb4ffb4, DIAMOND).asDust(ORE, ORE_SMALL).mats(ImmutableMap.of(Calcium, 3, Chrome, 2, Silicon, 3, Oxygen, 12));
    public static Material Wood = new Material(Ref.ID, "wood", 0x643200, NONE).asDust(GEAR).addHandleStat(12, 0.0F).mats(ImmutableMap.of(Carbon, 1, Oxygen, 1, Hydrogen, 1));
    public static Material Stone = new Material(Ref.ID, "stone", 0xcdcdcd, ROUGH).asDust(DUST_IMPURE, GEAR).addHandleStat(-10, -0.5F);
    public static Material Wulfenite = new Material(Ref.ID, "wulfenite", 0xff8000, DULL).asDust(ORE).mats(ImmutableMap.of(Lead, 1, Molybdenum, 1, Oxygen, 4));
    public static Material YellowLimonite = new Material(Ref.ID, "yellow_limonite", 0xc8c800, METALLIC).asDust(ORE).mats(ImmutableMap.of(Iron, 1, Hydrogen, 1, Oxygen, 2));
    //public static Material SealedWood = new Material(Ref.ID, "sealed_wood", 0x502800, NONE).asDust().addTools(3.0F, 24, 0).addComposition(of(Wood, 1); TODO: Perhaps with IE integration or when we have some utility stuff
    public static Material Blaze = new Material(Ref.ID, "blaze", 0xffc800, NONE).asDust().addHandleStat(-10, -0.5F, ImmutableMap.of(Enchantments.FIRE_ASPECT, 1)).mats(ImmutableMap.of(Sulfur, 1, DarkAsh, 1/*, Magic, 1));
    public static Material Flint = new Material(Ref.ID, "flint", 0x002040, FLINT).asDust(ROCK).addTools(0.0F, 2.0F, 48, 1, ImmutableMap.of(Enchantments.FIRE_ASPECT, 1)).mats(ImmutableMap.of(SiliconDioxide, 1));
    public static Material PotassiumFeldspar = new Material(Ref.ID, "potassium_feldspar", 0x782828, FINE).asDust().mats(ImmutableMap.of(Potassium, 1, Aluminium, 1, Silicon, 3, Oxygen, 8));
    public static Material Biotite = new Material(Ref.ID, "biotite", 0x141e14, METALLIC).asDust().mats(b -> b.put(Potassium, 1).put(Magnesium, 3).put(Aluminium, 3).put(Fluorine, 2).put(Silicon, 3).put(Oxygen, 10));
    public static Material VanadiumMagnetite = new Material(Ref.ID, "vanadium_magnetite", 0x23233c, METALLIC).asDust(ORE).mats(ImmutableMap.of(Magnetite, 1, Vanadium, 1));
    public static Material Bastnasite = new Material(Ref.ID, "bastnasite", 0xc86e2d, FINE).asDust(ORE).mats(ImmutableMap.of(Cerium, 1, Carbon, 1, Fluorine, 1, Oxygen, 3));
    public static Material Pentlandite = new Material(Ref.ID, "pentlandite", 0xa59605, DULL).asDust(ORE, ORE_SMALL).mats(ImmutableMap.of(Nickel, 9, Sulfur, 8));
    public static Material Spodumene = new Material(Ref.ID, "spodumene", 0xbeaaaa, DULL).asDust(ORE).mats(ImmutableMap.of(Lithium, 1, Aluminium, 1, Silicon, 2, Oxygen, 6));
    public static Material Tantalite = new Material(Ref.ID, "tantalite", 0x915028, METALLIC).asDust(ORE).mats(ImmutableMap.of(Manganese, 1, Tantalum, 2, Oxygen, 6));
    public static Material Lepidolite = new Material(Ref.ID, "lepidolite", 0xf0328c, FINE).asDust(ORE).mats(ImmutableMap.of(Potassium, 1, Lithium, 3, Aluminium, 4, Fluorine, 2, Oxygen, 10)); //TODO: Ore Gen
    public static Material Glauconite = new Material(Ref.ID, "glauconite", 0x82b43c, DULL).asDust(ORE).mats(ImmutableMap.of(Potassium, 1, Magnesium, 2, Aluminium, 4, Hydrogen, 2, Oxygen, 12)); //TODO: Ore Gen
    public static Material Bentonite = new Material(Ref.ID, "bentonite", 0xf5d7d2, ROUGH).asDust(ORE).mats(b -> b.put(Sodium, 1).put(Magnesium, 6).put(Silicon, 12).put(Hydrogen, 6).put(Water, 5).put(Oxygen, 36)); //TODO: Ore Gen
    public static Material Pitchblende = new Material(Ref.ID, "pitchblende", 0xc8d200, DULL).asDust(ORE).mats(ImmutableMap.of(Uraninite, 3, Thorium, 1, Lead, 1));
    public static Material Malachite = new Material(Ref.ID, "malachite", 0x055f05, DULL).asDust(ORE).mats(ImmutableMap.of(Copper, 2, Carbon, 1, Hydrogen, 2, Oxygen, 5));
    public static Material Barite = new Material(Ref.ID, "barite", 0xe6ebff, DULL).asDust(ORE).mats(ImmutableMap.of(Barium, 1, Sulfur, 1, Oxygen, 4));
    public static Material Talc = new Material(Ref.ID, "talc", 0x5ab45a, DULL).asDust(ORE).mats(ImmutableMap.of(Magnesium, 3, Silicon, 4, Hydrogen, 2, Oxygen, 12));
    public static Material Soapstone = new Material(Ref.ID, "soapstone", 0x5f915f, DULL).asDust(ORE).mats(ImmutableMap.of(Magnesium, 3, Silicon, 4, Hydrogen, 2, Oxygen, 12)); //TODO: Ore Gen
    public static Material Concrete = new Material(Ref.ID, "concrete", 0x646464, ROUGH).asDust(300).mats(ImmutableMap.of(Stone, 1));
    public static Material AntimonyTrioxide = new Material(Ref.ID, "antimony_trioxide", 0xe6e6f0, DULL).asDust().mats(ImmutableMap.of(Antimony, 2, Oxygen, 3));
    public static Material CupricOxide = new Material(Ref.ID, "cupric_oxide", 0x0f0f0f, DULL).asDust().mats(ImmutableMap.of(Copper, 1, Oxygen, 1));
    public static Material Ferrosilite = new Material(Ref.ID, "ferrosilite", 0x97632a, DULL).asDust().mats(ImmutableMap.of(Iron, 1, Silicon, 1, Oxygen, 3));

    //public static Material CertusQuartz = new Material(Ref.ID, "certus_quartz", 0xd2d2e6, QUARTZ).asGemBasic(false, PLATE, ORE).addTools(5.0F, 32, 1); TODO: Only when AE2 is loaded
    public static Material Dilithium = new Material(Ref.ID, "dilithium", 0xfffafa, DIAMOND).asGemBasic(true);
    public static Material NetherQuartz = new Material(Ref.ID, "nether_quartz", 0xe6d2d2, QUARTZ).asGemBasic(false, ORE, ORE_SMALL);
    public static Material NetherStar = new Material(Ref.ID, "nether_star", 0xffffff, NONE).asGemBasic(false).addTools(3.5F, 6.0F, 3620, 4, ImmutableMap.of(Enchantments.SILK_TOUCH, 1)); //Made Nether Stars usable

    //Brittle Gems
    public static Material BlueTopaz = new Material(Ref.ID, "blue_topaz", 0x0000ff, GEM_H).asGem(true, ORE_SMALL).addTools(2.5F, 7.0F, 256, 3).mats(ImmutableMap.of(Aluminium, 2, Silicon, 1, Fluorine, 2, Hydrogen, 2, Oxygen, 6));
    public static Material Charcoal = new Material(Ref.ID, "charcoal", 0x644646, LIGNITE).asGemBasic(false).mats(ImmutableMap.of(Carbon, 1));
    public static Material CoalCoke = new Material(Ref.ID, "coal_coke", 0x8c8caa, LIGNITE).asGemBasic(false);
    public static Material LigniteCoke = new Material(Ref.ID, "lignite_coke", 0x8c6464, LIGNITE).asGemBasic(false);

    public static Material Diamond = new Material(Ref.ID, "diamond", 0xc8ffff, DIAMOND).asGem(true, ORE, ORE_SMALL).addTools(ItemTier.DIAMOND.getAttackDamage(), ItemTier.DIAMOND.getEfficiency(), ItemTier.DIAMOND.getMaxUses(), ItemTier.DIAMOND.getHarvestLevel()).mats(ImmutableMap.of(Carbon, 1));
    public static Material Emerald = new Material(Ref.ID, "emerald", 0x50ff50, GEM_V).asGem(true, ORE, ORE_SMALL).addTools(3.0F, 9.0F, 590, 3).mats(ImmutableMap.of(Silver, 1, Gold, 1)); //Made Emerald better
    public static Material GreenSapphire = new Material(Ref.ID, "green_sapphire", 0x64c882, GEM_H).asGem(true, ORE, ORE_SMALL).addTools(2.0F, 7.0F, 256, 2).mats(ImmutableMap.of(Aluminium, 2, Oxygen, 3));
    //public static Material Lazurite = new Material(Ref.ID, "lazurite", 0x6478ff, LAPIS).asGemBasic(false, ORE).addComposition(of(Aluminium, 6, Silicon, 6, Calcium, 8, Sodium, 8)); //TODO I think this is needed?
    public static Material Ruby = new Material(Ref.ID, "ruby", 0xff6464, RUBY).asGem(true, ORE, ORE_SMALL).addTools(2.0F, 7.0F, 256, 2).mats(ImmutableMap.of(Chrome, 1, Aluminium, 2, Oxygen, 3));
    public static Material BlueSapphire = new Material(Ref.ID, "blue_sapphire", 0x6464c8, GEM_V).asGem(true, ORE, ORE_SMALL).addTools(2.0F, 7.0F, 256, 2).mats(ImmutableMap.of(Aluminium, 2, Oxygen, 3));
    //public static Material Sodalite = new Material(Ref.ID, "sodalite", 0x1414ff, LAPIS).asGemBasic(false, ORE).addComposition(of(Aluminium, 3, Silicon, 3, Sodium, 4, Chlorine, 1)); //TODO I think this is needed?
    public static Material Tanzanite = new Material(Ref.ID, "tanzanite", 0x4000c8, GEM_V).asGem(true, ORE, ORE_SMALL).addTools(2.0F, 7.0F, 256, 2).mats(ImmutableMap.of(Calcium, 2, Aluminium, 3, Silicon, 3, Hydrogen, 1, Oxygen, 13));
    public static Material Topaz = new Material(Ref.ID, "topaz", 0xff8000, GEM_H).asGem(true, ORE, ORE_SMALL).addTools(2.0F, 7.0F, 256, 2).mats(ImmutableMap.of(Aluminium, 2, Silicon, 1, Fluorine, 2, Hydrogen, 2, Oxygen, 6));
    public static Material Glass = new Material(Ref.ID, "glass", 0xfafafa, SHINY).asDust(PLATE, LENS).mats(ImmutableMap.of(SiliconDioxide, 1));
    public static Material Olivine = new Material(Ref.ID, "olivine", 0x96ff96, RUBY).asGem(true, ORE, ORE_SMALL).addTools(2.0F, 7.0F, 256, 2, ImmutableMap.of(Enchantments.SILK_TOUCH, 1)).mats(ImmutableMap.of(Magnesium, 2, Iron, 1, SiliconDioxide, 2));
    public static Material Opal = new Material(Ref.ID, "opal", 0x0000ff, RUBY).asGem(true, ORE, ORE_SMALL).addTools(2.0F, 7.0F, 256, 2).mats(ImmutableMap.of(SiliconDioxide, 1));
    public static Material Amethyst = new Material(Ref.ID, "amethyst", 0xd232d2, RUBY).asGem(true, ORE, ORE_SMALL).addTools(2.0F, 7.0F, 256, 3).mats(ImmutableMap.of(SiliconDioxide, 4, Iron, 1));
    public static Material Lapis = new Material(Ref.ID, "lapis", 0x4646dc, LAPIS).asGemBasic(false, ORE, ORE_SMALL).mats(ImmutableMap.of(/*Lazurite, 12, Sodalite, 2, Pyrite, 1, Calcite, 1));
    public static Material EnderPearl = new Material(Ref.ID, "enderpearl", 0x6cdcc8, SHINY).asGemBasic(false).mats(ImmutableMap.of(Beryllium, 1, Potassium, 4, Nitrogen, 5/*, Magic, 6));
    public static Material EnderEye = new Material(Ref.ID, "endereye", 0xa0fae6, SHINY).asGemBasic(true, ROD, PLATE).mats(ImmutableMap.of(EnderPearl, 1, Blaze, 1));
    public static Material Phosphorus = new Material(Ref.ID, "phosphorus", 0xffff00, FLINT).asGemBasic(false, ORE).mats(ImmutableMap.of(Calcium, 3, Phosphate, 2));
    public static Material RedGarnet = new Material(Ref.ID, "red_garnet", 0xc85050, RUBY).asGemBasic(true, ORE, ORE_SMALL).mats(ImmutableMap.of(Pyrope, 3, Almandine, 5, Spessartine, 8));
    public static Material YellowGarnet = new Material(Ref.ID, "yellow_garnet", 0xc8c850, RUBY).asGemBasic(true, ORE, ORE_SMALL).mats(ImmutableMap.of(Andradite, 5, Grossular, 8, Uvarovite, 3));
    //public static Material Monazite = new Material(Ref.ID, "monazite", 0x324632, DIAMOND).asGemBasic(false, ORE).addComposition(of(RareEarth, 1, Phosphate, 1));

    public static Material Redstone = new Material(Ref.ID, "redstone", 0xc80000, ROUGH).asDust(ORE, ORE_SMALL, LIQUID).mats(ImmutableMap.of(Silicon, 1, Pyrite, 5, Ruby, 1, Mercury, 3));
    public static Material Cinnabar = new Material(Ref.ID, "cinnabar", 0x960000, ROUGH).asDust(ORE).mats(ImmutableMap.of(Mercury, 1, Sulfur, 1));

    public static Material AnnealedCopper = new Material(Ref.ID, "annealed_copper", 0xff7814, SHINY).asMetal(1357, 0, PLATE, FOIL, ROD, WIRE_FINE).mats(ImmutableMap.of(Copper, 1));
    public static Material BatteryAlloy = new Material(Ref.ID, "battery_alloy", 0x9c7ca0, DULL).asMetal(295, 0, PLATE).mats(ImmutableMap.of(Lead, 4, Antimony, 1));
    public static Material Brass = new Material(Ref.ID, "brass", 0xffb400, METALLIC).asMetal(1170, 0, FRAME).mats(ImmutableMap.of(Zinc, 1, Copper, 3));
    public static Material Bronze = new Material(Ref.ID, "bronze", 0xff8000, METALLIC).asMetal(1125, 0, GEAR, FRAME).addTools(1.5F, 6.5F, 182, 2, ImmutableMap.of(Enchantments.UNBREAKING, 1)).mats(ImmutableMap.of(Tin, 1, Copper, 3));
    public static Material Cupronickel = new Material(Ref.ID, "cupronickel", 0xe39680, METALLIC).asMetal(1728, 0).mats(ImmutableMap.of(Copper, 1, Nickel, 1));
    public static Material Electrum = new Material(Ref.ID, "electrum", 0xffff64, SHINY).asMetal(1330, 0, PLATE, FOIL, ROD, WIRE_FINE).addTools(1.0F, 13.0F, 48, 2, ImmutableMap.of(Enchantments.UNBREAKING, 3)).mats(ImmutableMap.of(Silver, 1, Gold, 1));
    public static Material Invar = new Material(Ref.ID, "invar", 0xb4b478, METALLIC).asMetal(1700, 0, FRAME).addTools(2.5F, 7.0F, 320, 2, ImmutableMap.of(Enchantments.BANE_OF_ARTHROPODS, 2)).mats(ImmutableMap.of(Iron, 2, Nickel, 1));
    public static Material Kanthal = new Material(Ref.ID, "kanthal", 0xc2d2df, METALLIC).asMetal(1800, 1800).addTools(2.5F, 6.0F, 64, 2, ImmutableMap.of(Enchantments.BANE_OF_ARTHROPODS, 1)).mats(ImmutableMap.of(Iron, 1, Aluminium, 1, Chrome, 1));
    public static Material Magnalium = new Material(Ref.ID, "magnalium", 0xc8beff, DULL).asMetal(870, 0).mats(ImmutableMap.of(Magnesium, 1, Aluminium, 2));
    public static Material Nichrome = new Material(Ref.ID, "nichrome", 0xcdcef6, METALLIC).asMetal(2700, 2700).addTools(2.0F, 6.0F, 81, 2, ImmutableMap.of(Enchantments.BANE_OF_ARTHROPODS, 3)).mats(ImmutableMap.of(Nickel, 4, Chrome, 1));
    public static Material NiobiumTitanium = new Material(Ref.ID, "niobium_titanium", 0x1d1d29, DULL).asMetal(4500, 4500, PLATE, FOIL, ROD, WIRE_FINE).mats(ImmutableMap.of(Nickel, 4, Chrome, 1));
    public static Material SolderingAlloy = new Material(Ref.ID, "soldering_alloy", 0xdcdce6, DULL).asMetal(400, 400, PLATE, FOIL, ROD, WIRE_FINE).mats(ImmutableMap.of(Tin, 9, Antimony, 1));
    public static Material Steel = new Material(Ref.ID, "steel", 0x808080, METALLIC).asMetal(1811, 1000, GEAR, PLATE, FOIL, SCREW, BOLT, ROD, RING, FRAME).addTools(Iron).mats(ImmutableMap.of(Iron, 50, Carbon, 1));
    public static Material StainlessSteel = new Material(Ref.ID, "stainless_steel", 0xc8c8dc, SHINY).asMetal(1700, 1700, SCREW, BOLT, GEAR, FRAME).addTools(Steel).mats(ImmutableMap.of(Iron, 6, Chrome, 1, Manganese, 1, Nickel, 1));
    public static Material Ultimet = new Material(Ref.ID, "ultimet", 0xb4b4e6, SHINY).asMetal(2700, 2700).mats(ImmutableMap.of(Cobalt, 5, Chrome, 2, Nickel, 1, Molybdenum, 1));
    public static Material VanadiumGallium = new Material(Ref.ID, "vanadium_gallium", 0x80808c, SHINY).asMetal(4500, 4500, ROD).mats(ImmutableMap.of(Vanadium, 3, Gallium, 1));
    public static Material WroughtIron = new Material(Ref.ID, "wrought_iron", 0xc8b4b4, METALLIC).asMetal(1811, 0, RING, FRAME).addTools(Iron).mats(ImmutableMap.of(Iron, 1));
    public static Material YttriumBariumCuprate = new Material(Ref.ID, "yttrium_barium_cuprate", 0x504046, METALLIC).asMetal(4500, 4500, PLATE, FOIL, ROD, WIRE_FINE).mats(ImmutableMap.of(Yttrium, 1, Barium, 2, Copper, 3, Oxygen, 7));
    public static Material SterlingSilver = new Material(Ref.ID, "sterling_silver", 0xfadce1, SHINY).asMetal(1700, 1700).addTools(3.0F, 10.5F, 96, 2, ImmutableMap.of(Enchantments.EFFICIENCY, 2)).mats(ImmutableMap.of(Copper, 1, Silver, 4));
    public static Material RoseGold = new Material(Ref.ID, "rose_gold", 0xffe61e, SHINY).asMetal(1600, 1600).addTools(Gold, ImmutableMap.of(Enchantments.FORTUNE, 3, Enchantments.SMITE, 3)).mats(ImmutableMap.of(Copper, 1, Gold, 4));
    public static Material BlackBronze = new Material(Ref.ID, "black_bronze", 0x64327d, DULL).asMetal(2000, 2000).addTools(Bronze, ImmutableMap.of(Enchantments.SWEEPING, 1)).mats(ImmutableMap.of(Gold, 1, Silver, 1, Copper, 3));
    public static Material BismuthBronze = new Material(Ref.ID, "bismuth_bronze", 0x647d7d, DULL).asMetal(1100, 900, PLATE).addTools(2.5F, Bronze.getToolSpeed() + 2.0F, 350, 2, ImmutableMap.of(Enchantments.BANE_OF_ARTHROPODS, 4)).mats(ImmutableMap.of(Bismuth, 1, Zinc, 1, Copper, 3));
    public static Material BlackSteel = new Material(Ref.ID, "black_steel", 0x646464, METALLIC).asMetal(1200, 1200, FRAME, PLATE).addTools(3.5F, 6.5F, 768, 2).mats(ImmutableMap.of(Nickel, 1, BlackBronze, 1, Steel, 3));
    public static Material RedSteel = new Material(Ref.ID, "red_steel", 0x8c6464, METALLIC).asMetal(1300, 1300).addTools(3.5F, 7.0F, 896, 2).mats(ImmutableMap.of(SterlingSilver, 1, BismuthBronze, 1, Steel, 2, BlackSteel, 4));
    public static Material BlueSteel = new Material(Ref.ID, "blue_steel", 0x64648c, METALLIC).asMetal(1400, 1400, FRAME).addTools(3.5F, 7.5F, 1024, 2).mats(ImmutableMap.of(RoseGold, 1, Brass, 1, Steel, 2, BlackSteel, 4));
    //public static Material DamascusSteel = new Material(Ref.ID, "damascus_steel", 0x6e6e6e, METALLIC).asMetal(2500, 1500).addTools(8.0F, 1280, 2).addComposition(of(Steel, 1)); //TODO: Sorta a fantasy metal
    public static Material TungstenSteel = new Material(Ref.ID, "tungstensteel", 0x6464a0, METALLIC).asMetal(3000, 3000, SCREW, BOLT, GEAR, ROD, RING, FRAME).addTools(4.0F, 8.0F, 2560, 4).mats(ImmutableMap.of(Steel, 1, Tungsten, 1));
    public static Material RedAlloy = new Material(Ref.ID, "red_alloy", 0xc80000, DULL).asMetal(295, 0, PLATE, FOIL, ROD, WIRE_FINE).mats(ImmutableMap.of(Copper, 1, Redstone, 4));
    public static Material CobaltBrass = new Material(Ref.ID, "cobalt_brass", 0xb4b4a0, METALLIC).asMetal(1500, 0, GEAR).addTools(2.5F, 8.0F, 256, 2).mats(ImmutableMap.of(Brass, 7, Aluminium, 1, Cobalt, 1));
    public static Material IronMagnetic = new Material(Ref.ID, "magnetic_iron", 0xc8c8c8, MAGNETIC).asMetal(1811, 0).addTools(Iron).mats(ImmutableMap.of(Iron, 1));
    public static Material SteelMagnetic = new Material(Ref.ID, "magnetic_steel", 0x808080, MAGNETIC).asMetal(1000, 1000).addTools(Steel).mats(ImmutableMap.of(Steel, 1));
    public static Material NeodymiumMagnetic = new Material(Ref.ID, "magnetic_neodymium", 0x646464, MAGNETIC).asMetal(1297, 1297).mats(ImmutableMap.of(Neodymium, 1));
    public static Material NickelZincFerrite = new Material(Ref.ID, "nickel_zinc_ferrite", 0x3c3c3c, ROUGH).asMetal(1500, 1500).addTools(0.0F, 3.0F, 32, 1).mats(ImmutableMap.of(Nickel, 1, Zinc, 1, Iron, 4, Oxygen, 8));
    public static Material TungstenCarbide = new Material(Ref.ID, "tungsten_carbide", 0x330066, METALLIC).asMetal(2460, 2460).addTools(5.0F, 14.0F, 1280, 4).mats(ImmutableMap.of(Tungsten, 1, Carbon, 1));
    public static Material VanadiumSteel = new Material(Ref.ID, "vanadium_steel", 0xc0c0c0, METALLIC).asMetal(1453, 1453).addTools(3.0F, 5.0F, 1920, 3).mats(ImmutableMap.of(Vanadium, 1, Chrome, 1, Steel, 7));
    public static Material HSSG = new Material(Ref.ID, "hssg", 0x999900, METALLIC).asMetal(4500, 4500, GEAR, FRAME).addTools(3.8F, 10.0F, 4000, 3).mats(ImmutableMap.of(TungstenSteel, 5, Chrome, 1, Molybdenum, 2, Vanadium, 1));
    public static Material HSSE = new Material(Ref.ID, "hsse", 0x336600, METALLIC).asMetal(5400, 5400, GEAR, FRAME).addTools(4.2F, 10.0F, 5120, 4).mats(ImmutableMap.of(HSSG, 6, Cobalt, 1, Manganese, 1, Silicon, 1));
    public static Material HSSS = new Material(Ref.ID, "hsss", 0x660033, METALLIC).asMetal(5400, 5400).addTools(5.0F, 14.0F, 3000, 4).mats(ImmutableMap.of(HSSG, 6, Iridium, 2, Osmium, 1));
    public static Material Osmiridium = new Material(Ref.ID, "osmiridium", 0x6464ff, METALLIC).asMetal(3333, 2500, FRAME).addTools(6.0F, 15.0F, 1940, 5).mats(ImmutableMap.of(Iridium, 3, Osmium, 1));
    public static Material Duranium = new Material(Ref.ID, "duranium", 0xffffff, METALLIC).asMetal(295, 0).addHandleStat(620, -1.0F, ImmutableMap.of(Enchantments.SILK_TOUCH, 1)).addTools(6.5F, 16.0F, 5120, 5);
    public static Material Naquadah = new Material(Ref.ID, "naquadah", 0x323232, METALLIC).asMetal(5400, 5400, ORE).addHandleStat(102, 0.5F, ImmutableMap.of(Enchantments.EFFICIENCY, 2)).addTools(4.0F, 6.0F, 890, 4);
    public static Material NaquadahAlloy = new Material(Ref.ID, "naquadah_alloy", 0x282828, METALLIC).asMetal(7200, 7200).addTools(4.5F, 8.0F, 5120, 5);
    public static Material EnrichedNaquadah = new Material(Ref.ID, "enriched_naquadah", 0x323232, SHINY).asMetal(4500, 4500, ORE).addTools(5.0F, 6.0F, 1280, 4);
    public static Material Naquadria = new Material(Ref.ID, "naquadria", 0x1e1e1e, SHINY).asMetal(9000, 9000);
    public static Material Tritanium = new Material(Ref.ID, "tritanium", 0xffffff, SHINY).asMetal(295, 0, FRAME).addTools(9.0F, 15.0F, 9400, 6);
    public static Material Vibranium = new Material(Ref.ID, "vibranium", 0x00ffff, SHINY).asMetal(295, 0, FRAME).addTools(10.0F, 20.0F, 12240, 6);

    public static Material Plastic = new Material(Ref.ID, "plastic", 0xc8c8c8, DULL).asSolid(295, 0, PLATE).addHandleStat(66, 0.5F).mats(ImmutableMap.of(Carbon, 1, Hydrogen, 2));
    public static Material Epoxid = new Material(Ref.ID, "epoxid", 0xc88c14, DULL).asSolid(400, 0, PLATE).addHandleStat(70, 1.5F).mats(ImmutableMap.of(Carbon, 2, Hydrogen, 4, Oxygen, 1));
    public static Material Silicone = new Material(Ref.ID, "silicone", 0xdcdcdc, DULL).asSolid(900, 0, PLATE, FOIL).addHandleStat(-40, 2.0F).mats(ImmutableMap.of(Carbon, 2, Hydrogen, 6, Oxygen, 1, Silicon, 1));
    public static Material Polycaprolactam = new Material(Ref.ID, "polycaprolactam", 0x323232, DULL).asSolid(500, 0).mats(ImmutableMap.of(Carbon, 6, Hydrogen, 11, Nitrogen, 1, Oxygen, 1));
    public static Material Polytetrafluoroethylene = new Material(Ref.ID, "polytetrafluoroethylene", 0x646464, DULL).asSolid(1400, 0, PLATE, FRAME).mats(ImmutableMap.of(Carbon, 2, Fluorine, 4));
    public static Material Rubber = new Material(Ref.ID, "rubber", 0x000000, SHINY).asSolid(295, 0, PLATE, RING).addHandleStat(11, 0.4F).mats(ImmutableMap.of(Carbon, 5, Hydrogen, 8));
    public static Material PolyphenyleneSulfide = new Material(Ref.ID, "polyphenylene_sulfide", 0xaa8800, DULL).asSolid(295, 0, PLATE, FOIL).mats(ImmutableMap.of(Carbon, 6, Hydrogen, 4, Sulfur, 1));
    public static Material Polystyrene = new Material(Ref.ID, "polystyrene", 0xbeb4aa, DULL).asSolid(295, 0).addHandleStat(3, 1.0F).mats(ImmutableMap.of(Carbon, 8, Hydrogen, 8));
    public static Material StyreneButadieneRubber = new Material(Ref.ID, "styrene_butadiene_rubber", 0x211a18, SHINY).asSolid(295, 0, PLATE, RING).addHandleStat(66, 1.2F).mats(ImmutableMap.of(Styrene, 1, Butadiene, 3));
    public static Material PolyvinylChloride = new Material(Ref.ID, "polyvinyl_chloride", 0xd7e6e6, NONE).asSolid(295, 0, PLATE, FOIL).addHandleStat(210, 0.5F).mats(ImmutableMap.of(Carbon, 2, Hydrogen, 3, Chlorine, 1));
    public static Material GalliumArsenide = new Material(Ref.ID, "gallium_arsenide", 0xa0a0a0, DULL).asSolid(295, 1200).mats(ImmutableMap.of(Arsenic, 1, Gallium, 1));
    public static Material EpoxidFiberReinforced = new Material(Ref.ID, "fiber_reinforced_epoxy_resin", 0xa07010, DULL).asSolid(400, 0).mats(ImmutableMap.of(Epoxid, 1));

    public static Material Granite = new Material(Ref.ID, "granite", 0xa07882, ROUGH).asDust(ROCK);
    public static Material Diorite = new Material(Ref.ID, "diorite", 0xf0f0f0, ROUGH).asDust(ROCK);
    public static Material Andesite = new Material(Ref.ID, "andesite", 0xbfbfbf, ROUGH).asDust(ROCK);

    public static Material Gravel = new Material(Ref.ID, "gravel", 0xcdcdcd, ROUGH).asDust(ROCK);
    public static Material Sand = new Material(Ref.ID, "sand", 0xfafac8, ROUGH).asDust(ROCK);
    public static Material RedSand = new Material(Ref.ID, "red_sand", 0xff8438, ROUGH).asDust(ROCK);
    public static Material Sandstone = new Material(Ref.ID, "sandstone", 0xfafac8, ROUGH).asDust(ROCK);

    public static Material RedGranite = new Material(Ref.ID, "red_granite", 0xff0080, ROUGH).asDust(ROCK).addHandleStat(74, 1.0F, ImmutableMap.of(Enchantments.UNBREAKING, 1)).mats(ImmutableMap.of(Aluminium, 2, PotassiumFeldspar, 1, Oxygen, 3));
    public static Material BlackGranite = new Material(Ref.ID, "black_granite", 0x0a0a0a, ROUGH).asDust(ROCK).addHandleStat(74, 1.0F, ImmutableMap.of(Enchantments.UNBREAKING, 1)).mats(ImmutableMap.of(SiliconDioxide, 4, Biotite, 1));
    public static Material Marble = new Material(Ref.ID, "marble", 0xc8c8c8, NONE).asDust(ROCK).mats(ImmutableMap.of(Magnesium, 1, Calcite, 7));
    public static Material Basalt = new Material(Ref.ID, "basalt", 0x1e1414, ROUGH).asDust(ROCK).mats(ImmutableMap.of(Olivine, 1, Calcite, 3, Flint, 8, DarkAsh, 4));
    public static Material Komatiite = new Material(Ref.ID, "komatiite", 0xbebe69, NONE).asDust(ROCK).mats(ImmutableMap.of(Olivine, 1, /*MgCO3, 2, Flint, 6, DarkAsh, 3));
    public static Material Limestone = new Material(Ref.ID, "limestone", 0xe6c882, NONE).asDust(ROCK).mats(ImmutableMap.of(Calcite, 1));
    public static Material GreenSchist = new Material(Ref.ID, "green_schist", 0x69be69, NONE).asDust(ROCK);
    public static Material BlueSchist = new Material(Ref.ID, "blue_schist", 0x0569be, NONE).asDust(ROCK);
    public static Material Kimberlite = new Material(Ref.ID, "kimberlite", 0x64460a, NONE).asDust(ROCK);
    public static Material Quartzite = new Material(Ref.ID, "quartzite", 0xe6cdcd, QUARTZ).asGemBasic(false, ORE, ROCK).mats(ImmutableMap.of(Silicon, 1, Oxygen, 2));

    public static Material Coal = new Material(Ref.ID, "coal", 0x464646, LIGNITE).asGemBasic(false, ORE, ORE_STONE, ORE_SMALL).mats(ImmutableMap.of(Carbon, 1));
    public static Material Lignite = new Material(Ref.ID, "lignite_coal", 0x644646, LIGNITE).asGemBasic(false, ORE_STONE).mats(ImmutableMap.of(Carbon, 3, Water, 1));
    public static Material Salt = new Material(Ref.ID, "salt", 0xfafafa, FINE).asDust(ORE_STONE, ORE_SMALL).mats(ImmutableMap.of(Sodium, 1, Chlorine, 1));
    public static Material RockSalt = new Material(Ref.ID, "rock_salt", 0xf0c8c8, FINE).asDust(ORE_STONE, ORE_SMALL).mats(ImmutableMap.of(Potassium, 1, Chlorine, 1));
    public static Material Bauxite = new Material(Ref.ID, "bauxite", 0xc86400, DULL).asDust(ORE_STONE).mats(ImmutableMap.of(Rutile, 2, Aluminium, 16, Hydrogen, 10, Oxygen, 11));
    public static Material OilShale = new Material(Ref.ID, "oil_shale", 0x32323c, NONE).asDust(ORE_STONE);
     */

    /** Vanilla Materials **/

    public static final Material Air = of("air", 0xC9DAE2).fluid(LIQUID, fluid -> fluid.temperature(79).customName("liquid_air")).fluid(GASEOUS, fluid -> fluid.customTranslation().temperature(290));
    public static final Material Charcoal = of("charcoal", 0x4C4335).items(ItemMaterialType.COAL).flag(GENERATE_DUST_VARIANTS);
    public static final Material Coal = of("coal", 0x464646).flag(ORE, GENERATE_DUST_VARIANTS).block(STORAGE).items(ItemMaterialType.COAL).tex(DUST).fluid(LIQUID, fluid -> fluid.still("blocks/fluids/coal_still").flow("blocks/fluids/coal_flow").noTint().customTranslation().density(900).viscosity(2000));
    public static final Material Copper = of("copper", 0xE77C56).flag(ORE, GENERATE_DEFAULT_METAL_TYPES).noTints(DUST, INGOT).tex(DUST, INGOT).fluid(MOLTEN, fluid -> fluid.temperature(1385))/*.tools(1, 144, 5.0F, 1.5F, -3.2F, 8, tools -> tools.axe().hoe().pickaxe().shovel().sword())*/;
    public static final Material Diamond = of("diamond", 0xA1FBE8).flag(ORE, GENERATE_DEFAULT_METAL_TYPES).noTints(DUST, INGOT).tex(DUST, INGOT).formula(b -> b.allotrope(C, "diamond"));
    public static final Material EnderEye = of("ender_eye", 0x8EC969).flag(GENERATE_DUST_VARIANTS).items(GEM);
    public static final Material EnderPearl = of("ender_pearl", 0x2CCDB1).flag(GENERATE_DUST_VARIANTS).items(CRYSTAL, GEM).fluid(MOLTEN, fluid -> fluid.customName("ender").still("blocks/fluids/ender_still").flow("blocks/fluids/ender_flow").noTint().luminosity(3).density(4000).viscosity(2500).rarity(EnumRarity.UNCOMMON));
    public static final Material Emerald = of("emerald", 0x17DD62).flag(ORE, GENERATE_DEFAULT_METAL_TYPES).noTints(DUST, INGOT).tex(DUST, INGOT);
    public static final Material Flint = of("flint", 0x7F7F7F);
    public static final Material Iron = of("iron", 0xAAAAAA, Fe).flag(ORE, GENERATE_DEFAULT_METAL_TYPES).block(STORAGE).items(BUZZSAW_BLADE, SAW_BLADE).fluid(MOLTEN, fluid -> fluid.temperature(1803));
    public static final Material Gold = of("gold", 0xFFFF0B, Au).flag(ORE, GENERATE_DEFAULT_METAL_TYPES).block(STORAGE).items(COIL).fluid(MOLTEN, fluid -> fluid.temperature(1337));
    public static final Material Redstone = of("redstone", 0xC80000).flag(ORE, GENERATE_DUST_VARIANTS).block(STORAGE).items(CRYSTAL).fluid(MOLTEN, fluid -> fluid.noTint().customTranslation().still("blocks/fluids/redstone_still").flow("blocks/fluids/redstone_flow").luminosity(7).density(1200).viscosity(1500).rarity(EnumRarity.UNCOMMON));
    public static final Material Glowstone = of("glowstone", 0xFFBC5E).flag(GENERATE_DUST_VARIANTS).items(CRYSTAL).fluid(MOLTEN, fluid -> fluid.noTint().customTranslation().still("blocks/fluids/glowstone_still").flow("blocks/fluids/glowstone_flow").luminosity(15).density(-500).viscosity(100).gasLike().rarity(EnumRarity.UNCOMMON));
    public static final Material Obsidian = of("obsidian", 0x211B2E).formula(b -> b.elements(f -> f.put(Mg, 1).put(Fe, 1).put(Si, 2).put(O, 8))).items(DUST, ROD, CRYSTAL);
    public static final Material Wood = of("wood", 0x866526).flag(GENERATE_DUST_VARIANTS);

    /** Generic - GregTech-esque **/

    public static final MetalMaterial Aluminium = ofMetal("aluminium", 0x80C8F0, Al, TIER_III, 0.3F, 660).flag(ORE, GENERATE_DUST_VARIANTS);
    public static final MetalMaterial Beryllium = ofMetal("beryllium", 0x64B464, Be, TIER_VI, 0.35F, 1300).flag(ORE, GENERATE_DUST_VARIANTS);
    public static final MetalMaterial Bismuth = ofMetal("bismuth", 0x64A0A0, Bi, TIER_I, 0.14F, 270).flag(ORE, GENERATE_DUST_VARIANTS);
    public static final Material Carbon = of("carbon", 0x141414, C).flag(GENERATE_DUST_VARIANTS);
    public static final MetalMaterial Chrome = ofMetal("chrome", 0xffE6E6, Cr, TIER_IV, 0.2F, 1907).flag(ORE, GENERATE_DUST_VARIANTS);
    public static final MetalMaterial Cobalt = ofMetal("cobalt", 0x5050FA, Co, TIER_III, 0.3F, 1495).flag(ORE, GENERATE_DUST_VARIANTS);
    public static final MetalMaterial Iridium = ofMetal("iridium", 0xF0F0F5, Ir, TIER_VI, 0.58F, 2466).flag(ORE, GENERATE_DUST_VARIANTS);
    public static final MetalMaterial Lanthanum = ofMetal("lanthanum", 0xFFFFFF, La, TIER_VI, 0.1F, 920).flag(GENERATE_DUST_VARIANTS);
    public static final MetalMaterial Lead = ofMetal("lead", 0x8C648C, Pb, TIER_I, 0.22F, 328).flag(ORE, GENERATE_DUST_VARIANTS);
    public static final MetalMaterial Manganese = ofMetal("manganese", 0xFAFAFA, Mn, TIER_III, 0.29F, 1250).flag(ORE, GENERATE_DUST_VARIANTS);
    public static final MetalMaterial Molybdenum = ofMetal("molybdenum", 0xB4B4DC, Mo, TIER_VI, 0.1F, 2623).flag(ORE, GENERATE_DUST_VARIANTS);
    public static final MetalMaterial Neodymium = ofMetal("neodymium", 0x646464, Nd, TIER_III, 0.2F, 1016).flag(ORE, GENERATE_DUST_VARIANTS);
    public static final MetalMaterial Nickel = ofMetal("nickel", 0xc8c8FA, Ni, TIER_I, 0.48F, 1453).flag(ORE, GENERATE_DUST_VARIANTS);
    public static final MetalMaterial Osmium = ofMetal("osmium", 0x3232FF, Os, TIER_VI, 0.35F, 3025).flag(ORE, GENERATE_DUST_VARIANTS);
    public static final MetalMaterial Palladium = ofMetal("palladium", 0x808080, Pd, TIER_III, 0.1F, 1555).flag(ORE, GENERATE_DUST_VARIANTS);
    public static final MetalMaterial Platinum = ofMetal("platinum", 0xFFFFC8, Pt, TIER_V, 0.35F, 1730).flag(ORE, GENERATE_DUST_VARIANTS);
    public static final MetalMaterial Plutonium244 = ofMetal("plutonium", 0xF03232, Pu, TIER_V, 0.1F, 912).flag(ORE, GENERATE_DUST_VARIANTS);
    public static final MetalMaterial Plutonium241 = ofMetal("plutonium_241", 0xFA4646, Pu_241, TIER_V, 0.1F, 912).flag(ORE, GENERATE_DUST_VARIANTS);
    public static final MetalMaterial Silver = ofMetal("silver", 0x79A7E0, Ag, TIER_I, 0.48F, 961).flag(ORE, GENERATE_DEFAULT_METAL_TYPES).items(COIL)/*.fluid(MOLTEN, fluid -> fluid.temperature(1235))*/;
    public static final MetalMaterial Thorium = ofMetal("thorium", 0x001E00, Th, TIER_III, 0.3F, 630).flag(ORE, GENERATE_DUST_VARIANTS);
    public static final MetalMaterial Titanium = ofMetal("titanium", 0xDCA0F0, Ti, TIER_VI, 0.3F, 1700).flag(ORE, GENERATE_DUST_VARIANTS);
    public static final MetalMaterial Tungsten = ofMetal("tungsten", 0x323232, W, TIER_VI, 0.2F, 3400).flag(ORE, GENERATE_DUST_VARIANTS);
    public static final MetalMaterial Uranium238 = ofMetal("uranium", 0x32F032, U, TIER_III, 0.3F, 3000).flag(ORE, GENERATE_DUST_VARIANTS);
    public static final MetalMaterial Uranium235 = ofMetal("uranium_235", 0x46FA46, U_235, TIER_III, 0.3F, 3000).flag(GENERATE_DUST_VARIANTS);

    public static final Material Graphite = of("graphite", 0x98B2C2).flag(ORE, GENERATE_DUST_VARIANTS).formula(b -> b.allotrope(C, "graphite")); // Normal 808080, TFC 98B2C2

    /** TFC **/

    // Ores
    public static final Material NativeCopper = of("native_copper", Copper.getColour()).flag(ORE).noTints(BlockMaterialType.ORE, BlockMaterialType.ORE_POOR, BlockMaterialType.ORE_RICH);
    public static final Material NativeGold = of("native_gold", Gold.getColour()).flag(ORE).noTints(BlockMaterialType.ORE, BlockMaterialType.ORE_POOR, BlockMaterialType.ORE_RICH);
    public static final Material NativePlatinum = of("native_platinum", Platinum.getColour()).flag(ORE).noTints(BlockMaterialType.ORE, BlockMaterialType.ORE_POOR, BlockMaterialType.ORE_RICH);
    public static final Material Hematite = of("hematite", 0x9B898D).flag(ORE).noTints(BlockMaterialType.ORE, BlockMaterialType.ORE_POOR, BlockMaterialType.ORE_RICH);
    public static final Material NativeSilver = of("native_silver", Silver.getColour()).flag(ORE).noTints(BlockMaterialType.ORE, BlockMaterialType.ORE_POOR, BlockMaterialType.ORE_RICH);
    public static final Material Cassiterite = of("cassiterite", 0x5F5F4D).flag(ORE).noTints(BlockMaterialType.ORE, BlockMaterialType.ORE_POOR, BlockMaterialType.ORE_RICH);
    public static final Material Galena = of("galena", 0x7E7981).flag(ORE).noTints(BlockMaterialType.ORE, BlockMaterialType.ORE_POOR, BlockMaterialType.ORE_RICH);
    public static final Material Bismuthinite = of("bismuthinite", 0x6A6548).flag(ORE).noTints(BlockMaterialType.ORE, BlockMaterialType.ORE_POOR, BlockMaterialType.ORE_RICH);
    public static final Material Garnierite = of("garnierite", 0x556E41).flag(ORE).noTints(BlockMaterialType.ORE, BlockMaterialType.ORE_POOR, BlockMaterialType.ORE_RICH);
    public static final Material Malachite = of("malachite", 0x68B384).flag(ORE).noTints(BlockMaterialType.ORE, BlockMaterialType.ORE_POOR, BlockMaterialType.ORE_RICH);
    public static final Material Magnetite = of("magnetite", 0x696D6E).flag(ORE).noTints(BlockMaterialType.ORE, BlockMaterialType.ORE_POOR, BlockMaterialType.ORE_RICH);
    public static final Material Limonite = of("limonite", 0x2C1D16).flag(ORE).noTints(BlockMaterialType.ORE, BlockMaterialType.ORE_POOR, BlockMaterialType.ORE_RICH);
    public static final Material Sphalerite = of("sphalerite", 0x93908D).flag(ORE).noTints(BlockMaterialType.ORE, BlockMaterialType.ORE_POOR, BlockMaterialType.ORE_RICH);
    public static final Material Tetrahedrite = of("tetrahedrite", 0x67676B).flag(ORE).noTints(BlockMaterialType.ORE, BlockMaterialType.ORE_POOR, BlockMaterialType.ORE_RICH);
    public static final Material BituminousCoal = of("bituminous_coal", 0x323423).flag(ORE).noTints(BlockMaterialType.ORE, BlockMaterialType.ORE_POOR, BlockMaterialType.ORE_RICH);
    public static final Material LigniteCoal = of("lignite", 0x5B4D3C).flag(ORE).noTints(BlockMaterialType.ORE, BlockMaterialType.ORE_POOR, BlockMaterialType.ORE_RICH);
    public static final Material Kaolinite = of("kaolinite", 0xE59885).flag(ORE).noTints(BlockMaterialType.ORE, BlockMaterialType.ORE_POOR, BlockMaterialType.ORE_RICH);
    public static final Material Gypsum = of("gypsum", 0x787B7A).flag(ORE).noTints(BlockMaterialType.ORE, BlockMaterialType.ORE_POOR, BlockMaterialType.ORE_RICH);
    public static final Material Satinspar = of("satinspar", 0xA9A4A8).flag(ORE).noTints(BlockMaterialType.ORE, BlockMaterialType.ORE_POOR, BlockMaterialType.ORE_RICH);
    public static final Material Selenite = of("selenite", 0xA3A39E).flag(ORE).noTints(BlockMaterialType.ORE, BlockMaterialType.ORE_POOR, BlockMaterialType.ORE_RICH);
    public static final Material Kimberlite = of("kimberlite", 0xB1AB9E).flag(ORE).noTints(BlockMaterialType.ORE, BlockMaterialType.ORE_POOR, BlockMaterialType.ORE_RICH);
    public static final Material PetrifiedWood = of("petrified_wood", 0x53493E).flag(ORE).noTints(BlockMaterialType.ORE, BlockMaterialType.ORE_POOR, BlockMaterialType.ORE_RICH);
    public static final Material Jet = of("jet", 0x2B2E29).flag(ORE).noTints(BlockMaterialType.ORE, BlockMaterialType.ORE_POOR, BlockMaterialType.ORE_RICH);
    public static final Material Microcline = of("microcline", 0x368874).flag(ORE).noTints(BlockMaterialType.ORE, BlockMaterialType.ORE_POOR, BlockMaterialType.ORE_RICH);
    public static final Material Pitchblende = of("pitchblende", 0x7E6A53).flag(ORE).noTints(BlockMaterialType.ORE, BlockMaterialType.ORE_POOR, BlockMaterialType.ORE_RICH);
    public static final Material Cryolite = of("cryolite", 0xF6F8F8).flag(ORE).noTints(BlockMaterialType.ORE, BlockMaterialType.ORE_POOR, BlockMaterialType.ORE_RICH);
    public static final Material Saltpeter = of("saltpeter", 0x959591).flag(ORE).noTints(BlockMaterialType.ORE, BlockMaterialType.ORE_POOR, BlockMaterialType.ORE_RICH);
    public static final Material Serpentine = of("serpentine", 0x686E56).flag(ORE).noTints(BlockMaterialType.ORE, BlockMaterialType.ORE_POOR, BlockMaterialType.ORE_RICH);
    public static final Material Sylvite = of("sylvite", 0x93522C).flag(ORE).noTints(BlockMaterialType.ORE, BlockMaterialType.ORE_POOR, BlockMaterialType.ORE_RICH);
    public static final Material Borax = of("borax", 0xACACAB).flag(ORE).noTints(BlockMaterialType.ORE, BlockMaterialType.ORE_POOR, BlockMaterialType.ORE_RICH);

    /** Metallurgy **/

    // Overworld TODO: colours
    public static final Material DeepIron = of("deep_iron", 0x808080).flag(ORE);
    public static final Material Prometheum = of("prometheum", 0x808080).flag(ORE);
    public static final Material Oureclase = of("oureclase", 0x808080).flag(ORE);
    public static final Material Infuscolium = of("infuscolium", 0x808080).flag(ORE);
    public static final Material Adamantine = of("adamantine", 0x808080).flag(ORE);
    public static final Material Rubracium = of("rubracium", 0x808080).flag(ORE);
    public static final Material Atlarus = of("atlarus", 0x808080).flag(ORE);
    public static final Material Carmot = of("carmot", 0x808080).flag(ORE);
    public static final Material Mithril = of("mithril", 0x808080).flag(ORE);
    public static final Material Orichalcum = of("orichalcum", 0x808080).flag(ORE);
    public static final Material AstralSilver = of("astral_silver", 0x808080).flag(ORE);
    public static final Material Lutetium = of("lutetium", 0x808080, Lu).flag(ORE);

    /** Netherrocks **/
    public static final Material Argonite = of("argonite", 0x32004D).flag(ORE); //harvest=4, maxUse=1300, eff=8F, att=3F, ench=18
    public static final Material Ashstone = of("ashstone", 0x5A5A5A).flag(ORE); //harvest=3, maxUse=900, eff=16F, att=2F, ench=7
    public static final Material Dragonstone = of("dragonstone", 0x740303).flag(ORE); //harvest=4, maxUse=4000, eff=10F, att=8F, ench=27
    public static final Material Fyrite = of("fyrite", 0xFC3300).flag(ORE); //harvest=3, maxUse=700, eff=8F, att=4F, ench=15
    public static final Material Illumenite = of("illumenite", 0xFCFF00).flag(ORE); //harvest=3, maxUse=700, eff=9F, att=3F, ench=39

    /** Thermal Foundation **/

    public static final Material Coke = of("coke", 0x4C4335).flag(GENERATE_DUST_VARIANTS).items(ItemMaterialType.COAL);
    public static final Material Electrum = of("electrum", 0xFFFF64).flag(GENERATE_DEFAULT_METAL_TYPES).items(COIL).fluid(MOLTEN, fluid -> fluid.temperature(1337));

    public static final Material Aerotheum = of("aerotheum", 0xD5D181).flag(GENERATE_DUST_VARIANTS).fluid(MOLTEN, fluid -> fluid.still("blocks/fluids/aerotheum_still").flow("blocks/fluids/aerotheum_flow").noTint().density(-800).viscosity(100).gasLike().rarity(EnumRarity.RARE));
    public static final Material Cryotheum = of("cryotheum", 0x49EFFF).flag(GENERATE_DUST_VARIANTS).fluid(MOLTEN, fluid -> fluid.still("blocks/fluids/cryotheum_still").flow("blocks/fluids/cryotheum_flow").noTint().density(4000).viscosity(4000).temperature(50).rarity(EnumRarity.RARE));
    public static final Material Petrotheum = of("petrotheum", 0x6E5A5D).flag(GENERATE_DUST_VARIANTS).fluid(MOLTEN, fluid -> fluid.still("blocks/fluids/petrotheum_still").flow("blocks/fluids/petrotheum_flow").noTint().density(4000).viscosity(1500).temperature(350).rarity(EnumRarity.RARE));
    public static final Material Pyrotheum = of("pyrotheum", 0xE56000).flag(GENERATE_DUST_VARIANTS).fluid(MOLTEN, fluid -> fluid.still("blocks/fluids/pyrotheum_still").flow("blocks/fluids/pyrotheum_flow").noTint().luminosity(15).density(2000).viscosity(1200).temperature(4000).rarity(EnumRarity.RARE));
    public static final Material Blizz = of("blizz", 0x211B2E).flag(GENERATE_DUST_VARIANTS).items(ROD);
    public static final Material Blitz = of("blitz", 0x211B2E).flag(GENERATE_DUST_VARIANTS).items(ROD);
    public static final Material Basalz = of("basalz", 0x211B2E).flag(GENERATE_DUST_VARIANTS).items(ROD);
    public static final Material Cinnabar = of("cinnabar", 0xAF666F).flag(ORE).formula(b -> b.element(Hg).element(S)).items(CRYSTAL); // TF BF4538, TFC AF666F

    public static final Material Niter = of("niter", 0xFFC8C8).flag(ORE, GENERATE_DUST_VARIANTS).formula(b -> b.element(K).element(N).element(O, 3));
    public static final Material Sulfur = of("sulfur", 0xC8C800).flag(ORE, GENERATE_DUST_VARIANTS).formula(S); // TF C8C800, TFC 979E43

    public static final Material Tar = of("tar", 0x2E2E2E);
    // public static final Material ROSIN = of("rosin", 0xE68821).types(GLOB);

    public static final Material PrimalMana = of("mana", 0x065E8E).flag(GENERATE_DEFAULT_METAL_TYPES).fluid(MOLTEN, fluid -> fluid.still("blocks/fluids/mana_still").flow("blocks/fluids/mana_flow").noTint().luminosity(15).density(600).viscosity(6000).rarity(EnumRarity.EPIC));

    /* Pure Fluid Materials */
    public static final Material Biocrude = of("biocrude", 0x346217).fluid(LIQUID, fluid -> fluid.still("blocks/fluids/biocrude_still").flow("blocks/fluids/biocrude_flow").noTint().customTranslation().density(1500).viscosity(2500));
    public static final Material Creosote = of("creosote", 0x804000).fluid(LIQUID, fluid -> fluid.still("blocks/fluids/creosote_still").flow("blocks/fluids/creosote_flow").noTint().customTranslation().density(1100).viscosity(2000));
    public static final Material Crude_oil = of("crude_oil", 0x666666).fluid(LIQUID, fluid -> fluid.still("blocks/fluids/crude_oil_still").flow("blocks/fluids/crude_oil_flow").noTint().customTranslation().density(900).viscosity(2000));
    public static final Material Experience = of("experience", 0x28CE0A).fluid(LIQUID, fluid -> fluid.still("blocks/fluids/experience_still").flow("blocks/fluids/experience_flow").noTint().luminosity(12).density(-200).viscosity(200).rarity(EnumRarity.UNCOMMON));
    public static final Material Mushroom_stew = of("mushroom_stew", 0xB79474).fluid(LIQUID, fluid -> fluid.still("blocks/fluids/mushroom_stew_still").flow("blocks/fluids/mushroom_stew_flow").noTint().customTranslation().density(2000).viscosity(2000));
    public static final Material Refined_biofuel = of("refined_biofuel", 0x7FA814).fluid(LIQUID, fluid -> fluid.still("blocks/fluids/refined_biofuel_still").flow("blocks/fluids/refined_biofuel_flow").noTint().customTranslation().density(750).viscosity(800));
    public static final Material Refined_oil = of("refined_oil", 0x666666).fluid(LIQUID, fluid -> fluid.still("blocks/fluids/refined_oil_still").flow("blocks/fluids/refined_oil_flow").noTint().customTranslation().density(800).viscosity(1400));
    public static final Material Refined_fuel = of("refined_fuel", 0xFFFF00).fluid(LIQUID, fluid -> fluid.still("blocks/fluids/refined_fuel_still").flow("blocks/fluids/refined_fuel_flow").noTint().customTranslation().density(750).viscosity(800));
    public static final Material Resin = of("resin", 0xA66E00).fluid(LIQUID, fluid -> fluid.still("blocks/fluids/resin_still").flow("blocks/fluids/resin_flow").noTint().customTranslation().density(900).viscosity(3000));
    public static final Material Sap = of("sap", 0x946426).fluid(LIQUID, fluid -> fluid.still("blocks/fluids/sap_still").flow("blocks/fluids/sap_flow").noTint().customTranslation().density(1050).viscosity(1500));
    public static final Material SeedOil = of("seed_oil", 0xC4FF00).fluid(LIQUID, fluid -> fluid.still("blocks/fluids/seed_oil_still").flow("blocks/fluids/seed_oil_flow").noTint().customTranslation().density(950).viscosity(1300));
    // Fixme UNUSED: public static final Material SYRUP = of("syrup", 0x946426).fluid(LIQUID, fluid -> fluid.still("blocks/fluids/sap_still").flow("blocks/fluids/sap_flow").noTint().density(1400).viscosity(2500));
    public static final Material Steam = of("steam", 0xFFFFFF).fluid(GASEOUS, fluid -> fluid.still("blocks/fluids/steam_still").flow("blocks/fluids/steam_flow").noTint().customTranslation().viscosity(200).temperature(750));
    public static final Material TreeOil = of("tree_oil", 0x8F7638).fluid(LIQUID, fluid -> fluid.still("blocks/fluids/tree_oil_still").flow("blocks/fluids/tree_oil_flow").noTint().customTranslation().density(900).viscosity(1200));

    /* Marker/Pseudo Materials - TODO: Match colours with electric tier defaults(?) */
    // public static final Material Basic = of("basic").items(FERTILIZER, SLAG, BAIT).noTints(FERTILIZER, SLAG).tex(FERTILIZER, "items/fertilizer/basic", 0).tex(SLAG, "items/slag/basic", 0);
    // public static final Material Rich = of("rich").items(FERTILIZER, SLAG, BAIT).noTints(FERTILIZER, SLAG).tex(FERTILIZER, "items/fertilizer/rich", 0).tex(SLAG, "items/slag/rich", 0);
    // public static final Material Flux = of("flux").items(FERTILIZER, BAIT).noTint(FERTILIZER).tex(FERTILIZER, "items/fertilizer/flux", 0);

    public static void init() {
        Potions.init();

        Material.all(Material::prepare);

        Materials.Charcoal.setItem(ItemMaterialType.COAL, Items.COAL, 1);
        Materials.Coal.setItem(ItemMaterialType.COAL, Items.COAL, 0);
        Materials.Iron.setItem(ItemMaterialType.INGOT, Items.IRON_INGOT);
        Materials.Iron.setItem(ItemMaterialType.NUGGET, Items.IRON_NUGGET);
        Materials.Gold.setItem(ItemMaterialType.INGOT, Items.GOLD_INGOT);
        Materials.Gold.setItem(ItemMaterialType.NUGGET, Items.GOLD_NUGGET);
        Materials.Redstone.setItem(ItemMaterialType.DUST, Items.REDSTONE);
        Materials.EnderEye.setItem(ItemMaterialType.GEM, Items.ENDER_EYE);
        Materials.EnderPearl.setItem(ItemMaterialType.GEM, Items.ENDER_PEARL);

        Materials.Coal.setBlock(BlockMaterialType.STORAGE, Blocks.COAL_BLOCK.getDefaultState());
        Materials.Iron.setBlock(BlockMaterialType.STORAGE, Blocks.IRON_BLOCK.getDefaultState());
        Materials.Gold.setBlock(BlockMaterialType.STORAGE, Blocks.GOLD_BLOCK.getDefaultState());
        Materials.Redstone.setBlock(BlockMaterialType.STORAGE, Blocks.REDSTONE_BLOCK.getDefaultState());
    }

    public static class Potions {

        /** Potions **/
        public static final Material Normal = of("potion", 0xF800F8).provideFluid(LIQUID, new PotionFluid("potion", "potion.effect.").setLuminosity(3).setDensity(500).setViscosity(1500).setRarity(EnumRarity.UNCOMMON));
        public static final Material Splash = of("potion_splash", 0xF800F8).provideFluid(LIQUID, new PotionFluid("potion_splash", "splash_potion.effect.").setLuminosity(3).setDensity(500).setViscosity(1500).setRarity(EnumRarity.UNCOMMON));
        public static final Material Lingering = of("potion_lingering", 0xF800F8).provideFluid(LIQUID, new PotionFluid("potion_lingering", "lingering_potion.effect.").setLuminosity(3).setDensity(500).setViscosity(1500).setRarity(EnumRarity.UNCOMMON));

        public static void init() { }

        public static FluidStack getNormalFluid(PotionType type, int amount) {
            if (type == null || type == PotionTypes.EMPTY) {
                return null;
            }
            if (type == PotionTypes.WATER) {
                return new FluidStack(FluidRegistry.WATER, amount);
            }
            FluidStack stack = Normal.getStack(LIQUID, amount);
            return topUp(stack, type);
        }

        public static FluidStack getSplashFluid(PotionType type, int amount) {
            if (type == null || type == PotionTypes.EMPTY) {
                return null;
            }
            if (type == PotionTypes.WATER) {
                return new FluidStack(FluidRegistry.WATER, amount);
            }
            FluidStack stack = Splash.getStack(LIQUID, amount);
            return topUp(stack, type);
        }

        public static FluidStack getLingeringFluid(PotionType type, int amount) {
            if (type == null || type == PotionTypes.EMPTY) {
                return null;
            }
            if (type == PotionTypes.WATER) {
                return new FluidStack(FluidRegistry.WATER, amount);
            }
            FluidStack stack = Lingering.getStack(LIQUID, amount);
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
            return stack.getFluid().getName().equals(Normal.getName());
        }

        public static boolean isSplashPotion(FluidStack stack) {
            return stack.getFluid().getName().equals(Splash.getName());
        }

        public static boolean isLingeringPotion(FluidStack stack) {
            return stack.getFluid().getName().equals(Lingering.getName());
        }

    }

}
