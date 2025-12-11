package rental.car.project.prenotazione.application;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import rental.car.project.auto.domain.Auto;
import rental.car.project.auto.infrastructure.AutoRepository;
import rental.car.project.prenotazione.domain.Prenotazione;
import rental.car.project.prenotazione.dto.PrenotazioneCreateDto;
import rental.car.project.prenotazione.dto.PrenotazioneDto;
import rental.car.project.prenotazione.dto.PrenotazioneUpdateDto;
import rental.car.project.prenotazione.infrastructure.PrenotazioneRepository;
import rental.car.project.prenotazione.mapper.PrenotazioneMapper;
import rental.car.project.user.domain.User;
import rental.car.project.user.infrastructure.UserRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class PrenotazioneService {

    private static final Logger logger = LoggerFactory.getLogger(PrenotazioneService.class);

    @Autowired
    private PrenotazioneRepository prenotazioneRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AutoRepository autoRepository;

    @Autowired
    private ApplicationEventPublisher publisher;

    @Autowired
    private PrenotazioneMapper prenotazioneMapper;

    public PrenotazioneDto getPrenotazioneById(Long prenotazioneId) {
        logger.info("::PrenotazioneService.getPrenotazioneById (START)::");
        Prenotazione prenotazione = prenotazioneRepository.findById(prenotazioneId)
                .orElseThrow(() -> new NoSuchElementException("::Prenotazione con Id: " + prenotazioneId + " non trovata!::"));
        logger.info("::GET_PRENOTAZIONE_BY_ID Prenotazione trovata con id: " + prenotazioneId + " ::");
        return prenotazioneMapper.convertToDto(prenotazione);
    }

    public List<PrenotazioneDto> getAllPrenotazioni() {
        logger.info("::PrenotazioneService.getAllPrenotazioni (START)::");
        List<PrenotazioneDto> dtoList = new ArrayList<>();
        List<Prenotazione> prenotazioni = prenotazioneRepository.findAll();
        for (Prenotazione p : prenotazioni) {
            dtoList.add(prenotazioneMapper.convertToDto(p));
        }
        return dtoList;
    }

    public PrenotazioneDto createPrenotazione(PrenotazioneCreateDto createDto) {
        logger.info("::PrenotazioneService.createPrenotazione:: (START)");

        User user = userRepository.findById(createDto.getUserId())
                .orElseThrow(() -> new NoSuchElementException("::User con Id: " + createDto.getUserId() + " non trovato!::"));

        Auto auto = autoRepository.findById(createDto.getAutoId())
                .orElseThrow(() -> new NoSuchElementException("::Auto con Id: " + createDto.getAutoId() + " non trovata!::"));

        Prenotazione prenotazione = prenotazioneMapper.convertToCreateEntity(createDto, user, auto);
        prenotazioneRepository.save(prenotazione);
        logger.info(":: Prenotazione creata con successo! ::");
        publisher.publishEvent(new PrenotazioneCreatedEvent(
                prenotazione.getId(),
                prenotazione.getUser().getId(),
                prenotazione.getAuto().getId(),
                prenotazione.getInizioPrenotazione(),
                prenotazione.getFinePrenotazione()));
        logger.info("::AutoService.createAuto:: (END)");

        return prenotazioneMapper.convertToDto(prenotazione);
    }

    public PrenotazioneDto updatePrenotazione(Long prenotazioneId, PrenotazioneUpdateDto updateDto) {
        logger.info("::PrenotazioneService.updatePrenotazione (START)::");
        Prenotazione existingPrenotazione = prenotazioneRepository.findById(prenotazioneId)
                .orElseThrow(() -> new NoSuchElementException("::Nessuna prenotazione trovata!::"));

        if(updateDto.getInizioPrenotazione() == null && updateDto.getFinePrenotazione() == null) {
            logger.error("::ERRORE:: Nessun campo da aggiornare!");
            throw new IllegalArgumentException("Non ci sono campi da aggiornare!");
        }

        existingPrenotazione = prenotazioneMapper.convertToUpdateEntity(existingPrenotazione, updateDto);
        existingPrenotazione = prenotazioneRepository.save(existingPrenotazione);
        publisher.publishEvent(new PrenotazioneUpdatedEvent(
                existingPrenotazione.getId(),
                existingPrenotazione.getInizioPrenotazione(),
                existingPrenotazione.getFinePrenotazione()
        ));
        logger.info("::UPDATE_PRENOTAZIONE La prenotazione con id: " + prenotazioneId + " è stata aggiornata con successo!::");
        return prenotazioneMapper.convertToDto(existingPrenotazione);
    }

    public void deletePrenotazione(Long prenotazioneId) {
        if (prenotazioneRepository.existsById(prenotazioneId)) {
            prenotazioneRepository.deleteById(prenotazioneId);
        }
        publisher.publishEvent(new PrenotazioneDeletedEvent(
                prenotazioneId
        ));
        logger.info("::DELETE_PRENOTAZIONE La prenotazione con id: " + prenotazioneId + " è stata eliminata con successo!::");
    }

}
