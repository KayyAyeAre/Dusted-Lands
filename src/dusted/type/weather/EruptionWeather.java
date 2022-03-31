package dusted.type.weather;

import arc.math.*;
import dusted.content.*;
import mindustry.*;
import mindustry.entities.bullet.*;
import mindustry.game.*;
import mindustry.gen.*;
import mindustry.type.weather.*;

public class EruptionWeather extends ParticleWeather {
    public BulletType bullet = DustedBullets.eruptionFireball;
    public float fallAngle = 160f;

    public EruptionWeather(String name) {
        super(name);
    }

    @Override
    public void update(WeatherState state) {
        float x, y;

        do {
            x = Mathf.random(0, Vars.world.unitWidth());
            y = Mathf.random(0, Vars.world.unitHeight());
        } while (Vars.world.buildWorld(x, y) != null);

        x += Angles.trnsx(fallAngle + 180, bullet.range());
        y += Angles.trnsy(fallAngle + 180, bullet.range());

        if (Mathf.chance(0.05)) {
            Call.createBullet(bullet, Team.derelict, x, y, fallAngle, bullet.damage, 1f, 1f);
        }
    }
}
