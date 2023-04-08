package animals.herbivorous;

import animals.Herbivorous;

public class Boar extends Herbivorous {

    private static int count = 0;

    public Boar() {
        super(++count);
    }

}
