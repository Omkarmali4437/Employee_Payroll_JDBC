package com.myPackage;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDate;
import java.util.Date;
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
    public void retur_sum_avg_min_max_count(){
        List<String> list=employeePayroll.dataManipulation();
        Assert.assertEquals(12,list.size());
    }
}
