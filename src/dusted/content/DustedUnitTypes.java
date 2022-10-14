package dusted.content;

import arc.graphics.*;
import dusted.ai.types.*;
import dusted.entities.abilities.*;
import dusted.entities.bullet.*;
import dusted.entities.units.*;
import dusted.graphics.*;
import dusted.type.weapons.*;
import mindustry.ai.*;
import mindustry.ai.types.*;
import mindustry.content.*;
import mindustry.entities.abilities.*;
import mindustry.entities.bullet.*;
import mindustry.entities.part.*;
import mindustry.entities.pattern.*;
import mindustry.gen.*;
import mindustry.graphics.*;
import mindustry.type.*;
import mindustry.type.weapons.*;

import static mindustry.Vars.tilesize;

public class DustedUnitTypes {
    public static UnitType
    carom, recur, saltate, staccato,
    sob, wail,
    annul,
    pique, rancor, animus,
    protei, hynobii,
    erode, recede, atrophy;

    public static void load() {
        FinUnitEntity.classID = EntityMapping.register("FinUnitEntity", FinUnitEntity::new);

        annul = new DustedUnitType("annul") {{
            constructor = MechUnit::create;
            speed = 0.6f;
            health = 180f;
            hitSize = 7f;

            weapons.add(new Weapon("dusted-lands-small-decay-launcher") {{
                mirror = false;
                x = 0f;
                y = -3f;
                reload = 45f;
                recoil = 3f;
                shootSound = Sounds.bang;

                bullet = new InstantBulletType() {{
                    damage = 17f;
                    distance = 100f;
                    shootEffect = DustedFx.shootLaunch;
                    status = StatusEffects.blasted;
                    hitEffect = despawnEffect = DustedFx.hitLaunch;
                }};
            }});
        }};

        carom = new DustedUnitType("carom") {{
            constructor = UnitEntity::create;
            aiController = BounceAI::new;
            speed = 4.2f;
            flying = true;
            lowAltitude = true;
            health = 120;
            accel = 0.04f;
            drag = 0.03f;
            rotateSpeed = 10f;
            trailLength = 12;
            trailColor = DustedPal.decayingYellow;
            trailScl = 0.6f;

            weapons.add(new Weapon("dusted-lands-carom-weapon") {{
                x = 3f;
                y = -2f;
                alternate = false;
                reload = 35f;
                shootSound = Sounds.missile;

                shoot.shots = 3;
                shoot.shotDelay = 5f;

                bullet = new BasicBulletType(4f, 8f) {{
                    width = 6f;
                    height = 11f;
                    lifetime = 20f;
                    frontColor = DustedPal.decayingYellow;
                    backColor = hitColor = DustedPal.decayingYellowBack;
                    hitEffect = despawnEffect = DustedFx.hitCavnen;
                    status = DustedStatusEffects.deteriorating;
                    statusDuration = 6f * 60f;
                }};

                abilities.add(new MoveLightningAbility(0f, 0, 1f, 0f, 0.4f, 1.2f, Color.white) {{
                    shootEffect = Fx.none;
                    shootSound = Sounds.none;
                    bullet = new BulletType(0f, 12f) {{
                        instantDisappear = true;
                        splashDamage = 10f;
                        splashDamageRadius = 3f;
                        hittable = reflectable = absorbable = setDefaults = despawnHit = false;
                        despawnEffect = Fx.none;
                        hitEffect = DustedFx.caromSparks;
                        //TODO sound
                        hitSound = Sounds.missile;
                        hitSoundVolume = 0.7f;
                    }};
                }});
            }});
        }};

        recur = new DustedUnitType("recur") {{
            constructor = UnitEntity::create;
            aiController = BounceAI::new;
            health = 320f;
            armor = 2f;
            speed = 2.6f;
            accel = 0.16f;
            flying = true;
            hitSize = 8f;
            range = 180f;
            engineOffset = 8f;
            engineSize = 3f;

            weapons.add(new Weapon() {{
                x = 0f;
                y = 6.2f;
                mirror = false;
                reload = 50f;
                shootSound = Sounds.laser;

                bullet = new LaserBulletType() {{
                    damage = 30f;
                    length = 50f;
                    sideWidth = 0.8f;
                    sideLength = 52f;
                    colors = new Color[]{DustedPal.decayingYellowBack, DustedPal.decayingYellow, Color.white};
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
            aiController = BounceAI::new;
            health = 680f;
            speed = 2f;
            accel = 0.08f;
            drag = 0.03f;
            flying = true;
            lowAltitude = true;
            armor = 4f;
            hitSize = 10f;
            range = 180f;
            engineOffset = 8f;
            engineSize = 3f;

            weapons.add(new Weapon("dusted-lands-decay-mount") {{
                reload = 70f;
                x = 6f;
                y = -2f;
                rotate = true;
                shootSound = Sounds.missile;

                bullet = new BasicBulletType(3.5f, 12f) {{
                    width = height = 14f;
                    splashDamageRadius = 18f;
                    splashDamage = 14f;
                    lifetime = 40f;
                    frontColor = DustedPal.decayingYellow;
                    backColor = DustedPal.decayingYellowBack;
                    shootEffect = Fx.shootHealYellow;
                    hitEffect = despawnEffect = DustedFx.hitCavnen;

                    fragBullets = 5;
                    fragBullet = new MissileBulletType(4f, 10f, "circle-bullet") {{
                        width = height = 8f;
                        homingPower = 0.1f;
                        weaveMag = 3f;
                        weaveScale = 4f;
                        lifetime = 30f;
                        splashDamageRadius = 16f;
                        splashDamage = 12f;
                        hitEffect = despawnEffect = DustedFx.hitCavnen;
                        frontColor = DustedPal.decayingYellow;
                        backColor = DustedPal.decayingYellowBack;
                        trailColor = DustedPal.decayingYellow;
                        trailLength = 6;
                        trailWidth = 3f;
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

        staccato = new DustedUnitType("staccato") {{
            constructor = UnitEntity::create;
            aiController = BounceAI::new;
            health = 6800f;
            armor = 7f;
            accel = 0.05f;
            drag = 0.03f;
            rotateSpeed = 4f;
            speed = 1.2f;
            hitSize = 22f;
            engineOffset = 18f;
            engineSize = 4.6f;
            flying = true;
            lowAltitude = true;

            weapons.addAll(
                    new Weapon("dusted-lands-decay-launcher-mount") {{
                        x = 12f;
                        y = -3f;
                        reload = 180f;
                        rotate = true;
                        shootSound = Sounds.shotgun;

                        bullet = new RocketBulletType(5.5f, 110f, "dusted-lands-arrow-bullet") {{
                            width = 18f;
                            height = 10f;
                            shrinkX = 0.2f;
                            shrinkY = 0.1f;
                            drag = 0.012f;
                            lifetime = 120f;
                            status = DustedStatusEffects.deteriorating;
                            statusDuration = 16 * 60f;
                            rocketReload = 10f;
                            weaveScale = 7f;
                            weaveMag = 1f;
                            homingPower = 0.08f;
                            homingRange = 80f;
                            frontColor = DustedPal.decayingYellow;
                            backColor = DustedPal.decayingYellowBack;
                            hitEffect = despawnEffect = DustedFx.hitCavnen;

                            rocketBulletType = new BasicBulletType(1f, 44f, "circle-bullet") {{
                                drag = 0.06f;
                                width = height = 8f;
                                shrinkX = shrinkY = 0.3f;
                                pierce = true;
                                lifetime = 140f;
                                frontColor = DustedPal.decayingYellow;
                                backColor = DustedPal.decayingYellowBack;
                                status = DustedStatusEffects.deteriorating;
                                statusDuration = 4 * 60f;
                                hitEffect = despawnEffect = DustedFx.hitCavnen;
                            }};
                        }};
                    }},
                    new Weapon("dusted-lands-decay-mount") {{
                        x = 9f;
                        y = 11f;
                        reload = 70f;
                        rotate = true;
                        shootSound = Sounds.missile;

                        bullet = new BasicBulletType(3f, 32f) {{
                            width = 12f;
                            height = 16f;
                            lifetime = 60f;
                            trailWidth = 2f;
                            trailLength = 18;
                            status = DustedStatusEffects.deteriorating;
                            statusDuration = 8 * 60f;
                            frontColor = DustedPal.decayingYellow;
                            backColor = trailColor = DustedPal.decayingYellowBack;
                            hitEffect = despawnEffect = DustedFx.hitCavnen;
                        }};
                    }},
                    new Weapon("dusted-lands-decay-mount") {{
                        x = 12f;
                        y = -11f;
                        reload = 80f;
                        rotate = true;
                        shootSound = Sounds.missile;

                        bullet = new BasicBulletType(3f, 32f) {{
                            width = 12f;
                            height = 16f;
                            lifetime = 60f;
                            trailWidth = 2f;
                            trailLength = 18;
                            status = DustedStatusEffects.deteriorating;
                            statusDuration = 8 * 60f;
                            frontColor = DustedPal.decayingYellow;
                            backColor = trailColor = DustedPal.decayingYellowBack;
                            hitEffect = despawnEffect = DustedFx.hitCavnen;
                        }};
                    }}
            );

            abilities.add(new BounceAbility() {{
                bounceDamage = 70f;
                bounceDistance = 180f;
                bounces = 10;
                bounceDelay = 10f;
                bounceCooldown = 320f;
            }});
        }};

        sob = new DustedUnitType("sob") {{
            constructor = UnitEntity::create;
            aiController = ShieldAI::new;
            defaultCommand = UnitCommand.repairCommand;

            accel = 0.1f;
            drag = 0.05f;
            speed = 5.5f;
            flying = true;
            lowAltitude = true;
            rotateSpeed = 8f;
            engineSize = 3.5f;
            engineOffset = 3f;
            healColor = DustedPal.pinkHeal;

            weapons.add(new Weapon("dusted-lands-orb-heal-mount") {{
                x = 3f;
                y = -2f;
                reload = 12f;
                shootSound = Sounds.lasershoot;

                bullet = new BasicBulletType(4f, 0f, "circle-bullet") {{
                    lifetime = 30f;
                    drag = 0.03f;
                    healPercent = 4f;
                    collidesTeam = true;
                    frontColor = Color.white;
                    backColor = healColor = DustedPal.pinkHeal;
                    smokeEffect = hitEffect = despawnEffect = DustedFx.hitPinkLaser;
                }};
            }});

            abilities.add(new DecayShieldAbility(80f));
        }};

        wail = new DustedUnitType("wail") {{
            constructor = UnitEntity::create;
            aiController = FlyingFollowAI::new;
            health = 340f;
            accel = 0.2f;
            drag = 0.1f;
            speed = 2.4f;
            flying = true;
            armor = 4f;
            hitSize = 8f;
            rotateSpeed = 6f;
            engineOffset = 3f;
            engineSize = 4f;
            healColor = DustedPal.pinkHeal;

            abilities.add(
                    new DecayShieldAbility(120f),
                    new RepairFieldAbility(10f, 8f * 60f, 0f) {{
                        activeEffect = DustedFx.pinkHealWaveDynamic;
                        healEffect = DustedFx.pinkHeal;
                    }},
                    new AttractorAbility(0.05f, 20f * 8f)
            );
        }};

        pique = new DustedUnitType("pique") {{
            constructor = MechUnit::create;
            aiController = OrbsAI::new;
            speed = 0.8f;
            hitSize = 7f;
            health = 140f;

            weapons.add(new Weapon("dusted-lands-decay-gun") {{
                y = -1f;
                x = 2.5f;
                reload = 50f;
                shoot = new ShootPattern() {{
                    shots = 3;
                }};
                inaccuracy = 10f;
                shootSound = Sounds.missile;

                bullet = new BasicBulletType(3.6f, 8f, "circle-bullet") {{
                    width = height = 10f;
                    shrinkX = shrinkY = 0.4f;
                    drag = 0.04f;
                    lifetime = 50f;
                    frontColor = Color.white;
                    backColor = DustedPal.pinkHeal;
                    hitEffect = despawnEffect = DustedFx.pinkHeal;
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
            aiController = OrbsAI::new;
            speed = 0.7f;
            hitSize = 10f;
            health = 300f;
            armor = 5f;

            weapons.add(new Weapon("dusted-lands-large-decay-gun") {{
                x = 3;
                y = -2;
                shootY = 6f;
                reload = 40f;
                shoot = new ShootSpread(2, 15f);
                shootSound = Sounds.shootBig;

                bullet = new ShrapnelBulletType() {{
                    length = 60f;
                    damage = 22f;
                    width = 8f;
                    serrations = 8;
                    shootEffect = DustedFx.shootPinkShrapnel;
                    smokeEffect = Fx.shootSmallSmoke;
                    fromColor = Color.white;
                    toColor = DustedPal.pinkHeal;
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
            groundLayer = Layer.legUnit - 1f;
            hovering = true;
            constructor = LegsUnit::create;
            aiController = OrbsAI::new;

            legCount = 4;
            legLength = 18f;
            legForwardScl = 0.5f;
            legBaseOffset = 2f;
            legStraightness = 0.5f;
            legLengthScl = 0.7f;
            legMaxLength = 1.3f;

            weapons.add(new Weapon("") {{
                x = 0f;
                y = 1f;
                mirror = false;
                reload = 110f;
                shootSound = Sounds.artillery;

                bullet = new RocketBulletType(2.6f, 26f) {{
                    width = height = 18f;
                    splashDamage = 16f;
                    splashDamageRadius = 20f;
                    lifetime = 80f;
                    frontColor = Color.white;
                    backColor = trailColor = DustedPal.pinkHeal;
                    trailLength = 18;
                    trailWidth = 4f;
                    shootSound = Sounds.explosion;
                    hitEffect = despawnEffect = DustedFx.pinkHeal;

                    shoot = new ShootSpread(3, 20f);

                    rocketBulletType = new BasicBulletType(2f, 8f) {{
                        width = height = 12f;
                        splashDamage = 14f;
                        splashDamageRadius = 14f;
                        lifetime = 40f;
                        frontColor = Color.white;
                        backColor = DustedPal.pinkHeal;
                        hitEffect = despawnEffect = DustedFx.pinkHeal;
                    }};
                }};
            }});

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

        protei = new DustedUnitType("protei") {{
            constructor = ElevationMoveUnit::create;
            aiController = HoverAI::new;
            hovering = true;
            speed = 2f;
            drag = 0.06f;
            accel = 0.1f;
            health = 160f;
            hitSize = 8f;
            engineSize = 0f;

            abilities.add(new MoveEffectAbility(0f, -6f, Color.white, Fx.missileTrailShort, 4f){{
                teamColor = true;
            }});

            parts.add(
                    new HoverPart() {{
                        x = 2.5f;
                        y = -5.5f;
                        mirror = true;
                        radius = 4f;
                        phase = 90f;
                        stroke = 1.5f;
                        layerOffset = -0.001f;
                        color = Color.valueOf("ecae9e");
                    }},
                    new RegionPart() {{
                        drawRegion = false;
                    }}
            );

            weapons.add(
                    new Weapon() {{
                        mirror = false;
                        alwaysContinuous = true;
                        shootSound = Sounds.tractorbeam;
                        x = 0f;
                        bullet = new ContinuousFlameBulletType() {{
                            damage = 3f;
                            length = 24f;
                            width = 4f;
                            pierceCap = 2;
                            drawFlare = false;
                            colors = new Color[]{Color.valueOf("cd5c5c").a(0.55f), Color.valueOf("cd5c5c").a(0.7f), Color.valueOf("ecae9e").a(0.8f), Color.valueOf("ffe5d3"), Color.white};
                            lightColor = hitColor = Color.valueOf("ecae9e");
                        }};
                    }}
            );
        }};

        EntityMapping.register("dusted-lands-hynobii", FinUnitEntity::new);
        hynobii = new FinUnitType("hynobii") {{
            hovering = true;
            aiController = LiquidPrefAI::new;
            rotateSpeed = 3.6f;
            speed = 1.6f;
            drag = 0.07f;
            accel = 0.09f;
            health = 290f;
            hitSize = 11f;
            engineSize = 0f;
            finScl = 10f;

            fins.add(
                    new FinPart(3f, 3f, 10f, 40f),
                    new FinPart(4.5f, -4f, -10f, 50f)
            );

            parts.add(new RegionPart() {{
                drawRegion = false;
                heatColor = Color.valueOf("cd5c5c");
            }});

            weapons.add(new PointWeapon() {{
                mirror = false;
                alwaysContinuous = true;
                shootSound = Sounds.minebeam;
                shootCone = 360f;
                rotate = true;
                rotateSpeed = 8f;
                aimChangeSpeed = 4f;
                shootY = 0f;
                x = 0f;
                y = 0.5f;

                bullet = new FlamePointLaserBulletType() {{
                    shootEffect = smokeEffect = Fx.none;
                    rangeOverride = 120f;
                    frontRad = 3f;
                    backRad = 5f;
                    color = Color.valueOf("ecae9e");
                    backColor = hitColor = Color.valueOf("cd5c5c");
                    beamEffect = DustedFx.flameSpreadColor;
                    damage = 16f;
                    damageInterval = 15f;
                    status = DustedStatusEffects.blazing;
                    statusDuration = 3f * 60f;
                }};
            }});
        }};

        erode = new DustedUnitType("erode") {{
            constructor = PayloadUnit::create;
            coreUnitDock = true;
            isEnemy = false;
            aiController = BuilderAI::new;
            flying = true;
            itemCapacity = 30;
            alwaysUnlocked = true;
            accel = 0.1f;
            drag = 0.05f;
            speed = 4f;
            mineSpeed = 7f;
            mineTier = 1;
            buildSpeed = 0.6f;
            rotateSpeed = 12f;
            payloadCapacity = 2f * 2f * tilesize * tilesize;

            weapons.add(new Weapon("dusted-lands-core-decay-weapon") {{
                x = 3.1f;
                y = 2f;
                top = false;
                reload = 40f;
                shootSound = Sounds.lasershoot;

                bullet = new BasicBulletType(3f, 10f) {{
                    width = 8f;
                    height = 12f;
                    lifetime = 40f;
                    buildingDamageMultiplier = 0.01f;
                    trailWidth = 2f;
                    trailLength = 16;
                    healPercent = 5f;
                    collidesTeam = true;
                    frontColor = healColor = DustedPal.decayingYellow;
                    backColor = trailColor = DustedPal.decayingYellowBack;
                    hitEffect = despawnEffect = DustedFx.hitCavnen;
                }};
            }});

            abilities.add(new DecayShieldAbility(40f));
        }};

        recede = new DustedUnitType("recede") {{
            constructor = UnitEntity::create;
            aiController = BuilderAI::new;
            flying = true;
            mineSpeed = 7f;
            mineTier = 1;
            buildSpeed = 0.8f;
            drag = 0.05f;
            accel = 0.1f;
            speed = 3.5f;
            rotateSpeed = 16f;
            itemCapacity = 40;
            health = 160f;
            engineOffset = 7f;
            engineSize = 3f;
            hitSize = 8f;
            lowAltitude = true;

            weapons.add(new RepairBeamWeapon("dusted-lands-recede-repair-weapon") {{
                x = 0f;
                y = -2f;
                shootY = 2.5f;
                beamWidth = 0.6f;
                laserColor = DustedPal.decayingYellow;
                mirror = false;
                repairSpeed = 0.7f;

                bullet = new BulletType() {{
                    maxRange = 120f;
                }};
            }});

            abilities.add(new DecayShieldAbility(60f));
        }};

        atrophy = new UnitType("atrophy") {{
            constructor = UnitEntity::create;
            hidden = true;
        }};
    }
}
