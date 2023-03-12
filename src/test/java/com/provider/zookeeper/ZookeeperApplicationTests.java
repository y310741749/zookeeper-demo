package com.provider.zookeeper;

import org.apache.zookeeper.*;
import org.apache.zookeeper.data.Stat;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.util.List;

@SpringBootTest
class ZookeeperApplicationTests {

    private String connect="192.168.146.128:2181,192.168.146.128:2182,192.168.146.128:2183";
    private int timeOut=60000;

    private ZooKeeper zkClient;

    @Test
    void contextLoads() {
    }

    @Test
    void init() throws IOException {
        zkClient = new ZooKeeper(connect, timeOut, new Watcher() {
            @Override
            public void process(WatchedEvent event) {
                try {
                    List<String> children = zkClient.getChildren("/", true);
                    children.forEach(System.out::println);
                    System.out.println("========================");
                    Thread.sleep(Long.MAX_VALUE);
                } catch (KeeperException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
        });
    }
    //创建节点
    @Test
    void createNode() throws KeeperException, InterruptedException, IOException {
        zkClient = new ZooKeeper(connect, timeOut, new Watcher() {
            @Override
            public void process(WatchedEvent event) {

            }
        });
        String s = zkClient.create("/java", "Hello,Zookeeper".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
        System.out.println(s);
    }

    //监听节点变化
    @Test
    void listenNode() throws IOException, KeeperException, InterruptedException {
        zkClient = new ZooKeeper(connect, timeOut, new Watcher() {
            @Override
            public void process(WatchedEvent event) {
                List<String> children = null;
                try {
                    children = zkClient.getChildren("/", true);
                } catch (KeeperException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                children.forEach(System.out::println);
                System.out.println("========================");
            }
        });
//        List<String> childrens = zkClient.getChildren("/", true);
//        childrens.forEach(System.out::println);
        Thread.sleep(Long.MAX_VALUE);
    }

    //判断节点是否存在
    @Test
    void isExist() throws IOException, KeeperException, InterruptedException {
        zkClient = new ZooKeeper(connect, timeOut, new Watcher() {
            @Override
            public void process(WatchedEvent event) {

            }
        });
        Stat exists = zkClient.exists("/javas", false);
        System.out.println(exists==null);
    }

}
