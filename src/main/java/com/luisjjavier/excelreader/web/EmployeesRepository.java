/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.luisjjavier.excelreader.web;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 *
 * @author LuisJavier
 */
public class EmployeesRepository {

    private final String filePath;
    private List<Employee> localDb = new ArrayList<>();
    private final List<String> tableHeaders;

    public EmployeesRepository(String filePath) {
        this.tableHeaders = new ArrayList<>();
        this.tableHeaders.add("Name");
        this.tableHeaders.add("Lastname");
        this.tableHeaders.add("Salary");
        this.tableHeaders.add("Department");
        this.filePath = filePath;
        localDb = readFile();
    }

    private List<Employee> readFile() {
        File file = new File(filePath);
        if (!file.exists()) {
            writeFile();
        }
        try {
            Workbook workbook = WorkbookFactory.create(file);
            Sheet sheet = workbook.getSheet("Employees");
            ArrayList<Employee> employees = new ArrayList<>();
            DataFormatter formatter = new DataFormatter();
            int index = 0;
            for (Row row : sheet) {
                if (index > 0) {
                    String name = formatter.formatCellValue(row.getCell(0));
                    String lastname = formatter.formatCellValue(row.getCell(1));
                    double salary = Double.parseDouble(formatter.formatCellValue(row.getCell(2)));
                    String department = formatter.formatCellValue(row.getCell(3));

                    Employee employee = new Employee(name, lastname, salary, department);

                    employees.add(employee);
                }
                index++;
            }

            return employees;
        } catch (IOException | InvalidFormatException | EncryptedDocumentException ex) {
            Logger.getLogger(EmployeesRepository.class.getName()).log(Level.SEVERE, null, ex);

        }
        return null;
    }

    private Boolean writeFile() {
        try {
            Workbook workbook = new XSSFWorkbook();
            Sheet sheet = (Sheet) workbook.createSheet();
            workbook.setSheetName(0, "Employees");
            sheet.setColumnWidth((short) 0, (short) ((50 * 8) / ((double) 1 / 20)));
            sheet.setColumnWidth((short) 1, (short) ((50 * 8) / ((double) 1 / 20)));
            Font font1 = workbook.createFont();
            font1.setFontHeightInPoints((short) 10);
            font1.setBold(true);
            XSSFCellStyle cellStyle1 = (XSSFCellStyle) workbook.createCellStyle();
            cellStyle1.setFont(font1);
            Font font2 = workbook.createFont();
            font2.setFontHeightInPoints((short) 10);
            font2.setColor((short) Font.COLOR_NORMAL);
            XSSFCellStyle cellStyle2 = (XSSFCellStyle) workbook.createCellStyle();
            cellStyle2.setFont(font2);
            Row headerRow = sheet.createRow(0);

            createHeaders(headerRow, cellStyle1);

            for (int rownum = (short) 1; rownum <= localDb.size(); rownum++) {
                Row row = sheet.createRow(rownum);
                Employee employee = localDb.get(rownum - 1);
                Cell cell = row.createCell(0);
                cell.setCellValue(employee.getName());
                cell.setCellStyle(cellStyle2);

                Cell cell1 = row.createCell(1);
                cell1.setCellValue(employee.getLastname());
                cell1.setCellStyle(cellStyle2);

                Cell cell2 = row.createCell(2);
                cell2.setCellValue(employee.getSalary());
                cell2.setCellStyle(cellStyle2);

                Cell cell3 = row.createCell(3);
                cell3.setCellValue(employee.getSalary());
                cell3.setCellStyle(cellStyle2);
            }

            FileOutputStream outputStream = new FileOutputStream(filePath);
            workbook.write(outputStream);
            outputStream.close();
            workbook.close();
            return true;
        } catch (IOException ex) {
            Logger.getLogger(EmployeesRepository.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    private void createHeaders(Row headerRow, XSSFCellStyle cellStyle1) {
        for (int i = 0; i < tableHeaders.size(); i++) {
            String header = tableHeaders.get(i);
            Cell cellHeader = headerRow.createCell(i);
            cellHeader.setCellValue(header);
            cellHeader.setCellStyle(cellStyle1);
        }
    }

    public Employee  addEmployee(Employee employee) {
        this.localDb.add(employee);
        
        return employee;
    }

    public void deleteEmployee(int position) {
        this.localDb.remove(position);
    }

    public List<Employee> getEmployees() {
        return this.localDb;
    }

    public Boolean saveChanges() {
        return writeFile();
    }

    public Employee getEmployee(int id) {
        List<Employee> employees = getEmployees();

        if (id > employees.size()  && id <= 0) {
            return null;
        }

        return employees.get(id - 1);
    }
}
