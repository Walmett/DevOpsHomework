import static org.junit.jupiter.api.Assertions.*;
import static io.qameta.allure.SeverityLevel.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.*;

public class GarageTest {
    private MyGarage garage;
    private Car c1, c2, c3, c4, c5;
    private Owner o1, o2, o3;

    public void setUp() {
        garage = new MyGarage();

        c1 = new Car(1, "first", "modelName", 100, 100, 1);
        c2 = new Car(2, "first", "modelName", 80, 80, 1);
        c3 = new Car(3, "second", "modelName", 130, 120, 1);
        c4 = new Car(4, "first", "modelName", 120, 130, 2);
        c5 = new Car(5, "third", "modelName", 110, 100, 3);

        o1 = new Owner(1, "first", "first", 30);
        o2 = new Owner(2, "second", "second", 20);
        o3 = new Owner(3, "third", "third", 60);

        garage.addNewCar(c1, o1);
        garage.addNewCar(c2, o1);
        garage.addNewCar(c3, o1);
        garage.addNewCar(c4, o2);
        garage.addNewCar(c5, o3);
    }

    @Test
    public void testUniqueOwners() {
    	setUp();
        assertEquals(Arrays.asList(o1, o2, o3), new ArrayList<>(garage.allCarsUniqueOwners()));
    }

    @Test
    public void testCarsOfBrand() {
    	setUp();
        assertEquals(Arrays.asList(c1, c2, c4), garage.allCarsOfBrand("first"));
        assertEquals(Arrays.asList(c3), garage.allCarsOfBrand("second"));
        assertEquals(Arrays.asList(c5), garage.allCarsOfBrand("third"));
    }

    @Test
    public void testCarsOfOwner() {
    	setUp();
        assertEquals(Arrays.asList(c1, c2, c3), garage.allCarsOfOwner(o1));
        assertEquals(Arrays.asList(c4), garage.allCarsOfOwner(o2));
        assertEquals(Arrays.asList(c5), garage.allCarsOfOwner(o3));
    }

    @Test
    public void testMorePowerThan() {
    	setUp();
        assertEquals(Arrays.asList(c4, c3, c1, c5, c2), garage.carsWithPowerMoreThan(50));
        assertEquals(Arrays.asList(c4, c3), garage.carsWithPowerMoreThan(100));
        assertEquals(Arrays.asList(), garage.carsWithPowerMoreThan(150));
    }
    @Test
    public void testTopThreeCars() {
    	setUp();
        assertEquals(Arrays.asList(c3, c4, c5), garage.topThreeCarsByMaxVelocity());
    }

    @Test
    public void testMeanCarNumber() {
    	setUp();
        assertEquals(1, garage.meanCarNumberForEachOwner());
    }

    @Test
    public void testMeanOwnersAge() {
    	setUp();
        assertEquals(25, garage.meanOwnersAgeOfCarBrand("first"));
        assertEquals(30, garage.meanOwnersAgeOfCarBrand("second"));
        assertEquals(60, garage.meanOwnersAgeOfCarBrand("third"));
    }

    @Test
    public void testRemoveCar() {
    	setUp();
        garage.removeCar(3);
        assertEquals(Arrays.asList(c1, c2), garage.allCarsOfOwner(o1));

        garage.removeCar(4);
        assertEquals(Arrays.asList(o1, o3), new ArrayList<>(garage.allCarsUniqueOwners()));

        garage.addNewCar(c3, o1);
        garage.addNewCar(c4, o2);
    }
}
