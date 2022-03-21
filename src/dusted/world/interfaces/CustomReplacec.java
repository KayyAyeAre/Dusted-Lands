package dusted.world.interfaces;

import mindustry.world.*;

import java.util.*;

public interface CustomReplacec {
    String replaceType();

    default boolean replaceable(Block replacement, Block other) {
        if (other.alwaysReplace) return true;
        return other.replaceable && (other != this || replacement.rotate) && other instanceof CustomReplacec replace && Objects.equals(replace.replaceType(), replaceType()) &&
                (replacement.size == other.size || (replacement.size >= other.size && (replacement.subclass != null && replacement.subclass == other.subclass)));
    }
}
