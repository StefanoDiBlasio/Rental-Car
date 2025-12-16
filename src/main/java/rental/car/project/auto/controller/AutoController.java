package rental.car.project.auto.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import rental.car.project.auto.application.AutoService;
import rental.car.project.auto.dto.AutoCreateDto;
import rental.car.project.auto.dto.AutoDto;
import rental.car.project.auto.dto.AutoUpdateDto;

import java.util.List;

@RestController
@RequestMapping("/api/v1/auto")
@CrossOrigin("*")
public class AutoController {

    private static final Logger logger = LoggerFactory.getLogger(AutoController.class);

    @Autowired
    private AutoService autoService;

    @GetMapping(path = "/all")
    public ResponseEntity<List<AutoDto>> getAll() {
        logger.info("::AutoController.getAll (START)::");
        List<AutoDto> auto = autoService.getAllAuto();
        logger.info("::AutoController.getAll (END)::");
        return ResponseEntity.ok().body(auto);
    }

    @PreAuthorize("hasRole('SUPERADMIN')")
    @GetMapping(path = "/{autoId}")
    public ResponseEntity<AutoDto> get(@PathVariable(value = "autoId") Long autoId) {
        logger.info("::AutoController.get (START)::");
        AutoDto auto = autoService.getAutoById(autoId);
        logger.info("::AutoController.get (END)::");
        return ResponseEntity.ok().body(auto);
    }

    @PreAuthorize("hasRole('SUPERADMIN')")
    @PostMapping(value = "/add")
    public ResponseEntity<AutoDto> addAuto(@RequestBody AutoCreateDto autoCreateDto) {
        logger.info("::AutoController.addAuto (START)::");
        AutoDto dto = autoService.createAuto(autoCreateDto);
        logger.info("::Nuova auto aggiunta: " + autoCreateDto.getCasaCostruttrice() + " " + autoCreateDto.getModello() + " con Targa: " + autoCreateDto.getTarga() + " ::");
        logger.info("::AutoController.addAuto (END)::");
        return ResponseEntity.ok().body(dto);
    }

    @PreAuthorize("hasRole('SUPERADMIN')")
    @PutMapping(path = "/{autoId}")
    public ResponseEntity<AutoDto> update(@PathVariable(value = "autoId") Long autoId, @RequestBody AutoUpdateDto updateDto) {
        logger.info("::AutoController.update (START)::");
        AutoDto autoDto = autoService.updateAuto(autoId, updateDto);
        logger.info("::Aggiornamento dell'auto con la nuova targa: " + updateDto.getTarga() + " ::");
        logger.info("::AutoController.update (END)::");
        return ResponseEntity.ok().body(autoDto);
    }

    @PreAuthorize("hasRole('SUPERADMIN')")
    @DeleteMapping(path = "{autoId}")
    public ResponseEntity<?> delete(@PathVariable(value = "autoId") Long autoId) {
        logger.info("::AutoController.delete (START)::");
        autoService.deleteAuto(autoId);
        return ResponseEntity.noContent().build();
    }
}
