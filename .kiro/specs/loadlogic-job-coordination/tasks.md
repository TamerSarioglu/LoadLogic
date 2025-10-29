# Implementation Plan

- [x] 1. Set up project structure and core configuration





  - Configure Spring Boot dependencies in build.gradle.kts
  - Set up application.yml with database and JWT configuration
  - Create package structure (controller, service, repository, entity, dto, config, security)
  - _Requirements: 9.3, 9.4_

- [x] 2. Implement core entities and enums




  - [x] 2.1 Create Role and JobStatus enums


    - Define Role enum with CHIEF, DRIVER, CREW values
    - Define JobStatus enum with PENDING, IN_PROGRESS, COMPLETED, CANCELLED values
    - _Requirements: 1.4, 7.2_

  - [x] 2.2 Create User entity with JPA annotations


    - Implement User entity with all required fields and constraints
    - Add unique constraint on username and proper validation annotations
    - _Requirements: 1.1, 1.2, 1.5, 9.1, 9.2_

  - [x] 2.3 Create Job entity with relationships


    - Implement Job entity with all job fields and status tracking
    - Add proper JPA annotations and constraints
    - _Requirements: 3.2, 3.3, 3.4, 3.5, 7.3, 9.2_

- [x] 3. Create DTOs for API requests and responses





  - [x] 3.1 Create authentication DTOs


    - Implement RegisterRequest, LoginRequest, and AuthResponse DTOs
    - Add validation annotations for required fields
    - _Requirements: 1.3, 2.1, 2.2_

  - [x] 3.2 Create job management DTOs


    - Implement CreateJobRequest, JobResponse, and UpdateJobStatusRequest DTOs
    - Add validation for material types and equipment from predefined lists
    - _Requirements: 3.2, 3.3, 7.1, 7.2_

  - [x] 3.3 Create reference data DTOs


    - Implement MaterialResponse, EquipmentResponse, and AvailableUserResponse DTOs
    - _Requirements: 8.1, 8.2, 8.4_

- [x] 4. Implement JWT security infrastructure





  - [x] 4.1 Create JWT service for token operations


    - Implement JwtService with token generation, validation, and extraction methods
    - Configure JWT secret and expiration settings
    - _Requirements: 2.3, 2.5_

  - [x] 4.2 Create UserDetails implementation and service


    - Implement CustomUserDetails and CustomUserDetailsService
    - Integrate with Spring Security authentication
    - _Requirements: 2.1, 2.4_

  - [x] 4.3 Configure Spring Security with JWT filter


    - Create JwtAuthenticationFilter for request processing
    - Configure SecurityConfig with role-based endpoint protection
    - _Requirements: 2.1, 6.3, 7.4_

- [ ] 5. Implement repository layer
  - [ ] 5.1 Create UserRepository with custom queries
    - Implement UserRepository with findByUsername and role-based queries
    - Add username existence check method
    - _Requirements: 1.2, 8.4_

  - [ ] 5.2 Create JobRepository with assignment queries
    - Implement JobRepository with assigned job queries and ordering
    - Add methods for finding jobs by driver/crew assignment
    - _Requirements: 4.2, 5.2, 5.3_

- [ ] 6. Implement service layer business logic
  - [ ] 6.1 Create UserService for authentication and user management
    - Implement user registration with validation and password encoding
    - Create authentication logic with JWT token generation
    - Add user lookup methods for job assignment validation
    - _Requirements: 1.1, 1.2, 1.4, 2.1, 2.2, 3.5, 9.1_

  - [ ] 6.2 Create JobService for job management
    - Implement job creation with assignment validation
    - Create job retrieval methods with role-based filtering
    - Add job status update logic with authorization checks
    - _Requirements: 3.1, 3.2, 3.3, 3.5, 4.1, 4.2, 5.1, 5.2, 6.1, 6.2, 6.3, 7.1, 7.4_

  - [ ] 6.3 Create ReferenceDataService for predefined lists
    - Implement methods to return materials and equipment lists
    - Create available users lookup by role
    - _Requirements: 8.1, 8.2, 8.3, 8.4_

- [ ] 7. Implement REST controllers
  - [ ] 7.1 Create AuthController for user registration and login
    - Implement POST /api/auth/register endpoint with validation
    - Implement POST /api/auth/login endpoint with authentication
    - Add proper error handling and response formatting
    - _Requirements: 1.1, 1.2, 1.3, 1.4, 1.5, 2.1, 2.2, 2.3, 2.4, 2.5_

  - [ ] 7.2 Create JobController for job management
    - Implement POST /api/jobs endpoint for job creation (CHIEF only)
    - Implement GET /api/jobs endpoint for all jobs (CHIEF only)
    - Implement GET /api/jobs/mine endpoint for assigned jobs (DRIVER/CREW)
    - Implement GET /api/jobs/{id} endpoint with authorization checks
    - Implement PATCH /api/jobs/{id}/status endpoint for status updates
    - _Requirements: 3.1, 3.2, 3.3, 3.4, 3.5, 4.1, 4.2, 4.3, 4.4, 5.1, 5.2, 5.3, 5.4, 6.1, 6.2, 6.3, 7.1, 7.2, 7.3, 7.4_

  - [ ] 7.3 Create ReferenceController for reference data
    - Implement GET /api/reference/materials endpoint
    - Implement GET /api/reference/equipment endpoint  
    - Implement GET /api/users/available endpoint with role filtering
    - _Requirements: 8.1, 8.2, 8.3, 8.4_

- [ ] 8. Implement global error handling and validation
  - [ ] 8.1 Create global exception handler
    - Implement GlobalExceptionHandler with custom exception handling
    - Create custom exceptions for business logic violations
    - Add consistent error response formatting
    - _Requirements: 1.2, 2.4, 3.5, 6.3, 7.4_

  - [ ] 8.2 Add input validation and business rule enforcement
    - Implement validation for material and equipment lists
    - Add user role and assignment validation
    - Create authorization checks for job access
    - _Requirements: 3.3, 3.4, 3.5, 6.3, 7.4, 9.4, 9.5_

- [ ] 9. Create application configuration and initialization
  - [ ] 9.1 Configure application properties and profiles
    - Set up database configuration for H2
    - Configure JWT settings and security properties
    - Add reference data configuration for materials and equipment
    - _Requirements: 8.1, 8.2, 9.3_

  - [ ] 9.2 Create data initialization for development
    - Implement CommandLineRunner to create sample users
    - Add sample data for testing different roles
    - _Requirements: 1.5, 9.2_

- [ ]* 10. Add comprehensive testing
  - [ ]* 10.1 Create unit tests for service layer
    - Write unit tests for UserService authentication and validation logic
    - Create unit tests for JobService business logic and authorization
    - Test ReferenceDataService methods
    - _Requirements: All requirements_

  - [ ]* 10.2 Create integration tests for controllers
    - Write integration tests for AuthController endpoints
    - Create integration tests for JobController with role-based access
    - Test ReferenceController endpoints
    - _Requirements: All requirements_

  - [ ]* 10.3 Create security and JWT testing
    - Test JWT token generation and validation
    - Verify role-based endpoint protection
    - Test authentication and authorization flows
    - _Requirements: 2.1, 2.2, 2.3, 2.4, 2.5, 6.3, 7.4_