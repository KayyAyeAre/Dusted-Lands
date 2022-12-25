package dusted.entities.bullet;

import arc.graphics.*;
import arc.graphics.g2d.*;
import arc.math.*;
import dusted.content.*;
import mindustry.entities.*;
import mindustry.entities.bullet.*;
import mindustry.gen.*;
import mindustry.graphics.*;

public class AuraBulletType extends BasicBulletType {
    public float auraDamage = 12f;
    public float auraDamageInterval = 10f;
    public float auraRadius = 60f;
    public float auraStroke = 3f;
    public float lightLayer = Layer.bullet - 0.03f;
    public Color auraColor;

    public AuraBulletType(float speed, float damage) {
        super(speed, damage);
    }

    @Override
    public void update(Bullet b) {
        super.update(b);

        if (b.timer(0, auraDamageInterval)) {
            Damage.damage(b.team, b.x, b.y, auraRadius, auraDamage, false, collidesAir, collidesGround, false, b);
        }
    }

    @Override
    public void draw(Bullet b) {
        super.draw(b);

        float drawRadius = Interp.pow2.apply(Math.min(b.time, 30f) / 30f) * auraRadius;

        Draw.color(auraColor);
        Lines.stroke(auraStroke);
        Lines.circle(b.x, b.y, drawRadius);
        Draw.z(lightLayer);
        Draw.blend(Blending.additive);
        Fill.light(b.x, b.y, Lines.circleVertices(drawRadius), drawRadius, Color.clear, auraColor);
        Draw.blend();
    }

    @Override
    public void removed(Bullet b) {
        super.removed(b);
        DustedFx.auraFade.at(b.x, b.y, auraRadius, auraColor);
    }
}
