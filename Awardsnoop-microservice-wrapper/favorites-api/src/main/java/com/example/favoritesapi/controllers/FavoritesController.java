package com.example.favoritesapi.controllers;

import com.example.favoritesapi.models.Favorite;
import com.example.favoritesapi.repositories.FavRepo;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


@RestController
public class FavoritesController {
    @Autowired
    private FavRepo favRepo;

    @GetMapping("/")
    public Iterable<Favorite> findAllFavorites(){return favRepo.findAll();}

    @GetMapping("/{favID}")
    public Favorite findFavById(@PathVariable Long favID) throws NotFoundException {
        Favorite foundFav = favRepo.findOne(favID);

        if(foundFav == null) {
            throw new NotFoundException("Favorite with ID " + favID + " not found");
        }

        return foundFav;
    }

    @DeleteMapping("/{favID}")
    public HttpStatus deleteUserById(@PathVariable Long favID) throws EmptyResultDataAccessException {
        favRepo.delete(favID);
        return HttpStatus.OK;
    }

    @PostMapping("/")
    public Favorite createNewFav(@RequestBody Favorite newFav){ return favRepo.save(newFav);}

    @PatchMapping("/{favID}")
    public Favorite updateFavorite(@RequestBody Long favID, @RequestBody Favorite favRequest) throws NotFoundException {
        Favorite favFromDB = favRepo.findOne(favID);

        if(favFromDB ==null){
            throw new  NotFoundException("this favorite isnt in the db");
        }

        favFromDB.setTitle(favRequest.getTitle());
        favFromDB.setDescription(favRequest.getDescription());

        return favRepo.save(favFromDB);
    }
    @ExceptionHandler
    void handleFavNotFound(
            NotFoundException exception,
            HttpServletResponse response) throws IOException {

        response.sendError(HttpStatus.NOT_FOUND.value(), exception.getMessage());
    }

    @ExceptionHandler
    void handleDeleteNotFoundException(
            EmptyResultDataAccessException exception,
            HttpServletResponse response) throws IOException {

        response.sendError(HttpStatus.NOT_FOUND.value());
    }
}
