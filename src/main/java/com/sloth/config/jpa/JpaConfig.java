package com.sloth.config.jpa;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import javax.persistence.EntityManager;
import javax.persistence.metamodel.EntityType;
import java.util.*;

@Configuration
@EnableJpaAuditing
@RequiredArgsConstructor
public class JpaConfig {

    private final EntityManager entityManager;

    @Bean
    public AuditorAware<String> auditorProvider() {
        return new AuditorAwareImpl();
    }

    /**
     * 엔티티 이름 리스트 빈 등록
     * @return 엔티티 이름 리스트
     */
    @Bean
    public Map<String, String> entityMap(){
        Set<EntityType<?>> entities = entityManager.getMetamodel().getEntities();

        Map<String , String> entityMap = new HashMap<>();

        for (EntityType<?> entity : entities) {
            String entityName = entity.getName();
            String packageName = entity.getJavaType().getPackageName();
            entityMap.put(entityName.toLowerCase(), packageName + "." + entityName);
        }

        return entityMap;
    }

}
