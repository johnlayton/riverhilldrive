# Riverhilldrive

[![Jitpack Build Status](https://jitpack.io/v/johnlayton/riverhilldrive.svg)](https://jitpack.io/#johnlayton/riverhilldrive)
[![Github Build Status](https://github.com/johnlayton/riverhilldrive/workflows/main/badge.svg)](https://github.com/johnlayton/riverhilldrive/actions)

## Gradle Plugin Wsdl

```kts
buildscript {
  repositories {
    mavenLocal()
    mavenCentral()
    maven("https://jitpack.io")
  }
  dependencies {
    classpath("com.github.johnlayton", "riverhilldrive", "0.0.9")
  }
  configurations {
    classpath {
      resolutionStrategy {
        cacheChangingModulesFor(0, "seconds")
      }
    }
  }
}
```

```kts
plugins {
  id("plugin-wsdl")
}
```

```kts
wsdlToJava {
    // [1] Optional
    tools {
        // [1] Optional: Default 3.3.6
        apacheCXFVersion = "3.3.6",
        // [1] Optional: Default 2.3.1
        javaxXmlVersion = "2.3.1",
        // [1] Optional: Default 1.1.1
        javaxActivationVersion = "1.1.1",
        // [1] Optional: Default 1.0-MR1
        javaxJwsVersion = "1.0-MR1",
        // [1] Optional: Default 2.3.0.1
        sunXmlVersion = "2.3.0.1"
    }
    // [0..n] Required
    wsdls {
        register("token") {
            // [1] Required
            wsdl.set(file("project/path/to/wsdl"))
    
            // [1] Optional Custom Binding File: Default is unset (generated bindings)
            bind.set(file("project/path/to/binding"))
            // [*] Optional Package Specification: Default is empty
            pkgs.put("http://mebank.com.au/service", 
                     "au.com.mebank.soap.service.api.model")
            // [1] Optional Enable Wrapper Style: Default is false
            enableWrapperStyle.set(false)
            // [1] Optional Enable Asynchronous Methods: Default is true
            enableAsyncMapping.set(true)
    
            // [1] Optional: Default is true
            enableExsh.set(true)
            // [1] Optional: Default is false
            enableDex.set(false)
            // [1] Optional: Default is false
            enableDns.set(false)
    
            // [1] Optional enable the WSDL Validation: Default false
            validate.set(false)
        }   
    }
}
```