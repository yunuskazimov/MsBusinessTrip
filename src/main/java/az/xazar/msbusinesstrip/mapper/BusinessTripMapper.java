package az.xazar.msbusinesstrip.mapper;

import az.xazar.msbusinesstrip.entity.BusinessTripEntity;
import az.xazar.msbusinesstrip.model.BusinessTripDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper()
public interface BusinessTripMapper {

    BusinessTripMapper INSTANCE = Mappers.getMapper(BusinessTripMapper.class);

    @Mapping(target = "isDeleted", source = "deleted")
    BusinessTripDto toDto(BusinessTripEntity entity);

    List<BusinessTripDto> toDtos(List<BusinessTripEntity> entities);

    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "isDeleted", source = "deleted")
    BusinessTripEntity toEntity(BusinessTripDto dto);
}