### Akka并发编程 ###

* [Scala并发编程-Akka-Actor模型](https://github.com/scalad/Akka/tree/master/doc/actorModel.md)

Akka是Lightbend(前身是Typesafe)公司在HVM上的Actor模型实现，它同时是一个可扩展、引入了多种分布式范式的框。Akka的Actor模式本身可以保证在单个Actor实例中每个行为的原子性，并行的粒度可以细化到单个Actor实例。也即，当行为被封装在一个Actor实例中时，该行为不会阻塞其他Actors实例的行为。
 