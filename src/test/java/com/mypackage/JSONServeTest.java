package com.mypackage;

import com.google.gson.Gson;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

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
        Assert.assertEquals(6,restAssureEmpData.length);
    }
}
