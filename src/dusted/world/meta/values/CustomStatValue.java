package dusted.world.meta.values;

import arc.*;
import arc.scene.ui.layout.*;
import arc.struct.*;
import mindustry.world.meta.*;
import mindustry.world.meta.values.*;

public class CustomStatValue implements StatValue {
    public String name;
    public StatValue value;
    public StatCat category = StatCat.general;

    public CustomStatValue(String name, StatValue value) {
        this.name = name;
        this.value = value;
    }

    public CustomStatValue(String name, float value, StatUnit unit) {
        this(name, new NumberValue(value, unit));
    }

    public CustomStatValue(String name, float value) {
        this(name, value, StatUnit.none);
    }

    public CustomStatValue(String name, boolean value) {
        this(name, new BooleanValue(value));
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
            table.clearChildren();
            table.add("[lightgray]" + Core.bundle.format("stat." + name) + ":[] ").left();
            value.display(table);
            table.add().size(10f);
        });
    }
}
