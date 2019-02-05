package OAST;

import java.util.ArrayList;

//Klasa mająca za zadanie zliczenie czasów w których system przebywał w różncyh stanach
public class Counter {
    double[] timesInState;

//      Funkcja mająca na celu zsumowanie czasów w każdym ze stanów, array jest tak duży z uwagi dla skrajnego przypadku ustawienia p na wartość 0.99
    public double[] getTimesInState(ArrayList<WebEvent> webEvents, int startPeriod){
        this.timesInState = new double[999];
        double lastEventTime = 0;
        double currEventTime = 0;
        int usersInSystem = 0;
        for(WebEvent e : webEvents){
            currEventTime = e.getEventTime();
            if(currEventTime> startPeriod) {
                timesInState[usersInSystem] += (currEventTime - lastEventTime);
            }
            if(e.isPacketIncoming()){
                usersInSystem++;
            }else{
                usersInSystem--;
            }
            lastEventTime = e.getEventTime();
        }
        return this.timesInState;
    };
//      Funkcja zliczająca ilość klientów, którzy przeszli przez system
    public int countClients(ArrayList<WebEvent> webEvents, int startPeriod){
      int amountOfClients = 0;
      for(WebEvent e: webEvents){
          if(!e.isPacketIncoming() && e.getEventTime()> startPeriod){
              amountOfClients++;
          }
      }
//      System.out.println(amountOfClients);
      return  amountOfClients;
    };
}
