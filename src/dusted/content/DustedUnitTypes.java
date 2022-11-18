package dusted.content;

import arc.graphics.*;
import dusted.ai.*;
import dusted.ai.types.*;
import dusted.entities.abilities.*;
import dusted.entities.bullet.*;
import dusted.entities.units.*;
import dusted.graphics.*;
import dusted.type.weapons.*;
import mindustry.*;
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
import mindustry.world.meta.*;

import static dusted.DustedLands.*;

public class DustedUnitTypes {
    public static UnitType
    carom, recur, saltate, staccato,
    sob, wail, snivel,
    annul, excise, deduct, diminish,
    pique, rancor, animus,
    protei, hynobii, sirenid,
    decayedAssemblyDrone, recedeDrone,
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
                    damage = 19f;
                    distance = 100f;
                    shootEffect = DustedFx.shootLaunch;
                    status = StatusEffects.blasted;
                    hitEffect = despawnEffect = DustedFx.hitLaunch;
                }};
            }});
        }};

        excise = new DustedUnitType("excise") {{
            constructor = MechUnit::create;
            speed = 0.7f;
            health = 320f;
            hitSize = 9f;
            armor = 5f;
            targetAir = false;

            weapons.add(new Weapon("dusted-lands-excise-cannon") {{
                mirror = false;
                x = 0f;
                y = -3f;
                reload = 50f;
                recoil = 2f;
                rotate = true;
                rotateSpeed = 4f;
                rotationLimit = 50f;
                shootSound = Sounds.artillery;

                bullet = new ArtilleryBulletType(3.5f, 22f) {{
                    width = height = 12f;
                    lifetime = 45f;
                    frontColor = DustedPal.decayingYellow;
                    backColor = DustedPal.decayingYellowBack;
                    hitEffect = despawnEffect = DustedFx.hitCavnen;
                    status = DustedStatusEffects.deteriorating;
                    statusDuration = 4f * 60f;
                }};
            }});
        }};

        deduct = new DustedUnitType("deduct") {{
            constructor = LegsUnit::create;
            speed = 0.4f;
            health = 820f;
            hitSize = 13f;
            rotateSpeed = 3f;
            armor = 8f;
            groundLayer = Layer.legUnit;
            hovering = true;

            legCount = 4;
            legLength = 18f;
            legForwardScl = 0.5f;
            legBaseOffset = 4f;
            legStraightness = 0.2f;
            legLengthScl = 0.7f;
            legMaxLength = 1.2f;

            weapons.add(new Weapon("dusted-lands-deduct-cannon") {{
                mirror = false;
                x = 0;
                y = -3f;
                reload = 180f;
                recoil = 3f;
                shootSound = DustedSounds.cannonHeavy;

                bullet = new RocketBulletType(4f, 44f, "circle-bullet") {{
                    width = height = 19f;
                    shrinkX = shrinkY = 0.5f;
                    lifetime = 70f;
                    frontColor = DustedPal.decayingYellow;
                    backColor = trailColor = DustedPal.decayingYellowBack;
                    hitEffect = despawnEffect = DustedFx.hitCavnen;
                    shootEffect = DustedFx.shootCavnenSmoke;
                    smokeEffect = Fx.none;
                    status = DustedStatusEffects.deteriorating;
                    trailWidth = 7f;
                    trailLength = 18;
                    statusDuration = 6f * 60f;
                    rocketReload = 10f;
                    rocketDelay = 10f;
                    inaccuracy = 360f;

                    rocketBulletType = new CloudBulletType() {{
                        speed = 0.7f;
                        drag = 0.08f;
                        damage = 15f;
                        damageInterval = 10f;
                        status = DustedStatusEffects.deteriorating;
                        statusDuration = 3f * 60f;
                        fieldEffect = DustedFx.yellowDeteriorating;
                        color = DustedPal.decayingYellow.cpy().a(0.5f);
                        alphaMag = 0f;
                        alphaScl = 0f;
                        fieldRadius = 30f;
                        blobLength = 40f;
                        blobRadius = 10f;
                        blobs = 4;
                        sclTime = 40f;
                        lifetime = 160f;
                        keepVelocity = false;
                    }};
                }};
            }});
        }};

        diminish = new DustedUnitType("diminish") {{
            constructor = LegsUnit::create;
            speed = 0.36f;
            health = 7800f;
            hitSize = 24f;
            rotateSpeed = 1.5f;
            armor = 10f;
            groundLayer = Layer.legUnit;
            hovering = true;
            genCustomIcon = false;

            legCount = 6;
            legLength = 35f;
            legForwardScl = 0.9f;
            legBaseOffset = 15f;
            legStraightness = 0.7f;
            legLengthScl = 0.6f;
            legMaxLength = 1.2f;

            parts.add(new RegionPart("-blade") {{
                moveRot = -5f;
                moveX = -1f;
                moves.add(new PartMove(PartProgress.reload, 2f, -1f, -10f));
                progress = PartProgress.warmup;
                under = true;
                mirror = true;

                children.add(new RegionPart("-side") {{
                    moveX = 1f;
                    moveY = -2f;
                    progress = PartProgress.warmup;
                    under = true;
                    mirror = true;
                    moves.add(new PartMove(PartProgress.reload, 1f, -1f, 0f));
                }});
            }});

            weapons.add(new Weapon() {{
                mirror = false;
                x = 0f;
                y = 3f;
                cooldownTime = 140f;
                reload = 200f;
                shootSound = DustedSounds.cannonHeavy;

                bullet = new BasicBulletType(4f, 44f) {{
                    width = 28f;
                    height = 36f;
                    lifetime = 40f;
                    frontColor = DustedPal.decayingYellow;
                    backColor = trailColor = DustedPal.decayingYellowBack;
                    hitEffect = despawnEffect = DustedFx.hitCavnen;
                    trailWidth = 6f;
                    trailLength = 12;
                    shootEffect = DustedFx.shootCavnenSmokeLarge;
                    smokeEffect = Fx.none;
                    status = DustedStatusEffects.deteriorating;
                    statusDuration = 8f * 60f;
                    hitSound = Sounds.dullExplosion;
                    fragRandomSpread = 0f;
                    fragSpread = 20f;
                    fragBullets = 5;
                    fragVelocityMin = 1f;
                    fragBullet = new BasicBulletType(4f, 18f) {{
                        width = 22f;
                        height = 30f;
                        lifetime = 30f;
                        frontColor = DustedPal.decayingYellow;
                        backColor = trailColor = DustedPal.decayingYellowBack;
                        hitEffect = despawnEffect = DustedFx.hitCavnen;
                        hitSound = Sounds.dullExplosion;
                        trailWidth = 4f;
                        trailLength = 10;
                        status = DustedStatusEffects.deteriorating;
                        fragBullets = 1;
                        fragBullet = new CloudBulletType() {{
                            damage = 10f;
                            damageInterval = 15f;
                            status = DustedStatusEffects.deteriorating;
                            statusDuration = 4f * 60f;
                            fieldEffect = DustedFx.yellowDeteriorating;
                            color = DustedPal.decayingYellow.cpy().a(0.5f);
                            alphaMag = 0f;
                            alphaScl = 0f;
                            sclTime = 60f;
                            lifetime = 240f;
                        }};
                    }};
                }};
            }});
        }};

        carom = new DustedUnitType("carom") {{
            constructor = UnitEntity::create;
            aiController = BounceAI::new;
            speed = 3.6f;
            flying = true;
            lowAltitude = true;
            circleTarget = true;
            health = 120;
            accel = 0.04f;
            drag = 0.03f;
            rotateSpeed = 10f;
            trailLength = 12;
            trailColor = DustedPal.decayingYellow;
            trailScl = 0.6f;

            //sort of like a suicide unit
            decay.ignoreShield.add(this);
            decay.decayMultipliers.put(this, 0.3f);

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
                    backColor = DustedPal.decayingYellowBack;
                    hitEffect = despawnEffect = DustedFx.hitCavnen;
                    status = DustedStatusEffects.deteriorating;
                    statusDuration = 6f * 60f;
                }};
            }});

            abilities.add(new MoveLightningAbility(0f, 0, 1f, 0f, 0.4f, 1.2f, Color.white) {{
                shootEffect = Fx.none;
                shootSound = Sounds.none;
                bullet = new BulletType(0f, 7f) {{
                    instantDisappear = true;
                    hittable = reflectable = absorbable = setDefaults = despawnHit = collidesAir = false;
                    despawnEffect = Fx.none;
                    hitEffect = DustedFx.sliceSparks;
                    //TODO sound
                    hitSound = Sounds.missile;
                    hitSoundVolume = 0.7f;
                }};
            }});
        }};

        recur = new DustedUnitType("recur") {{
            constructor = UnitEntity::create;
            health = 300f;
            armor = 2f;
            speed = 3f;
            accel = 0.1f;
            drag = 0.06f;
            flying = true;
            circleTarget = true;
            hitSize = 8f;
            engineOffset = 8f;
            engineSize = 3f;

            decay.ignoreShield.add(this);
            decay.decayMultipliers.put(this, 0.3f);

            weapons.add(new Weapon() {{
                mirror = false;
                x = 0f;
                y = 4f;
                reload = 15f;
                shoot = new ShootSpread(3, 10f);
                shootSound = Sounds.missile;
                bullet = new BasicBulletType(3.4f, 9f) {{
                    width = 8f;
                    height = 9f;
                    lifetime = 20f;
                    frontColor = DustedPal.decayingYellow;
                    backColor = trailColor = DustedPal.decayingYellowBack;
                    trailWidth = 2f;
                    trailLength = 5;
                    hitEffect = despawnEffect = DustedFx.hitCavnen;
                    status = DustedStatusEffects.deteriorating;
                    statusDuration = 6f * 60f;
                }};
            }});

            abilities.add(new MoveEnhanceAbility(0.8f, 1.4f));
        }};

        saltate = new DustedUnitType("saltate") {{
            constructor = UnitEntity::create;
            aiController = BounceAI::new;
            health = 660f;
            speed = 2f;
            accel = 0.08f;
            drag = 0.03f;
            flying = true;
            lowAltitude = true;
            armor = 4f;
            hitSize = 10f;
            range = 180f;
            engineSize = 0f;

            setEnginesMirror(
                    new UnitEngine(11.25f, -7.5f, 3f, 315f),
                    new UnitEngine(8.75f, -12.5f, 3f, 315f)
            );

            BasicBulletType b = new BasicBulletType(3.6f, 12f, "circle-bullet") {{
                width = height = 12f;
                shrinkX = shrinkY = 0.3f;
                lifetime = 40f;
                frontColor = DustedPal.decayingYellow;
                backColor = DustedPal.decayingYellowBack;
                hitEffect = despawnEffect = DustedFx.hitCavnen;
                status = DustedStatusEffects.deteriorating;
                statusDuration = 8f * 60f;
                hitSound = despawnSound = Sounds.explosion;
                fragBullets = 3;
                fragRandomSpread = 0f;
                fragSpread = 20f;
                fragVelocityMin = 1f;
                fragBullet = new BasicBulletType(4f, 8f) {{
                    width = 6f;
                    height = 10f;
                    lifetime = 30f;
                    frontColor = DustedPal.decayingYellow;
                    backColor = trailColor = DustedPal.decayingYellowBack;
                    hitEffect = despawnEffect = DustedFx.hitCavnen;
                    status = DustedStatusEffects.deteriorating;
                    trailWidth = 2f;
                    trailLength = 7;
                    statusDuration = 3f * 60f;
                }};
            }};

            weapons.add(
                    new Weapon("dusted-lands-saltate-weapon") {{
                        mirror = false;
                        x = 0f;
                        y = -4f;
                        reload = 50f;
                        shoot.shots = 4;
                        shoot.shotDelay = 5f;
                        shootSound = Sounds.missile;
                        bullet = b;
                    }},
                    new Weapon("dusted-lands-saltate-weapon") {{
                        alternate = false;
                        x = 7f;
                        y = -1f;
                        reload = 50f / 2f;
                        shoot.shots = 4;
                        shoot.shotDelay = 5f;
                        shootSound = Sounds.missile;
                        bullet = b;
                    }}
            );

            abilities.add(new BounceAbility() {{
                bounceDamage = 40f;
                bounceDistance = 180f;
                bounceCooldown = 120f;
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
            defaultCommand = DustedUnitCommands.shieldCommand;

            accel = 0.1f;
            drag = 0.05f;
            speed = 5f;
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
            accel = 0.08f;
            drag = 0.06f;
            speed = 3f;
            flying = true;
            armor = 4f;
            hitSize = 8f;
            engineSize = 3f;
            engineOffset = 6f;
            healColor = DustedPal.pinkHeal;

            setEnginesMirror(new UnitEngine(6f, -5f, 2f, 315f));

            abilities.add(
                    new DecayShieldAbility(60f),
                    new ShockwaveAbility()
            );
        }};

        snivel = new DustedUnitType("snivel") {{
            constructor = UnitEntity::create;
            defaultCommand = UnitCommand.assistCommand;
            health = 720f;
            armor = 2f;
            hitSize = 17f;
            speed = 2.6f;
            accel = 0.1f;
            drag = 0.07f;
            engineSize = 0f;
            payloadCapacity = 2f * 2f * Vars.tilePayload;
            buildSpeed = 2f;
            flying = true;
            isEnemy = false;
            drawBuildBeam = false;

            setEnginesMirror(
                    new UnitEngine(39f / 4f, -11f / 4f, 3f, 315f),
                    new UnitEngine(30f / 4f, -33f / 4f, 3f, 315f)
            );

            weapons.add(new BuildWeapon("dusted-lands-snivel-build-weapon") {{
                rotate = true;
                rotateSpeed = 7f;
                x = 6f;
                y = 3f;
                shootY = 4f;
            }});

            abilities.add(new DecayShieldAbility(60f));
        }};

        pique = new DustedUnitType("pique") {{
            constructor = MechUnit::create;
            aiController = ShieldAI::new;
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

                bullet = new BasicBulletType(3.6f, 6f, "circle-bullet") {{
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

            abilities.add(
                    new RevolvingOrbAbility() {{
                        damage = 22f;
                        orbs = 2;
                        radius = 20f;
                        rotationSpeed = 10f;
                        orbRadius = 5f;
                        orbLifetime = 320f;
                        orbCooldown = 200f;
                    }},
                    new DecayShieldAbility(80f)
            );
        }};

        rancor = new DustedUnitType("rancor") {{
            constructor = MechUnit::create;
            aiController = ShieldAI::new;
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

            abilities.add(
                    new RevolvingOrbAbility() {{
                        damage = 30f;
                        orbs = 3;
                        radius = 28f;
                        rotationSpeed = 12f;
                        orbRadius = 7f;
                        orbLifetime = 400f;
                        orbCooldown = 320f;
                    }},
                    new DecayShieldAbility(80f)
            );
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

            legCount = 4;
            legLength = 18f;
            legForwardScl = 0.5f;
            legBaseOffset = 2f;
            legStraightness = 0.5f;
            legLengthScl = 0.7f;
            legMaxLength = 1.3f;

            weapons.add(new Weapon() {{
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
                    color = DustedPal.blazingRed;
                    backColor = hitColor = DustedPal.darkBlazingRed;
                    beamEffect = DustedFx.flameSpreadColor;
                    damage = 16f;
                    damageInterval = 15f;
                    status = DustedStatusEffects.blazing;
                    statusDuration = 3f * 60f;
                }};
            }});
        }};

        EntityMapping.register("dusted-lands-sirenid", FinUnitEntity::new);
        sirenid = new FinUnitType("sirenid") {{
            hovering = true;
            aiController = LiquidPrefAI::new;
            rotateSpeed = 2.4f;
            speed = 1.2f;
            drag = 0.1f;
            accel = 0.12f;
            health = 820f;
            hitSize = 18f;
            engineSize = 0f;
            finScl = 7f;

            fins.add(
                    new FinPart(7f, 6f, 20f, 30f),
                    new FinPart(9f, -0.5f, 0f, 30f),
                    new FinPart(11f, -7f, -10f, 30f)
            );

            weapons.add(new Weapon() {{
                mirror = false;
                x = 0f;
                y = 6f;
                reload = 6f;
                shootCone = 360f;
                bullet = new BulletType(4f, 37f) {{
                    hitSize = 35f;
                    lifetime = 8f;
                    pierce = true;
                    pierceBuilding = true;
                    pierceCap = 4;
                    shootEffect = DustedFx.shootBlazingSpread;
                    hitEffect = DustedFx.hitBlazingSpread;
                    despawnEffect = Fx.none;
                    shootSound = Sounds.flame;
                    statusDuration = 60f * 4;
                    status = DustedStatusEffects.blazing;
                    keepVelocity = false;
                    hittable = false;
                }};
            }});
        }};

        decayedAssemblyDrone = new DustedUnitType("decayed-assembly-drone") {{
            constructor = BuildingTetherPayloadUnit::create;
            controller = u -> new AssemblerAI();
            decay.ignoreShield.add(this);
            decay.decayMultipliers.put(this, 0f);
            flying = true;
            drag = 0.08f;
            accel = 0.1f;
            speed = 1.6f;
            health = 80;
            engineSize = 2f;
            engineOffset = 6f;
            payloadCapacity = 0f;
            targetable = false;
            isEnemy = false;
            hidden = true;
            useUnitCap = false;
            logicControllable = false;
            playerControllable = false;
            allowedInPayloads = false;
            createWreck = false;
            envEnabled = Env.any;
            envDisabled = Env.none;
        }};

        recedeDrone = new DustedUnitType("recede-drone") {{
            constructor = UnitEntity::create;
            controller = u -> new DroneAI();
            flying = true;
            drag = 0.06f;
            accel = 0.1f;
            speed = 4f;
            health = 110;
            targetable = false;
            isEnemy = false;
            hidden = true;
            useUnitCap = false;
            logicControllable = false;
            playerControllable = false;
            allowedInPayloads = false;
            createWreck = false;
            envEnabled = Env.any;
            envDisabled = Env.none;
        }};

        erode = new DustedUnitType("erode") {{
            constructor = PayloadUnit::create;
            coreUnitDock = true;
            isEnemy = false;
            controller = u -> new BuilderAI(true, 500f);
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
            payloadCapacity = 2f * 2f * Vars.tilePayload;

            weapons.add(new Weapon("dusted-lands-core-decay-weapon") {{
                x = 12f / 4f;
                y = 5f / 4f;
                top = false;
                reload = 40f;
                recoil = 1f;
                shootSound = Sounds.lasershoot;

                bullet = new BasicBulletType(3f, 0f) {{
                    width = 8f;
                    height = 12f;
                    lifetime = 40f;
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
            controller = u -> new BuilderAI(true, 500f);
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

            abilities.add(
                    new DecayShieldAbility(60f)
                    //new DroneUnitAbility(recedeDrone, 60f, 0f, 0f)
            );
        }};

        atrophy = new UnitType("atrophy") {{
            constructor = UnitEntity::create;
            hidden = true;
        }};
    }
}
