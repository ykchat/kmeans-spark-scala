import org.apache.spark.{SparkContext, SparkConf}
import org.apache.spark.mllib.clustering.KMeans
import org.apache.spark.mllib.linalg.Vectors
import org.apache.spark.sql.{SQLContext, Row}
import org.apache.spark.sql.types.{StructType, StructField, StringType, IntegerType}

object KMeansIris {

    def main(args: Array[String]) {

        val conf = new SparkConf() setAppName "k-means for iris"
        val sc = new SparkContext(conf)

        val data = (sc textFile "/usr/local/share/data/iris.data").
                    filter(_.nonEmpty).
                    map { line =>
                        val items = line split ","
                        (items.last, Vectors dense items.init.map(_.toDouble))
                    }

        val k = 3 
        val maxItreations = 100 

        val clusters = KMeans.train(data.map(_._2), k, maxItreations)

        val sqlc = new SQLContext(sc)
        val schema = StructType(List(
                            StructField("target", StringType, true),
                            StructField("cluster", IntegerType, true)
                        ))

        val check = data.map { row =>
            Row(row._1, clusters predict row._2)
        }
        val checkdf = sqlc.createDataFrame(check, schema)

        ((checkdf groupBy "target") pivot ("cluster")).count().show

    }

}
