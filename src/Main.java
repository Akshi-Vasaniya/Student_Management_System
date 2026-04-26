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
//            addDataToCSV(header, path);
        }

        // Operations on file
        System.out.println("Select the Option: ");
        System.out.println("1. Add Student\n2. Show Records\n3. Search in records\n4. Update record\n5. Delete record");
        Scanner sc = new Scanner(System.in);
        System.out.print("=> ");

        switch (sc.nextInt()) {
            case 1:
//                addStudent(sc, path);
//                break;
            case 2:
//                List<String[]> rows = fetchStudent(path);
//                for (String[] row: rows) {
//                    for (String col: row) {
//                        System.out.print(col + "\t");
//                    }
//                    System.out.println();
//                }
                break;
            case 3:
//                searchRecords(sc, path);
                break;
            case 4:
//                updateRecord(path);
                break;
            case 5:
//                deleteRecord(path);
                break;
        }
    }
}