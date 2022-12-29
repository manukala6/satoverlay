package satoverlay

import geotrellis.spark._
import geotrellis.proj4.WebMercator
import geotrellis.layer.ZoomedLayoutScheme
import geotrellis.raster.geotiff.GeoTiffRasterSource

import org.apache.log4j.{Logger, Level}
import org.apache.spark.{SparkConf, SparkContext}
import org.apache.spark.sql.SparkSession

import scala.math.random
import scala.util.Properties.isWin

object Main extends App {

    // Reduce Spark logging verbosity
    Logger.getLogger("org.apache.spark").setLevel(Level.ERROR)
    if (isWin) {
        Logger.getLogger("org.apache.spark.util.ShutdownHookManager").setLevel(Level.OFF)
    }

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

    val uri = "s3://geotrellis-demo/cogs/harrisburg-pa/elevation.tif"
    val rasterSource = GeoTiffRasterSource(uri)

    val summary = RasterSummary.fromSeq(List(rasterSource))

    val layout = summary.layoutDefinition(ZoomedLayoutScheme(WebMercator, 256))
    val layer = RasterSourceRDD.spatial(rasterSource, layout)

    sc.stop()

}
