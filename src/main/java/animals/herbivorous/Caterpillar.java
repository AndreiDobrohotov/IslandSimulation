package animals.herbivorous;

import animals.Herbivorous;
import island.Field;

import java.util.List;

public class Caterpillar extends Herbivorous {
    private static int count = 0;

    public Caterpillar() {
        super(++count);
    }

    @Override
    public void move(List<Field> canGoTo) {
        //dont move
    }
}
