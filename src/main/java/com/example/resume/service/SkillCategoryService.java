package com.example.resume.service;

import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class RequiredSkillService {

        private static EntityManager entityManager;

        @Autowired
        public RequiredSkillService(EntityManager entityManager){
            RequiredSkillService.entityManager = entityManager;

        }
        public static RequiredSkill save(RequiredSkill requiredSkill)  {
            entityManager.persist(requiredSkill);
            return requiredSkill;
        }
        public static RequiredSkill findById(Long id) throws  RuntimeException {
            return Optional.ofNullable(entityManager.find(RequiredSkill.class, id)).orElseThrow();
        }

        public static RequiredSkill update(RequiredSkill requiredSkill) {
            entityManager.merge(requiredSkill);
            return requiredSkill;
        }

        public static void delete(Long id) {
            Optional <RequiredSkill> requiredSkill  = Optional.of(Optional.ofNullable(entityManager.find(RequiredSkill.class, id)).orElseThrow());
            {
                entityManager.remove(requiredSkill);
            }

        }

    }
