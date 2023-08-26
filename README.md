# spring-boot-rest-api-pratices

## 1. spring-rest-exception-handling
   - Example Api Error Response:
     - ```json
       {
        "guid": "8f421de8-b76f-4b5a-b711-fe4e74b84817",
        "errorCode": "PERSON_NOT_FOUND",
        "message": "Person with 21 not found!",
        "statusCode": 404,
        "statusName": "NOT_FOUND",
        "path": "/persons/21",
        "method": "GET",
        "timestamp": "2023-08-26T23:23:24.131032"
       }
       ```
   - Explanation of the format:

     - *guid* — unique global identifier of the error, this field is useful for searching errors in a large log.
     - *errorCode* — an application specific error code derived from business logic rules.
     - *message* — description of an error.
     - *statusCode* — HTTP status code.
     - *statusName* — full name of the HTTP status code.
     - *path* — URI of the resource where the error occurred.
     - *method* — used HTTP method.
     - *timestamp* — timestamp of the error creation.
