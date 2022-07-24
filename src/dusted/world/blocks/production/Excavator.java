package dusted.world.blocks.production;

import arc.graphics.g2d.*;
import arc.math.*;
import arc.util.*;
import dusted.world.blocks.environment.*;
import mindustry.*;
import mindustry.gen.*;
import mindustry.type.*;
import mindustry.world.*;

public class Excavator extends Block {
    public float range = 60f;
    public float mineTime = 40f;
    public int tier = 1;

    public float rotateSpeed = 5f;
    public float yOffset = -1;

    public Excavator(String name) {
        super(name);
        update = true;
        solid = true;
        configurable = true;
        hasItems = true;

        config(Integer.class, (entity, value) -> ((ExcavatorBuild) entity).mining = value);
    }

    @Override
    public void init() {
        super.init();

        if (yOffset < 0) {
            yOffset = size * 3;
        }
    }

    public boolean canMine(Tile target, Tile from) {
        Item drop = itemDrop(target);
        return drop != null && drop.hardness <= tier && target.dst(from) < range;
    }

    public @Nullable Item itemDrop(Tile tile) {
        return tile != null && tile.block() instanceof OreCluster cluster ? cluster.itemDrop : null;
    }

    public class ExcavatorBuild extends Building {
        public int mining = -1;
        public float progress;
        public float rotation;

        @Override
        public boolean onConfigureTapped(float x, float y) {
            Tile target = Vars.world.tileWorld(x, y);

            if (target.pos() == mining) {
                configure(-1);
                return true;
            }

            if (canMine(target, tile)) {
                configure(target.pos());
                return true;
            }

            return false;
        }

        @Override
        public void updateTile() {
            if (canMine(Vars.world.tile(mining), tile)) {
                progress += delta();
                rotation = Angles.moveToward(rotation, angleTo(Vars.world.tile(mining)), rotateSpeed * Time.delta);

                if (progress >= mineTime) {
                    offload(itemDrop(Vars.world.tile(mining)));
                    progress %= mineTime;
                }
            } else {
                progress = 0f;
            }
        }

        @Override
        public void draw() {
            super.draw();

            if (canMine(Vars.world.tile(mining), tile)) {
                Tile tile = Vars.world.tile(mining);
                float angle = Angles.angle(x, y, tile.worldx(), tile.worldy());
                float ox = Mathf.cosDeg(angle), oy = Mathf.sinDeg(angle);
                float l = tile.block().size * Vars.tilesize / 2f - 2f;

                Lines.stroke(5f);
                Lines.line(x + Angles.trnsx(rotation + 180f, yOffset), y + Angles.trnsy(rotation + 180f, yOffset), tile.worldx() - ox * l, tile.worldy() - oy * l);
                Lines.stroke(1f);
            }
        }
    }
}
