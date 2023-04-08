package animals.herbivorous;

import animals.Herbivorous;

public class Duck extends Herbivorous {
    private static int count = 0;

    public Duck() {
        super(++count);
    }
}
