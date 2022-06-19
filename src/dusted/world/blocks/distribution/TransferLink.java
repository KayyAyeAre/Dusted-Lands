package dusted.world.blocks.distribution;

import arc.*;
import arc.func.*;
import arc.graphics.g2d.*;
import arc.math.*;
import arc.math.geom.*;
import arc.struct.*;
import arc.util.*;
import arc.util.io.*;
import mindustry.*;
import mindustry.core.*;
import mindustry.entities.*;
import mindustry.entities.units.*;
import mindustry.game.*;
import mindustry.gen.*;
import mindustry.graphics.*;
import mindustry.input.*;
import mindustry.type.*;
import mindustry.world.*;
import mindustry.world.meta.*;

public class TransferLink extends Block {
    public float transferTime = 2f;
    public float linkRange = 60f;
    public int maxLinks = 5;
    public float arrowSpacing = 10f, arrowSpeed = 0.5f;
    public TextureRegion laserRegion, laserEndRegion, arrowRegion;

    public TransferLink(String name) {
        super(name);
        configurable = true;
        allowConfigInventory = false;
        solid = true;
        swapDiagonalPlacement = true;

        update = true;
        group = BlockGroup.transportation;
        hasItems = true;
        priority = TargetPriority.transport;
        unloadable = false;

        config(LinkData.class, (entity, value) -> {
            TransferLinkBuild build = (TransferLinkBuild) entity;
            build.links.add(value.pos);
            build.pending.add(value.pos);
        });

        config(Integer.class, (entity, value) -> {
            TransferLinkBuild build = (TransferLinkBuild) entity;
            Building other = Vars.world.build(value);

            if (build.links.contains(value)) {
                build.links.removeValue(value);
            } else if (linkValid(build, other)) {
                build.links.add(value);
            }
        });

        config(Point2[].class, (entity, value) -> {
            TransferLinkBuild build = (TransferLinkBuild) entity;
            IntSeq old = new IntSeq(build.links);

            //copied straight from powernode lmao
            for (int i = 0; i < old.size; i++) {
                int cur = old.get(i);
                configurations.get(Integer.class).get(build, cur);
            }

            for (Point2 p : value) {
                int newPos = Point2.pack(p.x + build.tileX(), p.y + build.tileY());
                configurations.get(Integer.class).get(build, newPos);
            }
        });
    }

    @Override
    public void init() {
        super.init();

        clipSize = Math.max(clipSize, linkRange);
    }

    @Override
    public void changePlacementPath(Seq<Point2> points, int rotation) {
        Placement.calculateNodes(points, this, rotation, (point, other) -> overlaps(Vars.world.tile(point.x, point.y), Vars.world.tile(other.x, other.y)));
    }

    @Override
    public void handlePlacementLine(Seq<BuildPlan> plans) {
        for (int i = 0; i < plans.size - 1; i++) {
            BuildPlan cur = plans.get(i);
            BuildPlan next = plans.get(i + 1);
            if (overlaps(cur.tile(), next.tile())) {
                cur.config = new LinkData(Point2.pack(next.x, next.y));
            }
        }
    }

    @Override
    public void load() {
        super.load();
        arrowRegion = Core.atlas.find(name + "-arrow");
        laserRegion = Core.atlas.find(name + "-laser");
        laserEndRegion = Core.atlas.find(name + "-laser-end");
    }

    @Override
    public void setStats() {
        super.setStats();

        stats.add(Stat.range, linkRange / Vars.tilesize, StatUnit.blocks);
    }

    public void getPotentialLinks(Tile tile, Team team, Cons<TransferLinkBuild> cons) {
        Geometry.circle(tile.x, tile.y, (int) (linkRange / Vars.tilesize) + 2, (x, y) -> {
            Building other = Vars.world.build(x, y);
            if (other instanceof TransferLinkBuild link && other.tile != tile && other.team == team && overlaps(tile, other.tile))
                cons.get(link);
        });
    }

    public boolean overlaps(Tile tile, Tile other) {
        if (tile == null || other == null) return true;
        return Intersector.overlaps(Tmp.cr1.set(tile.worldx(), tile.worldy(), linkRange), other.getHitbox(Tmp.r1));
    }

    public boolean linkValid(Building entity, Building other) {
        return linkValid(entity, other, true);
    }

    public boolean linkValid(Building entity, Building other, boolean checkMaxLinks) {
        return (((TransferLinkBuild) entity).links.size < maxLinks || !checkMaxLinks) && other instanceof TransferLinkBuild && other.team == entity.team && overlaps(entity.tile, other.tile);
    }

    public void drawLink(float x1, float y1, float x2, float y2, float size1, float size2, float progress) {
        float angle = Angles.angle(x1, y1, x2, y2);
        float ox = Mathf.cosDeg(angle), oy = Mathf.sinDeg(angle);
        float l1 = size1 * Vars.tilesize / 2f - 1.5f, l2 = size2 * Vars.tilesize / 2f - 1.5f;
        float scl = 8f * 0.5f * Draw.scl;

        float dx1 = x1 + ox * l1, dy1 = y1 + oy * l1, dx2 = x2 - ox * l2, dy2 = y2 - oy * l2;

        Draw.alpha(Renderer.laserOpacity);
        Draw.z(Layer.power);
        Draw.rect(laserEndRegion, dx1, dy1);
        Draw.rect(laserEndRegion, dx2, dy2);
        Lines.stroke(laserRegion.height * Draw.scl);
        Lines.line(laserRegion, dx1 + ox * scl, dy1 + oy * scl, dx2 - ox * scl, dy2 - oy * scl, false);

        float dst = Mathf.dst(dx1, dy1, dx2, dy2);
        float arot = Angles.angle(x1, y1, x2, y2);
        int arrows = (int) (dst / arrowSpacing);

        for (int a = 0; a < arrows; a++) {
            Draw.rect(arrowRegion,
                    dx1 + Angles.trnsx(arot, Vars.tilesize / 2f + a * arrowSpacing + progress),
                    dy1 + Angles.trnsy(arot, Vars.tilesize / 2f + a * arrowSpacing + progress),
                    arot);
        }
    }

    public class LinkData {
        public int pos;

        public LinkData(int pos) {
            this.pos = pos;
        }
    }

    public class TransferLinkBuild extends Building {
        public IntSeq links = new IntSeq();
        public IntSeq pending = new IntSeq();
        public int cur;
        public float transferCounter;
        public float time;

        @Override
        public void pickedUp() {
            links.clear();
        }

        @Override
        public void updateTile() {
            time = (time + (arrowSpeed * delta())) % arrowSpacing;

            validate();

            if (!links.isEmpty()) {
                updateTransfer();
            } else if (timer(timerDump, dumpTime)) {
                dump();
            }
        }

        public void updateTransfer() {
            Building other = Vars.world.build(links.get(cur));

            transferCounter += edelta();
            while (transferCounter >= transferTime) {
                Item item = items.take();
                if (item != null && other.acceptItem(this, item)) {
                    other.handleItem(this, item);
                } else if (item != null) {
                    items.add(item, 1);
                    items.undoFlow(item);
                }

                incrementCurrent();
                transferCounter -= transferTime;
            }
        }

        public void validate() {
            if (links.size > 0) {
                cur = cur % links.size;
            } else {
                cur = 0;
            }

            int iterations = 0;

            while (!links.isEmpty() && !linkValid(this, Vars.world.build(links.get(cur)), false) && iterations <= links.size) {
                iterations++;
                if (!pending.contains(links.get(cur))) {
                    links.removeValue(links.get(cur));
                }
                incrementCurrent();
            }
            pending.each(i -> {
                if (linkValid(this, Vars.world.build(i), false)) pending.removeValue(i);
            });
        }

        public void incrementCurrent() {
            if (links.size > 0) {
                cur = (cur + 1) % links.size;
            } else {
                cur = 0;
            }
        }

        @Override
        public boolean acceptItem(Building source, Item item) {
            return hasItems && team == source.team && items.total() < itemCapacity;
        }

        @Override
        public boolean onConfigureBuildTapped(Building other) {
            if (this == other) {
                if (links.size == 0) {
                    getPotentialLinks(tile, team, b -> {
                        if (!b.links.contains(pos())) configure(b.pos());
                    });
                } else {
                    while (links.size > 0) {
                        configure(links.get(0));
                    }
                }

                deselect();
                return false;
            }

            if (other instanceof TransferLinkBuild t && t.links.contains(pos())) {
                configure(other.pos());
                other.configure(pos());
                return true;
            }

            if (linkValid(this, other)) {
                configure(other.pos());
                return false;
            }

            return super.onConfigureBuildTapped(other);
        }

        @Override
        public void draw() {
            super.draw();

            links.each(i -> {
                Point2 pos = Point2.unpack(i);
                if (linkValid(this, Vars.world.build(i), false)) drawLink(x, y, pos.x * Vars.tilesize, pos.y * Vars.tilesize, block.size, Vars.world.build(i).block.size, time);
            });

            Draw.reset();
        }

        @Override
        public void drawSelect() {
            super.drawSelect();
            Drawf.circles(x, y, linkRange);
        }

        @Override
        public void drawConfigure() {
            Drawf.circles(x, y, tile.block().size * Vars.tilesize / 2f + 1f + Mathf.absin(Time.time, 4f, 1f));

            Drawf.circles(x, y, linkRange);
            links.each(i -> {
                Block linkedBlock = Vars.world.build(i).block;
                Point2 p = Point2.unpack(i);
                Drawf.square(p.x * Vars.tilesize, p.y * Vars.tilesize, linkedBlock.size * Vars.tilesize / 2f + 1f, Pal.place);
            });
        }

        @Override
        public Point2[] config() {
            Point2[] out = new Point2[links.size];
            for (int i = 0; i < out.length; i++) {
                out[i] = Point2.unpack(links.get(i)).sub(tile.x, tile.y);
            }
            return out;
        }

        @Override
        public void write(Writes write) {
            super.write(write);
            write.i(links.size);

            for (int i = 0; i < links.size; i++) {
                write.i(links.get(i));
            }
        }

        @Override
        public void read(Reads read, byte revision) {
            super.read(read, revision);
            links.clear();
            int size = read.i();

            for (int i = 0; i < size; i++) {
                links.add(read.i());
            }
        }
    }
}
