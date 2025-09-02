package org.intensiv;

import lombok.extern.slf4j.Slf4j;
import org.intensiv.controller.UserController;
import org.intensiv.dao.UserDAO;
import org.intensiv.dao.UserDAOImpl;
import org.intensiv.service.UserService;
import org.intensiv.util.HibernateUtil;

@Slf4j
public class Main {
    public static void main(String[] args) {
        UserController userController = null;

        try {
            log.info("Запуск user-service");

            // Инициализация DAO
            UserDAO userDAO = new UserDAOImpl(HibernateUtil.getSessionFactory());

            // Инициализация сервисов
            UserService userService = new UserService(userDAO);

            // Инициализация контроллера
            userController = new UserController(userService);

            // Запуск приложения
            userController.runApplication();

        } catch (Exception e) {
            log.error("Критическая ошибка при запуске приложения", e);
        } finally {
            log.info("Завершение работы user-service");

            // Закрытие ресурсов
            if (userController != null) {
                userController.close();
            }
            HibernateUtil.shutdown();
        }
    }
}
