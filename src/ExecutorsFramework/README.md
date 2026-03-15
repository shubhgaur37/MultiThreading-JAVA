# Executors Framework

## Advantages
1. Improves performance: by mitigating the overhead of creating and destroying threads each time a task is performed.
2. Control Over Thread Count
3. Resource Management: Limits the number of active threads, preventing excessive memory usage or CPU contention
4. Reduce Latency: Reuse existing threads for tasks reducing latency associated with creating new threads.

## UseCases:
- Webservers, Database connection handling etc.

## Background of Executors Framework:
1. Introduced in JAVA 5 as part of java.util.concurrent package.
2. Simplifies development by abstracting away the complexities involved in creating and managing threads.

## Problems Before Executors Framework Was Introduced:
1. Manual Thread Management
2. Resource Management Complexity
3. Scalability
4. Complexity in Thread reuse and Error Handling

## CORE INTERFACES
1. Executor
2. ExecutorService
3. ScheduledExecutorService


### CACHED THREAD POOL: DYNAMIC THREAD POOL

A **Cached Thread Pool** is a dynamically scaling thread pool that creates new threads when needed and reuses previously created idle threads.

```java
ExecutorService executor = Executors.newCachedThreadPool();
```

Internally it behaves roughly like:

```java
new ThreadPoolExecutor(
    0,
    Integer.MAX_VALUE,
    60L,
    TimeUnit.SECONDS,
    new SynchronousQueue<Runnable>()
);
```

---

### Key Characteristics

---

### How It Works

1. If an **idle thread exists** → it is reused.
2. If **no idle thread exists** → a new thread is created.
3. Threads idle for **60 seconds** are terminated.
4. Tasks are **not queued** — they are directly handed to a thread.

---

### When to Use

Cached thread pools are suitable when:

- Tasks are **short-lived**
- Workload is **bursty or unpredictable**
- Tasks are **I/O bound**
- You want **low latency execution without queue delays**

Typical examples:

- Handling short background tasks
- Event-driven processing
- Lightweight async operations
- Microservices handling quick requests

---

### When It Becomes a Bottleneck

Cached thread pools can become problematic when:

- Tasks are **long-running**
- Tasks are **CPU intensive**
- Large numbers of tasks arrive simultaneously

Because the pool can create **unlimited threads**, this can lead to:

- **Thread explosion**
- High **CPU context switching**
- **Memory exhaustion**
- **OutOfMemoryError**
- System instability

Example scenario:

```
1000 incoming tasks → 1000 threads created
```

---

### Pros

- Automatically scales based on workload
- Reuses idle threads (reduces creation overhead)
- No queue delays
- Good for burst workloads

---

### Cons

- Unbounded thread creation
- Hard to control resource usage
- Not suitable for long-running tasks
- Can overwhelm CPU and memory

---