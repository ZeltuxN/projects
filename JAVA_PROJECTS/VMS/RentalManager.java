package vehiclemanagementsystem;

import java.util.ArrayList;
import java.util.Scanner;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.time.LocalDate;

public class RentalManager {
    private ArrayList<Vehicle> fleet;
    private ArrayList<Customer> customerList;
    private ArrayList<Rental> rentalList;
    private int rentalCounter;
    private int loyaltyCardCounter;

    public RentalManager() {
        this.fleet = new ArrayList<>();
        this.customerList = new ArrayList<>();
        this.rentalList = new ArrayList<>();
        this.rentalCounter = 1;
        this.loyaltyCardCounter = 1;
    }

    public String generateLoyaltyCardID() {
        return String.format("L%03d", loyaltyCardCounter++);
    }

    public void registerCustomer(Customer customer) throws CustomerAlreadyExistsException {
        for (Customer c : customerList) {
            if (c.getIdentityNumber().equals(customer.getIdentityNumber())) {
                throw new CustomerAlreadyExistsException("Customer with this identity number already registered!");
            }
        }
        customerList.add(customer);
    }

    public Customer findCustomerbyIdentityNumber(String identityNumber) throws CustomerNotFoundException {
        for (Customer c : customerList) {
            if (c.getIdentityNumber().equals(identityNumber)) {
                return c;
            }
        }
        throw new CustomerNotFoundException("Customer not found");
    }

    public Customer findCustomerByLoyaltyCardID(String loyaltyCardID) {
        for (Customer customer : customerList) {
            if (customer.getLoyaltyCardID().equals(loyaltyCardID)) {
                return customer;
            }
        }
        return null;
    }

    public ArrayList<Customer> displayAllCustomers() {
        return customerList;
    }

    public String generateRentalID() {
        return String.format("R%03d", rentalCounter++);
    }

    public ArrayList<Vehicle> findAvailableVehicles(LocalDate startDate, int durationDays, int requiredSeats) {
        ArrayList<Vehicle> available = new ArrayList<>();
        LocalDate endDate = startDate.plusDays(durationDays - 1);

        for (Vehicle v : fleet) {
            if (isVehicleAvailable(v, startDate, endDate) && v.getSeats() >= requiredSeats) {
                available.add(v);
            }
        }

        available.sort((vehicle1, vehicle2) -> Double.compare(vehicle1.getDailyRate(), vehicle2.getDailyRate()));
        return available;
    }

    public ArrayList<Vehicle> findAvailableVehiclesByCategory(LocalDate startDate, int durationDays, int requiredSeats, String category) {
        ArrayList<Vehicle> available = new ArrayList<>();
        LocalDate endDate = startDate.plusDays(durationDays - 1);

        for (Vehicle v : fleet) {
            if (isVehicleAvailable(v, startDate, endDate) &&
                v.getSeats() >= requiredSeats &&
                v.getType().equals(category)) {
                available.add(v);
            }
        }

        available.sort((vehicle1, vehicle2) -> Double.compare(vehicle1.getDailyRate(), vehicle2.getDailyRate()));
        return available;
    }

    public boolean isVehicleAvailable(Vehicle vehicle, LocalDate startDate, LocalDate endDate) {
        for (Rental rental : rentalList) {
            if (rental.getVehicle().getRegistrationNumber().equals(vehicle.getRegistrationNumber())) {
                LocalDate rentalStart = rental.getStartDate();
                LocalDate rentalEnd = rental.getEndDate();

                if (!endDate.isBefore(rentalStart) && !startDate.isAfter(rentalEnd)) {
                    return false;
                }
            }
        }

        if (vehicle.isUnderMaintenance(startDate, endDate)) {
            return false;
        }

        return true;
    }

    public boolean isValidBrandModel(String brand, String model, String category) {
        String filename = category + "Models.csv";
        try (Scanner scanner = new Scanner(new FileReader(filename))) {
            if (scanner.hasNextLine()) scanner.nextLine();
            while (scanner.hasNextLine()) {
                String[] parts = scanner.nextLine().split(",");
                if (parts.length >= 2 &&
                    parts[0].trim().equalsIgnoreCase(brand.trim()) &&
                    parts[1].trim().equalsIgnoreCase(model.trim())) {
                    return true;
                }
            }
            return false;
        } catch (FileNotFoundException e) {
            System.out.println(filename + " not found.");
            return false;
        }
    }

    public Rental createRental(Customer customer, Vehicle vehicle, LocalDate startDate, int durationDays) {
        String rentalID = generateRentalID();
        Rental rental = new Rental(rentalID, customer, vehicle, startDate, durationDays);
        rentalList.add(rental);
        customer.addRental(rental);
        return rental;
    }

    public void applyLoyaltyDiscount(Rental rental, int pointsToRedeem) {
        Customer customer = rental.getCustomer();

        if (pointsToRedeem < 50) {
            System.out.println("Minimum redemption is 50 points.");
            return;
        }

        if (pointsToRedeem > customer.getLoyaltyPoints()) {
            System.out.println("Not enough points. Available: " + customer.getLoyaltyPoints());
            return;
        }

        double requestedDiscount = pointsToRedeem / 10.0;
        double maxDiscount = rental.getDailyCost() + rental.getServicesCost() - rental.getDiscount();

        if (requestedDiscount > maxDiscount) {
            requestedDiscount = maxDiscount;
        }

        rental.setDiscount(rental.getDiscount() + requestedDiscount);

        int actualPointsUsed = (int) (requestedDiscount * 10);
        customer.useLoyaltyPoints(actualPointsUsed);
    }

    public void completeRental(Rental rental) {
        rental.calculateTotalCost();
        int points = (int) rental.getTotalCost();
        rental.getCustomer().addLoyaltyPoints(points);
    }

    public void printInvoice(Rental rental) {
        System.out.println("=== INVOICE ===");
        System.out.println("Rental ID: " + rental.getRentalID());
        System.out.println("Customer: " + rental.getCustomer().getName() + " (ID: " + rental.getCustomer().getIdentityNumber() + ")");
        System.out.println("Loyalty Card: " + rental.getCustomer().getLoyaltyCardID());
        System.out.println("Vehicle: " + rental.getVehicle().getBrand() + " " + rental.getVehicle().getModel() +
                " [" + rental.getVehicle().getRegistrationNumber() + "]");
        System.out.println("Category: " + rental.getVehicle().getType());
        System.out.println("Dates: " + rental.getStartDate() + " to " + rental.getEndDate());
        System.out.println("Duration: " + rental.getDurationDays() + " days");
        System.out.println("--- Cost Breakdown ---");
        System.out.println("Base Cost (" + rental.getDurationDays() + " days x €" +
                String.format("%.2f", rental.getVehicle().getDailyRate()) + "): €" +
                String.format("%.2f", rental.getDailyCost()));

        if (rental.getServices().isEmpty()) {
            System.out.println("Additional Services: None");
        } else {
            for (AdditionalServices service : rental.getServices()) {
                System.out.println(service.getName() + ": €" +
                        String.format("%.2f", service.calculateCost(rental.getDurationDays())));
            }
        }

        System.out.println("Services Total: €" + String.format("%.2f", rental.getServicesCost()));
        System.out.println("Discount: €" + String.format("%.2f", rental.getDiscount()));
        System.out.println("Final Paid Amount: €" + String.format("%.2f", rental.getTotalCost()));
        System.out.println("================");
    }

    public Rental findRentalbyID(String rentalID) {
        for (Rental r : rentalList) {
            if (r.getRentalID().equals(rentalID)) {
                return r;
            }
        }
        return null;
    }

    public ArrayList<Rental> findCustomerRentalHistory(String identityNumber) {
        try {
            Customer customer = findCustomerbyIdentityNumber(identityNumber);
            return customer.getRentalHistory();
        } catch (CustomerNotFoundException e) {
            return new ArrayList<>();
        }
    }

    public ArrayList<Vehicle> findUnavailableVehicles(LocalDate startDate, LocalDate endDate) {
        ArrayList<Vehicle> unavailable = new ArrayList<>();

        for (Vehicle v : fleet) {
            if (!isVehicleAvailable(v, startDate, endDate)) {
                unavailable.add(v);
            }
        }

        return unavailable;
    }

    public ArrayList<Rental> findVehicleRentalHistory(String registrationNumber) {
        ArrayList<Rental> history = new ArrayList<>();

        for (Rental r : rentalList) {
            if (r.getVehicle().getRegistrationNumber().equals(registrationNumber)) {
                history.add(r);
            }
        }

        return history;
    }

    public Vehicle findVehiclebyRegistration(String registrationNumber) {
        for (Vehicle v : fleet) {
            if (v.getRegistrationNumber().equals(registrationNumber)) {
                return v;
            }
        }
        return null;
    }

    public void addVehicle(Vehicle vehicle) throws VehicleAlreadyExistsException {
        if (findVehiclebyRegistration(vehicle.getRegistrationNumber()) != null) {
            throw new VehicleAlreadyExistsException("This Vehicle is already part of the fleet.");
        }
        fleet.add(vehicle);
    }

    public void removeVehicle(String registrationNumber) throws Exception {
        Vehicle vehicle = findVehiclebyRegistration(registrationNumber);
        if (vehicle == null) {
            throw new Exception("This Vehicle is not a part of the fleet, thus it can't be removed.");
        }
        fleet.remove(vehicle);
    }

    public ArrayList<Vehicle> displayAllVehicles() {
        return fleet;
    }

    public ArrayList<Rental> displayAllRentals() {
        return rentalList;
    }

    public ArrayList<Rental> getRentalList() {
        return rentalList;
    }

    public int getRentalCounter() {
        return rentalCounter;
    }

    public void setRentalCounter(int rentalCounter) {
        this.rentalCounter = rentalCounter;
    }

    public int getLoyaltyCardCounter() {
        return loyaltyCardCounter;
    }

    public void setLoyaltyCardCounter(int loyaltyCardCounter) {
        this.loyaltyCardCounter = loyaltyCardCounter;
    }
}