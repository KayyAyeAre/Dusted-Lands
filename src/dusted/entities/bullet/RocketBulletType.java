package dusted.entities.bullet;

import arc.audio.*;
import arc.math.*;
import mindustry.entities.bullet.*;
import mindustry.gen.*;

public class RocketBulletType extends BasicBulletType {
    public float rocketReload = 10f;
    public int rockets = 1;
    public float rocketSpread;
    public BulletType rocketBulletType;
    public Sound shootSound = Sounds.none;
    public boolean invert = true;

    public RocketBulletType(float speed, float damage) {
        super(speed, damage);
    }

    @Override
    public void update(Bullet b) {
        super.update(b);
        if (b.timer(3, rocketReload)) {
            for (int i = 0; i < rockets; i++) {
                float angle = (i - (int) (rockets / 2f)) * rocketSpread;
                rocketBulletType.create(b, b.x, b.y, b.rotation() + (invert ? 180 : 0) + angle);
                rocketBulletType.shootEffect.at(b.x, b.y, b.rotation() + (invert ? 180 : 0) + angle);
                shootSound.at(b.x, b.y, Mathf.random(0.9f, 1.1f));
            }
        }
    }
}
