package vehiclemanagementsystem;

public class ChildSeat extends AdditionalServices {
	public ChildSeat() {
		super("Child Seat", 20.0);
	}

	@Override
	public double calculateCost(int rentalDays) {
		return 20.0;
	}
}