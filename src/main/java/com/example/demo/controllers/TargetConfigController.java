//Controller Klasse für die TargetConfigEntity, ermöglicht Datenmanipulation der TargetConfig Tabelle in der DB
package com.example.demo.controllers;

import com.example.demo.entities.TargetConfigEntity;
import com.example.demo.exceptions.TargetIDBadRequestException;
import com.example.demo.exceptions.TargetIDNotFoundException;
import com.example.demo.repositories.TargetConfigRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*")
@RestController
public class TargetConfigController {

    private final TargetConfigRepository repository;

    public TargetConfigController(TargetConfigRepository repository) {
        this.repository = repository;
    }

    //Gibt alle Targetconfigs zurück
    @GetMapping("/targetconfig")
    List<TargetConfigEntity> all() {
        return repository.findAll();
    }

    //Gibt eine Targetconfigs anhand der eingegebenen ID zurück
    @GetMapping("/targetconfig/id/{id}")
    TargetConfigEntity withId(@PathVariable int id) {
        return repository.findById(id).orElseThrow(() -> new TargetIDNotFoundException("No TargetConfig with ID: "+id));
    }

    //Löscht alle Targetconfigs
    @DeleteMapping("/targetconfig")
    void deleteAllConfigs() {
        repository.deleteAll();
    }

    /**
     * Service Methode zum Anlegen von neuen Targetconfigs
     * die einzelnen Objektvariablen können hierbei entweder den Wert 1 für True oder 2 für False haben
     * @param targetConfigEntity Targetconfig Objekt welches als Datensatz eingefügt werden soll
     * @return gespeichertes Targetconfig Objekt
     */

    @PostMapping("/targetconfig")
    TargetConfigEntity newTargetConfigEntity(@RequestBody TargetConfigEntity targetConfigEntity) {

        if (targetConfigEntity.getCheckEmployeeId() != 2 &&  targetConfigEntity.getCheckEmployeeId() != 1) {
            throw new TargetIDBadRequestException("CheckEmployeeID can either be be 1 or 2");
        }

        if (targetConfigEntity.getCheckLastName() != 2 &&  targetConfigEntity.getCheckLastName() != 1) {
            throw new TargetIDBadRequestException("CheckLastName can either be  1 or 2");
        }
        if (targetConfigEntity.getCheckFirstName() != 2 &&  targetConfigEntity.getCheckFirstName()  != 1) {
            throw new TargetIDBadRequestException("CheckFirstName can either be 1 or 2");
        }
        if (targetConfigEntity.getCheckLoginName() != 2 &&  targetConfigEntity.getCheckLoginName()  != 1) {
            throw new TargetIDBadRequestException("CheckLoginName can either be 1 or 2");
        }
        if (targetConfigEntity.getCheck_password() != 2 &&  targetConfigEntity.getCheck_password()  != 1) {
            throw new TargetIDBadRequestException("CheckPassword can either be 1 or 2");
        }
        if (targetConfigEntity.getCheckStartDate() != 2 &&  targetConfigEntity.getCheckStartDate()  != 1) {
            throw new TargetIDBadRequestException("CheckStartDate can either be 1 or 2");
        }
        if (targetConfigEntity.getCheckEndDate() != 2 &&  targetConfigEntity.getCheckEndDate()  != 1) {
            throw new TargetIDBadRequestException("CheckEnddate can either be 1 or 2");
        }
        if (targetConfigEntity.getCheckSVNR() != 2 &&  targetConfigEntity.getCheckSVNR()  != 1) {
            throw new TargetIDBadRequestException("CheckSVNR can either be 1 or 2");
        }

        return repository.save(targetConfigEntity);
    }

    /**
     * Service Methode zum Bearbeiten von bestehenden Targetconfigs
     * Die einzelnen Objektvariablen können hierbei entweder den Wert 1 für True oder 2 für False haben
     * Hierbei musste der Wert 2 für False vergeben werden, da in JSON Objekten nicht angegebene Variablen den Wert 0 haben
     * würde man also mit den Werten 0 und 1 arbeiten, so würden z. B, bei der Änderung des CheckEmployeeID Feldes sämtliche
     * nicht angegebene Felder auf 0 gesetzt werden
     *
     * @param id: Die ID der zu änderenden TargetConfig
     * @param newTargetConfig: Targetconfig Objekt, Variablen Werte dieses Objekts werden für die Änderungen an der gewünschten TargetConfig verwendet
     * @return verändertes TargetConfig Objekt
     */
    @PatchMapping("/targetconfig/{id}")
    public TargetConfigEntity patchTargetConfig(@PathVariable int id, @RequestBody TargetConfigEntity newTargetConfig) {
        TargetConfigEntity configToUpdate = repository.findById(id).orElseThrow(() -> new TargetIDNotFoundException("Could not find target config with ID: " +id));

            if (newTargetConfig.getCheckEmployeeId() == 1 || newTargetConfig.getCheckEmployeeId() == 2) {
                configToUpdate.setCheckEmployeeId(newTargetConfig.getCheckEmployeeId());
            }

            if (newTargetConfig.getCheckFirstName() == 1 || newTargetConfig.getCheckFirstName() == 2) {
                configToUpdate.setCheckFirstName(newTargetConfig.getCheckFirstName());
            }
            if (newTargetConfig.getCheckLastName() == 2 || newTargetConfig.getCheckLastName() == 1) {
                configToUpdate.setCheckLastName(newTargetConfig.getCheckLastName());
            }
            if (newTargetConfig.getCheckLoginName() == 2 || newTargetConfig.getCheckLoginName() == 1) {
                configToUpdate.setCheckLoginName(newTargetConfig.getCheckLoginName());
            }
            if (newTargetConfig.getCheck_password() == 2 || newTargetConfig.getCheck_password() == 1) {
                configToUpdate.setCheck_password(newTargetConfig.getCheck_password());
            }
            if (newTargetConfig.getCheckStartDate() == 2 || newTargetConfig.getCheckStartDate() == 1) {
                configToUpdate.setCheckStartDate(newTargetConfig.getCheckStartDate());
            }
           if (newTargetConfig.getCheckEndDate() == 2 || newTargetConfig.getCheckEndDate() == 1) {
               configToUpdate.setCheckEndDate(newTargetConfig.getCheckEndDate());
           }
            if (newTargetConfig.getCheckSVNR() == 2 || newTargetConfig.getCheckSVNR() == 1) {
                configToUpdate.setCheckSVNR(newTargetConfig.getCheckSVNR());
            }
            if(newTargetConfig.getDescription() != null && !newTargetConfig.getDescription().isBlank()) {
                configToUpdate.setDescription(newTargetConfig.getDescription());
            }

        repository.save(configToUpdate);
        return configToUpdate;
    }

    //Service Methode zum Löschen von Targetconfigs
    @DeleteMapping("/targetconfig/delete/{id}")
    void deleteTargetConfig(@PathVariable int id) {
        repository.deleteById(id);
    }

}
