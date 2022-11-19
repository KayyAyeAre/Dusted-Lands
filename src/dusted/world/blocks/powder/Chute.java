package dusted.world.blocks.powder;

import arc.*;
import arc.func.*;
import arc.graphics.*;
import arc.graphics.g2d.*;
import arc.math.*;
import arc.math.geom.*;
import arc.struct.*;
import arc.util.*;
import arc.util.pooling.*;
import arc.util.pooling.Pool.*;
import dusted.content.*;
import dusted.type.*;
import dusted.world.interfaces.*;
import mindustry.*;
import mindustry.entities.units.*;
import mindustry.gen.*;
import mindustry.graphics.*;
import mindustry.input.*;
import mindustry.world.*;
import mindustry.world.blocks.*;
import mindustry.world.blocks.distribution.*;

public class Chute extends PowderBlock implements Autotiler {
    public final int timerFlow = timers++;
    public boolean leaks = true;
    public Color bottomColor = Color.valueOf("3d3839");
    public TextureRegion[] topRegions = new TextureRegion[5], bottomRegions = new TextureRegion[5];

    public Chute(String name) {
        super(name);
        rotate = true;
        solid = false;
        conveyorPlacement = true;
    }

    @Override
    public void load() {
        super.load();

        for (int i = 0; i < 5; i++) {
            bottomRegions[i] = Core.atlas.find("dusted-lands-chute-bottom-" + i);
            topRegions[i] = Core.atlas.find(name + "-top-" + i);
        }
    }

    @Override
    public boolean blends(Tile tile, int rotation, int otherx, int othery, int otherrot, Block otherblock) {
        return Vars.world.build(otherx, othery) instanceof PowderBuildc pow && (pow.outputsPowder() || (lookingAt(tile, rotation, otherx, othery, otherblock))) && lookingAtEither(tile, rotation, otherx, othery, otherrot, otherblock);
    }

    @Override
    public TextureRegion[] icons() {
        return new TextureRegion[]{Core.atlas.find("dusted-lands-chute-bottom"), topRegions[0]};
    }

    @Override
    public void drawPlanRegion(BuildPlan plan, Eachable<BuildPlan> list) {
        int[] bits = getTiling(plan, list);
        if (bits != null) {
            Draw.scl(bits[1], bits[2]);
            Draw.color(bottomColor);
            Draw.alpha(0.5f);
            Draw.rect(bottomRegions[bits[0]], plan.drawx(), plan.drawy(), plan.rotation * 90f);
            Draw.color();
            Draw.rect(topRegions[bits[0]], plan.drawx(), plan.drawy(), plan.rotation * 90f);
            Draw.scl();
        }
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
        }

        return this;
    }

    @Override
    public void handlePlacementLine(Seq<BuildPlan> plans) {
        Placement.calculateBridges(plans, (ItemBridge) DustedBlocks.bridgeChute);
    }

    public static class ClumpData implements Poolable {
        public float size, progress, offset, srcRot, randColor;
        public float moveSpeed = 0.2f;

        public ClumpData set(float size, float progress, float offset, float srcRot, float randColor) {
            this.size = size;
            this.progress = progress;
            this.offset = offset;
            this.srcRot = srcRot;
            this.randColor = randColor;

            return this;
        }

        @Override
        public void reset() {
            size = 0f;
            progress = 0f;
            offset = 0f;
            srcRot = 0f;
            randColor = 0f;
            moveSpeed = 0.2f;
        }
    }

    public class ChuteBuild extends PowderBuild implements ChainedBuilding {
        public float smoothPowder;
        public int blendbits, xscl = 1, yscl = 1, blending;
        public Seq<ClumpData> clumps = new Seq<>();
        public boolean moveClump;

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
            float z = Draw.z();
            Draw.z(z- 0.002f);

            Draw.color(bottomColor);
            Draw.rect(sliced(bottomRegions[bits], slice), x, y, rotation);
            Draw.color(powders.current().color, smoothPowder);
            Draw.rect(sliced(bottomRegions[bits], slice), x, y, rotation);
            Draw.z(z - 0.001f);

            clumps.each(c -> {
                Draw.color(Tmp.c1.set(powders.current().clumpColor).mul(c.randColor), smoothPowder);
                float offsetX = Mathf.lerp(
                        Angles.trnsx(c.srcRot + 180f, hitSize() / 2f) + Angles.trnsx(c.srcRot, c.progress * hitSize(), c.offset),
                        Angles.trnsx(rotdeg() + 180f, hitSize() / 2f) + Angles.trnsx(rotdeg(), c.progress * hitSize(), c.offset),
                        c.progress
                );
                float offsetY = Mathf.lerp(
                        Angles.trnsy(c.srcRot + 180f, hitSize() / 2f) + Angles.trnsy(c.srcRot, c.progress * hitSize(), c.offset),
                        Angles.trnsy(rotdeg() + 180f, hitSize() / 2f) + Angles.trnsy(rotdeg(), c.progress * hitSize(), c.offset),
                        c.progress
                );

                Fill.circle(x + offsetX, y + offsetY, c.size);
            });
            Draw.z(z);

            Draw.color();
            Draw.rect(sliced(topRegions[bits], slice), x, y, rotation);
        }

        @Override
        public boolean acceptPowder(Building source, Powder powder) {
            return (powders.current() == powder || powders.currentAmount() < 0.2f) && (tile == null || (source.relativeTo(tile.x, tile.y) + 2) % 4 != rotation);
        }

        @Override
        public void handlePowder(Building source, Powder powder, float amount) {
            super.handlePowder(source, powder, amount);

            if (moveClump && !(source instanceof ChuteBuild) && Mathf.chanceDelta((powders.currentAmount() / powderCapacity) * 0.3f)) {
                clumps.add(Pools.obtain(ClumpData.class, ClumpData::new).set(Mathf.random(0.5f, 1.5f), 0f, Mathf.random(-2f, 2f), source.angleTo(this), Mathf.random(1f, 1.1f)));
            }
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

            clumps.each(c -> {
                c.moveSpeed = Mathf.lerpDelta(c.moveSpeed, moveClump ? 0.2f : 0f, 0.2f);
                c.progress += Time.delta * c.moveSpeed;
                if (c.progress > 1f) {
                    c.progress %= 1f;
                    clumps.remove(c);

                    Building next = front();
                    if (front() instanceof PowderBuildc pbuild) next = pbuild.getPowderDestination(this, powders.current());

                    if (next instanceof ChuteBuild chute) {
                        c.srcRot = rotdeg();
                        chute.clumps.add(c);
                    } else {
                        Pools.free(c);
                    }
                }
            });

            if (powders.total() > 0.001f && timer(timerFlow, 1)) {
                float flow = movePowderForward(leaks, powders.current());
                moveClump = flow > 0f && (!(front() instanceof ChuteBuild chute) || chute.clumps.size < 5);
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
