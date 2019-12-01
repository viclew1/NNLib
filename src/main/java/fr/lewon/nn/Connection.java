package fr.lewon.nn;

public class Connection {

    private final Long from;
    private final Long to;
    private double weight;
    private boolean active = true;

    public Connection(Long from, Long to, double weight) {
        this.from = from;
        this.to = to;
        this.weight = weight;
    }

    public boolean isActive() {
        return this.active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public Long getFrom() {
        return this.from;
    }

    public Long getTo() {
        return this.to;
    }

    public double getWeight() {
        return this.weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

}
