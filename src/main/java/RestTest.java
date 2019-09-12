import com.google.gson.JsonObject;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.parsing.Parser;
import io.restassured.response.Response;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.given;

public class RestTest {

    public static Response doGetRequest(String endpoint) {
        RestAssured.defaultParser = Parser.JSON;

        return
                given()
                        .contentType(ContentType.JSON)
                        .body("")
                        .get(endpoint)
                        .then()
                        .statusCode(200)
                        .extract()
                        .response();
    }

    public static Response doPostRequest(String endpoint) {
        RestAssured.defaultParser = Parser.JSON;

        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("userId",1);
        jsonObject.addProperty("title","KENTECH title");
        jsonObject.addProperty("body","KENTECH body");
        //next element
        return
                given()
                        .contentType(ContentType.JSON)
                        .body(jsonObject.toString())
                        .post(endpoint)
                        .then()
                        .statusCode(201)
                        .extract()
                        .response();
    }

    public static Response doPutRequest(String endpoint) {
        RestAssured.defaultParser = Parser.JSON;

        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("userId",1);
        jsonObject.addProperty("title","KENTECH title");
        jsonObject.addProperty("body","KENTECH PUT body");
        //put at specified order
        return
                given()
                        .contentType(ContentType.JSON)
                        .body(jsonObject.toString())
                        .put(endpoint + "/1")
                        .then()
                        .statusCode(200)
                        .extract()
                        .response();
    }

    public static Response doDeleteRequest(String endpoint) {
        RestAssured.defaultParser = Parser.JSON;

        return
                given()
                        .contentType(ContentType.JSON)
                        .delete(endpoint + "/1")
                        .then()
                        .statusCode(200)
                        .extract()
                        .response();
    }

    public static void main(String[] args) {
        String method="";
        Response response;
        Map<Object,Object> responseMap = new HashMap<Object, Object>();
        List<String> jsonResponse;

        String basePath = "https://jsonplaceholder.typicode.com";

        //API Testing 1st task
        method = "/users";
        response = doGetRequest(  basePath + method);
        jsonResponse = response.jsonPath().getList("$");
        System.out.println("users response size with GET call: " + jsonResponse.size());

        method = "/posts";
        response = doGetRequest(  basePath + method);
        jsonResponse = response.jsonPath().getList("$");
        System.out.println("posts response size with GET call: " + jsonResponse.size());

        //API Testing 2nd task
        //POST
        response = doPostRequest(  basePath + method);
        responseMap = response.jsonPath().getMap("");
        System.out.println("posts response size POST call: " + responseMap.size());

        //PUT
        response = doPutRequest(  basePath + method);
        responseMap = response.jsonPath().getMap("");
        System.out.println("posts response size PUT call: " + responseMap.size());

        //DELETE
        response = doDeleteRequest(  basePath + method);
        responseMap = response.jsonPath().getMap("");
        System.out.println("posts response size DELETE call: " + responseMap.size());
    }
}