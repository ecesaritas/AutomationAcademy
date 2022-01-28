import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.startsWith;

public class DataProvider {

    public class DataProvider2 {

        @org.testng.annotations.DataProvider(name="dataProvider")
        public Object[][] dataProvider (){
            return new Object[][]{
                    {3, 200},{4, 200},{5, 200}
            };
        }


        @Test(dataProvider = "dataProvider")
        public void getPetDetail(int petId, int statusCode){
            given()
                    .log().all().
                    when()
                    .get("https://petstore.swagger.io/v2/pet/" + petId).
                    then().statusCode(statusCode)
                    .body("status", startsWith("av"))
                    .body("category.id", equalTo(4));
        }
    }
}
