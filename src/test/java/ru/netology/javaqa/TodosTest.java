package ru.netology.javaqa;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class TodosTest {

    @Test
    public void shouldAddThreeTasksOfDifferentType() {
        SimpleTask simpleTask = new SimpleTask(5, "Позвонить родителям");

        String[] subtasks = { "Молоко", "Яйца", "Хлеб" };
        Epic epic = new Epic(55, subtasks);

        Meeting meeting = new Meeting(
                555,
                "Выкатка 3й версии приложения",
                "Приложение НетоБанка",
                "Во вторник после обеда"
        );

        Todos todos = new Todos();

        todos.add(simpleTask);
        todos.add(epic);
        todos.add(meeting);

        Task[] expected = { simpleTask, epic, meeting };
        Task[] actual = todos.findAll();
        Assertions.assertArrayEquals(expected, actual);
    }

    @Test
    public void testSearch() {
        SimpleTask task1 = new SimpleTask(1, "Купить молоко");
        SimpleTask task2 = new SimpleTask(2, "Позвонить маме");

        String[] subtasks = {"Молоко", "Яйца", "Хлеб"};
        Epic epic = new Epic(3, subtasks);

        Meeting meeting = new Meeting(4, "Обсуждение проекта",
                "Новый проект", "Завтра");

        Todos todos = new Todos();
        todos.add(task1);
        todos.add(task2);
        todos.add(epic);
        todos.add(meeting);

        // Поиск "молоко" (строчная)
        Task[] result = todos.search("молоко");
        Task[] expectedForMilk = { task1 };
        Assertions.assertArrayEquals(expectedForMilk, result);

        // Поиск "Молоко" (заглавная)
        result = todos.search("Молоко");
        Task[] expectedForMilkCapital = { epic };
        Assertions.assertArrayEquals(expectedForMilkCapital, result);

        // Поиск "проект" - meeting
        result = todos.search("проект");
        Task[] expectedForProject = { meeting };
        Assertions.assertArrayEquals(expectedForProject, result);

        // Поиск "маме" - только task2
        result = todos.search("маме");
        Task[] expectedForMom = { task2 };
        Assertions.assertArrayEquals(expectedForMom, result);

        // Поиск "сыр" - ничего
        result = todos.search("сыр");
        Task[] expectedForCheese = {};
        Assertions.assertArrayEquals(expectedForCheese, result);

        // Пустой запрос - ничего
        result = todos.search("");
        Task[] expectedEmpty = {};
        Assertions.assertArrayEquals(expectedEmpty, result);
    }

    @Test
    public void testSearchMultipleMatches() {
        SimpleTask task1 = new SimpleTask(1, "Купить молоко");
        SimpleTask task2 = new SimpleTask(2, "Купить хлеб");
        SimpleTask task3 = new SimpleTask(3, "Купить масло");

        Todos todos = new Todos();
        todos.add(task1);
        todos.add(task2);
        todos.add(task3);

        Task[] result = todos.search("Купить");
        Task[] expected = { task1, task2, task3 };
        Assertions.assertArrayEquals(expected, result);
    }

    @Test
    public void testSearchNoTasks() {
        Todos todos = new Todos();

        Task[] result = todos.search("молоко");
        Task[] expected = {};
        Assertions.assertArrayEquals(expected, result);
    }

    @Test
    public void testSearchCaseSensitive() {
        SimpleTask task1 = new SimpleTask(1, "Купить Молоко");
        SimpleTask task2 = new SimpleTask(2, "купить молоко");

        Todos todos = new Todos();
        todos.add(task1);
        todos.add(task2);

        Task[] result = todos.search("Молоко");
        Task[] expectedForCapital = { task1 };
        Assertions.assertArrayEquals(expectedForCapital, result);

        result = todos.search("молоко");
        Task[] expectedForLower = { task2 };
        Assertions.assertArrayEquals(expectedForLower, result);
    }

    @Test
    public void testSearchEmptyQueryWithTasks() {
        SimpleTask task = new SimpleTask(1, "Купить молоко");
        String[] subtasks = {"Молоко"};
        Epic epic = new Epic(2, subtasks);
        Meeting meeting = new Meeting(3, "Тема", "Проект", "Завтра");

        Todos todos = new Todos();
        todos.add(task);
        todos.add(epic);
        todos.add(meeting);


        Task[] result = todos.search("");
        Task[] expected = {};
        Assertions.assertArrayEquals(expected, result);
    }

    @Test
    public void testSearchWithNullQuery() {
        SimpleTask task = new SimpleTask(1, "Купить молоко");

        Todos todos = new Todos();
        todos.add(task);

        // null запрос не должен находить задачи
        Task[] result = todos.search(null);
        Task[] expected = {};
        Assertions.assertArrayEquals(expected, result);
    }

    @Test
    public void testSearchMeetingBothFields() {

        Meeting meeting = new Meeting(1, "Обсуждение проекта",
                "Проект разработки", "Завтра");

        Todos todos = new Todos();
        todos.add(meeting);


        Task[] result = todos.search("проект");
        Task[] expected = { meeting };
        Assertions.assertArrayEquals(expected, result);
        Assertions.assertEquals(1, result.length);
    }

    @Test
    public void testSearchEpicMultipleMatchesInSubtasks() {

        String[] subtasks = {"Купить молоко", "Выпить молоко", "Продать молоко"};
        Epic epic = new Epic(1, subtasks);

        Todos todos = new Todos();
        todos.add(epic);


        Task[] result = todos.search("молоко");
        Task[] expected = { epic };
        Assertions.assertArrayEquals(expected, result);
        Assertions.assertEquals(1, result.length); // Проверяем, что только одна задача
    }

    @Test
    public void testSearchPartialWordMatch() {

        SimpleTask task1 = new SimpleTask(1, "Программирование");
        SimpleTask task2 = new SimpleTask(2, "Графический дизайн");
        SimpleTask task3 = new SimpleTask(3, "Курс по дизайну интерьера");

        Todos todos = new Todos();
        todos.add(task1);
        todos.add(task2);
        todos.add(task3);

        // Частичное совпадение "дизайн"
        Task[] result = todos.search("дизайн");
        Task[] expected = { task2, task3 };
        Assertions.assertArrayEquals(expected, result);
    }

    @Test
    public void testSearchOrderPreserved() {
        // Проверяем, что порядок задач при поиске сохраняется как порядок добавления
        SimpleTask task1 = new SimpleTask(1, "Первая задача");
        SimpleTask task2 = new SimpleTask(2, "Вторая задача");
        SimpleTask task3 = new SimpleTask(3, "Третья задача");

        Todos todos = new Todos();
        todos.add(task1);
        todos.add(task2);
        todos.add(task3);

        Task[] result = todos.search("задача");
        Task[] expected = { task1, task2, task3 };
        Assertions.assertArrayEquals(expected, result);
    }
}