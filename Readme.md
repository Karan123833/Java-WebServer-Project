# Java Multi-Threaded Web Server

## 🚀 Project Overview

This project is a deep dive into the fundamentals of network programming and concurrency in Java. It demonstrates the evolution of a simple TCP server from a basic single-threaded model to a highly efficient and scalable thread pool architecture. The goal is to showcase the performance trade-offs and design patterns involved in building robust, high-performance network applications.

This project is an excellent demonstration of core Java skills, including:

-   **Networking:** Sockets, ServerSockets, and TCP/IP communication.
-   **Concurrency:** Threads, `Runnable`, `ExecutorService`, and `ThreadPoolExecutor`.
-   **Core Java:** I/O Streams, Exception Handling, and Object-Oriented Design.

---

## 🏛️ Server Architectures

This repository contains three distinct server implementations, each in its own package, showing a clear progression in design.

### 1. `singlethreaded`

-   **Description:** A basic, iterative server that can only handle one client at a time. When a client connects, all other potential clients must wait until the current one has been fully served.
-   **Pros:** Simple to understand and implement.
-   **Cons:** Extremely poor performance under any load. Does not scale. A single long-running client blocks all others.

### 2. `multithreaded`

-   **Description:** An improved server that spawns a new `Thread` for every incoming client connection. This allows the server to handle multiple clients simultaneously.
-   **Pros:** Significantly better performance than the single-threaded model. Can handle multiple clients concurrently.
-   **Cons:** Inefficient and dangerous under heavy load. Creating a new thread for every connection is resource-intensive (memory and CPU). An unlimited number of threads can be created, potentially crashing the system with an `OutOfMemoryError`.

### 3. `threadpool`

-   **Description:** The most robust and efficient implementation. This server uses a `ThreadPoolExecutor` to manage a fixed-size pool of worker threads and a work queue. New client connections are treated as tasks submitted to the pool.
-   **Pros:**
    -   **High Performance:** Reuses threads, avoiding the overhead of thread creation.
    -   **Resource Control:** Provides precise control over the maximum number of threads, preventing resource exhaustion.
    -   **Scalability & Stability:** Gracefully handles loads greater than its immediate capacity by queuing tasks, making the server resilient to traffic spikes.
-   **Cons:** More complex to configure, requiring careful tuning of pool size and queue capacity.

---

## 🛠️ How to Run

### Prerequisites

-   Java Development Kit (JDK) 8 or higher.
-   An IDE like IntelliJ IDEA or Eclipse, or compilation via the command line.

### Running a Server

1.  Navigate to the desired server package (`singlethreaded`, `multithreaded`, or `threadpool`).
2.  Compile and run the `Server.java` file.
3.  The console will indicate that the server is listening on port `8010`.

**Example (from command line):**

```bash
cd src/
javac threadpool/Server.java
java threadpool.Server