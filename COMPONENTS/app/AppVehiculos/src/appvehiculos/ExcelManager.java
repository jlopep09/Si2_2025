package appvehiculos;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.*;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Iterator;

/**
 * @author Jose Antonio Lopez Perez
 *
 * Esta clase es la encargada de leer y modificar el fichero Excel de datos usando
 * la libreria ApachePOI
 */
public class ExcelManager {
    XSSFWorkbook ExcelAgua;
    public ExcelManager(){

    }
    public boolean openExcel(String rute){
        try {
            FileInputStream fis = new FileInputStream(rute);
            XSSFWorkbook workbook = new XSSFWorkbook(fis);
            this.ExcelAgua = workbook;
            System.out.println("Archivo Excel cargado correctamente");
            return true;
        } catch (FileNotFoundException e) {
            System.out.println("ERROR No se ha encontrado el archivo");
            throw new RuntimeException(e);
        } catch (IOException e) {
            System.out.println("Se ha producido un error al intentar cargar el archivo");
            throw new RuntimeException(e);
        }
    }
    public int getRowsCount(int sheetNum){
        XSSFSheet sheet =  ExcelAgua.getSheetAt(sheetNum);
        return sheet.getLastRowNum()+1;
    }
    public boolean closeExcel(String rute) throws FileNotFoundException {
        if(ExcelAgua == null){
            throw new FileNotFoundException("No se ha cargado el archivo excel, usa el metodo openExcel primero");
        }
        try{
            ExcelAgua.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return true;
    }

    public String getCellValue(int sheetNumber, int rowNum, int columNum) throws FileNotFoundException {
        if(ExcelAgua == null){
            throw new FileNotFoundException("No se ha cargado el archivo excel, usa el metodo openExcel primero");
        }
        String result = "";
        // Obtener la hoja de trabajo (sheet) por su índice (por ejemplo, la primera hoja)
        XSSFSheet sheet = ExcelAgua.getSheetAt(sheetNumber);
        XSSFRow row = sheet.getRow(rowNum);
        if(row == null){
            return "EMPTYROW";
        }
        boolean empty = true;
        for (int i = 0; i < row.getLastCellNum(); i++) {
            if(row.getCell(i) !=null&& row.getCell(i).getStringCellValue()!=null&& row.getCell(i).getStringCellValue()!=""){
                empty = false;
                break;
            }
        }
        if(empty){
            return "EMPTYROW";
        }
        XSSFCell cell = row.getCell(columNum);
        if(cell == null){
            return "";
        }
        result = cell.getStringCellValue();
        return result;
    }
    public boolean setCellValue(int sheetNumber, int rowNum, int columNum, String newValue) throws IOException {
        if(ExcelAgua == null){
            throw new FileNotFoundException("No se ha cargado el archivo excel, usa el metodo openExcel primero");
        }

        // Obtener la hoja de trabajo (sheet) por su índice (por ejemplo, la primera hoja)
        XSSFSheet sheet = ExcelAgua.getSheetAt(sheetNumber);
        XSSFRow row = sheet.getRow(rowNum);
        if(row == null){
            return false;
        }
        XSSFCell cell = row.getCell(columNum);
        if(cell == null){
            return false;
        }
        cell.setCellValue(newValue);
        FileOutputStream fos = new FileOutputStream("resources/SistemasAguaTrasEjecucionTest.xlsx");
        ExcelAgua.write(fos);
        fos.close();
        return true;
    }
}
