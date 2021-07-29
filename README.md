# MTBS - Movie Ticket Booking System 

<!-- TABLE OF CONTENTS -->
<details open="open">
  <summary>Table of Contents</summary>
  <ol>
    <li>
      <a href="#about-the-project">About The Project</a>
      <ul>
        <li><a href="#tech-stack">Tech Stack</a></li>
      </ul>
    </li>
      <li>
      <a href="#hld">HLD - High Level Design</a>
    </li>
     <li>
      <a href="#lld">LLD - Low Level Design</a>
    </li>
     <li>
      <a href="#tech-stack">Points to Highlight</a>
     </li>
      </ul>
    </li>
     <li>
      <a href="#api-endpoints-and-docs">API Endpoints and Docs</a>
    </li>
    <li><a href="#license">License</a></li>
  </ol>
</details>



<!-- ABOUT THE PROJECT -->
## About The Project

**Movie ticket booking system with below features,**

![image](https://user-images.githubusercontent.com/65528044/127213353-debc698f-3e14-4f8c-a606-2e35c436e015.png)


### Tech Stack

* **Java 8** (Pre-Install)
* **Redis**  (Pre-Install)
* **Maven** (Pre-Install)
* **Postgresql**  (Pre-Install)
* **Spring Boot 2.4.5**
* **Lombok**
* **OpenAPI**


<!-- HLD -->
## HLD

![image](https://user-images.githubusercontent.com/65528044/127534456-7729a3aa-6936-486d-84ed-5af1b8c2fa70.png)




<!-- LLD -->
## LLD

### Redis Data Model
Below selected Redis ID is a combination of TheaterId+ShowId+ScreenId+SeatId+Time With MilliSecond. Used to group the concurrent request for that moment. This will be helpfull to find the concurrency by taking the **disjoint set using union find**. This data will be eventually evicted from system. Refer: [RedisConfig.java][redisconf] 

![image](https://user-images.githubusercontent.com/65528044/127560617-026402de-c518-4b7b-aabc-5f2e14736195.png)

### ER-Diagram

![image](https://user-images.githubusercontent.com/65528044/127528231-95e773dc-22e9-4316-beae-d9850779be36.png)


## API Endpoints and Docs

### **Note:** 
* Sample Data added for Users, Theaters, Branches, Screens, Shows, Seats, Movies !!!. Refer:  [Data.sql][data] 
* To change DB config/App port/Max seat size/Log path/Redis port/bucket. Refer: [Application.properties][application] 
* Use Swagger Ui http://localhost:8081/swagger-ui.html to access APIs.
* Module based package [grouping][group] done for readablity.
* Start with [ReservationController.java][reservationcontroller] for ticket booking logic.



### Ticket booking endpoint & Sample request: 
<p>
**Post:** http://localhost:8081/reserve/

{
  "theaterId": 1,
  "branchId": 1,
  "screenId": 1,
  "seatIds": [
    1,2,3
  ],
  "user": {
    "id": 1,
    "firstName": "Doni",
    "lastName": "Singh",
    "email": "doni@gmail.com",
    "activeStatus": true
  }
}
</p>

## Points to Hightlight

* It supports multiple Theaters, so we can use this as SAAS.
* Used Union finding disjoint set for concurrency handling.
* Configurable errors and logs. 


<!-- LICENSE -->
## License

Distributed under the MIT License. See [LICENSE][license-url] for more information.




<!-- MARKDOWN LINKS & IMAGES -->
[license-url]: https://github.com/MukeshStorge/MTBS/blob/main/LICENSE
[data]: https://github.com/MukeshStorge/MTBS/blob/main/src/main/resources/data.sql
[application]: https://github.com/MukeshStorge/MTBS/blob/main/src/main/resources/application.properties
[reservationcontroller]: https://github.com/MukeshStorge/MTBS/blob/main/src/main/java/com/mtbs/reservation/ReservationController.java
[redisconf]: https://github.com/MukeshStorge/MTBS/blob/main/src/main/java/com/mtbs/configs/RedisConfig.java
[group]: https://github.com/MukeshStorge/MTBS/tree/main/src/main/java/com/mtbs
