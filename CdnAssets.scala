package controllers

import play.api.Play
import play.api.Routes
import play.api.mvc.Call
import play.api.Play.current
import scala.language.reflectiveCalls


object CdnAssets {

	lazy val inDevelopmentMode: Boolean = {
		play.api.Play.isDev(play.api.Play.current)
	}

	lazy val assetsVersion: String = {
		play.Play.application.configuration.getString("assetsversion")
	}

	lazy val routes: Class[_] = {
		try {
			Thread.currentThread.getContextClassLoader.loadClass("controllers.routes")
		} catch {
			case e: Throwable =>
				ClassLoader.getSystemClassLoader.loadClass("controllers.routes")
		}
	}

	lazy val CDN: Option[String] = {
		Play.configuration.getString("assetscdn")
	}

	private[this] def url(baseUrl: String) = {
		CDN.getOrElse("") + baseUrl
	}

	def at(file: String): String = {
		val baseUrl = if (inDevelopmentMode) {
			val timestamp = System.currentTimeMillis / 1000
			"/assets/dev/" + file + "?" + timestamp.toString
		} else {
			"/assets/" + assetsVersion + "/" + file
		}

		url(baseUrl)
	}

}
