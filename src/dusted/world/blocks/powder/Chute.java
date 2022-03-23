package dusted.world.blocks.powder;

import arc.*;
import arc.func.*;
import arc.graphics.*;
import arc.graphics.g2d.*;
import arc.math.*;
import arc.math.geom.*;
import arc.struct.*;
import arc.util.*;
import dusted.content.*;
import dusted.type.*;
import dusted.world.interfaces.*;
import dusted.world.meta.values.*;
import mindustry.*;
import mindustry.entities.units.*;
import mindustry.gen.*;
import mindustry.graphics.*;
import mindustry.input.*;
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
        solid = false;
        conveyorPlacement = true;
    }

    @Override
    public void setStats() {
        super.setStats();
        new CustomStatValue("max-charge", maxCharge).add(stats);
    }

    @Override
    public void setBars() {
        super.setBars();
        bars.add("charge", build -> {
            Chargedc charged = (Chargedc) build;
            return new Bar(() -> Core.bundle.format("bar.charge"), () -> Pal.accent, () -> ((float) charged.charge(build)) / ((float) charged.maxCharge()));
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

    @Override
    public TextureRegion[] icons() {
        return new TextureRegion[]{Core.atlas.find("dusted-lands-chute-bottom"), topRegions[0], Core.atlas.find(name + "-power")};
    }

    @Override
    public Block getReplacement(BuildPlan req, Seq<BuildPlan> requests) {
        Boolf<Point2> jcont = p -> requests.contains(o -> o.x == req.x + p.x && o.y == req.y + p.y && o.rotation == req.rotation && (req.block instanceof Chute || req.block instanceof PowderJunction));

        if (jcont.get(Geometry.d4(req.rotation)) &&
                jcont.get(Geometry.d4(req.rotation - 2)) &&
                req.tile() != null &&
                req.tile().block() instanceof Chute &&
                Mathf.mod(req.build().rotation - req.rotation, 2) == 1) {
            return DustedBlocks.powderJunction;
        } /*else {
            TODO replace every chute that would have a charge of 0 with a chute drive
            return DustedBlocks.chuteDrive;
        }*/

        return this;
    }

    @Override
    public void handlePlacementLine(Seq<BuildPlan> plans) {
        Placement.calculateBridges(plans, (ItemBridge) DustedBlocks.bridgeChute);
    }

    public class ChuteBuild extends PowderBuild implements ChainedBuilding, Chargedc {
        public float smoothPowder;
        public int blendbits, xscl = 1, yscl = 1, blending;
        public int charge, properCharge;

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
            Draw.color(powders.current().color, smoothPowder);
            Draw.rect(sliced(bottomRegions[bits], slice), x, y, rotation);

            Draw.color();
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
            smoothPowder = Mathf.lerpDelta(smoothPowder, powders.currentAmount() / powderCapacity, 0.05f);

            properCharge = 0;
            proximity.each(build -> {
                if (build instanceof Chargedc entity && canCharge(build, this)) {
                    properCharge = Math.min(maxCharge, Math.max(properCharge, entity.charge(this) - 1));
                }
            });

            charge = properCharge;

            if (charge > 0 && powders.total() > 0.001f && timer(timerFlow, 1)) {
                movePowderForward(true, powders.current());
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

        @Override
        public int charge(Building accessor) {
            return charge;
        }

        @Override
        public int maxCharge() {
            return maxCharge;
        }

        @Override
        public void setCharge(int charge) {
            this.charge = charge;
        }
    }
}
