package dusted.world.blocks.production;

import arc.graphics.*;
import arc.math.*;
import arc.util.*;
import mindustry.*;
import mindustry.graphics.*;
import mindustry.logic.*;
import mindustry.world.meta.*;

public class BoosterCrafter extends PowderCrafter {
    public float reload = 60f;
    public float range = 80f;
    public float speedBoost = 2f;
    public Color baseColor = Color.valueOf("c3b1f9");

    public BoosterCrafter(String name) {
        super(name);
        canOverdrive = false;
    }

    @Override
    public void drawPlace(int x, int y, int rotation, boolean valid) {
        super.drawPlace(x, y, rotation, valid);

        Drawf.dashCircle(x * Vars.tilesize + offset, y * Vars.tilesize + offset, range, baseColor);

        Vars.indexer.eachBlock(Vars.player.team(), x * Vars.tilesize + offset, y * Vars.tilesize + offset, range, other -> other.block.canOverdrive, other -> Drawf.selected(other, Tmp.c1.set(baseColor).a(Mathf.absin(4f, 1f))));
    }

    @Override
    public void setStats() {
        super.setStats();
        stats.add(Stat.speedIncrease, "+" + (int) (speedBoost * 100f - 100) + "%");
        stats.add(Stat.range, range / Vars.tilesize, StatUnit.blocks);
    }

    public class BoosterCrafterBuild extends PowderCrafterBuild implements Ranged {
        public float charge;

        @Override
        public float range() {
            return range;
        }

        @Override
        public void updateTile() {
            super.updateTile();
            charge += potentialEfficiency * Time.delta;

            if (charge >= reload) {
                charge = 0f;

                Vars.indexer.eachBlock(this, range, other -> other.block.canOverdrive, other -> other.applyBoost(speedBoost * potentialEfficiency, reload + 1f));
            }
        }

        @Override
        public void drawSelect() {
            Vars.indexer.eachBlock(this, range, other -> other.block.canOverdrive, other -> Drawf.selected(other, Tmp.c1.set(baseColor).a(Mathf.absin(4f, 1f))));

            Drawf.dashCircle(x, y, range, baseColor);
        }
    }
}
