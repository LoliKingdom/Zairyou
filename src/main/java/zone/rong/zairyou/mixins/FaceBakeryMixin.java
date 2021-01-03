package zone.rong.zairyou.mixins;

import net.minecraft.client.renderer.block.model.*;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.client.ForgeHooksClient;
import net.minecraftforge.common.model.ITransformation;
import org.lwjgl.util.vector.Vector3f;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import zone.rong.zairyou.api.client.RenderUtils;
import zone.rong.zairyou.api.client.model.baked.FaceBakeryExtender;
import zone.rong.zairyou.api.client.model.baked.TintedBakedQuad;

@Deprecated
@Mixin(FaceBakery.class)
public abstract class FaceBakeryMixin implements FaceBakeryExtender {

    @Shadow protected abstract float[] getPositionsDiv16(Vector3f pos1, Vector3f pos2);
    @Shadow protected abstract int[] makeQuadVertexData(BlockFaceUV uvs, TextureAtlasSprite sprite, EnumFacing orientation, float[] p_188012_4_, ITransformation rotationIn, BlockPartRotation partRotation, boolean shade);
    @Shadow protected abstract void applyFacing(int[] p_178408_1_, EnumFacing p_178408_2_);

    @Shadow public static EnumFacing getFacingFromVertexData(int[] faceData) { return null; }

    @Override
    public TintedBakedQuad makeTintedQuad(Vector3f posFrom, Vector3f posTo, BlockPartFace face, TextureAtlasSprite sprite, EnumFacing facing, ITransformation modelRotationIn, BlockPartRotation partRotation, boolean uvLocked, int rgb) {
        BlockFaceUV blockfaceuv = face.blockFaceUV;
        if (uvLocked) {
            blockfaceuv = ForgeHooksClient.applyUVLock(face.blockFaceUV, facing, modelRotationIn);
        }
        int[] aint = this.makeQuadVertexData(blockfaceuv, sprite, facing, this.getPositionsDiv16(posFrom, posTo), modelRotationIn, partRotation, true); // This has to be true
        EnumFacing enumfacing = getFacingFromVertexData(aint);
        if (partRotation == null) {
            this.applyFacing(aint, enumfacing);
        }
        ForgeHooksClient.fillNormal(aint, enumfacing);
        int size = DefaultVertexFormats.ITEM.getIntegerSize();
        int offset = DefaultVertexFormats.ITEM.getColorOffset() / 4;
        for (int i = 0; i < 4; i++) {
            aint[offset + size * i] = RenderUtils.convertRGB2ABGR(rgb);
        }
        return new TintedBakedQuad(aint, face.tintIndex, enumfacing, sprite, false, DefaultVertexFormats.ITEM); // This has to be false
    }

}
