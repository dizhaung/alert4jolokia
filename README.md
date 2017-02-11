Alert4Jolokia
=====================
Alert4Jolokia adds alert capabilities to jmx metrics exposed via Jolokia.


Compile
-------------------
You need Apache Maven.

From root directory open a terminal and execute:

mvn clean package

A ear file, alert4jolokia-ear will be created in alert4jolokia-ear/target folder.


Run
-------------------
alert4jolokia-ear is an enterprise application running on a JEE application server.

Currently it has been tested on Wildfly 8.x/9.x/10.x

Examples
-------------------
Alerts defined so far:

Heap Memory in use:

_java.lang:type=Memory --> HeapMemoryUsage;_

JBoss/Wildfly datasource number of connections in use:

_jboss.as:data-source={0},statistics=pool,subsystem=datasources --> InUseCount_

Apache TomEE datasource number of connections in use

_openejb.management:ObjectType=datasources,DataSource={0} --> NumActive_

ActiveMQ broker messages sent to the destination  

_org.apache.activemq:type=Broker,brokerName={0},destinationType={1},destinationName={2} --> EnqueueCount_

ActiveMQ broker memory in use percentage

_org.apache.activemq:type=Broker,brokerName={0} --> MemoryPercentUsage_


Third-Party Libraries
--------------
apache commons

http://commons.apache.org


slf4j

http://www.slf4j.org


License
--------------
Copyright 2016 Hifly81
 
Licensed under the Apache License, Version 2.0 (the "License"); you may not use this work except in compliance with
the License. You may obtain a copy of the License in the LICENSE file, or at:
 
http://www.apache.org/licenses/LICENSE-2.0
 
Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on
an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the
specific language governing permissions and limitations under the License.

