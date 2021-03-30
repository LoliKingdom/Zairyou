package zone.rong.zairyou.api.ore.item;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import zone.rong.zairyou.api.item.MaterialItem;
import zone.rong.zairyou.api.material.Material;
import zone.rong.zairyou.api.material.type.BlockMaterialType;
import zone.rong.zairyou.api.material.type.ItemMaterialType;

public class OreItem extends MaterialItem {

    public OreItem(Material material, ItemMaterialType itemMaterialType) {
        super(material, itemMaterialType);
    }

    @Override
    public EnumActionResult onItemUse(EntityPlayer player, World world, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        if (!world.isRemote && this.itemMaterialType == ItemMaterialType.ORE_SMALL && player.isSneaking()) {
            BlockPos placePos;
            if (world.isSideSolid(pos, facing) && (world.isAirBlock(placePos = pos.up()) || world.getBlockState(placePos).getBlock().isReplaceable(world, placePos))) {
                world.setBlockState(placePos, this.material.getBlock(BlockMaterialType.ORE_SURFACE_ROCK), 3);
                world.playSound(null, placePos, SoundEvents.BLOCK_STONE_PLACE, SoundCategory.BLOCKS, 0.5F, 0.5F);
                return EnumActionResult.SUCCESS;
            }
        }
        return EnumActionResult.PASS;
    }

}
