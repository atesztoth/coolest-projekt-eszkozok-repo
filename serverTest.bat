
@echo off

echo ===============================================
echo SERVER TEST
echo ===============================================

javac -encoding utf8 -implicit:class -cp .;lib/junit-4.12.jar;lib/hamcrest-core-1.3.jar serverTest\ServerTest.java
IF ERRORLEVEL 1 GOTO end
java -cp .;lib/junit-4.12.jar;lib/hamcrest-core-1.3.jar org.junit.runner.JUnitCore serverTest.ServerTest
IF ERRORLEVEL 1 GOTO end

:end
