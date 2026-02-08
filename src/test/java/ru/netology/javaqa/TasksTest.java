package ru.netology.javaqa;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class TasksTest {

    @Test
    public void testSimpleTaskMatches() {
        SimpleTask task = new SimpleTask(1, "Купить молоко");

        Assertions.assertTrue(task.matches("молоко"));
        Assertions.assertTrue(task.matches("Купить"));
        Assertions.assertFalse(task.matches("яйца"));
        Assertions.assertFalse(task.matches(""));
    }

    @Test
    public void testEpicMatches() {
        String[] subtasks = {"Молоко", "Яйца", "Хлеб"};
        Epic epic = new Epic(2, subtasks);

        Assertions.assertTrue(epic.matches("Молоко"));
        Assertions.assertTrue(epic.matches("Яйца"));
        Assertions.assertTrue(epic.matches("Хлеб"));
        Assertions.assertFalse(epic.matches("Сыр"));
        Assertions.assertFalse(epic.matches(""));
    }

    @Test
    public void testMeetingMatches() {
        Meeting meeting = new Meeting(3, "Выкатка приложения",
                "НетоБанк", "Вторник утром");

        Assertions.assertTrue(meeting.matches("Выкатка"));
        Assertions.assertTrue(meeting.matches("НетоБанк"));
        Assertions.assertTrue(meeting.matches("приложения"));
        Assertions.assertFalse(meeting.matches("Вторник"));
        Assertions.assertFalse(meeting.matches("утром"));
        Assertions.assertFalse(meeting.matches(""));
    }

    @Test
    public void testEquals() {
        Task task1 = new SimpleTask(1, "Задача 1");
        Task task2 = new SimpleTask(1, "Задача 2");
        Task task3 = new SimpleTask(2, "Задача 1");

        Assertions.assertEquals(task1, task2);
        Assertions.assertNotEquals(task1, task3);
    }
}