import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import com.opencsv.exceptions.CsvException;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        System.out.println("Student Management System");
        System.out.println();

        startFlow();
    }

    private static void startFlow() {
        String path = "src/Data/StudentRecords.csv";
        // Add header to file
        String[] header = {"Id", "Name", "Age", "Gender", "Course"};
        File file = new File("src/Data/StudentRecords.csv");
        if (file.length() == 0) {
            addDataToCSV(header, path);
        }

        // Operations on file
        System.out.println("Select the Option: ");
        System.out.println("1. Add Student\n2. Show Records\n3. Search in records\n4. Update record\n5. Delete record");
        Scanner sc = new Scanner(System.in);
        System.out.print("=> ");

        switch (sc.nextInt()) {
            case 1:
                addStudent(sc, path);
                break;
            case 2:
                List<String[]> rows = fetchStudent(path);
                for (String[] row: rows) {
                    for (String col: row) {
                        System.out.print(col + "\t");
                    }
                    System.out.println();
                }
                break;
            case 3:
                searchRecords(sc, path);
                break;
            case 4:
//                updateRecord(path);
                break;
            case 5:
//                deleteRecord(path);
                break;
        }
    }

    /**
     * This method is used to filter the records of CSV file based on ID/Gender/Course field. User can select based on
     * which field user needs to get the records. I'm using fetchRecords() method to get the CSV file data
     * @param sc Scanner object
     * @param path CSV file path
     */
    private static void searchRecords(Scanner sc, String path) {
        List<String[]> rows = fetchStudent(path);
        // By which fields needs records.

        System.out.println("Search by:\n1. ID\n2. Gender\n3. Course");
        System.out.print("=> ");
        String input = "";
        int fields = sc.nextInt();
        sc.nextLine();
        switch (fields) {
            case 1:
                fields = 0;
                System.out.print("Enter the Student ID: ");
                input = sc.nextLine();
                break;
            case 2:
                fields = 3;
                System.out.print("Enter the Student Gender: ");
                input = sc.nextLine();
                break;
            case 3:
                fields = 4;
                System.out.print("Enter the Student Gender: ");
                input = sc.nextLine();
                break;
            default:
                System.out.println("Invalid Input. Please select the right option.");
                fields = -1;
        }

        if (fields != -1) {
            for (String[] row: rows) {
                if (row[fields].equalsIgnoreCase(input)) {
                    for (String col: row) {
                        System.out.print(col + "\t");
                    }
                    System.out.println();
                }
            }
        }
    }

    /**
     * This method is used to get all rows from CSV file by using CSVReader object.
     * @param path CSV file path
     * @return Student records in CSV file.
     */
    private static List<String[]> fetchStudent(String path) {
        List<String[]> rows = List.of();
        try (CSVReader reader = new CSVReader(new FileReader(path))) {
            rows = reader.readAll();
        } catch (IOException | CsvException ex) {
            System.out.println("fetchStudent() | Exception: "+ex.getMessage());
        }
        return rows;
    }

    /**
     * This method is used to fill up the student details. To create the array of records.
     * @param sc Scanner object
     * @param path Path of the CSV file.
     */
    private static void addStudent(Scanner sc, String path) {
        sc.nextLine();
        System.out.println("Enter the details of the student - ");
        String[] arr = new String[5];
        System.out.print("ID: ");
        arr[0] = sc.nextLine();
        System.out.print("Name: ");
        arr[1] = sc.nextLine();
        System.out.print("Age: ");
        arr[2] = sc.nextLine();
        System.out.print("Gender: ");
        arr[3] = sc.nextLine();
        System.out.print("Course: ");
        arr[4] = sc.nextLine();

        addDataToCSV(arr, path);
    }

    /**
     * This method is used to store records to CSV file. Using CSVWriter library to store the input in CSV file.
     * @param record Student record array
     * @param path Path of CSV file
     */
    private static void addDataToCSV(String[] record, String path) {
        try{
            FileWriter fw = new FileWriter(path, true); // Create the file in not exists
            CSVWriter csvWriter = new CSVWriter(fw);
            csvWriter.writeNext(record);
            csvWriter.close();
            System.out.println();
            System.out.println("Record added successfully!");
            System.out.println();
        } catch (Exception ex) {
            System.out.println("addDataToCSV() | Exception: "+ ex.getMessage());
        }
    }
}