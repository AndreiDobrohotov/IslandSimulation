package animals.herbivorous;


import animals.Herbivorous;

public class Horse extends Herbivorous {
    private static int count = 0;

    public Horse() {
        super(++count);
    }
}
