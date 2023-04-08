package island;

import animals.Animal;
import animals.enums.AnimalType;
import lombok.Getter;
import java.util.HashMap;
import java.util.Map;

@Getter
public class StatisticCollector {
    private final FieldInfo[][] fieldsInfo;
    private final Map<AnimalType, Integer> totalAnimalsCount;
    private final int width;
    private final int height;

    private double totalGrass;
    private int totalAnimalsEaten;
    private int totalAnimalsBorn;
    private int totalAnimalsDiedOfHunger;

    public StatisticCollector(int width, int height) {
        this.width = width;
        this.height = height;
        totalAnimalsBorn = 0;
        totalAnimalsEaten = 0;
        totalAnimalsDiedOfHunger = 0;
        totalGrass = 0;
        fieldsInfo = new FieldInfo[width][height];
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                fieldsInfo[i][j] = new FieldInfo();
            }
        }
        totalAnimalsCount = new HashMap<>();
        for (AnimalType type : AnimalType.values()) {
            totalAnimalsCount.put(type, 0);
        }
    }

    public void updateTotalInfo(Field[][] fields) {
        totalAnimalsBorn = 0;
        totalAnimalsEaten = 0;
        totalAnimalsDiedOfHunger = 0;
        totalGrass = 0;
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                fieldsInfo[i][j].updateFieldInfo(fields[i][j]);
                totalAnimalsBorn += fieldsInfo[i][j].getAnimalsBorn();
                totalAnimalsEaten += fieldsInfo[i][j].getAnimalsEaten();
                totalAnimalsDiedOfHunger += fieldsInfo[i][j].getAnimalsDiedOfHunger();
                totalGrass += fieldsInfo[i][j].getGrass();
                for(AnimalType type : AnimalType.values()) {
                    totalAnimalsCount.put(type, totalAnimalsCount.get(type) + fieldsInfo[i][j].getAnimalsCountOnField().get(type));
                }
            }
        }
    }
    
    @Getter
    private class FieldInfo {
        private double grass;
        private int animalsEaten;
        private int animalsBorn;
        private int animalsDiedOfHunger;
        private final Map<AnimalType, Integer> animalsCountOnField;

        public FieldInfo() {
            grass = 0;
            animalsCountOnField = new HashMap<>();
            for (AnimalType type : AnimalType.values()) {
                animalsCountOnField.put(type, 0);
            }
        }

        public void updateFieldInfo(Field field) {
            grass = field.getGrass();
            animalsEaten = field.getAnimalsEaten();
            animalsBorn = field.getAnimalsBorn();
            animalsDiedOfHunger = field.getAnimalsDiedOfHunger();
            for (AnimalType type : AnimalType.values()) {
                animalsCountOnField.put(type, field.getAnimalsCountOnField(type));
            }
        }

    }
}
