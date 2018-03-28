package com.example.favoritesapi.repositories;

import com.example.favoritesapi.models.Favorite;
import com.google.common.collect.Iterables;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class FavRepoTest {
    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private FavRepo favRepo;

    @Before
    public void setUp(){
        Favorite firstFav = new Favorite(
                "muh first fav",
                "muh first description"
        );
        Favorite secondFav = new Favorite(
                "my second favorite",
                "my second description"
        );

        entityManager.persist(firstFav);
        entityManager.persist(secondFav);
        entityManager.flush();
    }

    @Test
    public void findAll_returnsAllFavs(){
        Iterable<Favorite> usersFromDB = favRepo.findAll();

        assertThat(Iterables.size(usersFromDB), is(2));
    }
    @Test
    public void findAll_returnsTitle() {
        Iterable<Favorite> usersFromDb = favRepo.findAll();

        String secondFavTitle = Iterables.get(usersFromDb, 1).getTitle();

        assertThat(secondFavTitle, is("my second favorite"));
    }

    @Test
    public void findAll_returnsDescription() {
        Iterable<Favorite> usersFromDb = favRepo.findAll();

        String secondFavDescription = Iterables.get(usersFromDb, 1).getDescription();

        assertThat(secondFavDescription, is("my second description"));
    }




}
