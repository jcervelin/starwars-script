package com.starwars.script.dao;

import com.starwars.script.model.entity.CharacterEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CharacterDao extends MongoRepository<CharacterEntity, String> {

}