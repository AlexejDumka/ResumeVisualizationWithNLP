package com.example.resume.service;

import com.example.resume.entity.SkillCategory;
import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class SkillCategoryService {

        private static EntityManager entityManager;

        @Autowired
        public SkillCategoryService(EntityManager entityManager){
            SkillCategoryService.entityManager = entityManager;

        }
        public static SkillCategory save(SkillCategory requiredSkill)  {
            entityManager.persist(requiredSkill);
            return requiredSkill;
        }
        public static SkillCategory findById(Long id) throws  RuntimeException {
            return Optional.ofNullable(entityManager.find(SkillCategory.class, id)).orElseThrow();
        }

        public static SkillCategory update(SkillCategory requiredSkill) {
            entityManager.merge(requiredSkill);
            return requiredSkill;
        }

        public static void delete(Long id) {
            Optional <SkillCategory> requiredSkill  = Optional.of(Optional.ofNullable(entityManager.find(SkillCategory.class, id)).orElseThrow());
            {
                entityManager.remove(requiredSkill);
            }

        }

    }
