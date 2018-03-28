package com.example.Awardsnoopmicroservicewrapper.repositories;

import com.example.Awardsnoopmicroservicewrapper.models.Favorite;
import org.springframework.data.repository.CrudRepository;

public interface FavRepo extends CrudRepository<Favorite, Long>{
}
