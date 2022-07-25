package dusted.world.modules;

import arc.util.*;
import arc.util.io.*;
import dusted.type.*;
import mindustry.*;
import mindustry.ctype.*;
import mindustry.world.modules.*;

import java.util.*;

public class PowderModule extends BlockModule {
    private float[] powders = new float[Vars.content.getBy(ContentType.effect_UNUSED).size];
    private float total;
    private Powder current = Vars.content.getByID(ContentType.effect_UNUSED, 0);

    public void add(Powder powder, float amount) {
        powders[powder.id] += amount;
        total += amount;
        current = powder;
    }

    public void remove(Powder powder, float amount) {
        add(powder, -amount);
    }

    public float get(Powder powder) {
        return powders[powder.id];
    }

    public float total() {
        return total;
    }

    public Powder current() {
        return current;
    }

    public float currentAmount() {
        return powders[current.id];
    }

    public void clear() {
        total = 0;
        Arrays.fill(powders, 0);
    }

    @Override
    public void write(Writes write) {
        int amount = 0;
        for (float powder : powders) {
            if (powder > 0) amount++;
        }

        write.s(amount);

        for (int i = 0; i < powders.length; i++) {
            if (powders[i] > 0) {
                write.s(i);
                write.f(powders[i]);
            }
        }
    }

    //TODO broken
    @Override
    public void read(Reads read) {
        Arrays.fill(powders, 0);
        total = 0f;
        int count = read.s();

        Log.info("read start");
        for (int i = 0; i < count; i++) {
            int id = read.s();
            float amount = read.f();
            powders[id] = amount;
            if (amount > 0f) {
                current = Vars.content.getByID(ContentType.effect_UNUSED, id);
                Log.info("set current to @ with @ contained", current, amount);
            }
            this.total += amount;
        }
        Log.info("read end");
    }
}
