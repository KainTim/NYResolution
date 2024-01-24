package net.htlgkr.kainzt.pos3.NYResolution;

import net.htlgkr.kainzt.pos3.NYResolution.enums.MyCategory;
import net.htlgkr.kainzt.pos3.NYResolution.enums.MyFilter;
import net.htlgkr.kainzt.pos3.NYResolution.storage.ResolutionStorage;

import javax.xml.datatype.Duration;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAmount;
import java.util.Comparator;
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
            try {
                lastChosen = Integer.parseInt(scanner.nextLine());
            }catch (NumberFormatException e){
                System.err.println("Not a valid Selection");
                continue;
            }
            switch (lastChosen){
                case 1:
                    addNewResolution();
                    break;
                case 2 :
                    int showMenuChoice = -1;
                    MyFilter filter = null;

                    while (true) {
                        showResolutionMenu();
                        try {
                            showMenuChoice = Integer.parseInt(scanner.nextLine());
                        } catch (NumberFormatException e) {
                            System.out.println("Please enter a number!");
                        }
                        switch (showMenuChoice) {
                            case 1:
                                filter = MyFilter.DEADLINE;
                                break;
                            case 2:
                                filter = MyFilter.PRIORITY;
                                break;
                            case 3:
                                filter = MyFilter.CATEGORY;
                                break;
                            case 4:
                                break;
                            default:
                                System.out.println("Please enter a number between 1 and 4!");
                                break;
                        }
                        break;
                    }

                    showResolutions(filter);
                    break;
                case 3:
                    showStatistics();
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
                    System.err.println("Not a valid Selection");
            }
        }

    }

    private static void showStatistics() {
        System.out.println("\n_________________________________");
        System.out.println("Statistics:");
        System.out.println("All Resolutions: " + resolutionStorage.getResolutions().size());
        System.out.println("Done Resolutions: " + resolutionStorage.getResolutions().stream().filter(Resolution::isDone).count());
        System.out.println("Overtime Resolutions: " + resolutionStorage.getResolutions().stream().filter(resolution -> (!resolution.isDone())&&resolution.getDeadline().isAfter(LocalDate.now())).count());
        System.out.println("7 Day Resolutions: " + resolutionStorage.getResolutions().stream().filter(resolution -> (!resolution.isDone())&&resolution.getDeadline().isAfter(LocalDate.now().minusDays(7))).count());
        System.out.println("30 Day Resolutions: " + resolutionStorage.getResolutions().stream().filter(resolution -> (!resolution.isDone())&&resolution.getDeadline().isAfter(LocalDate.now().minusDays(30))).count());
        System.out.println("365 Day Resolutions: " + resolutionStorage.getResolutions().stream().filter(resolution -> (!resolution.isDone())&&resolution.getDeadline().isAfter(LocalDate.now().minusDays(365))).count());

        System.out.println("_________________________________\n");
    }

    private static void showResolutions(MyFilter filter) {
        List<Resolution> resolutionList = resolutionStorage.getResolutions();
        if (filter == MyFilter.DEADLINE) {
            resolutionList.sort(Comparator.comparing(Resolution::getDeadline));
        } else if (filter == MyFilter.PRIORITY) {
            resolutionList.sort(Comparator.comparingInt(Resolution::getPriority).reversed());
        } else if (filter == MyFilter.CATEGORY) {
            resolutionList.sort(Comparator.comparing((Resolution o) -> o.getCategory().name()));
        }
        for (Resolution resolution : resolutionList) {
            System.out.println(resolution);
        }
    }

    public static void showResolutionMenu() {
        System.out.println("1 ... Sort by deadline");
        System.out.println("2 ... Sort by priority");
        System.out.println("3 ... Sort by category (alphabetically)");
        System.out.println("4 ... Back to main menu");
    }

    private static void markResolutionDone() {
        Resolution resolution = searchForResolution();
        if (resolution==null) return;
        boolean validSelection = false;
        while(!validSelection){
            System.err.println("Mark as Done? (Done/Not Done)");
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
            System.err.println("This Operation is Not Reversible, Remove? (Y/N)");
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
        System.out.println("3... Show Statistics");
        System.out.println("4... Search for a Resolution");
        System.out.println("5... Delete a Resolution");
        System.out.println("6... Mark Resolution as Done");
        System.out.println("9... Quit");
    }
    private static Resolution searchForResolution(){
        System.out.println("Search for Title, Description or Category");
        Scanner scanner = new Scanner(System.in);
        String searchTerm = scanner.nextLine();
        List<Resolution> searchResult = resolutionStorage.getResolutions().stream()
                .filter(resolution -> resolution.getTitle().toUpperCase().contains(searchTerm.toUpperCase()) ||
                        resolution.getDescription().toUpperCase().contains(searchTerm.toUpperCase()) ||
                        resolution.getCategory().toString().toUpperCase().contains(searchTerm.toUpperCase()))
                .toList();
        if (searchResult.isEmpty()){
            System.err.println("Result is empty");
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
                System.err.println("not a valid selection");
            }
            if (selection < 0 || selection > (searchResult.size()+1)) {
                System.err.println("not a valid selection");
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
                System.err.println("Please enter a number!");
            }
        }

        MyCategory category = null;

        while (true) {
            try {
                System.out.println("Please enter the category of the resolution (WORK, PRIVATE, FAMILY, HOBBY, HEALTH, OTHER):");
                category = MyCategory.valueOf(scanner.nextLine().toUpperCase());
                break;
            } catch (IllegalArgumentException e) {
                System.err.println("Please enter a valid category!");
            }
        }

        Resolution resolution = new Resolution(title, description, deadline, false, priority, category, LocalDate.now(), null);
        resolutionStorage.addResolution(resolution);
    }
}
