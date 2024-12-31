package com.lava.server.collection.mapper;

import com.lava.server.collection.model.entity.Collection;
import com.lava.server.openapi.model.CollectionDTO;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface CollectionMapper {
    CollectionDTO collectionToCollectionDTO(Collection collection);

    Collection collectionDTOToCollection(CollectionDTO collectionDTO);
}

