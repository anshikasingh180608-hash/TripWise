import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Project: TripWise - Travel Planner & Budget Manager
 * Author: Anshika Singh
 * Reg No: 25BAI10764
 * University: VIT Bhopal
 * Description: A console-based application to manage travel destinations, 
 * itineraries, and track expenses using OOP, ArrayLists, and CSV file handling.
 */

public class TripWise {

    public static void main(String[] args) {
        
        Scanner scanner = new Scanner(System.in);

        // Initializing our managers for different modules
        DestinationManager destManager = new DestinationManager();
        TripManager tripManager = new TripManager();
        ActivityManager activityManager = new ActivityManager();
        ExpenseManager expenseManager = new ExpenseManager();

        boolean isAppRunning = true;

        System.out.println("******************************************");
        System.out.println("*          WELCOME TO TRIPWISE           *");
        System.out.println("*  Your Personal Travel & Budget Guide   *");
        System.out.println("******************************************");

        // Main application loop
        while (isAppRunning) {
            
            System.out.println("\n--- Main Menu ---");
            System.out.println("1. Manage Destinations");
            System.out.println("2. Manage Trips");
            System.out.println("3. Manage Itineraries (Activities)");
            System.out.println("4. Manage Expenses & Budget");
            System.out.println("5. Save All Data to Disk");
            System.out.println("6. Exit Application");
            System.out.print("What would you like to do? Enter choice: ");

            int choice = readIntegerInput(scanner);

            switch (choice) {
                case 1:
                    handleDestinations(scanner, destManager);
                    break;
                case 2:
                    handleTrips(scanner, tripManager);
                    break;
                case 3:
                    handleActivities(scanner, activityManager);
                    break;
                case 4:
                    handleExpenses(scanner, expenseManager, tripManager);
                    break;
                case 5:
                    System.out.println("Saving data, please wait...");
                    FileManager.saveData(
                            destManager.getDestinations(),
                            tripManager.getTrips(),
                            activityManager.getActivities(),
                            expenseManager.getExpenses()
                    );
                    break;
                case 6:
                    // Auto-save before quitting just to be safe
                    System.out.println("Saving your work before exiting...");
                    FileManager.saveData(
                            destManager.getDestinations(),
                            tripManager.getTrips(),
                            activityManager.getActivities(),
                            expenseManager.getExpenses()
                    );
                    System.out.println("Thanks for using TripWise. Safe travels!");
                    isAppRunning = false;
                    break;
                default:
                    System.out.println("Oops! Invalid choice. Let's try that again.");
            }
        }
        scanner.close();
    }

    // ---------------------------------------------------------
    // Destination Sub-Menu
    // ---------------------------------------------------------
    public static void handleDestinations(Scanner scanner, DestinationManager destManager) {
        boolean goBack = false;

        while (!goBack) {
            System.out.println("\n>> Destination Menu");
            System.out.println("1. Add a new destination");
            System.out.println("2. View all destinations");
            System.out.println("3. Search for a destination");
            System.out.println("4. Remove a destination");
            System.out.println("5. Go back");
            System.out.print("Select an option: ");

            int opt = readIntegerInput(scanner);

            switch (opt) {
                case 1:
                    System.out.print("Assign a Destination ID (number): ");
                    int id = readIntegerInput(scanner);
                    System.out.print("Destination Name (e.g., Goa): ");
                    String name = scanner.nextLine();
                    System.out.print("Country: ");
                    String country = scanner.nextLine();
                    System.out.print("Short description: ");
                    String desc = scanner.nextLine();

                    // Basic validation check
                    if (!InputValidator.isStringValid(name) || !InputValidator.isStringValid(country)) {
                        System.out.println("Error: Name and Country cannot be empty.");
                        break;
                    }

                    destManager.add(new Destination(id, name, country, desc));
                    break;
                case 2:
                    destManager.viewAll();
                    break;
                case 3:
                    System.out.print("Enter the name of the destination you're looking for: ");
                    destManager.searchByName(scanner.nextLine());
                    break;
                case 4:
                    System.out.print("Enter the ID of the destination to remove: ");
                    destManager.removeById(readIntegerInput(scanner));
                    break;
                case 5:
                    goBack = true;
                    break;
                default:
                    System.out.println("Invalid option.");
            }
        }
    }

    // ---------------------------------------------------------
    // Trip Sub-Menu
    // ---------------------------------------------------------
    public static void handleTrips(Scanner scanner, TripManager tripManager) {
        boolean goBack = false;

        while (!goBack) {
            System.out.println("\n>> Trip Menu");
            System.out.println("1. Create a new trip");
            System.out.println("2. View my trips");
            System.out.println("3. Search for a trip by ID");
            System.out.println("4. Go back");
            System.out.print("Select an option: ");

            int opt = readIntegerInput(scanner);

            switch (opt) {
                case 1:
                    System.out.print("Assign a Trip ID (number): ");
                    int tripId = readIntegerInput(scanner);
                    System.out.print("Where are you going? ");
                    String dest = scanner.nextLine();
                    System.out.print("Start Date (DD-MM-YYYY): ");
                    String startDate = scanner.nextLine();
                    System.out.print("End Date (DD-MM-YYYY): ");
                    String endDate = scanner.nextLine();
                    System.out.print("What's your budget? (₹): ");
                    
                    double budget = readDoubleInput(scanner);
                    if (!InputValidator.isPositiveAmount(budget)) {
                        System.out.println("Whoops, your budget can't be negative!");
                        break;
                    }

                    tripManager.add(new Trip(tripId, dest, startDate, endDate, budget));
                    break;
                case 2:
                    tripManager.viewAll();
                    break;
                case 3:
                    System.out.print("Enter Trip ID to find: ");
                    Trip found = tripManager.findById(readIntegerInput(scanner));
                    if (found != null) {
                        found.display();
                    } else {
                        System.out.println("Couldn't find a trip with that ID.");
                    }
                    break;
                case 4:
                    goBack = true;
                    break;
                default:
                    System.out.println("Invalid option.");
            }
        }
    }

    // ---------------------------------------------------------
    // Itinerary (Activity) Sub-Menu
    // ---------------------------------------------------------
    public static void handleActivities(Scanner scanner, ActivityManager actManager) {
        boolean goBack = false;

        while (!goBack) {
            System.out.println("\n>> Itinerary Menu");
            System.out.println("1. Add an activity to a trip");
            System.out.println("2. View all activities");
            System.out.println("3. View activities for a specific trip");
            System.out.println("4. Go back");
            System.out.print("Select an option: ");

            int opt = readIntegerInput(scanner);

            switch (opt) {
                case 1:
                    System.out.print("Activity ID: ");
                    int actId = readIntegerInput(scanner);
                    System.out.print("Which Trip ID is this for? ");
                    int linkedTripId = readIntegerInput(scanner);
                    System.out.print("Activity Name (e.g., Scuba Diving): ");
                    String name = scanner.nextLine();
                    System.out.print("Date planned (DD-MM-YYYY): ");
                    String date = scanner.nextLine();
                    System.out.print("Notes/Description: ");
                    String desc = scanner.nextLine();

                    actManager.add(new Activity(actId, linkedTripId, name, date, desc));
                    break;
                case 2:
                    actManager.viewAll();
                    break;
                case 3:
                    System.out.print("Enter Trip ID to see its itinerary: ");
                    actManager.viewByTrip(readIntegerInput(scanner));
                    break;
                case 4:
                    goBack = true;
                    break;
                default:
                    System.out.println("Invalid option.");
            }
        }
    }

    // ---------------------------------------------------------
    // Expense & Budget Sub-Menu
    // ---------------------------------------------------------
    public static void handleExpenses(Scanner scanner, ExpenseManager expManager, TripManager tripManager) {
        boolean goBack = false;

        while (!goBack) {
            System.out.println("\n>> Budget & Expenses Menu");
            System.out.println("1. Log a new expense");
            System.out.println("2. View all logged expenses");
            System.out.println("3. View expenses for a specific trip");
            System.out.println("4. See total spending for a trip");
            System.out.println("5. Check remaining budget");
            System.out.println("6. Go back");
            System.out.print("Select an option: ");

            int opt = readIntegerInput(scanner);

            switch (opt) {
                case 1:
                    System.out.print("Expense ID: ");
                    int expId = readIntegerInput(scanner);
                    System.out.print("Trip ID this expense belongs to: ");
                    int tId = readIntegerInput(scanner);
                    System.out.print("Category (Food, Travel, Hotel, etc.): ");
                    String category = scanner.nextLine();
                    System.out.print("Amount spent (₹): ");
                    
                    double amt = readDoubleInput(scanner);
                    if (!InputValidator.isPositiveAmount(amt)) {
                        System.out.println("Expense amount must be positive.");
                        break;
                    }
                    
                    System.out.print("Brief description: ");
                    String desc = scanner.nextLine();

                    expManager.add(new Expense(expId, tId, category, amt, desc));
                    break;
                case 2:
                    expManager.viewAll();
                    break;
                case 3:
                    System.out.print("Enter Trip ID to view its expenses: ");
                    expManager.viewByTrip(readIntegerInput(scanner));
                    break;
                case 4:
                    System.out.print("Enter Trip ID to calculate total spent: ");
                    double totalSpent = expManager.getTotalSpent(readIntegerInput(scanner));
                    System.out.println("Total Amount Spent = ₹" + totalSpent);
                    break;
                case 5:
                    System.out.print("Enter Trip ID to check budget: ");
                    int bTripId = readIntegerInput(scanner);
                    Trip tripRef = tripManager.findById(bTripId);
                    
                    if (tripRef == null) {
                        System.out.println("Wait, I can't find a trip with that ID.");
                        break;
                    }
                    
                    double remaining = expManager.getRemainingBudget(bTripId, tripRef.getBudget());
                    
                    System.out.println("---------------------------------");
                    System.out.println("Total Allocated Budget: ₹" + tripRef.getBudget());
                    System.out.println("Remaining Balance:      ₹" + remaining);
                    
                    if (remaining < 0) {
                        System.out.println("[WARNING] You have exceeded your budget!");
                    } else if (remaining == 0) {
                        System.out.println("[STATUS] You have exactly hit your budget limit.");
                    } else {
                        System.out.println("[STATUS] You are within your budget. Looking good!");
                    }
                    System.out.println("---------------------------------");
                    break;
                case 6:
                    goBack = true;
                    break;
                default:
                    System.out.println("Invalid option.");
            }
        }
    }

    // ---------------------------------------------------------
    // Helper Input Methods (To prevent Scanner bugs)
    // ---------------------------------------------------------
    
    // We read the whole line and parse it so we don't get stuck with newline characters
    public static int readIntegerInput(Scanner scanner) {
        while (true) {
            try {
                return Integer.parseInt(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.print("Please enter a valid number: ");
            }
        }
    }

    public static double readDoubleInput(Scanner scanner) {
        while (true) {
            try {
                return Double.parseDouble(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.print("Please enter a valid amount: ");
            }
        }
    }

    // =========================================================
    // MODEL CLASSES
    // =========================================================

    static class Destination {
        private int id;
        private String name;
        private String country;
        private String description;

        public Destination(int id, String name, String country, String description) {
            this.id = id;
            this.name = name;
            this.country = country;
            this.description = description;
        }

        public int getId() { return id; }
        public String getName() { return name; }
        public String getCountry() { return country; }
        public String getDescription() { return description; }

        public void display() {
            System.out.println("ID: " + id + " | Name: " + name + " | Country: " + country);
            System.out.println("Desc: " + description);
        }
    }

    static class Trip {
        private int tripId;
        private String destination;
        private String startDate;
        private String endDate;
        private double budget;

        public Trip(int tripId, String destination, String startDate, String endDate, double budget) {
            this.tripId = tripId;
            this.destination = destination;
            this.startDate = startDate;
            this.endDate = endDate;
            this.budget = budget;
        }

        public int getTripId() { return tripId; }
        public String getDestination() { return destination; }
        public String getStartDate() { return startDate; }
        public String getEndDate() { return endDate; }
        public double getBudget() { return budget; }

        public void display() {
            System.out.println("Trip ID: " + tripId + " | Dest: " + destination);
            System.out.println("Dates: " + startDate + " to " + endDate);
            System.out.println("Budget: ₹" + budget);
        }
    }

    static class Activity {
        private int activityId;
        private int tripId;
        private String name;
        private String date;
        private String description;

        public Activity(int activityId, int tripId, String name, String date, String description) {
            this.activityId = activityId;
            this.tripId = tripId;
            this.name = name;
            this.date = date;
            this.description = description;
        }

        public int getActivityId() { return activityId; }
        public int getTripId() { return tripId; }
        public String getName() { return name; }
        public String getDate() { return date; }
        public String getDescription() { return description; }

        public void display() {
            System.out.println("Activity ID: " + activityId + " (Trip: " + tripId + ")");
            System.out.println("What: " + name + " | When: " + date);
            System.out.println("Notes: " + description);
        }
    }

    static class Expense {
        private int expenseId;
        private int tripId;
        private String category;
        private double amount;
        private String description;

        public Expense(int expenseId, int tripId, String category, double amount, String description) {
            this.expenseId = expenseId;
            this.tripId = tripId;
            this.category = category;
            this.amount = amount;
            this.description = description;
        }

        public int getExpenseId() { return expenseId; }
        public int getTripId() { return tripId; }
        public String getCategory() { return category; }
        public double getAmount() { return amount; }
        public String getDescription() { return description; }

        public void display() {
            System.out.println("Expense ID: " + expenseId + " (Trip: " + tripId + ")");
            System.out.println("Category: " + category + " | Amount: ₹" + amount);
            System.out.println("Details: " + description);
        }
    }

    // =========================================================
    // MANAGER CLASSES
    // =========================================================

    static class DestinationManager {
        // Storing destinations in an ArrayList so it can grow dynamically
        private ArrayList<Destination> list = new ArrayList<>();

        public void add(Destination d) {
            if (d != null) {
                list.add(d);
                System.out.println("-> Destination added successfully!");
            }
        }

        public void viewAll() {
            if (list.isEmpty()) {
                System.out.println("No destinations logged yet.");
                return;
            }
            for (Destination d : list) {
                d.display();
                System.out.println("- - - - - - - - - - - - - - - -");
            }
        }

        public void searchByName(String query) {
            boolean found = false;
            for (Destination d : list) {
                if (d.getName().equalsIgnoreCase(query)) {
                    d.display();
                    found = true;
                }
            }
            if (!found) System.out.println("Couldn't find that destination.");
        }

        public void removeById(int id) {
            for (int i = 0; i < list.size(); i++) {
                if (list.get(i).getId() == id) {
                    list.remove(i);
                    System.out.println("-> Destination removed.");
                    return;
                }
            }
            System.out.println("Destination ID not found.");
        }

        public ArrayList<Destination> getDestinations() { return list; }
    }

    static class TripManager {
        private ArrayList<Trip> trips = new ArrayList<>();

        public void add(Trip trip) {
            if (trip != null) {
                trips.add(trip);
                System.out.println("-> Trip saved!");
            }
        }

        public void viewAll() {
            if (trips.isEmpty()) {
                System.out.println("You haven't planned any trips yet.");
                return;
            }
            for (Trip t : trips) {
                t.display();
                System.out.println("- - - - - - - - - - - - - - - -");
            }
        }

        public Trip findById(int id) {
            for (Trip t : trips) {
                if (t.getTripId() == id) {
                    return t;
                }
            }
            return null; // Return null if nothing matches
        }

        public ArrayList<Trip> getTrips() { return trips; }
    }

    static class ActivityManager {
        private ArrayList<Activity> activities = new ArrayList<>();

        public void add(Activity act) {
            if (act != null) {
                activities.add(act);
                System.out.println("-> Activity added to your itinerary.");
            }
        }

        public void viewAll() {
            if (activities.isEmpty()) {
                System.out.println("No activities logged.");
                return;
            }
            for (Activity a : activities) {
                a.display();
                System.out.println("- - - - - - - - - - - - - - - -");
            }
        }

        public void viewByTrip(int tripId) {
            boolean found = false;
            System.out.println("\n--- Itinerary for Trip " + tripId + " ---");
            for (Activity a : activities) {
                if (a.getTripId() == tripId) {
                    a.display();
                    System.out.println("- - - - - - - - - - - - - - - -");
                    found = true;
                }
            }
            if (!found) System.out.println("Nothing planned for this trip yet.");
        }

        public ArrayList<Activity> getActivities() { return activities; }
    }

    static class ExpenseManager {
        private ArrayList<Expense> expenses = new ArrayList<>();

        public void add(Expense exp) {
            if (exp != null) {
                expenses.add(exp);
                System.out.println("-> Expense recorded.");
            }
        }

        public void viewAll() {
            if (expenses.isEmpty()) {
                System.out.println("No expenses logged.");
                return;
            }
            for (Expense e : expenses) {
                e.display();
                System.out.println("- - - - - - - - - - - - - - - -");
            }
        }

        public void viewByTrip(int tripId) {
            boolean found = false;
            for (Expense e : expenses) {
                if (e.getTripId() == tripId) {
                    e.display();
                    System.out.println("- - - - - - - - - - - - - - - -");
                    found = true;
                }
            }
            if (!found) System.out.println("No expenses recorded for this trip.");
        }

        public double getTotalSpent(int tripId) {
            double total = 0;
            for (Expense e : expenses) {
                if (e.getTripId() == tripId) {
                    total += e.getAmount();
                }
            }
            return total;
        }

        public double getRemainingBudget(int tripId, double budget) {
            return budget - getTotalSpent(tripId);
        }

        public ArrayList<Expense> getExpenses() { return expenses; }
    }

    // =========================================================
    // UTILITIES 
    // =========================================================

    static class InputValidator {
        public static boolean isStringValid(String val) {
            return val != null && !val.trim().isEmpty();
        }

        public static boolean isPositiveAmount(double amount) {
            return amount >= 0;
        }
    }

    static class FileManager {
        private static final String DIR = "data/";

        public static void saveData(
                ArrayList<Destination> dests,
                ArrayList<Trip> trips,
                ArrayList<Activity> acts,
                ArrayList<Expense> exps) {

            // Create the directory if it doesn't exist yet
            File folder = new File(DIR);
            if (!folder.exists()) {
                folder.mkdirs();
            }

            saveDestinations(dests);
            saveTrips(trips);
            saveActivities(acts);
            saveExpenses(exps);

            System.out.println("-> All data backed up successfully to the 'data' folder.");
        }

        private static void saveDestinations(ArrayList<Destination> dests) {
            try (FileWriter fw = new FileWriter(DIR + "destinations.csv")) {
                fw.write("id,name,country,description\n");
                for (Destination d : dests) {
                    fw.write(d.getId() + "," + cleanText(d.getName()) + "," +
                             cleanText(d.getCountry()) + "," + cleanText(d.getDescription()) + "\n");
                }
            } catch (IOException e) {
                System.out.println("Issue saving destinations.");
            }
        }

        private static void saveTrips(ArrayList<Trip> trips) {
            try (FileWriter fw = new FileWriter(DIR + "trips.csv")) {
                fw.write("tripId,destination,startDate,endDate,budget\n");
                for (Trip t : trips) {
                    fw.write(t.getTripId() + "," + cleanText(t.getDestination()) + "," +
                             cleanText(t.getStartDate()) + "," + cleanText(t.getEndDate()) + "," +
                             t.getBudget() + "\n");
                }
            } catch (IOException e) {
                System.out.println("Issue saving trips.");
            }
        }

        private static void saveActivities(ArrayList<Activity> acts) {
            try (FileWriter fw = new FileWriter(DIR + "activities.csv")) {
                fw.write("activityId,tripId,name,date,description\n");
                for (Activity a : acts) {
                    fw.write(a.getActivityId() + "," + a.getTripId() + "," +
                             cleanText(a.getName()) + "," + cleanText(a.getDate()) + "," +
                             cleanText(a.getDescription()) + "\n");
                }
            } catch (IOException e) {
                System.out.println("Issue saving activities.");
            }
        }

        private static void saveExpenses(ArrayList<Expense> exps) {
            try (FileWriter fw = new FileWriter(DIR + "expenses.csv")) {
                fw.write("expenseId,tripId,category,amount,description\n");
                for (Expense e : exps) {
                    fw.write(e.getExpenseId() + "," + e.getTripId() + "," +
                             cleanText(e.getCategory()) + "," + e.getAmount() + "," +
                             cleanText(e.getDescription()) + "\n");
                }
            } catch (IOException e) {
                System.out.println("Issue saving expenses.");
            }
        }

        // Quick fix: removing commas so they don't break our CSV format
        private static String cleanText(String text) {
            return text == null ? "" : text.replace(",", " ");
        }
    }
}