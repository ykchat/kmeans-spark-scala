import org.apache.spark.{SparkContext, SparkConf}
import org.apache.spark.mllib.clustering.KMeans
import org.apache.spark.mllib.linalg.Vectors
import org.apache.spark.sql.{SQLContext, Row}
import org.apache.spark.sql.types.{StructType, StructField, StringType, IntegerType}

object KMeansIris {

    def main(args: Array[String]) {

        val sparkContext = SparkContext getOrCreate (new SparkConf() setAppName "k-means for iris")

        val iris = (sparkContext textFile "/usr/local/share/data/iris.data")
                    .filter(_.nonEmpty)
                    .map { 
                        line =>
                            val items = line split ","
                            (items.last, Vectors dense items.init.map(_.toDouble))
                    }

        val clusters = KMeans.train(iris.map(_._2), 3, 100)

        val sqlContext = SQLContext getOrCreate sparkContext
        val schema = StructType(
                        List(
                            StructField("target", StringType)
                            , StructField("cluster", IntegerType)
                        ))

        val check = sqlContext.createDataFrame(iris.map { 
                        row =>
                            Row(row._1, clusters predict row._2)
                    }, schema)

        val cross = ((check groupBy "target") pivot "cluster").count
        cross.show
        cross.write json "output"

    }

}
