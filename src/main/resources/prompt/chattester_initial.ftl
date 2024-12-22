```java
// Focal class
${class_sig} {
${fields}
<#if other_method_sigs?has_content>
// Signatures of other methods defined in the focal class
${other_method_sigs}
</#if>
// Focal method
${method_body}
}
```
You are a professional who writes Java test methods.
Write a test method for the "${method_sig}".
Provide the answer with just the source code of single JUnit class that will be used in the test.
