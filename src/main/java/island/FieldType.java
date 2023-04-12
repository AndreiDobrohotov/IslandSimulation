package island;

import lombok.Getter;

//энам отвечающий за тип поля. Определяет лимит травы, которая может вырасти.
public enum FieldType {
    GRASS(400,200),
    GROUND(300,150),
    ROCK(150,75),
    SAND(50,25);

    FieldType(int grassLimit, int grassGrowth) {
        this.grassLimit = grassLimit;
        this.grassGrowth = grassGrowth;

    }

    @Getter private final int grassLimit;
    @Getter private final int grassGrowth;
}
