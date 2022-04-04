package dusted.world.blocks.powder;

import arc.*;
import arc.struct.*;
import arc.util.io.*;
import dusted.type.*;
import dusted.world.consumers.*;
import dusted.world.interfaces.*;
import dusted.world.meta.CustomStatValue;
import dusted.world.modules.*;
import mindustry.*;
import mindustry.ctype.*;
import mindustry.gen.*;
import mindustry.ui.*;
import mindustry.world.*;
import mindustry.world.meta.*;

public class PowderBlock extends Block implements CustomReplacec {
    public Bits powderFilters = new Bits(Vars.content.getBy(ContentType.effect_UNUSED).size);
    public float powderCapacity = 20f;

    public PowderBlock(String name) {
        super(name);
        update = true;
        solid = true;
    }

    @Override
    public boolean canReplace(Block other) {
        return customReplace(this, other);
    }

    @Override
    public void init() {
        super.init();
        consumes.each(cons -> {
            if (cons instanceof ConsumePowderBase pcons) pcons.addPowderFilters(powderFilters);
        });
    }

    @Override
    public void setStats() {
        super.setStats();
        new CustomStatValue("powder-capacity", StatValues.number(powderCapacity, StatUnit.none)).add(stats);
    }

    @Override
    public void setBars() {
        super.setBars();

        bars.add("powders", build -> {
            PowderBuild entity = (PowderBuild) build;
            Powder powder = entity.powders.current();
            return new Bar(() -> entity.powders.get(powder) <= 0.001f ? Core.bundle.get("bar.powder") : powder.localizedName, () -> powder.color, () -> entity.powders.get(powder) / powderCapacity);
        });
    }

    @Override
    public String replaceType() {
        return "powder";
    }

    public class PowderBuild extends Building implements PowderBlockc {
        public PowderModule powders = new PowderModule();

        @Override
        public void write(Writes write) {
            super.write(write);
            powders.write(write);
        }

        @Override
        public void read(Reads read, byte revision) {
            super.read(read, revision);
            powders.read(read);
        }

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
    }
}
