package vehiclemanagementsystem;

import java.time.LocalDate;

public class MaintenanceRecord {
    private LocalDate startDate;
    private LocalDate endDate;
    private int kilometersMaintained;
    private String description;

    public MaintenanceRecord(LocalDate startDate, LocalDate endDate, int kilometersMaintained, String description) {
        this.startDate = startDate;
        this.endDate = endDate;
        this.kilometersMaintained = kilometersMaintained;
        this.description = description;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public int getKilometersMaintained() {
        return kilometersMaintained;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public String toString() {
        return "Maintenance: " + startDate + " to " + endDate +
               ", KM: " + kilometersMaintained +
               ", Description: " + description;
    }
}