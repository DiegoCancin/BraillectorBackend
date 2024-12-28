package com.braillector.persitence.repository;

import com.braillector.dto.response.IUsersRespProjection;
import com.braillector.persitence.entity.UsersEntity;
import com.braillector.persitence.entity.UsersTypeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface UsersRepository extends JpaRepository<UsersEntity, Long> {
    List<IUsersRespProjection> findAllByStatus(Integer status);
    IUsersRespProjection findByEmailAndPasswordAndStatus(String password, String email, Integer status);

    @Modifying
    @Query("update UsersEntity as ue set ue.typeEntity.id=:typeId where ue.id=:userId")
    Integer updateUser(
        @Param("userId")Long userId,
        @Param("typeId")Long typeId
    );
}
