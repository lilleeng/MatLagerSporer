import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Scanner;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class Test {

    // Load from csv
    public static final String COMMA_DELIMITER = ",";

    public static void main(String[] args) {

            // Load from csv
        String fileName = "java implementation/database.csv";
        List<List<String>> records = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(fileName), StandardCharsets.UTF_8))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] values = line.split(COMMA_DELIMITER);
                records.add(Arrays.asList(values));
            }
        }
        catch (Exception e) {
            System.out.println(e);
        }

        System.out.println(records.size());

        for (List<String> row : records) {
            // System.out.println(row.size());
            System.out.printf("%-15s%-15s%-15s%-15s\n", row.get(0), row.get(1), row.get(2), row.get(3));
        }




        
        // File file = new File("database.csv");
        // BufferedReader reader = new BufferedReader(
        //     new InputStreamReader(
        //         new FileInputStream(file),
        //         StandardCharsets.UTF_8
        //     )
        // );

        
        



            // Input from user
        // Scanner scanner = new Scanner(System.in);
        // System.out.print("Enter: ");
        // String input = scanner.nextLine();
        // scanner.close();
        // System.out.println("You entered:\n" + input);
        // System.out.println("Trimmed line:\n" + input.trim());


        // String input = "40062000";
        // String[] dayMonthYear = input.split("\\.");

        // LocalDate expDate;
        // try {
        //     expDate = LocalDate.of(
        //     Integer.valueOf(dayMonthYear[2]),
        //     Integer.valueOf(dayMonthYear[1]),
        //     Integer.valueOf(dayMonthYear[0])
        // );
        // } catch (Exception e) {
        //     System.out.println("Incorrect input");
        //     return;
        // }
        // System.out.println(expDate);


        // DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        // LocalDate d = LocalDate.of(2000, 6, 3);
        // String sd = d.format(formatter).toString();
        // String[] parts = sd.split(String.valueOf("\\."));
        // for (String part : parts) {
        //     System.out.println(part);
        // }

        // System.out.println(
        //     Integer.valueOf(parts[0]) +
        //     Integer.valueOf(parts[1]) +
        //     Integer.valueOf(parts[2])
        //     );

        // String sn = "0123";
        // int n = Integer.valueOf(sn);
        // System.out.println(n);
    }
}
