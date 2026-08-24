package utils.mappers;

import models.profile.Profile;
import models.profile.create.AddProfileRequest;
import models.profile.login.LoginRequest;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ProfileToRequestMapper {
    ProfileToRequestMapper INSTANCE = Mappers.getMapper(ProfileToRequestMapper.class);

    AddProfileRequest profileToAddProfileRequestDto(Profile profile);
    LoginRequest profileToLoginRequestDto(Profile profile);
}
