/*
 *  Copyright (c) 2014, Facebook, Inc.
 *  All rights reserved.
 *
 *  This source code is licensed under the BSD-style license found in the
 *  LICENSE file in the root directory of this source tree. An additional grant 
 *  of patent rights can be found in the PATENTS file in the same directory.
 *
 */
package com.idealista.android.tasks;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.CancellationException;
import java.util.concurrent.Executors;
import java.util.concurrent.Executor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

public class TaskTest {

  @Rule
  public ExpectedException thrown = ExpectedException.none();

  private void runTaskTest(Callable<com.idealista.android.tasks.Task<?>> callable) {
    try {
      com.idealista.android.tasks.Task<?> task = callable.call();
      task.waitForCompletion();
      if (task.isFaulted()) {
        Exception error = task.getError();
        if (error instanceof RuntimeException) {
          throw (RuntimeException) error;
        }
        throw new RuntimeException(error);
      } else if (task.isCancelled()) {
        throw new RuntimeException(new CancellationException());
      }
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

  @Test
  public void testCache() {
    assertSame(com.idealista.android.tasks.Task.forResult(null), com.idealista.android.tasks.Task.forResult(null));
    com.idealista.android.tasks.Task<Boolean> trueTask = com.idealista.android.tasks.Task.forResult(true);
    assertTrue(trueTask.getResult());
    assertSame(trueTask, com.idealista.android.tasks.Task.forResult(true));
    com.idealista.android.tasks.Task<Boolean> falseTask = com.idealista.android.tasks.Task.forResult(false);
    assertFalse(falseTask.getResult());
    assertSame(falseTask, com.idealista.android.tasks.Task.forResult(false));
    assertSame(com.idealista.android.tasks.Task.cancelled(), com.idealista.android.tasks.Task.cancelled());
  }

  @SuppressWarnings("ThrowableResultOfMethodCallIgnored")
  @Test
  public void testPrimitives() {
    com.idealista.android.tasks.Task<Integer> complete = com.idealista.android.tasks.Task.forResult(5);
    com.idealista.android.tasks.Task<Integer> error = com.idealista.android.tasks.Task.forError(new RuntimeException());
    com.idealista.android.tasks.Task<Integer> cancelled = com.idealista.android.tasks.Task.cancelled();

    assertTrue(complete.isCompleted());
    assertEquals(5, complete.getResult().intValue());
    assertFalse(complete.isFaulted());
    assertFalse(complete.isCancelled());

    assertTrue(error.isCompleted());
    assertTrue(error.getError() instanceof RuntimeException);
    assertTrue(error.isFaulted());
    assertFalse(error.isCancelled());

    assertTrue(cancelled.isCompleted());
    assertFalse(cancelled.isFaulted());
    assertTrue(cancelled.isCancelled());
  }

  @Test
  public void testDelay() throws InterruptedException {
    final com.idealista.android.tasks.Task<Void> delayed = com.idealista.android.tasks.Task.delay(200);
    Thread.sleep(50);
    assertFalse(delayed.isCompleted());
    Thread.sleep(200);
    assertTrue(delayed.isCompleted());
    assertFalse(delayed.isFaulted());
    assertFalse(delayed.isCancelled());
  }

  @Test
  public void testDelayWithCancelledToken() throws InterruptedException {
    com.idealista.android.tasks.CancellationTokenSource cts = new com.idealista.android.tasks.CancellationTokenSource();
    cts.cancel();
    final com.idealista.android.tasks.Task<Void> delayed = com.idealista.android.tasks.Task.delay(200, cts.getToken());
    assertTrue(delayed.isCancelled());
  }

  @Test
  public void testDelayWithToken() throws InterruptedException {
    com.idealista.android.tasks.CancellationTokenSource cts = new com.idealista.android.tasks.CancellationTokenSource();
    final com.idealista.android.tasks.Task<Void> delayed = com.idealista.android.tasks.Task.delay(200, cts.getToken());
    assertFalse(delayed.isCancelled());
    cts.cancel();
    assertTrue(delayed.isCancelled());
  }

  @Test
  public void testSynchronousContinuation() {
    final com.idealista.android.tasks.Task<Integer> complete = com.idealista.android.tasks.Task.forResult(5);
    final com.idealista.android.tasks.Task<Integer> error = com.idealista.android.tasks.Task.forError(new RuntimeException());
    final com.idealista.android.tasks.Task<Integer> cancelled = com.idealista.android.tasks.Task.cancelled();

    complete.continueWith(new com.idealista.android.tasks.Continuation<Integer, Void>() {
      public Void then(com.idealista.android.tasks.Task<Integer> task) {
        assertEquals(complete, task);
        assertTrue(task.isCompleted());
        assertEquals(5, task.getResult().intValue());
        assertFalse(task.isFaulted());
        assertFalse(task.isCancelled());
        return null;
      }
    });

    error.continueWith(new com.idealista.android.tasks.Continuation<Integer, Void>() {
      public Void then(com.idealista.android.tasks.Task<Integer> task) {
        assertEquals(error, task);
        assertTrue(task.isCompleted());
        assertTrue(task.getError() instanceof RuntimeException);
        assertTrue(task.isFaulted());
        assertFalse(task.isCancelled());
        return null;
      }
    });

    cancelled.continueWith(new com.idealista.android.tasks.Continuation<Integer, Void>() {
      public Void then(com.idealista.android.tasks.Task<Integer> task) {
        assertEquals(cancelled, task);
        assertTrue(cancelled.isCompleted());
        assertFalse(cancelled.isFaulted());
        assertTrue(cancelled.isCancelled());
        return null;
      }
    });
  }

  @Test
  public void testSynchronousChaining() {
    com.idealista.android.tasks.Task<Integer> first = com.idealista.android.tasks.Task.forResult(1);
    com.idealista.android.tasks.Task<Integer> second = first.continueWith(new com.idealista.android.tasks.Continuation<Integer, Integer>() {
      public Integer then(com.idealista.android.tasks.Task<Integer> task) {
        return 2;
      }
    });
    com.idealista.android.tasks.Task<Integer> third = second.continueWithTask(new com.idealista.android.tasks.Continuation<Integer, com.idealista.android.tasks.Task<Integer>>() {
      public com.idealista.android.tasks.Task<Integer> then(com.idealista.android.tasks.Task<Integer> task) {
        return com.idealista.android.tasks.Task.forResult(3);
      }
    });
    assertTrue(first.isCompleted());
    assertTrue(second.isCompleted());
    assertTrue(third.isCompleted());
    assertEquals(1, first.getResult().intValue());
    assertEquals(2, second.getResult().intValue());
    assertEquals(3, third.getResult().intValue());
  }

  @Test
  public void testSynchronousCancellation() {
    com.idealista.android.tasks.Task<Integer> first = com.idealista.android.tasks.Task.forResult(1);
    com.idealista.android.tasks.Task<Integer> second = first.continueWith(new com.idealista.android.tasks.Continuation<Integer, Integer>() {
      public Integer then(com.idealista.android.tasks.Task<Integer> task) {
        throw new CancellationException();
      }
    });
    assertTrue(first.isCompleted());
    assertTrue(second.isCancelled());
  }

  @Test
  public void testSynchronousContinuationTokenAlreadyCancelled() {
    com.idealista.android.tasks.CancellationTokenSource cts = new com.idealista.android.tasks.CancellationTokenSource();
    final com.idealista.android.tasks.Capture<Boolean> continuationRun = new com.idealista.android.tasks.Capture<>(false);
    cts.cancel();
    com.idealista.android.tasks.Task<Integer> first = com.idealista.android.tasks.Task.forResult(1);
    com.idealista.android.tasks.Task<Integer> second = first.continueWith(new com.idealista.android.tasks.Continuation<Integer, Integer>() {
      public Integer then(com.idealista.android.tasks.Task<Integer> task) {
        continuationRun.set(true);
        return 2;
      }
    }, cts.getToken());
    assertTrue(first.isCompleted());
    assertTrue(second.isCancelled());
    assertFalse(continuationRun.get());
  }

  @Test
  public void testSynchronousTaskCancellation() {
    com.idealista.android.tasks.Task<Integer> first = com.idealista.android.tasks.Task.forResult(1);
    com.idealista.android.tasks.Task<Integer> second = first.continueWithTask(new com.idealista.android.tasks.Continuation<Integer, com.idealista.android.tasks.Task<Integer>>() {
      public com.idealista.android.tasks.Task<Integer> then(com.idealista.android.tasks.Task<Integer> task) {
        throw new CancellationException();
      }
    });
    assertTrue(first.isCompleted());
    assertTrue(second.isCancelled());
  }

  @Test
  public void testBackgroundCall() {
    runTaskTest(new Callable<com.idealista.android.tasks.Task<?>>() {
      public com.idealista.android.tasks.Task<?> call() throws Exception {
        return com.idealista.android.tasks.Task.callInBackground(new Callable<Integer>() {
          public Integer call() throws Exception {
            Thread.sleep(100);
            return 5;
          }
        }).continueWith(new com.idealista.android.tasks.Continuation<Integer, Void>() {
          public Void then(com.idealista.android.tasks.Task<Integer> task) {
            assertEquals(5, task.getResult().intValue());
            return null;
          }
        });
      }
    });
  }

  @Test
  public void testBackgroundCallTokenCancellation() {
    final com.idealista.android.tasks.CancellationTokenSource cts = new com.idealista.android.tasks.CancellationTokenSource();
    final com.idealista.android.tasks.CancellationToken ct = cts.getToken();
    final com.idealista.android.tasks.Capture<Boolean> waitingToBeCancelled = new Capture<>(false);
    final Object cancelLock = new Object();

    com.idealista.android.tasks.Task<Integer> task = com.idealista.android.tasks.Task.callInBackground(new Callable<Integer>() {
      @Override
      public Integer call() throws Exception {
        synchronized (cancelLock) {
          waitingToBeCancelled.set(true);
          cancelLock.wait();
        }
        ct.throwIfCancellationRequested();
        return 5;
      }
    });

    while (true) {
      synchronized (cancelLock) {
        if (waitingToBeCancelled.get()) {
          cts.cancel();
          cancelLock.notify();
          break;
        }
      }
      try {
        Thread.sleep(5);
      } catch (InterruptedException e) {
        throw new RuntimeException(e);
      }
    }

    try {
      task.waitForCompletion();
    } catch (InterruptedException e) {
      throw new RuntimeException(e);
    }

    assertTrue(task.isCancelled());
  }

  @Test
  public void testBackgroundCallTokenAlreadyCancelled() {
    final com.idealista.android.tasks.CancellationTokenSource cts = new com.idealista.android.tasks.CancellationTokenSource();

    cts.cancel();
    runTaskTest(new Callable<com.idealista.android.tasks.Task<?>>() {
      public com.idealista.android.tasks.Task<?> call() throws Exception {
        return com.idealista.android.tasks.Task.callInBackground(new Callable<Integer>() {
          public Integer call() throws Exception {
            Thread.sleep(100);
            return 5;
          }
        }, cts.getToken()).continueWith(new com.idealista.android.tasks.Continuation<Integer, Void>() {
          public Void then(com.idealista.android.tasks.Task<Integer> task) {
            assertTrue(task.isCancelled());
            return null;
          }
        });
      }
    });
  }

  @Test
  public void testBackgroundCallWaiting() throws Exception {
    com.idealista.android.tasks.Task<Integer> task = com.idealista.android.tasks.Task.callInBackground(new Callable<Integer>() {
      public Integer call() throws Exception {
        Thread.sleep(100);
        return 5;
      }
    });
    task.waitForCompletion();
    assertTrue(task.isCompleted());
    assertEquals(5, task.getResult().intValue());
  }

  @Test
  public void testBackgroundCallWaitingWithTimeouts() throws Exception {
    final Object sync = new Object();

    com.idealista.android.tasks.Task<Integer> task = com.idealista.android.tasks.Task.callInBackground(new Callable<Integer>() {
      public Integer call() throws Exception {
        synchronized (sync) {
          sync.wait();
          Thread.sleep(100);
        }
        return 5;
      }
    });
    // wait -> timeout
    assertFalse(task.waitForCompletion(100, TimeUnit.MILLISECONDS));
    synchronized (sync) {
      sync.notify();
    }
    // wait -> completes
    assertTrue(task.waitForCompletion(1000, TimeUnit.MILLISECONDS));
    // wait -> already completed
    assertTrue(task.waitForCompletion(100, TimeUnit.MILLISECONDS));
    assertEquals(5, task.getResult().intValue());
  }

  @Test
  public void testBackgroundCallWaitingOnError() throws Exception {
    com.idealista.android.tasks.Task<Integer> task = com.idealista.android.tasks.Task.callInBackground(new Callable<Integer>() {
      public Integer call() throws Exception {
        Thread.sleep(100);
        throw new RuntimeException();
      }
    });
    task.waitForCompletion();
    assertTrue(task.isCompleted());
    assertTrue(task.isFaulted());
  }

  @Test
  public void testBackgroundCallWaitOnCancellation() throws Exception {
    com.idealista.android.tasks.Task<Integer> task = com.idealista.android.tasks.Task.callInBackground(new Callable<Integer>() {
      public Integer call() throws Exception {
        Thread.sleep(100);
        return 5;
      }
    }).continueWithTask(new com.idealista.android.tasks.Continuation<Integer, com.idealista.android.tasks.Task<Integer>>() {

      public com.idealista.android.tasks.Task<Integer> then(com.idealista.android.tasks.Task<Integer> task) {
        return com.idealista.android.tasks.Task.cancelled();
      }
    });
    task.waitForCompletion();
    assertTrue(task.isCompleted());
    assertTrue(task.isCancelled());
  }

  @Test
  public void testBackgroundError() {
    runTaskTest(new Callable<com.idealista.android.tasks.Task<?>>() {
      public com.idealista.android.tasks.Task<?> call() throws Exception {
        return com.idealista.android.tasks.Task.callInBackground(new Callable<Integer>() {
          public Integer call() throws Exception {
            throw new IllegalStateException();
          }
        }).continueWith(new com.idealista.android.tasks.Continuation<Integer, Void>() {
          public Void then(com.idealista.android.tasks.Task<Integer> task) {
            assertTrue(task.isFaulted());
            assertTrue(task.getError() instanceof IllegalStateException);
            return null;
          }
        });
      }
    });
  }

  @Test
  public void testBackgroundCancellation() {
    runTaskTest(new Callable<com.idealista.android.tasks.Task<?>>() {
      public com.idealista.android.tasks.Task<?> call() throws Exception {
        return com.idealista.android.tasks.Task.callInBackground(new Callable<Void>() {
          public Void call() throws Exception {
            throw new CancellationException();
          }
        }).continueWith(new com.idealista.android.tasks.Continuation<Void, Void>() {
          public Void then(com.idealista.android.tasks.Task<Void> task) {
            assertTrue(task.isCancelled());
            return null;
          }
        });
      }
    });
  }

  @Test
  public void testUnobservedError() throws InterruptedException {
    try {
      final Object sync = new Object();
      com.idealista.android.tasks.Task.setUnobservedExceptionHandler(new com.idealista.android.tasks.Task.UnobservedExceptionHandler() {
        @Override
        public void unobservedException(com.idealista.android.tasks.Task<?> t, UnobservedTaskException e) {
          synchronized (sync) {
            sync.notify();
          }
        }
      });

      synchronized (sync) {
        startFailedTask();
        System.gc();
        sync.wait();
      }

    } finally {
      com.idealista.android.tasks.Task.setUnobservedExceptionHandler(null);
    }
  }

  // runs in a separate method to ensure it is out of scope.
  private void startFailedTask() throws InterruptedException {
    com.idealista.android.tasks.Task.call(new Callable<Object>() {
      @Override
      public Object call() throws Exception {
        throw new RuntimeException();
      }
    }).waitForCompletion();
  }

  @Test
  public void testWhenAllNoTasks() {
    com.idealista.android.tasks.Task<Void> task = com.idealista.android.tasks.Task.whenAll(new ArrayList<com.idealista.android.tasks.Task<Void>>());

    assertTrue(task.isCompleted());
    assertFalse(task.isCancelled());
    assertFalse(task.isFaulted());
  }
    
  @Test
  public void testWhenAnyResultFirstSuccess() {
    runTaskTest(new Callable<com.idealista.android.tasks.Task<?>>() {
      @Override
      public com.idealista.android.tasks.Task<?> call() throws Exception {
        final ArrayList<com.idealista.android.tasks.Task<Integer>> tasks = new ArrayList<>();
        final com.idealista.android.tasks.Task<Integer> firstToCompleteSuccess = com.idealista.android.tasks.Task.callInBackground(new Callable<Integer>() {
          @Override
          public Integer call() throws Exception {
            Thread.sleep(50);
            return 10;
          }
        });
        tasks.addAll(launchTasksWithRandomCompletions(5));
        tasks.add(firstToCompleteSuccess);
        tasks.addAll(launchTasksWithRandomCompletions(5));
        return com.idealista.android.tasks.Task.whenAnyResult(tasks).continueWith(new com.idealista.android.tasks.Continuation<com.idealista.android.tasks.Task<Integer>, Void>() {
          @Override
          public Void then(com.idealista.android.tasks.Task<com.idealista.android.tasks.Task<Integer>> task) throws Exception {
            assertTrue(task.isCompleted());
            assertFalse(task.isFaulted());
            assertFalse(task.isCancelled());
            assertEquals(firstToCompleteSuccess, task.getResult());
            assertTrue(task.getResult().isCompleted());
            assertFalse(task.getResult().isCancelled());
            assertFalse(task.getResult().isFaulted());
            assertEquals(10, (int) task.getResult().getResult());
            return null;
          }
        });
      }
    });
  }
    
  @Test
  public void testWhenAnyFirstSuccess() {
    runTaskTest(new Callable<com.idealista.android.tasks.Task<?>>() {
      @Override
      public com.idealista.android.tasks.Task<?> call() throws Exception {
        final ArrayList<com.idealista.android.tasks.Task<?>> tasks = new ArrayList<>();
        final com.idealista.android.tasks.Task<String> firstToCompleteSuccess = com.idealista.android.tasks.Task.callInBackground(new Callable<String>() {
          @Override
          public String call() throws Exception {
            Thread.sleep(50);
            return "SUCCESS";
          }
        });
        tasks.addAll(launchTasksWithRandomCompletions(5));
        tasks.add(firstToCompleteSuccess);
        tasks.addAll(launchTasksWithRandomCompletions(5));
        return com.idealista.android.tasks.Task.whenAny(tasks).continueWith(new com.idealista.android.tasks.Continuation<com.idealista.android.tasks.Task<?>, Object>() {
          @Override
          public Object then(com.idealista.android.tasks.Task<com.idealista.android.tasks.Task<?>> task) throws Exception {
            assertTrue(task.isCompleted());
            assertFalse(task.isFaulted());
            assertFalse(task.isCancelled());
            assertEquals(firstToCompleteSuccess, task.getResult());
            assertTrue(task.getResult().isCompleted());
            assertFalse(task.getResult().isCancelled());
            assertFalse(task.getResult().isFaulted());
            assertEquals("SUCCESS", task.getResult().getResult());
            return null;
          }
        });
      }
    });
  }

  @Test
  public void testWhenAnyResultFirstError() {
    final Exception error = new RuntimeException("This task failed.");
    runTaskTest(new Callable<com.idealista.android.tasks.Task<?>>() {
      @Override
      public com.idealista.android.tasks.Task<?> call() throws Exception {
        final ArrayList<com.idealista.android.tasks.Task<Integer>> tasks = new ArrayList<>();
        final com.idealista.android.tasks.Task<Integer> firstToCompleteError = com.idealista.android.tasks.Task.callInBackground(new Callable<Integer>() {
          @Override
          public Integer call() throws Exception {
            Thread.sleep(50);
            throw error;
          }
        });
        tasks.addAll(launchTasksWithRandomCompletions(5));
        tasks.add(firstToCompleteError);
        tasks.addAll(launchTasksWithRandomCompletions(5));
        return com.idealista.android.tasks.Task.whenAnyResult(tasks).continueWith(new com.idealista.android.tasks.Continuation<com.idealista.android.tasks.Task<Integer>, Object>() {
          @Override
          public Object then(com.idealista.android.tasks.Task<com.idealista.android.tasks.Task<Integer>> task) throws Exception {
            assertTrue(task.isCompleted());
            assertFalse(task.isFaulted());
            assertFalse(task.isCancelled());
            assertEquals(firstToCompleteError, task.getResult());
            assertTrue(task.getResult().isCompleted());
            assertFalse(task.getResult().isCancelled());
            assertTrue(task.getResult().isFaulted());
            assertEquals(error, task.getResult().getError());
            return null;
          }
        });
      }
    });
  }

  @Test
  public void testWhenAnyFirstError() {
    final Exception error = new RuntimeException("This task failed.");
    runTaskTest(new Callable<com.idealista.android.tasks.Task<?>>() {
      @Override
      public com.idealista.android.tasks.Task<?> call() throws Exception {
        final ArrayList<com.idealista.android.tasks.Task<?>> tasks = new ArrayList<>();
        final com.idealista.android.tasks.Task<String> firstToCompleteError = com.idealista.android.tasks.Task.callInBackground(new Callable<String>() {
          @Override
          public String call() throws Exception {
            Thread.sleep(50);
            throw error;
          }
        });
        tasks.addAll(launchTasksWithRandomCompletions(5));
        tasks.add(firstToCompleteError);
        tasks.addAll(launchTasksWithRandomCompletions(5));
        return com.idealista.android.tasks.Task.whenAny(tasks).continueWith(new com.idealista.android.tasks.Continuation<com.idealista.android.tasks.Task<?>, Object>() {
          @Override
          public Object then(com.idealista.android.tasks.Task<com.idealista.android.tasks.Task<?>> task) throws Exception {
            assertTrue(task.isCompleted());
            assertFalse(task.isFaulted());
            assertFalse(task.isCancelled());
            assertEquals(firstToCompleteError, task.getResult());
            assertTrue(task.getResult().isCompleted());
            assertFalse(task.getResult().isCancelled());
            assertTrue(task.getResult().isFaulted());
            assertEquals(error, task.getResult().getError());
            return null;
          }
        });
      }
    });
  }

  @Test
  public void testWhenAnyResultFirstCancelled() {
    runTaskTest(new Callable<com.idealista.android.tasks.Task<?>>() {
      @Override
      public com.idealista.android.tasks.Task<?> call() throws Exception {
        final ArrayList<com.idealista.android.tasks.Task<Integer>> tasks = new ArrayList<>();
        final com.idealista.android.tasks.Task<Integer> firstToCompleteCancelled = com.idealista.android.tasks.Task.callInBackground(new Callable<Integer>() {
          @Override
          public Integer call() throws Exception {
            Thread.sleep(50);
            throw new CancellationException();
          }
        });
         
        tasks.addAll(launchTasksWithRandomCompletions(5));
        tasks.add(firstToCompleteCancelled);
        tasks.addAll(launchTasksWithRandomCompletions(5));
        return com.idealista.android.tasks.Task.whenAnyResult(tasks).continueWith(new com.idealista.android.tasks.Continuation<com.idealista.android.tasks.Task<Integer>, Object>() {
          @Override
          public Object then(com.idealista.android.tasks.Task<com.idealista.android.tasks.Task<Integer>> task) throws Exception {
            assertTrue(task.isCompleted());
            assertFalse(task.isFaulted());
            assertFalse(task.isCancelled());
            assertEquals(firstToCompleteCancelled, task.getResult());
            assertTrue(task.getResult().isCompleted());
            assertTrue(task.getResult().isCancelled());
            assertFalse(task.getResult().isFaulted());
            return null;
          }
        });
      }
    });
  }
    
  @Test
  public void testWhenAnyFirstCancelled() {
    runTaskTest(new Callable<com.idealista.android.tasks.Task<?>>() {
      @Override
      public com.idealista.android.tasks.Task<?> call() throws Exception {
        final ArrayList<com.idealista.android.tasks.Task<?>> tasks = new ArrayList<>();
        final com.idealista.android.tasks.Task<String> firstToCompleteCancelled = com.idealista.android.tasks.Task.callInBackground(new Callable<String>() {
          @Override
          public String call() throws Exception {
            Thread.sleep(50);
            throw new CancellationException();
          }
        });
        tasks.addAll(launchTasksWithRandomCompletions(5));
        tasks.add(firstToCompleteCancelled);
        tasks.addAll(launchTasksWithRandomCompletions(5));
        return com.idealista.android.tasks.Task.whenAny(tasks).continueWith(new com.idealista.android.tasks.Continuation<com.idealista.android.tasks.Task<?>, Object>() {
          @Override
          public Object then(com.idealista.android.tasks.Task<com.idealista.android.tasks.Task<?>> task) throws Exception {
            assertTrue(task.isCompleted());
            assertFalse(task.isFaulted());
            assertFalse(task.isCancelled());
            assertEquals(firstToCompleteCancelled, task.getResult());
            assertTrue(task.getResult().isCompleted());
            assertTrue(task.getResult().isCancelled());
            assertFalse(task.getResult().isFaulted());
            return null;
          }
        });
      }
    });
  }

  /**
   * Launches a given number of tasks (of Integer) that will complete either in a completed, 
   * cancelled or faulted state (random distribution).
   * Each task will reach completion after a somehow random delay (between 500 and 600 ms).
   * Each task reaching a success completion state will have its result set to a random Integer 
   * (between 0 to 1000).
   * @param numberOfTasksToLaunch The number of tasks to launch
   * @return A collection containing all the tasks that have been launched
   */
  private Collection<com.idealista.android.tasks.Task<Integer>> launchTasksWithRandomCompletions(int numberOfTasksToLaunch ) {
    final ArrayList<com.idealista.android.tasks.Task<Integer>> tasks = new ArrayList<>();
    for (int i=0; i < numberOfTasksToLaunch; i++) {
      com.idealista.android.tasks.Task<Integer> task = com.idealista.android.tasks.Task.callInBackground(new Callable<Integer>() {
        @Override
        public Integer call() throws Exception {
          Thread.sleep((long) (500 + (Math.random() * 100)));
          double rand = Math.random();
          if (rand >= 0.7) {
            throw new RuntimeException("This task failed.");
          } else if (rand >= 0.4) {
          throw new CancellationException();
          }
          return (int)(Math.random() * 1000);
        }
      });
      tasks.add(task);
    }
    return tasks;
  }

  @Test
  public void testWhenAllSuccess() {
    runTaskTest(new Callable<com.idealista.android.tasks.Task<?>>() {
      @Override
      public com.idealista.android.tasks.Task<?> call() throws Exception {
        final ArrayList<com.idealista.android.tasks.Task<Void>> tasks = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
          com.idealista.android.tasks.Task<Void> task = com.idealista.android.tasks.Task.callInBackground(new Callable<Void>() {
            @Override
            public Void call() throws Exception {
              Thread.sleep((long) (Math.random() * 100));
              return null;
            }
          });
          tasks.add(task);
        }
        return com.idealista.android.tasks.Task.whenAll(tasks).continueWith(new com.idealista.android.tasks.Continuation<Void, Void>() {
          @Override
          public Void then(com.idealista.android.tasks.Task<Void> task) {
            assertTrue(task.isCompleted());
            assertFalse(task.isFaulted());
            assertFalse(task.isCancelled());

            for (com.idealista.android.tasks.Task<Void> t : tasks) {
              assertTrue(t.isCompleted());
            }
            return null;
          }
        });
      }
    });
  }

  @Test
  public void testWhenAllOneError() {
    final Exception error = new RuntimeException("This task failed.");

    runTaskTest(new Callable<com.idealista.android.tasks.Task<?>>() {
      @Override
      public com.idealista.android.tasks.Task<?> call() throws Exception {
        final ArrayList<com.idealista.android.tasks.Task<Void>> tasks = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
          final int number = i;
          com.idealista.android.tasks.Task<Void> task = com.idealista.android.tasks.Task.callInBackground(new Callable<Void>() {
            @Override
            public Void call() throws Exception {
              Thread.sleep((long) (Math.random() * 100));
              if (number == 10) {
                throw error;
              }
              return null;
            }
          });
          tasks.add(task);
        }
        return com.idealista.android.tasks.Task.whenAll(tasks).continueWith(new com.idealista.android.tasks.Continuation<Void, Void>() {
          @Override
          public Void then(com.idealista.android.tasks.Task<Void> task) {
            assertTrue(task.isCompleted());
            assertTrue(task.isFaulted());
            assertFalse(task.isCancelled());

            assertFalse(task.getError() instanceof AggregateException);
            assertEquals(error, task.getError());

            for (com.idealista.android.tasks.Task<Void> t : tasks) {
              assertTrue(t.isCompleted());
            }
            return null;
          }
        });
      }
    });
  }

  @Test
  public void testWhenAllTwoErrors() {
    final Exception error0 = new RuntimeException("This task failed (0).");
    final Exception error1 = new RuntimeException("This task failed (1).");

    runTaskTest(new Callable<com.idealista.android.tasks.Task<?>>() {
      @Override
      public com.idealista.android.tasks.Task<?> call() throws Exception {
        final ArrayList<com.idealista.android.tasks.Task<Void>> tasks = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
          final int number = i;
          com.idealista.android.tasks.Task<Void> task = com.idealista.android.tasks.Task.callInBackground(new Callable<Void>() {
            @Override
            public Void call() throws Exception {
              Thread.sleep((long) (number * 10));
              if (number == 10) {
                throw error0;
              } else if (number == 11) {
                throw error1;
              }
              return null;
            }
          });
          tasks.add(task);
        }
        return com.idealista.android.tasks.Task.whenAll(tasks).continueWith(new com.idealista.android.tasks.Continuation<Void, Void>() {
          @Override
          public Void then(com.idealista.android.tasks.Task<Void> task) {
            assertTrue(task.isCompleted());
            assertTrue(task.isFaulted());
            assertFalse(task.isCancelled());

            assertTrue(task.getError() instanceof AggregateException);
            assertEquals(2, ((AggregateException) task.getError()).getInnerThrowables().size());
            assertEquals(error0, ((AggregateException) task.getError()).getInnerThrowables().get(0));
            assertEquals(error1, ((AggregateException) task.getError()).getInnerThrowables().get(1));
            assertEquals(error0, task.getError().getCause());

            for (com.idealista.android.tasks.Task<Void> t : tasks) {
              assertTrue(t.isCompleted());
            }
            return null;
          }
        });
      }
    });
  }

  @Test
  public void testWhenAllCancel() {
    runTaskTest(new Callable<com.idealista.android.tasks.Task<?>>() {
      @Override
      public com.idealista.android.tasks.Task<?> call() throws Exception {
        final ArrayList<com.idealista.android.tasks.Task<Void>> tasks = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
          final com.idealista.android.tasks.TaskCompletionSource<Void> tcs = new com.idealista.android.tasks.TaskCompletionSource<>();

          final int number = i;
          com.idealista.android.tasks.Task.callInBackground(new Callable<Void>() {
            @Override
            public Void call() throws Exception {
              Thread.sleep((long) (Math.random() * 100));
              if (number == 10) {
                tcs.setCancelled();
              } else {
                tcs.setResult(null);
              }
              return null;
            }
          });

          tasks.add(tcs.getTask());
        }
        return com.idealista.android.tasks.Task.whenAll(tasks).continueWith(new com.idealista.android.tasks.Continuation<Void, Void>() {
          @Override
          public Void then(com.idealista.android.tasks.Task<Void> task) {
            assertTrue(task.isCompleted());
            assertFalse(task.isFaulted());
            assertTrue(task.isCancelled());

            for (com.idealista.android.tasks.Task<Void> t : tasks) {
              assertTrue(t.isCompleted());
            }
            return null;
          }
        });
      }
    });
  }

  @Test
  public void testWhenAllResultNoTasks() {
    com.idealista.android.tasks.Task<List<Void>> task = com.idealista.android.tasks.Task.whenAllResult(new ArrayList<com.idealista.android.tasks.Task<Void>>());

    assertTrue(task.isCompleted());
    assertFalse(task.isCancelled());
    assertFalse(task.isFaulted());
    assertTrue(task.getResult().isEmpty());
  }

  @Test
  public void testWhenAllResultSuccess() {
    runTaskTest(new Callable<com.idealista.android.tasks.Task<?>>() {
      @Override
      public com.idealista.android.tasks.Task<?> call() throws Exception {
        final List<com.idealista.android.tasks.Task<Integer>> tasks = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
          final int number = (i + 1);
          com.idealista.android.tasks.Task<Integer> task = com.idealista.android.tasks.Task.callInBackground(new Callable<Integer>() {
            @Override
            public Integer call() throws Exception {
              Thread.sleep((long) (Math.random() * 100));
              return number;
            }
          });
          tasks.add(task);
        }
        return com.idealista.android.tasks.Task.whenAllResult(tasks).continueWith(new com.idealista.android.tasks.Continuation<List<Integer>, Void>() {
          @Override
          public Void then(com.idealista.android.tasks.Task<List<Integer>> task) {
            assertTrue(task.isCompleted());
            assertFalse(task.isFaulted());
            assertFalse(task.isCancelled());
            assertEquals(tasks.size(), task.getResult().size());

            for (int i = 0; i < tasks.size(); i++) {
              com.idealista.android.tasks.Task<Integer> t = tasks.get(i);
              assertTrue(t.isCompleted());
              assertEquals(t.getResult(), task.getResult().get(i));
            }

            return null;
          }
        });
      }
    });
  }

  @Test
  public void testAsyncChaining() {
    runTaskTest(new Callable<com.idealista.android.tasks.Task<?>>() {
      public com.idealista.android.tasks.Task<?> call() throws Exception {
        final ArrayList<Integer> sequence = new ArrayList<>();
        com.idealista.android.tasks.Task<Void> result = com.idealista.android.tasks.Task.forResult(null);
        for (int i = 0; i < 20; i++) {
          final int taskNumber = i;
          result = result.continueWithTask(new com.idealista.android.tasks.Continuation<Void, com.idealista.android.tasks.Task<Void>>() {
            public com.idealista.android.tasks.Task<Void> then(com.idealista.android.tasks.Task<Void> task) {
              return com.idealista.android.tasks.Task.callInBackground(new Callable<Void>() {
                public Void call() throws Exception {
                  sequence.add(taskNumber);
                  return null;
                }
              });
            }
          });
        }
        result = result.continueWith(new com.idealista.android.tasks.Continuation<Void, Void>() {
          public Void then(com.idealista.android.tasks.Task<Void> task) {
            assertEquals(20, sequence.size());
            for (int i = 0; i < 20; i++) {
              assertEquals(i, sequence.get(i).intValue());
            }
            return null;
          }
        });
        return result;
      }
    });
  }

  @Test
  public void testOnSuccess() {
    com.idealista.android.tasks.Continuation<Integer, Integer> continuation = new com.idealista.android.tasks.Continuation<Integer, Integer>() {
      public Integer then(com.idealista.android.tasks.Task<Integer> task) {
        return task.getResult() + 1;
      }
    };
    com.idealista.android.tasks.Task<Integer> complete = com.idealista.android.tasks.Task.forResult(5).onSuccess(continuation);
    com.idealista.android.tasks.Task<Integer> error = com.idealista.android.tasks.Task.<Integer> forError(new IllegalStateException()).onSuccess(
        continuation);
    com.idealista.android.tasks.Task<Integer> cancelled = com.idealista.android.tasks.Task.<Integer> cancelled().onSuccess(continuation);

    assertTrue(complete.isCompleted());
    assertEquals(6, complete.getResult().intValue());
    assertFalse(complete.isFaulted());
    assertFalse(complete.isCancelled());

    assertTrue(error.isCompleted());
    assertTrue(error.getError() instanceof RuntimeException);
    assertTrue(error.isFaulted());
    assertFalse(error.isCancelled());

    assertTrue(cancelled.isCompleted());
    assertFalse(cancelled.isFaulted());
    assertTrue(cancelled.isCancelled());
  }

  @Test
  public void testOnSuccessTask() {
    com.idealista.android.tasks.Continuation<Integer, com.idealista.android.tasks.Task<Integer>> continuation = new com.idealista.android.tasks.Continuation<Integer, com.idealista.android.tasks.Task<Integer>>() {
      public com.idealista.android.tasks.Task<Integer> then(com.idealista.android.tasks.Task<Integer> task) {
        return com.idealista.android.tasks.Task.forResult(task.getResult() + 1);
      }
    };
    com.idealista.android.tasks.Task<Integer> complete = com.idealista.android.tasks.Task.forResult(5).onSuccessTask(continuation);
    com.idealista.android.tasks.Task<Integer> error = com.idealista.android.tasks.Task.<Integer> forError(new IllegalStateException()).onSuccessTask(
        continuation);
    com.idealista.android.tasks.Task<Integer> cancelled = com.idealista.android.tasks.Task.<Integer> cancelled().onSuccessTask(continuation);

    assertTrue(complete.isCompleted());
    assertEquals(6, complete.getResult().intValue());
    assertFalse(complete.isFaulted());
    assertFalse(complete.isCancelled());

    assertTrue(error.isCompleted());
    assertTrue(error.getError() instanceof RuntimeException);
    assertTrue(error.isFaulted());
    assertFalse(error.isCancelled());

    assertTrue(cancelled.isCompleted());
    assertFalse(cancelled.isFaulted());
    assertTrue(cancelled.isCancelled());
  }

  @Test
  public void testContinueWhile() {
    final AtomicInteger count = new AtomicInteger(0);
    runTaskTest(new Callable<com.idealista.android.tasks.Task<?>>() {
      public com.idealista.android.tasks.Task<?> call() throws Exception {
        return com.idealista.android.tasks.Task.forResult(null).continueWhile(new Callable<Boolean>() {
          public Boolean call() throws Exception {
            return count.get() < 10;
          }
        }, new com.idealista.android.tasks.Continuation<Void, com.idealista.android.tasks.Task<Void>>() {
          public com.idealista.android.tasks.Task<Void> then(com.idealista.android.tasks.Task<Void> task) throws Exception {
            count.incrementAndGet();
            return null;
          }
        }).continueWith(new com.idealista.android.tasks.Continuation<Void, Void>() {
          public Void then(com.idealista.android.tasks.Task<Void> task) throws Exception {
            assertEquals(10, count.get());
            return null;
          }
        });
      }
    });
  }

  @Test
  public void testContinueWhileAsync() {
    final AtomicInteger count = new AtomicInteger(0);
    runTaskTest(new Callable<com.idealista.android.tasks.Task<?>>() {
      public com.idealista.android.tasks.Task<?> call() throws Exception {
        return com.idealista.android.tasks.Task.forResult(null).continueWhile(new Callable<Boolean>() {
          public Boolean call() throws Exception {
            return count.get() < 10;
          }
        }, new com.idealista.android.tasks.Continuation<Void, com.idealista.android.tasks.Task<Void>>() {
          public com.idealista.android.tasks.Task<Void> then(com.idealista.android.tasks.Task<Void> task) throws Exception {
            count.incrementAndGet();
            return null;
          }
        }, Executors.newCachedThreadPool()).continueWith(new com.idealista.android.tasks.Continuation<Void, Void>() {
          public Void then(com.idealista.android.tasks.Task<Void> task) throws Exception {
            assertEquals(10, count.get());
            return null;
          }
        });
      }
    });
  }

  @Test
  public void testContinueWhileAsyncCancellation() {
    final AtomicInteger count = new AtomicInteger(0);
    final com.idealista.android.tasks.CancellationTokenSource cts = new CancellationTokenSource();
    runTaskTest(new Callable<com.idealista.android.tasks.Task<?>>() {
      public com.idealista.android.tasks.Task<?> call() throws Exception {
        return com.idealista.android.tasks.Task.forResult(null).continueWhile(new Callable<Boolean>() {
                                                    public Boolean call() throws Exception {
                                                      return count.get() < 10;
                                                    }
                                                  }, new com.idealista.android.tasks.Continuation<Void, com.idealista.android.tasks.Task<Void>>() {
                                                    public com.idealista.android.tasks.Task<Void> then(com.idealista.android.tasks.Task<Void> task)
                                                        throws Exception {
                                                      if (count.incrementAndGet() == 5) {
                                                        cts.cancel();
                                                      }
                                                      return null;
                                                    }
                                                  }, Executors.newCachedThreadPool(),
            cts.getToken()).continueWith(new com.idealista.android.tasks.Continuation<Void, Void>() {
          public Void then(com.idealista.android.tasks.Task<Void> task) throws Exception {
            assertTrue(task.isCancelled());
            assertEquals(5, count.get());
            return null;
          }
        });
      }
    });
  }

  @Test
  public void testCallWithBadExecutor() {
    final RuntimeException exception = new RuntimeException("BAD EXECUTORS");

    com.idealista.android.tasks.Task.call(new Callable<Integer>() {
      public Integer call() throws Exception {
        return 1;
      }
    }, new Executor() {
      @Override
      public void execute(Runnable command) {
        throw exception;
      }
    }).continueWith(new com.idealista.android.tasks.Continuation<Integer, Object>() {
      @Override
      public Object then(com.idealista.android.tasks.Task<Integer> task) throws Exception {
        assertTrue(task.isFaulted());
        assertTrue(task.getError() instanceof com.idealista.android.tasks.ExecutorException);
        assertEquals(exception, task.getError().getCause());

        return null;
      }
    });
  }

  @Test
  public void testContinueWithBadExecutor() {
    final RuntimeException exception = new RuntimeException("BAD EXECUTORS");

    com.idealista.android.tasks.Task.call(new Callable<Integer>() {
      public Integer call() throws Exception {
        return 1;
      }
    }).continueWith(new com.idealista.android.tasks.Continuation<Integer, Integer>() {
      @Override
      public Integer then(com.idealista.android.tasks.Task<Integer> task) throws Exception {
        return task.getResult();
      }
    }, new Executor() {
      @Override
      public void execute(Runnable command) {
        throw exception;
      }
    }).continueWith(new com.idealista.android.tasks.Continuation<Integer, Object>() {
      @Override
      public Object then(com.idealista.android.tasks.Task<Integer> task) throws Exception {
        assertTrue(task.isFaulted());
        assertTrue(task.getError() instanceof com.idealista.android.tasks.ExecutorException);
        assertEquals(exception, task.getError().getCause());

        return null;
      }
    });
  }

  @Test
  public void testContinueWithTaskAndBadExecutor() {
    final RuntimeException exception = new RuntimeException("BAD EXECUTORS");

    com.idealista.android.tasks.Task.call(new Callable<Integer>() {
      public Integer call() throws Exception {
        return 1;
      }
    }).continueWithTask(new com.idealista.android.tasks.Continuation<Integer, com.idealista.android.tasks.Task<Integer>>() {
      @Override
      public com.idealista.android.tasks.Task<Integer> then(com.idealista.android.tasks.Task<Integer> task) throws Exception {
        return task;
      }
    }, new Executor() {
      @Override
      public void execute(Runnable command) {
        throw exception;
      }
    }).continueWith(new Continuation<Integer, Object>() {
      @Override
      public Object then(com.idealista.android.tasks.Task<Integer> task) throws Exception {
        assertTrue(task.isFaulted());
        assertTrue(task.getError() instanceof ExecutorException);
        assertEquals(exception, task.getError().getCause());

        return null;
      }
    });
  }

  //region TaskCompletionSource

  @Test
  public void testTrySetResult() {
    com.idealista.android.tasks.TaskCompletionSource<String> tcs = new com.idealista.android.tasks.TaskCompletionSource<>();
    com.idealista.android.tasks.Task<String> task = tcs.getTask();
    assertFalse(task.isCompleted());

    boolean success = tcs.trySetResult("SHOW ME WHAT YOU GOT");
    assertTrue(success);
    assertTrue(task.isCompleted());
    assertEquals("SHOW ME WHAT YOU GOT", task.getResult());
  }

  @Test
  public void testTrySetError() {
    com.idealista.android.tasks.TaskCompletionSource<Void> tcs = new com.idealista.android.tasks.TaskCompletionSource<>();
    com.idealista.android.tasks.Task<Void> task = tcs.getTask();
    assertFalse(task.isCompleted());

    Exception exception = new RuntimeException("DISQUALIFIED");
    boolean success = tcs.trySetError(exception);
    assertTrue(success);
    assertTrue(task.isCompleted());
    assertEquals(exception, task.getError());
  }

  @Test
  public void testTrySetCanceled() {
    com.idealista.android.tasks.TaskCompletionSource<Void> tcs = new com.idealista.android.tasks.TaskCompletionSource<>();
    com.idealista.android.tasks.Task<Void> task = tcs.getTask();
    assertFalse(task.isCompleted());

    boolean success = tcs.trySetCancelled();
    assertTrue(success);
    assertTrue(task.isCompleted());
    assertTrue(task.isCancelled());
  }

  @Test
  public void testTrySetOnCompletedTask() {
    com.idealista.android.tasks.TaskCompletionSource<Void> tcs = new com.idealista.android.tasks.TaskCompletionSource<>();
    tcs.setResult(null);

    assertFalse(tcs.trySetResult(null));
    assertFalse(tcs.trySetError(new RuntimeException()));
    assertFalse(tcs.trySetCancelled());
  }

  @Test
  public void testSetResultOnCompletedTask() {
    com.idealista.android.tasks.TaskCompletionSource<Void> tcs = new com.idealista.android.tasks.TaskCompletionSource<>();
    tcs.setResult(null);

    thrown.expect(IllegalStateException.class);
    tcs.setResult(null);
  }

  @Test
  public void testSetErrorOnCompletedTask() {
    com.idealista.android.tasks.TaskCompletionSource<Void> tcs = new com.idealista.android.tasks.TaskCompletionSource<>();
    tcs.setResult(null);

    thrown.expect(IllegalStateException.class);
    tcs.setError(new RuntimeException());
  }

  @Test
  public void testSetCancelledOnCompletedTask() {
    com.idealista.android.tasks.TaskCompletionSource<Void> tcs = new com.idealista.android.tasks.TaskCompletionSource<>();
    tcs.setResult(null);

    thrown.expect(IllegalStateException.class);
    tcs.setCancelled();
  }

  //endregion

  //region deprecated

  @SuppressWarnings("deprecation")
  @Test
  public void testDeprecatedTaskCompletionSource() {
    TaskCompletionSource tcsA = new TaskCompletionSource();
    tcsA.setResult(null);
    assertTrue(tcsA.getTask().isCompleted());

    TaskCompletionSource<Void> tcsB = new TaskCompletionSource<>();
    tcsB.setResult(null);
    assertTrue(tcsA.getTask().isCompleted());
  }

  @SuppressWarnings("deprecation")
  @Test
  public void testDeprecatedAggregateExceptionMethods() {
    final Exception error0 = new Exception("This is an exception (0).");
    final Exception error1 = new Exception("This is an exception (1).");
    final Error error2 = new Error("This is an error.");

    List<Exception> exceptions = new ArrayList<>();
    exceptions.add(error0);
    exceptions.add(error1);

    // Test old functionality
    AggregateException aggregate = new AggregateException(exceptions);
    assertEquals("There were multiple errors.", aggregate.getMessage());
    assertEquals(2, aggregate.getErrors().size());
    assertEquals(error0, aggregate.getErrors().get(0));
    assertEquals(error1, aggregate.getErrors().get(1));
    assertEquals(2, aggregate.getCauses().length);
    assertEquals(error0, aggregate.getCauses()[0]);
    assertEquals(error1, aggregate.getCauses()[1]);

    // Test deprecated getErrors method returns sane results with non-Exceptions
    aggregate = new AggregateException("message", new Throwable[]{ error0, error1, error2 });
    assertEquals("message", aggregate.getMessage());
    assertEquals(3, aggregate.getErrors().size());
    assertEquals(error0, aggregate.getErrors().get(0));
    assertEquals(error1, aggregate.getErrors().get(1));
    assertNotSame(error2, aggregate.getErrors().get(2));
    assertEquals(error2, aggregate.getErrors().get(2).getCause());
  }

  //endregion
}
