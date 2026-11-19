package vehiclemanagementsystem;

public class EconomyCar extends Vehicle {
    public EconomyCar(String registrationNumber, String brand, String model, int creationYear, int seats) throws InvalidSeatsException {
        super(registrationNumber, brand, model, creationYear, seats, 45.0);
        if (seats > 5 || seats < 4) {
            throw new InvalidSeatsException("The seats can be either 4 or 5!");
        }
        setMinSeats(4);
        setMaxSeats(5);
    }
}