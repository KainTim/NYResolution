package net.htlgkr.kainzt.pos3.NYResolution;

import net.htlgkr.kainzt.pos3.NYResolution.enums.MyCategory;
import net.htlgkr.kainzt.pos3.NYResolution.storage.ResolutionStorage;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    private static ResolutionStorage resolutionStorage = new ResolutionStorage();
    private static Scanner scanner = new Scanner(System.in);
    public static void main(String[] args) {
        int lastChosen = 0;
        int exitCode = 9;
        while (lastChosen!=exitCode){
            showMainMenu();
            lastChosen = Integer.parseInt(scanner.nextLine());
            switch (lastChosen){
                case 1:
                    addNewResolution();
                    break;
                case 2 :
                    break;
                case 3:
                    break;
                case 4 :
                    searchForResolution();
                    break;
                case 5:
                    deleteResolution();
                    break;
                case 6 :
                    markResolutionDone();
                    break;
                case 9:
                    System.out.println("Quitting ...");
                    break;
                default:
                    System.out.println("Not a valid Selection");
            }
        }

    }

    private static void markResolutionDone() {
        Resolution resolution = searchForResolution();
        if (resolution==null) return;
        boolean validSelection = false;
        while(!validSelection){
            System.out.println("Mark as Done? (Done/Not Done)");
            String result = scanner.nextLine();
            if (result.equalsIgnoreCase("Done")){
                validSelection = true;
                System.out.println("Marking as Done");
                resolution.setDone(true);
                resolution.setDoneDate(LocalDate.now());
                System.out.println("Done");
            } else if (result.equalsIgnoreCase("Not Done")) {
                validSelection = true;
                System.out.println("Marking as Not Done");
                resolution.setDone(false);
                resolution.setDoneDate(null);
            }
        }
    }

    private static void deleteResolution() {
        Resolution resolution = searchForResolution();
        if (resolution==null) return;
        boolean validSelection = false;
        while(!validSelection){
            System.out.println("This Operation is Not Reversible, Remove? (Y/N)");
            String result = scanner.nextLine();
            if (result.equalsIgnoreCase("Y")){
                validSelection = true;
                System.out.println("Deleting...");
                resolutionStorage.deleteResolution(resolution);
                System.out.println("Done");
            } else if (result.equalsIgnoreCase("N")) {
                validSelection = true;
                System.out.println("Aborting...");
            }
        }

    }

    private static void showMainMenu(){
        System.out.println("1... Add new Resolution");
        System.out.println("2... Show all Resolutions");
        System.out.println("3... Show Resolutions in specific Timeframe");
        System.out.println("4... Search for a Resolution");
        System.out.println("5... Delete a Resolution");
        System.out.println("6... Mark Resolution as Done Resolution");
        System.out.println("9... Quit");
    }
    private static Resolution searchForResolution(){
        System.out.println("Search for Title, Description or Category");
        Scanner scanner = new Scanner(System.in);
        String searchTerm = scanner.nextLine();
        List<Resolution> searchResult = resolutionStorage.getResolutions().stream()
                .filter(resolution -> {
                    return resolution.getTitle().toUpperCase().contains(searchTerm.toUpperCase()) ||
                            resolution.getDescription().toUpperCase().contains(searchTerm.toUpperCase()) ||
                            resolution.getCategory().name().toUpperCase().contains(searchTerm.toUpperCase());
                })
                .toList();
        if (searchResult.isEmpty()){
            System.out.println("Result is empty");
            return null;
        }
        int selection = -1;
        while(selection <= 0 || selection > (searchResult.size()+1)) {
            System.out.println("Select your Result:");
            for (int i = 0; i < searchResult.size(); i++) {
                System.out.println("ID: " + (i + 1) + "|| " + searchResult.get(i));
            }
            try {
                selection = Integer.parseInt(scanner.nextLine());
            }catch (NumberFormatException e){
                System.out.println("not a valid selection");
            }
            if (selection < 0 || selection > (searchResult.size()+1)) {
                System.out.println("not a valid selection");
            }
        }
        System.out.println(searchResult.get(selection - 1));
        return searchResult.get(selection-1);
    }
    private static void addNewResolution() {
        System.out.println("Please enter the title of the resolution:");
        String title = scanner.nextLine();

        System.out.println("Please enter the description of the resolution:");
        String description = scanner.nextLine();

        System.out.println("Please enter the deadline of the resolution (dd.MM.yyyy):");
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        LocalDate deadline = LocalDate.from(formatter.parse(scanner.nextLine()));


        int priority = -1;
        while (true) {
            try {
                System.out.println("Please enter the priority of the resolution (1-5):");
                priority = Integer.parseInt(scanner.nextLine());
                break;
            } catch (NumberFormatException e) {
                System.out.println("Please enter a number!");
            }
        }

        MyCategory category = null;

        while (true) {
            try {
                System.out.println("Please enter the category of the resolution (WORK, PRIVATE, FAMILY, HOBBY, HEALTH, OTHER):");
                category = MyCategory.valueOf(scanner.nextLine().toUpperCase());
                break;
            } catch (IllegalArgumentException e) {
                System.out.println("Please enter a valid category!");
            }
        }

        Resolution resolution = new Resolution(title, description, deadline, false, priority, category, LocalDate.now(), null);
        resolutionStorage.addResolution(resolution);
    }
}
