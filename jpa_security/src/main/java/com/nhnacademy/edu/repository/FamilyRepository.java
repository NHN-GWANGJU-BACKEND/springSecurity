package com.nhnacademy.edu.repository;

import com.nhnacademy.edu.entity.FamilyRelationship;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FamilyRepository extends JpaRepository<FamilyRelationship,FamilyRelationship.Pk> {

}
