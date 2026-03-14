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