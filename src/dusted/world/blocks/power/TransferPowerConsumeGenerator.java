package dusted.world.blocks.power;

import arc.graphics.*;
import arc.math.*;
import dusted.world.interfaces.*;
import mindustry.*;
import mindustry.gen.*;
import mindustry.graphics.*;
import mindustry.world.blocks.power.*;
import mindustry.world.meta.*;

//absurd
public class TransferPowerConsumeGenerator extends ConsumeGenerator {
    public float radius = 60f * 8f;

    public TransferPowerConsumeGenerator(String name) {
        super(name);
    }

    @Override
    public void init() {
        clipSize = Math.max(clipSize, radius * 2f);
        super.init();
    }

    @Override
    public void setStats() {
        super.setStats();
        stats.add(Stat.powerRange, radius / Vars.tilesize, StatUnit.blocks);
    }

    @Override
    public void drawPlace(int x, int y, int rotation, boolean valid) {
        super.drawPlace(x, y, rotation, valid);

        Drawf.dashCircle(x * Vars.tilesize, y * Vars.tilesize, radius, Pal.accent);
    }

    public class TransferPowerConsumeGeneratorBuild extends ConsumeGeneratorBuild implements TransferPowerc {
        public int lastChange = -2;

        @Override
        public void updateTile() {
            super.updateTile();
            updateTransfer();
        }

        @Override
        public void drawLight() {
            Drawf.light(x, y, (40f + Mathf.absin(10f, 5f)) * Math.min(productionEfficiency, 2f) * size, Color.scarlet, 0.4f);
        }

        @Override
        public void draw() {
            super.draw();
            drawTransfer();
        }

        @Override
        public Building build() {
            return this;
        }

        @Override
        public float radius() {
            return radius;
        }

        @Override
        public int lastChange() {
            return lastChange;
        }

        @Override
        public void lastChange(int change) {
            lastChange = change;
        }
    }
}
