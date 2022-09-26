package dusted.world.blocks.storage;

import arc.*;
import arc.graphics.*;
import arc.graphics.g2d.*;
import arc.math.*;
import arc.util.*;
import dusted.*;
import dusted.game.Decay.*;
import mindustry.*;
import mindustry.game.*;
import mindustry.graphics.*;
import mindustry.graphics.MultiPacker.*;
import mindustry.world.blocks.storage.*;

public class ShieldedCoreBlock extends CoreBlock {
    public float radius = 120f;

    public ShieldedCoreBlock(String name) {
        super(name);
    }

    @Override
    public void init() {
        super.init();

        clipSize = Math.max(clipSize, radius * 2f);
    }

    //TODO remove
    @Override
    public void createIcons(MultiPacker packer) {
        super.createIcons(packer);

        PixmapRegion base = Core.atlas.getPixmap(region);
        Pixmap out = base.crop();

        out.draw(Core.atlas.getPixmap(teamRegions[Team.sharded.id]), true);

        packer.add(PageType.main, name + "-full", new PixmapRegion(out));
    }

    public class ShieldedCoreBuild extends CoreBuild {
        public DecayShield shield;

        @Override
        public void created() {
            super.created();

            Core.app.post(() -> {
                shield = new DecayShield(this, radius);
                DustedLands.decay.shields.add(shield);
            });
        }

        @Override
        public void onRemoved() {
            super.onRemoved();
            DustedLands.decay.shields.remove(shield);
        }

        @Override
        public void draw() {
            super.draw();

            if (!Vars.renderer.isCutscene()) {
                Draw.z(Layer.shields + 2.5f);
                Draw.color(team.color);

                float fout = Interp.pow2.apply(Math.min(1f - thrusterTime, 1f));

                if (Vars.renderer.animateShields) {
                    Fill.poly(x, y, 24, radius * fout, Time.time / 10f);
                } else {
                    Lines.stroke(1.5f);
                    Draw.alpha(0.09f);
                    Fill.poly(x, y, 24, radius * fout, Time.time / 10f);
                    Draw.alpha(1f);
                    Lines.poly(x, y, 24, radius * fout, Time.time / 10f);
                }
            }
        }
    }
}
