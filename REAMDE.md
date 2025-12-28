# Library Management System
A console-based library management system built in Java.  
Supports adding books and users, changing user and book status, borrowing and returning books, removing loans, and viewing active and historical loans.

## Project Structure
```text
com.vladl.library
├── app
│    └── Main.java # Application entry point
├── dto
│    └── LoanDto.java # DTO for loan data
├── mapper
│    └── LoanMapper.java # Maps between LoanDto and Loan
├── menu
│    └── ConsoleMenu.java # Console-based user interface
├── model
│    ├── Book.java
│    ├── BookStatus.java
│    ├── Loan.java
│    ├── LoanStatus.java
│    ├── User.java
│    └── UserStatus.java
├── repository
│    ├── BookRepository.java
│    ├── LoanRepository.java
│    ├── UserRepository.java
│    ├── json
│ │  ├── JsonBookRepository.java
│ │  ├── JsonLoanRepository.java
│ │  └── JsonUserRepository.java
│ └── memory
│    ├── InMemoryBookRepository.java
│    ├── InMemoryLoanRepository.java
│    └── InMemoryUserRepository.java
├── service
│    ├── BookService.java
│    ├── LoanService.java
│    └── UserService.java
└── storage
└── JsonStorage.java # Utility for JSON read/write
```
## Features
- Add, mark removed, mark active, and view books
- Add, mark removed, mark available, and view users
- Borrow books
- Return books
- View active loans
- View all loans
- Persistent storage in JSON files (`books.json`, `users.json`, `loans.json`)
- In-memory repositories for testing purposes

## Example JSON Data
### `books.json`
```json
[ {
"id" : 1,
"title" : "Book_title",
"author" : "Book_author",
"status" : "AVAILABLE",
"libraryPurchaseDate" : "2025-12-28"
}, {
"id" : 2,
"title" : "Book_title2",
"author" : "Book_author2",
"status" : "REMOVED",
"libraryPurchaseDate" : "2025-12-28"
} ]
```
### `users.json`
```json
[ {
  "id" : 1,
  "name" : "User_name",
  "status" : "ACTIVE"
}, {
  "id" : 2,
  "name" : "User_name2",
  "status" : "DELETED"
} ]
```
### `loans.json`
```json
[ {
  "id" : 1,
  "bookId" : 1,
  "userId" : 1,
  "borrowDate" : "2025-12-28",
  "returnDate" : "2025-12-29",
  "status" : "ACTIVE"
} ]
```

# Compile and run
java -cp target/library-management-system.jar com.vladl.library.app.Main

## Tests
Written with JUnit 5
Test cases cover:
- Borrowing books
- Returning books
- Adding books and users
- Error handling (book already borrowed, deleted user)

Tests use InMemoryRepository to avoid file dependencies

## Notes
- Uses Optional to safely handle missing objects
- Clear separation of DTO ↔ Entity
- Loan history retains records for deleted books/users
