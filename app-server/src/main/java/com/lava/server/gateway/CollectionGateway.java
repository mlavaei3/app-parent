package com.lava.server.gateway;

import com.lava.server.openapi.api.CollectionApi;
import com.lava.server.openapi.model.CollectionDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
public class CollectionGateway implements CollectionApi {


    @Override
    public ResponseEntity<List<CollectionDTO>> listCollection(Long offset, Long limit) {
        return ResponseEntity.ok(null);
    }

}
