package pl.agh.edu.mwo.analiza;

public class Seat{

    private final String name;
    private boolean isAvailable;

    public String getName() {
        return name;
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    public void setAvailable(boolean available) {
        isAvailable = available;
    }

    public Seat(String name, boolean isAvailable) {
        this.name = name;
        this.isAvailable = isAvailable;
    }

    @Override
    public String toString() {
        return name ;
    }

    public void lockSeat() {
        this.setAvailable(false);
    }
}
