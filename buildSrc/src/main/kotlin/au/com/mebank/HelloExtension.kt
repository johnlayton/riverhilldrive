package au.com.mebank

import org.gradle.api.model.ObjectFactory
import org.gradle.kotlin.dsl.property

open class HelloExtension(objects: ObjectFactory) {
  val message = objects.property<String>()
}
