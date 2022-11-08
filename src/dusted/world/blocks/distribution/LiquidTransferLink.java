package dusted.world.blocks.distribution;

import mindustry.*;
import mindustry.gen.*;
import mindustry.world.meta.*;

public class LiquidTransferLink extends TransferLink {
    public LiquidTransferLink(String name) {
        super(name);
        hasItems = false;
        hasLiquids = true;
        outputsLiquid = true;
        canOverdrive = false;
        group = BlockGroup.liquids;
    }

    public class LiquidTransferLinkBuild extends TransferLinkBuild {
        @Override
        public void updateTransfer() {
            Building other = Vars.world.build(links.get(cur));
            if (other != null) moveTime = moveLiquid(other, liquids.current()) > 0.05f ? 30f : moveTime;
            incrementCurrent();
        }

        @Override
        public void doDump() {
            dumpLiquid(liquids.current(), 1f);
        }
    }
}
