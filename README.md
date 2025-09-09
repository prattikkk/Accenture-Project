# BankAPI

A Spring Boot RESTful API for a mini banking system that provides essential banking functionalities such as account management, transaction processing, and fund transfers.

## Features

- **Account Management**: Create, retrieve, and update bank accounts
- **Transaction Processing**: Record and manage financial transactions
- **Fund Transfers**: Transfer money between accounts securely
- **RESTful API**: Clean and intuitive REST endpoints
- **Data Validation**: Input validation using Jakarta Bean Validation
- **Database Integration**: Uses MySQL with Spring Data JPA

## Technologies Used

- **Java**: 17
- **Spring Boot**: 3.1.4
- **Spring Data JPA**: For database operations
- **MySQL**: Database
- **Maven**: Build tool
- **Spring Web**: For REST API
- **Jakarta Validation**: For input validation

## Prerequisites

- Java 17 or higher
- Maven 3.6+
- MySQL 8.0+

## Installation

1. **Clone the repository**:
   ```bash
   git clone https://github.com/prattikkk/Accenture-Project.git
   cd Accenture-Project
   ```

2. **Configure Database**:
   - Create a MySQL database
   - Update `src/main/resources/application.properties` with your database credentials:
     ```properties
     spring.datasource.url=jdbc:mysql://localhost:3306/your_database_name
     spring.datasource.username=your_username
     spring.datasource.password=your_password
     spring.jpa.hibernate.ddl-auto=update
     ```

3. **Build the project**:
   ```bash
   mvn clean install
   ```

4. **Run the application**:
   ```bash
   mvn spring-boot:run
   ```

The API will be available at `http://localhost:8080`

## API Endpoints

### Account Management

| Method | Endpoint | Description |
|--------|----------|-------------|
| POST | `/api/accounts` | Create a new account |
| GET | `/api/accounts` | Get all accounts |
| POST | `/api/accounts/get` | Get account by ID (send JSON: `{"id": 1}`) |
| PUT | `/api/accounts/update` | Update an existing account |

### Transaction Management

| Method | Endpoint | Description |
|--------|----------|-------------|
| POST | `/api/transactions` | Create a new transaction |
| GET | `/api/transactions` | Get all transactions |
| POST | `/api/transactions/transfer` | Transfer funds between accounts |

### Sample API Usage

#### Create Account
```bash
curl -X POST http://localhost:8080/api/accounts \
  -H "Content-Type: application/json" \
  -d '{
    "accountNumber": "123456789",
    "accountType": "SAVINGS",
    "balance": 1000.00,
    "customerName": "John Doe"
  }'
```

#### Transfer Funds
```bash
curl -X POST http://localhost:8080/api/transactions/transfer \
  -H "Content-Type: application/json" \
  -d '{
    "fromAccountId": 1,
    "toAccountId": 2,
    "amount": 500.00
  }'
```

## Project Structure

```
BankAPI/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/example/API/
│   │   │       ├── controller/     # REST controllers
│   │   │       ├── model/          # JPA entities
│   │   │       ├── repository/     # Data repositories
│   │   │       ├── service/        # Business logic
│   │   │       ├── dto/            # Data transfer objects
│   │   │       ├── response/       # API response wrappers
│   │   │       └── config/         # Configuration classes
│   │   └── resources/
│   │       └── application.properties
│   └── test/
│       └── java/
│           └── com/example/API/
│               └── ApiApplicationTests.java
├── .gitignore
├── pom.xml
├── mvnw
├── mvnw.cmd
└── README.md
```

## Contributing

1. Fork the repository
2. Create a feature branch (`git checkout -b feature/AmazingFeature`)
3. Commit your changes (`git commit -m 'Add some AmazingFeature'`)
4. Push to the branch (`git push origin feature/AmazingFeature`)
5. Open a Pull Request

## License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

## Acknowledgments

- Built with Spring Boot
- Inspired by modern banking API designs
- Thanks to the Spring community for excellent documentation
