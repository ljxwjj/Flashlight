package com.lvlian.flashlight;

import android.hardware.Camera;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ToggleButton;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.CheckedChange;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;


@EActivity(R.layout.activity_main)
public class MainActivity extends AppCompatActivity {

    @ViewById
    View light;

    @ViewById
    ToggleButton toggleButton;

    private Camera camera;
    private Camera.Parameters parameters;

    @AfterViews
    void init() {
        toggleButton.setChecked(true);
    }

    @CheckedChange(R.id.toggleButton)
    void toggleButtonOnClick(boolean status) {
        if (status) {
            turnOn();
        }else {
            turnOff();
        }
    }

    private void turnOn() {
        if (camera == null) {
            camera = Camera.open();
        }
        parameters = camera.getParameters();
        parameters.setFlashMode(Camera.Parameters.FLASH_MODE_TORCH);
        camera.setParameters(parameters);
        camera.startPreview();
        light.setVisibility(View.VISIBLE);
    }

    private void turnOff() {
        if (camera == null) {
            return;
        }
        camera.stopPreview();
        camera.release();
        camera = null;
        light.setVisibility(View.INVISIBLE);
    }

    @Override
    protected void onDestroy() {
        boolean status = toggleButton.isChecked();
        if (status) {
            turnOff();
        }
        super.onDestroy();
    }
}
