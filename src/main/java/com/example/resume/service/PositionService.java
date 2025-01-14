package com.example.resume.service;

import com.example.resume.entity.Position;
import com.example.resume.entity.Skill;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PositionService {

        private final EntityManager entityManager;

        @Autowired
        public PositionService(EntityManager entityManager){
            this.entityManager = entityManager;

        }
        public  Position save(Position position)  {
            entityManager.persist(position);
            return position;
        }
        public  Position findById(Long id) throws  RuntimeException {
            return Optional.ofNullable(entityManager.find(Position.class, id)).orElseThrow();
        }

        public  Position update(Position position) {
            entityManager.merge(position);
            return position;
        }

        public  void delete(Long id) {
            Optional <Position> position  = Optional.of(Optional.ofNullable(entityManager.find(Position.class, id)).orElseThrow());
            {
                entityManager.remove(position);
            }

        }

    }
