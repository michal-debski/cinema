package pl.agh.edu.mwo.analiza;

import java.math.BigDecimal;

public class Ticket {

    private int id;
    private int numberOfSeatsForAdults;
    private int numberOfSeatsForChildren;
    private BigDecimal totalPrice;

    public Ticket(int id, int numberOfSeatsForAdults, int numberOfSeatsForChildren) {
        this.id = id;
        this.numberOfSeatsForAdults = numberOfSeatsForAdults;
        this.numberOfSeatsForChildren = numberOfSeatsForChildren;

    }

    public int getId() {
        return id;
    }

    public int getNumberOfSeatsForAdults() {
        return numberOfSeatsForAdults;
    }

    public int getNumberOfSeatsForChildren() {
        return numberOfSeatsForChildren;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

}
