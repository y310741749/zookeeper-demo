package com.provider.zookeeper;

import org.apache.zookeeper.*;

import java.io.IOException;

public class DistributeServer {
    private String connect="192.168.146.128:2181,192.168.146.128:2182,192.168.146.128:2183";
    private int timeOut=60000;
    private ZooKeeper zkClient;
    private DistributeServer server;
    private String nodeName="jiedian";

    public static void main(String[] args) throws IOException, KeeperException, InterruptedException {
        DistributeServer server= new DistributeServer();
        //注册客户端
        server.getConnect();
        //创建节点
        server.createNode(args[0]);
        //业务逻辑处理
        server.business();
        
    }

    void business() throws InterruptedException {
        Thread.sleep(Long.MAX_VALUE);
    }

    void createNode(String nodeName) throws KeeperException, InterruptedException {
        String s = zkClient.create("/server/s", nodeName.getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL_SEQUENTIAL);
        System.out.println("节点:"+nodeName+"上线");
    }

    void getConnect() throws IOException {
        zkClient = new ZooKeeper(connect, timeOut, new Watcher() {
            @Override
            public void process(WatchedEvent event) {

            }
        });
    }
}
