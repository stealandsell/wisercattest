package com.petmanagement.petmanagement.repo;

import com.petmanagement.petmanagement.models.FurColorModel;
import com.petmanagement.petmanagement.models.PetTypeModel;
import org.springframework.data.repository.CrudRepository;

public interface FurColorRepository extends CrudRepository<FurColorModel, Long> {
}
