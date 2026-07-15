# Job Portal & Applicant Tracking System

A production-ready backend REST API built with **Java** and **Spring Boot** that manages the complete hiring lifecycle — from job posting to interview scheduling and candidate selection.

<h3>## Overview</h3>

This system allows: <br>
- **Admin** to manage roles and users </br>
- **Recruiters** to create companies, post jobs and manage applications</br>
- **Candidates** to register profiles, upload resumes, apply for jobs and track application status</br>

<h3>## Tech Stack</h3>

| Technology         | Purpose |
| Java               | Core programming language |
| Spring Boot        | REST API framework |
| Spring Data JPA    | Database operations |
| Hibernate          | ORM — maps Java classes to DB tables |
| PostgreSQL         | Relational database |
| Lombok             | Reduces boilerplate code |
| Jakarta Validation | Input validation |
| Maven              | Dependency management |

<h3>## Project Structure</h3>
src
├── main
│   ├── java
│   │   └── com.Neha.job_portal_applicant_tracking_system
│   │       ├── controller        # Handles HTTP requests
│   │       ├── service           # Business logic (interface + implementation)
│   │       │   └── implementation
│   │       ├── repository        # Database operations
│   │       ├── entity            # Database table mappings
│   │       ├── dto               # Request and Response DTOs
│   │       └── exception         # Custom exceptions + GlobalExceptionHandler
│   └── resources
│       └── application.properties

<h3>## Modules</h3>

### 1. Role Module </br>
Defines user types in the system. </br>
- Roles — `ADMIN`, `RECRUITER`, `CANDIDATE` </br>

### 2. User Module </br>
Manages login credentials and basic information for every person in the system. Every person must be registered as a User with a Role assigned.</br>

### 3. Company Module</br>
Manages companies that post jobs. Supports active/inactive status for soft delete. One Company can have Many Jobs.</br>

### 4. Job Module</br>
Manages job postings by companies with details like title, salary, required skills and experience. Jobs have status — `OPEN`, `CLOSED`, `ON_HOLD`, `FILLED`. Only OPEN jobs accept applications.</br>

**Job Types:** `FULL_TIME` `PART_TIME` `CONTRACT` `INTERNSHIP` `FREELANCE` `REMOTE`</br>

### 5. Candidate Module</br>
Manages candidate profiles linked to user accounts. One User maps to exactly One Candidate (OneToOne). Supports filtering by location, gender and experience level.</br>

### 6. Resume Module </br>
Candidates can upload multiple resumes. One resume can be marked as default which is auto-selected when applying for jobs. Switching default automatically removes it from the previous one. </br>

### 7. Application Module </br>
Candidates apply for jobs using their resume with an optional cover letter. Prevents duplicate applications to the same job. Job must be OPEN to accept applications. </br>

**Application Status Flow:** </br>
APPLIED → UNDER_REVIEW → SHORTLISTED → SELECTED </br>
↓
REJECTED
### 8. Interview Module</br>
Interviews are scheduled only for SHORTLISTED applications. Multiple rounds can be scheduled per application. Recruiter updates result and feedback after the interview.</br>

**Interview Result Flow:**
PENDING → PASSED
→ FAILED
→ CANCELLED

<h3>## Database Relationships</h3>
Role        (1) ──────── (Many) User
User        (1) ──────── (1)    Candidate
Company     (1) ──────── (Many) Job
Candidate   (1) ──────── (Many) Resume
Candidate   (1) ──────── (Many) Application
Job         (1) ──────── (Many) Application
Resume      (1) ──────── (Many) Application
Application (1) ──────── (Many) Interview
---

<h3>## API Endpoints</h3>

### Role APIs
| Method | Endpoint | Description |
|---|---|---|
| POST | `/api/roles` | Create new role |
| GET | `/api/roles/{id}` | Get role by ID |
| GET | `/api/roles/name/{role}` | Get role by name |
| GET | `/api/roles` | Get all roles |
| DELETE | `/api/roles/{id}` | Delete role |

### User APIs
| Method | Endpoint | Description |
|---|---|---|
| POST | `/api/users/register` | Register new user |
| GET | `/api/users/{id}` | Get user by ID |
| GET | `/api/users/email/{email}` | Get user by email |
| GET | `/api/users` | Get all users |
| PUT | `/api/users/{id}` | Update user |
| PATCH | `/api/users/{id}/deactivate` | Deactivate user |
| DELETE | `/api/users/{id}` | Delete user |

### Company APIs
| Method | Endpoint | Description |
|---|---|---|
| POST | `/api/companies` | Create company |
| GET | `/api/companies/{id}` | Get by ID |
| GET | `/api/companies/name/{name}` | Get by name |
| GET | `/api/companies` | Get all |
| GET | `/api/companies/active` | Get active companies |
| GET | `/api/companies/inactive` | Get inactive companies |
| GET | `/api/companies/industry/{industry}` | Filter by industry |
| GET | `/api/companies/location/{location}` | Filter by location |
| PUT | `/api/companies/{id}` | Update company |
| PATCH | `/api/companies/{id}/deactivate` | Deactivate company |
| DELETE | `/api/companies/{id}` | Delete company |

### Job APIs
| Method | Endpoint | Description |
|---|---|---|
| POST | `/api/jobs` | Create job |
| GET | `/api/jobs/{id}` | Get by ID |
| GET | `/api/jobs` | Get all jobs |
| GET | `/api/jobs/company/{companyId}` | Jobs by company |
| GET | `/api/jobs/status/{status}` | Filter by status |
| GET | `/api/jobs/type/{jobType}` | Filter by type |
| GET | `/api/jobs/location/{location}` | Filter by location |
| GET | `/api/jobs/experience?max=3` | Filter by experience |
| PUT | `/api/jobs/{id}` | Update job |
| PATCH | `/api/jobs/{id}/status` | Update job status |
| DELETE | `/api/jobs/{id}` | Delete job |

### Candidate APIs
| Method | Endpoint | Description |
|---|---|---|
| POST | `/api/candidates` | Register candidate |
| GET | `/api/candidates/{id}` | Get by ID |
| GET | `/api/candidates/email/{email}` | Get by email |
| GET | `/api/candidates/user/{userId}` | Get by user ID |
| GET | `/api/candidates` | Get all |
| GET | `/api/candidates/active` | Get active candidates |
| GET | `/api/candidates/inactive` | Get inactive candidates |
| GET | `/api/candidates/location/{location}` | Filter by location |
| GET | `/api/candidates/gender?gender=MALE` | Filter by gender |
| GET | `/api/candidates/experience/max?years=3` | Max experience filter |
| GET | `/api/candidates/experience/min?years=5` | Min experience filter |
| PUT | `/api/candidates/{id}` | Update candidate |
| PATCH | `/api/candidates/{id}/deactivate` | Deactivate candidate |
| DELETE | `/api/candidates/{id}` | Delete candidate |

### Resume APIs
| Method | Endpoint | Description |
|---|---|---|
| POST | `/api/resumes` | Upload resume |
| GET | `/api/resumes/{id}` | Get by ID |
| GET | `/api/resumes/candidate/{candidateId}` | All resumes of candidate |
| GET | `/api/resumes/candidate/{candidateId}/default` | Get default resume |
| PATCH | `/api/resumes/{resumeId}/default/{candidateId}` | Set as default |
| DELETE | `/api/resumes/{id}` | Delete resume |

### Application APIs
| Method | Endpoint | Description |
|---|---|---|
| POST | `/api/applications` | Apply for job |
| GET | `/api/applications/{id}` | Get by ID |
| GET | `/api/applications/candidate/{candidateId}` | By candidate |
| GET | `/api/applications/job/{jobId}` | By job |
| GET | `/api/applications/status?status=APPLIED` | Filter by status |
| GET | `/api/applications/candidate/{id}/status?status=SHORTLISTED` | Candidate apps by status |
| GET | `/api/applications/job/{id}/status?status=SHORTLISTED` | Job apps by status |
| PATCH | `/api/applications/{id}/status?status=SHORTLISTED` | Update status |

### Interview APIs
| Method | Endpoint | Description |
|---|---|---|
| POST | `/api/interviews` | Schedule interview |
| GET | `/api/interviews/{id}` | Get by ID |
| GET | `/api/interviews/application/{applicationId}` | By application |
| GET | `/api/interviews/candidate/{candidateId}` | By candidate |
| GET | `/api/interviews/job/{jobId}` | By job |
| GET | `/api/interviews/result?result=PASSED` | Filter by result |
| GET | `/api/interviews/mode?mode=ONLINE` | Filter by mode |
| PATCH | `/api/interviews/{id}/result` | Update result and feedback |
| PUT | `/api/interviews/{id}/reschedule` | Reschedule interview |
| DELETE | `/api/interviews/{id}` | Delete interview |

---

## Key Features

- ✅ **8 Modules** covering complete hiring lifecycle
- ✅ **60+ REST endpoints** with proper HTTP methods and status codes
- ✅ **Generic ApiResponse wrapper** for consistent JSON responses
- ✅ **Global Exception Handling** with 15+ custom exception classes
- ✅ **DTO Pattern** — separate Request and Response DTOs for all modules
- ✅ **Soft Delete** using active flag for User, Company and Candidate
- ✅ **Duplicate prevention** — candidate cannot apply to same job twice
- ✅ **Business validations** — interviews only for shortlisted applications
- ✅ **Default resume** — auto-selected when applying for jobs
- ✅ **Enum validation** — JobType, JobStatus, Gender, ApplicationStatus, InterviewMode, InterviewResult

---

## Design Decisions

| Decision | Choice | Reason |
|---|---|---|
| Response format | `ApiResponse<T>` | Consistent JSON structure |
| Salary field | `BigDecimal` | Exact decimal — no precision errors |
| Soft delete | `active = false` | Data preserved, not lost |
| Enums in DB | `EnumType.STRING` | Human readable values |
| Lazy loading | `FetchType.LAZY` | Better performance |
| File storage | File path as String | Simple, no DB bloat |
| Exception handling | `@RestControllerAdvice` | Centralized, consistent errors |

---

## Getting Started

### Prerequisites
- Java 17 or higher
- PostgreSQL installed and running
- Maven installed

### Step 1 — Clone the repository
```bash
git clone https://github.com/your-username/job-portal-applicant-tracking-system.git
cd job-portal-applicant-tracking-system
```

### Step 2 — Create PostgreSQL database
```sql
CREATE DATABASE job_portal_db;
```

### Step 3 — Configure application.properties
```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/job_portal_db
spring.datasource.username=your_postgres_username
spring.datasource.password=your_postgres_password
```

### Step 4 — Run the application
```bash
mvn spring-boot:run
```

### Step 5 — Test the APIs
Base URL: http://localhost:8080
Example:  http://localhost:8080/api/roles
---

## Project Flow
Admin creates Roles
↓
Admin creates Users (assigns roles)
↓
Recruiter creates Company
↓
Company posts Jobs
↓
Candidate registers Profile
↓
Candidate uploads Resume
↓
Candidate applies for Job
↓
Recruiter reviews Application
↓
Application marked as Shortlisted
↓
Interview Scheduled
↓
Interview Result updated
↓
Candidate Selected or Rejected

---

## API Response Format

Every API returns a consistent response:

```json
{
    "success": true,
    "message": "Job created successfully",
    "data": {
        "id": 1,
        "title": "Java Developer",
        "salary": 75000.00,
        "status": "OPEN"
    },
    "timestamp": "2024-01-15T10:30:00"
}
```

Error response:
```json
{
    "success": false,
    "message": "Job not found with id: 1",
    "data": null,
    "timestamp": "2024-01-15T10:30:00"
}
```

---
