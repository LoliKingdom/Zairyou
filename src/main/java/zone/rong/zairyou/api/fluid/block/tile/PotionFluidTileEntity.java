package zone.rong.zairyou.api.fluid.block.tile;

import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.PotionTypes;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.potion.PotionType;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import zone.rong.zairyou.Zairyou;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Map;

@Deprecated
public class PotionFluidTileEntity extends TileEntity {

    public static final ResourceLocation ID = new ResourceLocation(Zairyou.ID, "potion_fluid");

    public static Map<BlockPos, PotionFluidTileEntity> posToTile;
    public static Map<PotionFluidTileEntity, List<BlockPos>> tileToPos;

    public static PotionType getPotionType(BlockPos pos) {
        PotionFluidTileEntity tile = posToTile.get(pos);
        return tile == null ? PotionTypes.EMPTY : tile.getPotionType();
    }

    public static void setRelation(BlockPos fromPos, BlockPos toPos) {
        PotionFluidTileEntity tile = posToTile.get(fromPos);
        if (tile != null) {
            posToTile.put(toPos, tile);
            tileToPos.get(tile).add(toPos);
            tile.sendUpdates();
        }
    }

    private PotionType potionType = PotionTypes.EMPTY;

    public PotionType getPotionType() {
        return potionType;
    }

    public void setPotionType(PotionType potionType) {
        this.potionType = potionType;
        // this.world.notifyBlockUpdate(this.pos, );
    }

    private void sendUpdates() {
        this.world.markBlockRangeForRenderUpdate(pos, pos);
        IBlockState state = this.world.getBlockState(this.pos);
        this.world.notifyBlockUpdate(pos, state, state, 3);
        this.world.scheduleBlockUpdate(pos, state.getBlock(), 0, 0);
        markDirty();
    }

    @Override
    @Nullable
    public SPacketUpdateTileEntity getUpdatePacket() {
        return new SPacketUpdateTileEntity(this.pos, 3, this.getUpdateTag());
    }

    @Override
    public NBTTagCompound getUpdateTag() {
        return this.writeToNBT(new NBTTagCompound());
    }

    @Override
    public void onDataPacket(NetworkManager net, SPacketUpdateTileEntity pkt) {
        super.onDataPacket(net, pkt);
        handleUpdateTag(pkt.getNbtCompound());
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound tag) {
        super.writeToNBT(tag);
        tag.setInteger("PotionType", PotionType.REGISTRY.getIDForObject(this.potionType));
        NBTTagCompound posTag = new NBTTagCompound();
        List<BlockPos> blockposList = tileToPos.get(this);
        for (int i = 0; i < blockposList.size(); i++) {
            posTag.setLong(String.valueOf(i), blockposList.get(i).toLong());
        }
        tag.setTag("BlockPosList", posTag);
        return tag;
    }

    @Override
    public void readFromNBT(NBTTagCompound tag) {
        this.potionType = PotionType.REGISTRY.getObjectById(tag.getInteger("PotionType"));
        NBTTagCompound blockposList = tag.getCompoundTag("BlockPosList");
        for (int i = 0; i < blockposList.getSize(); i++) {
            BlockPos pos = BlockPos.fromLong(blockposList.getLong(String.valueOf(i)));
            posToTile.put(pos, this);
            tileToPos.computeIfAbsent(this, k -> new ObjectArrayList<>()).add(pos);
        }
        super.readFromNBT(tag);
    }

    @Override
    public void validate() {
        super.validate();
        if (posToTile == null) {
            posToTile = new Object2ObjectOpenHashMap<>();
        }
        posToTile.put(this.pos, this);
        if (tileToPos == null) {
            tileToPos = new Object2ObjectOpenHashMap<>();
        }
        final List<BlockPos> posList = new ObjectArrayList<>();
        posList.add(this.pos);
        tileToPos.put(this, posList);
    }

    @Override
    public void invalidate() {
        super.invalidate();
        for (BlockPos pos : tileToPos.get(this)) {
            posToTile.remove(pos);
        }
        tileToPos.remove(this);
    }

}
