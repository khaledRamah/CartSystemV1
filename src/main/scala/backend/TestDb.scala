package backend

import io.getquill.{CamelCase, H2JdbcContext}

object TestDb  extends App {

   val ctx = new  H2JdbcContext(CamelCase, "Context")
}
