package org.healtheta.web;


import org.healtheta.model.common.Identifier;
import org.healtheta.model.common.OperationOutcome;
import org.healtheta.model.common.Reference;
import org.healtheta.model.common.repos.IdentifierRepo;
import org.healtheta.model.common.repos.ReferenceRepo;
import org.healtheta.model.schedule.Schedule;
import org.healtheta.model.schedule.repo.ScheduleRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;

@RestController
public class ScheduleController {
    @Autowired
    ScheduleRepo scheduleRepo;
    @Autowired
    private IdentifierRepo identifierRepo;
    @Autowired
    private ReferenceRepo referenceRepo;

    @RequestMapping(value = "/create", method = RequestMethod.POST, produces = "application/json", consumes = "application/json")
    @ResponseBody
    public ResponseEntity<?> create(@RequestBody Schedule schedule){
        Identifier tmpId = schedule.getIdentifier();
        if(tmpId.getValue() == null){
            return new ResponseEntity<OperationOutcome>(OperationOutcome.InvalidParameter(),
                    HttpStatus.OK);
        }

        if(identifierRepo.findIdentifierByValue(tmpId.getValue()) != null){
            return new ResponseEntity<OperationOutcome>(OperationOutcome.RecordExists(),
                    HttpStatus.OK);
        }

        try{
            Schedule tmp = new Schedule();
            tmp = scheduleRepo.save(tmp);
            schedule.setId(tmp.getId());

            Reference ref = new Reference();
            ref.setIdentifier(schedule.getIdentifier());
            ref.setDisplay("schedule-reference");
            schedule.setReference(ref);
            schedule = scheduleRepo.save(schedule);
            return new ResponseEntity<Schedule>(schedule, HttpStatus.OK);
        }catch(Exception e){
            return new ResponseEntity<OperationOutcome>(OperationOutcome.InternalError(),
                    HttpStatus.OK);
        }
    }

    @RequestMapping(value = "/read/{id}", method = RequestMethod.GET, produces = "application/json")
    @ResponseBody
    public ResponseEntity<?> read(@PathVariable String id){
        try{
            Long lId = Long.parseLong(id);
            Schedule schedule = scheduleRepo.findScheduleById(lId);
            if(schedule != null){
                return new ResponseEntity<Schedule>(schedule, HttpStatus.OK);
            }else{
                return new ResponseEntity<OperationOutcome>(OperationOutcome.RecordNotFound(), HttpStatus.NOT_FOUND);
            }
        }catch(Exception e){
            return new ResponseEntity<OperationOutcome>(OperationOutcome.InternalError(),
                    HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST, produces = "application/json", consumes = "application/json")
    @ResponseBody
    public ResponseEntity<?> update(@RequestBody Schedule schedule){
        Long id = schedule.getId();
        Schedule tmp = scheduleRepo.findScheduleById(id);
        if ( tmp != null){
            //record exists let;s update.
            //reference and identifiers are not to be updated.
            schedule.setIdentifier(tmp.getIdentifier());
            schedule.setReference(tmp.getReference());
            schedule = scheduleRepo.save(schedule);
            return new ResponseEntity<Schedule>(schedule, HttpStatus.OK);
        }else{
            return new ResponseEntity<OperationOutcome>(OperationOutcome.RecordNotFound(), HttpStatus.NOT_FOUND);
        }
    }

    @RequestMapping(value = "/delete/{id}", method = RequestMethod.DELETE, produces = "application/json")
    @ResponseBody
    public ResponseEntity<?> delete(String id) {
        return null;
    }

    @RequestMapping(value = "/search",
            method = RequestMethod.GET,
            produces = "application/json")
    @ResponseBody
    public ResponseEntity<?> search(@RequestParam(value = "patient", required=false) Long patient,
                                    @RequestParam(value = "practitioner", required=false) Long practitioner,
                                    @RequestParam(value = "healthcareService", required=false) Long healthcareService){
        if(patient != null && practitioner != null){
            Reference patientRef = referenceRepo.findReferenceById(patient);
            Reference practionerRef = referenceRepo.findReferenceById(practitioner);
            List<Reference> actorList = new ArrayList<Reference>();
            actorList.add(patientRef);
            actorList.add(practionerRef);
            List<Schedule> scheduleList = scheduleRepo.findScheduleByActorIn(actorList);
            return new ResponseEntity<List>(scheduleList, HttpStatus.OK);
        }else if ( healthcareService != null){
            Reference healthCareRef = referenceRepo.findReferenceById(healthcareService);
            List<Reference> actorList = new ArrayList<Reference>();
            actorList.add(healthCareRef);
            List<Schedule> scheduleList = scheduleRepo.findScheduleByActorIn(actorList);
            return new ResponseEntity<List>(scheduleList, HttpStatus.OK);
        }else{
            return new ResponseEntity<OperationOutcome>(OperationOutcome.OperationNotSupported(),
                    HttpStatus.OK);
        }
    }
}
