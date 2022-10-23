package dusted.entities.abilities;

import arc.audio.*;
import arc.graphics.*;
import arc.graphics.g2d.*;
import arc.math.*;
import arc.struct.*;
import arc.util.*;
import dusted.graphics.*;
import mindustry.content.*;
import mindustry.entities.*;
import mindustry.entities.abilities.*;
import mindustry.gen.*;
import mindustry.graphics.*;

//kinda like a portable shockwave tower
public class ShockwaveAbility extends Ability {
    public float reload = 90f;
    public float radius = 160f;
    public float bulletDamage = 60f;
    public float warmupSpeed = 0.06f;
    public float interval = 8f;
    public float warmupCooldown = 120f;
    public Effect hitEffect = Fx.pointHit;
    public Sound shockwaveSound = Sounds.lasershoot;
    public Color color = DustedPal.pinkHeal;
    public float stroke = 2f;
    public int sectors = 3;
    public float sectorRad = 0.3f;
    public float rotateSpeed = 0.5f;

    public float counter;
    public float warmup;
    public float cooldown;
    public Seq<Bullet> targets = new Seq<>();

    @Override
    public void update(Unit unit) {
        counter += Time.delta;
        data += Time.delta;
        cooldown = Mathf.maxZero(cooldown - Time.delta);
        warmup = Mathf.lerpDelta(warmup, cooldown > 0f ? 1f : 0f, warmupSpeed);

        if (counter > interval) {
            counter = 0f;

            if (data > reload) {
                targets.clear();
                Groups.bullet.intersect(unit.x - radius, unit.y - radius, radius * 2f, radius * 2f, b -> {
                    if (b.within(unit, radius) && b.team != unit.team && b.type.hittable) {
                        targets.add(b);
                    }
                });

                if (targets.any()) {
                    cooldown = warmupCooldown;
                    data = 0f;
                    shockwaveSound.at(unit);
                    targets.each(target -> {
                        hitEffect.at(target);
                        if (target.damage > bulletDamage) {
                            target.damage -= bulletDamage;
                        } else {
                            target.remove();
                        }
                    });
                }
            }
        }
    }

    @Override
    public void draw(Unit unit) {
        Draw.z(Layer.effect);
        Draw.color(color);
        Lines.stroke(warmup * stroke);
        for (int i = 0; i < sectors; i++) {
            Lines.arc(unit.x, unit.y, radius, sectorRad, (360f / sectors) * i + Time.time * rotateSpeed);
        }
        Draw.reset();
    }
}
