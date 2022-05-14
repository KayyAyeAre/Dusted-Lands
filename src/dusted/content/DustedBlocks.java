package dusted.content;

import arc.graphics.*;
import arc.struct.*;
import dusted.entities.bullet.*;
import dusted.graphics.*;
import dusted.type.*;
import dusted.world.blocks.defense.turrets.*;
import dusted.world.blocks.environment.*;
import dusted.world.blocks.powder.*;
import dusted.world.blocks.production.*;
import dusted.world.blocks.units.*;
import dusted.world.consumers.*;
import dusted.world.draw.*;
import mindustry.content.*;
import mindustry.entities.bullet.*;
import mindustry.gen.*;
import mindustry.type.*;
import mindustry.world.*;
import mindustry.world.blocks.environment.*;
import mindustry.world.blocks.power.*;
import mindustry.world.blocks.storage.*;
import mindustry.world.blocks.units.*;
import mindustry.world.draw.*;
import mindustry.world.meta.*;

/*
* missing sprites:
* charred tree, crude thermal generator,
* every vacuum, abrade, scald, degrade, additional restructurer,
* every single status effect, pyreol, shirrote, telonate, plastel ore
*/
public class DustedBlocks {
    public static Block
    //environment
    orePlastel, oreShirrote, pyreolDeposit, cavnenSediment, cavnenDusting, volcanGravel, cavnenWall, volcanWall,
    //TODO resprite scoria, the sprites look kinda bad
    scoria, scoriaWall, scoriaBoulder,
    charredTree, volcanFlower, volcanBoulder, cavnenBoulder,
    //walls
    shirroteWall,
    //defense
    spume, abrade, scald, coruscate, degrade, cauterize, pyrexia, evanesce, atrophy, decadence,
    cocaineDuo,
    //powder distribution
    chute, powderRouter, powderJunction, bridgeChute,
    denseChute, armoredChute,
    //power
    powerElectrode, crudeThermalGenerator,
    //crafters
    titaniumMill, quartzExtractor, graphiteCompactor, siliconForge, cafraegenRadiator,
    //production
    pneumaticVacuum, thermalVacuum, blastVacuum,
    //units
    cavnenTerraConstructor, cavnenAerialConstructor,
    additionalRestructurer, triennialRestructurer,
    //sandbox
    powderSource, powderVoid;

    public static void load() {
        orePlastel = new OreBlock(DustedItems.plastel);

        oreShirrote = new OreBlock(DustedItems.shirrote);

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

        cavnenWall = new StaticWall("cavnen-wall") {{
            cavnenSediment.asFloor().wall = this;
            cavnenDusting.asFloor().wall = this;
        }};

        scoria = new Floor("scoria");

        scoriaWall = new StaticWall("scoria-wall") {{
            scoria.asFloor().wall = this;
        }};

        scoriaBoulder = new Prop("scoria-boulder") {{
            variants = 2;
            scoria.asFloor().decoration = this;
        }};

        volcanGravel = new Floor("volcan-gravel");

        volcanWall = new StaticWall("volcan-wall") {{
            volcanGravel.asFloor().wall = this;
        }};

        charredTree = new TreeBlock("charred-tree");

        volcanFlower = new Prop("volcan-flower") {{
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

        chute = new Chute("chute") {{
            requirements(Category.distribution, ItemStack.with(DustedItems.plastel, 2));
            consumes.power(0.5f);
        }};

        powderRouter = new PowderRouter("powder-router") {{
            requirements(Category.distribution, ItemStack.with(DustedItems.plastel, 3));
            consumes.power(0.5f);
        }};

        powderJunction = new PowderJunction("powder-junction") {{
            requirements(Category.distribution, ItemStack.with(DustedItems.plastel, 3, Items.copper, 2));
            consumes.power(0.5f);
        }};

        bridgeChute = new PowderBridge("bridge-chute") {{
            requirements(Category.distribution, ItemStack.with(DustedItems.plastel, 8, Items.copper, 4));
            range = 4;
            hasPower = false;
        }};

        powerElectrode = new PowerNode("power-electrode") {{
            requirements(Category.power, ItemStack.with(Items.graphite, 3, DustedItems.plastel, 1));
            maxNodes = 5;
            laserColor2 = DustedPal.lightTitanium;
        }};

        crudeThermalGenerator = new ThermalGenerator("crude-thermal-generator") {{
            requirements(Category.power, ItemStack.with(Items.copper, 50, DustedItems.plastel, 30));
            powerProduction = 1f;
        }};

        pneumaticVacuum = new Vacuum("pneumatic-vacuum") {{
            requirements(Category.production, ItemStack.with(Items.copper, 20, DustedItems.plastel, 10));
            vacuumAmount = 7f / 60f;
        }};

        thermalVacuum = new Vacuum("thermal-vacuum") {{
            requirements(Category.production, ItemStack.with(Items.graphite, 50, Items.copper, 40, DustedItems.plastel, 20));
            size = 2;
            hasPower = true;
            consumes.power(0.4f);
        }};

        blastVacuum = new Vacuum("blast-vacuum") {{
            requirements(Category.production, ItemStack.with(Items.titanium, 90, Items.graphite, 60, DustedItems.plastel, 50, Items.copper, 60));
            size = 3;
            vacuumAmount = 0.4f;
            hasPower = true;
            consumes.power(1.6f);
        }};

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
            consumes.power(1.5f);
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
                    DustedPowders.titaniumPowder, new BulletType(3.25f, 0) {{
                        hitSize = 8f;
                        lifetime = 18f;
                        pierce = true;
                        shootEffect = DustedFx.shootTitaniumSpray;
                        hitEffect = DustedFx.hitTitaniumSpray;
                        despawnEffect = Fx.none;
                        status = DustedStatusEffects.sprayed;
                        statusDuration = 60f * 3;
                        hittable = false;
                    }}
            );
        }};

        abrade = new PowderTurret("abrade") {{
            requirements(Category.turret, ItemStack.with());
            size = 2;
            health = 240 * size * size;
            targetGround = false;
            reloadTime = 25f;
            shots = 5;
            spread = 25f;
            recoilAmount = 12f;
            restitution = 0.1f;
            range = 50f;
            shootSound = Sounds.shotgun;
            rotateSpeed = 16f;

            ammo(
                    DustedPowders.cavnenDust, new ShrapnelBulletType() {{
                        collidesGround = false;
                        serrations = 6;
                        damage = 18f;
                        width = 20f;
                        length = 50f;
                        toColor = DustedPal.darkCavnen;
                    }}
            );
        }};

        scald = new PowderTurret("scald") {{
            requirements(Category.turret, ItemStack.with());
            size = 2;
            health = 260 * size * size;
            reloadTime = 80f;
            recoilAmount = 14f;
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
                        lifetime = 30f;
                        makeFire = true;

                        fragBullets = 3;
                        fragBullet = new ArtilleryBulletType(3f, 20f) {{
                            width = height = 12f;
                            lifetime = 25f;
                            splashDamageRadius = 18f;
                            splashDamage = 16f;
                            makeFire = true;
                            frontColor = DustedPal.lightQuartz;
                            backColor = DustedPal.darkQuartz;
                            status = StatusEffects.burning;
                            statusDuration = 6 * 60f;
                        }};
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
                    DustedPowders.quartzDust, new RocketBulletType(3f, 40f) {{
                        width = height = 18f;
                        splashDamage = 35f;
                        splashDamageRadius = 22f;
                        lifetime = 70f;
                        frontColor = DustedPal.lightQuartz;
                        backColor = trailColor = DustedPal.darkQuartz;
                        trailLength = 16;
                        trailWidth = 6f;
                        status = StatusEffects.burning;
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
                            makeFire = true;
                            status = StatusEffects.burning;
                            statusDuration = 3 * 60f;
                        }};
                    }}
            );
        }};

        pyrexia = new PowderTurret("pyrexia") {{
            requirements(Category.turret, ItemStack.with());
            size = 3;
            health = 280 * size * size;
            range = 460f;
            reloadTime = 160f;
            recoilAmount = 8f;
            shootSound = Sounds.shootBig;
            shots = 3;
            spread = 10f;

            ammo(
                    DustedPowders.titaniumPowder, new BasicBulletType(3.6f, 28f) {{
                        width = 14f;
                        height = 17f;
                        splashDamage = 18f;
                        splashDamageRadius = 16f;
                        homingPower = 0.12f;
                        pierce = true;
                        pierceCap = 5;
                        lifetime = 130f;
                        frontColor = DustedPal.lightTitanium;
                        backColor = trailColor = DustedPal.darkTitanium;
                        trailWidth = 5f;
                        trailLength = 20;
                        status = StatusEffects.burning;
                        statusDuration = 6 * 60f;
                    }}
            );
        }};

        degrade = new PowderTurret("degrade") {{
            requirements(Category.turret, ItemStack.with());
            size = 3;
            health = 290 * size * size;
            reloadTime = 8f;
            recoilAmount = 3f;
            shots = 3;
            inaccuracy = 25f;
            shootSound = Sounds.missile;
            targetGround = false;
            range = 45f;
            rotateSpeed = 14f;

            ammo(
                    DustedPowders.cavnenDust, new BasicBulletType(4f, 9f, "circle-bullet") {{
                        width = height = 15f;
                        shrinkX = shrinkY = 0.4f;
                        lifetime = 40f;
                        collidesGround = false;
                        frontColor = DustedPal.cavnenYellow;
                        backColor = DustedPal.cavnenYellowBack;
                        drag = 0.08f;
                        splashDamage = 14f;
                        splashDamageRadius = 12f;
                        status = DustedStatusEffects.deteriorating;
                        statusDuration = 8 * 60f;
                    }}
            );
        }};

        cauterize = new PowderTurret("cauterize") {{
            requirements(Category.turret, ItemStack.with());
            size = 3;
            health = 300 * size * size;
            reloadTime = 120f;
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

        cavnenAerialConstructor = new UnitFactory("cavnen-aerial-constructor") {{
            requirements(Category.units, ItemStack.with(Items.copper, 80, DustedItems.plastel, 60, Items.titanium, 50));
            plans = Seq.with(
                    new UnitPlan(DustedUnitTypes.carom, 60f * 15f, ItemStack.with(DustedItems.plastel, 10, Items.titanium, 10))
            );
            size = 3;
            consumes.power(1f);
        }};

        additionalRestructurer = new PowderReconstructor("additional-restructurer") {{
            requirements(Category.units, ItemStack.with(Items.copper, 120, Items.titanium, 90, DustedItems.plastel, 75, Items.thorium, 50));
            size = 3;

            consumes.power(2f);
            consumes.items(ItemStack.with(Items.silicon, 50, DustedItems.plastel, 40));
            new ConsumePowder(DustedPowders.cafraegen, 0.4f).add(consumes);
            upgrades.addAll(new UnitType[]{DustedUnitTypes.carom, DustedUnitTypes.recur});
        }};

        triennialRestructurer = new PowderReconstructor("triennial-restructurer") {{
            requirements(Category.units, ItemStack.with());
            size = 5;

            consumes.power(5f);
            consumes.items(ItemStack.with(Items.silicon, 120, Items.titanium, 100, DustedItems.plastel, 60));
            upgrades.addAll(new UnitType[]{DustedUnitTypes.recur, DustedUnitTypes.saltate});
        }};

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
    }
}