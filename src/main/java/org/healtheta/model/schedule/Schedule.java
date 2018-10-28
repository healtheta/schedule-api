package org.healtheta.model.schedule;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.healtheta.model.common.CodeableConcept;
import org.healtheta.model.common.Identifier;
import org.healtheta.model.common.Period;
import org.healtheta.model.common.Reference;
import java.util.List;
import javax.persistence.*;

@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Entity
@Table(name = "Schedule")
public class Schedule {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    @Column(name = "_id")
    private Long id;

    @OneToOne(fetch = FetchType.LAZY,
            cascade =  CascadeType.ALL)
    @JoinColumn(unique=true, name = "_identifier")
    private Identifier identifier;

    @Column(name = "_active")
    private boolean active;

    @OneToOne(fetch = FetchType.LAZY,
            cascade =  CascadeType.ALL)
    @JoinColumn(name = "_patient")
    private CodeableConcept serviceCategory;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @Column(name = "_serviceType")
    private List<CodeableConcept> serviceType;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @Column(name = "_speciality")
    private List<CodeableConcept> speciality;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @Column(name = "_actor")
    private List<Reference> actor;

    @OneToOne(fetch = FetchType.LAZY,
            cascade =  CascadeType.ALL)
    @JoinColumn(name = "_identifier")
    private Period planningHorizon;

    @Column(name = "_comment")
    private String comment;

    @OneToOne(fetch = FetchType.LAZY,
            cascade =  CascadeType.ALL)
    @JoinColumn(name = "_reference")
    private Reference reference;

    public Long getId() {
        return id;
    }

    public Identifier getIdentifier() {
        return identifier;
    }

    public boolean isActive() {
        return active;
    }

    public CodeableConcept getServiceCategory() {
        return serviceCategory;
    }

    public List<CodeableConcept> getServiceType() {
        return serviceType;
    }

    public List<CodeableConcept> getSpeciality() {
        return speciality;
    }

    public List<Reference> getActor() {
        return actor;
    }

    public Period getPlanningHorizon() {
        return planningHorizon;
    }

    public String getComment() {
        return comment;
    }

    public Reference getReference() {
        return reference;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setIdentifier(Identifier identifier) {
        this.identifier = identifier;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public void setServiceCategory(CodeableConcept serviceCategory) {
        this.serviceCategory = serviceCategory;
    }

    public void setServiceType(List<CodeableConcept> serviceType) {
        this.serviceType = serviceType;
    }

    public void setSpeciality(List<CodeableConcept> speciality) {
        this.speciality = speciality;
    }

    public void setActor(List<Reference> actor) {
        this.actor = actor;
    }

    public void setPlanningHorizon(Period planningHorizon) {
        this.planningHorizon = planningHorizon;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public void setReference(Reference reference) {
        this.reference = reference;
    }
}
