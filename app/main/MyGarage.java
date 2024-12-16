import java.util.*;

public class MyGarage implements Garage {

    private final Map<Integer, Car> carById;
    private final Map<Integer, Owner> ownerById;
    private final Map<String, List<Car>> carsByBrand;
    private final Map<Owner, List<Car>> carsByOwner;
    private final NavigableSet<Car> sortedCarsByVelocity;
    private final NavigableSet<Car> sortedCarsByPower;

    public MyGarage() {
        this.ownerById = new HashMap<>();
        this.carById = new HashMap<>();
        this.carsByBrand = new HashMap<>();
        this.carsByOwner = new HashMap<>();
        this.sortedCarsByVelocity = new TreeSet<>(Comparator.comparingInt(Car::getMaxVelocity).reversed().thenComparingInt(Car::getCarId));
        this.sortedCarsByPower = new TreeSet<>(Comparator.comparingInt(Car::getPower).reversed().thenComparingInt(Car::getCarId));
    }

    @Override
    public Collection<Owner> allCarsUniqueOwners() {
        return Collections.unmodifiableCollection(carsByOwner.keySet());
    }

    @Override
    public Collection<Car> topThreeCarsByMaxVelocity() {
        return sortedCarsByVelocity.stream().limit(3).toList();
    }

    @Override
    public Collection<Car> allCarsOfBrand(String brand) {
        return carsByBrand.getOrDefault(brand, Collections.emptyList());
    }

    @Override
    public Collection<Car> carsWithPowerMoreThan(int power) {
        ArrayList<Car> cars = new ArrayList<>();
        for (Car car : sortedCarsByPower) {
            if (car.getPower() <= power) {
                break;
            }
            cars.add(car);
        }
        return cars;
    }

    @Override
    public Collection<Car> allCarsOfOwner(Owner owner) {
        return carsByOwner.getOrDefault(owner, Collections.emptyList());
    }

    @Override
    public int meanOwnersAgeOfCarBrand(String brand) {
        if (!carsByBrand.containsKey(brand)) {
            return 0;
        }
        List<Car> cars = carsByBrand.get(brand);
        Set<Owner> owners = new HashSet<>();
        for (Car car : cars) {
            owners.add(ownerById.get(car.getOwnerId()));
        }
        int sumAge = 0;
        for (Owner owner : owners) {
            sumAge += owner.getAge();
        }
        return sumAge / owners.size();
    }

    @Override
    public int meanCarNumberForEachOwner() {
        return (int) carsByOwner.values().stream().mapToInt(List::size).average().orElse(0);
    }

    @Override
    public Car removeCar(int carId) {
        Car car = carById.remove(carId);
        if (car == null) {
            return null;
        }
        List<Car> carsByBrandList = carsByBrand.get(car.getBrand());
        carsByBrandList.remove(car);
        if (carsByBrandList.isEmpty()) {
            carsByBrand.remove(car.getBrand());
        }
        Owner owner = ownerById.get(car.getOwnerId());
        carsByOwner.get(owner).remove(car);
        if (carsByOwner.get(owner).isEmpty()) {
            carsByOwner.remove(owner);
            ownerById.remove(owner.getOwnerId());
        }
        sortedCarsByVelocity.remove(car);
        sortedCarsByPower.remove(car);
        return car;
    }

    @Override
    public void addNewCar(Car car, Owner owner) {
        if (car == null || owner == null) {
            throw new IllegalArgumentException("Null pointer");
        }
        if (car.getOwnerId() != owner.getOwnerId()) {
            throw new IllegalArgumentException("Different id");
        }
        carById.put(car.getCarId(), car);
        ownerById.put(owner.getOwnerId(), owner);
        carsByBrand.computeIfAbsent(car.getBrand(), k -> new ArrayList<>()).add(car);
        carsByOwner.computeIfAbsent(owner, k -> new ArrayList<>()).add(car);
        sortedCarsByVelocity.add(car);
        sortedCarsByPower.add(car);
    }
}
