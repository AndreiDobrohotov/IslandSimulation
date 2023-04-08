package animals.herbivorous;

import animals.Herbivorous;

public class Goat extends Herbivorous {
    private static int count = 0;

    public Goat() {
        super(++count);
    }
}
