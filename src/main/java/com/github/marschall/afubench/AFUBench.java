package com.github.marschall.afubench;

import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.State;

/**
 *
 * @see <a href="http://shipilev.net/blog/2015/faster-atomic-fu/">Faster Atomic*FieldUpdaters for Everyone</a>
 */
@State(Scope.Benchmark)
public class AFUBench {

  A a;
  B b;

  @Setup
  public void setup() {
    a = new A();
    b = new B(); // pollute the class hierarchy
  }

  @Benchmark
  public int updater() {
    return a.updater();
  }

  @Benchmark
  public int plain() {
    return a.plain();
  }

  public static class A {
    static final AtomicIntegerFieldUpdater<A> UP = AtomicIntegerFieldUpdater.newUpdater(A.class, "v");

    volatile int v;

    public int updater() {
      return UP.get(this);
    }

    public int plain() {
      return v;
    }
  }

  public static class B extends A {}
}
