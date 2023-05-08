package com.petmanagement.petmanagement.models;

import jakarta.persistence.*;

//public class SelectData {
//
//    @Entity
//    public class PetType {
//
//        @Id
//        private Long id;
//        private String name;
//        // getters and setters
//
//
//
//        public Long getId() {
//            return id;
//        }
//
//        public void setId(Long id) {
//            this.id = id;
//        }
//
//        public String getName() {
//            return name;
//        }
//
//        public void setName(String name) {
//            this.name = name;
//        }
//
//    }
//
//    @Entity
//    public class FurColor {
//
//
//        @Id
//        private Long id;
//        private String name;
//        // getters and setters
//
//
//        public Long getId() {
//            return id;
//        }
//
//        public void setId(Long id) {
//            this.id = id;
//        }
//
//        public String getName() {
//            return name;
//        }
//
//        public void setName(String name) {
//            this.name = name;
//        }
//
//    }
//
//    @Entity
//    public class Country {
//
//        @Id
//        private Long id;
//        private String name;
//
//        // getters and setters
//
//        public Long getId() {
//            return id;
//        }
//
//        public void setId(Long id) {
//            this.id = id;
//        }
//
//        public String getName() {
//            return name;
//        }
//
//        public void setName(String name) {
//            this.name = name;
//        }
//
//
//    }
//
//}

@Entity
@Table(name = "pet_type")
public class PetTypeModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;


    // getters setters


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}



