package dusted.content;

import arc.graphics.*;
import arc.struct.*;
import dusted.entities.bullet.*;
import dusted.graphics.*;
import dusted.type.*;
import dusted.world.blocks.defense.*;
import dusted.world.blocks.defense.turrets.*;
import dusted.world.blocks.distribution.*;
import dusted.world.blocks.environment.*;
import dusted.world.blocks.powder.*;
import dusted.world.blocks.power.*;
import dusted.world.blocks.production.*;
import dusted.world.blocks.storage.*;
import dusted.world.blocks.units.*;
import dusted.world.consumers.*;
import dusted.world.draw.*;
import mindustry.content.*;
import mindustry.entities.bullet.*;
import mindustry.entities.pattern.*;
import mindustry.gen.*;
import mindustry.type.*;
import mindustry.world.*;
import mindustry.world.blocks.defense.*;
import mindustry.world.blocks.defense.turrets.*;
import mindustry.world.blocks.environment.*;
import mindustry.world.blocks.power.*;
import mindustry.world.blocks.storage.*;
import mindustry.world.blocks.units.*;
import mindustry.world.draw.*;
import mindustry.world.meta.*;

// TODO add requirements to blocks, resprite most stuff, make sure everything functions in campaign
public class DustedBlocks {
    public static Block
    //environment
    orePlastel, oreArsenic, pyreol, volcanoZone,
    cavnenSediment, cavnenDusting, volstone, latite, scoria, stradrock, scorchedStradrock,
    cavnenWall, volstoneWall, scoriaWall, latiteWall, stradrockWall,
    //decor
    scoriaBoulder, latiteBoulder, stradrockBoulder,
    volstoneBoulder, cavnenBoulder, volSprout, volTree, weepingShrub, weepingBlossom, charredTree,
    //defense
    zirconWall, zirconWallLarge,
    stasisProjector, decaySuppressor,
    //turrets, TODO needs reworking
    abrade, sunder, bisect, scald, coruscate, evanesce,
    cocaineDuo,
    //distribution
    transferLink, transferTower,
    //powder distribution
    chute, powderRouter, powderJunction, bridgeChute,
    denseChute, armoredChute,
    //power
    powerElectrode, pressureBurner,
    //crafters
    quartzExtractor, metaglassFurnace, siliconForge, pyresinCondenser, telonateForge,
    //production
    thermalExcavator,
    pneumaticFunnel, rotaryFunnel, blastFunnel,
    //cores
    coreAbate, coreDissent, coreDecadence,
    //units
    //TODO maybe use a different system
    cavnenTerraConstructor, cavnenAerialConstructor,
    binaryRestructurer, ternaryRestructurer,
    //sandbox
    powderSource, powderVoid;

    public static void load() {
        //region environment
        volcanoZone = new VolcanoZone("volcano-zone");

        orePlastel = new OreBlock(DustedItems.zircon);

        oreArsenic = new OreBlock(DustedItems.arsenic);

        pyreol = new PowderFloor("pyreol-deposit") {{
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

        volstone = new Floor("volstone");

        scoria = new Floor("scoria");

        latite = new Floor("latite");

        stradrock = new Floor("stradrock");

        scorchedStradrock = new Floor("scorched-stradrock");

        cavnenWall = new StaticWall("cavnen-wall") {{
            cavnenSediment.asFloor().wall = this;
            cavnenDusting.asFloor().wall = this;
        }};

        volstoneWall = new StaticWall("volstone-wall");

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

        volstoneBoulder = new Prop("volstone-boulder") {{
            variants = 2;
            volstone.asFloor().decoration = this;
        }};

        //endregion
        //region crafters

        quartzExtractor = new PowderCrafter("quartz-extractor") {{
            requirements(Category.crafting, ItemStack.with(Items.titanium, 100, DustedItems.zircon, 60, Items.copper, 50));
            hasPower = true;
            size = 2;

            drawer = new DrawMulti(new DrawDefault(), new DrawPowder());
            outputPowder = new PowderStack(DustedPowders.quartzDust, 1f);
            craftTime = 12f;
            consumePower(1f);
            consumeItem(Items.sand, 3);
        }};

        siliconForge = new PowderCrafter("silicon-forge") {{
            requirements(Category.crafting, ItemStack.with(Items.titanium, 130, DustedItems.zircon, 80, Items.graphite, 50));
            size = 3;

            outputItem = new ItemStack(Items.silicon, 3);
            craftTime = 90f;
            craftEffect = Fx.smeltsmoke;
            drawer = new DrawMulti(new DrawDefault(), new DrawFlame(Color.valueOf("ffef99")));
            ambientSound = Sounds.smelter;
            ambientSoundVolume = 0.08f;

            consumePower(0.6f);
            consume(new ConsumePowder(DustedPowders.quartzDust, 0.05f));
            consumeItem(Items.graphite, 2);
        }};

        metaglassFurnace = new PowderCrafter("metaglass-furnace") {{
            requirements(Category.crafting, ItemStack.with());
            size = 3;
            squareSprite = false;

            outputItem = new ItemStack(Items.metaglass, 2);
            craftTime = 60f;
            craftEffect = Fx.smeltsmoke;
            drawer = new DrawMulti(new DrawDefault(), new DrawSpinFlame());
            ambientSound = Sounds.smelter;
            ambientSoundVolume = 0.09f;

            consumePower(0.8f);
            consume(new ConsumePowder(DustedPowders.quartzDust, 0.05f));
            consumeItem(DustedItems.platinum, 2);
        }};

        pyresinCondenser = new PowderCrafter("pyresin-condenser") {{
            requirements(Category.crafting, ItemStack.with());
            hasPower = true;
            size = 3;

            drawer = new DrawMulti(new DrawDefault(), new DrawPowderRotator());
            outputItem = new ItemStack(DustedItems.pyresin, 2);
            craftTime = 50f;
            consumePower(1f);
            consume(new ConsumePowder(DustedPowders.pyreol, 0.1f));
        }};
        //endregion
        //region distribution
        transferLink = new TransferLink("transfer-link") {{
            requirements(Category.distribution, ItemStack.with(DustedItems.zircon, 3));
            squareSprite = false;
        }};

        transferTower = new TransferLink("transfer-tower") {{
            requirements(Category.distribution, ItemStack.with(DustedItems.zircon, 10, Items.graphite, 7, Items.silicon, 5));
            squareSprite = false;
            size = 2;
            linkRange = 160f;
            maxLinks = 10;
            itemCapacity = 20;
        }};
        //endregion
        //region powder
        chute = new Chute("chute") {{
            requirements(Category.distribution, ItemStack.with(DustedItems.zircon, 1));
            health = 40;
        }};

        denseChute = new Chute("dense-chute") {{
            requirements(Category.distribution, ItemStack.with(DustedItems.zircon, 1, DustedItems.arsenic, 2, Items.graphite, 2));
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
            requirements(Category.distribution, ItemStack.with(DustedItems.zircon, 3));
        }};

        powderJunction = new PowderJunction("powder-junction") {{
            requirements(Category.distribution, ItemStack.with(DustedItems.zircon, 3, DustedItems.arsenic, 2));
        }};

        bridgeChute = new PowderBridge("bridge-chute") {{
            requirements(Category.distribution, ItemStack.with(DustedItems.zircon, 8, DustedItems.arsenic, 4));
            range = 4;
            hasPower = false;
        }};
        //endregion
        //region production
        pneumaticFunnel = new Funnel("pneumatic-funnel") {{
            requirements(Category.production, ItemStack.with(DustedItems.zircon, 10));
            funnelAmount = 7f / 60f;
            powderCapacity = 20f;
            squareSprite = false;
        }};

        rotaryFunnel = new Funnel("rotary-funnel") {{
            requirements(Category.production, ItemStack.with(Items.graphite, 40, DustedItems.zircon, 20));
            size = 2;
            hasPower = true;
            powderCapacity = 40f;
            consumePower(0.4f);
            squareSprite = false;
        }};

        blastFunnel = new Funnel("blast-funnel") {{
            requirements(Category.production, ItemStack.with(Items.silicon, 60, DustedItems.pyresin, 50, Items.graphite, 50));
            size = 3;
            funnelAmount = 0.4f;
            hasPower = true;
            powderCapacity = 80f;
            consumePower(1.6f);
            squareSprite = false;
        }};

        thermalExcavator = new Excavator("thermal-excavator") {{
            requirements(Category.production, ItemStack.with());
            size = 2;
        }};
        //endregion
        //region power
        powerElectrode = new PowerNode("power-electrode") {{
            requirements(Category.power, ItemStack.with(Items.graphite, 3, DustedItems.zircon, 1));
            maxNodes = 5;
            laserRange = 3.5f;
            laserColor2 = DustedPal.lightTitanium;
        }};

        pressureBurner = new PowderConsumeGenerator("pressure-burner") {{
            requirements(Category.power, ItemStack.with());
            powerProduction = 1.5f;
            size = 2;
            ambientSound = Sounds.smelter;
            ambientSoundVolume = 0.03f;
            drawer = new DrawMulti(new DrawDefault(), new DrawWarmupRegion());

            consume(new ConsumePowderFlammable(0.2f, 0.2f));
        }};

        //endregion
        //region defense
        zirconWall = new Wall("zircon-wall") {{
            requirements(Category.defense, ItemStack.with(DustedItems.zircon, 6));
            health = 280;
        }};

        zirconWallLarge = new Wall("zircon-wall-large") {{
            requirements(Category.defense, ItemStack.mult(zirconWall.requirements, 4));
            health = 280 * 4;
            size = 2;
        }};

        decaySuppressor = new DecaySuppressor("decay-suppressor") {{
           requirements(Category.effect, ItemStack.with());
           size = 3;
           consumePower(1f);
        }};
        //endregion
        //region turrets
        abrade = new ItemTurret("abrade") {{
            requirements(Category.turret, ItemStack.with());
            size = 2;
            health = 240 * size * size;
            targetGround = false;
            reload = 12f;
            recoil = 4f;
            range = 75f;
            shootSound = Sounds.pew;
            rotateSpeed = 18f;
            outlineColor = DustedPal.darkerWarmMetal;
            drawer = new DrawTurret("decayed-");

            ammo(
                    DustedItems.zircon, new ShrapnelBulletType() {{
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

            coolant = consumeCoolant(0.1f);
        }};

        bisect = new PowderTurret("bisect") {{
            requirements(Category.turret, ItemStack.with());
            size = 2;
            health = 220 * size * size;
            targetAir = false;
            reload = 70f;
            recoil = 1f;
            range = 100f;
            shootSound = Sounds.laser;

            outlineColor = DustedPal.darkerWarmMetal;
            drawer = new DrawTurret("decayed-");

            ammo(
                    DustedPowders.cavnenDust, new SplittingLaserBulletType(90f) {{
                        hitSize = 8f;
                        lifetime = 40f;
                        drawSize = 100f;
                        shootEffect = DustedFx.splitShot;
                        collidesAir = false;
                        length = 100f;
                        ammoMultiplier = 1f;
                    }}
            );

            coolant = consumeCoolant(0.1f);
        }};

        scald = new PowderTurret("scald") {{
            requirements(Category.turret, ItemStack.with());
            size = 2;
            health = 260 * size * size;
            reload = 80f;
            recoil = 3f;
            range = 100f;
            shootEffect = Fx.shootBig2;
            shootSound = Sounds.shootBig;
            targetAir = false;
            outlineColor = DustedPal.darkerWarmMetal;
            drawer = new DrawTurret("decayed-");

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

            coolant = consumeCoolant(0.1f);
        }};

        coruscate = new PowderTurret("coruscate") {{
            requirements(Category.turret, ItemStack.with());
            size = 3;
            health = 280 * size * size;
            reload = 110f;
            recoil = 5f;
            shootSound = Sounds.artillery;
            range = 3f * 70f;
            outlineColor = DustedPal.darkerWarmMetal;
            drawer = new DrawTurret("decayed-");

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
                        hitEffect = despawnEffect = DustedFx.hitQuartz;

                        shoot = new ShootSpread(3, 30f);

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

            coolant = consumeCoolant(0.1f);
        }};
        //endregion
        //region units
        cavnenTerraConstructor = new UnitFactory("cavnen-terra-constructor") {{
            requirements(Category.units, ItemStack.with(Items.copper, 80, DustedItems.zircon, 60, Items.titanium, 50));
            plans = Seq.with(
                    new UnitPlan(DustedUnitTypes.pique, 60f * 15f, ItemStack.with(Items.silicon, 15, DustedItems.arsenic, 10))
            );
            size = 3;
            consumePower(1f);
        }};

        cavnenAerialConstructor = new UnitFactory("cavnen-aerial-constructor") {{
            requirements(Category.units, ItemStack.with(DustedItems.zircon, 60, Items.titanium, 50));
            plans = Seq.with(
                    new UnitPlan(DustedUnitTypes.carom, 60f * 10f, ItemStack.with(Items.silicon, 10, Items.titanium, 10))
            );
            size = 3;
            consumePower(1f);
        }};

        binaryRestructurer = new PowderReconstructor("binary-restructurer") {{
            requirements(Category.units, ItemStack.with(Items.copper, 120, Items.titanium, 90, DustedItems.zircon, 75, Items.thorium, 50));
            size = 3;
            constructTime = 10 * 60f;

            consumePower(2f);
            consumeItems(ItemStack.with(Items.silicon, 50, DustedItems.arsenic, 40));

            upgrades.addAll(
                    new UnitType[]{DustedUnitTypes.carom, DustedUnitTypes.recur},
                    new UnitType[]{DustedUnitTypes.pique, DustedUnitTypes.rancor}
            );
        }};

        ternaryRestructurer = new PowderReconstructor("ternary-restructurer") {{
            requirements(Category.units, ItemStack.with());
            size = 5;

            consumePower(5f);
            consumeItems(ItemStack.with(Items.silicon, 120, Items.titanium, 100, DustedItems.zircon, 60));
            upgrades.addAll(
                    new UnitType[]{DustedUnitTypes.recur, DustedUnitTypes.saltate},
                    new UnitType[]{DustedUnitTypes.rancor, DustedUnitTypes.animus}
            );
        }};
        //endregion
        //region cores
        coreAbate = new ShieldedCoreBlock("core-abate") {{
            requirements(Category.effect, BuildVisibility.editorOnly, ItemStack.with());
            unitType = DustedUnitTypes.erode;
            size = 3;
            health = 2500;
            itemCapacity = 2000;
        }};

        coreDissent = new ShieldedCoreBlock("core-dissent") {{
            requirements(Category.effect, ItemStack.with());
            unitType = DustedUnitTypes.recede;
            size = 4;
            radius = 160f;
        }};

        coreDecadence = new ShieldedCoreBlock("core-decadence") {{
            requirements(Category.effect, ItemStack.with());
            unitType = DustedUnitTypes.atrophy;
            radius = 220f;
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

            shoot = new ShootAlternate(3.5f);

            shootY = 3f;
            reload = 20f;
            range = 110;
            shootCone = 15f;
            ammoUseEffect = Fx.casing1;
            health = 250;
            inaccuracy = 2f;
            rotateSpeed = 10f;
            coolant = consumeCoolant(0.1f);
        }};
        //endregion
    }
}