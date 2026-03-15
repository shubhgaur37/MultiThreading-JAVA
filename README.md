# Java Multithreading and Synchronization Study Notes

This repository is a hands-on study workspace for Java multithreading, synchronization, explicit locking, and the `java.util.concurrent` executor ecosystem.

The code shows a clear progression from basic thread creation to advanced coordination utilities such as `CountDownLatch`, `CyclicBarrier`, scheduled executors, and `CompletableFuture`.

## Prerequisites

- JDK 9 or later
- Basic Java compilation and command-line usage

This repository is a study and interview-prep workspace containing standalone examples, not a single runnable application.

## Quick Navigation

- [Thread Creation and Lambda Expressions](#1-thread-creation-and-lambda-expressions) | [Open File](src/Thread_Lambda_Expression.java)
- [Race Conditions and Implicit Locks](#2-race-conditions-and-implicit-locks) | [Open File](src/Multithreading_IMPLICIT_LOCKS.java)
- [Thread Communication](#3-thread-communication) | [Open File](src/ThreadCommunication.java)
- [Deadlock and Deadlock Prevention](#4-deadlock-and-deadlock-prevention) | [Open File](src/DeadLock_Example.java)
- [Explicit Locks with `ReentrantLock`](#5-explicit-locks-with-reentrantlock) | [Entry Point](src/ExplicitLocks/ExplicitLocks.java) | [BankAccount](src/ExplicitLocks/BankAccount.java)
- [Reentrancy](#6-reentrancy) | [Open File](src/ExplicitLocks/Reenntrant_Example.java)
- [Fair vs Unfair Locking](#7-fair-vs-unfair-locking) | [Fair Lock](src/ExplicitLocks/FairLock.java) | [Unfair Lock](src/ExplicitLocks/UnfairLock.java)
- [Read-Write Locks](#8-read-write-locks) | [Open File](src/ExplicitLocks/Read_WriteLock.java)
- [Executors Framework Basics](#9-executors-framework-basics) | [Open Notes](src/ExecutorsFramework/README.md)
- [Manual Threads vs Thread Pools](#10-manual-threads-vs-thread-pools) | [Open File](src/ExecutorsFramework/Factorial_Example_Multithreading.java)
- [`Runnable`, `Callable`, and `Future`](#11-runnable-callable-and-future) | [Open File](src/ExecutorsFramework/Submit_Callable_Runnable_Future_Example.java)
- [Scheduled Executors](#12-scheduled-executors) | [Open File](src/ExecutorsFramework/ScheduledExecutorExample.java)
- [CountDownLatch](#countdownlatch) | [Open File](src/ExecutorsFramework/CountDownLatchExample.java)
- [CyclicBarrier](#cyclicbarrier) | [Open File](src/ExecutorsFramework/CyclicBarrierExample.java)
- [CompletableFuture and Async Programming](#14-completablefuture-and-async-programming) | [Open File](src/ExecutorsFramework/CompletableFutureExample.java)

## How to Run

Compile all files:

```bash
javac -d out $(find src -name "*.java")
```

Run an example from the default package:

```bash
java -cp out ThreadCommunication
```

Run an example from a named package:

```bash
java -cp out ExplicitLocks.ExplicitLocks
java -cp out ExecutorsFramework.CompletableFutureExample
```

## What Has Been Studied So Far

### 1. Thread Creation and Lambda Expressions
- Creating threads using `Thread`
- Passing `Runnable` implementations
- Using lambda expressions with functional interfaces
- Capturing values inside lambda expressions

Files:
- [src/Thread_Lambda_Expression.java](src/Thread_Lambda_Expression.java)

### 2. Race Conditions and Implicit Locks
- Shared mutable state problems
- Race conditions on counters
- `synchronized` methods
- `synchronized` blocks
- Mutual exclusion using intrinsic/object monitors
- Using `join()` to wait for thread completion

Files:
- [src/Multithreading_IMPLICIT_LOCKS.java](src/Multithreading_IMPLICIT_LOCKS.java)

### 3. Thread Communication
- Producer-consumer style coordination
- `wait()`
- `notify()`
- Guarded blocks using `while`
- Why `wait()`/`notify()` must be called from synchronized code
- Basic thread-safe communication between producer and consumer threads

Files:
- [src/ThreadCommunication.java](src/ThreadCommunication.java)

### 4. Deadlock and Deadlock Prevention
- How cyclic resource dependency creates deadlock
- Multiple locks across multiple threads
- Prevention by enforcing a consistent lock acquisition order

Files:
- [src/DeadLock_Example.java](src/DeadLock_Example.java)

### 5. Explicit Locks with `ReentrantLock`
- Difference between intrinsic locks and explicit locks
- Manual lock acquisition and release
- `tryLock()`
- Timed lock acquisition with `tryLock(timeout, unit)`
- Importance of `unlock()` in `finally`
- Interrupt handling while working with locks

Files:
- [src/ExplicitLocks/ExplicitLocks.java](src/ExplicitLocks/ExplicitLocks.java)
- [src/ExplicitLocks/BankAccount.java](src/ExplicitLocks/BankAccount.java)

### 6. Reentrancy
- Same thread acquiring the same lock multiple times
- Reentrant behavior of `ReentrantLock`
- Why unlock count must match lock count
- `IllegalMonitorStateException` risk when unlocking incorrectly

Files:
- [src/ExplicitLocks/Reenntrant_Example.java](src/ExplicitLocks/Reenntrant_Example.java)

### 7. Fair vs Unfair Locking
- Fair locks and FIFO-style access
- Avoiding starvation
- Default unfair lock behavior
- Practical effect of fairness on thread scheduling

Files:
- [src/ExplicitLocks/FairLock.java](src/ExplicitLocks/FairLock.java)
- [src/ExplicitLocks/UnfairLock.java](src/ExplicitLocks/UnfairLock.java)

### 8. Read-Write Locks
- Separating read access from write access
- Allowing multiple readers
- Ensuring exclusive writers
- Using `ReentrantReadWriteLock`
- Fair read-write lock configuration

Files:
- [src/ExplicitLocks/Read_WriteLock.java](src/ExplicitLocks/Read_WriteLock.java)

### 9. Executors Framework Basics
- Why executors were introduced
- Benefits over manual thread management
- Thread reuse
- Resource control and scalability
- Core interfaces: `Executor`, `ExecutorService`, `ScheduledExecutorService`
- Cached thread pool concepts, use cases, and bottlenecks

Files:
- [src/ExecutorsFramework/README.md](src/ExecutorsFramework/README.md)

### 10. Manual Threads vs Thread Pools
- Comparing single-threaded, manual multithreaded, and executor-based execution
- Submitting tasks to a fixed thread pool
- Graceful shutdown using `shutdown()`
- Waiting for pool completion using `awaitTermination()`

Files:
- [src/ExecutorsFramework/Factorial_Example_Multithreading.java](src/ExecutorsFramework/Factorial_Example_Multithreading.java)

### 11. `Runnable`, `Callable`, and `Future`
- Difference between `Runnable` and `Callable`
- Returning values from asynchronous tasks
- Using `Future.get()`
- Blocking vs non-blocking status checks using `isDone()`
- Timed `get()`
- Cancelling tasks with `cancel(true)` and `cancel(false)`
- `shutdown()`, termination, and task rejection
- Bulk execution using `invokeAll()` and `invokeAny()`

Files:
- [src/ExecutorsFramework/Submit_Callable_Runnable_Future_Example.java](src/ExecutorsFramework/Submit_Callable_Runnable_Future_Example.java)

### 12. Scheduled Executors
- One-time delayed tasks with `schedule()`
- Periodic execution with `scheduleAtFixedRate()`
- Periodic execution with `scheduleWithFixedDelay()`
- Difference between fixed rate and fixed delay scheduling
- Shutdown behavior for scheduled tasks

Files:
- [src/ExecutorsFramework/ScheduledExecutorExample.java](src/ExecutorsFramework/ScheduledExecutorExample.java)

### 13. Coordination Utilities

#### CountDownLatch
- Waiting for multiple tasks to finish before continuing
- Replacing repeated `Future.get()` calls for bulk coordination
- `await()` and timed `await(timeout, unit)`

Files:
- [src/ExecutorsFramework/CountDownLatchExample.java](src/ExecutorsFramework/CountDownLatchExample.java)

#### CyclicBarrier
- Synchronizing multiple threads at a common barrier point
- Barrier action after all participants arrive
- Typical system startup / subsystem initialization use case

Files:
- [src/ExecutorsFramework/CyclicBarrierExample.java](src/ExecutorsFramework/CyclicBarrierExample.java)

### 14. CompletableFuture and Async Programming
- Asynchronous task execution with `supplyAsync()`
- Non-blocking result access with `getNow()`
- Blocking result retrieval with `get()` and `join()`
- Combining async tasks with `allOf()`
- Transforming results with `thenApply()`
- Timeouts with `orTimeout()`
- Exception fallback with `exceptionally()`
- Running async work on a custom executor

Files:
- [src/ExecutorsFramework/CompletableFutureExample.java](src/ExecutorsFramework/CompletableFutureExample.java)

## Learning Progression in This Repo

The overall study sequence visible in the codebase is:

1. Create threads and use lambdas.
2. Understand race conditions and fix them with `synchronized`.
3. Learn inter-thread communication using `wait()` and `notify()`.
4. Study deadlock scenarios and prevention.
5. Move from intrinsic locks to explicit locks.
6. Explore advanced lock behavior: reentrancy, fairness, and read-write separation.
7. Shift from manual thread management to executor services.
8. Learn task submission, result handling, cancellation, scheduling, coordination, and async composition.

## Project Structure

```text
src/
├── Thread_Lambda_Expression.java
├── Multithreading_IMPLICIT_LOCKS.java
├── ThreadCommunication.java
├── DeadLock_Example.java
├── ExplicitLocks/
│   ├── ExplicitLocks.java
│   ├── BankAccount.java
│   ├── Reenntrant_Example.java
│   ├── FairLock.java
│   ├── UnfairLock.java
│   └── Read_WriteLock.java
└── ExecutorsFramework/
    ├── README.md
    ├── Factorial_Example_Multithreading.java
    ├── Submit_Callable_Runnable_Future_Example.java
    ├── ScheduledExecutorExample.java
    ├── CountDownLatchExample.java
    ├── CyclicBarrierExample.java
    └── CompletableFutureExample.java
```

## Current Coverage Summary

So far, this repository covers:
- Core thread creation
- Lambda-based task definitions
- Intrinsic locking with `synchronized`
- Producer-consumer communication
- Deadlocks
- Explicit locks with `ReentrantLock`
- Fair and unfair locking
- Read-write locks
- Executor services and thread pools
- Task submission and result retrieval
- Future cancellation and timeout handling
- Scheduled task execution
- Synchronization helpers like `CountDownLatch` and `CyclicBarrier`
- Asynchronous composition with `CompletableFuture`

## Possible Next Topics

Natural next areas to study after the current material:
- `Semaphore`
- `Phaser`
- `BlockingQueue`
- `ConcurrentHashMap`
- `AtomicInteger` and other atomic classes
- `volatile`
- Thread interruption best practices
- Fork/Join framework
- Virtual threads and structured concurrency
