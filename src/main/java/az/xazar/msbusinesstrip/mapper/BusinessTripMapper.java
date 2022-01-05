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

    @Mapping(target = "file", ignore = true)
    @Mapping(target = "isDeleted", source = "deleted")
    BusinessTripDto toBusinessTripDto(BusinessTripEntity entity);

    List<BusinessTripDto> toBusinessTripDtoList(List<BusinessTripEntity> entities);

    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
        //  @Mapping(target = "isDeleted", source = "deleted")
    BusinessTripEntity toBusinessTripEntity(BusinessTripDto dto);
}
