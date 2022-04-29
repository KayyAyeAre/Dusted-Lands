package dusted.world.meta;

import arc.*;
import arc.scene.ui.layout.*;
import arc.struct.*;
import mindustry.world.meta.*;

public class CustomStatValue implements StatValue {
    public String name;
    public StatValue value;
    public StatCat category = StatCat.general;

    public CustomStatValue(String name, StatValue value) {
        this.name = name;
        this.value = value;
    }

    public void add(Stats stats) {
        if (!stats.toMap().containsKey(category)) {
            stats.toMap().put(category, new OrderedMap<>());
        }

        OrderedMap<Stat, Seq<StatValue>> map = stats.toMap().get(category);

        for (Stat stat : Stat.values()) {
            if (map.containsKey(stat)) continue;
            stats.add(stat, this);
            break;
        }
    }

    @Override
    public void display(Table table) {
        //TODO maybe change this implementation
        Core.app.post(() -> {
            ((Table) table.parent).table(inset -> {
                inset.add("[lightgray]" + Core.bundle.format("stat." + name) + ":[] ").left();
                value.display(inset);
                inset.add().size(10f);
            });
        });
    }
}
