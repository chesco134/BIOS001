package org.biospolivoto.condomizer;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLDecoder;
import java.util.Vector;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.DefaultHandler;

import android.util.Log;

public class HelloWebService {

	public Vector<String> saySomething(String nombreUsuario, String usrPsswd) {
		Vector<String> response = null;
		try {
			URL url = new URL("http://192.168.1.71/"
					+ "ServidorVotacionesWeb/services/Servicios/validateUser");
			HttpURLConnection httpURLConnection = (HttpURLConnection) url
					.openConnection();
			httpURLConnection.setRequestMethod("POST");
			httpURLConnection.setDoOutput(true);
			OutputStreamWriter salida = new OutputStreamWriter(
					httpURLConnection.getOutputStream());
			salida.write("nombreUsuario=" + nombreUsuario);
			salida.flush();
			salida.write("usrPsswd=" + usrPsswd);
			salida.flush();
			if (httpURLConnection.getResponseCode() == HttpURLConnection.HTTP_OK) {
				SAXParserFactory fabrica = SAXParserFactory.newInstance();
				SAXParser parser = fabrica.newSAXParser();
				XMLReader lector = parser.getXMLReader();
				ManejadorServicioWeb manejador = new ManejadorServicioWeb();
				lector.setContentHandler(manejador);
				lector.parse(new InputSource(httpURLConnection.getInputStream()));
				response = manejador.getLista();
			} else {
				Log.e("Ñe", httpURLConnection.getResponseMessage());
			}
		} catch (IOException | SAXException e) {
			e.printStackTrace();
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		}
		return response;
	}

	class ManejadorServicioWeb extends DefaultHandler {
		private Vector<String> lista;
		private StringBuilder cadena;

		public Vector<String> getLista() {
			return lista;
		}

		@Override
		public void startDocument() throws SAXException {
			cadena = new StringBuilder();
			lista = new Vector<String>();
		}

		@Override
		public void characters(char ch[], int comienzo, int longitud) {
			cadena.append(ch, comienzo, longitud);
		}

		@Override
		public void endElement(String uri, String nombreLocal,
				String nombreCualif) throws SAXException {
			if (nombreLocal.equals("return")) {
				try {
					lista.add(URLDecoder.decode(cadena.toString(), "UTF-8"));
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				}
			}
			cadena.setLength(0);
		}
	}
}
