# File-Storage
# CML TEST PROJECT
# Table of Contents

* [Project structure](#structure)



File Storage REST service

# <a name="structure"></a>Project Structure
* Java 11
* Maven 3.6.0
* Spring Boot
* Lombok
* Spring MVC
* Spring Test
* Elastic Search
<hr>

# <a name="developer-start"></a>Start application

 Elasticsearch should be installed and started locally on port 9200;
    as alternative, docker image can be used: 
    <br>
    <br>
    docker run -d --name es762 -p 9200:9200 -e "discovery.type=single-node" elasticsearch:7.6.2
    <br>
    <br>
    application is using port 8082 - it should not be blocked.
