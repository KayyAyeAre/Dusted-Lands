package dusted.world.consumers;

import arc.func.*;
import arc.scene.ui.layout.*;
import arc.struct.*;
import dusted.type.*;
import dusted.world.interfaces.*;
import dusted.world.meta.values.*;
import mindustry.*;
import mindustry.ctype.*;
import mindustry.gen.*;
import mindustry.ui.*;
import mindustry.world.meta.*;

public class ConsumePowderFilter extends ConsumePowderBase {
    public final Boolf<Powder> filter;

    public ConsumePowderFilter(Boolf<Powder> filter, float amount) {
        super(amount);
        this.filter = filter;
    }

    @Override
    public void addPowderFilters(Bits arr) {
        Vars.content.<Powder>getBy(ContentType.effect_UNUSED).each(filter, p -> arr.set(p.id));
    }

    @Override
    public void build(Building build, Table table) {
        Seq<Powder> list = Vars.content.<Powder>getBy(ContentType.effect_UNUSED).select(p -> !p.isHidden() && filter.get(p));
        MultiReqImage image = new MultiReqImage();
        list.each(powder -> image.add(new ReqImage(powder.icon(Cicon.medium), () ->
                build instanceof PowderBlockc entity && entity.powderModule().current() == powder && entity.powderModule().get(powder) >= Math.max(use(entity), amount * build.delta()))));

        table.add(image).size(8 * 4);
    }

    @Override
    public String getIcon() {
        return null;
    }

    @Override
    public void update(Building entity) {
        if (entity instanceof PowderBlockc build) {
            build.powderModule().remove(build.powderModule().current(), use(build));
        }
    }

    @Override
    public boolean valid(Building entity) {
        return entity instanceof PowderBlockc build && filter.get(build.powderModule().current()) && build.powderModule().currentAmount() >= use(build);
    }

    @Override
    public void display(Stats stats) {
        stats.add(booster ? Stat.booster : Stat.input, new PowderFilterValue(filter, amount * 60f, true));
    }
}
