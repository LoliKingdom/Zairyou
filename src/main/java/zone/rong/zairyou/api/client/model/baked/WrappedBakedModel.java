package zone.rong.zairyou.api.client.model.baked;

import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.client.renderer.block.model.ItemOverrideList;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.util.EnumFacing;
import org.apache.commons.lang3.tuple.Pair;
import zone.rong.zairyou.api.client.RenderUtils;

import javax.annotation.Nullable;
import javax.vecmath.Matrix4f;
import java.util.List;

public class WrappedBakedModel implements IBakedModel {

    private final IBakedModel backingModel;
    private final boolean isItem;

    public WrappedBakedModel(IBakedModel backingModel, boolean isItem) {
        this.backingModel = backingModel;
        this.isItem = isItem;
    }

    @Override
    public List<BakedQuad> getQuads(@Nullable IBlockState state, @Nullable EnumFacing side, long rand) {
        return backingModel.getQuads(state, side, rand);
    }

    @Override
    public boolean isAmbientOcclusion() {
        return backingModel.isAmbientOcclusion();
    }

    @Override
    public boolean isGui3d() {
        return backingModel.isGui3d();
    }

    @Override
    public boolean isBuiltInRenderer() {
        return backingModel.isBuiltInRenderer();
    }

    @Override
    public TextureAtlasSprite getParticleTexture() {
        return backingModel.getParticleTexture();
    }

    @Override
    public ItemOverrideList getOverrides() {
        return backingModel.getOverrides();
    }

    @Override
    public Pair<? extends IBakedModel, Matrix4f> handlePerspective(ItemCameraTransforms.TransformType cameraTransformType) {
        return Pair.of(this, this.isItem ? RenderUtils.getItemTransform(cameraTransformType) : RenderUtils.getBlockTransform(cameraTransformType));
    }

}
