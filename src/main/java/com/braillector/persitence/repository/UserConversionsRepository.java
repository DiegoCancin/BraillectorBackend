package com.braillector.persitence.repository;

import com.braillector.persitence.entity.UserConversionsEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;

public interface UserConversionsRepository extends JpaRepository<UserConversionsEntity, Long> {
    Long countByConversionDateAndUserId(LocalDate date, Long userId);
}
