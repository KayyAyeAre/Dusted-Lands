package dusted.content;

import dusted.type.*;
import dusted.world.blocks.defense.turrets.*;
import dusted.world.blocks.powder.*;
import dusted.world.blocks.production.*;
import mindustry.content.*;
import mindustry.type.*;
import mindustry.world.*;
import mindustry.world.meta.*;

public class DustedBlocks {
    public static Block
            //defense
            spume,
            //powder distribution
            pneumaticChute, pneumaticChuteDrive,
            //crafters
            titaniumMill,
            //sandbox
            powderSource, powderVoid;

    public static void load() {
        pneumaticChute = new Chute("pneumatic-chute") {{
            requirements(Category.distribution, ItemStack.with(Items.titanium, 3));
        }};

        pneumaticChuteDrive = new ChuteDrive("pneumatic-chute-drive") {{
            requirements(Category.distribution, ItemStack.with(Items.titanium, 3));
            consumes.power(0.5f);
        }};

        titaniumMill = new PowderCrafter("titanium-mill") {{
            requirements(Category.crafting, ItemStack.with());
            hasPower = true;
            size = 2;

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
        }};

        powderSource = new PowderSource("powder-source") {{
            requirements(Category.distribution, BuildVisibility.sandboxOnly, ItemStack.with());
        }};

        powderVoid = new PowderVoid("powder-void") {{
            requirements(Category.distribution, BuildVisibility.sandboxOnly, ItemStack.with());
        }};
    }
}
