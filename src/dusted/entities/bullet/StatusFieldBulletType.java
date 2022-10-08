package dusted.entities.bullet;

import arc.graphics.*;
import arc.graphics.g2d.*;
import arc.math.*;
import mindustry.content.*;
import mindustry.entities.*;
import mindustry.entities.bullet.*;
import mindustry.gen.*;

public class StatusFieldBulletType extends BulletType {
    public float damageInterval = 1f;
    public float fieldRadius = 40f;
    public float sclTime = 50f;
    public Color color = Color.white;
    public Interp sclInterp = Interp.pow3Out;
    public Effect fieldEffect = Fx.none;
    public float fieldEffectChance = 0.2f;
    public int blobs = 6;
    public float blobLength = 50f;
    public float blobRadius = 15f;

    public float alphaScl = 8f, alphaMag = 0.15f;

    {
        collides = false;
        collidesAir = false;
        reflectable = false;
        speed = 0f;
        hitEffect = despawnEffect = Fx.none;
    }

    @Override
    public void draw(Bullet b) {
        float ptime = sclTime / lifetime;
        float progress = sclInterp.apply(Mathf.clamp(b.fslope() * ((1f - ptime) * (1f / ptime))));

        Draw.color(color, color.a + Mathf.absin(alphaScl, alphaMag));
        Fill.circle(b.x, b.y, fieldRadius * progress);
        Angles.randLenVectors(b.id, blobs, blobLength * progress, (x, y) -> {
            Fill.circle(b.x + x, b.y + y, blobRadius * progress);
        });
        Draw.color();
    }

    @Override
    public void update(Bullet b) {
        super.update(b);

        if (Mathf.chanceDelta(fieldEffectChance)) {
            float ptime = sclTime / lifetime;
            float progress = sclInterp.apply(Mathf.clamp(b.fslope() * ((1f - ptime) * (1f / ptime))));

            float angle = Mathf.random(360f);
            float len = Mathf.random(fieldRadius * progress);
            fieldEffect.at(b.x + Angles.trnsx(angle, len), b.y + Angles.trnsy(angle, len));
        }

        if (b.timer.get(3, damageInterval)) {
            Damage.damage(b.team, b.x, b.y, fieldRadius, damage * b.damageMultiplier(), false, collidesAir, collidesGround, scaledSplashDamage, b);
            if (status != StatusEffects.none) Damage.status(b.team, b.x, b.y, fieldRadius, status, statusDuration, collidesAir, collidesGround);
        }
    }
}
