package org.healtheta.model.schedule.repo;


import org.aspectj.apache.bcel.classfile.Code;
import org.healtheta.model.common.CodeableConcept;
import org.healtheta.model.common.Identifier;
import org.healtheta.model.common.Reference;
import org.healtheta.model.schedule.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ScheduleRepo extends JpaRepository<Schedule, Long> {
    public Schedule findScheduleById(Long id);
    public Schedule findScheduleByIdentifier(Identifier identifier);
    public List<Schedule> findScheduleByServiceCategory(CodeableConcept serviceCategory);
    public List<Schedule> findScheduleByServiceTypeIn(List<CodeableConcept> serviceType);
    public List<Schedule> findScheduleBySpecialityIn(List<CodeableConcept> speciality);
    public List<Schedule> findScheduleByActorIn(List<Reference> actor);

}
