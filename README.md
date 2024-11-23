# Multiprocessing & Multithread JAVA

Software to differentiate and implement multithreading and multiprocessing.

## Characteristics

- **Concurrent downloads**: Thread and Runnable are used to initiate parallel downloads.
- **Parallel processing**: ForkJoinPool is used to process multiple files in parallel. Each file is a task (FileProcessor) executed in a thread of the pool.

## Requirements

- **Java** (JDk version 11 or higher)

## Implementation

1. **Clone the repository** and navigate to the project directory:

   ```bash
   git clone https://github.com/AliJaen/multiprocessingJAVA
   cd multiprocessingJAVA
   ```

2. **Add valid URLs** in the _Main_ class:

   To find .txt files on the internet you can do an advanced search on Google, for example:

   ```
   site:google.com fileType:txt
   ```

   \*\*Make sure it is a safe place where you search and be cautious with the information you consume.

3. **Excecute the _Main_ file**

## Operation details

### FileDownloader

The FileDownloader class is responsible for performing concurrent downloads through threads and is designed so that each download occurs in a separate thread.

Implements _Runnable_ which allows the **FileDownloader** class to be instantiated as a task that can be executed by a thread.

The _run()_ method contains the unloading logic, which is what will be executed in parallel when a thread is started.

For this exercise run() uses the URL of the file to start reading the file, after reading it it uses **PrintWriter** to save it in the specified directory.

### FileProcessor

The **FileProcessor** class extends _RecursiveTask_, to represent tasks that produce a result (of generic type `T`) in this case it is an integer that represents the number of words per file.

The _compute()_ method is where you define the work that the task will do. This method is executed when the task is invoked and can divide the task into subtasks or perform the work directly. In this case, there is no need to divide the task because each file is already a small unit and does not need further division.

For this exercise compute() opens the file, reads it line by line, divides each line into words, counts the number of words in it, and adds it to the total. To conclude by returning the result.

### Main

Since _FileDownloader_ implements _Runnable_ multiple downloads can be executed in parallel, by passing instances of _FileDownloader_ to threads.
`thread.start()` starts the thread, which executes the FileDownloader run() method in parallel to the main thread (the one running `main`).

Once all the files are downloaded, parallel processing begins with **ForkJoinPool**.
`ForkJoinPool` is a Java framework designed to execute concurrent tasks and take advantage of processor cores. In this case each file is managed by a task (**FileProcessor**).
For each file in the downloads directory, an instance of `FileProcessor` is created
`pool.invoke(task)` assigns the task to the pool to execute on one of its threads.
`ForkJoinPool` optimizes execution by dividing work among its threads (based on the number of available cores).
Finally `pool.invoke(task)` returns the result of the task (in this case, the number of words in the file).
