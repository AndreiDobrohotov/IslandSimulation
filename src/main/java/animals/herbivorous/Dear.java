package animals.herbivorous;

import animals.Herbivorous;

public class Dear extends Herbivorous {
    private static int count = 0;

    public Dear() {
        super(++count);
    }
}
