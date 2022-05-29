package dusted.content;

import arc.graphics.*;
import dusted.ai.types.*;
import dusted.entities.abilities.*;
import dusted.entities.bullet.*;
import dusted.entities.units.*;
import dusted.graphics.*;
import mindustry.ai.types.*;
import mindustry.content.*;
import mindustry.entities.bullet.*;
import mindustry.gen.*;
import mindustry.type.*;
import mindustry.type.weapons.*;

public class DustedUnitTypes {
    //why did i look for 28 different names to only use 9 of them
    public static UnitType
    carom, recur, saltate, staccato, recrudesce,

    revoke, negate, preclude, repudiate, terminate,

    quail, seism, quaver, temblor, convulse,

    pique, rancor, animus, enmity, acrimony,

    protei, hynobii, sirenid, pleurodel, dicamptodon,

    erode, recede, atrophy;

    public static void load() {
        erode = new DustedUnitType("erode") {{
            constructor = UnitEntity::create;
            defaultController = BuilderAI::new;
            isCounted = false;
            flying = true;
            itemCapacity = 30;
            outlineColor = DustedPal.darkerCavnen;
            alwaysUnlocked = true;
            accel = 0.1f;
            drag = 0.05f;
            commandLimit = 3;
            speed = 3.2f;
            mineSpeed = 7f;
            mineTier = 1;
            buildSpeed = 0.5f;
            rotateSpeed = 12f;

            weapons.add(new Weapon("dusted-lands-core-decay-weapon") {{
                x = 3.1f;
                y = 2f;
                top = false;
                reload = 40f;
                shootSound = Sounds.lasershoot;

                bullet = new BasicBulletType(3f, 18f) {{
                    width = 8f;
                    height = 12f;
                    lifetime = 40f;
                    buildingDamageMultiplier = 0.01f;
                    trailWidth = 2f;
                    trailLength = 16;
                    status = DustedStatusEffects.deteriorating;
                    statusDuration = 6 * 60f;
                    frontColor = DustedPal.cavnenYellow;
                    backColor = trailColor = DustedPal.cavnenYellowBack;
                }};
            }});
        }};

        recede = new UnitType("recede") {{
            constructor = UnitEntity::create;
        }};

        atrophy = new UnitType("atrophy") {{
            constructor = UnitEntity::create;
        }};

        carom = new DustedUnitType("carom") {{
            constructor = UnitEntity::create;
            defaultController = BounceAI::new;
            speed = 2.5f;
            flying = true;
            health = 80;
            commandLimit = 4;
            rotateSpeed = 10f;
            range = 120f;
            outlineColor = DustedPal.darkerCavnen;

            abilities.add(new BounceAbility() {{
                bounceDamage = 6f;
            }});
        }};

        recur = new DustedUnitType("recur") {{
            constructor = UnitEntity::create;
            defaultController = BounceAI::new;
            health = 320;
            armor = 2f;
            speed = 1.8f;
            accel = 0.16f;
            flying = true;
            hitSize = 8f;
            range = 180f;
            engineOffset = 8f;
            engineSize = 3f;
            outlineColor = DustedPal.darkerCavnen;

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
                    colors = new Color[]{DustedPal.cavnenYellowBack, DustedPal.cavnenYellow, Color.white};
                }};
            }});

            abilities.add(new BounceAbility() {{
                bounceDamage = 14f;
                bounceDistance = 180f;
                bounces = 2;
                bounceCooldown = 70f;
            }});
        }};

        saltate = new DustedUnitType("saltate") {{
            constructor = UnitEntity::create;
            defaultController = BounceAI::new;
            health = 650;
            speed = 1.6f;
            accel = 0.08f;
            flying = true;
            lowAltitude = true;
            armor = 4f;
            hitSize = 10f;
            range = 180f;
            engineOffset = 8f;
            engineSize = 6f;
            outlineColor = DustedPal.darkerCavnen;

            weapons.add(new Weapon("dusted-lands-saltate-launcher") {{
                reload = 70f;
                x = 6f;
                y = -2f;
                rotate = true;
                shootSound = Sounds.missile;
                outlineColor = DustedPal.darkerCavnen;

                bullet = new BasicBulletType(3.5f, 12f) {{
                    width = height = 14f;
                    splashDamageRadius = 18f;
                    splashDamage = 14f;
                    lifetime = 40f;
                    frontColor = DustedPal.cavnenYellow;
                    backColor = DustedPal.cavnenYellowBack;
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
                        frontColor = DustedPal.cavnenYellow;
                        backColor = DustedPal.cavnenYellowBack;
                        trailColor = DustedPal.cavnenYellow;
                        trailLength = 6;
                        trailWidth = 8f;
                    }};
                }};
            }});

            abilities.add(new BounceAbility() {{
                bounceDamage = 10f;
                bounceDistance = 180f;
                bounces = 4;
                bounceCooldown = 110f;
            }});
        }};

        pique = new DustedUnitType("pique") {{
            constructor = MechUnit::create;
            defaultController = OrbsAI::new;
            speed = 0.8f;
            hitSize = 7f;
            health = 140f;
            outlineColor = DustedPal.darkerCavnen;
            mechLegColor = DustedPal.darkerCavnen;

            weapons.add(new Weapon("dusted-lands-decay-gun") {{
                y = -1f;
                x = 2.5f;
                reload = 50f;
                shots = 3;
                inaccuracy = 10f;
                shootSound = Sounds.missile;

                bullet = new BasicBulletType(3.6f, 8f, "circle-bullet") {{
                    width = height = 10f;
                    shrinkX = shrinkY = 0.4f;
                    drag = 0.04f;
                    lifetime = 50f;
                    status = DustedStatusEffects.deteriorating;
                    statusDuration = 4 * 60f;
                }};
            }});

            abilities.add(new RevolvingOrbAbility() {{
                damage = 22f;
                orbs = 2;
                radius = 20f;
                rotationSpeed = 10f;
                orbRadius = 5f;
                orbLifetime = 320f;
                orbCooldown = 200f;
            }});
        }};

        rancor = new DustedUnitType("rancor") {{
            constructor = MechUnit::create;
            defaultController = OrbsAI::new;
            speed = 0.7f;
            hitSize = 10f;
            health = 300f;
            armor = 5f;
            outlineColor = DustedPal.darkerCavnen;
            mechLegColor = DustedPal.darkerCavnen;

            weapons.add(new Weapon("dusted-lands-large-decay-gun") {{
                x = 3;
                y = -2;
                reload = 40f;
                shots = 2;
                spacing = 15f;
                shootSound = Sounds.shootBig;

                bullet = new ShrapnelBulletType() {{
                    length = 80f;
                    damage = 22f;
                    width = 20f;
                    serrations = 8;
                    shootEffect = DustedFx.shootCavnenShrapnel;
                    smokeEffect = Fx.shootSmallSmoke;
                    fromColor = DustedPal.cavnenYellow;
                    toColor = DustedPal.cavnenYellowBack;
                }};
            }});

            abilities.add(new RevolvingOrbAbility() {{
                damage = 30f;
                orbs = 3;
                radius = 28f;
                rotationSpeed = 12f;
                orbRadius = 7f;
                orbLifetime = 400f;
                orbCooldown = 320f;
            }});
        }};

        animus = new DustedUnitType("animus") {{
            speed = 0.5f;
            health = 800f;
            hitSize = 12f;
            rotateSpeed = 4f;
            armor = 7f;
            constructor = MechUnit::create;
            defaultController = OrbsAI::new;
            outlineColor = DustedPal.darkerCavnen;
            mechLegColor = DustedPal.darkerCavnen;

            weapons.addAll(
                    new Weapon("dusted-lands-decay-launcher") {{
                        x = 6f;
                        y = 2f;
                        top = false;
                        recoil = 5f;
                        reload = 110f;
                        shake = 1.6f;
                        shootSound = Sounds.shootBig;

                        bullet = new RocketBulletType(2.6f, 30f) {{
                            width = height = 18f;
                            splashDamage = 16f;
                            splashDamageRadius = 20f;
                            lifetime = 80f;
                            frontColor = DustedPal.cavnenYellow;
                            backColor = trailColor = DustedPal.cavnenYellowBack;
                            trailLength = 18;
                            trailWidth = 6f;
                            status = DustedStatusEffects.deteriorating;
                            statusDuration = 12 * 60f;
                            rockets = 3;
                            rocketSpread = 20f;
                            shootSound = Sounds.explosion;

                            rocketBulletType = new BasicBulletType(2f, 12f) {{
                                width = height = 12f;
                                splashDamage = 14f;
                                splashDamageRadius = 14f;
                                lifetime = 40f;
                                frontColor = DustedPal.cavnenYellow;
                                backColor = DustedPal.cavnenYellowBack;
                                status = DustedStatusEffects.deteriorating;
                                statusDuration = 4 * 60f;
                            }};
                        }};
                    }},
                    new RepairBeamWeapon("dusted-lands-animus-repair-weapon") {{
                        x = 0f;
                        y = -6f;
                        shootY = 5f;
                        beamWidth = 0.8f;
                        laserColor = DustedPal.cavnenYellow;
                        mirror = false;
                        repairSpeed = 1f;

                        bullet = new BulletType() {{
                            maxRange = 140f;
                        }};
                    }}
            );

            abilities.add(new RevolvingOrbAbility() {{
                damage = 40f;
                orbs = 6;
                radius = 40f;
                rotationSpeed = 9f;
                orbRadius = 8f;
                orbLifetime = 720f;
                orbCooldown = 420f;
            }});
        }};

        quail = new DustedUnitType("quail") {{
            constructor = MechUnit::create;
            defaultController = QuakeAI::new;
            speed = 0.6f;
            health = 120;
            commandLimit = 5;
            outlineColor = DustedPal.darkerCavnen;
            mechLegColor = DustedPal.darkerCavnen;

            abilities.add(new QuakeAbility() {{
                quakeSteps = 3;
                quakes = 3;
            }});
        }};
    }
}
