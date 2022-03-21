package dusted.content;

import dusted.type.*;
import dusted.world.blocks.defense.turrets.*;
import dusted.world.blocks.powder.*;
import dusted.world.blocks.production.*;
import dusted.world.draw.*;
import mindustry.content.*;
import mindustry.gen.*;
import mindustry.type.*;
import mindustry.world.*;
import mindustry.world.meta.*;

public class DustedBlocks {
    public static Block
            //defense
            spume,
            //powder distribution
            chute, chuteDrive, powderRouter, powderJunction, bridgeChute,
            //crafters
            titaniumMill,
            //sandbox
            powderSource, powderVoid;

    public static void load() {
        chute = new Chute("chute") {{
            requirements(Category.distribution, ItemStack.with(Items.titanium, 2));
        }};

        chuteDrive = new ChuteDrive("chute-drive") {{
            requirements(Category.distribution, ItemStack.with(Items.titanium, 2, Items.lead, 2));
            consumes.power(0.5f);
        }};

        powderRouter = new PowderRouter("powder-router") {{
            requirements(Category.distribution, ItemStack.with(Items.graphite, 3, Items.titanium, 2));
        }};

        powderJunction = new PowderJunction("powder-junction") {{
            requirements(Category.distribution, ItemStack.with(Items.titanium, 2, Items.graphite, 2));
        }};

        bridgeChute = new PowderBridge("bridge-chute") {{
            requirements(Category.distribution, ItemStack.with(Items.titanium, 8, Items.graphite, 4));
            range = 4;
            hasPower = false;
        }};

        titaniumMill = new PowderCrafter("titanium-mill") {{
            requirements(Category.crafting, ItemStack.with());
            hasPower = true;
            size = 2;

            drawer = new DrawPowderRotator();
            outputPowder = new PowderStack(Powders.titaniumPowder, 1f);
            craftTime = 10f;
            consumes.power(2f);
            consumes.item(Items.titanium, 2);
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

        powderSource = new PowderSource("powder-source") {{
            requirements(Category.distribution, BuildVisibility.sandboxOnly, ItemStack.with());
        }};

        powderVoid = new PowderVoid("powder-void") {{
            requirements(Category.distribution, BuildVisibility.sandboxOnly, ItemStack.with());
        }};
    }
}
