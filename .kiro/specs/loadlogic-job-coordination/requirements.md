# Requirements Document

## Introduction

LoadLogic is a construction yard job coordination system that enables efficient management of construction jobs with crew and equipment assignment. The system provides role-based access control for Chiefs, Drivers, and Crew members to create, assign, and track construction jobs from creation to completion.

## Glossary

- **LoadLogic System**: The construction yard job coordination application
- **Chief**: A user role with administrative privileges to create and assign jobs
- **Driver**: A user role responsible for operating assigned vehicles and equipment
- **Crew**: A user role that assists on construction sites with tasks like loading and unloading
- **Job**: A construction task that includes material delivery, equipment assignment, and crew coordination
- **Equipment**: Construction vehicles and machinery (trucks, excavators, loaders, cranes)
- **Material**: Construction materials such as sand, gravel, concrete, bricks, steel, and wood
- **JWT Token**: JSON Web Token used for user authentication and authorization

## Requirements

### Requirement 1

**User Story:** As a Chief, I want to register new users in the system, so that I can build my team of drivers and crew members.

#### Acceptance Criteria

1. WHEN a Chief submits valid registration data, THE LoadLogic System SHALL create a new user account with the specified role
2. THE LoadLogic System SHALL validate that the username is unique across all users
3. THE LoadLogic System SHALL require all mandatory fields (username, fullName, password, role) for user creation
4. THE LoadLogic System SHALL restrict role assignment to CHIEF, DRIVER, or CREW only
5. THE LoadLogic System SHALL set the user status to active by default upon creation

### Requirement 2

**User Story:** As a user, I want to log into the system with my credentials, so that I can access my assigned jobs and perform my role-specific tasks.

#### Acceptance Criteria

1. WHEN a user provides valid credentials, THE LoadLogic System SHALL authenticate the user and return a JWT token
2. THE LoadLogic System SHALL include user information (username and role) in the authentication response
3. THE LoadLogic System SHALL set JWT token expiry to 1440 minutes (24 hours)
4. IF authentication fails, THEN THE LoadLogic System SHALL return an appropriate error message
5. THE LoadLogic System SHALL use "loadlogic-local" as the JWT token issuer

### Requirement 3

**User Story:** As a Chief, I want to create construction jobs with material and crew assignments, so that I can coordinate work efficiently across my team.

#### Acceptance Criteria

1. THE LoadLogic System SHALL allow only users with CHIEF role to create new jobs
2. WHEN a Chief creates a job, THE LoadLogic System SHALL require all mandatory job fields (title, materialType, quantity, destinationAddress, contactPerson, contactPhone, assignedDriverUsername, assignedCrewUsername, assignedEquipment)
3. THE LoadLogic System SHALL validate that materialType is from the predefined materials list
4. THE LoadLogic System SHALL validate that assignedEquipment is from the predefined equipment list
5. THE LoadLogic System SHALL validate that assigned users exist and have the correct roles (DRIVER and CREW)

### Requirement 4

**User Story:** As a Chief, I want to view all jobs in the system, so that I can monitor overall project progress and resource allocation.

#### Acceptance Criteria

1. THE LoadLogic System SHALL allow only users with CHIEF role to view all jobs
2. WHEN a Chief requests all jobs, THE LoadLogic System SHALL return a complete list of jobs regardless of status
3. THE LoadLogic System SHALL include all job details in the response
4. THE LoadLogic System SHALL order jobs by creation date with newest first

### Requirement 5

**User Story:** As a Driver or Crew member, I want to view my assigned jobs, so that I know what tasks I need to complete.

#### Acceptance Criteria

1. THE LoadLogic System SHALL allow users with DRIVER or CREW roles to view their assigned jobs
2. WHEN a Driver requests their jobs, THE LoadLogic System SHALL return jobs where assignedDriverUsername matches their username
3. WHEN a Crew member requests their jobs, THE LoadLogic System SHALL return jobs where assignedCrewUsername matches their username
4. THE LoadLogic System SHALL include all relevant job details for assigned jobs

### Requirement 6

**User Story:** As an assigned user or Chief, I want to view detailed information about a specific job, so that I can understand all requirements and contact information.

#### Acceptance Criteria

1. THE LoadLogic System SHALL allow job access to assigned users (Driver or Crew) and Chiefs
2. WHEN an authorized user requests job details, THE LoadLogic System SHALL return complete job information
3. IF an unauthorized user attempts to access a job, THEN THE LoadLogic System SHALL deny access
4. THE LoadLogic System SHALL include all job fields in the detailed view

### Requirement 7

**User Story:** As a Driver, Crew member, or Chief, I want to update job status, so that everyone can track progress from start to completion.

#### Acceptance Criteria

1. THE LoadLogic System SHALL allow assigned users (Driver or Crew) and Chiefs to update job status
2. THE LoadLogic System SHALL accept only valid status values (PENDING, IN_PROGRESS, COMPLETED, CANCELLED)
3. WHEN a job status is updated, THE LoadLogic System SHALL record the timestamp of the change
4. THE LoadLogic System SHALL validate user authorization before allowing status updates

### Requirement 8

**User Story:** As any user, I want to access reference data for materials and equipment, so that I can make informed decisions when creating or reviewing jobs.

#### Acceptance Criteria

1. THE LoadLogic System SHALL provide a list of available materials (Sand, Gravel, Concrete, Bricks, Steel, Wood)
2. THE LoadLogic System SHALL provide a list of available equipment (Truck-01, Truck-02, Excavator-A, Loader-B, Crane-X)
3. THE LoadLogic System SHALL allow any authenticated user to access reference data
4. WHEN a Chief needs to find available users, THE LoadLogic System SHALL provide a filtered list by role

### Requirement 9

**User Story:** As a system administrator, I want all user data to be securely stored and managed, so that the system maintains data integrity and user privacy.

#### Acceptance Criteria

1. THE LoadLogic System SHALL store user passwords securely using appropriate hashing
2. THE LoadLogic System SHALL maintain audit trails with creation and update timestamps
3. THE LoadLogic System SHALL ensure data consistency across all entities
4. THE LoadLogic System SHALL validate all input data before storage
5. THE LoadLogic System SHALL maintain referential integrity between jobs and assigned users