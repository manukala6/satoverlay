package satoverlay

import geotrellis.raster._
import scala.math.random
import org.apache.log4j.{Logger, Level}
import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}
import org.apache.spark.sql.SparkSession

object Main extends App {

    // Reduce Spark logging verbosity
    Logger.getLogger("org.apache.spark").setLevel(Level.ERROR)

    def conf: SparkConf = new SparkConf()
        .setAppName("satoverlay")
        .setMaster("local[*]")
        .set("spark.serializer", "org.apache.spark.serializer.KryoSerializer")
        .set("spark.kryo.registrator", "geotrellis.spark.store.kryo.KryoRegistrator")

    val session: SparkSession = SparkSession.builder
        .config(conf)
        .enableHiveSupport()
        .getOrCreate()

    def sc: SparkContext = session.sparkContext

    val slices = 32
    val n = math.min(100000L * slices, Int.MaxValue).toInt

    val countRdd: RDD[Int] = sc.parallelize(1 to n, slices)

    val mapRdd: RDD[Int] = countRdd.map { i =>
        val x = random * 2 - 1
        val y = random * 2 - 1
        if (x*x + y*y < 1) 1 else 0
    }

    val count = mapRdd.reduce{ (a, b) => a + b}

    println(s"Pi is roughly ${4.0 * count / (slices - 1)}")

    sc.stop()

}
