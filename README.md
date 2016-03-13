# kmeans-spark-scala

K-means for Iris data by Spark in Scala

## Steps

```bash
sbt package
/usr/local/spark/bin/spark-submit --class KMeansIris --master local target/scala-2.11/kmeans-iris_2.11-0.0.1.jar
/usr/local/hadoop/bin/hdfs dfs -cat output/*
```

```bash
sbt package
/usr/local/spark/bin/spark-submit --class KMeansIris --master yarn-cluster target/scala-2.11/kmeans-iris_2.11-0.0.1.jar
/usr/local/hadoop/bin/hdfs dfs -cat output/*
```

