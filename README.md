# UDP-Key-Value-Store-Server

Project Overview

The UDP Key-Value Store Server is a Java-based server application that provides a fast, connectionless distributed key-value store. Clients can perform CRUD (Create, Read, Update, Delete) operations using the UDP (User Datagram Protocol). Unlike TCP, UDP offers low-latency communication by not establishing a connection, which is ideal for scenarios where speed is more critical than guaranteed delivery. The server can handle multiple client requests concurrently, ensuring efficient performance in a lightweight environment.

Key-Value Store Operations

	•	PUT <key> <value>: Adds a key-value pair to the store.
	•	GET <key>: Retrieves the value associated with a given key.
	•	DELETE <key>: Removes a key-value pair from the store.
	•	KEYS: Lists all keys stored in the key-value store.
	•	QUIT: Closes the client communication.

Features

	•	Fast Communication: Uses UDP to provide fast, connectionless communication with minimal overhead, ideal for low-latency operations.
	•	Concurrent Clients: Supports handling multiple clients simultaneously using multithreading, ensuring scalability.
	•	In-Memory Storage: Key-value pairs are stored in memory, offering rapid read and write operations.
	•	Error Handling: Includes mechanisms to handle invalid commands, packet size limitations, and unexpected client behavior.
	•	Efficient Data Structures: Utilizes Maps for quick storage and retrieval of key-value pairs, ensuring uniqueness and optimized performance.

UDP Client-Server Communication

	•	The client sends requests as UDP datagrams to the server.
	•	The server processes the request (GET, PUT, DELETE) and responds via a UDP datagram.
	•	UDP is connectionless and does not guarantee delivery or ordering of packets, which makes it faster but less reliable than TCP.

How to Run

	1.	Clone the repository:

git clone https://github.com/your-username/udp-key-value-store.git


	2.	Navigate to the project directory:

cd udp-key-value-store


	3.	Compile the server and client classes:

javac UDPServer.java UDPClient.java


	4.	Start the UDP Server:

java UDPServer


	5.	Run the UDP Client:

java UDPClient


	6.	Use the following commands from the client:
	•	PUT <key> <value>: Store a key-value pair.
	•	GET <key>: Retrieve the value for a specific key.
	•	DELETE <key>: Remove a key-value pair.
	•	KEYS: Retrieve all stored keys.
	•	QUIT: Exit the client.

Future Enhancements

	•	Persistent Storage: Add support for saving key-value pairs to a database or a file system for long-term storage.
	•	Reliability: Implement techniques like packet acknowledgment and retries to improve reliability in UDP communication.
	•	Security: Add encryption and authentication mechanisms to secure data transmission.

