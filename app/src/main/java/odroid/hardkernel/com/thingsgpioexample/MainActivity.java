package odroid.hardkernel.com.thingsgpioexample;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.things.pio.PeripheralManager;
import com.google.android.things.pio.Gpio;

import java.io.IOException;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    PeripheralManager manager;
    Gpio gpio;
    Boolean state = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // get Peripheral Manager for managing the gpio.
        manager = PeripheralManager.getInstance();

        // get available gpio pin list.
        // each pin name is consist as P + physical pin number.
        List<String> gpioList = manager.getGpioList();

        try {
            // get first available gpio pin.
            // in this case, Physical pin #18 is used.
            gpio = manager.openGpio("18");

            // Sets the interrupt trigger type.
            gpio.setEdgeTriggerType(Gpio.EDGE_BOTH);

            Button gpioButton = findViewById(R.id.gpio_status_check);
            gpioButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        Button gpioButton = (Button) v;
                        if (gpio.getValue()) {
                            Toast.makeText(MainActivity.this, "GPIO is on", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(MainActivity.this, "GPIO is off", Toast.LENGTH_SHORT).show();
                        }
                    } catch (IOException io) {
                        io.printStackTrace();
                    }
                }
            });
        } catch (
                Exception exception) {
            exception.printStackTrace();
        }
    }
}
