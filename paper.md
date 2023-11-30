第一章：云原生服务的定义和范围
云原生服务是指基于容器、微服务架构和Kubernetes等云原生技术的软件交付方式。它具有弹性扩展、快速部署、高可用性等特点，适用于各种规模的应用程序。在云原生服务的架构中，容器负责运行应用程序，Kubernetes负责容器编排和服务治理，微服务架构则提高了应用程序的可维护性和可扩展性。
第二章：云原生服务的研究现状
2.1 国外研究现状
在国外，云原生服务的研究已经取得了显著的成果。一些知名的科技公司如Google、Amazon、Microsoft等已经推出了自己的云原生服务平台，如Google Kubernetes Engine、Amazon Elastic Container Service和Microsoft Azure Kubernetes Service等。这些平台提供了完整的容器编排和服务治理解决方案，能够自动化应用程序的部署和管理流程。同时，一些学术研究机构也对云原生服务进行了深入研究，提出了许多有价值的观点和实验结果。
2.2 国内研究现状
在国内，云原生服务的研究和应用也取得了长足的进步。一些大型互联网公司如阿里巴巴、腾讯、华为等已经将云原生技术应用于自己的业务系统中，并取得了良好的效果。同时，一些学术研究机构和高校也对云原生服务进行了深入研究，提出了一些有特色的解决方案和技术。总体来说，国内在云原生服务的研究和应用方面已经取得了一定的成果，但还需要进一步加强研究和应用推广。


该论文主要介绍了云原生应用程序的概念、特征和优势。作者认为，云原生应用程序是基于云计算平台构建的应用程序，具有高度可扩展性、可靠性和弹性。云原生应用程序可以利用云计算平台的优势，实现快速部署、自动化管理和持续交付。作者还介绍了云原生应用程序的开发方法和工具。
Dennis Gannon, Roger S. Barga, Neel Sundaresan. Cloud-Native Applications.[J], IEEE Cloud Computing, 2017, 4(5): 16-21. 

该论文主要介绍了阿里巴巴的Hologres系统。Hologres是一个云原生服务，用于混合服务/分析处理。Hologres提供了一个统一的查询接口，可以处理结构化和半结构化数据。Hologres还支持实时分析和批量分析。
Hologres的设计目标是提供一个高性能、可扩展、可靠和易用的系统。Hologres使用了一系列技术来实现这些目标，包括分布式数据库、流处理和内存数据库。
Hologres已经在阿里巴巴内部广泛使用，并在多个业务场景中取得了成功。Hologres还被其他公司采用，包括腾讯、京东和百度。
Xiaowei J, Yuejun H, Yu X, Guangran J, Xiaojun J, Chen X, Weihua J, Jun Y, Haitao W, Yuan J, Jihong M, Li S, Kai Z, et al. Alibaba hologres: a cloud-native service for hybrid serving/analytical processing[J], Proceedings of the VLDB Endowment, 2020, 13(12): 3272-3284. 


作者认为单一的通用数据库无法以合理的成本和强大的性能运行人们今天所要求的大量并发和规模的工作负载。专业化有它的价值，但在单个应用环境中使用多个专业系统的复杂性和成本正在变得明显。这种认识促使开发人员和IT决策者寻求在采用新数据库时能支持更广泛用例的数据库。混合事务和分析（HTAP）数据库就是为了解决这个问题而开发的。作者介绍介绍了SinglestoreDB（S2DB），前身为MemSQL，一款分布式通用SQL数据库，设计用于在良好的性能下运行操作和分析工作负载。它是市场上最早的分布式HTAP数据库之一。它可以扩展到有效利用数百个主机、数千个核心和数十TB的RAM，同时仍然提供类似于Oracle或SQL Server等单主机SQL数据库的用户体验。该方法利用了单存储的独特优势，包括其高性能、可扩展性和可靠性。作者还提出了一种新的事务和分析架构，该架构可以有效地处理来自多个数据源的数据。该架构还支持各种分析工作负载，包括实时分析、批处理分析和机器学习。作者在实验中表明，他们的方法可以提供比现有方法更高的性能和可扩展性。
Adam P, Szu-Po W, Joseph V, Zhou S, Yongzhu L, Jack C, Evan B, Eric N H, Robert W, Rodrigo G, Nikita S, et al. Cloud-Native Transactions and Analytics in SingleStore[J], Proceedings. ACM-Sigmod International Conference on Management of Data, 2022: 2340-2352.
本文主要介绍了无服务器计算的概念、架构和设计。作为部署微服务的最有前途的架构，无服务器计算最近在工业界和学术界越来越受到关注。本文通过将架构解耦为四个堆栈层：虚拟化、封装、系统编排和系统协调，来调查和阐述无服务器环境下的研究领域。受安全模型的启发，我们突出显示了每个层中的关键含义和限制，并对未来无服务器计算领域的潜在挑战提出了建议。本文主要介绍了一种在云原生环境中构建事务和分析应用程序的新方法。作者首先介绍了无服务器计算的背景和发展历程，然后详细介绍了无服务器计算的架构和设计。最后，作者讨论了无服务器计算的挑战和未来研究方向。
Zijun L, Linsong G, Jiagan C, Quan C, Bingsheng H, Minyi G, et al. The Serverless Computing Survey: A Technical Primer for Design Architecture[J], ACM Computing Surveys, 2022, 54(10s): 1-34. 


云原生应用构成了设计大规模软件系统的一个新兴趋势。然而，尽管已经出现了许多支持可扩展性的云原生工具和模式，但还没有一个被广泛接受的方法来实证基准测试它们的可扩展性。在本文中，我们提出了一种基准测试方法，让研究人员和实践者可以对云原生应用、框架和部署选项进行实证可扩展性评估。本文主要介绍了一种可配置的方法，用于评估云原生应用程序的可扩展性。该方法基于一个开源工具，该工具可以自动生成负载测试脚本和配置文件。作者还提出了一种新的度量方法，用于评估云原生应用程序的可扩展性。作者在实验中表明，他们的方法可以有效地评估云原生应用程序的可扩展性。
Henning  Sören, Hasselbring  Wilhelm. A configurable method for benchmarking scalability of cloud-native applications[J], Empirical Software Engineering, 2022, 27(6): 1-42.

--- 分布式
在全球分布式数据之间发生冲突的情况下提供 ACID 事务是事务处理协议的珠穆朗玛峰。由于跨大洲网络链路的延迟很高，因此在这种情况下的事务处理成本尤其高，这会增加并发控制和数据复制开销。为了缓解这个问题，我们引入了 Ocean Vista，这是一种保证严格串行化的新型分布式协议。Ocean Vista 可以并行访问冲突的事务，并在常见情况下使用一次往返来支持有效的写入仲裁/读取一个访问。我们在多数据中心云环境中通过实验证明，我们的设计在峰值吞吐量方面比领先的分布式事务处理引擎 (TAPIR) 高出十倍以上，尽管这需要额外的谣言延迟和更受限制的事务模型。
本文主要介绍了一种基于Gossip传播算法的新型地理分布式事务可见性控制方法。该方法利用谣言传播来快速传播事务的提交消息，从而提高事务的可见性。作者还提出了一种新的算法来控制谣言传播的速度，以确保事务的可见性和一致性。作者在实验中表明，他们的方法可以实现高性能的地理分布式事务。
Hua Fan, Wojciech Golab. Gossip-based visibility control for high-performance geo-distributed transactions[J], The Vldb Journal, 2021, 30(1): 93-114.

原子提交问题（ACP）是一种类似于共识的单次一致性协议，旨在模拟故障易发的分布式系统中事务提交协议的属性。我们认为，ACP对于捕捉现代事务型数据存储的复杂性来说过于严格，因为提交协议已经与并发控制集成，并且不同事务的执行是相互依赖的。作为替代方案，我们引入了事务认证服务（TCS），这是一个新的正式问题，捕捉了具有集成并发控制的多次提交事务的安全保证。TCS由一个认证函数参数化，该认证函数可以实例化为支持常见的隔离级别，如可串行化 和快照隔离。然后，我们通过逐步细化实现了TCS的正确性可证明的容错协议。我们的协议取得了比主流方法更好的时间复杂度，这些方法将两阶段提交堆叠在Paxos风格的复制之上。
本文提出了一种新的分布式事务提交协议，称为多重提交协议。该协议允许事务提交在多个阶段进行，从而提高吞吐量和可靠性。在第一阶段，事务提交的一部分被提交，并将其状态标记为"部分提交"。在第二阶段，事务的剩余部分被提交，并将其状态标记为"完全提交"。如果在第一阶段发生错误，则可以撤销部分提交的事务。如果在第二阶段发生错误，则可以撤销完全提交的事务。实验结果表明，多重提交协议可以提高吞吐量和可靠性。
Gregory V. Chockler, Alexey Gotsman. Multi-Shot Distributed Transaction Commit[J], Distributed Computing, 2021, 34(4): 301-318.

服务是一种架构风格，它允许将应用程序设计成一系列相互独立的服务，在功能、可维护性、可部署性和可扩展性方面。例如，两阶段提交协议作为一种经典的分布式事务协议，不适合这种模式。这些协议要求事务中的所有参与者在对事务进行处理之前提交或回滚。这就是为什么选择用Saga来替换2PC的原因。Saga是一种属于“每个服务一个数据库”模式的机制，它保证了许多服务的分布式事务。Saga模式使用一系列本地事务进行事务管理。Saga可以通过一个补偿事务回滚，确保两种可能性之一：所有操作都成功完成，或者运行相应的补偿事务来抵消更改。在本文中，我们试图使用Maude语言对Saga进行验证。我们使用Saga协议进行编排，并介绍了一种验证方法来检查它。论文可能探讨了如何利用Maude这个规范化和形式化建模工具，对SAGA分布式事务进行验证。这可能包括对SAGA协议的形式化规约，通过Maude进行模拟和分析，以确保SAGA事务系统的正确性和可靠性
Manel Djerou, Okba Tibermacine. SAGA Distributed Transactions Verification Using Maude[J], 2022 International Symposium on iNnovative Informatics of Biskra (ISNIB), 2022: 1-6.