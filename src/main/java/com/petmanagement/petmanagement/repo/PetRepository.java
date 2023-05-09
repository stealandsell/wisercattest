package com.petmanagement.petmanagement.repo;

import com.petmanagement.petmanagement.models.Pet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface PetRepository extends JpaRepository<Pet, Integer> {
    List<Pet> findByOwnerId(Integer ownerId);
}
