package vehiclemanagementsystem;

import java.util.ArrayList;

public class Customer {
    private String name;
    private String identityNumber;
    private String address;
    private String email;
    private String phoneNumber;
    private String creditCardNumber;
    private String loyaltyCardID;
    private int loyaltyPoints;
    private ArrayList<Rental> rentalHistory;

    public Customer(String name, String identityNumber,
                    String address, String email, String phoneNumber, String creditCardNumber, String loyaltyCardID) {
        this.name = name;
        this.identityNumber = identityNumber;
        this.address = address;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.creditCardNumber = creditCardNumber;
        this.loyaltyPoints = 0;
        this.loyaltyCardID = loyaltyCardID;
        this.rentalHistory = new ArrayList<>();
    }

    public String getLoyaltyCardID() {
        return loyaltyCardID;
    }

    public void addRental(Rental rental) {
        rentalHistory.add(rental);
    }

    public void addLoyaltyPoints(int points) {
        this.loyaltyPoints += points;
    }

    public void useLoyaltyPoints(int points) {
        this.loyaltyPoints -= points;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIdentityNumber() {
        return identityNumber;
    }

    public void setIdentityNumber(String identityNumber) {
        this.identityNumber = identityNumber;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getCreditCardNumber() {
        return creditCardNumber;
    }

    public void setCreditCardNumber(String creditCardNumber) {
        this.creditCardNumber = creditCardNumber;
    }

    public int getLoyaltyPoints() {
        return loyaltyPoints;
    }

    public void setLoyaltyPoints(int loyaltyPoints) {
        this.loyaltyPoints = loyaltyPoints;
    }

    public ArrayList<Rental> getRentalHistory() {
        return rentalHistory;
    }

    public void setRentalHistory(ArrayList<Rental> rentalHistory) {
        this.rentalHistory = rentalHistory;
    }

    @Override
    public String toString() {
        return "Customer:" + name +
               ", ID: " + identityNumber +
               ", Address: " + address +
               ", Email: " + email +
               ", Phone: " + phoneNumber +
               ", Loyalty Card: " + loyaltyCardID +
               ", Points: " + loyaltyPoints;
    }
}