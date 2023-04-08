package animals.predators;

import animals.Predator;

public class Eagle extends Predator {
    private static int count = 0;

    public Eagle() {
        super(++count);
    }
}
