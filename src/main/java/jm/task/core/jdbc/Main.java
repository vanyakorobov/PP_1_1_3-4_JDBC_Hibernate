package jm.task.core.jdbc;

import jm.task.core.jdbc.dao.UserDao;
import jm.task.core.jdbc.dao.UserDaoHibernateImpl;
import jm.task.core.jdbc.dao.UserDaoJDBCImpl;
import jm.task.core.jdbc.model.User;

public class Main {
    public static void main(String[] args) {
        UserDao userDao = new UserDaoHibernateImpl();
        userDao.createUsersTable();

        userDao.saveUser("Иван", "Пупкин", (byte) 30);
        userDao.saveUser("Василий", "Губкин", (byte) 25);
        userDao.saveUser("Никита", "Пеньков", (byte) 35);
        userDao.saveUser("Боб", "Ермолаев", (byte) 40);
        System.out.println("Добавлены пользователи в базу");

        System.out.println("Пользователи в базе:");
        for (User user : userDao.getAllUsers()) {
            System.out.println(user);
        }
        userDao.cleanUsersTable();
        System.out.println("Таблица пользователей очищена");

        userDao.dropUsersTable();
        System.out.println("Таблица пользователей удалена");
    }
}
