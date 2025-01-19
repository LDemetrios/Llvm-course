# Структура репозитория:

- #1 Код приложения — [app](app)/*.c
- #1.1 LLVM IR для приложения — [llvm](llvm)/*.ll
- #2 LLVM Pass:
  - Собственно, pass: [pass.cpp](stat/pass.cpp)
  - Сбор статистики: [build.gradle.kts](build.gradle.kts), task collect-stat
  - Результаты в виде таблицы: [stat](stat)/Statistics-*.txt

## Команды Make
 - build-app — собрать приложение в build/a.out
 - run-app — собрать и запустить приложение
 - emit-llvm — скомпилировать приложение в LLVM IR
 - build-stat — собрать плагин в build/tracer.so и обработанное им приложение stat/a.out
 - run-stat — запустить подсчёт статистики на несколько десятков кадров. 
 - clean — убрать все файлы, сгенерированные автоматически, кроме файлов статистики
 - prepare — подготовить репозиторий к публикации
