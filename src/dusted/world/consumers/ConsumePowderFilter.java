package dusted.world.consumers;

import arc.func.*;
import arc.scene.ui.layout.*;
import arc.struct.*;
import arc.util.*;
import dusted.type.*;
import dusted.world.interfaces.*;
import dusted.world.meta.*;
import mindustry.*;
import mindustry.ctype.*;
import mindustry.gen.*;
import mindustry.ui.*;
import mindustry.world.*;
import mindustry.world.meta.*;

public class ConsumePowderFilter extends ConsumePowderBase {
    public Boolf<Powder> filter;

    public ConsumePowderFilter(Boolf<Powder> filter, float amount) {
        super(amount);
        this.filter = filter;
    }

    public ConsumePowderFilter() {
    }

    @Override
    public void apply(Block block) {
        if (block instanceof PowderBlockc pow)
            Vars.content.<Powder>getBy(ContentType.effect_UNUSED).each(filter, p -> pow.powderFilters()[p.id] = true);
    }

    public @Nullable Powder getConsumed(Building build) {
        if (!(build instanceof PowderBuildc pow)) return null;
        if (filter.get(pow.powderModule().current()) && pow.powderModule().currentAmount() > 0) {
            return pow.powderModule().current();
        }

        Seq<Powder> powders = Vars.content.getBy(ContentType.effect_UNUSED);

        for (int i = 0; i < powders.size; i++) {
            Powder powder = powders.get(i);
            if (filter.get(powder) && pow.powderModule().get(powder) > 0) {
                return powder;
            }
        }
        return null;
    }

    @Override
    public void build(Building build, Table table) {
        Seq<Powder> list = Vars.content.<Powder>getBy(ContentType.effect_UNUSED).select(p -> !p.isHidden() && filter.get(p));
        MultiReqImage image = new MultiReqImage();
        list.each(powder -> image.add(new ReqImage(powder.uiIcon, () ->
                build instanceof PowderBuildc entity && entity.powderModule().current() == powder && entity.powderModule().get(powder) > 0)));

        table.add(image).size(8 * 4);
    }

    @Override
    public float efficiency(Building build) {
        Powder powder = getConsumed(build);
        return powder != null ? Math.min(((PowderBuildc) build).powderModule().get(powder) / (amount * build.edelta()), 1f) : 0f;
    }

    @Override
    public void update(Building entity) {
        if (entity instanceof PowderBuildc build) {
            build.powderModule().remove(build.powderModule().current(), amount * entity.edelta());
        }
    }

    @Override
    public void display(Stats stats) {
        stats.add(booster ? Stat.booster : Stat.input, DustedStatValues.powders(filter, amount * 60f, true));
    }
}
