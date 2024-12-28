package com.braillector.service;

import com.braillector.dto.request.UserRegisterDto;
import com.braillector.dto.response.IUsersConvsRespProjection;
import com.braillector.dto.response.IUsersRespProjection;
import com.braillector.dto.response.IUsersTypeRespProjection;
import com.braillector.persitence.entity.UserConversionsEntity;
import com.braillector.persitence.entity.UsersEntity;

import java.util.List;

public interface IDatabaseService {
    List<IUsersTypeRespProjection> getUsersType();
    List<IUsersRespProjection> getAllUsers();
    IUsersRespProjection getUser(String email, String password);
    UsersEntity saveUser(UserRegisterDto userDto);
    Long getUsersCount(Long userId);
    UserConversionsEntity saveConversion(Long userId);
    IUsersRespProjection updateUserPlan(Long userId, Long typeId);
}
