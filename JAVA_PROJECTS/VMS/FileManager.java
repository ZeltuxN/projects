package vehiclemanagementsystem;

import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Scanner;

public class FileManager {

    public static void createFiles() {
        createFile("vehicles.csv");
        createFile("customers.csv");
        createFile("rentals.csv");
        createFile("maintenance.csv");
        createFile("Brands.txt");
    }

    public static void createFile(String fileName) {
        try {
            File file = new File(fileName);
            file.createNewFile();
        } catch (IOException e) {
            System.out.println("File could not be created: " + e.getMessage());
        }
    }

    public static void saveVehicles(ArrayList<Vehicle> vehicles) {
        try (FileWriter writer = new FileWriter("vehicles.csv")) {
            writer.write("registrationNumber,brand,model,creationYear,dailyRate,type,seats,minSeats,maxSeats,totalKilometers,nextMaintenanceAt\n");
            for (Vehicle v : vehicles) {
                String type = v.getClass().getSimpleName();
                writer.write(v.getRegistrationNumber() + "," + v.getBrand() + "," + v.getModel() + "," +
                             v.getCreationYear() + "," + v.getDailyRate() + "," + type + "," +
                             v.getSeats() + "," + v.getMinSeats() + "," + v.getMaxSeats() + "," +
                             v.getTotalKilometers() + "," + v.getNextMaintenanceAt() + "\n");
            }
        } catch (IOException e) {
            System.out.println("Error saving vehicles: " + e.getMessage());
        }
    }

    public static void appendVehicle(Vehicle vehicle) {
        File vehicles = new File("vehicles.csv");
        boolean needsHeader = (vehicles.length() == 0);
        try (FileWriter writer = new FileWriter("vehicles.csv", true)) {
            if (needsHeader) {
                writer.write("registrationNumber,brand,model,creationYear,dailyRate,type,seats,minSeats,maxSeats,totalKilometers,nextMaintenanceAt\n");
            }
            String type = vehicle.getClass().getSimpleName();
            writer.write(vehicle.getRegistrationNumber() + "," + vehicle.getBrand() + "," + vehicle.getModel() + "," +
                         vehicle.getCreationYear() + "," + vehicle.getDailyRate() + "," + type + "," +
                         vehicle.getSeats() + "," + vehicle.getMinSeats() + "," + vehicle.getMaxSeats() + "," +
                         vehicle.getTotalKilometers() + "," + vehicle.getNextMaintenanceAt() + "\n");
        } catch (IOException e) {
            System.out.println("Error appending vehicle: " + e.getMessage());
        }
    }

    public static ArrayList<Vehicle> loadVehicles() {
        ArrayList<Vehicle> loadedVehicles = new ArrayList<>();
        try (Scanner loader = new Scanner(new FileReader("vehicles.csv"))) {
            if (loader.hasNextLine()) {
                loader.nextLine();
            }
            while (loader.hasNextLine()) {
                String line = loader.nextLine().trim();
                if (line.isEmpty()) continue;

                String[] parts = line.split(",");
                String registrationNumber = parts[0];
                String brand = parts[1];
                String model = parts[2];
                int creationYear = Integer.parseInt(parts[3]);
                String type = parts[5];
                int seats = Integer.parseInt(parts[6]);

                int totalKilometers = 0;
                int nextMaintenanceAt = 10000;
                if (parts.length > 9) {
                    totalKilometers = Integer.parseInt(parts[9]);
                }
                if (parts.length > 10) {
                    nextMaintenanceAt = Integer.parseInt(parts[10]);
                }

                boolean isDuplicate = false;
                for (Vehicle existing : loadedVehicles) {
                    if (existing.getRegistrationNumber().equals(registrationNumber)) {
                        System.out.println("Warning: Vehicle " + registrationNumber + " already exists, skipping duplicate.");
                        isDuplicate = true;
                        break;
                    }
                }

                if (!isDuplicate) {
                    try {
                        Vehicle loadedVehicle = null;
                        if (type.equals("EconomyCar")) {
                            loadedVehicle = new EconomyCar(registrationNumber, brand, model, creationYear, seats);
                        } else if (type.equals("SUV")) {
                            loadedVehicle = new SUV(registrationNumber, brand, model, creationYear, seats);
                        } else if (type.equals("Convertible")) {
                            loadedVehicle = new Convertible(registrationNumber, brand, model, creationYear, seats);
                        } else if (type.equals("LuxuryCar")) {
                            loadedVehicle = new LuxuryCar(registrationNumber, brand, model, creationYear, seats);
                        } else {
                            System.out.println("Invalid car type: " + type);
                        }

                        if (loadedVehicle != null) {
                            loadedVehicle.setTotalKilometers(totalKilometers);
                            loadedVehicle.setNextMaintenanceAt(nextMaintenanceAt);
                            loadedVehicles.add(loadedVehicle);
                        }
                    } catch (InvalidSeatsException e) {
                        System.out.println("Invalid seats for vehicle " + registrationNumber + ": " + e.getMessage());
                    }
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("Vehicles file not found.");
        }
        return loadedVehicles;
    }

    public static void saveCustomers(ArrayList<Customer> customers) {
        try (FileWriter writer = new FileWriter("customers.csv")) {
            writer.write("name,identityNumber,address,email,phoneNumber,creditCardNumber,loyaltyCardID,loyaltyPoints\n");
            for (Customer c : customers) {
                writer.write(c.getName() + "," + c.getIdentityNumber() + "," +
                             c.getAddress() + "," + c.getEmail() + "," + c.getPhoneNumber() + "," +
                             c.getCreditCardNumber() + "," + c.getLoyaltyCardID() + "," + c.getLoyaltyPoints() + "\n");
            }
        } catch (IOException e) {
            System.out.println("Error saving customers: " + e.getMessage());
        }
    }

    public static void appendCustomer(Customer c) {
        File customer = new File("customers.csv");
        boolean needsHeader = (customer.length() == 0);
        try (FileWriter writer = new FileWriter("customers.csv", true)) {
            if (needsHeader) {
                writer.write("name,identityNumber,address,email,phoneNumber,creditCardNumber,loyaltyCardID,loyaltyPoints\n");
            }
            writer.write(c.getName() + "," + c.getIdentityNumber() + "," +
                         c.getAddress() + "," + c.getEmail() + "," + c.getPhoneNumber() + "," +
                         c.getCreditCardNumber() + "," + c.getLoyaltyCardID() + "," + c.getLoyaltyPoints() + "\n");
        } catch (IOException e) {
            System.out.println("Error appending customer: " + e.getMessage());
        }
    }

    public static ArrayList<Customer> loadCustomers() {
        ArrayList<Customer> loadedCustomers = new ArrayList<>();
        try (Scanner loader = new Scanner(new FileReader("customers.csv"))) {
            if (loader.hasNextLine()) {
                loader.nextLine();
            }
            while (loader.hasNextLine()) {
                String line = loader.nextLine().trim();
                if (line.isEmpty()) continue;

                String[] parts = line.split(",");
                String name = parts[0];
                String identityNumber = parts[1];
                String address = parts[2];
                String email = parts[3];
                String phoneNumber = parts[4];
                String creditCardNumber = parts[5];
                String loyaltyCardID = parts[6];
                int loyaltyPoints = Integer.parseInt(parts[7]);

                boolean isDuplicate = false;
                for (Customer existing : loadedCustomers) {
                    if (existing.getIdentityNumber().equals(identityNumber)) {
                        System.out.println("Warning: Customer " + identityNumber + " already exists, skipping duplicate.");
                        isDuplicate = true;
                        break;
                    }
                }

                if (!isDuplicate) {
                    Customer cust = new Customer(name, identityNumber, address, email, phoneNumber, creditCardNumber, loyaltyCardID);
                    cust.setLoyaltyPoints(loyaltyPoints);
                    loadedCustomers.add(cust);
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("Customers file not found.");
        }
        return loadedCustomers;
    }

    public static void saveRentals(ArrayList<Rental> rentals) {
        try (FileWriter writer = new FileWriter("rentals.csv")) {
            writer.write("rentalID,identityNumber,registrationNumber,startDate,durationDays,discount,totalCost,services\n");
            for (Rental r : rentals) {
                StringBuilder servicesStr = new StringBuilder();
                for (int i = 0; i < r.getServices().size(); i++) {
                    if (i > 0) servicesStr.append(";");
                    servicesStr.append(r.getServices().get(i).getClass().getSimpleName());
                }

                writer.write(r.getRentalID() + "," +
                             r.getCustomer().getIdentityNumber() + "," +
                             r.getVehicle().getRegistrationNumber() + "," +
                             r.getStartDate() + "," +
                             r.getDurationDays() + "," +
                             r.getDiscount() + "," +
                             r.getTotalCost() + "," +
                             servicesStr.toString() + "\n");
            }
        } catch (IOException e) {
            System.out.println("Error saving rentals: " + e.getMessage());
        }
    }

    public static void appendRental(Rental r) {
        File rentals = new File("rentals.csv");
        boolean needsHeader = (rentals.length() == 0);
        try (FileWriter writer = new FileWriter("rentals.csv", true)) {
            if (needsHeader) {
                writer.write("rentalID,identityNumber,registrationNumber,startDate,durationDays,discount,totalCost,services\n");
            }

            StringBuilder servicesStr = new StringBuilder();
            for (int i = 0; i < r.getServices().size(); i++) {
                if (i > 0) servicesStr.append(";");
                servicesStr.append(r.getServices().get(i).getClass().getSimpleName());
            }

            writer.write(r.getRentalID() + "," +
                         r.getCustomer().getIdentityNumber() + "," +
                         r.getVehicle().getRegistrationNumber() + "," +
                         r.getStartDate() + "," +
                         r.getDurationDays() + "," +
                         r.getDiscount() + "," +
                         r.getTotalCost() + "," +
                         servicesStr.toString() + "\n");
        } catch (IOException e) {
            System.out.println("Error appending rental: " + e.getMessage());
        }
    }

    public static ArrayList<Rental> loadRentals(ArrayList<Vehicle> vehicles, ArrayList<Customer> customers) {
        ArrayList<Rental> loadedRentals = new ArrayList<>();
        try (Scanner loader = new Scanner(new FileReader("rentals.csv"))) {
            if (loader.hasNextLine()) {
                loader.nextLine();
            }
            while (loader.hasNextLine()) {
                String line = loader.nextLine().trim();
                if (line.isEmpty()) continue;

                String[] parts = line.split(",", -1);
                String rentalID = parts[0];
                String identityNumber = parts[1];
                String registrationNumber = parts[2];
                LocalDate startDate = LocalDate.parse(parts[3]);
                int durationDays = Integer.parseInt(parts[4]);
                double discount = Double.parseDouble(parts[5]);
                double totalCost = Double.parseDouble(parts[6]);
                String servicesStr = parts.length > 7 ? parts[7] : "";

                boolean isDuplicate = false;
                for (Rental existing : loadedRentals) {
                    if (existing.getRentalID().equals(rentalID)) {
                        System.out.println("Warning: Rental " + rentalID + " already exists, skipping duplicate.");
                        isDuplicate = true;
                        break;
                    }
                }

                if (!isDuplicate) {
                    Customer customer = null;
                    Vehicle vehicle = null;

                    for (Customer c : customers) {
                        if (c.getIdentityNumber().equals(identityNumber)) {
                            customer = c;
                            break;
                        }
                    }

                    for (Vehicle v : vehicles) {
                        if (v.getRegistrationNumber().equals(registrationNumber)) {
                            vehicle = v;
                            break;
                        }
                    }

                    if (customer != null && vehicle != null) {
                        Rental rental = new Rental(rentalID, customer, vehicle, startDate, durationDays);
                        rental.setDiscount(discount);
                        rental.setTotalCost(totalCost);

                        if (!servicesStr.isEmpty()) {
                            String[] serviceNames = servicesStr.split(";");
                            for (String sName : serviceNames) {
                                switch (sName.trim()) {
                                    case "GPS":
                                        rental.addService(new GPS());
                                        break;
                                    case "ChildSeat":
                                        rental.addService(new ChildSeat());
                                        break;
                                    case "AdditionalSafety":
                                        rental.addService(new AdditionalSafety());
                                        break;
                                }
                            }
                        }

                        loadedRentals.add(rental);
                        customer.addRental(rental);
                    }
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("Rentals file not found.");
        }
        return loadedRentals;
    }

    public static void saveMaintenance(ArrayList<Vehicle> vehicles) {
        try (FileWriter writer = new FileWriter("maintenance.csv")) {
            writer.write("registrationNumber,startDate,endDate,kilometersMaintained,description\n");
            for (Vehicle v : vehicles) {
                for (MaintenanceRecord m : v.getMaintenanceHistory()) {
                    writer.write(v.getRegistrationNumber() + "," + m.getStartDate() + "," +
                                 m.getEndDate() + "," + m.getKilometersMaintained() + "," +
                                 m.getDescription() + "\n");
                }
            }
        } catch (IOException e) {
            System.out.println("Error saving maintenance: " + e.getMessage());
        }
    }

    public static void loadMaintenance(ArrayList<Vehicle> vehicles) {
        try (Scanner loader = new Scanner(new FileReader("maintenance.csv"))) {
            if (loader.hasNextLine()) {
                loader.nextLine();
            }
            while (loader.hasNextLine()) {
                String line = loader.nextLine().trim();
                if (line.isEmpty()) continue;

                String[] parts = line.split(",", 5);
                String registrationNumber = parts[0];
                LocalDate startDate = LocalDate.parse(parts[1]);
                LocalDate endDate = LocalDate.parse(parts[2]);
                int kilometersMaintained = Integer.parseInt(parts[3]);
                String description = parts[4];

                for (Vehicle v : vehicles) {
                    if (v.getRegistrationNumber().equals(registrationNumber)) {
                        MaintenanceRecord record = new MaintenanceRecord(startDate, endDate, kilometersMaintained, description);
                        v.getMaintenanceHistory().add(record);
                        break;
                    }
                }
            }
        } catch (FileNotFoundException e) {
        }
    }

    public static void saveAllData(ArrayList<Vehicle> vehicles, ArrayList<Customer> customers, ArrayList<Rental> rentals) {
        saveVehicles(vehicles);
        saveCustomers(customers);
        saveRentals(rentals);
        saveMaintenance(vehicles);
    }

    public static void loadAllData(RentalManager manager) {
        ArrayList<Vehicle> vehicles = loadVehicles();
        ArrayList<Customer> customers = loadCustomers();
        ArrayList<Rental> rentals = loadRentals(vehicles, customers);

        for (Vehicle v : vehicles) {
            try {
                manager.addVehicle(v);
            } catch (VehicleAlreadyExistsException e) {
                System.out.println("Vehicle already exists: " + e.getMessage());
            }
        }

        for (Customer c : customers) {
            try {
                manager.registerCustomer(c);
            } catch (CustomerAlreadyExistsException e) {
                System.out.println("Error registering customer: " + e.getMessage());
            }
        }

        for (Rental r : rentals) {
            manager.getRentalList().add(r);
        }

        loadMaintenance(vehicles);

        int maxRentalNum = 0;
        for (Rental r : rentals) {
            String num = r.getRentalID().substring(1);
            int n = Integer.parseInt(num);
            if (n > maxRentalNum) maxRentalNum = n;
        }
        manager.setRentalCounter(maxRentalNum + 1);

        int maxLoyaltyNum = 0;
        for (Customer c : customers) {
            String loyalty = c.getLoyaltyCardID();
            if (loyalty != null && loyalty.length() > 1) {
                try {
                    int n = Integer.parseInt(loyalty.substring(1));
                    if (n > maxLoyaltyNum) maxLoyaltyNum = n;
                } catch (NumberFormatException e) {
                }
            }
        }
        manager.setLoyaltyCardCounter(maxLoyaltyNum + 1);
    }
}