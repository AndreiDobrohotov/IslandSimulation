package animals.herbivorous;

import animals.Herbivorous;

public class Mouse extends Herbivorous {
    private static int count = 0;

    public Mouse() {
        super(++count);
    }
}
