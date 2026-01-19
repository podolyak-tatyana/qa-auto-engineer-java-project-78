### Hexlet tests and linter status:
[![Actions Status](https://github.com/podolyak-tatyana/qa-auto-engineer-java-project-78/actions/workflows/hexlet-check.yml/badge.svg)](https://github.com/podolyak-tatyana/qa-auto-engineer-java-project-78/actions)


[![Quality Gate Status](https://sonarcloud.io/api/project_badges/measure?project=podolyak-tatyana_qa-auto-engineer-java-project-78&metric=alert_status)](https://sonarcloud.io/summary/new_code?id=podolyak-tatyana_qa-auto-engineer-java-project-78)
[![Coverage](https://sonarcloud.io/api/project_badges/measure?project=podolyak-tatyana_qa-auto-engineer-java-project-78&metric=coverage)](https://sonarcloud.io/summary/new_code?id=podolyak-tatyana_qa-auto-engineer-java-project-78)
[![Code Smells](https://sonarcloud.io/api/project_badges/measure?project=podolyak-tatyana_qa-auto-engineer-java-project-78&metric=code_smells)](https://sonarcloud.io/summary/new_code?id=podolyak-tatyana_qa-auto-engineer-java-project-78)
[![Lines of Code](https://sonarcloud.io/api/project_badges/measure?project=podolyak-tatyana_qa-auto-engineer-java-project-78&metric=ncloc)](https://sonarcloud.io/summary/new_code?id=podolyak-tatyana_qa-auto-engineer-java-project-78)

### Описание
Валидатор данных – библиотека, с помощью которой можно проверять корректность любых данных.
Подобных библиотек множество в каждом языке, так как практически все программы работают с внешними данными, которые нужно проверять на корректность. 
В первую очередь речь идет про данные форм заполняемых пользователями.

### Возможности
1. Валидация строк (StringSchema)

Методы:
- required() — запрещает null и пустую строку
- minLength(int) — строка должна быть не короче заданного значения
- contains(String) — строка должна содержать подстроку

2. Валидация чисел (NumberSchema)

Методы:
- required() — запрещает null
- positive() — число должно быть строго больше нуля
- range(int min, int max) — число должно быть в диапазоне (включая границы)

3. Валидация Map (MapSchema)

Методы:
- required() — запрещает null
- sizeof(int) — количество пар ключ-значение должно быть равно указанному числу
- shape(Map<String, BaseSchema<?>>) — вложенная валидация значений по ключам
