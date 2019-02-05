package OAST;
//Klasa definiuje obiekt wydarzenia sieciowego
public class WebEvent{
    private double eventTime;
    //Mamy tak naprawdę dwa wydarzenia - nadejście i wyjście pakietu. Stąd użycie czegokolwiek innego niż boolean byłoby zbędne.
    private boolean packetIncoming;

    public WebEvent(double eventTime, boolean packetIncoming) {
        this.eventTime = eventTime;
        this.packetIncoming = packetIncoming;
    }

    @Override
    public String toString() {
        return this.eventTime + ":" + this.packetIncoming;
    }


    public double getEventTime() {
        return eventTime;
    }

    public void setEventTime(double eventTime) {
        this.eventTime = eventTime;
    }

    public boolean isPacketIncoming() {
        return packetIncoming;
    }

    public void setPacketIncoming(boolean packetIncoming) {
        this.packetIncoming = packetIncoming;
    }
}
