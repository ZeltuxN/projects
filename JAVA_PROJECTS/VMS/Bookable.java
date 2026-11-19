package vehiclemanagementsystem;

import java.time.LocalDate;

public interface Bookable {
    boolean isAvailable(LocalDate startDate, int durationDays);
}