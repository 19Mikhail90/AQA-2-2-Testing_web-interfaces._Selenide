import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Date {
    public static void main(String[] args) {
        LocalDate today = LocalDate.now().plusDays(4);
        String formattedDate = today.format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
        System.out.println(formattedDate);
    }
}

