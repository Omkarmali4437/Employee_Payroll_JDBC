package com.mypackage;

import com.google.gson.Gson;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.sql.SQLException;

public class JSONServeTest {


    @Before
    public void setup(){
        RestAssured.baseURI="http://localhost";
        RestAssured.port=4000;
    }

    public JSONServerEmpData[] getEmplist(){
        Response response=RestAssured.get("/employees");
        System.out.println("Data in json is: \n"+response.asString());
        JSONServerEmpData[] restAssureEmpData=new Gson().fromJson(response.asString(),JSONServerEmpData[].class);
        return restAssureEmpData;
    }

    public Response addEmployeeToJsonServer(JSONServerEmpData restAssureEmpData){
        String employee=new Gson().toJson(restAssureEmpData);
        RequestSpecification requestSpecification=RestAssured.given();
        requestSpecification.header("Content-Type","application/json");
        requestSpecification.body(employee);
        return requestSpecification.post("/employees");
    }

    @Test
    public void givenEmployeeDate_shouldRetrieve_ServerData(){
        JSONServerEmpData[] restAssureEmpData=getEmplist();
        System.out.println(restAssureEmpData);
        Assert.assertEquals(8,restAssureEmpData.length);
    }

    @Test
    public void whenNewEmployee_isAdded_Sholdreturn201ResponseCode(){
        JSONServerEmpData[] jsonServerEmpData=getEmplist();

        JSONServerEmpData jsonServerEmpData1=new JSONServerEmpData(8,"Manish",20000);

        Response response=addEmployeeToJsonServer(jsonServerEmpData1);
        int statusCode= response.statusCode();
        Assert.assertEquals(201,statusCode);
    }

    @Test
    public void givenNewSalary_Should_Retun200ResponseCode() throws SQLException {
        JSONServerEmpData[] serverEmpData=getEmplist();

        RequestSpecification requestSpecification=RestAssured.given();
        requestSpecification.header("Content-Type","application/json");
        requestSpecification.body("{\"name\":\"Sayali\",\"salary\":\"789914\"}");
        Response response=requestSpecification.put("/employees/update/6");

        int statusCode=response.getStatusCode();
        Assert.assertEquals(200,statusCode);
    }

    @Test
    public void givenDelete_Command_ShouldRetun200ResponseCode() throws SQLException {
        JSONServerEmpData[] serverEmpData=getEmplist();
        String empJson=new Gson().toJson(serverEmpData);

        RequestSpecification requestSpecification=RestAssured.given();
        requestSpecification.header("Content-Type","application/json");
        requestSpecification.body(empJson);
        Response response=requestSpecification.delete("/employees/delete/8");

        int statusCode=response.getStatusCode();
        Assert.assertEquals(200,statusCode);
    }
}
