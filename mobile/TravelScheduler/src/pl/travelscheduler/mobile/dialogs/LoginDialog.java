package pl.travelscheduler.mobile.dialogs;

import pl.travelscheduler.mobile.R;
import pl.travelscheduler.mobile.helpers.SessionHelper;
import pl.travelscheduler.mobile.listeners.LoginDialogOnClickListener;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnShowListener;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.Toast;

public class LoginDialog
{	
	private Activity activity;
	
	private AlertDialog.Builder builder;
	private AlertDialog dialog;
	private View dialogView;
	
	private String userName;
	private String password;
	
	public LoginDialog(Activity activity)
	{
		this.activity = activity;
		ContextThemeWrapper ctw = new ContextThemeWrapper(activity, R.style.Theme_Tripadvisorstyle );
		builder = new AlertDialog.Builder(ctw);
		builder.setCancelable(true);
		builder.setPositiveButton("Login", null);
		builder.setNegativeButton("Cancel", null);
		LayoutInflater inflater = activity.getLayoutInflater();
		dialogView = inflater.inflate(R.layout.dialog_login, null);
		builder.setView(dialogView);
	}
	
	public void show()
	{
		dialog = builder.create();
		dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
		dialog.setOnShowListener(new OnShowListener()
		{
			@Override
			public void onShow(DialogInterface arg0)
			{
				Button btnPositive = dialog.getButton(DialogInterface.BUTTON_POSITIVE);
				btnPositive.setBackgroundDrawable(activity.getResources().getDrawable(R.drawable.btn_cab_done_tripadvisorstyle));
				Button btnNegative = dialog.getButton(DialogInterface.BUTTON_NEGATIVE);
				btnNegative.setBackgroundDrawable(activity.getResources().getDrawable(R.drawable.btn_cab_done_tripadvisorstyle));
				LoginDialogOnClickListener listener = new LoginDialogOnClickListener(btnPositive, btnNegative, getThis());
				btnPositive.setOnClickListener(listener);
				btnNegative.setOnClickListener(listener);
				AutoCompleteTextView txtVUserName = (AutoCompleteTextView) dialogView.findViewById(R.id.loginDialogUserNameTxtV);
				txtVUserName.setAdapter(SessionHelper.getUserNamesAdapter(getActivity()));
			}
			
		});
		dialog.show();
	}
	
	public View getDialogView()
	{
		return dialogView;
	}
	
	public LoginDialog getThis()
	{
		return LoginDialog.this;
	}

	public boolean dataProvided()
	{
		return userName != null && password != null;
	}

	public String getUserName()
	{
		return userName;
	}

	public String getPassword()
	{
		return password;
	}

	public void cancel()
	{
		if(dialog.isShowing())
		{
			dialog.cancel();
		}
	}

	public void showToast(String msg)
	{
		Toast.makeText(builder.getContext(), msg, Toast.LENGTH_SHORT).show();		
	}

	public void setUserName(String string)
	{
		if(string != null && !string.equals(""))
		{
			userName = string;
		}
		else
		{
			userName = null;
		}
	}

	public void setPassword(String string)
	{
		if(string != null && !string.equals(""))
		{
			password = string;
		}
		else
		{
			password = null;
		}
	}
	
	public Activity getActivity()
	{
		return activity;
	}

}
