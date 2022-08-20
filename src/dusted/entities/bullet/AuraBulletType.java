package dusted.entities.bullet;

import arc.graphics.*;
import arc.graphics.g2d.*;
import arc.math.*;
import dusted.content.*;
import mindustry.*;
import mindustry.content.*;
import mindustry.entities.*;
import mindustry.entities.bullet.*;
import mindustry.gen.*;

public class AuraBulletType extends BasicBulletType {
    public float auraDamage = 2f;
    public float auraRadius = 60f;
    public Effect auraEffect = Fx.none;
    public Color auraColor;
    public float auraEffectChance = 0.2f;

    @Override
    public void update(Bullet b) {
        super.update(b);

        Units.nearbyEnemies(b.team, b.x, b.y, auraRadius, u -> {
            u.damageContinuous(auraDamage);
            if (Mathf.chance(auraEffectChance)) auraEffect.at(u);
        });

        Vars.indexer.eachBlock(null, b.x, b.y, auraRadius, build -> build.team != b.team, build -> {
            build.damageContinuous(auraDamage);
            if (Mathf.chance(auraEffectChance)) auraEffect.at(build);
        });
    }

    @Override
    public void draw(Bullet b) {
        super.draw(b);

        float drawRadius = Interp.pow2.apply(Math.min(b.time, 30f) / 30f) * auraRadius;

        Draw.color(auraColor);
        Lines.dashCircle(b.x, b.y, drawRadius);
        Draw.color();
    }

    @Override
    public void removed(Bullet b) {
        super.removed(b);
        DustedFx.auraFade.at(b.x, b.y, auraRadius, auraColor);
    }
}
