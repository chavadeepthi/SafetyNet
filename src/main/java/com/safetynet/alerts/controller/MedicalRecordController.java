package com.safetynet.alerts.controller;

import com.safetynet.alerts.model.MedicalRecord;
import com.safetynet.alerts.service.MedicalRecordService;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
public class MedicalRecordController {


    private final MedicalRecordService medicalRecordList;


    public MedicalRecordController(MedicalRecordService medicalRecordList){
        this.medicalRecordList = medicalRecordList;
    }

    @GetMapping("/medicalRecord")
    public List<MedicalRecord> getAllMedicalRecords(){

        log.info("Getting all Medical records");
        return medicalRecordList.getAllMedicalRecords();
    }

    @PostMapping("/medicalRecord")
    public List<MedicalRecord> addMedicalRecords(@RequestBody MedicalRecord newRecord){

        log.info("Add new Medical records");
        return medicalRecordList.addMedicalRecords(newRecord);
    }
    @PutMapping("/medicalRecord/{firstName}/{lastName}")
    public List<MedicalRecord> addMedicalRecords(@RequestBody MedicalRecord newRecord,
                                                 @PathVariable String firstName,
                                                 @PathVariable String lastName){

        log.info("Updating Medical record for ", firstName, lastName);
        return medicalRecordList.updateMedicalRecords(newRecord, firstName, lastName);
    }
    @DeleteMapping("/medicalRecord/{firstName}/{lastName}")
    public List<MedicalRecord> deleteMedicalRecords(
                                                 @PathVariable String firstName,
                                                 @PathVariable String lastName){

        log.info("Deleting Medical record for ", firstName, lastName);
        return medicalRecordList.deleteMedicalRecords( firstName, lastName);
    }
}
