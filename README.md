# oxygen-account
Oxygen Feedback Internship 2023

![Java CI with Maven](https://github.com/oxygenxml-incubator/oxygen-account/workflows/Java%20CI%20with%20Maven/badge.svg)
[![Quality Gate Status](https://sonarcloud.io/api/project_badges/measure?project=oxygen-account&metric=alert_status)](https://sonarcloud.io/summary/new_code?id=oxygen-account)

    This application epitomizes a comprehensive project encompassing user registration, login, and profile display functionalities, meticulously developed utilizing a plethora of technologies such as Java Spring, JavaScript, React, MySQL, Flyway, H2 Database, Maven, and JUnit.

    The project is well-rounded, ensuring all conceivable types of exceptions are appropriately handled. The application is designed with built-in mechanisms to throw exceptions where needed, enhancing its robustness and reliability.

Feature Set:
Profile Editing: Users are permitted to edit their profiles solely by utilizing the designated edit button, limiting unauthorized access and alterations. Currently, only modifications to the user’s name are facilitated.
Password Management: Users can conveniently change their passwords by entering the existing and new passwords and confirming the new one. Exception handling mechanisms are instituted here to manage any discrepancies or errors occurring during the process.
Account Deletion and Recovery: Users have the option to delete their accounts, which will be permanently removed from the database after a 7-day grace period. During this interval, users can opt to recover their accounts by clicking the ‘recover’ button.
Email Confirmation System: To authenticate user accounts, an email confirmation system is integrated. Upon initial registration, users are assigned a "new" status in the database, restricting login capabilities. Once users verify their accounts through the confirmation link sent to their email, their status transitions to "active," granting them access to login.

Security and Accessibility:
Exception Handling: Comprehensive exception handling is integral to the application, managing various exception types and ensuring smooth user interactions.
Status Management: The application employs a status management system, categorizing newly registered users as "new" and limiting their access until email verification is completed.
Usability:
This application stands out due to its user-friendly interface and a well-thought-out set of features, focusing on user experience and accessibility, ensuring secure and efficient interactions.

Conclusion:
This multifaceted application, developed with advanced technologies and meticulous attention to user experience and security, offers a seamless and secure environment for user registration, profile management, and accessibility. The detailed consideration given to exception handling and user status management underscores its reliability and user-centric approach.

