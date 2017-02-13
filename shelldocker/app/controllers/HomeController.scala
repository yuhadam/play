package controllers

import javax.inject._
import play.api._
import play.api.mvc._
import play.api.Logger
import play.api.libs.json._
import scala.sys.process._
import play.api.libs.concurrent.Execution.Implicits.defaultContext
import scala.concurrent.Future

//modify by yuha
case class Node(selectval: String,passwordval: String,ipaddressval: String)
//case class Node(selectval: String,interfaceval: String, passwordval: String, sshportval: String, ipaddressval: String)
/**
 * This controller creates an `Action` to handle HTTP requests to the
 * application's home page.
 */
@Singleton
class HomeController @Inject() extends Controller {

  /**
   * Create an Action to render an HTML page with a welcome message.
   * The configuration in the `routes` file means that this method
   * will be called when the application receives a `GET` request with
   * a path of `/`.
   */
  def index = Action {
    Logger.debug("page loaded")
    Ok(views.html.index())
  }
//  
//  def install = Action { request =>    
//    val data = request.body.asFormUrlEncoded.get
//    val datasize = data.size
//    
//    Logger.debug(data + " " + datasize)
//    val loopsize = datasize/3
//    var x =0;
//    
//    
//    val arr = for( x <- 1 to loopsize) yield {
//      val typeval = data.get(x+"select").get(0)
////      val interfaceval = data.get(x+"interface").get(0)
////      val sshportval = data.get(x+"sshport").get(0)
//      val passwordval = data.get(x+"password").get(0)
//      val ipaddressval = data.get(x+"ipaddress").get(0)
////      Node(typeval,interfaceval,passwordval,sshportval,ipaddressval)
//      Node(typeval,passwordval,ipaddressval)
//    }
//    
//    val firstNode = arr(0)
//    
//    
//    Logger.debug(arr.toString())
//    
//    
//    //val commandfirst = "bash /install/install_bootstrap.sh " + firstNode.interfaceval + " " + firstNode.passwordval +" " + firstNode.sshportval + " "
//    val commandfirst = "bash /install/install_bootstrap.sh " + data.get("interface").get(0) + " " + data.get("sshport").get(0)+ " " 
//
//    val masterarr = arr.filter { x => x.selectval == "Master" }
//    val workerarr = arr.filter { x => x.selectval == "Worker" }
//    
//    Logger.debug("master"+masterarr.toString())
//    Logger.debug("worker"+workerarr.toString())
//    
//    val commandsecond = masterarr.foldLeft(commandfirst)((m:String, n:Node) => m + n.ipaddressval + " " + n.passwordval+ " ")
//    val commandthird = workerarr.foldLeft(commandsecond)((m:String, n:Node) => m + n.ipaddressval + " " + n.passwordval + " ")
//    
//    Logger.debug(commandthird)
//                                 
//       
//    //Process(commandthird).run
//    
//    Ok("hi")
//  }

    def install = Action { request =>
        val obj = request.body.asFormUrlEncoded
        val masterArr= Json.parse(obj.get("masters")(0))
        val workerArr= Json.parse(obj.get("workers")(0))
        val storageArr=Json.parse(obj.get("storages")(0))
        val sshPort= obj.get("sshPort")(0)
        val interfaceStr= obj.get("interfaceStr")(0)
        
        val command="bash /install/install_bootstrap.sh " + sshPort + " " + interfaceStr + " " 
//        var i=0
//        var masterIpArr=""       
//        while(i< masterArr.\\("ip").length){
//          masterIpArr+=masterArr.\\("ip")(i)+";"
//          i+=1
//        }
        
        val mIpCommand =masterArr.\\("ip").foldLeft(command)(_+";"+_) + " "
        val mPwCommand = masterArr.\\("pw").foldLeft(mIpCommand)(_+";"+_) + " "
        val wIpCommand = workerArr.\\("ip").foldLeft(mPwCommand)(_+";"+_) + " "
        val wPwCommand = workerArr.\\("pw").foldLeft(wIpCommand)(_+";"+_) + " "
        val sIpCommand = storageArr.\\("ip").foldLeft(wPwCommand)(_+";"+_) + " "
        val finalCommand = storageArr.\\("pw").foldLeft(sIpCommand)(_+";"+_) + " "
        
        Logger.debug(finalCommand.toString())
      Process(finalCommand).run
      Ok("hi")
  }
}
