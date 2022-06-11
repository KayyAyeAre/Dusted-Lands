package dusted.content;

import arc.graphics.*;
import arc.struct.*;
import dusted.entities.bullet.*;
import dusted.graphics.*;
import dusted.type.*;
import dusted.world.blocks.defense.*;
import dusted.world.blocks.defense.turrets.*;
import dusted.world.blocks.environment.*;
import dusted.world.blocks.powder.*;
import dusted.world.blocks.power.*;
import dusted.world.blocks.production.*;
import dusted.world.blocks.units.*;
import dusted.world.consumers.*;
import dusted.world.draw.*;
import mindustry.content.*;
import mindustry.entities.bullet.*;
import mindustry.gen.*;
import mindustry.type.*;
import mindustry.world.*;
import mindustry.world.blocks.defense.*;
import mindustry.world.blocks.environment.*;
import mindustry.world.blocks.power.*;
import mindustry.world.blocks.storage.*;
import mindustry.world.blocks.units.*;
import mindustry.world.draw.*;
import mindustry.world.meta.*;

public class DustedBlocks {
    public static Block
    //environment
    orePlastel, oreArsenic, pyreolDeposit, volcanicSlag, volcanoZone,
    cavnenSediment, cavnenDusting, volcanGravel, latite, scoria, stradrock, scorchedStradrock,
    cavnenWall, volcanWall, scoriaWall, latiteWall, stradrockWall,
    //decor
    scoriaBoulder, latiteBoulder, stradrockBoulder,
    volcanBoulder, cavnenBoulder, volSprout, volTree, weepingShrub, weepingBlossom, charredTree,
    //defense
    plastelWall, plastelWallLarge,
    stasisProjector,
    //turrets
    rive, abrade, sunder, redox, bisect, scald, spume, coruscate, cauterize, evanesce,
    cocaineDuo,
    //powder distribution
    chute, powderRouter, powderJunction, bridgeChute,
    denseChute, armoredChute,
    //power
    powerElectrode, pressureBurner,
    //crafters
    titaniumMill, quartzExtractor, graphiteCompactor, siliconForge, pyresinCondenser, cafraegenRadiator, telonateForge,
    //production
    pneumaticFunnel, rotaryFunnel, blastFunnel,
    //cores
    coreAbate, coreDissent, coreDecadence,
    //units
    cavnenTerraConstructor, cavnenAerialConstructor,
    binaryRestructurer, ternaryRestructurer,
    //sandbox
    powderSource, powderVoid;

    public static void load() {
        //region environment
        volcanoZone = new VolcanoZone("volcano-zone");

        orePlastel = new OreBlock(DustedItems.plastel);

        oreArsenic = new OreBlock(DustedItems.arsenic);

        pyreolDeposit = new PowderFloor("pyreol-deposit") {{
            powderDrop = DustedPowders.pyreol;
            attributes.set(Attribute.water, -0.7f);
        }};

        cavnenSediment = new Floor("cavnen-sediment") {{
            attributes.set(Attribute.oil, 1.2f);
            attributes.set(Attribute.water, -0.6f);
        }};

        cavnenDusting = new PowderFloor("cavnen-dusting") {{
            powderDrop = DustedPowders.cavnenDust;
            attributes.set(Attribute.oil, 0.9f);
            attributes.set(Attribute.water, -0.65f);
        }};

        volcanGravel = new Floor("volcan-gravel");

        scoria = new Floor("scoria");

        latite = new Floor("latite");

        stradrock = new Floor("stradrock");

        scorchedStradrock = new Floor("scorched-stradrock");

        cavnenWall = new StaticWall("cavnen-wall") {{
            cavnenSediment.asFloor().wall = this;
            cavnenDusting.asFloor().wall = this;
        }};

        volcanWall = new StaticWall("volcan-wall") {{
            volcanGravel.asFloor().wall = this;
        }};

        scoriaWall = new StaticWall("scoria-wall");

        latiteWall = new StaticWall("latite-wall");

        stradrockWall = new StaticWall("stradrock-wall") {{
            scorchedStradrock.asFloor().wall = this;
        }};

        scoriaBoulder = new Prop("scoria-boulder") {{
            variants = 2;
            scoria.asFloor().decoration = this;
        }};

        latiteBoulder = new Prop("latite-boulder") {{
            variants = 2;
            latite.asFloor().decoration = this;
        }};

        stradrockBoulder = new Prop("stradrock-boulder") {{
            variants = 2;
            stradrock.asFloor().decoration = this;
        }};

        charredTree = new TreeBlock("charred-tree");

        volSprout = new Prop("vol-sprout") {{
            variants = 2;
        }};

        cavnenBoulder = new Prop("cavnen-boulder") {{
            variants = 2;
            cavnenSediment.asFloor().decoration = cavnenDusting.asFloor().decoration = this;
        }};

        volcanBoulder = new Prop("volcan-boulder") {{
            variants = 2;
            volcanGravel.asFloor().decoration = this;
        }};

        //endregion
        //region powder
        chute = new Chute("chute") {{
            requirements(Category.distribution, ItemStack.with(DustedItems.plastel, 1));
            health = 40;
        }};

        denseChute = new Chute("dense-chute") {{
            requirements(Category.distribution, ItemStack.with(DustedItems.plastel, 1, DustedItems.arsenic, 2, Items.titanium, 2));
            powderPressure = 1.03f;
            powderCapacity = 16f;
            health = 100;
        }};

        armoredChute = new ArmoredChute("armored-chute") {{
            requirements(Category.distribution, ItemStack.with(DustedItems.arsenic, 2, DustedItems.pyresin, 3));
            powderPressure = 1.03f;
            powderCapacity = 16f;
            health = 220;
        }};

        powderRouter = new PowderRouter("powder-router") {{
            requirements(Category.distribution, ItemStack.with(DustedItems.plastel, 3));
        }};

        powderJunction = new PowderJunction("powder-junction") {{
            requirements(Category.distribution, ItemStack.with(DustedItems.plastel, 3, Items.copper, 2));
        }};

        bridgeChute = new PowderBridge("bridge-chute") {{
            requirements(Category.distribution, ItemStack.with(DustedItems.plastel, 8, Items.copper, 4));
            range = 4;
            hasPower = false;
        }};

        pneumaticFunnel = new Funnel("pneumatic-funnel") {{
            requirements(Category.production, ItemStack.with(Items.copper, 20, DustedItems.plastel, 10));
            funnelAmount = 7f / 60f;
            powderCapacity = 20f;
            squareSprite = false;
        }};

        rotaryFunnel = new Funnel("rotary-funnel") {{
            requirements(Category.production, ItemStack.with(Items.graphite, 50, Items.copper, 40, DustedItems.plastel, 20));
            size = 2;
            hasPower = true;
            powderCapacity = 40f;
            consumes.power(0.4f);
            squareSprite = false;
        }};

        blastFunnel = new Funnel("blast-funnel") {{
            requirements(Category.production, ItemStack.with(Items.titanium, 90, Items.graphite, 60, DustedItems.plastel, 50, Items.copper, 60));
            size = 3;
            funnelAmount = 0.4f;
            hasPower = true;
            powderCapacity = 80f;
            consumes.power(1.6f);
            squareSprite = false;
        }};
        //endregion
        //region power
        powerElectrode = new PowerNode("power-electrode") {{
            requirements(Category.power, ItemStack.with(Items.graphite, 3, DustedItems.plastel, 1));
            maxNodes = 5;
            laserRange = 3.5f;
            laserColor2 = DustedPal.lightTitanium;
        }};

        pressureBurner = new PowderBurnerGenerator("pressure-burner") {{
            requirements(Category.power, ItemStack.with());
            powerProduction = 1.5f;
            size = 2;
            ambientSound = Sounds.smelter;
            ambientSoundVolume = 0.03f;
        }};

        //endregion
        //region crafters
        graphiteCompactor = new PowderCrafter("graphite-compactor") {{
            requirements(Category.crafting, ItemStack.with(Items.copper, 70, DustedItems.plastel, 50));
            size = 2;

            outputItem = new ItemStack(Items.graphite, 2);
            craftTime = 60f;
            hasItems = true;

            new ConsumePowder(DustedPowders.cavnenDust, 0.1f).add(consumes);
        }};

        quartzExtractor = new PowderCrafter("quartz-extractor") {{
            requirements(Category.crafting, ItemStack.with(Items.titanium, 100, DustedItems.plastel, 60, Items.copper, 50));
            hasPower = true;
            size = 2;

            drawer = new DrawPowderMixer();
            outputPowder = new PowderStack(DustedPowders.quartzDust, 1f);
            craftTime = 12f;
            consumes.power(1f);
            consumes.item(Items.sand, 3);
        }};

        titaniumMill = new PowderCrafter("titanium-mill") {{
            requirements(Category.crafting, ItemStack.with(Items.graphite, 80, DustedItems.plastel, 60));
            hasPower = true;
            size = 2;
            drawer = new DrawPowderRotator();
            outputPowder = new PowderStack(DustedPowders.titaniumPowder, 1f);
            craftTime = 10f;
            consumes.power(2f);
            consumes.item(Items.titanium, 2);
        }};

        siliconForge = new PowderCrafter("silicon-forge") {{
            requirements(Category.crafting, ItemStack.with(Items.titanium, 130, DustedItems.plastel, 80, Items.graphite, 50));
            hasPower = true;
            hasItems = true;
            size = 3;

            outputItem = new ItemStack(Items.silicon, 2);
            craftTime = 90f;
            drawer = new DrawSmelter(Color.valueOf("ffef99"));
            ambientSound = Sounds.smelter;
            ambientSoundVolume = 0.08f;

            consumes.power(1.2f);
            new ConsumePowder(DustedPowders.quartzDust, 0.05f).add(consumes);
        }};

        pyresinCondenser = new PowderCrafter("pyresin-condenser") {{
            requirements(Category.crafting, ItemStack.with());
            hasPower = true;
            size = 3;

            drawer = new DrawPowderRotator();
            outputItem = new ItemStack(DustedItems.pyresin, 2);
            craftTime = 50f;
            consumes.power(1f);
            new ConsumePowder(DustedPowders.pyreol, 0.1f).add(consumes);
        }};

        cafraegenRadiator = new PowderCrafter("cafraegen-radiator") {{
            requirements(Category.crafting, ItemStack.with());
            hasPower = true;
            hasItems = true;
            size = 3;
            outputPowder = new PowderStack(DustedPowders.cafraegen, 1f);
            craftTime = 15f;
            drawer = new DrawSmelter(Color.valueOf("9ef4ef")) {{
                flameRadius = 6f;
                flameRadiusIn = 4f;
            }};

            consumes.power(2.5f);
            consumes.item(Items.thorium, 3);
            new ConsumePowder(DustedPowders.quartzDust, 0.2f).add(consumes);
        }};
        //endregion
        //region defense
        plastelWall = new Wall("plastel-wall") {{
            requirements(Category.defense, ItemStack.with(DustedItems.plastel, 6));
            health = 280;
        }};

        plastelWallLarge = new Wall("plastel-wall-large") {{
            requirements(Category.defense, ItemStack.mult(plastelWall.requirements, 4));
            health = 280 * 4;
            size = 2;
        }};

        stasisProjector = new StasisProjector("stasis-projector") {{
            requirements(Category.effect, ItemStack.with());
            size = 3;
            consumes.power(4f);
        }};
        //endregion
        //region turrets
        rive = new PowderTurret("rive") {{
            requirements(Category.turret, ItemStack.with());
            health = 240;
            reloadTime = 25f;
            rotateSpeed = 12f;
            recoilAmount = 3f;

            ammo(
                    DustedPowders.cavnenDust, new BasicBulletType(4f, 10f) {{
                        width = 6f;
                        height = 12f;
                        shrinkX = 0.3f;
                        shrinkY = 0.1f;
                        lifetime = 40f;
                        pierce = true;
                        trailChance = 0.2f;
                        frontColor = DustedPal.cavnenYellow;
                        backColor = trailColor = DustedPal.cavnenYellowBack;
                    }}
            );
        }};

        abrade = new PowderTurret("abrade") {{
            requirements(Category.turret, ItemStack.with());
            size = 2;
            health = 240 * size * size;
            targetGround = false;
            reloadTime = 12f;
            recoilAmount = 4f;
            restitution = 0.1f;
            range = 75f;
            shootSound = Sounds.pew;
            rotateSpeed = 18f;

            ammo(
                    DustedPowders.cavnenDust, new ShrapnelBulletType() {{
                        collidesGround = false;
                        hitEffect = Fx.hitBulletSmall;
                        smokeEffect = Fx.shootSmallSmoke;
                        shootEffect = DustedFx.shootCavnenShrapnel;
                        status = DustedStatusEffects.deteriorating;
                        statusDuration = 8 * 60f;
                        fromColor = DustedPal.cavnenYellow;
                        toColor = DustedPal.cavnenYellowBack;
                        length = 75f;
                        damage = 15f;
                        width = 16f;
                        serrations = 0;
                    }}
            );
        }};

        bisect = new PowderTurret("bisect") {{
            requirements(Category.turret, ItemStack.with());
            size = 2;
            health = 220 * size * size;
            targetAir = false;
            reloadTime = 70f;
            recoilAmount = 1f;
            restitution = 0.04f;
            range = 100f;
            shootSound = Sounds.laser;

            ammo(
                    DustedPowders.cavnenDust, new SplittingLaserBulletType(90f) {{
                        hitSize = 8f;
                        lifetime = 40f;
                        drawSize = 100f;
                        shootEffect = DustedFx.bisectShoot;
                        collidesAir = false;
                        length = 100f;
                        ammoMultiplier = 1f;
                    }}
            );
        }};

        scald = new PowderTurret("scald") {{
            requirements(Category.turret, ItemStack.with());
            size = 2;
            health = 260 * size * size;
            reloadTime = 80f;
            recoilAmount = 3f;
            restitution = 0.1f;
            range = 100f;
            shootEffect = Fx.shootBig2;
            shootSound = Sounds.shootBig;
            targetAir = false;

            ammo(
                    DustedPowders.quartzDust, new BasicBulletType(3.5f, 30f) {{
                        collidesAir = false;
                        width = height = 15f;
                        splashDamageRadius = 20f;
                        splashDamage = 24f;
                        status = StatusEffects.burning;
                        statusDuration = 8 * 60f;
                        frontColor = DustedPal.lightQuartz;
                        backColor = DustedPal.darkQuartz;
                        shootEffect = DustedFx.shootQuartz;
                        hitEffect = DustedFx.hitQuartz;
                        lifetime = 30f;
                        makeFire = true;

                        fragBullets = 3;
                        fragBullet = new ArtilleryBulletType(3f, 20f) {{
                            width = height = 12f;
                            lifetime = 25f;
                            splashDamageRadius = 18f;
                            splashDamage = 16f;
                            makeFire = true;
                            hitEffect = DustedFx.hitQuartz;
                            frontColor = DustedPal.lightQuartz;
                            backColor = DustedPal.darkQuartz;
                            status = StatusEffects.burning;
                            statusDuration = 6 * 60f;
                        }};
                    }}
            );
        }};

        spume = new PowderTurret("spume") {{
            requirements(Category.turret, ItemStack.with());
            size = 2;
            recoilAmount = 0f;
            reloadTime = 4f;
            coolantMultiplier = 1.5f;
            range = 70f;
            shootCone = 50f;
            ammoUseEffect = Fx.none;
            health = 260 * size * size;
            loopSound = Sounds.spray;
            shootSound = Sounds.none;

            ammo(
                    DustedPowders.titaniumPowder, new BulletType(3.25f, 6f) {{
                        hitSize = 8f;
                        lifetime = 18f;
                        pierce = true;
                        shootEffect = DustedFx.shootTitaniumSpray;
                        hitEffect = DustedFx.hitTitaniumSpray;
                        despawnEffect = Fx.none;
                        status = StatusEffects.burning;
                        statusDuration = 60f * 3;
                        hittable = false;
                    }}
            );
        }};

        coruscate = new PowderTurret("coruscate") {{
            requirements(Category.turret, ItemStack.with());
            size = 3;
            health = 280 * size * size;
            reloadTime = 110f;
            recoilAmount = 5f;
            shootSound = Sounds.artillery;
            range = 3f * 70f;

            ammo(
                    DustedPowders.quartzDust, new RocketBulletType(3f, 36f) {{
                        width = height = 18f;
                        splashDamage = 35f;
                        splashDamageRadius = 22f;
                        lifetime = 70f;
                        frontColor = DustedPal.lightQuartz;
                        backColor = trailColor = DustedPal.darkQuartz;
                        trailLength = 16;
                        trailWidth = 4f;
                        status = StatusEffects.burning;
                        shootEffect = DustedFx.shootQuartz;
                        statusDuration = 16 * 60f;
                        rockets = 3;
                        rocketSpread = 30f;
                        shootSound = Sounds.explosion;

                        rocketBulletType = new BasicBulletType(1.6f, 16f) {{
                            width = height = 12f;
                            splashDamage = 12f;
                            splashDamageRadius = 18f;
                            lifetime = 30f;
                            frontColor = DustedPal.lightQuartz;
                            backColor = DustedPal.darkQuartz;
                            shootEffect = DustedFx.shootQuartz;
                            hitEffect = despawnEffect = DustedFx.hitQuartz;
                            makeFire = true;
                            status = StatusEffects.burning;
                            statusDuration = 3 * 60f;
                        }};
                    }}
            );
        }};

        cauterize = new PowderTurret("cauterize") {{
            requirements(Category.turret, ItemStack.with());
            size = 3;
            health = 300 * size * size;
            reloadTime = 120f;
            recoilAmount = 6f;
            shots = 3;
            spread = 20f;
            shootSound = Sounds.shootBig;

            ammo(
                    DustedPowders.quartzDust, new RocketBulletType(3.8f, 50f) {{
                        rockets = 5;
                        width = height = 14f;
                        frontColor = DustedPal.lightQuartz;
                        backColor = trailColor = DustedPal.darkQuartz;
                        shootSound = Sounds.flame;
                        trailLength = 24;
                        trailWidth = 6f;
                        makeFire = true;
                        splashDamage = 25f;
                        splashDamageRadius = 14f;
                        status = StatusEffects.burning;
                        statusDuration = 10 * 60f;
                        rocketSpread = 15f;

                        rocketBulletType = new BulletType(4f, 20f) {{
                            hitSize = 8f;
                            lifetime = 20f;
                            pierce = true;
                            status = StatusEffects.burning;
                            statusDuration = 6 * 60f;
                            shootEffect = DustedFx.shootQuartzFlame;
                            hitEffect = DustedFx.hitQuartzFlame;
                            despawnEffect = Fx.none;
                            hittable = false;
                        }};
                    }}
            );
        }};
        //endregion
        //region units
        cavnenTerraConstructor = new UnitFactory("cavnen-terra-constructor") {{
            requirements(Category.units, ItemStack.with(Items.copper, 80, DustedItems.plastel, 60, Items.titanium, 50));
            plans = Seq.with(
                    new UnitPlan(DustedUnitTypes.pique, 60f * 15f, ItemStack.with(Items.silicon, 15, DustedItems.arsenic, 10))
            );
            size = 3;
            consumes.power(1f);
        }};

        cavnenAerialConstructor = new UnitFactory("cavnen-aerial-constructor") {{
            requirements(Category.units, ItemStack.with(DustedItems.plastel, 60, Items.titanium, 50));
            plans = Seq.with(
                    new UnitPlan(DustedUnitTypes.carom, 60f * 10f, ItemStack.with(Items.silicon, 10, Items.titanium, 10))
            );
            size = 3;
            consumes.power(1f);
        }};

        binaryRestructurer = new PowderReconstructor("binary-restructurer") {{
            requirements(Category.units, ItemStack.with(Items.copper, 120, Items.titanium, 90, DustedItems.plastel, 75, Items.thorium, 50));
            size = 3;
            constructTime = 10 * 60f;

            consumes.power(2f);
            consumes.items(ItemStack.with(Items.silicon, 50, DustedItems.arsenic, 40));

            upgrades.addAll(
                    new UnitType[]{DustedUnitTypes.carom, DustedUnitTypes.recur},
                    new UnitType[]{DustedUnitTypes.pique, DustedUnitTypes.rancor}
            );
        }};

        ternaryRestructurer = new PowderReconstructor("ternary-restructurer") {{
            requirements(Category.units, ItemStack.with());
            size = 5;

            consumes.power(5f);
            consumes.items(ItemStack.with(Items.silicon, 120, Items.titanium, 100, DustedItems.plastel, 60));
            upgrades.addAll(new UnitType[]{DustedUnitTypes.recur, DustedUnitTypes.saltate});
        }};
        //endregion
        //region cores
        coreAbate = new CoreBlock("core-abate") {{
            requirements(Category.effect, BuildVisibility.editorOnly, ItemStack.with());
            unitType = DustedUnitTypes.erode;
            size = 3;
            health = 2500;
            itemCapacity = 2000;
        }};

        coreDissent = new CoreBlock("core-dissent") {{
            requirements(Category.effect, ItemStack.with());
            unitType = DustedUnitTypes.recede;
            size = 4;
        }};

        coreDecadence = new CoreBlock("core-decadence") {{
            requirements(Category.effect, ItemStack.with());
            unitType = DustedUnitTypes.atrophy;
        }};

        //endregion
        //region sandbox
        powderSource = new PowderSource("powder-source") {{
            requirements(Category.distribution, BuildVisibility.sandboxOnly, ItemStack.with());
        }};

        powderVoid = new PowderVoid("powder-void") {{
            requirements(Category.distribution, BuildVisibility.sandboxOnly, ItemStack.with());
        }};

        cocaineDuo = new PowderTurret("cocaine-duo") {{
            requirements(Category.turret, BuildVisibility.sandboxOnly, ItemStack.with());
            ammo(
                    DustedPowders.cocaine, new BasicBulletType(2.5f, 1000000f) {{
                        frontColor = Color.white;
                        backColor = Color.lightGray;
                        width = 7f;
                        height = 9f;
                        lifetime = 44.4f;
                        shootEffect = Fx.shootSmall;
                        smokeEffect = Fx.shootSmallSmoke;
                        ammoMultiplier = 2;
                    }}
            );

            spread = 4f;
            shots = 2;
            alternate = true;
            reloadTime = 20f;
            restitution = 0.03f;
            range = 110;
            shootCone = 15f;
            ammoUseEffect = Fx.casing1;
            shootSound = Sounds.rockBreak;
            health = 250;
            inaccuracy = 2f;
            rotateSpeed = 10f;
        }};
        //endregion
    }
}