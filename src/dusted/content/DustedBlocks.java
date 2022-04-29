package dusted.content;

import arc.graphics.*;
import dusted.type.*;
import dusted.world.blocks.defense.turrets.*;
import dusted.world.blocks.environment.*;
import dusted.world.blocks.powder.*;
import dusted.world.blocks.production.*;
import dusted.world.consumers.*;
import dusted.world.draw.*;
import mindustry.content.*;
import mindustry.gen.*;
import mindustry.type.*;
import mindustry.world.*;
import mindustry.world.blocks.environment.*;
import mindustry.world.blocks.power.*;
import mindustry.world.blocks.storage.*;
import mindustry.world.draw.*;
import mindustry.world.meta.*;

import static mindustry.type.ItemStack.with;

public class DustedBlocks {
    public static Block
    //environment
    orePlastel, oreTelonate, cavnenSediment, cavnenDusting, volcanGravel, cavnenWall, volcanWall,
    charredTree, volcanFlower, volcanBoulder, cavnenBoulder,
    //defense
    spume, cascade,
    //powder distribution
    chute, powderRouter, powderJunction, bridgeChute,
    denseChute, armoredChute,
    //power
    crudeThermalGenerator,
    //crafters
    titaniumMill, quartzExtractor, graphiteCompactor, siliconForge,
    //production
    pneumaticVacuum, thermalVacuum, blastVacuum,
    //cores
    coreCavor,
    //sandbox
    powderSource, powderVoid;

    public static void load() {
        orePlastel = new OreBlock(DustedItems.plastel);

        oreTelonate = new OreBlock(DustedItems.telonate);

        cavnenSediment = new Floor("cavnen-sediment") {{
            attributes.set(Attribute.oil, 1.2f);
            attributes.set(Attribute.water, -0.6f);
        }};

        cavnenDusting = new PowderFloor("cavnen-dusting") {{
            powderDrop = Powders.cavnenDust;
            status = DustedStatusEffects.deteriorating;
            statusDuration = 180f;
            attributes.set(Attribute.oil, 0.9f);
            attributes.set(Attribute.water, -0.65f);
        }};

        cavnenWall = new StaticWall("cavnen-wall") {{
            cavnenSediment.asFloor().wall = this;
            cavnenDusting.asFloor().wall = this;
        }};

        volcanGravel = new Floor("volcan-gravel");

        volcanWall = new StaticWall("volcan-wall") {{
            volcanGravel.asFloor().wall = this;
        }};

        //TODO no sprites?
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

        graphiteCompactor = new PowderConsumeCrafter("graphite-compactor") {{
            requirements(Category.crafting, ItemStack.with(Items.copper, 70, DustedItems.plastel, 50));
            size = 2;

            outputItem = new ItemStack(Items.graphite, 2);
            craftTime = 60f;
            hasItems = true;

            new ConsumePowder(Powders.cavnenDust, 0.1f).add(consumes);
        }};

        quartzExtractor = new PowderCrafter("quartz-extractor") {{
            requirements(Category.crafting, ItemStack.with(Items.titanium, 100, DustedItems.plastel, 60, Items.copper, 50));
            hasPower = true;
            size = 2;

            drawer = new DrawPowderRotator();
            outputPowder = new PowderStack(Powders.quartzDust, 1f);
            craftTime = 12f;
            consumes.power(1.5f);
            consumes.item(Items.sand, 3);
        }};

        titaniumMill = new PowderCrafter("titanium-mill") {{
            requirements(Category.crafting, ItemStack.with(Items.graphite, 80, DustedItems.plastel, 60));
            hasPower = true;
            size = 2;

            drawer = new DrawPowderRotator();
            outputPowder = new PowderStack(Powders.titaniumPowder, 1f);
            craftTime = 10f;
            consumes.power(2f);
            consumes.item(Items.titanium, 2);
        }};

        siliconForge = new PowderConsumeCrafter("silicon-forge") {{
            requirements(Category.crafting, ItemStack.with(Items.titanium, 130, DustedItems.plastel, 80, Items.graphite, 50));
            hasPower = true;
            hasItems = true;
            size = 2;

            outputItem = new ItemStack(Items.silicon, 2);
            craftTime = 90f;
            drawer = new DrawSmelter();
            drawer = new DrawSmelter(Color.valueOf("ffef99"));
            ambientSound = Sounds.smelter;
            ambientSoundVolume = 0.08f;

            consumes.power(1.2f);
            new ConsumePowder(Powders.quartzDust, 0.05f).add(consumes);
        }};

        spume = new PowderTurret("spume") {{
            requirements(Category.turret, ItemStack.with());
            ammo(
                    Powders.titaniumPowder, DustedBullets.titaniumSpray
            );
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
        }};

        cascade = new PowderTurret("cascade") {{
            requirements(Category.turret, ItemStack.with());
            ammo(
                    Powders.quartzDust, DustedBullets.quartzCascadeShot,
                    Powders.cavnenDust, DustedBullets.cavnenCascadeShot
            );
            size = 2;
            reloadTime = 120f;
            shootSound = Sounds.laser;
        }};

        coreCavor = new CoreBlock("core-cavor") {{
            requirements(Category.effect, BuildVisibility.editorOnly, with(Items.copper, 1000, Items.lead, 800));
            alwaysUnlocked = true;

            unitType = UnitTypes.alpha;
            health = 1100;
            itemCapacity = 4000;
            size = 3;

            unitCapModifier = 6;
        }};

        powderSource = new PowderSource("powder-source") {{
            requirements(Category.distribution, BuildVisibility.sandboxOnly, ItemStack.with());
        }};

        powderVoid = new PowderVoid("powder-void") {{
            requirements(Category.distribution, BuildVisibility.sandboxOnly, ItemStack.with());
        }};
    }
}
