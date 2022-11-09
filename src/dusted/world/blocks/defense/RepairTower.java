package dusted.world.blocks.defense;

import arc.graphics.*;
import arc.graphics.g2d.*;
import arc.math.*;
import arc.math.geom.*;
import arc.util.*;
import dusted.content.*;
import dusted.graphics.*;
import mindustry.*;
import mindustry.entities.*;
import mindustry.entities.units.*;
import mindustry.gen.*;
import mindustry.graphics.*;
import mindustry.logic.*;
import mindustry.world.*;
import mindustry.world.draw.*;
import mindustry.world.meta.*;

import static mindustry.Vars.indexer;

public class RepairTower extends Block {
    public float range = 15f * 8f;
    public float healPercent = 20f / 60f;
    public Color baseColor = DustedPal.pinkHeal;
    public Effect repairEffect = DustedFx.regeneration;
    public float repairEffectChance = 0.05f;
    public float beamStroke = 4f, beamRad = 3f;
    public float scl = 18f;
    public DrawBlock drawer = new DrawDefault();

    public RepairTower(String name) {
        super(name);
        solid = true;
        update = true;
        group = BlockGroup.projectors;
        hasPower = true;
        emitLight = true;
        lightRadius = 50f;
        suppressable = true;
    }

    @Override
    public void setStats() {
        super.setStats();

        stats.add(Stat.repairTime, (int) (1f / (healPercent / 100f) / 60f), StatUnit.seconds);
        stats.add(Stat.range, range / Vars.tilesize, StatUnit.blocks);
    }

    @Override
    public void drawPlace(int x, int y, int rotation, boolean valid) {
        super.drawPlace(x, y, rotation, valid);

        Drawf.dashCircle(x * Vars.tilesize + offset, y * Vars.tilesize + offset, range, baseColor);

        Vars.indexer.eachBlock(Vars.player.team(), x * Vars.tilesize + offset, y * Vars.tilesize + offset, range, other -> true, other -> Drawf.selected(other, Tmp.c1.set(baseColor).a(Mathf.absin(4f, 1f))));
    }

    @Override
    public void drawPlanRegion(BuildPlan plan, Eachable<BuildPlan> list) {
        drawer.drawPlan(this, plan, list);
    }

    @Override
    public TextureRegion[] icons() {
        return drawer.finalIcons(this);
    }

    @Override
    public void load() {
        super.load();
        drawer.load(this);
    }

    public class RepairTowerBuild extends Building implements Ranged {
        public boolean healing;
        public float warmup;
        public Vec2 targetPos = new Vec2();
        public @Nullable Building target;

        @Override
        public float range() {
            return range;
        }

        @Override
        public float warmup() {
            return warmup;
        }

        @Override
        public void created() {
            targetPos.set(this);
        }

        @Override
        public void updateTile() {
            healing = false;
            target = null;

            if (efficiency > 0f) {
                Vars.indexer.eachBlock(this, range, b -> b.damaged() && !b.isHealSuppressed(), b -> {
                    if (!healing) {
                        healing = true;
                        targetPos.lerpDelta(b, 0.15f);
                        target = b;
                        b.heal((b.maxHealth() * healPercent / 100f) * efficiency);
                        b.recentlyHealed();

                        if (Mathf.chanceDelta(repairEffectChance)) repairEffect.at(b.x + Mathf.range(b.hitSize() / 2f - 1f), b.y + Mathf.range(b.hitSize() / 2f - 1f));
                    }
                });
            }

            if (!healing) targetPos.lerpDelta(this, 0.15f);
            warmup = Mathf.lerpDelta(warmup, healing ? 1f : 0f, 0.05f);
        }

        @Override
        public void draw() {
            drawer.draw(this);
            Draw.reset();

            float tx = targetPos.x + Mathf.sin(Time.time, scl, target == null ? 0f : target.hitSize() / 4f - 1f), ty = targetPos.y + Mathf.cos(Time.time, scl, target == null ? 0f : target.hitSize() / 4f - 1f);

            Draw.z(Layer.effect);
            Draw.color(baseColor);
            Lines.stroke(warmup * beamStroke);
            Fill.circle(x, y, warmup * beamRad);
            Lines.line(x, y, tx, ty);
            Fill.circle(tx, ty, warmup * beamRad);
            Draw.reset();
        }

        @Override
        public void drawSelect() {
            indexer.eachBlock(this, range, other -> true, other -> Drawf.selected(other, Tmp.c1.set(baseColor).a(Mathf.absin(4f, 1f))));
            Drawf.dashCircle(x, y, range, baseColor);
        }

        @Override
        public void drawLight() {
            super.drawLight();
            drawer.drawLight(this);
        }
    }
}
