package kr.syeyoung.dungeonsguide.roomprocessor.waterpuzzle.nodes;

import kr.syeyoung.dungeonsguide.roomprocessor.waterpuzzle.WaterCondition;
import kr.syeyoung.dungeonsguide.roomprocessor.waterpuzzle.WaterNode;
import lombok.AllArgsConstructor;
import lombok.Data;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;

@Data
@AllArgsConstructor
public class WaterNodeStart implements WaterNode {

    private BlockPos blockPos;
    private boolean isReversed;

    @Override
    public boolean canWaterGoThrough() {
        return true;
    }

    @Override
    public WaterCondition getCondition() {
        return new WaterCondition("mainStream", !isReversed);
    }

    @Override
    public boolean isWaterFilled(World w) {
        Block b = w.getChunkFromBlockCoords(blockPos).getBlock(blockPos);
        return b == Blocks.water || b == Blocks.flowing_water;
    }

    public boolean isTriggered(World w) {
        return isWaterFilled(w);
    }


    public BlockPos getBlockPos() {
        return blockPos;
    }
    private int x,y;

    public String toString() {
        return "S";
    }
}