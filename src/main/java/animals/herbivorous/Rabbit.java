package animals.herbivorous;

import animals.Herbivorous;

public class Rabbit extends Herbivorous {
    private static int count = 0;

    public Rabbit() {
        super(++count);
    }
}
