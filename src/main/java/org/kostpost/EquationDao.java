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
            return query.uniqueResult(); 
        }
    }

    public List<Equation> findEquationsByRoot(String root) {
        try (Session session = sessionFactory.openSession()) {
            String hql = "FROM Equation WHERE rootX = :root";
            Query<Equation> query = session.createQuery(hql, Equation.class);
            query.setParameter("root", root);
            return query.list();
        }
    }

    public List<Equation> findEquationsByQuantityRoot(int quantityRoot) {
        try (Session session = sessionFactory.openSession()) {
            String hql = "FROM Equation WHERE quantityX = :quantityRoot";
            Query<Equation> query = session.createQuery(hql, Equation.class);
            query.setParameter("quantityRoot", quantityRoot);
            return query.list();
        }
    }

    public List<Equation> findAllEquations() {
        try (Session session = sessionFactory.openSession()) {
            String hql = "FROM Equation";
            Query<Equation> query = session.createQuery(hql, Equation.class);
            return query.list();
        }
    }
}

