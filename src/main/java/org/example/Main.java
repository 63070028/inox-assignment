package org.example;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

// อ่าน file หา 3 อับดับรถที่จอดนานที่สุด

public class Main {
    public static void main(String[] args) {
        BufferedReader reader;

        try {
            reader = new BufferedReader(new FileReader("/Users/myner/Desktop/INOX/src/main/java/org/example/parkinglog1"));
            String line = reader.readLine();
            Map<String, Car> log = new HashMap<>();
            Map<String, String[]> parking = new HashMap<>();

            while (line != null) {
                String[] data = line.split(",");
                String no = data[0];
                String action = data[1];
                String datetime = data[2];

                if(action.equals("IN")){
                    parking.put(no, data);
                }else{
                    String[] car_in = parking.remove(no);
                    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
                    Date time_in = format.parse(car_in[2]);
                    Date time_out = format.parse(datetime);

                    long diff = time_out.getTime() - time_in.getTime();
                    long diffMinutes = diff / (60 * 1000);

                    if(log.containsKey(no)){
                        log.get(no).time += diffMinutes;
                    }else{
                        log.put(no, new Car(no, diffMinutes));
                    }


                }

                line = reader.readLine();
            }

            reader.close();

            List<Car> carRanking = new ArrayList<>(log.values());

            carRanking.sort((c1, c2) -> {
                if (c1.time < c2.time) {
                    return 1;
                } else if (c1.time > c2.time) {
                    return -1;
                } else {
                    return 0;
                }
            });

            if(carRanking.size() < 3){
                for(int i=0; i<carRanking.size(); i++){
                    System.out.printf("Rank:%s No:%s Time:%s\n", i+1, carRanking.get(i).no, carRanking.get(i).time);
                }
            }else{
                for(int i=0; i<3; i++){
                    System.out.printf("Rank:%s No:%s Time:%s\n", i+1, carRanking.get(i).no, carRanking.get(i).time);
                }
            }


        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }

    }

    public static <K, V extends Comparable<? super V>> Map<K, V> sortMapByValueDescending(Map<K, V> map) {
        return map.entrySet()
                .stream()
                .sorted(Map.Entry.<K, V>comparingByValue().reversed())
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new));
    }
}

