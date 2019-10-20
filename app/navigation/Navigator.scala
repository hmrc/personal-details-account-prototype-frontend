/*
 * Copyright 2019 HM Revenue & Customs
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package navigation

import controllers.routes
import javax.inject.{Inject, Singleton}
import models._
import pages._
import play.api.mvc.Call

@Singleton
class Navigator @Inject()() {

  private val normalRoutes: Page => UserAnswers => Call = {
    case NamePage               => _ => routes.PhoneNumberController.onPageLoad(NormalMode)
    case PhoneNumberPage        => _ => routes.ContactPreferenceController.onPageLoad(NormalMode)
    case ContactPreferencePage  => contactPreferenceRoute
    case AddressPage            => _ => routes.CheckYourAnswersController.onPageLoad()
    case _                      => _ => routes.IndexController.onPageLoad()
  }

  private def contactPreferenceRoute(answers: UserAnswers): Call = answers.get(ContactPreferencePage) match {
    case Some(true)  => routes.AddressController.onPageLoad(NormalMode)
    case Some(false) => routes.CheckYourAnswersController.onPageLoad()
    case None        => routes.SessionExpiredController.onPageLoad()
  }

  private val checkRouteMap: Page => UserAnswers => Call = {
    case ContactPreferencePage => contactPreferenceCheckRoute
    case _                     => _ => routes.CheckYourAnswersController.onPageLoad()
  }

  private def contactPreferenceCheckRoute(answers: UserAnswers): Call = answers.get(ContactPreferencePage) match {
    case Some(true) if answers.get(AddressPage).isEmpty => routes.AddressController.onPageLoad(CheckMode)
    case None                                           => routes.SessionExpiredController.onPageLoad()
    case _                                              => routes.CheckYourAnswersController.onPageLoad()
  }

  def nextPage(page: Page, mode: Mode, userAnswers: UserAnswers): Call = mode match {
    case NormalMode =>
      normalRoutes(page)(userAnswers)
    case CheckMode =>
      checkRouteMap(page)(userAnswers)
  }
}
