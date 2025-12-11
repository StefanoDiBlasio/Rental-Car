package rental.car.project.prenotazione.mapper;

import org.springframework.stereotype.Component;
import rental.car.project.auto.domain.Auto;
import rental.car.project.prenotazione.domain.Prenotazione;
import rental.car.project.prenotazione.dto.PrenotazioneCreateDto;
import rental.car.project.prenotazione.dto.PrenotazioneDto;
import rental.car.project.prenotazione.dto.PrenotazioneUpdateDto;
import rental.car.project.user.domain.User;
import rental.car.project.utils.base.BaseMapper;

@Component
public class PrenotazioneMapper implements BaseMapper<Prenotazione, PrenotazioneDto> {

    @Override
    public PrenotazioneDto convertToDto(Prenotazione entity) {
        PrenotazioneDto dto = PrenotazioneDto.builder()
                .id(entity.getId())
                .userId(entity.getUser().getId())
                .autoId(entity.getAuto().getId())
                .inizioPrenotazione(entity.getInizioPrenotazione())
                .finePrenotazione(entity.getFinePrenotazione())
                .build();

        return dto;
    }

    @Override
    public Prenotazione convertToEntity(PrenotazioneDto prenotazioneDto) {
        return null;
    }


    public Prenotazione convertToResultEntity(PrenotazioneDto dto, User user, Auto auto) {
        Prenotazione entity = Prenotazione.builder()
                .id(dto.getId())
                .user(user)
                .auto(auto)
                .inizioPrenotazione(dto.getInizioPrenotazione())
                .finePrenotazione(dto.getFinePrenotazione())
                .build();

        return entity;
    }

    public Prenotazione convertToUpdateEntity(Prenotazione entity, PrenotazioneUpdateDto updateDto) {
        entity.setInizioPrenotazione(updateDto.getInizioPrenotazione());
        entity.setFinePrenotazione(updateDto.getFinePrenotazione());
        return entity;
    }

    public Prenotazione convertToCreateEntity(PrenotazioneCreateDto createDto, User user, Auto auto) {
        Prenotazione entity = Prenotazione.builder()
                .user(user)
                .auto(auto)
                .inizioPrenotazione(createDto.getInizioPrenotazione())
                .finePrenotazione(createDto.getFinePrenotazione())
                .build();
        return entity;
    }
}
