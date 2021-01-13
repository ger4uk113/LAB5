package a.event_handling;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.view.GestureDetectorCompat;

import android.os.Bundle;
import android.view.GestureDetector;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements GestureDetector.OnGestureListener, GestureDetector.OnDoubleTapListener
{
    private ConstraintLayout main_layout;
    private TextView text_view_1, text_view_2, text_view_3, text_view_4;
    private Button button;

    private GestureDetectorCompat gDetector;

    //Установить кастомный toolbar в меню
    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        main_layout = findViewById(R.id.main_layout);

        text_view_1 = findViewById(R.id.textView1);
        text_view_2 = findViewById(R.id.textView2);
        text_view_3 = findViewById(R.id.textView3);
        text_view_4 = findViewById(R.id.textView4);

        button = findViewById(R.id.button1);

        gDetector = new GestureDetectorCompat(this, this);
        gDetector.setOnDoubleTapListener(this);

        //Обработчик короткого нажатия кнопки
        button.setOnClickListener(view -> {
            text_view_1.setText("Button clicked");
        });

        //Обработчик долгого нажатия кнопки
        button.setOnLongClickListener(view -> {
            text_view_1.setText("Long button clicked");
            return true;
        });
    }

    //Вызывается после выбора какого-либо пункта toolbar'а
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item)
    {
        String action = item.getTitle().toString();

        setTitle(action);

        if ("ButtonClick".equals(action)) {
            setVisibleElements(View.VISIBLE, View.INVISIBLE, View.INVISIBLE, View.INVISIBLE, View.VISIBLE);
        }
        else if ("MotionEvent".equals(action)) {
            setVisibleElements(View.INVISIBLE, View.VISIBLE, View.VISIBLE, View.INVISIBLE, View.INVISIBLE);
        }
        else {
            setVisibleElements(View.INVISIBLE, View.INVISIBLE, View.INVISIBLE, View.VISIBLE, View.INVISIBLE);
        }

        return super.onOptionsItemSelected(item);
    }

    //Изменить видимость элементов в зависимости от выбранного действия
    private void setVisibleElements(int text_view_1, int text_view_2, int text_view_3, int text_view_4, int button)
    {
        this.text_view_1.setVisibility(text_view_1);
        this.text_view_2.setVisibility(text_view_2);
        this.text_view_3.setVisibility(text_view_3);
        this.text_view_4.setVisibility(text_view_4);
        this.button.setVisibility(button);
    }

    ////Обработчик касания экрана
    private void handleTouch(MotionEvent event)
    {
        int ptr_count = event.getPointerCount();

        for (int i = 0; i < ptr_count; i++) {
            int action = event.getActionMasked();
            int action_index = event.getActionIndex();
            int id = event.getPointerId(i);
            int x = (int) event.getX(event.findPointerIndex(id));
            int y = (int) event.getY(event.findPointerIndex(id));

            String action_string = "";

            switch (action) {
                case MotionEvent.ACTION_DOWN:
                    action_string = "DOWN";
                    break;
                case MotionEvent.ACTION_UP:
                    action_string = "UP";
                    break;
                case MotionEvent.ACTION_POINTER_DOWN:
                    action_string = "PNTR DOWN";
                    break;
                case MotionEvent.ACTION_POINTER_UP:
                    action_string = "PNTR UP";
                    break;
                case MotionEvent.ACTION_MOVE:
                    action_string = "MOVE";
            }

            String touch_status = "Action: " + action_string + " Index: " +
                    action_index + " ID: " + id + " X: " + x + " Y: " + y;

            if (id == 0)
                text_view_2.setText(touch_status);
            else
                text_view_3.setText(touch_status);
        }
    }

    @Override
    public boolean onSingleTapConfirmed(MotionEvent e)
    {
        text_view_4.setText("onSingleTapConfirmed");
        return true;
    }

    @Override
    public boolean onDoubleTap(MotionEvent e)
    {
        text_view_4.setText("onDoubleTap");
        return false;
    }

    @Override
    public boolean onDoubleTapEvent(MotionEvent e)
    {
        text_view_4.setText("onDoubleTapEvent");
        return false;
    }

    @Override
    public boolean onDown(MotionEvent e)
    {
        text_view_4.setText("onDown");
        return false;
    }

    @Override
    public void onShowPress(MotionEvent e)
    {
        text_view_4.setText("onShowPress");
    }

    @Override
    public boolean onSingleTapUp(MotionEvent e)
    {
        text_view_4.setText("onSingleTapUp");
        return false;
    }

    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY)
    {
        text_view_4.setText("onScroll");
        return false;
    }

    @Override
    public void onLongPress(MotionEvent e)
    {
        text_view_4.setText("onLongPress");
    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY)
    {
        text_view_4.setText("onFling");
        return false;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event)
    {
        handleTouch(event);
        gDetector.onTouchEvent(event);
        return super.onTouchEvent(event);
    }
}