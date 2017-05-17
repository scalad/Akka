### Akka框架-并发编程 ###
多核处理器的出现使并发编程（Concurrent Programming）成为开发人员必备的一项技能，许多现代编程语言都致力于解决并发编程问题。并发编程虽然能够提高程序的性能，但传统并发编程的共享内存通信机制对开发人员的编程技能要求很高，需要开发人员通过自身的专业编程技能去避免死锁、互斥等待及竞争条件（Race Condition）等，熟悉Java语言并发编程的读者们对这些问题的理解会比较深刻，这些问题使得并发编程比顺序编程要困难得多。 

Scala语言并没有直接使用Java语言提供的并发编程库，而是通过Actor模型来解决Java并发编程中遇到的各种问题，为并发编程提供了更高级的抽象。

#### 重要概念  ####
并发和并行从宏观来看，都是为进行多任务运行，但并发（Concurrency）和并行（parallelism）两者之间是有区别的。并行是指两个或者两个以上任务在同一时刻同时运行,；而并发是指两个或两个以上的任务在同一时间段内运行，即一个时间段中有几个任务都处于已启动运行到运行完毕之间，这若干任务在同一CPU上运行但任一个时刻点上只有一个任务运行。图121给出了多核处理器下的现代操作系统进程和线程模型，图中进程2的线程1被调用度到处理器的核2上运行、进程3的线程1被调度到处理器的核3上运行，进程2的线程1和进程3的线程1是并行的，它们可以同时运行，而进程1的线程1和线程2都调度到处理器的核1上运行，此外它们还共享线程1的内存空间，在运行时面临着资源竞争包括CPU、内存及其它如IO等，它们在同一时候只能运行一个，但在一段时间内都可以运行，因此进程1的线程1和线程2是并发执行的。 

![](https://github.com/scalad/Akka/blob/master/doc/image/1.png)

图1 进程、线程模型 

#### 横向扩展和纵向扩展  ####
所谓纵向扩展（Scale Up）指的是增加程序的进度或线程数量，提高程序的并发性；而横向扩展（Scale Out）指的是程序可以扩展到其它机器上运行，即通过分布式系统来提到程序的并行度。传统的Java并发编程模型不容易进行纵向扩展，因此并发的线程数越多，程序行为便会变得很难理解和控制，更多的线程加入到资源竞争，出现死锁等情况的概率增加。横向扩展比纵向扩展困难更大，此时的程序变为分布式环境下的应用，情况更为复杂，对开发人员的要求更高。Scala提供的Actor模型可以解决并发应用程序的横向扩展和纵向扩展问题，如图2、图3给出了基本Actor模型的横向扩展和纵向扩展。 

![](https://github.com/scalad/Akka/blob/master/doc/image/2.png)

图2 纵向扩展 

![](https://github.com/scalad/Akka/blob/master/doc/image/3.png)

图3 横向扩展

#### Actor模型 ####
在使用Java语言进行并发编程时，需要特别关注共享的数据结构，线程间的资源竞争容易导致死锁等问题，而Actor模型便是要解决线程和锁带来的问题，Actor是一种基于事件（Event-Based）的轻量级线程，在使用Actor进行并发编程时只需要关注代码结构，而不需要过分关注数据结构，因此Actor最大限度地减少了数据的共享。 Actor由三个重要部分组成，它们是状态（state），行为（Behavior）和邮箱（Mailbox），Actor与Actor之间的交互通过消息发送来完成，Actor模型如图4所示，状态指的是Actor对象的变量信息，它可以是Actor对象中的局部变量、占用的机器资源等，状态只会根据Actor接受的消息而改变，从而避免并发环境下的死锁等问题；行为指的是Actor的计算行为逻辑，它通过处理Actor接收的消息而改变Actor状态；邮箱（mailbox）建立起Actor间的连接，即Actor发送消息后，另外一个Actor将接收的消息放入到邮箱中待后期处理，邮箱的内部实现是通过队列来实现的，队列可以是有界的（Bounded）也可以是无界的（Unbounded），有界队列实现的邮箱容量固定，无界队列实现的邮箱容易不受限制。 

![](https://github.com/scalad/Akka/blob/master/doc/image/4.png)

图4 Actor模型 

不难看出，Actor模型是对现实世界的高度抽象，它具有如下特点：
（1）Actor之间使用消息传递机制进行通信，传递的消息使用的是不可变消息，Actor之间并不共享数据结构，如果有数据共享则通过消息发送的方式进行；

（2） 各Actor都有对应的mailbox，如果其它Actor向该Actor发送消息，消息将入队待后期处理；

（3）Actor间的消息传递通过异步的方式进行，即消息的发送者发送完消息后不必等待回应便可以返回继承处理其它任务。

#### Akka并发编程框架  ####
Scala语言中原生地支持Actor模型，只不过功能还不够强大，从Scala 2.10版本之后，Akka框架成为Scala包的一部分，可以在程序中直接使用。Akka框架作为Akka是一个以Actor模型为基础构建的基于事件的并发编程框架，底层使用Scala语言实现，提供Java和Scala两种API，它属于LightBend公司（原Typesafe公司）体系结构的一部分，如图5所示。 

![](https://github.com/scalad/Akka/blob/master/doc/image/5.png)

图5 Lightbend 体系结构[ ]

Akka框架意在简化高并发、可扩展及分布式应用程序的设计，它具有如下优势： 
（1） 使用Akka框架编写的应用程序既可以横向扩展（Scale Out）、也可纵向扩展（Scale Up）。
 
（2） 编写并发应用程序更简单，Akka提供了更高的抽象，开发人员只需要专注于业务逻辑，而无需像Java语言那样需要处理底级语义如线程、锁及非阻塞IO等。 

（3） 高容错，Akka使用“let it crashes”机制，当Actor出错时可以快速恢复。 

（4） 事件驱动的架构，Akka中的Actor之间的通信采用异步消息发送，能够完美支持事件驱动。 

（5） 位置透明，无论是Actor运行在本地机器还是远程机器上，对于用户来说都是透明的，这极大地简化了多核处理器和分布式系统上的应用程序编程。 

（6） 事务支持能力，支持软件事务内存（software transactional memory，STM），使Actor具有原子消息流的操作能力。

Akka框架由下列十个组件构成： 

（1） akka-actor ：包括经典的Actor、Typed Actors、IO Actor等 

（2） akka-remote：远程Actor 

（3） akka-testkit：测试Actor系统的工具箱 

（4） akka-kernel ：Akka微内核，用于运行精简的微型应用程序服务器，无需运行于Java应用服务器上。 

（5） akka-transactor ：Transactors 即支持事务的 actors，集成了Scala STM 

（6） akka-agent – 代理, 同样集成了Scala STM 

（7） akka-camel – 集成Apache Camel 

（8） akka-zeromq – 集成ZeroMQ 消息队列 

（9） akka-slf4j – 支持SLF4J 日志功能 

（10） akka-filebased-mailbox – 支持基于文件的mailbox

