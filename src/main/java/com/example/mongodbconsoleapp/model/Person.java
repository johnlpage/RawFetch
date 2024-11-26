package com.example.mongodbconsoleapp.model;

import java.util.Random;
import java.lang.reflect.Field;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "people")
public class Person {

    @Id
    private ObjectId id;

    // Personal Information
    private String firstName;
    private String lastName;
    private String middleName;
    private String gender;
    private String dateOfBirth;
    private String placeOfBirth;
    private String nationality;
    private String maritalStatus;
    private String spouseName;
    private Integer numberOfChildren;

    // Contact Information
    private String email;
    private String phoneNumber;
    private String alternatePhoneNumber;
    private String address;
    private String city;
    private String state;
    private String postalCode;
    private String country;
    private String emergencyContactName;
    private String emergencyContactNumber;

    // Demographics
    private String ethnicity;
    private String religion;
    private String language;
    private String educationLevel;
    private String occupation;
    private String employer;
    private Double annualIncome;
    private String employmentStatus;
    private String jobTitle;
    private String workExperience;

    // Health Information
    private String bloodType;
    private Double height;
    private Double weight;
    private String allergies;
    private String medicalConditions;
    private String medications;
    private String primaryPhysician;
    private String healthInsuranceProvider;
    private String healthInsuranceNumber;
    private String vaccinationStatus;

    // Social Information
    private String hobbies;
    private String interests;
    private String favoriteBooks;
    private String favoriteMovies;
    private String favoriteMusic;
    private String socialMediaProfile;
    private String preferredCommunicationMethod;
    private String volunteerActivities;
    private String communityInvolvement;
    private String politicalAffiliation;

    // Financial Information
    private String bankName;
    private String bankAccountNumber;
    private String creditCardNumber;
    private String creditScore;
    private String loanStatus;
    private String mortgageStatus;
    private String investmentPortfolio;
    private String retirementPlan;
    private String taxBracket;
    private String financialAdvisor;

    // Additional Attributes
    private String passportNumber;
    private String driverLicenseNumber;
    private String vehicleRegistrationNumber;
    private String membershipIds;
    private String loyaltyPrograms;
    private String travelHistory;
    private String preferredAirlines;
    private String frequentFlyerNumber;
    private String hotelMemberships;
    private String rentalCarMemberships;

    // More Attributes
    private String petOwnership;
    private String homeOwnership;
    private String propertyType;
    private String propertyValue;
    private String rentalStatus;
    private String leaseAgreement;
    private String landlordContact;
    private String tenantContact;
    private String utilityProviders;
    private String internetProvider;

    // Even More Attributes
    private String favoriteCuisine;
    private String dietaryRestrictions;
    private String cookingSkills;
    private String gardeningSkills;
    private String diySkills;
    private String techSavvy;
    private String gamingPreferences;
    private String sportsParticipation;
    private String fitnessRoutine;
    private String travelPreferences;

    // Final Set of Attributes
    private String artPreferences;
    private String musicSkills;
    private String languageSkills;
    private String writingSkills;
    private String publicSpeakingSkills;
    private String leadershipSkills;
    private String negotiationSkills;
    private String timeManagementSkills;
    private String stressManagementSkills;
    private String conflictResolutionSkills;

    // Constructors, getters, and setters

    public Person() {
        populateStringFieldsWithRandomValues();
    }

    // Add constructors, getters, and setters for all fields here

    // Example getter and setter
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

  

    //This code populates all class members with random values for testing

    private void populateStringFieldsWithRandomValues() {
        Field[] fields = this.getClass().getDeclaredFields();
        Random random = new Random();
        for (Field field : fields) {
            if (field.getType().equals(String.class)) {
                field.setAccessible(true); // Allow access to private fields
                try {
                    String randomString = generateRandomString(random, 16); // Generate a random string of length 8
                    field.set(this, randomString);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }

            if (field.getType().equals(Integer.class)) {
                field.setAccessible(true); // Allow access to private fields
                try {
                    Integer randomInt = random.nextInt(10); 
                    field.set(this, randomInt);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }

            if (field.getType().equals(Double.class)) {
                field.setAccessible(true); // Allow access to private fields
                try {
                    Double randomDouble = random.nextDouble(500); 
                    field.set(this, randomDouble);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    private  String generateRandomString(Random random, int length) {
        String characters = "   ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        StringBuilder sb = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            sb.append(characters.charAt(random.nextInt(characters.length())));
        }
        return sb.toString();
    }
}

