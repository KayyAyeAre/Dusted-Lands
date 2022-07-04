package dusted.world.consumers;

import arc.scene.ui.layout.*;
import arc.struct.*;
import dusted.type.*;
import dusted.world.blocks.powder.*;
import dusted.world.interfaces.*;
import dusted.world.meta.DustedStatValues;
import mindustry.gen.*;
import mindustry.ui.*;
import mindustry.world.*;
import mindustry.world.meta.*;

import static mindustry.Vars.iconMed;

public class ConsumePowder extends ConsumePowderBase {
    public Powder powder;

    public ConsumePowder(Powder powder, float amount) {
        super(amount);
        this.powder = powder;
    }

    @Override
    public void apply(Block block) {
        if (block instanceof PowderBlockc pow) pow.powderFilters()[powder.id] = true;
    }

    @Override
    public void build(Building build, Table table) {
        table.add(new ReqImage(powder.uiIcon, () -> build instanceof PowderBuildc pow && pow.powderModule().get(powder) > 0)).size(iconMed).top().left();
    }

    @Override
    public void update(Building entity) {
        if (entity instanceof PowderBuildc build) {
            build.powderModule().remove(powder, amount * entity.edelta());
        }
    }

    @Override
    public float efficiency(Building build) {
        return Math.min(((PowderBuildc) build).powderModule().get(powder) / (amount * build.edelta()), 1f);
    }

    @Override
    public void display(Stats stats) {
        stats.add(booster ? Stat.booster : Stat.input, DustedStatValues.powder(powder, amount * 60f, true));
    }
}
