// FileName CreateSpaServices.java
// Written by: Jonathon Meyer
// Written on: 09/09/2026

import java.util.Scanner;

public class CreateSpaServices {

    public static void main(String[] args) {

        SpaService firstService = new SpaService();
        SpaService secondService = new SpaService();
        SpaService thirdService = new SpaService("facial", 22.99);

        firstService = getData(firstService);
        //secondService = getData(secondService);

        System.out.println("First service details:");
        System.out.println(firstService.getServiceDescription() + " $" + firstService.getPrice());

        System.out.println("Second service details:");
        System.out.println(secondService.getServiceDescription() + " $" + secondService.getPrice());

        System.out.println("Third service details:");
        System.out.println(thirdService.getServiceDescription() + " $" + thirdService.getPrice());
    }

    public static SpaService getData(SpaService service) {
        String serviceDescription;
        double price;

        Scanner keyboard = new Scanner(System.in);

        System.out.print("Enter service >> ");
        serviceDescription = keyboard.nextLine();
        System.out.print("Enter price >> ");
        price = keyboard.nextDouble();

        service.setServiceDescription(serviceDescription);
        service.setPrice(price);

        return service;
    }
}
