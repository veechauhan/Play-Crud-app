package controllers

import models.UserItemFormatter.UserItemFormatter
import models.{ UserItem, UserRepository }
import play.api.libs.json.{ JsError, JsSuccess, JsValue, Json }
import play.api.mvc.{ Action, AnyContent, BaseController, ControllerComponents }

import javax.inject.{ Inject, Singleton }
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

@Singleton
class RegisterController @Inject() (userRepository: UserRepository, val controllerComponents: ControllerComponents)
    extends BaseController {

  def getById(id: String): Action[AnyContent] =
    Action.async {
      userRepository.getById(id).map {
        case Some(user) => Ok(Json.toJson(user))
        case None       => NotFound
      }
    }

  def getAll: Action[AnyContent] =
    Action.async {
      userRepository.getAll.map(users => Ok(Json.toJson(users)))
    }

  def delete(id: String): Action[AnyContent] =
    Action.async {
      userRepository.delete(id).map { result =>
        if (result >= 1) Ok("true")
        else BadRequest("Something went wrong")
      }
    }

  def addNewUser(): Action[JsValue] =
    Action.async(parse.json) { implicit request =>
      request.body.validate[UserItem] match {
        case JsSuccess(user: UserItem, _) =>
          userRepository.add(user).map(_ => Ok)
        case JsError(_)                   => Future.successful(BadRequest)
      }
    }

  def update: Action[JsValue] =
    Action.async(parse.json) { implicit request =>
      request.body.validate[UserItem] match {
        case JsSuccess(UserItem(Some(id), name, password), _) =>
          userRepository.update(id, name, password).map { result =>
            if (result >= 1) Ok("true")
            else BadRequest("Something went wrong")
          }
        case _=> Future.successful(BadRequest)
      }
    }

}
