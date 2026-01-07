package rental.car.project.utility;

import rental.car.project.prenotazione.domain.StatusPrenotazione;

import java.time.LocalDate;

public class RequestParamParser {

    private RequestParamParser() {}

    public static String convertString(String s) {
        if (s == null) return null;
        String t = s.trim();
        return (t.isEmpty() || "null".equalsIgnoreCase(t)) ? null : t;
    }

    public static Long toLong(String s) {
        s = convertString(s);
        return (s == null) ? null : Long.valueOf(s);
    }

    public static LocalDate toLocalDate(String s) {
        s = convertString(s);
        return (s == null) ? null : LocalDate.parse(s); // yyyy-MM-dd
    }

    public static StatusPrenotazione toStatus(String s) {
        s = convertString(s);
        if (s == null) return null;
        try {
            return StatusPrenotazione.valueOf(s);
        } catch (IllegalArgumentException ex) {
            return null; // oppure throw -> 400
        }
    }
}
