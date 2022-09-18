package dusted.world.blocks.power;

import arc.graphics.*;
import arc.graphics.g2d.*;
import arc.math.*;
import arc.util.*;
import mindustry.*;
import mindustry.graphics.*;
import mindustry.world.blocks.power.*;
import mindustry.world.meta.*;

//a power generator that can transfer power to nearby blocks
public class TransferPowerGenerator extends PowerGenerator {
    public float radius = 60f * 8f;
    public Color transferColor = Pal.powerLight.cpy().a(0.5f);

    public TransferPowerGenerator(String name) {
        super(name);
    }

    @Override
    public void init() {
        super.init();
        clipSize = Math.max(clipSize, radius * 2f);
    }

    @Override
    public void setStats() {
        super.setStats();

        stats.add(Stat.powerRange, radius / Vars.tilesize, StatUnit.blocks);
    }

    public class TransferPowerGeneratorBuild extends GeneratorBuild {
        public int lastChange = -2;

        @Override
        public void updateTile() {
            if (lastChange != Vars.world.tileChanges) {
                lastChange = Vars.world.tileChanges;

                Vars.indexer.eachBlock(this, radius, b -> b.block.hasPower && !(b.block instanceof PowerNode), b -> {
                    power.links.addUnique(b.pos());
                    b.power.links.addUnique(pos());

                    power.graph.addGraph(b.power.graph);
                });
            }
        }

        @Override
        public void drawSelect(){
            Drawf.dashCircle(x, y, radius, Pal.accent);
        }

        @Override
        public void draw() {
            super.draw();

            float progress = Interp.pow2Out.apply((Time.time / 180f) % 1f);

            Draw.blend(Blending.additive);
            Draw.z(Layer.blockOver);
            Lines.stroke(10f * (1f - progress));
            Draw.color(transferColor);

            Lines.circle(x, y, progress * radius);

            Draw.blend();
            Draw.z(Layer.block);
            Draw.reset();
        }
    }
}
