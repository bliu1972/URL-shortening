# URL Shortener Service

## Table of Contents

- [Overview](#overview)
- [Design](#design)
- [Features](#features)
- [Technologies Used](#technologies-used)
- [Getting Started](#getting-started)
  - [Prerequisites](#prerequisites)
  - [Installation](#installation)
  - [Running the Application](#running-the-application)
- [API Endpoints](#api-endpoints)
- [Contributing](#contributing)
- [License](#license)

## Overview

The URL Shortener Service is a simple web application that allows users to shorten long URLs. It provides functionality to create shortened URLs and retrieve the original URLs using the shortened versions.

## Design

Refer to "URL Shortener.pptx" in this repository for all the design considerations

## Features

- Encode long URLs into shorter ones
- Decode shortened URLs to retrieve the original URL
- Simple and easy-to-use REST API

## Technologies Used

- Java 17
- Spring Boot
- Maven
- Docker

## Getting Started

### Prerequisites

- Java 17
- Maven
- Git
- Docker

### Installation

1. **Clone the repository:**

    ```sh
    git clone https://github.com/bliu1972/URL-shortening.git
    cd URL-shortening
    ```

2. **Build the project using Maven:**

    ```sh
    mvn clean package
    ```

### Running the Application

1. **Run the application locally:**

    ```sh
    mvn spring-boot:run
    ```

2. **Build Docker image:**

    ```sh
    docker build -t url-shortener-service .
    ```

3. **Run the Docker container:**

    ```sh
    docker run -p 9000:9000 url-shortener-service
    ```

## API Endpoints

### Encode URL

- **URL:** `/api/v1/encode`
- **Method:** `POST`
- **Request Body:**

    ```json
    {
        "originalUrl": "http://example.com/test"
    }
    ```

- **Response:**

    ```json
    {
        "originalUrl":"http://example.com/test",
        "shortenedUrl":"https://myshort.url/AAAAAB"
    }
    ```

### Decode URL

- **URL:** `/api/v1/decode`
- **Method:** `GET`
- **Request Parameters:**
    - `shortenedUrl`: The shortened URL to decode

- **Response:**

    ```json
    {
        "originalUrl": "http://example.com/test",
        "shortenedUrl": "https://myshort.url/AAAAAB"
    }
    ```

## Contributing

Contributions are welcome! Please open an issue or submit a pull request for any changes.

1. **Fork the repository**
2. **Create a new branch**

    ```sh
    git checkout -b feature/new-feature
    ```

3. **Make your changes**
4. **Commit your changes**

    ```sh
    git commit -m "Add new feature"
    ```

5. **Push to the branch**

    ```sh
    git push origin feature/new-feature
    ```

6. **Open a pull request**

## License

None
