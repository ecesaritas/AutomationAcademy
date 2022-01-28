import io.restassured.http.ContentType;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.startsWith;

public class SampleTest {
    @BeforeClass
    public void postCreatePet(){
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

    @AfterClass
    public void deletePet() {

        given().log().all().
                header("api-key", "special-key").
                when().
                delete("https://petstore.swagger.io/v2/pet/5").
                then()
                .statusCode(200).
                log().all();
    }
}
