package island;

import lombok.Getter;

//энам отвечающий за тип поля. Определяет лимит травы, которая может вырасти.
public enum FieldType {
    GRASS(200,100),
    GROUND(150,75),
    ROCK(100,50),
    SAND(50,25);

    FieldType(int grassLimit, int grassGrowth) {
        this.grassLimit = grassLimit;
        this.grassGrowth = grassGrowth;

    }

    @Getter private final int grassLimit;
    @Getter private final int grassGrowth;
}
