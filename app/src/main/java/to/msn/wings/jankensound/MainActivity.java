package to.msn.wings.jankensound;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends SoundActivity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        soundIcon = findViewById(R.id.MainSoundIcon);
        isMute = false;
        soundIcon.setImageResource(R.drawable.volumeoffimage);
        bgm.start();
    }

    public void btnBGM_onClick(View view) {
        bgmManager_onClick(0);
    }

    public void btn_click(View view) {
        Intent intent = new Intent(this, to.msn.wings.jankensound.GameActivity.class);
        intent.putExtra("isMute", isMute);
        startActivity(intent);
    }

    @Override
    protected void onPause() {
        super.onPause();
        bgm.stop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        bgm.release();
    }
}