package animals.herbivorous;

import animals.Herbivorous;

public class Buffalo extends Herbivorous {

    private static int count = 0;

    public Buffalo() {
        super(++count);
    }
}
