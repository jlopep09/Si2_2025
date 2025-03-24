package appvehiculos;
import org.apache.poi.xssf.usermodel.*;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import org.apache.poi.ss.usermodel.CellType;

/**
 * @author Jose Antonio Lopez Perez
 *
 * Esta clase es la encargada de leer y modificar el fichero Excel de datos usando
 * la libreria ApachePOI
 *  EL ORDEN DE LAS COLUMNAS NO VAN A CAMBAIR, LOS TIPOS DE DATOS DE LAS COLUMNAS NO VAN A CAMBIAR.
 *  EN LAS ORDENANZAS IGUAL, LOS TRAMOS PARA LOS TURISMOS SON IGUALES EN TODA ESPAÑA, NO DEBERIAN CAMBIAR ENTRE MUNICIPIOS
 */
public class ExcelManager {
    XSSFWorkbook Excel;
    String rute;
    String fileName;
    String suffix;
    public ExcelManager(String rute, String fileName, String suffix){
        this.rute = rute;
        this.fileName = fileName;
        this.suffix = suffix;
        openExcel();

    }

    public boolean closeExcel() throws FileNotFoundException {
        if(Excel == null){
            throw new FileNotFoundException("No se ha cargado el archivo excel, usa el metodo openExcel primero");
        }
        try{
            Excel.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return true;
    }
    private boolean openExcel(){
        try {
            FileInputStream fis = new FileInputStream(this.rute + this.fileName + ".xlsx");
            XSSFWorkbook workbook = new XSSFWorkbook(fis);
            this.Excel = workbook;
            System.out.println("Archivo Excel cargado correctamente");
            return true;
        } catch (FileNotFoundException e) {
            System.out.println("ERROR No se ha encontrado el archivo: "+ this.rute + this.fileName + ".xlsx");
            throw new RuntimeException(e);
        } catch (IOException e) {
            System.out.println("Se ha producido un error al intentar cargar el archivo");
            throw new RuntimeException(e);
        }
    }
    public int getRowsCount(int sheetNum){
        XSSFSheet sheet =  Excel.getSheetAt(sheetNum);
        return sheet.getLastRowNum()+1;
    }


    public String getCellValue(int sheetNumber, int rowNum, int columNum) throws FileNotFoundException {
        if (Excel == null) {
            throw new FileNotFoundException("No se ha cargado el archivo Excel, usa el método openExcel primero");
        }
        String result = "";
        XSSFSheet sheet = Excel.getSheetAt(sheetNumber);
        XSSFRow row = sheet.getRow(rowNum);

        if (row == null) {
            return "EMPTYROW";
        }

        boolean empty = true;
        for (int i = 0; i < row.getLastCellNum(); i++) {
            XSSFCell tempCell = row.getCell(i);
            if (tempCell != null && tempCell.getCellType() == CellType.STRING 
                && tempCell.getStringCellValue() != null && !tempCell.getStringCellValue().isEmpty()) {
                empty = false;
                break;
            } 
        }

        if (empty) {
            return "EMPTYROW";
        }

        XSSFCell cell = row.getCell(columNum);
        if (cell == null || cell.getRawValue()==null) {
            return "";
        }

        switch (cell.getCellType()) {
            case STRING:
                result = cell.getStringCellValue();
                break;
            case NUMERIC:
                // Forzar a BigDecimal para evitar problemas con números largos
                BigDecimal bd = new BigDecimal(cell.getNumericCellValue());
                result = bd.toPlainString();
                break;
            case BOOLEAN:
                result = Boolean.toString(cell.getBooleanCellValue());
                break;
            default:
                result = cell.getRawValue();
        }

        return result;
    }
    public boolean setCellValue(int sheetNumber, int rowNum, int columNum, String newValue) throws IOException {
        if(Excel == null){
            throw new FileNotFoundException("No se ha cargado el archivo excel, usa el metodo openExcel primero");
        }

        // Obtener la hoja de trabajo (sheet) por su índice (por ejemplo, la primera hoja)
        XSSFSheet sheet = Excel.getSheetAt(sheetNumber);
        XSSFRow row = sheet.getRow(rowNum);
        if(row == null){
            return false;
        }
        XSSFCell cell = row.getCell(columNum);
        if(cell == null){
            return false;
        }
        cell.setCellValue(newValue);
        FileOutputStream fos = new FileOutputStream(this.rute + this.fileName + this.suffix + ".xlsx");
        Excel.write(fos);
        fos.close();
        return true;
    }
}
