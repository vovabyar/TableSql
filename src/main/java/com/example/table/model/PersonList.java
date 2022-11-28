package com.example.table.model;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonParseException;

import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public class PersonList implements  Cloneable{
    public ArrayList<Person> persons;
    public PersonList() {
        persons = new ArrayList<>();
    }

    public void Add(Person b) {
        persons.add(b);
    }

    public void writeJson(String jsonFileName) throws IOException {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        Writer writer = Files.newBufferedWriter(Paths.get(jsonFileName));
        gson.toJson(this, writer);
        writer.close();
    }

    public void readJson(String jsonFileName) throws IOException {
        Gson gson = new Gson();
        Reader reader = Files.newBufferedReader(Paths.get(jsonFileName));
        var bookList = gson.fromJson(reader, PersonList.class);
        persons = bookList.persons;
        bookList.print();
        reader.close();
    }

    public void print() {
        var stream = persons.stream();
        stream.forEach(System.out::println);
    }
}
