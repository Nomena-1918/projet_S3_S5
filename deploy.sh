server_path="/Applications/apache-tomcat-10/webapps"

./mvnw clean package
cp -f target/demo-1.0-SNAPSHOT.war $server_path
mess='\n  Deployment done ! 🔥\n'
echo "$mess"