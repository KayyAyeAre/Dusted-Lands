package dusted.content;

import arc.graphics.*;
import arc.math.*;
import arc.struct.*;
import dusted.*;
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
import dusted.world.meta.*;
import mindustry.content.*;
import mindustry.entities.bullet.*;
import mindustry.entities.effect.*;
import mindustry.entities.part.*;
import mindustry.entities.pattern.*;
import mindustry.gen.*;
import mindustry.graphics.*;
import mindustry.type.*;
import mindustry.world.*;
import mindustry.world.blocks.defense.*;
import mindustry.world.blocks.defense.turrets.*;
import mindustry.world.blocks.environment.*;
import mindustry.world.blocks.production.*;
import mindustry.world.draw.*;
import mindustry.world.meta.*;

//TODO balancing, campaign stuff
public class DustedBlocks {
    public static Block
    //environment
    oreZircon, oreArsenic, oreAntimony, orePlatinum, orchar, sulfur, niter, volcanoZone,
    fallenStone, fallenMantle, warpedStone, decayedRock, riftRock, cavnenSediment, cavnenDusting, cavnenSilk,
    volstone, latite, scoria, stradrock, scorchedStradrock,
    basaltFumarole, volstoneFumarole, scoriaFumarole, latiteFumarole,
    fallenWall, decayedWall, cavnenWall, volstoneWall, scoriaWall, latiteWall, stradrockWall,
    //decor
    scoriaBoulder, latiteBoulder, stradrockBoulder,
    fallenBoulder, decayedBoulder, cavnenBoulder,
    volstoneBoulder, volSprout, weepingShrub,
    //defense
    zirconWall, antimonyWall, crisaltWall, perisleWall,
    zirconWallLarge, antimonyWallLarge, crisaltWallLarge, perisleWallLarge,
    decaySuppressor, regenerationTower,
    //turrets
    abrade, sunder, scald, coruscate, strike, clutter, blight, crush,
    cocaineDuo,
    //distribution
    transferLink, transferTower,
    //powder distribution
    chute, powderRouter, powderJunction, bridgeChute,
    denseChute, armoredChute,
    //liquids
    refractoryPump, liquidLink,
    //power
    magmaticGenerator, riftDischarger, crystalConcentrator,
    //crafters
    quartzExtractor, metaglassFurnace, siliconForge, rockwoolExtruder, crisaltSynthesizer, gunpowderMill, deteriorationChamber,
    //production
    pressureDrill, ignitionDrill,
    pneumaticFunnel, rotaryFunnel,
    sulfurSiphon, hydrogenSiphon,
    //cores
    coreAbate, coreDissent, coreDecadence,
    //units
    witheredAssembler, voltaicAssembler, blazingAssembler,
    largeWitheredAssembler, largeVoltaicAssembler, largeBlazingAssembler,
    aerialAssemblerModule, binaryAssemblerModule, ternaryAssemblerModule,
    //sandbox
    powderSource, powderVoid;

    public static void load() {
        //is this a good idea?
        Blocks.conduit.envDisabled |= DustedEnv.volcanic;

        //region environment
        volcanoZone = new VolcanoZone("volcano-zone");

        oreZircon = new OreBlock(DustedItems.zircon);

        oreArsenic = new OreBlock(DustedItems.arsenic);

        oreAntimony = new OreBlock(DustedItems.antimony);

        orePlatinum = new OreBlock(DustedItems.platinum);

        //TODO maybe dont use this for every single powder
        orchar = new PowderFloor("orchar-deposit") {{
            powderDrop = DustedPowders.orchar;
            attributes.set(Attribute.water, -0.7f);
        }};

        sulfur = new PowderFloor("sulfur-deposit") {{
            powderDrop = DustedPowders.sulfur;
            attributes.set(Attribute.water, -0.5f);
        }};

        niter = new PowderFloor("niter-deposit") {{
            powderDrop = DustedPowders.niter;
            attributes.set(Attribute.water, -0.5f);
        }};

        cavnenSediment = new Floor("cavnen-sediment") {{
            attributes.set(Attribute.oil, 1.2f);
            attributes.set(Attribute.water, -0.6f);
            attributes.set(DustedAttribute.decay, 0.25f);
        }};

        cavnenDusting = new Floor("cavnen-dusting") {{
            attributes.set(Attribute.oil, 0.9f);
            attributes.set(Attribute.water, -0.65f);
            attributes.set(DustedAttribute.decay, 0.25f);
        }};

        cavnenSilk = new Floor("cavnen-silk") {{
            drownTime = 360f;
            status = DustedStatusEffects.deteriorating;
            statusDuration = 3f * 60f;
            speedMultiplier = 0.1f;
            variants = 0;
            cacheLayer = DustedShaders.silkLayer;
            attributes.set(DustedAttribute.decay, 1f);
            attributes.set(DustedAttribute.decayEnergy, 1f);
            albedo = 0.7f;
        }};

        decayedRock = new Floor("decayed-rock") {{
            attributes.set(DustedAttribute.decay, 0.25f);
        }};

        riftRock = new RiftFloor("rift-rock") {{
            attributes.set(DustedAttribute.decay, 1.5f);
            attributes.set(DustedAttribute.decayEnergy, 1f);
            blendGroup = decayedRock;
        }};

        fallenStone = new MistFloor("fallen-stone") {{
            attributes.set(DustedAttribute.decay, 0.25f);
        }};

        fallenMantle = new MistFloor("fallen-mantle") {{
            attributes.set(DustedAttribute.decay, 0.25f);
        }};

        warpedStone = new RiftFloor("warped-stone") {{
            attributes.set(DustedAttribute.decay, 1f);
            attributes.set(DustedAttribute.decayEnergy, 1f);
            riftColor = Color.valueOf("c2ffd1");
            blendGroup = fallenStone;
        }};

        volstone = new Floor("volstone");

        scoria = new Floor("scoria");

        latite = new Floor("latite");

        stradrock = new Floor("stradrock");

        scorchedStradrock = new Floor("scorched-stradrock");

        basaltFumarole = new Fumarole("basalt-fumarole") {{
            variants = 2;
            blendGroup = Blocks.basalt;
            attributes.set(DustedAttribute.volcanicGas, 1f);
        }};

        volstoneFumarole = new Fumarole("volstone-fumarole") {{
            variants = 2;
            blendGroup = volstone;
            attributes.set(DustedAttribute.volcanicGas, 1f);
        }};

        scoriaFumarole = new Fumarole("scoria-fumarole") {{
            variants = 2;
            blendGroup = scoria;
            attributes.set(DustedAttribute.volcanicGas, 1f);
        }};

        latiteFumarole = new Fumarole("latite-fumarole") {{
            variants = 2;
            blendGroup = latite;
            attributes.set(DustedAttribute.volcanicGas, 1f);
        }};

        cavnenWall = new StaticWall("cavnen-wall") {{
            cavnenSediment.asFloor().wall = cavnenDusting.asFloor().wall = cavnenSilk.asFloor().wall = this;
        }};

        decayedWall = new StaticWall("decayed-wall") {{
            decayedRock.asFloor().wall = riftRock.asFloor().wall = this;
        }};

        fallenWall = new StaticWall("fallen-wall") {{
            fallenStone.asFloor().wall = fallenMantle.asFloor().wall = this;
        }};

        volstoneWall = new StaticWall("volstone-wall") {{
            attributes.set(DustedAttribute.rock, 0.8f);
        }};

        scoriaWall = new StaticWall("scoria-wall") {{
            attributes.set(DustedAttribute.rock, 1f);
        }};

        latiteWall = new StaticWall("latite-wall") {{
            attributes.set(DustedAttribute.rock, 1f);
        }};

        stradrockWall = new StaticWall("stradrock-wall") {{
            scorchedStradrock.asFloor().wall = this;
            attributes.set(DustedAttribute.rock, 1f);
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

        volSprout = new Prop("vol-sprout") {{
            variants = 2;
        }};

        weepingShrub = new Prop("weeping-shrub") {{
            variants = 2;
        }};

        cavnenBoulder = new Prop("cavnen-boulder") {{
            variants = 2;
            cavnenSediment.asFloor().decoration = cavnenDusting.asFloor().decoration = cavnenSilk.asFloor().decoration = this;
        }};

        decayedBoulder = new Prop("decayed-boulder") {{
            variants = 2;
            decayedRock.asFloor().decoration = riftRock.asFloor().decoration = this;
        }};

        fallenBoulder = new Prop("fallen-boulder") {{
            variants = 2;
            fallenStone.asFloor().decoration = fallenMantle.asFloor().decoration = this;
        }};

        //TODO maybe a tree for the fallen blocks?

        volstoneBoulder = new Prop("volstone-boulder") {{
            variants = 2;
            volstone.asFloor().decoration = this;
        }};

        //endregion
        //region crafters
        quartzExtractor = new PowderCrafter("quartz-extractor") {{
            requirements(Category.crafting, ItemStack.with(DustedItems.zircon, 80, DustedItems.arsenic, 80, DustedItems.antimony, 40));
            hasPower = true;
            size = 2;

            drawer = new DrawMulti(new DrawRegion("-bottom"), new DrawPowder(DustedPowders.quartzDust), new DrawDefault());
            outputPowder = new PowderStack(DustedPowders.quartzDust, 0.1f);
            craftTime = 120f;
            consumePower(1f);
            consumeItem(Items.sand, 2);

            researchCost = ItemStack.with(DustedItems.zircon, 100, DustedItems.arsenic, 50, DustedItems.antimony, 50);
        }};

        siliconForge = new PowderCrafter("silicon-forge") {{
            requirements(Category.crafting, ItemStack.with(DustedItems.arsenic, 120, DustedItems.antimony, 100, DustedItems.zircon, 80));
            size = 3;
            squareSprite = false;
            itemCapacity = 20;
            researchCost = ItemStack.with(DustedItems.zircon, 120, DustedItems.arsenic, 50, DustedItems.antimony, 50);

            outputItem = new ItemStack(Items.silicon, 15);
            craftTime = 300f;
            craftEffect = new MultiEffect(Fx.smeltsmoke, DustedFx.quartzSmokeCloud);
            drawer = new DrawMulti(new DrawRegion("-bottom"), new DrawPowderAbsorb(DustedPowders.quartzDust), new DrawDefault(), new DrawGlowProgress(Pal.redLight));
            ambientSound = Sounds.smelter;
            ambientSoundVolume = 0.08f;

            consumePower(2f);
            consume(new ConsumePowder(DustedPowders.quartzDust, 0.2f));
            consume(new ConsumePowder(DustedPowders.orchar, 0.1f));
        }};

        metaglassFurnace = new PowderCrafter("metaglass-furnace") {{
            requirements(Category.crafting, ItemStack.with(DustedItems.zircon, 120, Items.silicon, 70, DustedItems.antimony, 60));
            size = 3;
            squareSprite = false;

            outputItem = new ItemStack(Items.metaglass, 2);
            craftTime = 60f;
            craftEffect = Fx.smeltsmoke;
            drawer = new DrawMulti(new DrawDefault(), new DrawSpinFlame());
            ambientSound = Sounds.smelter;
            ambientSoundVolume = 0.09f;

            consumePower(2.5f);
            consume(new ConsumePowder(DustedPowders.quartzDust, 0.2f));
            consumeItem(DustedItems.antimony, 2);
        }};

        rockwoolExtruder = new DrawerWallCrafter("rockwool-extruder") {{
            requirements(Category.crafting, ItemStack.with(DustedItems.zircon, 120, Items.metaglass, 60, Items.silicon, 40));
            rotateDraw = false;
            size = 3;

            attribute = DustedAttribute.rock;
            output = DustedItems.rockwool;
            drillTime = 120f;
            drawer = new DrawMulti(
                    new DrawRegion("-bottom"),
                    new DrawLiquidTile(Liquids.slag),
                    new DrawDefault(),
                    new DrawSideExtractor()
            );

            consumePower(0.8f);
            consumeLiquid(Liquids.slag, 0.1f);
        }};

        crisaltSynthesizer = new PowderCrafter("crisalt-synthesizer") {{
            requirements(Category.crafting, ItemStack.with(DustedItems.antimony, 100, Items.silicon, 80, DustedItems.arsenic, 50));
            size = 3;
            squareSprite = false;

            outputItem = new ItemStack(DustedItems.crisalt, 2);
            craftTime = 80f;
            craftEffect = DustedFx.crisaltSmoke;
            ambientSound = DustedSounds.synthesisLoop;
            ambientSoundVolume = 0.09f;

            drawer = new DrawMulti(
                    new DrawRegion("-bottom"),
                    new DrawSquarePulse(),
                    new DrawGlowRegion() {{
                        color = Color.valueOf("ee9b40");
                        layer = 0f;
                        glowIntensity = 0.25f;
                    }},
                    new DrawDefault()
            );

            consumePower(1.5f);
            consume(new ConsumePowder(DustedPowders.quartzDust, 0.1f));
            consumeLiquid(Liquids.slag, 0.2f);
        }};

        gunpowderMill = new PowderCrafter("gunpowder-mill") {{
            requirements(Category.crafting, ItemStack.with(DustedItems.antimony, 120, Items.metaglass, 100, DustedItems.crisalt, 60, DustedItems.arsenic, 60));
            size = 3;
            squareSprite = false;
            outputPowder = new PowderStack(DustedPowders.gunpowder, 0.2f);
            craftTime = 120f;

            drawer = new DrawMulti(
                    new DrawRegion("-bottom"),
                    new DrawParticles() {{
                        color = Color.valueOf("6f6460");
                        particleSize = 4f;
                        particleRad = 9f;
                        particles = 35;
                        rotateScl = 0.5f;
                        fadeMargin = 0f;
                        reverse = true;
                        blending = Blending.additive;
                    }},
                    new DrawRegion("-rotator", 2f, true),
                    new DrawDefault()
            );

            consume(new ConsumePowder(DustedPowders.orchar, 0.1f));
            consume(new ConsumePowder(DustedPowders.sulfur, 0.2f));
            consume(new ConsumePowder(DustedPowders.niter, 0.2f));
            consumePower(2f);
        }};

        deteriorationChamber = new GenericCrafter("deterioration-chamber") {{
            requirements(Category.crafting, ItemStack.with(DustedItems.antimony, 120, DustedItems.platinum, 60, Items.metaglass, 40, DustedItems.arsenic, 40));
            size = 3;
            squareSprite = false;
            DustedLands.decay.decayMultipliers.put(this, 0.3f);

            outputItem = new ItemStack(DustedItems.perisle, 1);
            craftTime = 120f;
            ambientSound = DustedSounds.decayLoop;
            ambientSoundVolume = 0.16f;

            drawer = new DrawMulti(
                    new DrawRegion("-bottom"),
                    new DrawDeterioration(),
                    new DrawDefault()
            );

            consume(new ConsumeDecay());
            consumePower(1.5f);
            consumeItem(DustedItems.platinum, 2);
            consumeLiquid(Liquids.hydrogen, 0.1f);
        }};
        //endregion
        //region distribution
        transferLink = new TransferLink("transfer-link") {{
            requirements(Category.distribution, ItemStack.with(DustedItems.zircon, 3));
            squareSprite = false;
            researchCost = ItemStack.with(DustedItems.zircon, 5);
        }};

        //TODO is this necessary?
        transferTower = new TransferLink("transfer-tower") {{
            requirements(Category.distribution, ItemStack.with(DustedItems.zircon, 10, DustedItems.arsenic, 10, Items.silicon, 5));
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
            requirements(Category.distribution, BuildVisibility.hidden, ItemStack.with());
            powderPressure = 1.03f;
            powderCapacity = 16f;
            health = 100;
        }};

        armoredChute = new ArmoredChute("armored-chute") {{
            requirements(Category.distribution, ItemStack.with(DustedItems.zircon, 2, DustedItems.antimony, 1));
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
            fadeIn = moveArrows = false;
            arrowSpacing = 6f;
            range = 4;
            hasPower = false;
        }};
        //endregion
        //region production
        //TODO maybe this shouldnt be a burst drill
        pressureDrill = new BurstDrill("pressure-drill") {{
            requirements(Category.production, ItemStack.with(DustedItems.zircon, 20));
            drillTime = 60f * 8f;
            size = 3;
            tier = 3;
            squareSprite = false;
            hasPower = true;
            drillEffect = new MultiEffect(Fx.mineBig, Fx.explosion);
            itemCapacity = 20;
            arrows = 2;
            arrowSpacing = 3f;
            baseArrowColor = Color.valueOf("726c6a");
            researchCost = ItemStack.with(DustedItems.zircon, 10);

            consumePower(20f / 60f);
        }};

        ignitionDrill = new PowderBurstDrill("ignition-drill") {{
            requirements(Category.production, ItemStack.with(DustedItems.zircon, 60, DustedItems.arsenic, 40));
            drillTime = 60f * 8f;
            size = 4;
            tier = 5;
            squareSprite = false;
            hasPower = true;
            drillEffect = new MultiEffect(Fx.mineHuge, Fx.explosion, DustedFx.mineIgnite);
            shake = 4f;
            itemCapacity = 40;
            arrows = 2;
            arrowSpacing = 4f;
            baseArrowColor = Color.valueOf("726c6a");

            researchCost = ItemStack.with(DustedItems.zircon, 20, DustedItems.arsenic, 10);

            consumePower(1f);
            consume(new ConsumePowder(DustedPowders.orchar, 0.1f));
        }};

        //TODO maybe it should just be a 2x2 block instead
        pneumaticFunnel = new Funnel("pneumatic-funnel") {{
            requirements(Category.production, ItemStack.with(DustedItems.zircon, 10));
            powderCapacity = 20f;
            funnelAmount = 0.05f;
            squareSprite = false;
        }};

        rotaryFunnel = new Funnel("rotary-funnel") {{
            requirements(Category.production, ItemStack.with(Items.metaglass, 40, DustedItems.zircon, 20));
            size = 2;
            hasPower = true;
            powderCapacity = 40f;
            consumePower(0.2f);
            extractEffect = DustedFx.funnelExtract;
            squareSprite = false;
        }};

        sulfurSiphon = new PowderAttributeCrafter("sulfur-siphon") {{
            requirements(Category.production, ItemStack.with(DustedItems.rockwool, 80, DustedItems.arsenic, 60, Items.silicon, 40, DustedItems.crisalt, 40));
            size = 3;
            squareSprite = false;
            attribute = DustedAttribute.volcanicGas;
            baseEfficiency = 0f;
            minEfficiency = 0.01f;
            boostScale = 1f / 9f;
            craftTime = 60f;
            ambientSound = Sounds.machine;
            ambientSoundVolume = 0.09f;
            outputPowder = new PowderStack(DustedPowders.sulfur, 0.1f);
            drawer = new DrawMulti(
                    new DrawRegion("-bottom"),
                    new DrawCircles() {{
                        color = Color.valueOf("f7e89c").a(0.4f);
                        radius = 8f;
                        amount = 4;
                        timeScl = 240f;
                    }},
                    new DrawDefault(),
                    new DrawParticles() {{
                        color = Color.valueOf("f7e89c");
                        particleRad = 8f;
                        particles = 20;
                        fadeMargin = 0f;
                        particleInterp = particleSizeInterp = Interp.pow3Out;
                        particleLife = 50f;
                        reverse = true;
                    }},
                    new DrawRegion("-top")
            );
            consumePower(0.5f);
        }};

        hydrogenSiphon = new AttributeCrafter("hydrogen-siphon") {{
            requirements(Category.production, ItemStack.with(DustedItems.rockwool, 80, DustedItems.antimony, 60, Items.metaglass, 40, DustedItems.crisalt, 40));
            size = 3;
            squareSprite = false;
            attribute = DustedAttribute.volcanicGas;
            baseEfficiency = 0f;
            minEfficiency = 0.01f;
            boostScale = 1f / 9f;
            craftTime = 60f;
            ambientSound = Sounds.machine;
            ambientSoundVolume = 0.09f;
            outputLiquid = new LiquidStack(Liquids.hydrogen, 0.2f);
            liquidCapacity = 30f;
            drawer = new DrawMulti(
                    new DrawRegion("-bottom"),
                    new DrawCircles() {{
                        color = Color.valueOf("9eabf7").a(0.4f);
                        radius = 8f;
                        amount = 4;
                        timeScl = 240f;
                    }},
                    new DrawDefault(),
                    new DrawParticles() {{
                        color = Color.valueOf("9eabf7");
                        particleRad = 8f;
                        particles = 20;
                        fadeMargin = 0f;
                        particleInterp = particleSizeInterp = Interp.pow3Out;
                        particleLife = 50f;
                        reverse = true;
                    }},
                    new DrawRegion("-top")
            );
            consumePower(0.5f);
        }};
        //endregion
        //region liquids
        refractoryPump = new Pump("refractory-pump") {{
            requirements(Category.liquid, ItemStack.with(Items.metaglass, 60, DustedItems.antimony, 40, DustedItems.arsenic, 30));
            pumpAmount = 20f / 60f / 4f;
            liquidCapacity = 60f;
            size = 2;
            squareSprite = false;
            consumePower(1f);
        }};

        liquidLink = new LiquidTransferLink("liquid-link") {{
            requirements(Category.liquid, ItemStack.with(Items.metaglass, 2,DustedItems.zircon, 1));
            squareSprite = false;
        }};
        //endregion
        //region power
        magmaticGenerator = new FilterTileGenerator("magmatic-generator") {{
            requirements(Category.power, ItemStack.with(DustedItems.zircon, 50));
            size = 2;
            floating = true;
            squareSprite = false;
            ambientSound = Sounds.hum;
            filter = f -> f.liquidDrop == Liquids.slag ? f.liquidMultiplier : 0f;
            generateEffect = Fx.incinerateSlag;
            outputLiquid = new LiquidStack(Liquids.slag, 5f / 4f / 60f);
            liquidCapacity = 20f;
            powerProduction = 0.5f;
            researchCost = ItemStack.with(DustedItems.zircon, 10);

            drawer = new DrawMulti(new DrawRegion("-bottom"), new DrawLiquidTile(Liquids.slag, 1.25f), new DrawDefault());
        }};

        riftDischarger = new FilterTileGenerator("rift-discharger") {{
            requirements(Category.power, ItemStack.with(DustedItems.zircon, 70));
            size = 3;
            floating = true;
            ambientSound = Sounds.hum;
            filter = f -> f.attributes.get(DustedAttribute.decayEnergy);
            generateEffect = DustedFx.riftDischarge;
            effectChance = 0.01f;
            powerProduction = 160f / 9f / 60f;

            drawer = new DrawMulti(
                    new DrawRegion("-bottom"),
                    new DrawCircles() {{
                        color = DustedPal.decayingYellow.cpy().a(0.4f);
                        radius = 8f;
                        amount = 4;
                        timeScl = 80f;
                    }},
                    new DrawParticles() {{
                        color = DustedPal.decayingYellow;
                        particleSize = 2f;
                        particleRad = 9f;
                        fadeMargin = 0f;
                        blending = Blending.additive;
                    }},
                    new DrawDefault()
            );
        }};

        //i dont have any ideas ok?
        crystalConcentrator = new TransferPowerPowderConsumeGenerator("crystal-concentrator") {{
            requirements(Category.power, ItemStack.with(DustedItems.antimony, 100, Items.metaglass, 80, Items.silicon, 50));
            size = 3;
            powerProduction = 8f;
            ambientSound = Sounds.smelter;
            ambientSoundVolume = 0.08f;
            squareSprite = false;

            consume(new ConsumePowder(DustedPowders.quartzDust, 0.05f));
            consumeLiquid(Liquids.slag, 10f / 60f);

            drawer = new DrawMulti(
                    new DrawRegion("-bottom"),
                    new DrawCircles() {{
                        color = DustedPal.lightQuartz.cpy().a(0.4f);
                        radius = 8f;
                        amount = 2;
                        timeScl = 120f;
                    }},
                    new DrawParticles() {{
                        color = DustedPal.darkQuartz;
                        particleSize = 2f;
                        particleRad = 9f;
                        fadeMargin = 0f;
                        blending = Blending.additive;
                    }},
                    new DrawDefault()
            );
        }};
        //endregion
        //region defense
        zirconWall = new Wall("zircon-wall") {{
            requirements(Category.defense, ItemStack.with(DustedItems.zircon, 6));
            health = 280;
        }};

        zirconWallLarge = new Wall("zircon-wall-large") {{
            requirements(Category.defense, ItemStack.mult(zirconWall.requirements, 4));
            scaledHealth = 280;
            size = 2;
        }};

        antimonyWall = new Wall("antimony-wall") {{
            requirements(Category.defense, ItemStack.with(DustedItems.antimony, 6));
            health = 440;
        }};

        antimonyWallLarge = new Wall("antimony-wall-large") {{
            requirements(Category.defense, ItemStack.mult(antimonyWall.requirements, 4));
            scaledHealth = 440;
            size = 2;
        }};

        crisaltWall = new Wall("crisalt-wall") {{
            requirements(Category.defense, ItemStack.with(DustedItems.crisalt, 6));
            health = 960;
        }};

        crisaltWallLarge = new Wall("crisalt-wall-large") {{
            requirements(Category.defense, ItemStack.mult(crisaltWall.requirements, 4));
            scaledHealth = 960;
            size = 2;
        }};

        perisleWall = new Wall("perisle-wall") {{
            requirements(Category.defense, ItemStack.with(DustedItems.platinum, 6, DustedItems.perisle, 6));
            health = 1040;
        }};

        perisleWallLarge = new Wall("perisle-wall-large") {{
            requirements(Category.defense, ItemStack.mult(perisleWall.requirements, 4));
            scaledHealth = 1040;
            size = 2;
        }};

        decaySuppressor = new DecaySuppressor("decay-suppressor") {{
           requirements(Category.effect, ItemStack.with(DustedItems.zircon, 50));
           size = 3;
           consumePower(1f);
           researchCost = ItemStack.with(DustedItems.zircon, 30);
        }};

        //TODO boost item
        regenerationTower = new RepairTower("regeneration-tower") {{
            requirements(Category.effect, ItemStack.with(Items.metaglass, 100, Items.silicon, 80, DustedItems.antimony, 80, DustedItems.arsenic, 40));
            size = 3;
            consumePower(1.5f);

            drawer = new DrawMulti(
                    new DrawRegion("-bottom"),
                    new DrawCircles() {{
                        color = DustedPal.pinkHeal.cpy().a(0.4f);
                        radius = 10f;
                        amount = 2;
                        timeScl = 100f;
                    }},
                    new DrawDefault()
            );
        }};
        //endregion
        //region turrets
        //TODO make all of these use hydrogen as a coolant
        abrade = new ItemTurret("abrade") {{
            requirements(Category.turret, ItemStack.with(DustedItems.zircon, 80, DustedItems.arsenic, 60));
            size = 2;
            squareSprite = false;
            scaledHealth = 240f;
            reload = 15f;
            recoil = 4f;
            range = 75f;
            shootSound = Sounds.pew;
            rotateSpeed = 18f;
            outlineColor = DustedPal.darkerWarmMetal;
            drawer = new DrawTurret("decayed-");

            ammo(
                    DustedItems.zircon, new ShrapnelBulletType() {{
                        hitEffect = Fx.hitBulletSmall;
                        smokeEffect = Fx.shootSmallSmoke;
                        shootEffect = DustedFx.shootCavnenShrapnel;
                        status = DustedStatusEffects.deteriorating;
                        statusDuration = 8 * 60f;
                        fromColor = DustedPal.decayingYellow;
                        toColor = DustedPal.decayingYellowBack;
                        length = 75f;
                        damage = 8f;
                        width = 16f;
                        serrations = 0;
                    }}
            );

            coolant = consumeCoolant(0.1f);
            researchCostMultiplier = 0.1f;
        }};

        scald = new PowderTurret("scald") {{
            requirements(Category.turret, ItemStack.with(DustedItems.zircon, 70, DustedItems.arsenic, 60, DustedItems.antimony, 40));
            size = 2;
            squareSprite = false;
            scaledHealth = 260f;
            reload = 40f;
            recoil = 3f;
            range = 135f;
            rotateSpeed = 8f;
            shootEffect = Fx.shootBigColor;
            shootSound = Sounds.shootBig;
            targetGround = false;
            outlineColor = DustedPal.darkerWarmMetal;
            drawer = new DrawTurret("decayed-");

            shoot = new ShootSpread(5, 8f);

            ammo(
                    DustedPowders.orchar, new BasicBulletType(4.5f, 10f, "circle-bullet") {{
                        collidesGround = false;
                        width = height = 14f;
                        shrinkX = shrinkY = 0.4f;
                        splashDamageRadius = 20f;
                        splashDamage = 24f;
                        status = DustedStatusEffects.blazing;
                        statusDuration = 4f * 60f;
                        frontColor = DustedPal.lightOrchar;
                        backColor = hitColor = trailColor = DustedPal.darkOrchar;
                        trailChance = 0.4f;
                        trailEffect = Fx.hitSquaresColor;
                        shootEffect = DustedFx.shootPowder;
                        hitEffect = DustedFx.hitPowder;
                        lifetime = 30f;
                    }}
            );

            coolant = consumeCoolant(0.1f);
            researchCost = ItemStack.with(DustedItems.zircon, 30, DustedItems.arsenic, 20, DustedItems.antimony, 20);
        }};

        sunder = new PowderTurret("sunder") {{
            requirements(Category.turret, ItemStack.with(DustedItems.zircon, 80, DustedItems.arsenic, 60, DustedItems.antimony, 50, Items.silicon, 50));
            size = 2;
            squareSprite = false;
            scaledHealth = 220f;
            reload = 80f;
            range = 135f;
            inaccuracy = 5f;
            shootSound = DustedSounds.shootLight;

            shoot.shots = 2;

            outlineColor = DustedPal.darkerWarmMetal;
            drawer = new DrawTurret("decayed-");

            ammo(
                    DustedPowders.orchar, new BasicBulletType(4.5f, 18f) {{
                        lifetime = 30f;
                        width = 18f;
                        height = 22f;
                        frontColor = DustedPal.lightOrchar;
                        backColor = hitColor = trailColor = DustedPal.darkOrchar;
                        trailLength = 14;
                        trailWidth = 6f;
                        hitEffect = despawnEffect = Fx.hitSquaresColor;
                        shootEffect = Fx.shootSmallColor;
                        smokeEffect = Fx.shootSmokeSquare;
                        status = DustedStatusEffects.blazing;
                        statusDuration = 6 * 60f;
                        hitSound = Sounds.bang;
                    }}
            );

            researchCost = ItemStack.with(DustedItems.zircon, 100, DustedItems.arsenic, 50, DustedItems.antimony, 50, Items.silicon, 50);
        }};

        coruscate = new PowderTurret("coruscate") {{
            requirements(Category.turret, ItemStack.with(DustedItems.arsenic, 220, DustedItems.antimony, 180, Items.silicon, 150, Items.metaglass, 100));
            size = 3;
            squareSprite = false;
            scaledHealth = 280f;
            reload = 110f;
            recoil = 5f;
            shootSound = Sounds.artillery;
            range = 3f * 70f;
            outlineColor = DustedPal.darkerWarmMetal;
            shootWarmupSpeed = 0.15f;
            cooldownTime = 140f;

            drawer = new DrawTurret("decayed-") {{
                parts.add(
                        new RegionPart("-blade") {{
                            progress = PartProgress.recoil;
                            mirror = true;
                            under = true;
                            x = 3.5f;
                            y = 4.75f;
                            moveX = 2f;
                            moveY = -1f;
                            moveRot = -20f;
                        }},
                        new RegionPart("-blade-glow") {{
                            progress = PartProgress.recoil;
                            mirror = true;
                            under = true;
                            heatProgress = PartProgress.warmup;
                            heatColor = Color.valueOf("ff3b62");
                            drawRegion = false;
                            x = 3.5f;
                            y = 4.75f;
                            moveX = 2f;
                            moveY = -1f;
                            moveRot = -20f;
                        }},
                        new RegionPart("-glow") {{
                            heatProgress = PartProgress.warmup;
                            heatColor = Color.valueOf("ff3b62");
                            drawRegion = false;
                        }}
                );
            }};

            ammo(
                    DustedPowders.quartzDust, new RocketBulletType(3f, 36f) {{
                        width = height = 18f;
                        splashDamage = 35f;
                        splashDamageRadius = 22f;
                        ammoPerShot = 3;
                        lifetime = 70f;
                        frontColor = DustedPal.lightQuartz;
                        backColor = hitColor = trailColor = DustedPal.darkQuartz;
                        trailLength = 16;
                        trailWidth = 4f;
                        status = StatusEffects.burning;
                        shootEffect = DustedFx.shootPowder;
                        statusDuration = 16 * 60f;
                        hitEffect = despawnEffect = DustedFx.hitPowder;

                        shoot = new ShootSpread(3, 30f);

                        shootSound = Sounds.explosion;

                        rocketBulletType = new BasicBulletType(1.6f, 16f) {{
                            width = height = 12f;
                            splashDamage = 12f;
                            splashDamageRadius = 18f;
                            lifetime = 30f;
                            frontColor = DustedPal.lightQuartz;
                            backColor = hitColor = DustedPal.darkQuartz;
                            shootEffect = DustedFx.shootPowder;
                            hitEffect = despawnEffect = DustedFx.hitPowder;
                            makeFire = true;
                            status = StatusEffects.burning;
                            statusDuration = 3 * 60f;
                        }};
                    }}
            );

            coolant = consumeCoolant(0.1f);
        }};

        strike = new ItemPowderTurret("strike") {{
            requirements(Category.turret, ItemStack.with(DustedItems.arsenic, 210, DustedItems.antimony, 190, Items.silicon, 160, Items.metaglass, 150));
            size = 3;
            squareSprite = false;
            scaledHealth = 240f;
            outlineColor = DustedPal.darkerWarmMetal;
            rotateSpeed = 1f;
            reload = 6f;
            recoilTime = 30f;
            cooldownTime = 40f;
            recoil = 2f;
            shootSound = Sounds.dullExplosion;
            shootEffect = DustedFx.shootLaunch;
            shootWarmupSpeed = 0.05f;
            minWarmup = 0.8f;
            range = 240f;
            shootY = 10f;

            drawer = new DrawTurret("decayed-") {{
                parts.add(
                        new RegionPart("-blade") {{
                            progress = PartProgress.warmup;
                            mirror = true;
                            under = true;
                            moveX = 7f;
                            moveRot = -40f;
                            moves.add(new PartMove(PartProgress.warmup, 5f, 2f, -5f));
                        }},
                        new RegionPart("-blade") {{
                            progress = PartProgress.warmup;
                            mirror = true;
                            under = true;
                            moveX = 6f;
                            moveY = -4f;
                            moveRot = -60f;
                            moves.add(new PartMove(PartProgress.warmup, 4f, -2f, -4f));
                        }},
                        new RegionPart("-glow") {{
                            heatProgress = PartProgress.recoil;
                            heatColor = Color.valueOf("1013e0");
                            drawRegion = false;
                        }}
                );
            }};

            ammo(
                    //TODO this is probably unbalanced
                    DustedItems.antimony, new InstantBulletType() {{
                        damage = 18f;
                        splashDamage = 10f;
                        splashDamageRadius = 4f;
                        status = StatusEffects.blasted;
                        hitEffect = despawnEffect = DustedFx.hitLaunch;
                    }}
            );

            consume(new ConsumePowder(DustedPowders.sulfur, 0.2f));
        }};

        clutter = new ItemTurret("clutter") {{
            requirements(Category.turret, ItemStack.with(Items.silicon, 240, DustedItems.arsenic, 220, DustedItems.crisalt, 100, Items.metaglass, 80));
            size = 3;
            squareSprite = false;
            scaledHealth = 260f;
            outlineColor = DustedPal.darkerWarmMetal;
            shootSound = DustedSounds.cannonLight;
            rotateSpeed = 15f;
            reload = 50f;
            recoil = 2f;
            range = 135f;
            inaccuracy = 25f;
            velocityRnd = 0.8f;
            shootY = 1f;
            minWarmup = 0.8f;
            targetGround = false;

            shoot.shots = 50;

            drawer = new DrawTurret("decayed-") {{
                parts.add(
                        new RegionPart("-wing") {{
                            mirror = true;
                            under = true;
                            moveX = 1f;
                            moveY = -2f;
                            moveRot = -20f;
                            moves.add(new PartMove(PartProgress.recoil, 0f, 0f, -10f));
                            heatProgress = PartProgress.warmup;
                            heatColor = Color.valueOf("bf301d");
                        }},
                        new RegionPart() {{
                            heatProgress = PartProgress.warmup;
                            heatColor = Color.valueOf("bf301d");
                            drawRegion = false;
                        }}
                );
            }};

            ammo(
                    DustedItems.antimony, new BasicBulletType(7f, 14f, "circle-bullet") {{
                        collidesGround = false;
                        width = height = 9f;
                        shrinkX = shrinkY = 0.3f;
                        lifetime = 55f;
                        drag = 0.04f;
                        frontColor = Color.valueOf("f1b093");
                        backColor = hitColor = Color.valueOf("d36f56");
                        shootEffect = DustedFx.shootBlastSpray;
                        hitEffect = despawnEffect = DustedFx.hitBlastSpray;
                        status = StatusEffects.blasted;
                    }},
                    Items.metaglass, new BasicBulletType(7f, 16f, "circle-bullet") {{
                        collidesGround = false;
                        width = height = 10f;
                        shrinkX = shrinkY = 0.3f;
                        lifetime = 55f;
                        drag = 0.04f;
                        frontColor = Color.white;
                        backColor = hitColor = Color.valueOf("bccdf4");
                        shootEffect = DustedFx.shootBlastSpray;
                        hitEffect = despawnEffect = DustedFx.hitBlastSpray;
                        status = StatusEffects.burning;
                        statusDuration = 3f * 60f;
                        fragBullets = 3;
                        fragBullet = new BasicBulletType(3f, 4f, "circle-bullet") {{
                            collidesGround = false;
                            width = height = 6f;
                            shrinkX = shrinkY = 0.4f;
                            lifetime = 20f;
                            drag = 0.04f;
                            frontColor = Color.white;
                            backColor = hitColor = Color.valueOf("bccdf4");
                            hitEffect = despawnEffect = DustedFx.hitBlastSpray;
                            status = StatusEffects.burning;
                            statusDuration = 2f * 60f;
                        }};
                    }}
            );
        }};

        blight = new PowderTurret("blight") {{
            requirements(Category.turret, ItemStack.with(DustedItems.arsenic, 440, DustedItems.antimony, 160, Items.silicon, 120, DustedItems.platinum, 40));
            size = 4;
            squareSprite = false;
            reload = 190f;
            scaledHealth = 260f;
            range = 340f;
            shootY = 6f;
            shootSound = DustedSounds.fireworkLaunch;
            outlineColor = DustedPal.darkerWarmMetal;
            drawer = new DrawTurret("decayed-") {{
                parts.addAll(
                        new RegionPart("-blade") {{
                            mirror = true;
                            under = true;
                            x = 8f;
                            y = 3f;
                            moveX = 7f;
                            moveY = 4f;
                            moveRot = 50f;
                            moves.add(new PartMove(PartProgress.recoil, 0f, -1f, -40f));
                            heatProgress = PartProgress.warmup;
                            heatColor = Color.valueOf("ff3b62");
                        }},
                        new RegionPart("-blade") {{
                            mirror = true;
                            under = true;
                            x = 8f;
                            y = 3f;
                            moveX = 9f;
                            moveY = -2f;
                            moveRot = 20f;
                            moves.add(new PartMove(PartProgress.recoil, 0f, -1f, -40f));
                            heatProgress = PartProgress.warmup;
                            heatColor = Color.valueOf("ff3b62");
                        }},
                        new RegionPart("-blade") {{
                            mirror = true;
                            under = true;
                            x = 8f;
                            y = 3f;
                            moveX = 6f;
                            moveY = -7f;
                            moveRot = -30f;
                            moves.add(new PartMove(PartProgress.recoil, 0f, -1f, -40f));
                            heatProgress = PartProgress.warmup;
                            heatColor = Color.valueOf("ff3b62");
                        }},
                        new RegionPart("-side") {{
                            x = 7.25f;
                            y = -1f;
                            mirror = true;
                            under = true;
                            moveX = 0.5f;
                            moveY = 2.5f;
                            heatProgress = PartProgress.recoil;
                            heatColor = Color.valueOf("ff3b62");
                        }},
                        new RegionPart("-barrel") {{
                            under = true;
                            progress = PartProgress.recoil;
                            moveY = -3f;
                            heatProgress = PartProgress.recoil;
                            heatColor = Color.valueOf("ff3b62");
                        }}
                );
            }};

            ammo(
                    DustedPowders.quartzDust, new RocketBulletType(7f, 45f, "circle-bullet") {
                        {
                            lifetime = 240f;
                            drag = 0.02f;
                            width = height = 18f;
                            shrinkX = shrinkY = 0.25f;
                            ammoMultiplier = 1f;
                            pierce = true;
                            frontColor = DustedPal.lightQuartz;
                            backColor = trailColor = hitColor = DustedPal.darkQuartz;
                            shootEffect = DustedFx.shootPowderSquares;
                            hitEffect = despawnEffect = DustedFx.hitPowder;
                            trailWidth = 9f;
                            trailInterp = i -> 1f - shrinkX * i;
                            trailLength = 22;
                            status = StatusEffects.burning;
                            statusDuration = 6 * 60f;

                            rocketReload = 5f;
                            rocketDelay = 50f;
                            inaccuracy = 180f;
                            shoot.shots = 3;
                            shootSound = DustedSounds.crackle;

                            rocketBulletType = new BasicBulletType(3f, 30f) {{
                                width = 12f;
                                height = 18f;
                                lifetime = 40f;
                                frontColor = DustedPal.lightQuartz;
                                backColor = trailColor = hitColor = DustedPal.darkQuartz;
                                shootEffect = DustedFx.shootPowder;
                                hitEffect = despawnEffect = DustedFx.hitPowder;
                                trailWidth = 3f;
                                trailLength = 8;
                            }};
                        }

                        @Override
                        public void removed(Bullet b) {
                            //doesn't do anything, only overriden so that it doesn't draw trail fade
                        }
                    }
            );
        }};

        crush = new PowderTurret("crush") {{
            requirements(Category.turret, ItemStack.with(DustedItems.arsenic, 460, DustedItems.crisalt, 280, Items.metaglass, 120, Items.silicon, 120, DustedItems.platinum, 60));
            size = 4;
            squareSprite = false;
            scaledHealth = 280f;
            outlineColor = DustedPal.darkerWarmMetal;
            reload = 160f;
            shootSound = DustedSounds.cannonHeavy;
            minWarmup = 0.9f;
            range = 380f;

            drawer = new DrawTurret("decayed-") {{
                parts.addAll(
                        new RegionPart("-side") {{
                            mirror = true;
                            under = true;
                            moveX = 2f;
                            moveY = -5f;
                            moveRot = 15f;
                            heatProgress = PartProgress.warmup;
                            heatColor = Color.valueOf("ec4800");
                        }},
                        new RegionPart("-glow") {{
                            moveX = 2f;
                            moveY = -5f;
                            moveRot = 15f;
                            drawRegion = false;
                            mirror = true;
                            heatColor = Color.valueOf("ec4800");
                        }},
                        new RegionPart() {{
                            drawRegion = false;
                            heatProgress = PartProgress.warmup;
                            heatColor = Color.valueOf("ec4800");
                        }},
                        new RegionPart("-barrel") {{
                            under = true;
                            moveY = -2f;
                            progress = PartProgress.recoil;
                            heatProgress = PartProgress.warmup;
                            heatColor = Color.valueOf("ec4800");
                        }}
                );
            }};

            ammo(
                    DustedPowders.quartzDust, new PushBulletType(5f, 60f) {{
                        height = 21f;
                        width = 18f;
                        lifetime = 80f;
                        splashDamage = 80f;
                        splashDamageRadius = 12f;
                        frontColor = DustedPal.lightQuartz;
                        backColor = trailColor = DustedPal.darkQuartz;
                        shootEffect = DustedFx.shootCrushQuartz;
                        hitSound = Sounds.dullExplosion;
                        hitEffect = despawnEffect = DustedFx.hitCrushQuartz;
                        pushEffect = DustedFx.pushQuartz;
                        trailLength = 16;
                        trailWidth = 3.5f;
                        trailRotation = true;
                        fragBullets = 1;
                        fragBullet = new CloudBulletType() {{
                            damage = 10f;
                            damageInterval = 15f;
                            status = DustedStatusEffects.blazing;
                            statusDuration = 3f * 60f;
                            fieldEffect = DustedFx.quartzBurn;
                            color = DustedPal.darkQuartz.cpy().a(0.5f);
                            lifetime = 180f;
                        }};
                    }}
            );
        }};
        //endregion
        //region units
        //TODO change costs
        witheredAssembler = new UnitConstructor("withered-assembler") {{
            requirements(Category.units, ItemStack.with(Items.silicon, 200, DustedItems.antimony, 80, DustedItems.arsenic, 60));
            size = 3;
            areaSize = 7;
            droneType = DustedUnitTypes.decayedAssemblyDrone;

            plans.add(
                    Seq.with(
                            new UnitConstructorPlan(DustedUnitTypes.annul, 60f * 30f, ItemStack.with(Items.silicon, 40, DustedItems.zircon, 30)),
                            new UnitConstructorPlan(DustedUnitTypes.excise, 60f * 60f, ItemStack.with(Items.silicon, 100, DustedItems.crisalt, 80, DustedItems.antimony, 60))
                    ),
                    Seq.with(
                            new UnitConstructorPlan(DustedUnitTypes.carom, 60f * 20f, ItemStack.with(Items.silicon, 30, DustedItems.arsenic, 25)),
                            new UnitConstructorPlan(DustedUnitTypes.recur, 60f * 50f, ItemStack.with(Items.silicon, 80, DustedItems.crisalt, 70, Items.metaglass, 50))
                    )
            );

            consumePower(2f);
        }};

        voltaicAssembler = new UnitConstructor("voltaic-assembler") {{
            requirements(Category.units, ItemStack.with(Items.silicon, 220, DustedItems.antimony, 100, Items.metaglass, 90, DustedItems.arsenic, 60));
            size = 3;
            areaSize = 7;
            droneType = DustedUnitTypes.decayedAssemblyDrone;

            plans.add(
                    Seq.with(
                            new UnitConstructorPlan(DustedUnitTypes.pique, 60f * 40f, ItemStack.with(Items.silicon, 50, DustedItems.antimony, 30, Items.metaglass, 30)),
                            new UnitConstructorPlan(DustedUnitTypes.rancor, 60f * 70f, ItemStack.with(Items.silicon, 110, DustedItems.crisalt, 70, DustedItems.arsenic, 50))
                    ),
                    Seq.with(
                            new UnitConstructorPlan(DustedUnitTypes.sob, 60f * 30f, ItemStack.with(Items.silicon, 40, Items.metaglass, 40)),
                            new UnitConstructorPlan(DustedUnitTypes.wail, 60f * 60f, ItemStack.with(Items.silicon, 90, DustedItems.crisalt, 60, DustedItems.zircon, 40))
                    )
            );

            consumePower(2f);
        }};

        blazingAssembler = new UnitConstructor("blazing-assembler") {{
            requirements(Category.units, ItemStack.with(Items.silicon, 220, DustedItems.antimony, 90, DustedItems.arsenic, 90));
            size = 3;
            areaSize = 7;
            droneType = DustedUnitTypes.decayedAssemblyDrone;

            plans.add(
                    Seq.with(
                            new UnitConstructorPlan(DustedUnitTypes.protei, 60f * 50f, ItemStack.with(Items.silicon, 50, DustedItems.antimony, 30)),
                            new UnitConstructorPlan(DustedUnitTypes.hynobii, 60f * 70f, ItemStack.with(Items.silicon, 100, DustedItems.crisalt, 80, Items.metaglass, 60))
                    )
            );

            consumePower(2f);
        }};

        largeWitheredAssembler = new UnitConstructor("large-withered-assembler") {{
            requirements(Category.units, ItemStack.with(Items.silicon, 400, DustedItems.platinum, 240, Items.metaglass, 160, DustedItems.crisalt, 120));
            size = 5;
            droneType = DustedUnitTypes.decayedAssemblyDrone;

            plans.add(
                    Seq.with(
                            new UnitConstructorPlan(DustedUnitTypes.deduct, 60f * 100f, ItemStack.with(Items.silicon, 240, DustedItems.platinum, 120, DustedItems.crisalt, 80, DustedItems.arsenic, 80))
                    ),
                    Seq.with(
                            new UnitConstructorPlan(DustedUnitTypes.saltate, 60 * 80f, ItemStack.with(Items.silicon, 200, DustedItems.platinum, 80, DustedItems.antimony, 140))
                    )
            );

            consumeLiquid(Liquids.hydrogen, 0.2f);
            consumePower(4f);
        }};

        largeVoltaicAssembler = new UnitConstructor("large-voltaic-assembler") {{
            requirements(Category.units, ItemStack.with(Items.silicon, 440, DustedItems.platinum, 260, Items.metaglass, 100, DustedItems.antimony, 100));
            size = 5;
            droneType = DustedUnitTypes.decayedAssemblyDrone;

            plans.add(
                    Seq.with(
                            new UnitConstructorPlan(DustedUnitTypes.animus, 60f * 120f, ItemStack.with(Items.silicon, 260, DustedItems.platinum, 140, DustedItems.crisalt, 120, Items.metaglass, 100))
                    ),
                    Seq.with(
                            new UnitConstructorPlan(DustedUnitTypes.snivel, 60 * 90f, ItemStack.with(Items.silicon, 200, DustedItems.platinum, 120, DustedItems.crisalt, 110))
                    )
            );

            consumeLiquid(Liquids.hydrogen, 0.2f);
            consumePower(4f);
        }};

        largeBlazingAssembler = new UnitConstructor("large-blazing-assembler") {{
            requirements(Category.units, ItemStack.with(Items.silicon, 420, DustedItems.platinum, 260, Items.metaglass, 160, DustedItems.crisalt, 130));
            size = 5;
            droneType = DustedUnitTypes.decayedAssemblyDrone;

            plans.add(
                    Seq.with(
                            new UnitConstructorPlan(DustedUnitTypes.sirenid, 60f * 110f, ItemStack.with(Items.silicon, 260, DustedItems.platinum, 160, DustedItems.antimony, 80, Items.metaglass, 80))
                    )
            );

            consumeLiquid(Liquids.hydrogen, 0.2f);
            consumePower(4f);
        }};

        aerialAssemblerModule = new UnitConstructorModule("aerial-assembler-module") {{
            requirements(Category.units, ItemStack.with(Items.silicon, 80, DustedItems.antimony, 60, DustedItems.arsenic, 60));
            size = 3;
            tier = 0;
            ptier = 1;
            consumePower(0.5f);
        }};

        binaryAssemblerModule = new UnitConstructorModule("binary-assembler-module") {{
            requirements(Category.units, ItemStack.with(Items.silicon, 220, DustedItems.crisalt, 160, DustedItems.antimony, 120, Items.metaglass, 60));
            size = 3;
            consumePower(0.5f);
        }};

        ternaryAssemblerModule = new UnitConstructorModule("ternary-assembler-module") {{
            requirements(Category.units, ItemStack.with(Items.silicon, 440, DustedItems.platinum, 260, DustedItems.crisalt, 250, DustedItems.antimony, 200, Items.metaglass, 200, DustedItems.perisle, 40));
            size = 5;
            tier = 2;
            consumePower(2f);
        }};
        //endregion
        //region cores
        coreAbate = new ShieldedCoreBlock("core-abate") {{
            requirements(Category.effect, ItemStack.with(DustedItems.zircon, 1200, DustedItems.arsenic, 1000));
            unitType = DustedUnitTypes.erode;
            squareSprite = false;
            size = 3;
            health = 2000;
            itemCapacity = 2000;
            alwaysUnlocked = true;
            isFirstTier = true;
        }};

        coreDissent = new ShieldedCoreBlock("core-dissent") {{
            requirements(Category.effect, ItemStack.with(DustedItems.zircon, 4000, DustedItems.arsenic, 3200, Items.silicon, 3000));
            unitType = DustedUnitTypes.recede;
            squareSprite = false;
            size = 4;
            radius = 160f;
            health = 4500;
            itemCapacity = 4000;
        }};

        coreDecadence = new ShieldedCoreBlock("core-decadence") {{
            requirements(Category.effect, BuildVisibility.hidden, ItemStack.with());
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