package com.klapheck.priesthoodordinances;

import android.app.Activity;
import android.os.Bundle;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.widget.TextView;

public class BlessingsInGeneral extends Activity {


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.blessings);

		int blessing = R.string.blessings_in_general;

		TextView foo = (TextView) findViewById(R.id.tvBlessings);
		foo.setMovementMethod (LinkMovementMethod.getInstance());
		foo.setText(Html.fromHtml(getString(blessing)));
		
		
	}
	
}
