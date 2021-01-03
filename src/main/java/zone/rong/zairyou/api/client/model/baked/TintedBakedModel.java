package zone.rong.zairyou.api.client.model.baked;

import it.unimi.dsi.fastutil.ints.Int2IntMap;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.block.model.ItemOverrideList;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.util.EnumFacing;

import javax.annotation.Nullable;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

@Deprecated
public class TintedBakedModel implements IBakedModel {

    // private final IBakedModel baked;
    // private final Int2IntMap tints;

    private final boolean ao, gui3d, builtin;
    private final TextureAtlasSprite particleSprite;
    private final ItemOverrideList overrides;

    private final List<BakedQuad> newGeneralQuads;
    private final Map<EnumFacing, List<BakedQuad>> newFaceQuads;

    public TintedBakedModel(IBakedModel parent, Int2IntMap tints) {
        this.ao = parent.isAmbientOcclusion();
        this.gui3d = parent.isGui3d();
        this.builtin = parent.isBuiltInRenderer();
        this.particleSprite = parent.getParticleTexture();
        this.overrides = parent.getOverrides();
        this.newGeneralQuads = new ObjectArrayList<>();
        for (BakedQuad generalQuad : parent.getQuads(null, null, 0L)) {
            BakedQuad newGeneralQuad;
            if (tints.containsKey(generalQuad.getTintIndex())) {
                newGeneralQuad = new TintedBakedQuad(generalQuad, tints.get(generalQuad.getTintIndex()));
            } else {
                newGeneralQuad = generalQuad;
            }
            this.newGeneralQuads.add(newGeneralQuad);
        }
        this.newFaceQuads = new EnumMap<>(EnumFacing.class);
        for (EnumFacing face : EnumFacing.VALUES) {
            List<BakedQuad> eachFaceQuads = new ObjectArrayList<>();
            parent.getQuads(null, face, 0L).forEach(faceQuad -> {
                BakedQuad newFaceQuad;
                if (tints.containsKey(faceQuad.getTintIndex())) {
                    newFaceQuad = new TintedBakedQuad(faceQuad, tints.get(faceQuad.getTintIndex()));
                } else {
                    newFaceQuad = faceQuad;
                }
                eachFaceQuads.add(newFaceQuad);
            });
            this.newFaceQuads.put(face, eachFaceQuads);
        }
    }

    @Override
    public List<BakedQuad> getQuads(@Nullable IBlockState state, @Nullable EnumFacing side, long rand) {
        return side == null ? this.newGeneralQuads : this.newFaceQuads.get(side);
    }

    @Override
    public boolean isAmbientOcclusion() {
        return ao;
    }

    @Override
    public boolean isGui3d() {
        return gui3d;
    }

    @Override
    public boolean isBuiltInRenderer() {
        return builtin;
    }

    @Override
    public TextureAtlasSprite getParticleTexture() {
        return particleSprite;
    }

    @Override
    public ItemOverrideList getOverrides() {
        return overrides;
    }
}
