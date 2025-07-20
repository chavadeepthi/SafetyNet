package com.safetynet.alerts.controller;

import com.safetynet.alerts.model.MedicalRecord;
import com.safetynet.alerts.service.MedicalRecordService;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<String> updateMedicalRecords(@RequestBody MedicalRecord newRecord,
                                            @PathVariable String firstName,
                                            @PathVariable String lastName){

        log.info("Updating Medical record for ", firstName, lastName);
        boolean record_updated =  medicalRecordList.updateMedicalRecords(newRecord, firstName, lastName);
        if(record_updated)
        {
            return ResponseEntity.ok("Medical Record Updation successfully.");
    } else {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(firstName + lastName + " not found: " );
    }
    }
    @DeleteMapping("/medicalRecord/{firstName}/{lastName}")
    public ResponseEntity<String> deleteMedicalRecords(
                                                 @PathVariable String firstName,
                                                 @PathVariable String lastName){

        log.info("Deleting Medical record for ", firstName, lastName);
        boolean record_deleted =  medicalRecordList.deleteMedicalRecords( firstName, lastName);
        if(record_deleted)
        {
            return ResponseEntity.ok("Medical Record Updation successfully.");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(firstName + lastName + " not found: " );
        }
    }
}
