package com.cmpt276.prjModel;

import java.io.File;
import java.util.List;

import model.Inspection;
import model.InspectionManager;
import model.Restaurant;
import model.RestaurantManager;

public class Main {
    private static List<Restaurant> restaurants;
    private static List<Inspection> inspections;

    public static void main(String[] args) {
        RestaurantManager manager  = new RestaurantManager();
        InspectionManager Imanager  = new InspectionManager();

       // manager.add(new Restaurant("1", "Jordon", "a1", "c1", "T", 123, 321));
        //manager.add(new Restaurant("2", "Art", "a2", "c2", "T", 123, 321));
        //manager.add(new Restaurant("3", "Hoang", "a3", "c3", "T", 123, 321));

        restaurants = manager.getRestaurantList();
        //printList();

        inspections = Imanager.getInspectionList();
        IprintList();

        manager.setListFromFile(new File("src/data/restaurants_itr1.csv"));
        //printList();

        Imanager.setListFromFile(new File("src/data/inspectionreports_itr1.csv"));
        IprintList();
    }

    private static void printList() {
        for (Restaurant r : restaurants) {
            System.out.println(r.toString() + "\n");
        }
    }

    private static void IprintList() {
        for (Inspection I : inspections) {
            System.out.println(I.toString() + "\n");
        }
    }
}
