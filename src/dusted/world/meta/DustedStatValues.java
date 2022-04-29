package dusted.world.meta;

import arc.*;
import arc.func.*;
import arc.scene.ui.layout.*;
import arc.struct.*;
import dusted.type.Powder;
import dusted.ui.PowderDisplay;
import mindustry.Vars;
import mindustry.ctype.ContentType;
import mindustry.graphics.*;
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

    public static void customStats(Stats stats, Cons<CustomStatContainer> cons) {
        var map = stats.toMap();

        for (Stat stat : Stat.values()) {
            if (map.containsKey(stat.category) &&
                    map.get(stat.category).containsKey(stat) &&
                    map.get(stat.category).get(stat).contains(sstat -> sstat instanceof CustomStatContainer)) {
                cons.get((CustomStatContainer) map.get(stat.category).get(stat).find(sstat -> sstat instanceof CustomStatContainer));
                return;
            }
        }

        CustomStatContainer out = new CustomStatContainer();
        cons.get(out);

        for (Stat stat : Stat.values()) {
            if (map.containsKey(stat.category) && map.get(stat.category).containsKey(stat)) {
                stats.add(stat, out);
                break;
            }
        }
    }

    public static class CustomStatContainer implements StatValue {
        public ObjectMap<String, Seq<StatValue>> statmap = new ObjectMap<>();

        public void addCStat(String name, StatValue value) {
            statmap.get(name, Seq::new).add(value);
        }

        @Override
        public void display(Table table) {
            table.fill(t -> t.name = "cstatmarker");
            Core.app.post(() -> {
                table.getChildren().get(table.getChildren().indexOf(Core.scene.find("cstatmarker"))).remove();
                Table parent = (Table) table.parent;
                parent.add("@category.custom").color(Pal.accent).fillX();
                parent.row();
                for (String name : statmap.keys()) {
                    parent.table(inset -> {
                        inset.left();
                        inset.add("[lightgray]" + Core.bundle.format("stat." + name) + ":[] ").left().top();
                        statmap.get(name).each(value -> {
                            value.display(inset);
                            inset.add().size(10f);
                        });
                    }).fillX().padLeft(10f);
                    parent.row();
                }
            });
        }
    }
}
