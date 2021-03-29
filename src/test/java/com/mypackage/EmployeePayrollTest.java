package com.mypackage;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import java.util.List;

public class EmployeePayrollTest {

    EmployeePayroll employeePayroll;
    @Before
    public void setup(){
        employeePayroll=new EmployeePayroll();
    }

    @Test
    public void given_select_statement_should_return_count(){
        List<EmployeePayrollData> employeePayrollDataList=employeePayroll.readData();
        Assert.assertEquals(4,employeePayrollDataList.size());
    }

    @Test
    public void update_table_should_return_true(){
        double salary=600000;
        int id=3;
        long result=employeePayroll.updateData(salary,id);
        Assert.assertEquals(1,result);
    }

    @Test
    public void should_reurn_employee_datails_for_a_given_date_range(){
        String date = "2019-01-01";
        List<EmployeePayrollData>employeePayrollDataList=employeePayroll.employeeDetailsfromDate(date);
        Assert.assertEquals(3,employeePayrollDataList.size());
    }

    @Test
    public void return_sum_avg_min_max_count(){
        List<String> list=employeePayroll.dataManipulation();
        Assert.assertEquals(12,list.size());
    }

    @Test
    public void insert_new_employee_in_employee_table(){
        String name="Peter";
        String date="2020-03-07";
        double salary=500000;
        String gender="M";

        employeePayroll.insertValuesintoTables(name,date,salary,gender);
        List<EmployeePayrollData> employeePayrollDataList=employeePayroll.readData();
        Assert.assertEquals(5,employeePayrollDataList.size());
    }

    @Test
    public void insert_in_payroll_details() {
        int payroll_id=1;
        double basicpay=6000;
        double deduction=20000;
        double taxpay=60000;
        double tax=80000;
        double netpay=3000;
        employeePayroll.insertIntoPayrollDetails(payroll_id,basicpay,deduction,taxpay,tax,netpay);
    }
}