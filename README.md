# ChatUniTest Standalone
This repository is a standalone version of ChatUniTest, which is a tool to generate unit tests for any java project in commandline, specifically designed for our experiments of sf110 projects.
## step to run
1. Clone the repository
```shell
git clone https://github.com/ZJU-ACES-ISE/chatunitest-standalone.git 
cd chatunitest-standalone
```
2. Run `mvn package`, then the jar file [chatunitest-standalone-1.0.0.jar](target%2Fchatunitest-standalone-1.0.0.jar) will be generated in the target folder.
3. Run following command in the terminal to start ChatUniTest:
```shell
java -jar target/chatunitest-standalone-1.0.0.jar path_to_your_project_env_file project
```