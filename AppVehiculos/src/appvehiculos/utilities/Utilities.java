package appvehiculos.utilities;
import POJOS.Contribuyente;
import appvehiculos.data.ExcelManager;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;
import java.sql.Date;
import java.time.LocalDate;

/**
 *
 * @author José Antonio López Pérez
 */
public class Utilities {
      public static String askPeriod(){
        System.out.println("Por favor, introduzca el año de generación de recibos. (p.e. 2024)");
        Scanner sc = new Scanner(System.in);
        return sc.nextLine();
    }
    public static Double getDoubleOrNull(String cellValue) {
        if (cellValue == null || cellValue.trim().isEmpty() || cellValue.equals(ExcelManager.cellType.EMPTYCELL.toString())) {
            return null;
        }
        try {
            return Double.valueOf(cellValue);
        } catch (NumberFormatException e) {
            return null;
        }
    }
    public static String getEmail(ArrayList<String> emailList, Contribuyente cont){
        String letraInicial = cont.getNombre().substring(0,1);
        String letraInicialApellido1 = cont.getApellido1().substring(0,1);
        String letraInicialApellido2 = cont.getApellido2().substring(0,1);
        String userEmail = letraInicial + letraInicialApellido1 + letraInicialApellido2;
        emailList.add(userEmail);
        int nRepeticion = Collections.frequency(emailList, userEmail) - 1;
        String nRepeticionString = (nRepeticion < 10) ? ("0"+nRepeticion) : String.valueOf(nRepeticion);
        return userEmail + nRepeticionString + "@vehiculos2025.com";
    }
       public static Date getDateOrNull(String cellValue) {
        if (cellValue == null || cellValue.trim().isEmpty()
            || cellValue.equals(ExcelManager.cellType.EMPTYCELL.toString())) {
            return null;
        }
        try {
            double excelDate = Double.parseDouble(cellValue);
            // Fecha base: 1899-12-31 (serial 1 → 1900-01-01)
            LocalDate base = LocalDate.of(1899, 12, 31);
            long days = (long) excelDate;
            // Para seriales >= 60, restamos 1 día extra por el 29‑feb ficticio
            if (days >= 60) {
                days--;
            }
            LocalDate date = base.plusDays(days);
            return Date.valueOf(date);
        } catch (NumberFormatException e) {
            // Si no es un número, intentamos parsear ISO “yyyy-MM-dd”
            try {
                return Date.valueOf(cellValue);
            } catch (IllegalArgumentException ex) {
                return null;
            }
        }
    }
}
