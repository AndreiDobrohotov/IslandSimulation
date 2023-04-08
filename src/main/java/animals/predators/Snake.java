package animals.predators;

import animals.Predator;

public class Snake extends Predator {
    private static int count = 0;

    public Snake() {
        super(++count);
    }
}
