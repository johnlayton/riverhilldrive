package com.github.johnlayton

import com.github.johnlayton.WsdlPlugin.WsdlToJavaExtension
import io.kotest.core.spec.style.WordSpec
import io.kotest.matchers.collections.shouldNotBeEmpty
import org.gradle.internal.impldep.org.junit.rules.TemporaryFolder
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.get
import org.gradle.kotlin.dsl.the
import org.gradle.testfixtures.ProjectBuilder
//import org.jetbrains.kotlin.gradle.internal.ensureParentDirsCreated

class WsdlPluginTest : WordSpec({

//    val testProjectDir: TemporaryFolder = TemporaryFolder()
//
//    "Using the Plugin ID" should {
//        "Apply the Plugin" {
//
//            val project = ProjectBuilder.builder().build()
//            project.pluginManager.apply(WsdlPlugin::class.java)
//
////            project.configure<WsdlPlugin.WsdlToJavaExtension> {
////                wsdls.register("sample") {
////                    it.wsdl.set(project.file("sample.wsdl"))
////                }
////            }
//
////            , {
////                it.wsdl.set(project.file("sample.wsdl"))
////            })
////            val project = ProjectBuilder.builder().build()
////
////            val buildFile = testProjectDir.newFile("build.gradle").writeText("""
////        plugins {
////          id("plugin-wsdl")
////        }
////        wsdlToJava {
////          wsdls {
////            register("sample") {
////              wsdl.set(file("sample.wsdl"))
////            }
////          }
////        }
////      """.trimIndent())
////
//            project.file("src/sample/java").mkdirs()
//            project.file("src/sample/resources").mkdirs()
//
//            val wsdl = project.file("src/sample/resources/sample.wsdl")
//            wsdl.writeText("""
//        <?xml version='1.0' encoding='UTF-8'?>
//        <wsdl:definitions name="AmberleyWay"
//                          targetNamespace="http://johnlayton.github.com/amberleyway"
//                          xmlns:wsdl="http://schemas.xmlsoap.org/wsdl/"
//                          xmlns:tns="http://johnlayton.github.com/amberleyway"
//                          xmlns:soap12="http://schemas.xmlsoap.org/wsdl/soap12/">
//            <wsdl:types>
//                <xs:schema xmlns:xs="http://www.w3.org/2001/XMLSchema"
//                           xmlns:tns="http://johnlayton.github.com/amberleyway"
//                           targetNamespace="http://johnlayton.github.com/amberleyway"
//                           version="1.0">
//                    <xs:element name="AmberleyWayRequest" type="tns:amberleyWayRequest"/>
//                    <xs:element name="AmberleyWayResponse" type="tns:amberleyWayResponse"/>
//                    <xs:complexType final="extension restriction" name="amberleyWayRequest">
//                        <xs:sequence>
//                            <xs:element name="id" type="xs:int"/>
//                            <xs:element name="name" type="xs:string"/>
//                        </xs:sequence>
//                    </xs:complexType>
//                    <xs:complexType final="extension restriction" name="amberleyWayResponse">
//                        <xs:sequence>
//                            <xs:element name="id" type="xs:int"/>
//                            <xs:element name="name" type="xs:string"/>
//                        </xs:sequence>
//                    </xs:complexType>
//                </xs:schema>
//            </wsdl:types>
//            <wsdl:message name="sayHelloResponse">
//                <wsdl:part name="response" type="tns:amberleyWayResponse">
//                </wsdl:part>
//            </wsdl:message>
//            <wsdl:message name="sayHello">
//                <wsdl:part name="request" type="tns:amberleyWayRequest">
//                </wsdl:part>
//            </wsdl:message>
//            <wsdl:portType name="AmberleyWay">
//                <wsdl:operation name="sayHello">
//                    <wsdl:input message="tns:sayHello" name="sayHello">
//                    </wsdl:input>
//                    <wsdl:output message="tns:sayHelloResponse" name="sayHelloResponse">
//                    </wsdl:output>
//                </wsdl:operation>
//            </wsdl:portType>
//            <wsdl:binding name="AmberleyWaySoapBinding" type="tns:AmberleyWay">
//                <soap12:binding style="rpc" transport="http://schemas.xmlsoap.org/soap/http"/>
//                <wsdl:operation name="sayHello">
//                    <soap12:operation soapAction="" style="rpc"/>
//                    <wsdl:input name="sayHello">
//                        <soap12:body namespace="http://johnlayton.github.com/amberleyway" use="literal"/>
//                    </wsdl:input>
//                    <wsdl:output name="sayHelloResponse">
//                        <soap12:body namespace="http://johnlayton.github.com/amberleyway" use="literal"/>
//                    </wsdl:output>
//                </wsdl:operation>
//            </wsdl:binding>
//            <wsdl:service name="AmberleyWay">
//                <wsdl:port binding="tns:AmberleyWaySoapBinding" name="AmberleyWayImpl">
//                    <soap12:address location="http://localhost:8092/service/amberleyway"/>
//                </wsdl:port>
//            </wsdl:service>
//        </wsdl:definitions>
//      """.trimIndent())
//
//            val wsdlToJava = project.the(WsdlToJavaExtension::class) //>("wsdlToJava")
//            val sample = wsdlToJava.wsdls.create("sample")
//            sample.wsdl.set(project.file("src/sample/resources/sample.wsdl"))
//
//
//
//            project.tasks.shouldNotBeEmpty()
////
////            val result = GradleRunner.create()
////                    .withProjectDir(testProjectDir.root)
////                    .withArguments("wsdlToJavaGenerateSample")
////                    .withPluginClasspath()
////                    .build()
////
////            println(result.tasks)
//////      buildFile << ""
//////      val project = ProjectBuilder.builder().build()
//////      project.pluginManager.apply(WsdlPlugin::class.java)
//////      // Create Readme
//////      project.file("README.md").writeText("## Test Project")
//////
//////      buildFile << """
//////        plugins {
//////            id 'com.myplugin.gradle'
//////        }
//////    """
//////
//////      project.wsdlToJava {
//////
//////      }
////
//////      project.wsdlToJava()
//////      project.plugins.getPlugin(WsdlPlugin::class.java) shouldNotBe null
////        }
//        }
//    }
//    "Applying the Plugin" should {
//        "Register the 'wsdlToJava' extension" {
//            val project = ProjectBuilder.builder().build()
//            project.pluginManager.apply(WsdlPlugin::class.java)
////            project.wsdlToJava() shouldNotBe null
//        }
//    }
})
//{
//
//}