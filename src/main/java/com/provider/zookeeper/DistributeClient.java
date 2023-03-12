package com.provider.zookeeper;

import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class DistributeClient {
    private String connect="192.168.146.128:2181,192.168.146.128:2182,192.168.146.128:2183";
    private int timeOut=60000;
    private ZooKeeper zkClient;
    private DistributeServer server;
    private String nodeName="jiedian";


    public static void main(String[] args) throws IOException, KeeperException, InterruptedException {
        DistributeClient client= new DistributeClient();
        //注册客户端
        client.getConnect();
        //创建节点
        client.createNode();
        //业务逻辑处理
        client.business();
    }

    private void business() {
        try {
            Thread.sleep(Long.MAX_VALUE);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void createNode() throws KeeperException, InterruptedException {
        List<String> children = zkClient.getChildren("/server", true);
        List<String> datas=new ArrayList<>();
        children.forEach(p-> {
            try {
                byte[] data = zkClient.getData("/server/" + p, false, null);
                datas.add(new String(data));
            } catch (KeeperException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        datas.forEach(System.out::println);
    }

    private void getConnect() throws IOException {
        zkClient = new ZooKeeper(connect, timeOut, new Watcher() {
            @Override
            public void process(WatchedEvent event) {
                try {
                    createNode();
                } catch (KeeperException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
