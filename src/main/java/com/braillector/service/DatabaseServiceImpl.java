package com.braillector.service;

import com.braillector.dto.response.IUsersRespProjection;
import com.braillector.dto.response.IUsersTypeRespProjection;
import com.braillector.persitence.entity.UserConversionsEntity;
import com.braillector.persitence.entity.UsersEntity;
import com.braillector.persitence.entity.UsersTypeEntity;
import com.braillector.persitence.repository.UserConversionsRepository;
import com.braillector.persitence.repository.UsersRepository;
import com.braillector.persitence.repository.UsersTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.braillector.dto.request.UserRegisterDto;
import org.springframework.transaction.annotation.Transactional;


import java.time.LocalDate;
import java.util.List;

@Service
public class DatabaseServiceImpl implements IDatabaseService{

    private final int status_active = 1;
    private final long default_type = 1L;

    private final UsersTypeRepository usersTypeRepository;
    private final UsersRepository usersRepository;
    private final UserConversionsRepository conversionsRepository;

    @Autowired
    public DatabaseServiceImpl(
        UsersTypeRepository usersTypeRepository,
        UsersRepository usersRepository,
        UserConversionsRepository conversionsRepository
    ) {
        this.usersTypeRepository = usersTypeRepository;
        this.usersRepository = usersRepository;
        this.conversionsRepository = conversionsRepository;
    }

    @Override
    public List<IUsersTypeRespProjection> getUsersType() {
        return usersTypeRepository.getAllByStatusOrderByIdAsc(status_active);
    }

    @Override
    public List<IUsersRespProjection> getAllUsers() {
        return usersRepository.findAllByStatus(status_active);
    }

    @Override
    public IUsersRespProjection getUser(String email, String password) {
        return usersRepository.findByEmailAndPasswordAndStatus(email, password, status_active);
    }

    @Override
    public UsersEntity saveUser(UserRegisterDto userDto) {

        UsersTypeEntity type = usersTypeRepository.findById(default_type).orElse(null);

        if(type == null) {
            type = new UsersTypeEntity()
                .setId(1L)
                .setDescription("basic")
                .setStatus(status_active);
        }


        UsersEntity usersEntity = new UsersEntity()
            .setFirstName(userDto.getFirstName())
            .setPaLastName(userDto.getPaLastName())
            .setMaLastName(userDto.getMaLastName())
            .setEmail(userDto.getEmail())
            .setPassword(userDto.getPassword())
            .setStatus(status_active)
            .setTypeEntity(type);

        return usersRepository.save(usersEntity);
    }

    @Override
    public Long getUsersCount(Long userId) {
        return conversionsRepository.countByConversionDateAndUserId(LocalDate.now(),userId);
    }

    @Override
    public UserConversionsEntity saveConversion(Long userId) {
        UserConversionsEntity userConversionsEntity = new UserConversionsEntity()
            .setUserId(userId)
            .setConversionDate(LocalDate.now());
        return conversionsRepository.save(userConversionsEntity);
    }

    @Override
    @Transactional
    public IUsersRespProjection updateUserPlan(Long userId, Long typeId) {
        UsersEntity user = usersRepository.findById(userId)
            .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

       usersRepository.updateUser(user.getId(), typeId);

        return  usersRepository.findByEmailAndPasswordAndStatus(user.getEmail(), user.getPassword(), status_active);
    }
}
