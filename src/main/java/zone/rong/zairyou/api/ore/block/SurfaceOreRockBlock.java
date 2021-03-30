package zone.rong.zairyou.api.ore.block;

import com.google.common.collect.ImmutableSet;
import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
import it.unimi.dsi.fastutil.objects.ObjectOpenHashSet;
import it.unimi.dsi.fastutil.objects.ObjectSets;
import net.dries007.tfc.api.types.Rock;
import net.minecraft.block.Block;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.block.model.ModelRotation;
import net.minecraft.client.renderer.block.model.WeightedBakedModel;
import net.minecraft.client.renderer.block.statemap.StateMapperBase;
import net.minecraft.client.renderer.color.IBlockColor;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraftforge.client.event.ModelBakeEvent;
import net.minecraftforge.client.model.ModelLoader;
import zone.rong.zairyou.Zairyou;
import zone.rong.zairyou.api.block.IBlockGetter;
import zone.rong.zairyou.api.block.itemblock.MetaItemBlock;
import zone.rong.zairyou.api.block.metablock.AbstractMetaBlock;
import zone.rong.zairyou.api.block.metablock.MutableMetaBlockBuilder;
import zone.rong.zairyou.api.client.Bakery;
import zone.rong.zairyou.api.client.IModelOverride;
import zone.rong.zairyou.api.client.model.baked.RotateModel;
import zone.rong.zairyou.api.material.Material;
import zone.rong.zairyou.api.material.type.BlockMaterialType;
import zone.rong.zairyou.api.material.type.ItemMaterialType;
import zone.rong.zairyou.api.ore.stone.StoneType;
import zone.rong.zairyou.api.property.type.MaterialProperty;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class SurfaceOreRockBlock extends AbstractMetaBlock<MaterialProperty, Material, MetaItemBlock> implements IModelOverride, IBlockColor {

    public static final AxisAlignedBB GROUNDCOVER_AABB = new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 0.0625D, 1.0D);

    private static WeightedBakedModel bakedModel;

    private static WeightedBakedModel getOrBake() {
        if (bakedModel != null) {
            return bakedModel;
        }
        Bakery.ModelType type = new Bakery.ModelType(Zairyou.ID, "block/surface_ore_rock");
        final ModelRotation[] rots = ModelRotation.values();
        WeightedBakedModel.Builder builder = new WeightedBakedModel.Builder();
        IntStream.range(0, StoneType.VALUES.length).forEach(i -> IntStream.range(0, 4).forEach(j -> builder.add(Bakery.INSTANCE.getBlockDepartment()
                .template(type)
                .prepareTexture("layer0", StoneType.VALUES[i].getTypeLocation(Rock.Type.RAW))
                .prepareTexture("layer1", StoneType.VALUES[StoneType.VALUES.length - 1 - i].getTypeLocation(Rock.Type.RAW))
                .mutate(m -> new RotateModel(m, rots[j]))
                .bake()
                .take(), 1)));
        return bakedModel = builder.build();
    }

    public static void create() {
        new MutableMetaBlockBuilder<>("surface_ore_rock", SurfaceOreRockBlock.class, net.minecraft.block.material.Material.ROCK, MaterialProperty.class)
                .entries(Material.all().stream().filter(m -> m.hasType(BlockMaterialType.ORE_SURFACE_ROCK)).collect(Collectors.toList()))
                .modify((l, b) -> l.forEach(m -> m.setBlock(BlockMaterialType.ORE_SURFACE_ROCK, b.getDefaultState().withProperty(b.freezableProperty, m))))
                .build();
    }

    public SurfaceOreRockBlock(net.minecraft.block.material.Material material, MaterialProperty property) {
        super(material, property);
    }

    @Override
    public Supplier<MetaItemBlock> getItemBlock() {
        return () -> null;
    }

    @Override
    public Item getItemDropped(IBlockState state, Random rand, int fortune) {
        return state.getValue(freezableProperty).getItem(ItemMaterialType.ORE_SMALL, false).getItem();
    }

    @Override
    public int damageDropped(IBlockState state) {
        return 0;
    }

    @Override
    public boolean canPlaceBlockAt(World world, @Nonnull BlockPos pos) {
        return world.isSideSolid(pos.down(), EnumFacing.UP) && super.canPlaceBlockAt(world, pos);
    }

    @Override
    public boolean isReplaceable(IBlockAccess world, @Nonnull BlockPos pos) {
        return true;
    }

    @Override
    public void neighborChanged(IBlockState state, World world, BlockPos pos, Block blockIn, BlockPos fromPos) {
        if (!world.isSideSolid(pos.down(), EnumFacing.UP)) {
            world.destroyBlock(pos, true);
        }
    }

    @Override
    public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        if (!world.isRemote) {
            world.destroyBlock(pos, true);
        }
        return true;
    }

    @Override
    public boolean canHarvestBlock(IBlockAccess world, @Nonnull BlockPos pos, @Nonnull EntityPlayer player) {
        return true;
    }

    @Override
    @Nonnull
    public ItemStack getPickBlock(IBlockState state, RayTraceResult target, World world, BlockPos pos, EntityPlayer player) {
        return state.getValue(freezableProperty).getItem(ItemMaterialType.ORE_SMALL, true);
    }

    @Nonnull
    @Override
    public BlockFaceShape getBlockFaceShape(IBlockAccess worldIn, IBlockState state, BlockPos pos, EnumFacing face) {
        return BlockFaceShape.UNDEFINED;
    }

    @Nonnull
    @Override
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
        return GROUNDCOVER_AABB;
    }

    @Nullable
    @Override
    public AxisAlignedBB getCollisionBoundingBox(IBlockState state, @Nonnull IBlockAccess world, @Nonnull BlockPos pos) {
        return NULL_AABB;
    }

    @Override
    public boolean isPassable(IBlockAccess world, BlockPos pos) {
        return true;
    }

    @Override
    public boolean isSideSolid(IBlockState state, @Nonnull IBlockAccess world, @Nonnull BlockPos pos, EnumFacing side) {
        return false;
    }

    @Override
    public boolean isFullBlock(IBlockState state) {
        return false;
    }

    @Override
    public boolean isFullCube(IBlockState state) {
        return false;
    }

    @Override
    public boolean isOpaqueCube(IBlockState state) {
        return false;
    }

    @Override
    public boolean isNormalCube(IBlockState state, IBlockAccess world, BlockPos pos) {
        return false;
    }

    @Override
    public boolean addLandingEffects(IBlockState state, WorldServer world, BlockPos blockPosition, IBlockState iblockstate, EntityLivingBase entity, int numberOfParticles) {
        return true;
    }

    @Override
    public boolean addRunningEffects(IBlockState state, World world, BlockPos pos, Entity entity) {
        return true;
    }

    @Override
    public void addTextures(Set<ResourceLocation> textures) { }

    @Override
    public void onModelRegister() {
        ModelLoader.setCustomStateMapper(this, new StateMapperBase() {
            @Override
            protected ModelResourceLocation getModelResourceLocation(IBlockState state) {
                return new ModelResourceLocation(state.getValue(freezableProperty).getName() + "_surface_ore_rock");
            }
        });
    }

    @Override
    public void onModelBake(ModelBakeEvent event) {
        this.blockState.getValidStates().forEach(s -> {
            Material material = s.getValue(freezableProperty);
            event.getModelRegistry().putObject(new ModelResourceLocation(material.getName() + "_surface_ore_rock"), getOrBake());
        });
    }

    @Override
    public int colorMultiplier(IBlockState state, @Nullable IBlockAccess worldIn, @Nullable BlockPos pos, int tintIndex) {
        return tintIndex == 0 ? state.getValue(freezableProperty).getColour() : -1;
    }

    public static class Getter implements IBlockGetter {

        private final Map<Material, IBlockState> map = new Object2ObjectOpenHashMap<>();

        @Override
        public void supplyBlock(IBlockState state, Object... arguments) {
            if (arguments.length > 1) {
                throw new UnsupportedOperationException("This getter only allows 1 argument.");
            }
            for (Object argument : arguments) {
                if (!(argument instanceof Material)) {
                    throw new UnsupportedOperationException("Argument not supported");
                }
            }
            map.put((Material) arguments[0], state);
        }

        @Nullable
        @Override
        public IBlockState getBlockState(Object... arguments) {
            if (arguments.length > 1) {
                throw new UnsupportedOperationException("This getter only allows 1 argument.");
            }
            return map.get(arguments[0]);
        }

        @Override
        public Set<Block> getBlocks() {
            return ObjectSets.unmodifiable(new ObjectOpenHashSet<>(map.values().stream().map(IBlockState::getBlock).iterator()));
        }

        @Override
        public Set<IBlockState> getBlockStates() {
            return ImmutableSet.copyOf(map.values());
        }
    }
}
