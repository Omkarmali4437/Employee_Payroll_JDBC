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

}
