./mvnw clean package
cp target/demo-1.0-SNAPSHOT.war /Applications/apache-tomcat-10/webapps
mess='\n  Deployment done ! 🔥\n'
echo "$mess"