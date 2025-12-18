package rental.car.project.auto.application;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import rental.car.project.auto.domain.Auto;
import rental.car.project.auto.dto.AutoCreateDto;
import rental.car.project.auto.dto.AutoDto;
import rental.car.project.auto.dto.AutoUpdateDto;
import rental.car.project.auto.infrastructure.AutoRepository;
import rental.car.project.auto.mapper.AutoMapper;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class AutoService {

    private static final Logger logger = LoggerFactory.getLogger(AutoService.class);

    @Autowired
    private AutoRepository autoRepository;

    @Autowired
    private ApplicationEventPublisher publisher;

    @Autowired
    private AutoMapper autoMapper;

    public AutoDto getAutoById(Long autoId) {
        logger.info("::AutoService.getAutoById (START)::");
        Auto auto = autoRepository.findById(autoId)
                .orElseThrow(() -> new NoSuchElementException("::Auto con Id: " + autoId + " non trovata!::"));
        logger.info("::GET_AUTO_BY_ID Auto trovata con id: " + autoId + " e Targa: " + auto.getTarga() + " ::");
        return autoMapper.convertToDto(auto);
    }

    public List<AutoDto> getAllAuto() {
        logger.info("::AutoService.getAllAuto (START)::");
        List<AutoDto> dtoList = new ArrayList<>();
        List<Auto> parcoAuto = autoRepository.findAll();
        for (Auto a : parcoAuto) {
            dtoList.add(autoMapper.convertToDto(a));
        }
        logger.info("::AutoService.getAllAuto:: (END)");
        return dtoList;
    }

    public AutoDto createAuto(AutoCreateDto createDto) {
        logger.info("::AutoService.createAuto:: (START)");
        if (autoRepository.findByTarga(createDto.getTarga()).isPresent()) {
            throw new IllegalArgumentException("::Targa già presente!::");
        }
        String targa = createDto.getTarga();
        if(targa != null) {
            createDto.setTarga(targa.trim().toUpperCase());
        }
        Auto auto = autoMapper.convertToCreateEntity(createDto);
        auto = autoRepository.save(auto);
        logger.info(":: Auto creata con successo! ::");
        publisher.publishEvent(new AutoCreatedEvent(
                auto.getId(),
                auto.getCasaCostruttrice(),
                auto.getModello(),
                auto.getAnnoImmatricolazione(),
                auto.getTarga(),
                auto.getAutoType()));
        logger.info("::AutoService.createAuto:: (END)");

        return autoMapper.convertToDto(auto);
    }

    public AutoDto updateAuto(Long autoId, AutoUpdateDto updateDto) {
        logger.info("::AutoService.update (START)::");
        Auto existingAuto = autoRepository.findById(autoId)
                .orElseThrow(() -> new NoSuchElementException("::Nessuna auto trovata!::"));

        if(updateDto.getTarga() == null || updateDto.getTarga().trim().isEmpty()) {
            logger.error("::ERRORE:: Nessun campo da aggiornare!");
            throw new IllegalArgumentException("Non ci sono campi da aggiornare!");
        } else {
            updateDto.setTarga(updateDto.getTarga().trim().toUpperCase());
        }

        existingAuto = autoMapper.convertToUpdateEntity(existingAuto, updateDto);
        existingAuto = autoRepository.save(existingAuto);
        publisher.publishEvent(new AutoUpdatedEvent(
                existingAuto.getId(),
                existingAuto.getCasaCostruttrice(),
                existingAuto.getModello(),
                existingAuto.getAnnoImmatricolazione(),
                existingAuto.getTarga(),
                existingAuto.getAutoType()
        ));
        logger.info("::UPDATE_AUTO Auto con id: " + autoId + " aggiornata con successo!::");
        return autoMapper.convertToDto(existingAuto);
    }

    public void deleteAuto(Long autoId) {
        if(autoRepository.existsById(autoId)) {
            autoRepository.deleteById(autoId);
        }
        publisher.publishEvent(new AutoDeletedEvent(
                autoId
        ));
        logger.info("::DELETE_AUTO Auto con id: " + autoId + " eliminata con successo!::");
    }
}
