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
            reader = new BufferedReader(new FileReader("/Users/myner/Desktop/Test/src/main/java/org/example/parkinglog1"));
            String line = reader.readLine();
            Map<String, Long> car = new HashMap<>();
            Map<String, String[]> parking = new HashMap<>();

            while (line != null) {
//                System.out.println(line);
                // read next line
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

                    if(car.containsKey(no)){
                        car.put(no, car.get(no)+diffMinutes);
                    }else{
                        car.put(no, diffMinutes);
                    }


                }

                line = reader.readLine();
            }

            reader.close();
//            System.out.println(car);
            Map<String, Long> sortedMap = sortMapByValueDescending(car);
//            System.out.println(sortedMap);
            int rank = 0;
            for (String k:sortedMap.keySet()) {
                if(rank >= 3){
                    break;
                }
                System.out.printf("No:%s, Min:%s\n", k, sortedMap.get(k));
                rank++;
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

