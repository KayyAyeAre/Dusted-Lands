package dusted.entities.bullet;

import arc.graphics.*;
import arc.graphics.g2d.*;
import mindustry.entities.bullet.*;
import mindustry.gen.*;
import mindustry.graphics.*;

public class EruptionBulletType extends BulletType {
    public Trail trail = new Trail(50);

    public EruptionBulletType(float speed, float damage) {
        this.speed = speed;
        this.damage = damage;
    }

    @Override
    public void draw(Bullet b) {
        trail.draw(Pal.darkMetal, 50 + (20 * b.fin()));
        Draw.color(Pal.darkMetal);
        Fill.circle(b.x, b.y, 50 + (20 * b.fin()));
        Draw.color(Color.gray, Pal.darkFlame, Pal.lightFlame, b.fin());
        Fill.circle(b.x, b.y, 40 + (20 * b.fin()));
        Draw.reset();
    }
}
