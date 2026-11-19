package vehiclemanagementsystem;

import java.time.LocalDate;
import java.util.ArrayList;

public class Rental {
    private String rentalID;
    private Customer customer;
    private Vehicle vehicle;
    private LocalDate startDate;
    private int durationDays;
    private ArrayList<AdditionalServices> services;
    private double discount;
    private double totalCost;

    public Rental(String rentalID, Customer customer, Vehicle vehicle,
                  LocalDate startDate, int durationDays) {
        this.rentalID = rentalID;
        this.customer = customer;
        this.vehicle = vehicle;
        this.startDate = startDate;
        this.durationDays = durationDays;
        this.services = new ArrayList<>();
        this.discount = 0;
        this.totalCost = 0;
    }

    public LocalDate getEndDate() {
        return startDate.plusDays(durationDays - 1);
    }

    public int getDurationDays() {
        return durationDays;
    }

    public double getDailyCost() {
        return vehicle.getDailyRate() * durationDays;
    }

    public double getServicesCost() {
        double total = 0;
        for (AdditionalServices service : services) {
            total += service.calculateCost(durationDays);
        }
        return total;
    }

    public void addService(AdditionalServices service) {
        services.add(service);
    }

    public void calculateTotalCost() {
        this.totalCost = Math.max(0, getDailyCost() + getServicesCost() - discount);
    }

    public String getRentalID() {
        return rentalID;
    }

    public void setRentalID(String rentalID) {
        this.rentalID = rentalID;
    }

    public Customer getCustomer() {
        return customer;
    }

    public Vehicle getVehicle() {
        return vehicle;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public double getDiscount() {
        return discount;
    }

    public void setDiscount(double discount) {
        this.discount = discount;
    }

    public double getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(double totalCost) {
        this.totalCost = totalCost;
    }

    public ArrayList<AdditionalServices> getServices() {
        return services;
    }

    @Override
    public String toString() {
        return "Rental [" + rentalID + "] Customer: " + customer.getName() +
               ", Vehicle: " + vehicle.getBrand() + " " + vehicle.getModel() +
               " (" + vehicle.getRegistrationNumber() + ")" +
               ", " + startDate + " to " + getEndDate() +
               " (" + durationDays + " days)" +
               ", Total: €" + String.format("%.2f", totalCost);
    }
}