    ## Drones

-----------------------------------------------------

:scroll: **START**

### Introduction

There is a major new technology that is destined to be a disruptive force in the field of transportation: **drones**. Just as the mobile phone allowed developing countries to leapfrog older technologies for personal communication, the drone has the potential to leapfrog traditional transportation infrastructure.

Useful drone functions include delivery of small items that are needed in locations with difficult access.

-------------------------------------------------------

### Requirements

While implementing your solution **please take care of the following requirements**: 

#### Functional requirements

- There is no need for UI;
- Prevent the drone from being loaded with more weight that it can carry;
- Prevent the drone from being in LOADING state if the battery level is **below 25%**;
- Introduce a periodic task to check drones battery levels and create history/audit event log for this.

---------------------------------------------------------

#### Non-functional requirements

- Input/output data must be in JSON format;
- Your project must be buildable and runnable;
- Your project must have a README file with build/run/test instructions (use DB that can be run locally, e.g. in-memory, via container);
- Required data must be preloaded in the database.
- JUnit tests are optional but advisable (if you have time);
- Advice: Show us how you work through your commit history.

-----------------------------------------------------------

#### Features supported
* Add drone
* update drone
* Query about drone battery level by ``droneId``
* Query about available drones
* Query all about drones
* Query about  drone details ``droneId``
* Query about drone load weight
* Create Medication
* Update Medication
* Query about Medicine details by ``medicationId``
* Query about all Medications by a drone ``droneId``
* Load drone with one or many medications at a time
* Query about histories activities
* Query about activity histories by drone
* Query about activity histories by medication (in case of multiple medication trips. ex: a medication is shipped to a wrong destination and returned and then ship again...) 
* Start activity
* Check status and other data of history activity by ``activityHistoryId``
* Check all loaded medicines for a drone by ``droneId``
* upload image medication file and rename it randomly 
* CRUD Apis 
* Validate non-functional behaviors

#### Cron jobs
* Check drones battery level every 1 min
* Every 2 mins move the drone state from ```LOADED,
  DELIVERING,
  DELIVERED,
  RETURNING,IDLE``` to simulate finishing the drone trip

#### API Docs 
 - SWAGGER URL: http://127.0.0.1:8050/api/v1/swagger-ui/index.html#
#### RUN
## 1- Java -jar drones.jar # run IN-MEMORY database
## 2.1- pull dock image from registry
## 2.1- Run docker run --d --name drone:last
#### OPERATE
##### Step 1: Register drones
##### Step 2: create medications on the system
##### Step 3: load drone with medications
###### Step 3-1: provide each medication valid data
###### Step 3-2: provide data about the origin and destination
###### finally "SUBMIT". the drone is leaving... Congrats!!!

that's all!!
:scroll: **END** 
