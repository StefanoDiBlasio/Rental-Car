package rental.car.project.prenotazione.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import rental.car.project.prenotazione.application.PrenotazioneService;
import rental.car.project.prenotazione.dto.PrenotazioneCreateDto;
import rental.car.project.prenotazione.dto.PrenotazioneDto;
import rental.car.project.prenotazione.dto.PrenotazioneUpdateDto;

import java.util.List;

@RestController
@RequestMapping("/api/v1/prenotazione")
public class PrenotazioneController {

    private static final Logger logger = LoggerFactory.getLogger(PrenotazioneController.class);

    @Autowired
    private PrenotazioneService prenotazioneService;

    @PreAuthorize("hasRole('SUPERADMIN')")
    @GetMapping(path = "/all")
    public ResponseEntity<List<PrenotazioneDto>> getAll() {
        logger.info("::PrenotazioneController.getAll (START)::");
        List<PrenotazioneDto> prenotazioni = prenotazioneService.getAllPrenotazioni();
        logger.info("::PrenotazioneoController.getAll (END)::");
        return ResponseEntity.ok().body(prenotazioni);
    }

    @PreAuthorize("hasRole('SUPERADMIN')")
    @GetMapping(path = "/{prenotazioneId}")
    public ResponseEntity<PrenotazioneDto> get(@PathVariable(value = "prenotazioneId") Long prenotazioneId) {
        logger.info("::PrenotazioneController.get (START)::");
        PrenotazioneDto prenotazioneDto = prenotazioneService.getPrenotazioneById(prenotazioneId);
        logger.info("::PrenotazioneController.get (END)::");
        return ResponseEntity.ok().body(prenotazioneDto);
    }

    @PreAuthorize("hasRole('SUPERADMIN')")
    @PostMapping(value = "/add")
    public ResponseEntity<PrenotazioneDto> addPrenotazione(@RequestBody PrenotazioneCreateDto prenotazioneCreateDto) {
        logger.info("::PrenotazioneController.addPrenotazione (START)::");
        PrenotazioneDto prenotazioneDto = prenotazioneService.createPrenotazione(prenotazioneCreateDto);
        logger.info("::Nuova prenotazione aggiunta! Data prenotazione: " + prenotazioneCreateDto.getInizioPrenotazione() + " - " + prenotazioneCreateDto.getFinePrenotazione() + ". Utente: " + prenotazioneCreateDto.getUserId() + ", Auto: " + prenotazioneCreateDto.getAutoId() + " ::");
        logger.info("::PrenotazioneController.addPrenotazione (END)::");
        return ResponseEntity.ok().body(prenotazioneDto);
    }

    @PreAuthorize("hasRole('SUPERADMIN')")
    @PutMapping(path = "/{prenotazioneId}")
    public ResponseEntity<PrenotazioneDto> update(@PathVariable(value = "prenotazioneId") Long prenotazioneId, @RequestBody PrenotazioneUpdateDto updateDto) {
        logger.info("::PrenotazioneController.update (START)::");
        PrenotazioneDto prenotazioneDto = prenotazioneService.updatePrenotazione(prenotazioneId, updateDto);
        logger.info("::Data di prenotazione aggiornata: " + updateDto.getInizioPrenotazione() + " - " + updateDto.getFinePrenotazione() + " ::");
        logger.info("::PrenotazioneController.update (END)::");
        return ResponseEntity.ok().body(prenotazioneDto);
    }

    @PreAuthorize("hasRole('SUPERADMIN')")
    @DeleteMapping(path = "{prenotazioneId}")
    public ResponseEntity<?> delete(@PathVariable(value = "prenotazioneId") Long prenotazioneId) {
        logger.info("::PrenotazioneController.delete (START)::");
        prenotazioneService.deletePrenotazione(prenotazioneId);
        return ResponseEntity.noContent().build();
    }
}
