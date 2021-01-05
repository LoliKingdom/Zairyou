package zone.rong.zairyou.api.fluid.block;

import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.color.IBlockColor;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.PotionTypes;
import net.minecraft.potion.PotionEffect;
import net.minecraft.potion.PotionType;
import net.minecraft.potion.PotionUtils;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.util.Constants;
import net.minecraftforge.event.ForgeEventFactory;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidUtil;
import zone.rong.zairyou.api.fluid.FluidType;
import zone.rong.zairyou.api.fluid.block.tile.PotionFluidTileEntity;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Random;

@Deprecated
public class PotionFluidBlock extends DefaultFluidBlock implements ITileEntityProvider, IBlockColor {

    public PotionFluidBlock(Fluid fluid, FluidType type) {
        super(fluid, type);
    }

    @Override
    public void updateTick(@Nonnull World world, @Nonnull BlockPos pos, @Nonnull IBlockState state, @Nonnull Random rand) {
        int quantaRemaining = quantaPerBlock - state.getValue(LEVEL);
        int expQuanta = -101;
        if (quantaRemaining < quantaPerBlock) {
            int adjacentSourceBlocks = 0;
            if (ForgeEventFactory.canCreateFluidSource(world, pos, state, canCreateSources)) {
                for (EnumFacing side : EnumFacing.Plane.HORIZONTAL) {
                    if (isSourceBlock(world, pos.offset(side))) {
                        adjacentSourceBlocks++;
                    }
                }
            }
            // new source block
            if (adjacentSourceBlocks >= 2 && (world.getBlockState(pos.up(densityDir)).getMaterial().isSolid() || isSourceBlock(world, pos.up(densityDir)))) {
                expQuanta = quantaPerBlock;
            }
            // vertical flow into block
            else if (world.getBlockState(pos.down(densityDir)).getBlock() == this) { // BlockFluidBase#hasVerticalFlow{
                expQuanta = quantaPerBlock - 1;
            } else {
                int maxQuanta = -100;
                for (EnumFacing side : EnumFacing.Plane.HORIZONTAL) {
                    maxQuanta = getLargerQuanta(world, pos.offset(side), maxQuanta);
                }
                expQuanta = maxQuanta - 1;
            }
            // decay calculation
            if (expQuanta != quantaRemaining) {
                quantaRemaining = expQuanta;
                if (expQuanta <= 0) {
                    world.setBlockToAir(pos);
                } else {
                    world.setBlockState(pos, state.withProperty(LEVEL, quantaPerBlock - expQuanta), Constants.BlockFlags.SEND_TO_CLIENTS);
                    world.scheduleUpdate(pos, this, tickRate);
                    world.notifyNeighborsOfStateChange(pos, this, false);
                }
            }
        }
        // Flow vertically if possible
        if (canDisplace(world, pos.up(densityDir))) {
            flowIntoBlock(world, pos, pos.up(densityDir), 1);
            return;
        }
        // Flow outward if possible
        int flowMeta = quantaPerBlock - quantaRemaining + 1;
        if (flowMeta >= quantaPerBlock) {
            return;
        }
        if (isSourceBlock(world, pos) || !isFlowingVertically(world, pos)) {
            if (world.getBlockState(pos.down(densityDir)).getBlock() == this) { // BlockFluidBase#hasVerticalFlow
                flowMeta = 1;
            }
            boolean[] flowTo = getOptimalFlowDirections(world, pos);
            for (int i = 0; i < 4; i++) {
                if (flowTo[i]) {
                    flowIntoBlock(world, pos, pos.offset(SIDES.get(i)), flowMeta);
                }
            }
        }
    }

    protected void flowIntoBlock(World world, BlockPos fromPos, BlockPos toPos, int meta) {
        if (meta < 0) {
            return;
        }
        if (displaceIfPossible(world, toPos)) {
            world.setBlockState(toPos, this.getDefaultState().withProperty(LEVEL, meta));
            PotionFluidTileEntity.setRelation(fromPos, toPos);
        }
    }

    @Override
    public int place(World world, BlockPos pos, @Nonnull FluidStack fluidStack, boolean doPlace) {
        if (fluidStack.amount < Fluid.BUCKET_VOLUME) {
            return 0;
        }
        if (doPlace) {
            FluidUtil.destroyBlockOnFluidPlacement(world, pos);
            world.setBlockState(pos, this.getDefaultState(), Constants.BlockFlags.DEFAULT_AND_RERENDER);
            IBlockState state = world.getBlockState(pos);
            if (state.getBlock() == this) {
                TileEntity tile = world.getTileEntity(pos);
                if (tile instanceof PotionFluidTileEntity) {
                    PotionFluidTileEntity potionTile = (PotionFluidTileEntity) tile;
                    // potionTile.setPotionType(PotionFluid.getPotionType(fluidStack));
                }
            }
        }
        return Fluid.BUCKET_VOLUME;
    }

    @Override
    public void onEntityCollidedWithBlock(World world, BlockPos pos, IBlockState state, Entity entity) {
        if (!(entity instanceof EntityLivingBase)) {
            return;
        }
        EntityLivingBase livingEntity = (EntityLivingBase) entity;
        for (PotionEffect effect : PotionFluidTileEntity.getPotionType(pos).getEffects()) {
            if (livingEntity.isPotionApplicable(effect)) {
                livingEntity.addPotionEffect(effect);
            }
        }
    }

    @Nullable
    @Override
    public TileEntity createNewTileEntity(World world, int meta) {
        return new PotionFluidTileEntity();
    }

    @Override
    public int colorMultiplier(IBlockState state, @Nullable IBlockAccess world, @Nullable BlockPos pos, int tintIndex) {
        if (world != null && pos != null) {
            PotionType type = PotionFluidTileEntity.getPotionType(pos);
            if (type != PotionTypes.EMPTY) {
                return 0xFF000000 | PotionUtils.getPotionColorFromEffectList(type.getEffects());
            }
        }
        return 0;
    }

    /*
    private static class PotionEffectRetainer implements IWorldEventListener {

        private final PotionType potionType;
        // private final BlockPos originPos;

        public PotionEffectRetainer(PotionType potionType) {
            this.potionType = potionType;
        }

        public void scheduleRemoval(World world) {
            world.getMinecraftServer().addScheduledTask(() -> world.removeEventListener(this));
        }

        @Override
        public void notifyBlockUpdate(World world, BlockPos pos, IBlockState oldState, IBlockState newState, int flags) {

        }

        @Override
        public void notifyLightSet(BlockPos pos) { }

        @Override
        public void markBlockRangeForRenderUpdate(int x1, int y1, int z1, int x2, int y2, int z2) { }

        @Override
        public void playSoundToAllNearExcept(@Nullable EntityPlayer player, SoundEvent soundIn, SoundCategory category, double x, double y, double z, float volume, float pitch) { }

        @Override
        public void playRecord(SoundEvent soundIn, BlockPos pos) { }

        @Override
        public void spawnParticle(int particleID, boolean ignoreRange, double xCoord, double yCoord, double zCoord, double xSpeed, double ySpeed, double zSpeed, int... parameters) { }

        @Override
        public void spawnParticle(int id, boolean ignoreRange, boolean p_190570_3_, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed, int... parameters) { }

        @Override
        public void onEntityAdded(Entity entityIn) { }

        @Override
        public void onEntityRemoved(Entity entityIn) { }

        @Override
        public void broadcastSound(int soundID, BlockPos pos, int data) { }

        @Override
        public void playEvent(EntityPlayer player, int type, BlockPos blockPosIn, int data) { }

        @Override
        public void sendBlockBreakProgress(int breakerId, BlockPos pos, int progress) { }

    }
     */

}
