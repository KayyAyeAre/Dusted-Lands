package dusted.entities.bullet;

import arc.*;
import arc.graphics.*;
import arc.graphics.g2d.*;
import arc.math.*;
import arc.struct.*;
import arc.util.*;
import dusted.graphics.*;
import mindustry.content.*;
import mindustry.entities.*;
import mindustry.entities.bullet.*;
import mindustry.game.EventType.*;
import mindustry.game.*;
import mindustry.gen.*;
import mindustry.world.blocks.defense.Wall.*;

import static mindustry.Vars.*;

public class SplittingLaserBulletType extends BulletType {
    public float length;
    public float splitSpread = 80f;
    public Color fromColor = DustedPal.cavnenYellow, toColor = DustedPal.cavnenYellowBack;

    public SplittingLaserBulletType(float damage) {
        this.damage = damage;
        speed = 0f;

        despawnEffect = Fx.none;
        smokeEffect = Fx.none;
        impact = true;
        keepVelocity = false;
        collides = false;
        pierce = true;
        hittable = false;
        absorbable = false;
    }

    @Override
    public Bullet create(Entityc owner, Team team, float x, float y, float angle, float damage, float velocityScl, float lifetimeScl, Object data) {
        return super.create(owner, team, x, y, angle, damage, velocityScl, lifetimeScl, new IntSeq());
    }

    @Override
    public void hitEntity(Bullet b, Hitboxc entity, float health) {
        if (b.data instanceof IntSeq iseq && entity instanceof Healthc healthc && !iseq.contains(entity.id())) {
            healthc.damage(b.damage);
            iseq.add(entity.id());
        }

        if (entity instanceof Unit unit) {
            Tmp.v3.set(unit).sub(b).nor().scl(knockback * 80f);
            if (impact) Tmp.v3.setAngle(b.rotation() + (knockback < 0 ? 180f : 0f));
            unit.impulse(Tmp.v3);
            unit.apply(status, statusDuration);
        }

        if (b.owner instanceof WallBuild && player != null && b.team == player.team() && entity instanceof Unit unit && unit.dead) {
            Events.fire(Trigger.phaseDeflectHit);
        }
    }

    @Override
    public void update(Bullet b) {
        super.update(b);
        for (int i : Mathf.signs)
            Damage.collideLine(b, b.team, b.type.hitEffect, b.x, b.y, b.rotation() + (b.finpow() * splitSpread * i), Damage.findLaserLength(b, length), false);
    }

    @Override
    public void draw(Bullet b) {
        for (int i : Mathf.signs) {
            Draw.color(fromColor, toColor, b.fin());
            Fill.tri(
                    b.x,
                    b.y,
                    b.x + Angles.trnsx(b.rotation(), b.foutpow() * 12f),
                    b.y + Angles.trnsy(b.rotation(), b.foutpow() * 12f),
                    b.x + Angles.trnsx(b.rotation() + ((b.finpow() * 80f) * i), b.fout() * length),
                    b.y + Angles.trnsy(b.rotation() + ((b.finpow() * 80f) * i), b.fout() * length)
            );
        }
    }
}
