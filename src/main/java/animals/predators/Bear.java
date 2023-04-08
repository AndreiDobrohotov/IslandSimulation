package animals.predators;

import animals.Predator;

public class Bear extends Predator {
    private static int count = 0;

    public Bear() {
        super(++count);
    }

}
