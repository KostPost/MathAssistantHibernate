# MathAssistant
MathAssistant is a Java-based application that provides a platform for managing mathematical equations. Using Hibernate ORM for database interactions, it enables users to add, find, and validate mathematical equations with ease.

# Features
Equation Management: Users can add new equations to the system, which are then stored in a database for future retrieval.
Equation Validation: The application includes a functionality to check the format of the entered equations and validates them before adding them to the database.
Equation Retrieval: Users can search for equations based on the equation text, the root value, or the number of roots.
Hibernate Integration: MathAssistant uses Hibernate ORM for object-relational mapping, allowing for smooth database transactions.

# Components
Equation Class: Represents the model of an equation, including attributes such as the equation text, the root, and the number of roots.
CheckEquation Class: Validates the format and syntax of equations entered by the user, ensuring they meet the required structure for mathematical equations.
EquationDao Class: Data Access Object that provides an abstract interface to the database. It includes methods to perform operations like adding, searching, and listing all equations.
HibernateUtil Class: A utility class that sets up the Hibernate SessionFactory, which is then used throughout the application to manage database sessions.
Main Class: Contains the main method which is the entry point of the application. It handles user interactions and orchestrates the flow of the program.

# Workflow
Upon launching, the user is presented with options to add an equation, find equations, or exit the program.
If adding an equation, the user inputs the equation, which is then validated and, if unique, added to the database.
If finding an equation, the user can search based on different criteria, and the matching results from the database are displayed.
Users can list all stored equations at any time.
The application maintains a Hibernate session throughout its operation, ensuring efficient database interaction.
