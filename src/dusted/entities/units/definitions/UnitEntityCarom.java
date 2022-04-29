package dusted.entities.units.definitions;

import dusted.entities.units.*;

//i have no clue if this is a good idea or not but its the easiest implementation so im going with it
public class UnitEntityCarom extends BouncingUnitEntity {
    public static int classID;

    @Override
    public int classId() {
        return classID;
    }
}
