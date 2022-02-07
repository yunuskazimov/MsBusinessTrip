package az.xazar.msbusinesstrip.mapper;

import az.xazar.msbusinesstrip.dao.entity.BusinessTripEntity;
import az.xazar.msbusinesstrip.model.BusinessTripDto;
import az.xazar.msbusinesstrip.model.BusinessTripGetDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper()
public interface BusinessTripMapper {

    BusinessTripMapper INSTANCE = Mappers.getMapper(BusinessTripMapper.class);

    @Mapping(target = "file", ignore = true)
    BusinessTripDto toBusinessTripDto(BusinessTripEntity entity);

    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    BusinessTripEntity toBusinessTripEntity(BusinessTripDto dto);

    @Mapping(target = "userName", ignore = true)
    BusinessTripGetDto toBusinessTripGetDto(BusinessTripEntity entity);
}
