package dusted.entities.bullet;

import arc.graphics.*;
import arc.graphics.g2d.*;
import arc.math.*;
import arc.util.*;
import mindustry.entities.bullet.*;
import mindustry.gen.*;
import mindustry.graphics.*;

//pointlaserbullettype but with different draw code
public class FlamePointLaserBulletType extends PointLaserBulletType {
    public Color backColor = Color.white;
    public float frontRad, backRad;

    @Override
    public void draw(Bullet b) {
        float frad = frontRad * (b.fslope() * (1f - oscMag + Mathf.absin(Time.time, oscScl, oscMag)));
        float brad = backRad * (b.fslope() * (1f - oscMag + Mathf.absin(Time.time, oscScl, oscMag)));

        Draw.color(backColor);
        Fill.circle(b.aimX, b.aimY, brad);
        Drawf.tri(b.aimX, b.aimY, brad * 2f, Mathf.dst(b.x, b.y, b.aimX, b.aimY), Mathf.angle(b.x - b.aimX, b.y - b.aimY));
        Draw.color(color);
        Fill.circle(b.aimX, b.aimY, frad);
        Drawf.tri(b.aimX, b.aimY, frad * 2f, Mathf.dst(b.x, b.y, b.aimX, b.aimY), Mathf.angle(b.x - b.aimX, b.y - b.aimY));
        Draw.color();
    }
}
