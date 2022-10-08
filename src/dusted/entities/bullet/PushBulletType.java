package dusted.entities.bullet;

import arc.math.*;
import arc.math.geom.*;
import arc.util.*;
import mindustry.content.*;
import mindustry.entities.*;
import mindustry.entities.bullet.*;
import mindustry.gen.*;

public class PushBulletType extends BasicBulletType {
    public float force = 5f;
    public float pushRadius = 12f;
    public Effect pushEffect = Fx.none;
    public float pushEffectChance = 0.5f;

    public PushBulletType(float speed, float damage) {
        super(speed, damage);
    }

    {
        collides = false;
    }

    @Override
    public void update(Bullet b) {
        super.update(b);

        float[] diff = {1f};

        Units.nearbyEnemies(b.team, b.x, b.y, pushRadius, u -> {
            if (u.checkTarget(collidesAir, collidesGround)) {
                Tmp.v3.set(u).sub(b).nor().scl(force * 80f).setAngle(b.rotation());
                u.impulse(Tmp.v3);

                diff[0] = Math.min(diff[0], (b.dst(u) - u.hitSize) / (pushRadius + u.hitSize));
            }
        });

        diff[0] = Mathf.clamp(diff[0]);

        if (diff[0] >= 1f) {
            if (b.data instanceof Vec2 vel) b.vel = vel;
            b.data = b.vel.cpy();
        } else {
            if (b.data instanceof Vec2 vel) b.vel.set(vel).scl(diff[0]);
        }

        if (Mathf.chanceDelta(pushEffectChance)) {
            pushEffect.at(b.x, b.y, b.rotation(), b);
        }
    }
}
