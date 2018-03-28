package com.example.Awardsnoopmicroservicewrapper.features;

import com.example.Awardsnoopmicroservicewrapper.models.Favorite;
import com.example.Awardsnoopmicroservicewrapper.repositories.FavRepo;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static io.restassured.http.ContentType.JSON;
import static io.restassured.RestAssured.*;
import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.core.Is.is;

import java.util.stream.Stream;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class FavoritesApiFeatureTest {

    @Autowired
    private FavRepo favRepo;

    @Before
    public void setUp() {
        favRepo.deleteAll();
    }

    @After
    public void tearDown(){
        favRepo.deleteAll();
    }

    @Test
    public void shouldAllowFullCrudForAUser() throws Exception {
        Favorite firstFav = new Favorite(
                "first favorite",
                "i want to die"
        );

        Favorite secondFav = new Favorite(
                "second favorite",
                "i hope these tests work"
        );

        Stream.of(firstFav,secondFav)
                .forEach(fav ->{
                    favRepo.save(fav);
                });

        when()
                .get("http://localhost:8080/favorites/")
                .then()
                .statusCode(is(200))
                .and().body(containsString("first favorite"))
                .and().body(containsString("second favorite"));

        Favorite notYetinDB = new Favorite(
                "wew","lad"
        );
        given()
                .contentType(JSON)
                .and().body(notYetinDB)
                .when()
                .post("http://localhost:8080/favorites")
                .then()
                .statusCode(is(200))
                .and().body(containsString("wew"));

        when()
                .get("http://localhost:8080/favorites")
                .then()
                .statusCode(is(200))
                .and().body(containsString("die"))
                .and().body(containsString("favorite"))
                .and().body(containsString("wew"));

        when()
                .get("http://localhost:8080/favorites/" + secondFav.getId())
                .then()
                .statusCode(is(200))
                .and().body(containsString("second"))
                .and().body(containsString("tests"));

        secondFav.setTitle("jk third test");

        given()
                .contentType(JSON)
                .and().body(secondFav)
                .when()
                .patch("http://localhost:8080/favorites/"+secondFav.getId())
                .then()
                .statusCode(is(200)) //error here
                .and().body(containsString("third"));

        when()
                .delete("http://localhost:8080/favorites"+secondFav.getId())
                .then()
                .statusCode(is(200));


    }
}

