package org.example;

import org.example.models.City;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
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
        cities.forEach(System.out::println);
    }
}
