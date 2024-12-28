package com.braillector.dto.response;

public interface IUsersRespProjection {
    Long getId();

    String getFirstName();

    String getPaLastName();

    String getMaLastName();

    String getEmail();

    IUsersTypeRespProjection getTypeEntity();
}
