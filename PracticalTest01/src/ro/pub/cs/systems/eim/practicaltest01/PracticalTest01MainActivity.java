package ro.pub.cs.systems.eim.practicaltest01;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


public class PracticalTest01MainActivity extends Activity {
	private Button pressMe = null;
	private Button pressMeToo = null;
	private EditText leftText = null;
	private EditText rightText = null;
	private Button navigate = null;
	
	private static final int SECONDARY_ACTIVITY_REQUEST_CODE = 42;
	private int serviceStatus = Constants.SERVICE_STOPPED;
	
	private ButtonClickListener buttonClickListener = new ButtonClickListener();
	private class ButtonClickListener implements View.OnClickListener {

		@Override
		public void onClick(View view) {
			int leftNumberOfClicks = Integer.parseInt(leftText.getText().toString());
			int rightNumberOfClicks = Integer.parseInt(rightText.getText().toString());
			
			switch(view.getId()) {
				case R.id.button5:
			          Intent intent = new Intent(getApplicationContext(),
			        		  					 PracticalTest01SecondaryActivity.class);
			          
			          int numberOfClicks = Integer.parseInt(leftText.getText().toString()) + 
			                               Integer.parseInt(rightText.getText().toString());
			          
			          intent.putExtra("numberOfClicks", numberOfClicks);
			          
			          startActivityForResult(intent, SECONDARY_ACTIVITY_REQUEST_CODE);
			          break;
		        case R.id.button1:
		          leftNumberOfClicks++;
		          leftText.setText(String.valueOf(leftNumberOfClicks));
		          break;
		        case R.id.button2:
		          rightNumberOfClicks++;
		          rightText.setText(String.valueOf(rightNumberOfClicks));
		          break;
			}
			
			if (leftNumberOfClicks + rightNumberOfClicks > Constants.NUMBER_OF_CLICKS_THRESHOLD
			        && serviceStatus == Constants.SERVICE_STOPPED) {
				Intent intent = new Intent(getApplicationContext(), PracticalTest01Service.class);
				intent.putExtra("firstNumber", leftNumberOfClicks);
				intent.putExtra("secondNumber", rightNumberOfClicks);
				
				getApplicationContext().startService(intent);
				serviceStatus = Constants.SERVICE_STARTED;
			}
		}
	}
	
	private MessageBroadcastReceiver messageBroadcastReceiver = new MessageBroadcastReceiver();
	private class MessageBroadcastReceiver extends BroadcastReceiver {

		@Override
		public void onReceive(Context context, Intent intent) {
			Log.d("[Message]", intent.getStringExtra("message"));
			
		}
	}
	
	private IntentFilter intentFilter = new IntentFilter();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_practical_test01_main);
        
        for (int index = 0; index < Constants.actionTypes.length; index++) {
            intentFilter.addAction(Constants.actionTypes[index]);
        }
        
        leftText = (EditText) findViewById(R.id.leftEditText);
        rightText = (EditText) findViewById(R.id.rightEditText);
        
        leftText.setText(String.valueOf(0));
        rightText.setText(String.valueOf(0));
        
        pressMe = (Button) findViewById(R.id.button1);
        pressMeToo = (Button) findViewById(R.id.button2);
        
        pressMe.setOnClickListener(buttonClickListener);
        pressMeToo.setOnClickListener(buttonClickListener);
        
        navigate = (Button) findViewById(R.id.button5);
        navigate.setOnClickListener(buttonClickListener);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.practical_test01_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    
    @Override
    protected void onSaveInstanceState(Bundle savedInstanceState) {
    	savedInstanceState.putString("leftCount", leftText.getText().toString());
    	savedInstanceState.putString("rightCount", rightText.getText().toString());
    }
    
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        
    	if (savedInstanceState.containsKey("leftCount")) {
          leftText.setText(savedInstanceState.getString("leftCount"));
        } else {
          leftText.setText(String.valueOf(0));
        }
        if (savedInstanceState.containsKey("rightCount")) {
          rightText.setText(savedInstanceState.getString("rightCount"));
        } else {
          rightText.setText(String.valueOf(0));
        }
     }
    
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
      if (requestCode == SECONDARY_ACTIVITY_REQUEST_CODE) {
        Toast.makeText(this, "The activity returned with result " + resultCode, Toast.LENGTH_LONG).show();
      }
    }
    
    @Override
    protected void onDestroy() {
      Intent intent = new Intent(this, PracticalTest01Service.class);
      stopService(intent);
      super.onDestroy();
    }
    
    @Override
    protected void onResume() {
      super.onResume();
      registerReceiver(messageBroadcastReceiver, intentFilter);
    }
   
    @Override
    protected void onPause() {
      unregisterReceiver(messageBroadcastReceiver);
      super.onPause();
    }
}
