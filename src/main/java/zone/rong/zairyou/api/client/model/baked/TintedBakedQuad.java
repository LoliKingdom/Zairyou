package zone.rong.zairyou.api.client.model.baked;

import net.minecraft.client.renderer.block.model.BakedQuad;
import zone.rong.zairyou.api.client.RenderUtils;

public class TintedBakedQuad extends BakedQuad {

    private final int[] colouredVertexData;

    public TintedBakedQuad(BakedQuad quad, int rgb) {
        super(quad.getVertexData(), quad.getTintIndex(), quad.getFace(), quad.getSprite(), quad.shouldApplyDiffuseLighting(), quad.getFormat());
        /*
        for (int i = 0; i < 4; i++) {
            this.vertexData[(format.getColorOffset() / 4) + format.getIntegerSize() * i] = RenderUtils.convertRGB2ABGR(rgb);
        }
         */
        this.colouredVertexData = quad.getVertexData();
        for (int i = 0; i < 4; i++) {
            this.colouredVertexData[(format.getColorOffset() / 4) + format.getIntegerSize() * i] = RenderUtils.convertRGB2ABGR(rgb);
        }
    }

    @Override
    public int[] getVertexData() {
        return this.colouredVertexData;
    }
}
