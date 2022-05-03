package dusted.content;

import arc.graphics.*;
import dusted.entities.units.*;
import mindustry.content.*;
import mindustry.entities.bullet.*;
import mindustry.gen.*;
import mindustry.type.*;

public class DustedUnitTypes {
    public static UnitType
    carom, recur, saltate, ricochet, recrudesce,
    seism, quaver, temblor, convulse,
    cavanimate
    ;

    public static void load() {
        BouncingUnitEntity.classID = EntityMapping.register("BouncingUnitEntity", BouncingUnitEntity::new);
        QuakeUnitEntity.classID = EntityMapping.register("QuakeUnitEntity", QuakeUnitEntity::new);

        EntityMapping.nameMap.put("dusted-lands-carom", BouncingUnitEntity::new);
        carom = new BouncingUnitType("carom") {{
            speed = 3f;
            flying = true;
            health = 80;
            commandLimit = 4;
            rotateSpeed = 8f;
            range = 120f;
            outlineColor = Color.valueOf("4d5048");
        }};

        EntityMapping.nameMap.put("dusted-lands-recur", BouncingUnitEntity::new);
        recur = new BouncingUnitType("recur") {{
            health = 320;
            armor = 2f;
            speed = 1.8f;
            accel = 0.16f;
            flying = true;
            hitSize = 8f;
            bounceDamage = 20f;
            bounceDistance = 180f;
            bounces = 2;
            bounceCooldown = 70f;
            range = 180f;
            engineOffset = 8f;
            engineSize = 3f;
            outlineColor = Color.valueOf("4d5048");

            weapons.add(new Weapon() {{
                x = 0f;
                y = 6.2f;
                alternate = false;
                reload = 50f;
                shootSound = Sounds.laser;

                bullet = new LaserBulletType() {{
                    damage = 30f;
                    length = 50f;
                    sideWidth = 0.8f;
                    sideLength = 52f;
                    colors = new Color[]{Color.valueOf("d5ad7f"), Color.valueOf("fff0a1"), Color.white};
                }};
            }});
        }};

        EntityMapping.nameMap.put("dusted-lands-saltate", BouncingUnitEntity::new);
        saltate = new BouncingUnitType("saltate") {{
            health = 650;
            speed = 1.6f;
            accel = 0.08f;
            flying = true;
            lowAltitude = true;
            armor = 4f;
            hitSize = 10f;
            bounceDamage = 15f;
            bounceDistance = 180f;
            bounces = 4;
            bounceCooldown = 90f;
            range = 180f;
            engineOffset = 8f;
            engineSize = 6f;
            outlineColor = Color.valueOf("4d5048");

            weapons.add(new Weapon("dusted-lands-saltate-launcher") {{
                reload = 70f;
                x = 6f;
                y = -2f;
                rotate = true;
                shootSound = Sounds.missile;
                outlineColor = Color.valueOf("4d5048");

                bullet = new BasicBulletType(3.5f, 12f) {{
                    width = height = 14f;
                    splashDamageRadius = 18f;
                    splashDamage = 14f;
                    lifetime = 40f;
                    frontColor = Color.valueOf("fff0a1");
                    backColor = Color.valueOf("d5ad7f");
                    shootEffect = Fx.shootHealYellow;
                    hitEffect = despawnEffect = Fx.hitYellowLaser;

                    fragBullets = 5;
                    fragBullet = new MissileBulletType(4f, 10f) {{
                        width = height = 8f;
                        homingPower = 0.1f;
                        weaveMag = 3f;
                        weaveScale = 4f;
                        lifetime = 30f;
                        splashDamageRadius = 16f;
                        splashDamage = 12f;
                        hitEffect = despawnEffect = Fx.hitYellowLaser;
                        frontColor = Color.valueOf("fff0a1");
                        backColor = Color.valueOf("d5ad7f");
                        trailColor = Color.valueOf("fff0a1");
                        trailLength = 6;
                        trailWidth = 8f;
                    }};
                }};
            }});
        }};

        EntityMapping.nameMap.put("dusted-lands-seism", QuakeUnitEntity::new);
        seism = new QuakeUnitType("seism") {{
            quakeSteps = 3;
            quakes = 3;
            speed = 0.6f;
            health = 120;
            commandLimit = 5;
        }};

        //TODO
        //cavanimate = new UnitType("cavanimate");
    }
}
