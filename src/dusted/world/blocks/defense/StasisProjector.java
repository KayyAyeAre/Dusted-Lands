package dusted.world.blocks.defense;

import arc.math.*;
import arc.util.*;
import dusted.content.*;
import mindustry.entities.*;
import mindustry.gen.*;
import mindustry.graphics.*;
import mindustry.logic.*;
import mindustry.world.*;
import mindustry.world.meta.*;

import static mindustry.Vars.tilesize;

public class StasisProjector extends Block {
    public final int stasisTimer = timers++;
    public float stasisRadius = 60f;
    public float stasisCooldown = 90f;
    public float stasisTime = 320f;
    public Effect stasisEffect = DustedFx.stasis;

    public StasisProjector(String name) {
        super(name);
        update = true;
        solid = true;
        group = BlockGroup.projectors;
    }

    @Override
    public void drawPlace(int x, int y, int rotation, boolean valid) {
        super.drawPlace(x, y, rotation, valid);
        Drawf.dashCircle(x * tilesize + offset, y * tilesize + offset, stasisRadius, Pal.lancerLaser);
    }

    public class StasisProjectorBuild extends Building implements Ranged {
        public float stasisCountdown;

        @Override
        public void drawSelect() {
            Drawf.dashCircle(x, y, stasisRadius, Pal.lancerLaser);
        }

        @Override
        public void updateTile() {
            super.updateTile();
            if (timer(stasisTimer, stasisCooldown + stasisTime)) {
                stasisCountdown = stasisTime * efficiency();
                if (efficiency() > 0) stasisEffect.at(x, y);
            }

            if (stasisCountdown > 0) {
                stasisCountdown -= Time.delta;
                Units.nearbyEnemies(team, x, y, stasisRadius, u -> u.speedMultiplier = 0);
            }
        }

        @Override
        public float range() {
            return stasisRadius;
        }
    }
}
