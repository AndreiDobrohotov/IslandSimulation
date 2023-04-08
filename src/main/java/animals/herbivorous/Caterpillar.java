package animals.herbivorous;

import animals.Herbivorous;

public class Caterpillar extends Herbivorous {
    private static int count = 0;

    public Caterpillar() {
        super(++count);
    }
}
