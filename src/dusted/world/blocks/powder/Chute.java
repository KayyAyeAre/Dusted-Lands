package dusted.world.blocks.powder;

import arc.*;
import arc.graphics.*;
import arc.graphics.g2d.*;
import arc.math.geom.*;
import arc.util.*;
import dusted.type.*;
import mindustry.*;
import mindustry.gen.*;
import mindustry.graphics.*;
import mindustry.ui.*;
import mindustry.world.*;
import mindustry.world.blocks.*;
import mindustry.world.blocks.distribution.*;

public class Chute extends PowderBlock implements Autotiler {
    public int maxCharge = 16;
    public final int timerFlow = timers++;
    public Color bottomColor = Color.valueOf("565656");
    public TextureRegion[] powerRegions = new TextureRegion[5], topRegions = new TextureRegion[5], bottomRegions = new TextureRegion[5];

    public Chute(String name) {
        super(name);
        rotate = true;
    }

    @Override
    public void setBars() {
        super.setBars();
        bars.add("charge", build -> {
            ChuteBuild entity = (ChuteBuild) build;
            return new Bar(() -> Core.bundle.format("bar.charge"), () -> Pal.accent, () -> ((float) entity.charge) / ((float) ((Chute) entity.block).maxCharge));
        });
    }

    @Override
    public void load() {
        super.load();

        for (int i = 0; i < 5; i++) {
            bottomRegions[i] = Core.atlas.find("dusted-lands-chute-bottom-" + i);
            powerRegions[i] = Core.atlas.find(name + "-power-" + i);
            topRegions[i] = Core.atlas.find(name + "-top-" + i);
        }
    }

    @Override
    public boolean blends(Tile tile, int rotation, int otherx, int othery, int otherrot, Block otherblock) {
        return otherblock instanceof PowderBlock && lookingAtEither(tile, rotation, otherx, othery, otherrot, otherblock);
    }

    public class ChuteBuild extends PowderBuild implements ChainedBuilding {
        public int blendbits, xscl = 1, yscl = 1, blending;
        public int charge;

        @Override
        public void draw() {
            float rotation = rotdeg();
            int r = this.rotation;

            Draw.z(Layer.blockUnder);
            for (int i = 0; i < 4; i++) {
                if ((blending & (1 << i)) != 0) {
                    int dir = r - i;
                    float rot = i == 0 ? rotation : (dir) * 90;
                    drawAt(x + Geometry.d4x(dir) * Vars.tilesize * 0.75f, y + Geometry.d4y(dir) * Vars.tilesize * 0.75f, 0, rot, i != 0 ? SliceMode.bottom : SliceMode.top);
                }
            }

            Draw.z(Layer.block);

            Draw.scl(xscl, yscl);
            drawAt(x, y, blendbits, rotation, SliceMode.none);
            Draw.reset();
        }

        protected void drawAt(float x, float y, int bits, float rotation, SliceMode slice) {
            Draw.color(bottomColor);
            Draw.rect(sliced(bottomRegions[bits], slice), x, y, rotation);
            Draw.mixcol(powders.current().color, powders.currentAmount() / powderCapacity);
            Draw.rect(sliced(bottomRegions[bits], slice), x, y, rotation);

            Draw.color();
            Draw.mixcol();
            Draw.rect(sliced(topRegions[bits], slice), x, y, rotation);

            Draw.color(Pal.darkerMetal);
            Draw.rect(sliced(powerRegions[bits], slice), x, y, rotation);
            Draw.color(Pal.accent, ((float) charge) / ((float) maxCharge));
            Draw.rect(sliced(powerRegions[bits], slice), x, y, rotation);
        }

        @Override
        public boolean acceptPowder(Building source, Powder powder) {
            return (powders.current() == powder || powders.currentAmount() < 0.2f) && (tile == null || (source.relativeTo(tile.x, tile.y) + 2) % 4 != rotation);
        }

        @Override
        public void onProximityUpdate() {
            super.onProximityUpdate();

            int[] bits = buildBlending(tile, rotation, null, true);
            blendbits = bits[0];
            xscl = bits[1];
            yscl = bits[2];
            blending = bits[4];
        }


        @Override
        public void updateTile() {
            if (tile.nearbyBuild(rotation) != null && tile.nearbyBuild(rotation) instanceof ChuteBuild chute) {
                chute.charge = Math.max(chute.charge, charge - 1);
            }

            if (charge > 0 && powders.total() > 0.001f && timer(timerFlow, 1)) {
                movePowderForward(powders.current());
            }
        }

        @Nullable
        @Override
        public Building next() {
            Tile next = tile.nearby(rotation);
            if (next != null && next.build instanceof PowderBuild) {
                return next.build;
            }
            return null;
        }
    }
}
