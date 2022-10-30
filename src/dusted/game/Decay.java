package dusted.game;

import arc.*;
import arc.func.*;
import arc.graphics.g2d.*;
import arc.math.*;
import arc.math.geom.*;
import arc.struct.*;
import arc.util.*;
import dusted.content.*;
import dusted.world.blocks.storage.ShieldedCoreBlock.*;
import dusted.world.meta.*;
import mindustry.*;
import mindustry.entities.*;
import mindustry.game.*;
import mindustry.game.EventType.*;
import mindustry.gen.*;
import mindustry.graphics.*;
import mindustry.type.*;
import mindustry.world.*;

public class Decay implements ApplicationListener {
    public Seq<DecayShield> shields = new Seq<>();
    public Decay() {
        Events.run(WorldLoadEvent.class, shields::clear);
    }

    @Override
    public void update() {
        float decayDamage = Vars.state.rules.attributes.get(DustedAttribute.decay);

        if (Vars.state.isPlaying() && !Vars.state.isEditor()) {
            Groups.unit.each(u -> {
                //prevent wave units from dying before their shield unit can come shield them
                if (u.team == Vars.state.rules.waveTeam && Vars.spawner.getSpawns().contains(t -> u.within(t.worldx(), t.worldy(), Vars.state.rules.dropZoneRadius))) return;

                float dmg = (decayDamage + (u.isGrounded() && !u.hovering ? u.tileOn().floor().attributes.get(DustedAttribute.decay) : 0f)) * Time.delta;

                if (!isShielded(u) && dmg > 0) {
                    u.damagePierce(dmg, false);
                }
            });

            Vars.indexer.allBuildings(Vars.world.width() * 4, Vars.world.height() * 4, Math.max(Vars.world.width() * 4, Vars.world.height() * 4), b -> {
                //sure the cores already have shields but they still get decayed while launching for some reason
                if (!(b instanceof ShieldedCoreBuild) && !isShielded(b) && b.health > b.maxHealth * calculateDamage(b.block)) {
                    float dmg = (decayDamage + b.block.sumAttribute(DustedAttribute.decay, b.tileX(), b.tileY())) * Time.delta;
                    if (dmg <= 0) return;

                    b.damagePierce(Math.min(dmg, (1f - calculateDamage(b.block)) * b.maxHealth + b.maxHealth - b.health), false);

                    if (Mathf.chanceDelta(0.01f)) DustedFx.deteriorating.at(b.x + Mathf.range(b.hitSize() / 2f), b.y + Mathf.range(b.hitSize() / 2f));
                }
            });
        }
    }

    public float calculateDamage(Block block) {
        float total = 0f, percentage = 0f;

        for (ItemStack stack : block.requirements) {
            total += stack.amount;
            if (stack.item == DustedItems.zircon) percentage += stack.amount;
        }

        return percentage / total;
    }

    public <T extends Teamc & Sized> boolean isShielded(T entity) {
        return shields.contains(s -> entity.team() == s.team() && entity.within(s, s.radius + entity.hitSize() / 2f));
    }

    public static class DecayShield implements Position {
        public float radius;
        public Teamc owner;

        public DecayShield(Teamc owner) {
            this.owner = owner;
        }

        public DecayShield(Teamc owner, float radius) {
            this.owner = owner;
            this.radius = radius;
        }

        public Team team() {
            return owner.team();
        }

        @Override
        public float getX() {
            return owner.getX();
        }

        @Override
        public float getY() {
            return owner.getY();
        }

        public void draw() {
            Draw.z(Layer.shields + 2.5f);
            Draw.color(team().color);

            if (Vars.renderer.animateShields) {
                Fill.poly(getX(), getY(), 24, radius, Time.time / 10f);
            } else {
                Lines.stroke(1.5f);
                Draw.alpha(0.09f);
                Fill.poly(getX(), getY(), 24, radius, Time.time / 10f);
                Draw.alpha(1f);
                Lines.poly(getX(), getY(), 24, radius, Time.time / 10f);
            }
        }
    }
}
