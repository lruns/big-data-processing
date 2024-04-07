## Info
Данные были взяты с https://www.kaggle.com/datasets/bulter22/airline-data?resource=download . Время вычислений, указанное в zeppelin блокнотах, связаны именно с ними. Но в данном репозитории сохранен лишь отрывок этих данных, полный необходимо скачать с kaggle по ссылке выше. Также все тестировалось в докер среде https://github.com/panovvv/bigdata-docker-compose, которую нужно отдельно установить и после этого поместить туда приведенные блокноты и данные.


## Домашние задания

ДЗ 1

- Развернуть [Big data playground: Cluster with Hadoop, Hive, Spark, Zeppelin and Livy via Docker-compose.](https://github.com/panovvv/bigdata-docker-compose) через docker-compose.
- Найти датасет размером ~50Гб и загрузить в HDFS.
- Выполнить задания 2 и 3 на развернутой платформе из презентации к лекции №1:
    - Получить доступ к данным из Hive, использовать разные операторы SQL (WITH, GROUP BY, ORDER BY, LIMIT, ASC/DESC, OVER), посмотреть на производительность
    - Получить доступ к данным из Spark, проделав те же самые операции над RDD, DataFrame, DataSet. Замерить время выполнения

ДЗ 2

Обработать датасет из ДЗ 1, используя минимум 3 различных преобразования rdd: map, flatMap, reduceByKey, ... (см. презентацию)