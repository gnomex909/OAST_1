package OAST;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;
import javafx.scene.text.Text;

import java.util.ArrayList;
import java.util.HashMap;

//Klasa służąca kontroli całej symulacji i interfejsu. Z uwagi na ilość pól tekstowych w UI polecam przejść do funkcji OnSim
public class Controller {
    private Analyser analyser;
    private ArrayList<Double> lastEventTime;
    private ArrayList<Integer> clientAmount;
    private ArrayList<Text> probability_info_text;
    private ArrayList<Text> probability_result_text;
    @FXML
    private TextField input_length;
    @FXML
    private TextField input_p;
    @FXML
    private TextField input_start;
    @FXML
    private TextField input_limit;
    @FXML
    private TextField input_iterations;
    @FXML
    private Text text_info_prob_0;
    @FXML
    private Text text_info_prob_1;
    @FXML
    private Text text_info_prob_2;
    @FXML
    private Text text_info_prob_3;
    @FXML
    private Text text_info_prob_4;
    @FXML
    private Text text_info_prob_5;
    @FXML
    private Text text_info_prob_6;
    @FXML
    private Text text_info_prob_7;
    @FXML
    private Text text_info_prob_8;
    @FXML
    private Text text_info_prob_9;
    @FXML
    private Text text_info_prob_10;
    @FXML
    private Text text_info_prob_11;
    @FXML
    private Text text_result_prob_0;
    @FXML
    private Text text_result_prob_1;
    @FXML
    private Text text_result_prob_2;
    @FXML
    private Text text_result_prob_3;
    @FXML
    private Text text_result_prob_4;
    @FXML
    private Text text_result_prob_5;
    @FXML
    private Text text_result_prob_6;
    @FXML
    private Text text_result_prob_7;
    @FXML
    private Text text_result_prob_8;
    @FXML
    private Text text_result_prob_9;
    @FXML
    private Text text_result_prob_10;
    @FXML
    private Text text_result_prob_11;
    @FXML
    private Text text_result_client;
    @FXML
    private Text text_result_time;
    @FXML
    private TitledPane t_pane_1;
    @FXML
    private TitledPane t_pane_2;



    public Controller() {}
//   W funkcji tej agregujemy pola tekstowe probability w listę, żeby ułatwić dostęp
    @FXML
    private void initialize(){
        probability_info_text = new ArrayList<>();
        probability_info_text.add(text_info_prob_0);
        probability_info_text.add(text_info_prob_1);
        probability_info_text.add(text_info_prob_2);
        probability_info_text.add(text_info_prob_3);
        probability_info_text.add(text_info_prob_4);
        probability_info_text.add(text_info_prob_5);
        probability_info_text.add(text_info_prob_6);
        probability_info_text.add(text_info_prob_7);
        probability_info_text.add(text_info_prob_8);
        probability_info_text.add(text_info_prob_9);
        probability_info_text.add(text_info_prob_10);
        probability_info_text.add(text_info_prob_11);
        probability_result_text = new ArrayList<>();
        probability_result_text.add(text_result_prob_0);
        probability_result_text.add(text_result_prob_1);
        probability_result_text.add(text_result_prob_2);
        probability_result_text.add(text_result_prob_3);
        probability_result_text.add(text_result_prob_4);
        probability_result_text.add(text_result_prob_5);
        probability_result_text.add(text_result_prob_6);
        probability_result_text.add(text_result_prob_7);
        probability_result_text.add(text_result_prob_8);
        probability_result_text.add(text_result_prob_9);
        probability_result_text.add(text_result_prob_10);
        probability_result_text.add(text_result_prob_11);
        for(Text t : probability_result_text){
            t.setVisible(false);
        }
        text_result_client.setVisible(false);
        text_result_time.setVisible(false);
    }
//  Funkcja obsługująca przycisk zaczynający symulacje. Nie ma obsługi błędów z uwagi na założenie braku złośliwości użytkownika
    @FXML
    public void onSim(){
        int length = Integer.parseInt(input_length.getText());
        double p = Double.parseDouble(input_p.getText());
        int start = Integer.parseInt(input_start.getText());
        int iterations = Integer.parseInt(input_iterations.getText());
        int limit = Integer.parseInt(input_limit.getText());
        simulationController(length,limit,p,start,iterations);
    }
//   Funkcja, która w oparciu o wprowadzone dane tworzy symulacje, po czym zmienia UI
    @FXML
    public void simulationController(int endTime, int queueLimit, double p, int startPeriod, int iterations){
        lastEventTime = new ArrayList<>();
        clientAmount = new ArrayList<>();
        analyser = new Analyser();
//        Część, która ma na celu wykonanie procesu symulacji.
        for(int i = 0; i<iterations; i++) {
            ArrayList<WebEvent> webEvents = new Generator().genEventList(endTime + startPeriod, p);
            lastEventTime.add(webEvents.get(webEvents.size()-1).getEventTime());
            double[] times = new Counter().getTimesInState(webEvents, startPeriod);
            clientAmount.add(new Counter().countClients(webEvents, startPeriod));
            analyser.addResults(times);
        }
        HashMap<String, Double> results = analyser.calculateResults(clientAmount, endTime+startPeriod, lastEventTime, startPeriod);
//        results.forEach((key, value) -> System.out.println(key + ":" + value));
        System.out.println("Average time in system: " + results.get("Delay"));
        System.out.println("Average clients in system: " + results.get("Client"));

//      Część mająca na celu wprowadzenie zmian w interfejsie
        text_result_client.setText(""+results.get("Client"));
        text_result_client.setVisible(true);
        text_result_time.setText(""+results.get("Delay"));
        text_result_time.setVisible(true);
//       Sprawdzenie czy został narzucony limit na kolejkę, jeśli nie, to wszystkie pola tekstowe w interfejsie wyników są zmieniane
        if(queueLimit<0){
            for(int i=0; i<50;i++){
                System.out.println("Probability of " +i+" clients in system: " + results.get("Probability_"+i));
            }
            for(int i=0;i < probability_result_text.size();i++){
                probability_result_text.get(i).setText(""+results.get("Probability_"+i));
                probability_result_text.get(i).setVisible(true);
                probability_info_text.get(i).setVisible(true);
            }
        }else{
//            Jeśli istnieje limit, wprowadzamy normalnie dane do pól mniejszych lub równych limitowi użytkowników. Dodajemy 1 do sprawdzenia, ponieważ jeden użytkownik jest wewnątrz serwera
            int limit;
            for(int i=0; i<queueLimit;i++){
                System.out.println("Probability of " +i+" clients in system: " + results.get("Probability_"+i));
            }
            if(queueLimit<10){
                limit = queueLimit;
            }else{
                limit = 11;
            }

            for(int i=0; i<=limit;i++){
                probability_result_text.get(i).setText(""+results.get("Probability_"+i));
                probability_result_text.get(i).setVisible(true);
                probability_info_text.get(i).setText("Probability of "+i+" clients:");
            }
//            Dla tych, które są powyżej limitu, sumujemy ich prawdopodobieństwo i zmieniamy w interfejsie, aby nie były wyświetlone zbędnie
            double refusalProb = 0;
            for(int i = queueLimit +1; i <999; i++){
                refusalProb+=results.get("Probability_"+i);
            }
            System.out.println("Probability of refusal: "+refusalProb);
//            Jeśli elementy znajdują się w interfejsie - następuje zmiana tego co jest wyświetlane
            if(queueLimit <10) {
                for (int i = queueLimit + 1; i < probability_result_text.size(); i++) {
                    if (i != queueLimit + 1) {
                        probability_info_text.get(i).setVisible(false);
                        probability_result_text.get(i).setVisible(false);
                    } else {
                        probability_info_text.get(i).setText("Probability of refusal:");
                        probability_info_text.get(i).setVisible(true);
                        probability_result_text.get(i).setVisible(true);
                        probability_result_text.get(i).setText(""+refusalProb);
                    }
                }
            }
        }
        t_pane_2.setExpanded(true);
    }
}
