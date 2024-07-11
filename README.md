# kafkaElasticSearchLesson
Repo for the code and theory of the Event-Driven Microservices: Spring Boot, Kafka and Elastic course

## Kafka introduction

Kafka is an open source stream processing platform. It is designed to handle data feeds with low latency
and high throughput. 

- It halts the data in an **immutable append-only** structure called topics. This data is call logs or events.
It's immutability ensures that **you will have all history of data feeds** and can get a picture of 
the data **at any point of time.**

Kafka is fast, resilient, scalable and works with high throughput.

- Kafka is fast, It is fast because **it relies on disk caching, and memory mapped files of the
underlying operating system** instead of garbage collector eligible JVM memory. And **memory mapped file** contains the
contents of a file in virtual memory. The mapping between a file and memory space **enables an application to modify
the file by reading and writing directly to the memory.**

- It is resilient as it **relies on the file system instead of memory.** And it keeps all messages on disk.
And by doing that, it also **keeps a configurable amount of replicas to prevent data loss** in case of a failure.
Accessing memory mapped files is faster than using direct read and write operations as it operates on memory.

> In most operating systems, the memory region assigned to a mapped file actually
> is in Kernel's page cache.
> 
> That means no copies will be created in the user memory space, and it will directly operate on this
> cache, which will be faster.

- Kafka has a natural scaling capability thanks to the partitions inside each topic.
Which, again, configurable so that you can scale by just increasing the partition number.

- We can order all the related data inside the same partition because ordering is only guaranteed in a single partition.
To achieve it we use partitioning strategy,

- The important part for us to use Kafka in our microservices architecture, it is a great match for using an event 
driven microservices environments. As Kafka is a solid platform that can hold events and provides nice producer
and consumer APIs to work on events.

### Summary

![Kafka benefit summary](/img/kafka-basics.png "Kafka benefit summary")

## Kafka terms definition

**Topic**, one or more partitions to hold data or events of our system.

**Producer**, feed the data into topics and partitions. Producers can tackle multiple threads at the same time safely.

**Replication**, the data in each partition on different brokers. And assuming all brokers has their own machines,
we don't lose any data in case of broker failure.

**Partition**, data holders. They can only have one consumer. Maximum number of consumers that can work on a kafka topic
is equal to the partition number. The additional consumers will be idle, and won't do any job.

**Consumers**, they can read from multiple partitions. 

> Although we can use one consumer and assign to a new partition, we can use a single consumer to consume all the
> partitions, or we can simply add more consumers, up to the number of partition number, and then use different threats
> or even different processes to consume each partition.
> 
> This way Kafka naturally scales and provides high throughput with low latency as we can distribute the work among the
> consumers on different levels.

### Architecture

![Kafka architecture example](/img/kafke-architecture.png "Kafka architecture example")


## Producer properties

![Kafka basic producer properties](/img/kafka-producer-basics-1.png "Kafka basic producer properties")

![Kafka basic producer properties](/img/kafka-producer-basics-2.png "Kafka basic producer properties")