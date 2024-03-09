package org.kostpost;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

import java.util.List;

public class EquationDao {
    private final SessionFactory sessionFactory;

    public EquationDao(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public Equation findEquationByEquation(String equation) {
        try (Session session = sessionFactory.openSession()) {
            String hql = "FROM Equation WHERE equation = :equation";
            Query<Equation> query = session.createQuery(hql, Equation.class);
            query.setParameter("equation", equation);
            return query.uniqueResult(); // Используем uniqueResult для получения одного результата
        }
    }

    public List<Equation> findEquationsByRoot(String root) {
        try (Session session = sessionFactory.openSession()) {
            String hql = "FROM Equation WHERE rootX = :root";
            Query<Equation> query = session.createQuery(hql, Equation.class);
            query.setParameter("root", root);
            return query.list(); // Получаем список уравнений с заданным корнем
        }
    }

    public List<Equation> findEquationsByQuantityRoot(int quantityRoot) {
        try (Session session = sessionFactory.openSession()) {
            String hql = "FROM Equation WHERE quantityX = :quantityRoot";
            Query<Equation> query = session.createQuery(hql, Equation.class);
            query.setParameter("quantityRoot", quantityRoot);
            return query.list(); // Получаем список уравнений с заданным количеством корней
        }
    }
}

