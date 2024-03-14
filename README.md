# Industry track

#Name of the Project : Distributed crawler

## Description

The goal of the Distributed Web Crawler project is to create an advanced system that can traverse and analyze web data
across numerous workstations in an efficient manner. Its goal is to build a network of interdependent parts, each of
which contributes differently to the distributed crawling process. The main goal is to create a crawler that can use the
processing power of numerous machines to navigate through various websites in a methodical manner. This project offers a
chance to handle a number of issues related to distributed systems, such as message queuing, load balancing, and easing
communication between worker and master nodes. Furthermore, the project establishes the foundation for upcoming
developments, such the deployment of a distributed file system with integrated fault tolerance methods.

A distributed crawler is essentially a more advanced version of a regular web crawler that is designed to work in
distributed situations. It is necessary to dissect the architecture of a simple crawler in order to clarify the design
framework for such a crawler. Essentially, a traditional crawler consists of a URL management system, a seed set of URLs
to start the crawling process, web page fetching mechanisms, HTML content parsing to extract pertinent data, and URL
normalization to guarantee consistency in representation.

The distributed nature of the system requires a more complex infrastructure, which is included in the suggested design
for the distributed web crawler project. Unlike a standard crawler, this project involves coordinating the joint efforts
of several machines to accomplish effective data processing and crawling. Load balancers for fair job distribution,
message queues for smooth intercomponent communication, and systems for coordinating master-worker node interactions are
important parts of this distributed design.

Through exploring the complexities of parallel processing and distributed systems, the project aims to streamline the
crawling procedure so as to facilitate rapid and thorough data extraction from the web. In addition, the design takes
fault tolerance and scalability into consideration, which are critical for supporting the dynamic nature of web data and
guaranteeing reliable performance in a variety of computer environments.

Ultimately, the Distributed Web Crawler project is a calculated effort to advance web crawling technology by utilizing
distributed computing to open up new avenues for data collection and analysis. By means of careful planning and
execution, it aims to create a strong structure that can traverse the wide web, gather insightful information, and
enable progress in a number of fields that depend on web information.

## Implemented components:

Master Node:

- Role: As the central node of the system, the master node coordinates the crawling process by controlling data flow and
  task distribution.

Functions:

- Queue Management: The master node, which serves as the guardian of the URL queue, carefully
  manages the list of URLs that are scheduled for crawling. By means of careful administration, it
  guarantees a smooth and well-structured distribution of resources, maximizing the effectiveness of
  the crawling process.

- URL Distribution: The master node serves as the gatekeeper of information flow inside the system and is tasked with
  the vital duty of distributing URLs to the worker nodes. It seeks to maximize throughput and reduce latency by
  distributing URLs intelligently in order to create a balanced workload distribution across the network.

- Data Aggregation: The master node, which serves as the center of data consolidation, gathers all of the richly
  processed data that the conscientious worker nodes have retrieved. It enables well-informed decision-making and plan
  formulation by combining this abundance of data, which paves the way for further analysis and insight generation.

Worker Nodes:

- Role: Worker nodes, who act as the reliable crawling operation's foot soldiers, carry out their given jobs accurately
  and effectively, which is crucial to the data retrieval process.

Functions:

- Web Page Processing: Worker nodes are given the authority to retrieve and process web pages by the master node's
  instructions. They carefully sort through the digital terrain, gathering data and priceless information that are
  essential for the crawling project.

- URL Extraction: With the goal of broadening the scope of discovery, worker nodes painstakingly scan web pages in order
  to locate and collect fresh URLs woven in the digital fabric. They increase the crawling operation's depth and breadth
  and guarantee that the entire internet domain is covered because they are always discovering new and exciting routes
  for exploration.

- Communication: Worker nodes function as the intermediaries between the master node and the digital world, facilitating
  smooth communication. They communicate quickly and effectively, sending freshly found URLs and processed data back to
  the master node for a smooth transition into the overall crawling process.

Communication Protocol:

- Mechanism: By utilizing socket programming, the system's communication protocol allows for dependable and quick data
  transfer between nodes. Through the utilization of the TCP/IP stack, it creates a strong basis for smooth
  communication, guaranteeing effective coordination and cooperation in the dispersed setting.

- Data Format: The system uses bespoke protocols or the JSON (JavaScript Object Notation) format for data exchange, with
  an emphasis on efficiency and interoperability. Data exchange is streamlined and compatibility and simplicity of
  integration across disparate system components are promoted by standardizing the data format.

## Built with:

Detailed description of the system functionality and how to run the implementation

Environment Setup :

Make sure Java and Python are installed on every computer: This phase makes sure that all nodes have the runtime
environments required to run the code.
Verify that every node is connected to the same network and is able to communicate: Network connectivity between nodes
is essential to the distributed system's smooth coordination and communication.

Master Node Development :

Establish a synchronized queue to store and handle URLs: To effectively handle the URLs scheduled for crawls and
guarantee orderly processing, the master node needs a synchronized queue.

Create a TCP server to assist worker nodes with their requests: Tasks and data can be transmitted between worker nodes
and master nodes through the use of a TCP server.

Create the following logic to distribute URLs to worker nodes: In order to maintain optimal system performance, the
master node distributes URLs to worker nodes in a balanced manner.
Establish a mechanism to combine the data that employees provide: The creation of insights and further analysis depend
on aggregated data from worker nodes, which makes a reliable data aggregation process necessary.

Worker Node Development:

To allow communication with the master node, configure TCP clients as follows: In order to receive tasks and send
processed data to the master node, worker nodes establish TCP connections.

Fetch webpages using web crawling libraries: Worker nodes use web crawling libraries to retrieve webpages based on the
URLs that the master node has supplied.

Enable worker nodes to extract additional URLs and pertinent data from fetched web pages by implementing HTML parsing.
This will increase the breadth of the crawling process.

Create a protocol to facilitate the return of data to the master node: Worker nodes use a predetermined communication
protocol to send processed data and newly found URLs back to the master node.

Communication and Data Transfer:

Design a protocol for clear and effective data exchange: A well-defined communication protocol ensures seamless
interaction between nodes, facilitating efficient data exchange.

Choose a serialization format (like JSON) for network communication: Serialization formats like JSON standardize data
representation, enhancing interoperability and ease of communication across the distributed system.

Testing and Debugging:

Unit tests are a useful tool for confirming that individual components, such as URL management and data extraction,
behave as intended when used in isolation.

Apply integration testing to the entire system: Integration testing assesses the overall functionality of the system and
confirms that all of its parts perform as a cohesive whole.

Debugging fixes any problems or faults found during testing, ensuring the system runs smoothly and dependably. It can be
used to troubleshoot networking or data processing problems.

Deployment and Scaling :

Start with a small group of machines for deployment: Before a full-scale rollout, testing and validation can be done
with a limited first deployment.

In order to expand the system, add extra worker nodes: In order to handle growing workload needs, the system must be
scaled by adding more worker nodes, which improves system throughput and performance.

Put logging and monitoring systems in place for system supervision: Solutions for logging and monitoring make it
possible to see how the system is operating, allowing for real-time monitoring and proactive maintenance of the system's
performance and health.

## Getting Started:

Instructions on setting up your project locally

## Results of the tests:

Detailed description of the system evaluation
Evaluate your implementation using selected criteria, for example:

- Number of messages / lost messages, latencies, ...
- Request processing with different payloads, ..
- System throughput, ..

Design two evaluation scenarios that you compare with each other, for example:

- Small number / large number of messages
- Small payload / big payload

Collect numerical data of test cases:

- Collecting logs of container operations
- Conduct simple analysis for documentation purposes (e.g. plots or graphs)

## Acknowledgments:

list resources you find helpful
