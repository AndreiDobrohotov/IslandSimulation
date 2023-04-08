package animals.herbivorous;

import animals.Herbivorous;

public class Sheep extends Herbivorous {
    private static int count = 0;

    public Sheep() {
        super(++count);
    }
}
