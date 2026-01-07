package rental.car.project.prenotazione.application;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import rental.car.project.auto.domain.Auto;
import rental.car.project.auto.infrastructure.AutoRepository;
import rental.car.project.prenotazione.domain.Prenotazione;
import rental.car.project.prenotazione.domain.StatusPrenotazione;
import rental.car.project.prenotazione.dto.PrenotazioneCreateDto;
import rental.car.project.prenotazione.dto.PrenotazioneDto;
import rental.car.project.prenotazione.dto.PrenotazioneUpdateDto;
import rental.car.project.prenotazione.infrastructure.PrenotazioneRepository;
import rental.car.project.prenotazione.mapper.PrenotazioneMapper;
import rental.car.project.prenotazione.specification.PrenotazioneSpecification;
import rental.car.project.user.domain.User;
import rental.car.project.user.infrastructure.UserRepository;

import java.time.LocalDate;
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

    public List<PrenotazioneDto> getAllPrenotazioniByUserId(Long userId) {
        logger.info("::PrenotazioneService.getAllPrenotazioniById (START)::");
        List<Prenotazione> listaPrenotazioni = prenotazioneRepository.findAllPrenotazioniByUserId(userId);
        List<PrenotazioneDto> listaPrenotazioniDto = new ArrayList<>();
        for (Prenotazione prenotazione : listaPrenotazioni) {
            PrenotazioneDto dto = PrenotazioneDto.builder()
                    .id(prenotazione.getId())
                    .userId(userId)
                    .username(prenotazione.getUser().getUsername())
                    .firstName(prenotazione.getUser().getFirstName())
                    .lastName(prenotazione.getUser().getLastName())
                    .autoId(prenotazione.getAuto().getId())
                    .casaCostruttrice(prenotazione.getAuto().getCasaCostruttrice())
                    .modello(prenotazione.getAuto().getModello())
                    .annoImmatricolazione(prenotazione.getAuto().getAnnoImmatricolazione())
                    .targa(prenotazione.getAuto().getTarga())
                    .autoType(prenotazione.getAuto().getAutoType())
                    .inizioPrenotazione(prenotazione.getInizioPrenotazione())
                    .finePrenotazione(prenotazione.getFinePrenotazione())
                    .status(prenotazione.getStatus())
                    .build();
            listaPrenotazioniDto.add(dto);
        }
        logger.info("::GET_ALL_PRENOTAZIONI_BY_ID Numero prenotazioni trovate associate all'utente con id: "
                + userId + ": " + listaPrenotazioniDto.stream().count() + " ::");
        return listaPrenotazioniDto;
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

    public List<PrenotazioneDto> searchFilteredPrenotazioni(
            Long id,
            String firstName,
            String lastName,
            String casaCostruttrice,
            String modello,
            String targa,
            LocalDate inizioPrenotazione,
            LocalDate finePrenotazione,
            StatusPrenotazione status
    ) {
        logger.info("::PrenotazioneService.searchFilteredPrenotazioni (START)::");
        Specification<Prenotazione> spec =
                PrenotazioneSpecification.withFilters(
                        id,
                        firstName,
                        lastName,
                        casaCostruttrice,
                        modello,
                        targa,
                        inizioPrenotazione,
                        finePrenotazione,
                        status
                );
        List<Prenotazione> prenotazioniFiltrate = prenotazioneRepository.findAll(spec);
        List<PrenotazioneDto> listaPrenotazioniDto = new ArrayList<>();

        for (Prenotazione prenotazione : prenotazioniFiltrate) {
            PrenotazioneDto dto = PrenotazioneDto.builder()
                    .id(prenotazione.getId())
                    .userId(prenotazione.getUser().getId())
                    .autoId(prenotazione.getAuto().getId())
                    .casaCostruttrice(prenotazione.getAuto().getCasaCostruttrice())
                    .modello(prenotazione.getAuto().getModello())
                    .annoImmatricolazione(prenotazione.getAuto().getAnnoImmatricolazione())
                    .targa(prenotazione.getAuto().getTarga())
                    .autoType(prenotazione.getAuto().getAutoType())
                    .inizioPrenotazione(prenotazione.getInizioPrenotazione())
                    .finePrenotazione(prenotazione.getFinePrenotazione())
                    .status(prenotazione.getStatus())
                    .build();
            listaPrenotazioniDto.add(dto);
        }
        logger.info("::PrenotazioneService.searchFilteredPrenotazioni (END)::");

        return listaPrenotazioniDto;
    }

    public PrenotazioneDto createPrenotazione(PrenotazioneCreateDto createDto) {
        logger.info("::PrenotazioneService.createPrenotazione:: (START)");

        User user = userRepository.findById(createDto.getUserId())
                .orElseThrow(() -> new NoSuchElementException("::User con Id: " + createDto.getUserId() + " non trovato!::"));

        Auto auto = autoRepository.findById(createDto.getAutoId())
                .orElseThrow(() -> new NoSuchElementException("::Auto con Id: " + createDto.getAutoId() + " non trovata!::"));

        Prenotazione prenotazione = prenotazioneMapper.convertToCreateEntity(createDto, user, auto);
        prenotazione.setStatus(StatusPrenotazione.IN_ATTESA);
        prenotazioneRepository.save(prenotazione);
        logger.info(":: Prenotazione creata con successo! ::");
        publisher.publishEvent(new PrenotazioneCreatedEvent(
                prenotazione.getId(),
                prenotazione.getUser().getId(),
                prenotazione.getUser().getUsername(),
                prenotazione.getUser().getFirstName(),
                prenotazione.getUser().getLastName(),
                prenotazione.getAuto().getId(),
                prenotazione.getAuto().getCasaCostruttrice(),
                prenotazione.getAuto().getModello(),
                prenotazione.getAuto().getAnnoImmatricolazione(),
                prenotazione.getAuto().getTarga(),
                prenotazione.getAuto().getAutoType().name(),
                prenotazione.getInizioPrenotazione(),
                prenotazione.getFinePrenotazione(),
                prenotazione.getStatus()));
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
                existingPrenotazione.getFinePrenotazione(),
                existingPrenotazione.getStatus()
        ));
        logger.info("::UPDATE_PRENOTAZIONE La prenotazione con id: " + prenotazioneId + " è stata aggiornata con successo!::");
        return prenotazioneMapper.convertToDto(existingPrenotazione);
    }

    public PrenotazioneDto approvaPrenotazione(Long prenotazioneId) {
        logger.info("::PrenotazioneService.approvaPrenotazione (START)::");
        Prenotazione existingPrenotazione = prenotazioneRepository.findById(prenotazioneId)
                .orElseThrow(() -> new NoSuchElementException("::Nessuna prenotazione trovata!::"));

        if(existingPrenotazione.getStatus() != StatusPrenotazione.IN_ATTESA) {
            throw new IllegalArgumentException("Non è possibile approvare una prenotazione nello stato " + existingPrenotazione.getStatus());
        }
        existingPrenotazione.setStatus(StatusPrenotazione.APPROVATA);
        prenotazioneRepository.save(existingPrenotazione);
        logger.info("::PrenotazioneService.approvaPrenotazione (END)::");
        return prenotazioneMapper.convertToDto(existingPrenotazione);
    }

    public PrenotazioneDto rifiutaPrenotazione(Long prenotazioneId) {
        logger.info("::PrenotazioneService.rifiutaPrenotazione (START)::");
        Prenotazione existingPrenotazione = prenotazioneRepository.findById(prenotazioneId)
                .orElseThrow(() -> new NoSuchElementException("::Nessuna prenotazione trovata!::"));

        if(existingPrenotazione.getStatus() != StatusPrenotazione.IN_ATTESA) {
            throw new IllegalArgumentException("Non è possibile rifiutare una prenotazione nello stato " + existingPrenotazione.getStatus());
        }
        existingPrenotazione.setStatus(StatusPrenotazione.RIFIUTATA);
        prenotazioneRepository.save(existingPrenotazione);
        logger.info("::PrenotazioneService.rifiutaPrenotazione (END)::");
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

    public StatusPrenotazione parseStatus(String value) {
        if (value == null || value.isBlank() || "null".equalsIgnoreCase(value)) {
            return null;
        }
        try {
            return StatusPrenotazione.valueOf(value);
        } catch (IllegalArgumentException ex) {
            throw new IllegalArgumentException("Errore nel Parsin dello status!");
        }
    }

}
