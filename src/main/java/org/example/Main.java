package org.example;

import org.example.models.City;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Main {

    public static void main(String[] args) {
        List<City> cities = fillArray();

        //sort(cities);
        //findCityByHighestPopulation(cities);
        countCities(cities);
    }
    
    public static List<City> fillArray(){
        //У вас в файле в нескольких строках было элементов не 6 а 5 из-за этого возникал exception ArrayIndexOutOfBoundsException.
        //Я просто через debug нашел в каких строках не хватало информации и добавил, если эту ошибку нужно было как то обрабатывать то хоть написали бы что вместо пустыз значений поставить какое нибудь дефолтное или null.
        String pathToFile = "src/main/resources/source.csv";
        File file = new File(pathToFile);
        List<City> cities = new ArrayList<>();
        try (Scanner scanner = new Scanner(file)){
            while (scanner.hasNext()){
                String[] currentLine = scanner.nextLine().split(";");
                cities.add(new City(currentLine[1], currentLine[2], currentLine[3], Long.parseLong(currentLine[4]), currentLine[5]));
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        return  cities;
    }

    public static void sort(List<City> cities){
        cities.sort((c1, c2) -> c1.getName().compareToIgnoreCase(c2.getName()));
        cities.forEach(System.out::println);

        Comparator<City> districtComparator = Comparator.comparing(City::getDistrict);
        Comparator<City> nameComparator = Comparator.comparing(City::getName);
        cities.sort(districtComparator.thenComparing(nameComparator));
        cities.forEach(System.out::println);
    }

    public static void findCityByHighestPopulation(List<City> cities){
        //Не совсем понятно для чего нужно было преобразовывать в массив, если можно просто было пройтись по ArrayList это же тот же самый массив.
        City[] arrayCities = new City[cities.size()];
        long maxPopulation = Long.MIN_VALUE;
        int maxPopulationIndex = 0;
        for (int i = 0; i < cities.size(); i++){
            arrayCities[i] = cities.get(i);
            if (arrayCities[i].getPopulation() > maxPopulation){
                maxPopulationIndex = i;
                maxPopulation = arrayCities[i].getPopulation();
            }
        }
        System.out.println("[" + maxPopulationIndex + "] = " + maxPopulation);
    }

    public static void countCities(List<City> cities){
        Map<String, Integer> countCities = new HashMap<>();
        for (City city: cities){
            countCities.merge(city.getRegion(), 1, Integer::sum);
        }
        for (Map.Entry<String, Integer> current: countCities.entrySet()){
            System.out.println(current.getKey() + " - " + current.getValue());
        }
    }
}
