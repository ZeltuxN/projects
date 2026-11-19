package vehiclemanagementsystem;

import java.util.Scanner;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.time.LocalDate;

public class Main {

    private static RentalManager manager = new RentalManager();
    private static Scanner scanner = new Scanner(System.in);

    private static void printMenu() {
        System.out.println("\nVehicle Management System\r\n"
            + "Options:\r\n"
            + "1. Vehicle Management (Insert/Delete)\r\n"
            + "2. Customer Management (Register/Search)\r\n"
            + "3. Rental Creation\r\n"
            + "4. Print Invoice\r\n"
            + "5. Car Availability Status\r\n"
            + "6. Display Customer History\r\n"
            + "7. Display Vehicle History\r\n"
            + "8. Display All Data\r\n"
            + "9. Display All Maintenance History\r\n"
            + "0. Exit\r\n"
            + "Choose a Functionality:");
}

private static Vehicle getVehicleSpecs(int carchoice) {
    System.out.println("Please fill the specifications for the car!");

    String registrationNumber;
    while (true) {
        System.out.println("[Registration Number]");
        registrationNumber = scanner.nextLine();
        if (registrationNumber.isBlank()) {
            System.out.println("Registration number cannot be empty!");
        } else if (registrationNumber.contains(",")) {
            System.out.println("Registration number cannot contain commas.");
        } else if (registrationNumber.length() > 10) {
            System.out.println("[Maximum Amount of Characters 10]");
        } else if (registrationNumber.equals("0")) {
            System.out.println("Invalid registration number");
        } else if (registrationNumber.length() < 6) {
            System.out.println("Invalid registration number");
        } else if (manager.findVehiclebyRegistration(registrationNumber) != null) {
            System.out.println("A vehicle with this registration number already exists! Try again.");
        } else {
            break;
        }
    }

    String category;
    if (carchoice == 1) category = "EconomyCar";
    else if (carchoice == 2) category = "SUV";
    else if (carchoice == 3) category = "Convertible";
    else category = "LuxuryCar";

    String brand;
    String model;
    while (true) {
        System.out.println("[Brand]");
        brand = scanner.nextLine();
        if (brand.isBlank()) {
            System.out.println("Brand cannot be empty.");
            continue;
        } else if (brand.contains(",")) {
            System.out.println("Brand cannot contain commas.");
            continue;
        }

        System.out.println("[Model]");
        model = scanner.nextLine();
        if (model.isBlank()) {
            System.out.println("Model cannot be empty.");
            continue;
        } else if (model.contains(",")) {
            System.out.println("Model cannot contain commas.");
            continue;
        }

        if (manager.isValidBrandModel(brand, model, category)) {
            break;
        } else {
            System.out.println("Invalid brand/model combination for " + category + ". Try again.");
        }
    }

    int creationYear;
    int currentYear = LocalDate.now().getYear();
    while (true) {
        System.out.println("[Creation Year]");
        try {
            creationYear = scanner.nextInt();
            if (creationYear < 1884 || creationYear > currentYear) {
                System.out.println("[Creation Year (1884-" + currentYear + ")]");
                continue;
            }
            break;
        } catch (InputMismatchException e) {
            System.out.println("Invalid input! Please enter a valid year.");
            scanner.nextLine();
        }
    }

    int seats;
    while (true) {
        if (carchoice == 1) System.out.println("[Seats (4-5)]");
        else if (carchoice == 2) System.out.println("[Seats (5-7)]");
        else if (carchoice == 3) System.out.println("[Seats (2-4)]");
        else if (carchoice == 4) System.out.println("[Seats (4-5)]");
        try {
            seats = scanner.nextInt();
            break;
        } catch (InputMismatchException e) {
            System.out.println("Invalid input! Please enter a valid number.");
            scanner.nextLine();
        }
    }
    scanner.nextLine();

    try {
        if (carchoice == 1)
            return new EconomyCar(registrationNumber, brand, model, creationYear, seats);
        else if (carchoice == 2)
            return new SUV(registrationNumber, brand, model, creationYear, seats);
        else if (carchoice == 3)
            return new Convertible(registrationNumber, brand, model, creationYear, seats);
        else
            return new LuxuryCar(registrationNumber, brand, model, creationYear, seats);
    } catch (InvalidSeatsException e) {
        System.out.println("Error: " + e.getMessage() + " Try again.");
        while (true) {
            if (carchoice == 1)
                System.out.println("[Seats Allowed(4-5)]");
            else if (carchoice == 2)
                System.out.println("[Seats Allowed(5-7)]");
            else if (carchoice == 3)
                System.out.println("[Seats Allowed(2-4)]");
            else if (carchoice == 4)
                System.out.println("[Seats Allowed(4-5)]");
            try {
                seats = scanner.nextInt();
                scanner.nextLine();
                if (carchoice == 1)
                    return new EconomyCar(registrationNumber, brand, model, creationYear, seats);
                else if (carchoice == 2)
                    return new SUV(registrationNumber, brand, model, creationYear, seats);
                else if (carchoice == 3)
                    return new Convertible(registrationNumber, brand, model, creationYear, seats);
                else
                    return new LuxuryCar(registrationNumber, brand, model, creationYear, seats);
            } catch (InvalidSeatsException ex) {
                System.out.println("Error: " + ex.getMessage() + " Try again.");
            } catch (InputMismatchException ex) {
                System.out.println("Invalid input!");
                scanner.nextLine();
            }
        }
    }
}

private static void registerCustomer() {
    String name = "";
    while (true) {
        boolean invalidName = false;
        System.out.println("[Full Name]");
        name = scanner.nextLine();
        if (name.isBlank()) {
            System.out.println("Name cannot be empty.");
            continue;
        }
        if (name.contains(",")) {
            System.out.println("Name cannot contain commas.");
            continue;
        }
        if (name.length() < 2 || name.length() > 60) {
            System.out.println("Name must be 2-60 characters.");
            continue;
        }
        for (char c : name.toCharArray()) {
            if (Character.isDigit(c)) {
                System.out.println("Invalid name");
                invalidName = true;
                break;
            }
        }
        if (!invalidName) break;
    }

    String identityNumber;

    while (true) {
        System.out.println("[Identity Number]");
        identityNumber = scanner.nextLine();

        if (identityNumber.isBlank()) {
            System.out.println("Identity number cannot be empty.");
            continue;
        }

        if (identityNumber.contains(",")) {
            System.out.println("Identity number cannot contain commas.");
            continue;
        }

        if (!identityNumber.matches("\\d+")) {
            System.out.println("Please enter a valid identity card number (digits only).");
            continue;
        }

        if (identityNumber.length() < 6 || identityNumber.length() > 15) {
            System.out.println("Identity number must be 6-15 digits.");
            continue;
        }

        try {
            manager.findCustomerbyIdentityNumber(identityNumber);
            System.out.println("A customer with this identity number already exists! Try again.");
        } catch (CustomerNotFoundException e) {
            break;
        }
    }

    

    String address;
    while (true) {
        System.out.println("[Address]");
        address = scanner.nextLine();
        if (address.isBlank()) {
            System.out.println("Address cannot be empty.");
        } else if (address.contains(",")) {
            System.out.println("Address cannot contain commas.");
        } else {
            break;
        }
    }

    String email;

    while (true) {
        System.out.println("[Email]");
        email = scanner.nextLine();

        if (email.isBlank()) {
            System.out.println("Invalid email.");
        } 
        else if (email.contains(",")) {
            System.out.println("Email cannot contain commas.");
        } 
        else if (email.contains(" ")) {
            System.out.println("Email cannot contain spaces.");
        } 
        else {
            int count = 0;

            for (int i = 0; i < email.length(); i++) {
                if (email.charAt(i) == '@') {
                    count++;
                }
            }

            if (count != 1) {
                System.out.println("Email must contain exactly one @.");
            } 
            else if (email.startsWith("@") || email.endsWith("@")) {
                System.out.println("Invalid email.");
            } 
            else {
                int atIndex = email.indexOf('@');
                int dotIndex = email.lastIndexOf('.');

                if (dotIndex < atIndex + 2 || dotIndex == email.length() - 1) {
                    System.out.println("Invalid email.");
                } 
                else {
                    break;
                }
            }
        }
    }

    String phoneNumber;
    while (true) {
        System.out.println("[Phone Number]");
        phoneNumber = scanner.nextLine();
        if (phoneNumber.isBlank()) {
            System.out.println("Phone number cannot be empty.");
        } else if (phoneNumber.contains(",")) {
            System.out.println("Phone number cannot contain commas.");
        } else if (!phoneNumber.matches("\\d+")) {
                System.out.println("Please enter a valid phone number (digits only).");
                continue;
        } else if (phoneNumber.length() < 8 || phoneNumber.length() > 15) {
            System.out.println("Phone number must be 8-15 digits.");
        } else {
            break;
        }
    }

    String creditCard;
    boolean isNumber;

    while (true) {
        System.out.println("[Credit Card Number]");
        creditCard = scanner.nextLine();

        if (creditCard.isBlank()) {
            System.out.println("Credit card number cannot be empty.");
        } 
        else if (creditCard.contains(",")) {
            System.out.println("Credit card number cannot contain commas.");
        } 
        else if (creditCard.length() < 13 || creditCard.length() > 19) {
            System.out.println("Credit card must be 13-19 digits.");
        } 
        else {
            isNumber = true;

            for (int i = 0; i < creditCard.length(); i++) {
                if (!Character.isDigit(creditCard.charAt(i))) {
                    isNumber = false;
                    break;
                }
            }

            if (!isNumber) {
                System.out.println("Please enter a valid credit card number (digits only).");
            } 
            else {
                break;
            }
        }
    }

    String loyaltyCardID = manager.generateLoyaltyCardID();

    Customer customer = new Customer(name, identityNumber, address, email, phoneNumber, creditCard, loyaltyCardID);
    try {
        manager.registerCustomer(customer);
        FileManager.appendCustomer(customer);
        System.out.println("Customer registered successfully!");
        System.out.println("Identity Number: " + identityNumber);
        System.out.println("Loyalty Card ID: " + loyaltyCardID);
    } catch (CustomerAlreadyExistsException e) {
        System.out.println("Error: " + e.getMessage());
    }
}

private static void printCustomerInfo(Customer c) {
    System.out.println("\n=== Customer Info ===");
    System.out.println("Name: " + c.getName());
    System.out.println("Identity Number: " + c.getIdentityNumber());
    System.out.println("Address: " + c.getAddress());
    System.out.println("Email: " + c.getEmail());
    System.out.println("Phone: " + c.getPhoneNumber());
    System.out.println("Loyalty Card: " + c.getLoyaltyCardID());
    System.out.println("Loyalty Points: " + c.getLoyaltyPoints());
    System.out.println("====================");
}

private static void searchByIdentityNumber() {
    while (true) {
        System.out.println("[Enter Identity Number] (0 to go back)");
        String id = scanner.nextLine();
        if (id.equals("0")) return;
        try {
            Customer c = manager.findCustomerbyIdentityNumber(id);
            printCustomerFullInfo(c);
            return;
        } catch (CustomerNotFoundException e) {
            System.out.println("Error: " + e.getMessage() + " Try again.");
        }
    }
}

private static void printCustomerFullInfo(Customer c) {
    printCustomerInfo(c);

    System.out.println("\nRental History:");
    ArrayList<Rental> history = c.getRentalHistory();
    if (history.isEmpty()) {
        System.out.println("  No rentals.");
    } else {
        for (Rental r : history) {
            System.out.println("  Rental " + r.getRentalID() + ": " + r.getVehicle().getBrand() + " " +
                    r.getVehicle().getModel() + ", " + r.getStartDate() + " to " + r.getEndDate() +
                    " (" + r.getDurationDays() + " days), Total: " + String.format("%.2f", r.getTotalCost()) + " euro");
        }
    }

    LocalDate today = LocalDate.now();
    boolean hasActive = false;
    for (Rental r : history) {
        if (!today.isBefore(r.getStartDate()) && !today.isAfter(r.getEndDate())) {
            if (!hasActive) {
                System.out.println("\nActive Rental(s):");
                hasActive = true;
            }
            manager.printInvoice(r);
        }
    }
    if (!hasActive) {
        System.out.println("\nNo active rental.");
    }
}

private static void displayAllCustomers() {
    ArrayList<Customer> customers = manager.displayAllCustomers();
    System.out.println("\t\t\t[Customers]");
    for (Customer c : customers) {
        System.out.println("  " + c.getName() + " | ID: " + c.getIdentityNumber() +
                " | Loyalty: " + c.getLoyaltyCardID() + " | Points: " + c.getLoyaltyPoints());
    }
}

private static void searchByLoyaltyCardID() {
    System.out.println("[Enter Loyalty Card ID]");
    String loyaltyID = scanner.nextLine();
    Customer c = manager.findCustomerByLoyaltyCardID(loyaltyID);
    if (c == null) {
        System.out.println("No customer found with this loyalty card ID.");
    } else {
        printCustomerFullInfo(c);
    }
}

private static void createRental() {
    System.out.println("[Customer Identity Number] (0 to go back)");
    String identityNumber = scanner.nextLine();
    if (identityNumber.equals("0")) return;

    Customer customer;
    try {
        customer = manager.findCustomerbyIdentityNumber(identityNumber);
    } catch (CustomerNotFoundException e) {
        System.out.println("Error: " + e.getMessage());
        return;
    }
    System.out.println("Customer found: " + customer.getName());

    LocalDate startDate;
    while (true) {
        System.out.println("[Start Date (YYYY-MM-DD)] (0 to go back)");
        String date = scanner.nextLine();
        if (date.equals("0")) return;
        try {
            startDate = LocalDate.parse(date);
            if (startDate.isBefore(LocalDate.now())) {
                System.out.println("Start date cannot be in the past!");
                continue;
            }
            if (startDate.isAfter(LocalDate.now().plusYears(2))) {
                System.out.println("Start date cannot be more than 2 years in the future!");
                continue;
            }
            break;
        } catch (Exception e) {
            System.out.println("Invalid date format! Use YYYY-MM-DD.");
        }
    }

    int durationDays;
    while (true) {
        System.out.println("[Rental Duration (Days)] (0 to go back)");
        try {
            durationDays = scanner.nextInt();
            if (durationDays == 0) {
                scanner.nextLine();
                return;
            }
            if (durationDays < 1) {
                System.out.println("Duration must be at least 1 day!");
                continue;
            }
            if (durationDays > 365) {
                System.out.println("Duration cannot exceed 365 days!");
                continue;
            }
            break;
        } catch (InputMismatchException e) {
            System.out.println("Invalid input!");
            scanner.nextLine();
        }
    }

    System.out.println("\nVehicle Categories:");
    System.out.println("1. [Economy Car]");
    System.out.println("2. [SUV]");
    System.out.println("3. [Convertible]");
    System.out.println("4. [Luxury Car]");
    System.out.println("0. [Back]");
    int catChoice;
    while (true) {
        System.out.println("[Choose Category]");
        try {
            catChoice = scanner.nextInt();
            if (catChoice == 0) {
                scanner.nextLine();
                return;
            }
            if (catChoice >= 1 && catChoice <= 4) break;
            System.out.println("Invalid choice!");
        } catch (InputMismatchException e) {
            System.out.println("Invalid input!");
            scanner.nextLine();
        }
    }

    String category;
    if (catChoice == 1) category = "EconomyCar";
    else if (catChoice == 2) category = "SUV";
    else if (catChoice == 3) category = "Convertible";
    else category = "LuxuryCar";

    int requiredSeats;
    while (true) {
        System.out.println("[Required Number of Seats] (0 to go back)");
        try {
            requiredSeats = scanner.nextInt();
            if (requiredSeats == 0) {
                scanner.nextLine();
                return;
            }
            if (requiredSeats < 1) {
                System.out.println("Seats must be at least 1!");
                continue;
            }
            break;
        } catch (InputMismatchException e) {
            System.out.println("Invalid input!");
            scanner.nextLine();
        }
    }
    scanner.nextLine();

    ArrayList<Vehicle> available = manager.findAvailableVehiclesByCategory(startDate, durationDays, requiredSeats, category);

    if (available.isEmpty()) {
        System.out.println("No " + category + " available for these dates and seats.");
        System.out.println("Searching for alternatives...");
        available = manager.findAvailableVehicles(startDate, durationDays, requiredSeats);
        if (available.isEmpty()) {
            System.out.println("Unfortunately no vehicles were found.");
            return;
        }
    }

    System.out.println("\n[Available Vehicles (cheapest first)]");
    for (int i = 0; i < available.size(); i++) {
        System.out.println((i + 1) + ". " + available.get(i));
    }
    System.out.println("0. [Back]");

    int vehicleChoice;
    while (true) {
        System.out.println("[Choose Vehicle]");
        try {
            vehicleChoice = scanner.nextInt();
            if (vehicleChoice == 0) {
                scanner.nextLine();
                return;
            }
            if (vehicleChoice >= 1 && vehicleChoice <= available.size()) 
            	break;
            System.out.println("Invalid choice!");
        } catch (InputMismatchException e) {
            System.out.println("Invalid input!");
            scanner.nextLine();
        }
    }
    scanner.nextLine();
    Vehicle selectedVehicle = available.get(vehicleChoice - 1);

    Rental rental = manager.createRental(customer, selectedVehicle, startDate, durationDays);

    boolean hasGPS = false;
    boolean hasAdditionalSafety = false;
    int maxChildSeats = 0;

    while (true) {
        System.out.println("\nAdditional Services:");
        System.out.println("1. [GPS/navigation - 5euro/day]");
        System.out.println("2. [Child Seat - 20euro flat]");
        System.out.println("3. [Additional Safety - 30euro/5 days]");
        System.out.println("0. [Done]");
        int serviceChoice;
        try {
            serviceChoice = scanner.nextInt();
        } catch (InputMismatchException e) {
            System.out.println("Invalid input!");
            scanner.nextLine();
            continue;
        }
        scanner.nextLine();

        if (serviceChoice == 0) 
        	break;
        else if (serviceChoice == 1) {
            if (!hasGPS) {
                rental.addService(new GPS());
                System.out.println("GPS was added.");
                hasGPS = true;
            } else {
                System.out.println("[1 GPS Allowed]");
            }
        } else if (serviceChoice == 2) {
            if (maxChildSeats < (selectedVehicle.getMaxSeats() - 1)) {
                rental.addService(new ChildSeat());
                System.out.println("Child Seat was added.");
                maxChildSeats++;
            } else {
                System.out.println("[Maximum Amount of Child Seats Reached]");
            }
        } else if (serviceChoice == 3) {
            if (!hasAdditionalSafety) {
                rental.addService(new AdditionalSafety());
                System.out.println("Additional Safety added.");
                hasAdditionalSafety = true;
            } else {
                System.out.println("[Additional Safety already added]");
            }
        } else {
            System.out.println("Invalid choice!");
        }
    }

    if (customer.getLoyaltyPoints() >= 50) {
        System.out.println("\nYou have " + customer.getLoyaltyPoints() + " Loyalty Points.");
        System.out.println("10 points = 1euro discount. Minimum redemption: 50 points.");
        System.out.println("[1] Redeem [0] Skip");
        int redeemChoice = 0;
        try {
            redeemChoice = scanner.nextInt();
        } catch (InputMismatchException e) {
            scanner.nextLine();
        }

        if (redeemChoice == 1) {
            int maxPoints = customer.getLoyaltyPoints();
            while (true) {
                System.out.println("[How many points? (min 50, max " + maxPoints + ")] (0 to skip)");
                int pointsToRedeem;
                try {
                    pointsToRedeem = scanner.nextInt();
                } catch (InputMismatchException e) {
                    System.out.println("Invalid input!");
                    scanner.nextLine();
                    continue;
                }
                if (pointsToRedeem == 0) break;
                if (pointsToRedeem > maxPoints) {
                    System.out.println("These points exceed your balance!");
                    continue;
                }
                if (pointsToRedeem < 50) {
                    System.out.println("Minimum redemption is 50 points!");
                    continue;
                }
                if (pointsToRedeem % 10 != 0) {
                    System.out.println("Points must be a multiple of 10.");
                    continue;
                }
                manager.applyLoyaltyDiscount(rental, pointsToRedeem);
                break;
            }
        }
        scanner.nextLine();
    }

    manager.completeRental(rental);
    System.out.println("\nRental created successfully!");
    manager.printInvoice(rental);

    int defaultKm = durationDays * 125;
    System.out.println("\nEstimated kilometers: " + defaultKm + " (" + durationDays + " days x 125 km/day)");
    System.out.println("[1] Use estimate [2] Enter actual kilometers");
    int kmChoice = 0;
    try {
        kmChoice = scanner.nextInt();
    } catch (InputMismatchException e) {
        scanner.nextLine();
    }
    int km = defaultKm;
    if (kmChoice == 2) {
        while (true) {
            System.out.println("[Enter actual kilometers]");
            try {
                km = scanner.nextInt();
                if (km < 0) {
                    System.out.println("Kilometers cannot be negative!");
                    continue;
                }
                break;
            } catch (InputMismatchException e) {
                System.out.println("Invalid input!");
                scanner.nextLine();
            }
        }
    }
    scanner.nextLine();

    selectedVehicle.addKilometers(km);
    System.out.println("Vehicle now has " + selectedVehicle.getTotalKilometers() + " km total.");

    if (selectedVehicle.needsMaintenance()) {
        LocalDate maintenanceAfter = rental.getEndDate().plusDays(1);
        MaintenanceRecord record = selectedVehicle.scheduleMaintenance(maintenanceAfter);
        System.out.println("\n*** MAINTENANCE SCHEDULED ***");
        System.out.println(record);
        System.out.println("Vehicle unavailable: " + record.getStartDate() + " to " + record.getEndDate());
        FileManager.saveMaintenance(manager.displayAllVehicles());
    }

    FileManager.appendRental(rental);
    FileManager.saveCustomers(manager.displayAllCustomers());
    FileManager.saveVehicles(manager.displayAllVehicles());
}

private static void printInvoice() {
    System.out.println("[Enter Rental ID (e.g. R001)] (0 to go back)");
    String rentalID = scanner.nextLine();
    if (rentalID.equals("0")) return;
    Rental rental = manager.findRentalbyID(rentalID);
    if (rental == null) {
        System.out.println("Rental not found.");
    } else {
        manager.printInvoice(rental);
    }
}

private static void vehicleAvailability() {
    LocalDate startDate;
    int choice = 0;
    try {
        System.out.println("[1] Display all available vehicles");
        System.out.println("[2] Display vehicle of choice");
        choice = scanner.nextInt();
    } catch (InputMismatchException e) {
        System.out.println("Invalid input!");
        scanner.nextLine();
        return;
    }
    scanner.nextLine();

    if (choice < 1 || choice > 2) {
        System.out.println("Invalid choice!");
        return;
    }

    Vehicle selectedVehicle = null;
    if (choice == 2) {
        System.out.println("Enter registration number:");
        String regNum = scanner.nextLine();
        selectedVehicle = manager.findVehiclebyRegistration(regNum);
        if (selectedVehicle == null) {
            System.out.println("Vehicle not found!");
            return;
        }
    }

    while (true) {
        System.out.println("[Start Date (YYYY-MM-DD)] (0 to go back)");
        String dateStr = scanner.nextLine();
        if (dateStr.equals("0")) return;
        try {
            startDate = LocalDate.parse(dateStr);
            break;
        } catch (Exception e) {
            System.out.println("Invalid date format! Use YYYY-MM-DD.");
        }
    }

    LocalDate endDate;
    while (true) {
        System.out.println("[End Date (YYYY-MM-DD)] (0 to go back)");
        String dateStr = scanner.nextLine();
        if (dateStr.equals("0")) return;
        try {
            endDate = LocalDate.parse(dateStr);
            if (endDate.isBefore(startDate)) {
                System.out.println("End date cannot be before start date!");
                continue;
            }
            break;
        } catch (Exception e) {
            System.out.println("Invalid date format! Use YYYY-MM-DD.");
        }
    }

    if (choice == 1) {
        System.out.println("\n--- Available Vehicles ---");
        ArrayList<Vehicle> allVehicles = manager.displayAllVehicles();
        boolean foundAvailable = false;
        for (Vehicle v : allVehicles) {
            if (manager.isVehicleAvailable(v, startDate, endDate)) {
                System.out.println("  " + v);
                foundAvailable = true;
            }
        }
        if (!foundAvailable) {
            System.out.println("  None.");
        }

        System.out.println("\n--- Unavailable Vehicles ---");
        ArrayList<Vehicle> unavailable = manager.findUnavailableVehicles(startDate, endDate);
        if (unavailable.isEmpty()) {
            System.out.println("  None.");
        } else {
            for (Vehicle v : unavailable) {
                System.out.println("  " + v);
                ArrayList<Rental> rentals = manager.findVehicleRentalHistory(v.getRegistrationNumber());
                for (Rental r : rentals) {
                    if (!endDate.isBefore(r.getStartDate()) && !startDate.isAfter(r.getEndDate())) {
                        System.out.println("    -> Rental: " + r.getRentalID() + ", Customer: " + r.getCustomer().getName() +
                                ", " + r.getStartDate() + " to " + r.getEndDate());
                    }
                }
                if (v.isUnderMaintenance(startDate, endDate)) {
                    for (MaintenanceRecord m : v.getMaintenanceHistory()) {
                        if (!endDate.isBefore(m.getStartDate()) && !startDate.isAfter(m.getEndDate())) {
                            System.out.println("    -> Maintenance: " + m.getStartDate() + " to " + m.getEndDate());
                        }
                    }
                }
            }
        }
    } else if (choice == 2) {
        if (manager.isVehicleAvailable(selectedVehicle, startDate, endDate)) {
            System.out.println("\n" + selectedVehicle + " is AVAILABLE for the selected period.");
        } else {
            System.out.println("\n" + selectedVehicle + " is UNAVAILABLE for the selected period.");
            ArrayList<Rental> rentals = manager.findVehicleRentalHistory(selectedVehicle.getRegistrationNumber());
            for (Rental r : rentals) {
                if (!endDate.isBefore(r.getStartDate()) && !startDate.isAfter(r.getEndDate())) {
                    System.out.println("  -> Rental: " + r.getRentalID() + ", Customer: " + r.getCustomer().getName() +
                            ", " + r.getStartDate() + " to " + r.getEndDate());
                }
            }
            if (selectedVehicle.isUnderMaintenance(startDate, endDate)) {
                for (MaintenanceRecord m : selectedVehicle.getMaintenanceHistory()) {
                    if (!endDate.isBefore(m.getStartDate()) && !startDate.isAfter(m.getEndDate())) {
                        System.out.println("  -> Maintenance: " + m.getStartDate() + " to " + m.getEndDate());
                    }
                }
            }
        }
    }
}

private static void customerHistory() {
	int choice;
    ArrayList<Customer> customers = manager.displayAllCustomers();
	while(true) {
	System.out.println("1. [Display All Customers]");
	System.out.println("2. [Search Customer]");
	System.out.println("3. [Main Menu]");
    System.out.println("[Enter Customer Identity Number] (0 to go back)");
    try {
    	choice = scanner.nextInt();
    	if (choice == 1) {
    		for(Customer c :customers) {
    			System.out.println("" + c);
    		}
    	}
    	else if(choice == 2)
    		break;
    	else 
    		return;
    }catch(InputMismatchException e) {
    	scanner.nextLine();
    }
}
    String identityNum = scanner.nextLine();
    if (identityNum.equals("0")) return;
    try {
        Customer c = manager.findCustomerbyIdentityNumber(identityNum);
        printCustomerInfo(c);

        System.out.println("\nRental History:");
        ArrayList<Rental> history = c.getRentalHistory();
        if (history.isEmpty()) {
            System.out.println("  No rentals.");
        } else {
            for (Rental r : history) {
                System.out.println("  Rental " + r.getRentalID() + ": " + r.getVehicle().getBrand() + " " +
                        r.getVehicle().getModel() + ", " + r.getStartDate() + " to " + r.getEndDate() +
                        " (" + r.getDurationDays() + " days), Total: " + String.format("%.2f", r.getTotalCost()) + " euro");
            }
        }

        LocalDate today = LocalDate.now();
        boolean hasActive = false;
        for (Rental r : history) {
            if (!today.isBefore(r.getStartDate()) && !today.isAfter(r.getEndDate())) {
                if (!hasActive) {
                    System.out.println("\nActive Rental(s):");
                    hasActive = true;
                }
                manager.printInvoice(r);
            }
        }
        if (!hasActive) {
            System.out.println("No active rental.");
        }
    } catch (CustomerNotFoundException e) {
        System.out.println("Error: " + e.getMessage());
    }
}

private static void vehicleHistory() {
    System.out.println("[Enter Vehicle Registration Number] (0 to go back)");
    String regNum = scanner.nextLine();
    if (regNum.equals("0")) return;
    Vehicle vehicle = manager.findVehiclebyRegistration(regNum);
    if (vehicle == null) {
        System.out.println("Vehicle not found.");
        return;
    }

    System.out.println("\n--- Vehicle Details ---");
    System.out.println("Type: " + vehicle.getType());
    System.out.println("Registration: " + vehicle.getRegistrationNumber());
    System.out.println("Brand: " + vehicle.getBrand());
    System.out.println("Model: " + vehicle.getModel());
    System.out.println("Year: " + vehicle.getCreationYear());
    System.out.println("Seats: " + vehicle.getSeats());
    System.out.println("Daily Rate: " + String.format("%.2f", vehicle.getDailyRate()) + " euro");
    System.out.println("Total Kilometers: " + vehicle.getTotalKilometers());
    System.out.println("Next Maintenance At: " + vehicle.getNextMaintenanceAt() + " km");

    LocalDate today = LocalDate.now();
    boolean currentlyRented = false;
    ArrayList<Rental> history = manager.findVehicleRentalHistory(regNum);
    for (Rental r : history) {
        if (!today.isBefore(r.getStartDate()) && !today.isAfter(r.getEndDate())) {
            currentlyRented = true;
            break;
        }
    }

    boolean underMaintenance = vehicle.isUnderMaintenance(today, today);
    if (underMaintenance) {
        System.out.println("Status: Under Maintenance");
    } else if (currentlyRented) {
        System.out.println("Status: Currently Rented");
    } else {
        System.out.println("Status: Available");
    }

    System.out.println("\nRental History:");
    if (history.isEmpty()) {
        System.out.println("  No rentals.");
    } else {
        for (Rental r : history) {
            System.out.println("  Rental " + r.getRentalID() + ": Customer: " + r.getCustomer().getName() +
                    ", " + r.getStartDate() + " to " + r.getEndDate() +
                    " (" + r.getDurationDays() + " days), Total: " + String.format("%.2f", r.getTotalCost()) + " euro");
        }
    }

    System.out.println("\nMaintenance History:");
    ArrayList<MaintenanceRecord> maintenance = vehicle.getMaintenanceHistory();
    if (maintenance.isEmpty()) {
        System.out.println("  No maintenance records.");
    } else {
        for (MaintenanceRecord m : maintenance) {
            System.out.println("  " + m);
        }
    }
}

private static void printAllData() {
    System.out.println("\n========== ALL VEHICLES ==========");
    ArrayList<Vehicle> vehicles = manager.displayAllVehicles();
    if (vehicles.isEmpty()) {
        System.out.println("  No vehicles.");
    } else {
        for (Vehicle v : vehicles) {
            System.out.println("  " + v);
            System.out.println("  ----------------------------------");
        }
    }

    System.out.println("\n========== ALL CUSTOMERS ==========");
    ArrayList<Customer> customers = manager.displayAllCustomers();
    if (customers.isEmpty()) {
        System.out.println("  No customers.");
    } else {
        for (Customer c : customers) {
            System.out.println("  " + c.getName() + " | ID: " + c.getIdentityNumber() +
                    " | Loyalty: " + c.getLoyaltyCardID() + " | Points: " + c.getLoyaltyPoints());
            System.out.println("  ----------------------------------");
        }
    }

    System.out.println("\n========== ALL RENTALS ==========");
    ArrayList<Rental> rentals = manager.displayAllRentals();
    if (rentals.isEmpty()) {
        System.out.println("  No rentals.");
    } else {
        for (Rental r : rentals) {
            System.out.println("  " + r.getRentalID() + " | " + r.getCustomer().getName() + " | " +
                    r.getVehicle().getBrand() + " " + r.getVehicle().getModel() +
                    " [" + r.getVehicle().getRegistrationNumber() + "] | " +
                    r.getStartDate() + " to " + r.getEndDate() +
                    " (" + r.getDurationDays() + " days) | Total: " + String.format("%.2f", r.getTotalCost()) + " euro");
            for (AdditionalServices s : r.getServices()) {
                System.out.println("    Service: " + s.getName() + " - " +
                        String.format("%.2f", s.calculateCost(r.getDurationDays())) + " euro");
            }
            System.out.println("  ----------------------------------");
        }
    }
}
private static void printAllMaintenance() {
    System.out.println("\n========== ALL MAINTENANCE HISTORY ==========");
    ArrayList<Vehicle> vehicles = manager.displayAllVehicles();
    boolean found = false;
    for (Vehicle v : vehicles) {
        ArrayList<MaintenanceRecord> records = v.getMaintenanceHistory();
        if (!records.isEmpty()) {
            found = true;
            System.out.println("\n" + v.getType() + " [" + v.getRegistrationNumber() + "] " +
                    v.getBrand() + " " + v.getModel() + " (" + v.getTotalKilometers() + " km total):");
            for (MaintenanceRecord m : records) {
                System.out.println("  " + m);
            }
        }
    }
    if (!found) {
        System.out.println("  No maintenance records for any vehicle.");
    }
}

public static void main(String[] args) {
    FileManager.createFiles();
    FileManager.loadAllData(manager);

    int choice;
    while (true) {
        printMenu();
        try {
            choice = scanner.nextInt();
        } catch (InputMismatchException e) {
            System.out.println("Invalid choice!");
            scanner.nextLine();
            continue;
        }

        if (choice == 0) {
            FileManager.saveAllData(manager.displayAllVehicles(), manager.displayAllCustomers(), manager.displayAllRentals());
            System.out.println("Data saved. Goodbye!");
            break;
        }

        if (choice == 1) {
            boolean mainMenu = false;
            while (true) {
                int choice_ = 0;
                try {
                    if (!mainMenu) {
                        System.out.println("\n[Options] \n [1]Add \n [2]Delete\n [0]Back");
                        choice_ = scanner.nextInt();
                    } else {
                        break;
                    }
                } catch (InputMismatchException e) {
                    System.out.println("Invalid choice!");
                    scanner.nextLine();
                    continue;
                }
                scanner.nextLine();

                if (choice_ == 0) {
                    break;
                }
                if (choice_ == 1) {
                    while (true) {
                        System.out.println("[Options]");
                        System.out.println("1. [Economy Car]");
                        System.out.println("2. [SUV Car]");
                        System.out.println("3. [Convertible Car]");
                        System.out.println("4. [Luxury Car]");
                        System.out.println("5. [Main Menu]");
                        System.out.println("0. [Back]");
                        int carchoice;
                        try {
                            carchoice = scanner.nextInt();
                        } catch (InputMismatchException e) {
                            System.out.println("Invalid choice!");
                            scanner.nextLine();
                            continue;
                        }
                        scanner.nextLine();

                        if (carchoice == 0) {
                            break;
                        }
                        if (carchoice == 5) {
                            mainMenu = true;
                            break;
                        }
                        if (carchoice >= 1 && carchoice <= 4) {
                            Vehicle car = getVehicleSpecs(carchoice);
                            if (car != null) {
                                try {
                                    manager.addVehicle(car);
                                    FileManager.appendVehicle(car);
                                    System.out.println("Vehicle added successfully!");
                                } catch (VehicleAlreadyExistsException e) {
                                    System.out.println("Error: " + e.getMessage());
                                }
                            }
                        } else {
                            System.out.println("Invalid choice!");
                        }
                    }
                }
                if (choice_ == 2) {
                    ArrayList<Vehicle> vehicles = manager.displayAllVehicles();
                    if (vehicles.isEmpty()) {
                        System.out.println("No vehicles in the fleet.");
                    } else {
                        System.out.println("\nSelect a vehicle to delete:");
                        for (int i = 0; i < vehicles.size(); i++) {
                            System.out.println((i + 1) + ". " + vehicles.get(i));
                        }
                        System.out.println("0. [Back]");
                        int delChoice;
                        try {
                            delChoice = scanner.nextInt();
                        } catch (InputMismatchException e) {
                            System.out.println("Invalid input!");
                            scanner.nextLine();
                            continue;
                        }
                        scanner.nextLine();

                        if (delChoice >= 1 && delChoice <= vehicles.size()) {
                            Vehicle toDelete = vehicles.get(delChoice - 1);
                            try {
                                manager.removeVehicle(toDelete.getRegistrationNumber());
                                FileManager.saveVehicles(manager.displayAllVehicles());
                                System.out.println("Vehicle removed successfully!");
                            } catch (Exception e) {
                                System.out.println("Error: " + e.getMessage());
                            }
                        } else if (delChoice != 0) {
                            System.out.println("Invalid choice!");
                        }
                    }
                }
            }
        } else if (choice == 2) {
            scanner.nextLine();
            while (true) {
                System.out.println("\t[Options] \n [1] Register Customer\n [2] Search by Identity Number\n [3] Search by Loyalty Card\n [4] Display All Customers\n [0] Back");
                int custChoice;
                try {
                    custChoice = scanner.nextInt();
                } catch (InputMismatchException e) {
                    System.out.println("Invalid choice!");
                    scanner.nextLine();
                    continue;
                }
                scanner.nextLine();

                if (custChoice == 0) {
                    break;
                } else if (custChoice == 1) {
                    registerCustomer();
                } else if (custChoice == 2) {
                    searchByIdentityNumber();
                } else if (custChoice == 3) {
                    searchByLoyaltyCardID();
                } else if (custChoice == 4) {
                    displayAllCustomers();
                } else {
                    System.out.println("Invalid choice!");
                }
            }
        } else if (choice == 3) {
            scanner.nextLine();
            createRental();
        } else if (choice == 4) {
            scanner.nextLine();
            printInvoice();
        } else if (choice == 5) {
            scanner.nextLine();
            vehicleAvailability();
        } else if (choice == 6) {
            scanner.nextLine();
            customerHistory();
        } else if (choice == 7) {
            scanner.nextLine();
            vehicleHistory();
        } else if (choice == 8) {
            printAllData();
        } else if (choice == 9) {
            printAllMaintenance();
        } else {
            System.out.println("Invalid Choice!");
            }
        }

        scanner.close();
    }
}