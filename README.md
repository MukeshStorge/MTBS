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
      <a href="#how-it-works">How it works?</a>
    </li>
     <li>
      <a href="#api-endpoints-and-docs">API Endpoints and Docs</a>
    </li>
    <li><a href="#license">License</a></li>
  </ol>
</details>



<!-- ABOUT THE PROJECT -->
## About The Project

**Movie ticket booking system with below features**

![image](https://user-images.githubusercontent.com/65528044/127213353-debc698f-3e14-4f8c-a606-2e35c436e015.png)


### Tech Stack

* Java 8
* Spring Boot 2.4.5
* Lombok
* OpenAPI
* Redis 
* Postgresql

<!-- HLD -->
## HLD

![image](https://user-images.githubusercontent.com/65528044/127534456-7729a3aa-6936-486d-84ed-5af1b8c2fa70.png)




<!-- LLD -->
## LLD

![image](https://user-images.githubusercontent.com/65528044/127528231-95e773dc-22e9-4316-beae-d9850779be36.png)


<!-- How it works -->
## How It Works

## API Endpoints and Docs

* **Note:** Sample Data added for Users, Theaters, Branches, Screens, Shows, Seats, Movies !!! 

Swagger Ui http://localhost:8081/swagger-ui.html

### Booking end point & Sample request: 
http://localhost:8081/reserve/book

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

<!-- LICENSE -->
## License

Distributed under the MIT License. See [LICENSE][license-url] for more information.




<!-- MARKDOWN LINKS & IMAGES -->
[license-shield]: https://img.shields.io/github/license/ArcAlumni/url-shortener.svg?style=for-the-badge
[license-url]: https://github.com/ArcAlumni/url-shortener/blob/main/LICENSE

