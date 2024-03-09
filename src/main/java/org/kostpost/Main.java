package org.kostpost;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import org.hibernate.query.Query;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import java.util.List;
import java.util.Objects;
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


        ScriptEngineManager manager = new ScriptEngineManager();
        ScriptEngine engine = manager.getEngineByName("JavaScript");



        String doAction;
        Scanner askAction = new Scanner(System.in);

        do {
            System.out.print("\n1 - Add equation\n2 - Find equation\n3 - Exit\n-> ");
            doAction = askAction.nextLine();

            switch (doAction) {

                case "1" -> {
                    Scanner askEquation = new Scanner(System.in);
                    String askNewEquation = null;
                    Equation newEquation = null;
                    EquationService equationService = new EquationService();


                    do {
                        System.out.println("Enter a new equation");
                        askNewEquation = askEquation.nextLine();

                        try {
                            askNewEquation = equationService.checkEquation(askNewEquation);
                        } catch (IllegalArgumentException e) {
                            System.out.println(e.getMessage());
                            askNewEquation = null;
                        }

                        if(askNewEquation != null) {

                            newEquation = findEquation(askNewEquation);

                            if (newEquation != null) {
                                System.out.println("This equation is already exist");
                            } else {
                                newEquation = new Equation(askNewEquation);
                                break;
                            }
                        }
                        String rootAction;
                        Scanner askRootAction = new Scanner(System.in);

                        do {
                            System.out.println("Would you like to enter the root of the equation?\n1 - Yes\t2 - No");
                            rootAction = askRootAction.nextLine();
                            if(!Objects.equals(rootAction, "1") || !rootAction.equals("2")){
                                System.out.println("Wrong choice");
                            }
                        }while (Objects.equals(rootAction, "1") || Objects.equals(rootAction, "2"));

                        double rootX;
                        Scanner askRootX = new Scanner(System.in);

                        if(Objects.equals(rootAction, "1")){

                        }

                    } while (true);

                    break;
                }

                case "2" -> {
                    int qwe = 2;
                    break;
                }

                default -> {
                    if (!doAction.equals("3"))
                        System.out.println("Wrong action");
                }

            }


        } while (!doAction.equals("3"));


    }


    static void addEquation() {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            // Начало транзакции
            transaction = session.beginTransaction();

            // Создание экземпляра класса Equation
            Equation equation = new Equation();
            equation.setEquation("2*x+5=17");

            // Сохранение объекта в базе данных
            session.save(equation);

            // Фиксация транзакции
            transaction.commit();
            System.out.println("Уравнение успешно добавлено в базу данных");
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
