
# File-Storage
# CML TEST PROJECT
# Table of Contents

* [Project structure](#structure)

* [TASK](#developer-start)

File Storage REST service

# <a name="structure"></a>Project Structure
* Java 11
* Maven 3.6.0
* Spring Boot
* Elastic Search
<hr>

# <a name="developer-start"></a>Start application

0. Elasticsearch should be installed and started locally on port 9200;
    as alternative, docker image can be used: 
    <br>
    <br>
    docker run -d --name es762 -p 9200:9200 -e "discovery.type=single-node" elasticsearch:7.6.2
    <br>
    <br>
    application is using port 8083 - it should not be blocked.

