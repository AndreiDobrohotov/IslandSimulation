package island;

import animals.Animal;
import animals.Herbivorous;
import lombok.Getter;

import java.text.DecimalFormat;
import java.util.*;

public class EventLog {
    @Getter
    private static int numberOfIteration = 0;
    private final static String ANIMAL_DIED_OF_HUNGER = "%s умер от голода.";
    private final static String ANIMAL_ATE_ANIMAL = "%s съел %s.";
    private final static String ANIMAL_ATE_GRASS = "%s съел %s кг травы.";
    private final static String ANIMAL_MATED = "%s спарился с %s.";
    private final static String ANIMAL_GAVE_A_BIRTH = "%s родила %s.";
    private final static String ANIMAL_RAN_AWAY_FROM = "%s убежал от %s.";
    private final static String ANIMAL_LEFT_THE_FIELD = "%s покинул область.";
    private final static String ANIMAL_CAME_TO_THE_FIELD = "%s пришел в область.";
    private final static String ANIMAL_NO_CHILD = "%s не дала потомства.";
    private final static String ANIMAL_NO_PRAY = "%s не нашел добычу и остался голодным.";
    private final static String ANIMAL_NO_GRASS = "%s не нашел травы и остался голодным.";
    private final static String ANIMAL_NO_PARTNER = "%s не нашел партнера для спаривания.";

    public static void implementNumberOfIteration() {
        numberOfIteration++;
    }

    private static synchronized void addEvent(Field field, String message) {
        EventLog log = field.getEventLog();
        if (!log.getLogs().containsKey(numberOfIteration)) {
            log.getLogs().put(numberOfIteration, new ArrayList<>());
        }
        log.getLogs().get(numberOfIteration).add(message);
    }

    public static void animalDiedOfHunger(Animal animal) {
        addEvent(animal.getCurrentField(), String.format(ANIMAL_DIED_OF_HUNGER, animal.getName()));
    }

    public static void animalAteAnimal(Animal predator, Animal pray) {
        addEvent(predator.getCurrentField(), String.format(ANIMAL_ATE_ANIMAL, predator.getName(), pray.getName()));
    }

    public static void animalAteGrass(Herbivorous herbivorous, Double weightOfGrass) {
        addEvent(herbivorous.getCurrentField(), String.format(ANIMAL_ATE_GRASS, herbivorous.getName(), new DecimalFormat("#.##").format(weightOfGrass)));
    }

    public static void animalGaveABirth(Animal mother, Animal child) {
        addEvent(mother.getCurrentField(), String.format(ANIMAL_GAVE_A_BIRTH, mother.getName(), child.getName()));
    }

    public static void animalMated(Animal male, Animal female) {
        addEvent(male.getCurrentField(), String.format(ANIMAL_MATED, male.getName(), female.getName()));
    }

    public static void animalRanAwayFrom(Animal pray, Animal predator) {
        addEvent(pray.getCurrentField(), String.format(ANIMAL_RAN_AWAY_FROM, pray.getName(), predator.getName()));
    }

    public static void animalLeftTheField(Animal animal) {
        addEvent(animal.getCurrentField(), String.format(ANIMAL_LEFT_THE_FIELD, animal.getName()));
    }

    public static void animalCameToTheField(Animal animal) {
        addEvent(animal.getCurrentField(), String.format(ANIMAL_CAME_TO_THE_FIELD, animal.getName()));
    }

    public static void animalNoPray(Animal animal) {
        addEvent(animal.getCurrentField(), String.format(ANIMAL_NO_PRAY, animal.getName()));
    }

    public static void animalNoChild(Animal animal) {
        addEvent(animal.getCurrentField(), String.format(ANIMAL_NO_CHILD, animal.getName()));
    }

    public static void animalNoGrass(Animal animal) {
        addEvent(animal.getCurrentField(), String.format(ANIMAL_NO_GRASS, animal.getName()));
    }

    public static void animalNoPartner(Animal animal) {
        addEvent(animal.getCurrentField(), String.format(ANIMAL_NO_PARTNER, animal.getName()));
    }


    @Getter
    private final Map<Integer, List<String>> logs;

    public EventLog() {
        logs = new HashMap<>();
    }


}
