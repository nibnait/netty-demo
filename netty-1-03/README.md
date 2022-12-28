## 关于ChannelOutboundHandlerAdapter简单使用
ChannelOutboundHandlerAdapter 与 ChannelInboundHandlerAdapter 都是继承于 ChannelHandler，并实现自己的 ChannelXxxHandler。用于在消息管道中不同时机下处理处理消息。

> ChannelInboundHandler 拦截和处理入站事件  
> ChannelOutboundHandler 拦截和处理出站事件  
> ChannelHandler 和 ChannelHandlerContext 通过组合或继承的方式关联到一起成对使用。  
> 事件通过 ChannelHandlerContext 主动调用如 read(msg)、write(msg) 和 fireXXX() 等方法，将事件传播到下一个处理器。注意：入站事件在 ChannelPipeline 双向链表中由头到尾正向传播，出站事件则方向相反。   
> 当客户端连接到服务器时，Netty 新建一个 ChannelPipeline 处理其中的事件，而一个 ChannelPipeline 中含有若干 ChannelHandler。  
> 如果每个客户端连接都新建一个 ChannelHandler 实例，当有大量客户端时，服务器将保存大量的 ChannelHandler 实例。  
> 为此，Netty 提供了 Sharable 注解，如果一个 ChannelHandler 状态无关，那么可将其标注为 Sharable，如此，服务器只需保存一个实例就能处理所有客户端的事件。

