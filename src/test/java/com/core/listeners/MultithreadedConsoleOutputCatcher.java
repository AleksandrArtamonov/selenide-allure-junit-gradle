package com.core.listeners;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.HashMap;

public class MultithreadedConsoleOutputCatcher {

    private static PrintStream originalStream;
    private static OutputStreamRouter router;

    public static synchronized void startCatch() {
        if (router == null) {
            originalStream = System.out;
            router = new OutputStreamRouter(originalStream);
            System.setOut(new PrintStream(router));
        }
        router.registryThread(Thread.currentThread());
    }

    public static String getContent() {
        return getContent(true);
    }

    public static synchronized String getContent(boolean clear) {
        if (router == null) {
            throw new RuntimeException();
        }
        return router.fetchThreadContent(Thread.currentThread(), clear);
    }

    public static synchronized void stopCatch() {
        if (router == null) {
            throw new RuntimeException();
        }
        router.unregistryThread(Thread.currentThread());
        if (router.getActiveRoutes() == 0) {
            System.setOut(originalStream);
            originalStream = null;
            router = null;
        }
    }

    static class OutputStreamRouter extends OutputStream {

        private HashMap<Thread, ByteArrayOutputStream> loggerStreams = new HashMap<>();
        private OutputStream original;

        public OutputStreamRouter(OutputStream original) {
            this.original = original;
        }

        public void registryThread(Thread thread) {
            if (loggerStreams.containsKey(thread)) throw new RuntimeException();
            loggerStreams.put(thread, new ByteArrayOutputStream(4096));
        }

        public void unregistryThread(Thread thread) {
            if (!loggerStreams.containsKey(thread)) throw new RuntimeException();
            loggerStreams.remove(thread);
        }

        public int getActiveRoutes() {
            return loggerStreams.size();
        }

        public String fetchThreadContent(Thread thread, boolean clear) {
            if (!loggerStreams.containsKey(thread)) throw new RuntimeException();
            String result = loggerStreams.get(thread).toString();
            if (clear) {
                loggerStreams.get(thread).reset();
            }
            return result;
        }

        @Override
        public synchronized void write(int b) throws IOException {
            original.write(b);
            if (loggerStreams.containsKey(Thread.currentThread())) {
                loggerStreams.get(Thread.currentThread()).write(b);
            }
        }

        @Override
        public synchronized void flush() throws IOException {
            original.flush();
        }

        @Override
        public synchronized void close() throws IOException {
            original.close();
        }
    }

}

