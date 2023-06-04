package test;

import io.restassured.response.ValidatableResponse;

import static io.restassured.RestAssured.given;

public class CourierAPI extends BaseSettings {
    private static final String COURIER_PATH="/api/v1/courier";
    private static final String COURIER_LOGIN_PATH="/api/v1/courier/login";
    private static final String COURIER_DELETE_PATH="/api/v1/courier/%d";

    public ValidatableResponse create (Courier courier){
        return given()
                .spec(getBaseSettings())
                .body(courier)
                .post(COURIER_PATH)
                .then();
    }
    public ValidatableResponse login (Credentials credentials){
        return given()
                .spec(getBaseSettings())
                .body(credentials)
                .post(COURIER_LOGIN_PATH)
                .then();
    }
    public static ValidatableResponse delete(int intId){
        Id id = new Id(intId);
        return given()
                .spec(getBaseSettings())
                .body(id)
                .delete(String.format(COURIER_DELETE_PATH, intId))
                .then();
    }
}
