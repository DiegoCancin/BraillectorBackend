package com.braillector.persitence.repository;

import com.braillector.dto.response.IUsersRespProjection;
import com.braillector.dto.response.IUsersTypeRespProjection;
import com.braillector.persitence.entity.UsersTypeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface UsersTypeRepository extends JpaRepository<UsersTypeEntity, Long> {

    List<IUsersTypeRespProjection> getAllByStatusOrderByIdAsc(Integer status);
}
