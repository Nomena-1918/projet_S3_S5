@echo off
mvnw.cmd clean package
copy target\demo-1.0-SNAPSHOT.war C:\Applications\apache-tomcat-10\webapps
