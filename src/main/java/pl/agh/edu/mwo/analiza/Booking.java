package pl.agh.edu.mwo.analiza;

import lombok.extern.slf4j.Slf4j;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Slf4j
public class Booking {
    private String bookingNumber;
    private final List<Ticket> ticketsForBooking;
    private BigDecimal totalPrice;
    private boolean isPaid;

    public Booking(List<Ticket> ticketsForBooking, String bookingNumber) {
        this.ticketsForBooking = ticketsForBooking;
        this.bookingNumber = bookingNumber;
        this.isPaid = false;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public String getBookingNumber() {
        return bookingNumber;
    }

    public List<Ticket> getTicketsForBooking() {
        return ticketsForBooking;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }

    public boolean isPaid() {
        return isPaid;
    }

    public void setPaid(boolean paid) {
        isPaid = paid;
    }


    public void getInfoToPayAtCheckout() {
        System.out.println("Successfully booking process for booking number: " + bookingNumber +
                ". You should pay in checkout before the screening. Total price: " + totalPrice + " PLN");
    }

    public void getInfoForSuccessfullyBookedAndPaid() {
        List<String> seatsForBooking = new ArrayList<>();
        StringBuilder stringBuilder = new StringBuilder(
                "Successfully booking and payment process for booking number: " + bookingNumber + " for total price: "
                        + totalPrice + " PLN. See you in front of the screen.");
        ticketsForBooking.forEach(ticket -> {
            String seat = ticket.getSeat();
            seatsForBooking.add(seat);
        });
        System.out.println(stringBuilder.append("\nYou booked following seats: ").append(seatsForBooking));
    }

    public void getInfoAboutEmptyTicketList() {
        System.out.println("We don't have any tickets in storage, because you select locked seats");
    }


}
