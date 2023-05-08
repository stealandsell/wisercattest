package com.petmanagement.petmanagement.repo;

import com.petmanagement.petmanagement.PetmanagementApplication;
import com.petmanagement.petmanagement.models.PetTypeModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;


//    public interface PetTypeRepository extends JpaRepository<SelectData.PetType, Long> {
//    }
//
//    public interface FurColorRepository extends JpaRepository<SelectData.FurColor, Long> {
//    }
//
//    public interface CountryRepository extends JpaRepository<SelectData.Country, Long> {
//    }


public interface PetTypeRepository extends CrudRepository<PetTypeModel, Long> {
}
