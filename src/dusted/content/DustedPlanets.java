package dusted.content;

import mindustry.type.*;

public class DustedPlanets {
    public static Planet krakai;

    public static void load() {
        //TODO make generators and maps
        krakai = new Planet("krakai", null, 3, 1.6f);
    }
}
