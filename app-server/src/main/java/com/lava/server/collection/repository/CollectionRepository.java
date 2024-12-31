package com.lava.server.collection.repository;

import com.lava.server.collection.model.entity.Collection;
import com.lava.server.openapi.model.CollectionDTO;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface  CollectionRepository  extends CrudRepository<Collection, String> {

    @Query("""
           SELECT new com.lava.server.openapi.model.CollectionDTO(c.id,  c.name, c.description, c.lang, c.fee )
           FROM Collection c
           WHERE c.id = :id
           """)
    CollectionDTO findDTOById(String id);
}
