package dusted.world.meta.values;

import arc.func.*;
import arc.scene.ui.layout.*;
import arc.struct.*;
import dusted.type.*;
import dusted.ui.*;
import mindustry.*;
import mindustry.ctype.*;
import mindustry.world.meta.*;

public class PowderFilterValue implements StatValue {
    private final Boolf<Powder> filter;
    private final float amount;
    private final boolean perSecond;

    public PowderFilterValue(Boolf<Powder> filter, float amount, boolean perSecond) {
        this.filter = filter;
        this.amount = amount;
        this.perSecond = perSecond;
    }

    @Override
    public void display(Table table) {
        Seq<Powder> list = Vars.content.<Powder>getBy(ContentType.effect_UNUSED).select(p -> !p.isHidden() && filter.get(p));

        for (int i = 0; i < list.size; i++) {
            table.add(new PowderDisplay(list.get(i), amount, perSecond)).padRight(5);
            if (i != list.size - 1) {
                table.add("/");
            }
        }
    }
}
