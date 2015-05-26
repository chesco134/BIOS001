package org.biospolivoto.condomizer;

import java.util.Vector;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends ActionBarActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		if (savedInstanceState == null) {
			getSupportFragmentManager().beginTransaction()
					.add(R.id.container, new PlaceholderFragment()).commit();
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
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

	/**
	 * A placeholder fragment containing a simple view.
	 */
	public static class PlaceholderFragment extends Fragment {

		public PlaceholderFragment() {
		}

		EditText inputNombreUsuario;
		EditText inputUsrPsswd;
		Button login;
		String nombreUsuario;
		String usrPsswd;
		Context context;
		
		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_main, container,
					false);
			context = inflater.getContext();
			inputNombreUsuario = (EditText)rootView.findViewById(R.id.input_usr);
			inputUsrPsswd = (EditText)rootView.findViewById(R.id.input_psswd);
			login = (Button)rootView.findViewById(R.id.logIn);
			login.setOnClickListener(new OnClickListener(){
				@Override
				public void onClick(View view){
					nombreUsuario = inputNombreUsuario.getText().toString();
					usrPsswd = inputUsrPsswd.getText().toString();
					String[] args = {nombreUsuario,usrPsswd};
					Toast.makeText(context, nombreUsuario + "\n" + usrPsswd, Toast.LENGTH_LONG).show();
					HandleNetworking networker = new HandleNetworking(context);
					networker.execute(args);
				}
			});
			return rootView;
		}
		
		private class HandleNetworking extends AsyncTask<String,String,String>{
			
			Context context;
			
			public HandleNetworking(Context context) {
				// TODO Auto-generated constructor stub
				this.context = context;
			}
			
			@Override
			protected String doInBackground(String... args){
				String result = null;
				String[] publish = null;
				HelloWebService webService = new HelloWebService();
				Vector<String> response = webService.saySomething(nombreUsuario, usrPsswd);
				if (response != null){
					try{
						Object[] resps = response.toArray();
						String[] resultados = new String[resps.length];
						for(int i=0; i<resultados.length; i++){
							resultados[i] = resps[i].toString();
						}
						publish = resultados;
					}catch(ClassCastException e){
						publish = new String[1];
						publish[0] = "Error en el formato de los datos.";
					}
				}else{
					publish = new String[1];
					publish[0] = "Error de conexión.";
				}
				publishProgress(publish);
				return result;
			}
			
			@Override
			protected void onProgressUpdate(String... args){
				for(int i = 0; i<args.length; i++){
					launchAlert(args[i]);
				}
			}
			
			private void launchAlert(String message){
				Intent i = new Intent(context,AlertActivity.class);
				i.putExtra("message", message);
				startActivity(i);
			}
		}
	}
}
