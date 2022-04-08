package dusted.world.blocks.production;

import arc.struct.*;
import dusted.world.consumers.*;
import dusted.world.interfaces.*;
import dusted.world.meta.*;
import dusted.world.modules.*;
import mindustry.*;
import mindustry.ctype.*;
import mindustry.gen.*;
import mindustry.world.blocks.production.*;
import mindustry.world.meta.*;

//TODO there's gotta be a better implementation
public class PowderConsumeCrafter extends GenericCrafter {
    public Bits powderFilters = new Bits(Vars.content.getBy(ContentType.effect_UNUSED).size);
    public float powderCapacity = 20;

    public PowderConsumeCrafter(String name) {
        super(name);
    }

    @Override
    public void setStats() {
        super.setStats();
        new CustomStatValue("powder-capacity", StatValues.number(powderCapacity, StatUnit.none)).add(stats);
    }

    @Override
    public void init() {
        consumes.each(cons -> {
            if (cons instanceof ConsumePowderBase pcons) pcons.addPowderFilters(powderFilters);
        });

        super.init();
    }

    public class PowderConsumeCrafterBuild extends GenericCrafterBuild implements PowderBlockc {
        public PowderModule powders = new PowderModule();

        @Override
        public PowderModule powderModule() {
            return powders;
        }

        @Override
        public Building build() {
            return this;
        }

        @Override
        public Bits filters() {
            return powderFilters;
        }

        @Override
        public float powderCapacity() {
            return powderCapacity;
        }
    }
}
