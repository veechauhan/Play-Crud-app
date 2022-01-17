package controllers

import models.{UserItem, UserRepository}
import org.mockito.Mockito.when
import play.api.test.Helpers._
import org.scalatestplus.play.PlaySpec
import org.specs2.mock.Mockito.mock
import play.api.libs.json.JsValue
import play.api.mvc.{ Result, Results}
import play.api.test.{FakeRequest, Helpers}
import play.libs.Json

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future
class UserControllerTest extends  PlaySpec with Results {
  "UserController#getById" should {
    "be valid" in {
      val userRepository = mock[UserRepository]
      val defaultUser = UserItem(Some("1"),"user1","12345")
      when(userRepository.getById("1")) thenReturn Future(Option(defaultUser))
      val controller = new RegisterController(userRepository,Helpers.stubControllerComponents())
      val result: Future[Result] = controller.getById("1").apply(FakeRequest())
      val userJson: JsValue = contentAsJson(result)
      userJson mustBe Json.toJson(defaultUser)
    }
  }
}