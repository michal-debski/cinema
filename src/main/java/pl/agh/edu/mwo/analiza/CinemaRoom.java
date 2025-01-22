package pl.agh.edu.mwo.analiza;

public class CinemaRoom {
    private long id;
    private final String name;
    private final String description;
    private final int numberOfSeats;

    private int counterOfAvailableSeats;

    public CinemaRoom(long id, String name, String description, int numberOfSeats) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.numberOfSeats = numberOfSeats;
        this.counterOfAvailableSeats = numberOfSeats;
    }


    public int getNumberOfAvailableSeats() {
        return numberOfSeats;
    }

    public String getDescription() {
        return description;
    }

    public String getName() {
        return name;
    }

    public long getId() {
        return id;
    }

    @Override
    public String toString() {
        return "CinemaRoom{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", numberOfSeats=" + numberOfSeats +
                ", counterOfAvailableSeats=" + counterOfAvailableSeats +
                '}';
    }

    public void decreaseAvailableSeatsBasedOnBoughtTickets(Ticket ticket) {
        counterOfAvailableSeats =
                this.counterOfAvailableSeats - ticket.getNumberOfSeatsForAdults() - ticket.getNumberOfSeatsForChildren();
    }
}
