import com.opencsv.*;
import com.opencsv.exceptions.CsvException;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.lang.String;

public class Main {
    static String path = "src/Data/StudentRecords.csv";
    static List<String[]> header = new ArrayList<>(List.of());
//    static String path = "src/Data/Temp.csv";
    public static void main(String[] args) {
        System.out.println("Student Management System");
        System.out.println();

        startFlow();
    }

    private static void startFlow() {
        // Add header to file
        header.add(new String[]{"Id", "Name", "Age", "Gender", "Course"});
        File file = new File(path);
        if (file.length() == 0) {
            appendDataToCSV(header, path);
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
                List<String[]> rows = readRecords(path);
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
                updateRecord(sc, path);
                break;
            case 5:
                deleteRecord(sc, path);
                break;
        }
    }

    /**
     * This method is used to delete the single row/record based on I'd from CSV file. After skipping that row, I am
     * updating the id of all other rows below it. New records are overwriting the same file.
     * @param sc Scanner object
     * @param path Path of CSV file
     */
    private static void deleteRecord(Scanner sc, String path) {
        System.out.print("Enter the id of the student: ");
        int id = sc.nextInt();

        List<String[]> rows = readRecords(path);
        List<String[]> newRows = new ArrayList<>(header);

        for (String[] row : rows) {
            if (Integer.parseInt(row[0]) < id) {
                newRows.add(row);
            }else if (Integer.parseInt(row[0]) > id) {
                row[0] = String.valueOf(id);
                newRows.add(row);
                id++;
            }
        }

        writeDataToCSV(newRows, path);
        System.out.println();
        System.out.println("<<<< Records deleted successfully! >>>>");
    }

    /**
     * This method used to update the record of student data. The logic behind is that we are reading the data from
     * our current file and write the updated data into new file Has CSV is just text data, so we cannot just edit the
     * data of any col in between. If we want change the value to any col either we can change it to same bit that's
     * okay, but if bits are less or more than we need to move all the bits after that. That's why needs to create new
     * file.
     * @param sc Object of Scanner
     * @param path Path of current
     */
    private static void updateRecord(Scanner sc, String path) {
        sc.nextLine();
        System.out.print("Enter student ID to update: ");
        String id = sc.nextLine();

        System.out.print("Select which field needs to update:\n1. Name\n2. Course\n=> ");
        int field = sc.nextInt();
        if (field == 2) field = 4;
        sc.nextLine();

        System.out.print("Enter the value: ");
        String newValue = sc.nextLine();

        List<String[]> records = readRecords(path);
//        String tempPath = "src/Data/Temp.csv";

        // Overwriting the header to current CSV file.
        writeDataToCSV(header, path);

        for (String[] rows : records) {
            if (rows[0].equalsIgnoreCase(id)) {
                rows[field] = newValue;
            }
        }

        // Appending the data after overwriting the header.
        appendDataToCSV(records, path);

        System.out.println();
        System.out.println("<<<< Record update successfully! >>>>");
    }

    /*
    private static void renameTempFile(String tempFile, String originalFile) {
        try {
            // move() method rename file and overwrite if same path. Move + rename if different path.
            Files.move(
                    Paths.get(tempFile),
                    Paths.get(originalFile),
                    StandardCopyOption.REPLACE_EXISTING
            );
        } catch (IOException ex) {
            System.out.println("renameTempFile() | Exception: "+ex.getMessage());
        }
    }


    private static void deleteFile(String path) {
        try {
            Files.deleteIfExists(Paths.get(path));
        } catch (IOException ex) {
            System.out.println("deleteFile() | Exception: "+ex.getMessage());
        }
    } */

    /**
     * This method is used to filter the records of CSV file based on ID/Gender/Course field. User can select based on
     * which field user needs to get the records. I'm using fetchRecords() method to get the CSV file data
     * @param sc Scanner object
     * @param path CSV file path
     */
    private static void searchRecords(Scanner sc, String path) {
        List<String[]> rows = readRecords(path);
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
    private static List<String[]> readRecords(String path) {
        List<String[]> rows = List.of();
        try (
                CSVReader reader = new CSVReaderBuilder(new FileReader(path))
                        .withSkipLines(1)
                        .build()
        ) {
            rows = reader.readAll();
        } catch (IOException | CsvException ex) {
            System.out.println("readRecords() | Exception: "+ex.getMessage());
        }
        return rows;
    }

    /**
     * This method is used to fill up the student details. To create the array of records.
     * @param sc Scanner object
     * @param path Path of the CSV file.
     */
    private static void addStudent(Scanner sc, String path) {
        File file = new File(path);
        List<String[]> rows = List.of();
        if (file.length() != 0) {
            rows = readRecords(path);
        }
        
        sc.nextLine();
        System.out.println("Enter the details of the student - ");
        String[] arr = new String[5];
       
        System.out.print("ID: ");
        String id = sc.nextLine();
        if (Integer.parseInt(id) > rows.size()) {
            arr[0] = id;
        } else {
            System.out.println();
            System.out.println("Id already exists. Please enter correct Id!!!!");
            return;
        }
        System.out.print("Name: ");
        arr[1] = sc.nextLine();
        System.out.print("Age: ");
        arr[2] = sc.nextLine();
        System.out.print("Gender: ");
        arr[3] = sc.nextLine();
        System.out.print("Course: ");
        arr[4] = sc.nextLine();

        rows = new ArrayList<>(List.of());
        rows.add(arr);
        appendDataToCSV(rows, path);
        System.out.println();
        System.out.println("<<<< Record added successfully! >>>>");
        System.out.println();
    }

    /**
     * This method is used to store records to CSV file. Using CSVWriter library to store the input in CSV file.
     * @param rows Student record array
     * @param path Path of CSV file
     */
    private static void appendDataToCSV(List<String[]> rows, String path) {
        try (
                ICSVWriter csvWriter = new CSVWriterBuilder(new FileWriter(path, true))
                        .withSeparator(',')
                        .build()
        ) {
            csvWriter.writeAll(rows);
        } catch (Exception ex) {
            System.out.println("writeDataToCSV() | Exception: "+ ex.getMessage());
        }
    }

    private static void writeDataToCSV(List<String[]> rows, String path) {
        try (
                ICSVWriter csvWriter = new CSVWriterBuilder(new FileWriter(path))
                        .withSeparator(',')
                        .build()
        ) {
            csvWriter.writeAll(rows);
        } catch (Exception ex) {
            System.out.println("writeDataToCSV() | Exception: "+ ex.getMessage());
        }
    }
}