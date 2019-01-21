###调用方式顺序描述
#####http接受到调用,然后发起rpc调用为例
- apic-TracerFilter接受到http请求,调用Tracer.serverRecv(param)根据上游traceid/spanid作为当前span(如果为空就生成新的)并TraceContext.setCurrentServerSpan(span);
    - 1.rpc框架Tracer.clientSend(param)会获取当前serverspan,用来作为生成clientspan并将serverspan作为此clientspan的parent.clientspanid=parentspanid+"."+parent自增量
    - 发起rpc调用-比如调用order
        - 1.1 rpc框架调用serverRecv()将头部携带apic的trade信息clientspan,根据信息生成新的serverspan
        - 各种操作
        - 1.1 rpc框架调用serversend(),collect此serverspan
    - 1.rpc框架调用Tracer.clientRecvAsync(clientSpan),结束此次调用
    - 2.rpc框架Tracer.clientSend(param)会获取当前serverspan,用来作为生成clientspan并将serverspan作为此clientspan的parent.clientspanid=parentspanid+"."+parent自增量
    - 发起rpc调用
    - 2.rpc框架调用Tracer.clientRecvAsync(clientSpan),结束此次调用
    - .....
- TracerFilter调用结束,Tracer.serverSend();collect并清空当前线程的serverspan

####span收集过程
- 收集的span可能是clientspan或者serverspan
- 耗时大于某个阈值默认500ms,强制上报.
- serverspan直接入队
- clientspan如果有parent信息且parent节点子节点个数小于256则添加到父节点后面,否则直接入队
- 队列长度1024,Dispatcher负责批量出队..上报到服务端.