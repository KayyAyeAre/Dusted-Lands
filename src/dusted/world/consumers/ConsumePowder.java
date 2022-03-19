package dusted.world.consumers;

import arc.scene.ui.layout.*;
import arc.struct.*;
import arc.util.*;
import dusted.type.*;
import dusted.world.interfaces.*;
import dusted.world.meta.values.*;
import mindustry.gen.*;
import mindustry.ui.*;
import mindustry.world.meta.*;

public class ConsumePowder extends ConsumePowderBase {
    public Powder powder;

    public ConsumePowder(Powder powder, float amount) {
        super(amount);
        this.powder = powder;
    }

    @Override
    public void addPowderFilters(Bits filter) {
        filter.set(powder.id);
    }

    @Override
    public void build(Building tile, Table table) {
        table.add(new ReqImage(powder.icon(Cicon.medium), () -> valid(tile))).size(8 * 4);
    }

    @Override
    public String getIcon() {
        return null;
    }

    @Override
    public void update(Building entity) {
        if (entity instanceof PowderBlockc build) {
            build.powderModule().remove(powder, Math.min(use(build), build.powderModule().get(powder)));
        }
    }

    @Override
    public boolean valid(Building entity) {
        return entity instanceof PowderBlockc build && build.powderModule().get(powder) >= amount * entity.delta();
    }

    @Override
    public void display(Stats stats) {
        stats.add(booster ? Stat.booster : Stat.input, new PowderValue(powder, amount * 60f, true));
    }
}
