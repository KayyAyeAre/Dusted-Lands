package dusted.world.blocks.powder;

public class ChuteDrive extends Chute {
    public int outputCharge = 16;

    public ChuteDrive(String name) {
        super(name);
        hasPower = true;
    }

    public class ChuteDriveBuild extends ChuteBuild {
        @Override
        public void updateTile() {
            if (consValid()) {
                charge = outputCharge;
            } else {
                charge = 0;
            }

            super.updateTile();
        }
    }
}
