package com.myPackage;

import java.sql.*;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

public class EmployeePayroll {

    private Connection getConnection() throws SQLException {
        String jdbcurl="jdbc:mysql://localhost:3306/employeepayroll?useSSL=false";
        String usernam="root";
        String  password="Madnimisha@4437";
        Connection con;
        System.out.println("Connecting to database: "+jdbcurl);
        con= DriverManager.getConnection(jdbcurl,usernam,password);
        System.out.println("Connection successfull: "+con);
        return con;
    }

    public List<EmployeePayrollData> readData() {
        String sql_query="Select * from employee_payroll; ";
        List<EmployeePayrollData> employeePayrollDataArrayList=new ArrayList<>();
        try {
            Connection connection=this.getConnection();
            Statement statement=connection.createStatement();
            ResultSet resultSet=statement.executeQuery(sql_query);

            while (resultSet.next()){
                int id=resultSet.getInt(1);
                String name=resultSet.getString(2);
                Date date=resultSet.getDate(3);
                double salary=resultSet.getDouble(4);
                String gender=resultSet.getString(5);
                System.out.println("+++++++++++++++++++++++++++");
                System.out.println("Id: "+id);
                System.out.println("FirstName: "+name);
                System.out.println("LastName: "+date);
                System.out.println("Adress: "+salary);
                System.out.println("City: "+gender);

                EmployeePayrollData employeePayrollData=new EmployeePayrollData(resultSet.getInt(1),resultSet.getString(2),
                        resultSet.getDate(3),resultSet.getDouble(4),resultSet.getString(5));
                employeePayrollDataArrayList.add(employeePayrollData);
            }
            statement.close();
            connection.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return employeePayrollDataArrayList;
    }

    public long updateData(){
        try {
            Connection connection=this.getConnection();
            PreparedStatement preparedStatement=connection.prepareStatement("Update employee_payroll set salary=? where id=? ; ");
            preparedStatement.setDouble(1,300000);
            preparedStatement.setInt(2,3);
            long resultSet=preparedStatement.executeUpdate();
            System.out.println(resultSet);
            return resultSet;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return 0;
    }
}
