package api.model
import io.circe.Codec
import io.circe.generic.semiauto.{deriveCodec, deriveEncoder}
case class EmployeeRequest(name: String, lastName: String)

object EmployeeRequest {

  implicit val EmployeeRequest: Codec[EmployeeRequest] = deriveCodec
}
