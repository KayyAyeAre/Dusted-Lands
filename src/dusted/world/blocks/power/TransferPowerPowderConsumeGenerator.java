package dusted.world.blocks.power;

import arc.*;
import dusted.type.*;
import dusted.world.interfaces.*;
import dusted.world.meta.*;
import dusted.world.modules.*;
import mindustry.*;
import mindustry.ctype.*;
import mindustry.ui.*;

//hi there, you're probably wondering how i got myself into this mess
public class TransferPowerPowderConsumeGenerator extends TransferPowerConsumeGenerator implements PowderBlockc {
    public boolean[] powderFilter = {};
    public float powderCapacity = 10f;

    public TransferPowerPowderConsumeGenerator(String name) {
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

    @Override
    public void setStats() {
        super.setStats();

        stats.add(DustedStats.powderCapacity, powderCapacity, DustedStatUnits.powderUnits);
    }

    @Override
    public void setBars() {
        super.setBars();

        addBar("powders", build -> {
            TransferPowerPowderConsumeGeneratorBuild entity = (TransferPowerPowderConsumeGeneratorBuild) build;
            Powder powder = entity.powders.current();
            return new Bar(() -> entity.powders.get(powder) <= 0.001f ? Core.bundle.get("bar.powder") : powder.localizedName, () -> powder.color, () -> entity.powders.get(powder) / powderCapacity);
        });
    }

    public class TransferPowerPowderConsumeGeneratorBuild extends TransferPowerConsumeGeneratorBuild implements PowderBuildc {
        public PowderModule powders = new PowderModule();

        @Override
        public PowderModule powderModule() {
            return powders;
        }
    }
}
