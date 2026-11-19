package vehiclemanagementsystem;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;

public abstract class Vehicle implements Bookable {
    private String registrationNumber;
    private String brand;
    private String model;
    private int creationYear;
    private int seats;
    private double dailyRate;
    private int minSeats;
    private int maxSeats;
    private int totalKilometers;
    private int nextMaintenanceAt;
    private ArrayList<MaintenanceRecord> maintenanceHistory;

    public Vehicle(String registrationNumber, String brand, String model, int creationYear, int seats, double dailyRate) {
        this.registrationNumber = registrationNumber;
        this.brand = brand;
        this.model = model;
        this.creationYear = creationYear;
        this.seats = seats;
        this.dailyRate = dailyRate;
        this.totalKilometers = 0;
        this.nextMaintenanceAt = 10000;
        this.maintenanceHistory = new ArrayList<>();
    }

    @Override
    public boolean isAvailable(LocalDate startDate, int durationDays) {
        LocalDate endDate = startDate.plusDays(durationDays - 1);
        return !isUnderMaintenance(startDate, endDate);
    }

    public void addKilometers(int km) {
        this.totalKilometers += km;
    }

    public boolean needsMaintenance() {
        return totalKilometers >= nextMaintenanceAt;
    }

    public MaintenanceRecord scheduleMaintenance(LocalDate afterDate) {
        LocalDate start = getNextWorkday(afterDate);
        LocalDate end = getNextWorkday(start.plusDays(1));

        String description = "Scheduled maintenance at " + totalKilometers + " km: "
                + "oil change, brake inspection, tire rotation, fluid check, filter replacement";

        MaintenanceRecord record = new MaintenanceRecord(start, end, totalKilometers, description);
        maintenanceHistory.add(record);
        nextMaintenanceAt += 10000;
        return record;
    }

    private LocalDate getNextWorkday(LocalDate date) {
        while (date.getDayOfWeek() == DayOfWeek.SATURDAY || date.getDayOfWeek() == DayOfWeek.SUNDAY) {
            date = date.plusDays(1);
        }
        return date;
    }

    public boolean isUnderMaintenance(LocalDate startDate, LocalDate endDate) {
        for (MaintenanceRecord record : maintenanceHistory) {
            if (!endDate.isBefore(record.getStartDate()) && !startDate.isAfter(record.getEndDate())) {
                return true;
            }
        }
        return false;
    }

    public int getTotalKilometers() {
        return totalKilometers;
    }

    public void setTotalKilometers(int totalKilometers) {
        this.totalKilometers = totalKilometers;
    }

    public int getNextMaintenanceAt() {
        return nextMaintenanceAt;
    }

    public void setNextMaintenanceAt(int nextMaintenanceAt) {
        this.nextMaintenanceAt = nextMaintenanceAt;
    }

    public ArrayList<MaintenanceRecord> getMaintenanceHistory() {
        return maintenanceHistory;
    }

    public int getSeats() {
        return seats;
    }

    public void setSeats(int seats) {
        this.seats = seats;
    }

    public double getDailyRate() {
        return dailyRate;
    }

    public void setDailyRate(double dailyRate) {
        this.dailyRate = dailyRate;
    }

    public int getCreationYear() {
        return creationYear;
    }

    public void setCreationYear(int creationYear) {
        this.creationYear = creationYear;
    }

    public String getRegistrationNumber() {
        return registrationNumber;
    }

    public void setRegistrationNumber(String registrationNumber) {
        this.registrationNumber = registrationNumber;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public void setMinSeats(int minSeats) {
        this.minSeats = minSeats;
    }

    public void setMaxSeats(int maxSeats) {
        this.maxSeats = maxSeats;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public int getMinSeats() {
        return minSeats;
    }

    public int getMaxSeats() {
        return maxSeats;
    }

    public String getType() {
        return this.getClass().getSimpleName();
    }

    @Override
    public String toString() {
        return getType() + " [" + registrationNumber + "] " + brand + " " + model +
               " (" + creationYear + "), " + seats + " seats, €" +
               String.format("%.2f", dailyRate) + "/day, " + totalKilometers + " km";
    }
}