package statictic;

import animals.enums.AnimalType;
import island.Field;
import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

//класс собирает общую статистику со всех полей и передает в пользовательский интерфейс
@Getter
public class StatisticCollector {
    private final FieldInfo[][] fieldsInfo;
    private final Map<AnimalType, Integer> totalAnimalsCountByType;
    private final int width;
    private final int height;

    private double totalGrass;
    private int totalAnimalsCount;
    private int totalAnimalsEaten;
    private int totalAnimalsBorn;
    private int totalAnimalsDiedOfHunger;

    public StatisticCollector(int width, int height) {
        this.width = width;
        this.height = height;
        fieldsInfo = new FieldInfo[width][height];
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                fieldsInfo[i][j] = new FieldInfo();
            }
        }
        totalAnimalsCountByType = new HashMap<>();
        dataReset();
    }

    //обнуляет старые данные перед подсчетом новых
    private void dataReset(){
        totalAnimalsBorn = 0;
        totalAnimalsEaten = 0;
        totalAnimalsDiedOfHunger = 0;
        totalGrass = 0;
        totalAnimalsCount = 0;
        for (AnimalType type : AnimalType.values()) {
            totalAnimalsCountByType.put(type, 0);
        }
    }


    public boolean updateTotalInfo(Field[][] fields) {
        dataReset();
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                fieldsInfo[i][j].updateFieldInfo(fields[i][j]);
                totalAnimalsBorn += fieldsInfo[i][j].getAnimalsBorn();
                totalAnimalsEaten += fieldsInfo[i][j].getAnimalsEaten();
                totalAnimalsDiedOfHunger += fieldsInfo[i][j].getAnimalsDiedOfHunger();
                totalGrass += fieldsInfo[i][j].getGrass();
                for(AnimalType type : AnimalType.values()) {
                    int animalsCountOnField = fieldsInfo[i][j].getAnimalsCountOnField().get(type);
                    totalAnimalsCountByType.put(type, totalAnimalsCountByType.get(type) + animalsCountOnField);
                    totalAnimalsCount += animalsCountOnField;
                }
            }
        }
        return totalAnimalsCount == 0;
    }

    public int getTotalAnimalsCountByType(AnimalType type) {
        return totalAnimalsCountByType.get(type);
    }

    
    @Getter
    public static class FieldInfo {
        private double grass;
        private int animalsEaten;
        private int animalsBorn;
        private int animalsDiedOfHunger;
        private final Map<AnimalType, Integer> animalsCountOnField;
        private EventLog eventLog;

        public FieldInfo() {
            grass = 0;
            animalsCountOnField = new HashMap<>();
            for (AnimalType type : AnimalType.values()) {
                animalsCountOnField.put(type, 0);
            }
        }

        public int getAnimalsCountOnField(AnimalType type) {
            return animalsCountOnField.get(type);
        }

        public void updateFieldInfo(Field field) {
            grass = field.getGrass();
            animalsEaten = field.getAnimalsEaten();
            animalsBorn = field.getAnimalsBorn();
            animalsDiedOfHunger = field.getAnimalsDiedOfHunger();
            for (AnimalType type : AnimalType.values()) {
                animalsCountOnField.put(type, field.getAnimalsCountOnField(type));
            }
            eventLog = field.getEventLog();
        }

    }
}
