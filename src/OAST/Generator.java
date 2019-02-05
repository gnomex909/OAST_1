package OAST;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Random;

//Klasa ta ma za zadanie wygenerować posortowaną listę wydarzeń dla pojedynczej symulacji
//p = lambda/ mi
public class Generator {
    private Random generator;
    private double packetLastTime = 0;
    private double exitLastTime = 0;

    private double genExpNum(double gamma){
        double u = generator.nextDouble();
        double x = Math.log(1 - u) / (gamma) * -1;
        return x;
    }
    //p = lambda/ mi, w szczególnym przypadku p = p/1 (jeśli chodzi o uzyskanie tego samego wyniku
    public ArrayList<WebEvent> genEventList(double endTime, double p){
        ArrayList<WebEvent> webEvents = new ArrayList<>();
        double randomNumber;
        while(packetLastTime < endTime){
            //Generujemy wydarzenie nadejśćia nowego pakietu, ustalając jako punkt startu czas nadejścia poprzedniego pakietu (inaczej kolejka skakałaby między stanami 1 i 0 jedynie)
            randomNumber = genExpNum(p);
            packetLastTime += randomNumber;
            webEvents.add(new WebEvent(packetLastTime, true));
            //Generujemy wydarzenie wyjścia tego pakietu, w oparciu o czas jego nadejścia jeśli serwer był pusty, lub w oparciu o wyjśćie porpzedniego pakietu
            randomNumber = genExpNum(1);
            if(exitLastTime<= packetLastTime || exitLastTime+randomNumber<= packetLastTime) {
                exitLastTime = packetLastTime + randomNumber;
                webEvents.add(new WebEvent(exitLastTime, false));
            }
            else{
                exitLastTime += randomNumber;
                webEvents.add(new WebEvent(exitLastTime,false));
            }
        }
//        sortowanie używając wbudowanych biliotek JDK
        Collections.sort(webEvents, new Comparator<WebEvent>() {
            @Override
            public int compare(WebEvent o1, WebEvent o2) {
                if(o1.getEventTime() == o2.getEventTime())
                    return 0;
                return o1.getEventTime() < o2.getEventTime() ? -1 : 1;
            }
        });
//        for(WebEvent e :webEvents){
//            System.out.println(e);
//        }
        return webEvents;
    }
    public Generator() {
        generator = new Random(System.currentTimeMillis());
    }
}
