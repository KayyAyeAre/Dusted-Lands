package dusted.world.blocks.storage;

import arc.*;
import arc.graphics.g2d.*;
import arc.util.*;
import dusted.*;
import dusted.game.Decay.*;
import mindustry.*;
import mindustry.graphics.*;
import mindustry.world.blocks.storage.*;

public class ShieldedCoreBlock extends CoreBlock {
    public float radius = 120f;

    public ShieldedCoreBlock(String name) {
        super(name);
    }

    public class ShieldedCoreBuild extends CoreBuild {
        public DecayShield shield;

        @Override
        public void created() {
            super.created();

            Core.app.post(() -> {
                shield = new DecayShield(() -> Tmp.v1.set(this), () -> team, () -> radius);
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

            if (thrusterTime <= 0) {
                Draw.z(Layer.shields + 2.5f);
                Draw.color(team.color);

                if (Vars.renderer.animateShields) {
                    Fill.poly(x, y, 6, radius);
                } else {
                    Lines.stroke(1.5f);
                    Draw.alpha(0.09f);
                    Fill.poly(x, y, 6, radius);
                    Draw.alpha(1f);
                    Lines.poly(x, y, 6, radius);
                }
            }
        }
    }
}
