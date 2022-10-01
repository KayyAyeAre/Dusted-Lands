package dusted.type.weapons;

import arc.math.*;
import arc.util.*;
import mindustry.entities.units.*;
import mindustry.gen.*;
import mindustry.type.*;

public class PointWeapon extends Weapon {
    public float aimChangeSpeed = Float.POSITIVE_INFINITY;

    {
        mountType = PointWeaponMount::new;
    }

    @Override
    public void update(Unit unit, WeaponMount mount) {
        super.update(unit, mount);
        if (mount.bullet != null && mount instanceof PointWeaponMount pmount) {
            float shootLength = Math.min(Mathf.dst(mount.bullet.x, mount.bullet.y, mount.aimX, mount.aimY), range());
            float curLength = Mathf.dst(mount.bullet.x, mount.bullet.y, mount.bullet.aimX, mount.bullet.aimY);
            float resultLength = Mathf.approachDelta(curLength, shootLength, aimChangeSpeed);

            Tmp.v1.trns(mount.rotation + unit.rotation, pmount.lastLength = resultLength).add(unit.x, unit.y);

            mount.bullet.aimX = Tmp.v1.x;
            mount.bullet.aimY = Tmp.v1.y;
        }
    }

    @Override
    protected void handleBullet(Unit unit, WeaponMount mount, Bullet bullet) {
        if (!(mount instanceof PointWeaponMount pmount)) return;

        Tmp.v1.trns(mount.rotation + unit.rotation, shootY + pmount.lastLength).add(unit.x, unit.y);
        bullet.aimX = Tmp.v1.x;
        bullet.aimY = Tmp.v1.y;
    }

    public static class PointWeaponMount extends WeaponMount {
        public float lastLength;

        public PointWeaponMount(Weapon weapon) {
            super(weapon);
        }
    }
}
