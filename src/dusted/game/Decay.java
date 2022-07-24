package dusted.game;

import arc.*;
import arc.func.*;
import arc.math.geom.*;
import arc.struct.*;
import arc.util.*;
import dusted.content.*;
import dusted.world.meta.*;
import mindustry.*;
import mindustry.game.*;
import mindustry.game.EventType.*;
import mindustry.gen.*;
import mindustry.type.*;
import mindustry.world.*;

public class Decay implements ApplicationListener {
    public Seq<DecayShield> shields = new Seq<>();
    private float decayDamage;

    public Decay() {
        Events.run(WorldLoadEvent.class, () -> {
            shields.clear();
            decayDamage = Vars.state.rules.tags.getFloat("decay-damage", 0.5f);
        });
    }

    public void debug() {
        shields.each(s -> {
            Log.info("distance: @, radius: @", Vars.player.unit().dst(s.pos.get()), s.radius.get());
        });
    }

    @Override
    public void update() {
        if (Vars.state.isPlaying() && Vars.state.rules.hasEnv(DustedEnv.decay)) {
            Groups.unit.each(u -> {
                if (!shields.contains(s -> u.team == s.team.get() && u.dst(s.pos.get()) < s.radius.get())) {
                    u.damagePierce(decayDamage, false);
                }
            });

            Vars.indexer.allBuildings(Vars.world.width() * 4, Vars.world.height() * 4, Math.max(Vars.world.width() * 4, Vars.world.height() * 4), b -> {
                //TODO broken?
                if (!shields.contains(s -> b.team == s.team.get() && b.dst(s.pos.get()) < s.radius.get() + (b.hitSize() / 2f)) && b.health > b.maxHealth * calculateDamage(b.block)) {
                    b.damagePierce(Math.min(decayDamage, (1f - calculateDamage(b.block)) * b.maxHealth + b.maxHealth - b.health), false);
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

    public static class DecayShield {
        public Floatp radius;
        public Prov<Vec2> pos;
        public Prov<Team> team;

        public DecayShield(Prov<Vec2> pos, Prov<Team> team, Floatp radius) {
            this.pos = pos;
            this.team = team;
            this.radius = radius;
        }
    }
}
