package com.myPackage;
import org.junit.Assert;
import org.junit.Test;

import java.sql.*;
import java.util.Enumeration;
import java.util.List;


public class EmployeePayrollTest {

    @Test
    public void given_select_statement_should_return_count(){
        EmployeePayroll employeePayroll=new EmployeePayroll();
        List<EmployeePayrollData> employeePayrollDataList=employeePayroll.readData();
        Assert.assertEquals(3,employeePayrollDataList.size());
    }

    @Test
    public void update_table_should_return_true(){
        EmployeePayroll employeePayroll=new EmployeePayroll();
        long result=employeePayroll.updateData();
        Assert.assertEquals(1,result);
    }

    @Test
    public void should_reurn_employee_datails_for_a_given_date_range(){
        EmployeePayroll employeePayroll=new EmployeePayroll();
        List<EmployeePayrollData>employeePayrollDataList=employeePayroll.employeeDetailsfromDate();
        Assert.assertEquals(2,employeePayrollDataList.size());
    }
}
