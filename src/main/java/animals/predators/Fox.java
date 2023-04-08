package animals.predators;

import animals.Predator;

public class Fox extends Predator {
    private static int count = 0;

    public Fox() {
        super(++count);
    }
}
