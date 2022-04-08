package dusted.entities.bullet;

import arc.math.*;
import mindustry.entities.bullet.*;
import mindustry.game.*;
import mindustry.gen.*;

public class CascadingBulletType extends BasicBulletType {
    public float cascadeTime = 10f;
    public float cascadeSpacing = 10f;
    public int cascadeAmount = 10;

    public CascadingBulletType(float speed, float damage) {
        super(speed, damage);
    }

    public CascadingBulletType(float speed, float damage, String bulletSprite) {
        super(speed, damage, bulletSprite);
    }

    @Override
    public void update(Bullet b) {
        super.update(b);

        if (b.data instanceof CascadeData data && !data.hasCascaded && data.amount > 0 && b.time > cascadeTime) {
            create(b, b.team, b.x + Angles.trnsx(b.rotation(), cascadeSpacing), b.y + Angles.trnsy(b.rotation(), cascadeSpacing), b.rotation(), -1, 1, 1, new CascadeData(data.amount - 1));
            data.hasCascaded = true;
        }
    }

    @Override
    public Bullet create(Entityc owner, Team team, float x, float y, float angle, float velocityScl, float lifetimeScl) {
        return create(owner, team, x, y, angle, -1, velocityScl, lifetimeScl, new CascadeData(cascadeAmount));
    }

    static class CascadeData {
        public int amount;
        public boolean hasCascaded;

        public CascadeData(int amount) {
            this.amount = amount;
        }
    }
}
