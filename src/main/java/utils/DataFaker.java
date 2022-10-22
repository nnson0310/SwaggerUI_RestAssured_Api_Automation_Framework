package utils;
import net.datafaker.Faker;

public class DataFaker {

    private static Faker faker;
    private static DataFaker dataFaker;

    private DataFaker() {
        faker = new Faker();
    }

    public static DataFaker getInstance() {
        if (dataFaker == null) {
            dataFaker = new DataFaker();
        }
        return dataFaker;
    }

    public String getFirstName() {
        return faker.name().firstName();
    }

    public String getLastName() {
        return faker.name().lastName();
    }

    public String getFullName() {
        return getFirstName() + " " + getLastName();
    }
}
