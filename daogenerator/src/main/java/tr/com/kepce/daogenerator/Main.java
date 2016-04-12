package tr.com.kepce.daogenerator;

import java.io.File;

import de.greenrobot.daogenerator.DaoGenerator;
import de.greenrobot.daogenerator.Schema;

public class Main {

    public static void main(String[] args) throws Exception {
        Schema schema = new Schema(1, "tr.com.kepce.model");

        addCartEntity(schema);
        addCart(schema);

        String currentPath = new File(".").getCanonicalPath();

        new DaoGenerator().generateAll(schema, currentPath + "/app/src/main/java");
    }

    private static void addCart(Schema schema) {

    }

    private static void addCartEntity(Schema schema) {

    }

    private static void addRestaurant(Schema schema) {

    }

    private static void addMeal(Schema schema) {

    }
}
