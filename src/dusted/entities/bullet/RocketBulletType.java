package dusted.entities.bullet;

import arc.audio.*;
import arc.math.*;
import arc.util.*;
import mindustry.entities.*;
import mindustry.entities.bullet.*;
import mindustry.entities.pattern.*;
import mindustry.gen.*;

//yes i know intervalbullet exists now but i just wanna keep this thing ok?
public class RocketBulletType extends BasicBulletType {
    public float rocketReload = 10f;
    public float rocketDelay;
    public float inaccuracy;
    public float rocketAngle = 180f;
    public BulletType rocketBulletType;
    public ShootPattern shoot = new ShootPattern();
    public Sound shootSound = Sounds.none;

    {
        pierce = true;
    }

    public RocketBulletType(float speed, float damage) {
        super(speed, damage);
    }

    public RocketBulletType(float speed, float damage, String sprite) {
        super(speed, damage, sprite);
    }

    @Override
    public void init() {
        super.init();

        rocketBulletType.keepVelocity = false;
    }

    @Override
    public void update(Bullet b) {
        super.update(b);
        if (b.data == null) b.data = 0;

        if (b.time > rocketDelay) b.fdata -= Time.delta;

        if (b.fdata < 0) {
            b.fdata = rocketReload;

            shoot.shoot((int) b.data, (xOffset, yOffset, angle, delay, mover) -> {
                if (delay > 0) {
                    Time.run(delay, () -> shoot(b, xOffset, yOffset, angle, mover));
                } else {
                    shoot(b, xOffset, yOffset, angle, mover);
                }

                b.data = ((int) b.data) + 1;
            });
        }
    }

    public void shoot(Bullet owner, float xOffset, float yOffset, float angleOffset, Mover mover) {
        float bx = owner.x + Angles.trnsx(owner.rotation(), xOffset, yOffset);
        float by = owner.y + Angles.trnsy(owner.rotation(), xOffset, yOffset);
        float rot = owner.rotation() + rocketAngle + angleOffset + Mathf.range(inaccuracy);

        rocketBulletType.create(owner, owner.team, bx, by, rot, 1f, 1f, mover);
        rocketBulletType.shootEffect.at(bx, by, rot, rocketBulletType.hitColor);
        shootSound.at(bx, by, Mathf.random(0.9f, 1.1f));
    }
}
