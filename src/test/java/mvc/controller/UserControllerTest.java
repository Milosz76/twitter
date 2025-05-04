package mvc.controller;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.*;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;

import static io.restassured.RestAssured.given;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class UserControllerTest {

    @LocalServerPort
    private int port;

    private String baseUri;

    @BeforeEach
    void setup() {
        RestAssured.port = port;
        baseUri = "http://localhost:" + port;
    }

    @Test
    @Order(1)
    void shouldReturnUserPageView() {
        given()
                .when()
                .get("/userpage")
                .then()
                .statusCode(200);
    }

    @Test
    @Order(2)
    void shouldReturnAllUsers() {
        given()
                .accept(ContentType.JSON)
                .when()
                .get("/users")
                .then()
                .statusCode(200);
    }
}
