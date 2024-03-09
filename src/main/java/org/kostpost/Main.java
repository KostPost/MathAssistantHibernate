package org.kostpost;

import net.objecthunter.exp4j.Expression;
import net.objecthunter.exp4j.ExpressionBuilder;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;

import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Main {
    public static void main(String[] args) {

        java.util.logging.Logger.getLogger("org.hibernate").setLevel(Level.OFF);
        java.util.logging.Logger.getLogger("org.hibernate").setLevel(Level.SEVERE);
        Logger.getLogger("org.hibernate").setLevel(java.util.logging.Level.OFF);
        Logger logger = Logger.getLogger("org.hibernate.SQL");
        logger.setLevel(Level.OFF);


        String doAction;
        Scanner askAction = new Scanner(System.in);

        do {
            System.out.print("\n1 - Add equation\n2 - Find equation\n3 - Exit\n-> ");
            doAction = askAction.nextLine();

            switch (doAction) {

                case "1" -> {
                    Scanner equationInputScanner = new Scanner(System.in);
                    String userInputEquation;
                    Equation currentEquation = null;
                    EquationService equationService = new EquationService();

                    while (true) {
                        System.out.println("\n--- New Equation Input ---");
                        System.out.println("Enter a new equation:");
                        userInputEquation = equationInputScanner.nextLine();

                        String checkedEquation = equationService.checkEquation(userInputEquation);
                        if (checkedEquation == null) {
                            System.out.println("Invalid equation format. Please try again.");
                            continue;
                        }

                        if (findEquation(checkedEquation) != null) {
                            System.out.println("This equation already exists in the database.");
                            continue;
                        }
                        currentEquation = new Equation(checkedEquation);
                        System.out.println("Equation accepted: " + checkedEquation);
                        break;
                    }

                    System.out.println("\nWould you like to enter the root of the equation?");
                    System.out.println("1 - Yes\n2 - No");
                    String rootChoice;

                    do {
                        rootChoice = equationInputScanner.nextLine();
                        if (!rootChoice.equals("1") && !rootChoice.equals("2")) {
                            System.out.println("Invalid choice. Please enter 1 for Yes or 2 for No.");
                        }
                    } while (!rootChoice.equals("1") && !rootChoice.equals("2"));

                    if (rootChoice.equals("1")) {
                        while (true) {
                            System.out.println("Enter the root (x-value):");
                            try {
                                double doubleEquationRoot = Double.parseDouble(equationInputScanner.nextLine());

                                String tempEquation = currentEquation.getEquation().replace("x", String.valueOf(doubleEquationRoot));
                                String[] parts = tempEquation.split("=", 2);

                                double resultLeftPart = evaluateExpressionPart(parts[0]);
                                double resultRightPart = evaluateExpressionPart(parts[1]);

                                if (resultLeftPart == resultRightPart) {
                                    currentEquation.setRootX(Double.toString(doubleEquationRoot));
                                    System.out.println("Correct root. Equation solved.");
                                    break;
                                } else {
                                    System.out.println("Wrong root. Please try again.");
                                }
                            } catch (NumberFormatException e) {
                                System.out.println("Invalid input for rootX. Please enter a valid number.");
                            }
                        }
                    } else {
                        currentEquation.setRootX(null);
                    }

                    int countX = 0;
                    for (int i = 0; i < currentEquation.getEquation().length(); i++) {
                        if (currentEquation.getEquation().charAt(i) == 'x') {
                            countX++;
                        }
                    }

                    currentEquation.setQuantityX(countX);

                    addEquation(currentEquation);

                    break;
                }

                case "2" -> {
                    Scanner findScanner = new Scanner(System.in);
                    while (true) {
                        System.out.println("\n--- Search Menu ---");
                        System.out.println("1. Find by equation.");
                        System.out.println("2. Find equations by a given root.");
                        System.out.println("3. Find equations by quantity root");
                        System.out.println("4. Exit");
                        System.out.print("Enter your choice (1-4): ");

                        String choice = findScanner.nextLine();
                        switch (choice) {
                            case "1" -> {
                                String findEquation;
                                do {
                                    System.out.print("\nEnter the equation to search for (or type 'exit' to quit): ");
                                    findEquation = findScanner.nextLine();

                                    if ("exit".equalsIgnoreCase(findEquation)) {
                                        System.out.println("Exiting search.");
                                        break;
                                    }

                                    SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();

                                    EquationDao equationDao = new EquationDao(sessionFactory);

                                    Equation equation = equationDao.findEquationByEquation(findEquation);

                                    if (equation != null) {
                                        System.out.printf("Found equation: %s \n", equation.getEquation());
                                        System.out.printf("RootX: %s, QuantityX: %d\n", equation.getRootX(), equation.getQuantityX());
                                        break;
                                    } else {
                                        System.out.println("Equation not found. Please try again.");
                                    }
                                } while (true);

                                break;
                            }

                            case "2" -> {
                                System.out.print("\nEnter the root to search for (or type 'exit' to quit): ");
                                String findRoot = findScanner.nextLine();

                                if ("exit".equalsIgnoreCase(findRoot)) {
                                    System.out.println("Exiting search.");
                                    break;
                                }

                                SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();

                                EquationDao equationDao = new EquationDao(sessionFactory);

                                List<Equation> equations = equationDao.findEquationsByRoot(findRoot);

                                if (!equations.isEmpty()) {
                                    System.out.println("Found equations with root " + findRoot + ":");
                                    for (Equation eq : equations) {
                                        System.out.println("Equation: " + eq.getEquation() + " | RootX: " + eq.getRootX() + " | QuantityX: " + eq.getQuantityX());
                                    }
                                } else {
                                    System.out.println("No equations found with the root " + findRoot + ".");
                                }
                            }


                            case "3" -> {
                                System.out.print("Enter the quantity of roots to search for: ");
                                int quantityRoot = findScanner.nextInt();
                                findScanner.nextLine(); // Очистка буфера после чтения числа

                                SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();

                                EquationDao equationDao = new EquationDao(sessionFactory);

                                List<Equation> equations = equationDao.findEquationsByQuantityRoot(quantityRoot);

                                if (!equations.isEmpty()) {
                                    System.out.println("Found equations with " + quantityRoot + " root(s):");
                                    for (Equation eq : equations) {
                                        System.out.println("Equation: " + eq.getEquation() + " | RootX: " + eq.getRootX() + " | QuantityX: " + eq.getQuantityX());
                                    }
                                } else {
                                    System.out.println("No equations found with " + quantityRoot + " root(s).");
                                }
                            }

                            default -> {
                                System.out.println("Invalid choice. Please enter a number between 1 and 3.");
                            }
                        }
                        if (choice.equals("4")) {
                            break;
                        }
                    }
                }


                default -> {
                    if (!doAction.equals("3"))
                        System.out.println("Wrong action");
                }

            }


        } while (!doAction.equals("3"));


    }

    private static double evaluateExpressionPart(String expressionPart) {
        Expression expression = new ExpressionBuilder(expressionPart).build();
        return expression.evaluate();
    }

    static void addEquation(Equation newEquation) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();

            session.save(newEquation);

            transaction.commit();
            System.out.println("The equation has been successfully added to the database\"");
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }

    static Equation findEquation(String searchEquation) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {

            Query<?> query = session.createQuery("FROM Equation WHERE equation = :searchEquation", Equation.class);
            query.setParameter("searchEquation", searchEquation);
            Equation result = (Equation) query.uniqueResult();

            if (result != null) {
                return result;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
