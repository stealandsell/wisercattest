package com.petmanagement.petmanagement.controllers;

import com.petmanagement.petmanagement.configurer.UserService;
import com.petmanagement.petmanagement.models.*;
import com.petmanagement.petmanagement.repo.CountryOfOriginRepository;
import com.petmanagement.petmanagement.repo.FurColorRepository;
import com.petmanagement.petmanagement.repo.PetRepository;
import com.petmanagement.petmanagement.repo.PetTypeRepository;
import jakarta.validation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;


@Controller
public class MainController {
    @Autowired
    private PetRepository petRepository;

    @Autowired
    private PetTypeRepository petTypeRepository;

    @Autowired
    private FurColorRepository furColorRepository;

    @Autowired
    private CountryOfOriginRepository countryOfOriginRepository;

    @Autowired
    private UserService userService;

    @CrossOrigin(origins = "http://localhost:4200", allowedHeaders = "*")
    @PostMapping("/add")
    public ResponseEntity<?> addPet(@RequestParam("username") String username, @Valid @RequestBody Pet pet) {

        System.out.println(username);

        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();

        Set<ConstraintViolation<Pet>> violations = validator.validate(pet);

        if (!violations.isEmpty()) {
            List<String> errors = new ArrayList<>();
            for (ConstraintViolation<Pet> violation : violations) {
                errors.add(violation.getMessage());
            }
            ValidationErrorResponse response = new ValidationErrorResponse(errors);

            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }

        UserEntity user = userService.findByUsername(username);


        if (user != null) {
            pet.setOwnerId(Math.toIntExact(user.getId()));
        }


        Pet newPet = petRepository.save(pet);

        return new ResponseEntity<>(newPet, HttpStatus.CREATED);
    }


    @GetMapping("/edit/{id}")
    public ResponseEntity<?> getPetById(@PathVariable Long id) {
        Optional<Pet> petOptional = petRepository.findById(Math.toIntExact(id));
        if (petOptional.isPresent()) {
            Pet pet = petOptional.get();
            return ResponseEntity.ok(pet);
        } else {
            return ResponseEntity.notFound().build();
        }
    }


    @CrossOrigin(origins = "http://localhost:4200", allowedHeaders = "*")
    @PutMapping("/edit/{id}")
    public ResponseEntity<Pet> updatePet(@PathVariable Long id, @Valid @RequestBody Pet updatedPet) {
        Optional<Pet> optionalPet = petRepository.findById(Math.toIntExact(id));
        if (optionalPet.isPresent()) {
            Pet pet = optionalPet.get();
            pet.setName(updatedPet.getName());
            pet.setCode(updatedPet.getCode());
            pet.setType(updatedPet.getType());
            pet.setFurColor(updatedPet.getFurColor());
            pet.setCountryOfOrigin(updatedPet.getCountryOfOrigin());
            petRepository.save(pet);
            return new ResponseEntity<>(pet, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }


    @SpringBootApplication(exclude = {SecurityAutoConfiguration.class})
    @RestController
    @CrossOrigin(origins = "http://localhost:4200", allowedHeaders = "*")
    public static class login {


        @Autowired
        private UserService userService;

        @PostMapping("/login")
        public ResponseEntity<?> loginResponse(@RequestBody UserEntity loginForm) {

            String username = loginForm.getUsername();
            String password = loginForm.getPassword();

            UserEntity user = userService.findByUsername(username);


            if (user != null && user.getPassword().equals(password)) {
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(user, null, userService.getAuthorities());
                SecurityContextHolder.getContext().setAuthentication(authToken);


                if (user.getPassword().equals(password)) {


                    return ResponseEntity.ok().body(username);
                } else {
                    return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
                }


            } else {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
            }
        }
    }

    @RestController
    @CrossOrigin(origins = "http://localhost:4200")
    @RequestMapping("/api")
    public static class PetTypeController {
        @Autowired
        private PetTypeRepository petTypeRepository;

        @GetMapping("/types")
        public List<PetTypeModel> getPetType() {
            return (List<PetTypeModel>) petTypeRepository.findAll();
        }
    }

    @RestController
    @CrossOrigin(origins = "http://localhost:4200")
    @RequestMapping("/api")
    public static class FurColorController {
        @Autowired
        private FurColorRepository furColorRepository;

        @GetMapping("/colors")
        public List<FurColorModel> getFurColor() {
            return (List<FurColorModel>) furColorRepository.findAll();
        }
    }

    @RestController
    @CrossOrigin(origins = "http://localhost:4200")
    @RequestMapping("/api")
    public static class CountryOfOriginController {
        @Autowired
        private CountryOfOriginRepository countryOfOriginRepository;

        @GetMapping("/countries")
        public List<CountryOfOriginModel> getCountryOfOrigin() {
            return (List<CountryOfOriginModel>) countryOfOriginRepository.findAll();
        }
    }

    public class ValidationErrorResponse {
        private List<String> errors;

        public ValidationErrorResponse(List<String> errors) {
            this.errors = errors;
        }

        public List<String> getErrors() {
            return errors;
        }

        public void setErrors(List<String> errors) {
            this.errors = errors;
        }
    }

    @RestController
    @CrossOrigin(origins = "http://localhost:4200", allowedHeaders = "*")
    @RequestMapping("/api")
    public class PetRestController {
        @Autowired
        private PetRepository petRepository;


        @Autowired
        private UserService userService;


        @GetMapping("/pets")
        public ResponseEntity<?> getPets(@RequestParam("username") String username) {
            System.out.println(username);

            UserEntity user = userService.findByUsername(username);

            if (user == null) {

                // No user logged in, retrieve all pets

                System.out.println("no user logged in - all users items visible");
                System.out.println(petRepository.findAll());
                return new ResponseEntity<>(petRepository.findAll(), HttpStatus.CREATED);


            } else {
                // User logged in, retrieve pets belonging to that user

                System.out.println("user " + username + " logged in - user can see only his pets");
                return new ResponseEntity<>(petRepository.findByOwnerId(Math.toIntExact(user.getId())), HttpStatus.CREATED);
            }
        }


    }
}