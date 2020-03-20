# Mini Twitter Rest Api
This application serves as a backend server exposing several apis for tweets management.

## Meta info
Application showcases knowledge of:
- Java programming language
- Spring framework
  - Application setup and initialization
  - Annotation utilization
    - Controller and Service definition
    - Exposed requests mapping
      - CRUD (POST, GET)
      - Receiving objects as body and path variable
    - Dependency injection (on constructor)
    - Cross origin handling for environments
    - Integration tests
    - Http security configuration (interceptor) set with authentication provider and allowing only specific endpoint api
- General
  - REST calls
    - GET with path variable
    - POST with body
  - Having git commit messages readable
- Concepts
  - Separation of concerns
  - "N-tier architecture"
    - Controllers expose api endpoints
    - Business logic is found inside services
    - Data layer is accessed from repositories
- Operations
  - Setting environment variables (for production and development differentiation)
  - Deploying application (on Heroku)

## Requirements
1. Java - 1.8
1. Maven - 3.x.x

## Development server
