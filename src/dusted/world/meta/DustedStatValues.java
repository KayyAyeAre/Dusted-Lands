package dusted.world.meta;

import arc.func.*;
import arc.struct.*;
import dusted.type.*;
import dusted.ui.*;
import mindustry.*;
import mindustry.ctype.*;
import mindustry.world.meta.*;

public class DustedStatValues {
    public static StatValue powder(Powder powder, float amount, boolean perSecond) {
        return table -> table.add(new PowderDisplay(powder, amount, perSecond));
    }

    public static StatValue powders(Boolf<Powder> filter, float amount, boolean perSecond) {
        return table -> {
            Seq<Powder> list = Vars.content.<Powder>getBy(ContentType.effect_UNUSED).select(p -> !p.isHidden() && filter.get(p));

            for (int i = 0; i < list.size; i++) {
                table.add(new PowderDisplay(list.get(i), amount, perSecond)).padRight(5);
                if (i != list.size - 1) {
                    table.add("/");
                }
            }
        };
    }
}
