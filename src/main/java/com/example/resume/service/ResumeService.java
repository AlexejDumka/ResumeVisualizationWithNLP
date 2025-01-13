package com.example.resume.service;

import com.example.resume.entity.Section;
import com.example.resume.entity.Skill;
import com.example.resume.repository.PositionRepository;
import com.example.resume.repository.SkillRepository;
import jakarta.annotation.PostConstruct;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class SkillService {
         @PersistenceContext
        private static EntityManager entityManager = null;

        @Autowired
        public SkillService(EntityManager entityManager){
            com.example.resume.service.SkillService.entityManager = entityManager;

        }
        public static Skill save(Skill skill)  {
            entityManager.persist(skill);
            return skill;
        }
        public static Skill findById(Long id) throws  RuntimeException {
            return Optional.ofNullable(entityManager.find(Skill.class, id)).orElseThrow();
        }

        public static Skill update(Skill skill) {
            entityManager.merge(skill);
            return skill;
        }

        public static void delete(Long id) {
            Optional <Skill> skill  = Optional.of(Optional.ofNullable(entityManager.find(Skill.class, id)).orElseThrow());
            {
                entityManager.remove(skill);
            }

        }

    }
