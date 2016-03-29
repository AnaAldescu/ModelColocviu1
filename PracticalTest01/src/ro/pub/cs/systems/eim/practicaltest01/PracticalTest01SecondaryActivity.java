package ro.pub.cs.systems.eim.practicaltest01;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class PracticalTest01SecondaryActivity extends Activity {
	TextView numberOfClicksTextView = null;
	Button okButton = null;
	Button cancelButton = null;
	
	private ButtonClickListener buttonClickListener = new ButtonClickListener();
	 
	private class ButtonClickListener implements View.OnClickListener {
	    @Override
	    public void onClick(View view) {
	      switch(view.getId()) {
	        case R.id.button3:
	          setResult(RESULT_OK, null);
	          break;
	        case R.id.button4:
	          setResult(RESULT_CANCELED, null);
	          break;
	      }
	      finish();
	    }
	  }
	
	@Override
	  protected void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.activity_practical_test01_secondary);
	 
	    numberOfClicksTextView = (TextView)findViewById(R.id.textView1);
	    
	    Intent intent = getIntent();
	    
	    if (intent != null && intent.getExtras().containsKey("numberOfClicks")) {
	      int numberOfClicks = intent.getIntExtra("numberOfClicks", -1);
	      numberOfClicksTextView.setText(String.valueOf(numberOfClicks));
	    }
	 
	    okButton = (Button)findViewById(R.id.button3);
	    okButton.setOnClickListener(buttonClickListener);
	    cancelButton = (Button)findViewById(R.id.button4);
	    cancelButton.setOnClickListener(buttonClickListener);
	  }

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.practical_test01_secondary, menu);
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
}
