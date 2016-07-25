package eu.span.dev.osijek.di.compdepend.model;

public class Car
{
    private Seat frontLeft;
    private Seat frontRight;
    private Seat backLeft;
    private Seat backRight;
    private Engine engine;

    // TODO Seat ... seats
    public Car(Seat frontLeft, Seat frontRight, Seat backLeft, Seat backRight, Engine engine)
    {
        this.frontLeft = frontLeft;
        this.frontRight = frontRight;
        this.backLeft = backLeft;
        this.backRight = backRight;
        this.engine = engine;
    }
}
