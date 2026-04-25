\# Docify — Document Generation Portal (Backend)



A Spring Boot REST API for generating dynamic PDF documents from templates.



\## Features

\- JWT based authentication (register/login)

\- Create and manage document templates with dynamic placeholders

\- Generate PDF documents by filling placeholder values

\- Download and view generated documents

\- Document generation history per user



\## Tech Stack

\- Java 17

\- Spring Boot 3.5

\- Spring Security + JWT

\- Spring Data JPA

\- MySQL

\- iText7 (PDF generation)



\## Getting Started



\### Prerequisites

\- Java 17

\- MySQL 8.0

\- Maven



\### Setup

1\. Clone the repo

2\. Create a MySQL database called `docportal`

3\. Update `src/main/resources/application.properties` with your DB credentials

4\. Run `mvn spring-boot:run`



\### API Endpoints

| Method | Endpoint | Description |

|---|---|---|

| POST | /api/auth/register | Register new user |

| POST | /api/auth/login | Login and get JWT token |

| GET | /api/templates | Get all templates |

| POST | /api/templates | Create template |

| PUT | /api/templates/{id} | Update template |

| DELETE | /api/templates/{id} | Delete template |

| POST | /api/documents/generate | Generate PDF document |

| GET | /api/documents/history | Get user's document history |

| GET | /api/documents/download/{id} | Download PDF |



\## Frontend

\[Docify Frontend](https://github.com/Roshini864/docify-frontend)

