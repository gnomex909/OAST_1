package OAST;

import java.util.ArrayList;
import java.util.HashMap;

//      Klasa zajmująca się analizą danych z kolejnych symulacji.
public class Analyser {
    private ArrayList<double[]> simulationResultsList;


    public Analyser() {
        simulationResultsList = new ArrayList<>();
    }
//    Funkcja mająca na celu dodanie zliczonych czasów przebywania we wszystkich stanach do listy obiektów
    public void addResults(double[] results){
        simulationResultsList.add(results);
    }
//    Funkcja zmieniająca czas przebywania w danym stanie na prawdopodobieństwo
    private ArrayList<Double> convertToProbability(double[] times, int length){
        ArrayList<Double> probabilityList = new ArrayList<>();
        for(double t : times){
            double probability = t/(double) length;
            probabilityList.add(probability);
        }
        return  probabilityList;
    }
//    Funkcja obliczająca średnią ilość klientów w systemie
    private double calculateAvgClient(ArrayList<Double> probabilityList){
        double avgAmountOfClients = 0;
        for(int i = 0; i< probabilityList.size(); i++){
            avgAmountOfClients +=  i * probabilityList.get(i);
        }
        return  avgAmountOfClients;
    }
//    Funkcja obliczająca średni czas przebywania w systemie
    private double calculateDelay(int amountOfClients, double timeOfLastEvent, int startPeriod, double zeroProbability){
        double length = (timeOfLastEvent - startPeriod);
        return (length - length*zeroProbability) / (double) amountOfClients;
    }
//    Główna funkcja, używająca pozostałych, żeby uzyskać średni wynik ze wszystkich symulacji i przekazać go do Controllera
    public HashMap<String, Double> calculateResults(ArrayList<Integer> amountOfClients, int length, ArrayList<Double> timeOfLastEvent, int startPeriod){
        HashMap<String,Double> results = new HashMap<>();
        ArrayList<ArrayList<Double>> probabilities = new ArrayList<>();
        ArrayList<Double> avgAmounts = new ArrayList<>();
        ArrayList<Double> delays = new ArrayList<>();
//        zamiana czasów na prawdopodbieństwa
        for(double[] d : simulationResultsList){
            ArrayList<Double> probability = convertToProbability(d, length);
            probabilities.add(probability);
            avgAmounts.add(calculateAvgClient(probability));
            delays.add(calculateDelay(amountOfClients.get(simulationResultsList.indexOf(d)), timeOfLastEvent.get(simulationResultsList.indexOf(d)), startPeriod, probability.get(0)));
        }
//         wyliczenie średniego prawdopodobieństwa
        ArrayList<Double> probResults = new ArrayList<>();
        for(int i=0; i<probabilities.get(0).size();i++){
            double sum = 0;
            for(int j = 0; j<probabilities.size(); j++){
                sum += probabilities.get(j).get(i);
            }
            probResults.add(sum);
        }
        for(int i = 0; i<probResults.size();i++){
            results.put("Probability_"+i,(probResults.get(i) / (double) simulationResultsList.size()));
        }
//        część odpowiadająca za wyliczenie średniej ilości klientów w systemie
        double sum = 0;
        for(double d :avgAmounts){
            sum += d;
        }
        results.put("Client",(sum / (double) simulationResultsList.size()));
//        część, która wylicza czas przebywania w systemie pakietu
        sum = 0;
        for(double d: delays){
            sum += d;
        }
        results.put("Delay", (sum/ (double) simulationResultsList.size()));

        return  results;
    };
}
