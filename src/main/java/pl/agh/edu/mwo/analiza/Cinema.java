package pl.agh.edu.mwo.analiza;

import java.util.ArrayList;
import java.util.List;

public class Cinema {

    private final String cinemaName;
    private final String cinemaAddress;
    private static final List<Booking> bookings = new ArrayList<>();
    private static final List<CinemaRoom> cinemaRooms = new ArrayList<>();

    public Cinema(String cinemaAddress, String cinemaName) {
        this.cinemaAddress = cinemaAddress;
        this.cinemaName = cinemaName;
    }

    public String getCinemaName() {
        return cinemaName;
    }

    public String getCinemaAddress() {
        return cinemaAddress;
    }

    public static List<CinemaRoom> getCinemaRooms() {
        return cinemaRooms;
    }

    public List<Ticket> getTicketsForSelectedCustomer(Customer customer) {
        List<Booking> bookings = Cinema.bookings.stream().toList();
        List<Ticket> tickets = new ArrayList<>();
        for (Booking booking : bookings) {
            List<Ticket> ticketsForBooking = booking.getTicketsForBooking();
            for (Ticket ticket : ticketsForBooking) {
                if (ticket.getCustomer().equals(customer)) {
                    tickets.add(ticket);
                }
            }
        }
        return tickets;
    }

    public static void saveBookingInCinemaStorage(Booking newBooking) {
        bookings.add(newBooking);
    }

    public void displayAllBookings() {
        bookings.forEach(booking ->
                System.out.println("Booking Number: " + booking.getBookingNumber())
        );
    }
    public CinemaRoom createNewCinemaRoom(String name, String description) {
        CinemaRoom cinemaRoom = new CinemaRoom(name, description);
        cinemaRooms.add(cinemaRoom);
        return cinemaRoom;
    }
    public static boolean checkingIfThisCinemaContainsCinemaRoom(String name){
        return getCinemaRooms().stream()
                .anyMatch(cinemaRoom1 -> cinemaRoom1.getName().equals(name));
    }
    public static void removeBookingIfTicketsAreEmpty(Booking booking) {
        if(booking.getTicketsForBooking().isEmpty()){
            bookings.remove(booking);
        }
    }

}
