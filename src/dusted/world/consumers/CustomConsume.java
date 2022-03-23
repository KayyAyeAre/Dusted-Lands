package dusted.world.consumers;

import arc.util.*;
import mindustry.world.consumers.*;

import java.util.*;

public interface CustomConsume {
    Consume consume();

    default void add(Consumers consumers) {
        Consume[] map = Arrays.copyOf(Reflect.<Consume[]>get(consumers, "map"), 4);
        map[3] = consume();
        Reflect.set(consumers, "map", map);
    }
}
