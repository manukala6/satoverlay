package satoverlay

import org.apache.spark.{SparkConf, SparkContext}
import org.apache.spark.sql.SparkSession

object Spark {

    def conf: SparkConf = new SparkConf()
        .setAppName("satoverlay")
        .setMaster("local[*]")
        .set("spark.serializer", "org.apache.spark.serializer.KryoSerializer")
        .set("spark.kryo.registrator", "geotrellis.spark.store.kryo.KryoRegistrator")

    val session: SparkSession = SparkSession.builder
        .config(conf)
        .enableHiveSupport()
        .getOrCreate()

    implicit val sc: SparkContext = session.sparkContext

}
