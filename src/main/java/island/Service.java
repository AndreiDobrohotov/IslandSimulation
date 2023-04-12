package island;

import animals.Animal;
import animals.enums.AnimalType;
import animals.enums.Gender;
import controller.Controller;
import lombok.Getter;
import lombok.Setter;
import statictic.EventLog;
import statictic.StatisticCollector;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.*;

//класс который отвечает за работу острова
public class Service {
    @Getter
    private final Field[][] fields;
    @Getter
    @Setter
    private boolean isRunning = false;
    private final int width;
    private final int height;
    @Getter
    private final StatisticCollector statisticCollector;
    @Setter
    private Controller controller;
    private ScheduledExecutorService executorService;

    //конструктор принимает размер острова и количество случайных животных
    public Service(int width, int height, int predatorCount, int herbivorousCount) {
        this.width = width;
        this.height = height;
        fields = new Field[width][height];
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                fields[i][j] = new Field(i, j);

                //создаем песочный пляж по краю острова
                if (i == 0 || i == width - 1 || j == 0 || j == height - 1) {
                    fields[i][j].setType(FieldType.SAND);
                }
                //остальные поля заполняем случайными клетками с разной степенью травянистости
                else {
                    fields[i][j].setType(FieldType.values()[ThreadLocalRandom.current().nextInt(FieldType.values().length - 1)]);
                }
                fields[i][j].growGrass();
            }
        }
        //заполняем поля животными
        fillingTheIslandWithAnimals(predatorCount, herbivorousCount);
        //создаем класс статистика и обновляем ее
        statisticCollector = new StatisticCollector(width, height);
        statisticCollector.updateTotalInfo(fields);
    }

    //метод заполнения случайных полей животными. Принимает количество хищников и травоядных
    public void fillingTheIslandWithAnimals(int predatorCount, int herbivorousCount) {
        for (int i = 0; i < predatorCount; i++) {
            int x = ThreadLocalRandom.current().nextInt(width);
            int y = ThreadLocalRandom.current().nextInt(height);
            fields[x][y].createAnimalOnField(AnimalType.getRandomPredatorType());
        }
        for (int i = 0; i < herbivorousCount; i++) {
            int x = ThreadLocalRandom.current().nextInt(width);
            int y = ThreadLocalRandom.current().nextInt(height);
            fields[x][y].createAnimalOnField(AnimalType.getRandomHerbivorousType());
        }
    }

    //метод получает поле и скорость, и возвращает список полей, в которые можно переместиться
    private synchronized List<Field> whereCanGo(Field field, int speed) {
        List<Field> result = new ArrayList<>();
        int xMax = Math.min(field.getX() + speed, width - 1);
        int xMin = Math.max(field.getX() - speed, 0);
        int yMax = Math.min(field.getY() + speed, height - 1);
        int yMin = Math.max(field.getY() - speed, 0);
        for (int i = xMin; i <= xMax; i++) {
            for (int j = yMin; j <= yMax; j++) {
                if (fields[i][j] != field) {
                    result.add(fields[i][j]);
                }
            }
        }
        Collections.shuffle(result);
        return result;
    }

    //метод запускает поток, который отвечает за жизненые циклы острова.
    //переодичность получаем из пользовательского интерфейса.
    public void start(int frequency) {
        if (isRunning) {
            return;
        }
        isRunning = true;
        executorService = Executors.newSingleThreadScheduledExecutor();
        executorService.scheduleAtFixedRate(() -> {
            EventLog.implementNumberOfIteration();
            feedAnimals();
            removeAllDead();
            moveAnimals();
            reproduceAnimals();
            growGrassOnFields();
            if(statisticCollector.updateTotalInfo(fields)){
                controller.gameOver();
                stop();
            }
            controller.updateAllUI();


        }, 0, frequency, TimeUnit.SECONDS);
    }

    //даем команду остановится главному потоку и ждем завершения.
    public void stop() {
        if (!isRunning) {
            return;
        }
        isRunning = false;
        executorService.shutdown();
        try {
            executorService.awaitTermination(Integer.MAX_VALUE, TimeUnit.MICROSECONDS);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    //метод который проходится по всем полям острова и запускает рост травы
    private void growGrassOnFields() {
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                fields[i][j].growGrass();
            }
        }
    }

    //метод который проходится по всем полям острова и запускает поток для каждого из них
    //вызываем метод "eat()" для каждого животного на поле
    private void feedAnimals() {
        try (ExecutorService executorService = Executors.newCachedThreadPool()) {
            ArrayList<Runnable> tasks = new ArrayList<>();
            for (int i = 0; i < width; i++) {
                for (int j = 0; j < height; j++) {
                    Field field = fields[i][j];
                    Runnable runnable = () -> {
                        for (AnimalType type : AnimalType.values()) {
                            for (Animal animal : field.getAnimalsOnFieldByType(type)) {
                                animal.eat();
                            }
                        }
                    };
                    tasks.add(runnable);
                }
            }
            for (Runnable runnable : tasks) {
                executorService.execute(runnable);
            }
            executorService.shutdown();
            executorService.awaitTermination(Integer.MAX_VALUE, TimeUnit.MICROSECONDS);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    //вызывает метод чистки у каждого поля
    private void removeAllDead() {
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                fields[i][j].removeDeadAnimals();
            }
        }
    }

    //метод который проходится по всем полям острова и запускает поток для каждого из них
    //вызываем метод "move()" для каждого животного на поле
    private void moveAnimals() {
        try (ExecutorService executorService = Executors.newCachedThreadPool()) {
            ArrayList<Runnable> tasks = new ArrayList<>();
            for (int i = 0; i < width; i++) {
                for (int j = 0; j < height; j++) {
                    Field field = fields[i][j];
                    for (AnimalType type : AnimalType.values()) {
                        for (Animal animal : field.getAnimalsOnFieldByType(type)) {
                            Runnable runnable = () -> {
                                animal.move(whereCanGo(animal.getCurrentField(), animal.getSpeed()));
                            };
                            tasks.add(runnable);
                        }
                    }
                }
            }
            for (Runnable runnable : tasks) {
                executorService.execute(runnable);
            }
            executorService.shutdown();
            executorService.awaitTermination(Integer.MAX_VALUE, TimeUnit.MICROSECONDS);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    //метод который проходится по всем полям острова и запускает поток для каждого из них
    //вызываем метод "reproduction" для каждого животного на поле
    private void reproduceAnimals() {
        try (ExecutorService executorService = Executors.newCachedThreadPool()) {
            ArrayList<Runnable> tasks = new ArrayList<>();
            for (int i = 0; i < width; i++) {
                for (int j = 0; j < height; j++) {
                    Field field = fields[i][j];
                    for (AnimalType type : AnimalType.values()) {
                        Runnable runnable = () -> {
                            //проходимся по всем самцам и оплодотворяем самок
                            field.getAnimalsOnFieldByType(type)
                                    .stream()
                                    .filter(animal -> animal.getGender() == Gender.MALE)
                                    .forEach(Animal::reproduction);
                            //проходимся по всем беременным самкам и осуществляем рождение детенышей
                            field.getAnimalsOnFieldByType(type)
                                    .stream()
                                    .filter(Animal::isPregnant)
                                    .toList()
                                    .forEach(Animal::reproduction);
                            //создаем копию листа, чтобы при добавлении детенышей, они не попали в итерацию
                        };
                        tasks.add(runnable);
                    }

                }
            }
            for (Runnable runnable : tasks) {
                executorService.execute(runnable);
            }
            executorService.shutdown();
            executorService.awaitTermination(Integer.MAX_VALUE, TimeUnit.MICROSECONDS);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

}
