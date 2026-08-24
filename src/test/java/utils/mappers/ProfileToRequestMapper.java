package utils.mappers;

import models.profile.Profile;
import models.profile.create.AddProfileRequest;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ProfileToRequestMapper {
    ProfileToRequestMapper INSTANCE = Mappers.getMapper(ProfileToRequestMapper.class);

    AddProfileRequest profileToAddProfileRequestDto(Profile profile);
}
