import com.google.gson.Gson;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import models.OrderRequest;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.startsWith;

public class PetStore {

    @Test
    public void sample(){
        Response response= RestAssured.get("https://petstore.swagger.io/v2/store/inventory");

        System.out.println("getBody: "+response.asString());
        System.out.println("getBody: "+response.getBody().asString());

        Assert.assertEquals(response.getStatusCode(),200);

    }

    @Test
    public void getInventory() {
        given()
                .log().all().
                when()
                .get("https://petstore.swagger.io/v2/store/inventory")
                .then().statusCode(200).log().all();
    }

    @Test
    public void getPetDetail(){
        int petId = 3;
        given()
                .log().all().
                when()
                .get("https://petstore.swagger.io/v2/pet/" + petId).
                then().statusCode(200)
                .body("status", startsWith("av"))
                .body("category.id", equalTo(4));
    }

    @Test
    public void postCreatePet() {

        String postData= "{\n" +
                "  \"id\": 0,\n" +
                "  \"category\": {\n" +
                "    \"id\": 0,\n" +
                "    \"name\": \"string\"\n" +
                "  },\n" +
                "  \"name\": \"doggie\",\n" +
                "  \"photoUrls\": [\n" +
                "    \"string\"\n" +
                "  ],\n" +
                "  \"tags\": [\n" +
                "    {\n" +
                "      \"id\": 0,\n" +
                "      \"name\": \"string\"\n" +
                "    }\n" +
                "  ],\n" +
                "  \"status\": \"available\"\n" +
                "}";

        given().
                body(postData).
                contentType(ContentType.JSON).
                log().all().
                when()
                .post("https://petstore.swagger.io/v2/pet").
                then().statusCode(200)
                .log().all();
    }

    @Test
    public void createOrder() {

        OrderRequest orderRequest = new OrderRequest(1,1, 1,"2022-01-27T12:53:55.438Z", "placed", true);
        String requestBody= new Gson().toJson(orderRequest);

        given()
                .log().all().header("Content-Type", "Application/json").body(requestBody).
                when().post("https://petstore.swagger.io/v2/store/order").
                then().statusCode(200).log().all();
    }

    @Test
    public void postUpdatePet() {

        int petId=4;

        Map<String,Object> queryParams = new HashMap<>();
        queryParams.put("name", "doggie");
        queryParams.put("status", "available");

        given().log().all().queryParams(queryParams).
                when().post("https://petstore.swagger.io/v2/pet/" + petId).
                then()
                .statusCode(200).log().all();

    }
}
