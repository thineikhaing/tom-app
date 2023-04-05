

| URL / ENDPOINT               | VERB   | Body                                                                                                               | DESCRIPTION                 |
|------------------------------|--------|--------------------------------------------------------------------------------------------------------------------|-----------------------------|
| /api/auth/signup             | POST   | username, email, password                                                                                          | Registration                |
| /api/auth/login              | POST   | username, password                                                                                                 | Login                       |
| /api/main/all                | GET    |                                                                                                                    | Public Content              |
| /api/main/user               | GET    |                                                                                                                    | User Content                |
| /api/main/admin              | GET    |                                                                                                                    | Admin Content               |
| /api/departments             | POST   | name, details                                                                                                      | Create Department           |
| /api/departments/id          | PUT    | name, details, departHeadId                                                                                        | Update Department           |
| /api/departments/id          | GET    |                                                                                                                    | Show a Department           |
| /api/departments             | GET    |                                                                                                                    | All Departments             |
| /api/departments/id          | DELETE |                                                                                                                    | Delete a Department         |
| /api/employees               | POST   | fullName, email, address, contactNumber, <br>dateOfBirth,employmentStartDate", <br>employmentEndDate, departmentId | Create am Employee Profile  |
| /api/employees/id            | PUT    | fullName, email, address, contactNumber, <br>dateOfBirth,employmentStartDate", <br>employmentEndDate, departmentId | Update an Employee          |
| /api/employees/id            | GET    |                                                                                                                    | Show an Employee            |
| /api/employees               | GET    |                                                                                                                    | All Employees               |
| /api/employees/id            | DELETE |                                                                                                                    | Delete an Employee          |
| /api/employees/department/id | GET    |                                                                                                                    | Get Employees by Department |