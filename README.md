# ChatUniTest Standalone
This repository is a standalone version of ChatUniTest, which is a tool to generate unit tests for any java project in commandline, specifically designed for our experiments of sf110 projects.
## step to run
1. Clone the repository
```shell
git clone https://github.com/ZJU-ACES-ISE/chatunitest-standalone.git 
cd chatunitest-standalone
```
2. Run `mvn package`, then the jar file [chatunitest-standalone-1.0.0.jar](target%2Fchatunitest-standalone-1.0.0.jar) will be generated in the target folder.
3. Prepare the environment file named `chatunitest.env` for your project, which should contain the following information, here is an example of the environment file for the project a4j:
```shell
apiKeys=xxx
url=
model=
enableMultithreading=true
parentEnvPath=
classPaths=a4j
baseDir=/Users/chenyi/Downloads/sf110-chatgpt/sf110/SF110-src/2_a4j
groupId=com.example
artifactId=a4j
compileSourceRoots=/Users/chenyi/Downloads/sf110-chatgpt/sf110/SF110-src/2_a4j/src/main/java
buildPath=/Users/chenyi/Downloads/sf110-chatgpt/sf110/SF110-src/2_a4j/target
artifactPath=/Users/chenyi/Downloads/sf110-chatgpt/sf110/SF110-src/2_a4j/a4j.jar
classPaths=/Users/chenyi/Downloads/sf110-chatgpt/sf110/SF110-src/2_a4j:/Users/chenyi/Downloads/sf110-chatgpt/sf110/SF110-src/2_a4j/lib:/Users/chenyi/Downloads/sf110-chatgpt/sf110/SF110-src/2_a4j/test-lib:/Users/chenyi/Downloads/sf110-chatgpt/sf110/SF110-src/2_a4j/target:/Users/chenyi/Desktop/ChatUniTest/chatunitest-standalone/src/main/resources/dependency
```
Remember to add the `dependency` in the `resources` to the `classPaths` property in the environment file like: `/Users/chenyi/Desktop/ChatUniTest/chatunitest-standalone/src/main/resources/dependency`.

4. Run following command in the terminal to start ChatUniTest:
```shell
java -jar target/chatunitest-standalone-1.0.0.jar path_to_your_project_env_file project
```