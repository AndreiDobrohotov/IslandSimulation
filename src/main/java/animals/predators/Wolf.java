package animals.predators;

import animals.Predator;

public class Wolf extends Predator {
    private static int count = 0;

    public Wolf() {
        super(++count);
    }

}
