package com.prog3.demo.repo;

import java.util.List;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;
import org.springframework.stereotype.Repository;
import com.prog3.demo.model.Lugar;

@Repository
public interface LugarRepository extends Neo4jRepository<Lugar, String> {

}