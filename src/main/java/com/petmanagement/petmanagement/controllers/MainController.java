package com.petmanagement.petmanagement.controllers;

import com.petmanagement.petmanagement.configurer.MyUserDetails;
import com.petmanagement.petmanagement.configurer.UserService;
import com.petmanagement.petmanagement.models.*;
import com.petmanagement.petmanagement.repo.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.*;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;


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






    @SpringBootApplication(exclude = {SecurityAutoConfiguration.class })
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
                //String token = username + "verysecuretoken";

                if (user.getPassword().equals(password)) {
                    // your existing code here

                    return ResponseEntity.ok().body(username);
                } else {
                    return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
                }


//                System.out.println("[login] auth token: " + token);
//
//                // Return the token as a separate value in the response body
//                Map<String, String> responseBody = new HashMap<>();
//                responseBody.put("token", token);
//                return ResponseEntity.ok().body(responseBody);



//                HttpHeaders headers = new HttpHeaders();
//                headers.add("Authorization", "Bearer " + token);
//                System.out.println("[login] headers: " + headers);
//
//
//                System.out.println(token);
//
//                System.out.println(ResponseEntity.ok().header("Authorization", "Bearer " + token).build());
//
////                return ResponseEntity.ok().headers(headers).build();
////                return ResponseEntity.ok().header("Authorization", "Bearer " + token).build();
////                return ResponseEntity.ok().build();
//                return new ResponseEntity<>(token, HttpStatus.OK);


            } else {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
            }
        }
    }



//    @CrossOrigin(origins = "http://localhost:4200", allowedHeaders = "*")
//    @PostMapping("/logout")
//    public ResponseEntity<?> logout() {
//        SecurityContextHolder.getContext().setAuthentication(null);
//        return ResponseEntity.ok().build();
//    }



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


    //###########################################


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


        //System.out.println(pet.getId());
        System.out.println(pet.getName());
        System.out.println(pet.getCode());

        pet.setOwnerId(Math.toIntExact(user.getId()));

        System.out.println("user id");
        System.out.println(user.getId());

        System.out.println("save pet");


//        System.out.println(pet.getName());
//        System.out.println(pet.getCode());

        Pet newPet = petRepository.save(pet);

        return new ResponseEntity<>(newPet, HttpStatus.CREATED);
    }

    //###########################################



//    @CrossOrigin(origins = "http://localhost:4200", allowedHeaders = "*")
//    @PostMapping("/add")
//    public ResponseEntity<?> addPet(Authentication authentication, @Valid @RequestBody Pet pet) {
//
//        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
//        Validator validator = factory.getValidator();
//
//        Set<ConstraintViolation<Pet>> violations = validator.validate(pet);
//
//        if (!violations.isEmpty()) {
//            List<String> errors = new ArrayList<>();
//            for (ConstraintViolation<Pet> violation : violations) {
//                errors.add(violation.getMessage());
//            }
//            ValidationErrorResponse response = new ValidationErrorResponse(errors);
//
//            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
//        }
//
//        String username = authentication.getName();
//        UserEntity user = userService.findByUsername(username);
//
//        pet.setOwnerId(Math.toIntExact(user.getId()));
//
//        Pet newPet = petRepository.save(pet);
//
//        return new ResponseEntity<>(newPet, HttpStatus.CREATED);
//    }



//    @CrossOrigin(origins = "http://localhost:4200", allowedHeaders = "*")
//    @PostMapping("/add")
//    public ResponseEntity<?> addPet(@AuthenticationPrincipal MyUserDetails userDetails, @Valid @RequestBody Pet pet) {
//
//        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
//        Validator validator = factory.getValidator();
//
//        Set<ConstraintViolation<Pet>> violations = validator.validate(pet);
//
//        if (!violations.isEmpty()) {
//            List<String> errors = new ArrayList<>();
//            for (ConstraintViolation<Pet> violation : violations) {
//                errors.add(violation.getMessage());
//            }
//            ValidationErrorResponse response = new ValidationErrorResponse(errors);
//
//            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
//        }
//
//        UserEntity user = userService.findByUsername(userDetails.getUsername());
//
//        pet.setOwnerId(Math.toIntExact(user.getId()));
//
//        Pet newPet = petRepository.save(pet);
//
//        return new ResponseEntity<>(newPet, HttpStatus.CREATED);
//    }
//
//

//    @CrossOrigin(origins = "http://localhost:4200", allowedHeaders = "*")
//    @PostMapping("/add")
//    public ResponseEntity<?> addPet(@AuthenticationPrincipal MyUserDetails userDetails, @Valid @RequestBody Pet pet) {
//        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
//        Validator validator = factory.getValidator();
//
//        Set<ConstraintViolation<Pet>> violations = validator.validate(pet);
//
//        if (!violations.isEmpty()) {
//            List<String> errors = new ArrayList<>();
//            for (ConstraintViolation<Pet> violation : violations) {
//                errors.add(violation.getMessage());
//            }
//            ValidationErrorResponse response = new ValidationErrorResponse(errors);
//
//            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
//        }
//
//
//        String username = userDetails.getUsername();
//        //
//        System.out.println(username);
//        //
//        UserEntity user = userService.findByUsername(username);
//
//        pet.setOwnerId(Math.toIntExact(user.getId()));
//
//        Pet newPet = petRepository.save(pet);
//
//        return new ResponseEntity<>(newPet, HttpStatus.CREATED);
//    }


//    @CrossOrigin(origins = "http://localhost:4200", allowedHeaders = "*")
//    @PostMapping("/add")
//    public ResponseEntity<?> addPet(HttpServletRequest request, @Valid @RequestBody Pet pet) {
//
//        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
//        Validator validator = factory.getValidator();
//
//        Set<ConstraintViolation<Pet>> violations = validator.validate(pet);
//
//        if (!violations.isEmpty()) {
//            List<String> errors = new ArrayList<>();
//            for (ConstraintViolation<Pet> violation : violations) {
//                errors.add(violation.getMessage());
//            }
//            ValidationErrorResponse response = new ValidationErrorResponse(errors);
//
//            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
//        }
//
//        String username = request.getUserPrincipal().getName();
//        UserEntity user = userService.findByUsername(username);
//
//        pet.setOwnerId(Math.toIntExact(user.getId()));
//
//        Pet newPet = petRepository.save(pet);
//
//        return new ResponseEntity<>(newPet, HttpStatus.CREATED);
//    }


//    @CrossOrigin(origins = "http://localhost:4200", allowedHeaders = "*")
//    @PostMapping("/add")
//    public ResponseEntity<?> addPet(@Valid @RequestBody Pet pet) {
//
//        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
//        Validator validator = factory.getValidator();
//
//        Set<ConstraintViolation<Pet>> violations = validator.validate(pet);
//
//        if (!violations.isEmpty()) {
//            List<String> errors = new ArrayList<>();
//            for (ConstraintViolation<Pet> violation : violations) {
//                errors.add(violation.getMessage());
//            }
//            ValidationErrorResponse response = new ValidationErrorResponse(errors);
//
//            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
//        }
//
//        Pet newPet = petRepository.save(pet);
//
//        return new ResponseEntity<>(newPet, HttpStatus.CREATED);
//    }
//

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



    @RestController
    @CrossOrigin(origins = "http://localhost:4200", allowedHeaders = "*")
    @RequestMapping("/api")
    public class PetRestController {
        @Autowired
        private PetRepository petRepository;


        @Autowired
        private UserService userService;


        @GetMapping("/pets")
        public List<Pet> getPets() {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            if (authentication == null || !authentication.isAuthenticated()) {
                // No user logged in, retrieve all pets
                return petRepository.findAll();
            } else {
                // User logged in, retrieve pets belonging to that user
                UserEntity user = userService.getCurrentUser();
                return petRepository.findByOwnerId(Math.toIntExact(user.getId()));
            }
        }

        //
//        @GetMapping("/pets")
//        public List<Pet> getPets() {
//            return petRepository.findAll();
//        }
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



//    @GetMapping("/")
//    public String home(Model model) {
//        model.addAttribute("title", "Home page");
//        return "home";
//    }

//    @GetMapping("/add")
//    public String addPet(Model model) {
//        return "add";
//    }




//    @GetMapping("/add")
//    public String showAddPetForm(Model model) {
//        // retrieve pet types from the database
//        Iterable<PetTypeModel> petTypes = petTypeRepository.findAll();
//
//        // retrieve fur colors from the database
//        Iterable<FurColorModel> furColors = furColorRepository.findAll();
//
//        // retrieve countries from the database
//        Iterable<CountryOfOriginModel> countryOfOrigin = countryOfOriginRepository.findAll();
//
//        // add the pet types and fur colors to the model
//        model.addAttribute("petTypes", petTypes);
//        model.addAttribute("furColors", furColors);
//        model.addAttribute("countryOfOrigin", countryOfOrigin);
//
//        return "add";
//    }



//    @PostMapping("/add")
//    public String addPetPost(@RequestParam String name,
//                             @RequestParam String code,
//                             @RequestParam String type,
//                             @RequestParam String fur_color,
//                             @RequestParam String country_of_origin,
//                             Model model) {
//
//        Pet pet = new Pet(name, code, type, fur_color, country_of_origin);
//
//
//        petRepository.save(pet);
//
//        return "redirect:/add";
//    }


//    @CrossOrigin(origins = "http://localhost:4200", allowedHeaders = "*")
//    @PostMapping("/add")
//    public String addPet(@RequestParam(value = "name", required = false) String name,
//                         @RequestParam(value = "code", required = false) String code,
//                         @RequestParam(value = "type", required = false) String type,
//                         @RequestParam(value = "fur_color", required = false) String fur_color,
//                         @RequestParam(value = "country_of_origin", required = false) String country_of_origin,
//                         Model model){
//        Pet pet = new Pet(name, code, type, fur_color, country_of_origin);
//        System.out.println(pet.getCode());
//        System.out.println(pet.getType());
//        petRepository.save(pet);
//        return "redirect:/add";
//
//    }







//    @CrossOrigin(origins = "http://localhost:4200", allowedHeaders = "*")
//    @PostMapping("/add")
//    public ResponseEntity<Pet> addPet(@Valid @RequestBody Pet pet) {
//
//        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
//        Validator validator = factory.getValidator();
//
//        Set<ConstraintViolation<Pet>> violations = validator.validate(pet);
//
//        if (!violations.isEmpty()) {
//            List<String> errors = new ArrayList<>();
//            for (ConstraintViolation<Pet> violation : violations) {
//                errors.add(violation.getMessage());
//            }
//            ValidationErrorResponse response = new ValidationErrorResponse(errors);
//            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
//        }
//
//
//        Pet newPet = petRepository.save(pet);
////        System.out.println(newPet.getType());
////        System.out.println(newPet.getFurColor());
////        System.out.println(newPet.getCode());
//        return new ResponseEntity<>(newPet, HttpStatus.CREATED);
//    }


//    @GetMapping("/table")
//    public String viewTable(Model model) {
//        List<Pet> pets = petRepository.findAll();
//        model.addAttribute("title", "table");
//        model.addAttribute("pets", pets);
//        return "table";
//    }


//    @GetMapping("/add")
//    public Pet addPetPost(@RequestBody Pet pet) {
//
//        Pet pet = new Pet(name, code, type, fur_color, country_of_origin);
//
//        return petRepository.save(pet);
//
//    }


}