package com.jx.entity;

import org.springframework.stereotype.Component;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;


//线程锁
@Component
public class LockManager {
    private final Lock lock = new ReentrantLock();

    public Lock getLock() {
        return lock;
    }
}
