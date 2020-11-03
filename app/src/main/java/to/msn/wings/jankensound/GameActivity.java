package to.msn.wings.jankensound;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class GameActivity extends SoundActivity {
    Intent intent;
    // 画像を表示するImageViewオブジェクト変数をフィールド宣言
    ImageView _outputComp;
    // メッセージを表示するTextViewオブジェクト変数をフィールド宣言
    TextView _outputWinLose;
    // 人間の手を表す変数をフィールド宣言
    int _handYou;
    // グー、チョキ、パーのImageButton配列
    ImageButton[] btnHands;
    // [もう一回]ボタンのフィールド宣言
    Button retry;
    // コンピュータの手の画像のID配列を定義
    int[] handCompImageId = {R.drawable.com_gu,
                                R.drawable.com_choki,
                                R.drawable.com_pa,
                                R.drawable.com_janken};


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        // 音声設定用ビューの取得
        soundIcon = findViewById(R.id.GameSoundIcon);
        // [もう一回]ボタンビューの取得
        retry = findViewById(R.id.retry);

        // インテントを取得、データを挿入
        intent = getIntent();
        isMute = intent.getBooleanExtra("isMute", true);
        bgmManager_changePage(isMute);      // 音声設定

        // 画像を表示するImageViewオブジェクトを取得
        _outputComp = findViewById(R.id.ivComp);
        // メッセージを表示するTextViewオブジェクトを取得
        _outputWinLose = findViewById(R.id.tvWinLose);
        // グー、チョキ、パーのImageButtonオブジェクトを取得
        btnHands = new ImageButton[] {findViewById(R.id.btGoo),
                                        findViewById(R.id.btCho),
                                        findViewById(R.id.btPah)};
        // ゲームスタート
        startGame();
    }

    private void startGame() {
        for(int i = 0; i < btnHands.length; i++) {  // じゃんけんボタンを押せるようにする
            btnHands[i].setEnabled(true);
        }
        retry.setVisibility(View.INVISIBLE);        // [もう一回]ボタンを隠す

        _outputComp.setImageResource(handCompImageId[3]);
        mpResult[3].start();                        // じゃんけん開始の音声
        _outputWinLose.setText("じゃん・・けん・・・"); // TextViewに「じゃん・・けん・・・」と表示

    }

    public void retry(View view) {
        startGame();
    }

    /** BGM処理 **/
    public void btnBGM_onClick(View view) {
        bgmManager_onClick(1);
    }

    /**
     * ボタンをタップしたときの処理
     */
    public void btClick(View view) {
        // 人間の手のボタンID配列を定義
        int[] handYouId = {R.id.btGoo, R.id.btCho, R.id.btPah};
        // タップされた画面部品のidのR値に応じて人間の手を取得
        for (int i = 0; i < handYouId.length; i++) {
            if (view.getId() == handYouId[i])
                _handYou = i;
        }

        String[] handStr = {"グー", "チョキ", "パー"};
        String msg = "あなたが選んだ手：" + handStr[_handYou];
        Toast.makeText(GameActivity.this, msg, Toast.LENGTH_SHORT).show();

        // コンピュータの手を表す変数を定義、乱数で決定
        int handComp = (int) (Math.random() * 3.0);
        // コンピュータの手を画像表示
        _outputComp.setImageResource(handCompImageId[handComp]);

        // 勝敗を判定
        int result = (handComp - _handYou + 3) % 3;
        // 勝敗を表すテキスト文字列配列を定義
        String[] resultStr = {"引き分け", "あなたの勝ち", "あなたの負け"};
        // 勝敗をテキスト表示
        _outputWinLose.setText(resultStr[result] + "です");
        // 勝敗音声を再生
        mpResult[result].start();
        // じゃんけんボタンを押せないようにする
        for(int i = 0; i < btnHands.length; i++) {
            btnHands[i].setEnabled(false);
        }
        // [もう一回]ボタンを出現させる
        retry.setVisibility(View.VISIBLE);
    }

}
