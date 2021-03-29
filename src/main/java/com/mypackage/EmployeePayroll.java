package com.mypackage;

import java.sql.*;
import java.util.ArrayList;
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
        List<EmployeePayrollData> employeePayrollDataArrayList=new ArrayList<>();
        try {
            Connection connection=this.getConnection();
            PreparedStatement preparedStatement=connection.prepareStatement("Select * from employee_payroll; ");
            ResultSet resultSet=preparedStatement.executeQuery();

            while (resultSet.next()){
                EmployeePayrollData employeePayrollData=new EmployeePayrollData(resultSet.getInt(1),resultSet.getString(2),
                        resultSet.getDate(3),resultSet.getDouble(4),resultSet.getString(5));
                employeePayrollDataArrayList.add(employeePayrollData);
            }
            System.out.println(employeePayrollDataArrayList.toString());
            connection.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return employeePayrollDataArrayList;
    }

    public long updateData(double salary,int id){
        try {
            Connection connection=this.getConnection();
            PreparedStatement preparedStatement=connection.prepareStatement("Update employee_payroll set salary=? where id=? ; ");
            preparedStatement.setDouble(1,salary);
            preparedStatement.setInt(2,id);
            long resultSet=preparedStatement.executeUpdate();
            System.out.println(resultSet);
            return resultSet;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return 0;
    }

    public List<EmployeePayrollData> employeeDetailsfromDate(String date){
        List<EmployeePayrollData> employeePayrollDataArrayList=new ArrayList<>();
        try {
            Connection connection=this.getConnection();
            PreparedStatement preparedStatement=connection.prepareStatement("Select * from employee_payroll where start>=? ");
            preparedStatement.setDate(1, Date.valueOf(date));
            ResultSet resultSet=preparedStatement.executeQuery();

            while (resultSet.next()){
                EmployeePayrollData employeePayrollData=new EmployeePayrollData(resultSet.getInt(1),resultSet.getString(2),
                        resultSet.getDate(3),resultSet.getDouble(4),resultSet.getString(5));
                employeePayrollDataArrayList.add(employeePayrollData);
            }
            System.out.println(employeePayrollDataArrayList.toString());
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return employeePayrollDataArrayList;
    }

    public List<String> dataManipulation(){
        List<String> list=new ArrayList();
        try {
            Connection connection=this.getConnection();
            PreparedStatement preparedStatement=connection.prepareStatement("select gender,sum(salary), avg(salary),min(salary),max(salary),count(salary) from employee_payroll group by gender; ");
            ResultSet resultSet=preparedStatement.executeQuery();
            while (resultSet.next()){
                int index=1;
                System.out.println("Gender: "+resultSet.getString(1));
                System.out.println("Salary: "+resultSet.getString(2));
                for (int i=0;i<13;i++){
                    if(index<7) {
                        list.add(i, resultSet.getString(index));
                        index++;
                    }
                }
                System.out.println(list);
            }
        }catch (SQLException throwables){
            throwables.printStackTrace();
        }
        return list;
    }

    public void insertValuesintoTables(String name,String date,double salary,String gender) throws SQLException {
        try{
            Connection connection=this.getConnection();
            connection.setAutoCommit(false);
            PreparedStatement preparedStatement=connection.prepareStatement("insert into employee_payroll(name,start,salary,gender) values(?,?,?,?); ");
            preparedStatement.setNString(1,name);
            preparedStatement.setDate(2, Date.valueOf(date));
            preparedStatement.setDouble(3,salary);
            preparedStatement.setNString(4,gender);
            int resultSet=preparedStatement.executeUpdate();

            int i=3;
            PreparedStatement preparedStatement1=connection.prepareStatement("insert into payroll_detials(payroll_id,basicpay,deduction,taxpay,tax,netpay) values(?,?,?,?,?,?); ");
            preparedStatement1.setInt(1,i);
            preparedStatement1.setDouble(2, salary/20);
            preparedStatement1.setDouble(3,salary/10);
            preparedStatement1.setDouble(4,salary/8);
            preparedStatement1.setDouble(5,salary/60);
            preparedStatement1.setDouble(6,salary/30);
            int resultSet1=preparedStatement1.executeUpdate();
            i++;
            connection.commit();
        }catch (SQLException throwables){
            throwables.printStackTrace();
        }
    }

    public int insertIntoPayrollDetails(int payroll_id,double basicpay,double deduction,double taxpay,double tax,double netpay){
        try{
            Connection connection=this.getConnection();
            PreparedStatement preparedStatement=connection.prepareStatement("insert into payroll_detials(payroll_id,basicpay,deduction,taxpay,tax,netpay) values(?,?,?,?,?,?); ");

            preparedStatement.setInt(1,payroll_id);
            preparedStatement.setDouble(2, basicpay);
            preparedStatement.setDouble(3,deduction);
            preparedStatement.setDouble(4,taxpay);
            preparedStatement.setDouble(5,tax);
            preparedStatement.setDouble(6,netpay);
            int resultSet=preparedStatement.executeUpdate();
            return resultSet;
        }catch (SQLException throwables){
            throwables.printStackTrace();
        }
        return 0;
    }
}
