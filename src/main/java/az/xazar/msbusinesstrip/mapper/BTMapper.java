package az.xazar.msbusinesstrip.mapper;

import az.xazar.msbusinesstrip.entity.BTEntity;
import az.xazar.msbusinesstrip.model.BTDto;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.FIELD)
public interface BTMapper {
    BTMapper INSTANCE = Mappers.getMapper(BTMapper.class);

    @Mapping(target = "isDeleted", source = "deleted")
    BTDto toDto(BTEntity entity);

    List<BTDto> toDtos(List<BTEntity> entities);

    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "isDeleted", source = "deleted")
    BTEntity toEntity(BTDto dto);
}
