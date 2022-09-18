package dusted.world.blocks.power;

import dusted.world.interfaces.*;
import dusted.world.modules.*;
import mindustry.*;
import mindustry.ctype.*;
import mindustry.gen.*;
import mindustry.world.blocks.power.*;

//this is just painful
public class PowderConsumeGenerator extends ConsumeGenerator implements PowderBlockc {
    public boolean[] powderFilter = {};
    public float powderCapacity = 10f;

    public PowderConsumeGenerator(String name) {
        super(name);
    }

    @Override
    public void init() {
        powderFilter = new boolean[Vars.content.getBy(ContentType.effect_UNUSED).size];
        super.init();
    }

    @Override
    public boolean[] powderFilters() {
        return powderFilter;
    }

    @Override
    public float powderCapacity() {
        return powderCapacity;
    }

    public class PowderConsumeGeneratorBuild extends ConsumeGeneratorBuild implements PowderBuildc {
        public PowderModule powders = new PowderModule();

        @Override
        public Building build() {
            return this;
        }

        @Override
        public PowderModule powderModule() {
            return powders;
        }
    }
}
