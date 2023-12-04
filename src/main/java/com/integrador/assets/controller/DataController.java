package com.integrador.assets.controller;

import com.mongodb.DBObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/data")
public class DataController {

    private final Logger logger = LoggerFactory.getLogger(DataController.class);

    @Autowired
    private MongoTemplate mongoTemplate;

    @GetMapping("/getAll")
    public ResponseEntity<List<DBObject>> getAllDocuments() {
        try {
            List<DBObject> documents = mongoTemplate.findAll(DBObject.class, "asset");
            return ResponseEntity.ok(documents);
        } catch (Exception e) {
            logger.error("Erro ao obter todos os documentos", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/getByFields")
    public ResponseEntity<List<DBObject>> getByFields(@RequestParam List<String> fields) {
        try {
            Query query = new Query();
            fields.forEach(query.fields()::include);
            List<DBObject> documents = mongoTemplate.find(query, DBObject.class, "asset");
            return ResponseEntity.ok(documents);
        } catch (Exception e) {
            logger.error("Erro ao obter documentos por campos específicos", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/getById")
    public ResponseEntity<DBObject> getById(@RequestParam Integer id) {
        try {
            Query query = new Query(Criteria.where("id").is(id));
            DBObject document = mongoTemplate.findOne(query, DBObject.class, "asset");
            return document != null ? ResponseEntity.ok(document) : ResponseEntity.notFound().build();
        } catch (Exception e) {
            logger.error("Erro ao obter documento por ID", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
