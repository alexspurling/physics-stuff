package engine;

import engine.graph.Render;
import engine.scene.Scene;

public class Engine {

    public static final int TARGET_UPS = 30;
    private final engine.IAppLogic appLogic;
    private final Window window;
    private Render render;
    private boolean running;
    private Scene scene;
    private int targetFps;
    private int targetUps;

    public Engine(String windowTitle, engine.Window.WindowOptions opts, engine.IAppLogic appLogic) {
        window = new Window(windowTitle, opts, () -> {
            resize();
            return null;
        });
        targetFps = opts.fps;
        targetUps = opts.ups;
        this.appLogic = appLogic;
        render = new Render();
        scene = new Scene();
        appLogic.init(window, scene, render);
        running = true;
    }

    private void cleanup() {
        appLogic.cleanup();
        render.cleanup();
        scene.cleanup();
        window.cleanup();
    }

    private void resize() {
        // Nothing to be done yet
    }

    private void run() {
        long initialTime = System.currentTimeMillis();
        float timeU = 1000.0f / targetUps;
        float timeR = targetFps > 0 ? 1000.0f / targetFps : 0;
        float deltaUpdate = 0;
        float deltaFps = 0;

        int frameCount = 0;
        long lastFpsTime = System.currentTimeMillis();

        long updateTime = initialTime;
        long startTime = System.nanoTime();

        while (running && !window.windowShouldClose()) {
//            startTime = System.nanoTime();
            window.pollEvents();
//            System.out.println("Poll time: " + (System.nanoTime() - startTime) / 1e6);

            long now = System.currentTimeMillis();
            deltaUpdate += (now - initialTime) / timeU;
            deltaFps += (now - initialTime) / timeR;

//            if (targetFps <= 0 || deltaFps >= 1) {
                appLogic.input(window, scene, now - initialTime);
//            }

//            if (deltaUpdate >= 1) {
                long diffTimeMillis = now - updateTime;
                appLogic.update(window, scene, diffTimeMillis);
                updateTime = now;
                deltaUpdate--;
                frameCount++;
//            }

//            if (targetFps <= 0 || deltaFps >= 1) {

                startTime = System.nanoTime();
                render.render(window, scene);
                System.out.println("Render time: " + (System.nanoTime() - startTime) / 1e6);
                deltaFps--;
                startTime = System.nanoTime();
                window.update();
                System.out.println("Window update time: " + (System.nanoTime() - startTime) / 1e6);
//            }

            if (System.currentTimeMillis() - lastFpsTime > 1000) {
                System.out.println("FPS: " + frameCount);
                frameCount = 0;
                lastFpsTime = System.currentTimeMillis();
            }
            initialTime = now;
        }

        cleanup();
    }

    public void start() {
        running = true;
        run();
    }

    public void stop() {
        running = false;
    }

}
