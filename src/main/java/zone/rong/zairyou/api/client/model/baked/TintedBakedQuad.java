package zone.rong.zairyou.api.client.model.baked;

import net.minecraft.client.renderer.block.model.BakedQuad;
import zone.rong.zairyou.api.client.RenderUtils;

public class TintedBakedQuad extends BakedQuad {

    private final int[] colouredVertexData;

    public TintedBakedQuad(BakedQuad quad, int rgb) {
        super(quad.getVertexData(), quad.getTintIndex(), quad.getFace(), quad.getSprite(), quad.shouldApplyDiffuseLighting(), quad.getFormat());
        this.colouredVertexData = quad.getVertexData();
        int size = format.getIntegerSize();
        int offset = format.getColorOffset() / 4;
        for (int i = 0; i < 4; i++) {
            this.colouredVertexData[offset + size * i] = RenderUtils.convertRGB2ABGR(rgb);
        }
    }

    @Override
    public int[] getVertexData() {
        return this.colouredVertexData;
    }

    @Override
    public boolean hasTintIndex() {
        return false;
    }

}
