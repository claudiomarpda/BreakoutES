package com.breakout.es;

import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.WindowManager;

/**
 * Requests an OpenGL SurfaceView and assigns a renderer to it.
 */
public class MainActivity extends AppCompatActivity {

    private GLSurfaceView mGlSurfaceView;
    private boolean rendererSet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Remove notification bar
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        mGlSurfaceView = new GLSurfaceView(this);

        // Request an OpenGL 2.0 compatible context
        mGlSurfaceView.setEGLContextClientVersion(2);

        // Assign our renderer
        mGlSurfaceView.setRenderer(new MainRenderer(this));
        rendererSet = true;
        setContentView(mGlSurfaceView);

    }

    @Override
    protected void onPause() {
        super.onPause();
        if(rendererSet) {
            mGlSurfaceView.onPause();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(rendererSet) {
            mGlSurfaceView.onResume();
        }
    }
}
