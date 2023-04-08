package island;

import animals.Animal;
import animals.enums.AnimalType;
import lombok.Getter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class Service {
    @Getter
    private final Field[][] fields;
    private final int width;
    private final int height;

    //конструктор принимает размер острова и количество случайных животных сгенерированных для каждой клетки
    public Service(int width, int height, int predatorCount, int herbivorousCount) {
        this.width = width;
        this.height = height;
        fields = new Field[width][height];
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                fields[i][j] = new Field(i, j);
                fields[i][j].fillingTheFieldWithAnimals(predatorCount, herbivorousCount);
            }
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

    public void iterate() {
        System.out.println("test1");
        ScheduledExecutorService executorService = Executors.newScheduledThreadPool(1);

        //executorService.scheduleAtFixedRate(test,1,5, TimeUnit.SECONDS);
        executorService.scheduleAtFixedRate(() -> {
                EventLog.implementNumberOfIteration();
                feedAnimals();
                moveAnimals();
                reproduceAnimals();
                System.out.println("test2");
                for (int i = 0; i < width; i++) {
                    for (int j = 0; j < height; j++) {
                        fields[i][j].print();
                    }
                }
                System.out.println();
            },1,5, TimeUnit.SECONDS);

        //executorService.shutdown();
        System.out.println("test3");
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
                            for (Animal animal : new ArrayList<>(field.getAnimalsOnField(type))) {
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

    //метод который проходится по всем полям острова и запускает поток для каждого из них
    //вызываем метод "move()" для каждого животного на поле
    private void moveAnimals() {
        try (ExecutorService executorService = Executors.newCachedThreadPool()) {
            ArrayList<Runnable> tasks = new ArrayList<>();
            for (int i = 0; i < width; i++) {
                for (int j = 0; j < height; j++) {
                    Field field = fields[i][j];
                    for (AnimalType type : AnimalType.values()) {
                        for (Animal animal : field.getAnimalsOnField(type)) {
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
                    Runnable runnable = () -> {
                        for (AnimalType type : AnimalType.values()) {
                            //создаем копию листа, чтобы при добавлении детенышей, они не попали в итерацию
                            List<Animal> copyOfAnimalList = new ArrayList<>(field.getAnimalsOnField(type));
                            for (Animal animal : copyOfAnimalList) {
                                animal.reproduction(copyOfAnimalList);
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

}
