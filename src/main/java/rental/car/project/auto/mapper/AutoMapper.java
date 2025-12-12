package rental.car.project.auto.mapper;

import org.springframework.stereotype.Component;
import rental.car.project.auto.domain.Auto;
import rental.car.project.auto.dto.AutoCreateDto;
import rental.car.project.auto.dto.AutoDto;
import rental.car.project.auto.dto.AutoUpdateDto;
import rental.car.project.common.base.BaseMapper;

@Component
public class AutoMapper implements BaseMapper<Auto, AutoDto> {

    @Override
    public AutoDto convertToDto(Auto entity) {
        AutoDto dto = AutoDto.builder()
                .id(entity.getId())
                .casaCostruttrice(entity.getCasaCostruttrice())
                .modello(entity.getModello())
                .annoImmatricolazione(entity.getAnnoImmatricolazione())
                .targa(entity.getTarga())
                .autoType(entity.getAutoType())
                .build();

        return dto;
    }

    @Override
    public Auto convertToEntity(AutoDto dto) {
        Auto entity = Auto.builder()
                .id(dto.getId())
                .casaCostruttrice(dto.getCasaCostruttrice())
                .modello(dto.getModello())
                .annoImmatricolazione(dto.getAnnoImmatricolazione())
                .targa(dto.getTarga())
                .autoType(dto.getAutoType())
                .build();

        return entity;
    }

    public Auto convertToUpdateEntity(Auto entity, AutoUpdateDto updateDto) {
        entity.setTarga(updateDto.getTarga());
        return entity;
    }

    public Auto convertToCreateEntity(AutoCreateDto createDto) {
        Auto entity = Auto.builder()
                .casaCostruttrice(createDto.getCasaCostruttrice())
                .modello(createDto.getModello())
                .annoImmatricolazione(createDto.getAnnoImmatricolazione())
                .targa(createDto.getTarga())
                .autoType(createDto.getAutoType())
                .build();
        return entity;
    }
}
